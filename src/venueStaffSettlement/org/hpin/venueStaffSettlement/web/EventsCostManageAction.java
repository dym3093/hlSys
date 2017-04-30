package org.hpin.venueStaffSettlement.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.web.BaseAction;

/**
 * 场次费用结算Action
 * @author tangxing
 * @date 2016-5-17下午4:45:58
 */
@Namespace("/venueStaffSett")
@Action("eventsCostMan")
@Results({
    @Result(name="listEventsCostMan",location="/WEB-INF/venueStaffSettlement/eventsCostManage.jsp")
})
public class EventsCostManageAction extends BaseAction {

	/**
	 * 显示场次信息列表
	 * @return
	 * @author tangxing
	 * @date 2016-5-17下午5:08:19
	 */
	public String listEventsCostMan(){
		System.out.println("进入列表");
		
		return "listEventsCostMan";
	}
	
}
