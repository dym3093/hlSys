package org.hpin.venueStaffSettlement.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Carly
 * 会场费用管理的导出
 * @since 2016年12月7日17:27:16
 */
public class ConferenceCostExport implements Serializable{

	private static final long serialVersionUID = 6642480989674853513L;
	
	private String id;
	/**会议号*/
	private String conferenceNo;
	/**会议日期*/
	private String conferenceDate;
	/**项目编码*/
	private String projectCode;
	/**项目所属人*/
	private String projectOwner;
	/**项目名称*/
	private String projectName;
	/**总共费用*/
	private BigDecimal produceCost;
	
	private String province;
	private String city;
	private String branchCompanyId;
	private String ownedCompanyId;
	private String customerRelationshipProId;
	
	public String getConferenceNo() {
		return conferenceNo;
	}
	public void setConferenceNo(String conferenceNo) {
		this.conferenceNo = conferenceNo;
	}
	public String getConferenceDate() {
		return conferenceDate;
	}
	public void setConferenceDate(String conferenceDate) {
		this.conferenceDate = conferenceDate;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectOwner() {
		return projectOwner;
	}
	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setProduceCost(BigDecimal produceCost) {
		this.produceCost = produceCost;
	}
	public BigDecimal getProduceCost() {
		return produceCost;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBranchCompanyId() {
		return branchCompanyId;
	}
	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}
	public String getOwnedCompanyId() {
		return ownedCompanyId;
	}
	public void setOwnedCompanyId(String ownedCompanyId) {
		this.ownedCompanyId = ownedCompanyId;
	}
	public String getCustomerRelationshipProId() {
		return customerRelationshipProId;
	}
	public void setCustomerRelationshipProId(String customerRelationshipProId) {
		this.customerRelationshipProId = customerRelationshipProId;
	}
	@Override
	public String toString() {
		return "ConferenceCostExport [id=" + id + ", conferenceNo="
				+ conferenceNo + ", conferenceDate=" + conferenceDate
				+ ", projectCode=" + projectCode + ", projectOwner="
				+ projectOwner + ", projectName=" + projectName
				+ ", produceCost=" + produceCost + ", province=" + province
				+ ", city=" + city + ", branchCompanyId=" + branchCompanyId
				+ ", ownedCompanyId=" + ownedCompanyId
				+ ", customerRelationshipProId=" + customerRelationshipProId
				+ "]";
	}
	
	
	
}
