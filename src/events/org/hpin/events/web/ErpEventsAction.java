package org.hpin.events.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.region.dao.RegionDao;
import org.hpin.base.region.entity.JYRegion;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.DateUtil;
import org.hpin.common.util.DateUtils;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StaticMehtod;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpConference;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.entity.ErpExpress;
import org.hpin.events.entity.JYCombo;
import org.hpin.events.service.ErpConferenceService;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.events.service.ErpExpressService;
import org.hpin.events.service.ErpQRCodeService;
import org.hpin.events.util.GuNoUtil;
import org.hpin.reportdetail.util.BuidReportExcel;
import org.hpin.reportdetail.web.ShieldProcessActon;
import org.hpin.settlementManagement.entity.ErpEventsComboPrice;
import org.hpin.settlementManagement.service.ErpEventsComboPriceService;
import org.hpin.warehouse.mail.MailSenderInfo;
import org.hpin.warehouse.mail.SimpleMailSender;
import org.hpin.webservice.service.YmGeneReportServiceImpl;
import org.hpin.webservice.service.YmGeneReportServiceImplServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.ymjy.combo.entity.Combo;
import org.ymjy.combo.service.ComboService;

@Namespace("/events")
@Action("erpEvents")
@Results({
	@Result(name="toAddEvents",location="/WEB-INF/events/erpEvents/addEvents.jsp"),
	@Result(name="toModifyEvents",location="/WEB-INF/events/erpEvents/modifyEvents.jsp"),
	@Result(name="listEvents",location="/WEB-INF/events/erpEvents/listEvents.jsp"),
	@Result(name="listEventsSalesman",location="/WEB-INF/events/erpEvents/listEventsSalesman.jsp"),
	@Result(name="toEditEventsPrice",location="/WEB-INF/events/erpEvents/editEventsPrice.jsp"),
	@Result(name="companyship",location="/WEB-INF/events/erpEvents/companyship.jsp"),
	@Result(name="addEventsBX",location="/WEB-INF/settlementManagement/addEventsBX.jsp"),
})										
public class ErpEventsAction extends BaseAction {
	ErpEventsService eventsService = (ErpEventsService) SpringTool.getBean(ErpEventsService.class);
	ErpCustomerService customerService = (ErpCustomerService) SpringTool.getBean(ErpCustomerService.class);
	ErpExpressService expressService = (ErpExpressService) SpringTool.getBean(ErpExpressService.class);
	DeptService deptService = (DeptService) SpringTool.getBean(DeptService.class);
	ComboService comboService = (ComboService) SpringTool.getBean(ComboService.class);
	CustomerRelationShipService relService=(CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
	private UserService userService = (UserService) SpringTool.getBean(UserService.class);
	RegionDao regionDao=(RegionDao)SpringTool.getBean(RegionDao.class);
	ErpConferenceService conferenceService = (ErpConferenceService) SpringTool.getBean(ErpConferenceService.class);
	private static final String ADDRESS = "http://gene.healthlink.cn:8088/websGene/geneReport?wsdl";
//	private static final String ADDRESS = "http://localhost:8099/websGene/geneReport?wsdl";
//	private static final String ADDRESS = "http://localhost:8080/websGene/geneReport?wsdl";
	private Logger log = Logger.getLogger(ErpEventsAction.class);
	@Autowired
	private ErpEventsComboPriceService erpEventsComboPriceService;
	ErpQRCodeService erpQRCodeService = (ErpQRCodeService) SpringTool.getBean(ErpQRCodeService.class);
	
	private ErpEvents events;
	private ErpCustomer customer;
	private ErpExpress express;
	private Dept dept;
	
	public String extEvents() throws Exception{
		BuidReportExcel bre=new BuidReportExcel();
		
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String userName=currentUser.getUserName();
		Map searchMap = super.buildSearch();
		if(!userName.equals("管理员")){
			searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
		}
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
//		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		page = new Page(HttpTool.getPageNum(),500);

//		List<ErpEvents> eventsList=eventsService.findByPage(page, searchMap);
		List<ErpEvents> eventsList=eventsService.findByPage4(page, searchMap);
		
		List<Map> inputNumsList=eventsService.getAllInputNumsList(currentUser.getUserName());
		List<List<String>> rowList=new ArrayList<List<String>>();
		for(ErpEvents erpEvents:eventsList){
			List<String> liststr=new ArrayList<String>();
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
//			liststr.add(erpEvents.getCreateTime().toString());
			liststr.add(erpEvents.getBatchNo());
			liststr.add(erpEvents.getBranchCompany());
			liststr.add(erpEvents.getOwnedCompany());
			liststr.add(erpEvents.getComboName());
			liststr.add(erpEvents.getHeadcount().toString());//预计人数
			liststr.add(erpEvents.getNowHeadcount().toString());//现有人数
			liststr.add(erpEvents.getPdfcount().toString());//有报告人数
//			liststr.add(erpEvents.getNopdfcount().toString());//没有报告人数
//			liststr.add(erpEvents);
//			liststr.add(erpEvents);
//			liststr.add(erpEvents);
			rowList.add(liststr);
		}
		bre.writeXls("d:/", "a1.xlsx", rowList);
		return null;
	}
	
	public Page findByPage(Page page , Map searchMap){

//		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
//		String userName=currentUser.getUserName();
//		if(!userName.equals("管理员")){
//			/*
//			 * 除了admin以外的其他用户登录时，判断如果当前登录用户的【数据权限】字段值为部门数据，
//			 * 则根据当前用户所属的部门，查找这个部门下所有成员关联的场次信息。
//			 * 如果当前登录用户的【数据权限】字段值为个人数据，则查询当前用户关联的场次信息
//			 * modified by henry.xu 20161115
//			 */
//			String deptPri = currentUser.getDataPriv();
//			//当该值为空时,不执行;按照原有方式执行;
//			StringBuilder inStr = new StringBuilder("");
//			if(StringUtils.isNotEmpty(deptPri)) {
//				//查询该人员是否是部门管理;如果不是则查询该人员数据; 否则查询该部门管理下面包含的人员;
//				if("priv_01".equals(deptPri)){
//					//查询
//					List<User> lists = userService.getUserListByDeptId(currentUser.getDeptId());
//					//拼接字符串;
//					if(lists != null && lists.size()> 0) {
//						for(User user : lists) {
//							if(StringUtils.isEmpty(inStr.toString())) {
//								inStr.append("'").append(user.getUserName()).append("'");
//							} else {
//								inStr.append(", '").append(user.getUserName()).append("'");
//							}
//						}
//					}
//					searchMap.put("filter_and_ymSalesman_IN_S", inStr.toString());				
//				} else {
//					
//					searchMap.put("filter_and_ymSalesman_EQ_S", currentUser.getUserName());				
//				}
//				
//			} else {
//				searchMap.put("filter_and_ymSalesman_EQ_S", currentUser.getUserName());				
//			}
//		}
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		//移除 UniversalExportExcelAction导出报告时默认添加的 reportState  add by YoumingDeng 2016-09-26
		searchMap.remove("reportState");
		
		List<ErpEvents> eventsList=eventsService.findByPage(page, searchMap);
		
		/*
		 * 代码修改; modified by henry.xu 20161117
		 * 原有代码性能很低所以做了修改;
		 */
		for(ErpEvents erpEvents:eventsList){
			Map<String, Object> numsMap = eventsService.getNumsMapByEventsNo(erpEvents.getEventsNo());
			if(numsMap != null) {
				erpEvents.setNowHeadcount(StaticMehtod.nullObject2int(numsMap.get("TOTAL")));
				erpEvents.setPdfcount(StaticMehtod.nullObject2int(numsMap.get("PDFTOTAL")));
				erpEvents.setNopdfcount(StaticMehtod.nullObject2int(numsMap.get("NOPDFTOTAL")));
				
			}
		}
		page.setResults(eventsList) ;
		page.setTempProcess(ShieldProcessActon.REPORTEVENTSSALESMAN);
		
		return page ;
	}
	
	/**
	 * create by henry.xu copy findByPage方法;
	 * 原有导出使用
	 * @param page
	 * @param searchMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findByPageExcel(Page page , Map searchMap){

		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String userName=currentUser.getUserName();
		if(!userName.equals("管理员")){
			/*
			 * 除了admin以外的其他用户登录时，判断如果当前登录用户的【数据权限】字段值为部门数据，
			 * 则根据当前用户所属的部门，查找这个部门下所有成员关联的场次信息。
			 * 如果当前登录用户的【数据权限】字段值为个人数据，则查询当前用户关联的场次信息
			 * modified by henry.xu 20161115
			 */
			String deptPri = currentUser.getDataPriv();
			//当该值为空时,不执行;按照原有方式执行;
			StringBuilder inStr = new StringBuilder("");
			if(StringUtils.isNotEmpty(deptPri)) {
				//查询该人员是否是部门管理;如果不是则查询该人员数据; 否则查询该部门管理下面包含的人员;
				if("priv_01".equals(deptPri)){
					//查询
					List<User> lists = userService.getUserListByDeptId(currentUser.getDeptId());
					//拼接字符串;
					if(lists != null && lists.size()> 0) {
						for(User user : lists) {
							if(StringUtils.isEmpty(inStr.toString())) {
								inStr.append("'").append(user.getUserName()).append("'");
							} else {
								inStr.append(", '").append(user.getUserName()).append("'");
							}
						}
					}
					searchMap.put("filter_and_ymSalesman_IN_S", inStr.toString());				
				} else {
					
					searchMap.put("filter_and_ymSalesman_EQ_S", currentUser.getUserName());				
				}
				
			} else {
				searchMap.put("filter_and_ymSalesman_EQ_S", currentUser.getUserName());				
			}
		}
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		
		List<ErpEvents> eventsList=eventsService.findByPage(page, searchMap);
		/*
		 * 代码修改; modified by henry.xu 20161117
		 * 原有代码性能很低所以做了修改;
		 */
		for(ErpEvents erpEvents:eventsList){
			Map<String, Object> numsMap = eventsService.getNumsMapByEventsNo(erpEvents.getEventsNo());
			if(numsMap != null) {
				erpEvents.setNowHeadcount(StaticMehtod.nullObject2int(numsMap.get("TOTAL")));
				erpEvents.setPdfcount(StaticMehtod.nullObject2int(numsMap.get("PDFTOTAL")));
				erpEvents.setNopdfcount(StaticMehtod.nullObject2int(numsMap.get("NOPDFTOTAL")));
				
			}
		}
		page.setResults(eventsList) ;
		page.setTempProcess(ShieldProcessActon.EVENTSLISTEVENTS);
		
		return page ;
	}
	
	/**
	 * 销售人员场次信息查询
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String listEventsSalesman() throws Exception{
		
		//查询类型
		String queryType = HttpTool.getParameter("queryType");
		
		Map searchMap = super.buildSearch();
		
		if(StringUtils.isNotEmpty(queryType)&&"2".equals(queryType)){
			//获取批次号的值
			String batchNo = (String)searchMap.get("filter_and_batchno_EQ_S"); 
			//queryType为2，则为模糊查询
			if(StringUtils.isNotEmpty(batchNo)){
				searchMap.remove("filter_and_batchno_EQ_S");
				searchMap.put("filter_and_batchno_LIKE_S", batchNo);
			}
		}
		
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String aaaa = HttpTool.getParameter("aaaa", "");
		String userName=currentUser.getUserName();
		//String accountName=currentUser.getAccountName();
		
		/*
		 * 根据业务需求此处销售统计报表,只有admin有全部查看权限,其他销售智能看自己的,或者销售主管看相关人员数据
		 * modified by henry.xu 20161111
		 * */
		if(!userName.equals("管理员")){//销售人员
			/*
			 * 除了admin以外的其他用户登录时，判断如果当前登录用户的【数据权限】字段值为部门数据，
			 * 则根据当前用户所属的部门，查找这个部门下所有成员关联的场次信息。
			 * 如果当前登录用户的【数据权限】字段值为个人数据，则查询当前用户关联的场次信息
			 * modified by henry.xu 20161115
			 */
			String deptPri = currentUser.getDataPriv();
			//当该值为空时,不执行;按照原有方式执行;
			StringBuilder inStr = new StringBuilder("");
			if(StringUtils.isNotEmpty(deptPri)) {
				//查询该人员是否是部门管理;如果不是则查询该人员数据; 否则查询该部门管理下面包含的人员;
				if("priv_01".equals(deptPri)){
					//查询
					List<User> lists = userService.getUserListByDeptId(currentUser.getDeptId());
					//拼接字符串;
					if(lists != null && lists.size()> 0) {
						for(User user : lists) {
							if(StringUtils.isEmpty(inStr.toString())) {
								inStr.append("'").append(user.getUserName()).append("'");
							} else {
								inStr.append(", '").append(user.getUserName()).append("'");
							}
						}
					}
					searchMap.put("filter_and_ymSalesman_IN_S", inStr.toString());				
				} else {
					
					searchMap.put("filter_and_ymSalesman_EQ_S", currentUser.getUserName());				
				}
				
			} else {
				searchMap.put("filter_and_ymSalesman_EQ_S", currentUser.getUserName());				
			}
			
		}
		
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		
		List<ErpEvents> eventsList=eventsService.findByPage(page, searchMap);
		
		/*
		 * 代码修改; modified by henry.xu 20161117
		 * 原有代码性能很低所以做了修改;
		 */
		for(ErpEvents erpEvents:eventsList){
			Map<String, Object> numsMap = eventsService.getNumsMapByEventsNo(erpEvents.getEventsNo());
			if(numsMap != null) {
				erpEvents.setNowHeadcount(StaticMehtod.nullObject2int(numsMap.get("TOTAL")));
				erpEvents.setPdfcount(StaticMehtod.nullObject2int(numsMap.get("PDFTOTAL")));
				erpEvents.setNopdfcount(StaticMehtod.nullObject2int(numsMap.get("NOPDFTOTAL")));
				
			} else {
				erpEvents.setNowHeadcount(0);
				erpEvents.setPdfcount(0);
				erpEvents.setNopdfcount(0);
			}
		}
		
		HttpTool.setAttribute("aaaa", aaaa);
		HttpTool.setAttribute("queryType", queryType);
		
		page.setResults(eventsList) ;
		return "listEventsSalesman";
	}
	/**
	 * 场次信息查询
	 */
	public String listEvents() throws Exception{
		String eid = HttpTool.getParameter("eid", "");			
		String hiddenRadio = HttpTool.getParameter("hiddenRadio");
		String hiddenText = HttpTool.getParameter("hiddenText");
		if(eid!=null&&eid.equals("1")){
			//单击场次管理默认不显示信息，按查询按钮后显示！！！
		}else{
			Map searchMap = super.buildSearch();
			String aaaa = HttpTool.getParameter("aaaa", "");
			String bbbb = HttpTool.getParameter("bbbb", "");
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			String userName=currentUser.getUserName();
			String accountName=currentUser.getAccountName();
			
			/*if(!userName.equals("管理员")&&!userName.equals("基因解读")&&!userName.equals("韩晓菊")
					&&!userName.equals("张红")&&!userName.equals("曾宇")&&!userName.equals("吴亚娟")&&!userName.equals("张巍")){
				searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
			}*/
			searchMap.put("filter_and_isDeleted_IN_S", "0,2");
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<ErpEvents> eventsList=eventsService.findByPage(page, searchMap);
			/**/List<Map> inputNumsList=eventsService.getAllInputNumsList(currentUser.getUserName());
			for(ErpEvents erpEvents:eventsList){
//				erpEvents.setNowHeadcount(eventsService.getCustomerNums(erpEvents.getEventsNo()));//现有人数
//				erpEvents.setPdfcount(eventsService.getCustomerPDFNums(erpEvents.getEventsNo()));//已出PDF报告数
//				erpEvents.setNopdfcount(eventsService.getCustomerNOPDFNums(erpEvents.getEventsNo()));
				/**/
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
			HttpTool.setAttribute("hiddenRadio", hiddenRadio);
			HttpTool.setAttribute("hiddenText", hiddenText);
			HttpTool.setAttribute("aaaa", aaaa);
			HttpTool.setAttribute("bbbb", bbbb);	
			page.setResults(eventsList) ;
		}
		
		return "listEvents";
//		return jump("erpEvents!listEvents.action");
	}
	
	/**
	 * 保险公司结算任务，添加场次信息页面（addEventsBX.jsp）的查询功能
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-9 上午10:46:28
	 */
	public String listEventsToAddEventsBXPage(){
		String settleId = HttpTool.getParameter("settleId");
		
		String eid = HttpTool.getParameter("eid", "");
		if(eid!=null&&eid.equals("1")){
			//单击场次管理默认不显示信息，按查询按钮后显示！！！
		}else{
			Map searchMap = super.buildSearch();
			String aaaa = HttpTool.getParameter("aaaa", "");
			String bbbb = HttpTool.getParameter("bbbb", "");
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			String userName=currentUser.getUserName();
			if(!userName.equals("管理员")){
				searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
			}
			searchMap.put("filter_and_ymSalesman_EQ_S", currentUser.getUserName());
			searchMap.put("filter_and_isDeleted_EQ_I", 0);
			try {
				page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			} catch (ParseException e) {
				e.printStackTrace();
				log.error("listEventsToAddEventsBXPage:",e);
			}
			List<ErpEvents> eventsList=eventsService.findByPage(page, searchMap);
			/**/List<Map> inputNumsList=eventsService.getAllInputNumsList(currentUser.getUserName());
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
			HttpTool.setAttribute("aaaa", aaaa);
			HttpTool.setAttribute("bbbb", bbbb);
			page.setResults(eventsList) ;
		}
		//再次回传settleID和navTabId
		HttpTool.setAttribute("settleId", settleId);
		HttpTool.setAttribute("navTabId", super.navTabId);
		return "addEventsBX";
	}
	
    /**
     * 根据客户条形码查询场次信息
     */
    public String findEventsByCode() throws Exception{
        Map searchMap = super.buildSearch();
        String allStr = searchMap.toString(); //获取当前提交的sql条件
        String[] allStrs = allStr.split("_"); //分割sql条件
        int allSize = allStrs.length;
        for(int i=0;i<allSize;++i){
            if(allSize>=4){
                if(allStrs[2].endsWith("code")){//以条形码查询
                    String[] codes=allStrs[4].split("=");
                    String[] codes2=codes[1].split("}");
//                  if(codes[1]!=null||!(codes[1].equals(""))){
//                      List<ErpCustomer> customers = customerService.getCustomerByCode(codes[1]);
//                      for(int j=0;j<customers.size();++j){
//                          System.out.println("customers="+customers.get(j));
//                      }
////                      searchMap.put("filter_and_eventNo_EQ_S", customers.get(0).getEventsNo());
//                      searchMap.clear();
//                      searchMap.put("filter_and_eventsNo_LIKE_S", "asd");
//                  }else{
//                      ;
//                  }
                  List<ErpCustomer> customers = customerService.getCustomerByCode(codes2[0]);
                  if(customers.size()>0){
                      searchMap.clear();
                      searchMap.put("filter_and_eventsNo_LIKE_S", customers.get(0).getEventsNo());//查找到条形码对应的客户，就查场次
                  }else{
                      searchMap.clear();
                      searchMap.put("filter_and_eventsNo_LIKE_S", "fill"); //未查找到数据，就无结果
                  }
                }
            }
        }
        
        String aaaa = HttpTool.getParameter("aaaa", "");
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        String userName=currentUser.getUserName();
        if(!userName.equals("管理员")){
            searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
        }
        searchMap.put("filter_and_isDeleted_EQ_I", 0);
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
        List<ErpEvents> eventsList=eventsService.findByPage(page, searchMap);
        List<Map> inputNumsList=eventsService.getAllInputNumsList(currentUser.getUserName());
        for(ErpEvents erpEvents:eventsList){
//          erpEvents.setNowHeadcount(eventsService.getCustomerNums(erpEvents.getEventsNo()));
//          System.out.println(erpEvents.getNowHeadcount()+"erpEvents.getNowHeadcount()");
            for(Map map:inputNumsList){
                if(erpEvents.getEventsNo().equals(StaticMehtod.nullObject2String(map.get("EVENTS_NO")))){
                   erpEvents.setNowHeadcount(StaticMehtod.nullObject2int(map.get("TOTAL")));
                   erpEvents.setPdfcount(StaticMehtod.nullObject2int(map.get("PDFTOTAL")));
                   break;
                }
            }
            if(erpEvents.getNowHeadcount()==null){
                erpEvents.setNowHeadcount(0);
            }       
            if(erpEvents.getPdfcount()==null){
                erpEvents.setPdfcount(0);
            }       
        }
        HttpTool.setAttribute("aaaa", aaaa);
        page.setResults(eventsList) ;
        return "listEvents";
//      return jump("erpEvents!listEvents.action");
    }
	
	
	/**
	 * 
	 * @return
	 */
	public String toAddEvents(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String brach_company_id = currentUser.getJobNumber();
		CustomerRelationShip ship;
		if(brach_company_id==null){
			ship=new CustomerRelationShip();
		}else{
			ship = (CustomerRelationShip) relService.findById(brach_company_id);
			if(ship==null){
				ship=new CustomerRelationShip();
			}
		}
		
		String combosStr=ship.getOrgNature();
		/*List<Combo> combos=comboService.findByIds(combosStr);*/
//		String provice = ship.getProvince();
//		String city = ship.getCity();
		String salesman=ship.getSalesman();//远盟营销员
		String company = currentUser.getExtension();
		String companyId = "";
		if(StrUtils.isNotNullOrBlank(currentUser.getJobNumber())){
			companyId = currentUser.getJobNumber();//支公司id
		}
		Date now1 = new Date();
		SimpleDateFormat sd= new SimpleDateFormat(DateUtils.DATE_FORMAT);
		String now = sd.format(now1);
		String ownedCompany = currentUser.getDeptName();
		String ownedcompanyId ="";
		if(StrUtils.isNotNullOrBlank(currentUser.getDeptId())){
			ownedcompanyId=currentUser.getDeptId();
		}
		if(currentUser.getDept()!=null){
			Dept dept = (Dept) deptService.findById( currentUser.getDept().getParentId());
			HttpTool.setAttribute("dept", dept);
		}
//		HttpTool.setAttribute("provice", provice);
//		HttpTool.setAttribute("city", city);
		HttpTool.setAttribute("company", company);
		HttpTool.setAttribute("companyId", companyId);
		HttpTool.setAttribute("ownedCompany", ownedCompany);
		HttpTool.setAttribute("ownedcompanyId", ownedcompanyId);
		HttpTool.setAttribute("salesman", salesman);
		HttpTool.setAttribute("now", now);
		HttpTool.setAttribute("navTabId", super.getNavTabId());
		/*HttpTool.setAttribute("combos", combos);*/
		return "toAddEvents";
	}
	
	/**
	 * 场次信息增加
	 * @return
	 */
	public String addEvents(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String level = eventsService.getLevelName(HttpTool.getParameter("events.level2"));
		JSONObject json = new JSONObject();
		if (level.contains("癌筛")) {
//			String combo = HttpTool.getParameter("events.comboName");
//			String comboCodes = eventsService.getComboCode(combo);
			List<JYRegion> province = eventsService.getCityName(HttpTool.getParameter("filter_and_province_EQ_S"));
			List<JYRegion>  city = eventsService.getCityName(HttpTool.getParameter("filter_and_city_EQ_S"));
			List<JYRegion>  area = eventsService.getCityName(HttpTool.getParameter("filter_and_area_EQ_S"));
			String brach_company = events.getBranchCompany();
			List<CustomerRelationShip> ships =  relService.findByProperty(CustomerRelationShip.class, "branchCommany", brach_company, null, null);
	
			String deptName = events.getOwnedCompany();
			Dept currDept = eventsService.findDeptByName(deptName);
			String deptShortName = currDept.getDeptNameShort();
			
			try {

				org.json.JSONObject dataObject = new org.json.JSONObject();
				//重新修改的规则 add by DengYouming 2016-08-15
				String nameStr = events.getBatchNo()+"远盟"+ city.get(0).getName()+ deptShortName +"("+ships.get(0).getBranchCommany()+")";
				dataObject.put("name", nameStr);
				String bookTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(events.getEventDate());
				bookTimeStr = bookTimeStr.subSequence(0, 10)+" "+events.getHour()+":00:00 +0800";
				//改为场次日期
				dataObject.put("bookTime", bookTimeStr);
				
				//套餐
//				org.json.JSONObject comboObject = new org.json.JSONObject();
//				comboObject.put("name", combo);
//				comboObject.put("itemCodes", comboCodes);
//				org.json.JSONArray combosList = new org.json.JSONArray();
//				combosList.put(comboObject);
				//放入套餐JSONString
				//dataObject.put("serviceItems", combosList.toString());
				//如果实现不能确定团单套餐，传空字符串
				dataObject.put("serviceItems", "");
				//联系人
				org.json.JSONObject contactMap = new org.json.JSONObject();
				contactMap.put("name", currentUser.getUserName());
				contactMap.put("phone", currentUser.getMobile()==null?"18580154329":currentUser.getMobile());
				//放入联系人JSONString
				dataObject.put("contact", contactMap.toString());
				
				//地址相关
				org.json.JSONObject addressMap = new org.json.JSONObject();
				addressMap.put("provinceId", Integer.parseInt(province.get(0).getCityId()));
				addressMap.put("provinceName", province.get(0).getName());
				addressMap.put("cityId", Integer.parseInt(city.get(0).getCityId()));
				addressMap.put("cityName", city.get(0).getName());
				addressMap.put("districtId", Integer.parseInt(area.get(0).getCityId()));
				addressMap.put("districtName", area.get(0).getName());
				addressMap.put("address", events.getAddress());
				//放入地址字符串
				dataObject.put("address", addressMap.toString());
				//场次号
				dataObject.put("corServiceId", events.getEventsNo());
				
				String jsonObject=createGroupOrder(dataObject.toString(), "addEvents");
				log.info("创建团单号后返回的字符串："+jsonObject+"------");
				int code = JSONObject.fromObject(jsonObject).getInt("code");
				String msg = JSONObject.fromObject(jsonObject).getString("msg");
				log.info("创建团单号后返回的code："+code+"------");
				log.info("创建团单号后返回的message："+msg+"------");
				String groupOrderNo = "";
				int settNum=0;
				if (JSONObject.fromObject(jsonObject).size()>=3) {
					groupOrderNo = JSONObject.fromObject(JSONObject.fromObject(JSONObject.fromObject(jsonObject).getString("data")).getString("groupService")).getString("serviceId");
					settNum = JSONObject.fromObject(JSONObject.fromObject(JSONObject.fromObject(jsonObject).getString("data")).getString("groupService")).getInt("testeeNum");
				}
				log.info("创建团单号后返回的团单号："+groupOrderNo+"------");
				if(StringUtils.isNotEmpty(groupOrderNo)){//@since 2016年10月11日10:14:06，判断团单号是否是空，@add by chenqi
					insertEvents(currentUser,level,code,msg,groupOrderNo,settNum);//新增场次信息
				}else{
					json.accumulate("statusCode", 300);
					json.accumulate("message", msg);//modify by Damian 2017-04-01
//					json.accumulate("message", "增加失败:没有该团单号");
					renderJson(json);
				}
			}  catch (Exception e) {
				log.error("addEvents:",e);
			}
		}else {
			insertEvents(currentUser,"",200,"ok","",0);
		}
		return null;
	}
	
	/**
	 * @param currentUser	当前用户
	 * @param level 级别名称
	 * @param code 返回码
	 * @param msg	返回的信息
	 * @param groupOrderNo	返回的团单号
	 * @param settNum	返回的检测人数
	 */
	public void insertEvents(User currentUser,String level, int code,String msg,String groupOrderNo,int settNum){
		try {
		JSONObject json = new JSONObject();
		if (code == 200) {
			//String brach_company_id=currentUser.getJobNumber();
			CustomerRelationShip ship = (CustomerRelationShip) relService.findById(events.getBranchCompanyId());
			String provice = ship.getProvince();
			String city = ship.getCity();
			String company = ship.getBranchCommany();
			String ownedCompany = ship.getCustomerNameSimple();
			String companyId = ship.getId();
			String ownedCompanyId = ship.getOwnedCompany();
			try {
//				String eventsNo = events.getEventsNo();
				String hour = events.getHour();
				Date eventDate =events.getEventDate();
				String eventDateStr=DateUtil.getDateTime("MM/dd/yyyy", eventDate);
				String time= eventDateStr+" "+hour+":00:00";
				Date finalTime = DateUtil.convertStringToDate("MM/dd/yyyy HH:mm:ss", time);
				String message = eventsService.isNotRepeatNoByDateAndBannyCompany(company,ownedCompany,finalTime,events.getAddress()); //判断场次是否已添加
				if(StrUtils.isNullOrBlank(message)){
					events.setEventDate(finalTime);
					String comboName = events.getComboName();
					if (comboName.contains(",")) {
						comboName=comboName.replace(",", "");
					}
					comboName = comboName.trim();
					if(level.contains("癌筛")){//金域
						//TODO 暂停使用套餐  add by DengYouming 2016-08-15
					/*	JYCombo jyCombo = eventsService.getJyCombo(comboName);
						events.setComboId(jyCombo.getId());
						events.setComboName(jyCombo.getCombo());*/
						events.setGroupOrderNo(groupOrderNo);
						events.setSettNumbers(settNum);
					}else {
						Combo combo = comboService.findByComboName(comboName);
						if(combo!=null){
							events.setComboId(combo.getId());
							events.setComboName(combo.getComboName());
						}
					}
					events.setBranchCompany(company);
					events.setBranchCompanyId(companyId);
					events.setOwnedCompanyId(ownedCompanyId);
					events.setProvice(provice);
					events.setCity(city);
					events.setOwnedCompany(ownedCompany);
					events.setIsDeleted(0);
					events.setIsExpress(0);
					events.setCreateUserName(currentUser.getUserName());
					events.setCreateTime(new Date());
					events.setStatusBX(0);//默认保险结算状态为0 add by DengYouming 2016-08-04
					
					ErpConference conference = new ErpConference();	//增加场次的同时增加到会议表中
					conference.setConferenceNo(events.getEventsNo());
					conference.setConferenceDate(finalTime);
					conference.setProvice(provice);
					conference.setCity(city);
					conference.setBranchCompany(company);
					conference.setBranchCompanyId(companyId);
					conference.setOwnedCompany(ownedCompany);
					conference.setOwnedCompanyId(ownedCompanyId);
					conference.setAddress(events.getAddress());
					conference.setSettNumbers(0);
					conference.setProduceCost(Double.parseDouble("0"));
					conference.setConferenceType("1010902");
					conference.setProBelong(ship.getProBelong());
					conference.setProCode(ship.getProCode());
					conference.setProUser(ship.getProUser());
					conference.setIsDeleted(0);
					conference.setIsExpress(0);
					conference.setHeadcount(0);
					conference.setCreateTime(new Date());
					conference.setCreateUserName(currentUser.getAccountName());
					conference.setCustomerRelationShipProId(events.getCustomerRelationShipProId()); //modified by henry.xu 项目信息Id
					eventsService.save(events);
					conferenceService.save(conference);
					erpQRCodeService.saveErpQRCode(events);
					
					log.info("Events insertEvents eventNo :" + events.getEventsNo() +"\t create user : " + currentUser.getUserName());
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
				log.error("insertEvents:", e);
			}
		}else {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "增加失败:"+msg);
		}
		renderJson(json);
		} catch (Exception e) {
			log.error("ErpEventsAciton.insertEvents", e);
		}
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delEvents(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String[] id = ids.replaceAll(" ", "").split(",");
		List<ErpEvents> list = new LinkedList<ErpEvents>();
		try {
			Date now = new Date();
			for (int i = 0; i < id.length; i++) {
				events = (ErpEvents) eventsService.findById(id[i]);
				events.setIsDeleted(1);
				events.setUpdateTime(now);
				events.setUpdateUserName(currentUser.getAccountName());
				list.add(events);
				//级联操作会议管理
				ErpConference conference = (ErpConference) conferenceService.findByPropertyAndIsDelete(ErpConference.class, "conferenceNo", events.getEventsNo(), 0, null, null).get(0);
				if(conference!=null){
					conference.setIsDeleted(1);
	                conference.setUpdateTime(now);
	                conference.setUpdateUserName(currentUser.getAccountName());
					conferenceService.modify(conference);
				}
			}
				eventsService.delete(list);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "删除成功");
//				json.accumulate("navTabId", super.navTabId);
//				json.accumulate("callbackType", "closeCurrent");
			} catch (Exception e) {
				json.accumulate("statusCode", 300);
				json.accumulate("message", "删除失败");
				e.printStackTrace();
				log.error("delEvents:", e);
			}
		renderJson(json);
		return null;
	}
	/**
	 * 
	 */
	public String toModifyEvents(){
		events=eventsService.get(id);
		String eventDateStr=DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", events.getEventDate());
		HttpTool.setAttribute("eventDateStr", eventDateStr);
		
		CustomerRelationShipPro shipPro = null;
		if(StringUtils.isNotEmpty(events.getCustomerRelationShipProId())) {
			shipPro = relService.findCustRelShipProById(events.getCustomerRelationShipProId());
		}
		HttpTool.setAttribute("shipPro", shipPro);			
		return "toModifyEvents";
	}
	public String modifyEvents(){
		JSONObject json = new JSONObject();
		String isUpdateCustDate=HttpTool.getParameter("isUpdateCustDate");
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		try {
			Date now = new Date();
				String eventsNo=events.getEventsNo();
				//int nowHeadcount=eventsService.getNowHeadcountByEventsNo(eventsNo);
//				HttpTool.setAttribute("nowHeadcount", nowHeadcount);
//				events.setNowHeadcount(nowHeadcount);
				
				String comboName = events.getComboName();
				if(null!=comboName && !comboName.equals("")){
					Combo combo = comboService.findByComboName(comboName);
					events.setComboId(combo.getId());
					events.setComboName(combo.getComboName());
				}
//				String id = events.getId();
				events.setUpdateTime(now);
				events.setUpdateUserName(currentUser.getUserName());
				eventsService.modify(events);//判断场次是否已添加
				//根据场次号更新客户信息
				if(isUpdateCustDate.equals("1")){
					customerService.updateSampleDatebyEventsNo(events);
				}
				//根据场次号更新会议信息
				ErpConference conference = (ErpConference) conferenceService.findByPropertyAndIsDelete(ErpConference.class, "conferenceNo", events.getEventsNo(), 0, null, null).get(0);
				if(conference!=null){
					//会议日期
					conference.setConferenceDate(events.getEventDate());
					//会议地址
					conference.setAddress(events.getAddress());
					//支公司id
					conference.setBranchCompanyId(events.getBranchCompanyId());
					//总公司id
					conference.setOwnedCompanyId(events.getOwnedCompanyId());
					//项目id
					conference.setCustomerRelationShipProId(events.getCustomerRelationShipProId());
	                conference.setUpdateTime(now);
	                conference.setUpdateUserName(currentUser.getAccountName());
					conferenceService.modify(conference);
				}
				json.accumulate("statusCode", 200);
				json.accumulate("message", "修改成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "closeCurrent");
			} catch (Exception e) {
				json.accumulate("statusCode", 300);
				json.accumulate("message", "修改失败");
				e.printStackTrace();
				log.error("modifyEvents:", e);
			}
		renderJson(json);
		return null;
	}
	public String modifyEventsByExpress(){
		String isBtn=HttpTool.getParameter("isBtn");//2结束采集
		HttpTool.getSession().setAttribute("id", id);
		ErpEvents oldevents=eventsService.get(id);
		JSONObject json = new JSONObject();
		if(isBtn.equals("1")){//发快递
			try {
				events.setUpdateTime(new Date());
				oldevents.setEdate(events.getEdate());
				oldevents.setEname(events.getEname());
				oldevents.setEtrackingNumber(events.getEtrackingNumber());
				oldevents.setIsExpress(1);
				eventsService.modify(oldevents);//判断场次是否已添加
				json.accumulate("statusCode", 200);
				json.accumulate("message", "修改成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "refreshCurrent");
			} catch (Exception e) {
				json.accumulate("statusCode", 300);
				json.accumulate("message", "修改失败");
				e.printStackTrace();
				log.error("modifyEventsByExpress:", e);
			}
		}else{//结束采集发邮件
			String message="操作成功";
			try {
				/***************************发送邮件*******************************************/
				User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
				MailSenderInfo mailInfo = new MailSenderInfo();
				mailInfo.setMailServerHost("smtp.exmail.qq.com");
				mailInfo.setMailServerPort("25");
				mailInfo.setValidate(true);
				String currentMail=currentUser.getEmail();// 发件人邮箱
				String currentMailPwd=currentUser.getCtiPassword();// 邮箱密码
				mailInfo.setUserName(currentMail);	
				mailInfo.setPassword(currentMailPwd);
				mailInfo.setFromAddress(currentMail);
				//发件人:
				String username=currentUser.getUserName();
				// 邮件内容
				StringBuffer buffer = new StringBuffer();
				
				//收件人
				String toEmail;
				String subject;//标题
				String mes;
				//获取操作员邮箱
				String createUserName="远盟管理";
				User u=userService.getUserByCreateName(createUserName);
				toEmail=u.getEmail();
				subject="基因项目客户信息采集结束邮件";// 邮件标题
				buffer.append("场次号："+oldevents.getEventsNo()+"\n");
				buffer.append("场次日期："+oldevents.getEventDate()+"\n");
				buffer.append("支公司："+oldevents.getBranchCompany()+"\n");
				buffer.append("总公司："+oldevents.getOwnedCompany()+"\n");
				buffer.append("入门礼检测餐套：："+oldevents.getComboName()+"\n");
				buffer.append("预订人数："+oldevents.getHeadcount()+"\n");
				buffer.append("录入人数："+eventsService.getCustomerNums(oldevents.getEventsNo())+"\n");
				mes="信息采集已结束，请查收";
				buffer.append("发件人："+username+"\n");
				buffer.append("链接网址：http://gene.healthlink.cn\n");
				buffer.append(mes);
				mailInfo.setContent(buffer.toString());
				//判断邮件情况
				if(toEmail==null||currentMail==null){
					message=",发送邮箱为空";
				}
				
				// 邮件标题
				mailInfo.setSubject(subject);
				// 收件人邮箱
				mailInfo.setToAddress(toEmail);
				// 发送邮件
				SimpleMailSender sms = new SimpleMailSender();
				// 发送文体格式
				sms.sendTextMail(mailInfo);
				// 发送html格式
				SimpleMailSender.sendHtmlMail(mailInfo);
				System.out.println("邮件发送完毕");
				/********************邮件发送结束**************************************************/
				
				//CRUD操作
				
				json.accumulate("statusCode", 200);
				json.accumulate("message", "操作成功");
				json.accumulate("navTabId", super.navTabId);
//				json.accumulate("callbackType", "closeCurrent");
				json.accumulate("callbackType", "refreshCurrent");
			} catch (Exception e) {
				json.accumulate("statusCode", 300);
				json.accumulate("message", "操作失败"+message);
				e.printStackTrace();
				log.error("modifyEventsByExpress", e);
			}
		}
		
		renderJson(json);
		return null;
	}
	
	/**
	 * 显示修改场次价格的窗口
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-1 下午12:36:52
	 */
	public String toEditEventsPrice(){
		//场次ID
		String eventsId = HttpTool.getParameter("eventsId");
		//场次号
		String eventsNo = HttpTool.getParameter("eventsNo");
		//根据场次号查找场次中所有套餐名称
		Map<String,Object> params = new HashMap<String, Object>();
		try {
			params.put(ErpEventsComboPrice.F_EVENTSNO, eventsNo);
			page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
			//根据场次号查找场次套餐价格
			List<ErpEventsComboPrice> list = eventsService.findEventsComboPrice(params);
			page.setResults(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("toEditEventsPrice:", e);
		}
		HttpTool.setAttribute("eventsId", eventsId);
		HttpTool.setAttribute("eventsNo", eventsNo);
		HttpTool.setAttribute("navTabId", super.navTabId);
		return "toEditEventsPrice";
	}
	
	/**
	 * 保存场次套餐价格
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-1 下午1:08:54
	 */
	public String saveEvntsComboPrice(){
		String data = HttpTool.getParameter("data");
		//操作
		String opera = HttpTool.getParameter("opera");
		
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<ErpEventsComboPrice> list = JSONArray.toList(jsonArray, ErpEventsComboPrice.class);
		
		JSONObject json = new JSONObject();
		json.put("result","failure");
		json.put("statusCode", 300);
		json.put("message", "保存失败！");
		json.put("navTabId", super.navTabId);
		
		if(list!=null&&list.size()>0){
			try{
				if(erpEventsComboPriceService.saveList(list)){
					json.put("result","success");
					json.put("statusCode", 200);
					json.put("message", "保存成功");
					json.put("navTabId", super.navTabId);
					json.put("callbackType", "closeCurrent");
				}
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				log.error("saveEvntsComboPrice:", e);
			}
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 生成场次号
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public String createNo() throws ParseException, IOException{
		Date date = HttpTool.getDateParameter("data");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		if(StrUtils.isNotNullOrBlank(date)){
			String eventDate = sf.format(date);
			String eventsNo = eventsService.maxNo(eventDate);
			String data = GuNoUtil.getNo(eventsNo,date);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(data);
			log.info("Events createNo : " + data);
		}
		return null;
	}
	
	
	/**
	 * @return 金域套餐
	 */
	public String getJYCombo() {
		StringBuffer json = new StringBuffer("[") ;
		List<JYCombo> combo = eventsService.getJYCombo();
		
		if(combo.size() > 0){
			for(JYCombo jy:combo){
				json.append("{\"text\":\"" +jy.getCombo() + "\",\"id\":\"" + jy.getCombo() + "\",\"leaf\":" + false) ;
				json.append("},") ;
			}
		}
		if (json.toString().endsWith(",")) {
			json = json.delete(json.length() - 1, json.length());
		}

		json.append("]");
		this.jsonString = json.toString();
		return "json";
	}
	
	public String getCancerCombo() {
		StringBuffer json = new StringBuffer("[") ;
		List<Combo> comboList = eventsService.getCancerCombo();
		
		if(comboList.size() != 0){
			for(Combo combo:comboList){
				json.append("{\"text\":\"" +combo.getComboName() + "\",\"id\":\"" + combo.getComboName() + "\",\"leaf\":" + false) ;
				json.append("},") ;
			}
		}
		if (json.toString().endsWith(",")) {
			json = json.delete(json.length() - 1, json.length());
		}
		
		json.append("]");
		this.jsonString = json.toString();
		return "json";
	}
	
	/**
	 * @param jsonData
	 * @param method 用于记录某个调用方法的错误日志
	 */
	public String createGroupOrder(String jsonData,String method){
		String info = null;
		try {
			YmGeneReportServiceImplServiceLocator push = new YmGeneReportServiceImplServiceLocator();
			push.setYmGeneReportServiceImplPortEndpointAddress(ADDRESS);
			YmGeneReportServiceImpl serviceImpl = push.getYmGeneReportServiceImplPort();
			info = serviceImpl.createGroupOrder(jsonData);
		} catch (Exception e) {
			log.error(method, e);
			e.printStackTrace();
		}
		return info;
	}
	
    /**
     * 创建场次关联二维码入库
     * 
     * @author tangxing
     * @date 2016-8-19下午1:36:10
     */
    public void saveErpQRCode(){
    	JSONObject json = new JSONObject();
    	String eventsId = HttpTool.getParameter("eventsId");
    	ErpEvents erpEvents = eventsService.get(eventsId);
    	if(erpEvents!=null){
    		try {
    			erpQRCodeService.saveErpQRCode(erpEvents);
    			json.accumulate("status", "200");
        		json.accumulate("message", "生成二维码成功！");
			} catch (Exception e) {
				log.error("saveErpQRCode--"+e);
				json.accumulate("status", "300");
	    		json.accumulate("message", "生成二维码失败！");
			}
    	}else{
    		json.accumulate("status", "300");
    		json.accumulate("message", "生成二维码失败！");
    	}
    	renderJson(json);
    }
    
    public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
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
	
}
