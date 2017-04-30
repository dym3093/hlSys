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
import org.hpin.foreign.entity.ErpReportUrlJY;
import org.hpin.foreign.service.ErpReportUrlJYService;

/**
 * @author Carly
 * @since 2016年12月1日17:41:04
 * 报告URL地址信息类
 */
@Namespace("/reportUrlJY")
@Action("erpReportUrlJY")
@Results({
	@Result(name="listReportUrlJY",location="/WEB-INF/foreign/listReportUrlJY.jsp"),
})  
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ErpReportUrlJYAction extends BaseAction{
	private Logger logger = Logger.getLogger(ErpReportUrlJYAction.class);
    
	ErpReportUrlJYService urlJYService = (ErpReportUrlJYService)SpringTool.getBean(ErpReportUrlJYService.class);
	
	public String listReportUrlJY(){
		try {
			Map<String,Object> map = buildSearch();
			map.put("filter_and_isDeleted_EQ_I", 0);
			map.put("order_createTime", "desc");
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<ErpReportUrlJY> orgJYlist=urlJYService.findByPage(page, map);
			page.setResults(orgJYlist) ;
		} catch (Exception e) {
			logger.error("分页失败"+e);
		}
		
		return "listReportUrlJY";
	}
	
}
