package org.hpin.warehouse.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * StoreProduceDetail entity. @author MyEclipse Persistence Tools
 */

public class StoreProduceDetail extends BaseEntity implements java.io.Serializable {

	// Fields

	private String id;
	private String typeBigCode;
	private String typeBigName;
	private String typeSmallCode;
	private String typeSmallName;
	private Integer count;
	private Double price;
	private Double totalPrice;
	private String warehouseId;
	private String warehouseName;
	private String produceId;
	private Date createTime;
	private Integer isDeleted;
	private String remark;//card_id
	private String remark1;
	private String remark2;
	private String remark3;

	// Constructors

	/** default constructor */
	public StoreProduceDetail() {
	}

	/** full constructor */
	public StoreProduceDetail(String typeBigCode, String typeBigName,
			String typeSmallCode, String typeSmallName, Integer count,
			Double price, Double totalPrice, String warehouseId,
			String warehouseName, String produceId, Date createTime,
			Integer isDeleted, String remark, String remark1,
			String remark2, String remark3) {
		this.typeBigCode = typeBigCode;
		this.typeBigName = typeBigName;
		this.typeSmallCode = typeSmallCode;
		this.typeSmallName = typeSmallName;
		this.count = count;
		this.price = price;
		this.totalPrice = totalPrice;
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
		this.produceId = produceId;
		this.createTime = createTime;
		this.isDeleted = isDeleted;
		this.remark = remark;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
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

	public String getProduceId() {
		return this.produceId;
	}

	public void setProduceId(String produceId) {
		this.produceId = produceId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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