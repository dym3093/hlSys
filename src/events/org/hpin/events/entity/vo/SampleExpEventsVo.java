package org.hpin.events.entity.vo;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 样本快递费用管理展示场次信息VO
 * @author ybc
 * @since 2016/12/15
 */
public class SampleExpEventsVo extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String eveid;
	
	private String eventsBatchno;

	private String eventsNo;
	
	private Date eventsDate;
	
	private String branchCompanyId;
	
	private String ownedCompanyId;
	
	private Integer hasInHead;

	public String getEveid() {
		return eveid;
	}

	public void setEveid(String eveid) {
		this.eveid = eveid;
	}

	public String getEventsBatchno() {
		return eventsBatchno;
	}

	public void setEventsBatchno(String eventsBatchno) {
		this.eventsBatchno = eventsBatchno;
	}

	public String getEventsNo() {
		return eventsNo;
	}

	public void setEventsNo(String eventsNo) {
		this.eventsNo = eventsNo;
	}

	public Date getEventsDate() {
		return eventsDate;
	}

	public void setEventsDate(Date eventsDate) {
		this.eventsDate = eventsDate;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public String getOwnedCompanyId() {
		return ownedCompanyId;
	}

	public void setOwnedCompanyId(String ownedCompanyId) {
		this.ownedCompanyId = ownedCompanyId;
	}

	public Integer getHasInHead() {
		return hasInHead;
	}

	public void setHasInHead(Integer hasInHead) {
		this.hasInHead = hasInHead;
	}
	
}
