package org.hpin.physical.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.common.PhyReportModelPath;
import org.hpin.physical.entity.PhyReport;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * 心脑套餐的PDF生成
 * @author tangxing
 * @date 2016-11-24上午10:37:01
 */
public class CardiocerebralPdfUtil {

	private Logger log = Logger.getLogger(CardiocerebralPdfUtil.class);

	//物理根目录
	private String rootPath;
	
	//报告输出地址
	private String fileOutPath ;
	
	public CardiocerebralPdfUtil(){
		
	}
	
	public void initFilePath(String fileOutPath){
		this.fileOutPath = this.rootPath + fileOutPath;
	}
	
	//生成第一页
	public String createPdfNo1() throws Exception {
		String outFilePath = fileOutPath+File.separator+"001.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_ONE);
		File file = new File(fileOutPath);
		file.mkdirs();
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//生成第二页
	public String createPdfNo2() throws Exception {
		String outFilePath = fileOutPath+File.separator+"002.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_TWO);
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//生成第三页
	public String createPdfNo3(PhyReport reportUser) throws Exception {
		log.info("CardiocerebralPdfUtil createPdfNo3 PhyReport--"+reportUser.toString());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String now  = dateFormat.format(new Date());
		String outFilePath = fileOutPath+File.separator+"003.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_THREE);
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		AcroFields form = stamp.getAcroFields();
		form.setField("003name", reportUser.getUserName());
		form.setField("003sex", reportUser.getUserSex());
		form.setField("003age",reportUser.getUserAge());
		form.setField("geneCode", reportUser.getGeneCode());
		form.setField("geneTime", reportUser.getReportDate()==null?"":dateFormat.format(reportUser.getReportDate()));
		form.setField("assessDoctor","陈兆龙");
		form.setField("assessTime",now);
		stamp.setFormFlattening(true);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//生成第四页
	public Map<String, String> createPdfNo4(Map<String, List<List<String>>> analysis) throws Exception {
		Map<String, String> fourAndFivePathMap = new HashMap<String, String>();
		int count = 0;	//数据的行数
		
		int num = 0;
		String outFilePath = fileOutPath+File.separator+"004.pdf";
		String no5 = "";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_FOUR);
		FileOutputStream out = new FileOutputStream(outFilePath);
		Document document = new Document(PageSize.A4,15,15,170,0);//左右上下
		PdfWriter writer = PdfWriter.getInstance(document, out);
		 /*HeaderFooter footer=new HeaderFooter(new Phrase("-",chinese),new Phrase("-",chinese));  
         
          * 0是靠左 
          * 1是居中 
          * 2是居右 
           
         footer.setAlignment(1);  
         //footer.setBorder(Rectangle.BOX);  
         document.setFooter(footer);*/
		document.open();
		PdfContentByte cb = writer.getDirectContent();
		PdfImportedPage page3 = writer.getImportedPage(reader,1);
		cb.addTemplate(page3, 0, 0);
		PdfPTable table = new PdfPTable(3);
		table.setSplitLate(false);
		//本地
		//BaseFont wryhChinese = BaseFont.createFont("C:/WINDOWS/Fonts/MSYHL.TTC,1",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		//服务器
		//BaseFont wryhChinese = BaseFont.createFont("C:/WINDOWS/Fonts/msyh.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		//linux系统
		BaseFont wryhChinese = BaseFont.createFont("/usr/share/fonts/zh_CN/msyh.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		
		Font bgColorChinese = new Font(wryhChinese, 11, Font.NORMAL,new BaseColor(224,255,255));//表头字体
		Font cellContent = new Font(wryhChinese, 10, Font.NORMAL,BaseColor.BLACK);// 创建字体，设置family，size，style,还可以设置color 
		cellContent.setColor(54, 54, 54);	//字体颜色
		PdfPCell cell = null;
		cell = new PdfPCell(new Paragraph("项目分类", bgColorChinese));
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(new BaseColor(69,205,128));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(4f);
		cell.setPaddingBottom(7f);
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph("较高风险项目及评级", bgColorChinese));
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(new BaseColor(69,205,128));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(4f);
		cell.setPaddingBottom(7f);
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph("目前体检建议", bgColorChinese));
		cell.setBackgroundColor(new BaseColor(69,205,128));
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(4f);
		cell.setPaddingBottom(7f);
		table.addCell(cell);
		
		Map<String, List<List<String>>> analysisList = new LinkedHashMap<String, List<List<String>>>();
		analysisList = DiseaseMapComparator.comparatorDiseaseMap(analysis);
		
		for (Entry<String, List<List<String>>> entry1 : analysisList.entrySet()) {
			if(entry1.getValue().size()==2){
				count = count + entry1.getValue().get(0).size();
			}
		}
		
		log.info("CardiocerebralPdfUtil createPdfNo4 analysisList size -- "+ count);
		
		if(count<=34){	//数据不能超过36行，否则换页
			for (Entry<String, List<List<String>>> entry : analysisList.entrySet()) {
				log.info("CardiocerebralPdfUtil createPdfNo4 entry toString---"+entry.getKey().toString());
				cell = new PdfPCell(new Paragraph(entry.getKey().toString(), cellContent));
				cell.setBackgroundColor(new BaseColor(245,245,245));	//灰色背景
				cell.setUseAscender(true);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setLeading(0,1.5f);	//行间距
				//文字内容周边距  
				cell.setPaddingTop(4f);
				cell.setPaddingBottom(7f);
				cell.setPaddingLeft(4f);
				cell.setPaddingRight(4f);
				table.addCell(cell);
				
				if(entry.getValue().size()==2){
					cell = new PdfPCell(new Paragraph(dealList2str(entry.getValue().get(0)), cellContent));
					cell.setUseAscender(true);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setLeading(0,1.5f);	//行间距
					cell.setPaddingBottom(7f);
					cell.setPaddingTop(4f);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(dealList2str(entry.getValue().get(1)), cellContent));
					cell.setUseAscender(true);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setLeading(0,1.5f);	//行间距
					cell.setPaddingBottom(7f);
					cell.setPaddingTop(4f);
					table.addCell(cell);
				}
			}
			no5 = this.createPdfNo501();	//生成第五页
		}else{
			Map<String, List<List<String>>> headMap = new LinkedHashMap<String, List<List<String>>>();
			Map<String, List<List<String>>> tailMap = new LinkedHashMap<String, List<List<String>>>();
			
			for (Entry<String, List<List<String>>> entryTwo : analysisList.entrySet()) {
				String key = entryTwo.getKey();
				List<List<String>> listsOne = new ArrayList<List<String>>();
				List<List<String>> listsTwo = new ArrayList<List<String>>();
				
				if(entryTwo.getValue().size()==2){
					List<String> listOne = entryTwo.getValue().get(0);	//疾病list
					List<String> listTwo = entryTwo.getValue().get(1);	//检测项list
					
					List<String> listTemp1 = new ArrayList<String>();
					List<String> listTemp2 = new ArrayList<String>();
					
					for (String str : listOne) {
						if(num<=34){
							listTemp1.add(str);
						}else{
							listTemp2.add(str);
						}
						if(34==num){
							listsOne.add(listTemp1);
							listsOne.add(listTwo);
							headMap.put(key, listsOne);
						}
						num += 1;
					}
					if(num<34){
						if(listTemp1.size()>0){
							listsOne.add(listTemp1);
							listsOne.add(listTwo);
							headMap.put(key, listsOne);
						}
					}else{
						if(listTemp2.size()>0){
							listsTwo.add(listTemp2);
							listsTwo.add(listTwo);
							tailMap.put(key, listsTwo);
						}
					}
				}
			}
			
			/* ****用于分割分页检测项超出页面问题**** */
			String repeatKey="";	//存放两个list重复的key
			for (Entry<String, List<List<String>>> entryOne : headMap.entrySet()) {
				String keyOne = entryOne.getKey();
				for (Entry<String, List<List<String>>> entryTwo : tailMap.entrySet()) {
					String keyTwo = entryTwo.getKey();
					if(keyOne.equals(keyTwo)){
						repeatKey = keyOne;		//默认只会有一个重复
					}
				}
			}
			
			if(!StringUtils.isEmpty(repeatKey)){	//有重复的key(分割)
				List<String> listTemp1 = new ArrayList<String>();	//存放分割检测项上部分
				List<String> listTemp2 = new ArrayList<String>();	//存放分割检测项下部分
				
				List<List<String>> listUp = headMap.get(repeatKey);		//分割疾病上半部分
				List<List<String>> listDown = tailMap.get(repeatKey);	//分割疾病下半部分
				if(listUp.size()==2){
					int numTemp = 0;
					List<String> listSun1 = listUp.get(0);			//上部分疾病
					List<String> listSun2 = listUp.get(1);			//上部分检测项
					if(listSun2.size()>listSun1.size()){			//检测项条数是否大于分割上部分的疾病条数
						for (String str : listSun2) {
							if(numTemp<listSun1.size()){			//如果大于，就将检测项分割成两个集合（第一个检测项集合条数，为分割上部分的疾病的条数）
								listTemp1.add(str);
							}else{
								listTemp2.add(str);
							}
							numTemp++;
						}
						//替换为分割后的检测项
						listUp.set(1,listTemp1);
						listDown.set(1,listTemp2);
					}
				}
				
				/*//清除之前的检测项
				listUp.remove(1);
				listDown.remove(1);*/
				
			}
			
			if(headMap!=null){
				for (Entry<String, List<List<String>>> entry : headMap.entrySet()) {
					log.info("CardiocerebralPdfUtil createPdfNo4 entry toString---"+entry.getKey().toString());
					cell = new PdfPCell(new Paragraph(entry.getKey().toString(), cellContent));
					cell.setBackgroundColor(new BaseColor(245,245,245));	//灰色背景
					cell.setUseAscender(true);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setLeading(0,1.5f);	//行间距
					//文字内容周边距  
					cell.setPaddingTop(4f);
					cell.setPaddingBottom(7f);
					cell.setPaddingLeft(4f);
					cell.setPaddingRight(4f);
					table.addCell(cell);
					
					if(entry.getValue().size()==2){
						cell = new PdfPCell(new Paragraph(dealList2str(entry.getValue().get(0)), cellContent));
						cell.setUseAscender(true);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setLeading(0,1.5f);	//行间距
						cell.setPaddingBottom(7f);
						cell.setPaddingTop(4f);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph(dealList2str(entry.getValue().get(1)), cellContent));
						cell.setUseAscender(true);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setLeading(0,1.5f);	//行间距
						cell.setPaddingBottom(7f);
						cell.setPaddingTop(4f);
						table.addCell(cell);
					}
				}
			}
			
			if(tailMap!=null){
				no5 = this.createPdfNo5(tailMap);	//生成第五页
			}
		}
		
		document.add(table);
		document.close();
		writer.flush();
		out.flush();
		out.close();
		
		fourAndFivePathMap.put("fourPath", outFilePath);
		fourAndFivePathMap.put("fivePath", no5);
		
		return fourAndFivePathMap;
	}
	
	//生成第五页
	public String createPdfNo5(Map<String, List<List<String>>> tailMap) throws Exception {
		String outFilePath = fileOutPath+File.separator+"005.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_FIVE);
		FileOutputStream out = new FileOutputStream(outFilePath);
		Document document = new Document(PageSize.A4,15,15,50,0);//左右上下
		PdfWriter writer = PdfWriter.getInstance(document, out);
		 /*HeaderFooter footer=new HeaderFooter(new Phrase("-",chinese),new Phrase("-",chinese));  
         
          * 0是靠左 
          * 1是居中 
          * 2是居右 
           
         footer.setAlignment(1);  
         //footer.setBorder(Rectangle.BOX);  
         document.setFooter(footer);*/
		document.open();
		PdfContentByte cb = writer.getDirectContent();
		PdfImportedPage page3 = writer.getImportedPage(reader,1);
		cb.addTemplate(page3, 0, 0);
		PdfPTable table = new PdfPTable(3);
		table.setSplitLate(false);
		//本地
		//BaseFont wryhChinese = BaseFont.createFont("C:/WINDOWS/Fonts/MSYHL.TTC,1",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		//服务器
		//BaseFont wryhChinese = BaseFont.createFont("C:/WINDOWS/Fonts/msyh.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		
		//linux系统
		BaseFont wryhChinese = BaseFont.createFont("/usr/share/fonts/zh_CN/msyh.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		Font bgColorChinese = new Font(wryhChinese, 11, Font.NORMAL,new BaseColor(224,255,255));//表头字体
		Font cellContent = new Font(wryhChinese, 10, Font.NORMAL,BaseColor.BLACK);// 创建字体，设置family，size，style,还可以设置color 
		cellContent.setColor(54, 54, 54);	//字体颜色
		PdfPCell cell = null;
		
		for (Entry<String, List<List<String>>> entry : tailMap.entrySet()) {
			log.info("CardiocerebralPdfUtil createPdfNo5 entry toString---"+entry.getKey().toString());
			cell = new PdfPCell(new Paragraph(entry.getKey().toString(), cellContent));
			cell.setBackgroundColor(new BaseColor(245,245,245));	//灰色背景
			cell.setUseAscender(true);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0,1.5f);	//行间距
			//文字内容周边距  
			cell.setPaddingTop(4f);
			cell.setPaddingBottom(7f);
			cell.setPaddingLeft(4f);
			cell.setPaddingRight(4f);
			table.addCell(cell);
			
			if(entry.getValue().size()==2){
				cell = new PdfPCell(new Paragraph(dealList2str(entry.getValue().get(0)), cellContent));
				cell.setUseAscender(true);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setLeading(0,1.5f);	//行间距
				cell.setPaddingBottom(7f);
				cell.setPaddingTop(4f);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(dealList2str(entry.getValue().get(1)), cellContent));
				cell.setUseAscender(true);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setLeading(0,1.5f);	//行间距
				cell.setPaddingBottom(7f);
				cell.setPaddingTop(4f);
				table.addCell(cell);
			}
		}
		document.add(table);
		document.close();
		writer.flush();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//生成第五页（只复制）
	public String createPdfNo501() throws Exception {
		String outFilePath = fileOutPath+File.separator+"005.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_FIVE);
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}

	//生成第六页
	public String createPdfNo6(String sex) throws Exception {
		String outFilePath = fileOutPath+File.separator+"006.pdf";
		PdfReader reader = null;
		if(-1!=sex.indexOf("女")){
			reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_SIX_GRIL);
		}else{
			reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_SIX_BOY);
		}
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
		
	//生成第七页
	public String createPdfNo7(List<HashMap<String, List<Map<String,String>>>> specificPhy) throws Exception {
		String outFilePath = fileOutPath+File.separator+"007.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_SEVEN);
		FileOutputStream out = new FileOutputStream(outFilePath);
		Document document = new Document(PageSize.A4,15,15,160,0);//左右上下
		PdfWriter writer = PdfWriter.getInstance(document, out);
		document.open();
		PdfContentByte cb = writer.getDirectContent();
		PdfImportedPage page3 = writer.getImportedPage(reader,1);
		cb.addTemplate(page3, 0, 0);
		float[] f = {100,300};				//两列宽度
		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(f);
		table.setSplitLate(false);
		//win10系统
		//BaseFont wryhChinese = BaseFont.createFont("C:/WINDOWS/Fonts/MSYHL.TTC,1",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		//服务器
		//BaseFont wryhChinese = BaseFont.createFont("C:/WINDOWS/Fonts/msyh.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		
		//linux系统
		BaseFont wryhChinese = BaseFont.createFont("/usr/share/fonts/zh_CN/msyh.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		Font bgColorChinese = new Font(wryhChinese, 11, Font.NORMAL,BaseColor.WHITE);
		Font cellContent = new Font(wryhChinese, 10, Font.NORMAL,BaseColor.BLACK);// 创建字体，设置family，size，style,还可以设置color 
		cellContent.setColor(54, 54, 54);	//字体颜色
		PdfPCell cell = null;
		PdfPCell cellSun = null;
		
		cell = new PdfPCell(new Paragraph("项目分类", bgColorChinese));
		//cell.setBackgroundColor(new BaseColor(235,245,238));
		cell.setBackgroundColor(new BaseColor(69,205,128));
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(4f);
		cell.setPaddingBottom(7f);
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph("检查内容及意义", bgColorChinese));
		cell.setBackgroundColor(new BaseColor(69,205,128));
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(4f);
		cell.setPaddingBottom(7f);
		table.addCell(cell);
		
		List<LinkedHashMap<String, List<Map<String,String>>>> mapLists = new ArrayList<LinkedHashMap<String, List<Map<String,String>>>>();
		mapLists = ProjectMapComparator.mapComparator(specificPhy);
		
		for (LinkedHashMap<String, List<Map<String,String>>> mapListMap : mapLists) {
			for (Entry<String, List<Map<String,String>>> entry : mapListMap.entrySet()) {
				log.info("CardiocerebralPdfUtil createPdfNo7 entry.getKey().toString()---"+entry.getKey().toString());
				cell = new PdfPCell(new Paragraph(entry.getKey().toString(), cellContent));
				cell.setBackgroundColor(new BaseColor(245,245,245));	//灰色背景
				cell.setUseAscender(true);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setLeading(0,1.5f);	//行间距
				//cell.setFixedHeight(18f);
				/* 文字内容周边距  */
				cell.setPaddingBottom(7f);
				cell.setPaddingLeft(4f);
				cell.setPaddingRight(4f);
				table.addCell(cell);
				
				if(entry.getKey().equals("血液检验")){
					/* 嵌套table（一列下分两列）*/ 
					float[] fSun = {100,300};		//两列宽度
					PdfPTable tableSun = new PdfPTable(2);
					tableSun.setTotalWidth(fSun);
					for (int i = 0; i < entry.getValue().size(); i++) {
						Map<String,String> tempMap = entry.getValue().get(i);
						for (Map.Entry<String, String> entrySun : tempMap.entrySet()) {
							if(!entrySun.getKey().toString().equals("other")&&!entrySun.getKey().equals("six")){
								cellSun = new PdfPCell(new Paragraph(entrySun.getKey().toString(), cellContent));
								cellSun.setPaddingBottom(7f);
								cellSun.setLeading(0,1.5f);	//行间距
								cellSun.setUseAscender(true);
								cellSun.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cellSun.setHorizontalAlignment(Element.ALIGN_CENTER);
								tableSun.addCell(cellSun);
								cellSun = new PdfPCell(new Paragraph(entrySun.getValue().toString(), cellContent));
								cellSun.setPaddingBottom(7f);
								cellSun.setPaddingLeft(4f);
								cellSun.setLeading(0,1.5f);	//行间距
								cellSun.setUseAscender(true);
								//cellSun.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cellSun.setHorizontalAlignment(Element.ALIGN_CENTER);
								tableSun.addCell(cellSun);
							}
						}
					}
					cell = new PdfPCell(tableSun);
					table.addCell(cell);
				}else{
					for (int i = 0; i < entry.getValue().size(); i++) {
						Map<String,String> tempMap = entry.getValue().get(i);
						for (Map.Entry<String, String> entrySun : tempMap.entrySet()) {
							if(entrySun.getKey().equals("other")||entrySun.getKey().equals("six")){
								cell = new PdfPCell(new Paragraph(entrySun.getValue().toString(), cellContent));
								cell.setPaddingBottom(7f);
								cell.setPaddingLeft(4f);
								cell.setLeading(0,1.5f);	//行间距
								cell.setUseAscender(true);
								//cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								table.addCell(cell);
							}
						}
					}
				}
			}
		}
		document.add(table);
		document.close();
		writer.flush();
		out.flush();
		out.close();
		return outFilePath;
	}
		
	//生成第八页
	public String createPdfNo8() throws Exception {
		String outFilePath = fileOutPath+File.separator+"008.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_EIGHT);
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//生成第九页
	public String createPdfNo9() throws Exception {
		String outFilePath = fileOutPath+File.separator+"009.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_NINE);
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
		
	//生成第十页
	public String createPdfNo10() throws Exception {
		String outFilePath = fileOutPath+File.separator+"010.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_TEN);
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
		
	//生成第十一页
	public String createPdfNo11() throws Exception {
		String outFilePath = fileOutPath+File.separator+"011.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_ELEVEN);
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//生成第十二页
	public String createPdfNo12(int type) throws Exception {
		String outFilePath = fileOutPath+File.separator+"012.pdf";
		PdfReader reader = null;
		
		reader = new PdfReader(PhyReportModelPath.CARDIOCEREBRALMODELPATH_TWELVE);
		
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//PDF每页进行拼接
	public Map<String, String> createPDF(List<String> pdfNoList,String reportPath) throws Exception {
		Map<String, String> maps = new HashMap<String, String>();
		Iterator<String> pdfNoPath = pdfNoList.iterator();
		List<PdfReader> readerList = new ArrayList<PdfReader>();
		FileOutputStream out = new FileOutputStream(rootPath+reportPath);
		while (pdfNoPath.hasNext()) {
			PdfReader reader = new PdfReader(pdfNoPath.next());
			readerList.add(reader);
		}
		Iterator<PdfReader> iteratorPDFReader = readerList.iterator();
		Document document = new Document();
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		PdfWriter writer = PdfWriter.getInstance(document,out);
		document.open(); 
		PdfContentByte cb = writer.getDirectContent();
		while (iteratorPDFReader.hasNext()) {
			PdfReader pdfReader = iteratorPDFReader.next();		//拼接的每个pdf
			Integer pageNumber = pdfReader.getNumberOfPages();
			for (int i = 1; i < pageNumber+1; i++) {
				document.newPage();
				PdfImportedPage page = writer.getImportedPage(pdfReader,i);
				cb.addTemplate(page, 0, 0);
			}
			
		}
		
		out.flush();
		document.close();
		out.close();
		for (PdfReader pdfReader : readerList) {
			pdfReader.close();
		}
		
		PdfReader readerPage = new PdfReader(rootPath+reportPath);
		Integer pages = readerPage.getNumberOfPages();
		readerPage.close();
		maps.put("pdfPath", rootPath+reportPath);
		if(pages!=null){
			maps.put("pages", String.valueOf(pages));
		}else{
			maps.put("pages", "");
		}
		
		return maps;
	}
	
	//删除PDF文件
	public void deletePdf(List<String> pdfNoList){
		Iterator<String> pdfNoPath = pdfNoList.iterator();
		while (pdfNoPath.hasNext()) {
			File delFile = new File(pdfNoPath.next());
			delFile.delete();
			log.info("CardiocerebralPdfUtil delete pdf tmp file name : " + delFile.getName());
		}
	}
	
	private String dealList2str(List<String> value) {
		StringBuilder strBuilder = new StringBuilder();
		for(int i=0;i<value.size();i++){
			strBuilder.append(value.get(i)).append("\n");
		}
		if(value.size()<1){
			strBuilder.append("————");
		}
		return strBuilder.toString();
	}
	
	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getFileOutPath() {
		return fileOutPath;
	}

	public void setFileOutPath(String fileOutPath) {
		this.fileOutPath = fileOutPath;
	}
}
