package org.hpin.base.usermanager.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.region.entity.Region;
import org.hpin.base.region.service.RegionService;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.base.usermanager.service.GroupService;
import org.hpin.base.usermanager.service.RoleService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.PasswordMd5;
import org.hpin.common.util.StaticMethod;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户Action
 * 
 * @author thinkpad
 * @data 2009-8-9
 */
@Namespace("/um")
@Results( {
		@Result(name = "indexUser", location = "/WEB-INF/content/userManager/user/indexUser.jsp"),
		@Result(name = "listUser", location = "/WEB-INF/content/userManager/user/listUser.jsp"),
		@Result(name = "selectUser", location = "/WEB-INF/content/userManager/user/selectUser.jsp"),
		@Result(name = "listSelectedUser", location = "/WEB-INF/content/userManager/user/listSelectedUser.jsp"),
		@Result(name = "addUser", location = "/WEB-INF/content/userManager/user/addUser.jsp"),
		@Result(name = "detailUser", location = "/WEB-INF/content/userManager/user/detailUser.jsp"),
		@Result(name = "modifyUser", location = "/WEB-INF/content/userManager/user/modifyUser.jsp"),
		@Result(name = "productTree", location = "/WEB-INF/branchcommany/productTree.jsp"),
		@Result(name = "selectRenewalFollowUpUser" , location = "/WEB-INF/content/customerrenewal/followup/selectRenewalFollowUpUser.jsp"),
		@Result(name = "lookUpUserByDeptId", location = "/WEB-INF/content/userManager/user/lookUpUserByDeptId.jsp"),
})
public class UserAction extends BaseAction {
	private UserService userService = (UserService) SpringTool
			.getBean(UserService.class);
    
	private GroupService groupService = (GroupService) SpringTool
			.getBean(GroupService.class);

	private DeptService deptService = (DeptService) SpringTool
			.getBean(DeptService.class);

	private RoleService roleService = (RoleService) SpringTool
			.getBean(RoleService.class);
	
	@Autowired
	private RegionService regionService; //create by henry.xu;

	private User user = null;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 显示用户框架首页
	 */
	public String indexUser() throws Exception {
		List<Dept> deptList = deptService.findAllDept();
		if (user == null)user = new User();
		user.setDeptList(deptList);
		return "indexUser" ;
	}

	/**
	 * 显示用户列表
	 * duan update
	 */
	public String listUser() throws Exception {
		String deptId = HttpTool.getParameter("deptId");
		Map searchMap = super.buildSearch();
		if (deptId != null && !"0".equals(deptId))
		  searchMap.put("filter_and_deptId_EQ_S", deptId);
		
		page = new Page(HttpTool.getPageNum());
		List<User> userList = userService.findByPage(page, searchMap);
		page.setResults(userList) ;
		this.jsonString = deptService.getDeptUser("0");
		return "listUser";
	}

	/**
	 * 选择用户
	 */
	public String selectUser() throws Exception {
//		Dept rootDept = (Dept) deptService.findRootOrg();
//		String nameId = HttpTool.getParameter("nameId");
//		String valueId = HttpTool.getParameter("valueId");
//		HttpTool.setAttribute("rootOrg", rootOrg);
//		HttpTool.setAttribute("nameId", nameId);
//		HttpTool.setAttribute("valueId", valueId);
		return "selectUser";
	}

	/**
	 * 选择用户界面中显示用户列表
	 */
	public String listSelectedUser() throws Exception {
		String codeId = HttpTool.getParameter("codeId");
		String nameId = HttpTool.getParameter("nameId");
		String valueId = HttpTool.getParameter("valueId");
		String emplid = HttpTool.getParameter("emplid");
		String fun = HttpTool.getParameter("fun");
		HttpTool.setAttribute("codeId", codeId);
		HttpTool.setAttribute("nameId", nameId);
		HttpTool.setAttribute("valueId", valueId);
		HttpTool.setAttribute("emplid", emplid);
		HttpTool.setAttribute("fun", fun);
		page = new Page(HttpTool.getPageNum(), 10);
		Map searchMap = super.buildSearch();
		userService.findByPage(page, searchMap);
		return "listSelectedUser";
	}

	/**
	 * 添加用户
	 */
	public String addUser() throws Exception {
		//Long orgId = StaticMethod.nullObject2Long(HttpTool.getLongParameter("pass_orgId"));
		String deptId = HttpTool.getParameter("deptId");
		Dept dept = (Dept) deptService.findById(deptId);
		HttpTool.setAttribute("dept", dept);
		List<CustomerRelationShip> list = deptService.findBanny(deptId);
		List<Region> provices = regionService.findRegionByParentId("0") ;
		
		HttpTool.setAttribute("provices", provices);
		
	    HttpTool.setAttribute("list", list);
		return "addUser";
	}

	/**
	 * 保存用户
	 */
	public String saveUser() throws Exception {
		String accountName=user.getAccountName();
		boolean b=userService.findUserByAccountName(accountName);
		if(b){
			alert="登录名重复";
			return jump("user!addUser.action?deptId="+id);
		}
		
		String isCustomService = "1";
		if(isCustomService.equals("1")){
			//DBconnectionToUltratel conn = new DBconnectionToUltratel();
			//Statement stmt = conn.connect().createStatement();
			user.setPassword(PasswordMd5.createPassword(user.getPassword()));
			user.setUserType(null);
		/*	user.setExtension(user.getExtension());*/
			String[] roles = ServletActionContext.getRequest().getParameterValues("roles");
			userService.save(user, roles);
			
		}else{
			user.setPassword(PasswordMd5.createPassword(user.getPassword()));
			user.setUserType(null);
			String[] roles = ServletActionContext.getRequest().getParameterValues("roles");
			userService.save(user, roles);
		}
		return jump("user!listUser.action?deptId="+user.getDeptId());
	}

	/**
	 * 显示修改用户页面
	 */
	public String modifyUser() throws Exception {
		user = (User) userService.findById(id);
		if (user == null) {
			user = new User();			
		}
		
		List<Region> provices = regionService.findRegionByParentId("0") ;
		
		List<CustomerRelationShip> list = deptService.findBanny(user.getDeptId());
	    HttpTool.setAttribute("list", list);
	    HttpTool.setAttribute("provices", provices);
	    
		return "modifyUser";
	}

	/**
	 * 修改用户
	 */
	public String updateUser() throws Exception {
		String[] roles = ServletActionContext.getRequest().getParameterValues(
				"roles");
		if (user.getPassword().length() <= 16) {
			user.setPassword(PasswordMd5.createPassword(user.getPassword()));
		}
		userService.update(user, roles);
		return jump("user!listUser.action?deptId="+user.getDeptId());
	}

	/**
	 * 删除用户
	 */
	public String deleteUser() throws Exception {
		userService.deleteIds(id);
		return jump("user!listUser.action");
	}

	/**
	 * 获得人员信息
	 */
	public void getBroker() {
		JSONObject json = new JSONObject();
		String emplid = StaticMethod.nullObject2String((HttpTool.getParameter("emplid")));// 用户编号
		User user = userService.findUserByEmplid(emplid, "HL001");// 北京的用户
		boolean isExist = false;
		// 如果用户存在
		if (user != null) {
//			Dept zu = deptService.findOrgByDeptId(user.getDeptId(), "HL001");// 用户所属组
//			json.put("emplid", emplid);// 用户编号
//			json.put("emplName", user.getName());// 用户名称
//			json.put("emplPhoneMobile", user.getPhoneMobile());// 用户移动电话
//			json.put("emplDeptId", user.getDeptId());// 员工部门编号
//			json.put("emplDeptName", zu.getName());// 员工部门名称
			isExist = true;
		}
		json.put("isExist", isExist);
		this.renderJson(json);
	}

	/**
	 * 获得职能的自如管家所有相关信息
	 */
	public void getFunctionerAllInfo() {
		JSONObject json = new JSONObject();
		String emplid = StaticMethod.nullObject2String(HttpTool.getParameter("emplid"));// 用户编号
		User user = userService.findUserByEmplid(emplid, "HL001");// 北京的用户
		boolean isExist = false;
//		// 如果用户存在
//		if (user != null) {
//			User director = userService.findUserByEmplid(
//					user.getSupervisorId(), "HL001");// 主管
//			Org zu = orgService.findOrgByDeptId(user.getDeptId(), "HL001");// 用户所属组
//			String parentPath = OrgUtil.getParentPath(zu.getHlTreepath());// 店的hlTreepath
//			String commuityPath = OrgUtil.getParentPath(parentPath);// 大区的hlTreepath
//			// areaPath=OrgUtil.getParentPath(commuityPath);//大区的hlTreepath
//			Org shop = orgService.findByTreepath(parentPath, "HL001");// 自如管家的上一级就是业务拓展部
//			Org commuity = orgService.findByTreepath(commuityPath, "HL001");// 租赁管理中心,租赁管理中心总监所在部门
//			// Org area = orgService.findOrgByPathDeptGra(areaPath, "320",
//			// "HL001");//大区
//			JlevelStg majorJlevelStg = personDataService.findJlevelByName(
//					"HL001", "FUNC", "总监");// 总监的职等记录
//			List<JobcodeStg> majorJobcodeStgList = personDataService
//					.findJobcodeByName("HL001", "FUNC", majorJlevelStg
//							.getHlJobLevel(), null);// 总监的职务
//			User major = null;
//			if (majorJobcodeStgList != null && shop != null) {
//				String[] jobcodeStg = new String[majorJobcodeStgList.size()];
//				for (int a = 0; a < majorJobcodeStgList.size(); a++) {
//					if (majorJobcodeStgList.get(a) != null) {
//						jobcodeStg[a] = majorJobcodeStgList.get(a).getJobcode();
//					}
//				}
//				major = userService.findUserByJobcode(jobcodeStg, shop
//						.getDeptId(), "HL001");// 总监
//			}
//			json.put("emplid", emplid);// 员工编号
//			json.put("emplName", user.getName());// 员工名称
//			json.put("emplDeptId", user.getDeptId());// 员工部门编号
//			json.put("emplPhoneMobile", user.getPhoneMobile());// 员工 移动电话
//			
//			//向JSON中添加用户所在的组、部信息【提供单据管理页面使用】
//			json.put("zu", zu.getName()) ;
//			json.put("shop", shop.getName()) ;
//			
//			
//			if (major != null) {
//				json.put("majorId", major.getEmplid());// 总监编号
//				json.put("majorName", major.getName());// 总监姓名
//				json.put("majorPhoneMobile", major.getPhoneMobile());// 总监电话
//			}
//			if (director != null) {
//				json.put("directorId", director.getEmplid());// 主管编号
//				json.put("directorName", director.getName());// 主管姓名
//				json.put("directorPhoneMobile", director.getPhoneMobile());// 主管电话
//			}
//			isExist = true;
//		}
//		json.put("isExist", isExist);
//		this.renderJson(json);
	}

	/**
	 * 获得运营的经纪人所有相关信息
	 */
	public void getBrokerAllInfo() {
		JSONObject json = new JSONObject();
		String emplid = StaticMethod.nullObject2String(HttpTool.getParameter("emplid"));// 用户编号
		User user = userService.findUserByEmplid(emplid, "HL001");// 北京的用户
		boolean isExist = false;
		// 如果用户存在
//		if (user != null) {
//			json.put("emplid", emplid);// 员工编号
//			json.put("emplName", user.getName());// 员工名称
//			json.put("emplDeptId", user.getDeptId());// 员工部门编号
//			json.put("emplPhoneMobile", user.getPhoneMobile());// 员工 移动电话
//			Org zu = orgService.findOrgByDeptId(user.getDeptId(), "HL001");// 用户所属组
//			isExist = true;
//			if( zu != null ){
//			String parentPath = OrgUtil.getParentPath(zu.getHlTreepath());// 店的hlTreepath
//			String commuityPath = OrgUtil.getParentPath(parentPath);// 大区的hlTreepath
//			String areaPath = OrgUtil.getParentPath(commuityPath);// 区域的hlTreepath
//			String regionPath = OrgUtil.getParentPath(areaPath);// 总区的hlTreepath
//			Org shop = orgService.findByTreepath(parentPath, "HL001");// 用户所属的店
//			JlevelStg jlevelStg = personDataService.findJlevelByName("HL001",
//					"OPER", "店经理");// 店经理的职等记录
//			List<JobcodeStg> jobcodeStgList = personDataService
//					.findJobcodeByName("HL001", "OPER", jlevelStg
//							.getHlJobLevel(), null);// 店经理的职务
//			User shoper = null;
////			if (jobcodeStgList != null && shop != null
////					&& OrgUtil.isDept(shop.getHlDeptGra(), "340")) {
////				String[] jobcodeStg = new String[jobcodeStgList.size()];
////				for (int a = 0; a < jobcodeStgList.size(); a++) {
////					if (jobcodeStgList.get(a) != null) {
////						jobcodeStg[a] = jobcodeStgList.get(a).getJobcode();
////					}
////				}
////				shoper = userService.findUserByJobcode(jobcodeStg, zu
////						.getDeptId(), "HL001");// 店经理
////			}
//			Org commuity = orgService.findOrgByPathDeptGra(commuityPath, "330",
//					"HL001");// 大区
//			Org area = orgService
//					.findOrgByPathDeptGra(areaPath, "320", "HL001");// 区域
//			Org region = orgService.findOrgByPathDeptGra(regionPath, "310",
//					"HL001");// 总区
////			
////			if (zu != null) {
////				json.put("zuPath", zu.getDeptId());// 组的treepath
////				json.put("zuShortName", zu.getDescrshort());// 组的简称
////				json.put("zuName", zu.getDescr());// 组的全称
////			}
////			if (shop != null) {
////				json.put("shopPath", shop.getDeptId());// 店的treepath
////				json.put("shopShortName", shop.getDescrshort());// 店的简称
////				json.put("shopName", shop.getDescr());// 店的全称
////			}
////			if (shoper != null) {
////				json.put("shopManagerId", shoper.getEmplid());// 店经理编号
////				json.put("shopManagerName", shoper.getName());// 店经理姓名
////			}
////			if (region != null) {
////				json.put("regionPath", region.getDeptId());// 总区域的treepath
////				json.put("regionShortName", region.getDescrshort());//  总区域的简称
////				json.put("regionName", region.getDescr());//  总区域的全称
////			}
////			if (area != null) {
////				json.put("areaPath", area.getDeptId());// 区域的treepath
////				json.put("areaShortName", area.getDescrshort());// 区域的简称
////				json.put("areaName", area.getDescr());// 区域的全称
////			}
////			if (commuity != null) {
////				json.put("commuityPath", commuity.getDeptId());// 大区的treepath
////				json.put("commuityShortName", commuity.getDescrshort());// 大区的简称
////				json.put("commuityName", commuity.getDescr());// 大区的全称
////			}
//			isExist = true;
//		}
//		}
//		json.put("isExist", isExist);
//		this.renderJson(json);
	}

	/**
	 * 获得多个运营的经纪人所有相关信息
	 */
	public void getMultiBrokerAllInfo() {
//		JSONObject json = new JSONObject();
//		String multiEmplid = StaticMethod.nullObject2String(HttpTool.getParameter("multiEmplid"));// 用户编号
//		String[] emplidArrays = null;
//		if (StringUtils.isNotBlank(multiEmplid)) {
//			emplidArrays = multiEmplid.split(",");
//		}
//		StringBuilder emplidBuilder = new StringBuilder();
//		StringBuilder emplNameBuilder = new StringBuilder();
//		StringBuilder emplDeptIdBuilder = new StringBuilder();
//		StringBuilder zuPathBuilder = new StringBuilder();
//		StringBuilder zuShortNameBuilder = new StringBuilder();
//		StringBuilder shopPathBuilder = new StringBuilder();
//		StringBuilder shopShortNameBuilder = new StringBuilder();
//		StringBuilder shopManagerIdBuilder = new StringBuilder();
//		StringBuilder shopManagerNameBuilder = new StringBuilder();
//		StringBuilder regionPathBuilder = new StringBuilder();
//		StringBuilder regionShortNameBuilder = new StringBuilder();
//		StringBuilder areaPathBuilder = new StringBuilder();
//		StringBuilder areaShortNameBuilder = new StringBuilder();
//		StringBuilder commuityPathBuilder = new StringBuilder();
//		StringBuilder commuityShortNameBuilder = new StringBuilder();
//		for (int i = 0; i < emplidArrays.length; i++) {
//			String emplid = emplidArrays[i];
//			User user = userService.findUserByEmplid(emplid, "HL001");// 北京的用户
			// 如果用户存在
//			if (user != null) {
//				Org zu = orgService.findOrgByDeptId(user.getDeptId(), "HL001");// 用户所属组
//				String parentPath = OrgUtil.getParentPath(zu.getHlTreepath());// 店的hlTreepath
//				String commuityPath = OrgUtil.getParentPath(parentPath);// 小区的hlTreepath
//				String areaPath = OrgUtil.getParentPath(commuityPath);// 大区的hlTreepath
//				String regionPath = OrgUtil.getParentPath(areaPath);// 区域的hlTreepath
//				Org shop = orgService.findByTreepath(parentPath, "HL001");// 用户所属的店
//				JlevelStg jlevelStg = personDataService.findJlevelByName(
//						"HL001", "OPER", "店经理");// 店经理的职等记录
//				List<JobcodeStg> jobcodeStgList = personDataService
//						.findJobcodeByName("HL001", "OPER", jlevelStg
//								.getHlJobLevel(), null);// 店经理的职务
//				User shoper = null;
//				if (jobcodeStgList != null && shop != null
//						&& OrgUtil.isDept(shop.getHlDeptGra(), "340")) {
//					String[] jobcodeStg = null;
//					for (int a = 0; a < jobcodeStgList.size(); a++) {
//						jobcodeStg[a] = jobcodeStgList.get(a).getJobcode();
//					}
//					shoper = userService.findUserByJobcode(jobcodeStg, shop
//							.getDeptId(), "HL001");// 店经理
//				}
//				Org commuity = orgService.findOrgByPathDeptGra(commuityPath,
//						"330", "HL001");// 小区
//				Org area = orgService.findOrgByPathDeptGra(areaPath, "320",
//						"HL001");// 大区
//				Org region = orgService.findOrgByPathDeptGra(regionPath, "310",
//						"HL001");// 区域
//				// 每个用户之间用,分隔
//				String spaceMark = "";
//				if (i < emplidArrays.length - 1) {
//					spaceMark = ",";
//				}
//				emplidBuilder.append(emplid).append(spaceMark);// 员工编号
//				emplNameBuilder.append(user.getName()).append(spaceMark);// 员工名称
//				emplDeptIdBuilder.append(user.getDeptId()).append(spaceMark);// 员工部门编号
//				zuPathBuilder.append(zu != null ? zu.getHlTreepath() : null)
//						.append(spaceMark);// 组的treepath
////				zuShortNameBuilder.append(
////						zu != null ? zu.getDescrshort() : null).append(
////						spaceMark);// 组的简称
//				shopPathBuilder.append(
//						shop != null ? shop.getHlTreepath() : null).append(
//						spaceMark);// 店的treepath
//				shopShortNameBuilder.append(
//						shop != null ? shop.getDescrshort() : null).append(
//						spaceMark);// 店的简称
//				shopManagerIdBuilder.append(
//						shoper != null ? shoper.getEmplid() : null).append(
//						spaceMark);// 店经理编号
//				shopManagerNameBuilder.append(
//						shoper != null ? shoper.getName() : null).append(
//						spaceMark);// 店经理姓名
//				regionPathBuilder.append(
//						region != null ? region.getHlTreepath() : null).append(
//						spaceMark);// /区域的treepath
//				regionShortNameBuilder.append(
//						region != null ? region.getDescrshort() : null).append(
////						spaceMark);// 区域的简称
//				areaPathBuilder.append(
//						area != null ? area.getHlTreepath() : null).append(
//						spaceMark);// 大区的treepath
//				areaShortNameBuilder.append(
//						area != null ? area.getDescrshort() : null).append(
//						spaceMark);// 大区的简称
//				commuityPathBuilder.append(
//						commuity != null ? commuity.getHlTreepath() : null)
//						.append(spaceMark);// 小区的treepath
//				commuityShortNameBuilder.append(
//						commuity != null ? commuity.getDescrshort() : null)
//						.append(spaceMark);// 小区的简称
//			}
//		}
//		json.put("emplids", emplidBuilder.toString());
//		json.put("emplNames", emplNameBuilder.toString());
//		json.put("emplDeptIds", emplDeptIdBuilder.toString());
//		json.put("zuPaths", zuPathBuilder.toString());
//		json.put("zuShortNames", zuShortNameBuilder.toString());
//		json.put("shopPaths", shopPathBuilder.toString());
//		json.put("shopShortNames", shopShortNameBuilder.toString());
//		json.put("shopManagerIds", shopManagerIdBuilder.toString());
//		json.put("shopManagerNames", shopManagerNameBuilder.toString());
//		json.put("regionPaths", regionPathBuilder.toString());
//		json.put("regionShortNames", regionShortNameBuilder.toString());
//		json.put("areaPaths", areaPathBuilder.toString());
//		json.put("areaShortNames", areaShortNameBuilder.toString());
//		json.put("commuityPaths", commuityPathBuilder.toString());
//		json.put("commuityShortNames", commuityShortNameBuilder.toString());
//		this.renderJson(json);
	}

	/**
	 * 获得店经理所有相关信息
	 */
	public void getShopManagerAllInfo() {
//		JSONObject json = new JSONObject();
//		String emplid = StaticMethod.nullObject2String(HttpTool.getParameter("emplid"));// 用户编号
//		User user = userService.findUserByEmplid(emplid, "HL001");// 北京的用户
//		boolean isExist = false;
//		// 如果用户存在
//		if (user != null) {
//			Org zu = orgService.findOrgByDeptId(user.getDeptId(), "HL001");// 用户所属组
//			String parentPath = OrgUtil.getParentPath(zu.getHlTreepath());// 店的hlTreepath
//			String commuityPath = OrgUtil.getParentPath(parentPath);// 小区的hlTreepath
//			String areaPath = OrgUtil.getParentPath(commuityPath);// 大区的hlTreepath
//			String regionPath = OrgUtil.getParentPath(areaPath);// 区域的hlTreepath
//			Org shop = orgService.findByTreepath(parentPath, "HL001");// 用户所属的店
//			/*
//			 * JlevelStg jlevelStg = personDataService.findJlevelByName("HL001",
//			 * "OPER", "店经理");//店经理的职等记录 JobcodeStg jobcodeStg =
//			 * personDataService.findJobcodeByName("HL001", "OPER",
//			 * jlevelStg.getHlJobLevel(),null);//店经理的职务 User shoper = null;
//			 * if(jobcodeStg!=null&&shop!=null){ shoper =
//			 * userService.findUserByJobcode(jobcodeStg.getJobcode(),
//			 * shop.getDeptId(), "HL001");//店经理 }
//			 */
//			Org commuity = orgService.findOrgByPathDeptGra(commuityPath, "330",
//					"HL001");// 小区
//			Org area = orgService
//					.findOrgByPathDeptGra(areaPath, "320", "HL001");// 大区
//			Org region = orgService.findOrgByPathDeptGra(regionPath, "310",
//					"HL001");// 区域
//			json.put("emplid", emplid);// 员工编号
//			json.put("emplName", user.getName());// 员工名称
//			json.put("emplPhoneMobile", user.getPhoneMobile());// 员工 移动电话
////			if (zu != null) {
////				json.put("zuPath", zu.getDeptId());// 组的treepath
////				json.put("zuShortName", zu.getDescrshort());// 组的简称
////				json.put("zuName", zu.getDescr());// 组的全称
////			}
////			if (shop != null) {
////				json.put("shopPath", shop.getHlTreepath());// 店的treepath
////				json.put("shopShortName", shop.getDescrshort());// 店的简称
////				json.put("shopName", shop.getDescr());// 店的全称
////			}
////			/*
////			 * if(shoper!=null){ json.put("shopManagerId",
////			 * shoper.getEmplid());//店经理编号 json.put("shopManagerName",
////			 * shoper.getName());//店经理姓名 }
////			 */
////			if (region != null) {
////				json.put("regionPath", region.getHlTreepath());// 区域的treepath
////				json.put("regionShortName", region.getDescrshort());// 区域的简称
////				json.put("regionName", region.getDescr());// 区域的全称
////			}
////			if (area != null) {
////				json.put("areaPath", area.getHlTreepath());// 大区的treepath
////				json.put("areaShortName", area.getDescrshort());// 大区的简称
////				json.put("areaName", area.getDescr());// 大区的全称
////			}
////			if (commuity != null) {
////				json.put("commuityPath", commuity.getHlTreepath());// 小区的treepath
////				json.put("commuityShortName", commuity.getDescrshort());// 小区的简称
////				json.put("commuityName", commuity.getDescr());// 小区的全称
////			}
//			isExist = true;
//		}
//		json.put("isExist", isExist);
//		this.renderJson(json);
	}

	/**
	 * 用户详细页面
	 */
	public String detailUser() throws Exception {
		user = (User) userService.findById(id);
	/*	HttpTool.setAttribute("roleList", roleService.findAll());
		String roleIds = StrUtils.CollectionToStr(userService
				.findRoleIdByUserId(user.getId()), ",", false);
		HttpTool.setAttribute("roleIds", roleIds);*/
		HttpTool.setAttribute("descrshort", "");
		return "detailUser";
	}
	
	/**
	 * 客户续约跟进，选择续约跟进人
	 * @return
	 * @throws Exception
	 */
	public String selectRenewalFollowUpUser() throws Exception{
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize()) ;
		Map paramsMap = buildSearch() ;
		userService.findByPage(page , paramsMap) ;
		return "selectRenewalFollowUpUser" ;
	}
	public String lookUpUserByDeptId(){
		String deptId = HttpTool.getParameter("deptId");
		String companyId = HttpTool.getParameter("companyId");	
		System.out.println("deptId:"+deptId+",companyId:"+companyId);
		List<User> userList=null;
		if(!"".equals(deptId) ){			
			userList = userService.findByDeptIds(deptId,companyId);	
		}else{
			userList=new ArrayList<User>();
		}
		HttpTool.setAttribute("userList", userList);
		return "lookUpUserByDeptId";
	}
}
