package org.hpin.reportdetail.job;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hpin.reportdetail.entity.ErpReportFileTask;
import org.hpin.reportdetail.service.ErpReportFileTaskService;
import org.hpin.reportdetail.service.ErpReportdetailPDFContentService;
import org.hpin.reportdetail.util.DealWithFileUtil;
import org.hpin.reportdetail.util.FileList;
import org.hpin.reportdetail.util.KeyFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 定时获取上传的报告文件
 * @author ybc
 * @date 2016-3-29
 */
public class ErpReportdetailJob {

	private String fileFromPath;
	
	private String fileToPath;
	
	private static final String EXCELFILE = "/excel/";
	
	private static final String PDFFILE = "/pdf/";
	
	private static final String JSONFILE = "/json/";
	
	private static Logger log = Logger.getLogger(ErpReportdetailJob.class);
	
	@Autowired
	private ErpReportdetailPDFContentService pdfService;
	@Autowired
	private ErpReportFileTaskService taskService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	public synchronized void execute(){
		long start = System.currentTimeMillis();
		//根据当前日期获取文件路径
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		
		String timeNow =sdf.format(new Date());
		//File fileFromDir = new File(fileFromPath + fileDirName);
		File fileFromDir = new File(fileFromPath);
		
		if (!fileFromDir.exists()) {
			log.info("report detail file from path is not exists , path :"+fileFromDir);
			return ;
		}
		
		if(0 == fileFromDir.listFiles().length){
			log.info("report detail file from path file is empty , path :"+fileFromDir);
			return ;
		}
		
		File[] fileDirs = fileFromDir.listFiles();
		
		if(null == fileDirs||0 == fileDirs.length){
			return;
		}
		
		//构建拷贝路径(文件名与提取的文件名)
		String copyToExcelDir = "";
		String copyToPdfDir = "";
		String copytToJsonDir = ""; //json文件
		//PDF文件数大量时采用单目录处理
		for(File curFileDir : fileDirs){
			boolean result = true;
			if(FileList.fileReader().contains(curFileDir.getName())){
				if(curFileDir.isDirectory() && 0==curFileDir.listFiles().length){
					log.info("report detail file from path file is empty , path :"+curFileDir);
					continue;
				}
				//以当前日期+时分秒为批次号
				String batchno = curFileDir.getName() + timeNow + createRandomNo();
				copyToExcelDir = fileToPath + curFileDir.getName() + File.separator + timeNow + EXCELFILE;
				copyToPdfDir = fileToPath + curFileDir.getName() + File.separator + timeNow + PDFFILE;
				copytToJsonDir = fileToPath + curFileDir.getName() + File.separator + timeNow + JSONFILE;
				String[] paths = {copyToExcelDir,copyToPdfDir,copytToJsonDir};
				
				log.info("ErpReportdetailJob run start , batchno :"+batchno);
				try{
					List<String> fileNameList = new ArrayList<String>();
					result = DealWithFileUtil.getInstance().dealFile(curFileDir,paths,fileNameList);
					if(result){
						File file = curFileDir;
						KeyFileFilter fileFilter = new KeyFileFilter();
						fileFilter.deleteDir(file);
					}
					fileNameList.clear();
				}catch(Exception e){
					log.error("ErpReportdetailJob Error :", e);
					break;
				}
				int pdfnum = 0;
				int jsonNum = 0;
				pdfnum = pdfService.dealPdfByPath(curFileDir.getName(),copyToPdfDir,batchno);
				if(new File(copytToJsonDir).isDirectory()){
					jsonNum = pdfService.dealJsonByPath(copytToJsonDir,batchno);
				}
				if(0!=pdfnum){
					try{
						//记录本批次的任务信息
						saveTaskInfo(batchno,pdfnum,jsonNum);
						//去重任务
						boolean isDoRepeat = pdfService.dealRepeatContents(batchno);
						log.info("deal repeat content by procedure , result:"+isDoRepeat);
						//利用线程池执行比对任务
						ErpPdfMatchThread match = new ErpPdfMatchThread(batchno);
						taskExecutor.submit(match);
						
					}catch(Exception e){
						log.error("ErpReportdetailJob deal contents error , batchno = "+batchno, e);
					}
				}
				log.info("ErpReportdetailJob run end , batchno : "+batchno+"; use time ："+(System.currentTimeMillis()-start));
			}
		}
		
	}
	
	/**
	 * 保存任务信息
	 */
	public void saveTaskInfo(String batchno,int pdftotal,int jsonTotal){
		ErpReportFileTask task = new ErpReportFileTask();
		task.setBatchno(batchno);
		task.setCreatedate(new Date());
		task.setPdftotal(pdftotal);
		task.setIsmatch(0);
		task.setIsdisrepeat(0);
		task.setJsonTotal(jsonTotal);
		taskService.save(task);
	}
	
	public boolean deleteDir(File fileDir,File file){
		if(fileDir.isDirectory()){
			String[] children = fileDir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(fileDir, children[i]),file);
				if (!success) {
					return false;
				}
			}
		}
		boolean flag = true;
		if(!file.equals(fileDir)){
			flag = fileDir.delete();
		}
		return flag;
	}
	
	//生成三位数的随机数
	private String createRandomNo(){
		Random r = new Random();
		int n = r.nextInt(1000);
		int k = 3 - String.valueOf(n).length();
		String v = "";
		for (; k > 0; k--) {
			v = v + "0";
		}
		return v + String.valueOf(n);
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
