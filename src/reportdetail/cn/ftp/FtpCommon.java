package cn.ftp;

import java.io.File;
import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

public class FtpCommon {

	private String ip = "192.168.1.108";
	private int port = 21;
	private String userName = "liubaoguan";
	private String password = "liubaoguan";
	private String filepath = "D:/localdir/";
	private String remoteRoot = "/20160111/";
	static FTPClient client = new FTPClient();

	public void downloadAll(String remoteRoot, String filepath){
		try {
			client.connect(ip, port);
			client.login(userName, password);
			client.setType(FTPClient.TYPE_AUTO);
			client.setCharset("GBK");
			downloadDirectory(client, remoteRoot, filepath);
			client.disconnect(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	public static void main(String s[]) throws Exception {
		String filepath = "D:/localdir/";
		String remoteRoot = "/20160111/";
		FtpCommon ftpUtil = new FtpCommon();
		ftpUtil.downloadAll(remoteRoot, filepath);
		System.out.println("========");
	}

	public boolean downloadDirectory(FTPClient client, String directoryPath,String localDirectory) {
		try {
			File lf = new File(localDirectory);
			if (!lf.exists()) {
				lf.mkdirs();
			}
			if (lf.isDirectory()
					&& "Dir".equals(fileOrDirectory(client, directoryPath))) {
				client.changeDirectory(directoryPath);
				FTPFile[] list = client.list();
				for (FTPFile f : list) {
					if (!".".equals(f.getName()) && !"..".equals(f.getName())) {
						if ("File".equals(fileOrDirectory(client, directoryPath
								+ "/" + f.getName()))) {
							downloadFile(client,
									directoryPath + "/" + f.getName(), lf
											+ "\\" + f.getName());
						} else if ("Dir".equals(fileOrDirectory(client,
								directoryPath + "/" + f.getName()))) {
							downloadDirectory(client,
									directoryPath + "/" + f.getName(), lf
											+ "\\" + f.getName());
						} else {
							client.changeDirectory("/");
							return false;
						}
					}
				}
				client.changeDirectory("/");
			} else {
				client.changeDirectory("/");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private String fileOrDirectory(FTPClient client, String fileurl) {
		if ("".equals(fileurl) || fileurl == null) {
			return "error";
		}
		try {
			String filetemp[] = fileurl.split("/");
			String temp = "";
			for (int i = 0; i < filetemp.length; i++) {
				if (i == filetemp.length - 1) {
					client.changeDirectory(temp);
					FTPFile[] allf = client.list();
					for (FTPFile f : allf) {
						if (filetemp[i].equals(f.getName())) {
							if (f.getType() == 1)// 文件夹
							{
								return "Dir";
							}
							if (f.getType() == 0)// 文件
							{
								return "File";
							}
						}
					}
					client.changeDirectory("/");
					return "error";
				} else {
					if ("/".equals(temp)) {
						temp += filetemp[i];
					} else {
						temp += "/" + filetemp[i];
					}
					if (!isExistsDir(client, temp)) {
						return "error";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Dir";
	}

	private boolean isExistsDir(FTPClient client, String dirurl) {
		try {
			client.changeDirectory(dirurl);
			client.changeDirectory("/");
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean downloadFile(FTPClient client, String ftpFile,
			String localFile) {
		try {
			File lf = new File(localFile);
			// 判断父目录是否存在不存在则创建
			File far = new File(lf.getParent());
			if (lf.exists()) {
				lf.delete();
			} else if (!far.exists()) {
				far.mkdirs();
			}
			client.download(ftpFile, lf);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
