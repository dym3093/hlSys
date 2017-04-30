package cn.yuanmeng.labelprint.service.impl;

import java.util.List;

import cn.yuanmeng.labelprint.dao.BatchDao;
import cn.yuanmeng.labelprint.dao.impl.BatchDaoImpl;
import cn.yuanmeng.labelprint.entity.Batch;
import cn.yuanmeng.labelprint.service.BatchService;

public class BatchServiceImpl implements BatchService {
	private BatchDao batchDao=new BatchDaoImpl();
	public List<Batch> search(String sql){
		return batchDao.search(sql);
	}
	public int execute(String sql,String pathFileName){
		return batchDao.execute(sql, pathFileName);
	}
	@Override
	public List<Batch> getAll() {
		// TODO Auto-generated method stub
		return batchDao.getAll();
	}

	@Override
	public int insert(Batch batch) {
		// TODO Auto-generated method stub
		return batchDao.insert(batch);
	}

	@Override
	public int update(Batch batch) {
		// TODO Auto-generated method stub
		return batchDao.update(batch);
	}

	@Override
	public int del(int id) {
		// TODO Auto-generated method stub
		return batchDao.del(id);
	}
	public int insertByObj(Object []obj){
		return batchDao.insertByObj(obj);
	}

}
