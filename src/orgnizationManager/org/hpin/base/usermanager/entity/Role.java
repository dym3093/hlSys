package org.hpin.base.usermanager.entity;

import java.util.List;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 角色实体类
 * 
 * @author thinkpad
 * @data 2009-7-25
 */
public class Role extends BaseEntity {

	//private Long id = null;
	private String id = null;

	private String code = null;

	private String name;// 角色名称

	private String description;// 说明
	
	private String parentId;
	
	private List<Role> roleList = null;
	
	private List<UserRole> userRoleList = null;
	
    private String userId;
    
    private String userName;
    
	
	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<UserRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<UserRole> userRoleList) {
		this.userRoleList = userRoleList;
	}

}
