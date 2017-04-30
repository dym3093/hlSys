package org.hpin.warehouse.web;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.dict.entity.SysDictType;
import org.hpin.base.region.entity.JYRegion;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.children.web.ErpOrderInfoAction;
import org.hpin.common.constant.URLConstants;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpClientTool;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StaticMehtod;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.JYCombo;
import org.hpin.warehouse.entity.StoreApplication;
import org.hpin.warehouse.entity.StoreApplicationDetail;
import org.hpin.warehouse.entity.StoreApplyedCount;
import org.hpin.warehouse.entity.StoreDeliveryDetail;
import org.hpin.warehouse.entity.StoreProduce;
import org.hpin.warehouse.mail.MailSenderInfo;
import org.hpin.warehouse.mail.SimpleMailSender;
import org.hpin.warehouse.service.StoreApplicationDetailService;
import org.hpin.warehouse.service.StoreApplicationService;
import org.hpin.warehouse.service.StoreDeliveryDetailService;
import org.hpin.warehouse.service.StoreProduceDetailService;
import org.hpin.webservice.service.YmGeneReportServiceImpl;
import org.hpin.webservice.service.YmGeneReportServiceImplServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


//http://192.168.1.15/hbs/warehouse/storeApplication!liststoreApplication.action
@Namespace("/warehouse")
@Action("storeApplication")
@Results({
	@Result(name = "liststoreApplication", location = "/WEB-INF/warehouse/storeApplication/liststoreApplication.jsp"),
	@Result(name = "addstoreApplication", location = "/WEB-INF/warehouse/storeApplication/addstoreApplication.jsp"),
	@Result(name = "browstoreApplication", location = "/WEB-INF/warehouse/storeApplication/browstoreApplication.jsp"),
	@Result(name = "lookUpStoreApplication", location = "/WEB-INF/warehouse/storeApplication/lookUpStoreApplication.jsp"),
	@Result(name = "modifystoreApplication", location = "/WEB-INF/warehouse/storeApplication/modifystoreApplication.jsp"),
	@Result(name = "toStoreProduceDetail", location = "/WEB-INF/warehouse/storeApplication/listStoreProduceDetail.jsp"),
	@Result(name = "pullStoreApplication", location = "/WEB-INF/warehouse/storeApplication/pullStoreApplication.jsp"),
	@Result(name = "sendMail" , location = "/WEB-INF/warehouse/storeApplication/sendMail.jsp"),
})
public class StoreApplicationAction extends BaseAction{
	StoreDeliveryDetailService deliveryService = (StoreDeliveryDetailService) SpringTool.getBean(StoreDeliveryDetailService.class);
	StoreApplicationService storeApplicationService = (StoreApplicationService) SpringTool.getBean(StoreApplicationService.class);
	DeptService deptService=(DeptService) SpringTool.getBean(DeptService.class);
	CustomerRelationShipService shipService=(CustomerRelationShipService) SpringTool.getBean(CustomerRelationShipService.class);
	private UserService userService = (UserService) SpringTool.getBean(UserService.class);
	private CustomerRelationShipService customerService = (CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
	
	private StoreApplication storeApplication;
	
	private Logger log = Logger.getLogger(StoreApplicationAction.class);
	
	private List<StoreApplicationDetail>  detailList; // add by DengYouming 2016-07-14
	
	@Autowired
	private StoreProduceDetailService storeProduceDetailService;  // add by DengYouming 2016-07-15
	
	@Autowired
	private StoreApplicationDetailService storeApplicationDetailService; // add by DengYouming 2016-07-15
	
	/**
	 * 获取CRM中相关项目负责人以及项目名称信息，解析后返填到当前申请界面中
	 * create by henry.xu 20160905
	 */
	public void getCrmBaseInfoByProCode() {
		JSONObject json = new JSONObject();
		String proNum = HttpTool.getParameter("proNum");
		String url="http://192.168.1.16:8010/websHbs/hbs/hbsPmInfo!getGeneProjectInfo.action"; //crm项目编码访问路径
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("projectNum", proNum);
			HttpClientTool tool = HttpClientTool.getInstance();
			String response = tool.httpPost(url, param);
			
			json.put("result", response);
		} catch (Exception e1) {
			log.error("获取CRM异常YmGeneReportServiceImpl.getCrmBaseInfoByProCode", e1);
		} 
		renderJson(json);
	}
	
	public String addstoreApplication(){
		//到新增页面时生成ID
		String id = UUID.randomUUID().toString().replace("-", "");
		storeApplication = new StoreApplication();
		storeApplication.setId(id);
		HttpTool.setAttribute("useDate", StaticMehtod.getDateString(5));
		return "addstoreApplication";
	}
	public String modifystoreApplication(){
		storeApplication =(StoreApplication)storeApplicationService.findById(id);
		return "modifystoreApplication";
	}
	public String browstoreApplication(){
		storeApplication = (StoreApplication)storeApplicationService.findById(id);
		List emsList=storeApplicationService.getEmsList(id);
		HttpTool.setAttribute("emsList", emsList);
		String batNo = HttpTool.getParameter("batNo");
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
//			List<StoreApplicationDetail> applyDetailsList= storeProduceDetailService.getApplyDetails(id);
//			List<StoreDeliveryDetail> list = storeProduceDetailService.getDeliveryDetails(page,storeApplication.getBatNo());
			List<StoreApplyedCount> applyDetailsList= deliveryService.getApplyDetails(id,batNo);
			List<StoreDeliveryDetail> list = deliveryService.getDeliveryDetails(page,batNo);
			HttpTool.setAttribute("applyDetailsList", applyDetailsList);
			page.setResults(list);
		} catch (Exception e) {
			log.error("StoreApplicationAction toStoreProduceDetail", e);
		}
		return "browstoreApplication";
	}
	/**
	 * 提交申请
	 * @return
	 */
	public String saveStoreApplication(){
		JSONObject json = new JSONObject();
		String sqr=storeApplication.getCreateUserName();//申请人
		try {	
			if(StrUtils.isNotNullOrBlank(storeApplication.getId())){
				storeApplicationService.update(storeApplication);
			}else{
				
			String ownedCompanyId=storeApplication.getOwnedCompanyId();
			String branchCommany=HttpTool.getParameter("branchCommany");
			List<CustomerRelationShip> list = customerService.findByOwnedCompany(ownedCompanyId) ;
			for (CustomerRelationShip customerRelationShip : list) {
				if(customerRelationShip.getBranchCommany().equals(branchCommany)){
					storeApplication.setBannyCompanyId(customerRelationShip.getId());
				}
			}
			
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			storeApplication.setCreateDeptId(currentUser.getDeptId());
			storeApplication.setCreateUserId(currentUser.getAccountName());
			storeApplication.setCreateUserName(currentUser.getUserName());
			storeApplication.setId(null);
			storeApplication.setCreateTime(new Date());	
			//总公司名称
			storeApplication.setOwnedCompany(deptService.findByTreepath(storeApplication.getOwnedCompanyId()).getDeptName());
			//子公司名称
			CustomerRelationShip ship=(CustomerRelationShip)shipService.findById(storeApplication.getBannyCompanyId());
			storeApplication.setBannyCompanyName(ship.getBranchCommany());
			//批次号
			storeApplication.setBatNo(StaticMehtod.getCurrentDateTime("yyyyMMddHHmmss"));
			storeApplicationService.save(storeApplication);
			}
/***************************发送邮件*******************************************/
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setMailServerHost("smtp.exmail.qq.com");
			mailInfo.setMailServerPort("25");
			mailInfo.setValidate(true); 
			
			String currentMail= "gene@healthlink.cn";// 用公共邮箱发件
			String currentMailPwd= "Yue123.com";// 公共邮箱密码
			
			mailInfo.setUserName(currentMail);	
			mailInfo.setPassword(currentMailPwd);
			mailInfo.setFromAddress(currentMail);
			
			
			
			
			//发件人:
			String username=currentUser.getUserName();
			//项目负责人：             
			String remark1=storeApplication.getRemark1();
			//项目归属：
			String remark2=storeApplication.getRemark2();
			//联系电话
			String receiveTel=storeApplication.getReceiveTel();
			//需求说明
			String requires=storeApplication.getRequires();
			//处理意见
			String remark=storeApplication.getRemark();
			//项目编码
			String remark3=storeApplication.getRemark3();
			// 邮件内容
			StringBuffer buffer = new StringBuffer();
			
			//收件人
			String toEmail;
			String subject;//标题
			String mes;
			if(remark!=null&&remark.length()>0){//处理
				User u=userService.getUserByCreateName(sqr);
				toEmail=u.getEmail();
				subject="基因项目"+remark2+"处理邮件";// 邮件标题
				buffer.append("申请编码："+storeApplication.getBatNo()+"\n");
				buffer.append("项目编码："+remark3+"\n");
				buffer.append("项目归属："+remark2+"\n");
				buffer.append("联系电话："+receiveTel+"\n");
				buffer.append("需求说明："+requires+"\n");
				buffer.append("处理意见："+remark+"\n");
				mes="已处理，请查收";
				buffer.append("发件人："+username+"\n");
				buffer.append("发件人邮箱："+toEmail+"\n");
			}else{//申请
				//获取业务员邮箱
				String createUserName="远盟管理";
				User u=userService.getUserByCreateName(createUserName);
				toEmail=u.getEmail();
				subject="基因项目"+remark2+"申请邮件";// 邮件标题
				buffer.append("申请编码："+storeApplication.getBatNo()+"\n");
				buffer.append("项目编码："+remark3+"\n");
				buffer.append("项目负责人："+remark1+"\n");
				buffer.append("项目归属："+remark2+"\n");
				buffer.append("联系电话："+receiveTel+"\n");
				buffer.append("需求说明："+requires+"\n");
				mes="已申请，请查收";
				buffer.append("发件人："+username+"\n");
				buffer.append("发件人邮箱："+toEmail+"\n");
			}
			buffer.append("链接网址：http://gene.healthlink.cn\n");
			mailInfo.setSubject(subject);// 邮件标题
			// 收件人邮箱
			// 收件人邮箱 固定为  xuejunyang@healthlink.cn
			toEmail = "xuejunyang@healthlink.cn";
			mailInfo.setToAddress(toEmail);
			buffer.append(mes);
			mailInfo.setContent(buffer.toString());
			// 发送邮件
			SimpleMailSender sms = new SimpleMailSender();
			// 发送文体格式
			sms.sendTextMail(mailInfo);
			// 发送html格式
			SimpleMailSender.sendHtmlMail(mailInfo);
			System.out.println("邮件发送完毕");
/********************邮件发送结束**************************************************/
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "closeCurrent");
		}catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
		}
		renderJson(json);
		return null;
	}
	
	public Page findByPage(Page page , Map paramsMap){
		paramsMap.put("order_createTime", "desc");
		storeApplicationService.findByPage(page, paramsMap);
		return page ;
	}
	
	public String liststoreApplication() throws ParseException{
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		String status = HttpTool.getParameter("filter_and_status_LIKE_S");
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			if(!user.getUserName().equals("管理员")&&!"杨学军".equals(user.getUserName())){
				if("40289b6a5206079d01520619b31e0008".equals(user.getDeptId())){
					paramsMap.put("filter_and_businessId_EQ_S", user.getDeptId());
					HttpTool.setAttribute("checkflag", "1");
				}else{
				    paramsMap.put("filter_and_createUserId_EQ_S", user.getAccountName());
				}
			}
			findByPage(page, paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpTool.setAttribute("filter_and_status_EQ_S", status);
		return "liststoreApplication";
	}
	
	public String lookUpStoreApplication() throws ParseException{
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();			
		    paramsMap.put("filter_and_status_NEQ_S", "2");
			findByPage(page, paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "lookUpStoreApplication";
	}
	
	/**
	 * 跳转到品类列表页面
	 * @return String
	 * @author DengYouming
	 * @since 2016-7-12 下午7:16:21
	 */
	public String toStoreProduceDetail(){
		try {
			Map searchMap = super.buildSearch();
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			page.setResults(storeProduceDetailService.findByPage(page, searchMap));
			
		} catch (Exception e) {
			log.error("StoreApplicationAction toStoreProduceDetail", e);
		}
		HttpTool.setAttribute("navTabId", navTabId);
		return "toStoreProduceDetail";
	}
	
	/**
	 * 	获取物品品类
	 */
	public String getThingsType(){
		String parentDictid = HttpTool.getParameter("parentDictid");
		StringBuffer json = new StringBuffer("[") ;
		List<SysDictType> typeList = storeApplicationService.getThingsType(parentDictid);
		if(typeList.size() > 0){
			for(SysDictType type:typeList){
				json.append("{\"text\":\"" +type.getDictName() + "\",\"id\":\"" + type.getDictId()+ "\",\"leaf\":" + false) ;
				json.append("},") ;
			}
		}
		if (json.toString().endsWith(",")) {
			json = json.delete(json.length() - 1, json.length());
		}
		json.append("]");
		this.jsonString = json.toString();
		return "json";
	}
	
	/**
	 * 基因物品申请（新的提交申请业务逻辑）
	 * @return String
	 * @author DengYouming
	 * @since 2016-7-11 上午11:50:24
	 */
	public String appleStoreApplication(){
		JSONObject json = new JSONObject();
		boolean flag = false;
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		try {	
			if(user!=null&&storeApplication!=null&& !CollectionUtils.isEmpty(detailList)){
				flag = storeApplicationService.saveStoreApplicationInfo(user, storeApplication, detailList);
			}
/********************邮件发送结束**************************************************/
			if(flag){
				json.accumulate("statusCode", 200);
				json.accumulate("message", "操作成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}else{
				json.accumulate("statusCode", 300);
				json.accumulate("message", "网络异常！请稍后再试！");
				json.accumulate("navTabId", super.navTabId);
			}
		}catch (Exception e) {
			e.printStackTrace();
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
		}
		renderJson(json);
		return null;
	}

	/**
	 * 申请派发
	 * @return String
	 * @author DengYouming
	 * @since 2016-7-15 上午10:44:34
	 */
	public String pullStoreApplication(){
		String id = HttpTool.getParameter("id","");
		if(StringUtils.isNotEmpty(id)){
			try {
				storeApplication = (StoreApplication) storeApplicationService.findById(id);
				
				page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
				
				Map<String,Object> params = new HashMap<String, Object>();
				params.put(StoreApplicationDetail.F_IDRELATED, id);
				int total = storeApplicationDetailService.listByProps(params).size();
				params.put(Page.F_PAGENUM, HttpTool.getPageNum());
				params.put(Page.F_PAGESIZE, HttpTool.getPageSize());
				detailList = storeApplicationDetailService.listByProps(params);
				if(!CollectionUtils.isEmpty(detailList)){
					page.setResults(detailList);
					page.setTotalCount(Long.valueOf(""+total));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "pullStoreApplication";
	}
	
	
	public String toSendMail(){
		return "sendMail";
	}
	
	/**
	 * 手动发送未发出的邮件
	 * @return
	 * @author DengYouming
	 * @since 2016-8-12 下午6:32:43
	 */
	public String sendMailByHand(){
		String msg = null;
		String email = HttpTool.getParameter("email");
		String fileName = HttpTool.getParameter("fileName");
		try {
			msg = storeApplicationService.sendMailByHand(email,fileName);
		} catch (Exception e) {
			log.info(e.getMessage());
			msg = e.getMessage();
		}
		return msg;
	}
	
	
	
	public StoreApplication getStoreApplication() {
		return storeApplication;
	}
	public void setStoreApplication(StoreApplication storeApplication) {
		this.storeApplication = storeApplication;
	}
	public List<StoreApplicationDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<StoreApplicationDetail> detailList) {
		this.detailList = detailList;
	}
	
}
