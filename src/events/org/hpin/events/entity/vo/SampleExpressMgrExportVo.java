package org.hpin.events.entity.vo;

import java.math.BigDecimal;
import java.util.Date;
import org.hpin.common.core.orm.BaseEntity;

/**
 * 样本快递费管理导出表格VO
 * @author ybc
 * @since 2016/12/21
 */
public class SampleExpressMgrExportVo extends BaseEntity{
	
	private static final long serialVersionUID = 9083835472929379578L;

	private String expressNum;				//快递单号
	
	private String expressCompanyId;		//快递公司ID
	
	private String totalCost;			//快递费用
	
	private String receiveSendType;			//寄送类型
	
	private String receiveSendDate;			//收发件日期
	
	private String expressContent;			//包裹内容
	
	private String isbill;					//发票有无
	
	private String eventsNo;				//场次号
	
	private String eventBatchno;			//场次批次号
	
	private String eventDate;					//快递日期
	
	private String branchCompanyId;		//支公司ID
	
	private String ownedCompanyId;			//总公司ID
	
	private String hasInHead;				//已录入人数
	
	private String expHead;				//寄送人数
	
	private String avgCost;				//均摊费用

	public String getExpressNum() {
		return expressNum;
	}

	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}

	public String getExpressCompanyId() {
		return expressCompanyId;
	}

	public void setExpressCompanyId(String expressCompanyId) {
		this.expressCompanyId = expressCompanyId;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getReceiveSendType() {
		return receiveSendType;
	}

	public void setReceiveSendType(String receiveSendType) {
		this.receiveSendType = receiveSendType;
	}

	public String getReceiveSendDate() {
		return receiveSendDate;
	}

	public void setReceiveSendDate(String receiveSendDate) {
		this.receiveSendDate = receiveSendDate;
	}

	public String getExpressContent() {
		return expressContent;
	}

	public void setExpressContent(String expressContent) {
		this.expressContent = expressContent;
	}

	public String getIsbill() {
		return isbill;
	}

	public void setIsbill(String isbill) {
		this.isbill = isbill;
	}

	public String getEventsNo() {
		return eventsNo;
	}

	public void setEventsNo(String eventsNo) {
		this.eventsNo = eventsNo;
	}

	public String getEventBatchno() {
		return eventBatchno;
	}

	public void setEventBatchno(String eventBatchno) {
		this.eventBatchno = eventBatchno;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
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

	public String getHasInHead() {
		return hasInHead;
	}

	public void setHasInHead(String hasInHead) {
		this.hasInHead = hasInHead;
	}

	public String getExpHead() {
		return expHead;
	}

	public void setExpHead(String expHead) {
		this.expHead = expHead;
	}

	public String getAvgCost() {
		return avgCost;
	}

	public void setAvgCost(String avgCost) {
		this.avgCost = avgCost;
	}
	
}
