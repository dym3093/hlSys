package org.hpin.events.job;

import java.util.List;

import org.apache.log4j.Logger;
import org.hpin.events.entity.ErpCustomerTesting;
import org.hpin.events.service.ErpCustomerExceptionService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 基因公司套餐费用维护情况检测
 * @author machuan
 * @date 2016年12月25日
 */
public class ErpCustomerTestingJob {

	private static Logger log = Logger.getLogger(ErpCustomerTestingJob.class);
	@Autowired
	private ErpCustomerExceptionService service;
	public void execute(){
		try{
			//从数据库erp_customer_testing表中查询所有状态为0的场次号
			 List<ErpCustomerTesting> list = service.getAllEventsNo();
			if(list!=null){
				for(ErpCustomerTesting testing : list){
					//判断该场次号是否已经没有异常数据
					boolean flag = service.isHaveException(testing.getId());
					if(flag){
						//发送邮件通知
						service.testingPrice(testing.getId());
					}
				}
			}
			if(list==null||list.size()==0){
				log.info("ErpCustomerTestingJob--success！----无场次号需要维护基因套餐价格。");
			}
		}catch(Exception e){
			log.error("ErpCustomerSyncJob -- error :",e);
		}
	}

}
