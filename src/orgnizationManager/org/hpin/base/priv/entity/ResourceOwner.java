package org.hpin.base.priv.entity;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 权限与拥有者关系
 * @author : wangzhiyong
 *
 */
public class ResourceOwner extends BaseEntity {
	//主键
	private String id;
	//权限id
	private String resourceId;
	//分配对象的id（部门id 用户name 或角色id）
	private String ownerId;
	//权限分配类型：1部门，2用户，3角色
	private Integer assignType;

	public String getOwnerId() {
		return ownerId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public Integer getAssignType() {
		return assignType;
	}
	public void setAssignType(Integer assignType) {
		this.assignType = assignType;
	}
}
