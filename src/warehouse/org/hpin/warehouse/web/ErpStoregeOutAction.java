package org.hpin.warehouse.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.ErpApplication;
import org.hpin.warehouse.entity.ErpApplicationDetail;
import org.hpin.warehouse.entity.ErpStoregeOut;
import org.hpin.warehouse.entity.ErpStoregeReback;
import org.hpin.warehouse.entity.vo.StoregeInOutVo;
import org.hpin.warehouse.service.ErpApplicationService;
import org.hpin.warehouse.service.ErpStoregeOutService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @description: 基因物品发货Action
 * create by henry.xu 2016年10月12日
 */
@Namespace("/warehouse")
@Action("storegeOut")
@Results({
		@Result(name="list", location="/WEB-INF/warehouse/storegeOut/listStoregeOutApplication.jsp"),
		@Result(name="listStoregeOut", location="/WEB-INF/warehouse/storegeOut/listStoregeOut.jsp"),
		@Result(name="view", location="/WEB-INF/warehouse/storegeOut/viewErpStorege.jsp"),
		@Result(name="backStorege", location="/WEB-INF/warehouse/storegeOut/editStoregeReback.jsp"),
		@Result(name="updateStoregeOut", location="/WEB-INF/warehouse/storegeOut/updateStoregeOut.jsp"),
		@Result(name="sendEdit", location="/WEB-INF/warehouse/storegeOut/sendStoregeOut.jsp")})
public class ErpStoregeOutAction extends BaseAction{

	/**序列号*/
	private static final long serialVersionUID = 5380680839158046224L;
	private static final Logger log = Logger.getLogger(ErpStoregeOutAction.class);
	private static final String BACKSTATUS = "4"; //申请数据状态为4标示回退;
	
	@Autowired
	private ErpStoregeOutService erpStoregeOutService; //基因物品发货service
	
	@Autowired
	private ErpApplicationService erpApplicationService; //基因物品申请Service
	
	private ErpApplication application; //基因申请;
	
	private List<ErpApplicationDetail> details; //基因申请明细;
	
	private List<StoregeInOutVo> productList; //入库表中相应的产品集合
	
	private ErpStoregeOut storegeOut; //发货对象;
	
	private ErpStoregeReback storegeReback; //退货对象;
	
	private StoregeInOutVo storegeInOutVo; //发货入库关联对象
	
	/**
	 * 撤销处理
	 */
	public void dealRepeal() {
		JSONObject json = new JSONObject();
		boolean result = true;
		String msg = "";
		try {
			if(StringUtils.isNotEmpty(id)) {
				//当前人事该处理人相同才可以执行;
				result = this.erpApplicationService.findDealRepealCount(id, getUserInfo().getId()) ;
				
				if(result) {
					//查询是否满足回退条件; 不满足不进行撤销处理;
					String status = this.erpApplicationService.findStatusById(id);
					if("1".equals(status) || "2".equals(status)) { //部分发货, 已发货不可以撤销;
						msg = "该数据部分发货或已发货不能撤销处理!";
						result = false;
						
					} else if("0".equals(status)) {
						msg = "该数据处于待发货状态不能撤销处理!";
						result = false;
					
					}
				} else {
					msg = "该数据你不能撤销处理!";
				}
			}
			
			//进行回退处理;
			if(result) {
				this.erpApplicationService.updateStatusAndDealUserId(id);
				msg = "撤销处理成功!";
			}
		} catch(Exception e) {
			result = false;
			msg = "撤销处理失败!";
			log.error("基因申请发货撤销处理错误!", e);
		}
		
		json.put("result", result);
		json.put("message", msg);
		
		renderJson(json);
	}
	
	/**
	 * 退回;
     * 当前申请单的主表中状态不为：1和2才能退回
	 */
	public void backDeal() {
		
		JSONObject json = new JSONObject();
		boolean result = true;
		String failFlag = "";
		String msg = "";
		try {
			if(StringUtils.isNotEmpty(id)) {
				//查询是否满足回退条件; 不满足不进行回退处理;
				String status = this.erpApplicationService.findStatusById(id);
				if("1".equals(status) || "2".equals(status)) { //部分发货, 已发货不可以回退;
					msg = "该数据部分发货或已发货不能回退!";
					failFlag = "0";
					result = false;
				}
			}
			
			//进行回退处理;
			if(result) {
				this.erpApplicationService.updateStatus(id, BACKSTATUS);
				msg = "回退成功!";
			}
		} catch(Exception e) {
			result = false;
			failFlag = "1";
			msg = "回退失败!";
			log.error("基因申请发货回退错误!", e);
		}
		
		json.put("result", result);
		json.put("message", msg);
		json.put("failFlag", failFlag);
		
		renderJson(json);
	}
	
	/**
	 * 出库信息导出;
	 * @param page
	 * @param searchMap
	 */
	@SuppressWarnings("rawtypes")
	public Page exportExcel(Page page, Map searchMap) {
		//参数;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("storegeName", HttpTool.getParameter("params.storegeName", ""));
		params.put("applicationNo", HttpTool.getParameter("params.applicationNo", ""));
		params.put("productName", HttpTool.getParameter("params.productName", ""));
		params.put("applyUserName", HttpTool.getParameter("params.applyUserName", ""));
		params.put("startDate", HttpTool.getParameter("params.startDate", ""));
		params.put("endDate", HttpTool.getParameter("params.endDate", ""));
		
		
		//service重新获取,通过getbean方式;
		this.erpStoregeOutService = (ErpStoregeOutService) SpringTool.getBean("erpStoregeOutService");
		
		//list
		this.erpStoregeOutService.findStoregeOutsAll(page, params);
		
		return page;
	}
	
	/**
	 * 修改实体对象;
	 */
	public void update() {
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String message = "操作成功!"; //异常使用该字段;抛出到前台显示;
		String callbackType= "closeCurrent";
		try {
			this.erpStoregeOutService.updateStoregeOut(storegeOut, getUserInfo());			
		} catch (Exception e) {
			statusCode = "300";
			message = "操作失败!";
			log.error("发货修改失败!", e);
		}
		json.put("callbackType", callbackType);
		json.put("navTabId", navTabId);
		json.put("statusCode", statusCode);
		json.put("message", message);
		renderJson(json);
	}
	
	/**
	 * 修改界面跳转;
	 * @return
	 */
	public String updateStoregeOut() {
		if(StringUtils.isNotEmpty(id)) {
			storegeInOutVo = this.erpStoregeOutService.findStoregeOutById(id);
		}
		return "updateStoregeOut";
	}
	
	/**
	 * 查询已退库数据数量sum
	 */
	public void validBackNum() {
		JSONObject json = new JSONObject();
		Integer num = this.erpStoregeOutService.findStoregeRebackNumByOutId(id) ;
		json.put("num", num);
		renderJson(json);
	}
	
	/**
	 * 退库处理;
	 * @return
	 */
	public String backStorege() {
		//发货ID是否为空;
		if(StringUtils.isNotEmpty(id)) {
			//获取回显信息;
			storegeInOutVo = this.erpStoregeOutService.findStoregeOutById(id);
			
		}
		return "backStorege";
	}
	
	public void saveStoregeReback() {
		
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String message = "操作成功!"; //异常使用该字段;抛出到前台显示;
		String callbackType= "closeCurrent";
		try {
			this.erpStoregeOutService.saveStoregeReback(storegeReback);			
		} catch(Exception e) {
			callbackType = "";
			statusCode = "300";
			message = "操作失败!";
			log.error("退库保存错误!", e);
		}
		
		json.put("callbackType", callbackType);
		json.put("statusCode", statusCode);
		json.put("message", message);
		renderJson(json);
	}
	
	/**
	 * 出库信息分页查询;
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String listStoregeOut() {
		try {
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			this.erpStoregeOutService.findStoregeOutByPage(page, getUserInfo(), params);
		} catch(Exception e) {
			log.error("基因物品申请查询错误!", e);
		}
		return "listStoregeOut";
	}
	
	
	/**
	 * 查看
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String view() {
		
		Map<String, Object> map = this.erpApplicationService.findApplicationToDetailById(id) ;
		this.application = (ErpApplication) map.get("application");
		this.details = (List<ErpApplicationDetail>) map.get("details");
		this.productList = this.erpStoregeOutService.findStoregeOutByApplicationId(id);
		return "view";
	}
	
	/**
	 * 处理;
	 */
	public void dealApplicationStatus() {
		JSONObject json = new JSONObject();
		boolean result = false;
		String message = "数据处理失败!"; //异常使用该字段;抛出到前台显示;
		
		//验证, 该数据是否已被处理;由于发货功能在后,所以只用判断是否被处理即可;及deal_user_id是否有值;
		result = this.erpApplicationService.findDealUserIdById(ids);
		
		if(result) {
			this.erpStoregeOutService.dealApplicationStatus(ids, getUserInfo());
			message = "数据处理成功!";
			result = true;
		} else {
			message = "该数据已被处理,是否刷新当前界面?";
			result = false;
		}
		json.put("result", result);
		json.put("message", message);
		renderJson(json);
	}
	
	/**
	 * 保存;
	 */
	public void sendSave() {
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String message = ""; //异常使用该字段;抛出到前台显示;
		
		//调用保存;
		erpStoregeOutService.sendSave(storegeOut, getUserInfo(), id);
		
		json.put("statusCode", statusCode);
		json.put("message", message);
		renderJson(json);
	}

	/**
	 * 列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String list() {
		try {
			
			HttpTool.setAttribute("user", getUserInfo());
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			this.erpStoregeOutService.findApplicationsByPage(page, getUserInfo(), params);
		} catch(Exception e) {
			log.error("基因物品申请查询错误!", e);
		}
		return "list";
	}
	
	/**
	 * 发货界面跳转;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String sendEdit() {
		Map<String, Object> map = this.erpApplicationService.findApplicationToDetailById(id) ;
		this.application = (ErpApplication) map.get("application");
		this.details = (List<ErpApplicationDetail>) map.get("details");
		this.productList = this.erpStoregeOutService.findObjectsByApplicationId(id, getUserInfo().getId());
		return "sendEdit";
	}

	public ErpApplication getApplication() {
		return application;
	}

	public void setApplication(ErpApplication application) {
		this.application = application;
	}

	public List<ErpApplicationDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ErpApplicationDetail> details) {
		this.details = details;
	}

	public List<StoregeInOutVo> getProductList() {
		return productList;
	}

	public void setProductList(List<StoregeInOutVo> productList) {
		this.productList = productList;
	}

	public ErpStoregeOut getStoregeOut() {
		return storegeOut;
	}

	public void setStoregeOut(ErpStoregeOut storegeOut) {
		this.storegeOut = storegeOut;
	}

	public ErpStoregeReback getStoregeReback() {
		return storegeReback;
	}

	public void setStoregeReback(ErpStoregeReback storegeReback) {
		this.storegeReback = storegeReback;
	}

	public StoregeInOutVo getStoregeInOutVo() {
		return storegeInOutVo;
	}

	public void setStoregeInOutVo(StoregeInOutVo storegeInOutVo) {
		this.storegeInOutVo = storegeInOutVo;
	}
	
	
}
