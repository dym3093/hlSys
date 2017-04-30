package org.hpin.base.usermanager.entity;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 菜单角色关系表
 * 
 * @author thinkpad
 * @data 2009-8-1
 */
public class RoleResource extends BaseEntity {

	private String id;

    private String roleId = null;
	private String resourceId = null; //资源id

	private Integer isMenu = null; //是否菜单

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsMenu() {
		return isMenu;
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

}
