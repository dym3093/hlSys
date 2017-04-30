package org.hpin.warehouse.web;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.ErpProduct;
import org.hpin.warehouse.entity.ErpStoregeIn;
import org.hpin.warehouse.entity.ErpStoregeReturn;
import org.hpin.warehouse.entity.StoreWarehouse;
import org.hpin.warehouse.service.ErpStoregeInService;
import org.hpin.warehouse.service.ErpStoregeReturnService;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;


/**
 * 退货管理
 * @author machuan
 * @date 2017年3月21日
 */
@Namespace("/warehouse")
@Action("storegeReturn")
@Results({
	@Result(name = "liststoregeReturn", location = "/WEB-INF/warehouse/storegeReturn/listStoregeReturn.jsp"),
	@Result(name = "toAddReturn", location = "/WEB-INF/warehouse/storegeReturn/addReturn.jsp"),
})
public class ErpStoregeReturnAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ErpStoregeReturnService erpStoregeReturnService;
	
	@Autowired
	private ErpStoregeInService storegeInService;
	
	private Logger log = Logger.getLogger(ErpStoregeReturnAction.class);
	
	private ErpStoregeReturn storegeReturn;
	
	
	/**
	 * 列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String liststoregeReturn() {
		try {
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			this.erpStoregeReturnService.findStoregeReturnsByPage(page, getUserInfo(), params);
		} catch(Exception e) {
			log.error("退货信息查询错误!", e);
		}
		return "liststoregeReturn";
	}
	
	public String toAddReturn(){
		return "toAddReturn";
	}
	
	/**
	 * 查询所有仓库
	 * @return
	 * @throws Exception
	 */
	public String getStoreWareHouse() throws Exception{
		StringBuffer json = new StringBuffer("[") ;
		List<StoreWarehouse> list = erpStoregeReturnService.getStoreWareHouse() ;
		if(list.size() > 0){
			for(int i = 0 ; i < list.size() ; i ++){
				StoreWarehouse storeWarehouse = new StoreWarehouse();
				storeWarehouse = list.get(i) ;
				json.append("{\"text\":\"" + storeWarehouse.getName() + "\",\"id\":\"" + storeWarehouse.getId()+ "\",\"leaf\":" + false) ;
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
	 * 查询所有产品
	 * @return
	 * @throws Exception
	 */
	public String getProduct() throws Exception{
		StringBuffer json = new StringBuffer("[") ;
		List<ErpProduct> list = erpStoregeReturnService.getProduct() ;
		if(list.size() > 0){
			for(int i = 0 ; i < list.size() ; i ++){
				ErpProduct erpProduct = new ErpProduct();
				erpProduct = list.get(i) ;
				json.append("{\"text\":\"" + erpProduct.getProductName() + "\",\"id\":\"" + erpProduct.getId()+ "\",\"leaf\":" + false) ;
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
	 * 添加客户页面
	 * @return
	 */
	public void addReturn(){ 
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String navTabId = HttpTool.getParameter("navTabId");
		String standard = HttpTool.getParameter("standard");
		try {
			storegeReturn.setCreateTime(new Date());
			storegeReturn.setCreateUserName(currentUser.getUserName());
			storegeReturn.setIsDeleted(0);
			//在入库表中也新增一条数据
			ErpStoregeIn erpStoregeIn = new ErpStoregeIn();
			erpStoregeIn.setStoregeId(storegeReturn.getStoregeId());//仓库ID
			erpStoregeIn.setProductId(storegeReturn.getProductId());//产品ID
			erpStoregeIn.setPrice(storegeReturn.getUnitPrice());//单价
			erpStoregeIn.setStandard(standard);//规格
			erpStoregeIn.setQuantity(storegeReturn.getReturnNumber());//数量
			erpStoregeIn.setAmount(storegeReturn.getTotalCost());//总金额
			erpStoregeIn.setCardStart(storegeReturn.getCardStart());//卡号开始
			erpStoregeIn.setCardEnd(storegeReturn.getCardEnd());//卡号结束
			erpStoregeIn.setUserCardNum(storegeReturn.getCardStart());//真正可用的卡号
			erpStoregeIn.setRemark(storegeReturn.getRemark());//备注
			//成本  总成本需要后台计算
//			storegeReturn.setCost(cost);
//			storegeReturn.setTotalCost(totalCost);
			erpStoregeReturnService.save(storegeReturn);
			storegeInService.saveStroregeIn(erpStoregeIn, currentUser,0);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "closeCurrent");
		} catch (Exception e) {
			json.accumulate("statusCode", 300); 
			json.accumulate("message", "操作失败");
			log.error("addReturn fail--"+e);
		}
		renderJson(json);
	}

	public ErpStoregeReturn getStoregeReturn() {
		return storegeReturn;
	}

	public void setStoregeReturn(ErpStoregeReturn storegeReturn) {
		this.storegeReturn = storegeReturn;
	}
}
