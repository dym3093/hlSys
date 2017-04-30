/**
 * 
 */
package org.hpin.events.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.ProjectType;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.region.dao.RegionDao;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.DateUtil;
import org.hpin.common.util.DateUtils;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpConference;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.entity.ErpExpress;
import org.hpin.events.service.ErpConferenceService;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.events.service.ErpExpressService;
import org.hpin.events.util.GuNoUtil;
import org.hpin.venueStaffSettlement.entity.vo.ConferenceQuery;
import org.hpin.venueStaffSettlement.service.ConferenceCostManageService;

/**
* 公司名称: 远盟康健(北京)科技有限公司
* 
* author: dengqin 
* createDate: 2016-3-28 下午3:31:58
*/
/**
 * @author dengqin
 *
 */

@Namespace("/events")
@Action("erpConference")
@Results({
    @Result(name="toAddConference",location="/WEB-INF/events/erpConference/addConference.jsp"),
    @Result(name="toModifyConference",location="/WEB-INF/events/erpConference/modifyConference.jsp"),
    @Result(name="listConference",location="/WEB-INF/events/erpConference/listConference.jsp")
})  

public class ErpConferenceAction extends BaseAction {
    
    ErpCustomerService customerService = (ErpCustomerService) SpringTool.getBean(ErpCustomerService.class);
    ErpExpressService expressService = (ErpExpressService) SpringTool.getBean(ErpExpressService.class);
//    ErpConferenceService conferenceService = (ErpConferenceService) SpringTool.getBean(ErpConferenceService.class);
    /*ErpConferenceService conferenceService = new ErpConferenceService();*/
    ErpEventsService eventsService = (ErpEventsService) SpringTool.getBean(ErpEventsService.class);
    ErpConferenceService conferenceService = (ErpConferenceService)SpringTool.getBean(ErpConferenceService.class);
    DeptService deptService = (DeptService) SpringTool.getBean(DeptService.class);
    CustomerRelationShipService relService=(CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
    private UserService userService = (UserService) SpringTool.getBean(UserService.class);
    RegionDao regionDao=(RegionDao)SpringTool.getBean(RegionDao.class);
    ConferenceCostManageService conferenceCostManService = (ConferenceCostManageService)SpringTool.getBean(ConferenceCostManageService.class);
    
    private Logger logger = Logger.getLogger(ErpConferenceAction.class);
    private ErpConference conference;
    private ErpCustomer customer;
    private ErpExpress express;
    private ConferenceQuery confQuery; //查询条件; create by henry.xu 20160928

	String id;  //参数id
    
    String conferenceNo;
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the conference
     */
    public ErpConference getConference() {
        return conference;
    }

    /**
     * @param conference the conference to set
     */
    public void setConference(ErpConference conference) {
        this.conference = conference;
    }

    /**
     * @return the customer
     */
    public ErpCustomer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(ErpCustomer customer) {
        this.customer = customer;
    }

    /**
     * @return the express
     */
    public ErpExpress getExpress() {
        return express;
    }

    /**
     * @param express the express to set
     */
    public void setExpress(ErpExpress express) {
        this.express = express;
    }
    
    public Page findByPage(Page page , Map searchMap){
        User currentUser = getUserInfo();
        String userName=currentUser.getUserName();
        if(!userName.equals("管理员")){
            searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
        }
        searchMap.put("filter_and_isDeleted_EQ_I", 0);
        
        List<ErpConference> conferenceList=conferenceService.findByPage(page, searchMap);

        page.setResults(conferenceList) ;
        
        return page ;
    }
    
    /**
     * 导出时调用;
     * @param page
     * @param searchMap
     * @return
     */
    @SuppressWarnings("rawtypes")
	public Page findConferenceByPage(Page page, Map searchMap) {
    	confQuery = new ConferenceQuery();
    	
    	User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        String userName=currentUser.getUserName();
        String accountName=currentUser.getAccountName();
        
        //此处数据中没有保存id所以只有使用登陆者姓名来查询;
        if(!userName.equals("管理员")&&!accountName.equals("zhangwei")&&!accountName.equals("xianglujia")&&!accountName.equals("jiying")){
            confQuery.setCreateUserName(userName);
        }
    	
        confQuery.setConferenceNo(HttpTool.getParameter("confQuery.conferenceNo", ""));
        confQuery.setConferenceType(HttpTool.getParameter("confQuery.conferenceType", ""));
        confQuery.setOwnedCompany(HttpTool.getParameter("confQuery.ownedCompany", ""));
        confQuery.setBranchCompany(HttpTool.getParameter("confQuery.branchCompany", ""));
        confQuery.setStartDate(HttpTool.getParameter("confQuery.startDate", ""));
        confQuery.setEndDate(HttpTool.getParameter("confQuery.endDate", ""));
        confQuery.setProvince(HttpTool.getParameter("confQuery.province", ""));
        confQuery.setCity(HttpTool.getParameter("confQuery.city", ""));
        confQuery.setProjectType(HttpTool.getParameter("confQuery.projectType", ""));
        
        conferenceCostManService.findConferenceCostsByCondition(page, confQuery);
    	
    	return page;
    }
    
    /**
     * 会议信息查询
     */
    @SuppressWarnings("rawtypes")
	public String listConference() {
    	try {
    		
    		List<ProjectType> proTypes = relService.findProjectTypes();
    		HttpTool.setAttribute("proTypes", proTypes);
    		
    		try {
    			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
            
    		conferenceCostManService.findConferenceCostsByCondition(page, confQuery);
		} catch (Exception e) {
			logger.error("listConference", e);
		}
        
        return "listConference";
    }
    
    /**
     * 添加会议的时候，不需要用户填充的数据（显示到添加场次页面）
     * @return
     * @throws IOException 
     * @throws ParseException 
     */
    public String toAddConference() throws ParseException, IOException{
    	
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");//获取当前用户
        String brach_company_id=currentUser.getJobNumber();//支公司号
        CustomerRelationShip    ship;//客户关系表
        if(brach_company_id==null){
            ship=new CustomerRelationShip();
        }else{
            ship = (CustomerRelationShip) relService.findById(brach_company_id);
            if(ship==null){
                ship=new CustomerRelationShip();
            }
        }
        
        Date now1 = new Date();
        SimpleDateFormat sd= new SimpleDateFormat(DateUtils.DATE_FORMAT);
        String now = sd.format(now1); //格式化后的当前时间
        StringBuilder confereceNo = new StringBuilder("HY");
        confereceNo.append(new SimpleDateFormat("yyMMdHHmm").format(new Date())+((int)(Math.random()*900)+100));
        if(currentUser.getDept()!=null){  //部门类
            Dept dept = (Dept) deptService.findById( currentUser.getDept().getParentId());
            HttpTool.setAttribute("dept", dept);
        }
        HttpTool.setAttribute("now", now);
        HttpTool.setAttribute("navTabId", super.getNavTabId());
        HttpTool.setAttribute("conferenceNo", confereceNo);
        return "toAddConference";
    }
    
    //判断添加的表单提交过来的对象
    public ErpConference getModel() {    
        if (null == conference) {    
            return conference = new ErpConference();    
        }    
        return conference;    
    } 
    
    /**
     * 会议信息增加
     * @return
     */
    public String addConfernece(){
        JSONObject json = new JSONObject();
        String conferenceNo = getModel().getConferenceNo();
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");//获取当前用户
//        String brach_company_id=currentUser.getJobNumber();//支公司号
//        
//        CustomerRelationShip ship = (CustomerRelationShip) relService.findById(brach_company_id);//客户关系表（根据支公司号）
        String provice = getModel().getProvice();//省份
        String city = getModel().getCity();//城市
        String company = getModel().getBranchCompany();//支公司
        String ownedCompany = getModel().getOwnedCompany();//总公司
        String proUser = getModel().getProUser();		//项目负责人
        String proCode = getModel().getProCode();		//项目编码
        String proBelong = getModel().getProBelong();	//项目名称
        String conferenceType = getModel().getConferenceType();
        if (StrUtils.isNullOrBlank(provice) || StrUtils.isNullOrBlank(city) || StrUtils.isNullOrBlank(company) || StrUtils.isNullOrBlank(conferenceType) ||
        	StrUtils.isNullOrBlank(ownedCompany) || StrUtils.isNullOrBlank(proUser) || StrUtils.isNullOrBlank(proCode)|| StrUtils.isNullOrBlank(proBelong) ) {
        	json.accumulate("statusCode", 300);
			json.accumulate("message", "信息不全,不能添加会议");
		}else{
			try {
//				String conferneceNo = conference.getConferenceNo();//会议号
				String hour = conference.getHour();//时间
				Date conferenceDate =conference.getConferenceDate();//会议日期
				String conferenceDateStr=DateUtil.getDateTime("MM/dd/yyyy", conferenceDate);//年月日
				String time= conferenceDateStr+" "+hour+":00:00";
				Date finalTime = DateUtil.convertStringToDate("MM/dd/yyyy HH:mm:ss", time);
				String message = conferenceService.isNotRepeatNoByDateAndBannyCompany(company,ownedCompany, finalTime,conference.getAddress()); //判断场次是否已添加
				if(StrUtils.isNullOrBlank(message)){//判断是否添加场次成功
					conference.setConferenceDate(finalTime);
					conference.setBranchCompany(company);
					conference.setProvice(provice);
					conference.setCity(city);
					conference.setOwnedCompany(ownedCompany);
					conference.setIsDeleted(0);
					conference.setIsExpress(0);
					conference.setCreateUserName(currentUser.getUserName());
					conference.setProBelong(proBelong);
					conference.setProCode(proCode);
					conference.setProUser(proUser);
					conference.setCreateTime(new Date());
					conference.setProduceCost(Double.valueOf(0));
					conference.setSettNumbers(0);
					conference.setConferenceType(conferenceType);
					conferenceService.save(conference); //save会议类
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
		}
        
        renderJson(json);
        return null;
    }
    
    /**
     * 删除
     * @return
     */
    public String delConference(){
        JSONObject json = new JSONObject();
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        String[] id = ids.replaceAll(" ", "").split(",");
        List<ErpConference> list = new LinkedList<ErpConference>();
        try {
        	Date now = new Date();
            for (int i = 0; i < id.length; i++) {
                conference = (ErpConference) conferenceService.findById(id[i]);
                conference.setIsDeleted(1);
                conference.setUpdateTime(now);
                conference.setUpdateUserName(currentUser.getAccountName());
                conferenceService.modify(conference);
                //级联删除场次
                ErpEvents erpEvents = eventsService.queryByEventNO(conference.getConferenceNo());
            	if(erpEvents!=null){
            		erpEvents.setIsDeleted(1);
            		erpEvents.setUpdateTime(now);
            		erpEvents.setUpdateUserName(currentUser.getAccountName());
            		eventsService.updateInfo(erpEvents);
            	}
            }
//              conferenceService.delete(list);
                json.accumulate("statusCode", 200);
                json.accumulate("message", "删除成功");
//              json.accumulate("navTabId", super.navTabId);
//              json.accumulate("callbackType", "closeCurrent");
            } catch (Exception e) {
                json.accumulate("statusCode", 300);
                json.accumulate("message", "删除失败");
                e.printStackTrace();
            }
        renderJson(json);
        return null;
    }
    
    /**
     * 修改的默认数据
     */
    public String toModifyConference(){
        conference=conferenceService.get(id);//获取当前选中的会议
      //查询项目编码信息;
        if(StringUtils.isNotEmpty(conference.getCustomerRelationShipProId())) {
        	HttpTool.setAttribute("shipPro", relService.findCustRelShipProById(conference.getCustomerRelationShipProId()));
        }
        String conferenceDateStr=DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", conference.getConferenceDate());
        
        HttpTool.setAttribute("conferenceDateStr", conferenceDateStr);
        return "toModifyConference";
    }
    
    /**
     * 修改
     * @description 
     * @return
     *
     * @return String
     * @throws
     *
     */
    public String modifyConference(){
        JSONObject json = new JSONObject();
//      String isUpdateCustDate=HttpTool.getParameter("isUpdateCustDate");
        String conferenceType=HttpTool.getParameter("conference.conferenceType");
        String provice = HttpTool.getParameter("conference.provice");
        String city = HttpTool.getParameter("conference.city");
        String branchCompanyId = HttpTool.getParameter("conference.branchCompanyId");
        String ownedCompanyId = HttpTool.getParameter("conference.ownedCompanyId");
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        try {
        	conference.setProvice(provice);
        	conference.setCity(city);
        	conference.setBranchCompanyId(branchCompanyId);
        	conference.setOwnedCompanyId(ownedCompanyId);
        	conference.setUpdateTime(new Date());
        	conference.setUpdateUserName(currentUser.getUserName());
        	conference.setConferenceType(conferenceType);
        	conferenceService.modify(conference);//判断场次是否已添加
        	//级联操作场次的isdeleted状态   
        	String eventsNo = conference.getConferenceNo();
        	//根据会议号获取场次 ---有则更改  没有则不操作
        	ErpEvents erpEvents = eventsService.queryByEventNO(eventsNo);
        	if(erpEvents!=null){
        		erpEvents.setIsDeleted(conference.getIsDeleted());
        		eventsService.updateInfo(erpEvents);
        	}
        	//根据场次号更新客户信息
        	//if(isUpdateCustDate.equals("1")){
        	//	customerService.updateSampleDatebyConferenceNo(conference);
        	//}
        	json.accumulate("statusCode", 200);
        	json.accumulate("message", "修改成功");
        	json.accumulate("navTabId", super.navTabId);
        	json.accumulate("callbackType", "closeCurrent");
            } catch (Exception e) {
                json.accumulate("statusCode", 300);
                json.accumulate("message", "修改失败");
                e.printStackTrace();
            }
        renderJson(json);
        return null;
    }
    
    
    /**
     * 生成会议号
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public String createNo() throws ParseException, IOException{
        Date date = new Date();
        String sData="";
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
            String conferenceDate = sf.format(date);
            String conferenceNo = conferenceService.maxNo(conferenceDate);
            sData = GuNoUtil.getNoTwo(conferenceNo,date);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(sData);
            conferenceNo = sData;
//        return sData;
            return null;
    }
    
    public ConferenceQuery getConfQuery() {
		return confQuery;
	}

	public void setConfQuery(ConferenceQuery confQuery) {
		this.confQuery = confQuery;
	}

}
