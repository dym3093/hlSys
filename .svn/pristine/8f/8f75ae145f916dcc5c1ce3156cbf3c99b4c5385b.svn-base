package org.hpin.base.customerrelationship.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.customerrelationship.entity.CustomerRelationshipLink;
import org.hpin.base.customerrelationship.entity.CustomerShipCode;
import org.hpin.base.customerrelationship.entity.ProjectType;
import org.hpin.base.customerrelationship.entity.vo.CustomerRelationShipQuery;
import org.hpin.base.customerrelationship.entity.vo.CustomerRelationShipVO;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.customerrelationship.service.CustomerRelationshipComboService;
import org.hpin.base.customerrelationship.service.CustomerRelationshipLinkService;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.AjaxCheckCode.service.AjaxCheckCodeService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpEventsService;
import org.hpin.warehouse.entity.StoreApplication;
import org.hpin.webservice.service.YmGeneReportServiceImpl;
import org.hpin.webservice.service.YmGeneReportServiceImplServiceLocator;
import org.springframework.beans.factory.annotation.Value;
import org.ymjy.combo.entity.Combo;
import org.ymjy.combo.service.ComboService;

@Namespace("/resource")
@Action("customerRelationShip")
@Results( { 
	@Result(name = "listCustomerRelationShip", location = "/WEB-INF/branchcommany/listCustomerRelationShip.jsp"),
	@Result(name = "lookCustomerRelationShip", location = "/WEB-INF/branchcommany/lookCustomerRelationShip.jsp"),
	@Result(name = "searchCompany", location = "/WEB-INF/branchcommany/searchCompany.jsp"),
	@Result(name = "lookCustomerRelationShip1", location = "/WEB-INF/branchcommany/lookCustomerRelationShip1.jsp"),
	@Result(name = "lookDept", location = "/WEB-INF/branchcommany/lookDept.jsp"),
	@Result(name = "listCustomer", location = "/WEB-INF/business/customer/listCustomer.jsp"), 
	@Result(name = "listCustomerForContract", location = "/WEB-INF/business/customer/listCustomerForContract.jsp"), 
	@Result(name = "lookUpCustomer", location = "/WEB-INF/business/customer/lookUpCustomer.jsp"), 
	@Result(name = "lookUpCustomerAll", location = "/WEB-INF/business/customer/lookUpCustomerAll.jsp"), 
	@Result(name = "browCustomer", location = "/WEB-INF/branchcommany/browCustomer.jsp"), 
	@Result(name = "browCustomerRelationShip", location = "/WEB-INF/branchcommany/browCustomerRelationShip.jsp"), 
	@Result(name = "addCustomer", location = "/WEB-INF/branchcommany/addCustomer.jsp"), 
	@Result(name = "addProductServe", location = "/WEB-INF/branchcommany/addallCombo.jsp"),
	@Result(name = "editProComboDetail", location = "/WEB-INF/branchcommany/editProComboDetail.jsp"),
	@Result(name = "queryCombo", location = "/WEB-INF/branchcommany/queryCombo.jsp"),
	@Result(name = "modifiedComboRelation", location = "/WEB-INF/branchcommany/comboRelationPdialog.jsp"),
	@Result(name = "productTree", location = "/WEB-INF/branchcommany/productTree.jsp"),
	@Result(name = "customerRelshipPro", location = "/WEB-INF/branchcommany/customerRelshipPro.jsp"),
	@Result(name = "lookCustomerRelationshipPro", location = "/WEB-INF/branchcommany/lookCustomerRelationshipPro.jsp"),
	@Result(name = "success", location = "/WEB-INF/warehouse/storeApplication/addstoreApplication.jsp"),
	@Result(name = "modifyCustomer", location = "/WEB-INF/branchcommany/modifyCustomer.jsp"),
	@Result(name = "printKeyWord", location = "/WEB-INF/branchcommany/printKeyWord.jsp"),
    @Result(name = "listCompanyQRCode", location = "/WEB-INF/branchcommany/listCompanyQRCode.jsp")})
public class CustomerRelationShipAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(CustomerRelationShipAction.class);

	CustomerRelationShipService customerService = (CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
	CustomerRelationshipLinkService customerRelationshipLinkService = (CustomerRelationshipLinkService)SpringTool.getBean(CustomerRelationshipLinkService.class);
	CustomerRelationshipComboService customerRelationshipComboService=(CustomerRelationshipComboService)SpringTool.getBean(CustomerRelationshipComboService.class);
	ComboService comboService = (ComboService)SpringTool.getBean(ComboService.class);
	DeptService deptService = (DeptService)SpringTool.getBean(DeptService.class);
	AjaxCheckCodeService  ajaxCheckCodeService=(AjaxCheckCodeService)SpringTool.getBean(AjaxCheckCodeService.class);
	ErpEventsService eventsService = (ErpEventsService) SpringTool.getBean(ErpEventsService.class);
	UserService userService = (UserService) SpringTool.getBean(UserService.class);
	
	YmGeneReportServiceImplServiceLocator geneReportServiceImplServiceLocator = new YmGeneReportServiceImplServiceLocator();
	/**
	 * 实体信息
	 */
	CustomerRelationShip customerRelationShip;
	private StoreApplication storeApplication;
	private CustomerRelationShipQuery custRelaQuery; //查询条件对象; create by henry.xu
	@Value("${interface.address}")
	public String address;
	/**
	 * 套餐信息获取
	 */
	List<Combo> bankList;
	
	/**linkList
	 * 联系人信息获取
	 */
	List<CustomerRelationshipLink> linkList =new LinkedList<CustomerRelationshipLink>();
	/**
	 * 项目信息列表;
	 */
	List<CustomerRelationShipPro> shipPros = new ArrayList<CustomerRelationShipPro>();
	private List<CustomerRelationShipVO> shipVos ;
	
	/**
	 * 根据总公司Id查询对应的支公司,以及查询条件;
	 * <p>Description: </p>
	 * @author herny.xu
	 * @date 2017年3月10日
	 */
	public String findCompanyByOwnerCompanyId() {
        shipVos = customerService.findCompanyByOwnerCompanyId(params);
		return "searchCompany";
	}
	
	/**
	 * 保存套餐显示名称数据;
	 * create by henry.xu 2016年12月26日
	 */
	public void saveRelationCombo() {
		boolean result = true;
		String id = HttpTool.getParameter("id", "");
		String isUsed = HttpTool.getParameter("isUsed", "");
		String comboShowName = HttpTool.getParameter("comboShowName", "");
		String isCreatePdf = HttpTool.getParameter("isCreatePdf", "");
		Integer printType = Integer.valueOf(HttpTool.getParameter("printType")); 
		result = this.comboService.saveRelationCombo(id, isUsed, comboShowName, isCreatePdf,printType);
		
		JSONObject json = new JSONObject();
		json.put("result", result);
		renderJson(json);
	}
	
	/**
	 * 修改套餐显示名称, 编辑界面;
	 * create by henry.xu 2016年12月26日
	 */
	public String modifiedComboRelation() {
		List<ProjectType> proTypes = customerService.findProjectTypes();
		HttpTool.setAttribute("proTypes", proTypes);
		
		Combo combo = this.customerService.findErpRelationComboById(id);
		HttpTool.setAttribute("combo", combo);
		
		return "modifiedComboRelation";
		
	}
	
	/**
	 * 删除按钮操作;
	 * 需要校验：如果当前erp_customer表中已经存在当前这个项目下的这个套餐，
	 * 则不允许进行删除，并给出提示“客户已经关联了此套餐，不允许删除
	 */
	public void deleteProCombo() {
		JSONObject json = new JSONObject();
		boolean result = true;
		//执行保存
		try {
			result = this.customerService.deleteProCombo(id);
		} catch (Exception e) {
			result = false;
			log.error("中间表保存错误!", e);
		}
		//返回
		json.put("result", result);
		
		renderJson(json);
	}
	
	/**
	 * 修改1+X数据套餐是否需要生成报告状态
	 * 
	 * @author tangxing
	 * @date 2016-12-5下午3:27:17
	 */
	public void changeIsCreatePdf(){
		String id = HttpTool.getParameter("id");			//中间表（ERP_RelationShipPro_Combo）id
		
		JSONObject json = new JSONObject();
		boolean result = true;
		
		try {
			result = this.customerService.changeIsCreatePdf(id);
		} catch (Exception e) {
			log.error("CustomerRelationShipAction changeIsCreatePdf -- "+e);
			result = false;
		}
		
		json.put("result", result);
		
		renderJson(json);
	}
	
	
	/**
	 * 保存中间表数据;
	 */
	public void saveProCombo() {
		JSONObject json = new JSONObject();
		boolean result = true;
		//执行保存
		try {
			result = this.customerService.saveProCombo(id, ids);
		} catch (Exception e) {
			result = false;
			log.error("中间表保存错误!", e);
		}
		//返回
		json.put("result", result);
		
		renderJson(json);
	}
	
	/**
	 * 查询套餐,排除已选择的套餐;分页;
	 * creaty by henry.xu 20161117
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String queryCombo() {
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			this.comboService.findCombosPageByConditions(page, params);
		} catch(Exception e) {
			log.error("套餐查询错误!", e);
		}
		return "queryCombo";
	}
	
	/**
	 * 跳转界面到支公司项目列表更改套餐数据
	 * modified by henry.xu	20161117
	 * 
	 */
	public String editProComboDetail() {
		//根据id获取对应的支公司以及项目列表信息;如果是修改编辑还要获取支公司对应的套餐;
		Object[] obj = customerService.findCustomerById(id);
		customerRelationShip = (CustomerRelationShip) obj[0];
		shipPros = customerService.findCustRelShipProsByCustRelShipId(id, null);
		 
		List<ProjectType> proTypes = customerService.findProjectTypes();
		HttpTool.setAttribute("proTypes", proTypes);
		
		return "editProComboDetail";
	}
	
	/**
	 * 在点击带回按钮时调用,没有分页;数量可能实现分页情况较少;
	 * @return
	 */
	public String customerRelshipPro() {
		String customerRelShipId = HttpTool.getParameter("customerRelShipId", "");
		List<CustomerRelationShipPro> pros = customerService.findCustRelShipProsByCustRelShipId(customerRelShipId, "0");
		List<ProjectType> proTypes = customerService.findProjectTypes();
		HttpTool.setAttribute("proTypes", proTypes);
		HttpTool.setAttribute("pros", pros);
		return "customerRelshipPro";
	}
	
	/**
	 * @return 2017年1月22日15:38:08
	 * @author Carly
	 */
	@SuppressWarnings("unchecked")
	public String findCustomerRelshipPro() {
		Map<String,String> searchMap = buildSearch();
		try {
			page = new Page<CustomerRelationShipPro>(HttpTool.getPageNum(),HttpTool.getPageSize());
			customerService.findCustomerShipPro(page, searchMap);
			List<ProjectType> proTypes = customerService.findProjectTypes();
			HttpTool.setAttribute("proTypes", proTypes);
		} catch (Exception e) {
			log.error("findCustomerRelshipPro---"+e);
		}
		return "lookCustomerRelationshipPro";
	}
	
	/**
	 * 项目信息列表点击新增返回select下拉列表;
	 * @return
	 */
	public String findProTypes() {
		List<ProjectType> proTypes = customerService.findProjectTypes();
		StringBuilder resultHtml = new StringBuilder("<select name='noname' style='width:100px;background-color:#EEEEEE' class='required projectType'> <option value=''>请选择</option>");
		for (ProjectType protype : proTypes) {
			resultHtml.append("<option value='");
			resultHtml.append(protype.getId());
			resultHtml.append("'>");
			resultHtml.append(protype.getProjectTypeName());
			resultHtml.append("</option>");
		}
		resultHtml.append("</select>");
		HttpTool.toResponse(resultHtml.toString());
		return null;
	}
	
	public void findIsSeal() {
		StringBuilder resultHtml = new StringBuilder("<select name='sealName' style='width:100px;'>");
		resultHtml.append("<option value='0'>否</option>");
		resultHtml.append("<option value='1'>是</option>");
		resultHtml.append("</select>");
		HttpTool.toResponse(resultHtml.toString());
	}
	
	@SuppressWarnings("rawtypes")
	public String listCustomerRelationShip() throws ParseException{
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		//查询公司信息;custRelaQuery
		customerService.findCustomerRelationShipsByCondition(page, custRelaQuery) ;

		// 施主，勿怪,为保证查询后查询条件仍在查询框中而设定，命名方式是历史遗留问题，用的地方太多，不好改 begin
		String aaaa = HttpTool.getParameter("aaaa", "");
		String bbbb = HttpTool.getParameter("bbbb", "");
		HttpTool.setAttribute("aaaa", aaaa);
		HttpTool.setAttribute("bbbb", bbbb);
		// 施主，勿怪,为保证查询后查询条件仍在查询框中而设定，命名方式是历史遗留问题，用的地方太多，不好改 end

		return "listCustomerRelationShip";
	}
	/**
	 * 总公司
	 * @return
	 * @throws ParseException
	 */
	public String lookDept() throws ParseException{
        Map searchMap = super.buildSearch();
        searchMap.put("filter_and_isDeleted_EQ_I", 0);
//        searchMap.put("filter_and_deptCode_LIKE_S", "D01");
        searchMap.put("filter_and_parentId_EQ_S", "40289b6a5206079d0152061530000007");
        searchMap.put("order_createTime", "desc");
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
        List<Dept> deptList=deptService.findByPage(page,searchMap);
        page.setResults(deptList) ;
        return "lookDept";
    }
	public String lookCustomerRelationShip1() throws ParseException{
	        Map searchMap = super.buildSearch();
	        searchMap.put("filter_and_isDeleted_EQ_I", 0);
	        searchMap.put("order_createTime", "desc");
	        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
	        List<CustomerRelationShip> customerRelationShipList=customerService.findByPage(page,searchMap);
	        page.setResults(customerRelationShipList) ;
	        return "lookCustomerRelationShip1";
	    }
	/**
	 * 查找带回中调用该方法;
	 * create by henry.xu 20161121
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String findCustomerRelationShip() {
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			this.customerService.findCustomerRelationShipsByCondition(page, custRelaQuery);
		} catch (ParseException e) {
			log.error("支公司查找带回错误!", e);
		}
		
		return "lookCustomerRelationShip1";
	}
	
	public String lookCustomerRelationShip() throws ParseException{
        Map searchMap = super.buildSearch();
        searchMap.put("filter_and_isDeleted_EQ_I", 0);
        searchMap.put("order_createTime", "desc");
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
        List<CustomerRelationShip> customerRelationShipList=customerService.findByPage(page,searchMap);
        page.setResults(customerRelationShipList) ;
        return "lookCustomerRelationShip";
    }
	
	/**
	 * 查找带回中调用该方法; 由于之前代码问题,出现不可重用,所以分开写;
	 * create by henry.xu 20161121
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String findCustomerRelationShipTwo() {
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			this.customerService.findCustomerRelationShipsByCondition(page, custRelaQuery);
		} catch (ParseException e) {
			log.error("支公司查找带回错误!", e);
		}
		
		return "lookCustomerRelationShip";
	}
	
	public String productTree() throws Exception {
		String orgNature = HttpTool.getParameter("orgNature") ; //回显选中ID;
		String comboName = HttpTool.getParameter("comboName"); // 查询参数;
		String projectTypes = HttpTool.getParameter("projectTypes");//项目类型  add by YoumingDeng 2016-09-26
		
		List<Combo> customerList = new LinkedList<Combo>();
		Map<String,List<Combo>> productMap = new HashMap<String,List<Combo>>();
		String customerIds = productMap.keySet().toString().replaceAll(" ", "");
		if(customerIds.length()>3){
			customerIds = customerIds.substring(1, customerIds.length()-1);
		}
		
		//修改为通过套餐参数模糊查找; modified by henry.xu 20160816;
		customerList = comboService.findListByLikeComboName(comboName,projectTypes);
			
			
		HttpTool.setAttribute("comboName", comboName);
		HttpTool.setAttribute("projectTypes", projectTypes); //项目类型  add by YoumingDeng 2016-09-26
		HttpTool.setAttribute("customerList", customerList);
		HttpTool.setAttribute("productMap", productMap);
		HttpTool.setAttribute("name", "2");
		
		if(!"".equals(orgNature) && orgNature!=null){
			String orgNatureNew = orgNature.replaceAll("'", "");
		    //获取id对应的名称;便于回显使用;
		    StringBuilder orgNatureName = new StringBuilder("");
		    String[] arrIds = orgNatureNew.split(",");
		    
		    for(int j=0 ; j<arrIds.length; j++) {
		    	Combo combo = comboService.findByComboId(arrIds[j]);
		    	if(combo != null) {
		    		if(j==0) {
		    			orgNatureName.append(combo.getComboName());
		    		} else {
		    			orgNatureName.append(",").append(combo.getComboName());
		    		}
		    	}
		    }
		    HttpTool.setAttribute("orgNature", orgNatureNew);
		    HttpTool.setAttribute("orgNatureName", orgNatureName);
		}else{
			HttpTool.setAttribute("orgNature", "");	
		}
		
		
		return "productTree";
	}
	
	
	/**
	 * 
	 * 查找带回所有套餐信息
	 * @return
	 */
	
	
	public String addProductServe(){
		String productId = HttpTool.getParameter("productId");
		List<Combo> elementList = comboService.findAll();
		/*List<ServiceGroup> groupList = groupService.findServiceGroup();*/
		HttpTool.setAttribute("elementList", elementList);
		/*HttpTool.setAttribute("groupList", groupList);*/
		HttpTool.setAttribute("productId", productId);
		return "addProductServe";
	}


	/**
	 * @客户列表页
	 */
	public String listCustomer() throws ParseException {
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		Map paramsMap = buildSearch();
		String orderField  = HttpTool.getParameter("orderField");
		String orderDirection = HttpTool.getParameter("orderDirection");
		if( paramsMap.containsKey("orderDirection")){
			paramsMap.remove("orderDirection");
		}
		if( paramsMap.containsKey("orderField")){
			paramsMap.remove("orderField");
		}
		if (StrUtils.isNotNullOrBlank(orderField)&&StrUtils.isNotNullOrBlank(orderDirection)) {
			paramsMap.put(orderField,orderDirection);
		}
		else {
			paramsMap.put("order_isDeleted", "asc");   
			paramsMap.put("order_createTime", "desc");
		}
		findByPage(page , paramsMap) ;
		HttpTool.setAttribute("orderField", orderField);
		HttpTool.setAttribute("orderDirection", orderDirection);
		return "listCustomer";
	}
	
	public Page findByPage(Page page , Map paramsMap){
		//paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		paramsMap.put("order_isDeleted", "asc");   
		paramsMap.put("order_createTime", "desc");
		customerService.findByPage(page , paramsMap) ;
		return page ;
	}
	
	/**
	 * 修改条件后查询Page方法导出使用;
	 * create by henry.xu
	 * 20160928
	 */
	@SuppressWarnings("rawtypes")
	public Page findPageByConditions(Page page, Map searchMap) {
		//参数;
		custRelaQuery = new CustomerRelationShipQuery();
		custRelaQuery.setBranchCommany(HttpTool.getParameter("custRelaQuery.branchCommany", ""));
		custRelaQuery.setCity(HttpTool.getParameter("custRelaQuery.city", ""));;
		custRelaQuery.setLinkName(HttpTool.getParameter("custRelaQuery.linkName", ""));
		custRelaQuery.setCombo(HttpTool.getParameter("custRelaQuery.combo", ""));
		custRelaQuery.setProjectOwner(HttpTool.getParameter("custRelaQuery.projectOwner", ""));
		custRelaQuery.setProvince(HttpTool.getParameter("custRelaQuery.province", ""));
		// add by Damian 2017-04-19 begin
		custRelaQuery.setProCode(HttpTool.getParameter("custRelaQuery.proCode", ""));
		custRelaQuery.setCustomerNameSimple(HttpTool.getParameter("custRelaQuery.customerNameSimple", ""));
		// add by Damian 2017-04-19 end

		//查询公司信息;custRelaQuery
		customerService.findCustomerRelationShipsByCondition(page, custRelaQuery) ;
		return page;
	}
	
	/**
	 * @合约添加签约信息列表页
	 */
	public String listCustomerForContract() throws ParseException {
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		Map paramsMap = buildSearch();
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		paramsMap.put("order_createTime", "desc");
		List<CustomerRelationShip> customerList= customerService.findByPage(page, paramsMap);
		page.setResults(customerList);
		HttpTool.setAttribute("contractId", HttpTool.getParameter("contractId"));
		return "listCustomerForContract";
	}
	/**
	 * @客户列表页
	 */
	public String lookUpCustomer() throws ParseException {
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		Map paramsMap = buildSearch();
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		paramsMap.put("order_createTime", "desc");
		List<CustomerRelationShip> customerList= customerService.findByPage(page, paramsMap);
		page.setResults(customerList);
		return "lookUpCustomer";
	}
	/**
	 * @客户列表页
	 */
	public String lookUpCustomerAll() throws ParseException {
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		Map paramsMap = buildSearch();
		paramsMap.put("order_createTime", "desc");
		List<CustomerRelationShip> customerList= customerService.findByPage(page, paramsMap);
		page.setResults(customerList);
		return "lookUpCustomerAll";
	}

	/**
	 * @进入添加客户信息页面
	 */
	public String addCustomer() {
		return "addCustomer";
	}
	
	/**
	 * @添加客户信息
	 */
	public String savecustomerRelationShip() throws IOException {
		JSONObject json = new JSONObject();
		try {
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			Date createTime = new Date();
			customerRelationShip.setCreateUserId(currentUser.getAccountName());
			customerRelationShip.setCreateTime(new Date());
			customerRelationShip.setIsDeleted(0);
			boolean flat =ajaxCheckCodeService.findHisByName("","",customerRelationShip.getBranchCommany().trim(),"",customerRelationShip.getCity(),"",customerRelationShip.getOwnedCompany());
			if(flat){
				json.accumulate("statusCode", 300);
				json.accumulate("message", "公司名称已存在！");
			}else{
				customerService.saveCustomerMessage(customerRelationShip,linkList, shipPros, bankList,currentUser.getAccountName(),createTime);
			}
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "closeCurrent");
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	
	
	/**
	 * @查看客户信息
	 */
	public String browCustomer(){
		Object[] obj = customerService.findCustomerById(id);
		/*String flag = HttpTool.getParameter("flag");
		HttpTool.setAttribute("flag", flag);*/
		customerRelationShip = (CustomerRelationShip) obj[0];
		linkList = (List<CustomerRelationshipLink>)obj[1];
		
		shipPros = customerService.findCustRelShipProsByCustRelShipId(id, null);
		List<ProjectType> proTypes = customerService.findProjectTypes();
		HttpTool.setAttribute("proTypes", proTypes);
		
		return "browCustomer";
	}
	
	 /**
     * @查看公司信息
     */
    public String browCustomerRelationShip(){
        List CRSList = customerService.findByCustomer(id);
        /*String flag = HttpTool.getParameter("flag");
        HttpTool.setAttribute("flag", flag);*/
        customerRelationShip=(CustomerRelationShip) CRSList.get(0);
        return "browCustomerRelationShip";
    }
	
	/**
	 * @修改客户信息前查询
	 */
	public String modifyResource(){
		customerRelationShip = (CustomerRelationShip) customerService.findById(id);
		shipPros = customerService.findCustRelShipProsByCustRelShipId(id, null);
		List<ProjectType> proTypes = customerService.findProjectTypes();
		HttpTool.setAttribute("proTypes", proTypes);
		return "modifyCustomer";
	}
	
	/**
	 * @停用客户
	 * @throws IOException
	 */
	public String deleteCustomer() throws IOException{
		JSONObject json = new JSONObject();
		try {
			User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
			String[] id = ids.replaceAll(" ", "").split(",");
			customerService.deleteCustomerRelationship(id, currentUser.getAccountName(),new Date());
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "");
			json.accumulate("forwardUrl", "");
			json.accumulate("confirmMsg", "");
		} catch (Exception e) {
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作失败");
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	/**
	 * @修改客户信息
	 */
	public String updateCustomer() throws IOException {
		JSONObject json = new JSONObject();
		try {
			Date time = new Date();
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			customerRelationShip.setUpdateTime(new Date());
			customerRelationShip.setUpdateUserId(currentUser.getAccountName());
			customerService.updateCustomerMessage(customerRelationShip,linkList, shipPros,currentUser.getAccountName(),time);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "closeCurrent");
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	
	public String findAllCustomer() throws Exception{
		StringBuffer json = new StringBuffer("[") ;
		List<CustomerRelationShip> customerList = customerService.findByCustomer();
		CustomerRelationShip customer = new CustomerRelationShip() ;
		if(customerList.size() > 0){
			for(int i = 0 ; i < customerList.size() ; i ++){
				customer = customerList.get(i) ;
				json.append("{\"text\":\"" + customer.getBranchCommany() + "\",\"id\":\"" + customer.getId() + "\",\"leaf\":" + false) ;
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
	 * 查询所有的套餐
	 * @return
	 * @throws Exception
	 */
	public String findAllCombo() throws Exception{
		User user=null;
		
		String eventsid=HttpTool.getParameter("eventsid");
		
		String eventsno=HttpTool.getParameter("eventsno");
		if(eventsno!=null){
			ErpEvents events=eventsService.queryEventNO(eventsno);
			eventsid=events.getId();
		}
		
		ErpEvents events= null;
		
		if(eventsid!=null&&!eventsid.equals("")){
			events=eventsService.get(eventsid);
			
			String branchCompany=events.getBranchCompany();
			String ownedCompany=events.getOwnedCompany();
			String createUserName=events.getCreateUserName();
			
			User u = new User();
			u.setExtension(branchCompany);
			u.setDeptName(ownedCompany);
			u.setUserName(createUserName);
			u.setJobNumber(events.getBranchCompanyId());
			user=userService.findUserByUserName(u);
			
		}
		
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		if(user!=null){
			currentUser=user;
			System.out.println("user不为空!"+user.getJobNumber());
		}
		
	
		StringBuffer json = new StringBuffer("[") ;
		List<CustomerRelationShip> customerList = customerService.findByCustomer(events.getBranchCompanyId());//参数为支公司ID，获取套餐大字段
		CustomerRelationShip customer = new CustomerRelationShip() ;
		if(customerList.size() > 0){
			for(int i = 0 ; i < customerList.size() ; i ++){
				customer = customerList.get(i) ;
				System.out.println("customer.getCombo():"+customer.getCombo());
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
	}
	
	/**
	 * 获取项目对应套餐
	 * @return
	 */
	public String findComboByEventNo() {
		String eventsno = HttpTool.getParameter("eventsno", "");
		this.jsonString = this.comboService.findComboByEventNo(eventsno);
		return "json";
	}
	
	public String test() throws Exception{
		String parentdictid = HttpTool.getParameter("deptId");
		List<CustomerRelationShip> list = customerService.findByOwnedCompany(parentdictid) ;
		StringBuffer json = new StringBuffer("{'storeApplicationBranchCommany':[");
		if(list!=null){
			int n=0;
			for (CustomerRelationShip customerRelationShip : list) {
				if(customerRelationShip.getBranchCommany().startsWith(HttpTool.getParameter("branchCommany"))){
					n++;
					json.append("{'branchCommany':'"+customerRelationShip.getBranchCommany()+"'},");
					if(n==5){
						break;
					}
				}
			}
			json.delete(json.length()-1,json.length());
    		json.append("]}");
    		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
    		PrintWriter out=ServletActionContext.getResponse().getWriter();
    		out.write(json.toString());
    		out.close();
		   return "success";
		}else{
			System.out.println("没有找到");
		}
		return null;
	}
	
	/**
	 * 根据员工表(um_user)的支公司ID（JOB_NUMBER）查询套餐，用于入门套餐下拉框
	 * @return String
	 * @throws Exception
	 * @author DengYouming
	 * modified by henry.xu 20161122根据提供需求,此处入门礼套餐来源支公司下对应项目对应的套餐; 原有代码已删除,可以通过svn版本控制找回;
	 * @since 2016-5-27 下午12:14:47
	 */
	public void findAllComboByJobNumber() throws Exception{
		//根据项目Id查询对应的数据;
		JSONObject json = new JSONObject();
		json.put("result", this.comboService.findComboByShipProId(id));
		renderJson(json);
	
	}
	
	/**
	 * 根据选择的支公司ID（JOB_NUMBER）查询套餐，用于入门套餐下拉框
	 * @return String
	 * @throws Exception
	 * @author henry.xu 20160920
	 */
	public String findComboByJobNumber() throws Exception{
		
		String companyId = HttpTool.getParameter("companyId", ""); //支公司Id
		//加"_"字符处理时因为前台返回值会默认加上一串数字,所以为了处理加上了该字符区分;
		String[] str = companyId.split("_"); 
		String id = ""; //处理后的支公司ID
		/*
		 * 执行判断当获取的数据不为空,且长度为uuid在进行处理;
		 */
		if(str != null && str.length > 0) {
			if(str[0].length() == 32) {
				id = str[0];				
			}
		}
		//获取对应支公司信息;
		List<CustomerRelationShip> ships = customerService.findCustomerByIds(id);
		
		/*
		 * 拼接支公司中分解多个套餐;
		 */
		CustomerRelationShip customer = new CustomerRelationShip() ;
		StringBuffer json = new StringBuffer("[") ;
		if(ships != null && ships.size() > 0){
			for(int i = 0 ; i < ships.size() ; i ++){
				customer = ships.get(i) ;
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
	
	}
	
	public String treeRegion() throws Exception{
		StringBuffer json = new StringBuffer("[") ;
		String parentdictid = HttpTool.getParameter("defaultID");
		System.out.println(parentdictid+"treeRegion refUrl parentdictid");
		List<CustomerRelationShip> list = customerService.findByOwnedCompany(parentdictid) ;
		if(list.size() > 0){
			for(int i = 0 ; i < list.size() ; i ++){
				customerRelationShip = (CustomerRelationShip)list.get(i) ;
				json.append("{\"text\":\"" + customerRelationShip.getBranchCommany() + "\",\"id\":\"" + customerRelationShip.getId()+ "\",\"leaf\":" + false) ;
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
	
	public String listCompanyQRCode() throws Exception{
        String branchCommany = HttpTool.getParameter("branchCommany");
        String customerNameSimple = HttpTool.getParameter("customerNameSimple");
        String projectType = HttpTool.getParameter("projectType");
        List<Map<String,Object>> projectTypeList = customerService.getProjectTypeList();
        HttpTool.setAttribute("projectTypeList", projectTypeList);
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
        customerService.findCustomerRelationShipsForQRCode(page,branchCommany,customerNameSimple,projectType);
        HttpTool.setAttribute("branchCommany", branchCommany);
        HttpTool.setAttribute("customerNameSimple", customerNameSimple);
        HttpTool.setAttribute("projectType", projectType);
        return "listCompanyQRCode";
    }
	
	public String printKeyWord(){
		String crid = HttpTool.getParameter("crid");
		String projectTypeCode = HttpTool.getParameter("projectTypeCode");
		CustomerRelationShip ship  = (CustomerRelationShip) customerService.findById(crid);
		HttpTool.setAttribute("crid", crid);
		HttpTool.setAttribute("ship", ship);
		HttpTool.setAttribute("projectTypeCode", projectTypeCode);
		return "printKeyWord";
	}
	
	public void checkShowCombo() throws Exception{
		//获取页面传过来的参数 项目ID
		String crid = HttpTool.getParameter("crid");
		//项目编码
		String projectCode = HttpTool.getParameter("projectCode");
		//项目类别编码
		String projectTypeCode = HttpTool.getParameter("projectTypeCode");
		JSONObject json = new JSONObject();
		//当项目类别是基因项目才进行后续操作
		if(!"PCT_001".equals(projectTypeCode)){
			json.put("status", "200");
			renderJson(json);
			return;
		}
		List<Map<String, Object>> list = customerService.checkShowCombo(crid,projectCode);
		boolean flag = false;
		json.put("status", "300");
		json.put("message", "该支公司不允许进行二维码生成！");
		//当前项目下的套餐如果都配置【是否前台显示】为否，或者【前台显示名称】都为空的，则不允许进行二维码生成。
		if(list!=null&&list.size()>0){
			for(Map<String, Object> map : list){
				if("1".equals(map.get("is_used"))&&map.get("combo_show_name")!=null&&StringUtils.isNotBlank((String) map.get("combo_show_name"))){
					flag = true;
					break;
				}
			}
			if(flag){
				json.put("status", "200");
			}
		}
		renderJson(json);
	}
	
	public void createQRCode() throws Exception{
		try{
			//获取页面传过来的参数
			String crid = HttpTool.getParameter("crid");
			CustomerRelationShip ship  = (CustomerRelationShip) customerService.findById(crid);
			String keyWord = HttpTool.getParameter("keyWord");
			//项目类型编码
			String projectTypeCode = HttpTool.getParameter("projectTypeCode");
			HttpTool.setAttribute("crid", crid);
			//调用接口  获取返回的二维码地址
			JSONObject json = new JSONObject();
			String xmlInfo = "<?xml version=\"1.0\" encoding=\"utf-8\"?><companyInfo>"
					+"<ownedCompanyId>"+ship.getOwnedCompany()+"</ownedCompanyId>"
					+"<ownedCompanyName>"+ship.getCustomerNameSimple()+"</ownedCompanyName>"
					+"<branchCompanyId>"+ship.getId()+"</branchCompanyId>"
					+"<branchCompanyName>"+ship.getBranchCommany()+"</branchCompanyName>"
					+"<projectType>"+projectTypeCode+"</projectType>"
					+"<keyword>"+keyWord+"</keyword>"
					+"<validHour></validHour>"
					+"</companyInfo>";
			YmGeneReportServiceImplServiceLocator impl = new YmGeneReportServiceImplServiceLocator();
			impl.setYmGeneReportServiceImplPortEndpointAddress(address);
			YmGeneReportServiceImpl serviceImpl = impl.getYmGeneReportServiceImplPort();
			String repData = "0";			//调用接口返回值	
			repData = serviceImpl.pushCompanyQRCodeInfoWuChuang(xmlInfo);
			log.info("接口返回值--"+repData);
			if(!repData.equals("0")){
				if(ship!=null){
					//获得返回的二维码地址  地址需要修改
					String viewPth = repData.replace("/home/ymdata/images/commpanyQrcode","http://img.healthlink.cn:8099/commpanyQrcode");
					log.info("viewP--"+viewPth);
					//验证数据库是否有重复信息
					boolean flag = customerService.checkRepeatByParam(crid,projectTypeCode);
					if(flag){
						json.put("status", "300");
						json.put("message", "已经生成，请勿重复生成！");
						renderJson(json);
						return;
					}
					//20170206新增中间表保存二维码地址
					CustomerShipCode shipCode = new CustomerShipCode();
					shipCode.setCompanyId(ship.getId());
					shipCode.setProjectType(projectTypeCode);
					shipCode.setQrCodeUrl(viewPth);
					shipCode.setKeyWord(keyWord);
					User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
					shipCode.setCreateUser(currentUser.getUserName());
					shipCode.setCreateTime(new Date());
					json.put("status", "200");
					json.put("message", "生成成功！");
					customerService.save(shipCode);
				}
			}else{
				json.put("status", "300");
				json.put("message", "生成失败！");
			}
			renderJson(json);
		}catch(Exception e){
			log.error("createQRCode---error:", e);
		}
	}
	
	public CustomerRelationShipService getCustomerService() {
		return customerService;
	}


	public void setCustomerService(CustomerRelationShipService customerService) {
		this.customerService = customerService;
	}


	public CustomerRelationshipLinkService getCustomerRelationshipLinkService() {
		return customerRelationshipLinkService;
	}


	public void setCustomerRelationshipLinkService(
			CustomerRelationshipLinkService customerRelationshipLinkService) {
		this.customerRelationshipLinkService = customerRelationshipLinkService;
	}


	public CustomerRelationshipComboService getCustomerRelationshipComboService() {
		return customerRelationshipComboService;
	}


	public void setCustomerRelationshipComboService(
			CustomerRelationshipComboService customerRelationshipComboService) {
		this.customerRelationshipComboService = customerRelationshipComboService;
	}


	public ComboService getComboService() {
		return comboService;
	}


	public void setComboService(ComboService comboService) {
		this.comboService = comboService;
	}


	public CustomerRelationShip getCustomer() {
		return customerRelationShip;
	}


	public void setCustomer(CustomerRelationShip customerRelationShip) {
		this.customerRelationShip = customerRelationShip;
	}


	public List<Combo> getBankList() {
		return bankList;
	}


	public void setBankList(List<Combo> bankList) {
		this.bankList = bankList;
	}


	public List<CustomerRelationshipLink> getLinkList() {
		return linkList;
	}


	public void setLinkList(List<CustomerRelationshipLink> linkList) {
		this.linkList = linkList;
	}
	public StoreApplication getStoreApplication() {
		return storeApplication;
	}


	public void setStoreApplication(StoreApplication storeApplication) {
		this.storeApplication = storeApplication;
	}


	public CustomerRelationShip getCustomerRelationShip() {
		return customerRelationShip;
	}


	public void setCustomerRelationShip(CustomerRelationShip customerRelationShip) {
		this.customerRelationShip = customerRelationShip;
	}

	public List<CustomerRelationShipPro> getShipPros() {
		return shipPros;
	}

	public void setShipPros(List<CustomerRelationShipPro> shipPros) {
		this.shipPros = shipPros;
	}
	

	public CustomerRelationShipQuery getCustRelaQuery() {
		return custRelaQuery;
	}

	public void setCustRelaQuery(CustomerRelationShipQuery custRelaQuery) {
		this.custRelaQuery = custRelaQuery;
	}

	public List<CustomerRelationShipVO> getShipVos() {
		return shipVos;
	}
}
