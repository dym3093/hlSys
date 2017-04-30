package org.hpin.settlementManagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpPrintTask;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.hpin.settlementManagement.dao.ErpPrintTaskSettlementDao;
import org.hpin.settlementManagement.entity.ErpPrintComboCost;
import org.hpin.settlementManagement.entity.ErpPrintTaskSettlement;
import org.hpin.venueStaffSettlement.util.CreateNewExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service(value = "org.hpin.settlementManagement.service.ErpPrintTaskSettlementService")
@Transactional()
@SuppressWarnings({"rawtypes","unchecked"})
public class ErpPrintTaskSettlementService extends BaseService {

	@Autowired
	private ErpPrintTaskSettlementDao dao;
	/**excel下载路径*/
	private String printCostSettlementExcel;
	
	private Logger logger = Logger.getLogger(ErpPrintTaskSettlementService.class);
	
	public List findByPage(Page page, Map searchMap) {
    	return dao.findByPage(page, searchMap);
    }

	public List<ErpPrintTaskSettlement> getSettlementId(String printTaskNo) {
		String sql = "from ErpPrintTaskSettlement where printtaskno=?";
		return dao.getHibernateTemplate().find(sql,printTaskNo);
	}

	/**
	 * @param page
	 * @param paramsMap
	 * @return 打印公司结算明细
	 */
	public List<ErpPrintTaskSettlement> getPrintSettlementDetail(Page page,
			Map paramsMap) {
		String printTaskNo = paramsMap.get("filter_and_printTaskNo_LIKE_S").toString();
		String sql = "from ErpPrintTaskSettlement where printTaskNo =?";
		List<ErpPrintTaskSettlement> list = dao.getHibernateTemplate().find(sql,printTaskNo);
		page.setTotalCount((long)list.size());
		return list;
	}

	/**
	 * @param page
	 * @param searchMap
	 * @return 打印结算任务
	 */
	public List<Map<String, Object>> getPrintSettlementTask(Page page,Map<String,Object> searchMap) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ep.id,ep.printtaskno,ep.printstate,ep.createtime,ep.printcompany,ep.expressprice,ep.settlementstate,t.payedprice from erp_printtask ep ");
		sql.append("left join (select epts.printtaskno printtaskno, sum(epts.payedprice) payedprice from erp_print_task_settlement epts group by epts.printtaskno)t on t.printtaskno =ep.printtaskno where ep.isdelete='0' ");
		for(String key:searchMap.keySet()){
			if(key.equals("filter_and_createTime_GEST_T")){//TO_DATE('2016-08-12 12:12:12','yyyy-MM-dd hh24:mi:ss')
				sql.append(" and ep."+getKey(key)+">=to_date('"+searchMap.get(key)+"','yyyy-MM-dd hh24:mi:ss')");
			}else if(key.equals("filter_and_createTime_LEET_T")){
				sql.append(" and ep."+getKey(key)+"<=to_date('"+searchMap.get(key)+" 23:59:59','yyyy-MM-dd hh24:mi:ss')");
			}else{
				sql.append(" and ep."+getKey(key)+"='"+searchMap.get(key)+"'");
			}
		}
		sql.append(" order by ep.createtime desc");
		List<Map<String,Object>> list =dao.getPrintSettlementTask(page,sql.toString());
		return list;
	}
	
	public String getKey(String key) {
		return key.split("_")[2];
	}

	/**
	 * @param companyId
	 * @return 公司信息
	 */
	public String getCompanyInfo(String printTaskNo) {
		String sql1 = "select branchcompanyid from erp_printtask where printtaskno='"+printTaskNo+"'";
		List<Map<String, Object>> list = dao.getJdbcTemplate().queryForList(sql1);
		if(list.size()>=1){
			return list.get(0).get("BRANCHCOMPANYID").toString();
		}else {
			return null;
		}
	}

	/**
	 * @param printTaskNo
	 * @return 报告打印合计金额
	 */
	public List<Map<String, Object>> getTotalPrice(String printTaskNo) {
		String sql = "select sum(epts.payedprice) totalprice from erp_print_task_settlement epts group by epts.printtaskno having printtaskno='"+printTaskNo+"'";
		return dao.getJdbcTemplate().queryForList(sql);
	}

	public int updateAveragePrice(BigDecimal bigDecimal,String printTaskNo) {
		String sql = "update erp_print_task_settlement set averageprice=? where printtaskno=?";
		return dao.getJdbcTemplate().update(sql, bigDecimal,printTaskNo);
	}

	/**
	 * @param printTaskNo 
	 * @return 根据打印任务号获取支公司信息
	 */
	public List<ErpPrintTaskSettlement> getCompanyInfoByTaskNo(String printTaskNo) throws Exception{
		String sql = "from ErpPrintTaskSettlement where printtaskno=?";
		return dao.getHibernateTemplate().find(sql,printTaskNo);
	}

	/**
	 * @param printTaskNo 通过打印任务号删除打印结算任务
	 */
	public void deleteByPrintTaskNo(String id) throws Exception{
		String sql = "update erp_printtask set isdelete='1' where id=?";
		dao.getJdbcTemplate().update(sql, id);
	}
	
	/**
	 * @param ids
	 * @since 2017年1月2日10:56:46
	 * @return 通过ID获取打印任务信息
	 */
	public int[] confirm2UnSettled(String ids) throws Exception{
		List<ErpPrintTask> taskList = getPrintTaskList(ids);
		User user = (User) HttpTool.getSession().getAttribute("currentUser");
		int [] countArray = new int[5];
			for(ErpPrintTask printTask:taskList){
				List<ErpPrintTaskContent> taskContentList = dao.getPrintTaskContentInfo(printTask.getPrintTaskNo());
				if(taskContentList.size()!=0){//该表是新建，之前的数据需要从pdfcontent表中获取
					if(printTask.getSettlementState().equals("0")){
						for(ErpPrintTaskContent taskContent:taskContentList){
							List <ErpPrintComboCost> comboCostList = dao.getPrintComboCostInfo(printTask.getPrintCompanyId(),taskContent.getCombo());
							int count = dao.getPrintSettlementCount(printTask.getPrintTaskNo());
							if(comboCostList.size()==0){
								countArray[3]++;	//没有该套餐价格
							}else{
								if(count==0){
									ErpPrintComboCost comboCost = comboCostList.get(0);
									if(!taskContent.getType().equals("3")){//不是报告单的，只添加报告（pdf）
										dao.save(getPrintTaskSettlement(taskContent,comboCost,user));
									}
								}
								dao.updatePrintTaskState(printTask.getId(),"1");
							}
						}
						countArray[0]++;
					}else if (printTask.getSettlementState().equals("1")) {
						countArray[1]++;	//待支付
					}else if (printTask.getSettlementState().equals("2")) {
						countArray[2]++;	//已支付
					}
				}else {
					if(printTask.getSettlementState().equals("0")){
						List<ErpReportdetailPDFContent> pdfContentList = dao.getPdfContentInfo(printTask.getPrintTaskNo());
						for(ErpReportdetailPDFContent taskContent:pdfContentList){
							List <ErpPrintComboCost> comboCostList = dao.getPrintComboCostInfo(printTask.getPrintCompanyId(),taskContent.getSetmeal_name());
							int count = dao.getPrintSettlementCount(printTask.getPrintTaskNo());
							if(comboCostList.size()==0){
								countArray[3]++;	//没有该套餐价格
							}else{
								if(count==0){
									ErpPrintComboCost comboCost = comboCostList.get(0);
									String pdfName = taskContent.getPdfname();
									logger.info("报告名称："+pdfName);
									if(pdfName.substring(pdfName.lastIndexOf("."),pdfName.length()).equals("pdf")){//不是报告单的，只添加报告（pdf）
										dao.save(getPdfContentSettlement(taskContent,printTask,comboCost,user));
									}
								}
								dao.updatePrintTaskState(printTask.getId(),"1");
							}
						}
						countArray[0]++;
					}else if (printTask.getSettlementState().equals("1")) {
						countArray[1]++;	//待支付
					}else if (printTask.getSettlementState().equals("2")) {
						countArray[2]++;	//已支付
					}
				}
		}
		
		return countArray;
	}
	
	/**
	 * @param taskContent
	 * @param comboCost
	 * @param user
	 * @return 组装明细
	 */
	private ErpPrintTaskSettlement getPrintTaskSettlement(ErpPrintTaskContent taskContent,
			ErpPrintComboCost comboCost,User user){
		ErpPrintTaskSettlement taskSettlement = new ErpPrintTaskSettlement();
		taskSettlement.setPrintTaskNo(taskContent.getPrintTaskNo());
		taskSettlement.setCustomerId(taskContent.getCustomerId());
		taskSettlement.setCode(taskContent.getCode());
		taskSettlement.setName(taskContent.getUserName());
		taskSettlement.setGender(taskContent.getGender());
		taskSettlement.setAge(taskContent.getAge());
		taskSettlement.setCombo(taskContent.getCombo());//不在从客户表中获取客户的套餐而是以分批出来的套餐为准
		taskSettlement.setComboPrintPrice(comboCost.getPrice());
		taskSettlement.setPayedPrice(comboCost.getPrice());
		taskSettlement.setBranchCompanyId(taskContent.getBranchCompanyId());
		taskSettlement.setOwedCompanyId(taskContent.getOwnedCompanyId());
		taskSettlement.setCreateUser(user.getUserName());
		taskSettlement.setCreateTime(new Date());
		taskSettlement.setSaleman(taskContent.getSaleman());
		return taskSettlement;
	}
	
	
	/**
	 * @param pdfContent 
	 * @param printTask 打印任务
	 * @param comboCost 套餐
	 * @param user
	 * @return
	 */
	private ErpPrintTaskSettlement getPdfContentSettlement(ErpReportdetailPDFContent pdfContent,ErpPrintTask printTask,
			ErpPrintComboCost comboCost,User user){
		CustomerRelationShip company = dao.getCompanyInfo(pdfContent.getBranch_company());
		ErpPrintTaskSettlement taskSettlement = new ErpPrintTaskSettlement();
		taskSettlement.setPrintTaskNo(printTask.getPrintTaskNo());
		taskSettlement.setCustomerId(pdfContent.getCustomerid());
		taskSettlement.setCode(pdfContent.getCode());
		taskSettlement.setName(pdfContent.getUsername());
		taskSettlement.setGender(pdfContent.getSex());
		taskSettlement.setAge(pdfContent.getAge());
		taskSettlement.setCombo(pdfContent.getSetmeal_name());//不在从客户表中获取客户的套餐而是以分批出来的套餐为准
		taskSettlement.setComboPrintPrice(comboCost.getPrice());
		taskSettlement.setPayedPrice(comboCost.getPrice());
		taskSettlement.setBranchCompanyId(pdfContent.getBranch_company());
		taskSettlement.setOwedCompanyId(company.getOwnedCompany());
		taskSettlement.setCreateUser(user.getUserName());
		taskSettlement.setCreateTime(new Date());
		taskSettlement.setSaleman(pdfContent.getSales_man());
		return taskSettlement;
	}
	
	private String resultSql(String printTaskId) {
		StringBuilder helps = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		String [] printTaskNos = printTaskId.split(",");
		for (int i = 0; i < printTaskNos.length; i++) {
			if(i==0) {
				helps.append("'"+printTaskNos[i]+"'");
			} else {
				helps.append(",").append("'"+printTaskNos[i]+"'");
			}
		}
		sql.append("select * from erp_printtask where isdelete='0' and id in("+helps+")  order by createtime desc");
		return sql.toString();
	}
	
	/**
	 * @param ids
	 * @return 根据ID返回printtask信息
	 */
	private List<ErpPrintTask> getPrintTaskList(String ids){
		return dao.getJdbcTemplate().query(resultSql(ids),new BeanPropertyRowMapper(ErpPrintTask.class));
		
	}
	
	public int[] confirm2Settled(String ids) {
		List<ErpPrintTask> taskList = getPrintTaskList(ids);
		User user = (User) HttpTool.getSession().getAttribute("currentUser");
		int [] countArray = new int[3];
		for(ErpPrintTask printTask:taskList){
			if(printTask.getSettlementState().equals("0")){
				countArray[0]++;	//可支付
			}else if (printTask.getSettlementState().equals("1")) {
				dao.updatePrintTaskState(printTask.getId(),"2",user.getUserName());
				countArray[1]++;	//待支付
			}else if (printTask.getSettlementState().equals("2")) {
				countArray[2]++;	//已支付
			}
		}
		return countArray;
	}
	
	/**
	 * @param printTaskNo
	 * @param request
	 * @return 获取路径
	 */
	public String getDownloadPath(String printTaskNo, HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).toString();
		String fileName = printTaskNo+"费用明细.xls";
		String outFilePath = printCostSettlementExcel+fileName;
		List<List<String>> resultList = new ArrayList<List<String>>();
		List<ErpPrintTaskSettlement> list = dao.getPrintSettlementDetail(printTaskNo);
		int count = 1;
		for(ErpPrintTaskSettlement settlement:list){
			List<String> detaiList = new ArrayList<String>();
			CustomerRelationShip relationShip = dao.getCompanyInfo(settlement.getBranchCompanyId());
			detaiList.add(String.valueOf(count));
			detaiList.add(settlement.getCode());
			detaiList.add(settlement.getName());
			detaiList.add(settlement.getGender());
			detaiList.add(settlement.getAge());
			detaiList.add(settlement.getCombo());
			detaiList.add(settlement.getComboPrintPrice().toString());
			detaiList.add(settlement.getPayedPrice().toString());
			detaiList.add(relationShip.getBranchCommany());
			detaiList.add(relationShip.getCustomerNameSimple());
			resultList.add(detaiList);
			count++;
		}
		CreateNewExcel.createPrintCostXls(printCostSettlementExcel, fileName, resultList);
//		return contextUrl+outFilePath.substring(2,outFilePath.length()); //window下
//		logger.info("outfilepath截取后的路径："+outFilePath.substring(11,outFilePath.length()));
		return contextUrl+outFilePath.substring(12,outFilePath.length()); //linux下
	}

	public String getPrintCostSettlementExcel() {
		return printCostSettlementExcel;
	}

	public void setPrintCostSettlementExcel(String printCostSettlementExcel) {
		this.printCostSettlementExcel = printCostSettlementExcel;
	}
	
}
