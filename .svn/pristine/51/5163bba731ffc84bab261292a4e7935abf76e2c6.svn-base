package org.hpin.base.usermanager.entity;

import java.util.List;

import org.hpin.base.resource.entity.Resource;
import org.hpin.common.core.orm.BaseEntity;

/**
 * 用户实体类
 * 
 * @author thinkpad/duan update
 * @data 2009-8-7
 */
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String id = null;
	/**
	 * 用户名
	 */
	private String accountName = null;

	/**
	 * 用户姓名
	 */
	private String userName = null;

	/**
	 * 密码
	 */
	private String password = null;

	/**
	 * 是否启用
	 */
	private Integer isEnable = 1;

	/**
	 * 部门ID
	 */
	private String deptId = null;
	//总公司
	private String deptName = null;
	
	private String mobile = null;
	/**
	 * 电子邮箱
	 */
	private String email=null;
	/**
	 * 电子邮箱密码
	 */
	private String ctiPassword = null;
	private Dept dept = null;
	// 支公司
	private String extension = null;
	/**
	 * 支公司号
	 */
	private String jobNumber = null;
	
	/**
	 * 是否座席员
	 */
	private String isCustomService = null;
	/**
	 * 座席员角色
	 */
	private String customServiceRole = null;
	/**
	 * 座席员类型
	 */
	private String customServiceType = null;
	/**
	 * 用户类型，0标识超级管理员 1标识普通用户
	 */
	private String groupId = null;

	/**
	 * 组名称集合
	 */
	private String groupName = null;

	/**
	 * 角色名称集合
	 */
	private String roleNames = null;

	private String roleCodes = null;

	private String resources = null;

	private List<Resource> resourceList = null;// 用户可以访问的资源

	private List<Resource> moduleResourceList = null;// 用户可以访问的模块

	private List<Dept> deptList = null;
	/**
	 * 是否删除
	 */
	private Integer isDeleted = null;

	private Long loginNum = 0l;

	private String userType = null;

	private String loginLogId = null;
	
	private String dataPriv; //数据权限;priv_01部门数据;priv_02个人数据;
	
	private String ymSaleMan; //远盟对接人 create by henry.xu 20161213;
	
	private String provice; //省份; create by henry.xu 20161221;
	private String city; //城市; create by henry.xu 20161221;
	
	private String projectCode;//项目编码;create by ybc 20170213
	
	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getYmSaleMan() {
		return ymSaleMan;
	}

	public void setYmSaleMan(String ymSaleMan) {
		this.ymSaleMan = ymSaleMan;
	}

	public String getDataPriv() {
		return dataPriv;
	}

	public void setDataPriv(String dataPriv) {
		this.dataPriv = dataPriv;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(String roleCodes) {
		this.roleCodes = roleCodes;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	public List<Resource> getModuleResourceList() {
		return moduleResourceList;
	}

	public void setModuleResourceList(List<Resource> moduleResourceList) {
		this.moduleResourceList = moduleResourceList;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Long loginNum) {
		this.loginNum = loginNum;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(String loginLogId) {
		this.loginLogId = loginLogId;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}
	/**
	 * 支公司
	 * @return
	 */
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	/**
	 * 支公司号
	 * @return
	 */
	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getCtiPassword() {
		return ctiPassword;
	}

	public void setCtiPassword(String ctiPassword) {
		this.ctiPassword = ctiPassword;
	}

	public String getIsCustomService() {
		return isCustomService;
	}

	public void setIsCustomService(String isCustomService) {
		this.isCustomService = isCustomService;
	}

	public String getCustomServiceRole() {
		return customServiceRole;
	}

	public void setCustomServiceRole(String customServiceRole) {
		this.customServiceRole = customServiceRole;
	}

	public String getCustomServiceType() {
		return customServiceType;
	}

	public void setCustomServiceType(String customServiceType) {
		this.customServiceType = customServiceType;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}
