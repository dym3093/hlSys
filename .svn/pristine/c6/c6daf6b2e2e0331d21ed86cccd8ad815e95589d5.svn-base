package org.hpin.reportdetail.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpReportdetailManagement;
import org.hpin.reportdetail.service.ErpReportPrintManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Carly
 * @since 2016年9月5日17:38:54
 * 报告打印管理
 */
@Namespace("/reportdetail")
@Action("erpReportPrintManagement")
@Results({
    @Result(name="reportDetailManagement",location="/WEB-INF/reportdetail/reportDetailManagement.jsp"),
    @Result(name="showNoReportPrint",location="/WEB-INF/reportdetail/showNoReportPrint.jsp"),
    
})
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ErpReportPrintManagementAction extends BaseAction{
	
	ErpReportPrintManagementService printManagementService = (ErpReportPrintManagementService)SpringTool.getBean(ErpReportPrintManagementService.class);
	Logger logger = Logger.getLogger(ErpReportPrintManagementAction.class);
	
	private File file;
	private String fileFileName;
	private String fileContentType;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	/**
	 * @return 
	 */
	public String reportDetailManagement(){
		try {
			Map<String,Object> searchMap = buildSearch();
			page = new Page<ErpReportdetailManagement>(HttpTool.getPageNum(),HttpTool.getPageSize());
			printManagementService.findByPages(page, searchMap);
			HttpTool.setAttribute("tabId", "reportDetailManagement");
		} catch (Exception e) {
			logger.error("ErpReportPrintManagementAction reportDetailManagement",e);
		}
		
		return "reportDetailManagement";
	}
	
	/**
	 * @return 上传文件
	 */
	public void uploadFile(){
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		int count = 0;
		try{
			List<ErpReportdetailManagement> list = printManagementService.getReportPrintData(file,fileFileName,user.getAccountName());	
			for(ErpReportdetailManagement management:list){
				String name = management.getName();
				String code = management.getCode();
				String gender = management.getGender();
				if(printManagementService.isRepeatData(name, code, gender)==0){
					printManagementService.save(management);
					count++;
				}
			}
			json.put("batchNo", list.get(0).getBatchNo());
			json.put("count", count);
			json.put("count2", list.size()-count);
		}catch(Exception e){
			logger.error("ErpReportPrintManagement uploadFile",e);
			count--;
			json.put("count", count);
		}finally{
			file.delete();
			renderJson(json);
		}
	}
	
	/**
	 * 确认该打印批次为不打印，设置状态为1
	 * 取消该该打印批次，设置状态为2
	 */
	public void updateState(){
		String batchNo = HttpTool.getParameter("batchNo");
		int count = Integer.valueOf(HttpTool.getParameter("count"));
		int count2 = 0;
		if(HttpTool.getParameter("isConfirm").equals("1")){
			count2 = printManagementService.updateState1(batchNo);
		}else{
			count2 = printManagementService.updateState2(batchNo);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", 1);
		if(count!=count2){
			jsonObject.put("state", 0);
		}
		renderJson(jsonObject);
	}
	
	/**
	 * @return 展示新增页面
	 */
	public String showNoReportPrint(){
		String tabId = HttpTool.getParameter("tabId");
		HttpTool.setAttribute("tabId", tabId);
		return "showNoReportPrint";
	}
	
	public void addNoReportPrint(){
		JSONObject json = new JSONObject();
		try {
			User user = (User)HttpTool.getSession().getAttribute("currentUser");
			String name = HttpTool.getParameter("name");
			String code = HttpTool.getParameter("code");
			String gender = HttpTool.getParameter("gender");
			if(printManagementService.isRepeatData(name, code, gender)>=1){
				json.put("count", 2);
			}else{
				Date date = new Date();
				String batchNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);//批次号
				ErpReportdetailManagement management = new ErpReportdetailManagement();
				management.setBatchNo(batchNo);
				management.setCode(code);
				management.setName(name);
				management.setGender(gender);
				management.setCreateUser(user.getAccountName());
				management.setCreateTime(date);
				management.setIsDelete("0");
				management.setState("1");
				printManagementService.save(management);
				json.put("count", 1);
			}
		} catch (Exception e) {
			logger.error("ErpReportPrintManagement addNoReportPrint",e);
			json.put("count", 0);
		}
		renderJson(json);
	}

//	public void cs(){
//		JSONObject json = new JSONObject();
//		ErpReportMatchThread thread = new ErpReportMatchThread("JY62");
//		try {
//			taskExecutor.submit(thread);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		json.put("count", 1);
//		renderJson(json);
//	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
	
}
