package org.hpin.events.dao;

import java.util.ArrayList;
import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.ErpCustomer;
import org.springframework.stereotype.Repository;

/**
 * 条形码主表管理;
 * @description: 
 * create by henry.xu 2016年11月30日
 */
@Repository()
public class ErpBarCodeDetailDao extends BaseDao {
	
	/**
	 * 根据条形码修改条码明细表中是否使用状态为已使用;
	 * @param barcode
	 * @return
	 */
	public int updateCodeDetail(List<ErpCustomer> customers) throws Exception {
		
		StringBuilder updateSql = new StringBuilder("update erp_bar_code_detail set IS_USED='1' where BARCODE in (");
		if(null == customers || customers.size() <= 0) {
			return 0;
		}
		int leg = customers.size();
		List<String> params = new ArrayList<String> ();
		for(int i=0; i < leg; i++) {
			if(i==0) {
				updateSql.append("?");
			} else {
				updateSql.append(", ?");
			}
			params.add(customers.get(i).getCode());
		}
		updateSql.append(") ");
		
		return this.getJdbcTemplate().update(updateSql.toString(), params.toArray()); 
	}
}
