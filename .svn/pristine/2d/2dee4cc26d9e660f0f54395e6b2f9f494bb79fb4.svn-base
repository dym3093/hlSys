package org.hpin.events.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.hpin.barcode.service.BarCodeBatService;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.entity.ErpExpress;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.events.service.ErpExpressService;
import org.hpin.events.util.GuNoUtil;

@Namespace("/events")
@Action("erpExpress")
@Results({
	@Result(name="toAddExpres",location="/WEB-INF/events/erpExpress/addExpress.jsp"),
	@Result(name="listEvents",location="/WEB-INF/events/erpEvents/listEvents.jsp"),
})										
public class ErpExpressAction extends BaseAction {
	ErpEventsService eventsService = (ErpEventsService) SpringTool.getBean(ErpEventsService.class);
	ErpCustomerService customerService = (ErpCustomerService) SpringTool.getBean(ErpCustomerService.class);
	ErpExpressService expressService = (ErpExpressService) SpringTool.getBean(ErpExpressService.class);
	DeptService deptService = (DeptService) SpringTool.getBean(DeptService.class);
	private ErpEvents events;
	private ErpCustomer customer;
	private ErpExpress express;
	 
	public ErpCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(ErpCustomer customer) {
		this.customer = customer;
	}
	public ErpExpress getExpress() {
		return express;
	}
	public void setExpress(ErpExpress express) {
		this.express = express;
	}
	public ErpEvents getEvents() {
		return events;
	}
	public void setEvents(ErpEvents events) {
		this.events = events;
	} 
	/**
	 * 
	 * @return
	 */
	public String toAddExpress(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		//String[] id = ids.replaceAll(" ", "").split(",");
		events=eventsService.get(id);
		return "toAddExpres";
	}
	/**
	 * 场次信息增加
	 * @return
	 */
	public String addExpress(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		try {
			if(express==null){
				express=new ErpExpress();
			}
			String trackingNumber = express.getTrackingNumber();
			String eventsNo=express.getEventsNo();
			String message = expressService.isNotRepeatNo(trackingNumber); //判断场次是否已添加
			if(StrUtils.isNullOrBlank(message)){
				express.setIsDeleted(0);
				expressService.save(express);
				
				events=eventsService.queryEventNO(eventsNo);
				events.setIsExpress(1);
				eventsService.modify(events);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "增加成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}else{
				json.accumulate("statusCode", 300);
				json.accumulate("message", message);
			}
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "增加失败");
			e.printStackTrace();
		}
		
		renderJson(json);
		return null;
//		return "listEvents";
	}
	
}
