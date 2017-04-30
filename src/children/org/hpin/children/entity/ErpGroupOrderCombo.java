package org.hpin.children.entity;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author chenqi
 * @since 2016年6月23日10:11:30
 * 儿童基因套餐entity
 */
public class ErpGroupOrderCombo extends BaseEntity  implements java.io.Serializable{
	
	private String id;				//主键
	private String orderNo;			//订单号
	private String comboName;		//套餐名
	private Integer comboNum;		//套餐数量
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getComboName() {
		return comboName;
	}
	public void setComboName(String comboName) {
		this.comboName = comboName;
	}
	public Integer getComboNum() {
		return comboNum;
	}
	public void setComboNum(Integer comboNum) {
		this.comboNum = comboNum;
	}
	
	
	
}
