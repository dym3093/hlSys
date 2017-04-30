package org.hpin.settlementManagement.service;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipDao;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.ExcelUtils;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.dao.ErpReportdetailPDFContentDao;
import org.hpin.reportdetail.entity.ErpPrintBatch;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.hpin.settlementManagement.dao.ErpComboPriceDao;
import org.hpin.settlementManagement.dao.ErpPrintCompanySettleTaskDao;
import org.hpin.settlementManagement.dao.ErpSettlementCustomerJYDao;
import org.hpin.settlementManagement.entity.ErpComboPrice;
import org.hpin.settlementManagement.entity.ErpPrintComSettleCustomer;
import org.hpin.settlementManagement.entity.ErpPrintCompanySettleTask;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerJY;
import org.hpin.settlementManagement.entity.ErpSettlementTaskJY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.settlementManagement.service.ErpPrintCompanySettleTaskService")
@Transactional()
public class ErpPrintCompanySettleTaskService extends BaseService{
	
	@Autowired
	private ErpPrintCompanySettleTaskDao dao;
	
	@Autowired
	ErpComboPriceDao comboPriceDao;
	
	@Autowired
	ErpReportdetailPDFContentDao contentDao;
	
	@Autowired
	ErpSettlementCustomerJYDao erpSettlementCustomerJYDao;
	
	@Autowired
	CustomerRelationshipDao customerRelationshipDao; // add by YoumingDeng 2016-09-19
	
	/**
     * 分页获取用户
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
        return dao.findByPage(page, searchMap);
    }
    
    /**
     * 添加打印公司结算任务
     * @param companySettleTask
     * @author tangxing
     * @date 2016-5-5下午8:43:06
     */
    public void addPrintCompanySettleTask(ErpPrintCompanySettleTask companySettleTask){
    	dao.addPrintCompanySettleTask(companySettleTask);
    }
    
    /**
	 * 取消添加打印公司结算时，删除对应的导入数据
	 * 
	 * @author tangxing
	 * @date 2016-5-25下午5:01:06
	 */
	public boolean deletePrintCompanyCustomerBatch(String settle_id) throws Exception{
		boolean flag = false;
		//批量删除
		dao.deletePrintCompanyCustomerBatch(settle_id);
		flag = true;
		return flag;
	}
    
    /**
     * 根据id查找打印公司结算任务
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016-5-5下午9:14:45
     */
    public ErpPrintCompanySettleTask getPrintComSettleTask(String settlementId){
    	return dao.getPrintComSettleTask(settlementId);
    }
    
    /**
     * 将导入的会员信息套餐价格找到
     * @param comSettleCustomers
     * @author tangxing
     * @date 2016-5-25下午5:58:43
     */
    public void getSetmealprice(List<ErpPrintComSettleCustomer> comSettleCustomers) throws Exception{
    	List<ErpComboPrice> comboPrices = comboPriceDao.listComboPrice();
    	String cusSetmealName = "";
    	String comboPrice = "";
    	for (ErpPrintComSettleCustomer erpPrintComSettleCustomer : comSettleCustomers) {
    		cusSetmealName = erpPrintComSettleCustomer.getSetmeal_name();
			for (ErpComboPrice erpComboPrice : comboPrices) {
				comboPrice = erpComboPrice.getComboName();
				
				if(cusSetmealName.indexOf(comboPrice)!=-1){
					erpPrintComSettleCustomer.setSetmeal_price(erpComboPrice.getPrice());
					break;
				}else{
					erpPrintComSettleCustomer.setSetmeal_price(BigDecimal.ZERO);
				}
			}
			dao.updatePCCustomer(erpPrintComSettleCustomer);
		}
    }
    
    /**
     * 根据打印公司介绍任务id查找会员信息
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016-5-5下午10:06:26
     */
    public List getPrintComCustomer(String settlementId){
    	return dao.getPrintComCustomer(settlementId);
    }
    
    /**
     * 根据打印公司介绍任务id查找会员信息(套餐分组)
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016-5-5下午10:06:26
     */
    public List getPrintComCustomerGBCombo(String settlementId){
    	return dao.getPrintComCustomerGBCombo(settlementId);
    }
    
    
    /**
     * 核对会员信息后重新加载会员信息（只加载可结算的）
     * @param settlementId
     * @param status
     * @return
     * @author tangxing
     * @date 2016-5-5下午10:06:47
     */
	public List showCustomerByStatus(String settlementId,String status){
		return dao.showCustomerByStatus(settlementId, status);
	}
	
	/**
	 * 保存会员信息
	 * @param file
	 * @param afileName
	 * @param eventsNo
	 * @return Map<String,Object>
	 * @throws ParseException
	 * @author DengYouming
	 * @since 2016-4-27 上午7:28:44
	 */
	public List<ErpPrintComSettleCustomer> saveSettlementCustomer(File file,String settlementTaskId) throws Exception {
		
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		
		List<Map<String, String>> result = ExcelUtils.importSettlementExcel(file);
		
		List<ErpPrintComSettleCustomer> settlements = createSettlementObj(result,currentUser,settlementTaskId);
		
		List<ErpPrintComSettleCustomer> matchSettlements = matchSettlementsAndCustomer(settlements);
		//保存比对好的结果
		this.saveErpSettlementCustomerJYBatch(matchSettlements);
		return matchSettlements;
	}

	private List<ErpPrintComSettleCustomer> matchSettlementsAndCustomer(List<ErpPrintComSettleCustomer> settlements) {
		if(settlements.size()>0){ 
			for(ErpPrintComSettleCustomer settlement : settlements){
				if(("").equals(settlement.getCode())){
					settlement.setStatus("5");//条码为空，异常数据
					continue;
				}
				List<ErpReportdetailPDFContent>  curPdfContents = contentDao.queryUnSettlementContent(settlement.getCode());
				
				if(null==curPdfContents||0==curPdfContents.size()){
					settlement.setStatus("4");
				}else{
					for(ErpReportdetailPDFContent content : curPdfContents){
						if(null!=content.getSettlement_status()&&(content.getSettlement_status()).equals("1")){
							settlement.setStatus("3");
							settlement.setPdfcontent_id(content.getId());
							continue;
						}
						if((content.getUsername()).equals(settlement.getName())&&null!=settlement.getSetmeal_name()&&(settlement.getSetmeal_name().indexOf(content.getSetmeal_name())!=-1)){
							settlement.setStatus("2");//可结算
							settlement.setPdfcontent_id(content.getId());
							break;
						}
						if((content.getUsername()).equals(settlement.getName())&&null!=settlement.getSetmeal_name()&&(settlement.getSetmeal_name().indexOf(content.getSetmeal_name())==-1)){
							settlement.setStatus("1");//信息有误
							settlement.setPdfcontent_id(content.getId());
							continue;
						}
						if(!(content.getUsername()).equals(settlement.getName())){
							settlement.setStatus("1");//信息有误
							settlement.setPdfcontent_id(content.getId());
							continue;
						}
					}
				}
			}
			
		}
		
		return settlements;
	}

	//将EXCEL中的数据组装对象
	private List<ErpPrintComSettleCustomer> createSettlementObj(List<Map<String, String>> result,User user,String settlementTaskId) throws ParseException {
		List<ErpPrintComSettleCustomer> list = new ArrayList<ErpPrintComSettleCustomer>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		
		// 根据支公司名称获取支公司ID所需变量  add by YoumingDeng 2016-09-19 start 
		String branchCompany = null;
		List<CustomerRelationShip> crsList = null;
		CustomerRelationShip crs = null;
		// 根据支公司名称获取支公司ID所需变量  add by YoumingDeng 2016-09-19 end 

		for(Map<String, String> map : result){
			branchCompany = null;
			crsList = null;
			crs = null;
			
			ErpPrintComSettleCustomer customerJY = new ErpPrintComSettleCustomer();
			// 第一行是条码,第二行是姓名,第三行性别,第四行年龄,第五行联系方式,第六行支公司,
			// 第七行检测项目,第八行样本类型,第九行送检医生,第十行送检单位,
			// 第十一行提交者,第十二行送检日期,第十三行收样日期,第十四行状态,第十五行报告
			customerJY.setCode(map.get("0"));
			customerJY.setName(map.get("1"));
			customerJY.setSex(map.get("2"));
			customerJY.setAge(map.get("3"));
			customerJY.setContact(map.get("4"));
			customerJY.setBranch_company(map.get("5"));
			//customerJY.setFamily_history(map.get("6"));
			customerJY.setItems(map.get("6"));
			customerJY.setSetmeal_name(map.get("6"));
			customerJY.setSample_type(map.get("7"));
			customerJY.setCensorship_doctor(map.get("8"));
			customerJY.setCensorship_company(map.get("9"));
			customerJY.setSubmitter(map.get("10"));
			customerJY.setCensorship_time(sdf.parse(map.get("11")));
			customerJY.setReceive_time(sdf.parse(map.get("12")));
			customerJY.setImport_status(map.get("13"));
			customerJY.setReport_name(map.get("14"));
			customerJY.setCreate_user_id(user.getId());
			customerJY.setCreate_time(now);
			customerJY.setCreate_user_name(user.getUserName());
			customerJY.setStatus("0");
			customerJY.setSettlementTask_id(settlementTaskId);
			
			// 根据支公司名称获取支公司ID  add by YoumingDeng 2016-09-19 start 
			branchCompany = map.get("5"); 
			//设置支公司ID
			if(StringUtils.isNotEmpty(branchCompany)){
				crsList = customerRelationshipDao.getCustomerRelationShipBybranch(branchCompany);
				if(crsList!=null&&crsList.size()>0){
					crs = crsList.get(0);
					customerJY.setBranchCompanyId(crs.getId());
				}
			}
			// 根据支公司名称获取支公司ID  add by YoumingDeng 2016-09-19 end 
			
			list.add(customerJY);
		}
		return list;
	}
	
	/**
	 * 批量保存ErpSettlementCustomerJY
	 * @param list
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-3 下午2:25:32
	 */
	public boolean saveErpSettlementCustomerJYBatch(List<ErpPrintComSettleCustomer> list) throws Exception{
		boolean flag = false;
		//批量保存
		erpSettlementCustomerJYDao.savePrintComSettleCustomer(list);
		flag = true;
		return flag;
	}
	
	/**
	 * 根据ID获取读取打印公司结算任务
	 * @param id
	 * @return
	 */
	public ErpPrintCompanySettleTask getSettlementTask(String id){
		return dao.getSettlementTask(id);
	}
	
	/**
	 * 修改结算任务
	 * @author tangxing
	 */
	public void updateSetTask(ErpPrintCompanySettleTask companySettleTask){
		dao.update(companySettleTask);
	}
	
	/**
	 * 修改客户信息状态(集合)
	 * @author tangxing
	 */
	public void updateCustomerPrice(List<ErpPrintComSettleCustomer> customerList){
		for (ErpPrintComSettleCustomer erpPrintComSettleCustomer : customerList) {
			dao.update(erpPrintComSettleCustomer);
		}
	}
	
	/**
	 * 根据ID获取读取客户类
	 * @param id
	 * @return
	 */
	public ErpPrintComSettleCustomer get(String id){
		return dao.get(id);
	}
	
	/**
	 * 修改价格
	 * @author tangxing
	 */
	public void updateCustomerPrice(ErpPrintComSettleCustomer customer){
		dao.update(customer);
	}
	
	/**
	 * excel的数量
	 * @return
	 * @author tangxing
	 * @date 2016-5-9上午10:35:45
	 */
	public int getExcelNum(String settlementId){
		int num = dao.getExcelNum(settlementId);
		return num;
	}
	
	/**
	 * 读取客户信息异常数量
	 * @param settlementId
	 * @return
	 * @author tangxing
	 * @date 2016-5-9上午10:51:27
	 */
	public int getExceptionNum(String settlementId){
		int num = dao.getExceptionNum(settlementId);
		return num;
	}
	
	/**
	 * 统计支公司数量
	 * @param settlementId
	 * @return
	 * @author tangxing
	 * @date 2016-5-16上午10:24:45
	 */
	public int getBranchCompanyNum(String settlementId){
		int num = dao.getBranchCompanyNum(settlementId);
		return num;
	}
	
	/**
	 * 获取当前结算批次的所有套餐数量
	 * @param settlementId
	 * @return
	 * @author tangxing
	 * @date 2016-5-9上午10:54:04
	 */
	public int getComboNumBySettleId(String settlementId){
		List<ErpPrintComSettleCustomer> list = dao.getPrintComCustomer(settlementId);
		List<String> strings = new ArrayList<String>();	//存放套餐
		int size=0;
		Set<String> set = new LinkedHashSet<String>();	//排重
		
		if(list.size()>0){
			for(int i=0;i<list.size(); ++i){
				if(list.get(i).getSetmeal_name()!=null){
					strings.add(list.get(i).getSetmeal_name());
				}
			}
			set.addAll(strings);
			size = set.size();
			return size;
		}else{
			return 0;
		}
	}
	
	/**
	 * 统计总人数
	 * @param settlementId
	 * @return
	 * @author tangxing
	 * @date 2016-5-16上午10:24:45
	 */
	public int getSumNum(String settlementId){
		int num = dao.getSumNum(settlementId);
		return num;
	}
	
	/**
	 * 统计异常数量
	 * @param settlementId
	 * @return
	 * @author tangxing
	 * @date 2016-5-16上午10:24:45
	 */
	public int getExceNum(String settlementId){
		int num = dao.getExceNum(settlementId);
		return num;
	}
	
	/**
	 * 统计套餐数量
	 * @param settlementId
	 * @return
	 * @author tangxing
	 * @date 2016-5-16上午10:24:45
	 */
	public int getComboNum(String settlementId){
		int num = dao.getComboNum(settlementId);
		return num;
	}
	
	/**
	 * 统计可结算数量
	 * @param settlementId
	 * @return
	 * @author tangxing
	 * @date 2016-5-16上午10:24:45
	 */
	public int getIsSett(String settlementId){
		int num = dao.getIsSett(settlementId);
		return num;
	}
	
	
	/*-----通过集合获取各类数量-----*/
	
	/**
	 * 异常数量
	 * @param result
	 * @return
	 * @author tangxing
	 * @date 2016-5-19下午11:08:43
	 */
	public int getAbnormalNum(List<ErpPrintComSettleCustomer> result){
		int num = 0;
		
		for (ErpPrintComSettleCustomer erpPrintComSettleCustomer : result) {
			if(erpPrintComSettleCustomer.getStatus().equals("5")){
				num++;
			}
		}
		
		return num;
	}
	
	/**
	 * 套餐数量
	 * @param result
	 * @return
	 * @author tangxing
	 * @date 2016-5-19下午11:08:43
	 */
	public int getSetMealNum(List<ErpPrintComSettleCustomer> result){
		/*该排重方式只会保留排完重的数据*/
		/*int num = 0;
		String combo="";
		ErpPrintComSettleCustomer comSettleCustomer = new ErpPrintComSettleCustomer();
		List<String> combos = new ArrayList<String>();
		Iterator<ErpPrintComSettleCustomer> it = result.iterator();
		while(it.hasNext()){
			comSettleCustomer = it.next();
        	combo = comSettleCustomer.getSetmeal_name();
        	if(combo!=null){
        		if(combos.contains(combo)){	//排重
            		it.remove();
            	}else{
            		combos.add(combo);
            	}
        	}
        	
        }
		num = combos.size();
		return num;*/
		
		List<String> strings = new ArrayList<String>();	//存放套餐
		int size=0;
		Set<String> set = new LinkedHashSet<String>();	//排重
		
		if(result.size()>0){
			for(int i=0;i<result.size(); ++i){
				if(result.get(i).getSetmeal_name()!=null){
					strings.add(result.get(i).getSetmeal_name());
				}
			}
			set.addAll(strings);
			size = set.size();
			return size;
		}else{
			return 0;
		}
	}
	
	/**
	 * 可结算数量
	 * @param result
	 * @return
	 * @author tangxing
	 * @date 2016-5-19下午11:08:43
	 */
	public int getSuccessNum(List<ErpPrintComSettleCustomer> result){
		int num = 0;
		for (ErpPrintComSettleCustomer erpPrintComSettleCustomer : result) {
			if(erpPrintComSettleCustomer.getStatus().equals("2")){
				num++;
			}
		}
		return num;
	}

	/**
	 * @param string
	 * @return 报告状态
	 */
	public String getPrintState(String id) {
		String sql = "select printstate from erp_printtask where id='"+id+"'";
		String printState = dao.getJdbcTemplate().queryForList(sql).get(0).get("PRINTSTATE").toString();
		return printState;
	}
	
}
