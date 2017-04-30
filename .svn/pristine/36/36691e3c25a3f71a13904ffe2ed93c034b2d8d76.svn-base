package org.hpin.warehouse.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.ErpApplicationDetail;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
/**
 * 
 * @description: 基因物品申请Dao
 * create by henry.xu 2016年10月10日
 */
@Repository
public class ErpApplicationDetailDao extends BaseDao{
	
	public void saveErpApplicationDetail(ErpApplicationDetail entity) throws Exception {
		this.getHibernateTemplate().save(entity);
	}
	
	public void updateErpApplicationDetail(ErpApplicationDetail entity) throws Exception {
		this.getHibernateTemplate().update(entity);
	}
	
	/**
	 * 先删除原有老数据;此处由于明细数据没有处理,所以物理删除;没有使用逻辑删除;
	 * @throws Exception
	 */
	public void deleteApplicationDetail(String applicationID) throws Exception{
		String sql = "delete from erp_application_detail where application_id = '" + applicationID + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 根据applicationId查询明细集合;
	 * @param id
	 * @param sql 明细查询;
	 * @return
	 */
	public List<ErpApplicationDetail> findObjectsByApplicationId(String applicationID) {
		StringBuilder sql = dealJdbcSql();
		sql.append(" and detail.application_id = '").append(applicationID).append("'");
		sql.append(" order by comb.product_combo_name ");
		BeanPropertyRowMapper<ErpApplicationDetail> rowMapper = new BeanPropertyRowMapper<ErpApplicationDetail>(ErpApplicationDetail.class);
		return this.getJdbcTemplate().query(sql.toString(), rowMapper);
	}
	
	/**
	 * 处理jdbcSql方法;
	 * @return
	 */
	public StringBuilder dealJdbcSql() {
		StringBuilder jdbcsql = new StringBuilder("select " +
				"detail.id, " +
				"detail.application_id applicationId, " +
				"detail.PRODUCT_ID productId, " +
				"detail.PRODUCT_NAME productName, " +
				"detail.STANDARD standard, " +
				"detail.APPLICATION_COUNT applicationCount, " +
				"detail.SEND_COUNT sendCount, " +
				"detail.REMARK remark, " +
				"detail.STATUS status, " +
				"detail.CREATE_TIME createTime, " +
				"detail.CREATE_USER_ID createUserId, " +
				"detail.UPDATE_TIME updateTime, " +
				"detail.UPDATE_USER_ID updateUserId, " +
				"detail.IS_DELETED isDeleted, " +
				"dict.DICTNAME productTypeName, " +
				"detail.proCombo_ID proComboId, " +
				"comb.product_combo_name productComboName " +
				"from " +
				"erp_application_detail detail " + 
				"inner JOIN erp_product pro on pro.id = detail.PRODUCT_ID " + 
				"inner join SYS_DICTTYPE dict on pro.PRODUCT_TYPE = dict.DICTCODE " + 
				"left join erp_product_combo comb on comb.id = detail.proCombo_id " + 
				"WHERE detail.IS_DELETED = 0 ");
		
		return jdbcsql;
	}

	/**
	 * 根据产品Id查看申请明细中存在
	 * @param idSql
	 * @return
	 */
	public int findAppDetailUseProductCount(String idSql) {
		String sql = "select count(1) from ERP_APPLICATION_DETAIL where IS_DELETED = 1 and PRODUCT_ID in ("+idSql+")";
		return this.getJdbcTemplate().queryForInt(sql);
	}

	/**
	 * 根据申请id查询该明细对应已发货完成的数据
	 * 完成规则: 申请数量== 发货数量;
	 * @param applicationId
	 * @return
	 */
	public List<String> findDetailIdByApplicationId(String applicationId) {
		String sql = "select id from ERP_APPLICATION_DETAIL where APPLICATION_ID = '"+applicationId+"' and application_count = send_count";
		List<String> list = new ArrayList<String>();
		List<Map<String, Object>> mapList = this.getJdbcTemplate().queryForList(sql);
		if(mapList != null && mapList.size() > 0) {
			for (Map<String, Object> map : mapList) {
				list.add((String)map.get("id"));
			}
		}
		return list;
	}
}
