package org.hpin.venueStaffSettlement.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author Carly
 * @since 2017年1月22日09:33:36
 * 非会场费用
 */
public class ErpNonConferenceCost extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -8245324068480719217L;
	
	private String id ;
	private String nonConferenceId;
	private String name;				//姓名
	private BigDecimal travelCost;		//差旅费
	private BigDecimal cityCost;		//市内交通费
	private BigDecimal provinceCost;	//往返交通费
	private BigDecimal hotelCost;		//住宿费
	private BigDecimal laborCost;		//劳务费
	private BigDecimal otherCost;		//其他费用
	private BigDecimal amount;			//小计
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNonConferenceId() {
		return nonConferenceId;
	}
	public void setNonConferenceId(String nonConferenceId) {
		this.nonConferenceId = nonConferenceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getTravelCost() {
		return travelCost;
	}
	public void setTravelCost(BigDecimal travelCost) {
		this.travelCost = travelCost;
	}
	public BigDecimal getCityCost() {
		return cityCost;
	}
	public void setCityCost(BigDecimal cityCost) {
		this.cityCost = cityCost;
	}
	public BigDecimal getProvinceCost() {
		return provinceCost;
	}
	public void setProvinceCost(BigDecimal provinceCost) {
		this.provinceCost = provinceCost;
	}
	public BigDecimal getHotelCost() {
		return hotelCost;
	}
	public void setHotelCost(BigDecimal hotelCost) {
		this.hotelCost = hotelCost;
	}
	public BigDecimal getLaborCost() {
		return laborCost;
	}
	public void setLaborCost(BigDecimal laborCost) {
		this.laborCost = laborCost;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getOtherCost() {
		return otherCost;
	}
	public void setOtherCost(BigDecimal otherCost) {
		this.otherCost = otherCost;
	}
	@Override
	public String toString() {
		return "ErpNonConferenceCost [id=" + id + ", nonConferenceId="
				+ nonConferenceId + ", name=" + name + ", travelCost="
				+ travelCost + ", cityCost=" + cityCost + ", provinceCost="
				+ provinceCost + ", hotelCost=" + hotelCost + ", laborCost="
				+ laborCost + ", otherCost=" + otherCost + ", amount=" + amount
				+ "]";
	}
	
}
