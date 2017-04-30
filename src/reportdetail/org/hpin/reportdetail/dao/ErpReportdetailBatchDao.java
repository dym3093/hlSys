package org.hpin.reportdetail.dao;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.reportdetail.entity.ErpReportdetailBatch;
import org.springframework.stereotype.Repository;

@Repository
public class ErpReportdetailBatchDao extends BaseDao {
	public ErpReportdetailBatch get(String id){
		return (ErpReportdetailBatch)this.findById(ErpReportdetailBatch.class, id);
	}
	
}
