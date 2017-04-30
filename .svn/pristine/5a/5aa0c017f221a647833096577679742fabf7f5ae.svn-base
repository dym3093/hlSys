package org.hpin.base.accessories.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileDownLoad implements Runnable {

	private int sleepTime = 10;

	private static boolean flag = true;

	private URL url = null;

	private String newFileName = "";

	private String newAddr = "";

	private int count = 0;

	private int error = 0;

	public FileDownLoad(String strRemoteAddr, String strLocalAddr) {
		try {
			url = new URL(strRemoteAddr);
			newFileName = (new File(url.getFile())).getName();
			newAddr = strLocalAddr;
			flag = true;
		} catch (Exception ex) {
			System.out.println("---DOWND ERROR---" + ex.getMessage());
			stopThread(); // 不成功停止线程的启动
		}
	}

	public FileDownLoad(String strRemoteAddr, String strLocalAddr,
			String filename) {
		try {
			url = new URL(strRemoteAddr);
			// newFileName = (new File(url.getFile())).getName();
			newFileName = filename;
			newAddr = strLocalAddr;
			flag = true;
		} catch (Exception ex) {
			System.out.println("---DOWND ERROR---" + ex.getMessage());
			stopThread(); // 不成功停止线程的启动
		}
	}

	public static void stopThread() {
		flag = false;
	}

	public void run() {
		System.out.println("-----------下载线程启动---------------");
		while (flag) {
			try {
				System.out.println("-----------开始打开远程链接---------------");
				URLConnection urlconnection = url.openConnection();
				System.out.println("-----------已经打开远程链接---------------");
				urlconnection.setUseCaches(false);
				InputStream is = urlconnection.getInputStream();
				long filelength = urlconnection.getContentLength(); // 附件的大小
				System.out.println("@@@@@@@@@@@@@@@附件的大小：filelength=" + filelength);
				int ratio = 0;
				if (is != null) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffers = new byte[4096];
					int len = 0;
					long received = 0;
					boolean isContinue = true;
					while (isContinue) {
						len = is.read(buffers, 0, 4096);
						if (len > 0) {
							baos.write(buffers, 0, len);
							received += len;
							System.out.print(received);
							if (ratio != (int) (received * 100 / filelength)) {
								ratio = (int) (received * 100 / filelength);
								System.out.print(ratio + "%");
							}
						} else {
							break;
						}
					}
					System.out.println("!!!!!!!!!!!!!!!!received=" + received);
					if (received != 0 && received == filelength) {
						FileOutputStream fos = new FileOutputStream(newAddr
								+ File.separator + newFileName);
						baos.writeTo(fos);
						fos.close();
						System.out
								.println("file tranfering finished!! and be saved in "
										+ newAddr
										+ File.separator
										+ newFileName);
						this.stopThread();
					} else if(filelength == -1 && received>0){//如果是未知文件大小的情况下，则下载完成之后直接保存，不再比较文件大小
						System.out.println("!!!!!!!!!未知大小的文件保存到本地");
						FileOutputStream fos = new FileOutputStream(newAddr
								+ File.separator + newFileName);
						baos.writeTo(fos);
						fos.close();
						System.out
								.println("file tranfering finished!! and be saved in "
										+ newAddr
										+ File.separator
										+ newFileName);
						this.stopThread();
					} else if(filelength == 0 && received == 0){
						this.stopThread();
					}
					
				} // is == null
				else {
					error = 1;
					System.out.println("@@@@@@@@@@@@@@@error");
				}
			} catch (MalformedURLException expt) {
				expt.printStackTrace();
				System.out.println(expt);
				error = 1;
			} catch (IOException eio) {
				eio.printStackTrace();
				System.out.println(eio);
				error = 1;
			} finally {
				if (error == 1) {
					if (count > 5) {
						System.out.println("--------重试次数超过10次，下载失败--------");
						this.stopThread();
					} else {
						System.out.println("--------错误发生，等待" + sleepTime
								+ "秒后重试--------");
						count = count + 1;
						try {
							Thread.currentThread().sleep(sleepTime);
						} catch (InterruptedException ei) {
							ei.printStackTrace();
							error = 1;
						}

					}
					error = 0;
				}
			}
		}
	}
}
