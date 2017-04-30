package org.hpin.warehouse.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * StoreWarehouseAll entity. @author MyEclipse Persistence Tools
 */

public class StoreWarehouseAll extends BaseEntity implements java.io.Serializable {

	// Fields

	private String id;
	private String warehouseId;
	private String warehouseName;
	private String typeBigCode;
	private String typeBigName;
	private String typeSmallCode;
	private String typeSmallName;
	private Integer count;//剩余总量
	private Date createTime;
	private String createUserId;
	private Date updateTime;
	private String updateUserId;
	private Integer isDeleted;
	private Date deleteTime;
	private String deleteUserId;
	private Integer countPull; //入库总量
	private Integer countPush;//出库总量
	private String remark;
	private String remark1;
	private String remark2;
	private String remark3;

	// Constructors

	/** default constructor */
	public StoreWarehouseAll() {
	}

	/** full constructor */

	// Property accessors

	public String getId() {
		return this.id;
	}

	public StoreWarehouseAll(String id, String warehouseId,
			String warehouseName, String typeBigCode, String typeBigName,
			String typeSmallCode, String typeSmallName, Integer count,
			Date createTime, String createUserId, Date updateTime,
			String updateUserId, Integer isDeleted, Date deleteTime,
			String deleteUserId, Integer countPull, Integer countPush,
			String remark, String remark1, String remark2, String remark3) {
		super();
		this.id = id;
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
		this.typeBigCode = typeBigCode;
		this.typeBigName = typeBigName;
		this.typeSmallCode = typeSmallCode;
		this.typeSmallName = typeSmallName;
		this.count = count;
		this.createTime = createTime;
		this.createUserId = createUserId;
		this.updateTime = updateTime;
		this.updateUserId = updateUserId;
		this.isDeleted = isDeleted;
		this.deleteTime = deleteTime;
		this.deleteUserId = deleteUserId;
		this.countPull = countPull;
		this.countPush = countPush;
		this.remark = remark;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
	}

	public Integer getCountPull() {
		return countPull;
	}

	public void setCountPull(Integer countPull) {
		this.countPull = countPull;
	}

	public Integer getCountPush() {
		return countPush;
	}

	public void setCountPush(Integer countPush) {
		this.countPush = countPush;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getTypeBigCode() {
		return this.typeBigCode;
	}

	public void setTypeBigCode(String typeBigCode) {
		this.typeBigCode = typeBigCode;
	}

	public String getTypeBigName() {
		return this.typeBigName;
	}

	public void setTypeBigName(String typeBigName) {
		this.typeBigName = typeBigName;
	}

	public String getTypeSmallCode() {
		return this.typeSmallCode;
	}

	public void setTypeSmallCode(String typeSmallCode) {
		this.typeSmallCode = typeSmallCode;
	}

	public String getTypeSmallName() {
		return this.typeSmallName;
	}

	public void setTypeSmallName(String typeSmallName) {
		this.typeSmallName = typeSmallName;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getDeleteTime() {
		return this.deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getDeleteUserId() {
		return this.deleteUserId;
	}

	public void setDeleteUserId(String deleteUserId) {
		this.deleteUserId = deleteUserId;
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

}