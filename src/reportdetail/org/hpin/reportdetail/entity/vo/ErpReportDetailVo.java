/**
 * 
 */
package org.hpin.reportdetail.entity.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author machuan
 * @date 2016年12月16日
 */
public class ErpReportDetailVo {
	private String reportId;
	private Date expressDate;
	private String batchNo;
	private BigDecimal totalCost;
	private String expressWeight;
	private String alreadyCount;
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public Date getExpressDate() {
		return expressDate;
	}
	public void setExpressDate(Date expressDate) {
		this.expressDate = expressDate;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	public String getExpressWeight() {
		return expressWeight;
	}
	public void setExpressWeight(String expressWeight) {
		this.expressWeight = expressWeight;
	}
	public String getAlreadyCount() {
		return alreadyCount;
	}
	public void setAlreadyCount(String alreadyCount) {
		this.alreadyCount = alreadyCount;
	}
}
