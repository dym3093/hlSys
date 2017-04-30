package cn.yuanmeng.labelprint.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.ftp.FtpOperation;
import cn.poi.Excel;
import cn.poi.Excel2007;
import cn.poi.FileList;
import cn.yuanmeng.labelprint.entity.ReportDetail;
import cn.yuanmeng.labelprint.pdf.ReportDetailPdf;
import cn.yuanmeng.labelprint.service.ReportDetailBatchService;
import cn.yuanmeng.labelprint.service.ReportDetailService;
import cn.yuanmeng.labelprint.dao.*;
import cn.yuanmeng.labelprint.dao.impl.*;
import cn.yuanmeng.labelprint.entity.*;

public class ReportDetailServiceImpl implements ReportDetailService {
	private ReportDetailDaoImpl dao = new ReportDetailDaoImpl();
	private FileList fl;
	private Excel2007 excel2007 = new Excel2007();
	Excel excel = new Excel();
	private static final int NUM = 30;
	private String districtNamePath;

	/**
	 * 保存到数据库表中
	 */
	@Override
	public void xlsToTable(String fileDirPath) {
		dao.del();
		// 批次号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String batchNo = sdf.format(new Date());
		// excel中条形码数
		int xlsCodeCount = 0;
		// pdf报告数
		int pdfFileCount = 0;
		// 本次正确人数
		int xlspdfCount = 0;
		// 条形码没有报告文件数
		int codeNoReportFileCount = 0;
		// 报告文件没有条形码
		int reportFileNoCode = 0;

		fl = new FileList(districtNamePath);
		List<String> xlsFilePathNameList = fl.getXlsFileListByDir();
		List<String> pdfFilePathNameList = fl.getPDFFileListByDir();
		pdfFileCount = pdfFilePathNameList.size();

		List<String> pdfFileNameList = new ArrayList<String>();

		for (String pdfFilePathName : pdfFilePathNameList) {
			String pdfFileName = pdfFilePathName.substring(
					pdfFilePathName.lastIndexOf("\\") + 1,
					pdfFilePathName.lastIndexOf(".")).trim();
			pdfFileNameList.add(pdfFileName);
			System.out.println(pdfFileName);
		}
		int colcount = 0;
		for (int i = 0; i < xlsFilePathNameList.size(); i++) {
			String xlsFilePathName = xlsFilePathNameList.get(i);
			String xlsFileName = xlsFilePathName.substring(
					xlsFilePathName.lastIndexOf("\\") + 1,
					xlsFilePathName.lastIndexOf(".")).trim();
			BaseDao bd = new BaseDao();
			Connection con = bd.getConnection();
			try {
				con.setAutoCommit(false);
				String sql = "insert into ERP_REPORTDETAIL(id,xlsno,code,name,sex,age,phone,branchCompany,project,sampleType,salesMan,entering,institution,samplingDate,collectionDate,simpleStatus,page,batchno,filepath,ismatch) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				List<List<String>> list = excel.readXls(xlsFilePathName);

				for (List<String> row : list) {
					colcount = row.size() + 4;
					Object obj[] = new Object[colcount];
					int j = 0;
					obj[j++] = UUID.randomUUID().toString().replaceAll("-", "");
					for (String cell : row) {
						obj[j++] = cell;
					}
					obj[j++] = batchNo;// 批次号
					obj[j++] = fileDirPath + xlsFileName + ".xls";
					String code = row.get(1).trim();
					// //判断条形码是否和PDF报告一致
					if (pdfFileNameList.contains(code)) {
						obj[j++] = "1";// 是否匹配
						xlspdfCount++;
						pdfFileNameList.remove(code);
					} else {
						codeNoReportFileCount++;// 条形码没有报告文件数
						obj[j++] = "0";// 是否匹配
					}
					if (obj != null) {
						for (int oi = 0; oi < obj.length; oi++) {
							pstmt.setObject(oi + 1, obj[oi]);
						}
					}
					pstmt.addBatch();
					// 保存dao.sheetToTable(obj);
					xlsCodeCount++;// excel中条形码数
				}
				pstmt.executeBatch();
				con.commit();

				reportFileNoCode = pdfFileNameList.size();// 报告文件没有条形码
				ReportDetailPDFDaoImpl reportDetailPDFDaoImpl = new ReportDetailPDFDaoImpl();
				for (String pdfFilePathName : pdfFilePathNameList) {
					String pdfFileName = pdfFilePathName.substring(
							pdfFilePathName.lastIndexOf("\\") + 1,
							pdfFilePathName.lastIndexOf(".")).trim();
					Object obj[] = new Object[6];
					obj[0] = UUID.randomUUID().toString().replaceAll("-", "");
					obj[1] = batchNo;
					obj[2] = fileDirPath + pdfFileName + ".pdf";
					// obj[2]=pdfFilePathName.substring(pdfFilePathName.indexOf(districtNamePath)).replaceAll("\\\\",
					// "/");
					System.out.println(pdfFilePathName + "  "
							+ districtNamePath);
					System.out.println(pdfFilePathName
							.indexOf(districtNamePath));
					if (pdfFileNameList.contains(pdfFileName)) {
						obj[3] = "0";
						obj[5] = "";
					} else {
						obj[3] = "1";
						obj[5] = pdfFileName;
					}
					obj[4] = pdfFileName + ".pdf";

					reportDetailPDFDaoImpl.pdfToTable(obj);
				}

				int ismatch = 1;
				if (reportFileNoCode > 0 || codeNoReportFileCount > 0) {
					ismatch = 0;
				}
				System.out.println("批次号" + batchNo);
				System.out.println("excel中条形码数" + xlsCodeCount);
				System.out.println("pdf报告数" + pdfFileCount);
				System.out.println("本次正确人数" + xlspdfCount);
				System.out.println("条形码没有报告文件数" + codeNoReportFileCount);
				System.out.println("报告文件没有条形码" + reportFileNoCode);

				ReportDetailBatchService reportDetailBatchService = new ReportDetailBatchServiceImpl();
				ReportDetailBatch reportDetailBatch = new ReportDetailBatch();
				reportDetailBatch.setBatchDate(new SimpleDateFormat(
						"yyyy-MM-dd").format(new Date()));
				reportDetailBatch.setBatchNo(batchNo);
				reportDetailBatch.setXlsCodeCount(xlsCodeCount);
				reportDetailBatch.setPdfFileCount(pdfFileCount);
				reportDetailBatch.setXlsPdfCount(xlspdfCount);
				reportDetailBatch
						.setCodenoReportFileCount(codeNoReportFileCount);
				reportDetailBatch.setReportFileNocodeCount(reportFileNoCode);
				reportDetailBatch.setIsmatch(ismatch);
				reportDetailBatchService.add(reportDetailBatch);

				System.out.println(UUID.randomUUID().toString()
						.replaceAll("-", ""));

			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				bd.closeAll();
			}
		}
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		// xlsToTable();

		// List<ReportDetail> list = dao.getAll();
		// int total = list.size();
		// int dirCount = total / NUM + 1;
		// String dirName[] = new String[dirCount];
		// Date date = new Date();
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// String ymd = sdf.format(date);
		// int i = 0;
		// for (; i < dirName.length - 1; i++) {
		// char c = (char) (i + 65);
		// dirName[i] = ymd + "_" + c + "_" + NUM;
		// }
		// char c = (char) (i + 65);
		// dirName[i] = ymd + "_" + c + "_" + total % NUM;
		//
		// String district = "district_test";
		//
		// String tempDistrictName[] = districtNamePath.split("//");
		// int count = tempDistrictName.length;
		// if (count > 1) {
		// district = tempDistrictName[count - 1];
		// }
		// try {
		// district=new String(district.getBytes("GBK"),"iso-8859-1");
		// } catch (UnsupportedEncodingException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// FtpOperation.makeDirectory(district);
		//
		// for (int j = 0; j < dirName.length; j++) {
		// String curDir = dirName[j];
		// // 目录
		// FtpOperation.makeDirectory(district, curDir);
		// }
		//
		// for (int j = 0; j < dirName.length; j++) {
		// String curDir = dirName[j];
		// // pdf
		// int start = j * NUM;
		// int end = NUM;
		// if (j == dirName.length - 1) {
		// end = total % NUM;
		// }
		// // end=j==dirName.length-1?total%NUM:NUM;
		// excel.ReportDetailListIoSubDirXls(curDir, start, end, list);
		//
		// String xlsPath = district + "//" + curDir;
		// String xlsFileName = curDir + ".xls";
		// InputStream xlsInput = null;
		// try {
		// xlsInput = new FileInputStream(new File("d://testpdf//"
		// + curDir + ".xls"));
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// FtpOperation.uploadFile(xlsPath, xlsFileName, xlsInput);
		//
		// for (int t = 0; t < end; t++) {
		//
		// ReportDetail reportDetail = list.get(start + t);
		//
		// ReportDetailPdf reportDetailPdf = new ReportDetailPdf();
		// reportDetailPdf.writeCharpter(reportDetail);//pdf 标签文件
		//
		// String labelPath = district + "//" + curDir + "//label";
		// String labelFileName = reportDetail.getCode() + ".pdf";
		// InputStream labelInput = null;
		// try {
		// labelInput = new FileInputStream(new File("D://testpdf//"
		// + reportDetail.getCode() + ".pdf"));
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// FtpOperation.uploadFile(labelPath, labelFileName, labelInput);
		//
		// String reportPath = district + "//" + curDir + "//report";
		// String reportFileName = reportDetail.getCode() + ".pdf";
		// InputStream reportInput = null;
		// try {
		// String pdfFilename = "";
		// boolean b = false;
		// fl = new FileList(districtNamePath);
		// List<String> pdfFileNameList = fl.getPDFFileListByDir();
		// for (int n = 0; n < pdfFileNameList.size(); n++) {
		// pdfFilename = pdfFileNameList.get(n);
		// if (pdfFilename.endsWith(labelFileName)) {
		// b = true;
		// break;
		// }
		//
		// }
		// if (b) {
		// // reportInput = new FileInputStream(new
		// // File("D://A92//"+ reportDetail.getCode() + ".pdf"));
		// reportInput = new FileInputStream(new File(pdfFilename));
		// } else {
		// System.err.println(labelFileName);
		// }
		//
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// FtpOperation
		// .uploadFile(reportPath, reportFileName, reportInput);
		//
		// }
		// }

	}

	@Override
	public void tableToXls() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tableToPdf() {
		// TODO Auto-generated method stub

	}

	@Override
	public void copyReport() {
		// TODO Auto-generated method stub

	}

	public ReportDetailServiceImpl(String districtNamePath) {
		this.districtNamePath = districtNamePath;
	}
}
