package org.hpin.reportdetail.job;

import org.hpin.reportdetail.service.ReportPassBackShenyouService;
import org.springframework.beans.factory.annotation.Autowired;

public class ReportPassBackShenyouJob {
	
	@Autowired
	private ReportPassBackShenyouService reportPassBackShenyouService; //service
	
	/**
	 * 定时任务执行入口;
	 * 执行定时任务每天早上定时查询数据进行匹配并按照指定格式传给申友;
	 * <p>Description: </p>
	 * @author herny.xu
	 * @date 2017年3月15日
	 */
	public void execute(){
		//log.info("经销商收到申友已发布报告");
		/*
		 * 查询昨天的按照条件匹配的数据;如果没有这标记为-1,如果成功上传标记为1;
		 */
		//根据日期查询匹配条件的数据;
		reportPassBackShenyouService.findYesterdayDataByCondition() ;
		
	}
	
	
	
}
