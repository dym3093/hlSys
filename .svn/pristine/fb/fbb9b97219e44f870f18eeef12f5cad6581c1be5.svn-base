package org.hpin.warehouse.dao;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;


/**
 * 退货DAO
 * @author machuan
 * @date 2017年3月21日
 */
@Repository
public class ErpStoregeReturnDao extends BaseDao {
	
	public StringBuilder dealJdbcSql() {
		StringBuilder jdbcsql = new StringBuilder("select " +
				   "esr.id, "+
			       "esr.project_code projectCode, "+
			       "esr.project_name projectName, "+
			       "esr.project_owner projectowner, "+
			       "sw.name storege, "+
			       "ep.product_name product, "+
			       "esr.unit_price unitPrice, "+
			       "esr.return_number returnNumber, "+
			       "esr.cost, "+
			       "esr.express_cost expressCost, "+
			       "esr.create_time createTime "+
				   "from erp_storege_return esr  "+
				   "left join erp_product ep on esr.product_id=ep.id "+
				   "left join store_warehouse sw on esr.storege_id=sw.id "+
			       "where esr.is_deleted = 0 ");
		return jdbcsql;
	}
	
	public StringBuilder dealJdbcSqlByParams(Map<String, String> params) {
		StringBuilder jdbcsql = dealJdbcSql();
		
		if(params != null) {
			
			String startDate = params.get("startDate");
			String endDate = params.get("endDate");
			
			if(StringUtils.isNotEmpty(startDate)) {
				jdbcsql.append(" and esr.create_Time >= to_date('").append(startDate).append("', 'yyyy-MM-dd') ");
			}
			
			if(StringUtils.isNotEmpty(endDate)) {
				jdbcsql.append(" and esr.create_Time < to_date('").append(endDate).append("', 'yyyy-MM-dd')+1 ");
			}
		}
		//排序
		jdbcsql.append(" order by esr.create_Time desc, esr.Id desc ");
		
		return jdbcsql;
	}
}
