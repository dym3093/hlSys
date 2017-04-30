package org.hpin.reportdetail.job;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hpin.reportdetail.entity.ErpReadPDFMatchInfo;
import org.hpin.reportdetail.service.ErpReadPDFMatchInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 读取PDF文本内容并匹配定时任务类
 * @author tangxing
 * @date 2017-3-8下午3:41:33
 */
public class ErpReadPDFMatchInfoJob {

	@Autowired
	ErpReadPDFMatchInfoService service;
	
	public synchronized void execute(){
		Logger log  = Logger.getLogger("readUnMatchReport");
		log.info("====== ErpReadPDFMatchInfoJob start ======");
		
		List<ErpReadPDFMatchInfo> pdfMatchInfos = null;
		try {
			log.info("开始读取所有吉思朗报告---");
			pdfMatchInfos = service.saveErpReadPDFMatchInfo();
			int size = pdfMatchInfos.size();
			int count = 1;
			for (ErpReadPDFMatchInfo erpReadPDFMatchInfo : pdfMatchInfos) {
				log.info("共有" + size + "个报告,正在读取第" + count +"个报告---");
				service.readPdfPageText(erpReadPDFMatchInfo);
				count ++;
			}
			
			log.info("====== ErpReadPDFMatchInfoJob end ======");
		} catch (Exception e) {
			log.error("ErpReadPDFMatchInfoJob error --"+e);
			e.printStackTrace();
		}
	}
	
}
