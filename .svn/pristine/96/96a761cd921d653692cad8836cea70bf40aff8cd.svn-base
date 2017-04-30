package org.hpin.reportdetail.web;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;

/**
 * 文件上传进度获取Servlet
 * @author ybc
 * @CreateDate：2016.05.31
 */
@Namespace("/serverProcess")
@Action("getServerProcess")
public class ServerProcessAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(ServerProcessAction.class);
	
	private static final long serialVersionUID = 1L;
	public static final String OUTEXPORT = "serverExportProcess"; //导出常量使用
	public static final String INEXPORT = "serverInExportProcess"; //文件导入常量使用
	
	public void getProcess() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String serverProcess = (String)request.getSession().getAttribute("serverProcess");
		JSONObject jsonObject = new JSONObject();
		try {
			if(null!=serverProcess){
				serverProcess = String.valueOf(Integer.parseInt(serverProcess)+1);
			}else{
				serverProcess = "0";
			}
			request.getSession().setAttribute("serverProcess",serverProcess);
			jsonObject.put("serverProcess", serverProcess);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			renderJson(jsonObject);
		}
	}
	
	/**
	 * 导出获取session中的值;来作为判断;
	 */
	public void getSessionExportOutVal() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String exportProcess = (String)request.getSession().getAttribute(OUTEXPORT); //用于临时保存进度完成情况;
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isEmpty(exportProcess)) {
			exportProcess = "defuat";
		}
		jsonObject.put(OUTEXPORT, exportProcess);
		renderJson(jsonObject);
	}
	/**
	 * 导入获取session中的值;来作为判断;
	 */
	public void getSessionExportInVal() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String exportProcess = (String)request.getSession().getAttribute(INEXPORT); //用于临时保存进度完成情况;
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isEmpty(exportProcess)) {
			exportProcess = "defuat";
		}
		jsonObject.put(INEXPORT, exportProcess);
		renderJson(jsonObject);
	}
	
	/**
	 * 获取session中指定值;
	 * 用于判断导出的时候,导出完成后关闭遮挡层;通用;
	 * create by henry.xu 20160818;
	 * 
	 */
	public void getExportProcess() {
		renderJson(dealProcess(OUTEXPORT, ServletActionContext.getRequest()));
	}
	
	/**
	 * 获取session中指定值;
	 * 用于判断导出的时候,导入完成后关闭遮挡层;通用;
	 * create by henry.xu 20160818;
	 * 
	 */
	public void getExportInProcess() {
		renderJson(dealProcess(INEXPORT, ServletActionContext.getRequest()));
	}
	
	public void delExportOutProcess() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String exportProcess = (String)request.getSession().getAttribute(OUTEXPORT); //用于临时保存进度完成情况;
		//当serverExportProcess为1的时候标示该excel已导出;所以移除掉参数;
		if("1".equals(exportProcess)) {
			request.getSession().removeAttribute(OUTEXPORT);
		}
	}
	
	public void delExportInProcess() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String exportProcess = (String)request.getSession().getAttribute(INEXPORT); //用于临时保存进度完成情况;
		//当serverExportProcess为1的时候标示该excel已导出;所以移除掉参数;
		if("1".equals(exportProcess)) {
			request.getSession().removeAttribute(INEXPORT);
		}
	}
	
	/**
	 * 处理进度条导入导出关闭问题;
	 * @param export 导入/导出
	 * @param request
	 * @return
	 */
	private JSONObject dealProcess(String export, HttpServletRequest request) {
		
		String navTabId = HttpTool.getParameter("navTalId");
		
		JSONObject jsonObject = new JSONObject();
		String exportProcess = (String)request.getSession().getAttribute(export); //用于临时保存进度完成情况;
		
		//如果不存在,重新设置; 当点击导出按钮时,设置该导出判断值为0;
		if(StringUtils.isEmpty(exportProcess)) {
			exportProcess = "0";
			request.getSession().setAttribute(export,exportProcess);
		}
		
		//当serverExportProcess为1的时候标示该excel已导出;所以移除掉参数;
		if("1".equals(exportProcess)) {
			request.getSession().removeAttribute(export);
			if(INEXPORT.equals(export)) { //当导入时,同事删除进度数;
				String serverProcess = (String)request.getSession().getAttribute("serverProcess");
				if(null!=serverProcess){
					request.getSession().removeAttribute("serverProcess");
				}
			}
		}
		
		//设置到返回值中;
		jsonObject.put(export, exportProcess);
		jsonObject.put("navTabId", navTabId);
		
		return jsonObject;
	}
	
	public void delProcess(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String serverProcess = (String)request.getSession().getAttribute("serverProcess");
		if(null!=serverProcess){
			request.getSession().removeAttribute("serverProcess");
		}
		
	}
	
	public void testProcess(){
		JSONObject jsonObject = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		String serverProcess = (String)request.getSession().getAttribute("serverProcess");
		jsonObject.put("serverProcess", serverProcess);
		renderJson(jsonObject);
	}
	
}
