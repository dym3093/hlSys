package org.hpin.physical.entity;

import java.util.Date;

public class PhyReportTemp {

	private String id;
	
	private String userName;		//用户名
	
	private String userAge;			//用户年龄
	
	private String userSex;			//用户性别
	
	private String userIdno;		//用户身份证号
	
	private String geneCode;		//基因编码
	
	private String comboCode;		//套餐编码
	
	private String comboName;		//套餐姓名
	
	private Date reportDate;		//报告时间(表格里的时间)
	
	private Date importDate;		//导入时间(导入Excel时间)
	
	private String reportName;		//报告名字(PDF生成的报告名字)
	
	private String reportCreateDate;//报告创建时间(PDF生成时间)
	
	private String reportFlag;		//报告标示(是否为异常  0--不是异常  1--异常)
	
	private String geneDiseaseCode;	//基因疾病编码
	
	private String geneDiseaseName;	//基因疾病名字
	
	private Integer geneRiskGrade;	//基因风险等级
	
	private String geneRiskRemark;	//基因风险描述
	
	private String phyItem;			//检测项列表
	
	private String reportBatch;		//报告批次

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

	public String getGeneDiseaseCode() {
		return geneDiseaseCode;
	}

	public void setGeneDiseaseCode(String geneDiseaseCode) {
		this.geneDiseaseCode = geneDiseaseCode;
	}

	public String getGeneDiseaseName() {
		return geneDiseaseName;
	}

	public void setGeneDiseaseName(String geneDiseaseName) {
		this.geneDiseaseName = geneDiseaseName;
	}

	public Integer getGeneRiskGrade() {
		return geneRiskGrade;
	}

	public void setGeneRiskGrade(Integer geneRiskGrade) {
		this.geneRiskGrade = geneRiskGrade;
	}

	public String getGeneRiskRemark() {
		return geneRiskRemark;
	}

	public void setGeneRiskRemark(String geneRiskRemark) {
		this.geneRiskRemark = geneRiskRemark;
	}

	public String getPhyItem() {
		return phyItem;
	}

	public void setPhyItem(String phyItem) {
		this.phyItem = phyItem;
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

	public String getReportCreateDate() {
		return reportCreateDate;
	}

	public void setReportCreateDate(String reportCreateDate) {
		this.reportCreateDate = reportCreateDate;
	}

	public String getReportFlag() {
		return reportFlag;
	}

	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
	}

	public String getReportBatch() {
		return reportBatch;
	}

	public void setReportBatch(String reportBatch) {
		this.reportBatch = reportBatch;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhyReportTemp other = (PhyReportTemp) obj;
		if (geneCode == null) {
			if (other.geneCode != null)
				return false;
		} else if (!geneCode.equals(other.geneCode))
			return false;
		if (geneDiseaseCode == null) {
			if (other.geneDiseaseCode != null)
				return false;
		} else if (!geneDiseaseCode.equals(other.geneDiseaseCode))
			return false;
		if (geneDiseaseName == null) {
			if (other.geneDiseaseName != null)
				return false;
		} else if (!geneDiseaseName.equals(other.geneDiseaseName))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
}
