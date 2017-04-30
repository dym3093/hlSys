package org.hpin.venueStaffSettlement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author Carly
 * @since 2017年2月22日11:43:33
 * 单项导入的异常数据
 */
public class ErpConferenceCostDetailExc extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -3058254020017650614L;

	private String id ;
	private String conferenceNo;
	private String name;
	private String days;
	private String flight;
	private BigDecimal cost;
	private String describe;
	private Date createTime;
	private String createUser;
	private String costName;
	private String exceptionDescribe; 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConferenceNo() {
		return conferenceNo;
	}
	public void setConferenceNo(String conferenceNo) {
		this.conferenceNo = conferenceNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCostName() {
		return costName;
	}
	public void setCostName(String costName) {
		this.costName = costName;
	}
	public String getExceptionDescribe() {
		return exceptionDescribe;
	}
	public void setExceptionDescribe(String exceptionDescribe) {
		this.exceptionDescribe = exceptionDescribe;
	}
	
	
}
