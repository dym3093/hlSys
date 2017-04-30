package cn.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class ListFtpFile {

	private FTPClient ftpClient = new FTPClient();
	
	private OutputStream outSteam = null;
	public ListFtpFile(){
		
	}
	public ListFtpFile(String remoteDir,String localDir){
		this.remoteDir=remoteDir;
		this.localDir=localDir;
	}
	/**
	 * ftp服务器地址
	 */
	private String hostName = "ftp.healthlink.cn";
	private int port = 21;

	/**
	 * 登录名
	 */
	private String userName = "liubaoguan";// 匿名登录，空字符串不行

	/**
	 * 登录密码
	 */
	private String password = "liubaoguan";// 随便一个地址，我胡乱写一个也可以运行的

	/**
	 * 需要访问的远程目录
	 */
	private String remoteDir = "yuanmeng";
	/**
	 * 需要下载的本地目录
	 */
	private  String localDir="D:\\ftp"; 

	/**
	 * 测试可用下载制定目录下的文件,不是指定目录下的所有文件
	 */
	public  void download() {
		try {
			// 链接到ftp服务器
			ftpClient.connect(hostName, port);
			System.out.println("连接到ftp服务器：" + hostName + " 成功..开始登录");
			// 登录.用户名 密码
			boolean b = ftpClient.login(userName, password);
			System.out.println("登录成功." + b);

			// 检测连接是否成功
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}

			ftpClient.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// ====

			ftpClient.changeWorkingDirectory(remoteDir);// 转移到FTP服务器目录
			FTPFile[] fs = ftpClient.listFiles();
			for (FTPFile ff : fs) {
				System.out.println(ff.getName());
				if (ff.getName().lastIndexOf("pdf")>0||ff.getName().lastIndexOf("xlsx")>0||ff.getName().lastIndexOf("xls")>0) {
					//File localFile = new File("D:\\ftp\\"+ff.getName());
					File localFile = new File(localDir+"\\"+ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					
					ftpClient.retrieveFile(new String(ff.getName().getBytes("GBK"),"ISO-8859-1"), is);
					is.close();
				}
			}

			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outSteam);
			try {
				ftpClient.disconnect();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * 上传
	 * */
	public void upload() {
		String srcUrl = "d:/ftp/1.pdf";
		String targetFileName = "19.pdf";
		try {
			targetFileName = new String(targetFileName.getBytes("GBK"),
					"iso-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ftpClient.connect(hostName, port);
			boolean b = ftpClient.login(userName, password);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}

			ftpClient.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// ====

			File srcFile = new File(srcUrl);
			FileInputStream fis = null;
			fis = new FileInputStream(srcFile);

			// 设置上传目录
			ftpClient.changeWorkingDirectory(remoteDir);
			// 上传
			b = ftpClient.storeFile(targetFileName, fis);
			IOUtils.closeQuietly(fis);
			System.out.println(22);

			ftpClient.logout();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 测试
	 * */
	public static void main(String[] args) {
		ListFtpFile listFtpfiles = new ListFtpFile();
		// listFtpfiles.upload();
		listFtpfiles.download();
	}
}