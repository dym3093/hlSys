package org.hpin.statistics.briefness.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.dict.service.SysDictTypeService;
import org.hpin.base.region.entity.Region;
import org.hpin.base.region.service.RegionService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.SystemConstant;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StaticMethod;
import org.hpin.common.widget.pagination.Page;
import org.hpin.statistics.briefness.entity.GeneReportSummary;
import org.hpin.statistics.briefness.entity.GeneReportSummaryFinance;
import org.hpin.statistics.briefness.service.CreateXmlBO;
import org.hpin.statistics.briefness.service.QueryService;
import org.hpin.statistics.briefness.util.ReportDbExecuteUtils;
import org.hpin.statistics.briefness.util.StatUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/statistics")
@Action("queryaction")
@Results({
	    @Result(name = "liststat101" , location = "/WEB-INF/pages/briefness/liststat101.jsp"),
	    @Result(name = "liststat108" , location = "/WEB-INF/pages/briefness/liststat108.jsp"),
	    @Result(name = "liststat121" , location = "/WEB-INF/pages/briefness/liststat121.jsp"),
	    @Result(name = "liststat102" , location = "/WEB-INF/pages/briefness/liststat102.jsp"),
		@Result(name = "liststat141" , location = "/WEB-INF/pages/briefness/liststat141.jsp"),
		@Result(name = "liststat102_s" , location = "/WEB-INF/pages/briefness/liststat102_s.jsp"),
	    @Result(name = "liststat103" , location = "/WEB-INF/pages/briefness/liststat103.jsp"),
	    @Result(name = "liststat104" , location = "/WEB-INF/pages/briefness/liststat104.jsp"),
	    @Result(name = "liststat105" , location = "/WEB-INF/pages/briefness/liststat105.jsp"),
	    @Result(name = "liststat105_s" , location = "/WEB-INF/pages/briefness/liststat105_1.jsp"),
	    @Result(name = "liststat106" , location = "/WEB-INF/pages/briefness/liststat106.jsp"),
	    @Result(name = "liststat107" , location = "/WEB-INF/pages/briefness/liststat107.jsp"),
	    @Result(name = "liststat109" , location = "/WEB-INF/pages/briefness/liststat109.jsp"),
	    @Result(name = "liststat110" , location = "/WEB-INF/pages/briefness/liststat110.jsp"),
	    @Result(name = "liststat111" , location = "/WEB-INF/pages/briefness/liststat111.jsp"),
	    @Result(name = "liststat107_s" , location = "/WEB-INF/pages/briefness/liststat107_1.jsp"),
	    @Result(name = "liststat121" , location = "/WEB-INF/pages/briefness/liststat121.jsp"),
	    @Result(name = "liststat120" , location = "/WEB-INF/pages/briefness/liststat120.jsp"),
	    @Result(name = "liststat131" , location = "/WEB-INF/pages/briefness/liststat131.jsp"),
	    @Result(name = "liststat132" , location = "/WEB-INF/pages/briefness/liststat132.jsp"),
	    @Result(name = "liststat133" , location = "/WEB-INF/pages/briefness/liststat133.jsp"),
	    @Result(name = "liststat134" , location = "/WEB-INF/pages/briefness/liststat134.jsp"),
	    @Result(name = "liststat135" , location = "/WEB-INF/pages/briefness/liststat135.jsp"),
	    @Result(name = "liststat136" , location = "/WEB-INF/pages/briefness/liststat136.jsp"),
	    @Result(name = "liststat137" , location = "/WEB-INF/pages/briefness/liststat137.jsp"),
	    @Result(name = "liststat138" , location = "/WEB-INF/pages/briefness/liststat138.jsp"),
	    @Result(name = "liststat139" , location = "/WEB-INF/pages/briefness/liststat139.jsp"),
	    @Result(name = "liststat140" , location = "/WEB-INF/pages/briefness/liststat140.jsp"),
	    @Result(name = "liststat142" , location = "/WEB-INF/pages/briefness/liststat142.jsp"),
	    @Result(name = "liststat143" , location = "/WEB-INF/pages/briefness/liststat143.jsp"),
	    @Result(name = "liststat144" , location = "/WEB-INF/pages/briefness/liststat144.jsp"),
	    @Result(name = "liststat122" , location = "/WEB-INF/pages/briefness/liststat122.jsp"),	   
	    @Result(name= "listreportFileTaskJY" ,location="/WEB-INF/foreign/reportFileTaskJY.jsp"),
	    @Result(name = "liststat123" , location = "/WEB-INF/pages/briefness/liststat123.jsp"),//add by tx(无创项目体检客户信息)	
	    @Result(name = "liststat124" , location = "/WEB-INF/pages/briefness/liststat124.jsp"),//add by tx(邀约客户信息)	
	    @Result(name = "liststat125" , location = "/WEB-INF/pages/briefness/liststat125.jsp"),//add by tx(无创体检客户信息（市）)	
	    @Result(name = "liststat126" , location = "/WEB-INF/pages/briefness/liststat126.jsp"),//add by tx(邀约客户信息（市）)
	    @Result(name = "liststat127" , location = "/WEB-INF/pages/briefness/liststat127.jsp"),//add by tx(无创邀约客户信息查询(基因部门报表下))
	    @Result(name = "liststat128" , location = "/WEB-INF/pages/briefness/liststat128.jsp"),//add by tx(统计报表-销售部报表-客户查询（新）)
	    @Result(name = "liststat129" , location = "/WEB-INF/pages/briefness/liststat129.jsp"),//add by tx(统计报表-基因部门报表-客户信息核对)
	    @Result(name = "liststat130" , location = "/WEB-INF/pages/briefness/liststat130.jsp"),//add by tx(统计报表- 保险公司报表- 预约客户统计)
	    @Result(name = "liststat145" , location = "/WEB-INF/pages/briefness/liststat145.jsp"),//add by wh((支公司)无创项目体检客户信息)
	    @Result(name = "liststat146" , location = "/WEB-INF/pages/briefness/liststat146.jsp"),//add by wh((支公司)邀约客户信息)
	    @Result(name = "liststat147" , location = "/WEB-INF/pages/briefness/liststat147.jsp"),//add by wh(检测客户信息查询（太平微磁）)
	    @Result(name = "liststat148" , location = "/WEB-INF/pages/briefness/liststat148.jsp"),//add by wh(申友已出报告客户信息)
	    @Result(name = "liststat150" , location = "/WEB-INF/pages/briefness/liststat150.jsp"),//add by wh(省公司物料申请)
	    @Result(name = "liststat151" , location = "/WEB-INF/pages/briefness/liststat151.jsp"),//add by wh(市公司物料申请)
	    @Result(name = "liststat152" , location = "/WEB-INF/pages/briefness/liststat152.jsp"),//add by wh(支公司物料申请)
	    @Result(name = "liststat153" , location = "/WEB-INF/pages/briefness/liststat153.jsp"),//add by wh(省公司检测客户汇总表)
	    @Result(name = "liststat154" , location = "/WEB-INF/pages/briefness/liststat154.jsp"),//add by wh(省公司邀约客户信息汇总表)
	    @Result(name = "liststat155" , location = "/WEB-INF/pages/briefness/liststat155.jsp"),//add by wh(市公司检测客户汇总表)
	    @Result(name = "liststat156" , location = "/WEB-INF/pages/briefness/liststat156.jsp"),//add by wh(市公司邀约客户信息汇总表)
	    @Result(name = "liststat157" , location = "/WEB-INF/pages/briefness/liststat157.jsp"),//add by wh(支公司检测客户汇总表)
	    @Result(name = "liststat158" , location = "/WEB-INF/pages/briefness/liststat158.jsp"),//add by wh(支公司公司邀约客户信息汇总表)
	    @Result(name = "liststat149" , location = "/WEB-INF/pages/briefness/liststat149.jsp"),//add by wh(基因决策委员会报表)
	    @Result(name = "liststat159" , location = "/WEB-INF/pages/briefness/liststat159.jsp"),//add by wh(保险公司套餐价格报表)
	    @Result(name = "liststat160" , location = "/WEB-INF/pages/briefness/liststat160.jsp"),//add by wh(收寄样本费用统计)
	    @Result(name = "liststat161" , location = "/WEB-INF/pages/briefness/liststat161.jsp"),//add by wh(出库快递费用统计)
	    @Result(name = "liststat162" , location = "/WEB-INF/pages/briefness/liststat162.jsp"),//add by wh(太平微磁报告状态查询)
	    @Result(name = "liststat163" , location = "/WEB-INF/pages/briefness/liststat163.jsp"),//add by wh(报告寄送费用统计)
	    @Result(name = "liststat164" , location = "/WEB-INF/pages/briefness/liststat164.jsp"),//add by wh(阳光对账报表)
	    @Result(name = "liststat165" , location = "/WEB-INF/pages/briefness/liststat165.jsp"),//create by henry.xu 20170401省公司场次查询
	    @Result(name = "liststat165_s" , location = "/WEB-INF/pages/briefness/liststat165_s.jsp"),//create by henry.xu 20170401省公司场次查询
	    @Result(name = "liststat166" , location = "/WEB-INF/pages/briefness/liststat166.jsp"),//add by wh(市公司场次查询)
	    @Result(name = "liststat166_s" , location = "/WEB-INF/pages/briefness/liststat166_s.jsp"),//add by wh(市公司场次查询)
	    @Result(name = "liststat167" , location = "/WEB-INF/pages/briefness/liststat167.jsp"),//add by wh(支公司场次查询)
	    @Result(name = "liststat167_s" , location = "/WEB-INF/pages/briefness/liststat167_s.jsp"),//add by wh(支公司场次查询)
	    @Result(name = "liststat168" , location = "/WEB-INF/pages/briefness/liststat168.jsp"),//add by wh(客户明细报表)
	    @Result(name = "liststat169" , location = "/WEB-INF/pages/briefness/liststat169.jsp"),//add by wh(套餐价格清单)
		@Result(name = "liststat170" , location = "/WEB-INF/pages/briefness/liststat170.jsp"),//create by henry.xu 20170407无创项目(生物电)项目数据统计表
	    @Result(name = "liststat171" , location = "/WEB-INF/pages/briefness/liststat171.jsp"),//create by henry.xu 20170407无创项目(微磁)项目数据统计表
		@Result(name = "liststat172" , location = "/WEB-INF/pages/briefness/liststat172.jsp"),//add by wh(无创邀约数据统计)
	    @Result(name = "liststat173" , location = "/WEB-INF/pages/briefness/liststat173.jsp"),//add by wh(无创检测数据统计)
	    @Result(name = "liststat174" , location = "/WEB-INF/pages/briefness/liststat174.jsp"),//add by wh(入库信息统计)
	    @Result(name = "liststat175" , location = "/WEB-INF/pages/briefness/liststat175.jsp"),//add by wh(入库信息统计)
})
public class QueryAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	public Logger logger = Logger.getLogger(this.getClass());
	QueryService queryService = (QueryService)SpringTool.getBean(QueryService.class) ;
	//QueryService queryService = (QueryService)SpringTool.getBean("queryService");
	ReportDbExecuteUtils executeService = (ReportDbExecuteUtils)SpringTool.getBean(ReportDbExecuteUtils.class) ;
	SysDictTypeService systemDictTypeService = (SysDictTypeService)SpringTool.getBean(SysDictTypeService.class) ;
	
	@Autowired
	private UserService userService; //
	
	private Long countSum; //create by henry.xu 20170407 合计字段;
	public Long getCountSum() {
		return countSum;
	}

	/**
	 * 打开转取页面，跟本系统框架查询保持一致
	 * @param page
	 * @param configId
	 * @param fieldId
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Page findBySearchDoPage(Page page , String configId , String fieldId , Map parameterMap) throws Exception{
//		Map actionMap = new HashMap() ;
//		actionMap.putAll(parameterMap);
//		Map mapp = StatUtil.parameterMap2UtilMap(parameterMap);
        
	    CreateXmlBO creathtmlBO = new CreateXmlBO(ServletActionContext.getRequest().getRealPath("/"));
       
	    Map map=creathtmlBO.GetListXml(configId,fieldId);
        String sql=StaticMethod.nullObject2String(map.get("sql")).trim();
        /*String summary=StaticMethod.nullObject2String(map.get("summary")).trim();
        String [] summaryarr=summary.split(",");
        for(int i=0;i<summaryarr.length;i++){
        	String sn=summaryarr[i];
        	String sv=(String)mapp.get(sn);
        	sv = StrUtils.isNotNullOrBlank(sv)?sv:"";
        	actionMap.put(sn, sv+"");
        }*/
        
        String params=StaticMethod.nullObject2String(map.get("params")).trim();
     	String paramsflag=StaticMethod.nullObject2String(map.get("paramsflag")).trim();
     	//组装参数值map，
     	Map sqlParams=new HashMap();
        sqlParams =creathtmlBO.getParams(sqlParams,params,paramsflag,parameterMap);
        sql = StatUtil.getRepString(sql,sqlParams,"@");//结果集sql
        
        if(StringUtils.isNotBlank(sql)){
    		queryService.findBySqlPage(page , sql , null) ;
    	}
    	return page ;
	}
	
   /**
   * @执行查询操作，执行完后页面跳转到查询结果页面
   * @param mapping ActionMapping
   * @param form ActionForm
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @throws Exception
   * @return ActionForward
   */
	public String performSearchDo() {
	    try {
			Map searchMap = super.buildSearch();
 
			String province = HttpTool.getParameter("filter_and_provice_EQ_S");
			String city = HttpTool.getParameter("filter_and_city_EQ_S");
			String branchCompany = HttpTool.getParameter("filter_and_branchCompany_EQ_S");
			String branchCompanyId = HttpTool.getParameter("filter_and_branchCompanyId_EQ_S");
			String configId = HttpTool.getParameter("configId" , "") ;
			String _configId = configId;
			String field = HttpTool.getParameter("field" , "") ;
			
			//第一次传值
			if(StringUtils.isNotEmpty(province)){
				searchMap.put("filter_and_provice_EQ_S", province);
				HttpTool.setAttribute("filter_and_provice_EQ_S", province);
			}
			if(StringUtils.isNotEmpty(city)){
				searchMap.put("filter_and_city_EQ_S", city);
				HttpTool.setAttribute("filter_and_city_EQ_S", city);
			}
			if(StringUtils.isNotEmpty(branchCompany)){
				searchMap.put("filter_and_branchCompany_EQ_S", branchCompany);
				HttpTool.setAttribute("filter_and_branchCompany_EQ_S", branchCompany);
			}
			if(StringUtils.isNotEmpty(branchCompanyId)){
				searchMap.put("filter_and_branchCompanyId_EQ_S", branchCompanyId);
				HttpTool.setAttribute("filter_and_branchCompanyId_EQ_S", branchCompanyId);
			}
						
			if(StringUtils.isNotEmpty(configId)){
				HttpTool.setAttribute("configId", configId);
			}
			
			if(StringUtils.isNotEmpty(field)){
				HttpTool.setAttribute("field", field);
			}
			searchMap.put("filter_and_isDeleted_EQ_I", 0);
	        int pageNum = HttpTool.getPageNum() ;
	        int pageSize = HttpTool.getPageSize() ;
	        
	        page = new Page(pageNum , pageSize) ;
	        
	    	searchMap.put("filter_and_isDeleted_EQ_I", 0);
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			//queryResults(page , _configId , searchMap);
			findBySearchDoPage(page, _configId, field, buildSearch()) ;
			
	        return "list"+configId+"_s";
	    }
	    catch (Exception e) {
	     e.printStackTrace();
	      return "testStat";
	    }
	  }
	
	/*  旧的performSearchDo方法		mark DengYouming 2016-06-02 
	 * 	public String performSearchDo() {
	    try {
	    	HttpServletRequest request = ServletActionContext.getRequest() ;
	    	
	    	String configId = request.getParameter("configId");
	        String field=StaticMethod.nullObject2String(request.getParameter("field"));
	        String type = HttpTool.getParameter("filter_and_type_EQ_S");
	        String _configId = "";
	        if(StrUtils.isNotNullOrBlank(type)){
	        	_configId = configId+"-"+type;
	        }else{
	        	_configId = configId;
	        }
	        int pageNum = HttpTool.getPageNum() ;
	        int pageSize = HttpTool.getPageSize() ;
	        
	        page = new Page(pageNum , pageSize) ;
	        
	        findBySearchDoPage(page, _configId, field, buildSearch()) ;
	        
	        HttpTool.setAttribute("page", page);
	        
	        HttpTool.setAttribute("field", field);
	        HttpTool.setAttribute("configId", configId);
	        
	       request.setAttribute("BASEINFOLIST", page.getResults());
	        return "list"+configId+"_s";
	    }
	    catch (Exception e) {
	     e.printStackTrace();
	      return "testStat";
	    }
	  }
	 */
	
	public String queryReport() throws Exception{
		Map actionMap = new HashMap() ;
		/*String createStartTime = HttpTool.getParameter("filter_and_createTime_GE_T");
		String createEndTime = HttpTool.getParameter("filter_and_createTime_LE_T");*/
		String ss="";
		User user = (User) HttpTool.getSession().getAttribute("currentUser");

		if(!StringUtils.isBlank(user.getCtiPassword())){
		  String productIds= user.getCtiPassword().trim();
		  if(productIds.endsWith(",")){
			  productIds=productIds.substring(0,productIds.length()-1);
		  }
		  
		  String[] products = productIds.split(",");
		  if(products.length>0){
			  for(int i = 0;i<products.length;i++){
				  ss=ss+"'"+products[i]+"',";
			  }
			  ss=ss.substring(0,ss.length()-1);
		  }
		}
		
		Map paramsMap = buildSearch() ;
		paramsMap.put("filter_and_productId_EQ_S", ss);
		
		String configId = HttpTool.getParameter("configId" , "") ;
		String _configId = configId;
		String flag = "0";
		
		//2016-12-21 add by tx. 125/126报表. 根据当前登录用户获取其省份城市信息，用作查询条件。
		if(_configId.equals("stat125")||_configId.equals("stat126")||_configId.equals("stat138")||_configId.equals("stat151")||_configId.equals("stat155")||_configId.equals("stat156")||_configId.equals("stat166")){
			String provice="";
			String city="";
			String deptId="";
			String projectcode="";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					provice = userObj.getProvice();
					city = userObj.getCity();
					deptId=userObj.getDeptId();
					projectcode=userObj.getProjectCode();
				}
			}
			if(StringUtils.isEmpty(city)&&StringUtils.isEmpty(provice)&&StringUtils.isEmpty(deptId)&&StringUtils.isEmpty(projectcode)){	//两个都为null，不查询数据
				provice = "isNull";
				city = "isNull";
				deptId="isNull";
				projectcode="isNull";
			}
			paramsMap.put("filter_and_provice_EQ_S",provice);
			paramsMap.put("filter_and_city_EQ_S",city);
			paramsMap.put("filter_and_deptId_EQ_S",deptId);
			paramsMap.put("filter_and_projectcode_like_S", projectcode);
		}else if(_configId.equals("stat128")||_configId.equals("stat159")){	//2017-1-11 add by LeslieTong. 统计报表-销售部报表-客户查询（新）project_owner='??'--取当前登录用户的名字
			String userName = user.getUserName();
			if(StringUtils.isNotEmpty(userName)){
				paramsMap.put("filter_and_projectOwner_EQ_S",userName);
			}
		}else if (_configId.equals("stat130")){	//2017-1-19 add by LeslieTong.  统计报表- 保险公司报表- 预约客户统计 t.owned_company_id and r.id 字段是根据当前登录用户查找
			String regionId="";
			String deptId="";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					deptId = userObj.getDeptId();
					regionId = userObj.getProvice();
				}
			}
			paramsMap.put("filter_and_regionId_EQ_S",regionId);
			paramsMap.put("filter_and_deptId_EQ_S",deptId);
		}else if (_configId.equals("stat123")||_configId.equals("stat124")||_configId.equals("stat153")||_configId.equals("stat154")){	//2017-2-15 add by wuhao.  统计报表- 保险公司报表- 无创项目体检客户信息 pr.project_code and e.owned_company_id and e.province 字段是根据当前登录用户查找
			String provice="";
			String deptId="";
			String projectcode="";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					deptId = userObj.getDeptId();
					provice = userObj.getProvice();
					projectcode=userObj.getProjectCode();
				}
			}
			if(StringUtils.isEmpty(provice)&&StringUtils.isEmpty(deptId)&&StringUtils.isEmpty(projectcode)){	//两个都为null，不查询数据
				provice = "isNull";
				deptId="isNull";
				projectcode="isNull";
			}
			paramsMap.put("filter_and_provice_EQ_S",provice);
			paramsMap.put("filter_and_deptId_EQ_S",deptId);
			paramsMap.put("filter_and_projectcode_like_S", projectcode);
		}else if (_configId.equals("stat145")||_configId.equals("stat146")||_configId.equals("stat157")||_configId.equals("stat158")||_configId.equals("stat152")||_configId.equals("stat167")||_configId.equals("stat168")||_configId.equals("stat169")){	//2017-2-15 add by wuhao.  统计报表- 保险公司报表- (支公司)无创项目体检客户信息 、(支公司)邀约客户信息pr.project_code and e.owned_company_id and pr.customer_relationship_id 字段是根据当前登录用户查找
			String jobnumber="";
			String deptId="";
			String projectcode="";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					deptId = userObj.getDeptId();
					jobnumber = userObj.getJobNumber();
					projectcode=userObj.getProjectCode();
				}
			}
			if(StringUtils.isEmpty(jobnumber)&&StringUtils.isEmpty(deptId)&&StringUtils.isEmpty(projectcode)){	//两个都为null，不查询数据
				jobnumber = "isNull";
				deptId="isNull";
				projectcode="isNull";
			}
			paramsMap.put("filter_and_jobnumber_EQ_S",jobnumber);
			paramsMap.put("filter_and_deptId_EQ_S",deptId);
			paramsMap.put("filter_and_projectcode_like_S", projectcode);
		}else if (_configId.equals("stat150")){	//2017-1-19 add by LeslieTong.  统计报表- 保险公司报表- 预约客户统计 t.owned_company_id and r.id 字段是根据当前登录用户查找
			String projectcode="";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					projectcode = userObj.getProjectCode();		
				}
			}
			paramsMap.put("filter_and_projectcode_like_S",projectcode);			
		}
		
		if ("stat33".equals(_configId)) {
			if("2".equals(paramsMap.get("filter_and_hylType_EQ_S"))) {
				_configId = "stat33-2";
			}
			else if("3".equals(paramsMap.get("filter_and_hylType_EQ_S"))) {
				_configId = "stat33-3";
			}
			else if("4".equals(paramsMap.get("filter_and_hylType_EQ_S"))) {
				_configId = "stat33-4";
			}
			else _configId = "stat33-1";
			flag = "1";
			page = new Page(1 , 12);
		}else if ("stat54".equals(_configId)) {
			if("1".equals(paramsMap.get("filter_and_type_EQ_S"))) {
				_configId = "stat54-1";
			}
			else if("2".equals(paramsMap.get("filter_and_type_EQ_S"))) {
				_configId = "stat54-2";
			}
			else _configId = "stat54-3";
			flag = "3";
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize()) ;
		}else if ("stat84".equals(_configId)) {
			flag = "1";
			page = new Page(HttpTool.getPageNum() , 100);
		}else if("stat44".equals(_configId)||"stat45".equals(_configId)||"stat46".equals(_configId)||"stat47".equals(_configId)||"stat48".equals(_configId)||"stat87".equals(_configId)){
			page = new Page(HttpTool.getPageNum() , 100);
		}else {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize()) ;
		}
		queryResults(page , _configId , paramsMap);
		List resultList = page.getResults() ;
		
		String xml = "" ;
		int defaultCount = StaticMethod.nullObject2int(SystemConstant.getSystemConstant("default_count_of_no_show_chart")) ;
		if(defaultCount >= page.getTotalCount()){
			if(resultList != null && resultList.size() > 0 && "0".equals(flag)){

				switch(ReportType.getReportTypeByName(_configId)) {
					case stat21 :
						xml = getDataXmlStat21(page , _configId , paramsMap) ;
						break ;
					case stat22 :
						xml = getDataXmlStat22(page , _configId , paramsMap) ;
						break ;
					case stat31 :
						xml = getDataXmlStat31(page , _configId , paramsMap) ;				
						break ;
					case stat32 :
						xml = getDataXmlStat32(page , _configId , paramsMap) ;
						break ;
					case stat33 :
						xml = getDataXmlStat33(page , _configId , paramsMap) ;
						break ;
					case stat34 :
						
						break ;
					case stat35 :
						xml = getDataXmlStat35(page , _configId , paramsMap) ;
						break ;
					case stat36 :
						
						break ;
					case stat37 :
						xml = getDataXmlStat37(page , _configId , paramsMap) ;
						break ;
					case stat38 :
						xml = getDataXmlStat38(page , _configId , paramsMap) ;
						break ;
					case stat39 :
						xml = getDataXmlStat39(page , _configId , paramsMap) ;
						break ;
					case stat310 :
						xml = getDataXmlStat310(page , _configId , paramsMap) ;
						break ;
					case stat41 :
						xml = getDataXmlStat41(page , _configId , paramsMap) ;
						
						break ;
					case stat42 :
						xml = getDataXmlStat42(page , _configId , paramsMap) ;
						break ;
					case stat43 :
						xml = getDataXmlStat43(page , _configId , paramsMap) ;
						break ;
					case stat44 :
						xml = getDataXmlStat44(page , _configId , paramsMap) ;
						break ;
					case stat45 :
						xml = getDataXmlStat45(page , _configId , paramsMap) ;
						break ;
					case stat46 :
						xml = getDataXmlStat46(page , _configId , paramsMap) ;
						break ;
					case stat47 :
						xml = getDataXmlStat47(page , _configId , paramsMap) ;
						break ;
					case stat48 :
						xml = getDataXmlStat48(page , _configId , paramsMap) ;
						break ;
					case stat49 :
						break ;
					case stat410 :
						
						break ;
					case stat51 :
						xml = getDataXmlStat51(page , _configId , paramsMap) ;
						break ;
					case stat52 :
						xml = getDataXmlStat52(page , _configId , paramsMap) ;
						break ;
					case stat53 :
						xml = getDataXmlStat53(page , _configId , paramsMap) ;
						break ;
					case stat54 :
						xml = getDataXmlStat54(page , _configId , paramsMap) ;
						break ;
					case stat55 :
						xml = getDataXmlStat55(page , _configId , paramsMap) ;
						break ;
					case stat56 :
						
						break ;
					case stat61 :
						xml = getDataXmlStat61(page , _configId , paramsMap) ;
						break ;
					case stat62 :
						xml = getDataXmlStat62(page , _configId , paramsMap) ;
						break ;
					case stat63 :
						
						break ;
					case stat71 :
						xml = getDataXmlStat71(page , _configId , paramsMap) ;
						break ;
					case stat72 :
						xml = getDataXmlStat72(page , _configId , paramsMap) ;
						break ;
					case stat73 :
						xml = getDataXmlStat73(page , _configId , paramsMap) ;
						break ;
					case stat74 :
						xml = getDataXmlStat74(page , _configId , paramsMap) ;
						break ;
					case stat75 :
						xml = getDataXmlStat75(page , _configId , paramsMap) ;
						break ;
					case stat76 :
						
						break ;
					case stat81 :
						xml = getDataXmlStat81(page , _configId , paramsMap) ;
						break ;
					case stat82 :
						xml = getDataXmlStat82(page , _configId , paramsMap) ;
						break ;
					case stat83 :
						xml = getDataXmlStat83(page , _configId , paramsMap) ;
						break ;
					case stat84 :
						xml = getDataXmlStat84(page , _configId , paramsMap) ;			
						break ;
					case stat85 :
						xml = getDataXmlStat85(page , _configId , paramsMap) ;
						break ;
					case stat86 :
						xml = getDataXmlStat86(page , _configId , paramsMap) ;
						break ;
					case stat87 :
						xml = getDataXmlStat87(page , _configId , paramsMap) ;
						break ;
					case stat91 :
						
						break ;
					case stat92 :
						xml = getDataXmlStat92(page , _configId , paramsMap) ;
						break ;
					case stat100 :
						xml = getDataXmlStat100(page , _configId , paramsMap) ;
						break ;
					case stat141 :
						xml = getDataXmlStat141(page , _configId , paramsMap) ;
						break ;
				}
			}
		}
		actionMap.put("configId" , configId);
		actionMap.put("dataXml" , xml) ;
		actionMap.put("resultList" , resultList) ;
		actionMap.putAll(paramsMap) ;
		HttpTool.getSession().setAttribute("actionMap" , actionMap) ;
		/*
		if(!StringUtils.isEmpty(createStartTime)){
			HttpTool.setAttribute("filter_and_createTime_GE_T", createStartTime);
		}
		if(!StringUtils.isEmpty(createEndTime)){
			HttpTool.setAttribute("filter_and_createTime_LE_T", createEndTime);
		}*/
		
		return "list" + configId ;
	}
	


	public enum ReportType{
		stat21 , stat22 , 
		stat31 , stat32 , stat33 , stat34 , stat35 , stat36 , stat37 , stat38 , stat39 , stat310 ,
		stat41 , stat42 , stat43 , stat44 , stat45 , stat46 , stat47 , stat48 , stat49 , stat410 ,
		stat51 , stat52 , stat53 , stat54 , stat55 , stat56 , 
		stat61 , stat62 , stat63 , stat64 , stat65 , 
		stat71 , stat72 , stat73 , stat74 , stat75 , stat76 ,
		stat81 , stat82 , stat83 , stat84 , stat85 , stat86 , stat87 ,
		stat91 , stat92 , stat100, stat141,
		stat101 , stat102 , stat103, stat107,stat120,stat121;
		
		public static ReportType getReportTypeByName(String name){
			return ReportType.valueOf(name) ;
 		}
	}
	
	public Page queryResults(Page page , String configId , Map paramsMap) throws Exception{
		String _configId = HttpTool.getParameter("configId" , "") ;
		String sql = "" ;
		String params = "" ;
		String paramsFlag = "" ;
		Map sqlParams = new HashMap() ;
		Map actionMap = new HashMap() ;
		String ss="";
		User user = (User) HttpTool.getSession().getAttribute("currentUser");
		System.out.println(user.getCtiPassword());
		if(!StringUtils.isBlank(user.getCtiPassword())){
		  String productIds= user.getCtiPassword().trim();
		  if(productIds.endsWith(",")){
			  productIds=productIds.substring(0,productIds.length()-1);
		  }
		  String[] products = productIds.split(",");
		  if(products.length>0){
			  for(int i = 0;i<products.length;i++){
				  ss=ss+"'"+products[i]+"',";
			  }
			  ss=ss.substring(0,ss.length()-1);
		  }
		}
		
		//2016-12-27 add by tx. 125/126报表. 根据当前登录用户获取其省份城市信息，用作查询条件。（导出excel）
		if(_configId.equals("stat125")||_configId.equals("stat126")||_configId.equals("stat138")||_configId.equals("stat151")||_configId.equals("stat155")||_configId.equals("stat156")||_configId.equals("stat166")){
			String provice="";
			String city="";
			String deptId="";
			String projectcode="";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					provice = userObj.getProvice();
					city = userObj.getCity();
					deptId=userObj.getDeptId();
					projectcode=userObj.getProjectCode();
				}
			}
			if(StringUtils.isEmpty(city)&&StringUtils.isEmpty(provice)&&StringUtils.isEmpty(deptId)&&StringUtils.isEmpty(projectcode)){	//两个都为null，不查询数据
				provice = "isNull";
				city = "isNull";
				deptId="isNull";
				projectcode="isNull";
			}
			paramsMap.put("filter_and_provice_EQ_S",provice);
			paramsMap.put("filter_and_city_EQ_S",city);
			paramsMap.put("filter_and_deptId_EQ_S",deptId);
			paramsMap.put("filter_and_projectcode_like_S", projectcode);
		}else if(_configId.equals("stat128")||_configId.equals("stat159")){	//2017-1-11 add by LeslieTong. 统计报表-销售部报表-客户查询（新）project_owner='??'--取当前登录用户的名字
			String userName = user.getUserName();
			if(StringUtils.isNotEmpty(userName)){
				paramsMap.put("filter_and_projectOwner_EQ_S",userName);
			}
		}else if (_configId.equals("stat130")){	//2017-1-19 add by LeslieTong.  统计报表- 保险公司报表- 预约客户统计 t.owned_company_id and r.id 字段是根据当前登录用户查找
			String regionId="";
			String deptId="";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					deptId = userObj.getDeptId();
					regionId = userObj.getProvice();
				}
			}
			paramsMap.put("filter_and_regionId_EQ_S",regionId);
			paramsMap.put("filter_and_deptId_EQ_S",deptId);
		}else if (_configId.equals("stat143")){
			String provice="";
			String deptId="";
			if (user!=null) {
				String userId=user.getId();
				User userObj=queryService.getUserByUserId(userId);
				if (userObj!=null) {
					provice=userObj.getProvice();
					deptId=userObj.getDeptId();
				}				
			}
			if(StringUtils.isEmpty(deptId)&&StringUtils.isEmpty(provice)){	//两个都为null，不查询数据
				provice = "isNull";
				deptId = "isNull";
			}
			String createTimeDate = HttpTool.getParameter("filter_and_createTime_GEST_T" , "");
			if(StringUtils.isEmpty(createTimeDate)){
				Date createTime = new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				paramsMap.put("filter_and_createTime_GEST_T",sdf.format(createTime));
			}
			paramsMap.put("filter_and_provice_EQ_S",provice);
			paramsMap.put("filter_and_deptId_EQ_S",deptId);			
		}else if (_configId.equals("stat123")||_configId.equals("stat124")||_configId.equals("stat153")||_configId.equals("stat154")){	//2017-2-15 add by wuhao.  统计报表- 保险公司报表- 无创项目体检客户信息 pr.project_code and e.owned_company_id and e.province 字段是根据当前登录用户查找
			String provice="";
			String deptId="";
			String projectcode="";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					deptId = userObj.getDeptId();
					provice = userObj.getProvice();
					projectcode=userObj.getProjectCode();
				}
			}
			if(StringUtils.isEmpty(provice)&&StringUtils.isEmpty(deptId)&&StringUtils.isEmpty(projectcode)){	//两个都为null，不查询数据
				provice = "isNull";
				deptId="isNull";
				projectcode="isNull";
			}
			paramsMap.put("filter_and_provice_EQ_S",provice);
			paramsMap.put("filter_and_deptId_EQ_S",deptId);
			paramsMap.put("filter_and_projectcode_like_S", projectcode);
		}else if (_configId.equals("stat145")||_configId.equals("stat146")||_configId.equals("stat152")||_configId.equals("stat157")||_configId.equals("stat158")||_configId.equals("stat167")||_configId.equals("stat168")||_configId.equals("stat169")){	//2017-2-15 add by wuhao.   pr.project_code and e.owned_company_id and pr.customer_relationship_id 字段是根据当前登录用户查找
			String jobnumber="";
			String deptId="";
			String projectcode="";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					deptId = userObj.getDeptId();
					jobnumber = userObj.getJobNumber();
					projectcode=userObj.getProjectCode();
				}
			}
			if(StringUtils.isEmpty(jobnumber)&&StringUtils.isEmpty(deptId)&&StringUtils.isEmpty(projectcode)){	//两个都为null，不查询数据
				jobnumber = "isNull";
				deptId="isNull";
				projectcode="isNull";
			}
			paramsMap.put("filter_and_jobnumber_EQ_S",jobnumber);
			paramsMap.put("filter_and_deptId_EQ_S",deptId);
			paramsMap.put("filter_and_projectcode_like_S", projectcode);
		}else if (_configId.equals("stat150")){	//2017-1-19 add by LeslieTong.  统计报表- 保险公司报表- 预约客户统计 t.owned_company_id and r.id 字段是根据当前登录用户查找
			String projectcode="";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					projectcode = userObj.getProjectCode();		
				}
			}
			paramsMap.put("filter_and_projectcode_like_S",projectcode);			
		} else if("stat165".equals(_configId)) { //create by henry.xu 20170401  统计报表-保险公司报表-省公司场次查询
			//获取当前登陆人信息;
			//获取projectCode
			String projectCode = "";
			//获取登录人省份;
			String province = "";
			if(user!=null){
				String userId = user.getId();
				User userObj = queryService.getUserByUserId(userId);
				if(userObj!=null){
					projectCode = userObj.getProjectCode();
					if(projectCode != null && !"".equals(projectCode)) {
						paramsMap.put("filter_and_projectCode_EQ_S", projectCode);
						
					}
					province = userObj.getProvice();
					if(province != null && !"".equals(province)) {
						paramsMap.put("filter_and_province_EQ_S", province);
						
					}
				}
			}
			
		} else if("stat170".equals(_configId) || "stat171".equals(_configId)) { //create by henry.xu 20170407 求和处理;
			countSum = this.queryService.queryCountSum(_configId, HttpTool.getParameter("filter_and_createTime_GE_T" , ""));
		}
		/*else if (_configId.equals("stat147")){

			String createTimeDate_GE = HttpTool.getParameter("filter_and_createTime_GE_T" , "");
			String createTimeDate_LE = HttpTool.getParameter("filter_and_createTime_LE_T" , "");
			if(StringUtils.isEmpty(createTimeDate_GE)&&StringUtils.isEmpty(createTimeDate_LE)){
				Date createTime_LE = new Date();
				Date createTime_GE = new Date();
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(createTime_LE);
				calendar.add(calendar.DAY_OF_MONTH,-1);
				createTime_GE=calendar.getTime();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				paramsMap.put("filter_and_createTime_GE_T",sdf.format(createTime_GE));
				paramsMap.put("filter_and_createTime_LE_T",sdf.format(createTime_LE));
			}else if (StringUtils.isEmpty(createTimeDate_GE)&&StringUtils.isNotEmpty(createTimeDate_LE)) {
				Date createTime_GE = new Date();
				Date createTime_LE = new Date();
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(createTime_LE);
				calendar.add(calendar.DAY_OF_MONTH,-1);
				createTime_GE=calendar.getTime();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				paramsMap.put("filter_and_createTime_GE_T",sdf.format(createTime_GE));
			}else if (StringUtils.isNotEmpty(createTimeDate_GE)&&StringUtils.isEmpty(createTimeDate_LE)) {
				Date createTime_LE = new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				paramsMap.put("filter_and_createTime_LE_T",sdf.format(createTime_LE));
			}
						
		}*/
		/*else if (_configId.equals("stat142")){
			
			String month = HttpTool.getParameter("filter_and_month_like_S" , "");
			if(StringUtils.isEmpty(month)){
				Date monthDate = new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
				paramsMap.put("filter_and_month_like_S",sdf.format(monthDate));
			}
					
		}	*/	
		paramsMap.put("filter_and_productId_EQ_S", ss);
		actionMap.putAll(paramsMap) ;
		
		CreateXmlBO createHtmlBo = new CreateXmlBO(ServletActionContext.getRequest().getRealPath("/")) ;
		
		Map map = createHtmlBo.GetStatXml(configId) ;
		sql = StaticMethod.nullObject2String(map.get("sql")).trim() ;
		params = StaticMethod.nullObject2String(map.get("params")).trim() ;
		paramsFlag = StaticMethod.nullObject2String(map.get("paramsFlag")).trim() ;
		sqlParams = createHtmlBo.getParams(sqlParams , params , paramsFlag , actionMap) ;
		sql = StatUtil.getRepString(sql , sqlParams , "@") ;
		List resultList = new ArrayList() ;
		if(StringUtils.isNotBlank(sql)){
			queryService.findBySqlPage(page , sql , null) ;
		}
		return page ;
	}
	
	/**
	 * 基因报告及金额等信息的汇总(业务)
	 * @return
	 * @author tangxing
	 * @date 2016-8-30下午2:54:12
	 */
	@SuppressWarnings("unchecked")
	public String queryGeneReportSummary(){
		//Map actionMap = new HashMap() ;
		Map paramsMap = super.buildSearch() ;
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize()) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		queryService.findByPage(page, paramsMap);
		String xml = "";
//		actionMap.put("configId" , configId) ;
//		actionMap.put("dataXml" , xml) ;
//		actionMap.put("resultList" , resultList) ;
//		HttpTool.setAttribute("showWarn", "false");
//		actionMap.putAll(paramsMap) ;
		//page.setResults(resultList);
		//HttpTool.getSession().setAttribute("actionMap" , actionMap) ;
		return "geneReportSummary";
		
	} 
	
	/**
	 * 因报告及金额等信息的汇总(财务)
	 * @return
	 * @author tangxing
	 * @date 2016-9-21下午6:06:04
	 */
	@SuppressWarnings("unchecked")
	public String queryGeneReportSummaryFinance(){
		//Map actionMap = new HashMap() ;
		/*Map paramsMap = super.buildSearch() ;
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize()) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		queryService.findByPageFinance(page, paramsMap);
		String xml = "";*/
//		actionMap.put("configId" , configId) ;
//		actionMap.put("dataXml" , xml) ;
//		actionMap.put("resultList" , resultList) ;
//		HttpTool.setAttribute("showWarn", "false");
//		actionMap.putAll(paramsMap) ;
		//page.setResults(resultList);
		//HttpTool.getSession().setAttribute("actionMap" , actionMap) ;
		
		HttpTool.getSession().removeAttribute("actionMap") ;
		Map actionMap = new HashMap() ;
		Map paramsMap = buildSearch() ;
		String configId = HttpTool.getParameter("configId" , "") ;
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize()) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List resultList = page.getResults() ;
		String xml = "";
		actionMap.put("configId" , configId) ;
		actionMap.put("dataXml" , xml) ;
		actionMap.put("resultList" , resultList) ;
		HttpTool.setAttribute("showWarn", "false");
		actionMap.putAll(paramsMap) ;
		HttpTool.getSession().setAttribute("actionMap" , actionMap) ;
		return "geneReportSummaryFinance";
		
	} 
	
	public void createExSeFilePath(){
		JSONObject json = new JSONObject();
		List<GeneReportSummary> geneReportSummaries = new ArrayList<GeneReportSummary>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = HttpTool.getParameter("ids");
		
		if(ids.indexOf(",")==-1){	//单个
			List<GeneReportSummary> geneReportSummarieTemp = queryService.getGeneReportSummaryById(ids);
			if(geneReportSummarieTemp!=null&&geneReportSummarieTemp.size()>0){
				geneReportSummaries.add(geneReportSummarieTemp.get(0));
			}
		}else{						//多个
			String strArr[] = ids.replace(" ", "").split(",");
			for (int i = 0; i < strArr.length; i++) {
				String id = strArr[i];
				List<GeneReportSummary> geneReportSummarieTemp = queryService.getGeneReportSummaryById(id);
				if(geneReportSummarieTemp!=null&&geneReportSummarieTemp.size()>0){
					geneReportSummaries.add(geneReportSummarieTemp.get(0));
				}
			}
		}
		
		String excelPath= queryService.createExSeFilePath(request, geneReportSummaries);
		System.out.println("路径-------"+excelPath);
		json.put("path",excelPath);
		
		renderJson(json);
	}
	
	public void createExSeFilePathTwo(){
		JSONObject json = new JSONObject();
		List<GeneReportSummaryFinance> geneReportSummarieFinances = new ArrayList<GeneReportSummaryFinance>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = HttpTool.getParameter("ids");
		
		if(ids.indexOf(",")==-1){	//单个
			List<GeneReportSummaryFinance> geneReportSummarieFinanceTemp = queryService.getGeneReportSummaryFinanceById(ids);
			if(geneReportSummarieFinanceTemp!=null&&geneReportSummarieFinanceTemp.size()>0){
				geneReportSummarieFinances.add(geneReportSummarieFinanceTemp.get(0));
			}
		}else{						//多个
			String strArr[] = ids.replace(" ", "").split(",");
			for (int i = 0; i < strArr.length; i++) {
				String id = strArr[i];
				List<GeneReportSummaryFinance> geneReportSummarieFinanceTemp = queryService.getGeneReportSummaryFinanceById(id);
				if(geneReportSummarieFinanceTemp!=null&&geneReportSummarieFinanceTemp.size()>0){
					geneReportSummarieFinances.add(geneReportSummarieFinanceTemp.get(0));
				}
			}
		}
		
		String excelPath= queryService.createExSeFilePathTwo(request, geneReportSummarieFinances);
		System.out.println("路径-------"+excelPath);
		json.put("path",excelPath);
		
		renderJson(json);
	}
	
	@SuppressWarnings("unchecked")
	public String queryTotal() throws Exception{
		HttpTool.getSession().removeAttribute("actionMap") ;
		Map actionMap = new HashMap() ;
		Map paramsMap = buildSearch() ;
		String configId = HttpTool.getParameter("configId" , "") ;
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize()) ;
		List resultList = page.getResults() ;
		String xml = "";
		actionMap.put("configId" , configId) ;
		actionMap.put("dataXml" , xml) ;
		actionMap.put("resultList" , resultList) ;
		HttpTool.setAttribute("showWarn", "false");
		actionMap.putAll(paramsMap) ;
		HttpTool.getSession().setAttribute("actionMap" , actionMap) ;
		return "list"+configId;
	}

	public String getDataXmlStat21(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + objs[3] + "' />");
    		sbs[1].append("<set value='" + objs[5] + "' />");
    		sbs[2].append("<set value='" + objs[6] + "' />");
    		sbs[3].append("<set value='" + objs[7] + "' />");
    		sbs[4].append("<set value='" + objs[8] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='新增'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='更新'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='续服'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='退服'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	
    	return sb.toString();
	}
	public String getDataXmlStat100(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='数量'>").append(sbs[1].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	
    	return sb.toString();
	}
	
	public String getDataXmlStat41(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='合约' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='业务类型统计合约性质' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    		sbs[3].append("<set value='" + objs[3] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='销售'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='采购'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='框架'>").append(sbs[3].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	public String getDataXmlStat42(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='合约' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='合约销售类型（1）(业务类型)' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    		sbs[3].append("<set value='" + objs[3] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='保险'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='非保险'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='TPA'>").append(sbs[3].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat43(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[13] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='合约' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='合约销售类型2按业务类型统计' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    		sbs[3].append("<set value='" + objs[3] + "' />");
    		sbs[4].append("<set value='" + objs[4] + "' />");
    		sbs[5].append("<set value='" + objs[5] + "' />");
    		sbs[6].append("<set value='" + objs[6] + "' />");
    		sbs[7].append("<set value='" + objs[7] + "' />");
    		sbs[8].append("<set value='" + objs[8] + "' />");
    		sbs[9].append("<set value='" + objs[9] + "' />");
    		sbs[10].append("<set value='" + objs[10] + "' />");
    		sbs[11].append("<set value='" + objs[11] + "' />");
    		sbs[12].append("<set value='" + objs[12] + "' />");
    
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='寿团险'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='寿个险'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='财险'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='健康险'>").append(sbs[4].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='旅游险'>").append(sbs[5].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='汽车行业'>").append(sbs[6].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='企业'>").append(sbs[7].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='金融'>").append(sbs[8].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='代理'>").append(sbs[9].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='个人'>").append(sbs[10].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='其他'>").append(sbs[11].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='合计'>").append(sbs[12].toString()).append("</dataset>");
    	
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat44(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='合约' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='统计合约性质' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[2] + "' />");
    		sbs[2].append("<set value='" + objs[3] + "' />");
    		sbs[3].append("<set value='" + objs[4] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='销售'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='框架'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='采购'>").append(sbs[3].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat45(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[6] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='合约' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='统计合约销售类型1（销售组别）' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    		sbs[3].append("<set value='" + objs[3] + "' />");
    		sbs[4].append("<set value='" + objs[4] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='保险'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='非保险'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='TPA'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat46(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[10] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='合约' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='统计合约业务类型（销售组别）' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    		sbs[3].append("<set value='" + objs[3] + "' />");
    		sbs[4].append("<set value='" + objs[4] + "' />");
    		sbs[5].append("<set value='" + objs[5] + "' />");
    		sbs[6].append("<set value='" + objs[6] + "' />");
    		sbs[7].append("<set value='" + objs[7] + "' />");
    		sbs[8].append("<set value='" + objs[8] + "' />");
    		sbs[9].append("<set value='" + objs[9] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='TPA'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='车险'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='旅游'>").append(sbs[4].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='其他'>").append(sbs[5].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='其他保险'>").append(sbs[6].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='企业'>").append(sbs[7].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='个人'>").append(sbs[8].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='合计'>").append(sbs[9].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat47(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='合约' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='合约状态统计（销售组别）' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    		sbs[3].append("<set value='" + objs[3] + "' />");
    		sbs[4].append("<set value='" + objs[4] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='有效'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='关闭'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='过期'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat48(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='合约' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='合约签约类型统计' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    		sbs[3].append("<set value='" + objs[3] + "' />");
    		sbs[4].append("<set value='" + objs[4] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='续月量'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='新签量'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='关闭（到期不合作）'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat22(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer();
    	StringBuffer sb0 = new StringBuffer();
    	StringBuffer sb1= new StringBuffer();
    	StringBuffer sb2= new StringBuffer();
    	sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员数据采集' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='"+obj[0]+"' />");
    		sb1.append("<set value='"+String.valueOf(obj[0])+"' />");
    		sb2.append("<set value='"+String.valueOf(obj[1])+"' />");
    	}
    	sb.append("</categories>");
    	sb0.append("<dataset seriesname='新增'>").append(sb1.toString()).append("</dataset>");
    	sb0.append("<dataset seriesname='更新'>").append(sb2.toString()).append("</dataset>");
    	sb.append(sb0.toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	public String getDataXmlStat310(Page page, String configId, Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[19] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='产品展示统计' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='"+obj[0]+"' />");
    		sbs[0].append("<set value='"+String.valueOf(obj[1])+"' />") ; 
    		sbs[1].append("<set value='"+String.valueOf(obj[2])+"' />"); ;
    		sbs[2].append("<set value='"+String.valueOf(obj[3])+"' />"); ;
    		sbs[3].append("<set value='"+String.valueOf(obj[4])+"' />"); ;
    		sbs[4].append("<set value='"+String.valueOf(obj[5])+"' />"); ;
    		sbs[5].append("<set value='"+String.valueOf(obj[6])+"' />"); ;
    		sbs[6].append("<set value='"+String.valueOf(obj[7])+"' />"); ;
    		sbs[7].append("<set value='"+String.valueOf(obj[8])+"' />"); ;
    		sbs[8].append("<set value='"+String.valueOf(obj[9])+"' />"); ;
    		sbs[9].append("<set value='"+String.valueOf(obj[10])+"' />"); ;
    		sbs[10].append("<set value='"+String.valueOf(obj[11])+"' />"); ;
    		sbs[11].append("<set value='"+String.valueOf(obj[12])+"' />"); ;
    		sbs[12].append("<set value='"+String.valueOf(obj[13])+"' />"); ;
    		sbs[13].append("<set value='"+String.valueOf(obj[14])+"' />"); ;
    		sbs[14].append("<set value='"+String.valueOf(obj[15])+"' />"); ;
    		sbs[15].append("<set value='"+String.valueOf(obj[15])+"' />"); ;
    		sbs[16].append("<set value='"+String.valueOf(obj[15])+"' />"); ;
    		sbs[17].append("<set value='"+String.valueOf(obj[15])+"' />"); ;
    		sbs[18].append("<set value='"+String.valueOf(obj[15])+"' />"); ;
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='产品会员量'>").append(sbs[17].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='产品有效会员量'>").append(sbs[18].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	public String getDataXmlStat31(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer();
    	StringBuffer[] sbs = new StringBuffer[15] ;
    	for(int i = 0 ; i < sbs.length ; i ++){
    		sbs[i] = new StringBuffer() ;
    	}
    	sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员信息统计' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='"+obj[1]+"' />");
    		sbs[0].append("<set value='"+String.valueOf(obj[1])+"' />"); 
    		sbs[1].append("<set value='"+String.valueOf(obj[2])+"' />");
    		sbs[2].append("<set value='"+String.valueOf(obj[3])+"' />");
    		sbs[3].append("<set value='"+String.valueOf(obj[4])+"' />");
    		sbs[4].append("<set value='"+String.valueOf(obj[5])+"' />");
    		sbs[5].append("<set value='"+String.valueOf(obj[6])+"' />");
    		sbs[6].append("<set value='"+String.valueOf(obj[7])+"' />");
    		sbs[7].append("<set value='"+String.valueOf(obj[8])+"' />");
    		sbs[8].append("<set value='"+String.valueOf(obj[9])+"' />");
    		sbs[9].append("<set value='"+String.valueOf(obj[10])+"' />");
    		sbs[10].append("<set value='"+String.valueOf(obj[11])+"' />");
    		sbs[11].append("<set value='"+String.valueOf(obj[12])+"' />");
    		sbs[12].append("<set value='"+String.valueOf(obj[13])+"' />");
    		sbs[13].append("<set value='"+String.valueOf(obj[14])+"' />");
    		sbs[14].append("<set value='"+String.valueOf(obj[15])+"' />"); ;
    	}
    	sb.append("</categories>");
    	sb.append("<dataset>").append(sbs[0].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[1].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[2].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[3].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[4].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[5].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[6].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[7].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[8].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[9].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[10].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[11].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[12].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[13].toString()).append("</dataset>");
    	sb.append("<dataset>").append(sbs[14].toString()).append("</dataset>");
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat32(Page page , String configId , Map paramsMap) throws Exception{
			if(page.getTotalCount() > 0){
				page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
				queryResults(page , configId , paramsMap) ;
			}
			List resultList = page.getResults() ;
			StringBuffer sb = new StringBuffer() ;
			StringBuffer[] sbs = new StringBuffer[21] ;
			for(int i = 0 ; i < sbs.length ; i ++){
				sbs[i] = new StringBuffer() ;
			}
    	sb.append("<graph xaxisname='月份' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='销售月报统计' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='"+obj[17]+"' />");
    		sbs[1].append("<set value='"+String.valueOf(obj[1])+"' />");
    		sbs[2].append("<set value='"+String.valueOf(obj[2])+"' />");
    		sbs[3].append("<set value='"+String.valueOf(obj[3])+"' />");
    		sbs[4].append("<set value='"+String.valueOf(obj[4])+"' />");
    		sbs[5].append("<set value='"+String.valueOf(obj[5])+"' />");
    		sbs[6].append("<set value='"+String.valueOf(obj[6])+"' />");
    		sbs[7].append("<set value='"+String.valueOf(obj[7])+"' />");
    		sbs[8].append("<set value='"+String.valueOf(obj[8])+"' />");
    		sbs[9].append("<set value='"+String.valueOf(obj[9])+"' />");
    		sbs[10].append("<set value='"+String.valueOf(obj[10])+"' />");
    		sbs[11].append("<set value='"+String.valueOf(obj[11])+"' />");
    		sbs[12].append("<set value='"+String.valueOf(obj[12])+"' />");
    		sbs[13].append("<set value='"+String.valueOf(obj[13])+"' />");
    		sbs[14].append("<set value='"+String.valueOf(obj[14])+"' />");
    		sbs[15].append("<set value='"+String.valueOf(obj[15])+"' />");
    		sbs[16].append("<set value='"+String.valueOf(obj[16])+"' />");
    	}    	
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='当月会员数据接收总量'>").append(sbs[9].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='当月减人量'>").append(sbs[10].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='当月会员数据接收有效量'>").append(sbs[11].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='当月使用服务量'>").append(sbs[12].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='截止会员数据接收总量'>").append(sbs[13].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='截止减人量'>").append(sbs[14].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='截止会员数据接收有效量'>").append(sbs[15].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='截止使用服务量'>").append(sbs[16].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();

	}
	
	public String getDataXmlStat33(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(12 , 12) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer();
    	StringBuffer[] sbs = new StringBuffer[12];
    	for(int i = 0 ; i < 12 ; i ++){
    		sbs[i] = new StringBuffer(" ");
    	}
    	sb.append("<graph xaxisname='会员' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员量增长率' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='"+obj[1]+ "' />");
    		sbs[0].append("<set value='"+ String.valueOf(obj[2] == null ? 0 : obj[2]) +"' />");
    		sbs[1].append("<set value='"+String.valueOf(obj[3] == null ? 0 : obj[3])+"' />");
    		sbs[2].append("<set value='"+String.valueOf(obj[5] == null ? 0 : obj[5])+"' />");
    	}
    	sb.append("</categories>");
    	sb.append("<dataset seriesname='本期'>").append(sbs[0].toString()).append("</dataset>");
    	sb.append("<dataset seriesname='上一年度'>").append(sbs[1].toString()).append("</dataset>");
    	sb.append("<dataset seriesname='上一月份'>").append(sbs[2].toString()).append("</dataset>");
    	sb.append("</graph>");
    	return sb.toString() ;
	}
	public String getDataXmlStat35(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='业务类型' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[3] + "' />");
    		sbs[3].append("<set value='" + objs[4] + "' />");
    		sbs[4].append("<set value='" + objs[5] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='产品会员总量'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='产品会员增量'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='服务期内会员量'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='使用服务会员量'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	
    	return sb.toString();
	}
	
	public String getDataXmlStat38(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='所在地' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0]))+","+id2Name(String.valueOf(objs[1]))+","+id2Name(String.valueOf(objs[2])) + "' />");
    		sbs[1].append("<set value='" + objs[3] + "' />");
    		sbs[2].append("<set value='" + objs[4] + "' />");
    		sbs[3].append("<set value='" + objs[5] + "' />");
//    		sbs[4].append("<set value='" + objs[8] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='产品会员总量'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='服务期内会员量'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='使用服务会员量'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='产品会员增量'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString() ;
	}
	
	public String getDataXmlStat39(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer();
    	StringBuffer[] sbs = new StringBuffer[8];
    	for(int i = 0 ; i < 8 ; i ++){
    		sbs[i] = new StringBuffer(" ") ;
    	}
    	sb.append("<graph xaxisname='业务类型' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='"+obj[0]+"' />");
    		sbs[1].append("<set value='"+String.valueOf(obj[1])+"' />");
    		sbs[2].append("<set value='"+String.valueOf(obj[2])+"' />");
    		sbs[3].append("<set value='"+String.valueOf(obj[3])+"' />");
    		sbs[4].append("<set value='"+String.valueOf(obj[4])+"' />");
    		sbs[5].append("<set value='"+String.valueOf(obj[5])+"' />");
    		sbs[6].append("<set value='"+String.valueOf(obj[6])+"' />");
    		sbs[7].append("<set value='"+String.valueOf(obj[7])+"' />");
    	
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='产品会员总量'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='产品会员增量'>").append(sbs[4].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='服务期内会员量'>").append(sbs[5].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString() ;
	}
	
	public String getDataXmlStat37(Page page , String configId , Map paramsMap) throws Exception{
		
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[4] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='销售组别' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    		sbs[3].append("<set value='" + objs[3] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='产品会员量'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='服务期内会员量'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='使用服务会员量'>").append(sbs[3].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString() ;
	}
	
	/*视图ID2Name*/
	public String id2Name(String id) {
		String name = systemDictTypeService.id2Name(id);
		return name;
	}

	public String getDataXmlStat61(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer();
    	StringBuffer[] sbs = new StringBuffer[15];
    	for(int i = 0 ; i < 15 ; i ++){
    		sbs[i] = new StringBuffer(" ") ;
    	}
    	sb.append("<graph xaxisname='产品_客户' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='" + obj[1] + "-" + obj[0] + "' />");
    		sbs[1].append("<set value='"+ String.valueOf(obj[1] == null ? 0 : obj[1]) +"' />");
    		sbs[2].append("<set value='"+ String.valueOf(obj[2] == null ? 0 : obj[2]) +"' />");
    		sbs[3].append("<set value='"+String.valueOf(obj[3] == null ? 0 : obj[3])+"' />");
    		sbs[4].append("<set value='"+String.valueOf(obj[4] == null ? 0 : obj[4])+"' />");
    		sbs[5].append("<set value='"+String.valueOf(obj[5] == null ? 0 : obj[5])+"' />");
    		sbs[6].append("<set value='"+String.valueOf(obj[6] == null ? 0 : obj[6])+"' />");
    		sbs[7].append("<set value='"+String.valueOf(obj[7] == null ? 0 : obj[7])+"' />");
    		sbs[8].append("<set value='"+String.valueOf(obj[8] == null ? 0 : obj[8])+"' />");
    		sbs[9].append("<set value='"+String.valueOf(obj[9] == null ? 0 : obj[9])+"' />");
    		sbs[10].append("<set value='"+String.valueOf(obj[10] == null ? 0 : obj[10])+"' />");
    		sbs[11].append("<set value='"+String.valueOf(obj[11] == null ? 0 : obj[11])+"' />");
    		sbs[12].append("<set value='"+String.valueOf(obj[12] == null ? 0 : obj[12])+"' />");
    		sbs[13].append("<set value='"+String.valueOf(obj[13] == null ? 0 : obj[13])+"' />");
    		sbs[14].append("<set value='"+String.valueOf(obj[14] == null ? 0 : obj[14])+"' />");
    	
    	}
    	sb.append("</categories>");
    	sb.append("<dataset seriesname='客户名称'>").append(sbs[0].toString()).append("</dataset>");
    	sb.append("<dataset seriesname='产品名称'>").append(sbs[1] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='院前垫付'>").append(sbs[2] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='门急诊垫付'>").append(sbs[3] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='住院垫付'>").append(sbs[4] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='健康咨询'>").append(sbs[5] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='挂号服务'>").append(sbs[6].toString()).append("</dataset>");
    	sb.append("<dataset seriesname='紧急救援'>").append(sbs[7] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='中医出诊'>").append(sbs[8] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='西医出诊'>").append(sbs[9] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='核保体检'>").append(sbs[10] .toString()).append("</dataset>");
    	sb.append("</graph>");
    	return sb.toString() ;
	}
	
	public String getDataXmlStat62(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer();
    	StringBuffer[] sbs = new StringBuffer[15];
    	for(int i = 0 ; i < 15 ; i ++){
    		sbs[i] = new StringBuffer(" ") ;
    	}
    	sb.append("<graph xaxisname='产品_客户' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='" + obj[1] + "-" + obj[0] + "' />");
    		sbs[1].append("<set value='"+ String.valueOf(obj[1] == null ? 0 : obj[1]) +"' />");
    		sbs[2].append("<set value='"+ String.valueOf(obj[2] == null ? 0 : obj[2]) +"' />");
    		sbs[3].append("<set value='"+String.valueOf(obj[3] == null ? 0 : obj[3])+"' />");
    		sbs[4].append("<set value='"+String.valueOf(obj[4] == null ? 0 : obj[4])+"' />");
    		sbs[5].append("<set value='"+String.valueOf(obj[5] == null ? 0 : obj[5])+"' />");
    		sbs[6].append("<set value='"+String.valueOf(obj[6] == null ? 0 : obj[6])+"' />");
    		sbs[7].append("<set value='"+String.valueOf(obj[7] == null ? 0 : obj[7])+"' />");
    		sbs[8].append("<set value='"+String.valueOf(obj[8] == null ? 0 : obj[8])+"' />");
    		sbs[9].append("<set value='"+String.valueOf(obj[9] == null ? 0 : obj[9])+"' />");
    		sbs[10].append("<set value='"+String.valueOf(obj[10] == null ? 0 : obj[10])+"' />");
    		sbs[11].append("<set value='"+String.valueOf(obj[11] == null ? 0 : obj[11])+"' />");
    		sbs[12].append("<set value='"+String.valueOf(obj[12] == null ? 0 : obj[12])+"' />");
    		sbs[13].append("<set value='"+String.valueOf(obj[13] == null ? 0 : obj[13])+"' />");
    		sbs[14].append("<set value='"+String.valueOf(obj[14] == null ? 0 : obj[14])+"' />");

    	}
    	sb.append("</categories>");
    	sb.append("<dataset seriesname='客户名称'>").append(sbs[1].toString()).append("</dataset>");
    	sb.append("<dataset seriesname='产品名称'>").append(sbs[3] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='专家门诊安排'>").append(sbs[4] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='手术安排'>").append(sbs[5] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='住院安排'>").append(sbs[6] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='体检安排'>").append(sbs[7] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='医院陪同'>").append(sbs[8].toString()).append("</dataset>");
    	sb.append("<dataset seriesname='PET-CT'>").append(sbs[9] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='住院探视'>").append(sbs[10] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='驻场服务'>").append(sbs[11] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='健康讲座'>").append(sbs[12] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='其他医疗安排'>").append(sbs[13] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='其他非医疗安排'>").append(sbs[14] .toString()).append("</dataset>");
 
    	sb.append("</graph>");
    	return sb.toString() ;
	}
	
	public String getDataXmlStat71(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer();
		StringBuffer[] sbs = new StringBuffer[8];
		for(int i = 0 ; i < 8 ; i ++){
			sbs[i] = new StringBuffer(" ") ;
		}
		sb.append("<graph xaxisname='产品_客户' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='客户(产品)电话量统计图' subcaption='' >");
		sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
		for (int i = 0 ; i < resultList.size(); i++) {
			Object[] obj = (Object[])resultList.get(i);
			sb.append("<category name='"+obj[1] + "-" + obj[0] + "' />");
			sbs[0].append("<set value='"+ String.valueOf(obj[2] == null ? 0 : obj[2]) +"' />");
			sbs[1].append("<set value='"+String.valueOf(obj[3] == null ? 0 : obj[3])+"' />");
			sbs[2].append("<set value='"+String.valueOf(obj[4] == null ? 0 : obj[4])+"' />");
			sbs[3].append("<set value='"+String.valueOf(obj[5] == null ? 0 : obj[5])+"' />");
			sbs[4].append("<set value='"+String.valueOf(obj[6] == null ? 0 : obj[6])+"' />");
			sbs[5].append("<set value='"+String.valueOf(obj[7] == null ? 0 : obj[7])+"' />");
		}
		sb.append("</categories>");
		sb.append("<dataset seriesname='呼入数量'>").append(sbs[0].toString()).append("</dataset>");
		sb.append("<dataset seriesname='呼出数量'>").append(sbs[1] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='短信数量'>").append(sbs[2] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='HPN数量'>").append(sbs[3] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='内部转接数量'>").append(sbs[4] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='三方通话数量'>").append(sbs[5] .toString()).append("</dataset>");
		sb.append("</graph>");
		return sb.toString() ;
	}
	
	public String getDataXmlStat72(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer();
		StringBuffer[] sbs = new StringBuffer[8];
    	for(int i = 0 ; i < 8 ; i ++){
    		sbs[i] = new StringBuffer(" ") ;
    	}
    	sb.append("<graph xaxisname='产品_客户' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='客户(产品)案例量统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='"+obj[1] + "-" + obj[0] + "' />");
    		sbs[0].append("<set value='"+ String.valueOf(obj[2] == null ? 0 : obj[2]) +"' />");
    		sbs[1].append("<set value='"+String.valueOf(obj[3] == null ? 0 : obj[3])+"' />");
    		sbs[2].append("<set value='"+String.valueOf(obj[4] == null ? 0 : obj[4])+"' />");
    		sbs[3].append("<set value='"+String.valueOf(obj[5] == null ? 0 : obj[5])+"' />");
    		sbs[4].append("<set value='"+String.valueOf(obj[6] == null ? 0 : obj[6])+"' />");
    		sbs[5].append("<set value='"+String.valueOf(obj[7] == null ? 0 : obj[7])+"' />");
    	}
    	sb.append("</categories>");
    	sb.append("<dataset seriesname='呼入数量'>").append(sbs[0].toString()).append("</dataset>");
    	sb.append("<dataset seriesname='呼出数量'>").append(sbs[1] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='短信数量'>").append(sbs[2] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='HPN数量'>").append(sbs[3] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='内部转接数量'>").append(sbs[4] .toString()).append("</dataset>");
    	sb.append("<dataset seriesname='三方通话数量'>").append(sbs[5] .toString()).append("</dataset>");
    	sb.append("</graph>");
    	return sb.toString() ;
	}
	
	public String getDataXmlStat73(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer();
		StringBuffer[] sbs = new StringBuffer[8];
		for(int i = 0 ; i < 8 ; i ++){
			sbs[i] = new StringBuffer(" ") ;
		}
		sb.append("<graph xaxisname='省份_城市' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='来电地域电话量统计图' subcaption='' >");
		sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
		for (int i = 0 ; i < resultList.size(); i++) {
			Object[] obj = (Object[])resultList.get(i);
			sb.append("<category name='"+obj[1] + "-" + obj[0] + "' />");
			sbs[0].append("<set value='"+ String.valueOf(obj[2] == null ? 0 : obj[2]) +"' />");
			sbs[1].append("<set value='"+String.valueOf(obj[3] == null ? 0 : obj[3])+"' />");
			sbs[2].append("<set value='"+String.valueOf(obj[4] == null ? 0 : obj[4])+"' />");
			sbs[3].append("<set value='"+String.valueOf(obj[5] == null ? 0 : obj[5])+"' />");
			sbs[4].append("<set value='"+String.valueOf(obj[6] == null ? 0 : obj[6])+"' />");
			sbs[5].append("<set value='"+String.valueOf(obj[7] == null ? 0 : obj[7])+"' />");
		}
		sb.append("</categories>");
		sb.append("<dataset seriesname='呼入数量'>").append(sbs[0].toString()).append("</dataset>");
		sb.append("<dataset seriesname='呼出数量'>").append(sbs[1] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='短信数量'>").append(sbs[2] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='HPN数量'>").append(sbs[3] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='内部转接数量'>").append(sbs[4] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='三方通话数量'>").append(sbs[5] .toString()).append("</dataset>");
		sb.append("</graph>");
		return sb.toString() ;
	}
	
	public String getDataXmlStat74(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer();
		StringBuffer[] sbs = new StringBuffer[8];
		for(int i = 0 ; i < 8 ; i ++){
			sbs[i] = new StringBuffer(" ") ;
		}
		sb.append("<graph xaxisname='省份_城市' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='来电地域案例量统计' subcaption='' >");
		sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
		for (int i = 0 ; i < resultList.size(); i++) {
			Object[] obj = (Object[])resultList.get(i);
			sb.append("<category name='"+obj[1] + "-" + obj[0] + "' />");
			sbs[0].append("<set value='"+ String.valueOf(obj[2] == null ? 0 : obj[2]) +"' />");
			sbs[1].append("<set value='"+String.valueOf(obj[3] == null ? 0 : obj[3])+"' />");
			sbs[2].append("<set value='"+String.valueOf(obj[4] == null ? 0 : obj[4])+"' />");
			sbs[3].append("<set value='"+String.valueOf(obj[5] == null ? 0 : obj[5])+"' />");
			sbs[4].append("<set value='"+String.valueOf(obj[6] == null ? 0 : obj[6])+"' />");
			sbs[5].append("<set value='"+String.valueOf(obj[7] == null ? 0 : obj[7])+"' />");
		}
		sb.append("</categories>");
		sb.append("<dataset seriesname='呼入数量'>").append(sbs[0].toString()).append("</dataset>");
		sb.append("<dataset seriesname='呼出数量'>").append(sbs[1] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='短信数量'>").append(sbs[2] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='HPN数量'>").append(sbs[3] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='内部转接数量'>").append(sbs[4] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='三方通话数量'>").append(sbs[5] .toString()).append("</dataset>");
		sb.append("</graph>");
		return sb.toString() ;
	}

	public String getDataXmlStat75(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer();
		StringBuffer[] sbs = new StringBuffer[8];
		for(int i = 0 ; i < 8 ; i ++){
			sbs[i] = new StringBuffer(" ") ;
		}
		sb.append("<graph xaxisname='产品_客户' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='客户(产品)会员类统计图' subcaption='' >");
		sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
		for (int i = 0 ; i < resultList.size(); i++) {
			Object[] obj = (Object[])resultList.get(i);
			sb.append("<category name='"+obj[1] + "-" + obj[0] + "' />");
			sbs[0].append("<set value='"+String.valueOf(obj[1] == null ? 0 : obj[2]) +"' />");
			sbs[1].append("<set value='"+String.valueOf(obj[2] == null ? 0 : obj[3])+"' />");
			sbs[2].append("<set value='"+String.valueOf(obj[3] == null ? 0 : obj[4])+"' />");
			sbs[3].append("<set value='"+String.valueOf(obj[4] == null ? 0 : obj[5])+"' />");
			sbs[4].append("<set value='"+String.valueOf(obj[5] == null ? 0 : obj[6])+"' />");
//			sbs[5].append("<set value='"+String.valueOf(obj[6] == null ? 0 : obj[7])+"' />");
		}
		sb.append("</categories>");
		sb.append("<dataset seriesname='系统内会员数量'>").append(sbs[0].toString()).append("</dataset>");
		sb.append("<dataset seriesname='系统外会员数量'>").append(sbs[1] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='电话确认会员数量'>").append(sbs[2] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='其它数量'>").append(sbs[3] .toString()).append("</dataset>");
		sb.append("</graph>");
		return sb.toString() ;
	}

	public String getDataXmlStat92(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer();
		StringBuffer[] sbs = new StringBuffer[6];
		for(int i = 0 ; i < 6 ; i ++){
			sbs[i] = new StringBuffer(" ") ;
		}
		sb.append("<graph xaxisname='产品_客户' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='客户来源类型统计图' subcaption='' >");
		sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
		for (int i = 0 ; i < resultList.size(); i++) {
			Object[] obj = (Object[])resultList.get(i);
			sb.append("<category name='"+id2Name(String.valueOf(obj[1])) + "-" + id2Name(String.valueOf(obj[0])) + "' />");
//			sbs[0].append("<set value='"+ String.valueOf(obj[2] == null ? 0 : obj[2]) +"' />");
//			sbs[1].append("<set value='"+String.valueOf(obj[3] == null ? 0 : obj[3])+"' />");
//			sbs[2].append("<set value='"+String.valueOf(obj[4] == null ? 0 : obj[4])+"' />");
//			sbs[3].append("<set value='"+String.valueOf(obj[5] == null ? 0 : obj[5])+"' />");
		}
		sb.append("</categories>");
		sb.append("<dataset seriesname='手机项目数量'>").append(sbs[0].toString()).append("</dataset>");
		sb.append("<dataset seriesname='车牌项目数量'>").append(sbs[1] .toString()).append("</dataset>");
		sb.append("<dataset seriesname='车载设备数量'>").append(sbs[2] .toString()).append("</dataset>");
		sb.append("</graph>");
		return sb.toString() ;
	}

	public String getDataXmlStat51(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[3] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		
		sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		//Map map = (Map)resultList.get(i);
    		//Product product = (Product)productService.findById((String)(map.get("product_id"))) ;
    		Object objs[] = (Object[]) resultList.get(i) ;
    		
    		sb.append("<category name='" + objs[0] + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='有效'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='失效'>").append(sbs[2].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	
    	return sb.toString();
	}
	public String getDataXmlStat141(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		
		sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		//Map map = (Map)resultList.get(i);
    		//Product product = (Product)productService.findById((String)(map.get("product_id"))) ;
    		Object objs[] = (Object[]) resultList.get(i) ;
    		
    		sb.append("<category name='" + objs[1] + "' />");
    		sbs[1].append("<set value='" + objs[2] + "' />");
    		sbs[2].append("<set value='" + objs[3] + "' />");
    		sbs[3].append("<set value='" + objs[4] + "' />");
    		sbs[4].append("<set value='" + objs[5] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='新增'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='更新'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='续服'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='退服'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	
    	return sb.toString();
	}
	public String getDataXmlStat52(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		
		sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		//Map map = (Map)resultList.get(i);
    		//Product product = (Product)productService.findById((String)(map.get("product_id"))) ;
    		Object objs[] = (Object[]) resultList.get(i) ;
    		
    		sb.append("<category name='" + objs[1] + "' />");
    		sbs[1].append("<set value='" + objs[2] + "' />");
    		sbs[2].append("<set value='" + objs[3] + "' />");
    		sbs[3].append("<set value='" + objs[4] + "' />");
    		sbs[4].append("<set value='" + objs[5] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='新增'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='更新'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='续服'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='退服'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	
    	return sb.toString();
	}
	
	public String getDataXmlStat53(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		
		sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + objs[1] + "' />");
    		sbs[1].append("<set value='" + objs[4] + "' />");
    		sbs[2].append("<set value='" + objs[5] + "' />");
    		sbs[3].append("<set value='" + objs[6] + "' />");
    		sbs[4].append("<set value='" + objs[7] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='新增'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='更新'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='续保'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='减人'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	
    	return sb.toString();
	}
	
	public String getDataXmlStat54(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		
		sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + objs[1] + "' />");
    		sbs[1].append("<set value='" + objs[5] + "' />");
    		sbs[2].append("<set value='" + objs[6] + "' />");
    		sbs[3].append("<set value='" + objs[7] + "' />");
    		sbs[4].append("<set value='" + objs[8] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='新增'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='更新'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='续服'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='退服'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	
    	return sb.toString();
	}
	
	public String getDataXmlStat55(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		
		sb.append("<graph xaxisname='产品' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员导入统计图' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		//Map map = (Map)resultList.get(i);
    		//Product product = (Product)productService.findById((String)(map.get("product_id"))) ;
    		Object objs[] = (Object[]) resultList.get(i) ;
    		
    		sb.append("<category name='" + objs[1] + "' />");
    		sbs[1].append("<set value='" + objs[3] + "' />");
    		sbs[2].append("<set value='" + objs[4] + "' />");
    		sbs[3].append("<set value='" + objs[5] + "' />");
    		sbs[4].append("<set value='" + objs[6] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='新增'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='更新'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='续保'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='减人'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	
    	return sb.toString();
	}
	
	public String getDataXmlStat81(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer();
    	StringBuffer sb0 = new StringBuffer();
    	StringBuffer sb1= new StringBuffer();
    	StringBuffer sb2= new StringBuffer();
    	sb.append("<graph xaxisname='月份' yaxisname='数据来源数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='每月出险量和会员量比对汇总表' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='"+obj[0]+"' />");
    		sb1.append("<set value='"+String.valueOf(obj[1])+"' />");
    		sb2.append("<set value='"+String.valueOf(obj[2])+"' />");
    	}
    	sb.append("</categories>");
    	sb0.append("<dataset seriesname='120出险量'>").append(sb1.toString()).append("</dataset>");
    	sb0.append("<dataset seriesname='比对成功会员量'>").append(sb2.toString()).append("</dataset>");
    	sb.append(sb0.toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat82(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer();
    	StringBuffer sb0 = new StringBuffer();
    	StringBuffer sb1= new StringBuffer();
    	sb.append("<graph xaxisname='月份' yaxisname='比对成功会员量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='各产品车牌比对结果汇总表' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='"+obj[0]+"' />");
    		sb1.append("<set value='"+String.valueOf(obj[2])+"' />");
    	}
    	sb.append("</categories>");
    	sb0.append("<dataset seriesname='比对成功会员量'>").append(sb1.toString()).append("</dataset>");
    	sb.append(sb0.toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat83(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[5] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='城市' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='急救中心城市出险数量和会员量汇总' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    		sbs[3].append("<set value='" + objs[3] + "' />");
    		sbs[4].append("<set value='" + objs[4] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='出险数量'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='比对成功会员数量'>").append(sbs[4].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat84(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[32] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='城市' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='急救中心传输数量和传输次数统计' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    		sbs[3].append("<set value='" + objs[3] + "' />");
    		sbs[4].append("<set value='" + objs[4] + "' />");
    		sbs[5].append("<set value='" + objs[5] + "' />");
    		sbs[6].append("<set value='" + objs[6] + "' />");
    		sbs[7].append("<set value='" + objs[7] + "' />");
    		sbs[8].append("<set value='" + objs[8] + "' />");
    		sbs[9].append("<set value='" + objs[9] + "' />");
    		sbs[10].append("<set value='" + objs[10] + "' />");
    		sbs[11].append("<set value='" + objs[11] + "' />");
    		sbs[12].append("<set value='" + objs[12] + "' />");
    		sbs[13].append("<set value='" + objs[13] + "' />");
    		sbs[14].append("<set value='" + objs[14] + "' />");
    		sbs[15].append("<set value='" + objs[15] + "' />");
    		sbs[16].append("<set value='" + objs[16] + "' />");
    		sbs[17].append("<set value='" + objs[17] + "' />");
    		sbs[18].append("<set value='" + objs[18] + "' />");
    		sbs[19].append("<set value='" + objs[19] + "' />");
    		sbs[20].append("<set value='" + objs[20] + "' />");
    		sbs[21].append("<set value='" + objs[21] + "' />");
    		sbs[22].append("<set value='" + objs[22] + "' />");
    		sbs[23].append("<set value='" + objs[23] + "' />");
    		sbs[24].append("<set value='" + objs[24] + "' />");
    		sbs[25].append("<set value='" + objs[25] + "' />");
    		sbs[26].append("<set value='" + objs[26] + "' />");
    		sbs[27].append("<set value='" + objs[27] + "' />");
    		sbs[28].append("<set value='" + objs[28] + "' />");
    		sbs[29].append("<set value='" + objs[29] + "' />");
    		sbs[30].append("<set value='" + objs[30] + "' />");
    		sbs[31].append("<set value='" + objs[31] + "' />");
    		
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='1号'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='2号'>").append(sbs[2].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='3号'>").append(sbs[3].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='4号'>").append(sbs[4].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='5号'>").append(sbs[5].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='6号'>").append(sbs[6].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='7号'>").append(sbs[7].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='8号'>").append(sbs[8].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='9号'>").append(sbs[9].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='10号'>").append(sbs[10].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='11号'>").append(sbs[11].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='12号'>").append(sbs[12].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='13号'>").append(sbs[13].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='14号'>").append(sbs[14].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='15号'>").append(sbs[15].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='16号'>").append(sbs[16].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='17号'>").append(sbs[17].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='18号'>").append(sbs[18].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='19号'>").append(sbs[19].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='20号'>").append(sbs[20].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='21号'>").append(sbs[21].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='22号'>").append(sbs[22].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='23号'>").append(sbs[23].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='24号'>").append(sbs[24].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='25号'>").append(sbs[25].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='26号'>").append(sbs[26].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='27号'>").append(sbs[27].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='28号'>").append(sbs[28].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='29号'>").append(sbs[29].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='30号'>").append(sbs[30].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='31号'>").append(sbs[31].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat85(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[3] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='医院名称' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='出险车牌送达医院统计' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='医院名称'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='数量'>").append(sbs[2].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	public String getDataXmlStat86(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer() ;
		StringBuffer[] sbs = new StringBuffer[3] ;
		for(int i = 0 ; i < sbs.length ; i ++){
			sbs[i] = new StringBuffer() ;
		}
		sb.append("<graph xaxisname='城市' yaxisname='数量' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='急救中心城市出险数量和会员量汇总' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object objs[] = (Object[]) resultList.get(i) ;
    		sb.append("<category name='" + id2Name(String.valueOf(objs[0])) + "' />");
    		sbs[1].append("<set value='" + objs[1] + "' />");
    		sbs[2].append("<set value='" + objs[2] + "' />");
    	}
    	sb.append("</categories>");
    	sbs[0].append("<dataset seriesname='医院名称'>").append(sbs[1].toString()).append("</dataset>");
    	sbs[0].append("<dataset seriesname='数量'>").append(sbs[2].toString()).append("</dataset>");
    	sb.append(sbs[0].toString());
    	sb.append("</graph>");
    	return sb.toString();
	}
	
	
	
	public String getDataXmlStat87(Page page , String configId , Map paramsMap) throws Exception{
		if(page.getTotalCount() > 0){
			page = new Page(HttpTool.getPageNum() , (int)page.getTotalCount()) ;
			queryResults(page , configId , paramsMap) ;
		}
		List resultList = page.getResults() ;
		StringBuffer sb = new StringBuffer();
    	StringBuffer sb0 = new StringBuffer();
    	StringBuffer sb1= new StringBuffer();
    	StringBuffer sb2= new StringBuffer();
    	sb.append("<graph xaxisname='车型' yaxisname='车型统计' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='会员数据采集' subcaption='' >");
    	sb.append("<categories font='Arial' fontSize='11' fontColor='000000'>");
    	for (int i = 0 ; i < resultList.size(); i++) {
    		Object[] obj = (Object[])resultList.get(i);
    		sb.append("<category name='"+obj[0]+"' />");
    		sb1.append("<set value='"+String.valueOf(obj[1])+"' />");
    		sb2.append("<set value='"+String.valueOf(obj[2])+"' />");
    	}
    	sb.append("</categories>");
    	sb0.append("<dataset seriesname='总计'>").append(sb1.toString()).append("</dataset>");
    	sb0.append("<dataset seriesname='车型占比'>").append(sb2.toString()).append("</dataset>");
    	sb.append(sb0.toString());
    	sb.append("</graph>");
    	return sb.toString();
	}

	
	/**
	 * 生成子模块树JSON
	 * @return
	 * @throws Exception
	 */
	private RegionService regionService = (RegionService) SpringTool.getBean(RegionService.class);
	
	public String treeCity() throws Exception{
		StringBuffer json = new StringBuffer("[") ;
		String parentId="";
		User user = (User) HttpTool.getSession().getAttribute("currentUser");	
			String userId = user.getId();
			User userObj = queryService.getUserByUserId(userId);
			if(userObj!=null){
				parentId = userObj.getProvice();
				List<Region> regionList =  regionService.findRegionByParentId(parentId)  ;
				Region region = null ;
				if(regionList.size() > 0){
					for(int i = 0 ; i < regionList.size() ; i ++){
						region = regionList.get(i) ;
						json.append("{\"text\":\"" + region.getRegionName() + "\",\"id\":\"" + region.getId() + "\",\"leaf\":" + true) ;
						json.append("},") ;
					}
				}
				if (json.toString().endsWith(",")) {
					json = json.delete(json.length() - 1, json.length());
				}

				json.append("]");
				this.jsonString = json.toString();
			}
			if(StringUtils.isEmpty(parentId)){	//两个都为null，不查询数据
				parentId = "isNull";
			}
		//加载叶子节点下的button
		
		return "json";
	}
}
