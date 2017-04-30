package org.hpin.reportdetail.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class BuidReportExcel {
	
	private Logger log = Logger.getLogger(BuidReportExcel.class);
	public static void main(String[] args) {
		BuidReportExcel bre=new BuidReportExcel();
		String str1="1111";
		String str2="2222";
		List<String> liststr=new ArrayList<String>();
		liststr.add(str2);liststr.add(str1);
		
		List<List<String>> rowList=new ArrayList<List<String>>();
		rowList.add(liststr);
		
		bre.writeXls("d:/", "a.xls", rowList);
	}
	public void writeXls(String path ,String filename, List<List<String>> rowList) {
		
		try {
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("test");
			
			HSSFRow row0 = hssfSheet1.createRow(0);
			Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
			CellStyle cellStyle = hssfWorkbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
			cellStyle.setFont(font);
			
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("序号");
			Cell cell1 = row0.createCell(1);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue("条码");
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("姓名");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("性别");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("年龄");
			Cell cell6 = row0.createCell(5);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("支公司");
			Cell cell7 = row0.createCell(6);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("营销员");
			Cell cell8 = row0.createCell(7);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("批次号");
			
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row1 = hssfSheet1.createRow(rowNum+1);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					String str=row.get(colI);
					createCell(hssfWorkbook, row1, colI, str, cellStyle);
				}
				
			}
			OutputStream os=new FileOutputStream(new File(path,filename));
			hssfWorkbook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			log.error("BuidReportExcel writeXls", e);
		} catch (IOException e) {
			log.error("BuidReportExcel writeXls", e);
		}
	}
	
	//打印批次生成EXCEL
	public void createPrintBthXls(String path ,String filename, List<List<String>> rowList) {
		
		try {
			File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("test");
			
			HSSFRow row0 = hssfSheet1.createRow(0);
			Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
			CellStyle cellStyle = hssfWorkbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
			cellStyle.setFont(font);
			
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("序号");
			
			Cell cell1 = row0.createCell(1);
			setBackgroundColor(hssfWorkbook, cell1, "条码", HSSFColor.RED.index );
			
			Cell cell2 = row0.createCell(2);
			setBackgroundColor(hssfWorkbook, cell2, "姓名", HSSFColor.RED.index );
			
			Cell cell3 = row0.createCell(3);
			setBackgroundColor(hssfWorkbook, cell3, "性别", HSSFColor.RED.index );
			
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("年龄");
			
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("省");
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("市");
			
			Cell cell7 = row0.createCell(7);
			setBackgroundColor(hssfWorkbook, cell7, "支公司", HSSFColor.RED.index );
			
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("部门");
			
			Cell cell9 = row0.createCell(9);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("所属公司");
			
			Cell cell10 = row0.createCell(10);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("套餐");
			
			Cell cell11 = row0.createCell(11);
			cell11.setCellStyle(cellStyle);
			cell11.setCellValue("采样日期");
			
			Cell cell12 = row0.createCell(12);
			setBackgroundColor(hssfWorkbook, cell12, "营销员", HSSFColor.RED.index );
			
			Cell cell13 = row0.createCell(13);
			setBackgroundColor(hssfWorkbook, cell13, "批次号", HSSFColor.RED.index );
			
			Cell cell14 = row0.createCell(14);
			cell14.setCellStyle(cellStyle);
			cell14.setCellValue("检测机构");
			Cell cell15 = row0.createCell(15);
			cell15.setCellStyle(cellStyle);
			cell15.setCellValue("样本类型");
			
			/*增加显示支公司寄送地址、收件人、收件人电话; modified by henry.xu 20160811*/
			Cell cell16 = row0.createCell(16);
			cell16.setCellStyle(cellStyle);
			cell16.setCellValue("寄送地址");
			
			Cell cell17 = row0.createCell(17);
			cell17.setCellStyle(cellStyle);
			cell17.setCellValue("收件人");
			
			Cell cell18 = row0.createCell(18);
			cell18.setCellStyle(cellStyle);
			cell18.setCellValue("收件人电话");
			
			/**@since 2016年10月28日10:25:00 add by chenqi*/
			Cell cell19 = row0.createCell(19);
			cell19.setCellStyle(cellStyle);
			cell19.setCellValue("项目负责人");
			
			Cell cell20 = row0.createCell(20);
			cell20.setCellStyle(cellStyle);
			cell20.setCellValue("项目名称");
			
			Cell cell21 = row0.createCell(21);
			cell21.setCellStyle(cellStyle);
			cell21.setCellValue("项目编码");
			
			Cell cell22 = row0.createCell(22);
			cell22.setCellStyle(cellStyle);
			cell22.setCellValue("扫码支公司");
			
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row1 = hssfSheet1.createRow(rowNum+1);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					String str=row.get(colI);
					createCell(hssfWorkbook, row1, colI, str, cellStyle);
				}
				
			}
			OutputStream os=new FileOutputStream(new File(path,filename));
			hssfWorkbook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			log.error("BuidReportExcel writeXls", e);
		} catch (IOException e) {
			log.error("BuidReportExcel writeXls", e);
		}
	}
	
	//设置字体格式
	public static Font createFonts(Workbook wb, short bold, String fontName,boolean isItalic, short hight) {
		Font font = wb.createFont();
		font.setFontName(fontName);
		font.setBoldweight(bold);
		font.setItalic(isItalic);
		font.setFontHeight(hight);
		return font;
	}
	
	//设置内容
	public static void createCell(Workbook wb, Row row, int column,String value, CellStyle cellStyle) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		cell.setCellStyle(cellStyle);
	}
	
	//设置背景颜色
	public static void setBackgroundColor(HSSFWorkbook hssfWorkbook, Cell cell, String value, short fg) {
		HSSFCellStyle style = hssfWorkbook.createCellStyle();
		Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
		style.setFillForegroundColor(fg);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
		style.setFont(font);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
}
