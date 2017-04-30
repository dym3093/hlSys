package org.hpin.warehouse.entity;

import java.math.BigDecimal;

import org.hpin.common.core.orm.BaseEntity;
import org.hpin.common.util.PreciseCompute;

/**
 * 
 * @description: 基因物品发货 ERP_STOREGE_OUT
 * create by henry.xu 2016年10月12日
 */
public class ErpStoregeOut extends BaseEntity {

	/**
	 * 序列号;
	 */
	private static final long serialVersionUID = 6261500731726797486L;
	
	private String id; //主键
	private String storegeInId;	//入库信息ID
	private String applicationId;	//申请单ID
	private String productId;	//产品ID
	private String productName;	//产品名称
	private String standard;	//规格
	private BigDecimal price;	//单价
	private Integer quantity;	//数量
	private BigDecimal amount;	//总金额（单价*数量）+快递费(保存时后台处理;)
	private String expressNo;	//快递单号
	private String expressName;	//快递公司
	private BigDecimal expressMoney;	//快递费
	private BigDecimal cost;	//成本（单价*数量）
	private String cardStart;	//卡号开始
	private String cardEnd;	//卡号结束
	
	private String userCard; //不保存数据库;下次使用卡号
	
	public String getUserCard() {
		return userCard;
	}
	public void setUserCard(String userCard) {
		this.userCard = userCard;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStoregeInId() {
		return storegeInId;
	}
	public void setStoregeInId(String storegeInId) {
		this.storegeInId = storegeInId;
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
	/**
	 * 处理页面传参问题;
	 * @param priceBak
	 */
	public void setPriceBak(String priceBak) {
		this.price = PreciseCompute.formatComma2BigDecimal(priceBak, 2);
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
	public void setAmountBak(String amountBak) {
		this.amount = PreciseCompute.formatComma2BigDecimal(amountBak, 2);
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	
	
	public BigDecimal getExpressMoney() {
		return expressMoney;
	}
	public void setExpressMoney(BigDecimal expressMoney) {
		this.expressMoney = expressMoney;
	}
	/**
	 * 处理页面传参问题;
	 * @param priceBak
	 */
	public void setExpressMoneyBak(String expressMoneyBak) {
		this.expressMoney = PreciseCompute.formatComma2BigDecimal(expressMoneyBak, 2);
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

	
}
