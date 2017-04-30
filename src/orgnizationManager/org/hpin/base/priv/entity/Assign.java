package org.hpin.base.priv.entity;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author : 张艳
 * 权限菜单按钮
 *
 */
public class Assign extends BaseEntity {

	//private Long id;
	private String id;
	
	/**
	 * 角色ID
	 */
	//private Long roleId = null;
	private String roleId = null;
	/**
	 * 资源id
	 */
	//private Long resourceId = null;
	private String resourceId = null;
	/**
	 * 是否为菜单
	 */
	private Integer isMenu = null;
	
	/**
	 * 1代表给用户授权，2代表给角色授权，3代表给部门授权
	 */
	private Integer objectType = null;
	
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Long getRoleId() {
//		return roleId;
//	}
//
//	public void setRoleId(Long roleId) {
//		this.roleId = roleId;
//	}
//
//	public Long getResourceId() {
//		return resourceId;
//	}
//
//	public void setResourceId(Long resourceId) {
//		this.resourceId = resourceId;
//	}

	public Integer getIsMenu() {
		return isMenu;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	
}
