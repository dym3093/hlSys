package org.hpin.physical.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 1+X会员信息实体类
 * @author tangxing
 * @date 2016-12-6上午11:12:57
 */
public class PhyReport extends BaseEntity {

	private String id;
	
	private String userName;		//用户名
	
	private String userAge;			//用户年龄
	
	private String userSex;			//用户性别
	
	private String userIdno;		//用户身份证号
	
	private String geneCode;		//基因编码
	
	private String comboCode;		//套餐编码
	
	private String comboName;		//套餐姓名
	
	private Date reportDate;		//报告时间
	
	private Date importDate;		//导入时间
	
	private String reportName;		//电子版报告名字
	
	private Date reportCreateDate;	//报告创建时间
	
	private String geneCompanyCode;	//基因公司编码
	
	private String geneCompanyName;	//基因公司名字
	
	private String reportPath;		//电子版报告地址
	
	private String reportBatch;		//报告批次
	
	private String printReportPath;	//打印报告地址
	
	private String printReportName;	//打印报告名字
	
	private String pdfStatus;		//pdf状态（0--合并成功，1--未成功）
	
	private String isSuccess;		//是否成功生成PDF（0--成功生成，1--生成失败）
	
	private String reportCompletePath;	//完整的PDF地址（两个PDF合并）
	
	private String isMergeStatus;		//是否是需要合并的PDF(0--是，1--否)
	
	private String createPdfStatus;		//根据combo判断，是否能生成报告

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserIdno() {
		return userIdno;
	}

	public void setUserIdno(String userIdno) {
		this.userIdno = userIdno;
	}

	public String getGeneCode() {
		return geneCode;
	}

	public void setGeneCode(String geneCode) {
		this.geneCode = geneCode;
	}

	public String getComboCode() {
		return comboCode;
	}

	public void setComboCode(String comboCode) {
		this.comboCode = comboCode;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}
	
	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	public Date getReportCreateDate() {
		return reportCreateDate;
	}

	public void setReportCreateDate(Date reportCreateDate) {
		this.reportCreateDate = reportCreateDate;
	}

	public String getGeneCompanyCode() {
		return geneCompanyCode;
	}

	public void setGeneCompanyCode(String geneCompanyCode) {
		this.geneCompanyCode = geneCompanyCode;
	}

	public String getGeneCompanyName() {
		return geneCompanyName;
	}

	public void setGeneCompanyName(String geneCompanyName) {
		this.geneCompanyName = geneCompanyName;
	}
	
	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public String getReportBatch() {
		return reportBatch;
	}

	public void setReportBatch(String reportBatch) {
		this.reportBatch = reportBatch;
	}

	public String getPrintReportPath() {
		return printReportPath;
	}

	public void setPrintReportPath(String printReportPath) {
		this.printReportPath = printReportPath;
	}

	public String getPrintReportName() {
		return printReportName;
	}

	public void setPrintReportName(String printReportName) {
		this.printReportName = printReportName;
	}

	public String getPdfStatus() {
		return pdfStatus;
	}

	public void setPdfStatus(String pdfStatus) {
		this.pdfStatus = pdfStatus;
	}
	
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public String getReportCompletePath() {
		return reportCompletePath;
	}

	public void setReportCompletePath(String reportCompletePath) {
		this.reportCompletePath = reportCompletePath;
	}
	
	public String getIsMergeStatus() {
		return isMergeStatus;
	}

	public void setIsMergeStatus(String isMergeStatus) {
		this.isMergeStatus = isMergeStatus;
	}
	
	public String getCreatePdfStatus() {
		return createPdfStatus;
	}

	public void setCreatePdfStatus(String createPdfStatus) {
		this.createPdfStatus = createPdfStatus;
	}

	@Override
	public String toString() {
		return "PhyReport [id=" + id + ", userName=" + userName + ", userAge="
				+ userAge + ", userSex=" + userSex + ", userIdno=" + userIdno
				+ ", geneCode=" + geneCode + ", comboCode=" + comboCode
				+ ", comboName=" + comboName + ", reportDate=" + reportDate
				+ ", importDate=" + importDate + ", reportName=" + reportName
				+ ", reportCreateDate=" + reportCreateDate
				+ ", geneCompanyCode=" + geneCompanyCode + ", geneCompanyName="
				+ geneCompanyName + ", reportPath=" + reportPath
				+ ", reportBatch=" + reportBatch + ", printReportPath="
				+ printReportPath + ", printReportName=" + printReportName
				+ ", pdfStatus=" + pdfStatus + ", isSuccess=" + isSuccess
				+ ", reportCompletePath=" + reportCompletePath
				+ ", isMergeStatus=" + isMergeStatus + ", createPdfStatus="
				+ createPdfStatus + "]";
	}

}
