package org.hpin.physical.web;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.physical.entity.PhyReport;
import org.hpin.physical.service.PhyReportService;
import org.hpin.physical.service.ReportInfoService;
import org.hpin.physical.thread.PhysicalReportThread;
import org.hpin.physical.util.DiseaseMapComparator;
import org.hpin.physical.util.ProjectMapComparator;
import org.hpin.physical.vo.PhyReportQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Namespace("/physical")
@Action("phyReport")
@Results({
    @Result(name="phyReport",location="/WEB-INF/physical/listPhysical.jsp"),
    @Result(name="listImportRecord",location="/WEB-INF/physical/importRecordList.jsp"), 
    @Result(name="importReport",location="/WEB-INF/physical/importPhysicalReport.jsp"), 
    @Result(name="reportTestInfo",location="/WEB-INF/physical/reportTestInfo.jsp"), 
    @Result(name="listPhyReportExcelFile",location="/WEB-INF/physical/listPhyReportExcelFile.jsp"),
    @Result(name="listReportInfoAll",location="/WEB-INF/physical/listReportInfoAll.jsp"),
})  
public class PhyReportAction extends BaseAction {

	private Logger log = Logger.getLogger(PhyReportAction.class);

	//PhyReportService service = (PhyReportService) SpringTool.getBean(PhyReportService.class);
	@Autowired
	PhyReportService service;
	
	@Autowired
	ReportInfoService infoService;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	private File affixExcel;
    private String affixContentType;
    private String affixFileName;
    
    private PhyReportQueryObject queryObject;
    
    /**
     * 查看该code的详细检测
     * @return
     * @author tangxing
     * @date 2016-7-8上午11:58:14
     */
    public String getReportInfo(){
    	String geneCode = HttpTool.getParameter("geneCode");
    	JSONObject json = new JSONObject();
    	try {
    		Map<String, List<List<String>>> diseaseMap = new LinkedHashMap<String, List<List<String>>>();			//用做保存排序好的disease Map
    		List<LinkedHashMap<String, List<Map<String, String>>>> projectMap = new ArrayList<LinkedHashMap<String,List<Map<String,String>>>>();	//用做保存排序好的project Map
    		PhyReport report = infoService.getPhyReportByCodeAndIsSuccess(geneCode);
    		
			Map<String, List<List<String>>> diseaseTemp =infoService.getDiseaseInfoTwo(geneCode);
    		//Map<String, List<List<String>>> diseaseTemp = infoService.getDiseaseAndItemByDisCode(geneCode);//以前版本
			//List<HashMap<String, List<Map<String, String>>>> projectTemp = infoService.getProjectAndItemByDisCode(geneCode);//以前版本
    		List<HashMap<String, List<Map<String, String>>>> projectTemp = infoService.getProjectInfoTwo(geneCode);
			diseaseMap = DiseaseMapComparator.comparatorDiseaseMap(diseaseTemp);	//排序
			
			//diseaseMap = infoService.formatDiseaseMap(diseaseMap);					//格式化
			
			projectMap = ProjectMapComparator.mapComparator(projectTemp);			//排序
			/*
			//前一版本
			List<Entry<String, List<List<String>>>> tempList = new ArrayList<Entry<String, List<List<String>>>>(diseaseMap.entrySet());
			Collections.sort(analysisList,AnalyseTypeComparator.getInstance());
			for(Entry<String, List<List<String>>> entry: analysisList){
				String str = entry.getKey().toString();
				List<List<String>> lists = entry.getValue();
				diseaseTemp.put(str, lists);
			}
			List<Entry<String, String>> specificPhyList = new ArrayList<Entry<String, String>>(projectMap.entrySet());
			Collections.sort(specificPhyList,PhyTypeComparator.getInstance());
			for(Entry<String, String> entry: specificPhyList){
				String strKey = entry.getKey().toString();
				String strValue = entry.getValue().toString();
				projectTemp.put(strKey, strValue);
			}*/
			
			HttpTool.setAttribute("sex", report.getUserSex());
			HttpTool.setAttribute("diseaseMap", diseaseMap);
	    	HttpTool.setAttribute("projectMap", projectMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	renderJson(json);
    	return "reportTestInfo";
    }
    
    /**
     * 常规报告的查看
     * @return
     * @author tangxing
     * @date 2016-10-21上午11:35:48
     */
    public String getReportInfoRoutine(){
    	String geneCode = HttpTool.getParameter("geneCode");
    	JSONObject json = new JSONObject();
    	try {
    		Map<String, List<List<String>>> diseaseMap = new LinkedHashMap<String, List<List<String>>>();			//用做保存排序好的disease Map
    		List<LinkedHashMap<String, List<Map<String, String>>>> projectMap = new ArrayList<LinkedHashMap<String,List<Map<String,String>>>>();	//用做保存排序好的project Map
    		PhyReport report = infoService.getPhyReportByCodeAndIsSuccess(geneCode);
    		
			Map<String, List<List<String>>> diseaseTemp =infoService.getDiseaseInfoRoutineTwo(geneCode);
    		//Map<String, List<List<String>>> diseaseTemp = infoService.getDiseaseAndItemByDisCode(geneCode);//以前版本
			//List<HashMap<String, List<Map<String, String>>>> projectTemp = infoService.getProjectAndItemByDisCode(geneCode);//以前版本
    		List<HashMap<String, List<Map<String, String>>>> projectTemp = infoService.getProjectInfoRoutineTwo(geneCode);
			diseaseMap = DiseaseMapComparator.comparatorDiseaseMap(diseaseTemp);	//排序
			
			//diseaseMap = infoService.formatDiseaseMap(diseaseMap);					//格式化
			
			projectMap = ProjectMapComparator.mapComparator(projectTemp);			//排序
			/*
			//前一版本
			List<Entry<String, List<List<String>>>> tempList = new ArrayList<Entry<String, List<List<String>>>>(diseaseMap.entrySet());
			Collections.sort(analysisList,AnalyseTypeComparator.getInstance());
			for(Entry<String, List<List<String>>> entry: analysisList){
				String str = entry.getKey().toString();
				List<List<String>> lists = entry.getValue();
				diseaseTemp.put(str, lists);
			}
			List<Entry<String, String>> specificPhyList = new ArrayList<Entry<String, String>>(projectMap.entrySet());
			Collections.sort(specificPhyList,PhyTypeComparator.getInstance());
			for(Entry<String, String> entry: specificPhyList){
				String strKey = entry.getKey().toString();
				String strValue = entry.getValue().toString();
				projectTemp.put(strKey, strValue);
			}*/
			
			HttpTool.setAttribute("sex", report.getUserSex());
			HttpTool.setAttribute("diseaseMap", diseaseMap);
	    	HttpTool.setAttribute("projectMap", projectMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	renderJson(json);
    	return "reportTestInfo";
    }
    
	/**
	 * 列表默认页
	 * @return
	 * @author tangxing
	 * @date 2016-6-23下午6:01:14
	 */
	public String defaultPage(){
		
		return "phyReport";
	}
	
	
	/**
	 * 根据条件获取报告
	 * @return
	 * @author tangxing
	 * @date 2016-6-23下午5:54:34
	 */
	public String getPhyReport(){
		Map searchMap = super.buildSearch();
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(searchMap.size()>0){						//没有输入条件，就查不到结果
			service.getPhyReport(page, searchMap);
		}
		
		return "phyReport";
	}
	
	/**
	 * 所有的导入报告
	 * @return
	 * @author tangxing
	 * @date 2016-6-23下午6:34:15
	 */
	public String listImportRecord(){
		Map searchMap = super.buildSearch();
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("listImportRecord",e);
		}
		
		service.listPhyReport(page, searchMap);
		
		return "listImportRecord";
	}
	
	/**
	 * 导入Excel页面
	 * @return
	 * @author tangxing
	 * @date 2016-6-23下午6:35:35
	 */
	public String importReport(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String importTime = sdf.format(new Date());
		/*Map searchMap = super.buildSearch();
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("listPhyReport Page", e);
			e.printStackTrace();
		}
		
		service.findByPage(page, searchMap);*/
		
		HttpTool.setAttribute("importTime", importTime);
		return "importReport";
	}
	
	/**
	 * 导入Excel数据
	 * 
	 * @author tangxing
	 * @date 2016-6-23下午8:52:47
	 */
	public void uploadReportInfo(){
		String time = HttpTool.getParameter("time");
		Integer size = 0;
		JSONObject json = new JSONObject();
		//获取总人数
		Integer total_person_num = 0;
		TreeSet<String> resultSet = new TreeSet<String>();
//		json.put("statusCode", 300);
//		json.put("message", "导入失败");
		
		try {
			//page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			if(null!=affixExcel){
				resultSet = service.saveReport(affixExcel,time);
				size = service.excelSize();
				/*if(result!=null){
					total_person_num = result.size();		//比对好的结果的List大小
				}*/
			}else{
				json.put("statusCode", 300);
				json.put("message", "数据为空,导入失败");
			}
			
			if(size<=50000&&size>0){
				PhysicalReportThread thread = new PhysicalReportThread(resultSet);
				taskExecutor.submit(thread);
				
				json.put("message", "导入成功");
				json.put("statusCode", 200);			
	//			json.put("navTabId", super.navTabId);
				json.put("callbackType", "closeCurrent");
			}else{
				json.put("statusCode", 300);
				json.put("message", "超过一万条数据,导入失败！");
				System.out.println("超过一万条");
			}
			//page.setResults(result);
		} catch (Exception e) {
			log.error("uploadReportInfo",e);
			json.put("statusCode", 300);
			json.put("message", "导入失败");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		//把ID传回
//		HttpTool.setAttribute("navTabId", super.navTabId);
		HttpTool.setAttribute("page", page);
		renderJson(json);
	}
	
	public void seeReport(){
		String temp = "";
		String str = "ymdata";
		JSONObject json = new JSONObject();
		String geneCode = HttpTool.getParameter("geneCode");
		String reportPath = HttpTool.getParameter("reportPath");
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), 
				url.length()).toString();
		
		
		if(reportPath!=null&&reportPath.length()>0){
			temp = reportPath.substring(reportPath.indexOf(str)+str.length(), reportPath.length());
		}
		
		String seePath = contextUrl+temp;
		log.info("see report path--"+seePath);
		json.put("path", seePath);
		renderJson(json);
		
		
		/*JSONObject json = new JSONObject();
		String geneCode = HttpTool.getParameter("geneCode");
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), 
				url.length()).toString();
		
		String path = "";
		String temp = "";
		List<PhyReport>  report = service.getPhyReportByCode(geneCode);
		if(report.size()>0){
			path = report.get(0).getReportPath();
		}
		
		if(path.length()>0&&path!=null){
			temp = path.substring(2, path.length());
		}
		
		String seePath = contextUrl+temp;
		json.put("path", seePath);
		renderJson(json);*/
	}
	
	/**
	 * 分页查询excel文件路径类
	 * @return
	 * @author tangxing
	 * @date 2016-12-6下午6:04:21
	 */
	public String listPhyReportExcelFile(){
		Map searchMap = super.buildSearch();
		String selectStatus = HttpTool.getParameter("selectStatus");			//状态查询下拉，选中值
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("listImportRecord",e);
		}
		service.findByPageExcelFile(page,searchMap);
		HttpTool.setAttribute("selectStatus", selectStatus);
		
		return "listPhyReportExcelFile";
	}
	
	/**
	 * 1+X报告日报数据
	 * @return 
	 * @author tangxing
	 * @date 2016-12-7下午3:07:47
	 */
	public String listReportInfoAll(){
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			service.findReportAll(page,queryObject);
		}catch (Exception e) {
			log.error("PhyReportAction listReportInfoAll--"+e);
		}	
		
		return "listReportInfoAll";
	}

	public File getAffixExcel() {
		return affixExcel;
	}

	public void setAffixExcel(File affixExcel) {
		this.affixExcel = affixExcel;
	}

	public PhyReportQueryObject getQueryObject() {
		return queryObject;
	}

	public void setQueryObject(PhyReportQueryObject queryObject) {
		this.queryObject = queryObject;
	}
	
}
