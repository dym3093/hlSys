package org.hpin.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

public class StoreDeliveryDetail extends BaseEntity implements Serializable{
	
	private String wareHouseId;
	/** 仓库名 */
	private String wareHouse;
	/** 品类 */
	private String type;
	/** 名称 */
	private String name;
	/** 规格 */
	private String specifications;
	/** 发货数量 */
	private String deliveryNum;
	/** 快递单号 */
	private String expressNum;
	/** 快递公司 */
	private String expressCompany;
	/** 库存 */
	private Integer stock;
	/** 品类的code */
	private String typeCode;
	/** 发货时间 */
	private Date deliveryDate;
	/** StoreTypeId */
	private String storeTypeId;
	
	public String getStoreTypeId() {
		return storeTypeId;
	}
	public void setStoreTypeId(String storeTypeId) {
		this.storeTypeId = storeTypeId;
	}
	public String getWareHouse() {
		return wareHouse;
	}
	public void setWareHouse(String wareHouse) {
		this.wareHouse = wareHouse;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getDeliveryNum() {
		return deliveryNum;
	}
	public void setDeliveryNum(String deliveryNum) {
		this.deliveryNum = deliveryNum;
	}
	public String getExpressNum() {
		return expressNum;
	}
	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
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
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getWareHouseId() {
		return wareHouseId;
	}
	public void setWareHouseId(String wareHouseId) {
		this.wareHouseId = wareHouseId;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	
	
}
