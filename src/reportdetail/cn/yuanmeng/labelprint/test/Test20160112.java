package cn.yuanmeng.labelprint.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.hpin.common.core.web.BaseAction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.ftp.FtpCommon;
import cn.ftp.ListFtpFile;
import cn.poi.FileList;
import cn.yuanmeng.labelprint.service.impl.ReportDetailServiceImpl;

public class Test20160112 extends BaseAction {
	public static String userdir=System.getProperty("user.dir");

	public static void main(String[] args) {
//		String strPath=System.getProperty("user.dir")+"\\localdir\\";
//		strPath=System.getProperty("user.dir")+"\\";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String strDate=sdf.format(new Date());
		//ftp远程源目录位置
//		String remoteDir = "/20160111";
		//本地源目录位置
		String localDirA="z:/"+"20160121";
		localDirA="d:/sz";
		//本地目录目标位置
		String localDirB="z:/A/"+strDate;
		localDirB="d:/test";
//		String fileDirPath = "z:/A/"+strDate+"/";
		String username="liubaoguan";
		String password="liubaoguan"; 
		String fileDirPath = "ftp://"+username+":"+password+"@ftp.healthlink.cn/A/"+strDate+"/";
		
		FileList fl=new FileList(localDirA);
		List<String> xlsFilePathNameList=fl.getXlsFileListByDir();
		for (String xlsFilePathName : xlsFilePathNameList) {
			fl.moveFileToDir(xlsFilePathName, localDirB);
		}
		List<String> pdfFilePathNameList=fl.getPDFFileListByDir();
		for (String pdfFilePathName : pdfFilePathNameList) {
			fl.moveFileToDir(pdfFilePathName, localDirB);
		}
		//获取本地excel文件
		ReportDetailServiceImpl reportDetailServiceImpl=new ReportDetailServiceImpl(localDirB);
		reportDetailServiceImpl.xlsToTable(fileDirPath);
		
	}
	public void test() {
//		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
//		ServletContext servletContext = webApplicationContext.getServletContext();
//		String filepath=servletContext.getRealPath("/");
//		System.out.println(filepath);
//		String strPath=System.getProperty("user.dir")+"\\localdir\\";
//		strPath=System.getProperty("user.dir")+"\\";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String strDate=sdf.format(new Date());
		
		//ftp远程源目录位置
//		String remoteDir = "/20160111";
				
		//本地源目录位置
		String localDirA="z:\\"+"localDirA";
		
		//本地目录目标位置
		String localDirB="z:\\localDirB\\"+strDate;
		
/**/
		String fileDirPath = "z:\\localDirB/"+strDate+"/";
		
		//下载
//		FtpCommon ftpUtil = new FtpCommon();
//		ftpUtil.downloadAll(remoteDir, localDirA);
		
//		FileList fl=new FileList(localDirA);
//		List<String> xlsFilePathNameList=fl.getXlsFileListByDir();
//		for (String xlsFilePathName : xlsFilePathNameList) {
//			fl.moveFileToDir(xlsFilePathName, localDirB);
//		}
//		List<String> pdfFilePathNameList=fl.getPDFFileListByDir();
//		for (String pdfFilePathName : pdfFilePathNameList) {
//			fl.moveFileToDir(pdfFilePathName, localDirB);
//		}
//		
//		//获取本地excel文件
//		ReportDetailServiceImpl reportDetailServiceImpl=new ReportDetailServiceImpl(localDirB);
//		reportDetailServiceImpl.xlsToTable(fileDirPath);
		
	}
}
