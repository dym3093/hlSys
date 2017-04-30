package org.hpin.warehouse.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StaticMehtod;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.StoreApplication;
import org.hpin.warehouse.entity.StoreApplicationDetail;
import org.hpin.warehouse.entity.StoreApplyedCount;
import org.hpin.warehouse.entity.StoreDeliveryDetail;
import org.hpin.warehouse.entity.StoreProduce;
import org.hpin.warehouse.entity.StoreProduceDetail;
import org.hpin.warehouse.entity.StoreType;
import org.hpin.warehouse.entity.StoreWarehouseAll;
import org.hpin.warehouse.service.StoreApplicationService;
import org.hpin.warehouse.service.StoreDeliveryDetailService;
import org.hpin.warehouse.service.StoreProduceDetailService;
import org.hpin.warehouse.service.StoreProduceService;
import org.hpin.warehouse.service.StoreTypeService;
import org.hpin.warehouse.service.StoreWarehouseAllService;
import org.hpin.warehouse.service.StoreWarehouseService;


//http://192.168.1.15/hbs/warehouse/storeApplication!liststoreApplication.action
/**
 * 	@author chenqi
 * 	@since	2016年8月12日13:29:33
 *	基因物品库存发货
 */
@Namespace("/warehouse")
@Action("storeDelivery")
@Results({
	@Result(name = "liststoreDelivery", location = "/WEB-INF/warehouse/storeDelivery/liststoreDelivery.jsp"),
	@Result(name = "modifyDeliveryInfo", location = "/WEB-INF/warehouse/storeDelivery/modifyDeliveryInfo.jsp"),
	@Result(name = "addDeliveryInfo", location = "/WEB-INF/warehouse/storeDelivery/addDeliveryInfo.jsp"),
})
public class StoreDeliveryAction extends BaseAction{
	StoreApplicationService storeApplicationService = (StoreApplicationService) SpringTool.getBean(StoreApplicationService.class);
	StoreDeliveryDetailService deliveryService = (StoreDeliveryDetailService) SpringTool.getBean(StoreDeliveryDetailService.class);
	//库存service
    StoreWarehouseService service = (StoreWarehouseService)SpringTool.getBean(StoreWarehouseService.class);
    //调拨过程service
    StoreProduceService produceService = (StoreProduceService) SpringTool.getBean(StoreProduceService.class);
    //调拨过程明细service
    StoreProduceDetailService produceDetailService = (StoreProduceDetailService) SpringTool.getBean(StoreProduceDetailService.class);
    //库存总表service
    StoreWarehouseAllService allService = (StoreWarehouseAllService) SpringTool.getBean(StoreWarehouseAllService.class);
    //产品小类
    StoreTypeService typeService  = (StoreTypeService)SpringTool.getBean(StoreTypeService.class);
	private Logger log = Logger.getLogger(StoreDeliveryAction.class);
	
	/**
	 * 	总公司信息
	 */
	public void setOwedCompanySelected(){
		JSONObject json = new JSONObject();
		json.put("owedCompany", deliveryService.getOwedCompany());
		renderJson(json);
	}
	
	/**
	 * 	支公司信息
	 */
	public void setCompanySelected(){
		JSONObject json = new JSONObject();
		json.put("company", deliveryService.getCompany(HttpTool.getParameter("owedComapny")));
		renderJson(json);
	}
	
	public Page findByPage(Page page , Map paramsMap){
		paramsMap.put("order_createTime", "desc");
		storeApplicationService.findByPage(page, paramsMap);
		return page ;
	}
	
	public String liststoreDelivery(){
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		String status = HttpTool.getParameter("filter_and_status_LIKE_S");
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			findByPage(page, paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpTool.setAttribute("filter_and_status_EQ_S", status);
		return "liststoreDelivery";
	}

	/**
	 * 点击发货,进入发货页面
	 * @return
	 */
	public String modifyDeliveryInfo(){
		String id = HttpTool.getParameter("id");
		String batNo = HttpTool.getParameter("batNo");
		try {
			StoreApplication storeApplication = (StoreApplication)storeApplicationService.findById(id);
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<StoreApplyedCount> applyDetailsList= deliveryService.getApplyDetails(id,batNo);	//申请详细信息
			List<StoreDeliveryDetail> list = deliveryService.getDeliveryDetails2(page,id);		//库存信息
			HttpTool.setAttribute("storeApplication", storeApplication);
			HttpTool.setAttribute("applyDetailsList", applyDetailsList);
			HttpTool.setAttribute("list", list);
			HttpTool.setAttribute("batNo", batNo);
			HttpTool.setAttribute("id", id);
//			page.setResults(list);
		} catch (Exception e) {
			log.error("StoreApplicationAction toStoreProduceDetail", e);
		}
		return "modifyDeliveryInfo";
	}
	
	/**
	 * 	获取当前库存信息
	 */
	public void getCurrentCount(){
		JSONObject json = new JSONObject();
		String storeTypeId = HttpTool.getParameter("storeTypeId");
		String id = HttpTool.getParameter("id");
		int count = deliveryService.getStockNum(storeTypeId,id);
		json.put("count", count);
		renderJson(json);
	}
	
	/**
	 * @return 发货弹窗
	 */
	public String addDeliveryInfo(){
		try {
			HttpTool.setAttribute("batchNo", HttpTool.getParameter("batchNo"));	//申请单号
			HttpTool.setAttribute("id", HttpTool.getParameter("id"));			//仓库id
			HttpTool.setAttribute("typeCode", HttpTool.getParameter("typeCode"));//品类
			HttpTool.setAttribute("remark", HttpTool.getParameter("remark"));//品类
			HttpTool.setAttribute("wareHouse", java.net.URLDecoder.decode(HttpTool.getParameter("wareHouse"),"UTF-8"));//仓库名
			HttpTool.setAttribute("storeTypeId", HttpTool.getParameter("storeTypeId"));//小类ID
			HttpTool.setAttribute("flag", HttpTool.getParameter("flag"));//是否部分发货
			HttpTool.setAttribute("applyedId", HttpTool.getParameter("applyedId"));//申请信息的id
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "addDeliveryInfo";
	}
	
	/**
	 * 	获取已发货的数量
	 */
	public void getApplyedCount(){
		JSONObject json = new JSONObject();
		String batNo = HttpTool.getParameter("batNo");
		String name = HttpTool.getParameter("name");
		String applyedCount = deliveryService.getApplyedCount(name,batNo);
		json.put("count", applyedCount);
		renderJson(json);
	}
	
	/**
	 * @return 保存发货
	 */
	public void setStoreProduce(){
		JSONObject json = new JSONObject();
		JSONObject jsonObject = JSONObject.fromObject(HttpTool.getParameter("dataJson"));
		try {
			User user = (User)HttpTool.getSession().getAttribute("currentUser");
			String wareHouseId = jsonObject.getString("houseId");
			String wareHouse = jsonObject.getString("houseName");
			String storeTypeId = jsonObject.getString("storeTypeId");
			String flag = jsonObject.getString("flag");
			String batNo = jsonObject.getString("batNo");
			StoreProduce produce = new StoreProduce();
			StoreApplication application = deliveryService.getApplycation(batNo);	//申请单号的信息
			StoreWarehouseAll houseAll = deliveryService.getAllInfo(wareHouseId,storeTypeId);
			StoreType storeType = deliveryService.getTypeInfo(storeTypeId);
			
			//更新申请
			if(flag.equals("1")){//申请状态
				application.setStatus("2");
			}else{//申请状态1
				application.setStatus("1");
			}
			storeApplicationService.update(application);
			
			Integer count = Integer.valueOf(jsonObject.getString("expressNum"));
			Integer oldcount =houseAll.getCount();
			produce.setUseTime(application.getUseDate()); 						//使用时间
			produce.setAddress(application.getAddress());
			produce.setApplicationId(application.getId());
			produce.setOwncompannyId(application.getBannyCompanyId());
			produce.setOwncompannyName(application.getOwnedCompany());
			produce.setBannyCompannyId(application.getBannyCompanyId());				
			produce.setBannyCompannyName(application.getBannyCompanyName());
			produce.setProjectBusinessName(application.getRemark1()); 			//项目负责人
			produce.setProjectBelone(application.getRemark2());					//项目归属
			produce.setProjectCode(application.getRemark3()); 					//项目编码
			produce.setReceiveName(application.getReceiveName());				//收件人
			produce.setReceiveTel(application.getReceiveTel());					//联系电话
			produce.setRequireDetail(application.getRequires()); 				//需求说明
			produce.setRemark1(storeType.getId());
			produce.setRemark2(storeType.getName());
			
			produce.setRemark(jsonObject.getString("expressNum"));				//数量
			produce.setBasePrice(jsonObject.getDouble("basePrice"));
			produce.setEmsName(jsonObject.getString("expressComapny"));
			produce.setEmsNo(jsonObject.getString("expressNo"));
			produce.setEmsPrice(jsonObject.getDouble("epressPrice"));
			produce.setTotalPrice(jsonObject.getDouble("total"));
			produce.setWarehouseId(wareHouseId);
			produce.setWarehouseName(wareHouse);
			produce.setRemark3(batNo);   			//批次号
			
			produce.setBusinessId(user.getCreateUserId());
			produce.setBusinessName(user.getAccountName());
			produce.setType(1);				//0入库，1出库
			produce.setIsDeleted(0);
			produce.setCreateTime(new Date());
			produce.setCreateUserId(user.getId());
			produce.setCreateUserName(user.getUserName());
	//		produce.setCardStart(cardStart);
	//		produce.setCardEnd(cardEnd);
			produceService.save(produce);
			//调拨过程明细表
			StoreProduceDetail storeDetail=new StoreProduceDetail();
			storeDetail.setTypeBigCode(storeType.getRemark1());
			storeDetail.setTypeSmallCode(storeType.getId());
			storeDetail.setTypeSmallName(storeType.getName());
			storeDetail.setWarehouseId(wareHouseId);
			storeDetail.setWarehouseName(wareHouse);
			storeDetail.setCount(Integer.parseInt(produce.getRemark()));	//调拨个数
			storeDetail.setProduceId(produce.getId());
			storeDetail.setCreateTime(new Date());
			storeDetail.setCreateUserId(user.getId());
			storeDetail.setIsDeleted(0);
			produceDetailService.save(storeDetail);
			
			//库存表
			houseAll.setWarehouseId(wareHouseId);
			houseAll.setWarehouseName(wareHouse);
			houseAll.setTypeBigCode(storeType.getRemark1());
			houseAll.setTypeSmallCode(storeType.getId());
			houseAll.setTypeSmallName(storeType.getName());
			houseAll.setCount(oldcount-count);
			Integer oldPullCount = houseAll.getCountPull();
			houseAll.setCountPull(oldPullCount+Integer.parseInt(produce.getRemark()));//设置出库量
			houseAll.setUpdateUserId(user.getId());
			houseAll.setUpdateTime(new Date());
			houseAll.setIsDeleted(0);
			allService.update(houseAll);
			
			//小类
			storeType.setNumPull(houseAll.getCountPull());
			storeType.setNum(houseAll.getCount());
			storeType.setUpdateTime(new Date());
			storeType.setUpdateUserId(user.getUpdateUserId());
			storeType.setUpdateUserName(user.getAccountName());
			typeService.update(storeType);
			HttpTool.setAttribute("id", jsonObject.getString("applyedId"));
			HttpTool.setAttribute("batNo", jsonObject.getString("batNo"));
			json.put("count", 1);
		} catch (Exception e) {
			log.error("setStoreProduce(保存出库信息)",e);
			json.put("count", 0);
		}
		renderJson(json);
	}
	
}
