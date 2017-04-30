package org.hpin.physical.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hpin.common.PhyReportModelPath;
import org.hpin.physical.entity.PhyReport;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
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
 * 打印报告生成工具类
 * @author ybc
 * @since 2016-06-29
 */
public class PhysicalPrintPdfUtil {
	
	private Logger log = Logger.getLogger(PhysicalPrintPdfUtil.class);

	//物理根目录
	private String rootPath;
	
	//报告输出地址
	private String fileOutPath ;
	
	public PhysicalPrintPdfUtil(){}
	
	public void initPrintFilePath(String fileOutPath){
		this.fileOutPath = this.rootPath + fileOutPath;
	}
	
	/*public PhysicalPrintPdfUtil(String fileOutPath) {
		this.fileOutPath = this.rootPath + fileOutPath;
	}*/
	
	//生成第一页
	public String createPdfNo1(int type,PhyReport reportUser) throws Exception {
		String outFilePath = fileOutPath+File.separator+"p001.pdf";
		PdfReader reader = null;
		//0:国寿;1:远盟
		if(0==type){
			reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_ONE_GS);
		}else{
			reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_ONE);
		}
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		AcroFields form = stamp.getAcroFields();
		form.setField("001name",reportUser.getUserName());
		form.setField("001sex",reportUser.getUserSex()); 
		form.setField("001age",reportUser.getUserAge());
		stamp.setFormFlattening(true);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//生成第二页
	public String createPdfNo2(PhyReport reportUser) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String now  = dateFormat.format(new Date());
		String outFilePath = fileOutPath+File.separator+"p002.pdf";
		PdfReader reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_TWO);
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		AcroFields form = stamp.getAcroFields();
		form.setField("002name", reportUser.getUserName());
		form.setField("002sex", reportUser.getUserSex());
		form.setField("002age",reportUser.getUserAge());
		form.setField("002code", reportUser.getGeneCode());
		form.setField("002date", dateFormat.format(reportUser.getReportDate()));
		form.setField("002doctor","陈兆龙");
		form.setField("002time",now);
		stamp.setFormFlattening(true);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//生成第三页
	public String createPdfNo3(int type,String name,String sex) throws Exception {
		String outFilePath = fileOutPath+File.separator+"p003.pdf";
		PdfReader reader = null;
		//0:国寿;1:远盟
		if(0==type){
			if(-1!=sex.indexOf("女")){
				reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_THREE_GS_GRIL);
			}else{
				reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_THREE_GS_BOY);
			}
		}else{
			if(-1!=sex.indexOf("女")){
				reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_THREE_GRIL);
			}else{
				reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_THREE_BOY);
			}
		}
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		AcroFields form = stamp.getAcroFields();
		if(-1!=sex.indexOf("女")){
			form.setField("003grilcall",name+"女士:");
		}else{
			form.setField("003boycall", name+"先生:");
		}
		stamp.setFormFlattening(true);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//生成第四页
	public String createPdfNo4(int type,String name,String sex,Map<String,List<String>> analysis) throws Exception {
		String outFilePath = fileOutPath+File.separator+"p004.pdf";
		PdfReader reader = null;
		//0:国寿;1:远盟
		if(0==type){
			if(-1!=sex.indexOf("女")){
				reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_FOUR_GS_GRIL);
			}else{
				reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_FOUR_GS_BOY);
			}
		}else{
			if(-1!=sex.indexOf("女")){
				reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_FOUR_GRIL);
			}else{
				reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_FOUR_BOY);
			}
		}
		FileOutputStream out = new FileOutputStream(outFilePath);
		Rectangle rect = new Rectangle(1207.55f,824.9f);
		Document document = new Document(rect,21,620,140,0);
		PdfWriter writer = PdfWriter.getInstance(document, out);
		document.open();
		PdfContentByte cb = writer.getDirectContent();
		PdfImportedPage page3 = writer.getImportedPage(reader,1);
		cb.addTemplate(page3, 0, 0);
		PdfPTable table = new PdfPTable(2);
		//BaseFont wryhChinese = BaseFont.createFont("C:/WINDOWS/Fonts/MSYHL.TTC,1",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		BaseFont wryhChinese = BaseFont.createFont("C:/WINDOWS/Fonts/msyh.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		Font bgColorChinese = new Font(wryhChinese, 12, Font.NORMAL,new BaseColor(0,163,76));
		Font cellContent = new Font(wryhChinese, 12, Font.NORMAL,BaseColor.BLACK);// 创建字体，设置family，size，style,还可以设置color 
		PdfPCell cell = null;
		cell = new PdfPCell(new Paragraph("项目分类", bgColorChinese));
		cell.setBackgroundColor(new BaseColor(235,245,238));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(4f);
		cell.setPaddingBottom(7f);
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph("较高风险项目及评级", bgColorChinese));
		cell.setBackgroundColor(new BaseColor(235,245,238));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(4f);
		cell.setPaddingBottom(7f);
		table.addCell(cell);
		
		List<Entry<String, List<String>>> analysisList = new ArrayList<Entry<String, List<String>>>(analysis.entrySet());
		Collections.sort(analysisList,AnalyseTypeComparator.getInstance());
		for(Entry<String, List<String>> entry: analysisList){
			cell = new PdfPCell(new Paragraph(entry.getKey().toString(), cellContent));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(dealList2str(entry.getValue()), cellContent));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		document.add(table);
		document.close();
		writer.flush();
		out.flush();
		out.close();
		return outFilePath;
	}
	
	//生成第五页
	public String createPdfNo5(int type,Map<String,String> specificPhy) throws Exception {
		String outFilePath = fileOutPath+File.separator+"p005.pdf";
		PdfReader reader = null;
		//0:国寿;1:远盟
		if(0==type){
			reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_FIVE_GS);
		}else{
			reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_FIVE);
		}
		FileOutputStream out = new FileOutputStream(outFilePath);
		Rectangle rect = new Rectangle(1207.55f,824.9f);
		Document document = new Document(rect,21,620,140,0);
		PdfWriter writer = PdfWriter.getInstance(document, out);
		document.open();
		PdfContentByte cb = writer.getDirectContent();
		PdfImportedPage page3 = writer.getImportedPage(reader,1);
		cb.addTemplate(page3, 0, 0);
		float[] widths = {22f,78f};
		PdfPTable table = new PdfPTable(widths);
		//win10系统
		//BaseFont wryhChinese = BaseFont.createFont("C:/WINDOWS/Fonts/MSYHL.TTC,1",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		//服务器
		BaseFont wryhChinese = BaseFont.createFont("C:/WINDOWS/Fonts/msyh.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		Font bgColorChinese = new Font(wryhChinese, 12, Font.NORMAL,BaseColor.WHITE);
		Font cellContent = new Font(wryhChinese, 12, Font.NORMAL,BaseColor.BLACK);// 创建字体，设置family，size，style,还可以设置color 
		PdfPCell cell = null;
		cell = new PdfPCell(new Paragraph("项目分类", bgColorChinese));
		cell.setBackgroundColor(new BaseColor(167,168,171));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(4f);
		cell.setPaddingBottom(7f);
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph("检查内容及意义", bgColorChinese));
		cell.setBackgroundColor(new BaseColor(167,168,171));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(4f);
		cell.setPaddingBottom(7f);
		table.addCell(cell);
		List<Entry<String, String>> specificPhyList = new ArrayList<Entry<String, String>>(specificPhy.entrySet());
		Collections.sort(specificPhyList,PhyTypeComparator.getInstance());
		for(Entry<String, String> entry: specificPhyList){
			cell = new PdfPCell(new Paragraph(entry.getKey().toString(), cellContent));
			cell.setBackgroundColor(new BaseColor(243,229,199));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(entry.getValue().toString(), cellContent));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		document.add(table);
		document.close();
		writer.flush();
		out.flush();
		out.close();
		return outFilePath;
	}

	//生成第六页
	public String createPdfNo6(int type) throws Exception {
		String outFilePath = fileOutPath+File.separator+"p006.pdf";
		PdfReader reader = null;
		//0:国寿;1:远盟
		if(0==type){
			reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_SIX_GS);
		}else{
			reader = new PdfReader(PhyReportModelPath.PRINT_MODELPATH_SIX);
		}
		FileOutputStream out = new FileOutputStream(outFilePath);
		PdfStamper stamp = new PdfStamper(reader, out);
		stamp.close();
		out.flush();
		out.close();
		return outFilePath;
	}
		
	
	//PDF每页进行拼接
	public String createPDF(List<String> pdfNoList,String reportPath) throws Exception {
		
		Iterator<String> pdfNoPath = pdfNoList.iterator();
		List<PdfReader> readerList = new ArrayList<PdfReader>();
		FileOutputStream out = new FileOutputStream(rootPath+reportPath);
		while (pdfNoPath.hasNext()) {
			PdfReader reader = new PdfReader(pdfNoPath.next());
			readerList.add(reader);
		}
		Iterator<PdfReader> iteratorPDFReader = readerList.iterator();
		Rectangle rect = new Rectangle(1207.55f,824.9f);
		Document document = new Document(rect,21,620,140,0);		//设置合并生成页面的大小
		PdfWriter writer = PdfWriter.getInstance(document,out);
		document.open(); 
		PdfContentByte cb = writer.getDirectContent();
		while (iteratorPDFReader.hasNext()) {
			PdfReader pdfReader = iteratorPDFReader.next();
			document.newPage();
			PdfImportedPage page = writer.getImportedPage(pdfReader,1);
			cb.addTemplate(page,0,0);
		}
		out.flush();
		document.close();
		out.close();
		for (PdfReader pdfReader : readerList) {
			pdfReader.close();
		}
		return rootPath+reportPath;
	}
	
	//删除PDF文件
	public void deletePdf(List<String> pdfNoList){
		Iterator<String> pdfNoPath = pdfNoList.iterator();
		while (pdfNoPath.hasNext()) {
			File delFile = new File(pdfNoPath.next());
			delFile.delete();
			log.info("delete pdf tmp file name : " + delFile.getName());
		}
	}
	
	private String dealList2str(List<String> value) {
		StringBuilder strBuilder = new StringBuilder();
		for(int i=0;i<value.size();i++){
			if(i==value.size()-1){
				strBuilder.append(value.get(i));
			}else{
				strBuilder.append(value.get(i)).append("\n");
			}
		}
		return strBuilder.toString();
	}
	
	public String getFileOutPath() {
		return fileOutPath;
	}

	public void setFileOutPath(String fileOutPath) {
		this.fileOutPath = fileOutPath;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
}
