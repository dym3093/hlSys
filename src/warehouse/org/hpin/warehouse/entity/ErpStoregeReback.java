package org.hpin.warehouse.entity;

import java.math.BigDecimal;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @description: 
 * create by henry.xu 2016年10月19日
 */
public class ErpStoregeReback extends BaseEntity {
	/**
	 * 序列号;
	 */
	private static final long serialVersionUID = 915570048829309378L;
	
	private String id;	//主键
	private String storegeOutId;	//出库信息ID
	private String applicationId;	//申请单ID
	private String productId;	//产品ID
	private String productName;	//产品名称
	private String standard;	//规格
	private BigDecimal price;	//单价
	private Integer quantity;	//退库数量
	private BigDecimal amount;	//总金额（单价*数量）
	private BigDecimal cost;	//成本（单价*数量）
	private String cardStart;	//卡号开始
	private String cardEnd;	//卡号结束
	
	private String storegeInId; //入库id; 不做保存
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStoregeOutId() {
		return storegeOutId;
	}
	public void setStoregeOutId(String storegeOutId) {
		this.storegeOutId = storegeOutId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
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
	public String getStoregeInId() {
		return storegeInId;
	}
	public void setStoregeInId(String storegeInId) {
		this.storegeInId = storegeInId;
	}
	
	
}
