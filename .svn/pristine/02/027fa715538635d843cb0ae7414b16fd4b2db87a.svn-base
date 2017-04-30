package cn.poi;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.hpin.common.util.DateUtils;

import com.itextpdf.text.pdf.StringUtils;

public class ExcelUtils {
	//新版本
	public static String getValue(Cell cell) {
		String value = "";
		if(cell==null){
			return value;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN://boolean类型
			value = String.valueOf(cell.getBooleanCellValue()).trim();
			 break;
		case Cell.CELL_TYPE_NUMERIC: //数字或日期类型
			
			 if (HSSFDateUtil.isCellDateFormatted(cell)) {//判断是否是日期类型
				 value = DateUtils.DateToStr(cell.getDateCellValue(), DateUtils.DATE_FORMAT); // 把Date转换成本地格式的字符串
			}else{
				short format = cell.getCellStyle().getDataFormat();
		        SimpleDateFormat sdf = null;
		        if(format == 14 || format == 31 || format == 57 || format == 58){
		            //日期
		            sdf = new SimpleDateFormat("yyyy-MM-dd");
		            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue());
			        value = sdf.format(date);
		        }else if (format == 20 || format == 32) {
		            //时间
		            sdf = new SimpleDateFormat("HH:mm");
		            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue());
			        value = sdf.format(date);
		        }else{
		        	DecimalFormat df = new DecimalFormat("0");
		        	value = String.valueOf(df.format(cell.getNumericCellValue())).trim();
		        }
			}
//			 System.out.println(HSSFDateUtil.isCellDateFormatted(cell)+"   "+value);
			break;
		case Cell.CELL_TYPE_STRING://字符类型
			value = String.valueOf(cell.getStringCellValue()).trim();
			break;
		case Cell.CELL_TYPE_BLANK://空白单元格
			break;
		case Cell.CELL_TYPE_FORMULA:
			value=cell.getCellFormula();
			break;
		default:
			value = String.valueOf(cell.getStringCellValue()).trim();
			break;
		}
		return value;
	}
	
	//老版本
	public static String getStringVal(HSSFCell cell) {
		// TODO Auto-generated method stub
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
			// return cell.toString();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return "";

		}
	}
//
	public static String getStringVal(XSSFCell cell) {
		// TODO Auto-generated method stub
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return "";
		}
	}

	/**
	 * 获取单元格内文本信息
	 * 
	 * @param cell
	 * @return
	 * @date 2013-5-8
	 */
	public static final String getValueFromCell(Cell cell) {
		if (cell == null) {
			return "";
		}
		String value = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			if (HSSFDateUtil.isCellDateFormatted(cell)) { // 如果是日期类型
				value = new SimpleDateFormat("yyyy-MM-dd").format(cell
						.getDateCellValue());
			} else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				// value = String.valueOf(cell.getNumericCellValue());
				value = cell.getStringCellValue();
			}
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			// 用数字方式获取公式结果，根据值判断是否为日期类型
			double numericValue = cell.getNumericCellValue();
			if (HSSFDateUtil.isValidExcelDate(numericValue)) { // 如果是日期类型
				// value = new
				// SimpleDateFormat(DatePattern.LOCALE_ZH_DATE.getValue()).format(cell.getDateCellValue())
				// ;
				value = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
			} else
				value = String.valueOf(numericValue);
			break;
		case Cell.CELL_TYPE_BLANK: // 空白
			value = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR: // Error，返回错误码
			value = String.valueOf(cell.getErrorCellValue());
			break;
		default:
			value = "";
			break;
		}
		// 使用[]记录坐标
		// return value + "["+cell.getRowIndex()+","+cell.getColumnIndex()+"]" ;
		if(value.startsWith("'")){
			value=value.substring(1);
		}
		if(value.endsWith("'")){
			value=value.substring(0,value.length()-1);
		}
		if(value.length()>100){
			value=value.substring(0,100);
		}
		return value;
	}

}
