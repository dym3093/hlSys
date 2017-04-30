package cn.yuanmeng.labelprint.service.impl;

import cn.yuanmeng.labelprint.dao.ReportDetailBatchDao;
import cn.yuanmeng.labelprint.dao.impl.ReportDetailBatchDaoImpl;
import cn.yuanmeng.labelprint.entity.ReportDetailBatch;
import cn.yuanmeng.labelprint.service.ReportDetailBatchService;

public class ReportDetailBatchServiceImpl implements ReportDetailBatchService {
	private ReportDetailBatchDao reportDetailBatchDao = new ReportDetailBatchDaoImpl();

	@Override
	public void add(ReportDetailBatch reportDetailBatch) {
		// TODO Auto-generated method stub
		reportDetailBatchDao.add(reportDetailBatch);
	}

}
