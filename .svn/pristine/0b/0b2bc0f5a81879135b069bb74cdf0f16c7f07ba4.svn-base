package org.hpin.settlementManagement.web;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringContextHolder;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpPrintTask;
import org.hpin.reportdetail.service.ErpPrintTaskService;
import org.hpin.settlementManagement.entity.ErpPrintComSettleCustomer;
import org.hpin.settlementManagement.entity.ErpPrintCompanySettleTask;
import org.hpin.settlementManagement.entity.ErpPrintTaskSettlement;
import org.hpin.settlementManagement.service.ErpPrintCompanySettleTaskService;
import org.hpin.settlementManagement.service.ErpPrintTaskSettlementService;

/**
 * 打印公司结算Action
 * @author tangxing
 * @date 2016-5-5下午5:25:43
 */

@Namespace("/settlementManagement")
@Action("erpPrintCompanySettleTask")
@Results({
	@Result(name="listPrintCompanySettleTask", location="/WEB-INF/settlementManagement/listPrintCompanySettleTask.jsp"),
	@Result(name="addPrintCompanySettleTask", location="/WEB-INF/settlementManagement/addPrintCompanySettleTask.jsp"),
	@Result(name="importPrintCompanyCustomer", location="/WEB-INF/settlementManagement/importPrintCompanyCustomer.jsp"),
	@Result(name="printCompanySettleTaskDetail", location="/WEB-INF/settlementManagement/printCompanySettleTaskDetail.jsp"),
	@Result(name="modifyPrintCompanyCustomer", location="/WEB-INF/settlementManagement/modifyPrintCompanyCustomer.jsp"),
	@Result(name="modifyPrintCompanyPriceEnd", location="/WEB-INF/settlementManagement/modifyPrintCompanyPriceEnd.jsp"),
	@Result(name="listPrintSettlementDetail", location="/WEB-INF/settlementManagement/listPrintSettlementDetail.jsp"),
	@Result(name="showPrintComboPrice", location="/WEB-INF/settlementManagement/showPrintComboPrice.jsp"),
})
@SuppressWarnings({"unchecked"})
public class ErpPrintCompanySettleTaskAction extends BaseAction {

	ErpPrintCompanySettleTaskService printCompanySettleTaskService = (ErpPrintCompanySettleTaskService) SpringTool.getBean(ErpPrintCompanySettleTaskService.class);
	/** 打印结算 */
	ErpPrintTaskSettlementService printSettlementService = (ErpPrintTaskSettlementService)SpringContextHolder.getBean("printCostSettlementExcelFile");
	CustomerRelationShipService customerService = (CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
	ErpPrintTaskService taskService = (ErpPrintTaskService)SpringTool.getBean(ErpPrintTaskService.class);
	
	ErpPrintCompanySettleTask erpPrintCompanySettleTask;
	
	private Logger logger = Logger.getLogger(ErpPrintCompanySettleTaskAction.class);
	
	private File affix;
	
	private Page page;
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * @return
	 * @author tangxing
	 * @date 2016-5-5下午5:27:25
	 */
	public String listPrintCompanySettleTask(){
        Map<String,Object> searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
        try {
        	if(page == null) {
        		page = new Page<ErpPrintTask>(HttpTool.getPageNum(),HttpTool.getPageSize());
        	}
			List<Map<String, Object>> printTaskList=printSettlementService.getPrintSettlementTask(page, searchMap);
			page.setResults(printTaskList) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "listPrintCompanySettleTask";
	}
	
	public String listPrintSettlementDetail() {
		try {
			Map<String,Object> map = buildSearch();
			String printTaskId = HttpTool.getParameter("printTaskId");
//			String companyId = printSettlementService.getCompanyInfo(printTaskNo);
			ErpPrintTask printTask = (ErpPrintTask) taskService.findById(printTaskId);
			List<ErpPrintTaskSettlement> taskContent = printSettlementService.getCompanyInfoByTaskNo(printTask.getPrintTaskNo());
			if(taskContent.size()!=0){
				CustomerRelationShip company = (CustomerRelationShip) customerService.findById(taskContent.get(0).getBranchCompanyId());
				page = new Page<ErpPrintTaskSettlement>(HttpTool.getPageNum(),HttpTool.getPageSize());
				map.put("filter_and_printTaskNo_LIKE_S", printTask.getPrintTaskNo());
				List<ErpPrintTaskSettlement> printSettlementList=printSettlementService.findByPage(page, map);
				for(ErpPrintTaskSettlement taskSettlement:printSettlementList){
					CustomerRelationShip relationShip = (CustomerRelationShip) customerService.findById(taskSettlement.getBranchCompanyId());
					taskSettlement.setBranchCompany(relationShip.getBranchCommany());
					taskSettlement.setOwedCompany(relationShip.getCustomerNameSimple());
				}
				List<Map<String, Object>> totalPriceList = printSettlementService.getTotalPrice(printTask.getPrintTaskNo());
				page.setResults(printSettlementList);
				if (company!=null) {
					HttpTool.setAttribute("proUser", company.getProUser());	//项目负责人
					HttpTool.setAttribute("proCode", company.getProCode());	//项目编号
					HttpTool.setAttribute("insurerName", taskContent.get(0).getSaleman());	//对接人
				}
				if (totalPriceList.size()!=0) {
					HttpTool.setAttribute("totalPrice", totalPriceList.get(0).get("TOTALPRICE"));
				}
			}
			HttpTool.setAttribute("printCompany", printTask.getPrintCompany());	//打印公司
			HttpTool.setAttribute("printTaskNo", printTask.getPrintTaskNo());
			HttpTool.setAttribute("reportEndTime", printTask.getReportEndTime());
			HttpTool.setAttribute("settltmentState", printTask.getSettlementState());
			HttpTool.setAttribute("printTaskId", printTaskId);
			
		} catch (Exception e) {
			logger.error("ErpPrintCompanySettleTaskAction listPrintSettlementDetail", e);
		}
		return "listPrintSettlementDetail";
	}
	
	/**
	 * 	导出打印任务结算明细
	 * @return 
	 */
	public void exportPrintSettlementDetail(){
		JSONObject jsonObject = new JSONObject();
		try {
			String printTaskNo = HttpTool.getParameter("printTaskNo");
			HttpServletRequest request = ServletActionContext.getRequest();
			String filePath = printSettlementService.getDownloadPath(printTaskNo, request);
			jsonObject.put("path",filePath);
		} catch (Exception e) {
			logger.error("exportPrintSettlementDetail+-----"+e);
		}
		renderJson(jsonObject);
	}
	
	/**
	 * @return 显示套餐打印价格
	 */
	public String showPrintComboPrice(){
		try {
			HttpTool.setAttribute("id", HttpTool.getParameter("id"));
			HttpTool.setAttribute("price", HttpTool.getParameter("price"));
			HttpTool.setAttribute("payedPrice", HttpTool.getParameter("payedPrice"));
			HttpTool.setAttribute("combo", java.net.URLDecoder.decode(HttpTool.getParameter("combo"),"UTF-8"));
			HttpTool.setAttribute("printTaskId", HttpTool.getParameter("printTaskId"));
		} catch (Exception e) {
			logger.error("showPrintComboPrice",e);
		}
		return "showPrintComboPrice";
	}
	
	/**
	 *  设置该打印报告不结算
	 */
	public void editPayedPrice1(){
		String id = HttpTool.getParameter("id");
		JSONObject json = new JSONObject();
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		try {
			ErpPrintTaskSettlement printSettlement = (ErpPrintTaskSettlement) printSettlementService.findById(id);
			printSettlement.setUpdateTime(new Date());
			printSettlement.setUpdateUser(user.getAccountName());
			printSettlement.setPayedPrice(new BigDecimal(0));
			printSettlementService.update(printSettlement);
			json.put("count", 1);
		} catch (Exception e) {
			logger.error("ErpPrintCompanySettleTaskAction editPayedPrice1", e);
			json.put("count", 0);
		}
		renderJson(json);
	}
	
	/**
	 *  更改实际支付价格
	 */
	public void editPayedPrice2(){
		String id = HttpTool.getParameter("id");
		String combo = HttpTool.getParameter("combo");
		String payedPrice = HttpTool.getParameter("price");
		JSONObject json = new JSONObject();
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		try {
			ErpPrintTaskSettlement printSettlement = (ErpPrintTaskSettlement) printSettlementService.findById(id);
			printSettlement.setUpdateTime(new Date());
			printSettlement.setUpdateUser(user.getAccountName());
			printSettlement.setPayedPrice(new BigDecimal(payedPrice));
			printSettlement.setCombo(combo);
			printSettlementService.update(printSettlement);
			json.put("count", 1);
		} catch (Exception e) {
			logger.error("ErpPrintCompanySettleTaskAction editPayedPrice1", e);
			json.put("count", 0);
		}
		renderJson(json);
	}
	
	/**
	 *  提交结算
	 */
	public void submitSettlement(){
		String ids = HttpTool.getParameter("ids");
		User user =(User)HttpTool.getSession().getAttribute("currentUser");
		String [] idArray= ids.split(",");
		int count = 0;
		JSONObject json = new JSONObject();
		try {
			for(int i=0;i<idArray.length;i++){
				ErpPrintTask task = (ErpPrintTask) taskService.findById(idArray[i]);
				task.setUpdateTime(new Date());
				task.setUpdateUsername(user.getAccountName());
				task.setSettlementState("1");
				taskService.update(task);
				count ++;
			}
			json.put("count", count);
		} catch (Exception e) {
			json.put("count", 0);
			logger.error("ErpPrintCompanySettleTaskAction submitSettlement", e);
		}
		renderJson(json);
	}
	
	/**
	 *  确认结算
	 */
	public void confirmSettlement2(){
		String ids = HttpTool.getParameter("ids");
		User user =(User)HttpTool.getSession().getAttribute("currentUser");
		String [] idArray= ids.split(",");
		int count = 0;
		JSONObject json = new JSONObject();
		try {
			for(int i=0;i<idArray.length;i++){
				ErpPrintTask task = (ErpPrintTask) taskService.findById(idArray[i]);
				task.setUpdateTime(new Date());
				task.setUpdateUsername(user.getAccountName());
				task.setSettlementState("2");
				taskService.update(task);
				count ++;
			}
			json.put("count", count);
		} catch (Exception e) {
			json.put("count", 0);
			logger.error("ErpPrintCompanySettleTaskAction confirmSettlement2", e);
		}
		renderJson(json);
	}
	
	/**
	 * 添加打印公司结算任务初始化页面
	 * @author tangxing
	 * @date 2016-5-5下午6:30:16
	 */
	public String toAddPrintCompanySettleTask(){
		String id = UUID.randomUUID().toString().replace("-", "");
		HttpTool.setAttribute("id", id);							//预生成ID
		String navTabId = HttpTool.getParameter("navTabId");
		HttpTool.setAttribute("navTabId", navTabId);
		
		return "addPrintCompanySettleTask";
	}
	
	/**
	 * 添加打印公司结算任务
	 * @author tangxing
	 * @date 2016-5-5下午7:28:49
	 */
	public void addPrintCompanySettleTask(){
		JSONObject json = new JSONObject();
		ErpPrintCompanySettleTask companySettleTask = new ErpPrintCompanySettleTask();
		List<ErpPrintComSettleCustomer> comSettleCustomerList = new ArrayList<ErpPrintComSettleCustomer>();
		
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		String settlementId = HttpTool.getParameter("id");
		String taskNo = HttpTool.getParameter("taskNo");
		String taskName = HttpTool.getParameter("taskName");
		String printCompany = HttpTool.getParameter("printCompany");
		String settlementTime = HttpTool.getParameter("settlementTime");
		
		try {
			date = sdf.parse(settlementTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		companySettleTask.setId(settlementId);
		companySettleTask.setTaskNo(taskNo);
		companySettleTask.setTaskName(taskName);
		companySettleTask.setPrintCompany(printCompany);
		companySettleTask.setSettlementTime(date);
		companySettleTask.setCreateTime(new Date());
		companySettleTask.setCreateUser(currentUser.getUserName());
		companySettleTask.setStatus("0");
		
		comSettleCustomerList = printCompanySettleTaskService.getPrintComCustomerGBCombo(settlementId);//该打印公司结算批次导入的会员信息
		
		
		try {
			printCompanySettleTaskService.addPrintCompanySettleTask(companySettleTask);
			printCompanySettleTaskService.getSetmealprice(comSettleCustomerList);					//将有套餐价格的，设置好价格
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		if(flag){
    		json.accumulate("statusCode", 200);
			json.accumulate("message", "添加成功");
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "closeCurrent");
    	}else{
    		json.accumulate("statusCode", 300);
			json.accumulate("message", "添加失败");
    	}
    	renderJson(json);
	}
	
	/**
	 * 跳转到导入客户信息Excel页面
	 * @return
	 * @author tangxing
	 * @since 2016-5-6 11:00:44
	 */
	public String toImportCustomerPage(){
		//获取结算任务ID
		String settlementTaskId = HttpTool.getParameter("id");		
		//把结算任务ID传到导入Excel页面
		HttpTool.setAttribute("settlementTaskId", settlementTaskId);
		HttpTool.setAttribute("navTabId", super.navTabId);
		return "importPrintCompanyCustomer";
	}
	
	/**
	 * 将导入的Excel客户信息保存到对应的打印公司结算任务
	 * 
	 * @author tangxing
	 * @date 2016-5-6下午1:58:40
	 */
	public void saveCustomerByPrintCompany(){
		//获取结算任务ID
		String settlementTaskId = HttpTool.getParameter("settlementTaskId");
		JSONObject json = new JSONObject();
		//获取总人数
		Integer total_person_num = 0;
		List<ErpPrintComSettleCustomer> result = new ArrayList<ErpPrintComSettleCustomer>();
		
		
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			if(null!=affix){
				result = printCompanySettleTaskService.saveSettlementCustomer(affix,settlementTaskId);
			}
			
			/*Integer sumNum = printCompanySettleTaskService.getSumNum(settlementTaskId);	//总人数
			Integer exceNum = printCompanySettleTaskService.getExceNum(settlementTaskId);	//异常数
			Integer comboNum = printCompanySettleTaskService.getComboNum(settlementTaskId);	//套餐数
			Integer isSett = printCompanySettleTaskService.getIsSett(settlementTaskId);		//可结算数
			 */	
			Integer totalPersonNum=result.size();			//总人数
			Integer abnormalNum=printCompanySettleTaskService.getAbnormalNum(result);					//异常数
			Integer successNum=printCompanySettleTaskService.getSuccessNum(result);						//可结算数
			Integer setMealNum=printCompanySettleTaskService.getSetMealNum(result);						//套餐数(此时集合里面的数据，是排完重的)
			
			json.put("setMealNum", setMealNum);
			json.put("totalPersonNum", totalPersonNum);
			json.put("successNum", successNum);
			json.put("abnormalNum", abnormalNum);
			json.put("statusCode", 200);
			json.put("message", "导入成功");
//			json.put("navTabId", super.navTabId);
			json.put("callbackType", "closeCurrent");
			
			page.setResults(result);
		} catch (Exception e) {
			json.put("statusCode", 300);
			json.put("message", "导入失败");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		//把ID传回
		HttpTool.setAttribute("totalPersonNum", total_person_num);
		HttpTool.setAttribute("id", settlementTaskId);
		HttpTool.setAttribute("page", page);
		renderJson(json);
	}
	
	/**
	 * 取消添加打印公司结算时，删除对应的导入数据
	 * 
	 * @author tangxing
	 * @date 2016-5-25下午5:01:06
	 */
	public void deletePrintCompanyCustomerBatch(){
		JSONObject json = new JSONObject();
		json.put("result","error");
		//获取结算任务ID
		String settle_id = HttpTool.getParameter("id");
		System.out.println("settle_id: "+settle_id);
		if(settle_id!=null&&settle_id.length()>0){
			try {
				//批量删除
				printCompanySettleTaskService.deletePrintCompanyCustomerBatch(settle_id);
				json.put("result","success");
			} catch (Exception e) {	
				json.put("result","error");
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		renderJson(json);
	}
	
	
	/**
	 * 根据ID获取打印公司结算批次
	 * @return
	 * @author tangxing
	 * @date 2016-5-5下午9:07:46
	 */
	public String getPrintComSettleTaskById(){
		String settlementId = HttpTool.getParameter("id");
//		Page page = null;
		int baCompanyNum=printCompanySettleTaskService.getBranchCompanyNum(settlementId);		//支公司数
		int comboNum = printCompanySettleTaskService.getComboNumBySettleId(settlementId);		//套餐数
		int excelNum=printCompanySettleTaskService.getExcelNum(settlementId);					//excel总数
		int exceptionNum=printCompanySettleTaskService.getExceptionNum(settlementId);			//异常数量
		int readNum=excelNum-exceptionNum;														//读取数量（excel总数-异常数量）
		
		
		int settlementNum=0;	//可结算数量
		int errorNum=0;			//信息错误数量
		int nullNum=0;			//不存在异常
		int okSettlementNum=0;	//已经结算数量
		
		
		ErpPrintCompanySettleTask  companySettleTask= printCompanySettleTaskService.getPrintComSettleTask(settlementId);
		
		//初始化客户信息集合
		List<ErpPrintComSettleCustomer> comSettleCustomerList = printCompanySettleTaskService.getPrintComCustomer(settlementId);

		if(comSettleCustomerList.size()>0){
			for(int i=0;i<comSettleCustomerList.size(); ++i){
				if(comSettleCustomerList.get(i).getStatus().equals("1")){
					errorNum++;
				}else if(comSettleCustomerList.get(i).getStatus().equals("2")){
					settlementNum++;
				}else if(comSettleCustomerList.get(i).getStatus().equals("3")){
					okSettlementNum++;
				}else if(comSettleCustomerList.get(i).getStatus().equals("4")){
					nullNum++;
				}else{
					;
				}
			}
		}
		
		HttpTool.setAttribute("settlementId", settlementId);
		HttpTool.setAttribute("settlementNum", settlementNum);
 		HttpTool.setAttribute("errorNum", errorNum);
		HttpTool.setAttribute("nullNum", nullNum);
		HttpTool.setAttribute("okSettlementNum", okSettlementNum);
		
		HttpTool.setAttribute("baCompanyNum", baCompanyNum);
		HttpTool.setAttribute("comboNum", comboNum);
		HttpTool.setAttribute("excelNum", excelNum);
		HttpTool.setAttribute("readNum", readNum);
		HttpTool.setAttribute("exceptionNum", exceptionNum);
		
		//结算任务
		HttpTool.setAttribute("erpPrintCompanySettleTask", companySettleTask);
		System.out.println("集合大小---------"+comSettleCustomerList.size());
		page = new Page();
		page.setResults(comSettleCustomerList);
    	
		return "printCompanySettleTaskDetail";
	}
	
	/**
	 * 确认结算任务重新加载页面(只加载状态为可结算的)
	 * @author tangxing
	 */
	public void getSettlementPriceById(){
		JSONObject jsonObject = new JSONObject();
		String settlementId = HttpTool.getParameter("id");
		//初始化客户信息集合
		List<ErpPrintComSettleCustomer> comSettleCustomerList = printCompanySettleTaskService.showCustomerByStatus(settlementId,"2");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(ErpPrintComSettleCustomer customerJY : comSettleCustomerList){
			String censorshiptime = sdf.format(customerJY.getCensorship_time());
			customerJY.setCensorshiptime(censorshiptime);
			String receivetime = sdf.format(customerJY.getReceive_time());
			customerJY.setReceivetime(receivetime);
			String status = customerJY.getStatus();
			//0:初始状态;1:信息有误2:可结算3:已结算4:信息不存在5:异常
			if(("1").equals(status)){
				customerJY.setStatusView("信息有误");
			}
			if(("2").equals(status)){
				customerJY.setStatusView("可结算");
			}
			if(("3").equals(status)){
				customerJY.setStatusView("已结算");
			}
			if(("4").equals(status)){
				customerJY.setStatusView("信息不存在");
			}
			if(("5").equals(status)){
				customerJY.setStatusView("信息异常");
			}
		}
		
		jsonObject.put("settlementCustomer", comSettleCustomerList);
		renderJson(jsonObject);
	}
	
	/**
	 * 根据不同状态查找客户信息
	 * @author tangxing
	 * @date 2016-5-5下午10:09:24
	 */
	public void showCustomerByStatus(){
		JSONObject jsonObject = new JSONObject();
		String transStatus = HttpTool.getParameter("status");//传递过来的状态
		String settlementId = HttpTool.getParameter("id");
		
		List<ErpPrintComSettleCustomer> comSettleCustomerList = printCompanySettleTaskService.showCustomerByStatus(settlementId,transStatus);
		//初始化客户信息集合
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(ErpPrintComSettleCustomer customerJY : comSettleCustomerList){
			String censorshiptime = sdf.format(customerJY.getCensorship_time());
			customerJY.setCensorshiptime(censorshiptime);
			String receivetime = sdf.format(customerJY.getReceive_time());
			customerJY.setReceivetime(receivetime);
			String status = customerJY.getStatus();
			//0:初始状态;1:信息有误2:可结算3:已结算4:信息不存在5:异常
			if(("1").equals(status)){
				customerJY.setStatusView("信息有误");
			}
			if(("2").equals(status)){
				customerJY.setStatusView("可结算");
			}
			if(("3").equals(status)){
				customerJY.setStatusView("已结算");
			}
			if(("4").equals(status)){
				customerJY.setStatusView("信息不存在");
			}
			if(("5").equals(status)){
				customerJY.setStatusView("信息异常");
			}
		}
		jsonObject.put("settlementCustomer", comSettleCustomerList);
		renderJson(jsonObject);
	}
	
	/**
	 * 确认结算
	 * 
	 * @author tangxing
	 * @date 2016-5-6下午5:10:24
	 */
	public void confirmSettlement(){
		boolean flag = false;
		JSONObject json = new JSONObject();
		String settlementId = HttpTool.getParameter("id");
		BigDecimal decimal = BigDecimal.ZERO;
		//获取当前结算任务，并将所有能结算的客户价格相加
		List<ErpPrintComSettleCustomer> seCustomerList = printCompanySettleTaskService.showCustomerByStatus(settlementId,"2");
		ErpPrintCompanySettleTask companySettleTask= printCompanySettleTaskService.getSettlementTask(settlementId);
		
		//存放更改状态为已结算的会员信息
		List<ErpPrintComSettleCustomer> containerCustomerList = new ArrayList<ErpPrintComSettleCustomer>();	
		for (ErpPrintComSettleCustomer sCustomerJY : seCustomerList) {
			if(sCustomerJY.getSetmeal_price()!=null){
				decimal = decimal.add(sCustomerJY.getSetmeal_price());
			}else{
				;
			}
			sCustomerJY.setStatus("3");						//更改会员信息状态
			containerCustomerList.add(sCustomerJY);
		}
		
		try {
			companySettleTask.setTotalAmount(decimal);		//结算总价
			companySettleTask.setStatus("2");				//结算完成
			printCompanySettleTaskService.updateSetTask(companySettleTask);
			if(containerCustomerList.size()>0){
				printCompanySettleTaskService.updateCustomerPrice(containerCustomerList);	//将当前可结算会员集合状态改为已结算
			}else{
				;
			}
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		
		if(flag){
    		/*json.accumulate("statusCode", 200);
			json.accumulate("message", "结算成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "closeCurrent");*/
			
    	}else{
    		/*json.accumulate("statusCode", 300);
			json.accumulate("message", "结算失败");*/
    	}
		json.put("result","success");
    	renderJson(json);
	}
	
	
	/**
	 * 核对更改客户信息初始化页面
	 * @return
	 * @author tangxing
	 * @date 2016-5-6下午6:34:13
	 */
	public String toModifyCustomerInfo(){
		String id = HttpTool.getParameter("id");
		ErpPrintComSettleCustomer  comSettleCustomer = printCompanySettleTaskService.get(id);
		
		HttpTool.setAttribute("erpSettlementCustomerJY", comSettleCustomer);
		
		return "modifyPrintCompanyCustomer";
	}
	
	/**
	 * 核对更改客户信息
	 * 
	 * @author tangxing
	 * @date 2016-5-6下午6:34:24
	 */
	public void modifyCustomerInfo(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		boolean flag = false;
		String name = HttpTool.getParameter("name");				//姓名
		String setmealName = HttpTool.getParameter("setmealName");	//套餐名字
		
		String setCustomerId = HttpTool.getParameter("setCustomerId");
		ErpPrintComSettleCustomer customer = printCompanySettleTaskService.get(setCustomerId);	//根据ID获取客户对象
		
		try {
			customer.setName(name);
			customer.setSetmeal_name(setmealName);
			customer.setUpdate_user_name(currentUser.getUserName());
			customer.setUpdate_time(new Date());
			customer.setStatus("2");
			printCompanySettleTaskService.updateCustomerPrice(customer);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		
    	if(flag){
    		json.accumulate("statusCode", 200);
			json.accumulate("message", "修改成功");
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "refreshCurrent");
    	}else{
    		json.accumulate("statusCode", 300);
			json.accumulate("message", "修改失败");
    	}
    	renderJson(json);
	}
	
	/**
	 * 最后修改价格初始化页面
	 * @author tangxing
	 */
	public String toModifyPriceEnd(){
		String id = HttpTool.getParameter("id");
		String curtabid = HttpTool.getParameter("navTabId");
		ErpPrintComSettleCustomer customer = printCompanySettleTaskService.get(id);
		
		BigDecimal setmealPrice = customer.getSetmeal_price();
		String setCustomerId = customer.getId();
		HttpTool.setAttribute("setmealPrice", setmealPrice);
		HttpTool.setAttribute("navTabId", curtabid);
		HttpTool.setAttribute("setCustomerId", setCustomerId);
		
		return "modifyPrintCompanyPriceEnd";
	}
	
	/**
	 * 最后修改价格
	 * @author tangxing
	 */
	public void modifyPriceEnd(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		boolean flag = false;
		BigDecimal hisDB = null;	//修改前
    	BigDecimal currDB = null;	//修改后
		String setCustomerId = HttpTool.getParameter("setCustomerId");
		String currentPrice = HttpTool.getParameter("currentPrice");
		String curtabid = HttpTool.getParameter("navTabId");
		ErpPrintComSettleCustomer customer = printCompanySettleTaskService.get(setCustomerId);	//根据ID获取客户信息对象
		
		try {
			currDB = new BigDecimal(currentPrice);
			customer.setSetmeal_price(currDB);
			customer.setUpdate_user_name(currentUser.getUserName());
			customer.setUpdate_time(new Date());
			printCompanySettleTaskService.updateCustomerPrice(customer);	//修改价格
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		
    	if(flag){
    		json.accumulate("statusCode", 200);
			json.accumulate("message", "修改成功");
			json.accumulate("navTabId", curtabid);
			json.accumulate("callbackType", "closeCurrent");
    	}else{
    		json.accumulate("statusCode", 300);
			json.accumulate("message", "修改失败");
    	}
    	renderJson(json);
	}
	
	/**
	 * @since 2016年12月28日16:01:55
	 * @author：chenqi
	 * @return 提交结算
	 */
	public void confirm2UnSettled(){
		JSONObject jsonObject = new JSONObject();
		try {
			String ids = HttpTool.getParameter("ids");
			int [] count = printSettlementService.confirm2UnSettled(ids);
			jsonObject.put("result", "success");
			jsonObject.put("count0", count[0]);	//可支付
			jsonObject.put("count1", count[1]);	//待支付
			jsonObject.put("count2", count[2]);	//已支付
			jsonObject.put("count3", count[3]); //没有套餐价格
		} catch (Exception e) {
			jsonObject.put("result", "error");
			logger.info("confirm2UnSettled(提交结算出错)----"+e);
		}
		renderJson(jsonObject);
	}
	
	/**
	 * @since 2016年12月28日17:44:16
	 * @author：chenqi
	 * @return 确认结算
	 */
	public void confirm2Settled(){
		JSONObject jsonObject = new JSONObject();
		try {
			String ids = HttpTool.getParameter("ids");
			int [] count = printSettlementService.confirm2Settled(ids);
			jsonObject.put("result", "success");	
			jsonObject.put("count0", count[0]);	//可支付
			jsonObject.put("count1", count[1]);	//待支付
			jsonObject.put("count2", count[2]);	//已支付
		} catch (Exception e) {
			logger.info("confirm2Settled(确认结算出错)----"+e);
			jsonObject.put("result", "error");
		}
		renderJson(jsonObject);
	}
	

	public ErpPrintCompanySettleTask getErpPrintCompanySettleTask() {
		return erpPrintCompanySettleTask;
	}

	public void setErpPrintCompanySettleTask(
			ErpPrintCompanySettleTask erpPrintCompanySettleTask) {
		this.erpPrintCompanySettleTask = erpPrintCompanySettleTask;
	}

	public File getAffix() {
		return affix;
	}

	public void setAffix(File affix) {
		this.affix = affix;
	}
	
}
