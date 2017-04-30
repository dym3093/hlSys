/**
 * 
 */
package org.hpin.reportdetail.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.region.dao.RegionDao;
import org.hpin.base.region.entity.Region;
import org.hpin.base.region.service.RegionService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.DateUtils;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpConference;
import org.hpin.reportdetail.entity.CityBean;
import org.hpin.reportdetail.entity.ErpPrintBatch;
import org.hpin.reportdetail.entity.ErpPrintTask;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.hpin.reportdetail.entity.PrintBatchInfoBean;
import org.hpin.reportdetail.entity.ProvinceBean;
import org.hpin.reportdetail.service.ErpPrintBatchService;
import org.hpin.reportdetail.service.ErpPrintTaskService;
import org.hpin.reportdetail.service.ErpReportdetailPDFContentService;

/**
* 公司名称: 远盟康健(北京)科技有限公司
* 
* author: dengqin 
* createDate: 2016-4-14 下午3:33:10
*/
/**
 * @author dengqin
 *
 */
@Namespace("/reportdetail")
@Action("erpPrintBatch")
@Results({
    @Result(name="toAddPrintBatch",location="/WEB-INF/reportdetail/addPrintBatch.jsp"),
    @Result(name="toModifyPrintBatch",location="/WEB-INF/reportdetail/modifyPrintBatch.jsp"),
    @Result(name="listPrintBatch",location="/WEB-INF/reportdetail/listPrintBatch.jsp"),
    @Result(name="lookPrintTask",location="/WEB-INF/reportdetail/lookPrintTask.jsp"),
    @Result(name="printBatchDetail",location="/WEB-INF/reportdetail/printBatchDetail.jsp"),
    @Result(name="listAlreadyPrintBatch", location="/WEB-INF/reportdetail/listAlreadyPrintBatch.jsp"),
    @Result(name="listUnPrintBatch", location="/WEB-INF/reportdetail/listUnPrintBatch.jsp"),
    @Result(name="undonePrintTask", location="/WEB-INF/reportdetail/undonePrintTask.jsp"),
    @Result(name="manualCreatePrint", location="/WEB-INF/reportdetail/manualCreatePrint.jsp"),
    
})  
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ErpPrintBatchAction extends BaseAction{
	private Logger log = Logger.getLogger(ErpPrintBatchAction.class);
    ErpPrintBatchService printBatchService = (ErpPrintBatchService) SpringTool.getBean(ErpPrintBatchService.class);
    ErpPrintTaskService printTaskService = (ErpPrintTaskService) SpringTool.getBean(ErpPrintTaskService.class);
    DeptService deptService = (DeptService) SpringTool.getBean(DeptService.class);
    CustomerRelationShipService relService=(CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
    RegionDao regionDao=(RegionDao)SpringTool.getBean(RegionDao.class);
    ErpReportdetailPDFContentService contentService = (ErpReportdetailPDFContentService) SpringTool.getBean(ErpReportdetailPDFContentService.class);
    RegionService regionService = (RegionService)SpringTool.getBean(RegionService.class);
    private ErpPrintBatch printBatch;
    
    List<String> provinces = new ArrayList<String>();
    List<String> citys = new ArrayList<String>();
    Set<String> provinceSet=new LinkedHashSet<String>();
    Set<String> citySet=new LinkedHashSet<String>();
    
    String id;  //参数id
    
    String printBatchNo;
    
    int threeNum;
    
    String taskName;
    
    String printBatchDate;
    
    String provinceNo;	//省份的字典项
    
	public String getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
     * @return the printBatchService
     */
    public ErpPrintBatchService getPrintBatchService() {
        return printBatchService;
    }

    /**
     * @param printBatchService the printBatchService to set
     */
    public void setPrintBatchService(ErpPrintBatchService printBatchService) {
        this.printBatchService = printBatchService;
    }

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
     * @return the printBatch
     */
    public ErpPrintBatch getPrintBatch() {
        return printBatch;
    }

    /**
     * @param printBatch the printBatch to set
     */
    public void setPrintBatch(ErpPrintBatch printBatch) {
        this.printBatch = printBatch;
    }

    /**
     * @return the printBatchNo
     */
    public String getPrintBatchNo() {
        return printBatchNo;
    }

    /**
     * @param printBatchNo the printBatchNo to set
     */
    public void setPrintBatchNo(String printBatchNo) {
        this.printBatchNo = printBatchNo;
    }

    public Page findByPage(Page page , Map searchMap){
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        String userName=currentUser.getUserName();
//        if(!userName.equals("管理员")){
//            searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
//        }
        searchMap.put("filter_and_isDelete_EQ_S", 0);
        
        List<ErpPrintBatch> printBatchList=printBatchService.findByPage(page, searchMap);
        page.setResults(printBatchList) ;
        
        return page ;
    }
    
    /**
     * 批次信息查询
     */
	public String listPrintBatch() throws Exception{
        Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
        searchMap.put("filter_and_isDelete_EQ_S", 0);//默认查询条件
        searchMap.put("filter_and_isPrintTask_EQ_S", 0);//默认查询条件
        String queryCodes = HttpTool.getParameter("queryCodes");
        List<String> hasNoPrintCode = new ArrayList<String>();
        if(null!=queryCodes&&!("").equals(queryCodes)){
        	HashSet<String> allPrintBthno = new HashSet<String>();
        	List<String> codeList=dealString2List(queryCodes);
        	Iterator<String> iter = codeList.iterator();
        	while(iter.hasNext()){
        		String code = iter.next();
        		HashSet<String> printBthnos = contentService.queryPrintBthnoByCode(code);
        		if(null == printBthnos){
        			hasNoPrintCode.add(code);
        		}else{
        			allPrintBthno.addAll(printBthnos);
        		}
        	}
        	if(0<allPrintBthno.size()){
        		if(searchMap.containsKey("filter_and_printBatchNo_LIKE_S")){
        			searchMap.remove("filter_and_printBatchNo_LIKE_S");
        		}
        		String printBthnoStr = dealSet2String(allPrintBthno);
        		searchMap.put("filter_and_printBatchNo_IN_S", printBthnoStr);
        	}
        	if(0<hasNoPrintCode.size()){
        		HttpTool.setAttribute("noPrintCode", dealList2String(hasNoPrintCode));
        	}
        }

        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
        List<ErpPrintBatch> batchList = printBatchService.findByPage(page, searchMap);
        page.setResults(batchList);
        HttpTool.setAttribute("province", HttpTool.getParameter("filter_and_province_EQ_S"));
        HttpTool.setAttribute("city", HttpTool.getParameter("filter_and_city_EQ_S"));
        HttpTool.setAttribute("filter_and_ownedCompany_EQ_S", HttpTool.getParameter("filter_and_ownedCompany_EQ_S"));
        HttpTool.setAttribute("filter_and_branchCompanyId_EQ_S", HttpTool.getParameter("filter_and_branchCompanyId_EQ_S"));
        HttpTool.setAttribute("filter_and_combo_LIKE_S", HttpTool.getParameter("filter_and_combo_LIKE_S"));
        HttpTool.setAttribute("dept", HttpTool.getParameter("filter_and_dept_EQ_S"));
        HttpTool.setAttribute("queryCodes", queryCodes);
    	
        return "listPrintBatch";
    }
    
	/**
     * 获取打印批次集合
     * @param printBatchList
     * @throws ParseException 
     */
    private List<ErpPrintBatch> getProvinceCity() throws ParseException{
    	
        List<ErpPrintBatch> printBatchList=printBatchService.getProvinceCity();
        
        return printBatchList;
    }
    
    /**
     * 获取已打印批次集合
     * @return
     * @throws ParseException
     * @author tangxing
     * @date 2016-5-31下午5:09:26
     */
    private List<ErpPrintBatch> getProvinceCityTwo() throws ParseException{
    	
        List<ErpPrintBatch> printBatchList=printBatchService.getProvinceCityTwo();
        
        return printBatchList;
    }
    
    
    /**
     * 加载页面获取省份
     * @throws ParseException
     */
    public void getProvinceRequest() throws ParseException{
    	System.out.println("---------开始加载省份-----------");
    	ErpPrintBatch erpPrintBatch = new ErpPrintBatch();
    	Long starttime= new Date().getTime();
    	String flag = HttpTool.getParameter("flag");		
    	String pro = "";
    	String provinceids="";
    	Iterator<ErpPrintBatch> it=null;
    	
    	if(StringUtils.isEmpty(flag)){					//判断是否为已处理打印批次页面的查询请求
    		it = getProvinceCity().iterator();			
    	}else if(flag.equals("flag")){
    		it = getProvinceCityTwo().iterator();		//已处理打印批次
    	}
    		
    	
    	while(it.hasNext()){
    		erpPrintBatch = it.next();
        	pro = erpPrintBatch.getProvince();
//        	provinces.add(pro);
        	if(provinces.contains(pro)){	//排重
        		it.remove();
        	}else{
        		provinces.add(pro);
        	}
        }
//        provinceSet.addAll(provinces);	//设置进set集合，排重
    	
    	
    	JSONObject jsonObject = new JSONObject();
    	List<ProvinceBean> proList = new ArrayList<ProvinceBean>();
    	Iterator<String> itP = provinces.iterator();
    	
    	while (itP.hasNext()) {
			ProvinceBean probean = new ProvinceBean();
			provinceids = itP.next();
			String provinceName = regionService.id2Name(provinceids,null);
			probean.setProvinceid(provinceids);
			probean.setProvincename(provinceName);
			proList.add(probean);
		}
		jsonObject.put("province", proList);
		Long endtime= new Date().getTime();
		renderJson(jsonObject);
		System.out.println("省份加载耗时---------"+(endtime-starttime));
    }
    
    /**
     * 根据选中的省份获取对应的城市
     * @throws ParseException
     */
    public void getCityRequest() throws ParseException{
    	System.out.println("---------开始加载城市-----------");
    	ErpPrintBatch erpPrintBatch = new ErpPrintBatch();
    	Long starttime= new Date().getTime();
    	Iterator<ErpPrintBatch> it = null;
    	String flag = HttpTool.getParameter("flag");
    	String city = "";
    	String str="";
    	String threeS="";
    	String cityThreeS="";
    	JSONObject jsonObject = new JSONObject();
    	
    	if(StringUtils.isEmpty(flag)){
    		it = getProvinceCity().iterator();
    	}else if(flag.equals("flag")){
    		it = getProvinceCityTwo().iterator();
    	}
    	
    	while(it.hasNext()){
    		erpPrintBatch = it.next();
        	city = erpPrintBatch.getCity();
        	if(citys.contains(city)){	//排重
        		it.remove();
        	}else{
        		citys.add(city);
        	}
//        	citySet.add(city);
        }
//        citySet.addAll(citys);
		
    	if(provinceNo!=null&&!provinceNo.equals("")){
    		List<CityBean> cityBeanList = new ArrayList<CityBean>();
    		threeS=provinceNo.substring(0,2);		//省份前两位为
    		Iterator<String> itC = citys.iterator();  
    		while (itC.hasNext()) {  
    		  str = itC.next(); 
    		  if(str!=null&&!str.equals("")){
    			cityThreeS=str.substring(0, 2);	//城市前两位
	    		if(cityThreeS.equals(threeS)){	//如果城市的前三位和省份前三位相等，就存起来
	    			CityBean cityBean = new CityBean();
	    			String cityName = regionService.id2Name(str,null);
	    			cityBean.setCityid(str);
	    			cityBean.setCityname(cityName);
	    			cityBeanList.add(cityBean);
	    		} 
    		  }
    		}
    		jsonObject.put("city", cityBeanList);
    	}
    	Long endtime= new Date().getTime();
    	renderJson(jsonObject);
    	System.out.println("城市加载耗时---------"+(endtime-starttime));
    }
    
    /**
     * 打印批次下的详细信息
     * @throws ParseException 
     */
    public String printBatchDetail() throws ParseException{
    	String id = HttpTool.getParameter("id");
    	printBatch = printBatchService.get(id);
    	
        Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
        
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        String userName=currentUser.getUserName();
//        if(!userName.equals("管理员")){
//            searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
//        }printbthno
        searchMap.put("filter_and_printBatchNo_EQ_S", printBatchNo);//默认查询条件
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
    	
    	List<ErpPrintTaskContent> pdfContents = printBatchService.findByPageReport(page, searchMap);
    	
    	page.setResults(pdfContents);
    	HttpTool.setAttribute("printBatchBean", printBatch);
    	
    	return "printBatchDetail";
    }
    
    /**
     * 添加批次的时候，不需要用户填充的数据（显示到添加场次页面）
     * @return
     * @throws IOException 
     * @throws ParseException 
     */
    public String toAddPrintBatch() throws ParseException, IOException{
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");//获取当前用户
        String printBatchNos = createNo();
//        String brach_company_id=currentUser.getJobNumber();//支公司号
//        CustomerRelationShip    ship;//客户关系表
//        if(brach_company_id==null){
//            ship=new CustomerRelationShip();
//        }else{
//            ship = (CustomerRelationShip) relService.findById(brach_company_id);
//            if(ship==null){
//                ship=new CustomerRelationShip();
//            }
//        }
        
        Date now1 = new Date();
        SimpleDateFormat sd= new SimpleDateFormat(DateUtils.DATE_FORMAT);
        String now = sd.format(now1); //格式化后的当前时间
        
//        if(currentUser.getDept()!=null){  //部门类
//            Dept dept = (Dept) deptService.findById( currentUser.getDept().getParentId());
//            HttpTool.setAttribute("dept", dept);
//        }
        HttpTool.setAttribute("now", now);
        HttpTool.setAttribute("navTabId", super.getNavTabId());
        HttpTool.setAttribute("printBatchNo", printBatchNos);
        return "toAddPrintBatch";
    }
    
    //判断添加的表单提交过来的对象
    public ErpPrintBatch getModel() {    
        if (null == printBatch) {    
            return printBatch = new ErpPrintBatch();    
        }    
        return printBatch;    
    } 
    
    /**
     * 批次信息增加
     * @return
     */
    public String addPrintBatch(){
        JSONObject json = new JSONObject();
        
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");//获取当前用户
       
        //提交过来的批次对象
        String nowPrintBatchNo = getModel().getPrintBatchNo();
        String province = getModel().getProvince();//省份
        String city = getModel().getCity();//城市
        String company = getModel().getBranchCompany();//支公司
        String ownedCompany = getModel().getOwnedCompany();//总公司
        int count = getModel().getCount();  //数量
        String ymContacts = getModel().getYmContacts();//远盟联系人
        String events = getModel().getEvents();//场次
        String combo = getModel().getCombo();//套餐
        
        try {
//            Date hour = printBatch.getCreateTime();//创建时间
//            Date conferenceDate =conference.getConferenceDate();//会议日期
//            String conferenceDateStr=DateUtil.getDateTime("MM/dd/yyyy", conferenceDate);//年月日
//            String time= conferenceDateStr+" "+hour+":00:00";
//            Date finalTime = DateUtil.convertStringToDate("MM/dd/yyyy HH:mm:ss", time);
            String message = printBatchService.isNotRepeatNoByEvents(printBatchNo,events); //判断场次是否已添加
            if(StrUtils.isNullOrBlank(message)){//判断是否添加场次成功
                printBatch.setPrintBatchNo(nowPrintBatchNo);
                printBatch.setCombo(combo);
                printBatch.setCount(count);
                printBatch.setYmContacts(ymContacts);
                printBatch.setEvents(events);
                printBatch.setBranchCompany(company);
                printBatch.setProvince(province);
                printBatch.setCity(city);
                printBatch.setOwnedCompany(ownedCompany);
                printBatch.setIsDeleted(0);
                printBatch.setCreateTime(new Date());
                
                printBatchService.save(printBatch); //save会议类
                json.accumulate("statusCode", 200);
                json.accumulate("message", "增加成功");
                json.accumulate("navTabId", super.navTabId);
                json.accumulate("callbackType", "closeCurrent");
            }else{
                json.accumulate("statusCode", 300);
                json.accumulate("message", message);
            }
        } catch (Exception e) {
        	log.error("addPrintBatch",e);
            json.accumulate("statusCode", 300);
            json.accumulate("message", "增加失败");
        }
        
        renderJson(json);
        return null;
    }
    
    /**
     * 删除
     * @return
     */
    public String delPrintBatch(){
        JSONObject json = new JSONObject();
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        String[] id = ids.replaceAll(" ", "").split(",");
        List<ErpConference> list = new LinkedList<ErpConference>();
        try {
            for (int i = 0; i < id.length; i++) {
                printBatch = (ErpPrintBatch) printBatchService.findById(id[i]);
                printBatch.setIsDeleted(1);
                printBatch.setUpdateTime(new Date());
                printBatchService.modify(printBatch);
            }
//              conferenceService.delete(list);
                json.accumulate("statusCode", 200);
                json.accumulate("message", "删除成功");
            } catch (Exception e) {
            	log.error("delPrintBatch",e);
                json.accumulate("statusCode", 300);
                json.accumulate("message", "删除失败");
            }
        renderJson(json);
        return null;
    }
    
    /**
     * 修改的默认数据
     */
    public String toModifyPrintBatch(){
        printBatch=printBatchService.get(id);//获取当前选中的批次
//      String eventsNo=events.getEventsNo();
//      int nowHeadcount=eventsService.getNowHeadcountByEventsNo(eventsNo);
        
//        String conferenceDateStr=DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", conference.getConferenceDate());
        
        HttpTool.setAttribute("conferenceDateStr", printBatch.getCreateTime());
        return "toModifyPrintBatch";
    }
    
    
    /**
     * 处理批次的打印任务添加
     */
    public String isPrintBatch(){
    	log.info("isPrintBatch ids :"+ids);
    	Integer sumCount = 0;
    	Integer tempNum = 0;
    	List<ErpPrintBatch> printBatchList = new ArrayList<ErpPrintBatch> ();
    	
    	String exTime = expectTime();
    	String printTaskNo = defTaskNum();
    	threeNum = ranNum();
    	printBatchDate = creTime();
    	String[] id = ids.replaceAll(" ", "").split(",");
    	
    	ErpPrintBatch printBatch = null;
    	StringBuilder areas = new StringBuilder("");
    	StringBuilder combos = new StringBuilder("");
    	 //遍历id集合
    	 for(int i=0; i<id.length; ++i){
    		 printBatch = printBatchService.get(id[i]); 
    		 
    		 //拼接不同的区域;
    		 log.info(printBatch.getCity());
    		 String str = regionService.id2Name(printBatch.getCity(), null);//查询下关于区域的name
    		 if(!areas.toString().contains(str)) {
    			 areas.append(str); 
    			 
    		 }
    		 //拼接不同的套餐
    		 if(!combos.toString().contains(printBatch.getCombo())) {
    			 combos.append(printBatch.getCombo());    			 
    		 }
    		 
    		 tempNum = printBatch.getCount();
    		 sumCount = sumCount + tempNum;
    		 log.info("选中id对应的ErpPrintBatch-------------"+printBatch);
    		 printBatchList.add(printBatch);
    		 getTaskName(id[i]);
    	 }
    	
    	 int listSize = printBatchList.size();
    	 
    	 HttpTool.setAttribute("sumCount", sumCount);		//报告总数量
    	//设置打印任务号规则: '日期+套餐+地区+报告数量';
    	 HttpTool.setAttribute("printTaskNo", printTaskNo + combos.toString() + areas.toString() + sumCount);	//默认打印任务号
    	 HttpTool.setAttribute("expectTime", exTime);		//预计时间
    	 HttpTool.setAttribute("threeNum", threeNum);		//随机三位数
    	 HttpTool.setAttribute("batchNum", listSize);		//批次数量
    	 HttpTool.setAttribute("createTime", printBatchDate);	//创建时间
    	 HttpTool.setAttribute("taskName", taskName);		//任务名称
    	 HttpTool.setAttribute("printBatchId", ids);		//选中的id
    	 HttpTool.setAttribute("companyId", printBatchList.get(0).getBranchCompany());		//选中的支公司Id
    	 HttpTool.setAttribute("printBatchNos", HttpTool.getParameter("printBatchNos"));		//选中的批次号
    	 HttpTool.setAttribute("projectCode", printBatchList.get(0).getProjectCode());			//项目编码
    	 HttpTool.setAttribute("dept", printBatchList.get(0).getDept());//支公司部门
         page = new Page();
         page.setResults(printBatchList);
//         HttpTool.setAttribute("printBatchList", printBatchList);
    	 
    	 return "lookPrintTask";
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
    public String modifyPrintBatch(){
        JSONObject json = new JSONObject();
        String isUpdateCustDate=HttpTool.getParameter("isUpdateCustDate");
        String conferenceType=HttpTool.getParameter("conferenceType");
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        try {
//                String conferenceNo=printBatchService.getConferenceNo();
                //int nowHeadcount=eventsService.getNowHeadcountByEventsNo(eventsNo);
//              HttpTool.setAttribute("nowHeadcount", nowHeadcount);
//              events.setNowHeadcount(nowHeadcount);
                
                printBatch.setUpdateTime(new Date());
//                printBatch.setConferenceType(conferenceType);
                printBatchService.modify(printBatch);//判断场次是否已添加
                //根据场次号更新客户信息
//                if(isUpdateCustDate.equals("1")){
//                    customerService.updateSampleDatebyConferenceNo(conference);
//                }
                json.accumulate("statusCode", 200);
                json.accumulate("message", "修改成功");
                json.accumulate("navTabId", super.navTabId);
                json.accumulate("callbackType", "closeCurrent");
            } catch (Exception e) {
            	log.error("modifyPrintBatch",e);
                json.accumulate("statusCode", 300);
                json.accumulate("message", "修改失败");
            }
        renderJson(json);
        return null;
    }
    
    
    /**
     * 生成批次号
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public String createNo() throws ParseException, IOException{

//            String printBatchNos = printBatchService.maxNo(printBatchDate);
//            sData = GuNoUtil.getNoTwo(printBatchDate,date);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
//            response.getWriter().write(sData);
            printBatchNo = printBatchDate;
//        return sData;
            return null;
    }
    
    /**
     * 生成文件名
     * @param id
     */
    public void getTaskName(String id){
    	
    	printBatch = printBatchService.get(id);
    	String s = printBatch.getProvince()+printBatch.getCity()+"_"+printBatch.getEvents()+"_"+
    				printBatch.getBranchCompany()+"_"+printBatch.getCombo();
    	taskName = s;
    }
    
    /**
     * 默认的打印任务号
     * @return
     * @author tangxing
     * @date 2016-5-16下午3:23:28
     */
    public String defTaskNum(){
    	Date date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    	String time = dateFormat.format(date);
    	return time;
    }
    
    public int ranNum(){
    	Random randomNumber = new Random();
    	int i=randomNumber.nextInt(900)+100;	//三位数随机数
    	return i;
    }
    
    /**
     * 创建时间
     * @return
     * @author tangxing
     * @date 2016-5-16下午3:27:55
     */
    public String creTime(){
    	Date date = new Date();
        String sData="";
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        sData = sf.format(date);
            
        return sData;    
    }
    
    /**
     * 预计时间(往后48小时)
     * @return
     * @author tangxing
     * @date 2016-5-16下午3:28:30
     */
    public String expectTime(){
    	String exTime = "";
    	 long currentTime = System.currentTimeMillis() + 48 * 60 * 60 * 1000;
         Date date = new Date(currentTime); 
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
         try{ 
             exTime = sdf.format(date);
             System.out.println("下一天的时间是：" + exTime); 
         } 
         catch (Exception e){ 
        	 log.error("expectTime",e);
         } 
    	
    	return exTime;
    }
    
	/**
	 * 生成模块树JSON
	 * @return
	 * @throws Exception
	 */
	public String treeRegion() throws Exception{
		System.out.println("------省份城市树-----");
		StringBuffer json = new StringBuffer("[") ;
		List<Region> regionList = printBatchService.findRegionByParentId("0") ;
		Region region = null ;
		if(regionList.size() > 0){
			for(int i = 0 ; i < regionList.size() ; i ++){
				region = regionList.get(i) ;
				json.append("{\"text\":\"" + region.getRegionName() + "\",\"id\":\"" + region.getId() + "\",\"leaf\":" + false) ;
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
	 * 查询已处理批次(分页)
	 * @return String
	 * @throws Exception
	 * @author dym
	 */
	public String listAlreadyPrintBatch() throws Exception{
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		
		/*super.page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());*/
//		List<ErpPrintBatch> list =printBatchService.findByPageIsPrint(super.page, searchMap);
		printBatchService.findByPageIsPrint(page, searchMap);
		String provinceTwo = HttpTool.getParameter("filter_and_province_EQ_S");
        String cityTwo = HttpTool.getParameter("filter_and_city_EQ_S");
//		List<ErpPrintBatch> list = printBatchService.listAlreadyErpPrintBatchs();
//		page = new Page();
		HttpTool.setAttribute("provinceTwo", provinceTwo);
        HttpTool.setAttribute("cityTwo", cityTwo);
//        super.page.setResults(list);
		return "listAlreadyPrintBatch";
	}
	
	/**
	 * 获取未打印批次
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listUnPrintBatch() throws Exception{
		//获取打印任务的ID
		String printTaskId = HttpTool.getParameter("id");
		String printTaskNo = HttpTool.getParameter("printTaskNo");
		
		Map searchMap = super.buildSearch();
		
		//注意：erp_print_batch中的printTaskId字段实际存储的是erp_printtask表的printTaskNo字段内容而非实际的printTaskId
		searchMap.put("filter_and_printTaskId_IS_S", "null");
		
		searchMap.put("filter_and_isDelete_EQ_S", "0");
		searchMap.put("filter_and_isPrintTask_EQ_S", "0");
		page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
		//获取未在打印任务中的批次
		printBatchService.queryUnPrintBatchByPage(page, searchMap);
		
		//把打印printTaskId,printTaskNo传入listUnPrintBatch.jsp页面，以便之后的新增该批次到任务中时使用
		HttpTool.setAttribute("printTaskId", printTaskId);  
		HttpTool.setAttribute("printTaskNo", printTaskNo);  
		//传入listUnPrintBatch.jsp页面
		return "listUnPrintBatch";
	}
	
	/**
	 * 删除打印任务中的批次
	 * @return
	 * @throws Exception
	 */
	public String removePrintBatchFromPrintTask() throws Exception{
		JSONObject json = new JSONObject();
		String ids = HttpTool.getParameter("ids");
		String printTaskId = HttpTool.getParameter("printTaskId");
		//
		printBatchService.removePrintBatchFromPrintTask(ids);
		
		
		ErpPrintTask printTask = printTaskService.get(printTaskId);
		//更改打印任务中的批次数量
		String batchNum = printTask.getBatchNum();
		int num = 0;
		if(batchNum.length()>0){
			num += Integer.valueOf(batchNum);
		}
		//ids为逗号分隔的字符串
		if(ids.indexOf(",")>-1){
			String[] idArr = ids.split(",");
			num -= idArr.length;
		}else{
			num -= 1;
		}
		
		printTask.setBatchNum(""+num);
		printTaskService.save(printTask);
		json.put("result","success");
		renderJson(json);
		//返回未打印页面
		return null;
	}
	
	//查询需要手工处理的数据
	public String queryManualPrintBth() throws Exception{
		//从PDFCONTENT中获取需要手工处理的数量
		List<PrintBatchInfoBean> batchPrint = contentService.getManualPrintBatch();
		
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		List<ErpPrintBatch> printBatchList=printBatchService.findByPage(page, searchMap);
		
		return "manualCreatePrint";
	}
	
	//处理字符串
	public List<String> dealString2List(String str){
		String newStr = str.replaceAll("'","");
		String strs[] = newStr.split(",");
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, strs);
		return list;
	}
	
	//将Set转为Str,能直接用In形式查询
    private String dealSet2String(HashSet<String> allPrintBthno) {
    	Iterator<String> iter = allPrintBthno.iterator();
    	StringBuffer result = new StringBuffer();
    	while(iter.hasNext()){
    		result.append("'"+iter.next()+"',");
    	}
    	String newRes = result.toString();
    	return newRes.substring(0,newRes.lastIndexOf(","));
	}
    
    //list转String
    public String dealList2String(List<String> str){
    	Iterator<String> iter = str.iterator();
    	StringBuffer result = new StringBuffer();
    	while(iter.hasNext()){
    		result.append(iter.next()+",");
    	}
    	String newRes = result.toString();
    	return newRes.substring(0,newRes.lastIndexOf(","));
    }
    
/*    *//**
     * 设置套餐下拉框
     *//*
    public void setComboSelectedInput(){
    	setSelectedInput("combo",printBatchService.getSelectedInputData());
    }*/
    
    /**
     *  设置总公司和套餐的下拉框
     */
    public void setSelectedInput(){
    	setSelectedInput("selectedData", printBatchService.getSelectedInputData());
    }
    
    /**
     *  设置支公司的下拉框
     */
    public void setBranchCompanySelectedInput(){
    	String ownedCompany= HttpTool.getParameter("ownedCompany");
    	setSelectedInput("branchCompany", printBatchService.getBranchCompanySelectedInputData(ownedCompany));
    }
    
    public void setOwnedCompanyByCity(){
    	String city = HttpTool.getParameter("city");
    	setSelectedInput("ownedCompany", printBatchService.getOwnedCompanySelectedInputData(city));
    }
    
    public void setSelectedInput(String jsonObj,List<ErpPrintBatch> list){
    	JSONObject json = new JSONObject();
		json.put(jsonObj, list);
		renderJson(json);
    }
    
}
