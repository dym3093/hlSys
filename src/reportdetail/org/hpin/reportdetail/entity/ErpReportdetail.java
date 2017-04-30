package org.hpin.reportdetail.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * ErpReportdetail entity. @author MyEclipse Persistence Tools
 */

public class ErpReportdetail extends BaseEntity implements java.io.Serializable {

	private String id;
	private String xlsno;
	private String code;
	private String name;
	private String sex;
	private String age;
	private String phone;
	private String branchcompany;
	private String project;
	private String sampletype;
	private String salesman;
	private String entering;
	private String institution;
	private Date samplingdate;
	private Date collectiondate;
	private String simplestatus;
	private String page;
	private String batchno;
	private String filepath;//excel文件路径
	

	private String ismatch;


	public String getXlsno() {
		return xlsno;
	}

	public void setXlsno(String xlsno) {
		this.xlsno = xlsno;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBranchcompany() {
		return this.branchcompany;
	}

	public void setBranchcompany(String branchcompany) {
		this.branchcompany = branchcompany;
	}

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getSampletype() {
		return this.sampletype;
	}

	public void setSampletype(String sampletype) {
		this.sampletype = sampletype;
	}

	public String getSalesman() {
		return this.salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getEntering() {
		return this.entering;
	}

	public void setEntering(String entering) {
		this.entering = entering;
	}

	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public Date getSamplingdate() {
		return samplingdate;
	}

	public void setSamplingdate(Date samplingdate) {
		this.samplingdate = samplingdate;
	}

	public Date getCollectiondate() {
		return collectiondate;
	}

	public void setCollectiondate(Date collectiondate) {
		this.collectiondate = collectiondate;
	}

	public String getSimplestatus() {
		return this.simplestatus;
	}

	public void setSimplestatus(String simplestatus) {
		this.simplestatus = simplestatus;
	}

	public String getPage() {
		return this.page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getBatchno() {
		return this.batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getIsmatch() {
		return this.ismatch;
	}

	public void setIsmatch(String ismatch) {
		this.ismatch = ismatch;
	}

}