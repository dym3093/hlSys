package org.hpin.settlementManagement.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 认领明细数据实体类
 * @author LeslieTong
 * @date 2017-3-29下午3:28:15
 */

public class ErpClaimInfo extends BaseEntity {

	private String id;
	
	/** 款项ID */
	private String receiptId;
	
	/** 项目ID */
	private String projectId;
	
	/** 认领金额 */
	private BigDecimal claimCost;
	
	private String createUserName;
	
	private Date createDate;
	
	private Integer isDeleted;
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getReceiptId() {
		return receiptId;
	}


	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public BigDecimal getClaimCost() {
		return claimCost;
	}


	public void setClaimCost(BigDecimal claimCost) {
		this.claimCost = claimCost;
	}


	public String getCreateUserName() {
		return createUserName;
	}


	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Integer getIsDeleted() {
		return isDeleted;
	}


	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}


	@Override
	public String toString() {
		return "ErpClaimInfo [id=" + id + ", receiptId=" + receiptId
				+ ", projectId=" + projectId + ", claimCost=" + claimCost
				+ ", createUserName=" + createUserName + ", createDate="
				+ createDate + ", isDeleted=" + isDeleted + "]";
	}
	
}
