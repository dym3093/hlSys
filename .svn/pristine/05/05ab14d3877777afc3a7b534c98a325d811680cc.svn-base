package org.hpin.barcode.web;

import java.text.ParseException;

import java.util.Map;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.barcode.service.BarCodeInfoService;

//http://192.168.1.15/hbs/business/barCodeInfo!listbarCodeInfo.action
@Namespace("/business")
@Action("barCodeInfo")
@Results({
	@Result(name = "listbarCodeInfo", location = "/WEB-INF/business/barcode/listbarCodeInfo.jsp")
})
public class BarCodeInfoAction extends BaseAction{
	BarCodeInfoService barCodeInfoService = (BarCodeInfoService) SpringTool.getBean(BarCodeInfoService.class);
	
	public Page findByPage(Page page , Map paramsMap){
		paramsMap.put("order_batNO", "desc");
		barCodeInfoService.findByPage(page, paramsMap);
		return page ;
	}
	
	public String listbarCodeInfo() throws ParseException{
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			findByPage(page, paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listbarCodeInfo";
	}

	

	
}
