package org.hpin.reportdetail.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Carly
 * @since 2017年1月3日16:39:22
 * 用于过滤不删除的文件目录
 */
public class KeyFileFilter implements FileFilter{
	
	@Override
	public boolean accept(File pathname) {
		if(FileList.fileReader().contains(pathname.getName())){
			return false;
		}
		return true;
	}
	
	public boolean deleteDir(File fileDir){
		if(fileDir.isDirectory()){
			String[] children = fileDir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(fileDir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		boolean flag = true;
		if(accept(fileDir)){
			flag = fileDir.delete();
		}
		return flag;
	}
	
	/**
	 * @return
	 * 不被删除的文件
	 */
	public List<String> filterFile() {
		List<String> list = new ArrayList<String>();
		list.add("zhikang");
		list.add("upload");
		list.add("jisilang");
		return list;
	}
	
}
