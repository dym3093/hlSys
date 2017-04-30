
package org.hpin.reportdetail.web;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.customerrelationship.entity.CustomerRelationshipLink;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.customerrelationship.service.CustomerRelationshipLinkService;
import org.hpin.base.region.dao.RegionDao;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.PropsUtils;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpConference;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.reportdetail.entity.ErpPrintBatch;
import org.hpin.reportdetail.entity.ErpPrintTask;
import org.hpin.reportdetail.entity.ErpPrintTaskBean;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.hpin.reportdetail.service.ErpPrintBatchService;
import org.hpin.reportdetail.service.ErpPrintTaskService;
import org.hpin.reportdetail.service.ErpReportdetailPDFContentService;
import org.hpin.reportdetail.util.BuidReportExcel;
import org.hpin.reportdetail.util.ZipFileUtil;
import org.hpin.settlementManagement.service.ErpPrintTaskSettlementService;


/**
* 公司名称: 远盟康健(北京)科技有限公司
* author: dengqin 
* createDate: 2016-4-14 下午5:42:20
*/


@Namespace("/reportdetail")
@Action("erpPrintTask")
@Results({
    @Result(name="toAddPrintTask",location="/WEB-INF/reportdetail/addPrintTask.jsp"),
    @Result(name="toModifyPrintTask",location="/WEB-INF/reportdetail/modifyPrintTask.jsp"),
    @Result(name="listPrintTask",location="/WEB-INF/reportdetail/listPrintTask.jsp"),
    @Result(name="listPrintTaskBySlh",location="/WEB-INF/reportdetail/listPrintTaskBySlh.jsp"),
    @Result(name="lookPrintTask",location="/WEB-INF/reportdetail/lookPrintTask.jsp"),
    @Result(name="printTaskDetail",location="/WEB-INF/reportdetail/printTaskDetail.jsp"),
    @Result(name="waitComplete",location="/WEB-INF/reportdetail/waitComplete.jsp"),
    @Result(name="confirmSigning",location="/WEB-INF/reportdetail/confirmSigning.jsp"),
    @Result(name="showAddDialog", location="/WEB-INF/reportdetail/addExtendPrintTask.jsp"),
    @Result(name="undonePrintTask", location="/WEB-INF/reportdetail/undonePrintTask.jsp"),
    @Result(name="downLoadPrintTask", location="/WEB-INF/reportdetail/downLoadPrintTask.jsp"),
    @Result(name="listPrintTaskOverTime", location="/WEB-INF/reportdetail/listPrintTaskOverTime.jsp"),
    @Result(name="showExpressInfo", location="/WEB-INF/reportdetail/showExpressInfo.jsp"),
    @Result(name="listPrintTaskExpress", location="/WEB-INF/reportdetail/listPrintTaskExpress.jsp"),
    @Result(name="showExpressInput", location="/WEB-INF/reportdetail/showExpressInput.jsp"),
})  

@SuppressWarnings({"unchecked","rawtypes"})
public class ErpPrintTaskAction extends BaseAction{
	private Logger log = Logger.getLogger(ErpPrintTaskAction.class);
    ErpPrintBatchService printBatchService = (ErpPrintBatchService) SpringTool.getBean(ErpPrintBatchService.class);
    ErpCustomerService customerService = (ErpCustomerService) SpringTool.getBean(ErpCustomerService.class);
    ErpPrintTaskService printTaskService = (ErpPrintTaskService) SpringTool.getBean(ErpPrintTaskService.class);
    DeptService deptService = (DeptService) SpringTool.getBean(DeptService.class);
    CustomerRelationShipService relService=(CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
    CustomerRelationshipLinkService shipLinkService = (CustomerRelationshipLinkService)SpringTool.getBean(CustomerRelationshipLinkService.class);
    RegionDao regionDao=(RegionDao)SpringTool.getBean(RegionDao.class);
    ErpReportdetailPDFContentService contentService = (ErpReportdetailPDFContentService)SpringTool.getBean(ErpReportdetailPDFContentService.class);
    /** 报告打印任务结算 */
    ErpPrintTaskSettlementService taskService = (ErpPrintTaskSettlementService)SpringTool.getBean(ErpPrintTaskSettlementService.class);
    
    private ErpPrintTask printTask;
    private ErpPrintBatch printBatch;
    
    private BuidReportExcel buildExcel = new BuidReportExcel();
    
    private ZipFileUtil zipUtil = new ZipFileUtil();
    
    String id;  //参数id
    
    String printBatchNo;
    String printTaskNo;
    
    public String getPrintTaskNo() {
		return printTaskNo;
	}

	public void setPrintTaskNo(String printTaskNo) {
		this.printTaskNo = printTaskNo;
	}

	public ErpPrintTask getPrintTask() {
		return printTask;
	}

	public void setPrintTask(ErpPrintTask printTask) {
		this.printTask = printTask;
	}

	/**
     * @return the printTaskService
     */
    public ErpPrintTaskService getPrintTaskService() {
        return printTaskService;
    }

    /**
     * @param printTaskService the printTaskService to set
     */
    public void setPrintTaskService(ErpPrintTaskService printTaskService) {
        this.printTaskService = printTaskService;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the printBatchNo
     */
    public String getPrintBatchNo() {
        return printBatchNo;
    }

    /**
     * @param printBatchNo the printBatchNo to set
     */
    public void setPrintBatchNo(String printBatchNo) {
        this.printBatchNo = printBatchNo;
    }
    

    public ErpPrintBatch getPrintBatch() {
		return printBatch;
	}

	public void setPrintBatch(ErpPrintBatch printBatch) {
		this.printBatch = printBatch;
	}
	
	public Page findByPage(Page page , Map searchMap){
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        String userName=currentUser.getUserName();
        searchMap.put("filter_and_isDelete_EQ_S", 0);
        
        List<ErpPrintTask> printTaskList=printTaskService.findByPage(page, searchMap);
        page.setResults(printTaskList) ;
        
       
        
        return page ;
    }
    
    /**
     * 批次信息查询
     */
//    public String listPrintTask() throws Exception{
//        Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
//        
//        //条码
//        String codeValues = null;
//		if(searchMap.containsKey("filter_and_code_LIKE_S")){
//			codeValues = (String)searchMap.get("filter_and_code_LIKE_S");
//			searchMap.remove("filter_and_code_LIKE_S");
//		}
//		if(searchMap.containsKey("filter_and_code_IN_S")){
//			codeValues = (String)searchMap.get("filter_and_code_IN_S");
//			searchMap.remove("filter_and_code_IN_S");
//		}
//        
//        String selectState = HttpTool.getParameter("selectState");
//        
//        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
//        searchMap.put("filter_and_isDelete_EQ_S", 0);//默认查询条件 
//        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
//        List<ErpPrintTask> printTaskList=printTaskService.findByPage(page, searchMap);
//        
//        //依据条码查询
//        List<ErpPrintTask> printTaskInCodeList = null;
//        //条码查询单独处理
//		if(StringUtils.isNotEmpty(codeValues)){
//			//默认模糊查询
//			//根据导入的批次查询
//			printTaskInCodeList = printTaskService.listPrintTaskByCodes(codeValues, false);
//			HttpTool.setAttribute("codes",codeValues);
//			if(!CollectionUtils.isEmpty(printTaskInCodeList)){
//				printTaskList = printTaskInCodeList;
//			}
//		}
//
//       	page.setResults(printTaskList) ;
//        HttpTool.setAttribute("selectState", selectState);
//        
//        return "listPrintTask";
//    }
	
	public String listPrintTask(){//打印任务-十里河
		try {
			Map<String,Object> map = buildSearch();
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			if(map.containsKey("filter_and_code_IN_S")){//有条码的单独查询
				for(String key:map.keySet()){
//					String [] codes= map.get(key).toString().split(",");
					List<ErpPrintTaskBean> list = printTaskService.getPrintTaskInfoByCode(map.get(key).toString(),page,"0");
					page.setResults(list);
				}
				HttpTool.setAttribute("codes", map.get("filter_and_code_IN_S"));
			}else{
				map.put("filter_and_expressBySlh_EQ_S", "0");
				List<ErpPrintTask> list = printTaskService.findByPage(page, map);
				page.setResults(list);
			}
		} catch (Exception e) {
			log.error("ErpPrintTaskAciton listPrintTask", e);
		}
		HttpTool.setAttribute("selectState", HttpTool.getParameter("selectState"));
		return "listPrintTask";
	}
	
	public String listPrintTask2(){//打印任务
		try {
			Map<String,Object> map = buildSearch();
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			if(map.containsKey("filter_and_code_IN_S")){//有条码的单独查询
				for(String key:map.keySet()){
//					String [] codes= map.get(key).toString().split(",");
					List<ErpPrintTaskBean> list = printTaskService.getPrintTaskInfoByCode(map.get(key).toString(),page,"1");
					page.setResults(list);
				}
				HttpTool.setAttribute("codes", map.get("filter_and_code_IN_S"));
			}else{
				map.put("filter_and_expressBySlh_EQ_S", "1");
				List<ErpPrintTask> list = printTaskService.findByPage(page, map);
				page.setResults(list);
			}
		} catch (Exception e) {
			log.error("ErpPrintTaskAciton listPrintTask", e);
			e.printStackTrace();
		}
		HttpTool.setAttribute("selectState", HttpTool.getParameter("selectState"));
		return "listPrintTaskBySlh";
	}
    
	public String getKey(String key) {
		return key.split("_")[2];
	}
    
    /**
     * 根据ID查找打印任务详情
     * @return
     * @throws ParseException 
     */
    public String getPrintTaskById() throws ParseException{
    	printTask = printTaskService.get(id);
        Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
        
        searchMap.put("filter_and_printTaskId_EQ_S", printTaskNo);//默认查询条件
        
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
    	
    	List<ErpPrintBatch> printBatchs = printBatchService.findByPage(page, searchMap);
    	
    	log.info("-----printBatch-----"+printTask);
    	
    	page.setResults(printBatchs);
    	
    	
    	HttpTool.setAttribute("erpPrintTask", printTask);
    	return "printTaskDetail";
    }
    
    /**
     * 创建打印任务
     * @return
     * @throws Exception 
     */
    public void toAddPrintTask() throws Exception{
    	printTask = new ErpPrintTask();
    	JSONObject json = new JSONObject();
        String printTaskNos = HttpTool.getParameter("printTaskNo");
//        String threeNum = HttpTool.getParameter("threeNum");
        String printBatchId = HttpTool.getParameter("printBatchId");	//选中的打印批次的id
        String taskName = HttpTool.getParameter("taskName");
        String batchNum = HttpTool.getParameter("batchNum");
        String sumCount = HttpTool.getParameter("sumCount");			//该批次的报告总数
        String companyId = HttpTool.getParameter("companyId");			//支公司ID
        String printBatchNos = HttpTool.getParameter("printBatchNos");	//打印任务批次号
        String expressBySlh = HttpTool.getParameter("isSlh");	//是否是十里河寄出0：不是，1：是
        String projectCode = HttpTool.getParameter("projectCode").trim();//项目编码
        String dept = HttpTool.getParameter("dept").trim();//部门
        Integer reportCount = 0;
        if(!StringUtils.isEmpty(sumCount)){
        	reportCount = Integer.valueOf(sumCount);
        }
        
        //打印公司值处理;[0]Id,[1]name
        String printCompanyIdVsprintCompanyId = HttpTool.getParameter("printCompanyArray");
        String printCompanyId = null;
        String printCompany = null;
        if(StringUtils.isNotEmpty(printCompanyIdVsprintCompanyId)) {
        	String[] strs = printCompanyIdVsprintCompanyId.split("#");
        	printCompanyId = strs[0];
        	printCompany = strs[1];
        	
        }
        //新添加打印公司和预计时间
        String expectTime = HttpTool.getParameter("expectTime");
        
        log.info(printBatchId);
	    String[] id = printBatchId.replaceAll(" ", "").split(",");
	    SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
//	    String printTaskNo = printTaskNos + "_" + threeNum;
        printTask.setPrintTaskNo(printTaskNos);	//打印任务号
        printTask.setTaskName(taskName);
        printTask.setBatchNum(batchNum);
        printTask.setReportCount(reportCount);
        printTask.setIsDelete("0");
        printTask.setPrintCompany(printCompany);
        printTask.setPrintCompanyId(printCompanyId); //打印公司ID
        printTask.setCompanyId(companyId); 			//支公司ID
        printTask.setExpectTime(formatter.parse(expectTime));
        printTask.setCreateTime(new Date());
        printTask.setPrintState("0");
        printTask.setSettlementState("0");
        printTask.setExpressBySlh(expressBySlh); 						//是否是十里河寄出0：不是，1：是
        if(!dept.equals("无")){//2016年9月24日22:19:27 chenqi 根据支公司获取对应的寄送信息
        	List<CustomerRelationshipLink> shipLinkList = shipLinkService.getBranchCompanyExpressInfo(companyId,dept);
        	if(shipLinkList.size()>=1){
        		CustomerRelationshipLink shipLink = shipLinkList.get(0);
        		printTask.setAddress(shipLink.getFunctions());
        		printTask.setTelephone(shipLink.getPhone());
        		printTask.setExpressRecipient(shipLink.getLinkMan());
        	}
        }else{
        	List<CustomerRelationShipPro> shipProList = relService.getBranchCompanyExpressInfo(companyId,projectCode); 
        	if (shipProList.size()>=1) {
        		CustomerRelationShipPro shipPro = shipProList.get(0);
        		printTask.setAddress(shipPro.getMailAddress());
        		printTask.setTelephone(shipPro.getReceptionTel());
        		printTask.setExpressRecipient(shipPro.getReception());
			}
        }
        try{
        	if(printTask != null){
//	            List<Map<String, Object>> printBatchList = printBatchService.getCustomerId(printBatchNos);
	            ErpPrintBatch printBatch = (ErpPrintBatch) printBatchService.findById(id[0]);
//	            User user = (User)HttpTool.getSession().getAttribute("currentUser");
//	            for(Map<String, Object> map:printBatchList){		//打印报告结算明细
//	            	ErpCustomer customer = (ErpCustomer) customerService.findById(map.get("CUSTOMERID").toString());
//	            	List<ErpPrintComboCost> costList = printBatchService.getComboPrice(printCompanyId,printBatch.getCombo());
//	            	if(costList.size()==0){		//是否有该公司的套餐
//	            		json.put("count", 2);
//	            		json.put("combo", printBatch.getCombo());
//	            		renderJson(json);
//	            		return;
//	            	}else {//保存要结算的打印结算具体信息（分摊的个人）
//	            		ErpPrintTaskSettlement taskSettlement = new ErpPrintTaskSettlement();
//	            		taskSettlement.setPrintTaskNo(printTaskNos);
//	            		taskSettlement.setCustomerId(customer.getId());
//	            		taskSettlement.setCode(customer.getCode());
//	            		taskSettlement.setName(customer.getName());
//	            		taskSettlement.setTelephone(customer.getPhone());
//	            		taskSettlement.setIdCard(customer.getIdno());
//	            		taskSettlement.setCombo(printBatch.getCombo());//不在从客户表中获取客户的套餐而是以分批出来的套餐为准
//	            		taskSettlement.setComboPrintPrice(costList.get(0).getPrice());
//	            		taskSettlement.setPayedPrice(costList.get(0).getPrice());
//	            		taskSettlement.setGender(customer.getSex());
//	            		taskSettlement.setAge(customer.getAge());
//	            		taskSettlement.setBranchCompany(customer.getBranchCompany());
//	            		taskSettlement.setOwedCompany(customer.getOwnedCompany());
//	            		taskSettlement.setCreateUser(user.getAccountName());
//	            		taskSettlement.setCreateTime(new Date());
//	            		taskSettlement.setSaleman(customer.getSalesMan());
//	            		
//	            		taskSettlement.setBranchCompanyId(customer.getBranchCompanyId()); // add by YoumingDeng 2016-09-19
//	            		taskSettlement.setOwedCompanyId(customer.getOwnedCompanyId()); // add by YoumingDeng 2016-09-19
//	            		
//	            		taskService.save(taskSettlement);
//					}
//	            }
	            
	            try {
	            	printTaskService.save(printTask);	//save打印任务
	            	log.info("我已经保存到了打印任务");
	            	contentService.updateTaskContentPrintTaskNos(printBatchNos,printTaskNos);//更新打印任务号
	            	log.info("我更新了print_task_content表的打印任务号");
				} catch (Exception e) {
					log.error("toAddPrintTask", e);
				}
	            
	            for(int i=0; i<id.length; ++i){
	            	printBatch = printBatchService.get(id[i]); 
	            	printBatch.setPrintTaskId(printTaskNos);	//将当前的打印任务号设置
	            	printBatch.setIsPrintTask("1");
	            	printBatchService.modify(printBatch);
	            }
	            
	            json.put("count", 1);
        	}else{
        		json.put("count", 0);
        	}
        }catch(Exception e){
        	log.error("toAddPrintTask",e);
        	json.put("count", 0);
        }
        renderJson(json);
    }
    
    /**
     * 创建一个文件夹
     * @param destDirName
     * @return
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }
    
    //判断添加的表单提交过来的对象
    public ErpPrintTask getModel() {    
        if (null == printTask) {    
            return printTask = new ErpPrintTask();    
        }    
        return printTask;    
    } 
    
    /**
     * 确认打印任务打印按钮
     * @throws IOException 
     */
    public void confirmPrintTask() throws IOException{
		log.info("确认打印任务打印 \t id :\t"+id);
		try {
			printTask = printTaskService.get(id);
			printTask.setPrintState("1");
			printTaskService.save(printTask);
		} catch (Exception e){
			log.error("confirmPrintTask error ", e);
		}
    }
    
    /**
     * 批次信息增加
     * @return
     */
    public String addPrintTask(){
        JSONObject json = new JSONObject();
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");//获取当前用户
        //提交过来的批次对象
        String nowPrintTaskNo = getModel().getPrintTaskNo();
        
        try {
//            Date hour = printBatch.getCreateTime();//创建时间
//            Date conferenceDate =conference.getConferenceDate();//会议日期
//            String conferenceDateStr=DateUtil.getDateTime("MM/dd/yyyy", conferenceDate);//年月日
//            String time= conferenceDateStr+" "+hour+":00:00";
//            Date finalTime = DateUtil.convertStringToDate("MM/dd/yyyy HH:mm:ss", time);
            String message = printTaskService.isNotRepeatNo(nowPrintTaskNo); //判断场次是否已添加
            if(StrUtils.isNullOrBlank(message)){//判断是否添加场次成功
                printTask.setPrintTaskNo(nowPrintTaskNo);
                printTask.setIsDeleted(0);
                printTask.setCreateTime(new Date());
                
                printTaskService.save(printTask); //
                json.accumulate("statusCode", 200);
                json.accumulate("message", "增加成功");
                json.accumulate("navTabId", super.navTabId);
                json.accumulate("callbackType", "closeCurrent");
            }else{
                json.accumulate("statusCode", 300);
                json.accumulate("message", message);
            }
        } catch (Exception e) {
        	log.error("addPrintTask", e);
            json.accumulate("statusCode", 300);
            json.accumulate("message", "增加失败");
        }
        
        renderJson(json);
        return null;
    }
    
    /**
     * 删除
     * @return
     */
    public String delPrintTask(){
        JSONObject json = new JSONObject();
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        String[] id = ids.replaceAll(" ", "").split(",");
        List<ErpConference> list = new LinkedList<ErpConference>();
        try {
            for (int i = 0; i < id.length; i++) {
                printTask = (ErpPrintTask) printTaskService.findById(id[i]);
                printTask.setIsDelete("1");
                printTask.setUpdateTime(new Date());
                printTaskService.modify(printTask);
            }
//              conferenceService.delete(list);
                json.accumulate("statusCode", 200);
                json.accumulate("message", "删除成功");
            } catch (Exception e) {
            	log.error("delPrintTask",e);
                json.accumulate("statusCode", 300);
                json.accumulate("message", "删除失败");
            }
        renderJson(json);
        return null;
    }
    
    /**
     * 修改的默认数据
     */
    public String toModifyPrintTask(){
        printTask=printTaskService.get(id);//获取当前选中的批次
//      String eventsNo=events.getEventsNo();
//      int nowHeadcount=eventsService.getNowHeadcountByEventsNo(eventsNo);
        
//        String conferenceDateStr=DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", conference.getConferenceDate());
        
        HttpTool.setAttribute("conferenceDateStr", printTask.getCreateTime());
        return "toModifyPrintBatch";
    }
    
    /**
     * 修改
     * @description 
     * @return
     *
     * @return String
     * @throws
     *
     */
    public String modifyPrintTask(){
        JSONObject json = new JSONObject();
        String isUpdateCustDate=HttpTool.getParameter("isUpdateCustDate");
        String conferenceType=HttpTool.getParameter("conferenceType");
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        try {
//                String conferenceNo=printBatchService.getConferenceNo();
                //int nowHeadcount=eventsService.getNowHeadcountByEventsNo(eventsNo);
//              HttpTool.setAttribute("nowHeadcount", nowHeadcount);
//              events.setNowHeadcount(nowHeadcount);
                
                  printTask.setUpdateTime(new Date());
//                printBatch.setConferenceType(conferenceType);
                printTaskService.modify(printTask);//判断场次是否已添加
                //根据场次号更新客户信息
//                if(isUpdateCustDate.equals("1")){
//                    customerService.updateSampleDatebyConferenceNo(conference);
//                }
                json.accumulate("statusCode", 200);
                json.accumulate("message", "修改成功");
                json.accumulate("navTabId", super.navTabId);
                json.accumulate("callbackType", "closeCurrent");
            } catch (Exception e) {
            	log.error("modifyPrintTask",e);
                json.accumulate("statusCode", 300);
                json.accumulate("message", "修改失败");
            }
        renderJson(json);
        return null;
    }
    
    /**
     * 打开手动添加打印任务的窗口，并显示未添加在打印任务的打印批次
     * @author dym
     * @return
     */
    public String showAddDialog(){
    	System.out.println("*********showAddDialog*********");
    	List<ErpPrintBatch> printBatchList = new ArrayList<ErpPrintBatch>();
 	
	 	 int threeNum = ranNum();
	 	 String printBatchDate = creTime();
	     printBatchList.addAll((List<ErpPrintBatch>)printBatchService.listErpPrintBatchsNotInPrintTask());
	   	 int listSize = printBatchList.size();
	   	 HttpTool.setAttribute("threeNum", threeNum);		//随机三位数
	   	 HttpTool.setAttribute("batchNum", listSize);		//批次数量
	   	 HttpTool.setAttribute("createTime", printBatchDate);	//创建时间
	   	 //TODO 任务名称生成法则
	//   	 HttpTool.setAttribute("taskName", taskName);		//任务名称
	   	 HttpTool.setAttribute("printBatchId", ids);		//选中的id
	     page = new Page();
	     page.setResults(printBatchList);   
		 return "showAddDialog";
    }
    
    /**
     * 根据ID查找打印任务及其对应的批次
     * @author dym
     * @return String
     * @throws Exception
     */
    public String listPrintTaskById() throws Exception{
    	System.out.println("-----method: listPrintTaskById-----");
    	String pageLocation = HttpTool.getParameter("pageLocation");
    	String id = HttpTool.getParameter("id");
    	ErpPrintTask printTask = printTaskService.get(id);
        Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
//        String printTaskNo = HttpTool.getParameter("printTaskNo");
        String printTaskNoOther = null;
        if(printTask!=null){
        	printTaskNoOther = printTask.getPrintTaskNo();
        }
        searchMap.put("filter_and_printTaskId_EQ_S", printTaskNoOther);//默认查询条件
        
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
    	
    	List<ErpPrintBatch> printBatchs = printBatchService.findByPage(page, searchMap);
    	
    	HttpTool.setAttribute("navTabId", super.navTabId);
    	HttpTool.setAttribute("erpPrintTask", printTask);
    	//返回确认打印任务的页面
    	return pageLocation;
    }
    
    /**
     * 
     * @return String
     * @throws Exception
     */
    public String modifyPrintTaskById() throws Exception{
    	//TODO 
    	printTask = printTaskService.get(id);
    	//返回打开的页面
    	return "listPrintTaskById";
    }
    
    
    /**
     * 把批次添加到对应的打印任务
     * @return String
     * @throws Exception
     */
    public String addBatchToPrintTask() throws Exception{
    	JSONObject json = new JSONObject();
    	json.put("result","error");
    	String printTaskId = HttpTool.getParameter("printTaskId");
    	String printTaskNo = HttpTool.getParameter("printTaskNo");
    	
    	//获取批次ID
    	String printBatchIds = HttpTool.getParameter("printBatchIds");
    	String[] strArr = null;
    	if(printBatchIds.indexOf(",")!=-1){
    		strArr = printBatchIds.split(",");
    		for (int i = 0; i < strArr.length; i++) {
    			printBatch = printBatchService.get(strArr[i]);
    			//设置批次所属的打印任务
    			printBatch.setPrintTaskId(printTaskNo);
    			//设置打印任务状态
    			printBatch.setIsPrintTask("1");
    			printBatchService.save(printBatch);
    		}
    	}else{
    		strArr = new String[]{printBatchIds};
   			printBatch = printBatchService.get(printBatchIds);
			//设置批次所属的打印任务
			printBatch.setPrintTaskId(printTaskNo);
			//设置打印任务状态
			printBatch.setIsPrintTask("1");
			printBatchService.save(printBatch);
    	}
    	//修改打印任务中的批次数量
        printTask = printTaskService.get(printTaskId);
        
        String  orgBatchNum = printTask.getBatchNum();
        int n = 0;
        String batchNum ="";
        if(strArr.length>0){
        	n +=strArr.length;
        }else{
        	//无法切割成数组说明只有一条数据
        	n +=1;
        }
        if(orgBatchNum.length()>0){
        	n += Integer.valueOf(orgBatchNum);
        }
        batchNum = ""+n;
    	//修改批次数量
        printTask.setBatchNum(batchNum);
    	try{
    		printTaskService.save(printTask);
    	}catch(Exception e){
    		log.error("addBatchToPrintTask",e);
    	}
    	json.put("result","success");
		renderJson(json);
    	return null;
    }
    
	/**
	 * 手动添加打印任务
	 * @return
	 * @throws Exception
	 */
	public String addExtendPrintTask() throws Exception{
		//TODO
		log.info("*********addExtendPrintTask*********");
		return "listPrintTask";
	}
	
	/**
	 * 确认打印，并刷新页面
	 * @return
	 * @throws Exception
	 */
	public String confirmPrintTaskReloadWeb() throws Exception{
		JSONObject json = new JSONObject();
	   	//获取任务ID, add
	   	String printTaskId = HttpTool.getParameter("id");
	   	String printTaskNo = HttpTool.getParameter("printTaskNo");
//	   	boolean flag = false;
	   	//查询该打印任务下的所有打印批次
	   	List<ErpPrintBatch> list = printBatchService.listErpPrintBatchByTaskId(printTaskNo);
	   	//批次ID处理
	   	StringBuilder buff = new StringBuilder();	 
	   	String[] printBatchIdArr = new String[list.size()];
	   	//批次号处理
	   	String[] printBatchNoArr = new String[list.size()];
	   	
	   	for (int n=0; n<list.size(); n++) {
	   		printBatchIdArr[n]=list.get(n).getId();
	   		printBatchNoArr[n]=list.get(n).getPrintBatchNo();
			buff.append(list.get(n).getId());
			if(n!=list.size()-1){
				buff.append(",");
				
			}
		}
		ErpPrintTask printTask = printTaskService.get(printTaskId);
		printTask.setPrintState("3");
	   try{
        	System.out.println("printTask------"+printTask);
        	HttpServletRequest request = ServletActionContext.getRequest();
        	for(String nostr : printBatchNoArr){
            	List<ErpPrintTaskContent> taskContentList = contentService.getTaskContentByBatchNo(nostr);
            	if(null!=taskContentList){
            		contentService.updateTaskContentPrintInfo(taskContentList,printTaskNo);
            	}
            }
//        	String zipName = printTaskNo.substring(0,printTaskNo.length()-4)+String.valueOf(pdfNum);
            String filePath = printTaskService.createDownLoadFileByPrintBth(request,list,printTaskNo);
            printTask.setDownLoadPath(filePath);
            log.info("printTask------"+printTask);
            printTaskService.save(printTask);	//save打印任务
           //TODO 批量确认打印，修改会员状态
           //根据打印任务ID修改其下会员的报告状态(statusYm = 已打印) add by YoumingDeng 2016-12-15 start
//            printTaskService.updateCustomerStatusYm(printTaskId, PropsUtils.getInt("status","statusYm.ydy"));
           //根据打印任务ID修改其下会员的报告状态(statusYm = 已打印) add by YoumingDeng 2016-12-15 end
//            flag = true;
//            if(flag){
//            	//遍历id集合
//      	   		for(int i=0; i<printBatchIdArr.length; i++){
//       	   			printBatch = printBatchService.get(printBatchIdArr[i]); 
//    	   	   		printBatch.setIsPrintTask("1");
//    	   	   		printBatchService.modify(printBatch);
//    	   	   		System.out.println("更新的printBatch-----------"+printBatch);
//       	   	 	}
//      	   		
//            }
            json.put("result","success");
        } catch (Exception e) {
        	log.error("confirmPrintTaskReloadWeb",e);
            json.put("result", "error");
        }
		renderJson(json);
		return null;
	}
	
	/**
	 * @since 2016年9月18日12:08:55
	 * @author Carly
	 * 批量确认打印
	 */
	public void confirmBatchPrintTask(){
		JSONObject json = new JSONObject();
	   	//获取任务ID, add
	   	String printTaskIds = HttpTool.getParameter("ids");
	   	String printTaskNos = HttpTool.getParameter("printTaskNos");
//	   	boolean flag = false;
	   	//查询该打印任务下的所有打印批次
	   	List<ErpPrintBatch> printBatchlist = printBatchService.getPrintBatchList(printTaskNos);
	   	//批次ID处理
	   	StringBuilder buff = new StringBuilder();	 
	   	String[] printBatchIdArr = new String[printBatchlist.size()];
	   	//批次号处理
	   	String[] printBatchNoArr = new String[printBatchlist.size()];
	   	//打印任务号
	   	String[] printTaskNoArr = printTaskNos.split(",");
	   	for (int n=0; n<printBatchlist.size(); n++) {
	   		printBatchIdArr[n]=printBatchlist.get(n).getId();
	   		printBatchNoArr[n]=printBatchlist.get(n).getPrintBatchNo();
			buff.append(printBatchlist.get(n).getId());
		}
	   	List<ErpPrintTask> printTaskList = printTaskService.getPrintTaskById(printTaskIds);
	   try{
        	HttpServletRequest request = ServletActionContext.getRequest();
        	for(int i=0;i<printBatchNoArr.length;i++){
            	List<ErpPrintTaskContent> taskContentList = contentService.getTaskContentByBatchNo(printBatchNoArr[i]);
            	if(taskContentList.size()!=0){
            		contentService.updatePrintTaskContent(taskContentList);
            	}
            }
            for(int i=0;i<printTaskList.size();i++){
            	ErpPrintTask printTask = printTaskList.get(i);
            	printTask.setPrintState("3");
            	List<ErpPrintBatch> list = printBatchService.getPrintBatchList(printTaskNoArr[i]);	//根据打印任务号分开设置下载路径
            	String filePath = printTaskService.createDownLoadFileByPrintBth(request,list,printTaskNoArr[i]);
            	printTask.setDownLoadPath(filePath);
            	printTaskService.save(printTask);	//save打印任务
                //TODO 批量确认打印，修改会员状态
                //根据打印任务ID修改其下会员的报告状态(statusYm = 已打印) add by YoumingDeng 2016-12-15 start
//                printTaskService.updateCustomerStatusYm(printTask.getId(), PropsUtils.getInt("status","statusYm.ydy"));
                //根据打印任务ID修改其下会员的报告状态(statusYm = 已打印) add by YoumingDeng 2016-12-15 end
            }
            
            json.put("result","success");
        } catch (Exception e) {
        	log.error("confirmPrintTaskReloadWeb",e);
        }
		renderJson(json);
	}
	
	/**
	 * 保存打印信息
	 * @return
	 * @throws Exception
	 */
	public String savePrintTaskInfo() throws Exception{
		JSONObject json = new JSONObject();
		String printTaskId = HttpTool.getParameter("id");
    	String printCompany = HttpTool.getParameter("printCompany");
//    	printCompany = new String(printCompany.getBytes("ISO-8859-1"),"UTF-8");
    	String expectTime = HttpTool.getParameter("expectTime");
    	
    	ErpPrintTask printTask = printTaskService.get(printTaskId);
    	printTask.setPrintState("3");
    	if(printCompany.length()>0){
    		printTask.setPrintCompany(printCompany);
    	}
    	if(expectTime.length()>0){
    		printTask.setExpectTime(new SimpleDateFormat("yyyy-mm-dd").parse(expectTime));
    	}
		try{
			printTask.setUpdateTime(new Date());
			printTaskService.save(printTask);
            //批量确认打印 printState=3，修改会员状态
            //根据打印任务ID修改其下会员的报告状态(statusYm = 已打印) add by YoumingDeng 2016-12-15 start
//            printTaskService.updateCustomerStatusYm(printTaskId, PropsUtils.getInt("status","statusYm.ydy"));
            //根据打印任务ID修改其下会员的报告状态(statusYm = 已打印) add by YoumingDeng 2016-12-15 end
			json.put("result","success");
			json.accumulate("statusCode", 200);
			json.accumulate("message", "保存成功！");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "closeCurrent");
		}catch(Exception e){
			log.error("savePrintTaskInfo",e);
			json.accumulate("statusCode", 300);
			json.accumulate("message", "保存失败！");
			json.accumulate("navTabId", super.navTabId);
		}
		renderJson(json);
		return null;
	}
	
    public int ranNum(){
    	Random randomNumber = new Random();
    	int i=randomNumber.nextInt(900)+100;	//三位数随机数
    	return i;
    }
    
    public String creTime(){
		Date date = new Date();
		String sData="";
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sData = sf.format(date);
		return sData;
    }
    
    /**
     * 等待快递公司反馈状态的处理
     */
    public String waitComplete(){
    	
    	return "waitComplete";
    }
    
    /**
     * 确认签收状态的处理
     */
    public String confirmSigning(){
    	User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String now = dateFormat.format(new Date());
    	String person = currentUser.getUserName();
    	
    	HttpTool.setAttribute("person", person);
    	HttpTool.setAttribute("now", now);
    	HttpTool.setAttribute("id", id);
    	
    	return "confirmSigning";
    }
    
    /**
     * 确认签收页面
     * @throws ParseException 
     */
    public void confirm() throws ParseException{
    	JSONObject json = new JSONObject();
    	String currentId = HttpTool.getParameter("id");
    	String signPerson = HttpTool.getParameter("signPerson");	//签收人
    	String signTime = HttpTool.getParameter("signTime");		//签收时间
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = dateFormat.parse(signTime);
    	
    	
    	try {
    		printTask = printTaskService.get(currentId);
    		printTask.setExpressRecipient(signPerson);
    		printTask.setExpectTime(date);
    		printTask.setPrintState("5");
    		
    		printTaskService.modify(printTask);
    		
    		json.accumulate("statusCode", 200);
    		json.accumulate("message", "签收成功");
    		json.accumulate("bialog", super.navTabId);
		} catch (Exception e) {
			log.error("confirm",e);
			json.accumulate("statusCode", 300);
    		json.accumulate("message", "签收失败");
		}
    	
    	renderJson(json);
    }
    
    /**
     * 等待反馈页面
     * @throws ParseException 
     */
    public void complete() throws ParseException{
    	JSONObject json = new JSONObject();
    	String currentId = HttpTool.getParameter("id");
    	String expressNo = HttpTool.getParameter("expressNo");	//签收人
    	String expressConpany = HttpTool.getParameter("expressConpany");		//签收时间
    	
    	try {
    		printTask = printTaskService.get(currentId);
    		printTask.setExpressNo(expressNo);
    		printTask.setExpressConpany(expressConpany);
    		printTask.setPrintState("4");
    		
    		printTaskService.modify(printTask);
    		
    		json.accumulate("statusCode", 200);
    		json.accumulate("message", "录入成功");
    		json.accumulate("bialog", super.navTabId);
		} catch (Exception e) {
			log.error("complete",e);
			json.accumulate("statusCode", 300);
    		json.accumulate("message", "录入失败");
		}
    	renderJson(json);
    }
    
    /**
     * 下载打印任务zip
     */
    public void downLoad(){
    	JSONObject json = new JSONObject();
	    printTask = printTaskService.get(id);
	    
	    String path = printTask.getDownLoadPath();
	    json.put("path", path);
	   
	    renderJson(json);
    }
    
    /**
     * 删除打印任务，把其相关的打印批次更改状态
     * @return String
     * @throws Exception
     * @author dym
     */
    public String deletePrintTask() throws Exception{
    	 JSONObject json = new JSONObject();
         String[] id = ids.replaceAll(" ", "").split(",");
         try {
             for (int i = 0; i < id.length; i++) {
                 printTask = (ErpPrintTask) printTaskService.findById(id[i]);
                 printTask.setIsDelete("1");
                 printTask.setUpdateTime(new Date());
                 //printBatch中实际存储的为printTask的任务号（printTaskNo）
                 List<ErpPrintBatch> batchList = printBatchService.listErpPrintBatchByTaskId(printTask.getPrintTaskNo());
                 if(batchList!=null&&batchList.size()>0){
	                 //
	                 List<ErpPrintBatch> erpPrintBatchs = new ArrayList<ErpPrintBatch>();
	                 ErpPrintBatch printBatch = null;
	                 for(int n=0; n<batchList.size(); n++){
	                	 printBatch = batchList.get(n);
	                	 //打印状态置0，打印任务ID置为空
	                	 printBatch.setIsPrintTask("0");
	                	 printBatch.setPrintTaskId(null);
	                	 //记录修改时间
	                	 printBatch.setUpdateTime(new Date());
	                	 erpPrintBatchs.add(printBatch);
	                 }
	                 printBatchService.save(erpPrintBatchs);
                 }
                 printTaskService.modify(printTask);
                 
                 //删除打印结算任务 @since 2016年10月28日11:16:26 @add by chenqi
                 log.info("打印任务号："+printTask.getPrintTaskNo());
                 taskService.deleteByPrintTaskNo(id[i]);
             }
             } catch (Exception e) {
            	 log.error("deletePrintTask",e);
             }
         json.put("result","success");
         renderJson(json);
    	
    	return null;
    }
    
    /**
     * 列出超时的打印任务
     * @return String
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-27 上午11:30:07
     */
    public String listPrintTaskOverTime() throws Exception{
    	log.info("function listPrintTaskOverTime begin");
    	Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
    	//超时的条件
    	searchMap.put("filter_and_expectTime_LEET_T", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String selectState = HttpTool.getParameter("selectState");
           
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        String userName=currentUser.getUserName();
        searchMap.put("filter_and_isDelete_EQ_S", 0);//默认查询条件
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
        page=findByPage(page, searchMap);
           
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
        printTaskService.findByPage(page, searchMap);
        
        HttpTool.setAttribute("selectState", selectState); 
    	
        return "listPrintTaskOverTime";
    }
    
    /**
     * 处理打印任务批量下载文件;
     * create by henry.xu 20160812
     * @throws Exception
     */
    public void batchDownload() {
    	JSONObject json = new JSONObject(); //返回json对象;
    	try {
    		
    		String ids = HttpTool.getParameter("ids"); //获取前台传入参数ids;
    		String path = ""; //将所有文件重新生成zip后返回的新路劲;
    		// 文件处理;
    		HttpServletRequest request = ServletActionContext.getRequest();
    		path = printTaskService.dealBatchDownload(ids, request);
    		
    		json.put("path", path);
    	} catch (Exception e) {
    		log.error("批量文件导出出错!", e);
    	}
	   
	    renderJson(json);
    }
    
    /**
     * 处理单个文件下载,便于处理修改下载状态;
     * create by henry.xu 20161024
     */
    public void oneDownloadModifiedStatus() {
    	JSONObject json = new JSONObject();
    	try {
    		//状态修改;
    		if(StringUtils.isNotEmpty(id)) {
    			this.printTaskService.modifiedDownloadStatusById(id) ;
    			json.put("result", true);
    		} else {
    			json.put("result", false);
    		}
    		
    	} catch(Exception e) {
    		json.put("result", false);
    		log.error("单个文件数据状态修改失败!", e);
    	}
    	
    	renderJson(json);
    }
    
    /**
     * @return 展示要修改的寄送信息
     */
    public String showExpressInfo(){
    	String id = HttpTool.getParameter("id");
    	ErpPrintTask printTask = (ErpPrintTask) printTaskService.findById(id);
    	HttpTool.setAttribute("id",id);
    	HttpTool.setAttribute("recipient", printTask.getExpressRecipient());
    	HttpTool.setAttribute("telephone", printTask.getTelephone());
    	HttpTool.setAttribute("address", printTask.getAddress());
    	return "showExpressInfo";
    }	
    
    /**
     * 	修改寄送信息
     */
    public void editExpressInfo(){
    	String id = HttpTool.getParameter("id");
    	String recipient = HttpTool.getParameter("recipient");
    	String telephone = HttpTool.getParameter("telephone");
    	String address = HttpTool.getParameter("address");
    	JSONObject json = new JSONObject();
    	User user = (User) HttpTool.getSession().getAttribute("currentUser");
    	try {
    		ErpPrintTask printTask = (ErpPrintTask) printTaskService.findById(id);
    		printTask.setAddress(address);
    		printTask.setTelephone(telephone);
    		printTask.setExpressRecipient(recipient);
    		printTask.setUpdateTime(new Date());
    		printTask.setUpdateUsername(user.getUserName());
    		printTaskService.update(printTask);
    		json.put("count", 1);
		} catch (Exception e) {
			json.put("count", 0);
			log.error("ErpPrintTaskAction editExpressInfo(修改寄送信息)", e);
		}
    	renderJson(json);
    }
    
    /**
     * @return	快递补录
     */
    public String listPrintTaskExpress(){
    	Map<String,Object> map = buildSearch();
    	try {
    		String printTaskNo = HttpTool.getParameter("printTaskNo");
    		String printState = HttpTool.getParameter("printState");
    		String printCompanyId = HttpTool.getParameter("printCompanyId");
    		String startTime = HttpTool.getParameter("startTime");
    		String endTime = HttpTool.getParameter("endTime");
    		if(StringUtils.isNotEmpty(printTaskNo)){
    			map.put("filter_and_printTaskNo_LIKE_S", printTaskNo);
    			HttpTool.setAttribute("filter_and_printTaskNo_LIKE_S", printTaskNo);
    		}
    		if(StringUtils.isNotEmpty(printState)){
    			map.put("filter_and_printState_EQ_S", printState);
    			HttpTool.setAttribute("filter_and_printState_EQ_S", printState);
    		}
    		if(StringUtils.isNotEmpty(printCompanyId)){
    			map.put("filter_and_printCompanyId_EQ_S", printCompanyId);
    			HttpTool.setAttribute("filter_and_printCompanyId_EQ_S", printCompanyId);
    		}
    		if(StringUtils.isNotEmpty(startTime)){
    			map.put("filter_and_createTime_GEST_T", startTime);
    			HttpTool.setAttribute("filter_and_createTime_GEST_T", startTime);
    		}
    		if(StringUtils.isNotEmpty(endTime)){
    			map.put("filter_and_createTime_LEET_T", endTime);
    			HttpTool.setAttribute("filter_and_createTime_LEET_T", endTime);
    		}
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			List<ErpPrintTask> list = printTaskService.findByPage(page, map);
			page.setResults(list);
		} catch (Exception e) {
			log.error("ErpPrintTaskAciton listPrintTask", e);
			e.printStackTrace();
		}
		HttpTool.setAttribute("filter_and_printState_EQ_S", map.get("filter_and_printState_EQ_S"));
    	return "listPrintTaskExpress";
    }
    
    /**
     * @return 快递补录
     */
    public String showExpressInput(){
    	try {
			
    		String id = HttpTool.getParameter("id");
    		ErpPrintTask printTask = (ErpPrintTask) printTaskService.findById(id);
    		String jsonData = HttpTool.getParameter("jsonData");
    		String printTaskNo = null;
    		String printState = null;
    		String printCompanyId = null;
    		String startTime = null;
    		String endTime = null;
    		if(StringUtils.isNotEmpty(jsonData)){
    			JSONArray array = JSONArray.fromObject(jsonData);
    			for(int i=0;i<array.size();i++){
    				JSONObject object = array.getJSONObject(i);
    				if(object.containsKey("filter_and_printTaskNo_LIKE_S")){
    					printTaskNo = java.net.URLDecoder.decode(object.getString("filter_and_printTaskNo_LIKE_S"),"UTF-8");
    				}else if(object.containsKey("filter_and_printState_EQ_S")){
    					printState = object.getString("filter_and_printState_EQ_S");
    				}else if(object.containsKey("filter_and_printCompanyId_EQ_S")){
    					printCompanyId = object.getString("filter_and_printCompanyId_EQ_S");
    				}else if(object.containsKey("filter_and_createTime_GEST_T")){
    					startTime = object.getString("filter_and_createTime_GEST_T");
    				}else if(object.containsKey("filter_and_createTime_LEET_T")){
    					endTime = object.getString("filter_and_createTime_LEET_T");
    				}
    			}
    		}
    		HttpTool.setAttribute("id",id);
    		HttpTool.setAttribute("expressCompany",printTask.getExpressConpany());
    		HttpTool.setAttribute("expressNo", printTask.getExpressNo());
    		HttpTool.setAttribute("expressPrice", printTask.getExpressPrice());
    		HttpTool.setAttribute("count", printTask.getCount());
    		HttpTool.setAttribute("averagePrice", printTask.getAveragePrice());
    		if(StringUtils.isNotEmpty(printTaskNo)){
    			HttpTool.setAttribute("printTaskNo",printTaskNo);
    		}
    		if(StringUtils.isNotEmpty(printState)){
    			HttpTool.setAttribute("printState",printState);
    		}
    		if(StringUtils.isNotEmpty(printCompanyId)){
    			HttpTool.setAttribute("printCompanyId",printCompanyId);
    		}
    		if(StringUtils.isNotEmpty(startTime)){
    			HttpTool.setAttribute("startTime",startTime);
    		}
    		if(StringUtils.isNotEmpty(endTime)){
    			HttpTool.setAttribute("endTime",endTime);
    		}
		} catch (Exception e) {
			log.error("showExpressInput:---",e);
		}
    	return "showExpressInput";
    }	
    
    /**
     * 快递补录
     */
    public void insertExpressInfo(){
    	String id = HttpTool.getParameter("id");
    	String expressCompany = HttpTool.getParameter("expressCompany");
    	String expressNo = HttpTool.getParameter("expressNo");
    	String expressPrice = HttpTool.getParameter("expressPrice");
    	String count = HttpTool.getParameter("count");
    	String averagePrice = HttpTool.getParameter("averagePrice");
    	JSONObject json = new JSONObject();
    	User user = (User) HttpTool.getSession().getAttribute("currentUser");
    	try {
    		ErpPrintTask printTask = (ErpPrintTask) printTaskService.findById(id);
    		printTask.setExpressConpany(expressCompany);
    		printTask.setExpressNo(expressNo);
    		printTask.setExpressPrice(new BigDecimal(expressPrice));
    		printTask.setCount(count);
    		printTask.setAveragePrice(new BigDecimal(averagePrice));
    		printTask.setPrintState("5");
    		printTask.setUpdateTime(new Date());
    		printTask.setReportEndTime(new Date());
    		printTask.setUpdateUsername(user.getUserName());
    		printTaskService.update(printTask);
    		taskService.updateAveragePrice(new BigDecimal(averagePrice),printTask.getPrintTaskNo());
    		json.put("count", 1);
		} catch (Exception e) {
			json.put("count", 0);
			log.error("ErpPrintTaskAction editExpressInfo(快递信息补录)", e);
		}
    	renderJson(json);
    }
    
    /**
     * 	快递补录页面的操作按钮
     */
    public void updateState(){
    	String id = HttpTool.getParameter("id");
    	User user = (User) HttpTool.getSession().getAttribute("currentUser");
    	JSONObject json = new JSONObject();
    	try {
    		ErpPrintTask printTask = (ErpPrintTask) printTaskService.findById(id);
    		printTask.setPrintState("5");
    		printTask.setUpdateTime(new Date());
    		printTask.setReportEndTime(new Date());
    		printTask.setUpdateUsername(user.getUserName());
    		printTaskService.update(printTask);
    		json.put("count", 1);
		} catch (Exception e) {
			json.put("count", 0);
			log.error("ErpPrintTaskAction updateState(快递信息补录页面的报告完成按钮)", e);
		}
    	renderJson(json);
    }
    
}
