package org.hpin.base.usermanager.entity;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 用户组管理
 * 
 * @author thinkpad
 * 
 */
public class Group extends BaseEntity {

	//private Long id = null;
	private String id = null;

	private String name;// 组名称

	private String description;// 描述

	private Integer orderCode = null;

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

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

	public Integer getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

}
