package cn.poi;

import java.text.Format;
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Excel {
//	public void ReportDetailListIoSubDirXls(String dirName,int start,int end,List<ReportDetail> list){
//		List<List<String>> rowList = new ArrayList<List<String>>();
//		for (int t=0;t<end;t++) {
//			List<String> colList = new ArrayList<String>();
//			ReportDetail rd =list.get(start+t);
//			colList.add(rd.getCode());
//			colList.add(rd.getName());
//			colList.add(rd.getSex());
//			colList.add(rd.getBranchCompany());
//			colList.add(rd.getSalesMan());
//			colList.add(rd.getEntering());
//			
//			rowList.add(colList);
//		}
//		Excel excel = new Excel();
//		excel.writeXls("d:\\testpdf\\"+dirName+".xls", rowList);
//	}
	public void writeXls(String path,List<List<String>> rowList)  {
		try {
			//InputStream is = new FileInputStream(path);
			//HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			//System.out.println(hssfWorkbook.getNumberOfSheets());
			// 工作表
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("test");
//			HSSFRow row1=hssfSheet1.createRow(0);
//			row1.createCell(0).setCellValue(true);
			
			//HSSFSheet hssfSheet2 = hssfWorkbook.getSheetAt(1);
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row0 = hssfSheet1.createRow(rowNum);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					String cell=row.get(colI);
					Font font0 = createFonts(hssfWorkbook, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200); 
					// 合并第一行的单元格  
					//hssfSheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));  
					//设置第一列的文字  
					createCell(hssfWorkbook, row0, colI, cell, font0);  
				}
				
			}
			OutputStream os=new FileOutputStream(new File(path));
			hssfWorkbook.write(os);
			//is.close();
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("结束");
	}

	public List<List<String>> readXls(String path){
		List<List<String>> result = new ArrayList<List<String>>();
		InputStream is =null;
		if(path.lastIndexOf(".xlsx")==-1){
			try {
				is = new FileInputStream(path);
				// 整个excel
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
				// 循环每一页，并处理当前循环页
				// for(int
				// numSheet=0;numSheet<hssfWorkbook.getNumberOfSheets();numSheet++){
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
				if (hssfSheet == null) {
					// continue;
				}
				// 处理当前页循环处理每一行
				for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if(hssfRow==null){
						continue;
					}
					int minColIx = hssfRow.getFirstCellNum();
					int maxColIx = hssfRow.getLastCellNum();
					List<String> rowList = new ArrayList<String>();
					
					if(hssfRow.getCell(1)==null||hssfRow.getCell(1).toString().length()==0){
						continue;
					}
					// 遍历该行，获取每个cell元素
					for (int colIx = minColIx; colIx < maxColIx; colIx++) {
						HSSFCell cell = hssfRow.getCell(colIx);
						rowList.add(ExcelUtils.getValue(cell));
					}
					result.add(rowList);
				}
				// }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					System.out.println("EXCEL关闭异常");
				}
			}
		}else{
			try {
				is = new FileInputStream(path);
				// 整个excel
				XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
				XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
				if (hssfSheet == null) {
					// continue;
				}
				// 处理当前页循环处理每一行
				for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if(hssfRow==null){
						continue;
					}
					int minColIx = hssfRow.getFirstCellNum();
					int maxColIx = hssfRow.getLastCellNum();
					List<String> rowList = new ArrayList<String>();
					if(hssfRow.getCell(1)==null||hssfRow.getCell(1).toString().length()==0){
						continue;
					}
					// 遍历该行，获取每个cell元素
					for (int colIx = minColIx; colIx < maxColIx; colIx++) {
						XSSFCell cell = hssfRow.getCell(colIx);
						rowList.add(ExcelUtils.getValue(cell));
					}
					result.add(rowList);
				}
				// }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					System.out.println("关闭异常");
				}
			}
		}
		
		return result;
	}

	/**
	 * 创建单元格并设置样式,值
	 * 
	 * @param wb
	 * @param row
	 * @param column
	 * @param
	 * @param
	 * @param value
	 */
	public static void createCell(Workbook wb, Row row, int column,
			String value, Font font) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 设置字体
	 * 
	 * @param wb
	 * @return
	 */
	public static Font createFonts(Workbook wb, short bold, String fontName,
			boolean isItalic, short hight) {
		Font font = wb.createFont();
		font.setFontName(fontName);
		font.setBoldweight(bold);
		font.setItalic(isItalic);
		font.setFontHeight(hight);
		return font;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str == null || "".equals(str.trim()) || str.length() > 10)
			return false;
		Pattern pattern = Pattern.compile("^0|[1-9]\\d*(\\.\\d+)?$");
		return pattern.matcher(str).matches();
	}

}
