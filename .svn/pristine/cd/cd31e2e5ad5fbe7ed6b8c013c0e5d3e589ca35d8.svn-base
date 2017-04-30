package org.hpin.reportdetail.job;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author chenqi
 * @since 2017年2月23日18:01:09
 * @return 定时传报告给平安
 */
public class ErpUploadPdf2PAJob {

	
	private static Logger logger = Logger.getLogger(ErpUploadPdf2PAJob.class);
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	private String paAddress;
	public synchronized void execute(){
		logger.info("ErpPdf2PAJob excute use time---开始上传平安报告");
		long start = System.currentTimeMillis();
		//根据当前日期获取文件路径
		ErpUploadPdf2PAThread match = new ErpUploadPdf2PAThread(paAddress);
		taskExecutor.submit(match);
		long end = System.currentTimeMillis();
		
		logger.info("ErpPdf2PAJob excute use time---"+ (end-start));
	}
	
	public String getPaAddress() {
		return paAddress;
	}
	public void setPaAddress(String paAddress) {
		this.paAddress = paAddress;
	}

}
