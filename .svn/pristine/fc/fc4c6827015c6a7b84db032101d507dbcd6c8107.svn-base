package org.hpin.reportdetail.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.reportdetail.entity.ErpReportdetailPDF;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpReportdetailPDFDao extends BaseDao {
	public List<ErpReportdetailPDF> getAll(){
		String hql="from ErpReportdetailPDF";
		return this.getHibernateTemplate().find(hql);
	}
	public void pdfToTable(Object obj[]){
		String sql="insert into ERP_REPORTDETAIL_PDF(id,batchno,filepath,ismatch,filename,code) values(?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, obj);
	}
	
}
