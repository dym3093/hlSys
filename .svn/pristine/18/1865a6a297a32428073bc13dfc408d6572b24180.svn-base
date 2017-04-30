package org.hpin.reportdetail.job;

import org.hpin.reportdetail.service.ReportOverdueWarnService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 生物电报告报告超时效提醒：
 * 定时任务：推送后，大于等于3个工作日没有收到报告的客户明细，发邮件给：christywang@healthlink.cn 和nond@healthlink.cn 邮箱
 * 备注：推送日期在erp_qrcode表中的UPDATE_TIME
 * 背景：远盟运营人员推送给知康确认信息后，知康一直没有提供pdf报告，造成大量客户投诉
 * <p>Description: </p>
 * @author henry.xu
 * @date 2017年3月30日
 */
public class ReportOverdueWarnJob {
	
	@Autowired
	private ReportOverdueWarnService reportOverdueWarnService;
	
	/**
	 * 每天6点执行该方法; 统一跟指定账号发邮件;
	 * <p>Description: </p>
	 * @author herny.xu
	 * @date 2017年3月30日
	 */
	public void execute() {
		/* 
		 * 处理过期后不存在文档处理;
		 * 1.数据查询;
		 * 2.邮件处理;
		 */
		reportOverdueWarnService.dealReportOverdueWarn();
	}
}
