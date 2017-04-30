package org.hpin.children.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author chenqi
 * @since 2016年6月23日09:55:32
 * 团购订单entity
 */
public class ErpGroupOrderInfo extends BaseEntity  implements java.io.Serializable{
	
	private String id;				//主键
	private String orderNo;			//订单号
	private Date orderDate;			//订单日期
	private String name;			//姓名
	private String phone;			//电话
	private String address;			//地址
	private String type;			//类型
	private String price;			//价格
	
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
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
}
