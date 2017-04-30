package org.hpin.reportdetail.entity;

import java.io.Serializable;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;
import org.hpin.statistics.briefness.web.QueryAction.ReportType;

public class ErpReportdetailPDFContent extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String pdfname;
	
	private String username;
	
	private String age;
	
	private String code;
	
	private String sex;
	
	private String filesize;
	
	private String md5;
	
	private String batchno;
	
	private String filepath;
	
	private int isrecord;
	
	private int isrepeat;
	
	private int matchstate;
	
	private Date createdate;
	
	private Date updatedate;
	
	private String customerid;
	
	private String provice;
	
	private String city;
	
	private String branch_company;
	
	private String events_no;
	
	private String setmeal_name;
	
	private String sales_man;
	
	private String ps;
	
	private String printbthno;
	
	private String printtaskno;
	
	private String settlement_status;
	
	private String matchstateView;//显示状态含义,数据库并无此字段
	/**项目编码*///2016年9月23日15:45:28
	private String projectCode;

	/**人员部门*///2016年9月23日15:45:33
	private String dept;
	
	/**报告类型 0：基因，3无创*/
	private Integer reportType;
	/* 需要寻找套餐对应关系的类型(0:需要;1:不需要) */
	private Integer convertionFileType;
	
	private String updateUser;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getPdfname() {
		return pdfname;
	}

	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getIsrecord() {
		return isrecord;
	}

	public void setIsrecord(int isrecord) {
		this.isrecord = isrecord;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public int getMatchstate() {
		return matchstate;
	}

	public void setMatchstate(int matchstate) {
		this.matchstate = matchstate;
	}

	public int getIsrepeat() {
		return isrepeat;
	}

	public void setIsrepeat(int isrepeat) {
		this.isrepeat = isrepeat;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBranch_company() {
		return branch_company;
	}

	public void setBranch_company(String branch_company) {
		this.branch_company = branch_company;
	}

	public String getEvents_no() {
		return events_no;
	}

	public void setEvents_no(String events_no) {
		this.events_no = events_no;
	}

	public String getSetmeal_name() {
		return setmeal_name;
	}

	public void setSetmeal_name(String setmeal_name) {
		this.setmeal_name = setmeal_name;
	}

	public String getSales_man() {
		return sales_man;
	}

	public void setSales_man(String sales_man) {
		this.sales_man = sales_man;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getPrintbthno() {
		return printbthno;
	}

	public void setPrintbthno(String printbthno) {
		this.printbthno = printbthno;
	}

	public String getPrinttaskno() {
		return printtaskno;
	}

	public void setPrinttaskno(String printtaskno) {
		this.printtaskno = printtaskno;
	}

	public String getSettlement_status() {
		return settlement_status;
	}

	public void setSettlement_status(String settlement_status) {
		this.settlement_status = settlement_status;
	}

	public String getMatchstateView() {
		return matchstateView;
	}

	public void setMatchstateView(String matchstateView) {
		this.matchstateView = matchstateView;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public Integer getConvertionFileType() {
		return convertionFileType;
	}

	public void setConvertionFileType(Integer convertionFileType) {
		this.convertionFileType = convertionFileType;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
}
