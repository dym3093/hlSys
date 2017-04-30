package org.hpin.venueStaffSettlement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author Carly
 * @since 2016年8月28日10:32:56
 * 会议费用详细信息
 */
public class ErpConferenceCostDetail extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -1273658140795501344L;
	private String id ;
	private String conferenceId;
	private String conferenceNo;
	private String name;
	private String days;
	private String flight;
	private BigDecimal cost;
	private String descripe;
	private String belong;
	private Date createTime;
	private String createUser;
	private Date updateTime;
	private String updateUser;
	private String costName;		//费用名称,未用于数据存储
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConferenceId() {
		return conferenceId;
	}
	public void setConferenceId(String conferenceId) {
		this.conferenceId = conferenceId;
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
	public String getDescripe() {
		return descripe;
	}
	public void setDescripe(String descripe) {
		this.descripe = descripe;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * @return 费用名称,未用于数据存储
	 */
	public String getCostName() {
		return costName;
	}
	/**
	 * @param costName 费用名称,未用于数据存储
	 */
	public void setCostName(String costName) {
		this.costName = costName;
	}
	
}
