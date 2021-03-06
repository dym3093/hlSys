package org.hpin.warehouse.dao;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.ErpStoregeIn;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * @description: 入库Dao 
 * create by henry.xu 2016年10月9日
 */
@Repository
public class ErpStoregeInDao extends BaseDao {
	

	public void updateUseableQuantity(Integer quantity, String id) throws Exception {
		String sql = "update erp_storege_in set USEABLE_QUANTITY = USEABLE_QUANTITY + " + quantity + "where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 修改数据;
	 * @param stroregeIn
	 */
	public void updateStroregeIn(ErpStoregeIn stroregeIn) {
		this.getHibernateTemplate().update(stroregeIn);
		
	}

	/**
	 * 查找数据;
	 * @param id
	 * @return
	 */
	public ErpStoregeIn findStoregeInById(String id) {
		StringBuilder jdbcsql = dealJdbcSql();
		jdbcsql.append(" and stoIn.id = '").append(id).append("'");
		BeanPropertyRowMapper<ErpStoregeIn> rowMapper = new BeanPropertyRowMapper<ErpStoregeIn>(ErpStoregeIn.class);
		return this.getJdbcTemplate().queryForObject(jdbcsql.toString(), rowMapper);
	}
	
	/**
	 * 数据保存
	 * @param stroregeIn
	 */
	public void save(ErpStoregeIn stroregeIn) {
		this.getHibernateTemplate().save(stroregeIn);
	}
	
	
	public StringBuilder dealJdbcSql() {
		StringBuilder jdbcsql = new StringBuilder("select " +
				"stoIn.Id id, " +
				"stoIn.Storege_Id storegeId, " +
				"stoWar.Name storegeName, " +
				"stoIn.Product_Id productId, " +
				"pro.product_name productName, " +
				"stoIn.product_type productType, " +
				"stoIn.PRICE price, " +
				"stoIn.QUANTITY quantity, " +
				"stoIn.AMOUNT amount, " +
				"stoIn.STANDARD standard, " +
				"stoIn.DESCRIBE describe, " +
				"stoIn.USEABLE_QUANTITY useableQuantity, " +
				"stoIn.CARD_NUM cardNum, " +
				"stoIn.CARD_START cardStart , " +
				"stoIn.CARD_END cardEnd, " +
				"stoIn.IS_DELETED isDeleted, " +
				"stoIn.USER_CARD_NUM userCardNum, " +
				"stoIn.Create_User_Id createUserId, " +
				"stoIn.Create_Time createTime, " +
				"stoIn.Update_User_Id udpateUserId, " +
				"stoIn.Update_Time udpateTime, " +
				"stoIn.STOREGE_STATUS storegeStatus, " +  //add by henry.xu 2016-03-20
				"(select sup.supplier_name from erp_supplier sup where sup.id=stoIn.supplier_id and sup.is_Deleted=0) as supplierName, "+	//2017-2-8 add by leslieTong. 增加显示 字段【供货商】
				// add by Damian 2016-02-07 start
				"stoIn.SUPPLIER_ID supplierId, " +
				"stoIn.SERIAL_NO serialNo, " +
				"stoIn.SETTLE_STATUS settleStatus, " +
				"stoIn.SERIAL_NO_OA serialNoOA " +
				// add by Damian 2016-02-07 end

				"from  " +
				"erp_storege_in stoIn " +
				"left join store_warehouse stoWar on stoIn.Storege_Id = stoWar.Id " +
				"left join erp_product pro on pro.id = stoIn.Product_Id " + 
				"where stoIn.is_deleted = 0 ");
		return jdbcsql;
	}
	
	public StringBuilder dealJdbcSqlByParams(Map<String, String> params) {
		StringBuilder jdbcsql = dealJdbcSql();
		
		if(params != null) {
			String storegeName = params.get("storegeName");
			String productName = params.get("productName");
			
			if(StringUtils.isNotEmpty(storegeName)) {
				jdbcsql.append(" and stoWar.Name like '%").append(storegeName.trim()).append("%' ");
			}
			
			if(StringUtils.isNotEmpty(productName)) {
				jdbcsql.append(" and pro.product_name like '%").append(productName.trim()).append("%' ");
			}
			
			String startDate = params.get("startDate");
			String endDate = params.get("endDate");
			
			if(StringUtils.isNotEmpty(startDate)) {
				jdbcsql.append(" and stoIn.Create_Time >= to_date('").append(startDate).append("', 'yyyy-MM-dd') ");
			}
			
			if(StringUtils.isNotEmpty(endDate)) {
				jdbcsql.append(" and stoIn.Create_Time < to_date('").append(endDate).append("', 'yyyy-MM-dd')+1 ");
			}
		}
		//排序
		jdbcsql.append(" order by stoIn.Create_Time desc, stoIn.Id desc ");
		
		return jdbcsql;
	}

	/**
	 * 根据产品id查询入库中包含该数据数量;
	 * @param ids
	 * @return
	 */
	public int findStoregeInUseProductCount(String ids) {
		String sql = "select count(1) from erp_storege_in where IS_DELETED = 0 and product_id in ("+ids+")";
		return this.getJdbcTemplate().queryForInt(sql);
	}

}
