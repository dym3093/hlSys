package cn.yuanmeng.labelprint.entity;

import java.io.Serializable;

public class Batch implements Serializable {
	private int id;
	private String batchNo;
	private String code;
	private String name;
	private String sex;
	private String age;
	private String contact;
	private String idno;
	private String project;
	private String sampleType;
	private String branchCompany;
	private String salesMan;
	private String entering;
	private String samplingDate;
	//Submission date;// 
	private String familyHistory;
	private String note;
	private String collectionDate;//收样日期
	private String Guardian;
	private String relation;
	private String guardianContact;
	private String institution;
	private String simpleStatus;
	private String page;
	private String nation;
	public Batch(){}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getSimpleStatus() {
		return simpleStatus;
	}
	public void setSimpleStatus(String simpleStatus) {
		this.simpleStatus = simpleStatus;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getSampleType() {
		return sampleType;
	}
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	public String getBranchCompany() {
		return branchCompany;
	}
	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}
	public String getSalesMan() {
		return salesMan;
	}
	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}
	public String getEntering() {
		return entering;
	}
	public void setEntering(String entering) {
		this.entering = entering;
	}
	public String getSamplingDate() {
		return samplingDate;
	}
	public void setSamplingDate(String samplingDate) {
		this.samplingDate = samplingDate;
	}
	public String getFamilyHistory() {
		return familyHistory;
	}
	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getGuardian() {
		return Guardian;
	}
	public void setGuardian(String guardian) {
		Guardian = guardian;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getGuardianContact() {
		return guardianContact;
	}
	public void setGuardianContact(String guardianContact) {
		this.guardianContact = guardianContact;
	}
	
	

}
