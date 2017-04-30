package org.hpin.foreign.web;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.foreign.entity.ErpReportResultsJY;
import org.hpin.foreign.service.ErpReportResultsJYService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 报告详情里(ErpReportOrgJy)的results字段(金域报告)Action
 * @author Damian
 * @since 2017-02-23
 */
@Namespace("/foreign")
@Action("reportResultsJY")
@Results({
    @Result(name="list",location="/WEB-INF/foreign/listReportOrgJY.jsp"),
})  
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ErpReportResultsJYAction extends BaseAction{

	private Logger logger = Logger.getLogger(ErpReportResultsJYAction.class);

	@Autowired
	private ErpReportResultsJYService service;

	public String listReportOrgJY(){
		try {
			Map<String,Object> map = buildSearch();
			map.put("filter_and_isDeleted_EQ_I", 0);
			map.put("order_createTime", "desc");
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<ErpReportResultsJY> list = service.findByPage(page, map);
			page.setResults(list) ;
		} catch (Exception e) {
			logger.error("分页失败"+e);
		}
		
		return "listReportOrgJY";
	}
	
}
