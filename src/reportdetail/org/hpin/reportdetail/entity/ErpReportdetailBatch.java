package org.hpin.reportdetail.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * ErpReportdetailBatch entity. @author MyEclipse Persistence Tools
 */

public class ErpReportdetailBatch extends BaseEntity implements java.io.Serializable {


	private String id;
	private String batchno;
	private Date batchdate;
	private int xlscodecount;//excel中条形码数
	private int pdffilecount;//pdf报告文件数
	private int xlspdfcount;//本次正确人数
	private int codenoreportfilecount;//条形码没有报告文件数
	private int reportfilenocodecount;//报告文件没有条形码数
	private int ismatch;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatchno() {
		return this.batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public Date getBatchdate() {
		return this.batchdate;
	}

	public void setBatchdate(Date batchdate) {
		this.batchdate = batchdate;
	}

	public int getXlscodecount() {
		return xlscodecount;
	}

	public void setXlscodecount(int xlscodecount) {
		this.xlscodecount = xlscodecount;
	}

	public int getPdffilecount() {
		return pdffilecount;
	}

	public void setPdffilecount(int pdffilecount) {
		this.pdffilecount = pdffilecount;
	}

	public int getXlspdfcount() {
		return xlspdfcount;
	}

	public void setXlspdfcount(int xlspdfcount) {
		this.xlspdfcount = xlspdfcount;
	}

	public int getCodenoreportfilecount() {
		return codenoreportfilecount;
	}

	public void setCodenoreportfilecount(int codenoreportfilecount) {
		this.codenoreportfilecount = codenoreportfilecount;
	}

	public int getReportfilenocodecount() {
		return reportfilenocodecount;
	}

	public void setReportfilenocodecount(int reportfilenocodecount) {
		this.reportfilenocodecount = reportfilenocodecount;
	}

	public int getIsmatch() {
		return ismatch;
	}

	public void setIsmatch(int ismatch) {
		this.ismatch = ismatch;
	}

}