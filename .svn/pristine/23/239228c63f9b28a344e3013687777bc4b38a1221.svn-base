package org.hpin.events.web;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.events.entity.Express;
import org.hpin.events.service.ExpressService;

@Namespace("/events")
@Action("express")
@Results({
	@Result(name="listExpress",location="/WEB-INF/events/erpExpress/listExpress.jsp"),
})	
public class ExpressAction extends BaseAction {
	ExpressService service = (ExpressService) SpringTool.getBean(ExpressService.class);
	private Express express;

	public String listExpress(){
		String postid = HttpTool.getParameter("postid");
		List<Express> list = service.listExpress(postid);
		HttpTool.setAttribute("express", list);
		return "listExpress";
	}
	public String listExpressForAjax(){
		JSONObject json = new JSONObject();
		String postid = HttpTool.getParameter("code");
		List<Express> list = service.listExpress(postid);
		HttpTool.setAttribute("express", list);
		json.accumulate("emsName", list.get(0).getCompTyp());
		renderJson(json);
		return null;
	}
	public Express getExpress() {
		return express;
	}

	public void setExpress(Express express) {
		this.express = express;
	}
	
}
