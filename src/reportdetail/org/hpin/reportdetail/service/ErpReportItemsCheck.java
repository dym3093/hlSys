package org.hpin.reportdetail.service;

/**
 * 检测报告所涉及的检测项
 * @author ybc
 * @since 2017/3/8
 */
public abstract class ErpReportItemsCheck implements ErpReportStandardCheck{

	private ErpReportStandardCheck standardCheck;
	
	public void setChecker(ErpReportStandardCheck standardCheck){
		this.standardCheck = standardCheck;
	}
	
	@Override
	public void check() {
		standardCheck.check();
	}

}
