/**
 * 
 */
package org.hpin.reportdetail.entity.vo;

import java.util.Date;

/**
 * @author machuan
 * @date 2016年12月13日
 */
public class ErpReportExpressVo {
	private String id;
	//批次号
	private String batchNo;
	//场次号
	private String eventsNo;
	//场次日期
	private Date eventsDate;
	//支公司
	private String branchCommany;
	//已录人数
	private String alreadyCount;
	//已寄送报告数
	private String haveReportCount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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
	public String getBranchCommany() {
		return branchCommany;
	}
	public void setBranchCommany(String branchCommany) {
		this.branchCommany = branchCommany;
	}
	public String getAlreadyCount() {
		return alreadyCount;
	}
	public void setAlreadyCount(String alreadyCount) {
		this.alreadyCount = alreadyCount;
	}
	public String getHaveReportCount() {
		return haveReportCount;
	}
	public void setHaveReportCount(String haveReportCount) {
		this.haveReportCount = haveReportCount;
	}
	
}
