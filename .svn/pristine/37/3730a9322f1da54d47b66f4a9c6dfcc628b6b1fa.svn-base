package org.hpin.physical.thread;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.common.core.SpringContextHolder;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.orm.daoWrapper.DaoSupport;
import org.hpin.physical.entity.PhyReport;
import org.hpin.physical.service.ReportInfoService;
import org.hpin.physical.util.AddSouthPdfUtil;
import org.hpin.physical.util.CardiocerebralPdfUtil;
import org.hpin.physical.util.PhysicalEtcPdfUtil;
import org.hpin.physical.util.PhysicalPrintPdfUtil;

/**
 * 生成体检报告线程
 * @author ybc
 * @since 2016-06-29
 */
public class PhysicalReportThread implements Runnable{

	private Logger log = Logger.getLogger(PhysicalReportThread.class);
	
	private TreeSet<String> geneCode;
	
	private DaoSupport dao = null;
	
	public PhysicalReportThread(TreeSet<String> geneCode) {
		this.geneCode = geneCode;
		if (null == dao) {
			dao = (DaoSupport) SpringTool.getBean("DaoSupport");
		}
	}
	
	@Override
	public void run() {
		log.info("PhysicalReportThread start genecode size : "+ geneCode.size());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
		SimpleDateFormat reportDateFormat = new SimpleDateFormat("yyyyMMdd");
		String nowPathName  = dateFormat.format(new Date());
		String printNowPath = nowPathName+"print";
		String cardiocerebralNowPath = nowPathName+"/"+"cardiocerebral";	//避免JSP转义
		ReportInfoService infoService = new ReportInfoService();
		Map<String, String> no8Map = null;
		Map<String, String> no4Map = null;
		Map<String, String> pdfPathMap = null;
		if(!geneCode.isEmpty()){
			
			try{
				Iterator<String> iter = geneCode.iterator();
				while (iter.hasNext()) {
					no8Map = new HashMap<String, String>();
					no4Map = new HashMap<String, String>();
					pdfPathMap = new HashMap<String, String>();
					String reportName = "";		//PDF文件名字
					String pdfPath = "";		//PDF的路径
					String pageStr = "";		//生成PDF页数
					String isSuccess = "";		//是否成功生成PDF
					String pdfPathStr = "";		//PDF页数是否正常
					String isMerge = "";		//是否需要合并的PDF
					boolean flag = false;
					
					String code = iter.next();
					log.info("set report code---"+code);
					String filePath = nowPathName+File.separator;
					//String filePath = nowPathName+File.separator+code;
					//String printFilePath = printNowPath+File.separator+code;
					String cardiocerebralNowFilePath = cardiocerebralNowPath+File.separator;
					PhyReport reportUser = infoService.getPhyReportByCode(code);
					String ownedCompany = infoService.getOwnedCompanyByGeneCode(code);	//该Code所属公司
					int type;
					if(ownedCompany.equals("中国人寿保险股份有限公司")||ownedCompany.indexOf("远盟")!=-1){	//判断是否为国寿
						type = 0;
					}else{
						type = 1;
					}
					if(null!=reportUser){
						//单独加上（韩娟：2144881 ；张群：1399769；陈海量：1223089；刘建国 ：1223099；王文娇：1309395）
						/*if(reportUser.getComboName().indexOf("常规九")!=-1||reportUser.getGeneCode().equals("2144881")
								||reportUser.getGeneCode().equals("1399769")||reportUser.getGeneCode().equals("1223089")
								||reportUser.getGeneCode().equals("1223099")||reportUser.getGeneCode().equals("1309395")
								||reportUser.getGeneCode().equals("2111012")||reportUser.getGeneCode().equals("1562662")){*/
							
						if(reportUser.getComboName().indexOf("基础")==-1){//常规高端的
							isMerge = "1";	//不需要合并
							
							/* ****单页PDF的生成（常规，[高端]）**** */
							List<String> pdfModeList = new ArrayList<String>();
							PhysicalEtcPdfUtil pdfUtil = (PhysicalEtcPdfUtil)SpringContextHolder.getBean("PhysicalEtcPdfUtil");
							pdfUtil.initFilePath(filePath);//初始化文件路径
							try {
								String no1 = pdfUtil.createPdfNo1(reportUser);
								pdfModeList.add(no1);
								String no2 = pdfUtil.createPdfNo2();
								pdfModeList.add(no2);
								String no3 = pdfUtil.createPdfNo3(reportUser);
								pdfModeList.add(no3);
								
								no4Map = pdfUtil.createPdfNo4(infoService.getDiseaseInfoRoutine(code));
								if(no4Map!=null){
									pdfModeList.add(no4Map.get("fourPath"));
									/*String no5 = pdfUtil.createPdfNo5();*/
									/*if(!StringUtils.isEmpty(no4Map.get("fivePath"))){
										pdfModeList.add(no4Map.get("fivePath"));
									}*/
									pdfModeList.add(no4Map.get("fivePath"));
								}
								
								String no6 = pdfUtil.createPdfNo6(reportUser.getUserSex(),reportUser.getComboName());
								pdfModeList.add(no6);
								String no7 = pdfUtil.createPdfNo7(infoService.getProjectInfoRoutine(code));
								pdfModeList.add(no7);
								String no8 = pdfUtil.createPdfNo8();
								pdfModeList.add(no8);						
								String no9 = pdfUtil.createPdfNo9(reportUser.getUserName());
								pdfModeList.add(no9);
								String no10 = pdfUtil.createPdfNo10();
								pdfModeList.add(no10);
								String no11 = pdfUtil.createPdfNo11();
								pdfModeList.add(no11);
								String no12 = pdfUtil.createPdfNo12(type);
								pdfModeList.add(no12);
								/*String no13 = pdfUtil.createPdfNo13(type);
								pdfModeList.add(no13);*/
								String time = "";
								if(reportUser.getReportDate()==null){
									time = "noHaveTime";
								}else{
									time = reportDateFormat.format(reportUser.getReportDate());
								}
								reportName = code+"_"+reportUser.getUserName()+"_"+time+".pdf";
								pdfPathMap = pdfUtil.createPDF(pdfModeList,filePath+File.separator+reportName);//组装所有pdf
								
								flag = true;
							} catch (Exception e) {
								log.error("physical create rule report Thread run -- "+e);
								flag = false;
							}
							
							if(flag){		//未抛异常才执行
								if(pdfPathMap!=null){
									pdfPath = pdfPathMap.get("pdfPath");
									//pageStr = pdfPathMap.get("pages");
								}
								/*if(pageStr!=null){	//判断生成的PDF是否异常
									int pages = Integer.parseInt(pageStr);
									if(12==pages){//判断生成的PDF页数是否为12
										pdfPathStr = "0";
									}else{
										pdfPathStr = "2";
									}
								}*/
								if(StringUtils.isEmpty(pdfPath)){	//判断生成的地址是否为null
									//pdfPathStr = "1";
									isSuccess = "1";
								}
								
								if(pdfPath.length()>0&&pdfPath!="null"){
									isSuccess = "0";
									pdfUtil.deletePdf(pdfModeList);
								}
							}else{
								isSuccess = "1";
							}
						}else if(reportUser.getComboName().indexOf("心脑血管")!=-1){	//心脑套餐
							isMerge = "1";	//不需要合并
							
							/* ****单页PDF的生成（心脑血管套餐）**** */
							List<String> pdfModeList = new ArrayList<String>();
							CardiocerebralPdfUtil cardiocerebralPdfUtil  = (CardiocerebralPdfUtil)SpringContextHolder.getBean("CardiocerebralPdfUtil");
							cardiocerebralPdfUtil.initFilePath(cardiocerebralNowFilePath);//初始化文件路径
							try {
								String no1 = cardiocerebralPdfUtil.createPdfNo1();
								pdfModeList.add(no1);
								String no2 = cardiocerebralPdfUtil.createPdfNo2();
								pdfModeList.add(no2);
								String no3 = cardiocerebralPdfUtil.createPdfNo3(reportUser);
								pdfModeList.add(no3);
								
								no4Map = cardiocerebralPdfUtil.createPdfNo4(infoService.getDiseaseInfo(code));
								if(no4Map!=null){
									pdfModeList.add(no4Map.get("fourPath"));
									pdfModeList.add(no4Map.get("fivePath"));
								}
								
								String no6 = cardiocerebralPdfUtil.createPdfNo6(reportUser.getUserSex());
								pdfModeList.add(no6);
								String no7 = cardiocerebralPdfUtil.createPdfNo7(infoService.getProjectInfo(code));
								pdfModeList.add(no7);
								String no8 = cardiocerebralPdfUtil.createPdfNo8();
								pdfModeList.add(no8);						
								String no9 = cardiocerebralPdfUtil.createPdfNo9();
								pdfModeList.add(no9);
								String no10 = cardiocerebralPdfUtil.createPdfNo10();
								pdfModeList.add(no10);
								String no11 = cardiocerebralPdfUtil.createPdfNo11();
								pdfModeList.add(no11);
								String no12 = cardiocerebralPdfUtil.createPdfNo12(type);
								pdfModeList.add(no12);
								String time = "";
								if(reportUser.getReportDate()==null){
									time = "noHaveTime";
								}else{
									time = reportDateFormat.format(reportUser.getReportDate());
								}
								reportName = code+"_"+reportUser.getUserName()+"_"+time+".pdf";
								pdfPathMap = cardiocerebralPdfUtil.createPDF(pdfModeList,cardiocerebralNowFilePath+reportName);//组装所有pdf
								
								flag = true;
							} catch (Exception e) {
								log.error("physical create cardiocerebralPdfUtil report Thread run -- "+e);
								flag = false;
							}
							
							if(flag){		//未抛异常才执行
								if(pdfPathMap!=null){
									pdfPath = pdfPathMap.get("pdfPath");
									//pageStr = pdfPathMap.get("pages");
								}
								/*if(pageStr!=null){	//判断生成的PDF是否异常
									int pages = Integer.parseInt(pageStr);
									if(12==pages){//判断生成的PDF页数是否为12
										pdfPathStr = "0";
									}else{
										pdfPathStr = "2";
									}
								}*/
								if(StringUtils.isEmpty(pdfPath)){	//判断生成的地址是否为null
									//pdfPathStr = "1";
									isSuccess = "1";
								}
								
								if(pdfPath.length()>0&&pdfPath!="null"){
									isSuccess = "0";
									cardiocerebralPdfUtil.deletePdf(pdfModeList);
								}
							}else{
								isSuccess = "1";
							}
							
						}/*else if(reportUser.getComboName().indexOf("基础")!=-1){
							Date samplingTime = infoService.getSamplingTimeByGeneCode(code);			//该Code检测时间
							
							//基础的套餐（并且需要合并）
							isMerge = "0";	
							
							 ***生成出基础pdf（需要核合成）** 
							List<String> pdfModeList = new ArrayList<String>();
							AddSouthPdfUtil dockPdfUtil = (AddSouthPdfUtil)SpringContextHolder.getBean("AddSouthPdfUtil");
							dockPdfUtil.initFilePath(filePath);//初始化文件路径
							try {
								String no1 = dockPdfUtil.createPdfNo1();
								pdfModeList.add(no1);
								String no2 = dockPdfUtil.createPdfNo2();
								pdfModeList.add(no2);
								String no3 = dockPdfUtil.createPdfNo3();
								pdfModeList.add(no3);
								String no4 = dockPdfUtil.createPdfNo4();
								pdfModeList.add(no4);
								String no5 = dockPdfUtil.createPdfNo5(reportUser,samplingTime);
								pdfModeList.add(no5);
								
								String no6 = dockPdfUtil.createPdfNo6();
								pdfModeList.add(no6);
								String no7 = dockPdfUtil.createPdfNo7(reportUser);
								pdfModeList.add(no7);						
								no8Map = dockPdfUtil.createPdfNo8(infoService.getDiseaseInfo(code));
								if(no8Map!=null){
									pdfModeList.add(no8Map.get("eightPath"));
									pdfModeList.add(no8Map.get("ninePath"));
								}
								
								String no10 = dockPdfUtil.createPdfNo10(reportUser.getUserSex());
								pdfModeList.add(no10);
								String no11 = dockPdfUtil.createPdfNo11(infoService.getProjectInfo(code));
								pdfModeList.add(no11);
								String no12 = dockPdfUtil.createPdfNo12();
								pdfModeList.add(no12);
								String no13 = dockPdfUtil.createPdfNo13();
								pdfModeList.add(no13);
								String no14 = dockPdfUtil.createPdfNo14();
								pdfModeList.add(no14);
								String no15 = dockPdfUtil.createPdfNo15();
								pdfModeList.add(no15);
								String no16 = dockPdfUtil.createPdfNo16();
								pdfModeList.add(no16);
								String no17 = dockPdfUtil.createPdfNo17();
								pdfModeList.add(no17);
								
								String time = "";
								if(reportUser.getReportDate()==null){
									time = "noHaveTime";
								}else{
									time = reportDateFormat.format(reportUser.getReportDate());
								}
								reportName = code+"_"+reportUser.getUserName()+"_"+time+".pdf";
								pdfPathMap = dockPdfUtil.createPDF(pdfModeList,filePath+File.separator+reportName);//组装pdf
								
								flag = true;
							} catch (Exception e) {
								log.error("physical create base report Thread run -- "+e);
								flag = false;
							}
							
							if(flag){		//鏈姏寮傚父鎵嶆墽琛�
								if(pdfPathMap!=null){
									pdfPath = pdfPathMap.get("pdfPath");
								}
								if(StringUtils.isEmpty(pdfPath)){	//鍒ゆ柇鐢熸垚鐨勫湴鍧�槸鍚︿负null
									isSuccess = "1";
								}
								
								if(pdfPath.length()>0&&pdfPath!="null"){
									isSuccess = "0";
									dockPdfUtil.deletePdf(pdfModeList);
								}
							}else{
								isSuccess = "1";
							}
						}*/
							
						infoService.createPdfInfoAlterETC(pdfPath, reportName, reportUser,isSuccess,isMerge);
					}
				}
				log.info("PhysicalReportThread end !");
			}catch(Exception e){
				log.error("PhysicalReportThread Run Error", e);
			}
			
		}
		
	}
}
