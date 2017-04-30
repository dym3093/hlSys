package org.hpin.physical.vo;

/**
 * 1+X报告报表VO
 * @author tangxing
 * @date 2016-12-7下午4:52:36
 */
public class PhyReportInfoVO {

	/** 批次号 **/
	private String reportBatchNo;
	
	/** 条形码 **/
	private String geneCode;
	
	/** 用户名 **/
	private String userName;
	
	/** 支公司ID **/
	private String branchCompanyId;
	
	/** 所属公司ID **/
	private String ownedCompanyId;
	
	/** 套餐 **/
	private String combo;
	
	/** 基因报告 **/
	private String geneReportPath;
	
	/** 1+X报告 **/
	private String reportPath;
	
	/** 部门 **/
	private String dept;
	
	/** 部门 **/
	private String importDate;

	public String getReportBatchNo() {
		return reportBatchNo;
	}

	public void setReportBatchNo(String reportBatchNo) {
		this.reportBatchNo = reportBatchNo;
	}

	public String getGeneCode() {
		return geneCode;
	}

	public void setGeneCode(String geneCode) {
		this.geneCode = geneCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getCombo() {
		return combo;
	}

	public void setCombo(String combo) {
		this.combo = combo;
	}

	public String getGeneReportPath() {
		return geneReportPath;
	}

	public void setGeneReportPath(String geneReportPath) {
		this.geneReportPath = geneReportPath;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}
	
}
