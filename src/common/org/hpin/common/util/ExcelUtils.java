package org.hpin.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Chenl
 * @version 1.0 类说明 : Excel数据操作 创建时间：2011-3-24 下午04:03:59 修改时间：2011-3-24
 *          下午04:03:59
 */
public class ExcelUtils {

	private static Logger log = Logger.getLogger(ExcelUtils.class.getName());

	/**
	 * 导入excel数据
	 * 
	 * @param path
	 *            eg:c:\\excel.xls
	 * @return
	 */
	@SuppressWarnings("finally")
	public static List<Object> importExcel2Object(String path, Class entityClass) {
		InputStream is = null;
		Workbook wb = null;
		// id--值集合
		List<Object> valueList = new ArrayList<Object>();
		try {
			is = new FileInputStream(path);
			wb = Workbook.getWorkbook(is);
			Sheet st = wb.getSheet(0);// 得到工作薄中的第一个工作表
			// 从第二行开始读（第一行写的是字段类型和字段ID）
			int aa = st.getColumns();
			int bb = st.getRows();
			for (int i = 1; i < bb; i++) {
				Object entity = entityClass.newInstance();
				boolean isSave = true;
				for (int j = 0; j < aa; j++) {
					Cell fieldIdCell = st.getCell(j, 0);
					String type_method = fieldIdCell.getContents();// 得到字段id
					if (StringUtils.isNotBlank(type_method)) {
						Cell cell = st.getCell(j, i);// 得到工作表的单元格,即A1
						Object value = cell.getContents();// getContents()将Cell中的字符转为字符串
						// HR专用 只取北京的
						if ("String_setSetid".equals(type_method)) {
							if (!("HL001".equals(value.toString()))) {
								isSave = false;
								break;
							}
						}
						if (value == null
								|| StringUtils.isBlank(value.toString())) {
							continue;
						}
						String type = type_method.split("_")[0];
						String method = type_method.split("_")[1];
						value = ReflectionUtils.convertString2Object(value,
								XmlUtils.parseType(type));
						if ("String".equals(type)) {
							value = StringUtils.trim(value.toString());
						}
						// 反射调用方法
						entityClass.getMethod(method,
								new Class[] { XmlUtils.parseType(type) })
								.invoke(entity, new Object[] { value });
					}
				}
				// HR专用 空值不加
				if (isSave) {
					valueList.add(entity);// 把第一条记录加入到list中去
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();// 关闭工作薄
				wb = null;
			}
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return valueList;
		}
	}

	/**
	 * 导入excel数据
	 * 
	 * @param path
	 *            eg:c:\\excel.xls
	 * @return
	 */
	@SuppressWarnings("finally")
	public static List<Map<String, String>> importExcel(String path) {
		InputStream is = null;
		Workbook wb = null;
		// id--值集合
		List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
		try {
			is = new FileInputStream(path);
			wb = Workbook.getWorkbook(is);
			Sheet st = wb.getSheet(0);// 得到工作薄中的第一个工作表
			// 从第三行开始读（第一行写的是字段描述,第二行是字段ID）
			int aa = st.getColumns();
			int bb = st.getRows();
			for (int i = 1; i < bb; i++) {
				Map<String, String> valueMap = new HashMap<String, String>();
				for (int j = 0; j < aa; j++) {
					Cell fieldIdCell = st.getCell(j, 1);
					String fieldId = fieldIdCell.getContents();// 得到字段id
					// 判断是否字段id不为空
					if (StringUtils.isNotBlank(fieldId)) {
						Cell cell = st.getCell(j, i);// 得到工作表的单元格,即A1
						String value = cell.getContents();// getContents()将Cell中的字符转为字符串
						valueMap.put(fieldId, value);// key(字段ID)--value对
					}
				}
				valueList.add(valueMap);// 把第一条记录加入到list中去
			}
			// wb.close();//关闭工作薄
			// is.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();// 关闭工作薄
				wb = null;
			}
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return valueList;
		}
	}

	/**
	 * 填充excel文件
	 * 
	 * @param exportFilePath
	 *            文件路径 d:/Test.xls
	 * @param valueList
	 *            值集合
	 * @param filedNameMap
	 *            标题集合
	 * @throws Exception
	 */
	public static File fillExcel(String tableName,
			List<Map<String, Object>> valueList,
			Map<String, String> filedNameMap, String exportFilePath)
			throws Exception {
		File file = new File(exportFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String filePath = exportFilePath + "/" + tableName + ".xls";
		WritableWorkbook book = null;
		File exportFile = new File(filePath);
		try {
			book = Workbook.createWorkbook(exportFile); // 创建excel文件
			WritableSheet sheet = book.createSheet("Sheet_1", 0); // 创建一个sheet
			// 开始给sheet中添加数据了(第一行是标题名称,第二行是标题字段[隐藏])

			// 获取显示字段的id并以id为key
			Object[] keyArray = filedNameMap.keySet().toArray();

			// 第一行是标题名称
			for (int j = 0; j < keyArray.length; j++) { // 列数
				Label labelTitle = null;
				labelTitle = new Label(j, 0, filedNameMap.get(keyArray[j]));// 第一行放置标题名称
				sheet.addCell(labelTitle);
			}
			for (int j = 0; j < keyArray.length; j++) { // 列数
				Label labelFieldId = null;
				labelFieldId = new Label(j, 1, keyArray[j].toString());// 第二行放置标题字段
				sheet.addCell(labelFieldId);
			}
			// 记录数据的填充
			if (valueList != null && valueList.size() > 0) {
				for (int r = 2; r < valueList.size() + 2; r++) { // 行数
					Map<String, Object> lineMap = valueList.get(r - 2);
					for (int j = 0; j < keyArray.length; j++) { // 列数
						Label cellValueLabel = null;
						String cellValue = new String(lineMap
								.get((String) keyArray[j]).toString()
								.getBytes());
						cellValueLabel = new Label(j, r, cellValue);
						sheet.addCell(cellValueLabel);
					}
				}
			}
			book.write();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("创建excel文件发生异常：" + e);
		} finally {
			// 关闭工作薄
			if (book != null) {
				try {
					book.close();
					book = null;
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					e.printStackTrace();
				}

			}
		}
		return exportFile;
	}

	@SuppressWarnings("finally")
	public static List<Map<String, String>> importSettlementExcel(File file) {
		InputStream is = null;
		// Workbook wb = null;
		XSSFWorkbook wb = null;
		// id--值集合
		List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
		try {
			is = new FileInputStream(file);
			// wb = Workbook.getWorkbook(is);
			wb = new XSSFWorkbook(is);

			XSSFSheet st = wb.getSheetAt(0);// 得到工作薄中的第一个工作表
			// 第一行是条码,第二行是姓名,第三行性别,第四行年龄,第五行联系方式,第六行支公司,
			// 第七行检测项目,第八行样本类型,第九行送检医生,第十行送检单位,
			// 第十一行提交者,第十二行送检日期,第十三行收样日期,第十四行状态,第十五行报告
			int bb = st.getLastRowNum();
			for (int i = 1; i <= bb; i++) {
				Map<String, String> valueMap = new HashMap<String, String>();
				XSSFRow row = st.getRow(i);
				int aa = row.getLastCellNum();
				for (int j = 0; j < aa; j++) {
					String cellValue = getValue(row.getCell(j));// 得到字段id
					valueMap.put(String.valueOf(j), cellValue);
				}
				valueList.add(valueMap);// 把第一条记录加入到list中去
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
//					wb.close();// 关闭工作薄
					wb = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return valueList;
		}
	}

	public static String getValue(org.apache.poi.ss.usermodel.Cell cell) {
		String value = "";
		if (cell == null) {
			return value;
		}
		if(cell.toString().equals("#N/A")){
			return value;
		}
		switch (cell.getCellType()) {
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:// boolean类型
			value = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC: // 数字或日期类型

			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否是日期类型
				value = DateUtils.DateToStr(cell.getDateCellValue(),
						DateUtils.DATE_FORMAT); // 把Date转换成本地格式的字符串
			} else {
				short format = cell.getCellStyle().getDataFormat();
				SimpleDateFormat sdf = null;
				if (format == 14 || format == 31 || format == 57
						|| format == 58) {
					// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(cell.getNumericCellValue());
					value = sdf.format(date);
				} else if (format == 20 || format == 32) {
					// 时间
					sdf = new SimpleDateFormat("HH:mm");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(cell.getNumericCellValue());
					value = sdf.format(date);
				} else {
					DecimalFormat df = new DecimalFormat("0");
					value = String.valueOf(
							df.format(cell.getNumericCellValue())).trim();
				}
			}
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:// 字符类型
			value = String.valueOf(cell.getStringCellValue()).trim();
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:// 空白单元格
			break;
		default:
			value = String.valueOf(cell.getStringCellValue()).trim();
			break;
		}
		return value;
	}

	public static void main(String[] args) {
		File file = new File("D:\\pdf\\基因核对 2016-03-25北京远盟的数据 - 副本.xlsx");
		List<Map<String, String>> list = importSettlementExcel(file);
		System.out.println(list.size());

	}
	
	@SuppressWarnings("finally")
	public static List<Map<String, String>> xlsxExcel(File file) throws Exception{
		InputStream is = null;
		// Workbook wb = null;
		XSSFWorkbook wb = null;
		// id--值集合
		List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
		try {
			is = new FileInputStream(file);
			// wb = Workbook.getWorkbook(is);
			wb = new XSSFWorkbook(is);

			XSSFSheet st = wb.getSheetAt(0);// 得到工作薄中的第一个工作表
			// 第一行是条码,第二行是姓名,第三行性别,第四行年龄,第五行联系方式,第六行支公司,
			// 第七行检测项目,第八行样本类型,第九行送检医生,第十行送检单位,
			// 第十一行提交者,第十二行送检日期,第十三行收样日期,第十四行状态,第十五行报告
			int bb = st.getLastRowNum();
			for (int i = 1; i <= bb; i++) {
				Map<String, String> valueMap = new HashMap<String, String>();
				XSSFRow row = st.getRow(i);
				int aa = row.getLastCellNum();
				for (int j = 0; j < aa; j++) {
					String cellValue = getValue(row.getCell(j));// 得到字段id
					valueMap.put(String.valueOf(j), cellValue);
				}
				valueList.add(valueMap);// 把第一条记录加入到list中去
			}
		} catch (IOException e) {
			//log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
//					wb.close();// 关闭工作薄
					wb = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return valueList;
		}
	}
	
	@SuppressWarnings("finally")
	public static List<Map<String, String>> xlsExcel(File file) throws Exception{
		Workbook wb = null;
		InputStream is=null;
		Sheet st=null;
		// id--值集合
		List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
		try {
			is = new FileInputStream(file);
			// wb = Workbook.getWorkbook(is);
			wb = Workbook.getWorkbook(is);

			st = wb.getSheet(0);// 得到工作薄中的第一个工作表
			// 第一行是条码,第二行是姓名,第三行性别,第四行年龄,第五行联系方式,第六行支公司,
			// 第七行检测项目,第八行样本类型,第九行送检医生,第十行送检单位,
			// 第十一行提交者,第十二行送检日期,第十三行收样日期,第十四行状态,第十五行报告
			int bb = st.getRows();//总行数
			int rsCols=st.getColumns();//获取Sheet表中所包含的总列数
			for (int i = 1; i <= bb; i++) {
				Map<String, String> valueMap = new HashMap<String, String>();
				
				for (int j = 0; j < rsCols; j++) {
					Cell coo = st.getCell(j,i);// 得到字段id
					String cellValue=coo.getContents();
					valueMap.put(String.valueOf(j), cellValue);
				}
				valueList.add(valueMap);// 把第一条记录加入到list中去
			}
		} catch (IOException e) {
			//log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();// 关闭工作薄
					wb = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return valueList;
		}
	}
	
	/**
	 * @param file
	 * @return 在读取数字时自动在其后面添加两位小数，比如1→1.00 ；如果已有一位小数，则在最后一位添加0，比如1.2→1.20 
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public static List<Map<String, String>> readXlsxExcel(File file) throws Exception{
		InputStream is = null;
		// Workbook wb = null;
		XSSFWorkbook wb = null;
		// id--值集合
		List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
		try {
			is = new FileInputStream(file);
			// wb = Workbook.getWorkbook(is);
			wb = new XSSFWorkbook(is);

			XSSFSheet st = wb.getSheetAt(0);
			int bb = st.getLastRowNum();
			for (int i = 1; i <= bb; i++) {
				Map<String, String> valueMap = new HashMap<String, String>();
				XSSFRow row = st.getRow(i);
				int aa = row.getLastCellNum();
				for (int j = 0; j < aa; j++) {
					String cellValue = getRowValue(row.getCell(j));// 得到字段id
					valueMap.put(String.valueOf(j), cellValue);
				}
				valueList.add(valueMap);// 把第一条记录加入到list中去
			}
		} catch (IOException e) {
			//log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
//					wb.close();// 关闭工作薄
					wb = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return valueList;
		}
	}
	
	/**
	 * @since 2016年10月12日17:33:14
	 * @author Carly
	 * @param cell
	 * @return 在读取数字时自动在其后面添加两位小数，比如1→1.00 ；如果已有一位小数，则在最后一位添加0，比如1.2→1.20 
	 */
	public static String getRowValue(org.apache.poi.ss.usermodel.Cell cell) {
		String value = "";
		if (cell == null) {
			return value;
		}
		if(cell.toString().equals("#N/A")){
			return value;
		}
		switch (cell.getCellType()) {
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:// boolean类型
			value = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC: // 数字或日期类型
			
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否是日期类型
				value = DateUtils.DateToStr(cell.getDateCellValue(),
						DateUtils.DATE_FORMAT); // 把Date转换成本地格式的字符串
			} else {
				short format = cell.getCellStyle().getDataFormat();
				SimpleDateFormat sdf = null;
				if (format == 14 || format == 31 || format == 57
						|| format == 58) {
					// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(cell.getNumericCellValue());
					value = sdf.format(date);
				} else if (format == 20 || format == 32) {
					// 时间
					sdf = new SimpleDateFormat("HH:mm");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(cell.getNumericCellValue());
					value = sdf.format(date);
				} else {
					DecimalFormat df = new DecimalFormat("0.00#");
					value = String.valueOf(
							df.format(cell.getNumericCellValue())).trim();
				}
			}
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:// 字符类型
			value = String.valueOf(cell.getStringCellValue()).trim();
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:// 空白单元格
			break;
		default:
			value = String.valueOf(cell.getStringCellValue()).trim();
			break;
		}
		return value;
	}


}
