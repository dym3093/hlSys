package org.hpin.reportdetail.entity;

import java.util.Date;

public class MatchPdfAndCusBean {

	private String pdfid;
	
	private String cusid;
	
	private String pdffilename;
	
	private String pdfusername;
	
	private String pdfcode;
	
	private String cusname;
	
	private String cuscode;
	
	private Date createdate;

	public String getPdfid() {
		return pdfid;
	}

	public void setPdfid(String pdfid) {
		this.pdfid = pdfid;
	}

	public String getCusid() {
		return cusid;
	}

	public void setCusid(String cusid) {
		this.cusid = cusid;
	}

	public String getPdffilename() {
		return pdffilename;
	}

	public void setPdffilename(String pdffilename) {
		this.pdffilename = pdffilename;
	}

	public String getPdfusername() {
		return pdfusername;
	}

	public void setPdfusername(String pdfusername) {
		this.pdfusername = pdfusername;
	}

	public String getPdfcode() {
		return pdfcode;
	}

	public void setPdfcode(String pdfcode) {
		this.pdfcode = pdfcode;
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname;
	}

	public String getCuscode() {
		return cuscode;
	}

	public void setCuscode(String cuscode) {
		this.cuscode = cuscode;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
}
