package cn.poi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import cn.yuanmeng.labelprint.service.impl.ReportDetailServiceImpl;

public class FileList {
	private String dir_name = null;
	private String list_name = null;
	private BufferedWriter out = null;
	Vector<String> ver = null;

	public FileList() {
	}

	public FileList(String dir_name) {
		this.dir_name = dir_name; 
		ver = new Vector<String>(); 
	}

	public FileList(String dir_name, String list_name) throws IOException {
		this.dir_name = dir_name; 
		ver = new Vector<String>(); 
		this.list_name = list_name; 
	}


	public boolean moveFileToDir(String srcFilePath, String dirPath) {
		File file = new File(srcFilePath);
		
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String srcFileName=srcFilePath.substring(srcFilePath.lastIndexOf('\\'));
		File newFile = new File(dirPath + "\\"+srcFileName);
		
		boolean b = false;
		b = file.renameTo(newFile);
		return b;
	}


	public List<String> getXlsFileListByDir() {
		List<String> xlsFileNameList = new ArrayList<String>();
		List<String> fileNameList = this.getFileListByDir();
		if (fileNameList.size() > 0) {
			for (String fileName : fileNameList) {
				if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
					xlsFileNameList.add(fileName);
				}
			}
		}
		return xlsFileNameList;
	}

	public List<String> getPDFFileListByDir() {
		List<String> pdfFileNameList = new ArrayList<String>();
		List<String> fileNameList = this.getFileListByDir();
		if (fileNameList.size() > 0) {
			for (String fileName : fileNameList) {
				if (fileName.endsWith(".pdf")) {
					pdfFileNameList.add(fileName);
				}
			}
		}
		return pdfFileNameList;
	}


	public List<String> getFileListByDir() {
		ver = new Vector<String>(); 
		ver.add(dir_name);
		System.out.println("dir_name"+dir_name);
		List<String> fileNameList = new ArrayList<String>();
		while (ver.size() > 0) {
			File[] files = new File(ver.get(0).toString()).listFiles(); 
			ver.remove(0);

			int len = files.length;
			for (int i = 0; i < len; i++) {
				String tmp = files[i].getAbsolutePath();
				if (files[i].isDirectory()) 
					ver.add(tmp);
				else {
					fileNameList.add(tmp);
				}
		
			}
		}
		return fileNameList;
	}

	public void getListAndSaveFile() throws Exception {
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				list_name, true))); 
		ver.add(dir_name);
		while (ver.size() > 0) {
			File[] files = new File(ver.get(0).toString()).listFiles(); 
			ver.remove(0);

			int len = files.length;
			for (int i = 0; i < len; i++) {
				String tmp = files[i].getAbsolutePath();
				if (files[i].isDirectory()) 
					ver.add(tmp);
				else
					out.write(tmp + "\r\n"); 
			}
		}
		out.close();
	}

	public String filePathToJavaPath(String pathStr) {
		pathStr = pathStr.replaceAll("\\\\", "\\");
		return pathStr;
	}
}