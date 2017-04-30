package org.hpin.reportdetail.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;


/**
 * @author machuan
 * @date 2016年12月12日
 * 报告寄送表
 */
public class ErpReportExpressHis extends BaseEntity implements Serializable{
	
	private String id;
	private String expressNo;	//快递单号
	private String expressCompany;//快递公司
	private BigDecimal expressCost; //快递费用
	private String expressWeight;//快递重量
	private Date expressDate; //寄送时间
	private String userName;    //操作人员
	private String reason;		//变更原因
	private String reportId;	//寄送批次ID
	private String createUserName;	//创建人
	private Date createTime;	//创建时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public BigDecimal getExpressCost() {
		return expressCost;
	}
	public void setExpressCost(BigDecimal expressCost) {
		this.expressCost = expressCost;
	}
	public String getExpressWeight() {
		return expressWeight;
	}
	public void setExpressWeight(String expressWeight) {
		this.expressWeight = expressWeight;
	}
	public Date getExpressDate() {
		return expressDate;
	}
	public void setExpressDate(Date expressDate) {
		this.expressDate = expressDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
