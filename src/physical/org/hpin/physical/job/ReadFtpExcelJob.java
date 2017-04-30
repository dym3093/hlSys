package org.hpin.physical.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.common.util.ExcelUtils;
import org.hpin.physical.entity.PhyReportExcelFile;
import org.hpin.physical.entity.PhyReportTemp;
import org.hpin.physical.service.PhyReportService;
import org.hpin.reportdetail.util.ReportWithFileUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 读取ftp上1+X excel文件定时任务
 * @author tangxing
 * @date 2016-12-5下午4:46:47
 */
public class ReadFtpExcelJob {

	private Logger log = Logger.getLogger(ReadFtpExcelJob.class);
	
	private String fileFromPath;	//ftp地址
	
	private String fileToPath;		//输出路径
	
	String dirPath = "";			//用于存放excel文件的上一层时间文件夹
	
	//List<String> pathList = new ArrayList<String>();
	List<JSONObject> pathList = new ArrayList<JSONObject>();
	@Autowired
	private PhyReportService reportService;
	
	public synchronized  void execute() {
		
		//File fileFromDir = new File("D:/pdfTest");
		File fileFromDir = new File(fileFromPath);
		
		if (!fileFromDir.exists()) {
			log.info("ReadFtpExcelJob detail file from path is not exists , path :"+fileFromDir);
			return ;
		}
		
		if(0 == fileFromDir.listFiles().length){
			log.info("ReadFtpExcelJob detail file from path file is empty , path :"+fileFromDir);
			return ;
		}
		
		File[] fileDirs = fileFromDir.listFiles();
		
		if(null == fileDirs||0 == fileDirs.length){
			return;
		}
		
		//根据当前日期获取文件路径
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		
		String timeNow =sdf.format(new Date());
		String copyToPdfDir = "";	//文件复制的输出地址
		for(File curFileDir : fileDirs){
			//copyToPdfDir = "D:/Excel"+ File.separator + curFileDir.getName()+File.separator;
			copyToPdfDir = fileToPath+ File.separator + curFileDir.getName()+File.separator;
			log.info("ReadFtpExcelJob copy to file path -- "+copyToPdfDir);
			String[] paths = {copyToPdfDir};
			
			try {
				List<String> fileNameList = new ArrayList<String>();
				List<JSONObject> result = this.dealFile(curFileDir,paths,fileNameList);
				if(result!=null&&result.size()>0){
					for (JSONObject obj : result) {
						if(obj!=null){
							String filePath = obj.getString("filePath");
							String dir = obj.getString("dir");
							this.createFileObj(filePath,dir);
						}
					}
					pathList.clear();		//读取完一个文件夹中的文件，清理List中的路径
				}
				fileNameList.clear();
				
			} catch (Exception e) {
				log.error("ReadFtpExcelJob execute"+e);
			}
			
			
		}
	}
	
	public boolean deleteDir(File fileDir){
		if(fileDir.isDirectory()){
			String[] children = fileDir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(fileDir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return fileDir.delete();
	}
	
	
	/**
	 * 保存文件路径对象和入库PhyReport
	 * @param pdfPath
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-12-6上午11:43:35
	 */
	public void createFileObj(String pdfPath,String dir) throws Exception{
		//File pdfDir = new File(pdfPath);
		//File files[]=pdfDir.listFiles();
		PhyReportExcelFile excelFile = null;
		TreeSet<String> codeSets = new TreeSet<String>();
		if(StringUtils.isNotEmpty(pdfPath)){
			String status = "";		//excel状态
			excelFile = new PhyReportExcelFile();
			File currFile = new File(pdfPath);
			String path = currFile.getAbsolutePath();	//路径
			String fileName = currFile.getName();		//文件名字
			log.info("createFileObj file path--"+path);
			log.info("createFileObj file Name--"+fileName);
			excelFile.setCreateTime(new Date());
			if(StringUtils.isEmpty(dir)){
				excelFile.setFileName(fileName);
			}else{
				excelFile.setFileName(dir+File.separator+fileName);
			}
			
			excelFile.setFilePath(path);
			
			//解析出文件名中的人数
			if(fileName.indexOf("_")!=-1){	//如果文件名包含 "_"
				String tempStr = fileName.substring(0, fileName.indexOf("."));
				String num = tempStr.substring(tempStr.indexOf("_")+1, tempStr.length());	//该excel人数
				excelFile.setNum(num);		//人数
			}
			
			try {
				//入库PhyReport对象，并生成报告
				//List<Map<String, String>> result = ExcelUtils.importSettlementExcel(new File(path));
				List<Map<String, String>> result = new ArrayList<Map<String,String>>();
				if(path.indexOf(".xlsx")!=-1){
					result = ExcelUtils.importSettlementExcel(new File(path));
				}else if(path.indexOf(".xls")!=-1){
					result = ExcelUtils.xlsExcel(new File(path));
				}
				Set<PhyReportTemp> phyReportTemps = reportService.createPhyReportsObj(result);	//读取出来的List
				codeSets = reportService.matchPhyReportTemp(phyReportTemps);
				if(codeSets!=null&&codeSets.size()>0){
					status = "1";	//Excel正确
				}else{
					status = "0";
				}
				
			} catch (Exception e) {
				status = "0";				//Excel错误
				log.info("createFileObj exception--"+e);
				throw e;
			}
			excelFile.setStatus(status);	//读取excel异常、数据为null 都为错误
			
			reportService.save(excelFile);	//入库excel文件路径对象
		}
	}
	
	/**
	 * 拷贝报告文件到相应目录
	 * @param fileDir
	 * @param paths
	 * @return 异常次数
	 * @throws Exception
	 */
	public List<JSONObject> dealFile(File fileDir,String[] paths,List<String> fileNameList){
		log.info("fileDir name -- "+fileDir.getName());
		boolean result = true;
		final int strSubNum = 9;
		
		File files[]=fileDir.listFiles();
		if(null==files){
			return null;
		}
		for(File currFile : files){
			try{
				if(currFile.isFile()){
					String name = currFile.getName();
					String suffix = name.substring(name.lastIndexOf(".")+1,name.length());
					log.info("ReadFtpExcelJob excel name -- " + name);
					//判断文件名是否重名
					if(fileNameList.contains(name)){
						//重名文件在名字后加时间
						SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
						Random r=new Random();
						name = name.substring(0, name.lastIndexOf(".")) + sdf.format(new Date())+r.nextInt(10)+ "." +suffix;
					}
					
					/* ****
					说明：如果在excel文件前有多个空文件夹(如:"yyyymmdd/"),成员变量"dirPath"会导致创建多层文件夹。
						所以只截取最后一层文件夹。
					**** */
					if(suffix.equals("xlsx")||suffix.equals("xls")){
						String[] strArray = dirPath.split("/");
						if(strArray.length==1){	//只有一层文件夹，比如："20161212/" 或者  没有上一级文件夹目录
							File fileToExcelDir = new File(paths[0]+dirPath);
							if(!fileToExcelDir.exists()){
								fileToExcelDir.mkdirs();
							}
							String path = copyReportFile(currFile,paths[0]+dirPath,name);
							if(StringUtils.isNotEmpty(path)){
								JSONObject jsonObject = new JSONObject();
								jsonObject.accumulate("filePath", path);
								jsonObject.accumulate("dir", strArray[0]);
								pathList.add(jsonObject);
							}
							dirPath = "";		//清空路径
						}else if(strArray.length>1){
							//String subString = dirPath.substring(dirPath.length()-strSubNum,dirPath.length());	//截取最后一层 文件夹("20161212/")
							String subString = strArray[strArray.length-1]+File.separator;		//取最后一层文件夹
							log.info("ReadFtpExcelJob dealFile method substring dirPath -- "+subString);
							File fileToExcelDir = new File(paths[0]+subString);
							if(!fileToExcelDir.exists()){
								fileToExcelDir.mkdirs();
							}
							String path = copyReportFile(currFile,paths[0]+subString,name);
							if(StringUtils.isNotEmpty(path)){
								//pathList.add(path);
								JSONObject jsonObject = new JSONObject();
								jsonObject.accumulate("filePath", path);
								jsonObject.accumulate("dir", subString);
								pathList.add(jsonObject);
							}
							dirPath = "";		//清空路径
						}
						
					}
					currFile.delete();
					fileNameList.add(name);
				}else{
					//dirPath =dirPath+currFile.getName()+File.separator;
					dirPath =dirPath+currFile.getName()+"/";
					dealFile(currFile,paths,fileNameList);
				}
				
			}catch(Exception e){
				log.error("ReportWithFileUtil dealFile--"+e);
				result = false;
				continue;
			}
		}
		return pathList;
	}
	
	/**
	 * 拷贝文件到指定目录
	 * @return 
	 * @throws Exception 
	 */
	public String copyReportFile(File currFile,String toPath,String filename) throws Exception{
		FileInputStream fin = null;
		FileOutputStream fout = null;
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		String finalPath = "";
		if(null==filename){
			filename = currFile.getName();
		}
		
		try{
			log.info("ReportWithFileUtil-copyReportFile currFile:"+currFile);
			fin = new FileInputStream(currFile);
			log.info("ReportWithFileUtil-copyReportFile toPath:"+toPath);
			log.info("ReportWithFileUtil-copyReportFile filename:"+filename);
			finalPath = toPath+filename;
			log.info("ReportWithFileUtil-copyReportFile toPath+filename:"+finalPath);
			fout = new FileOutputStream(finalPath);
			inChannel = fin.getChannel();
			outChannel = fout.getChannel();
			outChannel.transferFrom(inChannel, 0, inChannel.size());
			
		}catch(Exception e){
			log.error("ReportWithFileUtil copyReportFile--"+e);
			throw e;
		}finally{
			try{
				outChannel.close();
				inChannel.close();
				fin.close();
				fout.close();
			}catch(Exception e){
				log.error("ReportWithFileUtil copyReportFile close stream--"+e);
			}
			
		}
		return finalPath;
	}
	
	public String getFileFromPath() {
		return fileFromPath;
	}

	public void setFileFromPath(String fileFromPath) {
		this.fileFromPath = fileFromPath;
	}

	public String getFileToPath() {
		return fileToPath;
	}

	public void setFileToPath(String fileToPath) {
		this.fileToPath = fileToPath;
	}
	
}
