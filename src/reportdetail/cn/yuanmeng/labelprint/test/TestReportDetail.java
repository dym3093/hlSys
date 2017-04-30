package cn.yuanmeng.labelprint.test;
import java.util.List;

import cn.ftp.FtpOperation;
import cn.poi.FileList;
import cn.yuanmeng.labelprint.service.*;
import cn.yuanmeng.labelprint.service.impl.*;
import cn.yuanmeng.labelprint.entity.*;
public class TestReportDetail {
	public static void main(String[] args) {
		//String path="d:/A92/1-0143-82.xls";
		String districtNamePath="D://标签打印制作//深圳";
		ReportDetailServiceImpl rdsi=new ReportDetailServiceImpl(districtNamePath);
		rdsi.start();
	}
}
