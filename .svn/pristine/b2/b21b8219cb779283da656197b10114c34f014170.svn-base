
package org.hpin.reportdetail.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.reportdetail.service.ReportPassBackShenyouService;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;


/**
* 公司名称: 远盟康健(北京)科技有限公司
* author: dengqin 
* createDate: 2016-4-14 下午5:42:20
*/


@Namespace("/reportdetail")
@Action("reportPassBackShenyou")
@Results({
	@Result(name = "toModifyCustomerInfo", location = "/WEB-INF/report/modifyCustomerInfo.jsp"),
})  

public class ReportPassBackShenyouAction extends BaseAction{
	private Logger log = Logger.getLogger(ReportPassBackShenyouAction.class);
	@Autowired
	private ReportPassBackShenyouService reportPassBackShenyouService; //service
	
	public String toModifyCustomerInfo() {
		try{
			String id = HttpTool.getParameter("id");
			//根据ID去拿reportCustomerInfo
			Map<String,Object> map = reportPassBackShenyouService.findCustomerInfoById(id);
			HttpTool.setAttribute("customerInfo", map);
		}catch(Exception e){
			log.error("toModifyCustomerInfo--errror:",e);
		}
		return "toModifyCustomerInfo";
	}
	
	public void editCustomerInfo(){
		JSONObject json = new JSONObject();
		try{
			String id = HttpTool.getParameter("id");
			String name = HttpTool.getParameter("name");
			String navTabId = HttpTool.getParameter("navTabId");
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			String userId=currentUser.getId();
			if(StringUtils.isNotBlank(id)&&StringUtils.isNotBlank(name)){
				reportPassBackShenyouService.editCustomerInfo(id,name,userId);
			}
			json.accumulate("statusCode", 200);
			json.accumulate("message", "修改成功");
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "closeCurrent");
		}catch(Exception e){
			log.error("editCustomerInfo--error:", e);
			json.accumulate("statusCode", 300); 
			json.accumulate("message", "操作失败");
		}
		renderJson(json);
	}
    
}
