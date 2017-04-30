/**
 * @author DengYouming
 * @since 2016-5-17 下午4:51:16
 */
package org.hpin.venueStaffSettlement.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StaticMehtod;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpEventsService;
import org.hpin.venueStaffSettlement.entity.ErpEventsStaffCost;
import org.hpin.venueStaffSettlement.service.ErpEventsStaffCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;


/** 
 * 场次人员费用 Action
 * @author DengYouming
 * @since 2016-5-17 下午4:51:16
 */
@Namespace("/venueStaffSettlement")
@Action("erpEventsStaffCost")
@Results({   
    @Result(name="listErpEventsStaffCost",location="/WEB-INF/venueStaffSettlement/eventsCostManage.jsp"),
    @Result(name="toAddErpEventsStaffCost",location="/WEB-INF/venueStaffSettlement/addErpEventsStaffCost.jsp"),
    @Result(name="listPersonDetail",location="/WEB-INF/venueStaffSettlement/eventsPersonDetail.jsp"),
})  
public class ErpEventsStaffCostAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ErpEventsStaffCostService service;
	
	@Autowired
	private ErpEventsService erpEventsService;
	
	private ErpEvents obj;
	
	private List<ErpEventsStaffCost> entityList;
	
	/**
	 * 列表页面
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-17 下午6:21:19
	 */
	public String listErpEvents(){
		String selectCost = "";			//是否结算的下拉选中项
		String mapString = "";
		int arrSize = 0;
		String[] strs = new String[20];	//查询条件数组
		String filters = "";			//分割需要的条件
		String value = "";				//输入的查询值
		String eid = HttpTool.getParameter("eid", "");
		if(eid!=null&&eid.equals("1")){
			//单击场次管理默认不显示信息，按查询按钮后显示！！！
		}else{
			Map searchMap = super.buildSearch();
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			String userName=currentUser.getUserName();
			String accountName=currentUser.getAccountName();
			if(!userName.equals("管理员")&&!accountName.equals("zhangwei")&&!accountName.equals("xianglujia")&&!accountName.equals("jiying")){
				searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
			}
	        mapString = searchMap.toString();
	        if(mapString.length()>10){
	        	strs = mapString.replaceAll(" ", "").split(",");	//多条件
	            arrSize = strs.length;
	            for(int i=0; i< arrSize; ++i){
	            	if(strs[0].equals("{filter_and_produceCost_LT_D=0")
	            			||strs[0].equals("{filter_and_produceCost_LT_D=1")
	            			||1==arrSize){
	            		filters = strs[i].substring(12, 23);				//分割filter语句
	            	}else{
	            		filters = strs[i].substring(11, 22);				//分割filter语句
	            	}
	            	System.out.println(filters);
	            	if(filters.equals("produceCost")){
	            		searchMap.remove("filter_and_produceCost_LT_D");//清除是否结算的条件
	            		int num= strs[i].indexOf("=",25);				//从指定位置开始查找"="出现的下标
	            		value = strs[i].substring(num+1,num+2);			//=后面的值
	            		if(value.equals("0")){							//如果为0，就是未结算(价格等于0)
	            			searchMap.put("filter_and_produceCost_EQ_D", 0.0);
	            			selectCost = "0";
	            		}else if(value.equals("1")){					//如果为1，就是已结算(价格大于0)
	            			searchMap.put("filter_and_produceCost_GT_D", 0.0);
	            			selectCost = "1";
	            		}
	            		break;
	            	}
	            }
	        }
			searchMap.put("filter_and_isDeleted_EQ_I", 0);
			HttpTool.setAttribute("selectCost",selectCost);
			try {
				page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<ErpEvents> eventsList=erpEventsService.findByPage(page, searchMap);
			List<Map> inputNumsList=erpEventsService.getAllInputNumsList(currentUser.getUserName());
			
			for(ErpEvents erpEvents:eventsList){
				for(Map map:inputNumsList){
					if(erpEvents.getEventsNo().equals(StaticMehtod.nullObject2String(map.get("EVENTS_NO")))){
					   erpEvents.setNowHeadcount(StaticMehtod.nullObject2int(map.get("TOTAL")));
					   erpEvents.setPdfcount(StaticMehtod.nullObject2int(map.get("PDFTOTAL")));
					   erpEvents.setNopdfcount(StaticMehtod.nullObject2int(map.get("NOPDFTOTAL")));
					   break;
				    }
				}
				if(erpEvents.getNowHeadcount()==null){
					erpEvents.setNowHeadcount(0);
				}		
				if(erpEvents.getPdfcount()==null){
					erpEvents.setPdfcount(0);
				}		
				if(erpEvents.getNopdfcount()==null){
					erpEvents.setNopdfcount(0);
				}
				
			}
			page.setResults(eventsList) ;
		}
		return "listErpEventsStaffCost";
	}
	
	/**
	 * 添加页面
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-17 下午6:22:34
	 */
	public String toAddErpEventsStaffCost(){
		//场次ID
		String eventsId = HttpTool.getParameter(ErpEvents.F_ID);
		//场次号
		String eventsNo =  HttpTool.getParameter(ErpEvents.F_EVENTSNO);
		
		ErpEvents obj = null;
		
		Map searchMap = super.buildSearch();
		
		if(StringUtils.isNotEmpty(eventsId)&&StringUtils.isNotEmpty(eventsNo)){
			Map<String,String> params = new HashMap<String, String>();
			params.put(ErpEvents.F_ID, eventsId);
			params.put(ErpEvents.F_EVENTSNO, eventsNo);
			searchMap.put("filter_and_eventsId_LIKE_S", eventsId);
			searchMap.put("filter_and_eventsNo_LIKE_S", eventsNo);
			List<ErpEvents> eventsList = null;
			try {
				eventsList = erpEventsService.listEventsByProps(params);
				if(!CollectionUtils.isEmpty(eventsList)){
					obj = eventsList.get(0);
				}
				page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
//				service.listErpEventsStaffCost(page, searchMap);
				//传空数据，不显示已添加的
				List<ErpEventsStaffCost> costList = new ArrayList<ErpEventsStaffCost>();
				page.setResults(costList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		HttpTool.setAttribute("obj", obj);
		HttpTool.setAttribute("navTabId", super.navTabId);
		return "toAddErpEventsStaffCost";
	}

	/**
	 * 保存场次人员费用
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-18 下午12:07:15
	 */
	public String saveErpEventsStaffCost(){
		boolean flag = false;
		JSONObject json = new JSONObject();
		json.put("result", "error");
		json.put("statusCode", 300);
		json.put("message","保存失败！");
		json.put("navTabId", super.navTabId);

		//场次ID
		String eventsId = HttpTool.getParameter(ErpEvents.F_ID);
		//场次号
		String eventsNo =  HttpTool.getParameter(ErpEvents.F_EVENTSNO);

		if(StringUtils.isNotEmpty(eventsId)&&StringUtils.isNotEmpty(eventsNo)&&!CollectionUtils.isEmpty(entityList)){
			try {
				 //获取当前用户
				User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		    	
				Map<String,String> props = new HashMap<String, String>();
				props.put(ErpEvents.F_ID, eventsId);
				props.put(ErpEvents.F_EVENTSNO, eventsNo);
				List<ErpEvents> eventsList = erpEventsService.listEventsByProps(props);
				ErpEvents events = null;
				//之前的场次费用
				Double prevAmount = null;
				//之前的结算人员数量
				Integer prevNum = null;
				if(!CollectionUtils.isEmpty(eventsList)){
					events =eventsList.get(0);
					if(events!=null){
						prevAmount = events.getProduceCost()==null ? new Double("0"): events.getProduceCost();
						prevNum = events.getSettNumbers()==null ? new Integer(0): events.getSettNumbers();
					
						Map<String,Object> params = new HashMap<String, Object>();
				    	ErpEventsStaffCost entity = null;
		
						for (int i = 0; i < entityList.size(); i++) {
							entity = entityList.get(i);
							params.clear();
							//场次ID
							params.put(ErpEventsStaffCost.F_EVENTSID, eventsId);
							//场次号
							params.put(ErpEventsStaffCost.F_EVENTSNO, eventsNo);
					    	//员工名
							params.put(ErpEventsStaffCost.F_STAFF, entity.getStaff());
							//员工职能
							params.put(ErpEventsStaffCost.F_POSITION, entity.getPosition());
							//验证是否存在
							ErpEventsStaffCost existObj = service.checkExist(params);
							
							entity.setEventsId(eventsId);
							entity.setEventsNo(eventsNo);
							
							if(existObj!=null){
								entity.setId(existObj.getId());
								entity.setCreateTime(existObj.getCreateTime());
								entity.setCreateUser(existObj.getCreateUser());
								entity.setCreateUserId(existObj.getCreateUserId());
								entity.setUpdateUserId(currentUser.getId());
								entity.setUpdateUser(currentUser.getUserName());
								entity.setUpdateTime(new Date());
								//先减去之前的总额, 再加上现在的总额
								prevAmount = prevAmount - Double.parseDouble(""+existObj.getAmount()) + Double.parseDouble(""+entity.getAmount());
							}else{
								entity.setCreateUserId(currentUser.getId());
								entity.setCreateUser(currentUser.getUserName());
								entity.setCreateTime(new Date());
								prevNum ++;
								prevAmount += Double.parseDouble(""+entity.getAmount());
							}
							
						}
						flag = service.saveList(entityList);
						events.setSettNumbers(prevNum);
						events.setProduceCost(prevAmount);
						events.setUpdateUserId(currentUser.getId());
						events.setUpdateUserName(currentUser.getUserName());
						events.setUpdateTime(new Date());
						erpEventsService.update(events);
						if(flag){
							json.put("result", "success");
							json.put("statusCode", 200);
							json.put("message","保存成功！");
							json.put("navTabId", super.navTabId);
							json.put("callbackType","closeCurrent");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 查询当前场次的人员费用列表
	 * @return
	 * @author tangxing
	 * @date 2016-5-19下午5:33:09
	 */
	public String getPersonByEventsId(){
		String eventsId = HttpTool.getParameter("id");
		Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
//        searchMap.put("filter_and_isDeleted_EQ_I", 0);//默认查询条件
        try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			e.printStackTrace();
		}
        List<ErpEventsStaffCost> eventsStaffList=service.findByPage3(page, searchMap,eventsId);
        page.setResults(eventsStaffList);
        
        HttpTool.setAttribute("eventsId", eventsId);
		
		return "listPersonDetail";
	}

	
	public List<ErpEventsStaffCost> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<ErpEventsStaffCost> entityList) {
		this.entityList = entityList;
	}

	public ErpEvents getObj() {
		return obj;
	}

	public void setObj(ErpEvents obj) {
		this.obj = obj;
	}

}
