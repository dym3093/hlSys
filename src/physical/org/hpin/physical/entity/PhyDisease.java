package org.hpin.physical.entity;

import org.hpin.common.core.orm.BaseEntity;

public class PhyDisease extends BaseEntity {

	private String id;
	
	private String groupCode;		//分组编码
	
	private String groupName;		//分组名称
	
	private Integer orderNum;		//排序

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
}
