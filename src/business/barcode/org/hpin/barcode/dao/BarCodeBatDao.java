package org.hpin.barcode.dao;

import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

@Repository()
public class BarCodeBatDao extends BaseDao{
	public int findBatdateNextNum(String batdate) {
		String queryString = "select count(*) from ERP_BAR_CODE_BAT where  BAT_NO LIKE '"+batdate+"__'";
		int count=this.getJdbcTemplate().queryForInt(queryString);
		return count+1;
	}
	
	public void updateStatus(int statuts,String code){
		String sql="update erp_bar_code_detail set is_effective=? where bar_code=? ";
		this.getJdbcTemplate().update(sql, new Object[]{statuts,code});
	}
	
	public void updateBarCodeStatus(int status,String batNo){
		String sql="update erp_bar_code_detail set is_effective=? where bat_no=? ";
		this.getJdbcTemplate().update(sql, new Object[]{status,batNo});
	}
}
