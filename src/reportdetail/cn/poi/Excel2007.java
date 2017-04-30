package cn.poi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.yuanmeng.labelprint.dao.BatchDao;
import cn.yuanmeng.labelprint.dao.BatchFileColumnNameDao;
import cn.yuanmeng.labelprint.dao.LabelPrintDao;
import cn.yuanmeng.labelprint.dao.ReportDetailDao;
import cn.yuanmeng.labelprint.dao.impl.BatchDaoImpl;
import cn.yuanmeng.labelprint.dao.impl.BatchFileColumnNameDaoImpl;
import cn.yuanmeng.labelprint.dao.impl.LabelPrintDaoImpl;
import cn.yuanmeng.labelprint.dao.impl.ReportDetailDaoImpl;
import cn.yuanmeng.labelprint.entity.*;

public class Excel2007 {
	private LabelPrintDao lpDao = new LabelPrintDaoImpl();
	private ReportDetailDao rdDao = new ReportDetailDaoImpl();
	private BatchDao batchDao = new BatchDaoImpl();
	BatchFileColumnNameDao batchFileColumnNameDao = new BatchFileColumnNameDaoImpl();

	public static void main(String[] args) {
		String fileName = "d:\\nanfang.xlsx";
		// 检测代码
		try {
			Excel2007 er = new Excel2007();
			// 读取excel2007
			// er.xlsToTable2007(fileName);
			er.tableLabelPrintToXls(fileName);
		} catch (Exception ex) {
		}
	}

	/**
	 * 把批次文件导入数据表中
	 * 
	 * @param strPath
	 */
	public void xls2007ToTableBatch(String strPath) {
		try { // 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(strPath);
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row、cell
			XSSFRow row;
			String cell;
			int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
			System.out.println(colCount);
			// 循环输出表格中的内容
			for (int i = sheet.getFirstRowNum() + 1; i < sheet
					.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				Object obj[] = new Object[colCount];
				// row.getPhysicalNumberOfCells();
				int j = row.getFirstCellNum();
				for (; j < colCount; j++) {

					if (ExcelUtils.getStringVal(row.getCell(0)).equals("")) {
						break;
					}
					cell = ExcelUtils.getStringVal(row.getCell(j));
					obj[j] = cell;
					System.out.print(cell + "\t");
				}
				if (j == colCount) {
					System.out.println();
					batchDao.insertByObj(obj);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * 批次表的所有列名保存到数据库
	 * 
	 * @param strPath
	 */
	public void xls2007BatchColumnName(String strPath, String fileName) {
		try { // 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(strPath);
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row、cell
			XSSFRow row;
			String cell;
			int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
			// 循环输出表格中的内容
			for (int i = sheet.getFirstRowNum(); i < 1; i++) {
				row = sheet.getRow(i);
				Object obj[] = new Object[22];
				System.out.println(obj.length);
				for (int j = 0; j < obj.length; j++) {
					obj[j] = "=";
				}
				obj[0] = fileName;
				obj[1] = colCount;
				System.out.print(obj[0] + "\t\t" + obj[1]);
				// row.getPhysicalNumberOfCells();
				for (int j = row.getFirstCellNum(); j < colCount; j++) {
					cell = ExcelUtils.getStringVal(row.getCell(j));
					obj[j + 2] = cell;
					System.out.print(cell + "\t");
				}
				batchFileColumnNameDao.add(obj);
				System.out.println();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void tableLabelPrintToXls(String strPath) throws Exception {

		List<LabelPrint> list = lpDao.getAll();
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			List<String> colList = new ArrayList<String>();
			LabelPrint lp = (LabelPrint) iterator.next();
			colList.add(lp.getId());
			colList.add(lp.getCode());
			colList.add(lp.getName());
			colList.add(lp.getSex());
			colList.add(lp.getAge());
			colList.add(lp.getPhone());
			colList.add(lp.getIdNumber());
			colList.add(lp.getEnterprise());
			colList.add(lp.getSaleMan());
			colList.add(lp.getBatch());
			colList.add(lp.getDueDate());
			colList.add(lp.getFamilyHistory());
			colList.add(lp.getNote());
			colList.add(lp.getDwellTime());
			colList.add(lp.getBaseDetection());
			rowList.add(colList);
		}
		Excel excel = new Excel();
		excel.writeXls("d:\\1-0143-82.xls", rowList);
	}

	public void xls2007ToTableLabelPrint(String strPath) throws Exception {

		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(strPath);
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		// 定义 row、cell
		XSSFRow row;
		String cell;
		// 循环输出表格中的内容
		for (int i = sheet.getFirstRowNum() + 1; i < sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			Object obj[] = new Object[row.getPhysicalNumberOfCells()];
			for (int j = row.getFirstCellNum(); j < row
					.getPhysicalNumberOfCells(); j++) {
				// 通过 row.getCell(j).toString() 获取单元格内容，
				cell = row.getCell(j).toString();
				obj[j] = cell;

				System.out.print(cell + "\t");
			}
			lpDao.sheetToTable(obj);
			System.out.println("");
		}
	}

	public void xls2007ToTableReportDetail(String strPath) {

		try {
			// 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(strPath);
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row、cell
			XSSFRow row;
			String cell;
			// 循环输出表格中的内容
			for (int i = sheet.getFirstRowNum() + 1; i < sheet
					.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				Object obj[] = new Object[row.getPhysicalNumberOfCells()];
				for (int j = row.getFirstCellNum(); j < row
						.getPhysicalNumberOfCells(); j++) {
					// 通过 row.getCell(j).toString() 获取单元格内容，
					cell = row.getCell(j).toString();
					obj[j] = cell;

					System.out.print(cell + "\t");
				}
				rdDao.sheetToTable(obj);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
