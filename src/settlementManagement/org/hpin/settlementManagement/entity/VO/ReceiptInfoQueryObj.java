package org.hpin.settlementManagement.entity.VO;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 存放查询条件Object
 * @author LeslieTong
 * @date 2017-3-29下午8:25:57
 */
public class ReceiptInfoQueryObj {

	
	/** 收款账户 */
	private String receivablesAccount;
	
	/** 付款账户 */
	private String paymentAccount;
	
	/** 收付款日期（开始） */
	private String paymentDateStart;
	
	/** 收付款日期（结束） */
	private String paymentDateEnd;
	
	/** 收付款金额 */
	private String paymentCost;

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

	public String getPaymentDateStart() {
		return paymentDateStart;
	}

	public void setPaymentDateStart(String paymentDateStart) {
		this.paymentDateStart = paymentDateStart;
	}

	public String getPaymentDateEnd() {
		return paymentDateEnd;
	}

	public void setPaymentDateEnd(String paymentDateEnd) {
		this.paymentDateEnd = paymentDateEnd;
	}

	public String getPaymentCost() {
		return paymentCost;
	}

	public void setPaymentCost(String paymentCost) {
		this.paymentCost = paymentCost;
	}

	@Override
	public String toString() {
		return "ReceiptInfoQueryObj [receivablesAccount=" + receivablesAccount
				+ ", paymentAccount=" + paymentAccount + ", paymentDateStart="
				+ paymentDateStart + ", paymentDateEnd=" + paymentDateEnd
				+ ", paymentCost=" + paymentCost + "]";
	}
	
}
