package org.hpin.reportdetail.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.reportdetail.entity.ErpReportdetail;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpReportdetailDao extends BaseDao {
	public List<ErpReportdetail> getAll(){
		String hql="from ErpReportdetail";
		return this.getHibernateTemplate().find(hql);
	}
	public void sheetToTable(Object obj[]){
		String sql="insert into ERP_REPORTDETAIL(id,code,name,sex,age,phone,branchCompany,project,sampleType,salesMan,entering,institution,samplingDate,collectionDate,simpleStatus,page) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?)";
		this.getJdbcTemplate().update(sql, obj);
	}
	
}
