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
import org.hpin.barcode.entity.BarCodeIssue;
import org.hpin.barcode.service.BarCodeIssueService;

//http://192.168.1.15/hbs/business/barCodeIssue!listbarCodeIssue.action
@Namespace("/business")
@Action("barCodeIssue")
@Results({
	@Result(name = "listbarCodeIssue", location = "/WEB-INF/business/barcode/listbarCodeIssue.jsp"),
	@Result(name = "browbarCodeIssue", location = "/WEB-INF/business/barcode/browbarCodeIssue.jsp"),
	@Result(name = "modifybarCodeIssue", location = "/WEB-INF/business/barcode/modifybarCodeIssue.jsp"),
	@Result(name = "addbarCodeIssue", location = "/WEB-INF/business/barcode/addbarCodeIssue.jsp")
})
public class BarCodeIssueAction extends BaseAction{
	BarCodeIssueService barCodeIssueService = (BarCodeIssueService) SpringTool.getBean(BarCodeIssueService.class);
	private BarCodeIssue barCodeIssue;
	
	public String addbarCodeIssue(){
		return "addbarCodeIssue";
	}
	public BarCodeIssue getBarCodeIssue() {
		return barCodeIssue;
	}
	public void setBarCodeIssue(BarCodeIssue barCodeIssue) {
		this.barCodeIssue = barCodeIssue;
	}
	public String modifybarCodeIssue(){
		barCodeIssue = (BarCodeIssue)barCodeIssueService.findById(id);
		return "modifybarCodeIssue";
	}
	public String browbarCodeIssue(){
		barCodeIssue = (BarCodeIssue)barCodeIssueService.findById(id);
		return "browbarCodeIssue";
	}
	
	public String saveBarCodeIssue(){
		JSONObject json = new JSONObject();
		Boolean flag=true;
		try {	
			if(StrUtils.isNotNullOrBlank(barCodeIssue.getId())){
				barCodeIssueService.update(barCodeIssue);
			}else{
			   //查询库存量是否够用
			   flag=barCodeIssueService.isEnoughStock(barCodeIssue.getBarcodeCount());
			   if(flag){
				User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			    barCodeIssue.setCreateUserId(currentUser.getUserName());
			    barCodeIssue.setIsDeleted(0);
			    barCodeIssue.setCreateTime(new Date());	
			    barCodeIssue.setId(null);
			    barCodeIssueService.save(barCodeIssue);
			   }
			}	
		  if(flag){
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "closeCurrent");
		  }else{
			  json.accumulate("statusCode", 300);
			  json.accumulate("message", "库存不够,请核实派发数量!");
		  }
		}catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
		}
		renderJson(json);
		return null;
	}
	public String deletebarCodeIssue(){
		JSONObject json = new JSONObject();
		try {
			String[] id = ids.replaceAll(" ", "").split(",");
			
			barCodeIssueService.deleteIds(id);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "操作成功");
			json.accumulate("navTabId", super.navTabId);
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	
	public Page findByPage(Page page , Map paramsMap){
		paramsMap.put("filter_and_isDeleted_EQ_I",0);
		paramsMap.put("order_createTime", "desc");
		barCodeIssueService.findByPage(page, paramsMap);
		return page ;
	}
	
	public String listbarCodeIssue() throws ParseException{
		try {
			String aaa = HttpTool.getParameter("aaaa","");
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			findByPage(page, paramsMap);
			HttpTool.setAttribute("aaaa", aaa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listbarCodeIssue";
	}



	
}
