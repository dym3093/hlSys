package org.hpin.events.entity;

import java.math.BigDecimal;

import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.common.core.orm.BaseEntity;
import org.hpin.common.util.PreciseCompute;

public class ProjectRelationship extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private String id;
	private String batchNum;
	private String customerRelationShipId;
	private String customerRelationShipName;
	private String customerRelationShipProId;
	private String projectCode;
	private String projectName;
	private String projectOwner; 
	private String linkName;
	
	private String proJointPerson;
	private String personNum;
	private BigDecimal price;
	private String sampleDeliManagerId;
	
	private CustomerRelationShip ship; //用于传参
	private CustomerRelationShipPro shipPro; //用于传参
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getCustomerRelationShipId() {
		return customerRelationShipId;
	}
	public void setCustomerRelationShipId(String customerRelationShipId) {
		this.customerRelationShipId = customerRelationShipId;
	}
	public String getCustomerRelationShipProId() {
		return customerRelationShipProId;
	}
	public void setCustomerRelationShipProId(String customerRelationShipProId) {
		this.customerRelationShipProId = customerRelationShipProId;
	}
	public String getProJointPerson() {
		return proJointPerson;
	}
	public void setProJointPerson(String proJointPerson) {
		this.proJointPerson = proJointPerson;
	}
	public String getPersonNum() {
		return personNum;
	}
	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public void setPriceBak(String priceBak) {
		this.price = PreciseCompute.formatComma2BigDecimal(priceBak, 2);
	}
	
	public String getSampleDeliManagerId() {
		return sampleDeliManagerId;
	}
	public void setSampleDeliManagerId(String sampleDeliManagerId) {
		this.sampleDeliManagerId = sampleDeliManagerId;
	}
	public CustomerRelationShip getShip() {
		return ship;
	}
	public void setShip(CustomerRelationShip ship) {
		this.ship = ship;
	}
	public CustomerRelationShipPro getShipPro() {
		return shipPro;
	}
	public void setShipPro(CustomerRelationShipPro shipPro) {
		this.shipPro = shipPro;
	}
	public String getCustomerRelationShipName() {
		return customerRelationShipName;
	}
	public void setCustomerRelationShipName(String customerRelationShipName) {
		this.customerRelationShipName = customerRelationShipName;
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
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	
	
}
