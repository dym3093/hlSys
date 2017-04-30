/**
 * @author DengYouming
 * @since 2016-4-26 下午2:53:34
 */
package org.hpin.settlementManagement.web;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpConference;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.settlementManagement.Thread.ErpSettleTaskJYPceThread;
import org.hpin.settlementManagement.entity.ErpJYSettleTaskDetail;
import org.hpin.settlementManagement.entity.ErpSettleExcetaskJY;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerJY;
import org.hpin.settlementManagement.entity.ErpSettlementTaskJY;
import org.hpin.settlementManagement.service.ErpSettlementTaskJYService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 基因公司结算任务_Action
 * @author DengYouming
 * @since 2016-4-26 下午2:53:34
 */

@Namespace("/settlementManagement")
@Action("erpSettlementTaskJY")
@Results({
	@Result(name="listSettlement", location="/WEB-INF/settlementManagement/listSettlement.jsp"),
	@Result(name="toAddPage", location="/WEB-INF/settlementManagement/addSettlement.jsp"),
	@Result(name="importCustomer", location="/WEB-INF/settlementManagement/importCustomer.jsp"),
	@Result(name="modifyCustomer", location="/WEB-INF/settlementManagement/modifyCustomer.jsp"),
	@Result(name="handleSettlement", location="/WEB-INF/settlementManagement/handleSettlement.jsp"),
	@Result(name="modifyPriceEnd", location="/WEB-INF/settlementManagement/modifyPriceEnd.jsp"),
	@Result(name="modifyCustomerInfo", location="/WEB-INF/settlementManagement/modifyCustomerInfo.jsp"),
	@Result(name="exceptionSettlement", location="/WEB-INF/settlementManagement/exceptionSettlement.jsp"),
	@Result(name="settlementTaskDetail", location="/WEB-INF/settlementManagement/settlementTaskDetail.jsp"),
	@Result(name="exceptionSettlementByTaskNo", location="/WEB-INF/settlementManagement/exceptionSettlementByTaskNo.jsp"),
	@Result(name="exceptionNumInfo", location="/WEB-INF/settlementManagement/exceptionNumInfo.jsp"),
	@Result(name="settleTaskDetail", location="/WEB-INF/settlementManagement/settleTaskDetail.jsp"),
	@Result(name="listSettlementOperate", location="/WEB-INF/settlementManagement/listSettlementOperate.jsp"),
	@Result(name="listSettlementComplete", location="/WEB-INF/settlementManagement/listSettlementComplete.jsp"),
	@Result(name="JYSettlementDetail", location="/WEB-INF/settlementManagement/JYSettlementDetail.jsp"),
	@Result(name="updateSettlementDetail", location="/WEB-INF/settlementManagement/updateSettlementDetail.jsp"),
})
public class ErpSettlementTaskJYAction extends BaseAction{
	private Logger log = Logger.getLogger(ErpSettlementTaskJYAction.class);
	private static final long serialVersionUID = -5045381634305276564L;

	//基因公司结算任务_service
	private ErpSettlementTaskJYService settlementTaskJYService = (ErpSettlementTaskJYService) SpringTool.getBean("settlementTaskJYService");
	
	private ErpCustomerService erpCustomerService = (ErpCustomerService) SpringTool.getBean(ErpCustomerService.class);
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	ErpSettlementTaskJY erpSettlementTaskJY;
	
	private ErpCustomer customer;
	private ErpEvents events;
	private ErpConference conference;
	private File affix;
    private String affixContentType;
    private String affixFileName;
    
    /**
     * 增加JY结算默认页面
     * @return
     * @author tangxing
     * @date 2016-8-12下午3:54:08
     */
	public String toAddPage(){
		String s = "JYJS";
		String defTaskNum = settlementTaskJYService.defTaskNum();
		int ranNum = settlementTaskJYService.ranNum();
		String settleJYNo = s+defTaskNum+ranNum;	//自动生成的任务号（当前日期+随机三位数）
		String createSettleTime = "";					//结算时间（当前时间）
		String location = "listSettlement";
		//String id = HttpTool.getParameter("id");
		//List<ErpSettlementCustomerJY> erpSettlementCustomerJYList = null;
		SimpleDateFormat dateFormat = null;
		
		/*if(id!=null&&id.length()>0){
			try {
				page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
				erpSettlementCustomerJYList =  settlementTaskJYService.getCustomer(id);
				page.setResults(erpSettlementCustomerJYList);
				
				
			} catch (ParseException e) {
				log.error("toAddPage for ParseException",e);
			} catch (Exception e){
				log.error("toAddPage for Exception",e);
			}
		}else{
			//到新增页面时生成ID
			id = UUID.randomUUID().toString().replace("-", "");
		}*/
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		createSettleTime = dateFormat.format(new Date());
		
		HttpTool.setAttribute("settleJYNo", settleJYNo);
		//HttpTool.setAttribute("id", id);
		HttpTool.setAttribute("createSettleTime", createSettleTime);
		//System.out.println("id: "+id);
		
		location = "toAddPage";
		return location;
	}
	
	/**
	 * 所有异常结算任务
	 * @return
	 * @author tangxing
	 * @date 2016-6-6下午7:40:36
	 */
	public String exceptionSettleTask(){
		String geCompanyId = HttpTool.getParameter("geCompanyId");
		String mapString = "";
		int arrSize = 0;
		String[] strs = new String[20];	//查询条件数组
		String value = "";				//输入的查询值
		
		List<Map<String, Object>> customerJYs = null;
		
		Map searchMap = super.buildSearch();
		
		mapString = searchMap.toString();
        if(mapString.length()>10){
        	strs = mapString.replaceAll(" ", "").split(",");	//多条件
            arrSize = strs.length;
            for(int i=0; i< arrSize; ++i){
            	if(strs[i].contains("code")){									//如果包含"code"子串
            		if(0==i&&arrSize>1){										//该字符串在第一个位置上，并且有多个条件
            			int num= strs[i].indexOf("=",18);						//从指定位置开始查找"="出现的下标
                    	String firstStr = strs[0].substring(0, num);
                    	if(firstStr.equals("{filter_and_code_EQ_S")){
                    		value=strs[i].substring(num+1);
                    		customerJYs = settlementTaskJYService.getSettleCustomerByCode(value);
                    		String valueStr = settlementTaskJYService.formatMap(customerJYs);
                    		if(customerJYs.size()>0){
                    			String str = strs[i].substring(1, num);
                    			searchMap.remove(str);
                        		searchMap.put("filter_and_id_IN_S", valueStr);
                    		}else{
                    			String str = strs[i].substring(1, num);
                    			searchMap.remove(str);
                    			searchMap.put("filter_and_id_IN_S","'fail'");
                    		}
                    	}
            		}else if(0==i){												//如果该字符串在第一个位置上
            			int num= strs[i].indexOf("=",18);						//从指定位置开始查找"="出现的下标
                    	String firstStr = strs[0].substring(0, num);
                    	if(firstStr.equals("{filter_and_code_EQ_S")){
                    		value=strs[i].substring(num+1);
                    		value=value.substring(0, value.length()-1);			//去除最后的"}"
                    		customerJYs = settlementTaskJYService.getSettleCustomerByCode(value);
                    		String valueStr = settlementTaskJYService.formatMap(customerJYs);
                    		if(customerJYs.size()>0){
                    			String str = strs[i].substring(1, num);
                    			searchMap.remove(str);
                        		searchMap.put("filter_and_id_IN_S", valueStr);
                    		}else{
                    			String str = strs[i].substring(1, num);
                    			searchMap.remove(str);
                    			searchMap.put("filter_and_id_IN_S","'fail'");
                    		}
                    	}
            		}else if(i==arrSize-1){										//如果该字符串在最后的位置上
            			int num= strs[i].indexOf("=",18);						//从指定位置开始查找"="出现的下标
                    	String firstStr = strs[0].substring(0, num);
                    	if(firstStr.equals("filter_and_code_EQ_S")){
                    		value=strs[0].substring(num+1);
                    		value=value.substring(0, value.length()-1);			//去除最后的"}"
                    		customerJYs = settlementTaskJYService.getSettleCustomerByCode(value);
                    		String valueStr = settlementTaskJYService.formatMap(customerJYs);
                    		if(customerJYs.size()>0){
                    			String str = strs[i].substring(0, num);
                    			searchMap.remove(str);
                        		searchMap.put("filter_and_id_IN_S", valueStr);
                    		}else{
                    			String str = strs[i].substring(0, num);
                    			searchMap.remove(str);
                    			searchMap.put("filter_and_id_IN_S","'fail'");
                    		}
                    	}
            		}else{
            			int num= strs[i].indexOf("=",18);						//从指定位置开始查找"="出现的下标
                    	String firstStr = strs[0].substring(0, num);
                    	if(firstStr.equals("filter_and_code_EQ_S")){
                    		value=strs[i].substring(num+1);
                    		customerJYs = settlementTaskJYService.getSettleCustomerByCode(value);
                    		String valueStr = settlementTaskJYService.formatMap(customerJYs);
                    		if(customerJYs.size()>0){
                    			String str = strs[i].substring(0, num);
                    			searchMap.remove(str);
                        		searchMap.put("filter_and_id_IN_S", valueStr);
                    		}else{
                    			String str = strs[i].substring(0, num);
                    			searchMap.remove(str);
                    			searchMap.put("filter_and_id_IN_S","'fail'");
                    		}
                    	}
            		}
            	}
            	
            	/*if(strs[0].equals("{filter_and_code_EQ_S=0")||strs[0].equals("{filter_and_produceCost_LT_D=1")||1==arrSize){
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
            	}*/
            }
        }
		
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("exceptionSettleTask",e);
		}
		
		//拿出所有的异常批次
		List<ErpSettleExcetaskJY> erpSettleExcetaskJYs = settlementTaskJYService.listSettleExceTask(page,searchMap);
		
		page.setResults(erpSettleExcetaskJYs);
		
		HttpTool.setAttribute("geCompanyId", geCompanyId);
		
		return "exceptionSettlement";
	}
	
	public String exceptionSettleTaskById(){
		String taskNo = HttpTool.getParameter("taskNo");	//结算任务号
		Map searchMap = super.buildSearch();
		
		searchMap.put("order_createTime", "desc");
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("exceptionSettleTask",e);
		}
		
		//拿出所有的异常批次
		List<ErpSettleExcetaskJY> erpSettleExcetaskJYs = settlementTaskJYService.getExceSettleTaskBySettleTaskNo(taskNo,page,searchMap);
		page.setResults(erpSettleExcetaskJYs);
		return "exceptionSettlementByTaskNo";
	}
	
	public String abnormalInfo(){
		String exceTaskId = HttpTool.getParameter("id");
		String flag = HttpTool.getParameter("flag");
		Map paramsMap = super.buildSearch() ;
		//获取异常任务
		ErpSettleExcetaskJY erpSettleExcetaskJY = settlementTaskJYService.getExceSettleById(exceTaskId);
		
		
		
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("exceptionSettleTask",e);
		}
		//获取当前异常任务的所有结算客户
		List<ErpSettlementCustomerJY> erpSettlementCustomerJYs = settlementTaskJYService.getExceSettleCusAll(exceTaskId,page,paramsMap);
		
		page.setResults(erpSettlementCustomerJYs);
		HttpTool.setAttribute("exceSettlementTask", erpSettleExcetaskJY);
		HttpTool.setAttribute("flag", flag);
		return "exceptionNumInfo";
	}
	
	/**
	 * 未出报告的结算customer
	 * @return
	 * @author tangxing
	 * @date 2016-9-12下午1:58:03
	 */
	public String noHavePdfInfo(){
		String exceTaskId = HttpTool.getParameter("id");
		String flag = HttpTool.getParameter("flag");
		Map paramsMap = super.buildSearch() ;
		String pdfStatus = "1";	//未出报告状态
		
		//获取异常任务
		ErpSettleExcetaskJY erpSettleExcetaskJY = settlementTaskJYService.getExceSettleById(exceTaskId);
		
		
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("exceptionSettleTask",e);
		}
		
		//获取当前异常任务的所有结算客户
		List<ErpSettlementCustomerJY> erpSettlementCustomerJYs = settlementTaskJYService.getSettleCusByExceIdAnd(exceTaskId,pdfStatus,page,paramsMap);
		
		
		page.setResults(erpSettlementCustomerJYs);
		HttpTool.setAttribute("exceSettlementTask", erpSettleExcetaskJY);
		HttpTool.setAttribute("flag", flag);
		return "exceptionNumInfo";
	}
	
	
	/**
	 * 信息有误的结算customer
	 * @return
	 * @author tangxing
	 * @date 2016-9-12下午1:58:45
	 */
	public String errInfo(){
		String exceTaskId = HttpTool.getParameter("id");
		String flag = HttpTool.getParameter("flag");
		Map paramsMap = super.buildSearch() ;
		String status = "3";
		
		//获取异常任务
		ErpSettleExcetaskJY erpSettleExcetaskJY = settlementTaskJYService.getExceSettleById(exceTaskId);
		
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("exceptionSettleTask",e);
		}
		
		//获取当前异常任务的所有结算客户
		List<ErpSettlementCustomerJY> erpSettlementCustomerJYs = settlementTaskJYService.getSettleCusByExceId(exceTaskId,status,page,paramsMap);
				
		page.setResults(erpSettlementCustomerJYs);
		HttpTool.setAttribute("exceSettlementTask", erpSettleExcetaskJY);
		HttpTool.setAttribute("flag", flag);
		
		return "exceptionNumInfo";
	}
	
	/**
	 * 已经结算的结算customer
	 * @return
	 * @author tangxing
	 * @date 2016-9-12下午1:59:01
	 */
	public String haveSettlementInfo(){
		String exceTaskId = HttpTool.getParameter("id");
		String flag = HttpTool.getParameter("flag");
		Map paramsMap = super.buildSearch() ;
		String status = "2";
		
		//获取异常任务
		ErpSettleExcetaskJY erpSettleExcetaskJY = settlementTaskJYService.getExceSettleById(exceTaskId);
		
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("exceptionSettleTask",e);
		}
		//获取当前异常任务的所有结算客户
		List<ErpSettlementCustomerJY> erpSettlementCustomerJYs = settlementTaskJYService.getSettleCusByExceId(exceTaskId,status,page,paramsMap);
		
		page.setResults(erpSettlementCustomerJYs);
		HttpTool.setAttribute("exceSettlementTask", erpSettleExcetaskJY);
		HttpTool.setAttribute("flag", flag);
		
		return "exceptionNumInfo";
	}
	
	
	/**
	 * 修改
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-3 下午10:43:44
	 */
	public String updateSettlementTaskJY(){
		String id = HttpTool.getParameter("id");
		List<ErpSettlementCustomerJY> erpSettlementCustomerJYList = null;
		ErpSettlementTaskJY obj = null;
		if(id!=null&&id.length()>0){
			try {
				//TODO 修改逻辑有问题，待修改 mark DengYouming 2015-05-04 
				page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
				obj = settlementTaskJYService.getSettlementTask(id);
				erpSettlementCustomerJYList =  settlementTaskJYService.getCustomer(id);
				page.setResults(erpSettlementCustomerJYList);
			} catch (ParseException e) {
				log.error("updateSettlementTaskJY for ParseException",e);
			} catch (Exception e){
				log.error("updateSettlementTaskJY for Exception",e);
				System.out.println(e.getMessage());
			}
		}
		HttpTool.setAttribute("obj", obj);
		return "toAddPage";
	}
	
	/**
	 * 保存JY结算任务
	 * @return
	 * @author tangxing
	 * @date 2016-8-12下午3:53:44
	 */
	public String saveSettlementTaskJY(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//会员信息ID
		String detail = HttpTool.getParameter("detail");
		String settleTaskId = HttpTool.getParameter("id");
		
		String taskNo = HttpTool.getParameter("taskNo");
		String geCompany = HttpTool.getParameter("geCompany");
		String geCompanyId = HttpTool.getParameter("geCompanyId");
		String settlementTime = HttpTool.getParameter("settlementTime");
		
		ErpSettlementTaskJY erpSettlementTaskJY = new ErpSettlementTaskJY();
		JSONObject json = new JSONObject();
		
		erpSettlementTaskJY.setTaskNo(taskNo);
		erpSettlementTaskJY.setGeCompany(geCompany);
		erpSettlementTaskJY.setGeCompanyId(geCompanyId);
		
		if(settlementTime!=null){
			try {
				Date date = sdf.parse(settlementTime);
				erpSettlementTaskJY.setSettlementTime(date);
			} catch (ParseException e) {
				json.put("result","error");
				log.error("saveSettlementTaskJY"+e);
			}
		}else{
			erpSettlementTaskJY.setSettlementTime(new Date());
		}

					//默认为失败
		//从前台获取结算任务和会员信息的JSON数据
		//String data = HttpTool.getParameter("data");
		//Integer count = settlementTaskJYService.excelSize();
		
		
		/*JSONObject jsonData = JSONObject.fromObject(data);
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss"}));
		erpSettlementTaskJY = (ErpSettlementTaskJY) jsonData.toBean(jsonData, ErpSettlementTaskJY.class);*/
		//创建返回数据
		
		
		/*Map<String, Object> params = new HashMap<String, Object>();
		//业务操作
		if(getErpSettlementTaskJY()!=null){			
			params.put(ErpSettlementTaskJY.F_ERPSETTLEMENTTASKJY, getErpSettlementTaskJY());
		}
		if(detail!=null&&detail.length()>0){
			params.put("detail", detail);
		}
		
		params.put("settleTaskId", settleTaskId);*/
		
		try {
			
			//保存结算任务和处理相关的会员信息
			settlementTaskJYService.saveErpSettlementTaskJY(erpSettlementTaskJY);
			json.put("result","success");
		} catch (Exception e) {
			log.error("saveSettlementTaskJY",e);
			json.put("result","error");
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 保存结算任务(以及异常结算任务的保存)
	 * @return
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-4-26 下午5:36:17
	 */
	@SuppressWarnings("static-access")
	public String saveSettlementTaskJYTest(){
		boolean isOk = false;
		ErpSettleExcetaskJY erpSettleExcetaskJY = null;
		//从前台获取结算任务和会员信息的JSON数据
		String data = HttpTool.getParameter("data");
		Integer count = settlementTaskJYService.excelSize();
		
		//会员信息ID
		String detail = HttpTool.getParameter("detail");
		String settleTaskId = HttpTool.getParameter("id");
		JSONObject jsonData = JSONObject.fromObject(data);
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss"}));
		erpSettlementTaskJY = (ErpSettlementTaskJY) jsonData.toBean(jsonData, ErpSettlementTaskJY.class);
		//创建返回数据
		JSONObject json = new JSONObject();
		json.put("result","error");				//默认为失败
		
		Map<String, Object> params = new HashMap<String, Object>();
		//业务操作
		if(getErpSettlementTaskJY()!=null){			
			params.put(ErpSettlementTaskJY.F_ERPSETTLEMENTTASKJY, getErpSettlementTaskJY());
		}
		if(detail!=null&&detail.length()>0){
			params.put("detail", detail);
		}
		
		params.put("settleTaskId", settleTaskId);
		
		try {
			//保存结算任务和处理相关的会员信息
			isOk =settlementTaskJYService.saveErpSettlementTaskJY(params,count);
			if(isOk){
				json.put("result","success");
			}
		} catch (Exception e) {
			log.error("saveSettlementTaskJY",e);
			json.put("result","error");
		}
		
		List<ErpSettlementCustomerJY> settCusException = settlementTaskJYService.getSettleCustomerByTaskId(erpSettlementTaskJY.getId());//异常结算任务客户集合
		ErpSettlementTaskJY settleTask = settlementTaskJYService.getSettleTaskJYById(erpSettlementTaskJY.getId());
		
		Integer noFindNum=settlementTaskJYService.getNoFindNum(settCusException);						//条码不存在数量
		Integer branchCompanyNum=settlementTaskJYService.getBranchCompanyNum(settCusException);			//支公司数
		Integer errInfoNum=settlementTaskJYService.getErrInfoNum(settCusException);						//信息有误数量
		Integer setMealNum=settlementTaskJYService.getSetMealNum(settCusException);						//套餐数
		Integer codeNullNum=settlementTaskJYService.getCodeNullNum(settCusException);					//条码为空数量
		
		//保存比对的客户到异常任务
		if(settleTask!=null&&settCusException.size()>0){					//当前的结算批次成功save了，异常客户信息数量大于0条
			String s = "JSYC";
			String defTaskNum = settlementTaskJYService.defTaskNum();
			int ranNum = settlementTaskJYService.ranNum();
			Integer sum = settCusException.size();							//异常数量（总数）
			String exceSettleTaskNo = s+defTaskNum+ranNum;					//异常结算任务号
			//到新增页面时生成ID
			String id = UUID.randomUUID().toString().replace("-", "");
			erpSettleExcetaskJY = new ErpSettleExcetaskJY();
			
			erpSettleExcetaskJY.setId(id);									//ID
			erpSettleExcetaskJY.setExceptionTaskNo(exceSettleTaskNo);		//异常任务号
			erpSettleExcetaskJY.setTaskNo(settleTask.getTaskNo());			//当前的结算任务的任务号
			erpSettleExcetaskJY.setGeCompany(settleTask.getGeCompany());	//基因公司名字
			erpSettleExcetaskJY.setGeCompanyId(settleTask.getGeCompanyId());//基因公司ID
			erpSettleExcetaskJY.setAbnormalNum(sum);						//异常数量
			erpSettleExcetaskJY.setBranchCompanyNum(branchCompanyNum);
			erpSettleExcetaskJY.setNoFindNum(noFindNum);
			erpSettleExcetaskJY.setErrInfoNum(errInfoNum);
			erpSettleExcetaskJY.setCodeNullNum(codeNullNum);
			erpSettleExcetaskJY.setSetMealNum(setMealNum);
			
			erpSettleExcetaskJY.setCreateTime(new Date());					//创建时间
			erpSettleExcetaskJY.setIsDeleted(0);
			
			for (ErpSettlementCustomerJY erpSettlementCustomerJY : settCusException) {
				erpSettlementCustomerJY.setExceSettle_id(erpSettleExcetaskJY.getId());
			}
			
			if(isOk){			//结算任务保存成功才保存异常任务
				try {
					settlementTaskJYService.saveExcepSettleTask(erpSettleExcetaskJY);			//save异常结算任务
					settlementTaskJYService.updateExceSettCustomer(settCusException);			//设置异常任务的id
					json.put("result","success");
				} catch (Exception e) {
					log.error("saveSettlementTaskJY for saveExcepSettleTask and updateExceSettCustomer",e);
					json.put("result","error");
				}
			}
		}
		
		renderJson(json);
		return null;
	}
	
	/**
	 * 批量删除保险公司结算任务及其相关的核算表ErpSettlementCustomerJY记录
	 * 
	 * @author DengYouming
	 * @since 2016-4-26 下午7:14:17
	 */
	public void deleteBatch(){
		Map<String, Object> params = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		String idArr = HttpTool.getParameter("ids");
		json.put("result","error");
		if(idArr!=null&&idArr.length()>0){
			params.put(ErpSettlementTaskJY.F_ID, idArr);
			try {
				//批量删除
				settlementTaskJYService.delErpSettlementTaskJYBatch(params);
				json.put("result","success");
			} catch (Exception e) {	
				log.error("deleteBatch",e);
				json.put("result","error");
			}
		}
		renderJson(json);
	}
	
	/**
	 * 逻辑删除，根据保险公司结算任务ID删除ErpSettlementCustomerJY表的记录
	 * 
	 * @author DengYouming
	 * @since 2016-5-3 下午11:10:12
	 */
	public void deleteErpSettlementCustomerJYBatch(){
		JSONObject json = new JSONObject();
		json.put("result","error");
		//获取结算任务ID
		String settle_id = HttpTool.getParameter("id");
		System.out.println("settle_id: "+settle_id);
		if(settle_id!=null&&settle_id.length()>0){
			try {
				//批量删除
				settlementTaskJYService.deleteErpSettlementCustomerJYBatchBySettleId(settle_id);
				json.put("result","success");
			} catch (Exception e) {	
				log.error("deleteErpSettlementCustomerJYBatch",e);
				json.put("result","error");
			}
		}
		renderJson(json);
	}
	
	/**
	 * 显示列表(基因公司结算list)  财务
	 * @return String
	 * @author DengYouming
	 * @since 2016-4-26 下午7:34:48
	 */
	public String listSettlementByProperties(){
		Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
		BigDecimal bigDecimal = BigDecimal.ZERO;
		BigDecimal bigDecimal2 = BigDecimal.ZERO;
		String selectStatus = HttpTool.getParameter("selectStatus");			//状态查询下拉，选中值
		String selectGeCompanyId = HttpTool.getParameter("selectGeCompanyId");	//基因公司查询下拉，选中值
		
		List<ErpSettlementTaskJY> erpSettlementTaskJYs = settlementTaskJYService.getSettleTaskAll();	//获取所有结算任务
		if(erpSettlementTaskJYs!=null){
			for (ErpSettlementTaskJY erpSettlementTaskJY : erpSettlementTaskJYs) {
				if(erpSettlementTaskJY.getTotalAmount()!=null){
					bigDecimal = bigDecimal.add(erpSettlementTaskJY.getTotalAmount());	//累加所有结算任务的结算总额
				}
				if(erpSettlementTaskJY.getAccruedPayAmount()!=null){
					bigDecimal2 = bigDecimal2.add(erpSettlementTaskJY.getAccruedPayAmount());	//累加所有结算任务的已付金额
				}
			}
		}
        
//        searchMap.put("filter_and_isDelete_EQ_S", 0);//默认查询条件
//        searchMap.put("filter_and_isPrintTask_EQ_S", 0);//默认查询条件
		JSONObject json = new JSONObject();
		json.put("result","error");
        List<ErpSettlementTaskJY> list = null;
		try {
			
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			list = settlementTaskJYService.listSettleJyTaskTwoAll(page, searchMap);
			json.put("result","success");
		} catch (Exception e) {
			log.error("listSettlementByProperties",e);
		}        
        page.setResults(list); 
        renderJson(json);
        HttpTool.setAttribute("selectStatus", selectStatus);
        HttpTool.setAttribute("selectGeCompanyId", selectGeCompanyId);
        
        HttpTool.setAttribute("totalAmountCal", bigDecimal);
        HttpTool.setAttribute("actualTotalAmountCal", bigDecimal2);
        HttpTool.setAttribute("balanceCal", bigDecimal.subtract(bigDecimal2));
          
        return "listSettlement";
	}
	
	public String settleTaskDetail(){
		String settleTaskId = HttpTool.getParameter("id");
		Map searchMap = super.buildSearch();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ErpSettlementTaskJY settlementTaskJY = settlementTaskJYService.getSettlementTask(settleTaskId);
		
		try {
			
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (Exception e) {
			log.error("settleTaskDetail",e);
		}  
		
		List<ErpJYSettleTaskDetail> details = settlementTaskJYService.getJYSettleTaskDetailBysettleId(settleTaskId,page,searchMap);
		
		if(details!=null&&details.size()>0){
			HttpTool.setAttribute("settleTaskDetailValue", details.get(0));
		}else{
			HttpTool.setAttribute("settleTaskDetailValue", null);
		}
		
		HttpTool.setAttribute("settlementTaskJY", settlementTaskJY);
		HttpTool.setAttribute("currentTime", sdf.format(new Date()));
		return "settleTaskDetail";
	}
	
	/**
	 * 确认付款
	 * @return
	 * @author tangxing
	 * @date 2016-9-13下午2:56:20
	 */
	@SuppressWarnings("null")
	public void settleTask(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String data = HttpTool.getParameter("data");
		String settleTaskId = "";
		boolean isOK = false;
		BigDecimal bigDecimal = BigDecimal.ZERO;
		JSONObject object = new JSONObject();
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ErpJYSettleTaskDetail jySettleTaskDetail = new ErpJYSettleTaskDetail();
		object = JSONObject.fromObject(data);
		log.info("确认结算JSON--"+object.toString());
		
		int noPayAmount = 0;
		int sumAmount = 0;
		int currentAmount = 0;
		String noPayAmountStr = object.getString("noPayAmount");
		String sumAmountStr = object.getString("sumAmount");
		String currentAmountStr = object.getString("currentAmount");
		
		noPayAmount = Integer.parseInt(noPayAmountStr);
		sumAmount = Integer.parseInt(sumAmountStr);
		currentAmount=Integer.parseInt(currentAmountStr);
		
		settleTaskId = object.getString("settleTaskId");
		
		try {
			jySettleTaskDetail.setSettleTaskId(settleTaskId);
			jySettleTaskDetail.setGeCompany(object.getString("geCompany"));
			jySettleTaskDetail.setPayBank(object.getString("payBank"));
			jySettleTaskDetail.setPayTime(object.getString("payTime")!=null?sdf.parse(object.getString("payTime")):null);
			jySettleTaskDetail.setPayMode(object.getString("payMode"));
			jySettleTaskDetail.setOANo(object.getString("OANo"));
			jySettleTaskDetail.setCollectionCompany(object.getString("collectionCompany"));
			jySettleTaskDetail.setNote(object.getString("note"));
			jySettleTaskDetail.setCreateTime(new Date());
			jySettleTaskDetail.setCreateUserName(currentUser.getUserName());
			jySettleTaskDetail.setSumAmount(BigDecimal.valueOf(sumAmount));
			jySettleTaskDetail.setCurrentAmount(BigDecimal.valueOf(currentAmount));
			jySettleTaskDetail.setNoPayAmount(BigDecimal.valueOf(noPayAmount));
			settlementTaskJYService.save(jySettleTaskDetail);
			isOK = true;
		} catch (Exception e) {
			isOK = false;
			log.error("save ErpJYSettleTaskDetail--"+e);
		}
		
		//入库成功再对基因结算任务做修改
		if(isOK){
			ErpSettlementTaskJY settlementTaskJY = settlementTaskJYService.getSettlementTask(settleTaskId);
			List<ErpJYSettleTaskDetail> details = settlementTaskJYService.getJYSettleTaskDetailBysettleId(settleTaskId);
			try {
				if(settlementTaskJY!=null){
					settlementTaskJY.setNoPayAmount(BigDecimal.valueOf(noPayAmount));	//设置未付金额
					if(noPayAmount==0){		//全部支付就修改结算会员的信息
						//修改对应的客户
						List<ErpSettlementCustomerJY> customerJYs = settlementTaskJYService.getCustomerBySettleTaskId(settleTaskId);
						for (ErpSettlementCustomerJY erpSettlementCustomerJY : customerJYs) {		//将该结算任务的客户状态改为 2(已结算)
							if(erpSettlementCustomerJY.getStatus().equals("6")){	//套餐不匹配，不处理
								log.info("settleTask ErpSettlementCustomerJY status is 6 --"+erpSettlementCustomerJY.getCode()+","+erpSettlementCustomerJY.getName());
								continue;
							}
							erpSettlementCustomerJY.setStatus("2");
							List<ErpCustomer> customers = erpCustomerService.getCustomerByCode(erpSettlementCustomerJY.getCode());
							if(customers!=null&&customers.size()>0){
								for (ErpCustomer customer : customers) {
									customer.setSettlement_status("2");								//将Customer结算状态改为 2(已结算)
									customer.setUpdateTime(new Date());
									try {
										erpCustomerService.update(customer);
									} catch (Exception e) {
										log.error("confirmSettlement",e);
									}
								}
							}
							
							try {
								settlementTaskJYService.updateCustomerPrice(erpSettlementCustomerJY);
							} catch (Exception e) {
								log.error("confirmSettlement for updateCustomerPrice",e);
							}
						}
						
						settlementTaskJY.setStatus("4");	//全部支付
						settlementTaskJY.setCompletePayNum(customerJYs!=null?customerJYs.size():0);//完成付款人数
					}else{
						settlementTaskJY.setStatus("9");	//部分支付
					}
					if(details==null&&details.size()==0){	//付款次数
						settlementTaskJY.setPayCount(0);
					}else{
						for (ErpJYSettleTaskDetail erpJYSettleTaskDetail : details) {				//累计付款金额
							bigDecimal = bigDecimal.add(erpJYSettleTaskDetail.getCurrentAmount());
						}
						settlementTaskJY.setAccruedPayAmount(bigDecimal);
						settlementTaskJY.setPayCount(details.size());
						settlementTaskJY.setCollectionCompany(object.getString("collectionCompany"));
					}
					settlementTaskJY.setPayOperator(currentUser.getUserName());
					settlementTaskJYService.update(settlementTaskJY);
				}
				json.put("status", "200");
				json.put("message", "确认付款成功");
			}catch (Exception e) {
				log.error("settleTask--"+e);
				json.put("status", "300");
				json.put("message", "确认付款失败");
			}
		}
		
		renderJson(json);
		
		//return "settleTask";
	}
	
	/**
	 * 基因结算任务（运营权限）
	 * @return
	 * @author tangxing
	 * @date 2016-9-13上午9:51:27
	 */
	public String listSettlementOperate(){
		String selectStatus = HttpTool.getParameter("selectStatus");			//状态查询下拉，选中值
		String selectGeCompanyId = HttpTool.getParameter("selectGeCompanyId");	//基因公司查询下拉，选中值
		
        Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
//        searchMap.put("filter_and_isDelete_EQ_S", 0);//默认查询条件
//        searchMap.put("filter_and_isPrintTask_EQ_S", 0);//默认查询条件
		JSONObject json = new JSONObject();
		json.put("result","error");
        List<ErpSettlementTaskJY> list = null;
		try {
			
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			list = settlementTaskJYService.listSettleJyTask(page, searchMap);
			json.put("result","success");
		} catch (Exception e) {
			log.error("listSettlementByProperties",e);
		}        
        page.setResults(list); 
        renderJson(json);
        HttpTool.setAttribute("selectStatus", selectStatus);
        HttpTool.setAttribute("selectGeCompanyId", selectGeCompanyId);
		
		return "listSettlementOperate";
	}
	
	/**
	 * 基因结算任务（财务权限已完成）
	 * @return
	 * @author tangxing
	 * @date 2016-9-13下午5:40:24
	 */
	public String listSettlementComplete(){
        String selectStatus = HttpTool.getParameter("selectStatus");			//状态查询下拉，选中值
		String selectGeCompanyId = HttpTool.getParameter("selectGeCompanyId");	//基因公司查询下拉，选中值
		BigDecimal bigDecimal = BigDecimal.ZERO;
		BigDecimal bigDecimal2 = BigDecimal.ZERO;
		
        Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
//        searchMap.put("filter_and_isDelete_EQ_S", 0);//默认查询条件
//        searchMap.put("filter_and_isPrintTask_EQ_S", 0);//默认查询条件
        
        List<ErpSettlementTaskJY> erpSettlementTaskJYs = settlementTaskJYService.getSettleTaskAll();	//获取所有结算任务
		if(erpSettlementTaskJYs!=null){
			for (ErpSettlementTaskJY erpSettlementTaskJY : erpSettlementTaskJYs) {
				if(erpSettlementTaskJY.getTotalAmount()!=null){
					bigDecimal = bigDecimal.add(erpSettlementTaskJY.getTotalAmount());	//累加所有结算任务的结算总额
				}
				if(erpSettlementTaskJY.getAccruedPayAmount()!=null){
					bigDecimal2 = bigDecimal2.add(erpSettlementTaskJY.getAccruedPayAmount());	//累加所有结算任务的已付金额
				}
			}
		}
        
		JSONObject json = new JSONObject();
		json.put("result","error");
        List<ErpSettlementTaskJY> list = null;
		try {
			
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			list = settlementTaskJYService.listSettleJyTaskTwo(page, searchMap);
			json.put("result","success");
		} catch (Exception e) {
			log.error("listSettlementByProperties",e);
		}        
        page.setResults(list); 
        renderJson(json);
        HttpTool.setAttribute("selectStatus", selectStatus);
        HttpTool.setAttribute("selectGeCompanyId", selectGeCompanyId);
        
        HttpTool.setAttribute("totalAmountCal", bigDecimal);
        HttpTool.setAttribute("actualTotalAmountCal", bigDecimal2);
        HttpTool.setAttribute("balanceCal", bigDecimal.subtract(bigDecimal2));
        
        
		return "listSettlementComplete";
	}
	
	/**
	 * 列出所有数据(财务权限)
	 * @return String
	 * @author DengYouming
	 * @since 2016-4-27 下午2:25:38
	 */
	public String listSettlement(){
		page = new Page<ErpSettlementTaskJY>();
		page.setResults(settlementTaskJYService.listAll());
		
		return "listSettlement";
	}
	
	/**
	 * 转到添加客户信息页面
	 * @return
	 * @author DengYouming
	 * @since 2016-4-27 上午7:25:38
	 */
	public String toImportCustomerPage(){
		//获取结算任务ID
		String settlementTaskId = HttpTool.getParameter("id");		
		
		//把结算任务ID传到导入Excel页面
		HttpTool.setAttribute("settlementTaskId", settlementTaskId);
		HttpTool.setAttribute("navTabId", super.navTabId);

		return "importCustomer";
	}
	
	/**
	 * 导入客户信息excel，读取其中的内容
	 * @return
	 * @author DengYouming
	 * @since 2016-4-27 上午7:15:13
	 */
	public String uploadJYCheckInfo(){
		JSONObject json = new JSONObject();
		String filePath = HttpTool.getParameter("filePath");
		String id = HttpTool.getParameter("id");
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		//判断是否传了ID
		if(!StringUtils.isEmpty(id)){
			//获取总人数
			Integer total_person_num = 0;
			try {
				if(filePath.indexOf(".xlsx")!=-1){
					if(null!=affix){
						total_person_num = settlementTaskJYService.saveSettlementCustomerXlsx(affix,id);
					}else{
						json.put("statusCode", 300);
						json.put("message", "数据为空,导入失败");
						log.info("uploadJYCheckInfo import xlsx file is null");
					}
				}else if(filePath.indexOf(".xls")!=-1){
					if(null!=affix){
						total_person_num = settlementTaskJYService.saveSettlementCustomerXls(affix,id);
					}else{
						json.put("statusCode", 300);
						json.put("message", "数据为空,导入失败");
						log.info("uploadJYCheckInfo import xls file is null");
					}
				}
				if(total_person_num>0){
					settlementTaskJYService.updateStateSettleTask("1", id);
					ErpSettleTaskJYPceThread settleTaskJYPceThread = new ErpSettleTaskJYPceThread(id,currentUser);
					taskExecutor.submit(settleTaskJYPceThread);
					json.put("message", "导入成功");
					json.put("statusCode", 200);
					json.put("navTabId", super.navTabId);
					json.put("callbackType", "closeCurrent");
				}
			}catch (Exception e) {
				log.error("uploadJYCheckInfo",e);
				json.put("statusCode", 300);
				json.put("message", "导入失败");
			}
		}else{
			json.put("statusCode", 300);
			json.put("message", "导入失败");
		}
		//把ID传回
		HttpTool.setAttribute("id", id);
		renderJson(json);
		return null;

	}
	
	/**
	 * 导入的默认页面
	 * @return
	 * @author tangxing
	 * @date 2016-8-12下午4:07:28
	 */
	public String importCustomer(){
		String settlementTaskId = HttpTool.getParameter("settlementTaskId");
		String settleJYNo = HttpTool.getParameter("settleJYNo");
		
		String id = HttpTool.getParameter("id");
		
		HttpTool.setAttribute("id", id);
		HttpTool.setAttribute("settleJYNo", settleJYNo);
		HttpTool.setAttribute("settlementTaskId", settlementTaskId);
		return "importCustomer";
	}

	/**
	 * 生成结算单
	 * @return String
	 * @author DengYouming
	 * @since 2016-4-27 上午8:37:40
	 */
	public String buildSettlementTask(){
		return null;
	}
	
	/**
	 * 确认结算，则结算单不可更改
	 * @return String
	 * @author DengYouming
	 * @since 2016-4-27 上午8:38:34
	 */
	public String confirmSettlementTask(){

		Map<String,Object> params = new HashMap<String, Object>();
		try{
			settlementTaskJYService.confirmErpSettlementTaskJY(params);
		}catch(Exception e){
			log.error("confirmSettlementTask",e);
		}
		return "listSettlement";
	}
	
	
	/**
	 * 核对更改客户信息初始化页面
	 * @author tangxing
	 */
	public String toModifyCustomerInfo(){
		String id = HttpTool.getParameter("id");
		ErpSettlementCustomerJY customerJY = settlementTaskJYService.get(id);
		
		HttpTool.setAttribute("erpSettlementCustomerJY", customerJY);
		
		return "modifyCustomerInfo";
	}
	
	
	/**
	 * 核对更改客户信息
	 * @author tangxing
	 */
	public void modifyCustomerInfo(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		boolean flag = false;
		String name = HttpTool.getParameter("name");				//姓名
		String setmealName = HttpTool.getParameter("setmealName");	//套餐名字
		
		String setCustomerId = HttpTool.getParameter("setCustomerId");
		ErpSettlementCustomerJY customerJY = settlementTaskJYService.get(setCustomerId);	//根据ID获取客户对象
		
		try {
			customerJY.setName(name);
			customerJY.setSetmeal_name(setmealName);
			customerJY.setUpdate_user_name(currentUser.getUserName());
			customerJY.setUpdate_time(new Date());
			customerJY.setStatus("2");
			settlementTaskJYService.updateCustomerPrice(customerJY);
			flag = true;
		} catch (Exception e) {
			log.error("modifyCustomerInfo",e);
			flag = false;
		}
		
    	if(flag){
    		json.accumulate("statusCode", 200);
			json.accumulate("message", "修改成功");
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "refreshCurrent");
    	}else{
    		json.accumulate("statusCode", 300);
			json.accumulate("message", "修改失败");
    	}
    	renderJson(json);
	}
	
	
	/**
	 * 最后修改价格初始化页面
	 * @author tangxing
	 */
	public String toModifyPriceEnd(){
		String id = HttpTool.getParameter("id");
		String curtabid = HttpTool.getParameter("navTabId");
		ErpSettlementCustomerJY customerJY = settlementTaskJYService.get(id);
		
		BigDecimal setmealPrice = customerJY.getSetmeal_price();
		String setCustomerId = customerJY.getId();
		HttpTool.setAttribute("setmealPrice", setmealPrice);
		HttpTool.setAttribute("navTabId", curtabid);
		HttpTool.setAttribute("setCustomerId", setCustomerId);
		
		return "modifyPriceEnd";
	}
	
	/**
	 * 最后修改价格
	 * @author tangxing
	 */
	public void modifyPriceEnd(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		boolean flag = false;
		BigDecimal hisDB = null;	//修改前
    	BigDecimal currDB = null;	//修改后
		String setCustomerId = HttpTool.getParameter("setCustomerId");
		String currentPrice = HttpTool.getParameter("currentPrice");
		String curtabid = HttpTool.getParameter("navTabId");
		ErpSettlementCustomerJY customerJY = settlementTaskJYService.get(setCustomerId);	//根据ID获取客户信息对象
		
		try {
			currDB = new BigDecimal(currentPrice);
			customerJY.setSetmeal_price(currDB);
			customerJY.setUpdate_user_name(currentUser.getUserName());
			customerJY.setUpdate_time(new Date());
			settlementTaskJYService.updateCustomerPrice(customerJY);	//修改价格
			flag = true;
		} catch (Exception e) {
			log.error("modifyPriceEnd",e);
			flag = false;
		}
		
    	if(flag){
    		json.accumulate("statusCode", 200);
			json.accumulate("message", "修改成功");
			json.accumulate("navTabId", curtabid);
			json.accumulate("callbackType", "closeCurrent");
    	}else{
    		json.accumulate("statusCode", 300);
			json.accumulate("message", "修改失败");
    	}
    	renderJson(json);
	}
	
	/**
	 * 根据id获取结算任务的详细信息
	 */
	public String getSettlementById(){
		String settlementId = HttpTool.getParameter("id");
		
/*		int baCompanyNum=settlementTaskJYService.getBranchCompanyNum(settlementId);		//支公司数
		int comboNum = settlementTaskJYService.getComboNumBySettleId(settlementId);		//套餐数
		int excelNum=settlementTaskJYService.getExcleNum(settlementId);					//excel总数
		int exceptionNum=settlementTaskJYService.getExceptionNum(settlementId);			//异常数量
		int readNum=excelNum-exceptionNum;												//读取数量（excel总数-异常数量）
*/		
		
		ErpSettlementTaskJY settlementTaskJY = settlementTaskJYService.getSettlementTask(settlementId);
		
		/*HttpTool.setAttribute("settlementId", settlementId);
		HttpTool.setAttribute("baCompanyNum", baCompanyNum);
		HttpTool.setAttribute("comboNum", comboNum);
		HttpTool.setAttribute("excelNum", excelNum);
		HttpTool.setAttribute("readNum", readNum);
		HttpTool.setAttribute("exceptionNum", exceptionNum);*/
		
		HttpTool.setAttribute("erpSettlementTaskJY", settlementTaskJY);
    	
		return "handleSettlement";
	}
	
	/**
	 * 根据id获取结算任务的详细信息（符合成功匹配的）
	 */
	public String getSettlementByIdDetail(){
		String settlementId = HttpTool.getParameter("id");
		Map searchMap = super.buildSearch();
		
/*		int baCompanyNum=settlementTaskJYService.getBranchCompanyNum(settlementId);		//支公司数
		int comboNum = settlementTaskJYService.getComboNumBySettleId(settlementId);		//套餐数
		int excelNum=settlementTaskJYService.getExcleNum(settlementId);					//excel总数
		int exceptionNum=settlementTaskJYService.getExceptionNum(settlementId);			//异常数量
		int readNum=excelNum-exceptionNum;												//读取数量（excel总数-异常数量）
*/		
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			
		} catch (ParseException e) {
			log.error("getSettlementById",e);
		}
		
		ErpSettlementTaskJY settlementTaskJY = settlementTaskJYService.getSettlementTask(settlementId);
		
		//初始化客户信息集合
		List<ErpSettlementCustomerJY> seCustomerList = settlementTaskJYService.getCustomer(searchMap,page,settlementId);

		/*HttpTool.setAttribute("settlementId", settlementId);
		HttpTool.setAttribute("baCompanyNum", baCompanyNum);
		HttpTool.setAttribute("comboNum", comboNum);
		HttpTool.setAttribute("excelNum", excelNum);
		HttpTool.setAttribute("readNum", readNum);
		HttpTool.setAttribute("exceptionNum", exceptionNum);*/
		
		//结算任务
		HttpTool.setAttribute("erpSettlementTaskJY", settlementTaskJY);
		
		
    	
		return "settlementTaskDetail";
	}
	
	/**
	 * 基因结算任务详细的付款信息
	 * @return
	 * @author tangxing
	 * @date 2016-9-14下午4:52:57
	 */
	public String listJYSettleDetail(){
		String settleTaskId = HttpTool.getParameter("id");
		Map searchMap = super.buildSearch();
		
		try {
			
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (Exception e) {
			log.error("settleTaskDetail",e);
		}  
		
		List<ErpJYSettleTaskDetail> details = settlementTaskJYService.getJYSettleTaskDetailBysettleId(settleTaskId,page,searchMap);
		
		return "JYSettlementDetail";
	}
	
	/**
	 * 确认结算任务重新加载页面(只加载状态为可结算的)
	 * @author tangxing
	 */
	public void getSettlementPriceById(){
		JSONObject jsonObject = new JSONObject();
		String settlementId = HttpTool.getParameter("id");
		//初始化客户信息集合
		List<ErpSettlementCustomerJY> seCustomerList = settlementTaskJYService.showCustomerByStatus(settlementId,"2");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(ErpSettlementCustomerJY customerJY : seCustomerList){
			String censorshiptime = sdf.format(customerJY.getCensorship_time());
			customerJY.setCensorshiptime(censorshiptime);
			String receivetime = sdf.format(customerJY.getReceive_time());
			customerJY.setReceivetime(receivetime);
			String status = customerJY.getStatus();
			//0:初始状态;1:信息有误2:可结算3:已结算4:信息不存在5:异常
			if(("1").equals(status)){
				customerJY.setStatusView("信息有误");
			}
			if(("2").equals(status)){
				customerJY.setStatusView("可结算");
			}
			if(("3").equals(status)){
				customerJY.setStatusView("已结算");
			}
			if(("4").equals(status)){
				customerJY.setStatusView("信息不存在");
			}
			if(("5").equals(status)){
				customerJY.setStatusView("信息异常");
			}
		}
		jsonObject.put("settlementCustomer", seCustomerList);
		renderJson(jsonObject);
	}
	
	/**
	 * 根据不同状态查找客户信息
	 */
	public void showCustomerByStatus(){
		JSONObject jsonObject = new JSONObject();
		String transStatus = HttpTool.getParameter("status");//传递过来的状态
		String settlementId = HttpTool.getParameter("id");
		
		List<ErpSettlementCustomerJY> seCustomerList = settlementTaskJYService.showCustomerByStatus(settlementId,transStatus);
		//初始化客户信息集合
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(ErpSettlementCustomerJY customerJY : seCustomerList){
			String censorshiptime = sdf.format(customerJY.getCensorship_time());
			customerJY.setCensorshiptime(censorshiptime);
			String receivetime = sdf.format(customerJY.getReceive_time());
			customerJY.setReceivetime(receivetime);
			String status = customerJY.getStatus();
			//0:初始状态;1:信息有误2:可结算3:已结算4:信息不存在5:异常
			if(("1").equals(status)){
				customerJY.setStatusView("信息有误");
			}
			if(("2").equals(status)){
				customerJY.setStatusView("可结算");
			}
			if(("3").equals(status)){
				customerJY.setStatusView("已结算");
			}
			if(("4").equals(status)){
				customerJY.setStatusView("信息不存在");
			}
			if(("5").equals(status)){
				customerJY.setStatusView("信息异常");
			}
		}
		jsonObject.put("settlementCustomer", seCustomerList);
		renderJson(jsonObject);
	}
	
	/**
	 * 提交结算
	 * 
	 * @author tangxing
	 * @date 2016-6-13下午5:52:06
	 */
	public void submitSettleTask(){
		boolean flagOne = false;
		boolean flagTwo = false;
		JSONObject json = new JSONObject();
		String settleTaskId = HttpTool.getParameter("id");
		List<ErpCustomer> customers = new ArrayList<ErpCustomer>();
		ErpSettlementTaskJY erpSettlementTaskJY = settlementTaskJYService.getSettlementTask(settleTaskId);
		List<ErpSettlementCustomerJY> customerJYs = settlementTaskJYService.getCustomerBySettleTaskId(settleTaskId);
		
		for (ErpSettlementCustomerJY erpSettlementCustomerJY : customerJYs) {	//将该结算任务的客户状态改为 1(待结算)
			if(erpSettlementCustomerJY.getStatus().equals("6")){
				log.info("ErpSettlementCustomerJY status is 6 --"+erpSettlementCustomerJY.getCode()+","+erpSettlementCustomerJY.getName());
				continue;
			}
			erpSettlementCustomerJY.setStatus("1");
			
			customers = erpCustomerService.getCustomerByCode(erpSettlementCustomerJY.getCode());
			if(customers.size()>0){
				for (ErpCustomer customer : customers) {
					customer.setSettlement_status("1");								//将Customer结算状态改为 1(待结算)
					customer.setUpdateTime(new Date());
					
					try {
						erpCustomerService.update(customer);
						flagOne = true;
					} catch (Exception e) {
						log.error("submitSettleTask",e);
						flagOne = false;
					}
				}
			}
			
			try {
				settlementTaskJYService.updateCustomerPrice(erpSettlementCustomerJY);
				flagTwo = true;
			} catch (Exception e) {
				log.error("submitSettleTask for updateCustomerPrice",e);
				flagTwo = false;
			}
		}
		
		erpSettlementTaskJY.setStatus("3");
		erpSettlementTaskJY.setExpectPayNum(customerJYs!=null?customerJYs.size():0);	//预计结算人数
		if(customerJYs.size()==0){			//只修改结算任务
			try {
				settlementTaskJYService.updateSetTask(erpSettlementTaskJY);
				json.put("result","success");
			} catch (Exception e) {
				log.error("submitSettleTask for updateSetTask",e);
				json.put("result","error");
			}
		}
		
		if(flagOne&&flagTwo){
			try {
				settlementTaskJYService.updateSetTask(erpSettlementTaskJY);
				json.put("result","success");
			} catch (Exception e) {
				log.error("submitSettleTask for updateSetTask",e);
				json.put("result","error");
			}
		}
		
		renderJson(json);
	}
	
	/**
	 * 确认结算
	 * 
	 * @author tangxing
	 * @date 2016-6-13下午6:10:30
	 */
	public void confirmSettlement(){
		boolean flagOne = false;
		boolean flagTwo = false;
		JSONObject json = new JSONObject();
		String settleTaskId = HttpTool.getParameter("id");
		List<ErpCustomer> customers = new ArrayList<ErpCustomer>();
		ErpSettlementTaskJY erpSettlementTaskJY = settlementTaskJYService.getSettlementTask(settleTaskId);
		List<ErpSettlementCustomerJY> customerJYs = settlementTaskJYService.getCustomerBySettleTaskId(settleTaskId);
		
		for (ErpSettlementCustomerJY erpSettlementCustomerJY : customerJYs) {		//将该结算任务的客户状态改为 2(已结算)
			erpSettlementCustomerJY.setStatus("2");
			customers = erpCustomerService.getCustomerByCode(erpSettlementCustomerJY.getCode());
			if(customers.size()>0){
				for (ErpCustomer customer : customers) {
					customer.setSettlement_status("2");								//将Customer结算状态改为 2(已结算)
					
					try {
						erpCustomerService.updateInfo(customer);
						flagOne = true;
					} catch (Exception e) {
						log.error("confirmSettlement",e);
						flagOne = false;
					}
				}
			}
			
			try {
				erpSettlementTaskJY.setSettlementTime(new Date());				//结算时间
				settlementTaskJYService.updateCustomerPrice(erpSettlementCustomerJY);
				flagTwo = true;
			} catch (Exception e) {
				log.error("confirmSettlement for updateCustomerPrice",e);
				flagTwo = false;
			}
		}
		
		erpSettlementTaskJY.setStatus("4");
		
		if(customerJYs.size()==0){			//只修改结算任务
			try {
				settlementTaskJYService.updateSetTask(erpSettlementTaskJY);
				json.put("result","success");
			} catch (Exception e) {
				log.error("confirmSettlement for updateSetTask",e);
				json.put("result","error");
			}
		}
		
		if(flagOne&&flagTwo){
			try {
				settlementTaskJYService.updateSetTask(erpSettlementTaskJY);
				json.put("result","success");
			} catch (Exception e) {
				log.error("confirmSettlement for updateSetTask",e);
				json.put("result","error");
			}
		}
		
		renderJson(json);
	}
	
	/**
	 * 修改基因结算任务的详细默认页面
	 * @return
	 * @author tangxing
	 * @date 2016-9-18下午12:03:36
	 */
	public String toUpdateSettlementDetail(){
		String settleTaskDetailId = HttpTool.getParameter("id");//结算任务详细的Id
		ErpJYSettleTaskDetail settleTaskDetail = settlementTaskJYService.getJYSettleTaskDetailByDetailId(settleTaskDetailId);
		if(settleTaskDetail!=null){
			HttpTool.setAttribute("settleTaskDetail", settleTaskDetail);
		}else{
			HttpTool.setAttribute("settleTaskDetail", null);
		}
		return "updateSettlementDetail";
	}
	
	/**
	 * 修改付款详细
	 * 
	 * @author tangxing
	 * @date 2016-9-18下午2:13:11
	 */
	public void UpdateSettlementDetail(){
		JSONObject json = new JSONObject();
		String data = HttpTool.getParameter("data");
		JSONObject object = new JSONObject();
		boolean isOk = false;
		
		object = JSONObject.fromObject(data);
		ErpJYSettleTaskDetail settleTaskDetail = settlementTaskJYService.getJYSettleTaskDetailByDetailId(object.getString("id"));
		
		if(settleTaskDetail!=null){
			try {
				settleTaskDetail.setPayBank(object.getString("payBank"));
				settleTaskDetail.setPayMode(object.getString("payMode"));
				settleTaskDetail.setOANo(object.getString("OANo"));
				settleTaskDetail.setCollectionCompany(object.getString("collectionCompany"));
				settleTaskDetail.setNote(object.getString("note"));
				settleTaskDetail.setUpdateTime(new Date());
				settlementTaskJYService.update(settleTaskDetail);
				isOk = true;
				
			} catch (Exception e) {
				isOk = false;
				log.error("UpdateSettlementDetail update-- "+e);
				json.put("status","300");
				json.put("message","修改失败");
			}
			
			if(isOk){
				ErpSettlementTaskJY settlementTaskJY = settlementTaskJYService.getSettlementTask(settleTaskDetail.getSettleTaskId());
				settlementTaskJY.setOANo(object.getString("OANo"));
				settlementTaskJY.setCollectionCompany(object.getString("collectionCompany"));
				settlementTaskJY.setUpdateTime(new Date());
				settlementTaskJYService.update(settlementTaskJY);
				json.put("status","200");
				json.put("message","修改成功");
			}else{
				json.put("status","300");
				json.put("message","修改失败");
			}
		}else{
			json.put("status","300");
			json.put("message","修改失败");
		}
		
		renderJson(json);
	}
	
	/**
	 * 导出异常结算任务的excel
	 * @return
	 * @author tangxing
	 * @date 2016-6-14下午6:56:14
	 */
	public void createExSeFilePath(){
		JSONObject json = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		String exSettleTaskId = HttpTool.getParameter("id");
		String flag="";
		ErpSettleExcetaskJY erpSettleExcetaskJY = settlementTaskJYService.getExceSettlementTask(exSettleTaskId);
		List<ErpSettlementCustomerJY> customerJYs = settlementTaskJYService.getSettleCustomerByExSettTaskId(exSettleTaskId);
		
		String excelPath= settlementTaskJYService.createExSeFilePath(request, customerJYs,erpSettleExcetaskJY.getExceptionTaskNo(),flag);
		log.info("createExSeFilePath Excle path--"+excelPath);
		json.put("path",excelPath);
		
		renderJson(json);
	}
	
	/**
	 * 根据不同异常状态，导出excel
	 * 
	 * @author tangxing
	 * @date 2016-11-10上午11:01:21
	 */
	public void createExSeFilePathDetail(){
		JSONObject json = new JSONObject();
		List<ErpSettlementCustomerJY> customerJYs = new ArrayList<ErpSettlementCustomerJY>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String exSettleTaskId = HttpTool.getParameter("id");
		String status = HttpTool.getParameter("status");
		String flag=HttpTool.getParameter("flag");
		
		if(status!=null&&status.equals("1")){	//根据pdf_status查找
			customerJYs = settlementTaskJYService.getSettleCusByExceIdAnd(exSettleTaskId,status,null,null);
		}else{
			customerJYs = settlementTaskJYService.getSettleCusByExceId(exSettleTaskId,status,null,null);
		}
		
		ErpSettleExcetaskJY erpSettleExcetaskJY = settlementTaskJYService.getExceSettlementTask(exSettleTaskId);
		
		String excelPath= settlementTaskJYService.createExSeFilePath(request, customerJYs,erpSettleExcetaskJY.getExceptionTaskNo(),flag);
		log.info("createExSeFilePathDetail Excle path--"+excelPath);
		json.put("path",excelPath);
		
		renderJson(json);
	}
	
	/**
	 * 导出匹配成功和套餐不匹配数据
	 * 
	 * @author tangxing
	 * @date 2016-11-10上午11:03:23
	 */
	public void createSuccessAndComboMatch(){
		JSONObject json = new JSONObject();
		List<ErpSettlementCustomerJY> customerJYs = new ArrayList<ErpSettlementCustomerJY>();
		List<ErpSettlementCustomerJY> customerJYsTwo = new ArrayList<ErpSettlementCustomerJY>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String settleTaskId = HttpTool.getParameter("id");
		
		ErpSettlementTaskJY settlementTaskJY = settlementTaskJYService.getSettlementTask(settleTaskId);
		if(settlementTaskJY!=null){
			customerJYs = settlementTaskJYService.getSettlementCustomerByStatus(settleTaskId);
			customerJYsTwo = settlementTaskJYService.getSettlementCustomerByStatus(settleTaskId,"6");
			String excelPath = settlementTaskJYService.createExSeFilePathForSuccess(request, customerJYs, customerJYsTwo, settlementTaskJY.getTaskNo());
			log.info("createSuccessAndComboMatch Excle path--"+excelPath);
			json.put("path",excelPath);
			json.put("status","200");
		}else{
			json.put("status","300");
			json.put("message","导出失败！");
		}
		renderJson(json);
	}
	
	
	public ErpSettlementTaskJY getErpSettlementTaskJY() {
		return erpSettlementTaskJY;
	}

	public void setErpSettlementTaskJY(ErpSettlementTaskJY erpSettlementTaskJY) {
		this.erpSettlementTaskJY = erpSettlementTaskJY;
	}

	public ErpCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ErpCustomer customer) {
		this.customer = customer;
	}

	public ErpEvents getEvents() {
		return events;
	}

	public void setEvents(ErpEvents events) {
		this.events = events;
	}

	public ErpConference getConference() {
		return conference;
	}

	public void setConference(ErpConference conference) {
		this.conference = conference;
	}

	public File getAffix() {
		return affix;
	}

	public void setAffix(File affix) {
		this.affix = affix;
	}

	public String getAffixContentType() {
		return affixContentType;
	}

	public void setAffixContentType(String affixContentType) {
		this.affixContentType = affixContentType;
	}

	public String getAffixFileName() {
		return affixFileName;
	}

	public void setAffixFileName(String affixFileName) {
		this.affixFileName = affixFileName;
	}
	
}
