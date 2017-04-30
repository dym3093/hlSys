package org.hpin.reportdetail.service;

import org.hpin.common.core.orm.BaseService;
import org.hpin.reportdetail.dao.ErpReportdetailBatchDao;
import org.hpin.reportdetail.entity.ErpReportdetailBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service(value = "org.hpin.reportdetail.service.ErpReportdetailBatchService")
@Transactional()
public class ErpReportdetailBatchService extends BaseService {
	@Autowired
	ErpReportdetailBatchDao reportdetailBatchDao;
	
	public ErpReportdetailBatch get(String id){
		return reportdetailBatchDao.get(id);
	}
	public void add(ErpReportdetailBatch reportdetailBatch){
		this.save(reportdetailBatch);
	}
}
