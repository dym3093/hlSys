package org.hpin.reportdetail.web;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;

/**
 * 文件上传进度获取Servlet//在原有的基础上重新修改;
 * @author henry.xu
 * @CreateDate：2016.09.21
 */
@Namespace("/shield")
@Action("shieldProcess")
public class ShieldProcessActon extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	/**报表统计-场次查询-导出预设定值;*/
	public static final String REPORTEVENTSSALESMAN = "report_eventsSalesman";
	/**场次管理-客户信息;*/
	public static final String EVENTSLISTALLCUSTOMER = "events_listAllCustomer";
	/**场次管理-场次信息;*/
	public static final String EVENTSLISTEVENTS = "events_listEvents";
	
	/**
	 * 有效性,正在导出/导入;
	 */
	public static final String VALIDITY = "00";
	/**
	 * 无效性; 完成了导入/导出;
	 */
	public static final String NOTVALIDITY = "11";
	/**
	 * 存在
	 */
	public static final String EXIST = "exist";
	/**
	 * 不存在;
	 */
	public static final String NOTEXIST = "notexist";
	
	/**
	 * 导出预处理访问操作;
	 * 判断该session是否在有效时间类是否处理过导出;
	 */
	public void dealSessionPre() {
		

		String applyKey = HttpTool.getParameter("applyKey", "");
		String flag = (String) HttpTool.getSession().getAttribute(applyKey); //用于临时保存进度完成情况;
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(StringUtils.isEmpty(flag)) {
			flag = (String) request.getSession().getAttribute(EVENTSLISTALLCUSTOMER);
		}
		
		if(StringUtils.isEmpty(flag)) {
			flag = (String) request.getSession().getAttribute(REPORTEVENTSSALESMAN);
		}
		
		if(StringUtils.isEmpty(flag)) {
			flag = (String) request.getSession().getAttribute(EVENTSLISTEVENTS);			
		}
		
		JSONObject jsonObject = new JSONObject();
		
		if(StringUtils.isEmpty(flag)) {//此时说明都没有;
			request.getSession().removeAttribute(EVENTSLISTALLCUSTOMER);
			request.getSession().removeAttribute(REPORTEVENTSSALESMAN);
			request.getSession().removeAttribute(EVENTSLISTEVENTS);
			jsonObject.put("result", NOTEXIST);			
			request.getSession().setAttribute(applyKey, "00");
		} else {
			jsonObject.put("result", EXIST);
		}
		renderJson(jsonObject);
	}
	
	/**
	 * 导出导入进行中处理;
	 * 如果有判断点了继续进入该方法, 没有直接进入;
	 * 不断获取该方法;
	 */
	public void dealSessionProcess() {
		JSONObject jsonObject = new JSONObject();
		String applyKey = HttpTool.getParameter("applyKey", "");
		HttpServletRequest request = ServletActionContext.getRequest();
		String flag = (String) request.getSession().getAttribute(applyKey); //用于临时保存进度完成情况;
		
		//如果不存在,重新设置; 当点击导出按钮时,设置该导出判断值为0;
		if(StringUtils.isEmpty(flag) || NOTVALIDITY.equals(flag)) {
			request.getSession().removeAttribute(applyKey); //当失效时,删除该属性;
			jsonObject.put("result", NOTVALIDITY);
		} else {
			jsonObject.put("result", VALIDITY);
		}
		
		renderJson(jsonObject);
	}
	
	/**
	 * 移除session中指定值;
	 */
	public void deleteSessonProcess() {
		String applyKey = HttpTool.getParameter("applyKey", "");
		//直接移除该值;
		HttpTool.getSession().removeAttribute(applyKey);
	}
	
}
