package org.hpin.base.usermanager.web;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.dict.entity.SysDictType;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.SystemConstant;
import org.hpin.common.core.exception.BusinessException;
import org.hpin.common.core.exception.ExceptionConstant;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.ReflectionUtils;
import org.hpin.common.widget.pagination.Page;
import org.json.simple.JSONArray;

/**
 * 部门Action
 * 
 * @author thinkpad
 * @data Oct 8, 2009
 */
@Namespace("/um")
@Action("dept")
@Results( {
		@Result(name = "indexDept", location = "/WEB-INF/content/userManager/dept/indexDept.jsp"),
		@Result(name = "listDept", location = "/WEB-INF/content/userManager/dept/listDept.jsp"),
		@Result(name = "lookDeptUserTree", location = "/WEB-INF/content/userManager/user/lookDeptUserTree.jsp"),
		@Result(name = "deptUserTree", location = "/WEB-INF/content/userManager/user/deptUserTree.jsp"),
		@Result(name = "getDeptUserTree", location = "/WEB-INF/content/userManager/user/getDeptUserTree.jsp"),
		@Result(name = "listSelectedDept", location = "/WEB-INF/content/userManager/dept/listSelectedOrg.jsp"),
		@Result(name = "addDept", location = "/WEB-INF/content/userManager/dept/addDept.jsp"),
		@Result(name = "modifyDept", location = "/WEB-INF/content/userManager/dept/modifyDept.jsp"),
		@Result(name = "modifyPriv", location = "/WEB-INF/content/userManager/dept/modifyPriv.jsp") })
public class DeptAction extends BaseAction {

	private DeptService deptService = (DeptService) SpringTool
			.getBean(DeptService.class);

	private UserService userService = (UserService) SpringTool
	.getBean(UserService.class);

	public Dept dept = null;

	public Dept getDept() {
		return dept;
	}

	public void setOrg(Dept dept) {
		this.dept = dept;
	}
	/**
	 * 字段树ON
	 * @return
	 * @throws Exception
	 */
	public String treeRegion() throws Exception{
		StringBuffer json = new StringBuffer("[") ;
		String parentdictid = HttpTool.getParameter("defaultID");
		List<Dept> hpinSystemDictTypeList = deptService.findByParentId(parentdictid) ;
		if(hpinSystemDictTypeList.size() > 0){
			for(int i = 0 ; i < hpinSystemDictTypeList.size() ; i ++){
				Dept hpinSystemDictType = new Dept();
				hpinSystemDictType = hpinSystemDictTypeList.get(i) ;
				json.append("{\"text\":\"" + hpinSystemDictType.getDeptName() + "\",\"id\":\"" + hpinSystemDictType.getId()+ "\",\"leaf\":" + false) ;
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
	 * 显示框架页面
	 */
	public String indexDept() throws Exception {
		List<Dept> deptList = deptService.findAllDept();
		if (dept == null) dept = new Dept();
		dept.setDeptList(deptList);
		return "indexDept";
	}
	
	public String deptUserTree() {
		String deptId = HttpTool.getParameter("deptId");
		if (deptId == null) deptId = "0";
		this.jsonString = deptService.getDeptUser(deptId);
		
		return null;
	}
	
	
	/**
	 * 部门树
	 * @throws Exception 
	 * */
	@SuppressWarnings("unchecked")
	public String lookDeptUserTree() throws Exception {
		Map <String,List<User>> deptUserMap=new HashMap<String,List<User>>();
		Map <String,List<Dept>> dept2Map=new HashMap<String, List<Dept>>();
		Map <String,List<Dept>> dept1Map=new HashMap<String, List<Dept>>();
		List<User> userList=deptService.findUser();
		for(User user : userList){//用户list
			List<User> _userList = deptUserMap.get(user.getDeptId());
			if(null==_userList){
				_userList = new LinkedList<User>();
			}
			_userList.add(user);
			deptUserMap.put(user.getDeptId(), _userList);
		}
		
		String keys = deptUserMap.keySet().toString().replaceAll(",", "','").replaceAll(" ", "").replace("[", "'").replace("]", "'");
		List<Dept> dept3List=deptService.findDeptByDeptLevel(3,keys);
		keys = createDeptMap(dept2Map,keys,dept3List);
		List<Dept> dept2List=deptService.findDeptByDeptLevel(2,keys);
		keys = createDeptMap(dept1Map,keys,dept2List);
		List<Dept> dept1List=deptService.findDeptByDeptLevel(1,keys);
		
		JSONArray _jsonDept1=new JSONArray();
		for(Dept dept1 : dept1List){
			List<Dept> _dept2List = dept1Map.get(dept1.getId());
			JSONArray _jsonDept2 = new JSONArray();
			if(null!=_dept2List&&_dept2List.size()>0){
				for(Dept dept2:_dept2List){
					List<Dept> _dept3List = dept2Map.get(dept2.getId());
					JSONArray _jsonDept3 = new JSONArray();
					if(null!=_dept3List&&_dept3List.size()>0){
						for(Dept dept3:_dept3List){
							addJsonElement(_jsonDept3,new JSONArray(),dept3,deptUserMap);
						}
					}
					addJsonElement(_jsonDept2,_jsonDept3,dept2,deptUserMap);
				}
			}
			addJsonElement(_jsonDept1,_jsonDept2,dept1,deptUserMap);
		}
		HttpTool.setAttribute("data", _jsonDept1.toString());
		return "lookDeptUserTree";
	}
	
	@SuppressWarnings("unchecked")
	public void addJsonElement(JSONArray _jsonDept,JSONArray jsonDept,Dept dept,Map<String, List<User>> deptUserMap){
		JSONObject _json=new JSONObject();
		_json.accumulate("name", dept.getDeptName());
		_json.accumulate("dept", jsonDept);
		_json.accumulate("user", userListToJson(dept.getId(),deptUserMap));
		_jsonDept.add(_json);
	}
	
	public String createDeptMap(Map<String,List<Dept>> deptMap,String keys,List<Dept> deptList){
		for(Dept dept:deptList){
			
			List<Dept> _deptList = deptMap.get(dept.getParentId());
			if(null==_deptList){
				_deptList = new LinkedList<Dept>();
			}
			_deptList.add(dept);
			deptMap.put(dept.getParentId(), _deptList);
			if(keys.indexOf(dept.getParentId())<0){
				keys +=",'"+dept.getParentId()+"'";
			}
		}
		return keys;
	}
	@SuppressWarnings("unchecked")
	public JSONArray userListToJson(String id, Map<String, List<User>> deptUserMap){
		JSONArray _jsonUser = new JSONArray();
		List<User> list = deptUserMap.get(id);
		if(null!=list&&list.size()>0){
			for(User u : list){
				JSONObject jsonUser = new JSONObject();
				jsonUser.accumulate("id", u.getId());
				jsonUser.accumulate("name", u.getUserName());
				jsonUser.accumulate("jobNumber", u.getJobNumber());
				_jsonUser.add(jsonUser);
			}
		}
		return _jsonUser;
	}
	
	/**
	 * 部门人员树
	 * @return
	 */
	public String getDeptUserTree() {
		List<Dept> deptList = deptService.findAllDept();
		List<User> userList = deptService.findAllUser();
		if (dept == null) dept = new Dept();
		dept.setDeptList(deptList);
		dept.setUserList(userList);
		return "getDeptUserTree";
	}	
	
	/**
	 * 根据父ID显示Org列表
	 */
	public String listDept() throws Exception {
		
		List deptList = new ArrayList();
		String deptId = HttpTool.getParameter("deptId");
		String level  = HttpTool.getParameter("level");
		String flush  = HttpTool.getParameter("flush"); 
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum());
		page.setPageSize(10);
		if (deptId != null && level != null) {
			searchMap.put("filter_and_parentId_EQ_S", deptId);
			searchMap.put("filter_and_deptLevel_EQ_I", Integer.parseInt(level));
		}
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		deptList = deptService.findByPage(page, searchMap);
		page.setResults(deptList) ;
		this.jsonString = deptService.getDeptUser("0");
		HttpTool.setAttribute("flush", flush);
		return "listDept" ;
	}
	
	/**
	 * 弹出窗口中部门
	 */
	public String selectDept() throws Exception {
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum());
		String parameter =  HttpTool.getParameter("pass_hlTreePath");
		//String orgId =  HttpTool.getParameter("pass_orgId");
		if("undefined".equals(parameter)){
			parameter = null;
		}
		searchMap.put("filter_and_parentPath_EQ_S", parameter);
		deptService.findByPage(page, searchMap);
		return "selectDept";
	}
	
	/**
	 * 弹出窗口中Org列表
	 */
	public String listSelectedDept() throws Exception {
		String nameId = HttpTool.getParameter("nameId");
		String valueId = HttpTool.getParameter("valueId");
		String fun = HttpTool.getParameter("fun");
		HttpTool.setAttribute("nameId", nameId);
		HttpTool.setAttribute("valueId", valueId);
		HttpTool.setAttribute("fun", fun);
		page = new Page(HttpTool.getPageNum());
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_hlDeptGra_EQ_S", "350");//店组
		searchMap.put("filter_and_setid_EQ_S", "HL001");//北京的部门
		//deptService.findAllByPage(page, searchMap);
		return "listSelectedDept";
	}

	/**
	 * 显示部门树
	 */
	public String treeDeptMain() throws Exception {
		//String hlTreePath = HttpTool.getParameter("hlTreePath");
		List<String> showParentOrgs = new ArrayList<String>() ;
		String rentManagerCenter = SystemConstant.getSystemConstant("show_parent_org") ;
		String financeCenter = SystemConstant.getSystemConstant("show_parent_org_1") ;
		showParentOrgs.add(rentManagerCenter) ;
		showParentOrgs.add(financeCenter) ;
		//List<Org> list = orgService.findByParentPath(hlTreePath);
		//List<Dept> list = deptService.findDeptByTreepathList(showParentOrgs);
		List<Dept> list = null;
		StringBuffer json = new StringBuffer("[");
//		Dept dept = null;
//		if( list != null ){
//		for (int i = 0; i < list.size(); i++) {
//			dept = list.get(i);
//			boolean isLeaf = true;
//			if (StringUtils.isBlank(org.getHlTreepath())) {
//				isLeaf = true;
//			} else {
//				List<Dept> childList = deptService.findByParentPath(org
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
	 * 根据父部门信息显示下一级部门树
	 */
	public String treeDept() throws Exception {
		String hlTreePath = HttpTool.getParameter("hlTreePath");
//		List<Org> list = orgService.findByParentPath(hlTreePath);
//		StringBuffer json = new StringBuffer("[");
//		Org org = null;
//		for (int i = 0; i < list.size(); i++) {
//			org = list.get(i);
//			//if(org.getName().indexOf("计划财务部") >= 0 || org.getName().indexOf("审计部") >= 0 ) continue ;
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
	 * 显示添加部门页面
	 */
	public String addDept() throws Exception {
		String parentId = HttpTool.getParameter("parentId");
		Dept parent = new Dept();
		if ("0".equals(parentId)) {
			parent.setId("0");
			parent.setDeptName("部门树");
			parent.setDeptLevel(1);
		}
		else {
			parent = (Dept) deptService.findById(parentId);
			parent.setDeptLevel(parent.getDeptLevel()+1);
		}
		HttpTool.setAttribute("parent", parent);
		return "addDept";

	}

	/**
	 *保存部门
	 */
	public String saveDept() throws Exception {
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		dept.setIsDeleted(0);
		dept.setCreateTime(new Date());
		dept.setCreateUserId(currentUser.getId());
		dept.setCreateDeptId(currentUser.getDeptId());
		deptService.save(dept);
		HttpTool.setAttribute("reLoadTree", "true");
		return jump("dept!listDept.action?flush=true");
	}

	/**
	 * 显示修改部门页面
	 */
	public String modifyDept() throws Exception {
		dept = (Dept) deptService.findById(String.valueOf(id));
		return "modifyDept";
	}

	/**
	 * 修改部门
	 */
	public String updateDept() throws Exception {
		Dept targetDept = (Dept) deptService.findById(String.valueOf(dept.getId()));
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		dept.setUpdateUserId(currentUser.getId());
		dept.setUpdateTime(new Date());
		ReflectionUtils.copyPropertiesForHasValue(targetDept, dept);
		deptService.update(targetDept);
		//HttpTool.setAttribute("pass_reLoadTree", "true");
		return jump("dept!listDept.action?flush=true");
	}

	/**
	 * 删除部门
	 */
	public String deleteDept() throws Exception {
		try {
			deptService.deleteIds(ids);
		} catch (BusinessException e) {
			if (e.getCode().equals(ExceptionConstant.HAS_CHILD)) {
				HttpTool.setAttribute("alert", "删除数据失败，请首先删除子部门！");
			} else if (e.getCode().equals(ExceptionConstant.HAS_DATA)) {
				HttpTool.setAttribute("alert", "删除数据失败，请首先删除用户!");
			}
		}
//		HttpServletRequest request = ServletActionContext.getRequest();
//		request.setAttribute("pass_reLoadTree", true);
		return jump("dept!listDept.action?flush=true");
	}
	
	/**
	 * 获得部门信息
	 */
	public void getOnlyDept(){
//		JSONObject json = new JSONObject();
//		String deptid = HttpTool.getParameter("deptId");//店组编号
//		Dept dept = deptService.findOrgByDeptid(deptid,"HL001");//店组
//		boolean isExist = false;
//		//如果店存在
//		if(dept!=null){
//			json.put("zuPath", org.getDeptid());//组的treepath
//			json.put("zuShortName", org.getDescrshort());//组的简称
//			json.put("zuName", org.getDescr());//组的全称
//			isExist = true;
//		}
//		json.put("isExist", isExist);
//		this.renderJson(json);
	}
	
	/**
	 * 获得部门所有相关信息
	 */
	public void getOrgAllInfo(){
//		JSONObject json = new JSONObject();
//		String deptid = HttpTool.getParameter("deptid");//店组编号
//		Org org = orgService.findOrgByDeptid(deptid,"HL001");//店组
//		boolean isExist = false;
//		//如果店存在
//		if(dept!=null){
//			String parentPath=OrgUtil.getParentPath(org.getHlTreepath());//店的hlTreepath
//			String commuityPath=OrgUtil.getParentPath(parentPath);//大区的hlTreepath
//			String areaPath=OrgUtil.getParentPath(commuityPath);//区域的hlTreepath
//			String regionPath=OrgUtil.getParentPath(areaPath);//总区的hlTreepath
//			Org shop = orgService.findByTreepath(parentPath,"HL001");//用户所属的店
//			JlevelStg jlevelStg = personDataService.findJlevelByName("HL001", "OPER", "店经理");//店经理的职等记录
//			List<JobcodeStg> jobcodeStgList = personDataService.findJobcodeByName("HL001", "OPER", jlevelStg.getHlJobLevel(),null);//店经理的职务
//			User shoper = null;
//			if(jobcodeStgList!=null&&shop!=null&&OrgUtil.isDept(shop.getHlDeptGra(), "340")){
//				String[] jobcodeStg = new String[jobcodeStgList.size()];
//				for(int a=0;a<jobcodeStgList.size();a++){
//					if(jobcodeStgList.get(a)!=null){
//						jobcodeStg[a]=jobcodeStgList.get(a).getJobcode();
//					}
//				}
//				shoper = userService.findUserByJobcode(jobcodeStg, org.getDeptid(), "HL001");//店经理
//			}
//			Org commuity = orgService.findOrgByPathDeptGra(commuityPath, "330", "HL001");//大区
//			Org area = orgService.findOrgByPathDeptGra(areaPath, "320", "HL001");//区域
//			Org region = orgService.findOrgByPathDeptGra(regionPath, "310", "HL001");//总区
//			json.put("zuPath", org.getDeptid());//组的treepath
//			json.put("zuShortName", org.getDescrshort());//组的简称
//			json.put("zuName", org.getDescr());//组的全称
//			if(shop!=null){
//				json.put("shopPath", shop.getDeptid());//店的treepath
//				json.put("shopShortName", shop.getDescrshort());//店的简称
//				json.put("shopName", shop.getDescr());//店的全称
//			}
//			if(shoper!=null){
//				json.put("shopManagerId", shoper.getEmplid());//店经理编号
//				json.put("shopManagerName", shoper.getName());//店经理姓名
//			}
//			if(region!=null){
//				json.put("regionPath", region.getDeptid());//总区的treepath
//				json.put("regionShortName", region.getDescrshort());//总区的简称
//				json.put("regionName", region.getDescr());//总区的全称
//			}
//			if(area!=null){
//				json.put("areaPath", area.getDeptid());//区域的treepath
//				json.put("areaShortName", area.getDescrshort());//区域的简称
//				json.put("areaName", area.getDescr());//区域的全称
//			}
//			if(commuity!=null){
//				json.put("commuityPath", commuity.getDeptid());//大区的treepath
//				json.put("commuityShortName", commuity.getDescrshort());//大区的简称
//				json.put("commuityName", commuity.getDescr());//大区的全称
//			}
//			isExist = true;
//		}
//		json.put("isExist", isExist);
//		this.renderJson(json);
	}
	
	/**
	 * 显示授权页面
	 */
	public String modifyPriv() throws Exception {
		return "modifyPriv";
	}
	
	/**
	 * 根据业务部加载业务组联动菜单
	 * @author sky
	 * @see 业务部->业务组联动菜单标签(BusinessDeptAndGroupTag.java)
	 * @date 2012-06-17
	 * @return
	 */
	public String loadBusinessGroupByAjax(){
//		StringBuffer jsonBuffer = new StringBuffer("{data:[") ;
//		String deptId = HttpTool.getParameter("deptId") ;
//		if(StringUtils.isNotBlank(deptId)){
//			Org o = orgService.findOrgByDeptid(deptId , SystemConstant.getSystemConstant("org_setid_of_beijing")) ;
//			List<Org> orgList = orgService.findByParentPath(o.getHlTreepath()) ;
//			for(Org org : orgList){
//				jsonBuffer.append("{'id' : '").append(org.getDeptid()).append("' , ") ;
//				jsonBuffer.append(" 'name' : '").append(org.getName()).append("' },") ;
//			}
//			jsonBuffer.replace(jsonBuffer.length() - 1 , jsonBuffer.length() , "]") ;
//		}else{
//			jsonBuffer.append("]") ;
//		}
//		jsonBuffer.append("}") ;
//		super.jsonString = jsonBuffer.toString() ;
//		return "json" ;
		return null;
	}
}
