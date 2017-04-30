package org.hpin.warehouse.entity.vo;


public class ErpPreCustomerQueryObj {

	private String batchNo;

	private String wereName;
	
	private String code;
	
	private String wereIdcard;
	
	private String werePhone;
	
	private String applicationNo;	//erp_application表
	
	private String checkCombo;
	
	private String statusYm;
	
	private String branchCompany;
	
	private String startTime;
	
	private String endTime;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getWereName() {
		return wereName;
	}

	public void setWereName(String wereName) {
		this.wereName = wereName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWereIdcard() {
		return wereIdcard;
	}

	public void setWereIdcard(String wereIdcard) {
		this.wereIdcard = wereIdcard;
	}

	public String getWerePhone() {
		return werePhone;
	}

	public void setWerePhone(String werePhone) {
		this.werePhone = werePhone;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getCheckCombo() {
		return checkCombo;
	}

	public void setCheckCombo(String checkCombo) {
		this.checkCombo = checkCombo;
	}

	public String getStatusYm() {
		return statusYm;
	}

	public void setStatusYm(String statusYm) {
		this.statusYm = statusYm;
	}

	public String getBranchCompany() {
		return branchCompany;
	}

	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "ErpPreCustomerQueryObj [batchNo=" + batchNo + ", wereName="
				+ wereName + ", code=" + code + ", wereIdcard=" + wereIdcard
				+ ", werePhone=" + werePhone + ", applicationNo="
				+ applicationNo + ", checkCombo=" + checkCombo
				+ ", statusYm=" + statusYm
				+ ", branchCompany=" + branchCompany + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}
	
	
}
