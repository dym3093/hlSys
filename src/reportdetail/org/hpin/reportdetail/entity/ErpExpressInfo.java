package org.hpin.reportdetail.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

public class ErpExpressInfo extends BaseEntity {

	private String id;
	
	private String expressNo;
	
	private String expressCompany;
	
	private String expressCompanyCode;
	
	private String errorCause;			//失败原因
	
	private Date createTime;
	
	private Date updateTime;
	
	private String status;				//0物流单号暂无结果，3在途，4 揽件，5 疑难，6签收，7退签，8 派件，9 退回
	
	private String expressContent;		//内容
	
	private Date transitTime;			//在途时间（也就是流程的实时时间）

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
	
	public String getExpressCompanyCode() {
		return expressCompanyCode;
	}

	public void setExpressCompanyCode(String expressCompanyCode) {
		this.expressCompanyCode = expressCompanyCode;
	}

	public String getErrorCause() {
		return errorCause;
	}

	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpressContent() {
		return expressContent;
	}

	public void setExpressContent(String expressContent) {
		this.expressContent = expressContent;
	}

	public Date getTransitTime() {
		return transitTime;
	}

	public void setTransitTime(Date transitTime) {
		this.transitTime = transitTime;
	}
	
}
