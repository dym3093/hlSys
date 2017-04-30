package org.hpin.common.exception;

/**
 * 
 * @description: 自定义异常抛出处理;
 * create by henry.xu 2016年10月12日
 */
public class MyException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public MyException() {
		super();
	}
	
	public MyException(String message) {
		super(message);
	}
	
	/**
	 * 强烈推存使用该方法;
	 * @param message
	 * @param e
	 */
	public MyException(String message, Exception e) {
		super(message, e);
	}
}
