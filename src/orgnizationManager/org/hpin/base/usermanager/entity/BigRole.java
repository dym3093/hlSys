package org.hpin.base.usermanager.entity;

import org.hpin.common.core.orm.BaseEntity;

/**
 * <p>@desc : </p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-11-18 上午11:06:02</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
public class BigRole extends BaseEntity{

	/**
	 * 大角色ID
	 */
	private String id ;
	
	/**
	 * 大角色名称
	 */
	private String roleName ;
	
	/**
	 * 大角色编码
	 */
	private String roleCode ; 
	
	/**
	 * 父角色ID
	 */
	private String parentId ;
	
	/**
	 * 父角色
	 */
	private BigRole parentBigRole ;
	
	/**
	 * 描述
	 */
	private String desc ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public BigRole getParentBigRole() {
		return parentBigRole;
	}

	public void setParentBigRole(BigRole parentBigRole) {
		this.parentBigRole = parentBigRole;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}

