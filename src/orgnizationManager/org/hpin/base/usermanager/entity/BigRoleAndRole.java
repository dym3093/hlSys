package org.hpin.base.usermanager.entity;

import org.hpin.common.core.orm.BaseEntity;

/**
 * <p>@desc : </p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-11-19 下午6:31:37</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
public class BigRoleAndRole extends BaseEntity {

	private String id ;
	
	private String bigRoleId ;
	
	private BigRole bigRole ;
	
	private String roleId ;
	
	private Role role ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBigRoleId() {
		return bigRoleId;
	}

	public void setBigRoleId(String bigRoleId) {
		this.bigRoleId = bigRoleId;
	}

	public BigRole getBigRole() {
		return bigRole;
	}

	public void setBigRole(BigRole bigRole) {
		this.bigRole = bigRole;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}

