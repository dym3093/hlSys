package cn.yuanmeng.labelprint.test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class T {

	public static void main(String[] args) {
//		
//		System.out.println( System.getProperty("user.dir"));
//		System.out.println( System.getProperty("user.dir")	+ "\\localdir\\");
		String s1="D:\\Workspaces\\.metadata\\.me_tcat7\\webapps\\ymjy\\uploadFile\\localDirB\\20160119\\SY-58527.pdf";
		String s2="D:\\Workspaces\\.metadata\\.me_tcat7\\webapps\\ymjy\\uploadFile\\localDirB\\20160119";
//		System.out.println(s1.indexOf(s2));
		List list=new ArrayList();
		list.add("0");
		list.add("1");
		System.out.println(list.get(list.size()-1));
	}
	
}
