package org.hpin.settlementManagement.Thread;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringContextHolder;
import org.hpin.common.util.HttpTool;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.settlementManagement.dao.ErpComboPriceDao;
import org.hpin.settlementManagement.dao.ErpSettlementCustomerJYDao;
import org.hpin.settlementManagement.dao.ErpSettlementTaskJYDao;
import org.hpin.settlementManagement.entity.ErpComboPrice;
import org.hpin.settlementManagement.entity.ErpSettleExcetaskJY;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerJY;
import org.hpin.settlementManagement.entity.ErpSettlementTaskJY;
import org.hpin.settlementManagement.service.ErpSettlementTaskJYService;

/**
 * 基因公司结算线程(业务逻辑代码实现)
 * @author tangxing
 * @since 2016-08-18
 */
public class ErpSettleTaskJYThread implements Runnable {

	private Logger log = Logger.getLogger(ErpSettleTaskJYThread.class);
	private String settleId = "";
	ErpCustomerDao customerDao=null;
	ErpSettlementTaskJYDao taskJYDao = null;
	 ErpComboPriceDao erpComboPriceDao = null;
	 ErpSettlementCustomerJYDao erpSettlementCustomerJYDao = null;
	 ErpSettlementTaskJYService settlementTaskJYService = null;
	public ErpSettleTaskJYThread(String settleId){
		this.settleId = settleId;
		customerDao = (ErpCustomerDao)SpringContextHolder.getBean("ErpCustomerDao");
		taskJYDao = (ErpSettlementTaskJYDao)SpringContextHolder.getBean("ErpSettlementTaskJYDao");
		erpComboPriceDao = (ErpComboPriceDao)SpringContextHolder.getBean("ErpComboPriceDao");
		erpSettlementCustomerJYDao = (ErpSettlementCustomerJYDao)SpringContextHolder.getBean("ErpSettlementCustomerJYDao");
		settlementTaskJYService = (ErpSettlementTaskJYService)SpringContextHolder.getBean("ErpSettlementTaskJYService");
	}
	
	@Override
	public void run() {
		log.info("ErpSettleTaskJYThread start settleTaskJY: "+ settleId);
		List<ErpSettlementCustomerJY> settlements = new ArrayList<ErpSettlementCustomerJY>();
		ErpSettlementTaskJY erpSettlementTaskJY = new ErpSettlementTaskJY();
		try {
			settlements = taskJYDao.getSettleCustomerByExSettTaskId(settleId);
			erpSettlementTaskJY = settlementTaskJYService.getSettlementTask(settleId);
			List<ErpSettlementCustomerJY> settlementsTemp = matchSettlementsAndCustomer(settlements);
			if(settlementsTemp!=null){
				saveSettleTaskCustomer(settlementsTemp, settleId);
			}
			createExceSettleTask(settleId);
			
			if(erpSettlementTaskJY!=null){
				erpSettlementTaskJY.setStatus("2");		//完成
				settlementTaskJYService.updateSetTask(erpSettlementTaskJY);
			}
		} catch (Exception e) {
			if(erpSettlementTaskJY!=null){
				erpSettlementTaskJY.setStatus("0");		//回滚为新增状态
				settlementTaskJYService.updateSetTask(erpSettlementTaskJY);
			}
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * 比对导入的Excel客户信息（从ErpCustomer里面去拿）
	 * @param settlements
	 * @return
	 * @author tangxing
	 * @date 2016-6-13下午5:42:52
	 */
	private List<ErpSettlementCustomerJY> matchSettlementsAndCustomer(List<ErpSettlementCustomerJY> settlements) throws Exception{
		if(settlements.size()>0){ 
			for(ErpSettlementCustomerJY settlement : settlements){
				String code = settlement.getCode();
				if(StringUtils.isEmpty(code)){
					settlement.setStatus("5");						//条码为空
					continue;
				}
				
				//根据当前客户的条形码到pdf客户表去找
				
				List<ErpCustomer> customers = customerDao.getCustomerByCode(settlement.getCode());
				/*List<ErpReportdetailPDFContent>  curPdfContents = contentDao.findSettlementContent(settlement.getCode());*/
				if(null==customers||0==customers.size()){	
					settlement.setStatus("4");						//信息不存在
				}else{
					//找到条码对应的信息后，遍历比对
					for(ErpCustomer customer : customers){
						String pdfId = customer.getId();
						if(StringUtils.isEmpty(customer.getPdffilepath())){	//根据customer是否有pdf路径，判断报告状态
							settlement.setPdf_Status("1");//未出
						}else{
							settlement.setPdf_Status("0");//已出
						}
						
						String name = customer.getName();
						String age = customer.getAge();
						if(name==null){
							name = "";
						}
						if(age==null){
							age = "";
						}
						
						//条码,姓名，性别 有一个不一致，为信息有误
						if(!name.equals(settlement.getName())
								||!age.equals(settlement.getAge())){
							settlement.setStatus("3");
							settlement.setPdfcontent_id(pdfId);
							continue;
						}
						
						//条码,姓名，性别一致。匹配结算状态
						if(name.equals(settlement.getName())
								&&customer.getCode().equals(settlement.getCode())
								&&age.equals(settlement.getAge())){
							if(StringUtils.isEmpty(customer.getSettlement_status())||customer.getSettlement_status().equals("0")){//可结算
								settlement.setStatus("0");	
								settlement.setPdfcontent_id(pdfId);
							}else if(customer.getSettlement_status().equals("1")){	//待结算
								settlement.setStatus("1");
								settlement.setPdfcontent_id(pdfId);
							}else if(customer.getSettlement_status().equals("2")){	//已结算
								settlement.setStatus("2");
								settlement.setPdfcontent_id(pdfId);
							}
						}
					}
				}
			}
		}
		return settlements;
	}
	
	private BigDecimal saveSettleTaskCustomer(List<ErpSettlementCustomerJY> settlements,String id){
		int sumSize = settlements.size();//总条数
		int dbNum = 1700;
		int i=0;
		int a = sumSize%dbNum;		//余数
		int b = sumSize/dbNum;		//被800整除的次数
		List<ErpSettlementCustomerJY> list = new ArrayList<ErpSettlementCustomerJY>();
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		BigDecimal bigDecimal = BigDecimal.ZERO;
		BigDecimal bigDecimalTwo = BigDecimal.ZERO;
		ErpSettlementTaskJY settlementTaskJY = taskJYDao.getSettlementTask(id);
		//List<ErpSettlementCustomerJY> erpSettlementCustomerJYList = erpSettlementCustomerJYDao.getSettleCustomerBySettIdStatus(ids);	//获取非异常任务的数据
		for (ErpSettlementCustomerJY erpSettlementCustomerJY : settlements) {
			
			String status = erpSettlementCustomerJY.getStatus();
			
			if(!status.equals("2")||!status.equals("3")||!status.equals("4")||!status.equals("5")){
				String comboName = erpSettlementCustomerJY.getSetmeal_name();	//套餐名字
				//根据套餐名称查找套餐价格
				List<ErpComboPrice> comboPrices = erpComboPriceDao.listComboPriceByGeCompanyId(settlementTaskJY.getGeCompanyId());
				
				for (ErpComboPrice erpComboPrice : comboPrices) {
					if(comboName.contains(erpComboPrice.getComboName())){
						//关联套餐相关
						erpSettlementCustomerJY.setSetmeal_id(erpComboPrice.getComboId());
						erpSettlementCustomerJY.setSetmeal_name(erpComboPrice.getComboName());
						erpSettlementCustomerJY.setSetmeal_price(erpComboPrice.getPrice());
						break;
					}else{
						erpSettlementCustomerJY.setSetmeal_price(bigDecimalTwo);
					}
				}
				
				//创建人，ID及时间
				erpSettlementCustomerJY.setCreate_user_name(currentUser.getUserName());
				erpSettlementCustomerJY.setCreate_user_id(currentUser.getId());
				erpSettlementCustomerJY.setCreate_time(new Date());
				
				bigDecimal = bigDecimal.add(erpSettlementCustomerJY.getSetmeal_price());
				//结算中的状态
//				erpSettlementCustomerJY.setStatus("2");
				//更新
				//erpSettlementCustomerJYDao.update(erpSettlementCustomerJY);
				list.add(erpSettlementCustomerJY);
				
				int size = list.size();
				if(a==0){
					if(size==dbNum){
						erpSettlementCustomerJYDao.saveSettCustomerListAll(list);
						list.clear();
					}
				}else{
					if(size==dbNum){
						erpSettlementCustomerJYDao.saveSettCustomerListAll(list);
						list.clear();
						++i;
					}
					if(i==b&&size==a){
						log.info("createSettlementObj surplus List size----"+list.size());
						erpSettlementCustomerJYDao.saveSettCustomerListAll(list);
					}
				}
			}
			
		}
		return bigDecimal;
	}
	
	public void createExceSettleTask(String id) throws Exception{
		List<ErpSettlementCustomerJY> settCusException = taskJYDao.getCustomerByStatus(id);
		//List<ErpSettlementCustomerJY> settCusException = settlementTaskJYService.getSettleCustomerByTaskId(erpSettlementTaskJY.getId());//异常结算任务客户集合
		//ErpSettlementTaskJY settleTask = settlementTaskJYService.getSettleTaskJYById(erpSettlementTaskJY.getId());
		ErpSettlementTaskJY settleTask = taskJYDao.getSettlementTask(id);
		Integer noFindNum=settlementTaskJYService.getNoFindNum(settCusException);						//条码不存在数量
		Integer branchCompanyNum=settlementTaskJYService.getBranchCompanyNum(settCusException);			//支公司数
		Integer errInfoNum=settlementTaskJYService.getErrInfoNum(settCusException);						//信息有误数量
		Integer setMealNum=settlementTaskJYService.getSetMealNum(settCusException);						//套餐数
		Integer codeNullNum=settlementTaskJYService.getCodeNullNum(settCusException);					//条码为空数量
		
		//保存比对的客户到异常任务
		if(settCusException.size()>0){					//当前的结算批次成功save了，异常客户信息数量大于0条
			String s = "JSYC";
			String defTaskNum = settlementTaskJYService.defTaskNum();
			int ranNum = settlementTaskJYService.ranNum();
			Integer sum = settCusException.size();							//异常数量（总数）
			String exceSettleTaskNo = s+defTaskNum+ranNum;					//异常结算任务号
			//到新增页面时生成ID
			String ids = UUID.randomUUID().toString().replace("-", "");
			ErpSettleExcetaskJY erpSettleExcetaskJY = new ErpSettleExcetaskJY();
			
			erpSettleExcetaskJY.setId(ids);									//ID
			erpSettleExcetaskJY.setExceptionTaskNo(exceSettleTaskNo);		//异常任务号
			erpSettleExcetaskJY.setTaskNo(settleTask.getTaskNo());			//当前的结算任务的任务号
			erpSettleExcetaskJY.setGeCompany(settleTask.getGeCompany());	//基因公司名字
			erpSettleExcetaskJY.setGeCompanyId(settleTask.getGeCompanyId());//基因公司ID
			erpSettleExcetaskJY.setAbnormalNum(sum);						//异常数量
			erpSettleExcetaskJY.setBranchCompanyNum(branchCompanyNum);
			erpSettleExcetaskJY.setNoFindNum(noFindNum);
			erpSettleExcetaskJY.setErrInfoNum(errInfoNum);
			erpSettleExcetaskJY.setCodeNullNum(codeNullNum);
			erpSettleExcetaskJY.setSetMealNum(setMealNum);
			
			erpSettleExcetaskJY.setCreateTime(new Date());					//创建时间
			erpSettleExcetaskJY.setIsDeleted(0);
			
			for (ErpSettlementCustomerJY erpSettlementCustomerJY : settCusException) {
				erpSettlementCustomerJY.setExceSettle_id(erpSettleExcetaskJY.getId());
			}
			
			try {
				settlementTaskJYService.saveExcepSettleTask(erpSettleExcetaskJY);			//save异常结算任务
				settlementTaskJYService.updateExceSettCustomer(settCusException);			//设置异常任务的id
			} catch (Exception e) {
				log.error("createExceSettleTask for saveExcepSettleTask and updateExceSettCustomer",e);
			}
		}
	}

}
