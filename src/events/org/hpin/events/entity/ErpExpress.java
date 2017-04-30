package org.hpin.events.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * ErpExpress entity. @author MyEclipse Persistence Tools
 */

public class ErpExpress extends BaseEntity implements java.io.Serializable {

	private String id;
	private String name;
	private String trackingNumber;
	private Date edate;
	private String eventsNo;


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrackingNumber() {
		return this.trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Date getEdate() {
		return this.edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	public String getEventsNo() {
		return this.eventsNo;
	}

	public void setEventsNo(String eventsNo) {
		this.eventsNo = eventsNo;
	}

}