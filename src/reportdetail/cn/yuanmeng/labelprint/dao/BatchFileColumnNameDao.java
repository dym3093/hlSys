package cn.yuanmeng.labelprint.dao;

import java.util.List;

public interface BatchFileColumnNameDao {
	public void add(Object []obj);
	public List<List<String>> search();
}
