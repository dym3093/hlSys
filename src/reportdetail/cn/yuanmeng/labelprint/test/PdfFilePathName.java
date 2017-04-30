package cn.yuanmeng.labelprint.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.poi.FileList;
import cn.yuanmeng.labelprint.dao.BaseDao;

/**
 * 20160224晚上执行
 * 
 * @author l
 *
 */
public class PdfFilePathName {

	public static void main(String[] args) throws Exception {
//		test5();
//		test1();
		test2();
	}
	// 客户信息和20160229南方最新数据进行匹配，解决系统中南方大写,系统中小写SY-的问题
		public static void test5() throws Exception{
					BaseDao bd = new BaseDao();
					Connection con = bd.getConnection();
					String sql = "SELECT C.CODE AS CCODE,EX.CODE AS ECODE FROM EXCEL20160229 ex,erp_customer c WHERE SUBSTR(EX.CODE,4,LENGTH(EX.CODE)-3)=c.code AND  SUBSTR(EX.CODE,0,3)='SY-' AND EX.XM=C.NAME";
					       sql = "SELECT c.code as CCODE,ex.code as ECODE FROM EXCEL20160229 ex,erp_customer c WHERE Upper(ex.code)=Upper(c.code) AND EX.CODE!=C.CODE AND EX.XM=C.NAME";
					PreparedStatement pstmt = con.prepareStatement(sql);
					List<Cust> listc;
					listc = new ArrayList<Cust>();
					try {
						ResultSet res=pstmt.executeQuery();
						for (;res.next();) {
							String ccode = res.getString("ccode");//76839
							String ecode = res.getString("ecode");//SY-76839
							Cust cust=new Cust();
							cust.setCcode(ccode);
							cust.setEcode(ecode);
							listc.add(cust);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}finally{
						bd.closeAll();
					}
					
					String sqlupdate = "update erp_customer set code=? where code=?";
					try {
						con=bd.getConnection();
						con.setAutoCommit(false);
						pstmt=con.prepareStatement(sqlupdate);
						int count = 0;
						for (Cust rdc : listc) {
							count++;
							String ccode =rdc.getCcode();//76839
							String ecode =rdc.getEcode();//SY-76839
							Object para[] = {ecode,ccode};
							for (int i = 0; i < para.length; i++) {
								pstmt.setObject(i + 1, para[i]);
							}
							pstmt.addBatch();
							if (count % 100 == 0) {
								System.out.println(count);
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
		}
	// 客户信息和20160229南方最新数据进行匹配，解决系统中没有SY-的问题
	public static void test3() throws Exception{
				BaseDao bd = new BaseDao();
				Connection con = bd.getConnection();
				String sql = "SELECT C.CODE AS CCODE,EX.CODE AS ECODE FROM EXCEL20160229 ex,erp_customer c WHERE SUBSTR(EX.CODE,4,LENGTH(EX.CODE)-3)=c.code AND  SUBSTR(EX.CODE,0,3)='SY-' AND EX.XM=C.NAME";
				       sql = "SELECT C.CODE AS CCODE,EX.CODE AS ECODE FROM EXCEL20160229 ex,erp_customer c WHERE SUBSTR(EX.CODE,4,LENGTH(EX.CODE)-3)=c.code AND  (SUBSTR(EX.CODE,0,3)='SY-'  OR  SUBSTR(EX.CODE,0,3)='sy-') AND EX.XM=C.NAME";
				PreparedStatement pstmt = con.prepareStatement(sql);
				List<Cust> listc;
				listc = new ArrayList<Cust>();
				try {
					ResultSet res=pstmt.executeQuery();
					for (;res.next();) {
						String ccode = res.getString("ccode");//76839
						String ecode = res.getString("ecode");//SY-76839
						Cust cust=new Cust();
						cust.setCcode(ccode);
						cust.setEcode(ecode);
						listc.add(cust);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}finally{
					bd.closeAll();
				}
				
				String sqlupdate = "update erp_customer set code=? where code=?";
				try {
					con=bd.getConnection();
					con.setAutoCommit(false);
					pstmt=con.prepareStatement(sqlupdate);
					int count = 0;
					for (Cust rdc : listc) {
						count++;
						String ccode =rdc.getCcode();//76839
						String ecode =rdc.getEcode();//SY-76839
						Object para[] = {ecode,ccode};
						for (int i = 0; i < para.length; i++) {
							pstmt.setObject(i + 1, para[i]);
						}
						pstmt.addBatch();
						if (count % 100 == 0) {
							System.out.println(count);
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
	}
	public static void test4() throws Exception{

		// 客户信息和20160229南方最新数据进行匹配，解决系统中多余SY-的问题
		BaseDao bd = new BaseDao();
		Connection con = bd.getConnection();
		//String sql = "SELECT C.CODE AS CCODE,EX.CODE AS ECODE FROM EXCEL20160229 ex,erp_customer c WHERE SUBSTR(EX.CODE,4,LENGTH(EX.CODE)-3)=c.code AND  SUBSTR(EX.CODE,0,3)='SY-' AND EX.XM=C.NAME";
		String sql = "SELECT  C.CODE AS CCODE,EX.CODE AS ECODE FROM EXCEL20160229 ex,erp_customer c WHERE SUBSTR(c.code,4,LENGTH(c.code)-3)=EX.CODE AND c.is_deleted='0' AND c.name=ex.xm";
		PreparedStatement pstmt = con.prepareStatement(sql);
		List<Cust> listc;
		listc = new ArrayList<Cust>();
		try {
			ResultSet res=pstmt.executeQuery();
			for (;res.next();) {
				String ccode = res.getString("ccode");//SY-76839
				String ecode = res.getString("ecode");//76839
				Cust cust=new Cust();
				cust.setCcode(ccode);
				cust.setEcode(ecode);
				listc.add(cust);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			bd.closeAll();
		}
		
		String sqlupdate = "update erp_customer set code=? where code=?";
		try {
			con=bd.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sqlupdate);
			int count = 0;
			for (Cust rdc : listc) {
				count++;
				String ccode =rdc.getCcode();//76839
				String ecode =rdc.getEcode();//SY-76839
				System.out.println(ccode);
				Object para[] = {ecode,ccode};
				for (int i = 0; i < para.length; i++) {
					pstmt.setObject(i + 1, para[i]);
				}
				pstmt.addBatch();
				if (count % 100 == 0) {
					System.out.println(count);
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
	}
	// 查找PDF名是否存在，打印PDF文件路径
	public static void test2() throws Exception {
		String geneftp = TestExcelProcess.GENEFTP + "upload";
		FileList fl = new FileList(geneftp);
		List<String> pdfdirfilenameList = fl.getPDFFileListByDir();
		List<String> pdffilenameList = new ArrayList<String>();
		String strCode="1219041";
		for (String pdfdirfilename : pdfdirfilenameList) {
			String code = pdfdirfilename.substring(pdfdirfilename.lastIndexOf("\\") + 1,pdfdirfilename.lastIndexOf("."));
			//pdffilenameList.add(code);
			System.out.println(code);
			if(code.contains(strCode)||strCode.equals(code)){
				System.out.println(pdfdirfilename);
				break;
			}
		}
	}
	public static void test1() throws Exception {
		BaseDao bd = new BaseDao();
		// 1.客户信息和报告信息比对，并更新客户pdf路径列表 20160308 1010.把ERP_REPORTDETAIL中的PDF路径更新到erp_customer表中
		int i = 0;
		String sql = "SELECT rd.code,rd.filepath FROM erp_customer c,ERP_REPORTDETAIL rd WHERE c.code=rd.code and "
				+ "c.pdffilepath is null and Length(rd.filepath)>20";
		List<HashMap> list = bd.querySql(sql);
		for (HashMap rdc : list) {
			String code = (String) rdc.get("code");
			String filepath = (String) rdc.get("filepath");
			String sqlupdate = "update erp_customer set pdffilepath=? where code=?  and pdffilepath is null";
			Object[] para = { filepath, code };
			bd.updateOrInsert(sqlupdate, para);
			System.out.println(i++);
		}
	}
}
class Cust{
	private String ecode;
	private String ccode;
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getCcode() {
		return ccode;
	}
	public void setCcode(String ccode) {
		this.ccode = ccode;
	}
	
}