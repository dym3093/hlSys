package org.hpin.reportdetail.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 1+X读取excel Util
 * @author tangxing
 * @date 2016-12-6上午11:01:57
 */

public class ReportWithFileUtil {
	
	private ReportWithFileUtil(){}
	
	private static ReportWithFileUtil Util = null;
	
	private static Logger log = Logger.getLogger(DealWithFileUtil.class);

	/**
	 * 线程安全单例模式
	 * @return DealWithPdfUtil
	 */
	public static synchronized ReportWithFileUtil getInstance(){
		if(Util == null){
			Util = new ReportWithFileUtil();
			log.info("DealWithPdfUtil create instance");
		}
		return Util;
	}
	
	/**
	 * 拷贝报告文件到相应目录
	 * @param fileDir
	 * @param paths
	 * @return 异常次数
	 * @throws Exception
	 */
	public static boolean dealFile(File fileDir,String[] paths,List<String> fileNameList){
		log.info("fileDir name -- "+fileDir.getName());
		
		boolean result = true;
		File files[]=fileDir.listFiles();
		if(null==files){
			return result;
		}
		for(File currFile : files){
			
			try{
				if(currFile.isFile()){
					String name = currFile.getName();
					String suffix = name.substring(name.lastIndexOf(".")+1,name.length());
					log.info("name -- " + name+","+suffix);
					//判断文件名是否重名
					if(fileNameList.contains(name)){
						//重名文件在名字后加时间
						SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
						Random r=new Random();
						name = name.substring(0, name.lastIndexOf(".")) + sdf.format(new Date())+r.nextInt(10)+ "." +suffix;
					}
					if(suffix.equals("xlsx")||suffix.equals("xls")){
						File fileToExcelDir = new File(paths[0]);
						if(!fileToExcelDir.exists()){
							fileToExcelDir.mkdirs();
						}
						copyReportFile(currFile,paths[0],name);
					}
					/*if(suffix.equals("doc")||suffix.equals("docx")){
						File fileToExcelDir = new File(paths[0]);
						if(!fileToExcelDir.exists()){
							fileToExcelDir.mkdirs();
						}
						copyReportFile(currFile,paths[0],name);
					}*/
					/*if(suffix.equals("pdf")){
						File fileToPdfDir = new File(paths[0]);
						if(!fileToPdfDir.exists()){
							fileToPdfDir.mkdirs();
						}
						copyReportFile(currFile,paths[0],name);
					}*/
					currFile.delete();
					fileNameList.add(name);
				}else{
					dealFile(currFile,paths,fileNameList);
				}
			}catch(Exception e){
				log.error("ReportWithFileUtil dealFile--"+e);
				result = false;
				continue;
			}
		}
		return result;
	}
	
	/**
	 * 拷贝文件到指定目录
	 * @throws Exception 
	 */
	public static void copyReportFile(File currFile,String toPath,String filename) throws Exception{
		FileInputStream fin = null;
		FileOutputStream fout = null;
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		
		if(null==filename){
			filename = currFile.getName();
		}
		
		try{
			log.info("ReportWithFileUtil-copyReportFile currFile:"+currFile);
			fin = new FileInputStream(currFile);
			log.info("ReportWithFileUtil-copyReportFile toPath:"+toPath);
			log.info("ReportWithFileUtil-copyReportFile filename:"+filename);
			String finalPath = toPath+filename;
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
	}
	
}
