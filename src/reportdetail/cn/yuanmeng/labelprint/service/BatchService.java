package cn.yuanmeng.labelprint.service;

import java.util.List;

import cn.yuanmeng.labelprint.entity.Batch;

public interface BatchService {
	public List<Batch> getAll();
	public int insert(Batch batch);
	public int update(Batch batch);
	public int del(int id);
	public int insertByObj(Object []obj);
	public int execute(String sql,String pathFileName);
	public List<Batch> search(String sql);
}
