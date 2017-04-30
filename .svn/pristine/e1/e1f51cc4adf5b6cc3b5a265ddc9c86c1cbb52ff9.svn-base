package org.hpin.reportdetail.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.customerrelationship.entity.CustomerRelationshipLink;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.customerrelationship.service.CustomerRelationshipLinkService;
import org.hpin.base.region.service.RegionService;
import org.hpin.common.core.SpringTool;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.reportdetail.entity.ErpComboRelation;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.hpin.reportdetail.service.ErpComboRelationService;
import org.hpin.warehouse.entity.ErpPreCustomer;
import org.hpin.warehouse.service.ErpHKPrepCustomerService;

public class DealWithFileUtil {

	private DealWithFileUtil(){}
	
	private static DealWithFileUtil pdfUtil = null;
	
	private static Logger log = Logger.getLogger(DealWithFileUtil.class);
	
	CustomerRelationShipService relService=(CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
	
	CustomerRelationshipLinkService shipLinkService=(CustomerRelationshipLinkService)SpringTool.getBean(CustomerRelationshipLinkService.class);
	
	ErpCustomerService cusService=(ErpCustomerService)SpringTool.getBean(ErpCustomerService.class);
	
	ErpEventsService eventsService = (ErpEventsService)SpringTool.getBean(ErpEventsService.class);
	
	RegionService regionService = (RegionService)SpringTool.getBean(RegionService.class);
	
	ErpComboRelationService comboRelationService = (ErpComboRelationService)SpringTool.getBean(ErpComboRelationService.class);
	
	ErpHKPrepCustomerService preCustomerService = (ErpHKPrepCustomerService)SpringTool.getBean(ErpHKPrepCustomerService.class);
	/**
	 * 线程安全单例模式
	 * @return DealWithPdfUtil
	 */
	public static synchronized DealWithFileUtil getInstance(){
		if(pdfUtil == null){
			pdfUtil = new DealWithFileUtil();
			log.info("DealWithPdfUtil create instance");
		}
		return pdfUtil;
	}
	
	/**
	 * 获取文件的MD5值
	 * @param file
	 * @return MD5
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public String getPdfMd5ByFile(File file){
		FileInputStream in = null;
		String result = null;
		try{
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			result = bi.toString(16);
		}catch(Exception e){
			log.error("DealWithFileUtil getPdfMd5ByFile", e);
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					log.error("DealWithFileUtil getPdfMd5ByFile catch", e);
				}
			}
		}
		return result;
	}
	
	/**
	 * 拷贝文件到指定目录
	 * @throws Exception 
	 */
	public void copyReportFile(File currFile,String toPath,String filename) throws Exception{
		
		FileInputStream fin = null;
		FileOutputStream fout = null;
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		
		if(null==filename){
			filename = currFile.getName();
		}
		
		try{
			fin = new FileInputStream(currFile);//D:\new\pdf\12\pdf\1331414.pdf
			String finalPath = toPath+filename;
			File file = new File(toPath);
			file.mkdirs();
			fout = new FileOutputStream(finalPath);
			//D:/ymdownload/20161028141801731\20161028141801731_402881b255018fbd015505e7aa590774_基础一_1\20161028141801731_402881b255018fbd015505e7aa590774_基础一_1\1331414.pdf
			inChannel = fin.getChannel();
			outChannel = fout.getChannel();
			outChannel.transferFrom(inChannel, 0, inChannel.size());
		}catch(Exception e){
			log.error("DealWithPdfUtil copyReportFile", e);
			throw e;
		}finally{
			try{
				outChannel.close();
				inChannel.close();
				fin.close();
				fout.close();
			}catch(Exception e){
				log.error("DealWithPdfUtil copyReportFile catch", e);
			}
			
		}
	}
	
	public void copyReportFile(String currFilePath,String toPath,String filename) throws Exception{
		File currFile = new File(currFilePath);
		copyReportFile(currFile,toPath,filename);
	}
	
	/**
	 * 根据内容生成入库的对象
	 * @param currFile
	 * @return
	 * @throws Exception 
	 */
	public ErpReportdetailPDFContent buildPDFContentBean(File currFile){
		ErpReportdetailPDFContent pdf = new ErpReportdetailPDFContent();
		String pdfContent = null;
		String type = null;
		Map<String,String> maps = readPdfContent(currFile);
		pdf.setIsrecord(0);
		if(maps.containsKey("content")&&maps.get("content")!=null){
			pdfContent = maps.get("content");
			type = maps.get("type");
			//处理PDF内容并存入表中,若处理过程出现异常，则会返回一个空内容的对象
			pdf = buildPDFContent(type,pdfContent);
			//判断PDF对象内容是否为空
			if(pdf.getUsername()!=null){
				String userName = pdf.getUsername().replaceAll(" ","");
				if(!StringUtils.isEmpty(userName)){
					if(type.equals("sysw")){
						String fileName = currFile.getName().substring(0, currFile.getName().lastIndexOf("."));
						if(pdf.getCode().indexOf(fileName)!=-1){
							pdf.setCode(pdf.getCode().substring(pdf.getCode().indexOf(fileName), fileName.length()));
						}
					}
					pdf.setIsrecord(1);
				}
			}
		}
		pdf.setPdfname(currFile.getName());
		pdf.setMd5(getPdfMd5ByFile(currFile));
		pdf.setFilesize(String.valueOf(currFile.length()));
		pdf.setFilepath(currFile.getAbsolutePath());
		return pdf;
	}
	
	/**
	 * 读取PDF文件的内容
	 * @param pdfFile
	 * @return String
	 * @throws Exception
	 */
	public Map<String,String> readPdfContent(File pdfFile){
		Map<String,String> resultMap = new HashMap<String,String>();
		String result = null;
		FileInputStream is = null;
		PDDocument document = null;
		String content = null;
		try {
			is = new FileInputStream(pdfFile);
			log.info("readPDF path : "+ pdfFile.getAbsolutePath());
			document = PDDocument.load(is);
			int nums = document.getNumberOfPages();
			//从每一页读取内容
			for(int i = 1;i<nums+1;i++){
				PDFTextStripper stripper = new PDFTextStripper();
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				result = stripper.getText(document);
				Pattern p = Pattern.compile("\\s*|\t|\r|\n|\r\n");
				Matcher m = p.matcher(result);
				content = m.replaceAll("");
				content = content.replaceAll(" ", "");
				if(isMatch(content,resultMap)){
					resultMap.put("content",content);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			log.error("DealWithPdfUtil readPdfContent ERROR ", e);
		} catch (IOException e) {
			log.error("DealWithPdfUtil readPdfContent ERROR ", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("DealWithPdfUtil readPdfContent ERROR ", e);
				}
			}
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					log.error("DealWithPdfUtil readPdfContent ERROR ", e);
				}
			}
		}
		return resultMap;
	}

	/**
	 * 生成PDF内容对象
	 * @param pdfStr
	 * @return
	 */
	public ErpReportdetailPDFContent buildPDFContent(String type,String pdfStr){
		ErpReportdetailPDFContent pdfContent = new ErpReportdetailPDFContent();
		try{
			if(type.equals("nfjyzx")){
				if(pdfStr.indexOf("编号")!=-1){
					pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("性别")));
					pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别：")+3,pdfStr.indexOf("年龄")));
					pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄：")+3,pdfStr.indexOf("样品类别")));
					pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("编号：")+3,pdfStr.length()));
				}else {
					if(pdfStr.indexOf("姓名：")!=-1){
						pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("性别")));
						pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别：")+3,pdfStr.indexOf("年龄")));
						pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄：")+3,pdfStr.indexOf("样品类别")));
						pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条码：")+3,pdfStr.length()));
					}else{
						pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名")+2, pdfStr.indexOf("性别")));
						pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2,pdfStr.indexOf("标本")));
						if(pdfStr.indexOf("送检日期")!=-1){
							pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条码")+2,pdfStr.indexOf("送检日期")));
						}else{
							pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条码")+2,pdfStr.indexOf("报告日期")));
						}
						pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2,pdfStr.indexOf("年龄")));
					}
				}
			}
			if(type.equals("sysw")){
				if(pdfStr.indexOf("年龄")!=-1){
					pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("性别")));
					pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄：")+3,pdfStr.indexOf("标本")));
					pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条码：")+3,pdfStr.length()));
					pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别：")+3,pdfStr.indexOf("年龄")));
				}else if(pdfStr.indexOf("性别")!=-1){
					pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("性别")));
					pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条码：")+3,pdfStr.length()));
					pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别：")+3,pdfStr.indexOf("标本")));
				}else{
					pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条码：")+3,pdfStr.length()));
					pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("标本")));
				}
				
			}
			if(type.equals("syswdd")){
				pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("检测技术")));
				pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄：")+3,pdfStr.indexOf("样本类型")));
				pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条形码：")+4,pdfStr.indexOf("项目")));
				if(pdfStr.indexOf("报告编号")!=-1){
					pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("报告编号：")+5,pdfStr.indexOf("项目")));
				}
				pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别：")+3,pdfStr.indexOf("年龄")));
			}
			if(type.equals("bfns")){
				if(pdfStr.indexOf("编号")==0){
					pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名")+2, pdfStr.indexOf("性别")));
					pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2,pdfStr.indexOf("姓名")));
					pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("编号")+2,pdfStr.indexOf("报告日期")));
					pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2,pdfStr.indexOf("个人信息")));
				}else if(pdfStr.indexOf("姓名")==0){
					pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名")+2, pdfStr.indexOf("性别")));
					pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2,pdfStr.length()));
					pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("编号")+2,pdfStr.indexOf("报告日期")));
					pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2,pdfStr.indexOf("个人信息")));
				}else if(pdfStr.indexOf("个人信息")==0){
					String name = pdfStr.substring(pdfStr.indexOf("姓名")+2, pdfStr.indexOf("性别"));
					if(name.indexOf("年龄")!=-1){
						pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名")+2,pdfStr.indexOf("年龄")));
						pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2,pdfStr.indexOf("编号")));
						pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("编号")+2,pdfStr.indexOf("性别")));
						pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2,pdfStr.indexOf("报告日期")));
					}else{
						pdfContent.setUsername(name);
						String sex = pdfStr.substring(pdfStr.indexOf("性别")+2,pdfStr.indexOf("编号"));
						if(sex.indexOf("年龄")!=-1){
							pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2,pdfStr.indexOf("年龄")));
							pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2,pdfStr.indexOf("编号")));
							pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("编号")+2,pdfStr.indexOf("报告日期")));
						}else{
							pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2,pdfStr.indexOf("编号")));
							pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2,pdfStr.length()));
							pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("编号")+2,pdfStr.indexOf("报告日期")));
						}
					}
				}else if(pdfStr.indexOf("1个人信息")==0){
					pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名")+2, pdfStr.indexOf("性别")));
					pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2,pdfStr.indexOf("编号")));
					pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("编号")+2,pdfStr.indexOf("报告日期")));
					pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2,pdfStr.indexOf("年龄")));
				}else{
					pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名")+2, pdfStr.indexOf("年龄")));
					pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2,pdfStr.indexOf("编号")));
					pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("编号")+2,pdfStr.indexOf("性别")));
					pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2,pdfStr.indexOf("报告日期")));
				}
				
			}
			if(type.equals("children")){
				pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("性别")));
				pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄：")+3,pdfStr.indexOf("标本类型")));
				pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条码：")+3,pdfStr.indexOf("送检单位")));
				pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别：")+3,pdfStr.indexOf("年龄")));
			}
			if(type.equals("fatgene")){
				pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("性别")));
				pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄：")+3,pdfStr.indexOf("送检日期")));
				pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条行码：")+4,pdfStr.indexOf("送检标本")));
				pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别：")+3,pdfStr.indexOf("年龄")));
			}
			if(type.equals("growthhormone")){
				pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("性别")));
				pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄：")+3,pdfStr.indexOf("送检日期")));
				pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条行码：")+4,pdfStr.indexOf("送检标本")));
				pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别：")+3,pdfStr.indexOf("年龄")));
			}
			if(type.equals("childrenVitamin")){
				pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("性别")));
				pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄：")+3,pdfStr.indexOf("送检日期")));
				pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条行码：")+4,pdfStr.indexOf("送检标本")));
				pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别：")+3,pdfStr.indexOf("年龄")));
			}
			if(type.equals("P53")){
				pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名")+2, pdfStr.indexOf("性别")));
				pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2,pdfStr.indexOf("条码")));
				pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条码")+2,pdfStr.indexOf("送检单位")));
				pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2,pdfStr.indexOf("年龄")));
			}
			if(type.equals("basicInfo")){
				pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名")+2, pdfStr.indexOf("性别")));
				pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2, pdfStr.indexOf("年龄")));
				pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2, pdfStr.indexOf("条码")));
				pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条码")+2, pdfStr.indexOf("送检单位")));
			}
			if(type.equals("childrenGrow")){//儿童基础成长能力基因检测
				pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名")+2, pdfStr.indexOf("性别")));
				pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别")+2, pdfStr.indexOf("年龄")));
				pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄")+2, pdfStr.indexOf("条码")));
				pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条码")+2, pdfStr.indexOf("送检单位")));
			}
			if(type.equals("geneReport")){//基因体检报告单
				pdfContent.setUsername(pdfStr.substring(pdfStr.indexOf("姓名：")+3, pdfStr.indexOf("性别")));
				pdfContent.setSex(pdfStr.substring(pdfStr.indexOf("性别：")+3, pdfStr.indexOf("年龄")));
				pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("年龄：")+3, pdfStr.indexOf("送检日期")));
				pdfContent.setCode(pdfStr.substring(pdfStr.indexOf("条行码：")+4, pdfStr.indexOf("送检标本")));
				if(pdfStr.indexOf("条形码")!=-1){
					pdfContent.setAge(pdfStr.substring(pdfStr.indexOf("条形码：")+4, pdfStr.indexOf("送检标本")));
				}
			}
		}catch(Exception e){
			log.error("DealWithFileUtil buildPDFContent error, pdf content : \n"+pdfStr, e);
			pdfContent = new ErpReportdetailPDFContent();
		}
		return pdfContent;
	}
	
	/*
	 * 根据不同基因公司不同PDF内容格式做匹配
	 */
	public boolean isMatch(String pdf,Map<String,String> map){
		//南方基因申友生物
		if(pdf.indexOf("受检人信息")!=-1){
			map.put("type","sysw");
			return true;
		}
		//含报告导读
		if(pdf.indexOf("基因体检基本信息受检者信息")!=-1){
			map.put("type","syswdd");
			return true;
		}
		//国家人类基因南方研究中心 
		if(pdf.indexOf("检者基本信息")!=-1){
			map.put("type","nfjyzx");
			return true;
		}
		//北方诺赛
		if(pdf.indexOf("个人信息")!=-1){
			map.put("type","bfns");
			return true;
		}
		//儿童用药安全检测报告
		if(pdf.indexOf("儿童用药安全检测报告")!=-1){
			map.put("type","children");
			return true;
		}
		//儿童/青春期肥胖基因
		if(pdf.indexOf("肥胖基因")!=-1){
			map.put("type","fatgene");
			return true;
		}
		//生长激素
		if(pdf.indexOf("生长激素")!=-1){
			map.put("type","growthhormone");
			return true;
		}
		//儿童成长基因检测
		if(pdf.indexOf("儿童成长基因检测")!=-1){
			map.put("type","basicInfo");
			return true;
		}
		//儿童维生素
		if(pdf.indexOf("儿童维生素")!=-1){
			map.put("type","childrenVitamin");
			return true;
		}
		//P53抑癌能力
		if(pdf.indexOf("P53抑癌能力")!=-1){
			map.put("type","P53");
			return true;
		}
		if(pdf.indexOf("儿童基础成长能力基因检测")!=-1){
			map.put("type", "childrenGrow");
			return true;
		}
		if(pdf.indexOf("基因体检报告单")!=-1){
			map.put("type", "geneReport");
			return true;
		}
		return false;
	}
	
	//生成Excel每行的内容
	public List<List<String>> buildExcelRow(List<ErpCustomer> customerList){
		List<List<String>> result = new ArrayList<List<String>>();
		for(int i=0;i<customerList.size();i++){
			ErpCustomer customer = customerList.get(i);
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(i));
			list.add(customer.getCode());
			list.add(customer.getName());
			list.add(customer.getSex());
			list.add(customer.getAge());
			list.add(customer.getPhone());
			list.add(customer.getBranchCompany());
			list.add(customer.getSalesMan());
			result.add(list);
		}
		return result;
	}
	
	//生成Excel每行的内容
	//序号、条码、姓名、性别、年龄、身份证号、电话、省、市、支公司、所属公司、套餐、采样日期、营销员、批次号、检测机构
	public List<List<String>> buildExcelRowByPdf(List<ErpPrintTaskContent> contentList){
		List<List<String>> result = new ArrayList<List<String>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<contentList.size();i++){
			ErpPrintTaskContent content = contentList.get(i);
				List<String> list = new ArrayList<String>();
				ErpCustomer customer = cusService.findCusById(content.getCustomerId());//20160519增加更多的会员信息
				List<ErpPreCustomer> preCustomerList = preCustomerService.getAdressInfo(content.getCustomerId());
				list.add(String.valueOf(i+1));//序号
				list.add(content.getCode());//条码
				list.add(content.getUserName());//姓名
				list.add(content.getGender());//性别
				list.add(content.getAge());//年龄
				if(null!=customer){
					list.add(customer.getProvice()==null?"":regionService.id2Name(customer.getProvice(),null));//省
					list.add(customer.getCity()==null?"":regionService.id2Name(customer.getCity(),null));//市
					list.add(customer.getBranchCompany()==null?"":customer.getBranchCompany());//支公司
					list.add(customer.getDepartment()==null?"":customer.getDepartment());//部门
					list.add(customer.getOwnedCompany()==null?"":customer.getOwnedCompany());//所属公司
					list.add(customer.getSetmealName()==null?"":customer.getSetmealName());//套餐
					list.add(customer.getSamplingDate()==null?"":sdf.format(customer.getSamplingDate()));//采样日期
					list.add(customer.getSalesMan()==null?"":customer.getSalesMan());//(营销员改为了远盟对接人)
					list.add(cusService.findEvtBthnoByEvtNo(customer.getEventsNo()));//批次号
					list.add(customer.getTestInstitution()==null?"":customer.getTestInstitution());//检测机构
					list.add(customer.getSampleType()==null?"":customer.getSampleType());//样本类型
					
					/* 查询出支公司对应的新增加数据字段;支公司寄送地址、收件人、收件人电话 modified by henry.xu 20160811;
					 * 如果支公司id不为null则通过id查询,否则通过名称匹配;
					 */
//				CustomerRelationShip cusRelShip = relService.findByIdOrName(customer.getBranchCompanyId(), customer.getBranchCompany());
//				if(cusRelShip != null) {
//					list.add(cusRelShip.getMailAddr() == null ? "" : cusRelShip.getMailAddr()); //支公司寄送地址
//					list.add(cusRelShip.getRecipients() == null ? "": cusRelShip.getRecipients()); //收件人
//					list.add(cusRelShip.getRecipientsTel() == null ? "": cusRelShip.getRecipientsTel()); //收件人电话
//				}
					List<CustomerRelationshipLink> shipLinkList = shipLinkService.getBranchCompanyExpressInfo(customer.getBranchCompanyId(), customer.getDepartment());
					ErpEvents events = eventsService.getCompanyPro(customer.getEventsNo());	
					//根据查询到的场次获取项目编码
					CustomerRelationShipPro project = relService.getBranchCompanyExpressInfo(events.getCustomerRelationShipProId());
					if(Integer.valueOf(3).equals(content.getReportType()) && preCustomerList.size()!=0){//无创的报告
						ErpPreCustomer preCustomer = preCustomerList.get(0);
						list.add(preCustomer.getReportSendAddr()); //被检测人寄送地址
						list.add(preCustomer.getWereName()); //被检测人姓名
						list.add(preCustomer.getWerePhone()); //被检测人电话
					}else{
						if(shipLinkList.size()!=0){
							list.add(shipLinkList.get(0).getFunctions()); //支公司寄送地址
							list.add(shipLinkList.get(0).getLinkMan()); //收件人
							list.add(shipLinkList.get(0).getPhone()); //收件人电话
						}else if(project!=null){
							list.add(project.getMailAddress());
							list.add(project.getReception());
							list.add(project.getReceptionTel());
						}
					}
					/**@since 2016年10月28日11:56:37 @author Carly*/
					if(project!=null){
						list.add(project.getProjectOwner()==null?"":project.getProjectOwner());	//项目负责人
						list.add(project.getProjectName()==null?"":project.getProjectName());	//项目名称
						list.add(project.getProjectCode()==null?"":project.getProjectCode());	//项目编码
					}
					if(null!=customer.getOtherCompanyId()){
						CustomerRelationShip relationShip = (CustomerRelationShip) relService.findById(customer.getOtherCompanyId());
						list.add(relationShip.getBranchCommany());	//扫码公司 @since 2017年1月4日18:05:11 @author chenqi
					}
				}
				result.add(list);
		}
		return result;
	}
	
	//处理所有的客户信息
	public Map<String,List<ErpCustomer>> dealErpCustomer(List<ErpCustomer> customerList,List<String> company) {
		Map<String,List<ErpCustomer>> result = new HashMap<String,List<ErpCustomer>>();
		for(ErpCustomer customer : customerList){
			if(company.contains(customer.getBranchCompany())){
				result.get(customer.getBranchCompany()).add(customer);
			}else{
				List<ErpCustomer> list = new ArrayList<ErpCustomer>();
				list.add(customer);
				result.put(customer.getBranchCompany(), list);
				company.add(customer.getBranchCompany());
			}
		}
		return result;
	}
	
	//生成PDF下载文件
	public void buidPDFDownload(String toPath,List<ErpCustomer> list) throws Exception{
		for(ErpCustomer customer : list){
			copyReportFile(customer.getPdffilepath(),toPath,null);
		}
	}
	
	//生成PDF下载文件
	public void buidPDFDownloadByContent(String toPath,List<ErpReportdetailPDFContent> list) throws Exception{
		for(ErpReportdetailPDFContent content : list){
			copyReportFile(content.getFilepath(),toPath,null);
		}
	}
	
	/**
	 * 拷贝报告文件到相应目录
	 * @param fileDir
	 * @param paths
	 * @return 异常次数
	 * @throws Exception
	 */
	public boolean dealFile(File fileDir,String[] paths,List<String> fileNameList){
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
					//判断文件名是否重名
					if(fileNameList.contains(name)){
						//重名文件在名字后加时间
						SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
						Random r=new Random();
						name = name.substring(0, name.lastIndexOf(".")) + sdf.format(new Date())+r.nextInt(10)+ "." +suffix;
					}
					if(suffix.equals("xls")||suffix.equals("xlsx")){
						File fileToExcelDir = new File(paths[0]);
						if(!fileToExcelDir.exists()){
							fileToExcelDir.mkdirs();
						}
						copyReportFile(currFile,paths[0],name);
					}
					if(suffix.equals("pdf")){
						File fileToPdfDir = new File(paths[1]);
						if(!fileToPdfDir.exists()){
							fileToPdfDir.mkdirs();
						}
						copyReportFile(currFile,paths[1],name);
					}
					if(suffix.equals("data")){
						File fileToPdfDir = new File(paths[2]);
						if(!fileToPdfDir.exists()){
							fileToPdfDir.mkdirs();
						}
						copyReportFile(currFile,paths[2],name);
					}
					currFile.delete();
					fileNameList.add(name);
				}else{
					dealFile(currFile,paths,fileNameList);
				}
			}catch(Exception e){
				log.error("ErpReportdetailJob dealFile", e);
				result = false;
				continue;
			}
		}
		return result;
	}
	
	/*
	 * 删除目录下所有的PDF文件
	 */
	public void delFileByPath(String filepath){
		File path = new File(filepath);
		if(!path.isDirectory()){
			return;
		}
		File files[] = path.listFiles();
		for(File curfile : files){
			String name = curfile.getName();
			String suffix = name.substring(name.lastIndexOf(".")+1,name.length());
			if(suffix.equals("pdf")||suffix.equals("xls")||suffix.equals("xlsx")){
				curfile.delete();
			}
		}
		path.delete();
	}
	
	/**
	 * @param currFile
	 * @since 2016年11月7日16:42:52
	 * @author Carly
	 * @return 根据文件名来获取条形码，姓名，套餐，性别
	 */
	public ErpReportdetailPDFContent createPDFContent(File currFile){
		ErpReportdetailPDFContent pdf = new ErpReportdetailPDFContent();
		String fileName = currFile.getName();//1308802_陈玉芹_基础一_女.pdf;条形码_姓名_套餐_性别.pdf
		String [] nameArray = fileName.substring(0,fileName.lastIndexOf(".")).split("_");//防止 1513276_张浩_F022.情商天赋潜能F025.智商天赋潜能F026.体育天赋潜能_男.pdf
		if (nameArray.length!=4) {
			pdf.setMatchstate(10);
			
		} else {
			String code = nameArray[0].trim();
			String userName = nameArray[1].trim();
			String combo = nameArray[2].trim();
			String gender = nameArray[3].trim();
			List<ErpComboRelation> ymComboList = comboRelationService.getYmCombo(combo);
			pdf.setSetmeal_name("");
			if (ymComboList.size() == 0) {
				pdf.setMatchstate(0);//没有找到对应的申友套餐
				log.info("申友套餐:"+combo);
				
			} else if (ymComboList.size() == 1) {//防止一个申友套餐对应多个套餐
				pdf.setMatchstate(0);//没有和远盟对应的套餐
				if (null != ymComboList.get(0).getYmCombo() ) {
					pdf.setSetmeal_name(ymComboList.get(0).getYmCombo());
					pdf.setMatchstate(0);
				}
				
			} else if (ymComboList.size() >= 2) {//申友的套餐对应多个远盟的套餐
				boolean flag = false;
				for (ErpComboRelation relation:ymComboList) {
					log.info("对应的远盟套餐名称："+relation.getYmCombo());
					List<ErpCustomer> customerList= comboRelationService.getCustomerComboInfo(code.toUpperCase(),userName.replaceAll(" ", ""));
					for (ErpCustomer customer:customerList) {//匹配客户中有套餐A+套餐B的情况
						String setmealName = customer.getSetmealName();
						log.info("客户的套餐："+setmealName);
						
						String [] comboArray = setmealName.split("[+]");
						for (int i=0;i<comboArray.length;i++) {
							if (comboArray[i].equals(relation.getYmCombo())) {
								pdf.setSetmeal_name(relation.getYmCombo());
								pdf.setMatchstate(0);
								flag = true;//如果匹配成功则不进行其他的循环
								break;
							}
						}
					}
					
					if (flag) {
						break;
					}
				}
				if (!flag) {//客户的套餐和申友所获取的对应远盟套餐不一致
					pdf.setMatchstate(8);
					log.info("客户信息不匹配");
				}
			}
			pdf.setCode(code);
			pdf.setUsername(userName);
			pdf.setSex(gender);
		}
		pdf.setReportType(0);
		pdf.setIsrecord(1);
		pdf.setPdfname(currFile.getName());
		pdf.setMd5(getPdfMd5ByFile(currFile));
		pdf.setFilesize(String.valueOf(currFile.length()));
		pdf.setFilepath(currFile.getAbsolutePath());
		return pdf;
	}

	/**
	 * @since 2016年12月15日10:49:23
	 * @author carly
	 * @param currFile
	 * @param type 文件类型
	 * @param convertionType 是否需要套餐转换
	 * @return 
	 */
	public ErpReportdetailPDFContent createPDFContent(File currFile,int type,int convertionType) {
		ErpReportdetailPDFContent pdf = new ErpReportdetailPDFContent();
		String fileName = currFile.getName();//1308802_陈玉芹_基础一_女.pdf;条形码_姓名_套餐_性别.pdf
		String [] nameArray = fileName.substring(0,fileName.lastIndexOf(".")).split("_");
		if(nameArray.length!=4){
			pdf.setMatchstate(10);//文件名不对
		}else{
			pdf.setCode(nameArray[0].trim());
			pdf.setUsername(nameArray[1].trim());
			pdf.setSex(nameArray[3].trim());
			pdf.setSetmeal_name(nameArray[2].trim());
			pdf.setMatchstate(0);
		}
		pdf.setReportType(type);
		pdf.setIsrecord(1);
		pdf.setPdfname(currFile.getName());
		pdf.setMd5(getPdfMd5ByFile(currFile));
		pdf.setFilesize(String.valueOf(currFile.length()));
		pdf.setFilepath(currFile.getAbsolutePath());
		pdf.setConvertionFileType(convertionType);
		return pdf;
	}
}
