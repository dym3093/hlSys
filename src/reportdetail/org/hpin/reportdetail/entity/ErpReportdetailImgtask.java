package org.hpin.reportdetail.entity;

import java.io.Serializable;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

public class ErpReportdetailImgtask extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 201060623041305891L;
	
	private String id ;
	private String customerId;
	private String code;
	private String userName;
	private String idNo;	//省份证号
	private String phoneNo;
	private String birthday;
	private Integer state;	//图片生成状态,0:未生成;1：已生成
	private String pdfName;
	private String filePath;
	private String batchNo;
	private Date createTime;
	private Integer isDeleted;
	private String ageRangeStatus;	//年龄范围状态(只用于展示)
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	public String getAgeRangeStatus() {
		return ageRangeStatus;
	}
	public void setAgeRangeStatus(String ageRangeStatus) {
		this.ageRangeStatus = ageRangeStatus;
	}
	@Override
	public String toString() {
		return "ErpReportdetailImgtask [id=" + id + ", customerId="
				+ customerId + ", code=" + code + ", userName=" + userName
				+ ", idNo=" + idNo + ", phoneNo=" + phoneNo + ", birthday="
				+ birthday + ", state=" + state + ", pdfName=" + pdfName
				+ ", filePath=" + filePath + ", batchNo=" + batchNo
				+ ", createTime=" + createTime + ", isDeleted=" + isDeleted
				+ ", ageRangeStatus=" + ageRangeStatus + "]";
	}
}
