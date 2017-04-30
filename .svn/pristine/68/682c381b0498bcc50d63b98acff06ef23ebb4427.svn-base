package org.hpin.bg.security.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
public class XheditorServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		String err = "";
		//用于显示在在线编辑器里，图片的路径
		String newFileName = "";
		//允许的图片格式
		String fileExt = "";
		String flag = request.getParameter("flag");
		System.out.println("flag:" + flag);
		long maxSize = 0;
		if("swf".equals(flag)){
			fileExt = "swf";
			maxSize = 1024*1024*10;
		}
		if("img".equals(flag)){
			fileExt = "jpg,jpeg,bmp,gif,png";
			maxSize = 1024*1024;
		}
		if("media".equals(flag)){
			fileExt = "wmv,avi,wma,mp3,mid";
			maxSize = 1024*1024*10;
		}
		if("zip".equals(flag)){
			fileExt = "zip,rar,txt,doc,docx,xls,xlsx,pdf";
			maxSize = 1024*1024*10;
		}
		try {
			List<FileItem> items = upload.parseRequest(request);
			Map fields = new HashMap();
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField())
					fields.put(item.getFieldName(), item.getString());
				else
					fields.put(item.getFieldName(), item);
			}
//			String folder=request.getSession().getServletContext().getRealPath("");
//			String savePath = folder+File.separator+"xheditorUpload"+File.separator;
//			File f1 = new File(savePath);
//			System.out.println("---上传保存的目录："+savePath);
//			if (!f1.exists()) {
//				f1.mkdirs();
//			}
			/*获取表单的上传文件*/
			FileItem uploadFile = (FileItem)fields.get("filedata");
			/*获取文件上传路径名称*/
			String fileNameLong = uploadFile.getName();
			/*获取文件扩展名*/
			/*索引加1的效果是只取xxx.jpg的jpg*/
			String extensionName = fileNameLong.substring(fileNameLong.lastIndexOf(".") + 1);
			System.out.println("extensionName:" + extensionName);
			/*检查文件类型*/
			if (("," + fileExt.toLowerCase() + ",").indexOf("," + extensionName.toLowerCase() + ",") < 0){
				printInfo(response, "不允许上传此类型的文件", "");
				return;
			}
			/*文件是否为空*/
			if (uploadFile.getSize() == 0){
				printInfo(response, "上传文件不能为空", "");
				return;
			}
			/*检查文件大小*/
			if (uploadFile.getSize() > maxSize){
				printInfo(response, "上传文件的大小超出限制,最大不能超过"+maxSize+"M", "");
				return;
			}
			/*文件存储的相对路径*/
			String saveDirPath = "/xheditorUpload/";
			/*文件存储在容器中的绝对路径*/
			String saveFilePath = request.getSession().getServletContext().getRealPath("") + saveDirPath;
			System.out.println("saveFilePath---"+saveFilePath);
			/*构建文件目录以及目录文件*/
			File fileDir = new File(saveFilePath);
			if (!fileDir.exists()) {fileDir.mkdirs();}
			/*重命名文件*/
			String filename = UUID.randomUUID().toString();
			File savefile = new File(saveFilePath + filename + "." + extensionName);
			/*存储上传文件*/ 
			uploadFile.write(savefile);
			//这个地方根据项目的不一样，需要做一些特别的定制。"/chinapan_bg"
			newFileName = request.getContextPath() +saveDirPath + filename + "." + extensionName;	
			System.out.println("newFileName---"+newFileName);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			newFileName = "";
			err = "错误: " + ex.getMessage();
		}
		printInfo(response, err, newFileName);	
	}
	public void printInfo(HttpServletResponse response, String err, String newFileName) throws IOException {
		PrintWriter out = response.getWriter();
		//String filename = newFileName.substring(newFileName.lastIndexOf("/") + 1);
		out.println("{\"err\":\"" + err + "\",\"msg\":\"" + newFileName + "\"}");
		out.flush();
		out.close();
	}

}
