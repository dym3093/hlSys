package org.hpin.warehouse.entity;

import java.math.BigDecimal;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 退货表
 * @author machuan
 * @date 2017年3月20日
 */
public class ErpStoregeReturn extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String projectCode;//项目编码
	private String projectOwner;//项目负责人
	private String projectName;//项目名称
	private String storegeId;//仓库ID
	private String productId;//产品ID
	private BigDecimal unitPrice;//单价
	private Integer returnNumber;//数量
	private BigDecimal cost;//成本
	private String expressCompany;//快递公司
	private BigDecimal expressCost;//快递费用
	private BigDecimal totalCost;//总成本
	private String cardStart;//卡号开始
	private String cardEnd;//卡号结束
	private String remark;//备注
	private String updateUserName;//修改人
	private String createUserName;//创建人
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectOwner() {
		return projectOwner;
	}
	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStoregeId() {
		return storegeId;
	}
	public void setStoregeId(String storegeId) {
		this.storegeId = storegeId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getReturnNumber() {
		return returnNumber;
	}
	public void setReturnNumber(Integer returnNumber) {
		this.returnNumber = returnNumber;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
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
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
