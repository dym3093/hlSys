package org.hpin.physical.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

public class PhyCompleteReport extends BaseEntity {

	private String id;
	
	private String reportId;			//1+X报告的ID
	
	private String xreportPdfId;		//检测机构所给PDF info 表的ID
	
	private String code;				//编码
	
	private String name;				//姓名
	
	private String companyId;			//公司Id
	
	private String completePdfPath;		//合并两个PDF后的路径
	
	private String status;				//拆分固定页状态（默认为null，0--成功，1--失败）
	
	private String createUserName;
	
	private Date createTime;
	
	private String updateUserName;
	
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getXreportPdfId() {
		return xreportPdfId;
	}

	public void setXreportPdfId(String xreportPdfId) {
		this.xreportPdfId = xreportPdfId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompletePdfPath() {
		return completePdfPath;
	}

	public void setCompletePdfPath(String completePdfPath) {
		this.completePdfPath = completePdfPath;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PhyCompleteReport [id=" + id + ", reportId=" + reportId
				+ ", xreportPdfId=" + xreportPdfId + ", code=" + code
				+ ", name=" + name + ", companyId=" + companyId
				+ ", completePdfPath=" + completePdfPath + ", status=" + status
				+ ", createUserName=" + createUserName + ", createTime="
				+ createTime + ", updateUserName=" + updateUserName
				+ ", updateTime=" + updateTime + "]";
	}
}
