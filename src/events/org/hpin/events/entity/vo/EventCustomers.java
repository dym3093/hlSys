package org.hpin.events.entity.vo;

import java.util.List;
import java.util.Map;

/**
 * 
 * @description: 用于场次客户信息确认转换为json使用;
 * create by henry.xu 2016年11月28日
 */
public class EventCustomers {
	private String startTime; //开始时间;
	private String endTime; //结束时间
	private String showsName; //场次名称(规则: 年与日+名称);
	private String fieldInfo; //场次地点
	
	/**
	 * userList中Map对象包含以下字段;
	 *  "birthday":"2016-11-26 16:47:15",
		"sex":"男",
		"height":181,
		"reportType":"16",
		"weight":78,
		"ymuserId":"",
		"serviceCode":"w0002",
		"userName":"张三",
		"institution_id":0

	 */
	private List<Map<String, String>> userList ;
	private List<String> deviceSNs; //由于我们系统中没有,则返回null就好;
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getShowsName() {
		return showsName;
	}
	public void setShowsName(String showsName) {
		this.showsName = showsName;
	}
	public String getFieldInfo() {
		return fieldInfo;
	}
	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}
	public List<Map<String, String>> getUserList() {
		return userList;
	}
	public void setUserList(List<Map<String, String>> userList) {
		this.userList = userList;
	}
	public List<String> getDeviceSNs() {
		return deviceSNs;
	}
	public void setDeviceSNs(List<String> deviceSNs) {
		this.deviceSNs = deviceSNs;
	}
	
}
