package cn.yuanmeng.labelprint.dao.impl;
import java.util.UUID;

import cn.yuanmeng.labelprint.dao.BaseDao;
import cn.yuanmeng.labelprint.dao.ReportDetailBatchDao;
import cn.yuanmeng.labelprint.entity.ReportDetailBatch;

public class ReportDetailBatchDaoImpl extends BaseDao implements
		ReportDetailBatchDao {

	@Override
	public void add(ReportDetailBatch r) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO erp_reportdetail_batch(ID, batchno, batchdate, xlscodecount, pdffilecount, xlspdfcount, codenoreportfilecount, reportfilenocodecount,ismatch) "
				+ " VALUES(?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?)";
		Object []obj=new Object[]{UUID.randomUUID().toString().replaceAll("-", ""),r.getBatchNo(),r.getBatchDate(),r.getXlsCodeCount(),r.getPdfFileCount(),r.getXlsPdfCount(),r.getCodenoReportFileCount(),r.getReportFileNocodeCount(),r.getIsmatch()};
		this.updateOrInsert(sql, obj);
	}
}
