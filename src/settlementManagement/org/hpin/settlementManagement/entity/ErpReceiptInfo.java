package org.hpin.settlementManagement.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 收款数据实体类
 * @author LeslieTong
 * @date 2017-3-29下午2:24:54
 */

public class ErpReceiptInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	/** 我方公司名称 */
	private String ownCompanyName;
	
	/** 收付款类别 */
	private String paymentTYpe;
	
	/** 我方现金/银行账户名称/POS机号 */
	private String ownCashBankAccountNamePOSNo;
	
	/** 我方现金/银行账户账号 */
	private String ownCashBankAccounts;
	
	/** 银行流水号 */
	private String BankStatement;
	
	/** 收付款日期 */
	private Date paymentDate;
	
	/** 实际银行到账日期 */
	private Date actualBankAccountArrival;
	
	/** 收付款金额 */
	private BigDecimal paymentCost;
	
	/** POS机手续费 */
	private BigDecimal POSFee;
	
	/** 实际银行到账金额 */
	private BigDecimal actualBankAccountCost;
	
	/** 付款方现金/付款方名称 */
	private String payerCostPayerName;
	
	/** 付款方现金/银行账户账号 */
	private String payerCostBankAccount;
	
	/** 付款方银行账户名称 */
	private String payerBankAccountName;
	
	/** 银行摘要 */
	private String BankSummary;
	
	/** 收款账户 */
	private String receivablesAccount;
	
	/** 付款账户 */
	private String paymentAccount;
	
	private String createUserName;
	
	private Date createDate;
	
	private Integer isDeleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwnCompanyName() {
		return ownCompanyName;
	}

	public void setOwnCompanyName(String ownCompanyName) {
		this.ownCompanyName = ownCompanyName;
	}

	public String getPaymentTYpe() {
		return paymentTYpe;
	}

	public void setPaymentTYpe(String paymentTYpe) {
		this.paymentTYpe = paymentTYpe;
	}

	public String getOwnCashBankAccountNamePOSNo() {
		return ownCashBankAccountNamePOSNo;
	}

	public void setOwnCashBankAccountNamePOSNo(String ownCashBankAccountNamePOSNo) {
		this.ownCashBankAccountNamePOSNo = ownCashBankAccountNamePOSNo;
	}

	public String getOwnCashBankAccounts() {
		return ownCashBankAccounts;
	}

	public void setOwnCashBankAccounts(String ownCashBankAccounts) {
		this.ownCashBankAccounts = ownCashBankAccounts;
	}

	public String getBankStatement() {
		return BankStatement;
	}

	public void setBankStatement(String bankStatement) {
		BankStatement = bankStatement;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getActualBankAccountArrival() {
		return actualBankAccountArrival;
	}

	public void setActualBankAccountArrival(Date actualBankAccountArrival) {
		this.actualBankAccountArrival = actualBankAccountArrival;
	}

	public BigDecimal getPaymentCost() {
		return paymentCost;
	}

	public void setPaymentCost(BigDecimal paymentCost) {
		this.paymentCost = paymentCost;
	}

	public BigDecimal getPOSFee() {
		return POSFee;
	}

	public void setPOSFee(BigDecimal pOSFee) {
		POSFee = pOSFee;
	}

	public BigDecimal getActualBankAccountCost() {
		return actualBankAccountCost;
	}

	public void setActualBankAccountCost(BigDecimal actualBankAccountCost) {
		this.actualBankAccountCost = actualBankAccountCost;
	}

	public String getPayerCostPayerName() {
		return payerCostPayerName;
	}

	public void setPayerCostPayerName(String payerCostPayerName) {
		this.payerCostPayerName = payerCostPayerName;
	}

	public String getPayerBankAccountName() {
		return payerBankAccountName;
	}

	public void setPayerBankAccountName(String payerBankAccountName) {
		this.payerBankAccountName = payerBankAccountName;
	}

	public String getPayerCostBankAccount() {
		return payerCostBankAccount;
	}

	public void setPayerCostBankAccount(String payerCostBankAccount) {
		this.payerCostBankAccount = payerCostBankAccount;
	}
	
	public String getBankSummary() {
		return BankSummary;
	}

	public void setBankSummary(String bankSummary) {
		BankSummary = bankSummary;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public String getReceivablesAccount() {
		return receivablesAccount;
	}

	public void setReceivablesAccount(String receivablesAccount) {
		this.receivablesAccount = receivablesAccount;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	@Override
	public String toString() {
		return "ErpReceiptInfo [id=" + id + ", ownCompanyName="
				+ ownCompanyName + ", paymentTYpe=" + paymentTYpe
				+ ", ownCashBankAccountNamePOSNo="
				+ ownCashBankAccountNamePOSNo + ", ownCashBankAccounts="
				+ ownCashBankAccounts + ", BankStatement=" + BankStatement
				+ ", paymentDate=" + paymentDate
				+ ", actualBankAccountArrival=" + actualBankAccountArrival
				+ ", paymentCost=" + paymentCost + ", POSFee=" + POSFee
				+ ", actualBankAccountCost=" + actualBankAccountCost
				+ ", payerCostBankAccountName=" + payerCostPayerName
				+ ", payerCostBankAccount=" + payerCostBankAccount + ", note="
				+ BankSummary + ", receivablesAccount=" + receivablesAccount
				+ ", paymentAccount=" + paymentAccount + ", createUserName="
				+ createUserName + ", createDate=" + createDate
				+ ", isDeleted=" + isDeleted + "]";
	}
	
}
