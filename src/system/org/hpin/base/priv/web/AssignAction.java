package org.hpin.base.priv.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.priv.service.AssignService;
import org.hpin.base.resource.entity.Resource;
import org.hpin.base.resource.service.ResourceService;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.RoleResource;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.base.usermanager.service.RoleService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.bg.system.util.Iuser;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.SystemConstant;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StaticMethod;
import org.hpin.common.widget.pagination.Page;

/**
 * @author : 张艳
 * 
 *
 */
@SuppressWarnings("serial")
@Namespace("/assign")
@Action("assignAction")
@Results({
	@Result(name="listAllAssignCount" ,location = "/WEB-INF/system/priv/listAllAssignCount.jsp"),
	@Result(name="listUser" ,location = "/WEB-INF/system/priv/listUser.jsp"),
	@Result(name="listRole" ,location = "/WEB-INF/system/priv/listRole.jsp"),
	@Result(name="listOrg" ,location = "/WEB-INF/system/priv/listOrg.jsp"),
	@Result(name="showGrantResource" ,location = "/WEB-INF/system/priv/showGrantResource.jsp"),
	@Result(name="listUserDetail" ,location = "/WEB-INF/system/priv/listUserDetail.jsp"),
	@Result(name="listOrgUserDetail" ,location = "/WEB-INF/system/priv/listOrgUserDetail.jsp"),
	@Result(name = "left", location = "/left.jsp")
})
public class AssignAction extends BaseAction {
	private AssignService assignService = (AssignService) SpringTool.getBean(AssignService.class);
	private UserService userService = (UserService) SpringTool.getBean(UserService.class);
	private RoleService roleService = (RoleService) SpringTool.getBean(RoleService.class);
	private DeptService deptService = (DeptService) SpringTool.getBean(DeptService.class);
	private ResourceService resourceService = (ResourceService) SpringTool
	.getBean(ResourceService.class);

	//显示角色下的用户
	@SuppressWarnings("rawtypes")
	public String listUserDetail() throws Exception{
		Long roleId = StaticMethod.nullObject2Long(HttpTool.getParameter("roleId"));
		page = new Page(HttpTool.getPageNum());
		Map searchMap = super.buildSearch();
		assignService.findUserByPage(page, searchMap, roleId);
		return "listUserDetail";
	}
	//显示部门下的用户
	@SuppressWarnings("rawtypes")
	public String listOrgUserDetail() throws Exception{
		Long orgId = StaticMethod.nullObject2Long(HttpTool.getParameter("orgId"));
		page = new Page(HttpTool.getPageNum());
		Map searchMap = super.buildSearch();
		assignService.findOrgUserByPage(page, searchMap, orgId);
		return "listOrgUserDetail";
	}
	public String listAllAssignCount() throws Exception{
		return "listAllAssignCount";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public String listUser() throws Exception{
		Long paramValue = StaticMethod.nullObject2Long(HttpTool.getParameter("pass_orgId")) ;
		if(paramValue != -100l && !paramValue.equals(-100l)){
			page = new Page(HttpTool.getPageNum());
			Map searchMap = super.buildSearch();
			HttpServletRequest request = ServletActionContext.getRequest() ;
			String orgIds[] = request.getParameterValues("pass_orgId") ;
			Long orgId = -1l ;
			if(orgIds.length > 0){
				orgId = StaticMethod.nullObject2Long(orgIds[0]) ;
			}
			Dept dept = (Dept) deptService.findById(String.valueOf(orgId));
			if (dept != null) {
				searchMap.put("filter_and_deptId_EQ_S", String.valueOf(dept.getDeptCode()));
			}
			HttpTool.setAttribute("org", dept);
			userService.findByPage(page, searchMap);
		}else{
			page = new Page(HttpTool.getPageNum());
			Map searchMap = super.buildSearch();
			List<String> showParentOrgs = new ArrayList<String>() ;
			String rentManagerCenter = SystemConstant.getSystemConstant("show_parent_org") ;
			String financeCenter = SystemConstant.getSystemConstant("show_parent_org_1") ;
			showParentOrgs.add(rentManagerCenter) ;
			showParentOrgs.add(financeCenter) ;
			//List<Org> list = orgService.findByParentPath(hlTreePath);
//			List<Org> orgList = new ArrayList<Org>();
//			List<Org> orgTempList = orgService.findOrgByTreepathList(showParentOrgs, "HL001") ;
//			for(Org org : orgTempList){
//				orgList.add(org) ;
//				List<Org> childList1 = orgService.findByParentPath(org.getHlTreepath()) ;
//				for(Org org1 : childList1){
//					orgList.add(org1) ;
//					List<Org> childList2 = orgService.findByParentPath(org1.getHlTreepath()) ;
//					for(Org org2 : childList2){
//						orgList.add(org2) ;
//						List<Org> childList3 = orgService.findByParentPath(org2.getHlTreepath()) ;
//						for(Org org3 : childList3){
//							orgList.add(org3) ;
//						}
//					}
//				}
//			}
//			if(orgList != null && !orgList.isEmpty()){
//				List<String> deptIdList = new ArrayList<String>() ;
// 				for(int i = 0 ; i < orgList.size() ; i ++){
//					Org org = (Org)orgList.get(i) ;
//					if(i == 0){
//						searchMap.put("filter_and_deptId_EQ_S", org.getDeptid()) ;
//					}else{
//						deptIdList.add(org.getDeptid()) ;
//					}
//				}
//				userService.findByPageOfParamsList(page, searchMap , deptIdList);
//			}
		}
		return "listUser";
		
	}
	public String listRole() throws Exception{
		page = new Page(HttpTool.getPageNum());
		Map searchMap = super.buildSearch();
		roleService.findByPage(page, searchMap);
		return "listRole";
	}
	public String listOrg() throws Exception{
		return "listOrg";
	}
	/**
	 * 
	 */
	public String showGrantResource() throws Exception {
		HttpTool.setAttribute("roleId", HttpTool.getParameter("roleId"));
		HttpTool.setAttribute("objectType", HttpTool.getParameter("tag"));
		
		return "showGrantResource";
	}

	/**
	 * 菜单授权树
	 */
	public String grantResourceTree() throws Exception {
		//Long roleId = HttpTool.getLongParameter("roleId");
		String roleId = HttpTool.getParameter("roleId");
		// 角色菜单集合
		List roleResourceList = roleService.findRoleResource(roleId);
		// 根菜单
		Resource rootResource = resourceService.findRootResource();
		StringBuffer json = new StringBuffer("[");
		json.append("{\"text\":\"系统菜单\"," + "\"leaf\":"
				+ rootResource.getIsLeaf());
		json.append(",\"state\":\"closed\"");
		recursionResourceForJson(json, rootResource, roleResourceList);
		json.append("},{\"text\":\"系统操作\"");
		json.append(",\"state\":\"closed\"");
		recursionOperationForJson(json, roleResourceList);
		json.append("}]");
		this.jsonString = json.toString();
		return "json";
	}
	/**
	 * 遍历子菜单拼写JSON
	 */
	private void recursionResourceForJson(StringBuffer json, Resource menu,
			List<RoleResource> roleResourceList) {
		List<Resource> childMenuList = resourceService
				.findEnableMenuByParentId(menu.getId());
		boolean isLeaf = false;
		// 是否含有子菜单,有子菜单，则遍历子菜单
		if (childMenuList.isEmpty()) {
			return;
		}
		json.append(",\"children\":[");
		// 遍历子菜单
		for (int i = 0; i < childMenuList.size(); i++) {
			// 此叶子菜单是否已经被付权限
			boolean hasGrant = false;
			Resource childMenu = childMenuList.get(i);
			isLeaf = childMenu.getIsLeaf() == 0 ? false : true;
			json.append("{\"id\":\"menu" + childMenu.getId() + "\"" + ","
					+ "\"text\":" + "\"" + childMenu.getName() + "\"");
			// 如果是叶子节点，则判断是否已经被赋权，并且判断操作权限赋权情况
			if (isLeaf) {
				// 否已经被赋权
				for (int j = 0; j < roleResourceList.size(); j++) {
					if (roleResourceList.get(j).getResourceId().compareTo(
							childMenu.getId()) == 0) {
						hasGrant = true;
					}
				}
				// 如果被赋权，则checkbox被选上
				if (hasGrant) {
					json.append(",\"checked\":true");
				} else {
					json.append(",\"checked\":false");
				}
			}
			if (isLeaf) {
				json.append(",\"leaf\":true");
			} else {
				json.append(",\"expanded\":\"true\"");
				json.append(",\"leaf\":false");
			}
			recursionResourceForJson(json, childMenu, roleResourceList);
			if (i >= 0 && i < childMenuList.size() - 1) {
				json.append("},");
			}
		}
		json.append("}]");
	}
	private void recursionOperationForJson(StringBuffer json,
			List<RoleResource> roleResourceList) {
		List<Resource> operationList = resourceService.findEnableOperation();
		if (operationList.isEmpty()) {
			return;
		}
		json.append(",\"children\":[");
		for (int i = 0; i < operationList.size(); i++) {
			boolean hasGrant = false;
			Resource operation = operationList.get(i);
			json.append("{\"id\":\"operation" + operation.getId() + "\"" + ","
					+ "\"text\":" + "\"" + operation.getName() + "\"");
			// 否已经被赋权
			for (int j = 0; j < roleResourceList.size(); j++) {
				if (roleResourceList.get(j).getResourceId().compareTo(
						operation.getId()) == 0) {
					hasGrant = true;
				}
			}
			// 如果被赋权，则checkbox被选上
			if (hasGrant) {
				json.append(",\"checked\":true");
			} else {
				json.append(",\"checked\":false");
			}
			json.append(",\"leaf\":true");
			if (i >= 0 && i < operationList.size() - 1) {
				json.append("},");
			}
		}
		json.append("}]");
	}
	public String grantResource() throws Exception {
		String grantInfo = HttpTool.getParameter("grantInfo");
		String roleId = HttpTool.getParameter("roleId");
		Integer objectType = HttpTool.getIntegerParameter("objectType");
//		if(objectType==3){
//			Org org = (Org) orgService.findById(String.valueOf(roleId));
//			List<Org> orgList = new ArrayList<Org>();
//			List<Org> childList1 = orgService.findByParentPath(org.getHlTreepath()) ;
//			if(org.getName().indexOf("财务中心")>=0){
//				for(Org org1 : childList1){
//					if(org1.getName().indexOf("资金管理部") < 0 && org1.getName().indexOf("会计业务核算部") < 0){
//						continue;
//					}else{
//						orgList.add(org1) ;
//						List<Org> childList2 = orgService.findByParentPath(org1.getHlTreepath()) ;
//						for(Org org2 : childList2){
//							orgList.add(org2) ;
//							List<Org> childList3 = orgService.findByParentPath(org2.getHlTreepath()) ;
//							for(Org org3 : childList3){
//								orgList.add(org3) ;
//							}
//						}
//					}
//				}
//			}else{
//				for(Org org1 : childList1){
//					
//						orgList.add(org1) ;
//						List<Org> childList2 = orgService.findByParentPath(org1.getHlTreepath()) ;
//						for(Org org2 : childList2){
//							orgList.add(org2) ;
//							List<Org> childList3 = orgService.findByParentPath(org2.getHlTreepath()) ;
//							for(Org org3 : childList3){
//								orgList.add(org3) ;
//							}
//						}
//					
//				}
//			}
//			if(orgList!=null){
//				for (Org org2 : orgList) {
//					//Long roleId1 = org2.getId();
//					String roleId1 = org2.getId();
//					boolean isSuccess = assignService.grantResource(roleId1, grantInfo,objectType);
//					if (isSuccess) {
//						this.jsonString = "true";
//					}
//				}
//			}
//		}
		boolean isSuccess = assignService.grantResource(roleId, grantInfo,objectType);
		if (isSuccess) {
			this.jsonString = "true";
		}
		return "json";
	}
	/**
	 * 显示部门树
	 */
	public String treeOrgMain() throws Exception {
		//String hlTreePath = HttpTool.getParameter("hlTreePath");
		List<String> showParentOrgs = new ArrayList<String>() ;
		String rentManagerCenter = SystemConstant.getSystemConstant("show_parent_org") ;
		String financeCenter = SystemConstant.getSystemConstant("show_parent_org_1") ;
		showParentOrgs.add(rentManagerCenter) ;
		showParentOrgs.add(financeCenter) ;
		//List<Org> list = orgService.findByParentPath(hlTreePath);
//		List<Org> list = orgService.findOrgByTreepathList(showParentOrgs, "HL001");
		StringBuffer json = new StringBuffer("[");
//		Org org = null;
//		for (int i = 0; i < list.size(); i++) {
//			org = list.get(i);
//			boolean isLeaf = true;
//			if (StringUtils.isBlank(org.getHlTreepath())) {
//				isLeaf = true;
//			} else {
//				List<Org> childList = orgService.findByParentPath(org
//						.getHlTreepath());
//				if (childList != null && childList.size() > 0) {
//					isLeaf = false;
//				}
//			}
//			json.append("{\"text\":" + "\"" + org.getName() + "\"" + ","
//					+ "\"id\":" + "\"" + org.getId() + "\""
//					+ ",\"hlTreePath\":" + "\"" + org.getHlTreepath() + "\""
//					+ ",\"leaf\":" + isLeaf);
//			json.append("},");
//		}
		if (json.toString().endsWith(",")) {
			json = json.delete(json.length() - 1, json.length());
		}

		json.append("]");
		this.jsonString = json.toString();
		return "json";
	}
	/**
	 * 根据父部门信息显示下一级部门树
	 */
	public String treeOrg() throws Exception {
//		String hlTreePath = HttpTool.getParameter("hlTreePath");
//		List<Org> list = orgService.findByParentPath(hlTreePath);
//		StringBuffer json = new StringBuffer("[");
//		Org org = null;
//		for (int i = 0; i < list.size(); i++) {
//			org = list.get(i);
//			if(org.getName().indexOf("计划财务部") >= 0 || org.getName().indexOf("审计部") >= 0 ) continue ;
//			boolean isLeaf = true;
//			if (StringUtils.isBlank(org.getHlTreepath())) {
//				isLeaf = true;
//			} else {
//				List<Org> childList = orgService.findByParentPath(org
//						.getHlTreepath());
//				if (childList != null && childList.size() > 0) {
//					isLeaf = false;
//				}
//			}
//			json.append("{\"text\":" + "\"" + org.getName() + "\"" + ","
//					+ "\"id\":" + "\"" + org.getId() + "\""
//					+ ",\"hlTreePath\":" + "\"" + org.getHlTreepath() + "\""
//					+ ",\"leaf\":" + isLeaf);
//			json.append("},");
//		}
//		if (json.toString().endsWith(",")) {
//			json = json.delete(json.length() - 1, json.length());
//		}
//
//		json.append("]");
//		this.jsonString = json.toString();
		return "json";
	}
	
	/**
	 * 根据模块ID显示菜单树
	 */
	public String getResources() throws Exception {
		String parentIds = HttpTool.getParameter("parentIds");
		String[] arrIds = parentIds.split(",");
		
		
		Set<String> setIds = new HashSet<String>();
		CollectionUtils.addAll(setIds, arrIds);
		List<Resource> parentMunus = new ArrayList<Resource>();
		
		Map<Long,List<Resource>> subMenus = new HashMap<Long,List<Resource>>();
		for(String parentId: arrIds){
			subMenus.put(Long.valueOf(parentId), new ArrayList<Resource>());
		}

		// 遍历用户可以访问的所有菜单
		List<Resource> menus = Iuser.getCurentUser().getResourceList();
		StringBuffer json = new StringBuffer("[");
		for (int i = 0; i < menus.size(); i++) {
			Resource menu = menus.get(i);
			if(setIds.contains(menu.getId().toString())){
				parentMunus.add(menu);
			}
			if (null != menu.getParentId()
					&& setIds.contains(menu.getParentId().toString())) {
				List<Resource> tmp = subMenus.get(menu.getParentId());
				tmp.add(menu);
			}
		}
		
		ServletActionContext.getRequest().setAttribute("subMenus", subMenus);
		ServletActionContext.getRequest().setAttribute("parentMunus", parentMunus);
		return "left";
	}
}
