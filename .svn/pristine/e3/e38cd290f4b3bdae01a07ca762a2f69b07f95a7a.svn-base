package org.hpin.settlementManagement.web;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.ss.formula.functions.Hlookup;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ErpCompanyComboPrice;
import org.hpin.settlementManagement.entity.ErpSettlementTaskBX;
import org.hpin.settlementManagement.service.ErpCompanyComboPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.ymjy.combo.entity.Combo;


@Namespace("/settlementManagement")
@Action("erpCompanyComboPrice")
@Results({
	@Result(name="listErpCompanyComboPrice",location="/WEB-INF/settlementManagement/listErpCompanyComboPrice.jsp"),
	@Result(name="toAddErpCompanyComboPrice",location="/WEB-INF/settlementManagement/addErpCompanyComboPrice.jsp"),
	@Result(name="editErpCompanyComboPrice",location="/WEB-INF/settlementManagement/editErpCompanyComboPrice.jsp"),
	@Result(name="showCompanyInfo",location="/WEB-INF/settlementManagement/showCompanyInfo.jsp")
})  
@SuppressWarnings({"rawtypes","unchecked"})
public class ErpCompanyComboPriceAction extends BaseAction {

	
	private static final long serialVersionUID = 8504157327410173033L;
	
	@Autowired
	private ErpCompanyComboPriceService companyComboPriceService;

	@Autowired
	private CustomerRelationShipService customerRelationShipService;
	
	/**
	 * @return 显示套餐列表
	 * @since 2016年6月15日10:19:11
	 * @author chenqi
	 * @throws Exception
	 */
	public String listErpCompanyComboPrice() throws Exception{
		Map param = super.buildSearch();
		try {
        	page = new Page();
        	int pageNum=HttpTool.getPageNum();
        	int pageSize=HttpTool.getPageSize();
			List list = companyComboPriceService.listErpCompanyComboPrice(pageNum,pageSize,param);
			long count = companyComboPriceService.getTotalPageSize(param);
			page.setPageNum(pageNum);
			page.setPageSize(pageSize);
			page.setTotalCount(count);
			page.setResults(list);
			if(param.get("filter_and_company_LIKE_S")!=null){
				HttpTool.setAttribute("filter_and_company_LIKE_S", param.get("filter_and_company_LIKE_S"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "listErpCompanyComboPrice";
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 * @author DengYouming
	 * @since 2016-5-4 下午5:26:28
	 */
	public String toAddErpCompanyComboPrice(){
		String id = HttpTool.getParameter(ErpCompanyComboPrice.F_ID);
		//有ID则为修改操作
		if(id!=null&&id.length()>0){
			ErpCompanyComboPrice entity = (ErpCompanyComboPrice) companyComboPriceService.findById(id);
			HttpTool.setAttribute("entity", entity);
		}
		return "toAddErpCompanyComboPrice";
	}
	
	
	/**
	 * @return 点击公司名称查看公司套餐
	 * @throws Exception
	 */
	public String showCompanyInfo()throws Exception{
		String companyName=HttpTool.getParameter("companyName").trim();
		String companyId=HttpTool.getParameter("companyId");
		 try {
				page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
				//List<ErpCompanyComboPrice> list = customerRelationShipService.getCompanyPackagePrice(companyName,companyId);
				List<ErpCompanyComboPrice> list=companyComboPriceService.getCustomerShipProByCompanyId(companyId);
				page.setResults(list);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return "showCompanyInfo";
	}
	
	/**
	 * 保存套餐价格
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-4 下午5:27:09
	 */
	public String saveErpCompanyComboPrice(){
		JSONObject json = new JSONObject();
		//获取前台数据,封装成对象
		String jsonStr = HttpTool.getParameter(ErpCompanyComboPrice.F_ERPCOMPANYCOMBOPRICE);
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		ErpCompanyComboPrice entity = (ErpCompanyComboPrice) JSONObject.toBean(jsonObj, ErpCompanyComboPrice.class);
		
		json.put("result", "error");
		try{
			if(entity!=null){
				boolean flag = companyComboPriceService.addErpCompanyComboPrice(entity);
				if(flag){
					json.put("result", "success");
				}
			}
		}catch(Exception e){
			json.put("result", "error");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 根据公司ID查找其下的套餐
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-5 下午12:03:48
	 */
	public String listComboByCompanyId(){
		JSONObject jsonObject = new JSONObject(); 
		
		String companyId = HttpTool.getParameter("companyId");
		
		List<Combo> list = null;
		try {
			if(companyId!=null&&companyId.length()>0){
				list = companyComboPriceService.listComboByCompanyId(companyId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		jsonObject.put("list", list);
		renderJson(jsonObject);
		return null;
	}
	
	/**
	 * 根据公司ID查找其下的套餐
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-5 下午12:03:48
	 */
	public String listComboJson(){
		JSONObject jsonObject = new JSONObject(); 
		
		String companyId = HttpTool.getParameter("companyId");
		
		List<Combo> comboList = null;
		Map<String,String> jsonMap = null;
		try {
			if(companyId!=null&&companyId.length()>0){
				comboList = companyComboPriceService.listComboByCompanyId(companyId);
				jsonMap = new HashMap<String, String>();
				if(comboList!=null&&comboList.size()>0){
					for (Combo combo : comboList) {
						jsonMap.put(combo.getComboName(), combo.getId());						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		jsonObject.put("list", jsonMap);
		renderJson(jsonObject);
		return null;
	}
	
	/**
	 * 批量删除公司套餐价格记录
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-5 下午4:11:42
	 */
	public String deleteErpCompanyComboPriceBatch(){
		JSONObject json = new JSONObject();
		String ids = HttpTool.getParameter("ids");
		json.put("result", "error");
		if(ids!=null&&ids.length()>0){
			try {
				companyComboPriceService.deleteErpCompanyComboPriceBatch(ids);
				json.put("result", "success");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		renderJson(json);
		return null;
	}
	
	public String toEditErpCompanyComboPrice()throws Exception{
		String company=URLDecoder.decode(HttpTool.getParameter("company"),"UTF-8");
		String parentCompany=URLDecoder.decode(HttpTool.getParameter("parentCompany"),"UTF-8");
		String province=HttpTool.getParameter("province");
		String city=HttpTool.getParameter("city");
		String id=HttpTool.getParameter("id").trim();
		String branchCompanyId = HttpTool.getParameter("branchCompanyId");
		List<ErpCompanyComboPrice> list=companyComboPriceService.getCustomerShipProByCompanyId(branchCompanyId);
		
		//List<CustomerRelationShipPro> relationShipPros = customerRelationShipService.findCustRelShipProsByCustRelShipId(branchCompanyId);
		
		HttpTool.setAttribute("company", company);
		HttpTool.setAttribute("parentCompany", parentCompany);
		HttpTool.setAttribute("province", province);
		HttpTool.setAttribute("city", city);
		HttpTool.setAttribute("branchCompanyId", branchCompanyId);
		HttpTool.setAttribute("results", list);
		//HttpTool.setAttribute("shipProList", relationShipPros);
		return "editErpCompanyComboPrice";
	}
	
	public String save() throws Exception{
		String result=HttpTool.getParameter("result");	//格式：基础三,A0001=100&基础一,A0001=150&基础二,A0001=150
		String branchCompanyId=HttpTool.getParameter("branchCompanyId");
		String company = HttpTool.getParameter("company");
		Map<String, Object> map1= new HashMap<String, Object>();
		for(String str: result.split("&")){
			for (int i = 0; i < 1; i++) {
				map1.put(str.split("=")[0], str.split("=")[1]);
			}
		}
		boolean flag = customerRelationShipService.setComboPrice(map1,branchCompanyId,company.substring(4,company.length()));
		JSONObject json=new JSONObject();
		if (flag) {
			json.put("result", "success");
		}else {
			json.put("result", "error");
		}
		renderJson(json);
		return null;
	}
	
}
