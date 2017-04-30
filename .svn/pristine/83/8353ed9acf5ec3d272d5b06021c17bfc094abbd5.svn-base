package org.hpin.warehouse.web;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.AjaxCheckCode.service.AjaxCheckCodeService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.ReflectionUtils;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.*;
import org.hpin.warehouse.mail.MailSenderInfo;
import org.hpin.warehouse.mail.SimpleMailSender;
import org.hpin.warehouse.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Namespace("/warehouse")
@Action("warehouse")

@Results( { 
	@Result(name = "listStoreWarehouse", location = "/WEB-INF/warehouse/listStoreWarehouse.jsp"),
	@Result(name = "listPull", location = "/WEB-INF/warehouse/report/listPull.jsp"),
	@Result(name = "listPush", location = "/WEB-INF/warehouse/report/listPush.jsp"),
	@Result(name = "listStoreType", location = "/WEB-INF/warehouse/report/listStoreType.jsp"),
	@Result(name = "toModifyStoreWarehouseById", location = "/WEB-INF/warehouse/modifyStoreWarehouse.jsp"), 
	@Result(name = "lookUpCustomerAll", location = "/WEB-INF/business/customer/lookUpCustomerAll.jsp"), 
	@Result(name = "browStoreWarehouse", location = "/WEB-INF/warehouse/listStoreWareType.jsp"), 
	@Result(name = "browStoreWarehouseAll", location = "/WEB-INF/warehouse/listStoreWarehouseAll.jsp"),
	@Result(name = "browPushStoreProduceByTypeId", location = "/WEB-INF/warehouse/listPushStoreProduce.jsp"),
	@Result(name = "browPullStoreProduceByTypeId", location = "/WEB-INF/warehouse/listPullStoreProduce.jsp"),
	@Result(name = "addWarehouse", location = "/WEB-INF/warehouse/addStoreWarehouse.jsp"), 
	@Result(name = "browStoreAll", location = "/WEB-INF/warehouse/browStoreAll.jsp"), 
	@Result(name = "browStoreProduce", location = "/WEB-INF/warehouse/browStoreProduce.jsp"), 
	@Result(name = "browProduceDetail", location = "/WEB-INF/warehouse/browProduceDetail.jsp"), 
	@Result(name = "toPushGoods", location = "/WEB-INF/warehouse/toPushGoods.jsp"),
	@Result(name = "toPushGoodsByTypeId", location = "/WEB-INF/warehouse/pushGoods.jsp"),
	@Result(name = "toPullGoodsByTypeId", location = "/WEB-INF/warehouse/pullGoods.jsp"),
	@Result(name = "toAddStoreType", location = "/WEB-INF/warehouse/addStoreWareType.jsp"),
	@Result(name = "modifyPushProduce", location = "/WEB-INF/warehouse/modifyPushProduce.jsp"),
	@Result(name = "modifyPullProduce", location = "/WEB-INF/warehouse/modifyPullProduce.jsp"),
	@Result(name = "stockByTypeId", location = "/WEB-INF/warehouse/listSurplusStoreProduce.jsp"),
	@Result(name = "showExpressInfo", location = "/WEB-INF/warehouse/showExpressInfo.jsp"), 
	@Result(name = "inWarehouse", location = "/WEB-INF/warehouse/inWarehouse.jsp"), 
	@Result(name = "viewDetailed", location = "/WEB-INF/warehouse/viewDetailed.jsp"), 
	@Result(name = "toModifyTypeById", location = "/WEB-INF/warehouse/modifyStoreWareType.jsp") })
public class StoreWarehouseAction extends BaseAction{
	
	private Logger log = Logger.getLogger(StoreWarehouseAction.class);
	
	//库存service
    StoreWarehouseService service = (StoreWarehouseService)SpringTool.getBean(StoreWarehouseService.class);
    //调拨过程service
    StoreProduceService produceService = (StoreProduceService) SpringTool.getBean(StoreProduceService.class);
   //调拨过程明细service
    StoreProduceDetailService produceDetailService = (StoreProduceDetailService) SpringTool.getBean(StoreProduceDetailService.class);
    //库存总表service
    StoreWarehouseAllService allService = (StoreWarehouseAllService) SpringTool.getBean(StoreWarehouseAllService.class);
	private UserService userService = (UserService) SpringTool.getBean(UserService.class);
	//申请
	StoreApplicationService storeApplicationService = (StoreApplicationService) SpringTool.getBean(StoreApplicationService.class);
    //库存entity
    StoreWarehouse stroeWarehouse;
    //调拨过程明细
    StoreProduceDetail storeDetail;
    //产量总表
    StoreWarehouseAll storeAll;
    //调拨过程主表
    StoreProduce produce;
    //产品小类entity
    StoreType stroeType;
    //校验是否重复
    AjaxCheckCodeService ajaxCheckCodeService = (AjaxCheckCodeService)SpringTool.getBean(AjaxCheckCodeService.class);
    //产品小类
    StoreTypeService typeService  = (StoreTypeService)SpringTool.getBean(StoreTypeService.class);
    
    @Autowired
    private ErpStoregeInService erpStoregeInService; //入库service
    
    
    
    /**
     * 验证名称重复问题;
     * create by henry.xu 20161019;
     */
    public void validNameRepeat() {
    	JSONObject json = new JSONObject();
    	json.put("result", this.service.validNameRepeat(id, HttpTool.getParameter("name", "")));
    	renderJson(json);
    }
    
    /**
     * create by henry.xu 20161019
     * 明细查看;
     * @return
     */
    @SuppressWarnings("rawtypes")
	public String viewDetailed() {
    	
    	//仓库信息;
    	stroeWarehouse = (StoreWarehouse)service.findById(id);
    	
    	//查询该仓库下对应入库明细记录;
    	try {
    		if(params == null) {
    			params = new HashMap<String, String>();
    		}
    		params.put("id", id);
    		page = new Page(HttpTool.getPageNum(),10);
    		
    	} catch (Exception e) {
    		log.error("仓库明细查看page分页错误");
    	}
		
    	this.erpStoregeInService.findStoregeInByParams(page, params);
    	
    	return "viewDetailed";
    }
    
    /**
    * 仓库列表
    * @return
    * @throws ParseException
    */
	@SuppressWarnings("rawtypes")
	public String listStoreWarehouse() {
		try {
			
			if(params == null) {
				params = new HashMap<String, String>();
			}
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			
			User userinfo = getUserInfo();
			String accountName = userinfo.getAccountName();
			//登陆人过滤,如果为admin不加入userid条件;
			if(!"admin".equals(accountName) && !"linhui".equals(accountName)) {
				params.put("currentUserId", getUserInfo().getId());
			}
			service.findStoreWarehouseByPage(page, params, true);
		} catch(Exception e) {
			log.error("仓库列表查询错误", e);
		}
		
		return "listStoreWarehouse";
	}
	/**
	 * 产品查询
	 * @return
	 * @throws ParseException
	 */
	public String listStoreType() throws ParseException{
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		searchMap.put("order_createTime", "desc");
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		List<StoreType> lists=typeService.findByPage(page,searchMap);
		List<StoreType> listsnew = new ArrayList<StoreType>();
		if(lists.size()>0){
			for (int i = 0; i < lists.size(); i++) {
				StoreType t = lists.get(i);
				List<StoreWarehouseAll> listalls =(List<StoreWarehouseAll>) allService.findByTypeId(t.getId());
				if(listalls.size()>0){
				StoreWarehouse a =  (StoreWarehouse) service.findById(t.getRemark());
				t.setRemark2(a.getProvince());
				t.setRemark3(a.getCity());
				t.setDeleteUserName(a.getName());
				listsnew.add(t);
			}
			}
		}
		page.setResults(listsnew) ;
		return "listStoreType";
	}
//	public String deletePull(){
//		produce = (StoreProduce) produceService.findById(id);
//		//本次出库储量
//		Integer oldCount = produce.get
//		return "";
//	}
	/**
	 * 出库查询
	 * @return
	 * @throws ParseException
	 */
	public String listPull() throws ParseException{
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		searchMap.put("filter_and_type_EQ_I", 1);
		searchMap.put("order_createTime", "desc");
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		List<StoreProduce> produceList=produceService.findByPage(page,searchMap);
		page.setResults(produceList) ;
		return "listPull";
	}
	/**
	 * 入库查询
	 * @return
	 * @throws ParseException
	 */
	public String listPush() throws ParseException{
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		searchMap.put("filter_and_type_EQ_I", 0);
		searchMap.put("order_createTime", "desc");
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		List<StoreWarehouse> storewarehouseList=produceService.findByPage(page,searchMap);
		page.setResults(storewarehouseList) ;
		return "listPush";
	}
	/**
	 * 跳转到小类修改页面
	 * @return
	 */
	public String toAddStoreType(){
		String warehouseId = HttpTool.getParameter("stroeWarehouseId");
		stroeWarehouse=(StoreWarehouse) service.findById(warehouseId);
		return "toAddStoreType";
	}
	/**
	 * 跳转到仓库修改页面
	 * @return
	 */
	public String toModifyStoreWarehouseById(){
		stroeWarehouse=(StoreWarehouse) service.findById(id);
		return "toModifyStoreWarehouseById";
	}
	/**
	 * 删除小类
	 * @return
	 */
	public String delByTypeId(){
		JSONObject json = new JSONObject();
		try {
			StoreType stroeType1=(StoreType) typeService.findById(id);
			String forwardUrl="../warehouse/warehouse!browStoreWarehouse.action?id="+stroeType1.getRemark().trim();
			stroeType1.setIsDeleted(1);
			stroeType1.setDeleteTime(new Date());
			typeService.update(stroeType1);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", stroeType1.getRemark());
			json.accumulate("callbackType", "forward");
			json.accumulate("forwardUrl", forwardUrl);
			json.accumulate("confirmMsg", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
			log.error("delByTypeId is error",e);
		}
		renderJson(json);
		return null;
	}
	/**
	 * 修改小类
	 * param:仓库ID
	 */
	public String modifyType(){
	  JSONObject json = new JSONObject();
	  StoreType stroeType1=(StoreType) typeService.findById(id);
	  ReflectionUtils.copyPropertiesForHasValue(stroeType1, stroeType);
	  stroeType1.setUpdateTime(new Date());
	  User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
	  stroeType1.setUpdateUserId(currentUser.getId());
	  stroeType1.setUpdateUserName(currentUser.getUserName());
	  try {
		typeService.update(stroeType1);
		json.accumulate("statusCode", 200);
		json.accumulate("message", "操作成功");
		String forwardUrl="../warehouse/warehouse!browStoreWarehouse.action?id="+stroeType1.getRemark()+"&navTabId="+"menu_40289b6a523eec2c01523f06d2210004";
		json.accumulate("callbackType", "forward");
		json.accumulate("forwardUrl",forwardUrl);
		/*json.accumulate("navTabId", super.navTabId);
		json.accumulate("callbackType", "closeCurrent");*/
	  } catch (Exception e) {
		json.accumulate("statusCode", 300);
		json.accumulate("message", "操作失败");
		log.error("modifyType",e);
	}
	renderJson(json);
	return null;
	}
	/**
	 * 入库跳转页面
	 * @return
	 * @throws ParseException 
	 */
	public String toPushGoods() throws ParseException{
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_remark_EQ_S", id);
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		searchMap.put("order_createTime", "desc");
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		stroeWarehouse=(StoreWarehouse) service.findById(id);
		List<StoreType> storeTypeList=typeService.findByPage(page,searchMap);
		page.setResults(storeTypeList);
		HttpTool.setAttribute("storeDetail", storeDetail);
		HttpTool.setAttribute("stroeWarehouse", stroeWarehouse);
		return "toPushGoods";
	}
	/**
	 * 入库跳转页面
	 * 根据小类ID入库
	 * @return
	 */
	public String toPushGoodsByTypeId(){
		stroeType=(StoreType) typeService.findById(id);
		String warehouseId = stroeType.getRemark();
		stroeWarehouse=(StoreWarehouse) service.findById(warehouseId);
		List<StoreWarehouseAll> all=(List<StoreWarehouseAll>) allService.findByTypeIdAndStoreId(warehouseId,id);
		if(all.size()>0){
			storeAll=all.get(0);
		}else{
			storeAll= new StoreWarehouseAll();
			storeAll.setCount(0);
		}
		String remark1= stroeType.getRemark1();
		log.info("toPushGoodsByTypeId remark1 : "+remark1);
		HttpTool.setAttribute("remark1", remark1);
		return "toPushGoodsByTypeId";
	}
	/**
	 * 出库跳转页面
	 * @return
	 */
	public String toPullGoodsByTypeId(){
		stroeType=(StoreType) typeService.findById(id);	//包含传到页面的cardStart
		String warehouseId = stroeType.getRemark();
		stroeWarehouse=(StoreWarehouse) service.findById(warehouseId);
		List<StoreWarehouseAll> all=(List<StoreWarehouseAll>) allService.findByTypeIdAndStoreId(warehouseId,id);
		if(all.size()>0){
			storeAll=all.get(0);
		}else{
			storeAll= new StoreWarehouseAll();
			storeAll.setCount(0);
		}//获取最新入库批次的价格作为当前出库价格
		List<StoreProduceDetail> details=produceDetailService.findByTypeIdAndStoreId(stroeType.getId(),stroeWarehouse.getId());
		if(details.size()>0){
			storeDetail=details.get(0);
		}else{
			storeDetail=new StoreProduceDetail();
		}
		String remark1= stroeType.getRemark1();
		log.info("toPullGoodsByTypeId remark1 : "+remark1);
		HttpTool.setAttribute("remark1", remark1);
		return "toPullGoodsByTypeId";
	}
	/**
	 * 派发按货物小类与仓库ID
	 * @return
	 */
	/*public String pullGoods(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		stroeType=(StoreType) typeService.findById(stroeType.getId());
		stroeWarehouse=(StoreWarehouse) service.findById(stroeType.getRemark());
		List alllista = allService.findByTypeIdAndStoreId(stroeWarehouse.getId(), stroeType.getId());
		Integer newCount=storeDetail.getCount();
		storeAll=(StoreWarehouseAll) alllista.get(0);
		if(newCount>storeAll.getCount()){
			json.accumulate("statusCode", 300);
			json.accumulate("message", "库存不足");
		}else{
			storeAll.setCount(storeAll.getCount()-newCount);
			storeAll.setUpdateTime(new Date());
			storeAll.setUpdateUserId(currentUser.getId());
			allService.update(storeAll);
		if(null!=produce){
			produce.setCreateTime(new Date());
			produce.setCreateUserId(currentUser.getId());
			produce.setCreateUserName(currentUser.getUserName());
			produce.setWarehouseId(stroeWarehouse.getId());
			produce.setWarehouseName(stroeWarehouse.getName());
			produce.setIsDeleted(0);
			produce.setType(1);
			produceService.save(produce);
			
			storeDetail=new StoreProduceDetail();
			storeDetail.setProduceId(produce.getId());
			storeDetail.setTypeBigCode(stroeType.getRemark1());
			storeDetail.setTypeSmallCode(stroeType.getId());
			storeDetail.setTypeSmallName(stroeType.getName());
			storeDetail.setWarehouseName(stroeWarehouse.getName());
			storeDetail.setWarehouseId(stroeWarehouse.getId());
			storeDetail.setCreateUserId(currentUser.getId());
			storeDetail.setCreateTime(new Date());
			storeDetail.setIsDeleted(0);
			produceDetailService.save(storeDetail);
			}
			json.accumulate("statusCode", 200);
			json.accumulate("message", "增加成功");
			String forwardUrl="../warehouse/warehouse!browStoreWarehouse.action?id="+stroeWarehouse.getId();
			json.accumulate("callbackType", "forward");
			json.accumulate("forwardUrl",forwardUrl);
		}
		renderJson(json);
		return null;
	}*/
	/**
	 * 入库
	 * @return
	 */
	public String pushGoods(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		stroeType=(StoreType) typeService.findById(stroeType.getId());
		stroeWarehouse=(StoreWarehouse) service.findById(stroeType.getRemark());
			try {
				if(null!=storeDetail){
				//调拨主表
				produce=new StoreProduce();
				produce.setCreateUserId(currentUser.getId());
				produce.setCreateTime(new Date());
				produce.setCreateUserName(currentUser.getUserName());
				produce.setWarehouseId(stroeWarehouse.getId());
				produce.setWarehouseName(stroeWarehouse.getName());
				produce.setRemark1(stroeType.getId());//设置产品小类ID
				produce.setType(0);//0为入库1为出库
				produce.setIsDeleted(0);
				//详细单价如果为空 则赋值0
				if(storeDetail.getPrice()!=null){
					produce.setBasePrice(storeDetail.getPrice());
				}else{
					produce.setBasePrice(0.00);
				}//如果详细总价为空 则赋值为0
				if(storeDetail.getTotalPrice()!=null){
					produce.setTotalPrice(storeDetail.getTotalPrice());
				}else{
					produce.setTotalPrice(0.00);
				}//如果数量为空 则赋值数量为0
				if(storeDetail.getCount()>0){
					produce.setRemark(storeDetail.getCount().toString());
				}else{
					produce.setRemark("0");
				}
				produce.setEventNos(storeDetail.getRemark1());//卡号
				produce.setProjectBelone(storeDetail.getRemark1());//入库时项目归属 
				produce.setProjectCode(storeDetail.getRemark());//入库时项目名称字段
				produce.setBusinessName(stroeType.getStandards());
				produce.setBusinessId(stroeType.getDescripe());
				produceService.save(produce);
				//调拨子表
				storeDetail.setProduceId(produce.getId());
				storeDetail.setTypeBigCode(stroeType.getRemark1());
				storeDetail.setTypeSmallCode(stroeType.getId());
				storeDetail.setTypeSmallName(stroeType.getName());
				storeDetail.setWarehouseName(stroeWarehouse.getName());
				storeDetail.setWarehouseId(stroeWarehouse.getId());
				storeDetail.setCreateUserId(currentUser.getId());
				//把调拨主表的卡号开始 复制到子表卡号开始字段
				if(null!=storeDetail.getRemark2()){
					produce.setCardStart(storeDetail.getRemark2());
					stroeType.setCardStart(storeDetail.getRemark2());
				}
				//把调拨主表的卡号截至字段 复制到子表卡号截止字段
				if(null!=storeDetail.getRemark3()){
					produce.setCardEnd(storeDetail.getRemark3());
					stroeType.setCardEnd(storeDetail.getRemark3());
				}
				storeDetail.setCreateTime(new Date());
				storeDetail.setIsDeleted(0);
				produceDetailService.save(storeDetail);
				//仓库全部库存表
				List alllista = allService.findByTypeIdAndStoreId(stroeWarehouse.getId(), stroeType.getId());
				Integer newCount=storeDetail.getCount();
				Integer newPushCount=Integer.parseInt(produce.getRemark());//本次入库个数
				if(alllista.size()>0){
					storeAll=(StoreWarehouseAll) alllista.get(0);
					storeAll.setUpdateTime(new Date());
					storeAll.setUpdateUserId(currentUser.getId());
					storeAll.setCount(storeAll.getCount()+newCount);
					if(null==storeAll.getCountPush()||storeAll.getCountPush()==0){
						storeAll.setCountPush(newPushCount);
						stroeType.setNumPush(newPushCount);
					}else{
						storeAll.setCountPush(storeAll.getCountPush()+newPushCount);
						stroeType.setNumPush(newPushCount+stroeType.getNumPush());
					}
					allService.update(storeAll);
				}else{
					storeAll=new StoreWarehouseAll();
					storeAll.setWarehouseId(stroeWarehouse.getId());
					storeAll.setWarehouseName(stroeWarehouse.getName());
					storeAll.setTypeBigCode(storeDetail.getTypeBigCode());
					storeAll.setTypeSmallCode(storeDetail.getTypeSmallCode());
					storeAll.setTypeSmallName(storeDetail.getTypeSmallName());
					storeAll.setCount(storeDetail.getCount());
					storeAll.setCreateTime(new Date());
					storeAll.setCreateUserId(currentUser.getId());
					storeAll.setIsDeleted(0);
					storeAll.setCountPush(newPushCount);
					allService.save(storeAll);
					typeService.save(stroeType);
				}
				json.accumulate("statusCode", 200);
				json.accumulate("message", "增加成功");
				String forwardUrl="../warehouse/warehouse!browStoreWarehouse.action?id="+stroeWarehouse.getId();
				json.accumulate("callbackType", "forward");
				json.accumulate("forwardUrl",forwardUrl);
			} else{
				json.accumulate("statusCode", 300);
				json.accumulate("message", "操作失败");
			}
			}catch (Exception e) {
				json.accumulate("statusCode", 300);
				json.accumulate("message", "操作失败");
				log.error("pushGoods error",e);
		}
		renderJson(json);
		return null;
	}
	/**
	 * 添加产品小类
	 * param:产品大类ID，产品小类详细
	 */
	public String saveStoreType(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String isBtn=HttpTool.getParameter("isBtn");
		String navTabId=HttpTool.getParameter("navTabId");
		stroeWarehouse=(StoreWarehouse) service.findById(id);
		try {
				if(typeService.isRepeat(id, stroeType.getName().trim(), stroeType.getRemark1())){
					stroeType.setIsDeleted(0);
					stroeType.setRemark(id);
					stroeType.setRemark2(stroeWarehouse.getName());
					stroeType.setCreateTime(new Date());
					stroeType.setCreateUserName(currentUser.getUserName());
					typeService.save(stroeType);
					
					//添加新的小类同步初始化库存量
					storeAll =  new StoreWarehouseAll();
					storeAll.setWarehouseId(stroeType.getRemark());
					storeAll.setCount(0);
					storeAll.setCountPull(0);
					storeAll.setCountPush(0);
					storeAll.setTypeBigCode(stroeType.getRemark1());
					storeAll.setIsDeleted(0);
					storeAll.setCreateUserId(currentUser.getId());
					storeAll.setTypeSmallCode(stroeType.getId());
					allService.save(storeAll);
					
					json.accumulate("statusCode", 200);
					json.accumulate("message", "增加成功");
					json.accumulate("navTabId", navTabId);//20160105
					System.out.println(navTabId);
					if(isBtn.equals("2")){
						String forwardUrl="../warehouse/warehouse!browStoreWarehouse.action?id="+id;
						json.accumulate("callbackType", "forward");
						json.accumulate("forwardUrl",forwardUrl);
					}else{
						String forwardUrl="..//warehouse/warehouse!toAddStoreType.action?stroeWarehouseId="+id;
						json.accumulate("callbackType", "forward");
						json.accumulate("forwardUrl",forwardUrl);
					}

				}else{
					json.accumulate("statusCode", 300);
					json.accumulate("message", "小类名称已存在！");
				}
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
			log.error("saveStoreType error",e);
		}
		System.out.println(json);
		renderJson(json);
		return null;
	}
	/**
	 * 导出使用push
	 * @param page
	 * @param paramsMap
	 * @return
	 */
	public Page findPushByPage(Page page,Map paramsMap){
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		paramsMap.put("filter_and_type_EQ_I", 0);
		paramsMap.put("order_createTime", "desc");
		produceService.findByPage(page, paramsMap);
		return page;
	}
	/**
	 * 导出使用pull
	 * @param page
	 * @param paramsMap
	 * @return
	 */
	public Page findPullByPage(Page page,Map paramsMap){
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		paramsMap.put("filter_and_type_EQ_I", 1);
		paramsMap.put("order_createTime", "desc");
		produceService.findByPage(page, paramsMap);
		return page;
	}
	public Page findTypesByPage(Page page , Map paramsMap){
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		paramsMap.put("order_createTime", "desc");
		typeService.findByPage(page , paramsMap) ;
		return page ;
	}
	
	/**
	 * modified by henry.xu 20161019
	 * 导出调用该方法;
	 */
	@SuppressWarnings("rawtypes")
	public Page findByPage(Page page , Map paramsMap){
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", HttpTool.getParameter("params.name", ""));
		map.put("director", HttpTool.getParameter("params.director", ""));
		map.put("tel", HttpTool.getParameter("params.tel", ""));
		map.put("province", HttpTool.getParameter("params.province", ""));
		map.put("city", HttpTool.getParameter("params.city", ""));
		User userinfo = getUserInfo();
		String accountName = userinfo.getAccountName();
		//登陆人过滤,如果为admin不加入userid条件;
		if(!"admin".equals(accountName)) {
			map.put("currentUserId", getUserInfo().getId());
		}
		service.findStoreWarehouseByPage(page, map, false);
		return page ;
	}
	
	/**
	 * @客户列表页
	 */
	public String lookUpWarehouse() throws ParseException {
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		Map paramsMap = buildSearch();
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		paramsMap.put("order_createTime", "desc");
		List<StoreWarehouse> storewarehouseList= service.findByPage(page, paramsMap);
		page.setResults(storewarehouseList);
		return "lookUpWarehouse";
	}
	/**
	 * @所有仓库信息
	 */
	public String lookUpWarehouseAll() throws ParseException {
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		Map paramsMap = buildSearch();
		paramsMap.put("order_createTime", "desc");
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		List<StoreWarehouse> storewarehouseList= service.findByPage(page, paramsMap);
		page.setResults(storewarehouseList);
		return "lookUpWarehouseAll";
	}

	/**
	 * @进入添加仓库页面
	 */
	public String addWarehouse() {
		return "addWarehouse";
	}
	
	/**
	 * @添加供应商信息
	 */
	public String saveWarehouse(){
		JSONObject json = new JSONObject();
		try {
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			stroeWarehouse.setCreateUserId(currentUser.getId());
			stroeWarehouse.setCreateTime(new Date());
			stroeWarehouse.setIsDeleted(0);
			boolean flat = ajaxCheckCodeService.findByCode("StoreWarehouse", "name", stroeWarehouse.getName());
			if(flat){
				json.accumulate("statusCode", 300);
				json.accumulate("message", "名称已存在！");
			}else{
				service.save(stroeWarehouse);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "操作成功");
			}
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "closeCurrent");
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
			log.error("saveWarehouse error",e);
		}
		renderJson(json);
		return null;
	}
	/**
	 * 出库
	 * @return
	 */
	public String pull(){
		String isBtn=HttpTool.getParameter("isBtn");
		JSONObject json = new JSONObject();
		try {
			StoreType stroeType1=(StoreType) typeService.findById(id);
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			stroeWarehouse=(StoreWarehouse) service.findById(stroeType1.getRemark());
			List alllists = allService.findByTypeIdAndStoreId(stroeWarehouse.getId(), stroeType1.getId());//库存量判断
			if(alllists.size()>0){//判断是否有库存信息
				storeAll=(StoreWarehouseAll) alllists.get(0);
				Integer oldcount=storeAll.getCount();//初始库存量
				Integer count = Integer.parseInt(produce.getRemark());//希望调拨数量
				if(oldcount>=count){//如果库存数量够
					String applicationId=produce.getApplicationId();
					StoreApplication storeApplication=(StoreApplication)storeApplicationService.findById(applicationId);
					String mes;
					if(isBtn.equals("2")){//申请状态
						storeApplication.setStatus("2");
						mes="已发货，状态完成";
					}else{//申请状态1
						storeApplication.setStatus("1");
						mes="已发货，状态处理中";
					}
					storeApplicationService.update(storeApplication);
					
					//获取业务员邮箱
					String createUserName=produce.getBusinessName();
					User u=userService.getUserByCreateName(createUserName);
					String toEmail=u.getEmail();
					
					//调拨过程主表
					produce.setCreateTime(new Date());
					produce.setCreateUserId(currentUser.getId());
					produce.setCreateUserName(currentUser.getUserName());
					produce.setWarehouseId(stroeWarehouse.getId());
					produce.setWarehouseName(stroeWarehouse.getName());
					produce.setType(1);
					produce.setIsDeleted(0);
					produce.setRemark1(stroeType1.getId());
					produce.setRemark2(stroeType1.getName());
					produceService.save(produce);
					//调拨过程明细表
					storeDetail=new StoreProduceDetail();
					storeDetail.setTypeBigCode(stroeType1.getRemark1());
					storeDetail.setTypeSmallCode(stroeType1.getId());
					storeDetail.setTypeSmallName(stroeType1.getName());
					storeDetail.setWarehouseId(stroeWarehouse.getId());
					storeDetail.setWarehouseName(stroeWarehouse.getName());
					storeDetail.setCount(Integer.parseInt(produce.getRemark()));//调拨个数
					storeDetail.setProduceId(produce.getId());
					storeDetail.setCreateTime(new Date());
					if(null!=produce.getCardStart()){
						//卡号开始
						storeDetail.setRemark2(produce.getCardStart());
					}
					if(null!=produce.getCardEnd()){
						//卡号截至
						storeDetail.setRemark3(produce.getCardEnd());
					}
					storeDetail.setCreateUserId(currentUser.getId());
					storeDetail.setIsDeleted(0);
					produceDetailService.save(storeDetail);
					//库存表
					storeAll.setWarehouseId(stroeWarehouse.getId());
					storeAll.setWarehouseName(stroeWarehouse.getName());
					storeAll.setTypeBigCode(stroeType1.getRemark1());
					storeAll.setTypeSmallCode(stroeType1.getId());
					storeAll.setTypeSmallName(stroeType1.getName());
					storeAll.setCount(oldcount-count);
					Integer oldPullCount = storeAll.getCountPull();
					storeAll.setCountPull(oldPullCount+Integer.parseInt(produce.getRemark()));//设置出库量
					storeAll.setCreateTime(new Date());
					storeAll.setCreateUserId(currentUser.getId());
					storeAll.setIsDeleted(0);
					allService.update(storeAll);
					stroeType1.setNumPull(storeAll.getCountPull());
					stroeType1.setNum(storeAll.getCount());
					typeService.update(stroeType1);
					
/***************************发送邮件*******************************************/
					if(StrUtils.isNullOrBlank(currentUser.getEmail())){
					MailSenderInfo mailInfo = new MailSenderInfo();
					mailInfo.setMailServerHost("smtp.exmail.qq.com");
					mailInfo.setMailServerPort("25");
					mailInfo.setValidate(true);
					// 邮箱用户名
					
					/*String currentMail=currentUser.getEmail();//当前用户邮箱
					String currentMailPwd=currentUser.getCtiPassword();*/
					//使用公共邮箱
					String currentMail= "gene@healthlink.cn";
					String currentMailPwd= "Yue123.com";
					
					mailInfo.setUserName(currentMail);
					// 邮箱密码
					mailInfo.setPassword(currentMailPwd);
					//当事人邮箱
					String senderMail = currentUser.getEmail();
					// 发件人邮箱
					mailInfo.setFromAddress(senderMail);
					// 收件人邮箱
					mailInfo.setToAddress(toEmail);
					
					String compannyName=produce.getBannyCompannyName();//公司名称
					String remark= produce.getRemark();//数量
					String requireDetail=produce.getRequireDetail();//需求
					//项目编码
					String projectCode=produce.getProjectCode();
					// 邮件标题
					mailInfo.setSubject(compannyName+"基因项目发货邮件");
					// 邮件内容
					StringBuffer buffer = new StringBuffer();
					buffer.append("申请编码："+storeApplication.getBatNo()+"\n");
					buffer.append("项目编码："+projectCode+"\n");
					buffer.append("需求信息："+requireDetail+"\n");
					buffer.append("数        量："+remark+"\n");
					buffer.append(mes);
					buffer.append("链接网址：http://gene.healthlink.cn\n");
					mailInfo.setContent(buffer.toString());
					// 发送邮件
					SimpleMailSender sms = new SimpleMailSender();
					// 发送文体格式
					sms.sendTextMail(mailInfo);
					// 发送html格式
					SimpleMailSender.sendHtmlMail(mailInfo);
					System.out.println("邮件发送完毕");
					}
/********************邮件发送结束**************************************************/
					//跳转页面
					String forwardUrl="../warehouse/warehouse!browStoreWarehouse.action?id="+stroeType1.getRemark().trim();
					json.accumulate("statusCode", 200);
					json.accumulate("message", "操作成功");
					json.accumulate("navTabId", stroeType1.getRemark());
					json.accumulate("callbackType", "forward");
					json.accumulate("forwardUrl", forwardUrl);
					json.accumulate("confirmMsg", "");
					//产品小类 修改明细
				}else{
					json.accumulate("statusCode", 300);
					json.accumulate("message", "库存不足");
				}
			}else{
				json.accumulate("statusCode", 300);
				json.accumulate("message", "操作失败");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
			log.error("pull is error",e);
		}
		renderJson(json);
		return null;
	}
	
	
	
	
	/**
	 * @throws ParseException 
	 * @查看仓库产品分类信息
	 */
	public String browStoreWarehouse() throws ParseException{
		stroeWarehouse = (StoreWarehouse)service.findById(id);
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_remark_EQ_S", id);
		searchMap.put("order_remark1", "asc");   
		searchMap.put("order_createTime", "desc");
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findTypesByPage(page, searchMap);
		List<StoreType> lists = page.getResults();
		List<StoreType> listsnew = new ArrayList<StoreType>();
		if(lists.size()>0){
			for (int i = 0; i < lists.size(); i++) {
				StoreType t = lists.get(i);
				List<StoreWarehouseAll> listalls =(List<StoreWarehouseAll>) allService.findByTypeId(t.getId());
				if(listalls.size()>0){
				StoreWarehouseAll a =  (StoreWarehouseAll) allService.findByTypeId(t.getId()).get(0);
				Integer counttype = a.getCount();
				t.setNum(counttype);
				if(a.getCountPull()==null||a.getCountPull()==0){
					t.setNumPull(0);
				}else{
					t.setNumPull(a.getCountPull());
				}
				if(a.getCountPush()==null||a.getCountPush()==0){
					t.setNumPush(a.getCountPush());
				}
				}else{
					t.setNum(0);
					t.setNumPull(0);
					t.setNumPush(0);
				}
				listsnew.add(t);
			}
		}
		page.setResults(listsnew);
		return "browStoreWarehouse";
	}
	
	
	public String browStoreWarehouseAll() throws ParseException{
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_warehouseId_EQ_S", id);
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		searchMap.put("order_createTime", "desc");
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		allService.findByPage(page, searchMap);
		return "browStoreWarehouseAll";
	}
	/**
	 * @修改客户信息前查询
	 */
	public String toModifyTypeById(){
		String wareId = HttpTool.getParameter("wareId");
		stroeWarehouse = (StoreWarehouse) service.findById(wareId);
		stroeType=(StoreType) typeService.findById(id);
		return "toModifyTypeById";
	}
	
	/**
	 * @删除供应商
	 * @throws IOException
	 */
	public String deleteWarehouse() throws IOException{
		JSONObject json = new JSONObject();
		try {
			User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
			String[] id = ids.replaceAll(" ", "").split(",");
			service.deleteWareHouse(id, currentUser.getAccountName(),new Date());
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "");
			json.accumulate("forwardUrl", "");
			json.accumulate("confirmMsg", "");
		} catch (Exception e) {
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作失败");
			log.error("deleteWarehouse error",e);
		}
		renderJson(json);
		return null;
	}
	/**
	 * @修改客户信息
	 */
	public String modifyWarehouse() throws IOException {
		JSONObject json = new JSONObject();
		try {
			User currentUser = getUserInfo();
			//modified by henry.xu 20161027
			StoreWarehouse old = (StoreWarehouse) this.service.findById(stroeWarehouse.getId());
			old.setUpdateTime(new Date());
			old.setUpdateUserId(currentUser.getAccountName());
			old.setCity(stroeWarehouse.getCity());
			old.setDirector(stroeWarehouse.getDirector());
			old.setProvince(stroeWarehouse.getProvince());
			old.setTel(stroeWarehouse.getTel());
			old.setName(stroeWarehouse.getName());
			old.setRemark(stroeWarehouse.getRemark());
			old.setRemark1(stroeWarehouse.getRemark1());
			old.setRemark2(stroeWarehouse.getRemark2());
			old.setRemark3(stroeWarehouse.getRemark3());
			old.setAddress(stroeWarehouse.getAddress());
			
			service.update(old);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "closeCurrent");
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
			log.error("modifyWarehouse error",e);
		}
		renderJson(json);
		return null;
	}
	/**
	 * 查询所有仓库
	 * @return
	 * @throws Exception
	 */
	public String findAllCustomer() throws Exception{
		StringBuffer json = new StringBuffer("[") ;
		List<StoreWarehouse> storeWarehouseList = service.findByStoreWarehouse();
		StoreWarehouse storeWarehouse = new StoreWarehouse() ;
		if(storeWarehouseList.size() > 0){
			for(int i = 0 ; i < storeWarehouseList.size() ; i ++){
				storeWarehouse = storeWarehouseList.get(i) ;
				json.append("{\"text\":\"" + storeWarehouse.getName() + "\",\"id\":\"" + storeWarehouse.getId() + "\",\"leaf\":" + false) ;
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
	 * 修改出库明细
	 * @return
	 */
	public String modifyPullProduce(){
		
		return "modifyPullProduce";
	}
	/**
	 * 修改入库明细
	 * @return
	 */
	public String modifyPushProduce(){
		
		return "modifyPushProduce";
	}
	/**
	 * 查看出库明细
	 * @return
	 */
	public String browPullStoreProduceByTypeId(){
		String type="";
		if(null!=HttpTool.getParameter("type")&&HttpTool.getParameter("type").length()>0){
			type= HttpTool.getParameter("type");
		}
		List<StoreProduce> produces = produceService.findByStoreIdAndType(id,type);
		stroeType=(StoreType)typeService.findById(id);
		HttpTool.setAttribute("name", stroeType.getName());
		HttpTool.setAttribute("produces", produces);
		HttpTool.setAttribute("remark1", stroeType.getRemark1());
		log.info("大类code="+stroeType.getRemark1());
		return "browPullStoreProduceByTypeId";
	}
	
	/**
	 * 查询库存数量
	 * @return
	 * @author tangxing
	 * @date 2016-8-8下午2:56:10
	 */
	public String stockByTypeId(){
		String id = HttpTool.getParameter("id");
		StoreProduce produce = null;
		String type="";
		String name = "";
		String remark1 = "";
		StoreType stroeTypeTemp=(StoreType)typeService.findById(id);
		
		Map searchMap = super.buildSearch();
		if(id==null||id.equals("")){
			id=HttpTool.getParameter("filter_and_id_EQ_S");
		}
		if(null!=HttpTool.getParameter("type")&&HttpTool.getParameter("type").length()>0){
			type= HttpTool.getParameter("type");
			searchMap.put("filter_and_type_EQ_I", type);
		}
		
		name = stroeTypeTemp.getName();
		remark1 = stroeTypeTemp.getRemark1();
		
		List<StoreProduce> produces = produceService.findByStoreIdAndType(id,type);
		List<StoreProduce> producesTwo = produceService.findByStoreIdAndType(id,"0");	//拿入库的produce
		
		if(remark1.equals("1010701")){		//物品为卡时
			if(produces.size()==0){
				produce = new StoreProduce();
				produce.setRemark(String.valueOf(stroeTypeTemp.getNumPush()));//数量
				
				/* **拿入库的produce** */
				produce.setBasePrice(producesTwo.get(0).getBasePrice());	//单价	
				produce.setTotalPrice(producesTwo.get(0).getTotalPrice());	//总价
				
				produce.setCardStart(stroeTypeTemp.getCardStart());
				//produce.setCardStart(String.valueOf(Long.valueOf(stroeTypeTemp.getCardEnd())-stroeTypeTemp.getNum()+1));		//卡号开始
				produce.setCardEnd(stroeTypeTemp.getCardEnd());		//卡号结束
				/*produce.setProjectBelone("");	//项目归属
				produce.setProjectCode("");		//项目编码
				produce.setProjectBusinessName("");//项目负责人*/
				produce.setProjectBelone(producesTwo.get(0).getProjectBelone());	//项目归属
				produce.setProjectCode(producesTwo.get(0).getProjectCode());		//项目编码
				produce.setProjectBusinessName(producesTwo.get(0).getProjectBusinessName());//项目负责人
				
			}else{
				produce = new StoreProduce();
				String cardStrat = stroeTypeTemp.getCardStart();
				String cardEnd = stroeTypeTemp.getCardEnd();
				long tempOne = Long.parseLong(cardStrat);
				int num = stroeTypeTemp.getNum();	//剩余库存
				//int num = 0;
				long tempTwo = tempOne-num;		//最新的cardStrat
				
				/* **拿入库的produce** */
				produce.setBasePrice(producesTwo.get(0).getBasePrice());	//单价	
				produce.setTotalPrice(producesTwo.get(0).getTotalPrice());	//总价
				
				produce.setCardStart(cardStrat);
				//produce.setCardStart(String.valueOf(Long.valueOf(stroeTypeTemp.getCardEnd())-stroeTypeTemp.getNum()+1));
				produce.setCardEnd(cardEnd);
				produce.setProjectBelone(produces.get(0).getProjectBelone());	//项目归属
				produce.setProjectCode(produces.get(0).getProjectCode());		//项目编码
				produce.setProjectBusinessName(produces.get(0).getProjectBusinessName());//项目负责人
				produce.setRemark(String.valueOf(stroeTypeTemp.getNum()));	//数量
				produce.setNote(produces.get(0).getNote());			//备注
			}
		}else{
			if(produces.size()==0){
				produce = new StoreProduce();
				produce.setRemark(String.valueOf(stroeTypeTemp.getNumPush()));//数量
				
				/* **拿入库的produce** */
				produce.setBasePrice(producesTwo.get(0).getBasePrice());	//单价	
				produce.setTotalPrice(producesTwo.get(0).getTotalPrice());	//总价
				/*produce.setProjectBelone("");	//项目归属
				produce.setProjectCode("");		//项目编码
				produce.setProjectBusinessName("");//项目负责人*/
				produce.setProjectBelone(producesTwo.get(0).getProjectBelone());	//项目归属
				produce.setProjectCode(producesTwo.get(0).getProjectCode());		//项目编码
				produce.setProjectBusinessName(producesTwo.get(0).getProjectBusinessName());//项目负责人
				
			}else{
				produce = new StoreProduce();
				String numStr = String.valueOf(stroeTypeTemp.getNum());
				produce.setRemark(numStr);		//数量
				
				/* **拿入库的produce** */
				produce.setBasePrice(producesTwo.get(0).getBasePrice());	//单价		
				produce.setTotalPrice(producesTwo.get(0).getTotalPrice());	//总价
				
				produce.setProjectBelone(produces.get(0).getProjectBelone());	//项目归属
				produce.setProjectCode(produces.get(0).getProjectCode());		//项目编码
				produce.setProjectBusinessName(produces.get(0).getProjectBusinessName());//项目负责人
				produce.setRemark(String.valueOf(stroeTypeTemp.getNum()));	//数量
				produce.setNote(produces.get(0).getNote());			//备注
			}
		}
		
		
		HttpTool.setAttribute("name", name);
		HttpTool.setAttribute("produce", produce);
		HttpTool.setAttribute("remark1",remark1);
		return "stockByTypeId";
	}
	
	/**
	 * 显示入库记录
	 * @return
	 */
	public String browPushStoreProduceByTypeId(){
		String type="";
		//String displayParam = HttpTool.getParameter("displayParam");	//用于确定是否显示修改数量按钮
		Map searchMap = super.buildSearch();
		if(id==null||id.equals("")){
			id=HttpTool.getParameter("filter_and_id_EQ_S");
		}
		if(null!=HttpTool.getParameter("type")&&HttpTool.getParameter("type").length()>0){
			type= HttpTool.getParameter("type");
			searchMap.put("filter_and_type_EQ_I", type);
		}
		List<StoreProduce> produces = produceService.findByStoreIdAndType(id,type);
		stroeType=(StoreType)typeService.findById(id);
		//HttpTool.setAttribute("displayParam", displayParam);
		HttpTool.setAttribute("name", stroeType.getName());
		HttpTool.setAttribute("produces", produces);
		HttpTool.setAttribute("remark1", stroeType.getRemark1());
		log.info("大类code="+stroeType.getRemark1());
		return "browPushStoreProduceByTypeId";
	}
	public String browStoreProduce() throws ParseException{
		Map searchMap = super.buildSearch();
		if(id==null||id.equals("")){
			id=HttpTool.getParameter("filter_and_warehouseId_EQ_S");
		}
		searchMap.put("filter_and_warehouseId_EQ_S", id);
		stroeWarehouse = (StoreWarehouse)service.findById(id);
		
		searchMap.put("order_createTime", "desc");
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findProduceByPage(page, searchMap);
		return "browStoreProduce";
	}
	/**
	 * @查看库存页面
	 */
	public String browStoreAll(){
		//storeAll = (StoreWarehouseAll) allService.findById(id);
		stroeWarehouse=(StoreWarehouse) service.findById(id);
		List<StoreWarehouseAll> storeWarehouseAlls =  allService.findByPropertyAndIsDelete(StoreWarehouseAll.class, "warehouseId", id,0, null, null);
		HttpTool.setAttribute("stroeWarehouse",stroeWarehouse);
		HttpTool.setAttribute("storeWarehouseAlls",storeWarehouseAlls);
		return "browStoreAll";
	}
	/**
	 * @调拨明细主页面
	 */
	public String browStoreProduce1(){
		//storeAll = (StoreWarehouseAll) allService.findById(id);
		stroeWarehouse=(StoreWarehouse) service.findById(id);
		List<StoreProduce> storeProduceAlls =  produceService.findByPropertyAndIsDelete(StoreProduce.class, "warehouseId", id,0, "createTime", false);
		HttpTool.setAttribute("stroeWarehouse",stroeWarehouse);
		HttpTool.setAttribute("storeProduceAlls",storeProduceAlls);
		return "browStoreProduce1";
	}
	public Page findProduceByPage(Page page , Map paramsMap){
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		paramsMap.put("order_createTime", "desc");
		produceService.findByPage(page , paramsMap) ;
		return page ;
	}
	
	public Page findProduceDetailByPage(Page page , Map paramsMap){
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		paramsMap.put("order_createTime", "desc");
		produceDetailService.findByPage(page , paramsMap) ;
		return page ;
	}
	public String browProduceDetail() throws ParseException{
		Map searchMap = super.buildSearch();
		if(id==null||id.equals("")){
			id=HttpTool.getParameter("filter_and_produceId_EQ_S");
		}
		searchMap.put("filter_and_produceId_EQ_S", id);
		produce = (StoreProduce)produceService.findById(id);
		
		searchMap.put("order_createTime", "desc");
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findProduceDetailByPage(page, searchMap);
		return "browProduceDetail";
	}
	
	//快递单号补录
	public String showEmsInfo(){
		HttpTool.setAttribute("storeProduceId",HttpTool.getParameter("id"));
		HttpTool.setAttribute("navTabId",HttpTool.getParameter("navTabId"));
		return "showExpressInfo"; 
	}
	
	public void saveEmsInfo(){
		JSONObject json = new JSONObject();
		String storeProduceId = HttpTool.getParameter("storeProduceId");
		String navTabId = HttpTool.getParameter("navTabId");
		String emsName = HttpTool.getParameter("emsName");
		String emsNo = HttpTool.getParameter("emsNo");
		String emsPrice = HttpTool.getParameter("emsPrice");
		StoreProduce storeProduce = (StoreProduce)produceService.findById(storeProduceId);
		if(null!=storeProduce){
			storeProduce.setEmsName(emsName);
			storeProduce.setEmsNo(emsNo);
			storeProduce.setEmsPrice(Double.valueOf(emsPrice));
			produceService.save(storeProduce);
			json.accumulate("statusCode", 200);
			json.accumulate("result", "success");
			json.accumulate("message", "补录成功");
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "closeCurrent");
		}else{
			json.accumulate("result", "fail");
			json.accumulate("statusCode", 200);
			json.accumulate("message", "补录失败，没有找到对应的信息！");
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "closeCurrent");
		}
		renderJson(json);
	}
	
	/**
	 * 查询所有的套餐
	 * @return
	 * @throws Exception
	 */
	/*public String findAllCombo() throws Exception{
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		
		StringBuffer json = new StringBuffer("[") ;
		List<CustomerRelationShip> customerList = service.findByCustomer(currentUser.getJobNumber());
		CustomerRelationShip customer = new CustomerRelationShip() ;
		if(customerList.size() > 0){
			for(int i = 0 ; i < customerList.size() ; i ++){
				customer = customerList.get(i) ;
				String[] combo = customer.getCombo().replaceAll(" ", "").split(",");
				for(int j=0;j<combo.length;j++){
					json.append("{\"text\":\"" +combo[j] + "\",\"id\":\"" + combo[j] + "\",\"leaf\":" + false) ;
					json.append("},") ;
				}
			}
		}
		if (json.toString().endsWith(",")) {
			json = json.delete(json.length() - 1, json.length());
		}

		json.append("]");
		this.jsonString = json.toString();
		return "json";
	}*/
	
	/**
	 * 修改入库数量
	 * @return String
	 * @author DengYouming
	 * @since 2016-6-30 下午12:21:10
	 */
	public String updateStoreProduceById(){
		JSONObject json = new JSONObject();
		json.put("statusCode", 300);
		json.put("message", "网络异常！");
		
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String id = HttpTool.getParameter("id");
		String pushNum = HttpTool.getParameter("pushNum");
		String type = HttpTool.getParameter("type");
		String note = HttpTool.getParameter("note", "");
		if(StringUtils.isNotEmpty(id)&&StringUtils.isNotEmpty(pushNum)&&StringUtils.isNotEmpty(note)){
			//type=0 , 入库
			StoreProduce obj = (StoreProduce) produceService.findById(id);
			//类型ID
			String storeTypeId = null;
			String prevPushNum = null;
			if(obj!=null){
				//获取之前的数量
				prevPushNum = obj.getRemark();
				//设置当前数量
				obj.setRemark(pushNum);
				obj.setNote(note);
				storeTypeId = obj.getRemark1();	
				obj.setUpdateUserId(currentUser.getId());
				obj.setUpdateTime(new Date());
				//计算总额
				Double basePrice = obj.getBasePrice();
				Double totalPrice = basePrice * (new Integer(pushNum));
				obj.setTotalPrice(totalPrice);
				produceService.save(obj);			
				//根据仓库ID查找
				stroeType = (StoreType) typeService.findById(storeTypeId);
				//现在数量
				Integer nowPushNum = new Integer(pushNum);
				//之前数量
				Integer prevPushNumInt = new Integer(prevPushNum);
				//库存表
				StoreWarehouseAll storeWarehouseAll = null;
				//库存表
				List<StoreWarehouseAll> listalls =(List<StoreWarehouseAll>) allService.findByTypeId(storeTypeId);
				if(stroeType!=null&& !CollectionUtils.isEmpty(listalls)){
					Integer balance = stroeType.getNumPush() - prevPushNumInt + nowPushNum;
					Integer num = balance-stroeType.getNumPull();
					stroeType.setNumPush(balance);
					//设置剩余数量
					stroeType.setNum(num);
					stroeType.setUpdateUserName(currentUser.getAccountName());
					stroeType.setUpdateUserId(currentUser.getId());
					stroeType.setUpdateTime(new Date());
					//仓库产品小类
					typeService.update(stroeType);
					
					storeWarehouseAll = listalls.get(0);
					//入库数量
					Integer countPush = storeWarehouseAll.getCountPush()- prevPushNumInt + nowPushNum;
					//剩余数量
					Integer count = countPush - storeWarehouseAll.getCountPull();
					storeWarehouseAll.setCountPush(countPush);
					storeWarehouseAll.setCount(count);
					storeWarehouseAll.setUpdateUserId(currentUser.getId());
					storeWarehouseAll.setUpdateTime(new Date());
					//更新库存表
					allService.update(storeWarehouseAll);
					//TODO
					json.put("statusCode", 200);
					json.put("message", "修改成功");
					//刷新当前页面
					String forwardUrlMe = "../warehouse/warehouse!browPushStoreProduceByTypeId.action?id="+storeTypeId+"&type=0";
					String forwardUrl="../warehouse/warehouse!browStoreWarehouse.action?id="+storeWarehouseAll.getWarehouseId();
				//	String forwardUrl= "../warehouse/warehouse!browStoreWarehouse.action?id="+stroeType.getRemark();
					json.put("callbackType", "forward");
					json.put("forwardUrlMe", forwardUrlMe);
					json.put("forwardUrl", forwardUrl);
				}
			}
		}
		
		renderJson(json);
		return null;
	}
	
	public StoreTypeService getTypeService() {
		return typeService;
	}
	public StoreProduceDetail getStoreDetail() {
		return storeDetail;
	}
	public void setStoreDetail(StoreProduceDetail storeDetail) {
		this.storeDetail = storeDetail;
	}
	public StoreProduce getProduce() {
		return produce;
	}
	public void setProduce(StoreProduce produce) {
		this.produce = produce;
	}
	public StoreType getStroeType() {
		return stroeType;
	}
	public void setStroeType(StoreType stroeType) {
		this.stroeType = stroeType;
	}
	public void setTypeService(StoreTypeService typeService) {
		this.typeService = typeService;
	}
	 //SETTER GETTER
    public StoreWarehouse getStroeWarehouse() {
		return stroeWarehouse;
	}
	public void setStroeWarehouse(StoreWarehouse stroeWarehouse) {
		this.stroeWarehouse = stroeWarehouse;
	}
	public StoreWarehouseAll getStoreAll() {
		return storeAll;
	}
	public void setStoreAll(StoreWarehouseAll storeAll) {
		this.storeAll = storeAll;
	}
	
}
