package org.hpin.children.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.children.entity.ErpOrderMailNo;
import org.hpin.children.entity.ExportOrderData;
import org.hpin.children.service.ErpOrderInfoService;
import org.hpin.common.core.SpringContextHolder;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.webservice.service.YmGeneReportServiceImpl;
import org.hpin.webservice.service.YmGeneReportServiceImplServiceLocator;

@Namespace("/children")
@Action("erpOrderInfo")
@Results({
    @Result(name="erpOrderList",location="/WEB-INF/children/personalOrder/erpPersonalOrderList.jsp"),
    @Result(name="erpBarCodeInputInfo",location="/WEB-INF/children/personalOrder/erpBarCodeInputInfo.jsp"),
    @Result(name="erpMaiCustomerBoxList",location="/WEB-INF/children/personalOrder/erpMailCustomerBoxList.jsp"),
    @Result(name="erpMaiCompanyBoxList",location="/WEB-INF/children/personalOrder/erpMailCompanyBoxList.jsp"),
    @Result(name="erpMaiCustomerReportList",location="/WEB-INF/children/personalOrder/erpMailCustomerReportList.jsp"),
    @Result(name="editCustomerOrderInfo",location="/WEB-INF/children/personalOrder/editCustomerOrderInfo.jsp")
}) 
public class ErpOrderInfoAction extends BaseAction{
	
//	ErpOrderInfoService orderInfoService = (ErpOrderInfoService)SpringTool.getBean(ErpOrderInfoService.class);
	
	private static final String ADDRESS = "http://gene.healthlink.cn:8088/websGene/geneReport?wsdl";
	
	private Logger log = Logger.getLogger(ErpOrderInfoAction.class);
	
	ErpOrderInfoService orderInfoService = (ErpOrderInfoService)SpringContextHolder.getBean("childrenPrintFile");
	
	public String getErpOrderList(){
		try {
			Map searchMap = buildSearch();
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			searchMap.put("filter_and_isDelete_LIKE_S", "1");
			page.setResults(orderInfoService.findByPage(page, searchMap));
			if (searchMap.get("filter_and_setMealName_LIKE_S")!=null) {
				HttpTool.setAttribute("filter_and_setMealName_LIKE_S",searchMap.get("filter_and_setMealName_LIKE_S") );
			}
			if(searchMap.get("filter_and_status_LIKE_S")!=null){
				HttpTool.setAttribute("filter_and_status_LIKE_S", searchMap.get("filter_and_status_LIKE_S"));
			}
		} catch (Exception e) {
			log.error("ErpOrderInfoAction getMailCustomerReportList", e);
		}
		return "erpOrderList";
	}
	
	/**
	 *	套餐下拉框
	 */
	public void getSelectInput() {
		JSONObject json = new JSONObject();
		json.put("combo", orderInfoService.getSelectedInput());
		renderJson(json);
	}
	
	/**
	 * 打印预览
	 */
	public void getPrintFile(){
		try{
			JSONObject json = new JSONObject();
			String id = HttpTool.getParameter("id").trim();
			String name = HttpTool.getParameter("name").trim();
			HttpServletRequest request = ServletActionContext.getRequest();
			String filePath = orderInfoService.getPrintOrderInfo(id,name,request);
			json.put("path", filePath);
			renderJson(json);
		}catch(Exception e){
			log.error("ErpOrderInfoAction getPrintFile", e);
		}
		
	}
	
	/**
	 * @return 寄客户采样盒页面
	 */
	public String getMailCustomerBoxList() {
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List <ErpOrderMailNo> list =orderInfoService.getCustomerSampleBox(page,HttpTool.getParameter("ids"));
			HttpTool.setAttribute("ids", HttpTool.getParameter("ids"));
			page.setResults(list);
		} catch (Exception e) {
			log.error("ErpOrderInfoAction getMailCustomerReportList", e);
		}
		return "erpMaiCustomerBoxList";
	}
	
	/**
	 * @return 寄客户采样盒页面保存方法
	 */
	public String insertMailCustomerBoxInfo() {
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		JSONArray jsonArray = JSONArray.fromObject(HttpTool.getParameter("dataJson"));
		List<ErpOrderMailNo> jsonList =new ArrayList<ErpOrderMailNo>();
		StringBuilder xml = new StringBuilder();
		for (int i = 0; i < jsonArray.size(); i++) {
			ErpOrderMailNo en = new ErpOrderMailNo();
			en.setId(jsonArray.getJSONObject(i).getString("id"));
			en.setOrderNo(jsonArray.getJSONObject(i).getString("orderNo"));
			en.setMailNo(HttpTool.getParameter("mailNo"));
			en.setCreateUser(user.getAccountName());
			en.setExpressType("1");
			en.setUpdateUser(user.getAccountName());
			en.setStatus("已处理");
			jsonList.add(en);
		}
		int count = orderInfoService.updateStatus(jsonList);
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><orderDeal><orderNo>"+jsonArray.getJSONObject(0).getString("orderNo")+"</orderNo>"
				+ "<status>已处理</status><name>"+jsonArray.getJSONObject(0).getString("name")+"</name>"
				+ "<phone>"+jsonArray.getJSONObject(0).getString("phone")+"</phone>"
				+ "<guardianName>"+jsonArray.getJSONObject(0).getString("guardianName")+"</guardianName>"
				+ "<guardianPhone>"+jsonArray.getJSONObject(0).getString("phone")+"</guardianPhone></orderDeal>");
		String info = pushGeneOrderInfo(xml.toString(),"insertMailCustomerBoxInfo");
		JSONObject json = new JSONObject();
		if(count==0){
			json.put("count", "error");
		}else {
			json.put("count", "success");
			if (info.equals("0") || info==null) {
				json.put("info", 0);
			}
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * @return 条形码录入界面
	 */
	public String getBarCodeInputInfo() {
		List<Map<String , Object>> codeList = orderInfoService.getCustomerOrderInfo(HttpTool.getParameter("id"));
		
		for (Map<String , Object> map:codeList) {
			HttpTool.setAttribute("id", map.get("id").toString());
			HttpTool.setAttribute("orderNo", map.get("orderNo").toString());
			HttpTool.setAttribute("createDate", map.get("createDate").toString());
			HttpTool.setAttribute("name", map.get("name").toString());
			HttpTool.setAttribute("sex", map.get("sex").toString());
			HttpTool.setAttribute("age", map.get("age").toString());
			HttpTool.setAttribute("setMealName", map.get("setMealName").toString());
			HttpTool.setAttribute("guardianName", map.get("guardianName").toString());
			HttpTool.setAttribute("guardianPhone", map.get("guardianPhone").toString());
			HttpTool.setAttribute("relationship", map.get("relationship").toString());
			HttpTool.setAttribute("status", map.get("status").toString());
			HttpTool.setAttribute("reportAddress", map.get("reportAddress").toString());
			HttpTool.setAttribute("note", (String)map.get("note"));
			HttpTool.setAttribute("code", (String)map.get("code"));
		}
		
		return "erpBarCodeInputInfo";
	}
	
	/** 
	 * 条形码录入界面的保存方法
	 * @return
	 */
	public String insertBarCodeInfo() {
		int count = orderInfoService.updateCustomerCode(HttpTool.getParameter("id"),HttpTool.getParameter("code"));
		JSONObject json = new JSONObject();
		json.put("count", count);
		renderJson(json);
		return null;
	}
	
	/**
	 * @return 寄基因公司采样盒页面
	 */
	public String getMailCompanyBoxList() {
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List <ErpOrderMailNo> list =orderInfoService.getCustomerSampleBox(page,HttpTool.getParameter("ids"));
			HttpTool.setAttribute("ids", HttpTool.getParameter("ids"));
			page.setResults(list);
		} catch (Exception e) {
			log.error("ErpOrderInfoAction getMailCustomerReportList", e);
		}
		return "erpMaiCompanyBoxList";
	}
	
	/**
	 * @return 寄基因公司页面保存方法
	 */
	public String insertMailCompanyBoxInfo() {
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		JSONArray jsonArray = JSONArray.fromObject(HttpTool.getParameter("dataJson"));
		List<ErpOrderMailNo> jsonList =new ArrayList<ErpOrderMailNo>();
		StringBuilder xml = new StringBuilder();
		for (int i = 0; i < jsonArray.size(); i++) {
			ErpOrderMailNo en = new ErpOrderMailNo();
			en.setId(jsonArray.getJSONObject(i).getString("id"));
			en.setOrderNo(jsonArray.getJSONObject(i).getString("orderNo"));
			en.setMailNo(HttpTool.getParameter("mailNo"));
			en.setCreateUser(user.getAccountName());
			en.setExpressType("2");
			en.setUpdateUser(user.getAccountName());
			en.setStatus("已送检");
			jsonList.add(en);
		}
		int count = orderInfoService.updateStatus(jsonList);
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><orderDeal><orderNo>"+jsonArray.getJSONObject(0).getString("orderNo")+"</orderNo>"
				+ "<status>已送检</status><name>"+jsonArray.getJSONObject(0).getString("name")+"</name>"
				+ "<phone>"+jsonArray.getJSONObject(0).getString("phone")+"</phone>"
				+ "<guardianName>"+jsonArray.getJSONObject(0).getString("guardianName")+"</guardianName>"
				+ "<guardianPhone>"+jsonArray.getJSONObject(0).getString("phone")+"</guardianPhone></orderDeal>");
		String info = pushGeneOrderInfo(xml.toString(),"insertMailCompanyBoxInfo");
		JSONObject json = new JSONObject();
		if(count==0){
			json.put("count", "error");
		}else {
			json.put("count", "success");
			if (info.equals("0") || info==null) {
				json.put("info", 0);
			}
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * @return 寄基因公司采样盒页面
	 */
	public String getMailCustomerReportList() {
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List <ErpOrderMailNo> list =orderInfoService.getCustomerSampleBox(page,HttpTool.getParameter("ids"));
			HttpTool.setAttribute("ids", HttpTool.getParameter("ids"));
			page.setResults(list);
		} catch (Exception e) {
			log.error("ErpOrderInfoAction getMailCustomerReportList", e);
		}
		return "erpMaiCustomerReportList";
	}
	
	/**
	 * @return 寄客户报告页面保存方法
	 */
	public String insertMailCustomerReportInfo(){
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		JSONArray jsonArray = JSONArray.fromObject(HttpTool.getParameter("dataJson"));
		StringBuilder xml = new StringBuilder();
		List<ErpOrderMailNo> jsonList =new ArrayList<ErpOrderMailNo>();
		for (int i = 0; i < jsonArray.size(); i++) {
			ErpOrderMailNo en = new ErpOrderMailNo();
			en.setId(jsonArray.getJSONObject(i).getString("id"));
			en.setOrderNo(jsonArray.getJSONObject(i).getString("orderNo"));
			en.setMailNo(HttpTool.getParameter("mailNo"));
			en.setCreateUser(user.getAccountName());
			en.setExpressType("3");
			en.setUpdateUser(user.getAccountName());
			en.setStatus("报告已寄出");
			jsonList.add(en);
		}
		int count = orderInfoService.updateStatus(jsonList);
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><orderDeal><orderNo>"+jsonArray.getJSONObject(0).getString("orderNo")+"</orderNo>"
				+ "<status>报告已出</status><name>"+jsonArray.getJSONObject(0).getString("name")+"</name>"
				+ "<phone>"+jsonArray.getJSONObject(0).getString("phone")+"</phone>"
				+ "<guardianName>"+jsonArray.getJSONObject(0).getString("guardianName")+"</guardianName>"
				+ "<guardianPhone>"+jsonArray.getJSONObject(0).getString("phone")+"</guardianPhone></orderDeal>");
		String info = pushGeneOrderInfo(xml.toString(),"insertMailCustomerReportInfo");
		JSONObject json = new JSONObject();
		if(count==0){
			json.put("count", "error");
		}else{
			json.put("count", "success");
			if (info.equals("0") || info==null) {
				json.put("info", 0);
			}
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * @return 要修改的客户订单信息
	 */
	public String setCustomerOrderInfo() throws Exception{
		List<Map<String, Object>> orderList = orderInfoService.getOrderInfoById1(HttpTool.getParameter("id"));
		List<Map<String , Object>> codeList2 = orderInfoService.getOrderInfoById2(HttpTool.getParameter("id"));
		for (Map<String, Object> listMap:orderList){
			HttpTool.setAttribute("id", listMap.get("id").toString());
			HttpTool.setAttribute("orderNo", listMap.get("ORDERNO").toString());
			HttpTool.setAttribute("orderDate", listMap.get("CREATEDATE").toString());
			HttpTool.setAttribute("name", listMap.get("NAME").toString());
			HttpTool.setAttribute("sex", listMap.get("SEX").toString());
			HttpTool.setAttribute("age", listMap.get("AGE").toString());
			HttpTool.setAttribute("setMealName", listMap.get("SETMEALNAME").toString());
			HttpTool.setAttribute("guardianName", listMap.get("GUARDIANNAME").toString());
			HttpTool.setAttribute("guardianPhone", listMap.get("GUARDIANPHONE").toString());
			HttpTool.setAttribute("relationship", listMap.get("RELATIONSHIP").toString());
			HttpTool.setAttribute("reportAddress", listMap.get("REPORTADDRESS").toString());
			HttpTool.setAttribute("note", (String)listMap.get("NOTE"));
			HttpTool.setAttribute("idNo", listMap.get("IDNO").toString());
			HttpTool.setAttribute("weight", (String)listMap.get("WEIGHT"));
			HttpTool.setAttribute("height", (String)listMap.get("HEIGHT"));
			HttpTool.setAttribute("familyHistory", (String)listMap.get("FAMILYHISTORY"));
			HttpTool.setAttribute("code", (String)listMap.get("CODE"));
		}
		if (codeList2.size()>=1) {
			for (int i = 0; i < codeList2.size(); i++) {
				if (codeList2.get(i).get("EXPRESS_TYPE").equals("1")) {
					HttpTool.setAttribute("sampleCode",codeList2.get(i).get("MAILNO"));
				}else {
					HttpTool.setAttribute("reportCode", codeList2.get(i).get("MAILNO"));
				}
			}
		}
		return "editCustomerOrderInfo";
	}
	
	/**
	 * 修改订单信息页面的套餐下拉框
	 */
	public void getSelectedInput() throws Exception{
		JSONObject json = new JSONObject();
		json.put("combo", orderInfoService.getAllComboInfo());
		System.out.println(orderInfoService.getAllComboInfo());
		renderJson(json);
	}
	
	/**
	 * @return 更新订单信息
	 */
	public String updateCustomerOrderInfo() throws Exception{
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		String [] jsonArray = HttpTool.getParameter("json").split(",");
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map1.put("id", jsonArray[1]);
		map2.put("id", jsonArray[1]);
		for(int i=2;i<jsonArray.length;i+=2){
			if (!jsonArray[i].equals("sampleCode") && !jsonArray[i].equals("reportCode")) {
				map1.put(jsonArray[i], jsonArray[i+1]);
			}else {
				map2.put(jsonArray[i], jsonArray[i+1]);
			}
		}
		int count = orderInfoService.updateOrderInfo(map1,map2,user.getAccountName());
		JSONObject json = new JSONObject();
		json.put("count", count);
		renderJson(json);
		return null;
	}
	
	/**
	 * 删除订单
	 * @return 删除条数
	 */
	public String deleteOrderInfo() throws Exception{
		int count = orderInfoService.deleteOrderInfo(HttpTool.getParameter("dataJson"));
		JSONObject json = new JSONObject();
		json.put("count", count);
		renderJson(json);
		return null;
	}
	
	//导出儿童基因订单excel
	public Page exportEtOrderByExl(Page page , Map paramsMap){
		System.out.println(paramsMap);
		List<ExportOrderData> list = orderInfoService.getCustomerMailInfo(page, paramsMap);
		page.setResults(list);
		return page;
	}
	
	/**
	 * 推送微信信息
	 * @param xml
	 * @param method 用于记录某个调用方法的错误日志
	 */
	public String pushGeneOrderInfo(String xml,String method){
		String info = null;
		try {
			YmGeneReportServiceImplServiceLocator push = new YmGeneReportServiceImplServiceLocator();
			push.setYmGeneReportServiceImplPortEndpointAddress(ADDRESS);
			YmGeneReportServiceImpl serviceImpl = push.getYmGeneReportServiceImplPort();
			info = serviceImpl.updateGeneOrderInfo (xml);
		} catch (Exception e) {
			log.error(method, e);
			e.printStackTrace();
		}
		return info;
	}
	
}
