package org.hpin.reportdetail.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 读取PDF内容与customer表信息不匹配记录
 * @author ybc
 */

public class ErpReportUnMatch extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String pdfid;
	
	private String cusid;
	
	private String pdfusername;
	
	private String cususername;
	
	private String pdfuserage;
	
	private String cususerage;
	
	private String pdfusersex;
	
	private String cususersex;
	
	private String pdfcode;
	
	private String cuscode;
	
	private String pdffilepath;
	
	private String cusfilepath;
	
	private Date createdate;
	
	private String modifystate;
	
	private String operatorname;
	
	private Date operatordate;
	
	private String pdfBthNo;
	
	private Date cusCreateDate;
	
	private String pdfCombo;
	
	private String cusCombo;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getPdfusername() {
		return pdfusername;
	}

	public void setPdfusername(String pdfusername) {
		this.pdfusername = pdfusername;
	}

	public String getCususername() {
		return cususername;
	}

	public void setCususername(String cususername) {
		this.cususername = cususername;
	}

	public String getPdfuserage() {
		return pdfuserage;
	}

	public void setPdfuserage(String pdfuserage) {
		this.pdfuserage = pdfuserage;
	}

	public String getCususerage() {
		return cususerage;
	}

	public void setCususerage(String cususerage) {
		this.cususerage = cususerage;
	}

	public String getPdfusersex() {
		return pdfusersex;
	}

	public void setPdfusersex(String pdfusersex) {
		this.pdfusersex = pdfusersex;
	}

	public String getCususersex() {
		return cususersex;
	}

	public void setCususersex(String cususersex) {
		this.cususersex = cususersex;
	}

	public String getPdfcode() {
		return pdfcode;
	}

	public void setPdfcode(String pdfcode) {
		this.pdfcode = pdfcode;
	}

	public String getCuscode() {
		return cuscode;
	}

	public void setCuscode(String cuscode) {
		this.cuscode = cuscode;
	}

	public String getPdffilepath() {
		return pdffilepath;
	}

	public void setPdffilepath(String pdffilepath) {
		this.pdffilepath = pdffilepath;
	}

	public String getCusfilepath() {
		return cusfilepath;
	}

	public void setCusfilepath(String cusfilepath) {
		this.cusfilepath = cusfilepath;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getModifystate() {
		return modifystate;
	}

	public void setModifystate(String modifystate) {
		this.modifystate = modifystate;
	}

	public String getOperatorname() {
		return operatorname;
	}

	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	public Date getOperatordate() {
		return operatordate;
	}

	public void setOperatordate(Date operatordate) {
		this.operatordate = operatordate;
	}

	public String getPdfBthNo() {
		return pdfBthNo;
	}

	public void setPdfBthNo(String pdfBthNo) {
		this.pdfBthNo = pdfBthNo;
	}

	public Date getCusCreateDate() {
		return cusCreateDate;
	}

	public void setCusCreateDate(Date cusCreateDate) {
		this.cusCreateDate = cusCreateDate;
	}

	public String getPdfCombo() {
		return pdfCombo;
	}

	public void setPdfCombo(String pdfCombo) {
		this.pdfCombo = pdfCombo;
	}

	public String getCusCombo() {
		return cusCombo;
	}

	public void setCusCombo(String cusCombo) {
		this.cusCombo = cusCombo;
	}
	
}
