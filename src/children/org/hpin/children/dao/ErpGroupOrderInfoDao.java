package org.hpin.children.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.springframework.stereotype.Repository;

@Repository()
@SuppressWarnings({ "rawtypes" })
public class ErpGroupOrderInfoDao extends BaseDao{

    /**
     * @param page
     * @param searchMap
     * @return 团购订单信息
     */
	public List<Map<String, Object>> findGroupOrderInfo(Page page, Map<String,Object> map) {
		
		StringBuilder resultSql = new StringBuilder();
		resultSql.append("SELECT egoi.id,egoi.orderno,egoi.orderdate,egoi.name,egoi.phone,egoi.type,T.exam_num ,egoi.price "
				+ "FROM erp_group_order_info egoi LEFT JOIN"
				+"(SELECT sum(b.combonum) exam_num,b.orderno "
				+ "FROM erp_group_order_info a,erp_group_order_combo b WHERE a.orderno=b.orderno GROUP BY b.orderno)t ON egoi.orderno=t.orderno "
				+ "where 1=1");
    	for(String key: map.keySet()){
    		if (isEndLT(key)) {
    			resultSql.append(" and to_char(egoi.orderDate,'yyyy-mm-dd')>='"+map.get(key)+"'");
			}else if (isEndRT(key)) {
				resultSql.append(" and to_char(egoi.orderDate,'yyyy-mm-dd')>='"+map.get(key)+"'");
			} else {
				resultSql.append( " and egoi."+getSearchMap(key)+" like '%"+map.get(key)+"%'");
			}
    	}
    	resultSql.append(" order by egoi.orderdate desc");
    	return getPageList(page, resultSql.toString());
    }
	
	public String getSql(Page page, Map<String,Object> map) throws SQLException{
		StringBuilder sqlBuilder=new StringBuilder();	
    	sqlBuilder.append("SELECT egoi.id,egoi.orderno,egoi.orderdate,egoi.name,egoi.phone,egoi.type,T.exam_num ,egoi.price "
				+ "FROM erp_group_order_info egoi LEFT JOIN"
				+"(SELECT sum(b.combonum) exam_num,b.orderno "
				+ "FROM erp_group_order_info a,erp_group_order_combo b WHERE a.orderno=b.orderno GROUP BY b.orderno)t ON egoi.orderno=t.orderno "
				+ "where 1=1");
    	for(String key: map.keySet()){
    		if (isEndLT(key)) {
    			sqlBuilder.append(" and to_char(orderDate,'yyyy-mm-dd')>='"+getSearchMap(key)+"'");
			}else if (isEndRT(key)) {
				sqlBuilder.append(" and to_char(orderDate,'yyyy-mm-dd')>='"+getSearchMap(key)+"'");
			} else {
				sqlBuilder.append( "and phone='"+getSearchMap(key)+"'");
			}
    	}
    	
		return sqlBuilder.toString();
	}

	public List test(Page page, Map map) {
		String date = "orderDate";
		String [] selectSql = new String[]{"*","erp_group_order_info"};
		String sql = "SELECT ROWNUM rn,egoi.id,egoi.orderno,egoi.orderdate,egoi.name,egoi.phone,egoi.type,T.exam_num ,egoi.price "
				+ "FROM erp_group_order_info egoi LEFT JOIN"
				+"(SELECT sum(b.combonum) exam_num,b.orderno "
				+ "FROM erp_group_order_info a,erp_group_order_combo b WHERE a.orderno=b.orderno GROUP BY b.orderno)t ON egoi.orderno=t.orderno where 1=1";
		return getPageList(page, sql);
	}

	public int insertCustomerOrderInfo(String sql, Object[] params, int[] types) {
		return super.getJdbcTemplate().update(sql, params, types);
	}

	public List findByPage(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from ErpOrderInfo where 1=1");
		List valueList = new ArrayList();
	    OrmConverter.assemblyQuery(query, searchMap, valueList);
	    return super.findByHql(page, query, valueList);
	}

	public List<Map<String, Object>> getSelectedInput() {
		String sql = "select distinct setmealname from erp_order_info where setmealname like '%儿童%' order by setmealname";
		return this.getJdbcTemplate().queryForList(sql);
	}

	
}
