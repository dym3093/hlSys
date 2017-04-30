package org.hpin.venueStaffSettlement.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * @author Carly
 * @since 2016年12月6日14:53:32
 * @创建excel
 */
public class CreateNewExcel {
	
	private static Logger logger = Logger.getLogger(CreateNewExcel.class);
	
	/**
	 * @param path
	 * @param filename
	 * @param rowList
	 * @since 2016年12月6日14:53:00
	 * 该方法用于会议费用费用的导出
	 */
	public static void createNewXls(String path ,String filename, List<List<String>> rowList) {
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
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue("会议号");
			
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("会议日期");
			
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("项目编码");
			
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("项目负责人");

			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("项目名称");
			
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("姓名");
			
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("出差补助");
			
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("讲师费");
			
			Cell cell9 = row0.createCell(9);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("市内交通费");
			
			Cell cell10 = row0.createCell(10);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("往返交通费");
			
			Cell cell11 = row0.createCell(11);
			cell11.setCellStyle(cellStyle);
			cell11.setCellValue("住宿费");
			
			Cell cell12 = row0.createCell(12);
			cell12.setCellStyle(cellStyle);
			cell12.setCellValue("劳务费");
			
			Cell cell13 = row0.createCell(13);
			cell13.setCellStyle(cellStyle);
			cell13.setCellValue("其他");
			
			Cell cell14 = row0.createCell(14);
			cell14.setCellStyle(cellStyle);
			cell14.setCellValue("小计");
			
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row1 = hssfSheet1.createRow(rowNum+1);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					HSSFCell cell = row1.createCell(colI);
					String str=row.get(colI);
					cell.setCellStyle(cellStyle);
					if(isNumeric(str)){
						cell.setCellValue(Double.parseDouble(str));
					}else {
						cell.setCellValue(str);
					}
				}

			}
			OutputStream os=new FileOutputStream(new File(path,filename));
			hssfWorkbook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException:"+e);
		} catch (IOException e) {
			logger.error("IOException:"+e);
		}
	}
	
	/**
	 * @param path
	 * @param filename
	 * @param rowList
	 * 打印费用明细结算EXCEL
	 */
	public static void createPrintCostXls(String path ,String filename, List<List<String>> rowList) {
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

			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("套餐");
			
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("套餐价格");
			
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("实际价格");
			
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("支公司");
			
			Cell cell9 = row0.createCell(9);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("所属公司");
			
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row1 = hssfSheet1.createRow(rowNum+1);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					HSSFCell cell = row1.createCell(colI);
					String str=row.get(colI);
					cell.setCellStyle(cellStyle);
					if(isNumeric(str)){
						cell.setCellValue(Double.parseDouble(str));
					}else {
						cell.setCellValue(str);
					}
				}

			}
			OutputStream os=new FileOutputStream(new File(path,filename));
			hssfWorkbook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException:"+e);
		} catch (IOException e) {
			logger.error("IOException:"+e);
		}
	}
	
	/**
	 * @param path
	 * @param filename
	 * @param rowList
	 * 会议详细费用导出
	 * @since  2017年2月22日16:16:02
	 * @author Carly 
	 */
	public static void createConferencCostDetailXls(String path ,String filename, List<List<String>> rowList) {
		try {
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("单项导出费用");

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
			cell1.setCellValue("场次号");
			
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("姓名");
			
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("类别");
			
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("天数");

			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("航班");
			
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("描述");
			
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("金额");
			
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row1 = hssfSheet1.createRow(rowNum+1);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					HSSFCell cell = row1.createCell(colI);
					String str=row.get(colI)==null?"":row.get(colI);
					cell.setCellStyle(cellStyle);
					if(isNumeric(str)){
						cell.setCellValue(Double.parseDouble(str));
					}else {
						cell.setCellValue(str);
					}
				}

			}
			OutputStream os=new FileOutputStream(new File(path,filename));
			hssfWorkbook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException:"+e);
		} catch (IOException e) {
			logger.error("IOException:"+e);
		}
	}
	
	//设置字体格式
	private static Font createFonts(Workbook wb, short bold, String fontName,boolean isItalic, short hight) {
		Font font = wb.createFont();
		font.setFontName(fontName);
		font.setBoldweight(bold);
		font.setItalic(isItalic);
		font.setFontHeight(hight);
		return font;
	}
	
	private static void createCell2(Workbook wb, Row row, int column,String value, CellStyle cellStyle) {
		Cell cell = row.createCell(column);
		HSSFRichTextString text = new HSSFRichTextString(value);
		cell.setCellValue(text);
		cell.setCellStyle(cellStyle);
	}
	//设置背景颜色
	private static void setStyle(HSSFWorkbook hssfWorkbook, Cell cell, String value, short fg) {
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
	
	private static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("^\\d+$|^\\d+\\.\\d+$|-\\d+$");
		Matcher isNum = pattern.matcher(str==null?"0":str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
