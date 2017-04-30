package org.hpin.base.usermanager.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.resource.entity.Resource;
import org.hpin.base.resource.service.ResourceService;
import org.hpin.base.usermanager.entity.Role;
import org.hpin.base.usermanager.entity.RoleResource;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.entity.UserRole;
import org.hpin.base.usermanager.service.RoleService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.constant.UIConstants;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.SystemConstant;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;

/**
 * 角色Action
 * 
 * @author thinkpad
 * @data 2009-8-10
 */
@Namespace("/um")
@Results( {
	    @Result(name = "indexRole", location = "/WEB-INF/content/userManager/role/indexRole.jsp"),
		@Result(name = "listRole", location = "/WEB-INF/content/userManager/role/listRole.jsp"),
		@Result(name = "addRole", location = "/WEB-INF/content/userManager/role/addRole.jsp"),
		@Result(name = "modifyRole", location = "/WEB-INF/content/userManager/role/modifyRole.jsp"),
		@Result(name = "showGrantResource", location = "/WEB-INF/content/userManager/role/showGrantResource.jsp"),
		@Result(name = "subRole", location = "/WEB-INF/content/userManager/role/subRole.jsp") ,
		@Result(name = "getRoleJobNumTrees", location = "/WEB-INF/content/userManager/role/getRoleJobNumTrees.jsp") ,
		@Result(name = "getRoleUserTrees", location = "/WEB-INF/content/userManager/role/getRoleUserTrees.jsp") ,
		@Result(name = "getRoleUserTreeNewCheckBox" , location = "/WEB-INF/content/userManager/role/getRoleUserTreeNewCheckBox.jsp") ,
		@Result(name = "getRoleUserTreess", location = "/WEB-INF/content/userManager/role/getRoleUserTreess.jsp") ,
		@Result(name = "getRoleUserTreeNew" , location = "/WEB-INF/content/userManager/role/getRoleUserTreeNew.jsp") ,
		@Result(name = "getRoleUserTree", location = "/WEB-INF/content/userManager/role/getRoleUserTree.jsp") })
public class RoleAction extends BaseAction {

	private RoleService roleService = (RoleService) SpringTool
			.getBean(RoleService.class);

	private ResourceService resourceService = (ResourceService) SpringTool
			.getBean(ResourceService.class);
	
	private UserService userService = (UserService)SpringTool.getBean(UserService.class) ;
	
	private Role role = null;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * 显示角色列表
	 */
	public String listRole() throws Exception {
		String parentId = HttpTool.getParameter("parentId");
		String flush = HttpTool.getParameter("flush");
		page = new Page(HttpTool.getPageNum());
		Map searchMap = super.buildSearch();
		if (parentId == null) parentId = "0";
		searchMap.put("filter_and_parentId_EQ_S", parentId);
		roleService.findByPage(page, searchMap);
		HttpTool.setAttribute("parentId", parentId);
		HttpTool.setAttribute("flush", flush);
		return "listRole";
	}
	
	/**
	 * 显示角色框架首页
	 */
	public String indexRole() throws Exception {
		List roleList = roleService.findAll();
		if (role == null) role = new Role();
		role.setRoleList(roleList);
		return "indexRole";
	}

	/**
	 * 显示添加角色页面
	 */
	public String addRole() throws Exception {
		HttpTool.setAttribute("parentId", HttpTool.getParameter("parentId"));
		return "addRole";
	}

	/**
	 * 显示添加角色页面
	 */
	public String saveRole() throws Exception {
		roleService.save(role);

		//保存关系
		String userIds = HttpTool.getParameter("userIds");
		if(StringUtils.isNotEmpty(userIds)){
			roleService.saveUserRoles(role.getId(), userIds);
		}
		
		return jump("role!listRole.action?parentId="+role.getParentId()+"&flush=ture");
	}

	/**
	 * 修改角色
	 */
	public String modifyRole() throws Exception {
		role = (Role) roleService.findById(String.valueOf(id));
		
		StringBuffer userNames = new StringBuffer();
		StringBuffer userIds = new StringBuffer();
		List roleUsers = roleService.getUserRoles(id);
		int size = roleUsers.size();
		for(int i = 0; i < size; i++){
			Object[] roleUser = (Object[])roleUsers.get(i);
			userNames.append(roleUser[1]).append(",");
			userIds.append(roleUser[0]).append(",");
		}
		HttpTool.setAttribute("users", userNames);
		HttpTool.setAttribute("userIds", userIds);
		HttpTool.setAttribute("users", roleUsers);
		
		return "modifyRole";
	}

	/**
	 * 修改角色
	 */
	public String updateRole() throws Exception {
		roleService.update(role);
		
		//保存关系
		String userIds = HttpTool.getParameter("userIds");
		if(StringUtils.isNotEmpty(userIds)){
			roleService.saveUserRoles(role.getId(), userIds);
		}
		
		return jump("role!listRole.action?parentId="+role.getParentId()+"&flush=ture");
	}

	/**
	 * 删除角色
	 */
	public String deleteRole() throws Exception {
		roleService.deleteIds(ids);
		return jump("role!listRole.action?flush=ture");
	}

	/**
	 * 删除角色
	 */
	public String showGrantResource() throws Exception {
		HttpTool.setAttribute("roleId", HttpTool.getParameter("roleId"));
		return "showGrantResource";
	}

	/**
	 * 菜单授权树
	 */
	public String grantResourceTree() throws Exception {
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

	//duan update
	public String grantResource() throws Exception {
		String grantInfo = HttpTool.getParameter("grantInfo");
		
		String roleId = HttpTool.getParameter("roleId");
		boolean isSuccess = roleService.grantResource(roleId, grantInfo);
		if (isSuccess) {
			this.jsonString = "true";
		}
		return "json";
	}
	
	/**
	 * 角色人员树(单选)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getRoleUserTree() {
		try {
		String roleCode = HttpTool.getParameter("roleCode");
		List<Role> roleList = new ArrayList();
		if (roleCode != null) roleList = roleService.findRoleBycode(roleCode); 
		else roleList = roleService.findAll();
		List<UserRole> userRoleList = roleService.findAllUserRole();
		if (role == null) role = new Role();
		role.setRoleList(roleList);
		role.setUserRoleList(userRoleList);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return "getRoleUserTree";
	}
	
	/**
	 * 角色人员树(复选)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getRoleUserTrees() {
		try {
		String roleCode = HttpTool.getParameter("roleCode");
		List<Role> roleList = new ArrayList();
		if (roleCode != null) roleList = roleService.findRoleBycode(roleCode); 
		else roleList = roleService.findAll();
		List<UserRole> userRoleList = roleService.findAllUserRole();
		if (role == null) role = new Role();
		role.setRoleList(roleList);
		role.setUserRoleList(userRoleList);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return "getRoleUserTrees";
	}
	
	/**
	 * 角色人员树(单选)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getRoleUserTreess() {
		try {
		String roleCode = HttpTool.getParameter("roleCode");
		List<Role> roleList = new ArrayList();
		if (roleCode != null) roleList = roleService.findRoleBycode(roleCode); 
		else roleList = roleService.findAll();
		List<UserRole> userRoleList = roleService.findAllUserRole();
		if (role == null) role = new Role();
		role.setRoleList(roleList);
		role.setUserRoleList(userRoleList);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return "getRoleUserTreess";
	}
	
	/**
	 * 分配任务选择人员树
	 * @return
	 */
	public String getRoleUserTreeNew(){
		String roleCode = HttpTool.getParameter("roleCode") ;
		try{
			String deptIds = SystemConstant.getSystemConstant("allocation_users_dept_ids") ;
			String[] deptIdArray = deptIds.split(",") ;
			List<User> userList = new ArrayList<User>() ;
			for(String id : deptIdArray){
				List<User> _userList = userService.getUserListByDeptId(id) ;
				userList.addAll(_userList) ;
			}
			HttpTool.setAttribute("userList", userList) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
		if(StringUtils.isBlank(roleCode)){
			return "getRoleUserTreeNew" ;
		}else{
			return "getRoleUserTreeNewCheckBox" ;
		}
	}
	
	/**
	 * 角色人员树(复选)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getRoleJobNumTrees() {
		try {
		String roleCode = HttpTool.getParameter("roleCode");
		List<Role> roleList = new ArrayList();
		if (roleCode != null) roleList = roleService.findRoleBycode(roleCode); 
		else roleList = roleService.findAll();
		List<UserRole> userRoleList = roleService.findAllUserRole();
		if (role == null) role = new Role();
		role.setRoleList(roleList);
		role.setUserRoleList(userRoleList);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return "getRoleJobNumTrees";
	}
	
	/**
	 * 获取角色树
	 * @return
	 */
	public String getRoleTree() {
		JSONArray json = new JSONArray() ;
		String node = HttpTool.getParameter("node") ;
		List<Role> parentList = roleService.getRolesByParentId(StringUtils.isBlank(node) ? "0" : node) ;
		for(Role role : parentList){
			JSONObject jitem = new JSONObject() ;
			jitem.put(UIConstants.JSON_ID, role.getId());
			jitem.put(UIConstants.JSON_TEXT, role.getName());
			jitem.put("iconCls", "dept");
			List<Role> childrenRole = roleService.getRolesByParentId(role.getId()) ;
			jitem.put("leaf", childrenRole == null || childrenRole.size() == 0 ? 1 : 0);
			if(childrenRole == null || childrenRole.size() == 0){
				jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_USER);
			}
			json.add(jitem);
		}
		super.jsonString = json.toString() ;
		return "json" ;
	}
}
