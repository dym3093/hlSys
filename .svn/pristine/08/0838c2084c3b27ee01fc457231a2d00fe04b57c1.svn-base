package org.hpin.base.priv.web;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.priv.service.AssignPrivService;
import org.hpin.base.resource.entity.Resource;
import org.hpin.base.resource.service.ResourceService;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.Role;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.base.usermanager.service.RoleService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.bg.system.util.Iuser;
import org.hpin.common.constant.UIConstants;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.SystemConstant;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;

/**
 * @author : wangzhiyong
 * 
 *
 */
@Namespace("/assign")
@Action("assignPriv")
@Results({
	@Result(name = "indexAssignPriv", location = "/WEB-INF/pages/system/priv/indexAssignPriv.jsp"),
	@Result(name = "modifyPriv", location = "/WEB-INF/pages/system/priv/modifyPriv.jsp")
})
public class AssignPrivAction extends BaseAction {
	private AssignPrivService assignPrivService = (AssignPrivService) SpringTool.getBean(AssignPrivService.class);
	private ResourceService  resourceService = (ResourceService) SpringTool.getBean(ResourceService.class);
	private DeptService deptService = (DeptService) SpringTool.getBean(DeptService.class);
	private UserService userService = (UserService) SpringTool.getBean(UserService.class);
	private RoleService roleService = (RoleService) SpringTool.getBean(RoleService.class);
	//权限分配类型：1部门，2用户，3角色
	Integer assignType;
	//分配对象的id（部门id 用户id 或角色id）
	String ownerId;
	//分配对象名称
	String ownerName;
	//Long owerId;
	String owerId;
	/**
	 * 显示授权主页面
	 */
	public String indexAssignPriv() throws Exception {
		return "indexAssignPriv";
	}
	/**
	 * 显示授权页面
	 */
	public String modifyPriv() throws Exception {
		if(assignType==1){
			ownerName = ((Dept)deptService.findById(ownerId)).getDeptName();
		}else if(assignType==2){
			User user = (User)userService.findById(ownerId);
			ownerName = user.getUserName();
			ownerId = user.getAccountName() ;
		}else if(assignType==3){
			ownerName = ((Role)roleService.findById(ownerId)).getName();
		}
		return "modifyPriv";
	}
	
	/**
	 * 生成子模块树JSON
	 * @return
	 * @throws Exception
	 */
	public String treeResource() throws Exception{
		JSONArray jsonRoot = new JSONArray();
		//Long parentId = StaticMethod.nullObject2Long(HttpTool.getParameter("parentId")) ;
		String parentId = HttpTool.getParameter("parentId") ;
		List<Resource> versionList = null ;
		User user = Iuser.getCurentUser();
		String userType = user.getUserType();
		//如果是超级管理员，则查询所以的权限
		if (null != userType && ("0".equals(userType) || "1".equals(userType) || "2".equals(userType))) {
			versionList = resourceService.findEnableMenuAndButtonByParentId(parentId) ;
		}else{
			versionList = assignPrivService.findEnableMenuByUserIdAndParentId(String.valueOf(Iuser.getCurentUser().getId()),parentId) ;
		}
		Resource resource = null ;
		if(versionList.size() > 0){
			for(int i = 0 ; i < versionList.size() ; i ++){
				resource = versionList.get(i);
				JSONObject jitem = new JSONObject();
				jitem.put("id", resource.getId());
				jitem.put("text",resource.getName());
				if(Integer.valueOf(1).equals(resource.getIsButton())){
					jitem.put("leaf",1);
				}else{
					jitem.put("leaf",0);
				}
				jsonRoot.add(jitem);
			}
		}
		
		this.jsonString = jsonRoot.toString();
		return "json";
	}
	
	/**
	 * 生成已分配的权限树JSON
	 * @return
	 * @throws Exception
	 */
	public String treeAssignResource() throws Exception{
		JSONArray jsonRoot = new JSONArray();
		//Long parentId = StaticMethod.nullObject2Long(HttpTool.getParameter("parentId")) ;
		String parentId = HttpTool.getParameter("parentId");
		List<Resource> resourceList = assignPrivService.findEnableMenuByParentId(ownerId, assignType, parentId) ;
		Resource resource = null ;
		if(resourceList.size() > 0){
			int size = resourceList.size();
			for(int i = 0 ; i < size ; i ++){
				resource = resourceList.get(i);
				JSONObject jitem = new JSONObject();
				jitem.put("id", resource.getId());
				jitem.put("text",resource.getName());
				if(Integer.valueOf(1).equals(resource.getIsButton())){
					jitem.put("leaf",1);
				}else{
					jitem.put("leaf",0);
				}
				jsonRoot.add(jitem);
			}
		}
		
		this.jsonString = jsonRoot.toString();
		return "json";
	}
	
	/**
	 * 根据用户id和父ID获取从部门和角色中获取的菜单
	 * @return
	 * @throws Exception
	 */
	public String treeExtendsResource() throws Exception{
		JSONArray jsonRoot = new JSONArray();
		//Long parentId = StaticMethod.nullObject2Long(HttpTool.getParameter("parentId")) ;
		String parentId = HttpTool.getParameter("parentId") ;
		//Long userId = ((User)userService.loadUserByUsername(ownerId)).getId();
		String userId = ((User)userService.loadUserByUsername(ownerId)).getId();
		List<Resource> resourceList = assignPrivService.findUserResExtendsDeptAndRoles(userId, parentId);
		Resource resource = null ;
		if(resourceList.size() > 0){
			int size = resourceList.size();
			for(int i = 0 ; i < size ; i ++){
				resource = resourceList.get(i);
				JSONObject jitem = new JSONObject();
				jitem.put("id", resource.getId());
				jitem.put("text",resource.getName());
				if(Integer.valueOf(1).equals(resource.getIsButton())){
					jitem.put("leaf",1);
				}else{
					jitem.put("leaf",0);
				}
				jsonRoot.add(jitem);
			}
		}
		
		this.jsonString = jsonRoot.toString();
		return "json";
	}
	
	/**
	 * 设置权限
	 * @return
	 * @throws Exception
	 */
	public String assignPriv() throws Exception {
		
		return "modifyPriv";
	}
	
	/**
	 * 设置权限
	 * @return
	 * @throws Exception
	 */
	public String addAssignPriv() throws Exception {
		this.jsonString = "{}";
		
		if(ownerId == null){
			this.jsonString = "{error:1,errorMsg:'要添加的权限拥有者为空'}";
			return "json";
		}
		if(assignType == null){
			this.jsonString = "{error:1,errorMsg:'权限拥有者类型为空'}";
			return "json";
		}
		String resIds = HttpTool.getParameter("resIds");
		if(StringUtils.isBlank(resIds)){
			this.jsonString = "{error:1,errorMsg:'要添加的权限参数为空'}";
			return "json";
		}
		String creatorId = Iuser.getCurentUser().getId();
		System.out.println(ownerId+"=="+resIds+"=="+assignType+"=="+creatorId);
		assignPrivService.addAssignByIds(ownerId, resIds, assignType, creatorId);
		
		return "json";
	}
	
	/**
	 * 删除权限
	 * @return
	 * @throws Exception
	 */
	public String deletePriv() throws Exception {
		this.jsonString = "{}";
		if(id == null){
			this.jsonString = "{error:1,errorMsg:'要删除的权限参数为空'}";
			return "json";
		}
		if(ownerId == null){
			this.jsonString = "{error:1,errorMsg:'要删除的权限拥有者为空'}";
			return "json";
		}
		if(assignType == null){
			this.jsonString = "{error:1,errorMsg:'权限拥有者类型为空'}";
			return "json";
		}
		Integer count = assignPrivService.deleteAssignPriv(ownerId, assignType, id);
		if(count == 0){
			this.jsonString = "{error:1,errorMsg:'要删除的权限数据库中不存在'}";
		}
		
		return "json";
	}
	
public String userTree() throws Exception{
		
		//Long node = StaticMethod.nullObject2Long(HttpTool.getParameter("node"));
		String node = HttpTool.getParameter("node");
		List<String> showParentOrgs = new ArrayList<String>() ;
		
		List<Dept> deptlist = new ArrayList<Dept>();
		List<User> userlist = new ArrayList<User>();
		if(node.equals("0")){
			String rentManagerCenter = SystemConstant.getSystemConstant("show_parent_org") ;
			String financeCenter = SystemConstant.getSystemConstant("show_parent_org_1") ;
			showParentOrgs.add(rentManagerCenter) ;
			showParentOrgs.add(financeCenter) ;
			deptlist = deptService.findDeptByTreepathList("0");
		}else{
			Dept dept = (Dept)deptService.findById(String.valueOf(node));
			deptlist = deptService.findByParentPath(node);
			
			page = new Page(HttpTool.getPageNum());
			Map searchMap = super.buildSearch();
			page.setPageSize(5000);
			searchMap.put("filter_and_deptId_EQ_S", dept.getId());
			userlist = userService.findByPage(page, searchMap);
		}
		JSONArray json = new JSONArray();  
		if (deptlist.size() > 0){
	    	for (int i = 0; i < deptlist.size(); i++) {
				Dept dept = deptlist.get(i);			
				JSONObject jitem = new JSONObject();
				jitem.put(UIConstants.JSON_ID, dept.getId());
				jitem.put(UIConstants.JSON_TEXT, dept.getDeptName());
				jitem.put("iconCls", "dept");
				jitem.put("leaf", 0);
				
				json.add(jitem);				
			}
		}
			
		if (userlist.size() > 0) {
			for (int j = 0; j < userlist.size(); j++) {
				User user = userlist.get(j);			
				JSONObject jitem = new JSONObject();
				jitem.put(UIConstants.JSON_ID, user.getId());
				jitem.put(UIConstants.JSON_TEXT, user.getUserName());
				jitem.put("leaf", 1);
				jitem.put("iconCls", "user");
				
				json.add(jitem);
			}
		}
		this.jsonString = json.toString();
		return "json";
	}
	
	public String getUser() throws Exception{
		
		String node = HttpTool.getParameter("node");
		List<String> showParentOrgs = new ArrayList<String>() ;
		List<Dept> deptlist = new ArrayList<Dept>();
		List<User> userlist = new ArrayList<User>();
		if(node.equals("0")){
			String rentManagerCenter = SystemConstant.getSystemConstant("show_parent_org") ;
			String financeCenter = SystemConstant.getSystemConstant("show_parent_org_1") ;
			showParentOrgs.add(rentManagerCenter) ;
			showParentOrgs.add(financeCenter) ;
			deptlist = deptService.findDeptByTreepathList("0");
		}else{
			Dept dept = (Dept)deptService.findById(String.valueOf(node));
			deptlist = deptService.findByParentPath(node);
			
			page = new Page(HttpTool.getPageNum());
			Map searchMap = super.buildSearch();
			page.setPageSize(1000);
			searchMap.put("filter_and_deptId_EQ_S", dept.getId());
			userlist = userService.findByPage(page, searchMap);
		}
		JSONArray json = new JSONArray();  
		if (deptlist.size() > 0){
	    	for (int i = 0; i < deptlist.size(); i++) {
				Dept dept = deptlist.get(i);			
				JSONObject jitem = new JSONObject();
				jitem.put(UIConstants.JSON_ID, dept.getId());
				jitem.put(UIConstants.JSON_TEXT, dept.getDeptName());
				jitem.put("iconCls", "dept");
				jitem.put("leaf", 0);
				
				json.add(jitem);				
			}
		}
			
		if (userlist.size() > 0) {
			for (int j = 0; j < userlist.size(); j++) {
				User user = userlist.get(j);			
				JSONObject jitem = new JSONObject();
				jitem.put(UIConstants.JSON_ID, user.getId());
				jitem.put(UIConstants.JSON_TEXT, user.getUserName());
				jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_USER);
				jitem.put("leaf", 1);
				jitem.put("iconCls", "user");
				
				json.add(jitem);
			}
		}
		this.jsonString = json.toString();
		return "json";
	}
	
	public String getRoles() throws Exception {
		//Long node = StaticMethod.nullObject2Long(HttpTool.getParameter("node"));
		String node = HttpTool.getParameter("node");
		int leaf = 1;
	
		List<Role> roles = roleService.getRolesByParentId(node);
		JSONArray json = new JSONArray();
		for (int j = 0; j < roles.size(); j++) {
			Role role = roles.get(j);			
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, role.getId());
			jitem.put(UIConstants.JSON_TEXT, role.getName());
			List<Role> sonroles = roleService.getRolesByParentId(role.getId());
			if( sonroles != null && sonroles.size()>0){
				leaf = 0;
			}
			jitem.put("leaf", leaf);
			
			json.add(jitem);
		}
		this.jsonString = json.toString();
		return "json";
	}
	
	public Integer getAssignType() {
		return assignType;
	}
	public void setAssignType(Integer assignType) {
		this.assignType = assignType;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwerId() {
		return owerId;
	}
	public void setOwerId(String owerId) {
		this.owerId = owerId;
	}
	
}
