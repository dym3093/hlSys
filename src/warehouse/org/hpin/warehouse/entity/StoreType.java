package org.hpin.warehouse.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * StoreType entity. @author MyEclipse Persistence Tools
 */

public class StoreType extends BaseEntity {

	// Fields

	private String id;
	private String name;
	//剩余数量
	private Integer num;
	//出库数量
	private Integer numPull;
	//入库储量
	private Integer numPush;
	private String standards;
	private String descripe;
	private String cardStart;
	private String cardEnd;
	private String cardCount;
	private String createUserId;
	private String createUserName;
	private Date createTime;
	private String updateUserId;
	private String updateUserName;
	private Date updateTime;
	private Integer isDeleted;
	private String deleteUserId;
	private String deleteUserName;
	private Date deleteTime;
	//仓库ID
	private String remark;
	//大类CODE
	private String remark1;
	private String remark2;
	private String remark3;


	public StoreType() {
		super();
		// TODO Auto-generated constructor stub
	}


	


	public StoreType(String id, String name, Integer num, Integer numPull,
			Integer numPush, String standards, String descripe,
			String cardStart, String cardEnd, String cardCount,
			String createUserId, String createUserName, Date createTime,
			String updateUserId, String updateUserName, Date updateTime,
			Integer isDeleted, String deleteUserId, String deleteUserName,
			Date deleteTime, String remark, String remark1, String remark2,
			String remark3) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
		this.numPull = numPull;
		this.numPush = numPush;
		this.standards = standards;
		this.descripe = descripe;
		this.cardStart = cardStart;
		this.cardEnd = cardEnd;
		this.cardCount = cardCount;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createTime = createTime;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.isDeleted = isDeleted;
		this.deleteUserId = deleteUserId;
		this.deleteUserName = deleteUserName;
		this.deleteTime = deleteTime;
		this.remark = remark;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
	}





	// Property accessors

	public String getCardStart() {
		return cardStart;
	}





	public void setCardStart(String cardStart) {
		this.cardStart = cardStart;
	}





	public String getCardEnd() {
		return cardEnd;
	}





	public void setCardEnd(String cardEnd) {
		this.cardEnd = cardEnd;
	}





	public String getCardCount() {
		return cardCount;
	}





	public void setCardCount(String cardCount) {
		this.cardCount = cardCount;
	}





	public String getId() {
		return this.id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStandards() {
		return this.standards;
	}

	public void setStandards(String standards) {
		this.standards = standards;
	}

	public String getDescripe() {
		return this.descripe;
	}

	public void setDescripe(String descripe) {
		this.descripe = descripe;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getDeleteUserId() {
		return this.deleteUserId;
	}

	public void setDeleteUserId(String deleteUserId) {
		this.deleteUserId = deleteUserId;
	}

	public String getDeleteUserName() {
		return this.deleteUserName;
	}

	public void setDeleteUserName(String deleteUserName) {
		this.deleteUserName = deleteUserName;
	}

	public Date getDeleteTime() {
		return this.deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}


	public Integer getNumPull() {
		return numPull;
	}


	public void setNumPull(Integer numPull) {
		this.numPull = numPull;
	}


	public Integer getNumPush() {
		return numPush;
	}


	public void setNumPush(Integer numPush) {
		this.numPush = numPush;
	}

}