package org.hpin.children.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.xmlbeans.impl.piccolo.xml.Piccolo;
import org.hpin.base.usermanager.entity.User;
import org.hpin.children.service.ErpGroupOrderComboService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ErpCompanyComboPrice;

import com.sun.org.apache.bcel.internal.generic.NEW;

@Namespace("/children")
@Action("erpGroupOrderCombo")
@Results({
    @Result(name="erpComboInfo",location="/WEB-INF/children/comboPrice/erpComboInfoList.jsp"),
    @Result(name="editComboInfo",location="/WEB-INF/children/comboPrice/editComboInfo.jsp"),
//    @Result(name="listErpComboPrice",location="/WEB-INF/children/comboPrice.jsp"),
}) 
public class ErpGroupOrderComboAction extends BaseAction{
	
	ErpGroupOrderComboService groupOrderComboService = (ErpGroupOrderComboService) SpringTool.getBean(ErpGroupOrderComboService.class);
	
	public String getErpGroupComboInfoList() throws Exception{
		Map<String,Object> searchMap = buildSearch();
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		page.setResults(groupOrderComboService.getComboPriceList(page, searchMap));
		return "erpComboInfo";
	}
	
	/**
	 * @return 展示儿童套餐信息
	 * @throws Exception
	 */
	public String getComboInfo() throws Exception{
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		List<ErpCompanyComboPrice> comboPrices=groupOrderComboService.getComboPrice(HttpTool.getParameter("company").trim(),HttpTool.getParameter("totalCompany").trim());
		page.setResults(comboPrices);
		HttpTool.setAttribute("company", HttpTool.getParameter("company"));
		HttpTool.setAttribute("totalCompany", HttpTool.getParameter("totalCompany"));
		HttpTool.setAttribute("province", HttpTool.getParameter("province"));
		HttpTool.setAttribute("city", HttpTool.getParameter("city"));
		return "editComboInfo";
	}
	
	/**
	 * 设置儿童套餐价格
	 * @return
	 * @throws Exception
	 */
	public String setComboPrice() throws Exception{
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		int count =0;
		JSONObject jObject =new JSONObject();
		JSONArray json = JSONArray.fromObject(HttpTool.getParameter("dataJson"));
		List<Map<String , Object>> jsonList =new ArrayList<Map<String,Object>>();
		for(int i=0;i<json.size();i++){
			String combo = json.getJSONObject(i).getString("combo");
			String nickName = json.getJSONObject(i).getString("nickName");
			String payedPrice = json.getJSONObject(i).getString("payedPrice");
			String guidancePrice = json.getJSONObject(i).getString("guidancePrice");
			String settlementPrice = json.getJSONObject(i).getString("settlementPrice");
			Map<String , Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("combo", combo);		//套餐名
			jsonMap.put("nickName", nickName);	//昵称
			if (payedPrice.equals("")) {
				jsonMap.put("payedPrice", null);	//客户支付价格
			}else {
				jsonMap.put("payedPrice", payedPrice);	//客户支付价格
			}
			if (guidancePrice.equals("")) {
				jsonMap.put("guidancePrice", null);	//指导价格
			}else {
				jsonMap.put("guidancePrice", guidancePrice);	//指导价格
			}
			if (settlementPrice.equals("")) {
				jsonMap.put("settlementPrice", null);	//渠道价格
			}else {
				jsonMap.put("settlementPrice", settlementPrice);	//渠道价格
			}
			jsonMap.put("userName", user.getAccountName());
			jsonMap.put("userId", user.getCreateUserId());
			jsonMap.put("company", HttpTool.getParameter("company"));
			jsonMap.put("companyId", groupOrderComboService.getCompanyId(HttpTool.getParameter("company")));
			jsonList.add(jsonMap);
		}
		count = groupOrderComboService.setComboPrice(jsonList, HttpTool.getParameter("company"));
		jObject.put("result", count);
		renderJson(jObject);
		return null;
	}
	
}
