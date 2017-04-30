package org.hpin.events.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.barcode.entity.BarCodeBat;
import org.hpin.barcode.service.BarCodeBatService;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.customerrelationship.entity.ProjectType;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.FileUtil;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.PropsUtils;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpConference;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpCustomerException;
import org.hpin.events.entity.ErpCustomerImpFailBtach;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.entity.ErpQRCode;
import org.hpin.events.entity.ErpScheduleJob;
import org.hpin.events.service.ErpConferenceService;
import org.hpin.events.service.ErpCustomerExceptionService;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.events.service.ErpQRCodeService;
import org.hpin.foreign.service.ErpReportScheduleJYService;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.hpin.reportdetail.service.ErpReportdetailPDFContentService;
import org.hpin.reportdetail.web.ServerProcessAction;
import org.hpin.reportdetail.web.ShieldProcessActon;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Namespace("/events")
@Action("erpCustomer")
@Results({
	@Result(name="listErpCustomer",location="/WEB-INF/events/erpCustomer/listErpCustomer.jsp"),
	@Result(name="toListCustomer",location="/WEB-INF/events/erpCustomer/listCustomer.jsp"),
	@Result(name="toListCustomerSalesman",location="/WEB-INF/events/erpCustomer/listCustomerSalesman.jsp"),
	@Result(name="toListCustomerNoReport",location="/WEB-INF/events/erpCustomer/listCustomerNoReport.jsp"),
	@Result(name="toListCustomerNoReportSalesman",location="/WEB-INF/events/erpCustomer/listCustomerNoReportSalesman.jsp"),
	@Result(name="toEventNoListCustomer",location="/WEB-INF/events/erpCustomer/eventNoToListCustomer.jsp"),
	@Result(name="toEventWuChuangNoListCustomer",location="/WEB-INF/events/erpCustomer/eventNoWuChuangToListCustomer.jsp"),
	@Result(name="toEventNoListCustomerSalesman",location="/WEB-INF/events/erpCustomer/eventNoToListCustomerSalesman.jsp"),
	@Result(name="toConferenceNoListCustomer",location="/WEB-INF/events/erpCustomer/conferenceNoToListCustomer.jsp"),
	@Result(name="toListAllCustomer",location="/WEB-INF/events/erpCustomer/listAllCustomer.jsp"),
	@Result(name="toListAllCustomerSalesman",location="/WEB-INF/events/erpCustomer/listAllCustomerSalesman.jsp"),
	@Result(name="toAddCustomer",location="/WEB-INF/events/erpCustomer/addCustomer.jsp"),
	@Result(name="toModifyEvents",location="/WEB-INF/events/erpCustomer/modifyCustomer.jsp"),
	@Result(name="toModifyException",location="/WEB-INF/events/erpCustomer/modifyException.jsp"),
	@Result(name="importCustomer",location="/WEB-INF/events/erpCustomer/importCustomer.jsp"),
	@Result(name="importCustomerOriginal",location="/WEB-INF/events/erpCustomer/importCustomerOriginal.jsp"),
	@Result(name="listCustomerFail",location="/WEB-INF/events/erpCustomer/listCustomerFail.jsp"),
	@Result(name="toDownloadPDFByEventsNo",location="/WEB-INF/events/erpCustomer/downloadPDFByEventsNo.jsp"),
	@Result(name="toSaveScheduleInfo",location="/WEB-INF/events/erpCustomer/saveScheduleInfo.jsp"),
	@Result(name="showDeletedStatusDialogPage",location="/WEB-INF/events/erpCustomer/showDeletedStatusDialogPage.jsp"),
})
@SuppressWarnings({"unchecked", "rawtypes"})
public class ErpCustomerAction extends BaseAction {
	private Logger log = Logger.getLogger(ErpCustomerAction.class);
	ErpCustomerService service = (ErpCustomerService) SpringTool.getBean(ErpCustomerService.class);
	ErpEventsService eventsService = (ErpEventsService) SpringTool.getBean(ErpEventsService.class);
	ErpConferenceService conferenceService = (ErpConferenceService) SpringTool.getBean(ErpConferenceService.class);
	BarCodeBatService barCodeBatService = (BarCodeBatService) SpringTool.getBean(BarCodeBatService.class);
	CustomerRelationShipService relService=(CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
	ErpReportScheduleJYService scheduleJYService = (ErpReportScheduleJYService)SpringTool.getBean(ErpReportScheduleJYService.class);
	private UserService userService = (UserService) SpringTool.getBean(UserService.class);
	private ErpCustomerExceptionService customerExceptionService = (ErpCustomerExceptionService) SpringTool.getBean(ErpCustomerExceptionService.class);
	private ErpReportdetailPDFContentService reportdetailPDFContentService = (ErpReportdetailPDFContentService) SpringTool.getBean(ErpReportdetailPDFContentService.class);
	
	@Autowired
	private ErpQRCodeService erpQRCodeService;  //二维码管理service;
	
	private BarCodeBat barCodeBat;
	@Value(value="${erpCustomerInfo.file.download.local.path}")
	private String customerInfoPath;
	
	public BarCodeBat getBarCodeBat() {
		return barCodeBat;
	}
	public void setBarCodeBat(BarCodeBat barCodeBat) {
		this.barCodeBat = barCodeBat;
	}
	private ErpCustomer customer;
	private ErpCustomerException customerException;
	private ErpEvents events;
	private ErpConference conference;
	private File affix;
    private String affixContentType;
    private String affixFileName;
    
    private String showId;
    
    /**
     * * add by henry.xu 20170414
     * 点击推送按钮，调用知康的接口，将当前此条客户信息推送给知康
     * <p>Description: </p>
     * @author herny.xu
     * @date 2017年4月14日
     */
    public void pushSingleNumEvent() {
    	String customerId = HttpTool.getParameter("customerId", "");
    	JSONObject json = new JSONObject();
    	boolean result = false ;
    	String message = "执行成功";
    	
    	try {
			result = this.service.pushSingleNumToWuChuang(customerId);
			
		} catch (Exception e) {
			result = false;
			message = e.getMessage();
			log.error(message, e);
		}
    	json.put("result", result);
    	json.put("message", message);
    	renderJson(json);
    }
    
    /**
     * 场地管理->客户信息确认;
     * create by henry.xu 20161128
     */
    public void customerInfoSure() {
    	JSONObject json = new JSONObject();
    	boolean result = false ;
    	String message = "执行成功";
    	try {
			result = this.service.customerInfoSure(id);
			
		} catch (Exception e) {
			result = false;
			message = e.getMessage();
			this.service.updateErpQRCodePush(id);
			log.error(message, e);
		}
    	json.put("result", result);
    	json.put("message", message);
    	renderJson(json);
    }
   
    /**
	 * 到添加客户页面
	 * @return
	 */
	public String toAddCustomer(){
		String eventsNo=HttpTool.getParameter("eventsNo");
		events=eventsService.get(eventsNo);
		events.setNowHeadcount(eventsService.getCustomerNums(events.getEventsNo()));
		log.info("toAddCustomer eventsNo -- "+eventsNo);
		log.info("toAddCustomer nowHeadcount -- "+eventsService.getCustomerNums(events.getEventsNo()));
		return "toAddCustomer";
	}
	
    /**
     * 到添加客户页面
     * @return
     */
    public String toAddCustomerTwo(){
        String conferenceNo=HttpTool.getParameter("conferenceNo");
        log.info("toAddCustomerTwo conferenceNo -- "+conferenceNo);
        conference=conferenceService.get(conferenceNo);//根据会议号查找具体的会议
        
        //根据当前的会议号来查询所有的参会人员
        conference.setNowHeadcount(conferenceService.getCustomerNums(conference.getConferenceNo()));        
        return "toAddCustomer";
    }
	/**
	 * 添加客户页面
	 * @return
	 */
	public String addCustomer(){ 
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String navTabId = HttpTool.getParameter("navTabId");
		String isBtn=HttpTool.getParameter("isBtn");
		try {
			
			//2017-3-7 add by leslieTong. 客户信息变更增加【营销员工号】字段
			//JSP输入框的数据
			String customerMemberId = customer.getId();
			String customerMemberName = customer.getName();
			String customerMemberCode = customer.getCode();
			if(StrUtils.isNotNullOrBlank(customerMemberId)){//已存在更新
				String jyId = HttpTool.getParameter("jyId");
				if(StringUtils.isNotEmpty(jyId)){
					scheduleJYService.updateUserInfo(customerMemberId,jyId);
				}else{	//不是金域的才做此修改
					ErpCustomer customerTemp = (ErpCustomer) service.findById(customerMemberId);
					
					if(customerTemp!=null){
						String customerMemberEventsNo = customer.getEventsNo();
						String customerTempName = customerTemp.getName();
						String customerTempCode = customerTemp.getCode();
						//判断当前客户的条形码或者姓名是否被修改
						if(!customerMemberName.equals(customerTempName)||!customerMemberCode.equals(customerTempCode)){
							List<ErpReportdetailPDFContent> reportdetailPDFContents = reportdetailPDFContentService.gePdfContentByEventsNoAndCodeAndName(customerTempCode, customerTempName, customerMemberEventsNo);
							if(reportdetailPDFContents!=null&&reportdetailPDFContents.size()>0){
								ErpReportdetailPDFContent content = reportdetailPDFContents.get(0);
								content.setCode(customerMemberCode);
								content.setUsername(customerMemberName);
								content.setUpdatedate(new Date());
								content.setUpdateUser(currentUser.getUserName());
								reportdetailPDFContentService.update(content);
							}
						}
					}
				}
				
				service.updateInfo(customer);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "操作成功");
				json.accumulate("navTabId", navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}else{
				String codeMess = service.isNotRepeatcode(customer.getCode());	//判断条形码是否已使用
				log.info("addCustomer whether repeat message --  "+codeMess);
				if(StrUtils.isNullOrBlank(codeMess)){//不重复增加
					/*customer.setCode(customer.getCode().replace("-", ""));*/
					
					// 根据批次号起始位JY判定检测机构：默认为南方，批次号起始位JY则重新设定为金域    add By YoumingDeng 2016-09-26  start
					customer.setTestInstitution("南方"); // add by DengYouming 2016-08-23 解决手动添加会员数据没有检测结构导致查询不到的bug
					
					String eventsNo = customer.getEventsNo();
					if(StringUtils.isNotEmpty(eventsNo)){
						List<ErpEvents> eventsList = eventsService.findByProperty(ErpEvents.class, ErpEvents.F_EVENTSNO, eventsNo, null, null);
						if(eventsList!=null&&eventsList.size()>0){
							String batchNo = eventsList.get(0).getBatchNo();
							if(batchNo.startsWith("JY")){
								customer.setTestInstitution("金域"); // add by DengYouming 2016-09-18  解决手动添加会员数据区分检测机构
							}
						}
					}
					// 根据批次号起始位JY判定检测机构：默认为南方，批次号起始位JY则重新设定为金域    add By YoumingDeng 2016-09-26  end
					customer.setCode(customer.getCode());
					customer.setIsDeleted(0);
					customer.setCreateTime(new Date());
					customer.setCreateUserName(currentUser.getUserName());
				
					customer.setStatus("0");  // add by DengYouming 2016-08-23 解决新增会员无法添加到保险公司结算任务的bug
					//可以改为注释掉下面一行改为用触发器(Trigger_saveCustomer)执行
					customer.setStatusYm(PropsUtils.getInt("status","statusYm.yhq"));// add by Dayton 2016-12-21 添加客户状态:已获取
//					ErpEvents oldEvents=eventsService.get(id);
//					oldEvents.setNowHeadcount(oldEvents.getNowHeadcount()+1);
//					
//					eventsService.modify(oldEvents);
//					barCodeBatService.updateStatus(1, customer.getCode());
					service.save(customer);
					
					json.accumulate("statusCode", 200);
					json.accumulate("message", "增加成功");
					json.accumulate("navTabId", super.navTabId);//20160105
					//json.accumulate("navTabId", "_blank");
					if(isBtn.equals("2")){
						json.accumulate("callbackType", "closeCurrent");
					}else{
	//					json.accumulate("callbackType", "closeCurrent");
	//					json.accumulate("statusCode", 200);
	//					json.accumulate("message", "操作成功");
						json.accumulate("callbackType", "refreshCurrent");
					}

				}else{
					String mes="";
					List<ErpCustomer> listCustomer = service.getCustomerByCode(customer.getCode());
					boolean b=true;
					for (ErpCustomer tempCustomer : listCustomer) {
						log.info("addCustomer getCustomerByCode --  "+tempCustomer);
						if(tempCustomer.getIdno()==null){
							tempCustomer.setIdno("");
						}
						if (!tempCustomer.getIdno().equals("")&&tempCustomer.getIdno().equals(customer.getIdno())) {//身份证号不空并且相等： 重复
							b = false;
							mes="身份证号非空并且相同";
						}
						if (tempCustomer.getIdno().equals("")&&tempCustomer.getIdno().equals(customer.getIdno())){//身份证号都为空
							if(tempCustomer.getName().equals(customer.getName())){//姓名相同
								b=false;
								mes="身份证号都为空,姓名相同";
							}
						}
						//身份证号有一个为空
						if(tempCustomer.getIdno().equals("")&&!customer.getIdno().equals("")||!tempCustomer.getIdno().equals("")&&customer.getIdno().equals("")){//一个为空，一个不为空
							if(tempCustomer.getName().equals(customer.getName())){//姓名相同
								b=false;
								mes="身份证号有一个为空，姓名相同";
							}
						}
					}
					if(b==true){
						customer.setCode(customer.getCode());
						customer.setIsDeleted(0);
						customer.setCreateTime(new Date());
						customer.setCreateUserName(currentUser.getUserName());
//						barCodeBatService.updateStatus(1, customer.getCode());
						//可以注释掉下面一行改为用触发器(Trigger_saveCustomer)执行
						customer.setStatusYm(PropsUtils.getInt("status","statusYm.yhq"));// add by Dayton 2016-12-21 添加客户状态:已获取
						service.save(customer);
						json.accumulate("statusCode", 200);
						json.accumulate("message", codeMess+",身份证号或姓名不同");
					}else{
						json.accumulate("statusCode", 300);
						json.accumulate("message", codeMess+","+mes);
					}
				}
			}
			
		} catch (Exception e) {
			json.accumulate("statusCode", 300); 
			json.accumulate("message", "操作失败");
			log.error("addCustomer save customer fail--"+e);
		}
		renderJson(json);
		return null;
	}
	/**
	 * 客户信息修改界面
	 * @return
	 */
	public String toModifyEvents(){
		customer = (ErpCustomer) service.findById(id);
		log.info("toModifyEvents -- "+customer);
		HttpTool.setAttribute("navTabId", super.navTabId);
		HttpTool.setAttribute("jyPage", HttpTool.getParameter("jyPage"));//change 2016年12月12日14:42:10，author：chenqi，用于标识是否是金域报告
		HttpTool.setAttribute("jyId", HttpTool.getParameter("jyId"));//同上
		return "toModifyEvents";
	}
	/**
	 * 异常客户信息修改界面
	 * @return
	 */
	public String toModifyException(){
		customerException = service.findExceptionById(id);
		log.info("toModifyException -- "+customerException);
		HttpTool.setAttribute("navTabId", super.navTabId);
		return "toModifyException";
	}
	/**
	 * 修改异常客户信息
	 * @return
	 */
	public void updateExceptionInfo(){
		JSONObject json = new JSONObject();
		String navTabId = HttpTool.getParameter("navTabId");
		try {
			String msg = service.updateExceptionInfo(customerException);
			String eventsNo = customerException.getEventsNo();
			//2016-12-22新增需求  基因公司套餐费用维护情况检测
			//第一步根据eventsNo判断该场次下是否还有异常数据 如果没有则为true
			boolean haveException = customerExceptionService.isHaveException(eventsNo);
			if(haveException){
				customerExceptionService.testingPrice(eventsNo);
			}
			json.accumulate("statusCode", 200);
			json.accumulate("message", msg);
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "closeCurrent");
		} catch (Exception e) {
			json.accumulate("statusCode", 300); 
			json.accumulate("message", "操作失败");
			log.error("addCustomer save customer fail--"+e);
		}
		renderJson(json);
	}
	/**
	 * 查询客户信息
	 * @param page
	 * @param paramsMap
	 * @return
	 */
	public Page findByPage(Page page , Map paramsMap){
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		service.findByPage(page, paramsMap);
		return page ;
	}
	
	/**
	 * 销售页面
	 * @return
	 * @throws Exception
	 */
	public String toEventNoListCustomerSalesman() throws Exception{
		String sid=(String)HttpTool.getSession().getAttribute("id");
		if(sid!=null){
			id=sid;
		}
		events=eventsService.get(id);
		
		String eventsNo=events.getEventsNo();
		int nowHeadcount=eventsService.getNowHeadcountByEventsNo(eventsNo);
		int pdfcount=eventsService.getCustomerPDFNums(eventsNo);
		
		HttpTool.setAttribute("nowHeadcount", nowHeadcount);
		HttpTool.setAttribute("pdfcount", pdfcount);
		
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_eventsNo_EQ_S", events.getEventsNo());
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findByPage(page, searchMap);
		return "toEventNoListCustomerSalesman";
	}
	
		   /**
     * 页面
     * @return
     * @throws Exception
     */
    public String toConferenceNoListCustomer() throws Exception{
        String showId=(String)HttpTool.getParameter("showId");
        if(showId!=null){
            id=showId;
        }
        conference=conferenceService.get(id);
        
        //查询项目编码信息;
        if(StringUtils.isNotEmpty(conference.getCustomerRelationShipProId())) {
        	HttpTool.setAttribute("shipPro", relService.findCustRelShipProById(conference.getCustomerRelationShipProId()));
        }
        
        String conferenceNo=conference.getConferenceNo();
        Map searchMap = super.buildSearch();
        searchMap.put("filter_and_conferenceNo_EQ_S", conferenceNo);
        searchMap.put("filter_and_isDeleted_EQ_I", 0);
        /*page = new Page(HttpTool.getPageNum());
        List<ErpCustomer> customerList=service.findByPage(page, searchMap);
        page.setResults(customerList);*/
        page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
        conferenceService.findByPage(page, searchMap);
        return "toConferenceNoListCustomer";
    }
    
    public void findRepeatInfo() throws Exception{
    	//获取重新条形码的数据信息
    	String eventsNo = HttpTool.getParameter("eventsNo");
		String message = service.findRepeatInfo(eventsNo);
		JSONObject json = new JSONObject();
		try {
			json.put("status","200");
			json.put("message",message);
			log.info("findRepeatInfo--success");
		} catch (Exception e) {
			json.put("status","300");
			json.put("error", "获取条形码重复数据失败！");
			log.error("findRepeatInfo:", e);
		}
		renderJson(json);
    }
	
	/**
	 * 页面
	 * @return
	 * @throws Exception
	 */
	public String toEventWuChuangNoListCustomer() throws Exception{
		String sid=(String)HttpTool.getSession().getAttribute("id");
		if(sid!=null){
			id=sid;
		}
		events=eventsService.get(id);
		
		/*
		 * 根据erpqrcode的id查询并获取pushStatus状态.(-1推送失败, 0未推送, 1推送成功)
		 * 用于限制客户信息推送功能按钮显示;
		 */
		String qrcodeId = HttpTool.getParameter("qrcodeId", "");
		ErpQRCode qrcode = this.erpQRCodeService.getErpQRCodeById(qrcodeId);
		
		HttpTool.setAttribute("qrcodeId", qrcodeId);
		HttpTool.setAttribute("qrcode", qrcode);
		if(null != qrcode) {
			HttpTool.setAttribute("pushStatus", qrcode.getPushStatus());
		}
		
		/*
		 * modified by henry.xu 20160920;
		 * 查询项目信息;
		 */
		CustomerRelationShipPro shipPro = null;
		ProjectType projectType = null;
		if(events !=null && StringUtils.isNotEmpty(events.getCustomerRelationShipProId())) {
			shipPro = relService.findCustRelShipProById(events.getCustomerRelationShipProId());
			/*
			 * modified by henry.xu 20161130
			 * 添加查询项目类型,用于(客户信息确认)按钮控制;
			 */
			if(null != shipPro) {
				String ptypeId = shipPro.getProjectType();
				projectType = this.relService.findProjectTypeByIdOrType(ptypeId, null);				
			}
		}
		HttpTool.setAttribute("shipPro", shipPro);
		HttpTool.setAttribute("projectType", projectType);

		
		String eventsNo=events.getEventsNo();
		int nowHeadcount=eventsService.getNowHeadcountByEventsNo(eventsNo);
		int pdfcount=eventsService.getCustomerPDFNums(eventsNo);
		int exceptCount = service.getExceptCount(eventsNo);
		HttpTool.setAttribute("nowHeadcount", nowHeadcount);
		HttpTool.setAttribute("pdfcount", pdfcount);
		HttpTool.setAttribute("exceptCount", exceptCount);
		HttpTool.setAttribute("customerException", false);
		
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_eventsNo_EQ_S", events.getEventsNo());
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findByPage(page, searchMap);
		
		return "toEventWuChuangNoListCustomer";
	}
	
	/**
	 * 页面
	 * @return
	 * @throws Exception
	 */
	public String toEventNoListCustomer() throws Exception{
		String sid = HttpTool.getParameter("id");
		if(sid!=null){
			id=sid;
		}
		events=eventsService.get(id);
		/*
		 * modified by henry.xu 20160920;
		 * 查询项目信息;
		 */
		CustomerRelationShipPro shipPro = null;
		ProjectType projectType = null;
		if(events !=null && StringUtils.isNotEmpty(events.getCustomerRelationShipProId())) {
			shipPro = relService.findCustRelShipProById(events.getCustomerRelationShipProId());
			/*
			 * modified by henry.xu 20161130
			 * 添加查询项目类型,用于(客户信息确认)按钮控制;
			 */
			if(null != shipPro) {
				String ptypeId = shipPro.getProjectType();
				projectType = this.relService.findProjectTypeByIdOrType(ptypeId, null);				
			}
		}
		HttpTool.setAttribute("shipPro", shipPro);	
		HttpTool.setAttribute("projectType", projectType);

		
		String eventsNo=events.getEventsNo();
		int nowHeadcount=eventsService.getNowHeadcountByEventsNo(eventsNo);
		int pdfcount=eventsService.getCustomerPDFNums(eventsNo);
		int exceptCount = service.getExceptCount(eventsNo);
		HttpTool.setAttribute("nowHeadcount", nowHeadcount);
		HttpTool.setAttribute("pdfcount", pdfcount);
		HttpTool.setAttribute("exceptCount", exceptCount);
		HttpTool.setAttribute("customerException", false);
		
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_eventsNo_EQ_S", events.getEventsNo());
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findByPage(page, searchMap);
		return "toEventNoListCustomer";
	}
	
	/**
	 * 页面
	 * @return
	 * @throws Exception
	 */
	public String toListCustomerSalesman() throws Exception{
		events=eventsService.get(id);
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_eventsNo_EQ_S", events.getEventsNo());
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		/*	page = new Page(HttpTool.getPageNum());
		List<ErpCustomer> customerList=service.findByPage(page, searchMap);
		page.setResults(customerList);*/
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findByPage(page, searchMap);
		return "toListCustomerSalesman";
	}
	/**
	 * 页面
	 * @return
	 * @throws Exception
	 */
	public String toListCustomer() throws Exception{
		events=eventsService.get(id);
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_eventsNo_EQ_S", events.getEventsNo());
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
	/*	page = new Page(HttpTool.getPageNum());
		List<ErpCustomer> customerList=service.findByPage(page, searchMap);
		page.setResults(customerList);*/
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findByPage(page, searchMap);
		return "toListCustomer";
	}
	/**
	 * 查询没报告客户信息
	 * @param page
	 * @param paramsMap
	 * @return
	 */
	public Page findByPageNoReport(Page page , Map paramsMap){
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		service.findByPageNoReport(page, paramsMap);
		return page ;
	}
	/**
	 * 页面
	 * @return
	 * @throws Exception
	 */
	public String toListCustomerNoReportSalesman() throws Exception{
		events=eventsService.get(id);
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_eventsNo_EQ_S", events.getEventsNo());
//		searchMap.put("filter_and_pdffilepath_EQ_S", null);
		/*	page = new Page(HttpTool.getPageNum());
		List<ErpCustomer> customerList=service.findByPage(page, searchMap);
		page.setResults(customerList);*/
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findByPageNoReport(page, searchMap);
		return "toListCustomerNoReportSalesman";
	}
	/**
	 * 页面
	 * @return
	 * @throws Exception
	 */
	public String toListCustomerNoReport() throws Exception{
		ErpEvents eventsTemp = new ErpEvents();
		String id = HttpTool.getParameter("id");
		eventsTemp=eventsService.get(id);
		Map searchMap = super.buildSearch();
		if(eventsTemp!=null){
			searchMap.put("filter_and_eventsNo_EQ_S", eventsTemp.getEventsNo());
		}
		
//		searchMap.put("filter_and_pdffilepath_EQ_S", null);
	/*	page = new Page(HttpTool.getPageNum());
		List<ErpCustomer> customerList=service.findByPage(page, searchMap);
		page.setResults(customerList);*/
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findByPageNoReport(page, searchMap);
		HttpTool.setAttribute("id",id);
		return "toListCustomerNoReport";
	}
	public Page findByPageAll(Page page , Map paramsMap){
		service.findByPage(page, paramsMap);
		//create by henry.xu 20160921
		page.setTempProcess(ShieldProcessActon.EVENTSLISTALLCUSTOMER);
		return page ;
	}
	/**
	 * 营销员对应客户信息列表
	 * @return
	 * @throws Exception
	 */
	public String toListAllCustomerSalesman() throws Exception{
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		String userName=currentUser.getUserName();
		/*
		 * 根据业务需求此处销售统计报表,只有admin有全部查看权限,其他销售智能看自己的,或者销售主管看相关人员数据
		 * modified by henry.xu 20161115
		 * */
		if(!userName.equals("管理员")){//销售人员
			/*
			 * 除了admin以外的其他用户登录时，判断如果当前登录用户的【数据权限】字段值为部门数据，
			 * 则根据当前用户所属的部门，查找这个部门下所有成员关联的场次信息。
			 * 如果当前登录用户的【数据权限】字段值为个人数据，则查询当前用户关联的场次信息
			 */
			String deptPri = currentUser.getDataPriv();
			//当该值为空时,不执行;按照原有方式执行;
			StringBuilder inStr = new StringBuilder("");
			if(StringUtils.isNotEmpty(deptPri)) {
				//查询该人员是否是部门管理;如果不是则查询该人员数据; 否则查询该部门管理下面包含的人员;
				if("priv_01".equals(deptPri)){
					//查询
					List<User> lists = userService.getUserListByDeptId(currentUser.getDeptId());
					//拼接字符串;
					if(lists != null && lists.size()> 0) {
						for(User user : lists) {
							if(StringUtils.isEmpty(inStr.toString())) {
								inStr.append("'").append(user.getUserName()).append("'");
							} else {
								inStr.append(", '").append(user.getUserName()).append("'");
							}
						}
					}
					searchMap.put("filter_and_ymSalesman_IN_S", inStr.toString());				
				} else {
					
					searchMap.put("filter_and_ymSalesman_EQ_S", currentUser.getUserName());				
				}
				
			} else {
				searchMap.put("filter_and_ymSalesman_EQ_S", currentUser.getUserName());				
			}
			
		}
			//searchMap.put("filter_and_ymSalesman_LIKE_S", userName);
		this.findByPageAll(page, searchMap);
		return "toListAllCustomerSalesman";
	}
	/**
	 * 创建场次账户对应客户信息列表
	 * @return
	 * @throws Exception
	 */
	public String toListAllCustomer() {
		String deleteStatus = "0,2";	//0正常, 2 异常报告
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			Map searchMap = super.buildSearch();
			//reportState 0有报告; 1没有报告; 添加报告状态查询条件; modified by henry.xu 20160817;
			String reportState = HttpTool.getParameter("reportState");
			searchMap.put("filter_and_isDeleted_IN_S", deleteStatus);
			if (searchMap.containsKey("filter_and_isDeleted_EQ_I")) {
				searchMap.remove("filter_and_isDeleted_IN_S");
				
			} 
			searchMap.put("reportState", reportState);
			this.findByPageAll(page, searchMap);
			HttpTool.setAttribute("reportState", reportState);
			
		} catch (Exception e) {
			log.error("查询客户信息失败(toListAllCustomer)--", e);
		}
		return "toListAllCustomer";
	}
	/**
	 * 删除多个
	 * @return
	 */
	public String delCustomer(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String[] id = ids.replaceAll(" ", "").split(",");
		List<ErpCustomer> list = new LinkedList<ErpCustomer>();
		List<ErpEvents> list2 = new LinkedList<ErpEvents>();
		try {
			for (int i = 0; i < id.length; i++) {
				customer = (ErpCustomer) service.findById(id[i]);
				log.info("delCustomer many customer--"+customer);
				customer.setIsDeleted(1);
				customer.setUpdateTime(new Date());
				customer.setUpdateUserName(currentUser.getUserName());
				list.add(customer);
				list2.add(events);
//				barCodeBatService.updateStatus(0, customer.getCode());
			}
			service.delete(list);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "删除成功");
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "删除失败");
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	/**
	 * 删除一个
	 */
	public String delOneCustomer(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
//		String[] id = ids.replaceAll(" ", "").split(",");
//		String[] id = ids.replaceAll(" ", "").split(",");
		List<ErpCustomer> list = new LinkedList<ErpCustomer>();
		List<ErpEvents> list2 = new LinkedList<ErpEvents>();
		try {
//			for (int i = 0; i < id.length; i++) {
			
				customer = (ErpCustomer) service.findById(id);
				customer.setIsDeleted(1);
				customer.setUpdateTime(new Date());
				customer.setUpdateUserName(currentUser.getUserName());
				log.info("delOneCustomer one customer--"+customer);
				list.add(customer);
				events=eventsService.queryEventNO(customer.getEventsNo());
				int num = events.getNowHeadcount();
				events.setNowHeadcount(num-1);
				list2.add(events);
//				barCodeBatService.updateStatus(0, customer.getCode());
//			}
			service.delete(list);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "删除成功");
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "删除失败");
			log.error("delOneCustomer Exception--"+e);
		}
		renderJson(json);
		return null;
//		return "toEventNoListCustomer";
	}
	/**
	 * 查询
	 * @return
	 */
	public String listErpCustomer(){
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			findByPage(page, paramsMap);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "listErpCustomer";
	}
	
	/**
	 * 导入页面
	 */
	public String importCustomer(){
		//events =  (ErpEvents) eventsService.findById(id);
		return "importCustomer";
	}
	
	
	/**
	 * 导入页面(根据具体的场次)
	 */
	public String importCustomerOriginal(){
		events =  (ErpEvents) eventsService.findById(id);
		log.info("importCustomerOriginal--"+events);
		return "importCustomerOriginal";
	}
	/**
	 * 导入客户信息失败批次List
	 * @return
	 * @author tangxing
	 * @date 2016-6-21上午10:48:49
	 */
	public String listCustomerFail(){
		Map searchMap = super.buildSearch();
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("exceptionSettleTask",e);
			e.printStackTrace();
		}
		
		//拿出所有的异常批次
		List<ErpCustomerImpFailBtach> customerImpFailBtachs = service.listFailBtach(page,searchMap);
		
		page.setResults(customerImpFailBtachs);
		
		return "listCustomerFail";
	}
	
	/**
	 * 客户信息EXCEL导入
	 * @return
	 */
	public String saveCustomerAll() {
		JSONObject json = new JSONObject();
		try {
			//保存
			int count = 0;
			//合约截止时间
			System.out.println(affixFileName);
			String type = affixFileName.substring(affixFileName.lastIndexOf(".")+1, affixFileName.length());
			if ("xlsx".equals(type)) {
//				count = service.saveCustomere(affix, affixFileName,customer.getEventsNo());
				//String message = service.saveCustomere(affix, affixFileName,customer.getEventsNo());
				String message = service.saveAllCustomere(affix, affixFileName);
	    		json.accumulate("statusCode", 200);
//	    		json.accumulate("message", message);
	    		json.accumulate("navTabId", super.navTabId);
	    		json.accumulate("callbackType", "1,"+count);
	    		System.out.println(message);
	    		if(message.indexOf("全部重复")!=-1){
	    			HttpServletResponse response = ServletActionContext.getResponse();
		    		response.setContentType("text/html;charset=UTF-8");
		    		response.setCharacterEncoding("UTF-8");//防止弹出的信息出现乱码
		    		PrintWriter out = response.getWriter();
		    		out.print("<script>alert('"+ message+"')</script>");
		    		out.flush();
		    		out.close();
	    		}else if(message.indexOf("[")>0&&message.indexOf("]")>0){//重复条形码弹出框不关闭
		    		HttpServletResponse response = ServletActionContext.getResponse();
		    		response.setContentType("text/html;charset=UTF-8");
		    		response.setCharacterEncoding("UTF-8");//防止弹出的信息出现乱码
		    		PrintWriter out = response.getWriter();
		    		out.print("<script>alert('"+ message+"');</script>");
		    		out.flush();
		    		out.close();
	    		}else{//全部导入正常提示成功
	    			json.accumulate("message", message.substring(0,message.indexOf("ok"))+",全部导入无重复信息");
	    		}
	    		
			}else if (!"xlsx".equals(type)) {
				json.accumulate("statusCode", 300);
	    		json.accumulate("message", "导入异常,请检查EXCEL");
	    		json.accumulate("navTabId", "menu_31");
	    		json.accumulate("callbackType", "1,0");
			}
		}
		catch (Exception ex) {
			log.error("saveCustomerAll"+ex);
    		json.accumulate("statusCode", 300);
    		json.accumulate("message", "导入异常,请检查EXCEL");
    		json.accumulate("navTabId", "menu_31");
    		json.accumulate("callbackType", "1,0");
		}finally{
			renderJson(json);
		}
		return null;
	}
	
public String saveCustomer(){
	JSONObject json = new JSONObject();
	try {
		//保存
		int count = 0;
		//合约截止时间
		log.info("saveCustomer affixFileName"+affixFileName);
		String type = affixFileName.substring(affixFileName.lastIndexOf(".")+1, affixFileName.length());
		//2017-01-17 备份导入的excel文件
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
			String timePath = sdf.format(new Date());
			String curFilePath = customerInfoPath+timePath+"_"+(int)(Math.random()*10000)+File.separator;//存放位置
			FileUtil.createNewFile(affix, affixFileName,curFilePath);
			log.info("saveCustomere--保存文件的地址："+curFilePath+"/"+affixFileName);
		}catch(Exception e){
			log.error("saveCustomere--备份源文件失败：",e);
		}
		if ("xlsx".equals(type)) {
			String eventsNo = customer.getEventsNo();
			String message = service.saveCustomere(affix, affixFileName,eventsNo);
			//2016-12-22新增需求 基因公司套餐费用维护情况检测  当导入时没有异常数据则去判断
			String countNum = message.substring(message.lastIndexOf("：")+1, message.lastIndexOf("条"));
			if("0".equals(countNum)){
				customerExceptionService.testingPrice(eventsNo);
			}
    		json.accumulate("statusCode", 200);
    		json.accumulate("navTabId", super.navTabId);
    		json.accumulate("callbackType", "1,"+count);
    		log.info("saveCustomer message"+message);
    		json.accumulate("message", message);
		}else if (!"xlsx".equals(type)) {
			json.accumulate("statusCode", 300);
    		json.accumulate("message", "导入异常,请检查EXCEL");
    		json.accumulate("navTabId", "menu_31");
    		json.accumulate("callbackType", "1,0");
		}
	}
	catch (Exception ex) {
		log.info("saveCustomer Exception"+ex);
		json.accumulate("statusCode", 300);
		json.accumulate("message", "导入异常,请检查EXCEL");
		json.accumulate("navTabId", "menu_31");
		json.accumulate("callbackType", "1,0");
	}finally{
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute(ServerProcessAction.INEXPORT, "1");
		renderJson(json);
	}
	return null;
}

	/**
	 * 导出Excel请求
	 * 
	 * @author tangxing
	 * @date 2016-6-21下午4:55:59
	 */
	public void createExSeFilePath(){
		JSONObject json = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		String failBtachId = HttpTool.getParameter("id");
		ErpCustomerImpFailBtach customerImpFailBtach = service.getfailBtachByid(failBtachId);
		List<ErpCustomer> customerImpFails =service.getCustomerByFailBtachId(failBtachId);
		
		String excelPath= service.createExSeFilePath(request, customerImpFails,customerImpFailBtach.getFailBtachNo());
		log.info("createExSeFilePath excelPath:"+excelPath);
		json.put("path",excelPath);
		
		renderJson(json);
	}
	
	public String toDownloadPDFByEventsNo(){
		return "toDownloadPDFByEventsNo";
	}
	
	/**
	 * 
	 * @return String
	 * @author DengYouming
	 * @since 2016-8-25 下午3:11:46
	 */
	public String downloadPDFByEventsNo(){
		String msg = null;
		JSONObject json = new JSONObject();
		json.put("result", "fail");
		json.put("msg", "下载失败!请稍后重试...");
		String eventsNo = HttpTool.getParameter(ErpCustomer.F_EVENTSNO);
		try {
			if(eventsNo!=null&&eventsNo.length()>0){
				msg = service.downloadPDFByEventsNo(eventsNo);
				if("1".equals(msg)){
					json.put("result", "success");
					json.put("msg", "场次【"+eventsNo+"】会员资料下载成功!");
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	
	public String toSaveScheduleInfo(){
		return "toSaveScheduleInfo";
	}
	
	public void saveScheduleInfo(){
		JSONObject json = new JSONObject();
		json.put("result", "fail");
		String infoType = HttpTool.getParameter("infoType");
		String scheduleTime = HttpTool.getParameter("scheduleTime");
		String scheduleInfo = HttpTool.getParameter("scheduleInfo");
		String keyWord = HttpTool.getParameter("keyWord");
		Map<String,String> result = null;
		if(StringUtils.isNotEmpty(infoType)&&StringUtils.isNotEmpty(scheduleTime)&&StringUtils.isNotEmpty(keyWord)&&StringUtils.isNotEmpty(scheduleInfo)){
			User user = getUserInfo();
			try {
				result = service.dealScheduleJob(infoType, scheduleTime, keyWord, scheduleInfo, user);
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e);
			}
			json.put("result", "success");
			json.put("msg", result.get(ErpScheduleJob.F_REMARK));
		}else{
			json.put("msg", "空数据！");
		}
		
		renderJson(json);
	}
	
	/**
	 * 根据场次号获取分页异常数据
	 * @return
	 * @author machuan
	 * @date  2016年11月17日
	 */
	public String listCustomerException(){
		try {
			String sid=(String)HttpTool.getSession().getAttribute("ids");
			if(sid!=null){
				ids=sid;
			}
			events=eventsService.get(ids);
			/*
			 * modified by henry.xu 20160920;
			 * 查询项目信息;
			 */
			CustomerRelationShipPro shipPro = null;
			if(events !=null && StringUtils.isNotEmpty(events.getCustomerRelationShipProId())) {
				shipPro = relService.findCustRelShipProById(events.getCustomerRelationShipProId());
			}
			HttpTool.setAttribute("shipPro", shipPro);	
			String eventsNo=events.getEventsNo();
			int nowHeadcount=eventsService.getNowHeadcountByEventsNo(eventsNo);
			int pdfcount=eventsService.getCustomerPDFNums(eventsNo);
			int exceptCount = service.getExceptCount(eventsNo);
			HttpTool.setAttribute("nowHeadcount", nowHeadcount);
			HttpTool.setAttribute("pdfcount", pdfcount);
			HttpTool.setAttribute("exceptCount", exceptCount);
			HttpTool.setAttribute("customerException", false);
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			service.getListCustomerException(page, eventsNo);
			HttpTool.setAttribute("customerException", true);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "toEventNoListCustomer";
	}
	
	/**
	 * 删除一条异常数据
	 * @author machuan
	 * @date  2016年11月23日
	 */
	public void deleteCustomerExption(){
		JSONObject json = new JSONObject();
		String exceptionId = HttpTool.getParameter("exceptionId");
		try {
			service.deleteCustomerExption(exceptionId);
			json.put("status","200");
			json.put("message","删除成功！");
			log.info("deleteCustomerExption--success：exceptionId = "+exceptionId);
		} catch (Exception e) {
			json.put("status","300");
			json.put("error", "删除失败！请稍后重试。。");
			log.error("deleteCustomerExption:", e);
		}
		renderJson(json);
	}
	/**
	 * 删除多个异常数据
	 * @return
	 */
	public String delCusException(){
		JSONObject json = new JSONObject();
		String[] id = ids.replaceAll(" ", "").split(",");
		try {
			for (int i = 0; i < id.length; i++) {
				service.deleteCustomerExption(id[i]);
			}
			json.accumulate("statusCode", 200);
			json.accumulate("message", "删除成功");
			log.info("delCusException--success：exceptionId = "+ids);
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "删除失败");
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}

	/**
	 * 根据场次号批量导出
	 * @return Page
	 * @author Damian
	 * @since 2017-02-22 15:48
	 */
	public void exportByEvents(){
	    String events = HttpTool.getParameter(ErpCustomer.F_EVENTSNO);
	    log.info("events: "+events);
	    File file;
		InputStream in;
		try {
		    if (StringUtils.isNotEmpty(events)) {
		        //查找客户信息,制作成excel文件并且打包压缩成ZIP文件
				file = service.findAndPatchByEventsNo(events);

				log.info("Zip包的地址： "+file.getAbsolutePath());
				if (file!=null) {
					in = new FileInputStream(file);
					HttpServletResponse response = ServletActionContext.getResponse();
					//设置响应头，控制浏览器下载该文件
                    String realName = file.getName();
					response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));

					OutputStream out = response.getOutputStream();
					//创建缓冲区
					byte buffer[] = new byte[1024];
					int len = 0;
					//循环将输入流中的内容读取到缓冲区当中
					while((len=in.read(buffer))>0){
						//输出缓冲区的内容到浏览器，实现文件下载
						out.write(buffer, 0, len);
					}
					//关闭文件输入流
					in.close();
					//关闭输出流
					out.close();
				}
			}
		} catch (ParseException e) {
		    log.info(e);
		} catch (Exception e) {
			log.info(e);
		}
	}
	
	/**
	 * @author CQ
	 * @since 2017年4月6日16:24:23
	 * @return 显示更新页面
	 */
	public String  showDeletedStatusDialogPage() {
		String parentTabId = HttpTool.getParameter("tabId");
		String customerId = HttpTool.getParameter("customerId");
		String deletedStatus = HttpTool.getParameter("deletedStatus");
		ErpCustomer customer = (ErpCustomer) service.findById(customerId);
		HttpTool.setAttribute("parentTabId", parentTabId);
		HttpTool.setAttribute("customerId", customerId);
		HttpTool.setAttribute("note", customer.getNote());
		HttpTool.setAttribute("deletedStatus", deletedStatus);
		return "showDeletedStatusDialogPage";
	}
	
	
	/**
	 * @author CQ
	 * @since 2017年4月5日18:54:23
	 * 异常报告处理
	 */
	public void updateDeletedStatus() {
		JSONObject json = new JSONObject();
		String parentTabId = HttpTool.getParameter("parentTabId");
		String customerId = HttpTool.getParameter("customerId");
		String note = HttpTool.getParameter("note");
		String deletedStatus = HttpTool.getParameter("deletedStatus");
		try {
			boolean result = service.updateDeletedStatus(customerId, note, deletedStatus);
			HttpTool.setAttribute("result", false);
			if (result) {
				json.put("result", true);
				json.put("parentTabId", parentTabId);
			}
			
		} catch (Exception e) {
			log.info("updateDeletedStatus---", e);
			
		} finally {
			renderJson(json);
		}
	}

	/**
     * @return the conference
     */
    public ErpConference getConference() {
        return conference;
    }
    /**
     * @param conference the conference to set
     */
    public void setConference(ErpConference conference) {
        this.conference = conference;
    }
    public ErpCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(ErpCustomer customer) {
		this.customer = customer;
	}
	public ErpCustomerException getCustomerException() {
		return customerException;
	}
	public void setCustomerException(ErpCustomerException customerException) {
		this.customerException = customerException;
	}
	public ErpEvents getEvents() {
		return events;
	}
	public void setEvents(ErpEvents events) {
		this.events = events;
	}
	public File getAffix() {
		return affix;
	}
	public void setAffix(File affix) {
		this.affix = affix;
	}
	public String getAffixContentType() {
		return affixContentType;
	}
	public void setAffixContentType(String affixContentType) {
		this.affixContentType = affixContentType;
	}
	public String getAffixFileName() {
		return affixFileName;
	}
	public void setAffixFileName(String affixFileName) {
		this.affixFileName = affixFileName;
	}
	public String getCustomerInfoPath() {
		return customerInfoPath;
	}
	public void setCustomerInfoPath(String customerInfoPath) {
		this.customerInfoPath = customerInfoPath;
	}
	 
    /**
     * @return the showId
     */
    public String getShowId() {
        return showId;
    }
    /**
     * @param showId the showId to set
     */
    public void setShowId(String showId) {
        this.showId = showId;
    }
}
