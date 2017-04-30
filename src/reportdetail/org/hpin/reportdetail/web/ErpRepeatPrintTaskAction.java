
package org.hpin.reportdetail.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomerSync;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.hpin.reportdetail.entity.ErpRepeatPrintTask;
import org.hpin.reportdetail.entity.vo.ErpRepeatPrintEntity;
import org.hpin.reportdetail.service.ErpRepeatPrintTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * @author Carly
 * @since 2016年9月19日17:24:39
 * 重复打印任务
 */
@Namespace("/reportdetail")
@Action("erpRepeatPrintTask")
@Results({
    @Result(name="listRepeatPrintTask",location="/WEB-INF/reportdetail/listRepeatPrintTask.jsp"),
    @Result(name="showRepeatPrintTask",location="/WEB-INF/reportdetail/showRepeatPrintTask.jsp"),
    @Result(name="showUploadFile",location="/WEB-INF/reportdetail/uploadRepeatPrintTask.jsp"),
    
})  
@SuppressWarnings({"rawtypes","unchecked"})
public class ErpRepeatPrintTaskAction extends BaseAction{
	
	ErpRepeatPrintTaskService repeatService = (ErpRepeatPrintTaskService)SpringTool.getBean(ErpRepeatPrintTaskService.class);
	private ErpCustomerService customerService = (ErpCustomerService)SpringTool.getBean(ErpCustomerService.class);;
	
	private Logger logger = Logger.getLogger(ErpRepeatPrintTaskAction.class);
	private File file;
	private String fileFileName;
	private String fileContentType;
	
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	public void test(){
//		ErpReportMatchThread match = new ErpReportMatchThread("2016101902967");
//		taskExecutor.submit(match);
		try{
			//获取ErpCustomerSync表中所有状态为0的数据  
			List<ErpCustomerSync> list = customerService.findListCustomerSync();
			int count = 0;

			for(ErpCustomerSync customerSync : list){
				System.out.println(customerSync.getStatus());
				// ErpCustomerSync 调用接口同步数据
//				String retCode = customerService.getSyncXmlRetCode(customerSync);	//调用接口，返回的结果
//				logger.info("ErpCustomerSyncJob--id ="+customerSync.getId()+",retCode="+retCode);
				//获取返回的状态码  判断成功或者失败 修改ErpCustomerSync表中数据的状态码
//				if("0".equals(retCode)){
					customerSync.setStatus("6");
					count++;
//				}
			}
			logger.info("ErpCustomerSyncJob--successCount="+count);
		}catch(Exception e){
			logger.error("ErpCustomerSyncJob -- error :",e);
		}
	}
	/**
	 * @return 展示页面
	 */
	
	public String listRepeatPrintTask(){
		 Map searchMap = super.buildSearch(); //dao层加入了isdeleted=0
		 try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<ErpRepeatPrintTask> repeatPrintTaskList=repeatService.findByPage(page, searchMap);
			HttpTool.setAttribute("isPrint", HttpTool.getParameter("filter_and_isPrint_LIKE_S"));
			page.setResults(repeatPrintTaskList) ;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ErpRepeatPrintTask listRepeatPrintTask",e);
		}
		return "listRepeatPrintTask";
	}
	
	public String showUploadFile(){
		try {
			String code = HttpTool.getParameter("code");
			HttpTool.setAttribute("codes", code);
			page = new Page<ErpRepeatPrintEntity>(HttpTool.getPageNum(), HttpTool.getPageSize());
			repeatService.getPdfContentInfoByCode(page, code);
		} catch (Exception e) {
			logger.error("查询出错：",e);
		}
		return "showUploadFile";
	}
	
	/**
	 *  文件上传
	 */
	public void uploadFile(){
		JSONObject json = new JSONObject();
		String code = null;
		try{
			code = repeatService.getRepeatPrintTaskData(file);	
		}catch(Exception e){
			logger.error("ErpRepeatPrintTask uploadFile", e);
		}finally{
			file.delete();
			json.put("code", code);
			renderJson(json);
		}
	}
	
	public void confirmRepeatCodes(){
		JSONObject json = new JSONObject();
		try {
			String ids = HttpTool.getParameter("ids");
			repeatService.saveRepeatPrintTask(ids);
			json.put("result", "success");
		} catch (Exception e) {
			json.put("error", "error");
		}
		renderJson(json);
	}
	
	/**
	 * @return 从pdfcontent表中获取需要再次打印的数据
	 * 默认显示空，通过查询条件获取结果
	 */
	public String getRepeatPrintTask(){
		try {
			String code = "'"+HttpTool.getParameter("filter_and_code_LIKE_S")+"'";
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			repeatService.getPdfContentInfoByCode(page,code);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ErpRepeatPrintTask listRepeatPrintTask",e);
		}
		return "showRepeatPrintTask";
	}
	
	/**
	 * 	通过选中的信息将值放到erp_repeat_print_task表中
	 *  用于确认重复打印
	 */
	public void confirmRepeatPrintTask(){
		String ids = HttpTool.getParameter("ids");
		JSONObject json = new JSONObject();
		try {
			boolean flag = repeatService.addRepeatPrintTask(ids);
			if(flag){
				json.put("count", 1);
			}else{
				json.put("count", 0);
			}
		} catch (Exception e) {
			logger.error("ErpRepeatPrintTask confirmRepeatPrintTask",e);
			json.put("count", 0);
		}
		renderJson(json);
	}
	
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
