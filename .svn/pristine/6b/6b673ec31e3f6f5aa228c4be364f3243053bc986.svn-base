package org.hpin.foreign.web;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.foreign.entity.ErpReportOrgJY;
import org.hpin.foreign.service.ErpReportOrgJYService;

/**
 * @author Carly
 * @since 2016年12月1日17:41:04
 * 原始报告信息表
 */
@Namespace("/reportOrgJY")
@Action("erpReportOrgJY")
@Results({
    @Result(name="listReportOrgJY",location="/WEB-INF/foreign/listReportOrgJY.jsp"),
})  
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ErpReportOrgJYAction extends BaseAction{
	private Logger logger = Logger.getLogger(ErpReportOrgJYAction.class);
    
	ErpReportOrgJYService orgJYService = (ErpReportOrgJYService)SpringTool.getBean(ErpReportOrgJYService.class);
	
	public String listReportOrgJY(){
		try {
			Map<String,Object> map = buildSearch();
			map.put("filter_and_isDeleted_EQ_I", 0);
			map.put("order_createTime", "desc");
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<ErpReportOrgJY> orgJYlist=orgJYService.findByPage(page, map);
			page.setResults(orgJYlist) ;
		} catch (Exception e) {
			logger.error("分页失败"+e);
		}
		
		return "listReportOrgJY";
	}
	
}
