package cn.yuanmeng.labelprint.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.Logger;

import cn.poi.Excel;
import cn.poi.FileList;
import cn.yuanmeng.labelprint.dao.BaseDao;

public class TestExcelProcess {
	static Logger logger = Logger.getLogger(TestExcelProcess.class);
//	static Logger logger = LoggerFactory.getLogger(TestExcelProcess.class);
	static BufferedWriter outSqlException;
	public static final String GENEFTP="d:\\ftp\\";
	public static void main(String margs[]) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd1=sdf1.format(new Date());
		logger.info(">>>>main进入定时任务"+sd1);
		
		TestExcelProcess t = new TestExcelProcess();
		String dirNameUpload="20160219";
		dirNameUpload="20160319";
		dirNameUpload="20080101";
		dirNameUpload="20160304";
		dirNameUpload="20160305";
		dirNameUpload="20160306";//北方数据
//		dirNameUpload="20150219";
		String dir=GENEFTP+"upload\\"+dirNameUpload;
		File f=new File(dir);
		if(f.exists()&&f.isDirectory()){
			File fileList[]=f.listFiles();
			if(fileList.length>0){
				try {
					t.start(dirNameUpload);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						outSqlException.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				System.out.println("没文件");
			}
		}else{
			System.out.println("不存在");
		}
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd2=sdf1.format(new Date());
		logger.info(">>>>main结束定时任务"+sd2);
	}
	//定时任务调用
	public void startTimmer(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dirNameUpload=sdf.format(new Date());
		String dir=GENEFTP+"upload\\"+dirNameUpload;
		File f=new File(dir);
		if(f.exists()&&f.isDirectory()){
			File fileList[]=f.listFiles();
			if(fileList.length>0){
				try {
					start(dirNameUpload);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						outSqlException.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				logger.info(">>>>"+dirNameUpload+"目录为空");
			}
		}else{
			logger.info(">>>>"+dirNameUpload+"目录不存在");
		}
		System.out.println(">>>>结束定时任务");
	}
	//jsp直接调用
	public void start(String path) throws FileNotFoundException,IOException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String batchno;
		// 原位置
		String localDirA = GENEFTP + "upload\\" + path;
		String localDirB = GENEFTP+ "transact\\" + path;

		outSqlException = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(localDirA+ "\\"+path+".txt",true)));
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s1=sdf1.format(new Date());
		logger.info(GENEFTP+">>>>进入定时任务"+s1);
		outSqlException.write(">>>>进入定时任务"+s1+"\r\n");
		
		FileList fl = new FileList(localDirA);
		Excel excel = new Excel();
		List<String> xlsFilePathNameList = fl.getXlsFileListByDir();
		// 等级排序索引
		int gradeorder[] = new int[xlsFilePathNameList.size()];
		String pathGrade[];
		// 初始化等级数组
		for (int i = 0; i < xlsFilePathNameList.size(); i++) {
			String xlsFilePathName = xlsFilePathNameList.get(i);
			pathGrade = xlsFilePathName.split("\\\\");
			gradeorder[i] = pathGrade.length;
		}

		BaseDao bd = new BaseDao();

		// 按等级从高到低排序
		for (int i = 0; i < gradeorder.length; i++) {
			int max = gradeorder[i];
			String tempXlsFilePathName = xlsFilePathNameList.get(i);
			int t = -1;
			// 选择等级高的优选处理
			for (int j = 0; j < gradeorder.length; j++) {
				if (max < gradeorder[j]) {
					max = gradeorder[j];
					tempXlsFilePathName = xlsFilePathNameList.get(j);
					t = j;
				}
			}
			if (t != -1)
				gradeorder[t] = -1;

			// 按等级从高到低
			pathGrade = tempXlsFilePathName.split("\\\\");
			// excel旧文件名
			String xlsOldFileName = tempXlsFilePathName.substring(
					tempXlsFilePathName.lastIndexOf("\\") + 1).trim();
			// xls旧文件路径
			String xlsFilePath = tempXlsFilePathName.substring(0,
					tempXlsFilePathName.lastIndexOf("\\"));
			String s = "";
			if (i < 9) {
				s = s + "000" + (i + 1);
			} else if (i >= 9 && i < 99) {
				s = s + "00" + (i + 1);
			} else if (i >= 99 && i < 999) {
				s = s + "0" + (i + 1);
			} else {
				s = s + (i + 1);
			}
			// 批次号
			batchno = path + (pathGrade.length - 2) + s;
			System.out.println("批次号：" + batchno);
			// 保存
			try {
				Connection con = bd.getConnection();
				String sql = "insert into epr_report_batch(ID,BATCHNO,BATCHDATE,XLSOLDNAME,XLSOLDPATH,PATHGRADE,XLSNEWNAME,XLSNEWPATH,STATUS) values(?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				String xlsNewName = batchno
						+ xlsOldFileName.substring(xlsOldFileName.indexOf("."));
				
				String xlsNewPath = localDirB + "\\" + batchno;
				System.out.println(localDirB);
				Object[] obj = {
						UUID.randomUUID().toString().replaceAll("-", ""),
						batchno, sdf.format(new Date()), xlsOldFileName,
						xlsFilePath, (pathGrade.length - 2), xlsNewName,
						xlsNewPath, "0" };
				bd.updateOrInsert(sql, obj);
			} catch (SQLException e) {
				logger.error("*******Excel"+xlsFilePath+ xlsOldFileName+"进表epr_report_batch时错误******");
				e.printStackTrace();
			} finally {
				bd.closeAll();
			}
		}

		// excel入库
		String sql = "select * from epr_report_batch where status='0'";
		PreparedStatement pstmt =null;
		List<HashMap> listh = null;
		try {
			listh = bd.querySql(sql);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for (HashMap hashMap : listh) {// 处理每一个Excel文件
			// 批次号
			batchno = (String) hashMap.get("batchno");
			// xls新路径
			String newPath = (String) hashMap.get("xlsnewpath");
			// xls新名字
			String newName = (String) hashMap.get("xlsnewname");

			String xlsoldpath = (String) hashMap.get("xlsoldpath");
			String xlsoldname = (String) hashMap.get("xlsoldname");

			String username = "gene";
			String password = "gene";

			String newPathF = newPath.substring(16);

			String fileDirPath = "ftp://" + username + ":" + password+ "@gene.healthlink.cn/transact/" + newPathF;

			fl = new FileList(xlsoldpath);
			List<String> pdfFilePathNameList = fl.getPDFFileListByDir();
			List<String> pdfFileNameList = new ArrayList<String>();

			int pdfFileCount = pdfFilePathNameList.size();
			int xlspdfCount = 0;
			int codeNoReportFileCount = 0;
			int xlsCodeCount = 0;
			int reportFileNoCode = 0;

			// 读Excel文件保存到数据库
			List<List<String>> excelList = excel.readXls(xlsoldpath + "\\"+ xlsoldname);

			Connection con = bd.getConnection();
			boolean b = true;// excel格式符合要求TEST_INSTITUTION
			try {
				con.setAutoCommit(false);
				
				int temp=4;
				if (excelList.size() > 0) {
					List<String> row = excelList.get(0);// 处理表头
					String code = row.get(1);
					String name = row.get(2);
					String sex = row.get(3);
					String age = row.get(4);
					String testInstitution=row.get(row.size()-1);//检测机构
					if(testInstitution.equals("检测机构")){
						sql = "insert into ERP_REPORTDETAIL(id,xlsno,code,name,sex,age,phone,branchCompany,project,sampleType,ym_salesman,entering,institution,samplingDate,collectionDate,simpleStatus,page,test_institution,batchno,filepath,ismatch) "
								+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?)";
						System.out.println("检测机构***************");
						temp=4;
						pstmt = con.prepareStatement(sql);
					}else{
						sql = "insert into ERP_REPORTDETAIL(id,xlsno,code,name,sex,age,phone,branchCompany,project,sampleType,salesMan,entering,institution,samplingDate,collectionDate,simpleStatus,page,batchno,filepath,ismatch) "
								+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,?,?)";
						pstmt = con.prepareStatement(sql);
					}
					if ((code == null || name == null || sex == null || age == null)) {
						System.out.println(xlsoldpath + "\\" + xlsoldname+ "格式不符合要求");
						b = false;
					} else if (!(code.contains("条形码") && name.contains("姓名") && sex.contains("性别") && age.equals("年龄"))) {
						System.out.println(xlsoldpath + "\\" + xlsoldname + "格式不符合要求");
						b = false;
					}
				}
				if (b == true && excelList.size() > 0) {// 格式符合要求，批处理
					for (int i = 1; i < excelList.size(); i++) {
						List<String> row = excelList.get(i);
						int colcount = row.size() + temp;
						Object obj[] = new Object[colcount];
						System.out.println(obj.length+"--------------");
						int j = 0;
						obj[j++] = UUID.randomUUID().toString().replaceAll("-", "");
						for (String cell : row) {
							System.out.print(cell+"  ");
							obj[j++] = cell;
						}System.out.println();
						obj[j++] = batchno;// 批次号
						String code = row.get(1).trim();
						// //判断条形码是否和PDF报告一致

						for (String pdfFilePathName : pdfFilePathNameList) {
							String pdfFileName = pdfFilePathName.substring(
									pdfFilePathName.lastIndexOf("\\") + 1,
									pdfFilePathName.lastIndexOf(".")).trim();
							pdfFileNameList.add(pdfFileName);
						}
						if (pdfFileNameList.contains(code)) {
							obj[j++] = fileDirPath + "/" + code + ".pdf";
							obj[j++] = "1";// 是否匹配
							xlspdfCount++;
							pdfFileNameList.remove(code);
						} else {
							codeNoReportFileCount++;// 条形码没有报告文件数
							obj[j++] = "";// //
							obj[j++] = "0";// 是否匹配
						}

						if (obj != null) {
							System.out.println("length:"+obj.length);
							System.out.println(sql);
							for (int oi = 0; oi < obj.length; oi++) {
								System.out.print( obj[oi]+" *");
								pstmt.setObject(oi + 1, obj[oi]);
							}
							System.out.println("!!obj");
						}
						
						
						pstmt.addBatch();
						xlsCodeCount++;// excel中条形码数
					}//多行循环结束
					
					pstmt.executeBatch();
					con.commit();// excel行处理完

					// 复制文件到指定路径
					FileCopy.fileFromSorceToDest(batchno);//**************************************************************************

					// 处理成功更新批次表状态
					sql = "update epr_report_batch set xlstotal=?,pdftotal=?,xlspdftotal=?,status=? where batchno=?";
					Object obj[] = { xlsCodeCount, pdfFileCount, xlspdfCount,"1", batchno };
					bd.updateOrInsert(sql, obj);
				} else {// b==false或者excelList.size()==0
					sql = "update epr_report_batch set xlstotal=?,pdftotal=?,xlspdftotal=?,status=? where batchno=?";
					Object obj[] = { excelList.size()>0?excelList.size()-1:0, pdfFileCount, 0,"2", batchno };
					System.out.println("print:"+sql);
					bd.updateOrInsert(sql, obj);
					logger.info("EXCEL格式表头错误>>批次号：" + batchno + " 路径：" + xlsoldpath+"/"+ xlsoldname);
					outSqlException.write("EXCEL格式表头错误>>批次号：" + batchno + " 路径：" + xlsoldpath+"/"+ xlsoldname+"\r\n");
				}
			} catch (Exception e) {
				try {
					con.rollback();
					bd.closeAll();
					con = bd.getConnection();
					sql = "update epr_report_batch set xlstotal=?,pdftotal=?,xlspdftotal=?,status=? where batchno=?";
					Object obj[] = { excelList.size()>0?excelList.size()-1:0, pdfFileCount, 0,"2", batchno };
					bd.updateOrInsert(sql, obj);
					logger.info("EXCEL格式行中错误>>批次号：" + batchno +  " 路径：" + xlsoldpath+"/"+ xlsoldname);
					outSqlException.write("EXCEL格式行中错误>>批次号：" + batchno +  " 路径：" + xlsoldpath+"/"+ xlsoldname+"\r\n");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				bd.closeAll();
			}

		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s2=sdf2.format(new Date());
		logger.info(">>>>进入定时任务"+s2);
		outSqlException.write(">>>>结束定时任务"+s2+"\r\n");
		outSqlException.close();

	}

}
