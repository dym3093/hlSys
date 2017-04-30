/**
 * @author DengYouming
 * @since 2016-4-26 下午2:53:34
 */
package org.hpin.settlementManagement.web;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.DocumentException;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.PreciseCompute;
import org.hpin.common.util.StaticMehtod;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.settlementManagement.entity.*;
import org.hpin.settlementManagement.service.ErpSettlementIncomeBXService;
import org.hpin.settlementManagement.service.ErpSettlementTaskBXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 保险公司结算任务_Action
 * @author DengYouming
 * @since 2016-5-1 下午4:26:46
 */
@Namespace("/settlementManagement")
@Action("erpSettlementTaskBX")
@Results({
	@Result(name="listSettlementBX", location="/WEB-INF/settlementManagement/listSettlementBX.jsp"),
	@Result(name="toAddPage", location="/WEB-INF/settlementManagement/addSettlementBX.jsp"),
	@Result(name="toAddEvents", location="/WEB-INF/settlementManagement/addEventsBX.jsp"),
	@Result(name="toAddCustomer", location="/WEB-INF/settlementManagement/addCustomerBX.jsp"),
	@Result(name="viewSettlementBX", location="/WEB-INF/settlementManagement/viewSettlementBX.jsp"),
	@Result(name="toEditPage", location="/WEB-INF/settlementManagement/editSettlementBX.jsp"),
	@Result(name="toChangeAmount", location="/WEB-INF/settlementManagement/changeSettlementAmountBX.jsp"),
	@Result(name="toUploafFilePage", location="/WEB-INF/settlementManagement/uploadFilePage.jsp"),
	@Result(name="proceeds", location="/WEB-INF/settlementManagement/proceeds.jsp"),
	@Result(name="customerAdd", location="/WEB-INF/settlementManagement/customerTaskAdd.jsp"),
	@Result(name="eventsAdd", location="/WEB-INF/settlementManagement/eventsTaskAdd.jsp"),
	@Result(name="listSettlementBX_kj", location="/WEB-INF/settlementManagement/listSettlementBX_kj.jsp"),//会计待收款页面
	@Result(name="listSettlementBX_kj_yjs", location="/WEB-INF/settlementManagement/listSettlementBX_kj_yjs.jsp"),
	@Result(name="toIncome", location="/WEB-INF/settlementManagement/addIncomeBX.jsp"),
	@Result(name="toIncome_view", location="/WEB-INF/settlementManagement/addIncomeBX_view.jsp"),
	@Result(name="toSetSale", location="/WEB-INF/settlementManagement/editSettlementBX_kj.jsp"), //会计设置结算价格的编辑页面
})
public class ErpSettlementTaskBXAction extends BaseAction{
	private Logger log = Logger.getLogger(ErpSettlementTaskBXAction.class);
	private static final long serialVersionUID = -5045381634305276564L;

	//基因公司结算任务_service
	@Autowired
	private ErpSettlementTaskBXService service;
	
	@Autowired
	private ErpSettlementIncomeBXService incomeBXService;//其他收入的Service add by Damian 2017-03-09

	private ErpSettlementTaskBX taskBX;
	private ErpCustomer customer;
	private ErpEvents events;
	private List<ErpSettlementIncomeBX> incomes;
	private static  final SimpleDateFormat taskNoSDF = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private  static final LinkedHashMap<String,String > incomeTypeMap;
	//保险公司结算，其他收入弹窗的费用类型下拉选项
	private static final StringBuilder options =
			new StringBuilder("<select name='incomes[#index#].incomeType' style='width:100px;' class='required'>");

	static {
	    //费用类型映射
		incomeTypeMap = new LinkedHashMap<String, String>();
		incomeTypeMap.put("lecturer","讲师费");
		incomeTypeMap.put("travel","差旅费");
		incomeTypeMap.put("reportPrint","报告打印费");
		incomeTypeMap.put("express","快递费");
		incomeTypeMap.put("other","其他费用");
		Iterator<String> keyIter = incomeTypeMap.keySet().iterator();
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			String value = incomeTypeMap.get(key);
			options.append("<option value='");
			options.append(key);
			options.append("'>");
			options.append(value);
			options.append("</option>");
		}
		options.append("</select>");
	}

	/**
	 * ajax访问鼠标移开后保存或修改数据;
	 * create by henry.xu 20160830
	 */
	public void ajaxSaveOrUpdateEventsTask() {
		//调用保存方法;
		JSONObject json = new JSONObject(); //创建返回对象;
		String statusCode = "200";
		try{
			service.saveOrUpdateEventsTask();
		} catch(Exception e) {
			statusCode = "300";
			log.error("数据操作报错!ErpSettlementTaskBXAction.ajaxSaveOrUpdateEventsTask", e);
		}
		
		json.put("statusCode", statusCode);
		
		renderJson(json);
		
	}
	
	/**
	 * 场次设置界面回显;
	 * create by henry.xu 20160830
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String eventsAdd() {
		
		//获取结算任务id
		String settlementTaskId = HttpTool.getParameter("settlementTaskId"); //结算任务ID;
		//根据结算任务id查询对应会员数据; 去重得到场次数据,关联ERP_EVENTS_COMBO_PRICE表; 分页;
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("settlementTaskId", settlementTaskId);
			service.findEvntsComboBySettleIdPage(page, searchMap) ;
			
		} catch (Exception e) {
			log.error("查询获取错误!ErpSettlementTaskBXAction.eventsAdd", e);
		}
		
		//信息回显;
		HttpTool.setAttribute("settlementTaskId", settlementTaskId);
		HttpTool.setAttribute("navTabId", HttpTool.getParameter("navTabId"));
		
		return "eventsAdd";
	}
	
	/**
	 * 修改会员实际结价数据;
	 * create henry.xu 20160830
	 */
	public void updateCustomerTask() {
		JSONObject json = new JSONObject(); //创建返回对象;
		String statusCode = "200";
		String message = "保存成功!";
		String callbackType = "closeCurrent";
		
		try {
			service.updateCustomerTask();
		} catch (Exception e) {
			message = "保存失败!";
			log.error("修改错误!ErpSettlementTaskBXAction.updateCustomerTask", e);
		}
		//回传参数;
		json.put("statusCode", statusCode);
		json.put("message", message);
		json.put("callbackType", callbackType);
//		json.put("navTabId", HttpTool.getParameter("navTabId"));
		
		renderJson(json);
	}
	
	/**
	 * 按照会员设置实价
	 * @return
	 */
	public String customerAdd() {
		//获取参数;
		String customerTaskId = HttpTool.getParameter("customerId");
		
		//根据customerid查询,顾客信息;
		ErpCustomerSettleBX customerSettle = service.findErpCusSettleById(customerTaskId);
		
		//回传参数;
		HttpTool.setAttribute("navTabId", HttpTool.getParameter("navTabId"));
		HttpTool.setAttribute("customerSettle", customerSettle);
		return "customerAdd";
	}
	
	/**
	 * 收款界面跳转;
	 * create by henry.xu 20160825;
	 * @return
	 */
	public String proceeds() {
		//获取前台参数;
		String settlementTaskId = HttpTool.getParameter("settlementTaskId"); //结算任务ID;
		String navTabId = HttpTool.getParameter("navTabId"); //结算任务的navtabId;
		ErpProceeds proceeds = null;
		
		//通过结算任务id查询收款对象;如果存在则修改,不存在新增;
		if(StringUtils.isNotEmpty(settlementTaskId)) {
			proceeds = service.findErpProceedsBySettleId(settlementTaskId);
		}
		
		if(proceeds == null) {
			proceeds = new ErpProceeds();
		}
		
		//银行集合信息;
		List<ErpBank> banks = service.findAllErpBanks();
		
		//通过结算任务ID获取对象;
		try {
			taskBX = service.findById(settlementTaskId);
			//取小数点保留2位;
			taskBX.setActualTotalAmount(PreciseCompute.round(taskBX.getActualTotalAmount(), 2));
		} catch (Exception e) {
			log.error("通过id获取结算任务对象失败!", e);
		}
		proceeds.setProcTime(new Date());
		//设置返回参数值;
		HttpTool.setAttribute("navTabId", navTabId);
		HttpTool.setAttribute("banks", banks);
		HttpTool.setAttribute("proceeds", proceeds);
		
		return "proceeds";
	}
	/**
	 * 收款数据保存;
	 * create by henry.xu 20160825;
	 * @return
	 */
	public void saveProceeds() {
		String statusCode = "200"; //默认成功; 
		String message = "保存成功!";
		String navTabId = HttpTool.getParameter("navTabId"); //结算任务的navtabId;
		JSONObject json = new JSONObject(); //创建返回对象;
		
		try {
			service.saveOrUpdateProceeds();
			
		} catch (Exception e) {
			statusCode = "300";
			message = "保存失败!";
			log.error("收款保存失败!", e);
		}
		
		json.put("navTabId", navTabId);
		json.put("statusCode", statusCode);
		json.put("message", message);
		json.put("callbackType","closeCurrent");
		renderJson(json);
	}
	
    /**
     * 跳转到添加保险公司结算任务的页面
     * @return String
     * @author DengYouming
     * @since 2016-5-1 下午9:30:58
     */
	public String toAddPage(){
		
		String settleId = HttpTool.getParameter(ErpSettlementTaskBX.F_ID,"");
		String taskNo = HttpTool.getParameter(ErpSettlementTaskBX.F_TASKNO,"");
		String taskName = HttpTool.getParameter(ErpSettlementTaskBX.F_TASKNAME,"");
		String settlementTime = HttpTool.getParameter(ErpSettlementTaskBX.F_SETTLEMENTTIME);
		String paymentType = HttpTool.getParameter(ErpSettlementTaskBX.F_PAYMENTTYPE,"");
		String invoice = HttpTool.getParameter(ErpSettlementTaskBX.F_INVOICE,"");
		
		if(settleId!=null&&settleId.length()>0){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				taskBX = service.findById(settleId);

				if(taskBX==null){
					taskBX = new ErpSettlementTaskBX();
					taskBX.setId(settleId);
					taskBX.setTaskNo(taskNo);
					taskBX.setTaskName(taskName);
					taskBX.setSettlementTime(sdf.parse(settlementTime));
					taskBX.setPaymentType(paymentType);
					taskBX.setInvoice(invoice);
				}

				//查询结算客户信息表
				page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
				Map searchMap = new HashMap();
				searchMap.put("filter_and_settleId_EQ_S", settleId);
				service.findErpCustomerSettleBXByPage(page, searchMap);

				//更新主表的统计信息
				taskBX = service.updateTaskInfo(taskBX, settleId, null, getUserInfo());

			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}else{
			taskBX = new ErpSettlementTaskBX();
			//到新增页面时生成ID
			settleId = UUID.randomUUID().toString().replace("-", "");	
			taskBX.setId(settleId);
			if(StringUtils.isEmpty(taskNo)){
				taskNo = "BXJS"+ taskNoSDF.format(Calendar.getInstance().getTime());
				HttpTool.setAttribute(ErpSettlementTaskBX.F_TASKNO, taskNo);
				taskBX.setTaskNo(taskNo);
			}
		}
				
		return "toAddPage";
	}
	
	 /**
     * 跳转到添加保险公司结算任务的修改页面
     * @return String
     * @author DengYouming
     * @since 2016-5-1 下午9:30:58
     */
	public String toEditPage(){
		String location = "listSettlementBX";
		String settleId = HttpTool.getParameter("id","");
		String type = HttpTool.getParameter("type","");
		Map<String,Object> params;
		
		List<ErpSettlementTaskBX> list;
		
		if(settleId!=null&&settleId.length()>0){
			params = new HashMap<String, Object>();
			params.put(ErpSettlementTaskBX.F_ID, settleId);
			try {
				list  = service.listErpSettlementTaskBX(params);
				if(!CollectionUtils.isEmpty(list)){
					taskBX = list.get(0);
					//更新主表的统计信息
					taskBX = service.updateTaskInfo(taskBX, settleId, null, getUserInfo());
				}
				page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
				Map searchMap = new HashMap();
				searchMap.put("filter_and_settleId_EQ_S", settleId);
				service.findErpCustomerSettleBXByPage(page, searchMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(taskBX!=null){
			HttpTool.setAttribute("taskBX", taskBX);
			HttpTool.setAttribute("type", type);
			location = "toEditPage";
		}
		return location;
	}
	
	 /**
     * 跳转到添加保险公司结算任务的变更收款页面
     * @return String
     * @author DengYouming
     * @since 2016-5-1 下午9:30:58
     */
	public String toChangeAmount(){
		String location = "listSettlementBX";
		String settleId = HttpTool.getParameter("id","");
		String type = HttpTool.getParameter("type","");
		String status = HttpTool.getParameter(ErpSettlementTaskBX.F_STATUS,"");
		Map<String,Object> params;
		
		List<ErpSettlementTaskBX> list;
		
		if(settleId!=null&&settleId.length()>0){
			params = new HashMap<String, Object>();
			params.put(ErpSettlementTaskBX.F_ID, settleId);
			try {
				list  = service.listErpSettlementTaskBX(params);
				if(!CollectionUtils.isEmpty(list)){
					taskBX = list.get(0);
				}
				page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
				Map searchMap = new HashMap();
				searchMap.put("filter_and_settleId_EQ_S", settleId);
				service.findErpCustomerSettleBXByPage(page, searchMap);
			} catch (Exception e) {
			    log.info(e);
			}
		}
		if(taskBX!=null){
			if(StringUtils.isNotEmpty(status)){
				taskBX.setStatus(status);
			}
			HttpTool.setAttribute("taskBX", taskBX);
			HttpTool.setAttribute("type", type);
			location = "toChangeAmount";
		}
		return location;
	}
	
	/**
	 * 跳转到添加场次页面
	 * @return
	 * @author DengYouming
	 * @since 2016-5-1 下午9:34:54
	 */
	public String toAddEvents(){
		String settleId = HttpTool.getParameter("settleId");
		String taskNo = HttpTool.getParameter(ErpSettlementTaskBX.F_TASKNO,"");
		String settlementTime = HttpTool.getParameter(ErpSettlementTaskBX.F_SETTLEMENTTIME,"");
		String type = HttpTool.getParameter("type");
		
		Map searchMap = super.buildSearch();
		//当前用户
		User user = getUserInfo();
		log.info("user"+user.getUserName());
		//非管理员用户
		if(!"系统管理员".equals(user.getRoleNames()) && !"管理员".equals(user.getRoleNames())){
			searchMap.put("filter_and_ymSalesman_EQ_S", user.getUserName());
		}
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			taskBX = service.findById(settleId);
			String projectNo = taskBX.getProjectNo();
			if (StringUtils.isNotEmpty(projectNo)) {
				//查找项目编号相关的结算会员信息
				service.findRelateEvents(page, searchMap, projectNo);
			}
		} catch (ParseException e) {
		    log.info(e);
		} catch (Exception e) {
			log.info(e);
		}

		// 施主，勿怪,为保证查询后查询条件仍在查询框中而设定，命名方式是历史遗留问题，用的地方太多，不好改 begin
		String aaaa = HttpTool.getParameter("aaaa", "");
		String bbbb = HttpTool.getParameter("bbbb", "");
		HttpTool.setAttribute("aaaa", aaaa);
		HttpTool.setAttribute("bbbb", bbbb);
		// 施主，勿怪,为保证查询后查询条件仍在查询框中而设定，命名方式是历史遗留问题，用的地方太多，不好改 end

		HttpTool.setAttribute("settleId", settleId);
		HttpTool.setAttribute("navTabId", super.navTabId);
		HttpTool.setAttribute(ErpSettlementTaskBX.F_TASKNO, taskNo);
		HttpTool.setAttribute(ErpSettlementTaskBX.F_SETTLEMENTTIME, settlementTime);
		HttpTool.setAttribute("type", type);
		return "toAddEvents";
	}
	
	/**
	 * 跳转到会员信息列表
	 * @return String 
	 * @author DengYouming
	 * @since 2016-5-7 下午5:38:35
	 */
	public String toAddCustomer(){
		String settleId = HttpTool.getParameter("settleId");
		Map searchMap = super.buildSearch();
		//当前用户
		User user = getUserInfo();
		//非管理员用户
		if(!"系统管理员".equals(user.getRoleNames()) && !"管理员".equals(user.getRoleNames())){
			searchMap.put("filter_and_ymSalesman_EQ_S", user.getUserName());
		}
		try {
		    taskBX = service.findById(settleId);
		    String projectNo = taskBX.getProjectNo();
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			if (StringUtils.isNotEmpty(projectNo)) {
				service.findRelateCustomer(page, searchMap, projectNo);
			}
		} catch (ParseException e) {
		    log.info(e);
		} catch (Exception e) {
		    log.info(e);
		}
		HttpTool.setAttribute("settleId", settleId);
		HttpTool.setAttribute("navTabId", super.navTabId);
		return "toAddCustomer";
	}
	
	/**
	 * 添加场次到保险公司结算任务(按场次添加)
	 * @return String
	 * @author DengYouming
	 * @since 2016-8-30 上午10:17:47
	 */
	public String addEventsToSettleBXNew(){
		String settleId = HttpTool.getParameter("settleId");
		String ids = HttpTool.getParameter("ids");
		String taskNo = HttpTool.getParameter("taskNo","");
		String settlementTime = HttpTool.getParameter("settlementTime","");
		
		String eventsNos = HttpTool.getParameter("eventsNos");
				
		Map<String,String> resultMap = new HashMap<String, String>();
		JSONObject json = new JSONObject();
		json.put("result","error");
		if(null!=ids&&ids.length()>0&&StringUtils.isNotEmpty(settleId)){
			try {
				//获取当前用户
				User user = getUserInfo();
				resultMap.putAll(service.addEventsCustomersToSettle(settleId, eventsNos, user.getUserName(), user.getId()));
				taskBX = service.findById(settleId);
				json.put("result", "success");
			} catch (Exception e) {
				json.put("result", "failure");
				json.put("msg", e.getMessage());
				log.info(e.getMessage());
			}
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(request.getContextPath()).append("/").toString();
		
		String forwardUrl = null;

		//返回保存的人数和单据总额，套餐总数等信息
		if(resultMap!=null){					
			String subUrl = "settlementManagement/erpSettlementTaskBX!reloadAddSettlementBXPageNew.action?";

			forwardUrl = contextUrl+ subUrl +"settleId="+settleId+"&totalPersonNum="+
					resultMap.get(ErpSettlementTaskBX.F_TOTALPERSONNUM)+"&totalAmount="+resultMap.get(ErpSettlementTaskBX.F_TOTALAMOUNT)+
					"&setMealNum="+resultMap.get(ErpSettlementTaskBX.F_SETMEALNUM)+"&companyNum="+resultMap.get(ErpSettlementTaskBX.F_COMPANYNUM)+
					"&actualTotalAmount="+resultMap.get(ErpSettlementTaskBX.F_ACTUALTOTALAMOUNT) + "&remark="+ resultMap.get(ErpSettlementTaskBX.F_REMARK) 
					+"&proUser="+resultMap.get(ErpSettlementTaskBX.F_PROUSER)+"&branchCompany="+resultMap.get(ErpSettlementTaskBX.F_BRANCHCOMPANY)
					+"&ownedCompany="+resultMap.get(ErpSettlementTaskBX.F_OWNEDCOMPANY)
					+"&taskNo="+taskNo+"&settlementTime="+settlementTime;
			json.put("forwardUrl", forwardUrl);
		}
		//再次回传settleID和navTabId
		HttpTool.setAttribute("settleId", settleId);
		HttpTool.setAttribute("navTabId", super.navTabId);
		
		//添加类型
		forwardUrl =forwardUrl+"&type=events";

		renderJson(json);
		return null;
	}
	
	/**
	 * 添加场次/会员成功后刷新addSettlementBX.jsp页面
	 * @return String
	 * @author DengYouming
	 * @since 2016-8-31 下午5:24:06
	 */
	public String reloadAddSettlementBXPageNew(){
		String settleId = HttpTool.getParameter(ErpSettlementTaskBX.F_ID,"");
		if (StringUtils.isEmpty(settleId)){
			settleId = HttpTool.getParameter("settleId","");
		}
		String taskNo = HttpTool.getParameter(ErpSettlementTaskBX.F_TASKNO);
		//获取添加的类型
		String type = HttpTool.getParameter("type");
		Map<String,Object> params;
		//结算任务统计的结果
		Map<String,String> resultMap;
		
		if(settleId!=null&&settleId.length()>0){
			params = new HashMap<String, Object>();
			params.put("filter_and_settleId_EQ_S", settleId);
			try {
				//获取当前用户
				User user = getUserInfo();
				taskBX = service.findById(settleId);
				resultMap = service.addEventsCustomersToSettle(settleId, null, user.getUserName(), user.getId());
				page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
				//查询
				service.findErpCustomerSettleBXByPage(page, params);
				if(taskBX==null){
					taskBX = new ErpSettlementTaskBX();
					taskBX.setId(settleId);
					taskBX.setTaskNo(taskNo);
				}
				if (!CollectionUtils.isEmpty(resultMap)) {
				    taskBX = service.updateTaskInfo(taskBX, resultMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		HttpTool.setAttribute("taskBX", taskBX);
		HttpTool.setAttribute("type", type);
		
		return "toAddPage";
	}
	
	/**
	 * 保存客户信息到结算任务
	 * @return String
	 * @author DengYouming
	 * @since 2016-9-1 下午4:04:17
	 */
	public String saveCustomerToSettleBxNew(){
		String settleId = HttpTool.getParameter("settleId");
		String ids = HttpTool.getParameter("ids");
		String taskNo = HttpTool.getParameter("taskNo","");
		String settlementTime = HttpTool.getParameter("settlementTime","");
		
		Map<String, String> resultMap = null;
		JSONObject json = new JSONObject();
		json.put("result", "error");
		
		if(StringUtils.isNotEmpty(settleId)&&StringUtils.isNotEmpty(ids)){
			try {
				User user = getUserInfo();
				resultMap = service.addEventsCustomersToSettle(settleId, ids, user.getUserName(), user.getId());
				json.put("result", "success");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(request.getContextPath()).append("/").toString();
		
		String forwardUrl;
		
		if(resultMap!=null){
			String subUrl = "settlementManagement/erpSettlementTaskBX!reloadAddSettlementBXPageNew.action?";

			forwardUrl = contextUrl+ subUrl +"settleId="+settleId+"&totalPersonNum="+
					resultMap.get(ErpSettlementTaskBX.F_TOTALPERSONNUM)+"&totalAmount="+resultMap.get(ErpSettlementTaskBX.F_TOTALAMOUNT)+
					"&setMealNum="+resultMap.get(ErpSettlementTaskBX.F_SETMEALNUM)+"&companyNum="+resultMap.get(ErpSettlementTaskBX.F_COMPANYNUM)+
					"&actualTotalAmount="+resultMap.get(ErpSettlementTaskBX.F_ACTUALTOTALAMOUNT) + "&remark="+ resultMap.get(ErpSettlementTaskBX.F_REMARK) 
					+"&proUser="+resultMap.get(ErpSettlementTaskBX.F_PROUSER)+"&branchCompany="+resultMap.get(ErpSettlementTaskBX.F_BRANCHCOMPANY)
					+"&ownedCompany="+resultMap.get(ErpSettlementTaskBX.F_OWNEDCOMPANY)
					+"&taskNo="+taskNo+"&settlementTime="+settlementTime;
			json.put("forwardUrl", forwardUrl);
			
		}
		//再次回传settleID和navTabId
		HttpTool.setAttribute(ErpSettlementTaskBX.F_ID, settleId);
		HttpTool.setAttribute("navTabId", super.navTabId);
		renderJson(json);
		return null;
	}
	
	/**
	 * 保存保险公司结算任务
	 * @return String
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-4-26 下午5:36:17
	 */
	@SuppressWarnings("static-access")
	public String saveSettlementTask(){
		//操作类型
		String type = HttpTool.getParameter("type","");
		//从前台获取结算任务和会员信息的JSON数据
		String data = HttpTool.getParameter("data");
		//会员信息ID
		String ids = HttpTool.getParameter("detail");
		//把JSON数据封装成ErpSettlementTaskBX和会员对象
		JSONObject jsonData = JSONObject.fromObject(data);
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss"}));
		ErpSettlementTaskBX taskBX = (ErpSettlementTaskBX) JSONObject.toBean(jsonData, ErpSettlementTaskBX.class);
		//创建返回数据
		JSONObject json = new JSONObject();
		json.put("result","error");
		
		Map<String, Object> params = new HashMap<String, Object>();
		//业务操作
		if(taskBX!=null){	
			params.put(ErpSettlementTaskBX.F_ERPSETTLEMENTTASKBX, taskBX);
		}
		if(ids!=null&&ids.length()>0){
			params.put("ids", ids);
		}
		
		params.put("type", type);
		try {
			//保存结算任务和处理相关的会员信息
			service.saveErpSettlementTask(params);
			json.put("result","success");
		} catch (Exception e) {
			json.put("result","error");
			e.printStackTrace();
			System.out.println(e.getMessage());

		}
		renderJson(json);
		return null;
	}
	
	
	/**
	 * 批量删除保险公司结算任务
	 * 
	 * @author DengYouming
	 * @since 2016-4-26 下午7:14:17
	 */
	public void delSettlementBatch(){
		Map<String, Object> params = new HashMap<String, Object>();
		String ids = HttpTool.getParameter("ids");
		JSONObject json = new JSONObject();
		json.put("result","error");
		if(ids!=null&&ids.length()>0){
			params.put(ErpSettlementTaskBX.F_ID, ids);
			try {
				//批量删除
				service.delErpSettlementTaskBatch(params);
				//删除其他收入表的信息
				incomeBXService.delIncomeById(ids, getUserInfo());
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
	 * 显示列表
	 * @return String
	 * @author DengYouming
	 * @since 2016-4-26 下午7:34:48
	 */
	public String listSettlementByProperties(){
       
		String jsp = "listSettlementBX";
		
        Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
        
        Map<String,Object> props = new HashMap<String, Object>();
        //全部的
        List<ErpSettlementTaskBX> totalList = null;
		//当前用户
		User user = getUserInfo();
		//管理员,会计可以查看所有提交的订单，销售只能看自己的订单
		if(StringUtils.isEmpty((String)(searchMap.get("filter_and_status_IN_S")))){
			if("管理员".equals(user.getRoleNames())||"系统管理员".equals(user.getRoleNames())){
				searchMap.put("filter_and_status_IN_S", "'0','1','2'");//管理员可以查看所有
				
				props.put(ErpSettlementTaskBX.F_STATUS, "0,1,2");
			}else if("会计".equals(user.getRoleNames())){
				searchMap.put("filter_and_status_IN_S", "'1','2'");//会计只能查看提交的
				
				props.put(ErpSettlementTaskBX.F_STATUS, "1,2");
			}else{
				searchMap.put("filter_and_status_IN_S", "'0','1','2'");//默认查询条件
				searchMap.put("filter_and_createUser_EQ_S", user.getUserName());
				
				props.put(ErpSettlementTaskBX.F_STATUS, "0,1,2");
				props.put(ErpSettlementTaskBX.F_CREATEUSER, user.getUserName());
			}
		}else{

			//普通员工
			if("远盟销售".equals(user.getRoleNames())){
				searchMap.put("filter_and_createUser_EQ_S", user.getUserName());
				
				props.put(ErpSettlementTaskBX.F_CREATEUSER, user.getUserName());
			}
		}
        searchMap.put("order_createTime", "desc");
        
        String status_new = (String)searchMap.get("filter_and_status_IN_S");
		
		if("'0'".equals(status_new)){
			props.put(ErpSettlementTaskBX.F_STATUS, "0");
			HttpTool.setAttribute("filter_and_status_IN_S", "【0】");
			HttpTool.setAttribute("status_text", "未收款");
		}else if("'1'".equals(status_new)){
			props.put(ErpSettlementTaskBX.F_STATUS, "1");
			HttpTool.setAttribute("filter_and_status_IN_S", "【1】");
			HttpTool.setAttribute("status_text", "待收款");
		}else if("'2'".equals(status_new)){
			props.put(ErpSettlementTaskBX.F_STATUS, "2");
			HttpTool.setAttribute("filter_and_status_IN_S", "【2】");
			HttpTool.setAttribute("status_text", "已收款");
		}else {
			if("会计".equals(user.getRoleNames())){			
				props.put(ErpSettlementTaskBX.F_STATUS, "1,2");
			}else{
				props.put(ErpSettlementTaskBX.F_STATUS, "0,1,2");
			}
			HttpTool.setAttribute("filter_and_status_IN_S", "");
			HttpTool.setAttribute("status_text", "全部");
		}
		
		if(StringUtils.isNotEmpty((String)(searchMap.get("filter_and_taskNo_LIKE_S")))){
			props.put(ErpSettlementTaskBX.F_TASKNO, (String)(searchMap.get("filter_and_taskNo_LIKE_S")));
		}
		
		if(StringUtils.isNotEmpty((String)(searchMap.get("filter_and_taskName_LIKE_S")))){
			props.put(ErpSettlementTaskBX.F_TASKNAME, (String)(searchMap.get("filter_and_taskName_LIKE_S")));
		}
		
		if(StringUtils.isNotEmpty((String)(searchMap.get("filter_and_projectNo_LIKE_S")))){
			props.put(ErpSettlementTaskBX.F_PROJECTNO, (String)(searchMap.get("filter_and_projectNo_LIKE_S")));
		}
		
		if(StringUtils.isNotEmpty((String)(searchMap.get("filter_and_createTime_GEST_T")))){
			props.put("filter_and_createTime_GEST_T", (String)(searchMap.get("filter_and_createTime_GEST_T")));
		}
		
		if(StringUtils.isNotEmpty((String)(searchMap.get("filter_and_createTime_LEET_T")))){
			props.put("filter_and_createTime_LEET_T", (String)(searchMap.get("filter_and_createTime_LEET_T")));
		}
		
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());

			service.findByPage(page, searchMap);
		    //全部的，用于统计差额
	        totalList = service.listErpSettlementTaskBX(props);
		} catch (Exception e) {
			e.printStackTrace();
		}       
	
		searchMap.remove("filter_and_status_IN_S");
		//统计全部应结算金额(元)
		BigDecimal totalAmountCal = new BigDecimal("0");
		BigDecimal actualTotalAmountCal = new BigDecimal("0");
		BigDecimal balanceCal = new BigDecimal("0");
		if(!CollectionUtils.isEmpty(totalList)){
			for (ErpSettlementTaskBX obj : totalList) {
				totalAmountCal = totalAmountCal.add(obj.getTotalAmount()==null?new BigDecimal("0"):obj.getTotalAmount());
				actualTotalAmountCal = actualTotalAmountCal.add(obj.getActualTotalAmount()==null?new BigDecimal("0"):obj.getActualTotalAmount());
			}
			balanceCal = actualTotalAmountCal.subtract(totalAmountCal);
		}
		HttpTool.setAttribute("totalAmountCal", totalAmountCal);
		HttpTool.setAttribute("actualTotalAmountCal", actualTotalAmountCal);
		HttpTool.setAttribute("balanceCal", balanceCal);
		
        return jsp;
	}
	
	/**
	 * 跳转会计代收款页面
	 * @return
	 * @author DengYouming
	 * @since 2016-9-18 下午2:48:56
	 */
	public String listSettlementByPropertiesKj(){
	    
		String status_bx = HttpTool.getParameter("status_bx");
				
		String jsp = "listSettlementBX_kj";
		String statusText = "待收款";
		if("2".equals(status_bx)){
			jsp = "listSettlementBX_kj_yjs";
			statusText = "已收款";
		}
		
        Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
        
        searchMap.put("filter_and_status_IN_S", status_bx);
        
        Map<String,Object> props = new HashMap<String, Object>();
        //全部的
        List<ErpSettlementTaskBX> totalList = null;

        searchMap.put("order_createTime", "desc");
        
        String status_new = (String)searchMap.get("filter_and_status_IN_S");
			
		if(StringUtils.isNotEmpty((String)(searchMap.get("filter_and_taskNo_LIKE_S")))){
			props.put(ErpSettlementTaskBX.F_TASKNO, (String)(searchMap.get("filter_and_taskNo_LIKE_S")));
		}
		
		if(StringUtils.isNotEmpty((String)(searchMap.get("filter_and_taskName_LIKE_S")))){
			props.put(ErpSettlementTaskBX.F_TASKNAME, (String)(searchMap.get("filter_and_taskName_LIKE_S")));
		}
		
		if(StringUtils.isNotEmpty((String)(searchMap.get("filter_and_projectNo_LIKE_S")))){
			props.put(ErpSettlementTaskBX.F_PROJECTNO, (String)(searchMap.get("filter_and_projectNo_LIKE_S")));
		}
		
		if(StringUtils.isNotEmpty((String)(searchMap.get("filter_and_createTime_GEST_T")))){
			props.put("filter_and_createTime_GEST_T", (String)(searchMap.get("filter_and_createTime_GEST_T")));
		}
		
		if(StringUtils.isNotEmpty((String)(searchMap.get("filter_and_createTime_LEET_T")))){
			props.put("filter_and_createTime_LEET_T", (String)(searchMap.get("filter_and_createTime_LEET_T")));
		}
		
		props.put(ErpSettlementTaskBX.F_STATUS, status_bx);
		
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			
			service.findByPage(page, searchMap);
		    //全部的，用于统计差额
	        totalList = service.listErpSettlementTaskBX(props);
		} catch (Exception e) {
			e.printStackTrace();
		}       
	
		searchMap.remove("filter_and_status_IN_S");
		//统计全部应结算金额(元)
		BigDecimal totalAmountCal = new BigDecimal("0");
		BigDecimal actualTotalAmountCal = new BigDecimal("0");
		BigDecimal balanceCal = new BigDecimal("0");
		if(!CollectionUtils.isEmpty(totalList)){
			for (ErpSettlementTaskBX obj : totalList) {
				totalAmountCal = totalAmountCal.add(obj.getTotalAmount()==null?new BigDecimal("0"):obj.getTotalAmount());
				actualTotalAmountCal = actualTotalAmountCal.add(obj.getActualTotalAmount()==null?new BigDecimal("0"):obj.getActualTotalAmount());
			}
			balanceCal = actualTotalAmountCal.subtract(totalAmountCal);
		}
		HttpTool.setAttribute("totalAmountCal", totalAmountCal);
		HttpTool.setAttribute("actualTotalAmountCal", actualTotalAmountCal);
		HttpTool.setAttribute("balanceCal", balanceCal);
		HttpTool.setAttribute("status_text", statusText);

        return jsp;
	}
	
	/**
	 * 确认结算，则结算单不可更改
	 * @return String
	 * @author DengYouming
	 * @since 2016-4-27 上午8:38:34
	 */
	public String confirmSettlementTask(){
		boolean flag;
		String settleId = HttpTool.getParameter("ids");
		String status = HttpTool.getParameter(ErpSettlementTaskBX.F_STATUS);
		Map<String,Object> params;
		JSONObject json = new JSONObject();
		json.put("result", "error");
		try{
			if(settleId!=null&&settleId.length()>0){
				params = new HashMap<String, Object>();
				params.put(ErpSettlementTaskBX.F_ID, settleId);
				params.put(ErpSettlementTaskBX.F_STATUS, status);
				flag = service.confirmErpSettlementTask(params);
				if(flag){
					json.put("result", "success");
				}
			}
		}catch(Exception e){
		    log.info(e);
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 查看结算任务明细
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-10 下午4:52:11
	 */
	public String viewSettlementBX(){
		String settleId = HttpTool.getParameter("settleId");
		
		Map params = super.buildSearch();
		ErpSettlementTaskBX obj = null;
		
		if(settleId!=null&&settleId.length()>0){
			params.put("filter_and_settleId_EQ_S", settleId);
			try {
				page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
				obj = service.findById(settleId);
				service.findErpCustomerSettleBXByPage(page, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(obj!=null){
			HttpTool.setAttribute("taskBX", obj);
		}
		
		return "viewSettlementBX";
	}

	/**
	 * 从结算任务中移除会员
	 * @return
	 * @author Damian
	 * @since 2016-9-1 下午3:38:52
	 */
	public String removeCustomerBatchNew(){
		JSONObject json = new JSONObject();
		json.put("result", "error");
		Map<String,String> resultMap = null;
		//会员ID
		String ids = HttpTool.getParameter("ids");
		//从前台获取结算任务和会员信息的JSON数据
		String data = HttpTool.getParameter("data");
		String type = HttpTool.getParameter("type"); 
		
		if(StringUtils.isNotEmpty(data)&&StringUtils.isNotEmpty(ids)){
			taskBX = null;
			//把JSON数据封装成ErpSettlementTaskBX和会员对象
			JSONObject jsonData = JSONObject.fromObject(data);
			if(jsonData.get(ErpSettlementTaskBX.F_SETTLEMENTTIME)==null||"".equals(jsonData.get(ErpSettlementTaskBX.F_SETTLEMENTTIME))){
				jsonData.remove(ErpSettlementTaskBX.F_SETTLEMENTTIME);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonData.put(ErpSettlementTaskBX.F_SETTLEMENTTIME, sdf.format(new Date()));
			}
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss"}));
			taskBX = (ErpSettlementTaskBX) JSONObject.toBean(jsonData, ErpSettlementTaskBX.class);
			
			Map<String,Object> params = new HashMap<String, Object>();
			params.put(ErpSettlementTaskBX.F_ID, taskBX.getId());
			params.put("ids", ids);
			
			try {
				service.removeCustomerBatch(params);
				//获取当前用户
				User user = getUserInfo();
				resultMap = service.addEventsCustomersToSettle(taskBX.getId(), null, user.getUserName(), user.getId());
				
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e.getMessage());
			}
			
			if(!CollectionUtils.isEmpty(resultMap)){
				HttpServletRequest request = ServletActionContext.getRequest();
				StringBuffer url = request.getRequestURL();
				String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(request.getContextPath()).append("/").toString();
				
				String forwardUrl = null;
				
				taskBX.setTotalAmount(new BigDecimal(resultMap.get(ErpSettlementTaskBX.F_TOTALAMOUNT)));				
				taskBX.setTotalPersonNum(Integer.valueOf(resultMap.get(ErpSettlementTaskBX.F_TOTALPERSONNUM)));
				taskBX.setSetMealNum(Integer.valueOf(resultMap.get(ErpSettlementTaskBX.F_SETMEALNUM)));
				taskBX.setCompanyNum(Integer.valueOf(resultMap.get(ErpSettlementTaskBX.F_COMPANYNUM)));
				taskBX.setRemark(resultMap.get(ErpSettlementTaskBX.F_REMARK));

				taskBX.setActualTotalAmount(new BigDecimal(resultMap.get(ErpSettlementTaskBX.F_ACTUALTOTALAMOUNT)));
				taskBX.setTotalIncome(new BigDecimal(resultMap.get(ErpSettlementTaskBX.F_TOTALINCOME)));
				
				String subUrl = "settlementManagement/erpSettlementTaskBX!reloadAddSettlementBXPageNew.action?";
				
				if("toEditPage".equals(type)){
					subUrl = "settlementManagement/erpSettlementTaskBX!reloadAddSettlementBXPageNew.action?";
				}
				
				forwardUrl = contextUrl+ subUrl +"id="+taskBX.getId()+"&taskNo="+taskBX.getTaskNo()+"&totalPersonNum="+
						Integer.valueOf(resultMap.get(ErpSettlementTaskBX.F_TOTALPERSONNUM))+"&totalAmount="+new BigDecimal(resultMap.get(ErpSettlementTaskBX.F_TOTALAMOUNT))+
					"&setMealNum="+Integer.valueOf(resultMap.get(ErpSettlementTaskBX.F_SETMEALNUM))+"&companyNum="+Integer.valueOf(resultMap.get(ErpSettlementTaskBX.F_COMPANYNUM))+
					"&actualTotalAmount="+new BigDecimal(resultMap.get(ErpSettlementTaskBX.F_ACTUALTOTALAMOUNT))+"&remark="+resultMap.get(ErpSettlementTaskBX.F_REMARK);
				//添加类型
				forwardUrl =forwardUrl+"&type=customer&ids="+ids;
				
				Map filterMap = new HashMap();
				filterMap.put("filter_and_settleId_EQ_S", taskBX.getId());
				
				try {
					//查看数据库中是否已存在该对象
					ErpSettlementTaskBX tempObj = service.findById(taskBX.getId());
					if(tempObj!=null){
						Map<String,Object> props = new HashMap<String,Object>();
						props.put(ErpSettlementTaskBX.F_ERPSETTLEMENTTASKBX, taskBX);
						service.saveErpSettlementTask(props);
					}
					page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
					service.findErpCustomerSettleBXByPage(page, filterMap);
				} catch (Exception e) {
				    log.info(e);
				}
				
				json.put("result", "success");
				json.put("forwardUrl", forwardUrl);
			}
			HttpTool.setAttribute("taskBX", taskBX);
		}
		log.info("delCustomerBatch");
		renderJson(json);
		return null;
	}
	

	/**
	 * 点击【取消】按钮，如果是已有的结算，则返回列表页面，如果是未保存的结算，则删除Erp_customer_settle_bx中的记录
	 * @return
	 * @author DengYouming
	 * @since 2016-6-15 下午2:54:24
	 */
	public String cancelTask(){
		JSONObject json = new JSONObject();
		json.put("result", "error");
		String settleId = HttpTool.getParameter(ErpSettlementTaskBX.F_ID,"");
		//当前用户
		User user= getUserInfo();
		//获取操作用户姓名
		String userName = user.getUserName();
		ErpSettlementTaskBX obj;
		try {
			obj = service.findById(settleId);
			if(obj==null){
				//
				service.cancelTask(settleId, userName);
				//删除其他收入表的信息
				incomeBXService.delIncomeById(settleId, user);
			}
			json.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 跳转到上传页面
	 * @return
	 * @author DengYouming
	 * @since 2016-7-8 下午12:03:28
	 */
	public String toUploafFilePage(){
		JSONObject json = new JSONObject();
		//当前结算任务ID
		String settleId = HttpTool.getParameter(ErpSettlementTaskBX.F_ID);
		
		HttpTool.setAttribute("settleId", settleId);
		HttpTool.setAttribute("navTabId", navTabId);

		return "toUploafFilePage";
	}
	
	public String updateEventsStatusBX(){
		boolean flag = false;
		JSONObject json = new JSONObject();
		try {
			flag = service.updateEventsStatusBX(null);
			if(flag){
				json.put("result", flag);
				json.put("msg", "更新成功!");
			}else{
				json.put("result", flag);
				json.put("msg", "更新失败!");
			}
		} catch (Exception e) {
			json.put("result", flag);
			json.put("msg", "网络异常!");
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 
	 * @return
	 * @author DengYouming
	 * @since 2016-7-8 下午6:22:00
	 */
	public String uploadFile(){
		JSONObject json = new JSONObject();
		String id = HttpTool.getParameter("id");
		String fileName = HttpTool.getParameter("Filename");
		String fileData = HttpTool.getParameter("Filedata");
		
		System.out.println("uploadFile: "+id+", fileName: "+fileName);
		
		renderJson(json);
		return null;
	}
	
    /**
     * 导出保险公司结算任务明细人员清单
     * 
     * @author tangxing
     * @date 2017-2-4上午11:30:28
     */
    public void exportBXSettlementInfo(){
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String settBXId = HttpTool.getParameter("id");
    	List<ErpCustomerSettleBX> bxs = service.getCustomerSettleBXBySettleId(settBXId);
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSSS");
    		String strName = sdf.format(new Date());
    		String excelName = "customerSettleBX"+strName+".xls";			//文件名
    		
    		List<List<String>> rowList = service.buildExcelRow(bxs);
    		
    		service.createExSettleXls(excelName,rowList,response);
		} catch (Exception e) {
    	    log.info(e);
		}
    }

	/**
	 * 跳转到添加其他收入页面
	 * @return String
	 * @author Daimian
	 * @since 2017-03-09
	 */
	public String toIncome(){
		//结算任务ID
		String taskId = HttpTool.getParameter(ErpSettlementIncomeBX.F_TASKID);
		String taskNo = HttpTool.getParameter(ErpSettlementTaskBX.F_TASKNO);
		//是否为查看
		String view = HttpTool.getParameter("view");
		//查询结算任务相关的其他收入信息
		try {
		    if (StringUtils.isNotEmpty(taskId)) {
				//根据ID查找结算任务taskBX
				if (StringUtils.isNotEmpty(taskId)) {
					taskBX = service.findById(taskId);
					//添加查询条件
					Map<String, Object> searchMap = new HashMap<String, Object>();
					page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
					//默认查询 status <> '-1' 即未删除的结算客户信息
					searchMap.put("filter_and_settleId_EQ_S", taskId);
					//查询结算客户的信息
					service.findErpCustomerSettleBXByPage(page, searchMap);
					//查询结算任务中的其他收入
					incomes = incomeBXService.findByTaskId(taskId);
				}
			}
		} catch (ParseException e) {
		    log.info(e);
		} catch (Exception e) {
			log.info(e);
		}
		HttpTool.setAttribute("taskId", taskId);
		HttpTool.setAttribute("taskNo", taskNo);
		HttpTool.setAttribute("incomeTypeMap", incomeTypeMap);
		HttpTool.setAttribute("view", view);
		String jsp = "toIncome";
		if ("view".equals(view)){
		    jsp = "toIncome_view";
		} else {
			HttpTool.setAttribute("navTabId", super.navTabId);
		}

    	return jsp;
	}

	/**
	 * 跳转到添加其他收入页面
	 * @return String
	 * @author Daimian
	 * @since 2017-03-09
	 */
	public String listIncomeType(){
        if (options.toString()!=null) {
			HttpTool.toResponse(options.toString());
		}
		return null;
	}

	/**
	 * 保存其他收入
	 * @return String
	 * @author Daimian
	 * @since 2017-03-10
	 */
	public String saveIncome() {
		JSONObject json = new JSONObject();
		String statusCode = "300";
		String message = "保存失败!";
		String navTabId = super.navTabId;
		String rel = "";
		String callbackType = "closeCurrent";

		String taskId = HttpTool.getParameter(ErpSettlementIncomeBX.F_TASKID);
		String taskNo = HttpTool.getParameter(ErpSettlementTaskBX.F_TASKNO);

		//获取当前用户
		User user = getUserInfo();
		try {
			if (!CollectionUtils.isEmpty(incomes)) {
				//保存客户信息
				Integer count = incomeBXService.saveOrUpdateList(incomes, user, taskId);
				if (count>0) {
					statusCode = "200";
				    message = "保存成功!";

				}
			}
			//根据ID查找结算任务
            if (StringUtils.isNotEmpty(taskId)) {
				taskBX = service.findById(taskId);
			}
			if (taskBX==null){
			    taskBX = new ErpSettlementTaskBX();
				taskBX.setId(taskId);
				taskBX.setTaskNo(taskNo);
			}
			//结算任务的统计数据
			Map<String,String> resultMap = service.addEventsCustomersToSettle(taskId, "", user.getUserName(), user.getId());
			if (!CollectionUtils.isEmpty(resultMap)) {
			    taskBX = service.updateTaskInfo(taskBX, resultMap);
			}
		} catch (Exception e) {
		    log.info(e);
		}

		json.put("statusCode", statusCode);
		json.put("message", message);
		json.put("navTabId", navTabId);
		json.put("rel", rel);
		json.put("callbackType", callbackType);
		json.put("forwardUrl", "");
		renderJson(json);

		return null;
	}

	/**
	 * 根据ID删除Income表信息
	 * @return String
	 * @author Daimian
	 * @since 2017-03-10
	 */
	public String delIncome(){
		JSONObject json = new JSONObject();
		String statusCode = "300";
		String message = "删除失败!";
		String navTabId = super.navTabId;
		String rel = "";
		String callbackType = "";

		String taskNo = HttpTool.getParameter(ErpSettlementTaskBX.F_TASKNO);
		String taskId = HttpTool.getParameter(ErpSettlementIncomeBX.F_TASKID);
		String incomeId = HttpTool.getParameter(ErpSettlementIncomeBX.F_ID);

		//删除其他收入表的信息
        User user = getUserInfo();
		try {
			Integer count = incomeBXService.delIncomeById(incomeId, user);
			if (count>0){
				statusCode = "200";
				message = "删除成功！";
			}
			//根据ID查找结算任务
			if (StringUtils.isNotEmpty(taskId)) {
				taskBX = service.findById(taskId);
			}
			if (taskBX==null){
				taskBX = new ErpSettlementTaskBX();
				taskBX.setId(taskId);
				taskBX.setTaskNo(taskNo);
			}
			//结算任务的统计数据
			taskBX = service.updateTaskInfo(taskBX, taskId, null, user);
		} catch (FileNotFoundException e) {
		    log.info(e);
		} catch (DocumentException e) {
			log.info(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("statusCode", statusCode);
		json.put("message", message);
		json.put("navTabId", navTabId);
		json.put("rel", rel);
		json.put("callbackType", callbackType);
		json.put("forwardUrl", "");

		renderJson(json);
		return null;
	}
	/**
	 * @desc  检查结算任务是否保存
	 * @author Damian
	 * @since 17-3-15 下午4:28
	 */
	public String checkTaskSave(){
		//创建返回数据
		JSONObject json = new JSONObject();
		json.put("result","error");
		String id = HttpTool.getParameter(ErpSettlementTaskBX.F_ID);
		if (StringUtils.isNotEmpty(id)) {
			try {
				taskBX = service.findById(id);
				if (taskBX != null) {
					json.put("result", "success");
				}
			} catch (Exception e) {
			    log.info(e);
			}
		}
		renderJson(json);
		return null;
	}

	/**
	 * @desc  会计使用的编辑界面,用于设置实际结算价格
	 * @author Damian
	 * @since 17-3-16 下午2:26
	 */
	public String toSetSale(){
		//结算任务ID
		String settleId = HttpTool.getParameter(ErpSettlementTaskBX.F_ID,"");
		if(settleId!=null&&settleId.length()>0){
			try {
				taskBX = service.findById(settleId);
				//查询结算客户信息表
				page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
				Map searchMap = new HashMap();
				searchMap.put("filter_and_settleId_EQ_S", settleId);
				service.findErpCustomerSettleBXByPage(page, searchMap);
				//更新主表的统计信息
				taskBX = service.updateTaskInfo(taskBX, settleId, null, getUserInfo());
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}
		return "toSetSale";
	}

	public ErpCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ErpCustomer customer) {
		this.customer = customer;
	}

	public ErpEvents getEvents() {
		return events;
	}

	public void setEvents(ErpEvents events) {
		this.events = events;
	}

	public ErpSettlementTaskBX getTaskBX() {
		return taskBX;
	}

	public void setTaskBX(ErpSettlementTaskBX taskBX) {
		this.taskBX = taskBX;
	}

	public List<ErpSettlementIncomeBX> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<ErpSettlementIncomeBX> incomes) {
		this.incomes = incomes;
	}

}
