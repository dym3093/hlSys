package org.hpin.base.customerrelationship.entity;

import java.io.Serializable;

/**
 * 支公司项目列表与套餐中间表;
 * @description: 
 * create by henry.xu 2016年11月17日
 */
public class ErpRelationShipCombo implements Serializable{
	/**
	 * 序列号;
	 */
	private static final long serialVersionUID = 6815265401403754348L;

	private String id; //主键ID
	
	private String customerRelationShipProId; //支公司项目列表ID
	
	private String comboId ; //套餐ID
	
	private String isCreatePdf;// add by tx 1+X下数据，该套餐是否生成报告状态
	
	private String comboShowName; //套餐显示名称; create by henry.xu
	
	private String isUsed; //是否使用该套餐显示名称; create by henry.xu
	
	private Integer printType; //是否是打印套餐,0不是,1是

	public String getComboShowName() {
		return comboShowName;
	}

	public void setComboShowName(String comboShowName) {
		this.comboShowName = comboShowName;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerRelationShipProId() {
		return customerRelationShipProId;
	}

	public void setCustomerRelationShipProId(String customerRelationShipProId) {
		this.customerRelationShipProId = customerRelationShipProId;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	public String getIsCreatePdf() {
		return isCreatePdf;
	}

	public void setIsCreatePdf(String isCreatePdf) {
		this.isCreatePdf = isCreatePdf;
	}

	public Integer getPrintType() {
		return printType;
	}

	public void setPrintType(Integer printType) {
		this.printType = printType;
	}
	
	
	
}
