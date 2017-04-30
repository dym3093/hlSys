package org.hpin.barcode.dao;

import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

@Repository()
public class BarCodeIssueDao extends BaseDao{
	public int findStockNums() {
		String queryString = "select (t1.f1-t2.f2) as count from (select  '1' as s1,nvl(sum(bat_actual_count),0)  as f1 from erp_bar_code_bat where status=3 ) t1,"
		+"(select '1' as s1,nvl(sum(BARCODE_COUNT),0) as f2 from erp_bar_code_issue where is_deleted=0 and status!=0) t2  where t1.s1=t2.s1";
		int count=this.getJdbcTemplate().queryForInt(queryString);
		System.out.println("库存量："+count);
		return count;
	}
}
