package org.hpin.barcode.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.base.usermanager.entity.User;
import org.hpin.barcode.entity.BarCodeBat;
import org.hpin.barcode.service.BarCodeBatService;

//http://192.168.1.15/hbs/business/barCodeBat!listbarCodeBat.action
@Namespace("/business")
@Action("barCodeBat")
@Results({
	@Result(name = "listbarCodeBat", location = "/WEB-INF/business/barcode/listbarCodeBat.jsp"),
	@Result(name = "addbarCodeBat", location = "/WEB-INF/business/barcode/addbarCodeBat.jsp"),
	@Result(name = "browbarCodeBat", location = "/WEB-INF/business/barcode/browbarCodeBat.jsp"),
	@Result(name = "modifybarCodeBat", location = "/WEB-INF/business/barcode/modifybarCodeBat.jsp")
})
public class BarCodeBatAction extends BaseAction{
	BarCodeBatService barCodeBatService = (BarCodeBatService) SpringTool.getBean(BarCodeBatService.class);
	private BarCodeBat barCodeBat;
	
	public String addbarCodeBat(){
		return "addbarCodeBat";
	}
	public String modifybarCodeBat(){
		barCodeBat = (BarCodeBat)barCodeBatService.findById(id);
		return "modifybarCodeBat";
	}
	public String browbarCodeBat(){
		barCodeBat = (BarCodeBat)barCodeBatService.findById(id);
		return "browbarCodeBat";
	}
	public String getDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
        String data = sDateFormat.format(new java.util.Date());
        return data;
	}
	public String saveBarCodeBat(){
		JSONObject json = new JSONObject();
		try {	
			if(StrUtils.isNotNullOrBlank(barCodeBat.getId())){
				barCodeBatService.update(barCodeBat);
			}else{
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			barCodeBat.setCreateDeptId(currentUser.getDeptId());
			barCodeBat.setCreateUserId(currentUser.getUserName());
			//取得批次号		
			String batno=barCodeBatService.findNewBatNo(getDate());
			barCodeBat.setBatNO(batno);
			barCodeBat.setId(null);
			barCodeBat.setCreateTime(new Date());						
			barCodeBatService.save(barCodeBat);
			}
			
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "closeCurrent");
		}catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
		}
		renderJson(json);
		return null;
	}
	
	
	public Page findByPage(Page page , Map paramsMap){
		paramsMap.put("order_createTime", "desc");
		barCodeBatService.findByPage(page, paramsMap);
		return page ;
	}
	
	public String listbarCodeBat() throws ParseException{
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			findByPage(page, paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listbarCodeBat";
	}

	public BarCodeBat getBarCodeBat() {
		return barCodeBat;
	}

	public void setBarCodeBat(BarCodeBat barCodeBat) {
		this.barCodeBat = barCodeBat;
	}

	
}
