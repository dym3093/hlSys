package org.hpin.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

/**
 * Http请求客户端
 * @author ybc
 * @since 2016-08-22
 */
public class HttpClientTool {

	private static Logger log = Logger.getLogger(HttpClientTool.class);

	private static HttpClientTool httpClientTool = null;

	//构造函数
	private HttpClientTool(){}

	public static synchronized HttpClientTool getInstance(){
		if(httpClientTool == null){
			httpClientTool = new HttpClientTool();
			log.info("HttpClientTool create instance");
		}
		return httpClientTool;
	}

	//get请求方式
	public String httpGet(String url){
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		String response = null;
		try{
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.info("httpGet Method failed: " + getMethod.getStatusLine());
			}else{
				// 返回内容
				response = getMethod.getResponseBodyAsString();
			}
		}catch(HttpException e){
			log.error("HttpClientTool HttpException error :", e);
		}catch(IOException e){
			log.error("HttpClientTool IOException  error :", e);
		}
		finally{
			getMethod.releaseConnection();
		}
		return response;
	}

	/**
	 * create by henry.xu 20161129
	 * @param url
	 * @param param
	 * @return
	 */
	public String httpPost(String url, Map<String, String> param) {
		HttpClient httpClient = new HttpClient();
		String response = null;
		PostMethod postMethod = new PostMethod(url);
		try {
			// 填入各个表单域的值
			NameValuePair[] data = null;
			if(null != param && !param.isEmpty()) {
				data = new NameValuePair[param.keySet().size()];
				int i = 0; 
				for(Map.Entry<String, String> entry : param.entrySet()){ 
					data[i++] = new NameValuePair(entry.getKey(), entry.getValue());
				}
			}
			
			// 将表单的值放入postMethod中
			postMethod.setRequestBody(data);
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
			if(statusCode == HttpStatus.SC_OK) {
				// 返回内容
				response = postMethod.getResponseBodyAsString();
			}
			
			
		} catch(Exception e) {
			log.error("post请求错误.url="+url, e);
		}


		return response;
	}

}
