package org.hpin.base.customerrelationship.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

public class ProjectType {
	private String id;
	
	private String projectTypeName; //项目类型名称;
	/** 创建人名称*/
	protected String createUserId;
	/** 创建时间*/
	protected Date createTime;
	/**最后修改人*/
	protected String updateUserId;
	/**最后修改时间*/
	protected Date updateTime;
	/**创建人部门编号*/
	protected String createDeptId;
	/**删除位*/
	protected String isDeleted;
	/**删除人*/
	protected String deleteUserId;
	/**删除时间*/
	protected Date deleteTime;
	
	private String projectType; //项目类型编码;
	
	protected String remark;
	
	protected String deptId;
	
	
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectTypeName() {
		return projectTypeName;
	}
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateDeptId() {
		return createDeptId;
	}
	public void setCreateDeptId(String createDeptId) {
		this.createDeptId = createDeptId;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getDeleteUserId() {
		return deleteUserId;
	}
	public void setDeleteUserId(String deleteUserId) {
		this.deleteUserId = deleteUserId;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
}
