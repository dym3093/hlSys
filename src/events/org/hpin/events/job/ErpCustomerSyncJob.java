package org.hpin.events.job;

import java.util.List;

import org.apache.log4j.Logger;
import org.hpin.events.entity.ErpCustomerSync;
import org.hpin.events.service.ErpCustomerSyncService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 基因系统中当修改完善基因客户信息时满足条件调用CRM备案接口
 * @author machuan
 * @date 2016年11月25日
 */
public class ErpCustomerSyncJob {

	private static Logger log = Logger.getLogger(ErpCustomerSyncJob.class);
	@Autowired
	private ErpCustomerSyncService customerService;
	public void execute(){
		try{
			//获取ErpCustomerSync表中所有状态为0的数据  
			List<ErpCustomerSync> list = customerService.findListCustomerSync();
			int count = 0;
			for(ErpCustomerSync customerSync : list){
				// ErpCustomerSync 调用接口同步数据
				String retCode = customerService.getSyncXmlRetCode(customerSync);	//调用接口，返回的结果
				log.info("ErpCustomerSyncJob--id ="+customerSync.getId()+",retCode="+retCode);
				//获取返回的状态码  判断成功或者失败 修改ErpCustomerSync表中数据的状态码
				if("0".equals(retCode)){
					customerSync.setStatus("1");
					count++;
				}
				customerService.updateSyncInfo(customerSync);
			}
			log.info("ErpCustomerSyncJob--successCount="+count);
		}catch(Exception e){
			log.error("ErpCustomerSyncJob -- error :",e);
		}
	}

}
