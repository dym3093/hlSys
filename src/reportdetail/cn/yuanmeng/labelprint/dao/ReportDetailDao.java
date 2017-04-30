package cn.yuanmeng.labelprint.dao;

import java.util.List;

import cn.yuanmeng.labelprint.entity.ReportDetail;

public interface ReportDetailDao {
	public List<ReportDetail> getAll();
	public int sheetToTable(Object obj[]);
	public int add(ReportDetail reportDetail);
	public int del();
	
}
