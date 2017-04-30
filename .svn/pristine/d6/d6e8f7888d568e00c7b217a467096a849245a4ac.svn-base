package org.hpin.warehouse.web;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.ErpProComboProduct;
import org.hpin.warehouse.entity.ErpProduct;
import org.hpin.warehouse.entity.ErpProductCombo;
import org.hpin.warehouse.service.ErpProductComboService;
import org.hpin.warehouse.service.ErpProductService;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/warehouse")
@Action("proCombo")
@Results({
	@Result(name="list", location="/WEB-INF/warehouse/proCombo/listProCombo.jsp"),
	@Result(name="edit", location="/WEB-INF/warehouse/proCombo/editProCombo.jsp"),
	@Result(name="addProduct", location="/WEB-INF/warehouse/proCombo/queryErpProductSelect.jsp"),
	@Result(name="view", location="/WEB-INF/warehouse/proCombo/viewProCombo.jsp")	
})
public class ErpProductComboAction extends BaseAction {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5014634810848334336L;
	private static final Logger log = Logger.getLogger(ErpApplicationAction.class);
	
	@Autowired
	private ErpProductService erpProductService; // 产品信息service
	@Autowired
	private ErpProductComboService erpProductComboService; //产品套餐service
	
	
	private ErpProductCombo proCombo; //产品套餐对象;
	
	private List<ErpProComboProduct> proComboProducts;
	
	private List<ErpProduct> products;
	
	public void exitsObject() {
		JSONObject json = new JSONObject();
		String message = "操作成功!";
		//验证的同时判断是否可以删除,如果被使用则不可以删除;
		String productComboName = HttpTool.getParameter("productComboName", "");
		boolean result = this.erpProductComboService.exitsObject(id, productComboName);
		
		json.put("result", result);
		json.put("message", message);
		json.put("navTabId", navTabId);
		renderJson(json);
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		JSONObject json = new JSONObject();
		String message = "操作成功!";
		//验证的同时判断是否可以删除,如果被使用则不可以删除;
		boolean result = this.erpProductComboService.deleteValid(ids, getUserInfo());
		if(!result) {
			message = "该数据已被使用,不能删除!";
		}
		json.put("result", result);
		json.put("message", message);
		json.put("navTabId", navTabId);
		renderJson(json);
	}
	
	/**
	 * 关闭启用;
	 */
	public void dealIsClose() {
		JSONObject json = new JSONObject();
		boolean result = true;
		String status = HttpTool.getParameter("status", "");
		this.erpProductComboService.dealIsClose(id, status, getUserInfo());
		
		json.put("result", result);
		json.put("navTabId", navTabId);
		renderJson(json);
	}
	
	/**
	 * 保存与修改;
	 */
	public void saveOrUpdate() {
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String callbackType= "closeCurrent";
		String message = "操作成功!";
		try {
			if(proCombo != null) {
				User userInfo = getUserInfo();
				if(StringUtils.isEmpty(proCombo.getId())) { 
					this.erpProductComboService.saveProCombo(proCombo, userInfo, proComboProducts);
				} else {
					this.erpProductComboService.updateProCombo(proCombo, userInfo, proComboProducts);
				}
			}
			
		} catch (Exception e) {
			statusCode = "300";
			message = e.getMessage();
			log.error(message, e);
		}
		json.put("statusCode", statusCode);
		json.put("navTabId", navTabId);
		json.put("callbackType", callbackType);
		json.put("message", message);
		renderJson(json);
		
		
	}
	
	/**
	 * 列表界面;
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String list() {
		try {
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			this.erpProductComboService.findErpProComboByPage(page, params);
		} catch(Exception e) {
			log.error("产品信息列表查询错误!", e);
		}
		return "list";
	}
	
	/**
	 * 列表界面;
	 * @return
	 */
	public String view() {
		if(StringUtils.isNotEmpty(id)) {
			proCombo = (ErpProductCombo) this.erpProductComboService.findById(id);
			//查询产品列表
			products = this.erpProductComboService.findProComboProductsById(id);
		}
		return "view";
	}
	
	/**
	 * 列表界面;
	 * @return
	 */
	public String edit() {
		if(StringUtils.isNotEmpty(id)) {
			proCombo = (ErpProductCombo) this.erpProductComboService.findById(id);
			//查询产品列表
			products = this.erpProductComboService.findProComboProductsById(id);
		}
		return "edit";
	}
	
	/**
	 * 产品列表;
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String addProduct() {
		try {
			page = new Page( HttpTool.getPageNum(), HttpTool.getPageSize());
			this.erpProductService.findErpProductByPage(page, params);
		} catch(Exception e) {
			log.error("产品信息列表查询错误!", e);
		}
		return "addProduct";
	}
	

	/*get/set*/
	public ErpProductCombo getProCombo() {
		return proCombo;
	}

	public void setProCombo(ErpProductCombo proCombo) {
		this.proCombo = proCombo;
	}

	public List<ErpProComboProduct> getProComboProducts() {
		return proComboProducts;
	}

	public void setProComboProducts(List<ErpProComboProduct> proComboProducts) {
		this.proComboProducts = proComboProducts;
	}

	public List<ErpProduct> getProducts() {
		return products;
	}

	public void setProducts(List<ErpProduct> products) {
		this.products = products;
	}
	
	

}
