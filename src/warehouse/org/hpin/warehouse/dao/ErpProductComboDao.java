package org.hpin.warehouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.ErpProComboProduct;
import org.hpin.warehouse.entity.ErpProductCombo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ErpProductComboDao extends BaseDao {

	
	public void saveProCombo(ErpProductCombo proCombo) {
		this.getHibernateTemplate().save(proCombo);
	}
	
	public void updateProCombo(ErpProductCombo proCombo) {
		this.getHibernateTemplate().update(proCombo);
	}

	/**
	 * 保存中间表
	 * @param erpProComboProduct
	 */
	public void saveProComboProduct(ErpProComboProduct erpProComboProduct) {
		this.getHibernateTemplate().save(erpProComboProduct);
		
	}
	/**
	 * 删除中间表
	 * @param erpProComboProduct
	 */
	public void deleteProComboProduct(String productComboId) {
		String sql = "delete from ERP_PROCOMBO_PRODUCT where PRODUCTCOMBO_ID='"+productComboId+"'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 查询列表sql处理;
	 */
	public StringBuilder dealErpProComboSql() {
		return new StringBuilder("select  " +
			"id,  " +
			"PRODUCT_COMBO_NAME productComboName,  " +
			"declare, " +
			"create_user_id createUserId, " +
			"create_time createTime, " +
			"update_user_id updateUserId, " +
			"update_time updateTime, " +
			"remark, " +
			"is_deleted idDeleted, " +
			"is_close isClose, " +
			"combo_type comboType " +
			"from erp_product_combo where IS_DELETED = '0' ");
		
	}
	
	/**
	 * 产品套餐列表参数处理;
	 * @param params
	 * @return
	 */
	public StringBuilder dealErpProComboSqlByParam(Map<String, String> params) {
		StringBuilder jdbcSql = this.dealErpProComboSql();
		//参数处理;
		if(params != null) {
			String productComboName = params.get("productComboName");
			String isClose = params.get("isClose");
			String comboType = params.get("comboType");
			
			String hiddenName = params.get("hiddenName");
			
			if(StringUtils.isNotEmpty(productComboName)) {
				jdbcSql.append(" and PRODUCT_COMBO_NAME like '%").append(productComboName.trim()).append("%' ");
			}
			
			if(StringUtils.isNotEmpty(comboType)) {
				jdbcSql.append(" and COMBO_TYPE = '").append(comboType).append("' ");
			}
			
			if(StringUtils.isNotEmpty(isClose)) {
				jdbcSql.append(" and is_close = '").append(isClose).append("'");
			}
			
			//默认为阳关保险公司的;
			if(StringUtils.isNotEmpty(hiddenName)) {
				//如果有其他公司的 显示其他简称;
				if("yg".equals(hiddenName)) {
					hiddenName = "阳光";
				}
				
				jdbcSql.append(" and PRODUCT_COMBO_NAME like '").append(hiddenName).append("%' ");
			}
		}
		
		return jdbcSql;
	}
	
	/**
	 * 根据主表数据id查询明细列表显示信息
	 * @param id
	 * @return
	 */
	public StringBuilder dealErpProComboProductSql(String id) {
		return new StringBuilder("select "+
			"product.ID, "+
			"product.PRODUCT_NAME productName, "+
			"dict.DICTNAME productTypeName, "+
			"product.standard, "+
			"product.image_path imagePath " +
			"from  "+
			"erp_product_combo comb "+
			"inner join ERP_PROCOMBO_PRODUCT prod on comb.id = prod.PRODUCTCOMBO_ID "+
			"inner join erp_product product on product.ID = prod.PRODUCT_ID "+
			"inner join SYS_DICTTYPE dict on product.PRODUCT_TYPE = dict.DICTCODE where comb.id='").append(id).append("'");
	}

	public List<ErpProComboProduct> findErpProComboProductByIds(String sqlIn) {
		String sql = "select id, productCombo_id proComboId, product_id productId from ERP_PROCOMBO_PRODUCT where ProductCombo_id in ("+sqlIn+")";
		BeanPropertyRowMapper<ErpProComboProduct> rowMapper = new BeanPropertyRowMapper<ErpProComboProduct>(ErpProComboProduct.class);
		return this.getJdbcTemplate().query(sql, rowMapper);
	}
}
