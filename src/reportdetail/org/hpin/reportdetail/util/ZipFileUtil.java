package org.hpin.reportdetail.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
  
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.util.CollectionUtils;

public class ZipFileUtil {

	private static Logger log = Logger.getLogger(ZipFileUtil.class);
	private int readedBytes;
	private int bufSize;
	private byte[] buf;
	private static String ZIP_ENCODEING = "GBK";
	
	public ZipFileUtil() {
		this(512);
	}
	
	public ZipFileUtil(int bufSize) {
		this.bufSize = bufSize;
		this.buf = new byte[this.bufSize];
	}
	
	/**
	 * 压缩文件或文件夹
	 */
	public void fileToZip(String zipFileName, String inputFile){
		try{
			zip(zipFileName, new File(inputFile));
		}catch(Exception e){
			log.error("ZipFileUtil fileToZip", e);
		}
		
	}

	/**
	 * 压缩文件或文件夹
	 */
	public void zip(String zipFileName, File inputFile) throws Exception {
		// 未指定压缩文件名，默认为"ZipFile"
		if (zipFileName == null || zipFileName.equals(""))
			zipFileName = "ZipFile";
		// 添加".zip"后缀
		if (!zipFileName.endsWith(".zip")){
			zipFileName += ".zip";
		}
		// 创建文件夹
		String path = Pattern.compile("[\\/]").matcher(zipFileName).replaceAll("\\\\");
		log.info("压缩路径："+path);
		int endIndex = path.lastIndexOf("\\");
		log.info("endIndex:"+endIndex);
		path = path.substring(0, endIndex);
		File f = new File(path);
		f.mkdirs();
		// 开始压缩
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));
		zos.setEncoding(ZIP_ENCODEING);
		compress(zos, inputFile, "");
		log.info("zip done");
		zos.close();
	}

	/**
	 * 压缩一个文件夹或文件对象到已经打开的zip输出流 
	 */
	public void compress(ZipOutputStream zipOut, File f, String fileName)throws Exception {
		log.info("Zipping " + f.getName());
		if (f.isDirectory()) {
			// 压缩文件夹
			File[] fl = f.listFiles();
			zipOut.putNextEntry(new ZipEntry(fileName + "/"));
			fileName = fileName.length() == 0 ? "" : fileName + "/";
			for (int i = 0; i < fl.length; i++) {
				compress(zipOut, fl[i], fileName + fl[i].getName());
			}
		} else {
			// 压缩文件
			zipOut.putNextEntry(new ZipEntry(fileName));
			FileInputStream fileIn = new FileInputStream(f);
			while ((this.readedBytes = fileIn.read(this.buf)) > 0) {
				zipOut.write(this.buf, 0, this.readedBytes);
			}
			fileIn.close();
			zipOut.closeEntry();
		}
	}

	/**
	 * 压缩一组文件夹
	 * @param zipFileName
	 * @param inputFile
	 * @throws Exception
     *
	 */
	public void zipFolder(String zipFileName,List<String> inputFile) throws Exception{
		//将需要打印的目录移动到指定文件夹下
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));
		zos.setEncoding(ZIP_ENCODEING);
		for(String str : inputFile){
			try{
				compress(zos, new File(str), "");
			}catch(Exception e){
				log.error("ZipFileUtil fileToZip", e);
			}
		}
		zos.close();
	}

	/**
	 * 批量压缩文件
	 * @param list 被压缩的文件列表
	 * @param saveDir 压缩文件保存的目录
	 * @param zipFileName 压缩文件名称
	 * @return File 压缩文件
	 * @author Damian
	 * @since 2017-02-24
	 */
	public File compressList(List<File> list, String saveDir, String zipFileName) throws Exception{
		File zipFile = null;
		ZipOutputStream zos;
		if (!CollectionUtils.isEmpty(list)) {
			String zipName = saveDir + File.separator + zipFileName;
			if (zipName.indexOf(".zip") < 0) {
				zipName = zipName + ".zip";
			}
			zipFile = new File(zipName);
			// 如果路径不存在,则创建
			if (!zipFile.getParentFile().exists()) {
				zipFile.getParentFile().mkdirs();
			}
			if (zipFile.exists()) {
				//检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
//				SecurityManager securityManager = new SecurityManager();
//				securityManager.checkDelete(zipName);
				//删除已存在的目标文件
				zipFile.delete();
			}
//			zipFile.createNewFile();
			zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
			zos.setEncoding(ZIP_ENCODEING);
			ZipEntry entry;
			BufferedInputStream orgIn;
			for (File file : list) {
				orgIn = new BufferedInputStream(new FileInputStream(file));
				String otherEntryName = new String(file.getName().getBytes(ZIP_ENCODEING), ZIP_ENCODEING);
				log.info("otherEntryName:" + otherEntryName );
				entry = new ZipEntry(otherEntryName);
				zos.putNextEntry(entry);
				while ((this.readedBytes = orgIn.read(this.buf)) > 0) {
					zos.write(this.buf, 0, this.readedBytes);
				}
				orgIn.close();
			}
			if (zos != null) {
				zos.close();
			}
		}
		return zipFile;
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		/*list.add("D:\\pdf\\20160404");
		list.add("D:\\pdf\\20160406");
		list.add("D:\\pdf\\bugpdf\\20160405");*/
		list.add("D:\\pdf\\zip\\201604251924_安庆分公司望江支公司_基础一_3\\");
		list.add("D:\\pdf\\zip\\201604251924_巴州分公司_联动基础一_2\\");
		list.add("D:\\pdf\\zip\\201604251924_蚌埠分公司蚌山支公司_基础一_80\\");
		try {
			ZipFileUtil t = new ZipFileUtil();
			t.zipFolder("D:\\pdf\\加.zip", list);
			//t.fileToZip("D:\\Tomcat\\apache-tomcat-6.0.45\\wtpwebapps\\ymjy\\filetmp\\加.zip", "D:\\Tomcat\\apache-tomcat-6.0.45\\wtpwebapps\\ymjy\\filetmp\\20160402100401");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
}
