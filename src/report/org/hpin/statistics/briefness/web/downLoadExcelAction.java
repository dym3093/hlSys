package org.hpin.statistics.briefness.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;

@Namespace("/resource")
@Action("downLoadExcel")
@Results( {@Result(name = "success", 
                   type = "stream", 
          params = {"contentType" , "application/vnd.ms-excel" , 
		            "inputName" , "downLoadExcel" , 
		            "contentDisposition" , "attachment;filename=\"${filename}\"" , 
		            "bufferSize" , "8192"}),
}) 
public class downLoadExcelAction extends BaseAction {
    private String filename;  
    private String filepath;  
	private InputStream inputStream = null;
	/**
	 * 规避文件名中文乱码
	 * @return
	 */
	public String getFilename() {
		try{ 
			return new String(filename.getBytes() , "UTF-8") ;
		}catch(Exception e){
			return this.filename ;
		}
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	    public InputStream getDownLoadExcel() throws Exception{
           //inputStream=ServletActionContext.getServletContext().getResourceAsStream(filepath);
	    	File file = new File(filepath);  
	    	inputStream = new FileInputStream(file);  
	    	return inputStream;
		}
		
		public String execute() throws Exception {
			System.out.println("开始导出。。。") ;
			filename = HttpTool.getParameter("filename");
			filepath=ServletActionContext.getServletContext().getRealPath("/")
					+ "uploadFile"+File.separator+filename;
					
			System.out.println("filename---"+filename) ;
			System.out.println("filepath---"+filepath) ;
			return "success";	
		}
}
