package org.hpin.warehouse.entity;

import java.util.List;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 
 * @description: 产品套餐维护; ERP_PRODUCT_COMBO
 * create by henry.xu 2016年11月4日
 */
public class ErpProductCombo extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3085261588966971899L;
	/**主键ID*/
	private String id;
	/**产品套餐维护*/
	private String productComboName;
	/**说明*/
	private String declare;
	/**是否关闭启用默认为0启用, 1关闭*/
	private String isClose;
	/**套餐分类
	 * 基因套餐combo_type_01
	 * 癌筛套餐combo_type_03
	 * 分子套餐combo_type_04
	 * 其他套餐combo_type_05)*/
	private String comboType; 
	
	private List<ErpProduct> proDetails; //明细集合; 前台显示使用;
	
	public String getComboType() {
		return comboType;
	}
	public void setComboType(String comboType) {
		this.comboType = comboType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductComboName() {
		return productComboName;
	}
	public void setProductComboName(String productComboName) {
		this.productComboName = productComboName;
	}
	public String getDeclare() {
		return declare;
	}
	public void setDeclare(String declare) {
		this.declare = declare;
	}
	public String getIsClose() {
		return isClose;
	}
	public void setIsClose(String isClose) {
		this.isClose = isClose;
	}
	public List<ErpProduct> getProDetails() {
		return proDetails;
	}
	public void setProDetails(List<ErpProduct> proDetails) {
		this.proDetails = proDetails;
	}

}
