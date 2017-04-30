package org.hpin.warehouse.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.util.PreciseCompute;

/**
 * 
 * @description: 入库表中相应的产品列表展示字段
 * create by henry.xu 2016年10月13日
 */
public class StoregeInOutVo implements Serializable{
	
	/**
	 * 序列号;
	 */
	private static final long serialVersionUID = 2301042063678284730L;
	private String id; //申请明细Id;
	private String storegeInId; //入库Id
	private String applicationId; //申请单ID
	private String storegeId;//仓库ID;
	private String storegeName; //仓库名称;
	private String productId; //产品ID
	private String productType;//产品类别
	private String productTypeName;//产品类别
	private String productName;//产品名称;
	private String standard;//产品规格;
	private Integer useableQuantity;//当前库存(可用数量)
	private BigDecimal price;//单价
	private Integer quantity;//发货数量
	private String userCardNum;//卡号开始(真正可用开始卡号)
	private String cardStart; //卡号开始;(浏览使用;请勿混淆)
	private String cardEnd;//卡号结束
	private String expressName;//快递公司
	private String expressNo;//快递单号;
	private BigDecimal expressMoney;//快递费用
	
	private BigDecimal cost; //成本
	private BigDecimal amount; //总费用;
	
	
	private String projectCode;
	private String projectName;
	private String projectOwner;
	private String applicationNo;
	private String createUserName; //申请人
	private String remark; //备注
	
	private Date createTime; //日期;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectOwner() {
		return projectOwner;
	}
	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}
	public String getCardStart() {
		return cardStart;
	}
	public void setCardStart(String cardStart) {
		this.cardStart = cardStart;
	}
	public BigDecimal getCost() {
		return  PreciseCompute.div(cost, new BigDecimal(1), 2);
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public BigDecimal getAmount() {
		return PreciseCompute.div(amount, new BigDecimal(1), 2);
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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
	public String getStoregeId() {
		return storegeId;
	}
	public void setStoregeId(String storegeId) {
		this.storegeId = storegeId;
	}
	public String getStoregeName() {
		return storegeName;
	}
	public void setStoregeName(String storegeName) {
		this.storegeName = storegeName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
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
	public Integer getUseableQuantity() {
		return useableQuantity;
	}
	public void setUseableQuantity(Integer useableQuantity) {
		this.useableQuantity = useableQuantity;
	}
	/**
	 * 这儿做了下处理,目的是为了让结果保留两位小数;
	 * @return
	 */
	public BigDecimal getPrice() {
		return PreciseCompute.div(price, new BigDecimal(1), 2);
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
	public String getUserCardNum() {
		return userCardNum;
	}
	public void setUserCardNum(String userCardNum) {
		this.userCardNum = userCardNum;
	}
	public String getCardEnd() {
		return cardEnd;
	}
	public void setCardEnd(String cardEnd) {
		this.cardEnd = cardEnd;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public BigDecimal getExpressMoney() {
		return PreciseCompute.div(expressMoney, new BigDecimal(1), 2) ;
	}
	public void setExpressMoney(BigDecimal expressMoney) {
		this.expressMoney = expressMoney;
	}
	
	
}
