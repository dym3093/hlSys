package org.hpin.base.accessories.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.accessories.entity.TawCommonsAccessories;
import org.hpin.base.accessories.entity.TawCommonsAccessoriesConfig;
import org.hpin.base.accessories.service.TawCommonsAccessoriesConfigService;
import org.hpin.base.accessories.service.TawCommonsAccessoriesManagerCOSService;
import org.hpin.base.accessories.service.TawCommonsAccessoriesManagerFileuploadService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.SystemConstant;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StaticMehtod;
import org.hpin.common.widget.pagination.Page;

@Namespace("/hpin")
@Action("acessesoriesconfig")
@Results( {
	@Result(name = "listTawCommonsAccessoriesConfig", location = "/WEB-INF/pages/accessories/tawCommonsAccessoriesConfig/tawCommonsAccessoriesConfigList.jsp"),
	@Result(name = "addTawCommonsAccessoriesConfig", location = "/WEB-INF/pages/accessories/tawCommonsAccessoriesConfig/tawCommonsAccessoriesConfigForm.jsp")
}
)
public  class TawCommonsAccessoriesConfigAction extends BaseAction {
	

	
	static String upFileRootDir = null;
	
	private TawCommonsAccessoriesConfig tawCommonsAccessoriesConfig;
	
	public TawCommonsAccessoriesConfig getTawCommonsAccessoriesConfig() {
		return tawCommonsAccessoriesConfig;
	}

	public void setTawCommonsAccessoriesConfig(
			TawCommonsAccessoriesConfig tawCommonsAccessoriesConfig) {
		this.tawCommonsAccessoriesConfig = tawCommonsAccessoriesConfig;
	}

	TawCommonsAccessoriesConfigService  tawCommonsAccessoriesConfigService = (TawCommonsAccessoriesConfigService)SpringTool.getBean(TawCommonsAccessoriesConfigService.class);
	TawCommonsAccessoriesManagerCOSService tawCommonsAccessoriesManagerCOSService = (TawCommonsAccessoriesManagerCOSService)SpringTool.getBean(TawCommonsAccessoriesManagerCOSService.class);
	TawCommonsAccessoriesManagerFileuploadService tawCommonsAccessoriesManagerFileuploadService = (TawCommonsAccessoriesManagerFileuploadService)SpringTool.getBean(TawCommonsAccessoriesManagerFileuploadService.class);


	/***************************************************************************
	 * 删除附件配置信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sherry
	 */
	public String deleteTawCommonsAccessoriesConfig()
			throws Exception {
		JSONObject json = new JSONObject();
		
		try {
			
		// Exceptions are caught by ActionExceptionHandler
			tawCommonsAccessoriesConfigService.deleteIds(ids);

    		json.accumulate("statusCode", 200);
    		json.accumulate("callbackType", "refreshCurrent");
    		json.accumulate("message", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("statusCode", 300);
    		json.accumulate("message", "删除失败");
		}
		
    	renderJson(json);
    	return null;
	}

	/**
	 * 编辑附件配置信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 
	 */
	public String addTawCommonsAccessoriesConfig()
			throws Exception {
		String appId = id;
		if (appId != null && !appId.equals("")) {
			 tawCommonsAccessoriesConfig = (TawCommonsAccessoriesConfig) tawCommonsAccessoriesConfigService.findById(appId);
		}
		// 获取系统应用模块信息
		List applicationTag = tawCommonsAccessoriesConfigService.getApplicationInfo();
		HttpTool.setAttribute("applicationList", applicationTag);
		HttpTool.setAttribute("appcode", appId);
		return "addTawCommonsAccessoriesConfig";
	}

	/**
	 * 保存附件配置信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 
	 */
	public String saveTtawCommonsAccessoriesConfig()
			throws Exception {
		
		try {
			// Extract attributes and parameters we will need
			String allowFileType = "";
			String[] allowFileTypes =HttpTool.getParameterValues("allowFileType");
			   
			for (int i = 0; i < allowFileTypes.length; i++) {
				allowFileType = allowFileType + "," + allowFileTypes[i];
			}
			if (allowFileType.indexOf(",") == 0)
				allowFileType = allowFileType.substring(1);
			tawCommonsAccessoriesConfig.setAllowFileType("");
			tawCommonsAccessoriesConfig.setAllowFileType(allowFileType);
			tawCommonsAccessoriesConfigService.saveTawCommonsAccessoriesConfig(tawCommonsAccessoriesConfig);

		} catch (Exception e) {
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
			renderJson(json);
			return null;
		}
		
		JSONObject json = new JSONObject();
		json.accumulate("statusCode", 200);
		json.accumulate("message", "操作成功");
		json.accumulate("navTabId", "menu_57");
		json.accumulate("callbackType", "closeCurrent");
		renderJson(json);
		return null;
		
	}

	/**
	 * 查询附件配置信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 
	 */
	public String listTawCommonsAccessoriesConfig()
			throws Exception {
		
		try {
			page = new Page(HttpTool.getPageNum() , 30);
			Map paramsMap = buildSearch();
			List<TawCommonsAccessoriesConfig>  tawCommonsAccessoriesConfigList = tawCommonsAccessoriesConfigService.findByPage(page, paramsMap);
			
			page.setResults(tawCommonsAccessoriesConfigList);
		} catch (Exception e) {
			
		}
		
		return "listTawCommonsAccessoriesConfig";
	}

	/**
	 * 查询附件配置信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 
	 */
	public String download()
			throws Exception {
		

	try {
			String id = StaticMehtod.nullObject2String(HttpTool
					.getParameter("idd"));
			TawCommonsAccessories accessories = (TawCommonsAccessories) tawCommonsAccessoriesManagerFileuploadService
					.getTawCommonsAccessories(id);
			if (accessories != null) {
				String fileCnName = accessories.getAccessoriesCnName();
				String fileName = accessories.getAccessoriesName();
				String rootFilePath = tawCommonsAccessoriesManagerCOSService.getFilePath(
								accessories.getAppCode());
				String path = rootFilePath + fileName;
				// 若文件系统中的文件不存在则给出提示
				File file = new File(path);
				if (!file.exists()) {
					HttpTool.setAttribute("noteInfo", "文件系统中的文件不存在，可能已经被删除！");
					String fileIdList = StaticMehtod.nullObject2String(HttpTool
							.getParameter("filelist"));
					HttpTool.setAttribute("fileIdlist", fileIdList);
					return "viewaccess";
				}
				else {
					HttpTool.setAttribute("noteInfo", "");
				}
				InputStream inStream = new FileInputStream(path);
				//
				HttpServletResponse response = ServletActionContext.getResponse() ;
				response.reset();
				response.setContentType("application/x-msdownload");
				response.setCharacterEncoding("GB2312");

				fileCnName = new String(fileCnName.getBytes("gbk"), "iso8859-1");
				response.addHeader("Content-Disposition",
						"attachment;filename=" + fileCnName);
				// 循环取出流中的数据
				byte[] b = new byte[128];
				int len;
				while ((len = inStream.read(b)) > 0)
					response.getOutputStream().write(b, 0, len);
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 默认执行方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author
	 */
	public String unspecified()
			throws Exception {
		return "search";
	}
	
	
	public String loadImage() {
		String picPath = HttpTool.getParameter("picPath");
		if( StringUtils.isEmpty(picPath) ) return null;
		
		if(upFileRootDir == null){
			upFileRootDir = SystemConstant.getSystemConstant("photo_upload_rootdir");
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType( "image/jpeg" );
		response.setContentType("text/html;charset=utf-8");
		ServletOutputStream output = null;		
		
		String osPicPath = upFileRootDir + picPath ;
		
		File file = new File(osPicPath);
		if(! file.exists()){
			return null;
		}
		byte tmp[] = new byte[1024];
		int j=0;
		java.io.InputStream fin = null;
		
		try {
			fin = new java.io.FileInputStream( file );
			output = response.getOutputStream();
			while((j = fin.read( tmp )) !=-1){
				output.write(tmp, 0, j);
			}
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			if(fin != null){
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(output != null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
