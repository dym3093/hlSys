package org.hpin.settlementManagement.exception;

/**
 * 回款银行流水号重复异常
 * @author LeslieTong
 * @date 2017-3-31下午2:10:18
 */

public class BankStatementRepeatException extends Exception {

	private static final long serialVersionUID = 1L;

	public BankStatementRepeatException(String msg){
		super(msg);
	}

}
