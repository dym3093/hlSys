package org.hpin.reportdetail.web;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpReportdetail;
import org.hpin.reportdetail.service.ErpReportdetailService;

@Namespace("/reportdetail")
@Action("erpReportdetail")
@Results({
	@Result(name="listReportdetail",location="/WEB-INF/reportdetail/listReportdetail.jsp"),
	@Result(name="addReportdetail",location="/WEB-INF/reportdetail/addReportdetail.jsp"),
})
public class ErpReportdetailAction extends BaseAction {
	ErpReportdetailService service = (ErpReportdetailService) SpringTool.getBean(ErpReportdetailService.class);
	private ErpReportdetail reportdetail;
	public Page findByPage(Page page , Map paramsMap){
		service.findByPage(page, paramsMap);
		return page ;
	}
	/**
	 * 查询
	 * @return
	 */
	public String listReportdetail(){
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			findByPage(page, paramsMap);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "listReportdetail";
	}
	
	public String addReportdetail(){
		return "addReportdetail";
	}
	/**
	 * 保存或更新
	 * @return
	 */			  
	public String saveOrUpdateReportdetail(){
		JSONObject json = new JSONObject();
		try {
			if(StrUtils.isNotNullOrBlank(reportdetail.getId())){
				service.updateInfo(reportdetail);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "操作成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}else{
				reportdetail.setId(null);
				service.save(reportdetail);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "操作成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 修改页面
	 * @return
	 */
	public String modifyReportdetail(){
		reportdetail = (ErpReportdetail) service.findById(id);
		return "addReportdetail";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deletereportdetail(){
		JSONObject json = new JSONObject();
		String[] id = ids.replaceAll(" ", "").split(",");
		List<ErpReportdetail> list = new LinkedList<ErpReportdetail>();
		try {
			for (int i = 0; i < id.length; i++) {
				reportdetail = (ErpReportdetail) service.findById(id[i]);
				list.add(reportdetail);
			}
			service.deleteInfo(list);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "删除成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "refreshCurrent");
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("statusCode", 300);
			json.accumulate("message", "删除失败");
		}
		renderJson(json);
		return null;
	}
	public ErpReportdetail getReportdetail() {
		return reportdetail;
	}
	public void setReportdetail(ErpReportdetail reportdetail) {
		this.reportdetail = reportdetail;
	}
	
	
}
