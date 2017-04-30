package org.hpin.reportdetail.entity;

import java.io.Serializable;

/**
 * 任务详情页需要展示Bean
 * @author ybc
 */
public class ErpReportFileTaskBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String batchno ;
	
	private String branchcompany;
	
	private Integer pdfnum;

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getBranchcompany() {
		return branchcompany;
	}

	public void setBranchcompany(String branchcompany) {
		this.branchcompany = branchcompany;
	}

	public Integer getPdfnum() {
		return pdfnum;
	}

	public void setPdfnum(Integer pdfnum) {
		this.pdfnum = pdfnum;
	}
	
}
