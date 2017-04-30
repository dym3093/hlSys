package org.hpin.children.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.springframework.stereotype.Repository;

import com.sun.istack.internal.FinalArrayList;

@Repository()
public class ErpOrderInfoDao extends BaseDao{

	public List findByPage(Page page,Map searchMap) {
		StringBuffer query = new StringBuffer(" from ErpOrderInfo where 1=1");
		List valueList = new ArrayList();
	    OrmConverter.assemblyQuery(query, searchMap, valueList);
	    return super.findByHql(page, query, valueList);
	}
	
	public List<Map<String, Object>> getSelectedInput() {
		String sql = "select distinct setmealname from erp_order_info where setmealname like '%儿童%' order by setmealname";
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<Map<String, Object>> getAllComboInfo() {
		String sql = "select distinct combo from erp_company_combo_price where combo like '%儿童%' order by combo";
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<Map<String, Object>> getCodeList() {
		String sql ="";
		return null;
	}

	public void deleteOrderInfo(String sql) {
		this.getJdbcTemplate().execute(sql);
	}

	public int updateOrderInfo(StringBuilder sql) {
		return this.getJdbcTemplate().update(sql.toString());
	}

	public List<Map<String, Object>> getOrderInfoById(String sql) {
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<Map<String, Object>>  getCustomerSampleBox(String sql) {
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<Map<String, Object>> getCustomerOrderInfo(String sql) {
		return this.getJdbcTemplate().queryForList(sql);
	}

	public int updateCustomerCode(String sql) {
		return this.getJdbcTemplate().update(sql);
	}

	/**
	 * @param id
	 * @return 是否有该条数据
	 */
	public int getMailInfoSize(String id,String type) {
		String sql = "select count(1) from erp_customer_mail_info where orderid='"+id+"' and express_type='"+type+"'";
		return this.getJdbcTemplate().queryForInt(sql);
	}

	/**
	 * @param sql
	 * @return 更新邮寄信息
	 */
	public int updateMailInfo(String sql) {
		return this.getJdbcTemplate().update(sql);
	}

	/**
	 * @param sql
	 * @param params
	 * @param types
	 * @return 插入邮寄信息
	 */
	public int insertMailInfo(String sql, Object[] params, int[] types) {
		return this.getJdbcTemplate().update(sql,params,types);
	}

	public int updateReportStatus(String sql) {
		return this.getJdbcTemplate().update(sql);
	}

	public String getMailId(String id, String expressType) {
		String sql = "select id from erp_customer_mail_info where orderid='"+id+"' and express_type='"+expressType+"'";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
		if (list.size()==1) {
			return list.get(0).get("ID").toString();
		}else {
			return null;
		}
	}

}
