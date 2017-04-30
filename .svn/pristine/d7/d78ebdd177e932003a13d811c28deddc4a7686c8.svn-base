package cn.ftp;

//2.在FTP服务器上生成一个文件，并将一个字符串写入到该文件中
//@Test    
//public   void  testUpLoadFromString(){   
//    try  {   
//        InputStream input = new  ByteArrayInputStream( "test ftp" .getBytes( "utf-8" ));   
//        boolean  flag = uploadFile( "127.0.0.1" ,  21 ,  "test" ,  "test" ,  "D:/ftp" ,  "test.txt" , input);   
//        System.out.println(flag);   
//    } catch  (UnsupportedEncodingException e) {   
//        e.printStackTrace();   
//    }   
//}

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import cn.poi.FileList;
import cn.yuanmeng.labelprint.entity.ReportDetail;
import cn.yuanmeng.labelprint.pdf.ReportDetailPdf;

public class FtpOperation {
	public static void main(String[] args) {
		new FtpOperation();
	}

	// 用户名
	private static String userName = "liubaoguan";
	// 密码
	private static String passWord = "liubaoguan";
	// 地址
	private static String ip = "ftp.healthlink.cn";

	// 上传文件存放的目录
	private static String UPLOAD_DIR = "test/upload";

	// OA上传xml文件到MpmsFtp的目录
	private static String DOWNLOAD_DIR = "test/download";

	// ftp客户端
	private static FTPClient ftpClient = new FTPClient();

	/**
	 * 构造函数，当目录不存在的时候，创建文件夹
	 */
	public FtpOperation() {
		try {
			connectToServer();
			// 判断指定路径是否存在，不存在就新建目录
			checkPathExist("test");
			checkPathExist(UPLOAD_DIR);
			checkPathExist(DOWNLOAD_DIR);
			closeConnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void makeDirectory(String dirName) {
		connectToServer();
		try {
			// checkPathExist("test");
			checkPathExist(dirName);
			// checkPathExist("label");
			// checkPathExist("report");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnect();
	}

	public static void makeDirectory(String district, String dirName) {
		connectToServer();
		try {
			checkPathExist(district);
			checkPathExist(dirName);
			// checkPathExist("label");
			// checkPathExist("report");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnect();
		makeSubDirectory(district, dirName);
	}

	public static void makeSubDirectory(String district, String dirName) {
		connectToServer();
		try {
			checkPathExist(district);
			checkPathExist(dirName);
			checkPathExist("label");
			checkPathExist("report");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnect();
	}

	public static void makeDirectorys(String dirName[]) {
		connectToServer();
		try {
			checkPathExist("test");
			for (int i = 0; i < dirName.length; i++) {
				checkPathExist(dirName[i]);
				System.out.println(dirName[i]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnect();
	}

	public static void makePdfToFTP(ReportDetail reportDetail) {
		try {
			connectToServer();
			checkPathExist("test");

			// 1.新建document对象
			// 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);

			// 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
			// 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("fileName.pdf"));

			// 3.打开文档
			document.open();

			// 4.向文档中添加内容
			// 通过 com.lowagie.text.Paragraph
			// 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落
			document.add(new Paragraph("First page of the document."));
			document.add(new Paragraph("with different color and font type.",
					FontFactory.getFont(FontFactory.COURIER, Float
							.valueOf("14").floatValue(), Font.BOLD,
							new BaseColor(255, 150, 200))));

			// 5.关闭文档
			document.close();

			closeConnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean uploadFile(String path, String filename,
			InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(ip);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(userName, passWord);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 设置文件类型（二进制）
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);// ====
			ftp.changeWorkingDirectory(path);
			ftp.storeFile(filename, input);

			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/**
	 * 向FTP服务器上传文件
	 * 
	 * @Version1.0
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String url, int port, String username,String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			//targetFileName=new String("汉字名称".getBytes("GBK"),"iso-8859-1");
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}

			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 设置文件类型（二进制）
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);// ====

			ftp.changeWorkingDirectory(path);
			filename=new String(filename.getBytes("gbk"),"iso-8859-1");
			ftp.storeFile(filename, input);

			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/**
	 * @Version1.0 从FTP服务器下载文件
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String url, int port, String username,String password, String remotePath, String fileName,
			String localPath) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			
			ftpClient.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 设置文件类型（二进制）
			//ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// ====
			
			
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {//ff.getName()获取FTP服务器目录下的文件假名、文件名以及..和.
				//if (ff.getName().equals(fileName)) {//获取指定文件
				if(ff.isFile()){
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					
					//ftpClient.retrieveFile(new String(ff.getName().getBytes("GBK"),"ISO-8859-1"), is);
					
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
//				if(ff.isDirectory()){
//					System.out.println(ff.getName());
//				}
			}

			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	//测试如何获取服务器上的文件夹、文件名
	public static boolean downFileTest(String url, int port, String username,String password, String remotePath, String fileName,String localPath) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			
			ftpClient.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 设置文件类型（二进制）
			//ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// ====
			
			List<String> fileNameList = new ArrayList<String>();
			Vector<String> ver = new Vector<String>(); // 用做堆栈
			ver.add(remotePath);
			
			while(ver.size()>0){
				ftp.changeWorkingDirectory(ver.get(0).toString());// 转移到FTP服务器目录
				FTPFile[] fs = ftp.listFiles();
				ver.remove(0);
				
				int len=fs.length;
				for(int i=0;i<len;i++){
					if(fs[i].isDirectory()){
						ver.add(fs[i].getName());
					}
//					else{
//						fileNameList.add(fs[i].getName());
//					}
					System.out.println(fs[i].getName());
				}
			}
			
			FTPFile dirs[]=ftp.listDirectories();//包括..和.
			for (FTPFile ftpDirFile : dirs) {
				System.out.println(ftpDirFile.getName());
//				File file=new File("d:/ftp/"+ftpDirFile.getName());
//				if(!file.exists()){
//					file.mkdirs();
//				}
			}
			System.out.println("目录结束");
			
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {//ff.getName()获取FTP服务器目录下的文件假名、文件名以及..和.
				//if (ff.getName().equals(fileName)) {//获取指定文件
				if(ff.isFile()){
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					
					//ftpClient.retrieveFile(new String(ff.getName().getBytes("GBK"),"ISO-8859-1"), is);
					
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
//				if(ff.isDirectory()){
//					System.out.println(ff.getName());
//				}
			}

			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	/**
	 * 查找指定目录是否存在
	 * 
	 * @param String
	 *            filePath 要查找的目录
	 * @return boolean:存在:true，不存在:false
	 * @throws IOException
	 */
	private static boolean checkPathExist(String filePath) throws IOException {
		boolean existFlag = false;
		try {
			if (!ftpClient.changeWorkingDirectory(filePath)) {
				ftpClient.makeDirectory(filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existFlag;
	}

	/**
	 * 连接到ftp服务器
	 */
	private static void connectToServer() {
		if (!ftpClient.isConnected()) {
			int reply;
			try {
				ftpClient = new FTPClient();
				ftpClient.connect(ip);

				ftpClient.login(userName, passWord);
				reply = ftpClient.getReplyCode();

				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					System.err.println("FTP server refused connection.");
				}
			} catch (Exception e) {
				System.err.println("登录ftp服务器【" + ip + "】失败");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭连接
	 */
	private static void closeConnect() {
		try {
			if (ftpClient != null) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 转码[GBK -> ISO-8859-1] 不同的平台需要不同的转码  !!!
	 * 
	 * @param obj
	 * @return
	 */
	private static String gbkToIso8859(Object obj) {
		try {
			if (obj == null)
				return "";
			else
				return new String(obj.toString().getBytes("GBK"), "iso-8859-1");
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 转码[ISO-8859-1 -> GBK] 不同的平台需要不同的转码
	 * 
	 * @param obj
	 * @return
	 */
	private static String iso8859ToGbk(Object obj) {
		try {
			if (obj == null)
				return "";
			else
				return new String(obj.toString().getBytes("iso-8859-1"), "GBK");
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 设置传输文件的类型[文本文件或者二进制文件]
	 * 
	 * @param fileType
	 *            --BINARY_FILE_TYPE、ASCII_FILE_TYPE
	 */
	private static void setFileType(int fileType) {
		try {
			ftpClient.setFileType(fileType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查找指定目录下面指定名称的文件是否存在
	 * 
	 * @param String
	 *            filePath 要查找的目录
	 * @param String
	 *            fileName 要查找的文件名称
	 * @return boolean:存在:true，不存在:false
	 * @throws IOException
	 */
	private static boolean checkFileExist(String filePath, String fileName)
			throws IOException {
		boolean existFlag = false;
		// 跳转到指定的文件目录
		if (filePath != null && !filePath.equals("")) {
			if (filePath.indexOf("/") != -1) {
				int index = 0;
				while ((index = filePath.indexOf("/")) != -1) {
					ftpClient.changeWorkingDirectory(filePath.substring(0,
							index));
					filePath = filePath.substring(index + 1, filePath.length());
				}
				if (!filePath.equals("")) {
					ftpClient.changeWorkingDirectory(filePath);
				}
			} else {
				ftpClient.changeWorkingDirectory(filePath);
			}
		}
		String[] fileNames = ftpClient.listNames();
		if (fileNames != null && fileNames.length > 0) {
			for (int i = 0; i < fileNames.length; i++) {
				if (fileNames[i] != null
						&& iso8859ToGbk(fileNames[i]).equals(fileName)) {
					existFlag = true;
					break;
				}
			}
		}
		ftpClient.changeToParentDirectory();
		return existFlag;
	}

	/**
	 * 从ftp下载指定名称的文件到本地
	 * 
	 * @param String
	 *            remoteFileName --服务器上的文件名(只需要文件名，比如"req_0823.doc")
	 * @param String
	 *            localFileName--本地文件名（包括完整的物理路径和文件名，比如"F:/ftpfile/req_0823.doc"
	 *            ，文件名可以自己定，可以不和服务器上的名字一致）
	 */
	private static boolean downloadFileByName(String remoteFilePath,String remoteFileName, String localFileName) throws IOException {
		boolean returnValue = false;
		// 下载文件
		BufferedOutputStream buffOut = null;
		try {
			// 连接ftp服务器
			connectToServer();
			File localFile = new File(localFileName.substring(0,
					localFileName.lastIndexOf("/")));
			if (!localFile.exists()) {
				localFile.mkdirs();
			}
			if (!checkFileExist(remoteFilePath, remoteFileName)) {
				System.out.println("<----------- ERR : file  " + remoteFilePath
						+ "/" + remoteFileName
						+ " does not exist, download failed!----------->");
				return false;
			} else {
				// 跳转到指定的文件目录
				if (remoteFilePath != null) {
					if (remoteFilePath.indexOf("/") != -1) {
						int index = 0;
						while ((index = remoteFilePath.indexOf("/")) != -1) {
							ftpClient.changeWorkingDirectory(remoteFilePath
									.substring(0, index));
							remoteFilePath = remoteFilePath.substring(
									index + 1, remoteFilePath.length());
						}
						if (!remoteFilePath.equals("")) {
							ftpClient.changeWorkingDirectory(remoteFilePath);
						}
					} else {
						ftpClient.changeWorkingDirectory(remoteFilePath);
					}
				}
				// 设置传输二进制文件
				setFileType(FTP.BINARY_FILE_TYPE);
				// 获得服务器文件
				buffOut = new BufferedOutputStream(new FileOutputStream(
						localFileName));
				returnValue = ftpClient.retrieveFile(
						gbkToIso8859(remoteFileName), buffOut);
				// 输出操作结果信息
				if (returnValue) {
					System.out.println("<----------- INFO: download "
							+ remoteFilePath + "/" + remoteFileName
							+ " from ftp ： succeed! ----------->");
				} else {
					System.out.println("<----------- ERR : download "
							+ remoteFilePath + "/" + remoteFileName
							+ " from ftp : failed! ----------->");
				}
			}
			// 关闭连接
			closeConnect();
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = false;
			// 输出操作结果信息
			System.out
					.println("<----------- ERR : download " + remoteFilePath
							+ "/" + remoteFileName
							+ " from ftp : failed! ----------->");
		} finally {
			try {
				if (buffOut != null) {
					buffOut.close();
				}
				if (ftpClient.isConnected()) {
					closeConnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	// 上传文件附件到ftp
	public static boolean uploadAnnexToMpmsFtp(File uploadFile, String fileName)
			throws IOException {
		boolean returnValue = false;
		// 上传文件
		BufferedInputStream buffIn = null;
		try {
			if (!uploadFile.exists()) {
				System.out.println("<----------- ERR : annex named " + fileName
						+ " not exist, upload failed! ----------->");
				return false;
			} else {
				// 建立连接
				connectToServer();
				// 设置传输二进制文件
				setFileType(FTP.BINARY_FILE_TYPE);
				// 获得文件
				buffIn = new BufferedInputStream(
						new FileInputStream(uploadFile));
				// 上传文件到ftp
				returnValue = ftpClient.storeFile(gbkToIso8859(UPLOAD_DIR + "/"
						+ fileName), buffIn);
				// 输出操作结果信息
				if (returnValue) {
					System.out.println("<----------- INFO: upload file "
							+ uploadFile.getAbsolutePath()
							+ " to ftp : succeed! ----------->");
				} else {
					System.out.println("<----------- ERR : upload file "
							+ uploadFile.getAbsolutePath()
							+ " to ftp : failed! ----------->");
				}
				// 关闭连接
				closeConnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = false;
			System.out.println("<----------- ERR : upload file "
					+ uploadFile.getAbsolutePath()
					+ " to ftp : failed! ----------->");
		} finally {
			try {
				if (buffIn != null) {
					buffIn.close();
				}
				if (ftpClient.isConnected()) {
					closeConnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	/**
	 * 删除服务器上文件
	 * 
	 * @param fileDir
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @throws IOException
	 */
	private static boolean delFile(String fileDir, String fileName)
			throws IOException {
		boolean returnValue = false;
		try {
			// 连接ftp服务器
			connectToServer();
			// 跳转到指定的文件目录
			if (fileDir != null) {
				if (fileDir.indexOf("/") != -1) {
					int index = 0;
					while ((index = fileDir.indexOf("/")) != -1) {
						ftpClient.changeWorkingDirectory(fileDir.substring(0,
								index));
						fileDir = fileDir
								.substring(index + 1, fileDir.length());
					}
					if (!fileDir.equals("")) {
						ftpClient.changeWorkingDirectory(fileDir);
					}
				} else {
					ftpClient.changeWorkingDirectory(fileDir);
				}
			}
			// 设置传输二进制文件
			setFileType(FTP.BINARY_FILE_TYPE);
			// 获得服务器文件
			returnValue = ftpClient.deleteFile(fileName);
			// 关闭连接
			closeConnect();
			// 输出操作结果信息
			if (returnValue) {
				System.out.println("<----------- INFO: delete " + fileDir + "/"
						+ fileName + " at ftp:succeed! ----------->");
			} else {
				System.out.println("<----------- ERR : delete " + fileDir + "/"
						+ fileName + " at ftp:failed! ----------->");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = false;
			// 输出操作结果信息
			System.out.println("<----------- ERR : delete " + fileDir + "/"
					+ fileName + " at ftp:failed! ----------->");
		} finally {
			try {
				if (ftpClient.isConnected()) {
					closeConnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

}
