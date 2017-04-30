package org.hpin.reportdetail.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.hpin.common.core.orm.BaseService;
import org.hpin.reportdetail.dao.ReportPassBackShenyouDao;
import org.hpin.reportdetail.entityVO.ErpReportCustomerVO;
import org.hpin.reportdetail.job.ReportPassBackShenyouJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@Service(value="reportPassBackShenyouService")
@Transactional
public class ReportPassBackShenyouService extends BaseService {
	
	public final static String ACCESSKEY = "f62eb7395e809a214307f93bed8f8a0a";
	
	//日志
	private static Logger log = Logger.getLogger(ReportPassBackShenyouService.class);
	@Autowired
	private ReportPassBackShenyouDao reportPassBackShenyouDao;

	/**
	 * 数据处理
	 * <p>Description: </p>
	 * @author herny.xu
	 * @date 2017年3月16日
	 */
	public void findYesterdayDataByCondition() {
		//测试地址;
		//String url = "http://180.168.201.126:8000/api/api/reportReturnResult_receiveResultByReportId"; 
		
		//正式地址;
		String url = "http://api.sygene.cn/api/reportReturnResult_receiveResultByReportId"; 
		
		String json = "";
		List<ErpReportCustomerVO> list = reportPassBackShenyouDao.findYesterdayDataByCondition();
		if(list != null && list.size() > 0) {
			json = JSONArray.fromObject(list).toString(); 
			try {
				String result = getHbsHttp(url, json);
				
				if(StringUtils.isNotEmpty(result)) {
					if(result.contains("false")) {
						//当返回false的时候,修改状态为1
						updateReportCustomerState(list, 1);
					} else if(result.contains("true")) {
						updateReportCustomerState(list, 2);
					}
				}
				
				
			} catch (Exception e) {
				//当出现异常后修改状态改为0,传输异常;
				updateReportCustomerState(list, 0);
				log.error("申友报告定时任务异常!", e);
			}
		}
		
	}
	
	public void updateReportCustomerState(List<ErpReportCustomerVO> list, int state) {
		this.reportPassBackShenyouDao.updateReportCustomerState(list, state);;
	}
	
	/**
	 * 请求无创url传送数据;
	 * create by henry.xu 2016年12月20日
	 * @param url
	 * @param param
	 * @param code
	 * @param userName
	 * @return
	 */
	@SuppressWarnings({"resource"})
	public String getHbsHttp(String url, String json) throws Exception {
		if(StringUtils.isEmpty(json)) {
			return "";
		}
		
		String result = null; //当出现异常时返回结果;
		log.info("数据推送地址URL: " + url);
		log.info("处理后的JSON数据: " + json);
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//Base64加密
		params.add(new BasicNameValuePair("accessKey", ACCESSKEY));
		params.add(new BasicNameValuePair("json", json));
		HttpEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(formEntity);

		HttpResponse response = client.execute(post);
		log.info("请求返回状态码STATUSCODE:" + response.getStatusLine().getStatusCode());
		
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} else {
			result = "";
		}
		log.info("返回结果result: " + result);

		return result;
	}

	/**
	 * 根据ID 去拿详细信息
	 * @param id
	 * @return
	 * @author machuan
	 * @date  2017年4月17日
	 */
	public Map<String, Object> findCustomerInfoById(String id) {
		String sql ="select * from ERP_REPORT_CUSTOMER_INFO where id='"
				+id
				+ "'";
		return reportPassBackShenyouDao.getJdbcTemplate().queryForMap(sql);
	}

	/**
	 * @param id
	 * @param name
	 * @param userId
	 * @author machuan
	 * @date  2017年4月18日
	 */
	public void editCustomerInfo(String id, String name, String userId) throws Exception {
		String sql = "update erp_report_customer_info i set i.name='"
				+ name
				+ "',i.update_user_id='"
				+ userId
				+ "',i.update_time=sysdate where i.id='"
				+ id
				+ "'";
		reportPassBackShenyouDao.getJdbcTemplate().update(sql);
	}
}
