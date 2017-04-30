package org.hpin.events.web;

import java.text.ParseException;
import java.util.Date;
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
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomerBak;
import org.hpin.events.service.ErpCustomerBakService;

@Namespace("/events")
@Action("erpCustomerBak")
@Results({
	@Result(name="listCustomerBak",location="/WEB-INF/events/erpCustomerBak/listCustomerBak.jsp"),
})
public class ErpCustomerBakAction extends BaseAction {
	ErpCustomerBakService service = (ErpCustomerBakService) SpringTool.getBean(ErpCustomerBakService.class);
	private ErpCustomerBak customerBak;
	
	public Page findByPage(Page page , Map paramsMap){
		paramsMap.put("order_createTime", "asc");
		paramsMap.put("filter_and_isDeleted_EQ_I", 0);
		service.findByPage(page, paramsMap);
		return page ;
	}
	
	/**
	 * 查询
	 * @return
	 */
	public String listCustomerBak(){
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			findByPage(page, paramsMap);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "listCustomerBak";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delCustomerBak(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String[] id = ids.replaceAll(" ", "").split(",");
		List<ErpCustomerBak> list = new LinkedList<ErpCustomerBak>();
		try {
			for (int i = 0; i < id.length; i++) {
				customerBak = (ErpCustomerBak) service.findById(id[i]);
				customerBak.setIsDeleted(1);
				customerBak.setUpdateTime(new Date());
				customerBak.setUpdateUserName(currentUser.getUserName());
				list.add(customerBak);
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
	public ErpCustomerBak getCustomerBak() {
		return customerBak;
	}
	public void setCustomerBak(ErpCustomerBak customerBak) {
		this.customerBak = customerBak;
	}
}
