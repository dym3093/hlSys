package org.hpin.foreign.web;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.foreign.entity.ErpReportScheduleJY;
import org.hpin.foreign.service.ErpReportScheduleJYService;
import org.hpin.reportdetail.web.ShieldProcessActon;

/**
 * @author Carly
 * @since 2016年12月1日17:41:04
 * 定时获取报告
 */
@Namespace("/reportScheduleJY")
@Action("erpReportScheduleJY")
@Results({
    @Result(name="listReportScheduleJY",location="/WEB-INF/foreign/listReportScheduleJY.jsp"),
    @Result(name="listUnMatchReportScheduleJY",location="/WEB-INF/foreign/listUnMatchReportScheduleJY.jsp"),
    @Result(name="listAllCustomer",location="/WEB-INF/events/erpCustomer/listAllCustomer.jsp")
})  
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ErpReportScheduleJYAction extends BaseAction{
	private Logger logger = Logger.getLogger(ErpReportScheduleJYAction.class);
    
	ErpReportScheduleJYService scheduleJYService = (ErpReportScheduleJYService)SpringTool.getBean(ErpReportScheduleJYService.class);
	ErpCustomerService customerService= (ErpCustomerService) SpringTool.getBean(ErpCustomerService.class);
	
	/**
	 * 查询
	 * @return
	 */
	public String listReportScheduleJY(){
		try {
			Map<String,Object> map = buildSearch();
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			if(map.containsKey("filter_and_code_IN_S")){
				map.remove("filter_and_code_LIKE_S");
			}
			map.put("filter_and_isDeleted_EQ_I", 0);
			map.put("order_createTime", "desc");
			List<ErpReportScheduleJY> oScheduleJYList=scheduleJYService.findByPage(page, map);
			page.setResults(oScheduleJYList) ;
		} catch (Exception e) {
			logger.error("分页失败"+e);
		}
		
		return "listReportScheduleJY";
	}
	
	/**
	 * @since 2016年12月6日11:43:14
	 * @param page
	 * @param paramsMap
	 * @return 导出
	 */
	public Page findByPage(Page page , Map paramsMap){
		try {
			paramsMap.put("filter_and_isDeleted_EQ_I",0);
			List<ErpReportScheduleJY> oScheduleJYList = scheduleJYService.findByPage(page, paramsMap);
			for(ErpReportScheduleJY scheduleJY:oScheduleJYList){
				switch (scheduleJY.getStatus()) {
				case 0:
					scheduleJY.setState("未完成");
					break;
				case 1:
					scheduleJY.setState("已完成");
					break;
				case 9:
					scheduleJY.setState("会员信息不匹配");
					break;
				default:
					scheduleJY.setState("");
					break;
				}
			}
			page.setResults(oScheduleJYList);
		} catch (Exception e) {
			logger.error("导出失败"+e);
		}
		return page;
	}
	
	/**
	 * @since 2016年12月10日18:04:13
	 * @author Carly
	 * @return 不匹配列表
	 */
	public String listUnMatchReportScheduleJY(){
		try {
			Map<String,Object> map = buildSearch();
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			map.put("filter_and_isDeleted_EQ_I", 0);
			map.put("filter_and_status_EQ_I", 9);		//不匹配
			map.put("order_createTime", "desc");
			List<ErpReportScheduleJY> oScheduleJYList=scheduleJYService.findByPage(page, map);
			page.setResults(oScheduleJYList) ;
		} catch (Exception e) {
			logger.error("分页失败"+e);
		}
		
		return "listUnMatchReportScheduleJY";
	}
	
	/**
	 * @since 2016年12月12日10:39:36
	 * @author Carly
	 * @return 跳转到客户查询页
	 */
	public String listAllCustomer(){
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			String jyId = HttpTool.getParameter("jyId");
			ErpReportScheduleJY customer = (ErpReportScheduleJY) scheduleJYService.findById(jyId);
			Map paramsMap = buildSearch();
			paramsMap.put("filter_and_isDeleted_EQ_I", 0);
			if(StringUtils.isNotEmpty(customer.getCode())){
				paramsMap.put("filter_and_code_LIKE_S", customer.getCode());
				HttpTool.setAttribute("filter_and_code_LIKE_S", customer.getCode());
			}
			if (StringUtils.isNotEmpty(customer.getName())) {
				paramsMap.put("filter_and_name_LIKE_S", customer.getName());
				HttpTool.setAttribute("filter_and_name_LIKE_S", customer.getName());
			}
			HttpTool.setAttribute("jyId", jyId);
			customerService.findByPage(page, paramsMap);
			page.setTempProcess(ShieldProcessActon.EVENTSLISTALLCUSTOMER);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "listAllCustomer";
	}
	
}
