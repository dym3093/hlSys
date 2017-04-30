package cn.yuanmeng.labelprint.pdf;

import java.awt.Color;
import java.awt.print.PageFormat;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.yuanmeng.labelprint.entity.ReportDetail;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.VerticalText;

public class ReportDetailPdf {
	private ReportDetail reportDetail;

	public ReportDetailPdf() {

	}

	public ReportDetailPdf(ReportDetail reportDetail) {
		this.reportDetail = reportDetail;
	}

	public static void main(String[] args) {

		// 调用第一个方法，向C盘生成一个名字为ITextTest.pdf 的文件
		// ReportDetailPdf rdp = new ReportDetailPdf();
		// rdp.writeSimplePdf();
		//
		// // 调用第二个方法，向C盘名字为ITextTest.pdf的文件，添加章节。
		// try {
		// //rdp.writeCharpter();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
//		testNewDocument2();
		
		// 测试标准标签
		ReportDetail reportDetail = new ReportDetail();
		reportDetail.setCode("SY000自定义a08");
		reportDetail.setName("张三");
		reportDetail.setSex("男");
		reportDetail.setSalesMan("张四");
		reportDetail.setEntering("批次1538");
		testLabelPrint(reportDetail);
	}

	public static void testLabelPrint(ReportDetail reportDetail) {
		Document document = new Document(PageSize.A10.rotate(), 2, 2, 2, 2);
		Rectangle rect = new Rectangle(1000, 1000, 50, 30);
//		Document document = new Document(rect);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("D://testpdf//A"+ reportDetail.getCode() + ".pdf"));
			document.open();
			// 向文档中添加内容
			BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bfChinese, 5, Font.NORMAL);
	
			Paragraph pragraph1 = new Paragraph("条形码:  "
					+ reportDetail.getCode(), FontChinese);
			Paragraph pragraph2 = new Paragraph("姓    名:  "
					+ reportDetail.getName(), FontChinese);
			Paragraph pragraph3 = new Paragraph("性    别:  "
					+ reportDetail.getSex(), FontChinese);
			Paragraph pragraph4 = new Paragraph("批    次:  "
					+ reportDetail.getEntering(), FontChinese);
			Paragraph pragraph5 = new Paragraph("营销员:  "
					+ reportDetail.getSalesMan(), FontChinese);
			document.add(pragraph1);
			document.add(pragraph2);
			document.add(pragraph3);
			document.add(pragraph4);
			document.add(pragraph5);

		} catch (Exception e) {
			e.printStackTrace();
		} 
		document.close();
	}

	public static void testNewDocument2() {
		Rectangle rect = new Rectangle(500, 500, 800, 600);
		Document document = new Document(rect);
		try {
			PdfWriter.getInstance(document, new FileOutputStream("d:/NewDocument2.pdf"));
			document.open();
			document.add(new Paragraph("Hello World"));
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		document.close();
	}

	public void writeSimplePdf() {

		// 1.新建document对象
		// 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		try {
			// 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
			// 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("fileName.pdf"));
			// 3.打开文档
			document.open();
			// 4.向文档中添加内容
			// 通过 com.lowagie.text.Paragraph
			// 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落
			document.add(new Paragraph("First page of the document."));
			document.add(new Paragraph("with different color and font type.",
					FontFactory.getFont(FontFactory.COURIER, Float
							.valueOf("14").floatValue(), Font.BOLD,
							new BaseColor(255, 150, 200))));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 5.关闭文档
			document.close();
		}

	}

	/**
	 * 添加含有章节的pdf文件
	 * 
	 * @throws Exception
	 */
	public void writeCharpter(ReportDetail reportDetail) {

		// 新建document对象 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
		Document document = new Document(PageSize.A4, 20, 20, 20, 20);

		try {
			// 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
			PdfWriter writer = PdfWriter.getInstance(
					document,
					new FileOutputStream("D://testpdf//"
							+ reportDetail.getCode() + ".pdf"));
			// 打开文件
			document.open();
			// 向文档中添加内容
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
			Paragraph pragraph1 = new Paragraph("条形码:  "
					+ reportDetail.getCode(), FontChinese);
			Paragraph pragraph2 = new Paragraph("姓    名:  "
					+ reportDetail.getName(), FontChinese);
			Paragraph pragraph3 = new Paragraph("性    别:  "
					+ reportDetail.getSex(), FontChinese);
			Paragraph pragraph4 = new Paragraph("批    次:  "
					+ reportDetail.getEntering(), FontChinese);
			Paragraph pragraph5 = new Paragraph("营销员:  "
					+ reportDetail.getSalesMan(), FontChinese);
			document.add(pragraph1);
			document.add(pragraph2);
			document.add(pragraph3);
			document.add(pragraph4);
			document.add(pragraph5);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 关闭文档
		document.close();
	}

}
