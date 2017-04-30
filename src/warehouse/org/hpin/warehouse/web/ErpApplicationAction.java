package org.hpin.warehouse.web;

import java.io.File;
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
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.ErpApplication;
import org.hpin.warehouse.entity.ErpApplicationDetail;
import org.hpin.warehouse.service.ErpApplicationService;
import org.hpin.warehouse.service.ErpProductComboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @description: 基因物品申请Action
 * create by henry.xu 2016年10月10日
 */
@Namespace("/warehouse")
@Action("application")
@Results({
		@Result(name = "list", location = "/WEB-INF/warehouse/application/listErpApplication.jsp"),
		@Result(name = "edit", location = "/WEB-INF/warehouse/application/editErpApplication.jsp"),
		@Result(name = "editBatch", location = "/WEB-INF/warehouse/application/editBatchErpApplication.jsp"),
		@Result(name = "addProduct", location = "/WEB-INF/warehouse/application/queryProComboSelect.jsp"),
		@Result(name = "addYgbxProduct", location = "/WEB-INF/warehouse/ygbxApplication/queryProComboSelect.jsp"),
		@Result(name = "updateEdit", location = "/WEB-INF/warehouse/application/modifiedErpApplication.jsp"),
		@Result(name = "view", location = "/WEB-INF/warehouse/application/viewErpApplication.jsp"),
		@Result(name = "ygbxView", location = "/WEB-INF/warehouse/ygbxApplication/viewYgbxApplication.jsp"),
		@Result(name = "ygbxEdit", location = "/WEB-INF/warehouse/ygbxApplication/editYgbxApplication.jsp"),
		@Result(name = "impCustomerUpload", location = "/WEB-INF/warehouse/ygbxApplication/importCustomerApplication.jsp"),
		@Result(name = "ygbxUpdate", location = "/WEB-INF/warehouse/ygbxApplication/modifiedYgbxApplication.jsp"),
		@Result(name = "ygbxList", location = "/WEB-INF/warehouse/ygbxApplication/listYgbxApplication.jsp"),
		@Result(name = "ygbxAllList", location = "/WEB-INF/warehouse/ygbxApplication/listYgbxAllApplication.jsp")
		
})
public class ErpApplicationAction extends BaseAction{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2000967056128428157L;
	private static final Logger log = Logger.getLogger(ErpApplicationAction.class);
	
	@Autowired
	private ErpProductComboService erpProductComboService; //产品套餐service
	
	@Autowired
	private ErpApplicationService erpApplicationService; //基因物品申请Service
	
	@Autowired
	private DeptService deptService; //总公司
	
	@Autowired
	private CustomerRelationShipService customerRelationShipService; //支公司
	
	private ErpApplication application; //基因申请;
	
	private List<ErpApplicationDetail> details; //基因申请明细;
	private String affixFileName; //文件上传名称;
	private File affix; //文件
    private String affixContentType; //文件类型;
    
    /**
	 * 弘康客户数据批量导入客户信息;
	 * create by henry.xu 2016年12月29日
	 */
	public void customerHKExcel() {
		JSONObject json = new JSONObject();
		String message = "";
		String statusCode = "";
		try {
			log.info("上传的excel文件名: " + affixFileName);
			if(StringUtils.isNotEmpty(affixFileName)) {
				//获取后缀名;
				String type = affixFileName.substring(affixFileName.lastIndexOf(".")+1, affixFileName.length());
				
				if("xlsx".equals(type) || "xls".equals(type)) {
					this.erpApplicationService.saveCustomerHKByExcel(affix, affixFileName, application, getUserInfo(), details);
					message = "Excel导入成功!";
					log.info("客户信息保存后消息: " + message);
					statusCode = "200";
					json.accumulate("callbackType", "closeCurrent");
				} else {
					statusCode = "300";
					message = "导入的文件格式不正确, 请下载模板导入!";
				}
				
				
			}
			
		} catch (Exception ex) {
			log.info("saveCustomer Exception"+ex);
			statusCode = "300";
			message = ex.getMessage();
		}finally{
			json.put("navTabId", navTabId);
			json.put("message", message);
			json.put("statusCode", statusCode);
			renderJson(json);
		}
		
	}
    
	
    /**
     * 批量申请跳转界面
     * create by henry.xu 2016年12月29日
     */
    public String editBatch() {
    	
    	return "editBatch";
    }
    
    /**
	 * 阳关保险公司导入客户信息;
	 * create by henry.xu 2016年12月15日
	 */
	public void impCustomerExcel() {
		JSONObject json = new JSONObject();
		String message = "";
		String statusCode = "";
		try {
			log.info("上传的excel文件名: " + affixFileName);
			if(StringUtils.isNotEmpty(affixFileName)) {
				//获取后缀名;
				String type = affixFileName.substring(affixFileName.lastIndexOf(".")+1, affixFileName.length());
				
				if("xlsx".equals(type) || "xls".equals(type)) {
					String applicationNo = HttpTool.getParameter("applicationNo", "");
					this.erpApplicationService.saveCustomerByExcel(affix, affixFileName, applicationNo, getUserInfo());
					message = "Excel导入成功!";
					statusCode = "200";
					json.put("navTabId", navTabId);
					log.info("客户信息保存后消息: " + message);
				} else {
					statusCode = "300";
					message = "导入的文件格式不正确, 请下载模板导入!";
				}
				
				
			}
			
		} catch (Exception ex) {
			log.info("saveCustomer Exception"+ex);
			statusCode = "300";
			message = ex.getMessage();
			json.accumulate("navTabId", "menu_31");
			json.accumulate("callbackType", "1,0");
		}finally{
			//HttpServletRequest request = ServletActionContext.getRequest();
			//request.getSession().setAttribute(ServerProcessAction.INEXPORT, "1");
			json.put("message", message);
			json.put("statusCode", statusCode);
			renderJson(json);
		}
		
	}
	
	/**
	 * 上传页面跳转
	 * create by henry.xu 2016年12月15日
	 * @return
	 */
	public String impCustomerUpload() {
		String applicationNo = HttpTool.getParameter("applicationNo", "");
		HttpTool.setAttribute("applicationNo", applicationNo);
		return "impCustomerUpload";
	}
	
	/**
	 * 
	 * create by henry.xu 2016年12月15日
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String ygbxView() {
		
		Map<String, Object> map = this.erpApplicationService.findApplicationToDetailById(id) ;
		this.application = (ErpApplication) map.get("application");
		this.details = (List<ErpApplicationDetail>) map.get("details");
		
		return "ygbxView";
	}

	
	/**
	 * 阳关保险保存申请单
	 * create by henry.xu 2016年12月15日
	 */
	public void saveOrUpdateYgbx() {
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String callbackType= "closeCurrent";
		String message = "操作成功!";
		try {
			if(application != null) {
				if(details == null || details.size() <= 0) {
					message= "请添加产品套餐后在提交数据!";
					statusCode = "300";
				} else {
					
					User userInfo = getUserInfo();
					if(StringUtils.isEmpty(application.getId())) { 
						this.erpApplicationService.saveYgbxApplication(application, userInfo, details);
					} else {
						this.erpApplicationService.updateYgbxApplication(application, userInfo, details);
					}
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
	 * 阳光保险物料申请编辑;
	 * create by henry.xu 2016年12月13日
	 * @return
	 */
	public String ygbxEdit() {
		//查询当前登录人的总公司,支公司数据;
		User userInfo = getUserInfo();
		
		//查找总公司;
		Dept dept = (Dept) this.deptService.findById(userInfo.getDeptId());
		
		//查找支公司;
		CustomerRelationShip ship = (CustomerRelationShip) this.customerRelationShipService.findById(userInfo.getJobNumber());
		
		HttpTool.setAttribute("dept", dept);
		HttpTool.setAttribute("ship", ship);
		HttpTool.setAttribute("userInfo", userInfo);
		
		return "ygbxEdit";
	}
	
	/**
	 * 阳光保险物料申请编辑;
	 * create by henry.xu 2016年12月13日
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String ygbxUpdate() {
		//查询当前登录人的总公司,支公司数据;
		User userInfo = getUserInfo();
		HttpTool.setAttribute("userInfo", userInfo);
		Map<String, Object> map = this.erpApplicationService.findApplicationById(id) ;
		this.application = (ErpApplication) map.get("application");
		this.details = (List<ErpApplicationDetail>) map.get("details");
		return "ygbxUpdate";
	}
	
	/**
	 * 阳光保险物料申请列表;
	 * create by henry.xu 2016年12月13日
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String ygbxList() {
		
		try {
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			if(null == params) {
				params = new HashMap<String, String>();
			}
			User user = getUserInfo();
			/*
			 * 此处这么设置实为了让阳光保险的人的支公司进行查询;管理员可以查看所有;
			 * 总公司可以查看该总公司对应的所有支公司信息;
			 */
			params.put("bannyCompanyId", user.getJobNumber());  //当前登陆人支公司;
			params.put("isMark", "1");
			this.erpApplicationService.findApplicationsByPage(page, params);
		} catch(Exception e) {
			log.error("基因物品申请查询错误!", e);
		}
		
		return "ygbxList";
	}
	
	/**
	 * 阳光保险物料申请列表;
	 * create by henry.xu 2016年12月13日
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String ygbxAllList() {
		
		try {
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			if(null == params) {
				params = new HashMap<String, String>();
			}
			User user = getUserInfo();
			/*
			 * 此处这么设置实为了让阳光保险的人的支公司进行查询;管理员可以查看所有;
			 * 总公司可以查看该总公司对应的所有支公司信息;
			 */
			params.put("bannyCompanyId", user.getJobNumber());  //当前登陆人支公司;
			params.put("isMark", "1");
			this.erpApplicationService.findApplicationsByPage(page, params);
		} catch(Exception e) {
			log.error("基因物品申请查询错误!", e);
		}
		
		return "ygbxList";
	}
	
	
	/**
	 * 判断是否存在产品冲突;如果存在返回false, 否则返回true;
	 */
	public void validProductEqual() {
		JSONObject json = new JSONObject();
		
		String ids = HttpTool.getParameter("ids", "");
		String areadyIds = HttpTool.getParameter("areadyIds", "");
		
		json.put("result", this.erpApplicationService.validProductEqual(ids, areadyIds));
		renderJson(json);
	}
	
	/**
	 * 查询所选择的套餐;
	 */
	public void ajaxProductComboDetail() {
		JSONObject json = new JSONObject();
		String proComboId = HttpTool.getParameter("proComboId", "");
		json.put("result", this.erpApplicationService.ajaxProductComboDetail(proComboId));
		renderJson(json);
	}
	
	/**
	 * 根据Id进行逻辑删除;
	 */
	public void delete() {
		JSONObject json = new JSONObject();
		boolean result = true;
		String msg = "";
		if(StringUtils.isNotEmpty(id)) {
			try {
				this.erpApplicationService.deleteApplicationById(id);
				msg = "删除成功!";
			} catch (Exception e) {
				result = false;
				msg = "删除失败!";
				log.error("基因物品申请删除操作失败!", e);
			}
		}
		json.put("result", result);
		json.put("message", msg);
		renderJson(json);
	}
	
	/**
	 * 通过id查询该数据状态;
	 */
	public void findStatusById() {
		JSONObject json = new JSONObject();
		json.put("status", this.erpApplicationService.findStatusById(id));
		
		renderJson(json);
	}
	
	/**
	 * 保存/修改;
	 */
	public void saveOrUpdate() {
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String callbackType= "closeCurrent";
		String message = "操作成功!";
		try {
			if(application != null) {
				if(details == null || details.size() <= 0) {
					message= "请添加产品套餐后在提交数据!";
					statusCode = "300";
				} else {
					
					User userInfo = getUserInfo();
					if(StringUtils.isEmpty(application.getId())) { 
						this.erpApplicationService.saveApplication(application, userInfo, details);
					} else {
						this.erpApplicationService.updateApplication(application, userInfo, details);
					}
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
	 * 编辑(新增, 修改)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String edit() {
		String resultPage = "edit";
		if(StringUtils.isNotEmpty(id)) {
			resultPage = "updateEdit";
			Map<String, Object> map = this.erpApplicationService.findApplicationById(id) ;
			this.application = (ErpApplication) map.get("application");
			this.details = (List<ErpApplicationDetail>) map.get("details");
		}
		return resultPage;
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
		
		return "view";
	}
	
	/**
	 * 列表;
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String list() {
		try {
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			if(params == null) {
				params = new HashMap<String, String>();
			}
			params.put("createUser", getUserInfo().getId());
			this.erpApplicationService.findApplicationsByPage(page, params);
		} catch(Exception e) {
			log.error("基因物品申请查询错误!", e);
		}
		return "list";
	}
	
	/**
	 * 列表信息查询;
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String addProduct() {
		try {
			page = new Page( HttpTool.getPageNum(), HttpTool.getPageSize());
			this.erpProductComboService.findErpProComboByPage(page, params);
		} catch(Exception e) {
			log.error("产品信息列表查询错误!", e);
		}
		return "addProduct";
	}
	
	/**
	 * 列表信息查询;
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String addYgbxProduct() {
		try {
			page = new Page( HttpTool.getPageNum(), HttpTool.getPageSize());
			this.erpProductComboService.findErpProComboByPage(page, params);
		} catch(Exception e) {
			log.error("产品信息列表查询错误!", e);
		}
		return "addYgbxProduct";
	}

	/*get/set*/
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

	public String getAffixFileName() {
		return affixFileName;
	}

	public void setAffixFileName(String affixFileName) {
		this.affixFileName = affixFileName;
	}

	public File getAffix() {
		return affix;
	}

	public void setAffix(File affix) {
		this.affix = affix;
	}

	public String getAffixContentType() {
		return affixContentType;
	}

	public void setAffixContentType(String affixContentType) {
		this.affixContentType = affixContentType;
	}
	
}
