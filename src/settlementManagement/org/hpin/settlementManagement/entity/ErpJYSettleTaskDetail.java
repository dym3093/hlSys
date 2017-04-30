package org.hpin.settlementManagement.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

public class ErpJYSettleTaskDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String settleTaskId;		//基因结算任务ID
	
	private BigDecimal sumAmount;		//总付款金额
	
	private String geCompany;			//基因公司
	
	private String geCompanyId;
	
	private Date payTime;				//付款时间
	
	private String payBank;				//付款银行
	
	private String payMode;				//支付方式
	
	private BigDecimal currentAmount;	//当场付款金额
	
	private BigDecimal noPayAmount;		//未付金额
	
	private String OANo;				//OA编号
	
	private String collectionCompany;	//收款公司
	
	private String collectionCompanyId;	//收款公司Id
	
	private String createUserName;		
	
	private Date createTime;			
	
	private String note;				//入账说明

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSettleTaskId() {
		return settleTaskId;
	}

	public void setSettleTaskId(String settleTaskId) {
		this.settleTaskId = settleTaskId;
	}

	public BigDecimal getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getGeCompany() {
		return geCompany;
	}

	public void setGeCompany(String geCompany) {
		this.geCompany = geCompany;
	}

	public String getGeCompanyId() {
		return geCompanyId;
	}

	public void setGeCompanyId(String geCompanyId) {
		this.geCompanyId = geCompanyId;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayBank() {
		return payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	
	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

	public BigDecimal getNoPayAmount() {
		return noPayAmount;
	}

	public void setNoPayAmount(BigDecimal noPayAmount) {
		this.noPayAmount = noPayAmount;
	}

	public String getOANo() {
		return OANo;
	}

	public void setOANo(String oANo) {
		OANo = oANo;
	}

	public String getCollectionCompany() {
		return collectionCompany;
	}

	public void setCollectionCompany(String collectionCompany) {
		this.collectionCompany = collectionCompany;
	}

	public String getCollectionCompanyId() {
		return collectionCompanyId;
	}

	public void setCollectionCompanyId(String collectionCompanyId) {
		this.collectionCompanyId = collectionCompanyId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
