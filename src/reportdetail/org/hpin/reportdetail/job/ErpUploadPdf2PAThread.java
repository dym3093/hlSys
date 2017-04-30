package org.hpin.reportdetail.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.orm.daoWrapper.DaoSupport;
import org.hpin.reportdetail.entity.ErpReportPATask;
import org.hpin.warehouse.entity.ErpPreCustomer;
import org.hpin.webservice.websExt.impl.GeneServiceImplPA;
import org.hpin.webservice.websExt.impl.GeneServiceImplPAServiceLocator;
import org.json.JSONObject;



/**
 * @author Carly
 * @since 2017年2月8日12:18:29
 * pdf转jpg
 */
@SuppressWarnings("unchecked")
public class ErpUploadPdf2PAThread implements Callable<Map<String, String>> {
	
	private String paAddress;
	private DaoSupport dao = null;
	
	public ErpUploadPdf2PAThread(String paAddress) {
		this.paAddress = paAddress;
		if (null == dao) {
			dao = (DaoSupport) SpringTool.getBean("DaoSupport");
		}
	}
	
	
	@Override
	public Map<String, String> call() throws Exception {
		Logger logger = Logger.getLogger("uploadPAReport");
		long start = System.currentTimeMillis();
		logger.info("ErpUploadPdf2PAThread run start time");
		Map<String, String> result = new HashMap<String, String>();
		String uploadPdfSql = "from ErpReportPATask where uploadState=0 and isDeleted=0";	//获取未上传的报告
		String updateStateSql = "update erp_report_pa_task set upload_state=?,update_time=to_date(?,'yyyy-MM-dd hh24:mi:ss') where id=?";	//更新上传状态
		String updateReportTimeSql = "update erp_pre_customer set status_ym=?, file_path=? where id=?";	//更新报告上传的时间
		String preCustomerSql = "from ErpPreCustomer where code=? and isDeleted=0";		//获取预导入表的客户信息
		String updateMessageSql = "update erp_report_pa_task set message=?,update_time=to_date(?,'yyyy-MM-dd hh24:mi:ss') where id =?";	//更新返回的备注信息
		String updateCustomerSql = "update erp_customer set status_ym=?, pdffilepath=? where id=?";	
		try {
			List<ErpReportPATask> taskList = dao.getHibernateTemplate().find(uploadPdfSql);
			logger.info("待上传报告人数---"+taskList.size());
			int count = 1;
			for (ErpReportPATask task : taskList) {
					logger.info("开始上传第"+count+"个人的报告");
					String formateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					List<ErpPreCustomer> preCustomerList = dao.getHibernateTemplate().find(preCustomerSql,task.getCode());
					switch (preCustomerList.size()) {
					case 0:
						logger.info("没有找到对应的客户信息---"+task.getCode());
						result.put("nonCode", task.getCode());
						break;
						
					case 1:
						logger.info("找到对应的客户信息---"+task.getCode());
						ErpPreCustomer preCustomer = preCustomerList.get(0);
						String info = getUploadResult(preCustomer,task);
						logger.info("获取到的上传到平安的报告信息---"+info);
						JSONObject json = new JSONObject(info);
						if (json.getBoolean("success")) {
							String filePath = task.getFilePath().replace("/home/ftp/transact","http://img.healthlink.cn:8099/ymReport");
							dao.getJdbcTemplate().update(updateStateSql, 1,formateDate,task.getId());
							dao.getJdbcTemplate().update(updateReportTimeSql,300,filePath,preCustomer.getId());
							dao.getJdbcTemplate().update(updateCustomerSql,300,filePath,preCustomer.getErpCustomerId());
						} else {
							dao.getJdbcTemplate().update(updateMessageSql,json.getString("msg"),formateDate,task.getId());
						}
						break;
	
					default:
						logger.info("找到多条对应的客户信息---"+task.getCode());
						result.put("moreCode", task.getCode());
						break;
					}
				count ++;
			}
		} catch (Exception e) {
			logger.error("ErpUploadPdf2PAThread run error --- " ,e);
			result.put("exception", e.toString());
		}
			
		logger.info("ErpUploadPdf2PAThread run end  use time : " + (System.currentTimeMillis() - start));
		return result;
	}
	
	/**
	 * @param preCustomer
	 * @param task 
	 * @return 通过接口上传报告
	 * @throws Exception
	 */
	public String getUploadResult(ErpPreCustomer preCustomer, ErpReportPATask task) throws Exception{
		GeneServiceImplPAServiceLocator push = new GeneServiceImplPAServiceLocator();
		push.setGeneServiceImplPAPortEndpointAddress(paAddress);
		GeneServiceImplPA paService = push.getGeneServiceImplPAPort();
		String info = paService.uploadReports(preCustomer.getPerformNo(), preCustomer.getDetailedTestItem(),task.getFilePath());
		return info;
	}
	
}
