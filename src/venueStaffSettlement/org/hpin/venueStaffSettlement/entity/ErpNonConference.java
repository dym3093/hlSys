package org.hpin.venueStaffSettlement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author Carly
 * @since 2017年1月22日09:24:45
 * 非会场费用管理                
 */
public class ErpNonConference extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -115474883128723813L;
	private String id ;
	private String projectCode;		//项目编码
	private String projectUser;		//项目负责人
	private String projectName;		//项目名称
	private String projectType;		//项目类型
	private String projectTypeName;	//项目名称
	private Date startTime;			//非会场费用的开始时间
	private Date endTime;			//非会场费用的结束时间
	private String month;			//月份
	private Date monthTime;			//月份所在时间
	private BigDecimal fees;		//产生费用
	private String note;			//备注
	private String OASerial;		//OA流水号
	private Date createTime;	
	private String createUser;
	private Date updateTime;
	private String updateUser;
	private Integer isDeleted;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectUser() {
		return projectUser;
	}
	public void setProjectUser(String projectUser) {
		this.projectUser = projectUser;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Date getMonthTime() {
		return monthTime;
	}
	public void setMonthTime(Date monthTime) {
		this.monthTime = monthTime;
	}
	public BigDecimal getFees() {
		return fees;
	}
	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getOASerial() {
		return OASerial;
	}
	public void setOASerial(String oASerial) {
		OASerial = oASerial;
	}
	public String getProjectTypeName() {
		return projectTypeName;
	}
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}
	@Override
	public String toString() {
		return "ErpNonConference [id=" + id + ", projectCode=" + projectCode
				+ ", projectUser=" + projectUser + ", projectName="
				+ projectName + ", projectType=" + projectType
				+ ", projectTypeName=" + projectTypeName + ", startTime="
				+ startTime + ", endTime=" + endTime + ", month=" + month
				+ ", monthTime=" + monthTime + ", fees=" + fees + ", note="
				+ note + ", OASerial=" + OASerial + ", createTime="
				+ createTime + ", createUser=" + createUser + ", updateTime="
				+ updateTime + ", updateUser=" + updateUser + ", isDeleted="
				+ isDeleted + "]";
	}
	
}
