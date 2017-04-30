package cn.yuanmeng.labelprint.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import cn.poi.FileList;
import cn.yuanmeng.labelprint.dao.BaseDao;

public class Del {

	public static void main(String[] args) {
		// BufferedWriter outSqlException = new BufferedWriter(new
		// OutputStreamWriter(new FileOutputStream("d:"+ "\\tesst.txt",true)));
		// outSqlException.write("sql语句异常\r\n");
		// outSqlException.close();

		// 所有PDF文件和22万匹配
		String geneftp = TestExcelProcess.GENEFTP + "upload";
		FileList fl = new FileList(geneftp);
		List<String> pdffilenameList = new ArrayList<String>();
		// 保存到数据库
		BaseDao bd = new BaseDao();
		Connection con = bd.getConnection();
		String sql = "insert into ERP_PDF_TEMP values(?,?,?)";
		try {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(sql);
			int count = 0;
			List<String> pdfdirfilenameList = fl.getPDFFileListByDir();
			for (String pdfdirfilename : pdfdirfilenameList) {
				String code = pdfdirfilename.substring(pdfdirfilename.lastIndexOf("\\") + 1,pdfdirfilename.lastIndexOf("."));
				Object para[] = {UUID.randomUUID().toString().replaceAll("-", ""), code,pdfdirfilename};
				for (int i = 0; i < para.length; i++) {
					pstmt.setObject(i + 1, para[i]);
				}
				pstmt.addBatch();
				count++;
				System.out.println(count);
				if (count % 100 == 0) {
					pstmt.executeBatch();
					con.commit();
				}
			}
			pstmt.executeBatch();
			con.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bd.closeAll();
		}
		/*
		 * List<String> pdfdirfilenameList=fl.getPDFFileListByDir(); for (String
		 * pdfdirfilename : pdfdirfilenameList) { String
		 * code=pdfdirfilename.substring(pdfdirfilename.lastIndexOf("\\")+1,
		 * pdfdirfilename.lastIndexOf(".")); pdffilenameList.add(code); }
		 * test2(pdffilenameList);
		 */

		/*
		 * //18万数据和22万匹配 BaseDao bd = new BaseDao(); Connection con =
		 * bd.getConnection(); String sql = "select * from hl_temp";//a为条形码
		 * List<HashMap> reportBatcLlist = bd.querySql(sql); for (HashMap map :
		 * reportBatcLlist) { String a = (String) map.get("a");
		 * pdffilenameList.add(a); } test2(pdffilenameList);
		 */
	}

	/**
	 * Excel中条形码和PDF文件匹配
	 * 
	 * @throws Exception
	 */
	static void test2(List<String> pdffilenameList) throws Exception {
		BaseDao bd = new BaseDao();
		Connection con = bd.getConnection();
		// String sql = "select * from hl_temp";//a为条形码
		String sql = "select * from erp_customer";// code为条形码
		List<HashMap> reportBatcLlist = bd.querySql(sql);
		int count = 0;
		for (HashMap map : reportBatcLlist) {
			// String a = (String) map.get("a");
			String a = (String) map.get("code");
			if (pdffilenameList.contains(a)) {
				count++;
				System.out.println(count);
				pdffilenameList.remove(a);
			}
		}
		System.out.println(count);
	}

	void test1() throws Exception {
		String dirPath = "20160101";
		BaseDao bd = new BaseDao();
		Connection con = bd.getConnection();
		String sql = "select * from epr_report_batch";
		List<HashMap> reportBatcLlist = bd.querySql(sql);
		for (HashMap map : reportBatcLlist) {
			String xlsoldpath = (String) map.get("xlsoldpath");
			String dirName = xlsoldpath.substring(14);
			dirName = dirName.substring(0, dirName.indexOf("\\"));
			System.out.println(dirName);
			if (dirPath.equals(dirName)) {
				System.out.print("<script>alert('" + dirPath
						+ "已经存在');</script>");
			}
		}
	}

	 static String isMatch(String code) {
	 String sql = "select * from erp_customer where code='" + code + "'";
	 BaseDao bd = new BaseDao();
	 Connection con = bd.getConnection();
	 try {
	 PreparedStatement pstmt = con.prepareStatement(sql);
	 ResultSet rs = pstmt.executeQuery();
	 if (rs.next()) {
	 return "1";
	 }
	 } catch (SQLException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 } finally {
	 bd.closeAll();
	 }
	 return "0";
	 }
}
