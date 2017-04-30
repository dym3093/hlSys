package org.hpin.genepack.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

public class GenepackDetail extends BaseEntity implements java.io.Serializable {
	private String id;
	private Integer idcount;
	private String batchno;
	private Date actiondate;
	private String actionevents;
	private String code1;
	private String code2;
	private String samplecount;
	private String ispapertable;
	public GenepackDetail(){}
	public Integer getIdcount() {
		return idcount;
	}
	public void setIdcount(Integer idcount) {
		this.idcount = idcount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public Date getActiondate() {
		return actiondate;
	}
	public void setActiondate(Date actiondate) {
		this.actiondate = actiondate;
	}
	public String getActionevents() {
		return actionevents;
	}
	public void setActionevents(String actionevents) {
		this.actionevents = actionevents;
	}
	public String getCode1() {
		return code1;
	}
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	public String getCode2() {
		return code2;
	}
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	public String getSamplecount() {
		return samplecount;
	}
	public void setSamplecount(String samplecount) {
		this.samplecount = samplecount;
	}
	public String getIspapertable() {
		return ispapertable;
	}
	public void setIspapertable(String ispapertable) {
		this.ispapertable = ispapertable;
	}
	
}
