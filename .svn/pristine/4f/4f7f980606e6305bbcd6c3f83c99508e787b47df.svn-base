package org.hpin.warehouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.ErpApplicationDetail;
import org.hpin.warehouse.entity.ErpStoregeOut;
import org.hpin.warehouse.entity.ErpStoregeReback;
import org.hpin.warehouse.entity.vo.StoregeInOutVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * @description: 基因物品发货Dao
 * create by henry.xu 2016年10月12日
 */
@Repository
public class ErpStoregeOutDao extends BaseDao {
	
	/**
	 * 修改数据;
	 * @param storegeOut
	 * @throws Exception
	 */
	public void updateStoregeOut(ErpStoregeOut storegeOut) throws Exception {
		this.getHibernateTemplate().update(storegeOut);
	}

	
	/**
	 * 根据出货数据Id查询退货数量sum;
	 * @param id
	 * @return
	 */
	public Integer findStoregeRebackNumByOutId(String id) {
		String sql = "select sum(quantity) from ERP_STOREGE_REBACK where STOREGE_OUT_ID = '"+id+"' ";
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	/**
	 * 保存退库数据;
	 * @param storegeReback
	 * @throws Exception
	 */
	public void saveStoregeReback(ErpStoregeReback storegeReback) throws Exception {
		this.getHibernateTemplate().save(storegeReback);
		
	}

	/**
	 * 根据发货Id查询数据;
	 * @param id
	 * @return
	 */
	public StoregeInOutVo findStoregeOutById(String id) {
		StringBuilder jdbcSql = dealJdbcStoregeOut();
		jdbcSql.append(" and stOut.id ='").append(id).append("' ");
		BeanPropertyRowMapper<StoregeInOutVo> rowMapper = new BeanPropertyRowMapper<StoregeInOutVo>(StoregeInOutVo.class);
		return this.getJdbcTemplate().queryForObject(jdbcSql.toString(), rowMapper);
	}
	
	/**
	 * 修改申请主表转态
	 * ERP_APPLICATION
	 * @param applicationId
	 * @param status 状态 0待发货, 1部分发货,2已发货,3处理中;
	 * @param userId 处理人Id
	 */
	public int updateApplicationStatusById(String applicationId, String status, String userId) {
		StringBuilder jdbcsql = new StringBuilder("update ERP_APPLICATION set ");
		if(StringUtils.isNotEmpty(userId)) {
			jdbcsql.append(" deal_user_id='").append(userId).append("', ");
		}
		jdbcsql.append(" status = '").append(status).append("' where id='").append(applicationId).append("' ");
		
		return this.getJdbcTemplate().update(jdbcsql.toString());
	}

	/**
	 * 将当前申请单中此产品的发货数量重新赋值
	 * ERP_APPLICATION_DETAIL
	 * @param detailId
	 * @param quantity
	 * @return
	 */
	public int updateApplicationDetailSendCount(String detailId, Integer quantity) {
		/*
		 * 将当前申请单中此产品的发货数量重新赋值，规则为：申请单中此产品的发货数量+当前页面中填写的发货数量,
		 *  判断:当前申请单中此产品的申请数量和发货数量相等，则状态为0：已发货，如果发货数量小于申请数量，则状态为1：部分发货
		 */
		//先获取明细中的申请数量; 获取已发货数量;
		ErpApplicationDetail detail = (ErpApplicationDetail)this.findById(ErpApplicationDetail.class, detailId);
		int applyCount = detail.getApplicationCount();
		int sendCount = detail.getSendCount()==null ? 0:  detail.getSendCount();
		String status = "1"; //状态：0已发货  1部分发货
		//已发货数量+ quantity == 申请数量   
		if(applyCount == sendCount + quantity) {
			status = "0";
		}
		//修改发送数量, 以及状态是否发货完成;
		String sql = "update ERP_APPLICATION_DETAIL set SEND_COUNT = " + (sendCount + quantity) + ", STATUS="+status+" where id='"+detailId+"'";
		return this.getJdbcTemplate().update(sql);
		
	}
	
	/**
	 * 修改入库中的真正可用卡号;
	 * ERP_STOREGE_IN
	 * @param storegeInId
	 * @param card
	 */
	public int updateStoregeInUserCardNum(String storegeInId, String card) {
		String sql = "update ERP_STOREGE_IN set USER_CARD_NUM = '"+card+"' where id='"+storegeInId+"'";
		return this.getJdbcTemplate().update(sql);
	}


	
	/**
	 * 冲减.使用jdbc.update
	 * ERP_STOREGE_IN
	 * @param storegeInId
	 * @param quantity
	 */
	public int writeDownStoregeInById(String storegeInId, Integer quantity) {
		String sql = "update ERP_STOREGE_IN set USEABLE_QUANTITY = USEABLE_QUANTITY - "+quantity+" where id='"+storegeInId+"'";
		return this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 保存
	 * @param storegeOut
	 */
	public void saveStoregeOut(ErpStoregeOut storegeOut) {
		this.getHibernateTemplate().save(storegeOut);
	}
	
	/**
	 * 根据基因申请Id获取对应的明细对应的入库信息;
	 * 规则: 如果入库没有则不显示; 如果一个产品多条入库,则对应多个入库信息; //库存为当前登陆人创建;
	 * @param applicationId
	 * @return
	 */
	public List<StoregeInOutVo> findObjectsByApplicationId(String applicationId, String ids, String currentUserId) {
		StringBuilder jdbcsql = dealJdbcSql();
		if(StringUtils.isNotEmpty(ids)) {
			jdbcsql.append(" and detail.id not in (").append(ids).append(") ");
		}
		jdbcsql.append(" and detail.APPLICATION_ID = '").append(applicationId).append("' ");
		jdbcsql.append(" and storege.useable_quantity > 0 "); //只有库存有效数量大于零的显示;
		jdbcsql.append(" and stoWar.CREATE_USER_ID='").append(currentUserId).append("' ");
		BeanPropertyRowMapper<StoregeInOutVo> rowMapper = new BeanPropertyRowMapper<StoregeInOutVo>(StoregeInOutVo.class);
		return this.getJdbcTemplate().query(jdbcsql.toString(), rowMapper);
	}
	/**
	 * 根据基因申请Id获取对应的明细对应的发货信息;
	 * @param applicationId
	 * @return
	 */
	public List<StoregeInOutVo> findStoregeOutByApplicationId(String applicationId) {
		StringBuilder jdbcsql = dealjdbcSqlStoregeOut();
		jdbcsql.append(" and stout.APPLICATION_ID = '").append(applicationId).append("'");
		BeanPropertyRowMapper<StoregeInOutVo> rowMapper = new BeanPropertyRowMapper<StoregeInOutVo>(StoregeInOutVo.class);
		return this.getJdbcTemplate().query(jdbcsql.toString(), rowMapper);
	}
	
	
	/**
	 * 根据申请id关联申请明细关联入库,
	 * 此处查询作为发货编辑界面数据;
	 * @return
	 */
	public StringBuilder dealJdbcSql() {
		StringBuilder jdbcsql = new StringBuilder("select " +
				  "detail.id, " +
				  "detail.APPLICATION_ID applicationId, " +
				  "storege.storege_id as storegeId, " +
				  "stoWar.NAME as storegeName, " +
				  "detail.PRODUCT_ID productId, " +
				  "pro.product_name as productName, " +
				  "storege.id as storegeInId, " +
				  "storege.price as price, " +
				  "storege.standard as standard, " +
				  "storege.describe as describe, " +
				  "storege.user_card_num as userCardNum, " +
				  "storege.useable_quantity as useableQuantity, " +
				  "storege.product_type as productType,  " +
				  "dict.DICTNAME as productTypeName  " +

				"from " +
				  "erp_application_detail detail " +
				"inner join erp_storege_in storege on storege.PRODUCT_ID = detail.PRODUCT_ID " +
				"left join SYS_DICTTYPE dict on storege.PRODUCT_TYPE = dict.DICTCODE " +
				"left join erp_product pro on pro.id = detail.PRODUCT_ID " +
				"left join STORE_WAREHOUSE stoWar on stoWar.id = storege.STOREGE_ID " +
				"where  " +
				  "detail.IS_DELETED = 0 ");
		return jdbcsql;
	}

	/**
	 * 根据申请id关联申请明细发货关联入库,
	 * 此处查询作为浏览界面数据;
	 * @return
	 */
	public StringBuilder dealjdbcSqlStoregeOut() {
		StringBuilder jdbcsql = new StringBuilder("select " +
				  "stout.id as id, " +
				  "stout.storege_in_id as storegeInId, " +
				  "stout.application_id as applicationId, " +
				  "stout.product_id as productId, " +
				  "stout.product_name as productName, " +
				  "stout.standard as standard, " +
				  "stout.price as price, " +
				  "stout.quantity as quantity, " +
				  "stout.amount as amount, " +
				  "stout.express_money as expressMoney, " +
				  "stout.cost as cost, " +
				  "stout.card_end as cardEnd, " +
				  "stout.card_start as cardStart, " +
				  "stout.express_no as expressNo, " +
				  "stout.express_name as expressName, " +
				  "storegeIn.product_type as productType,   " +
				  "dict.DICTNAME as productTypeName, " +
				  "storegeIn.storege_id as storegeId, " +
				  "stoWar.NAME as storegeName " +
				"from  " +
				"erp_storege_out stout " +
				"inner join erp_storege_in storegeIn on storegeIn.id = stout.storege_in_id " +
				"left join STORE_WAREHOUSE stoWar on stoWar.id = storegeIn.STOREGE_ID  " +
				"left join SYS_DICTTYPE dict on storegeIn.PRODUCT_TYPE = dict.DICTCODE  " +
				"where  " +
				"stout.IS_DELETED = 0 " );
		return jdbcsql;
	}
	
	/**
	 * 出库信息查询sql 参数处理
	 * @param params
	 * @return
	 */
	public StringBuilder dealJdbcStoregeOutParams(Map<String, String> params) {
		
		StringBuilder jdbcsql = this.dealJdbcStoregeOut();
		
		if(params != null) {
			String storegeName = params.get("storegeName");
			String applicationNo = params.get("applicationNo");
			String productName = params.get("productName");
			String applyUserName = params.get("applyUserName");
			
			if(StringUtils.isNotEmpty(storegeName)) {
				jdbcsql.append(" and stoWar.name like '%").append(storegeName.trim()).append("%'");
			}
			if(StringUtils.isNotEmpty(applicationNo)) {
				jdbcsql.append(" and appc.application_no like '%").append(applicationNo.trim()).append("%'");
			}
			if(StringUtils.isNotEmpty(productName)) {
				jdbcsql.append(" and stOut.product_name like '%").append(productName.trim()).append("%'");
			}
			if(StringUtils.isNotEmpty(applyUserName)) {
				jdbcsql.append(" and umser.USER_NAME like '%").append(applyUserName.trim()).append("%'");
			}
			
			String startDate = params.get("startDate");
			String endDate = params.get("endDate");
			
			if(StringUtils.isNotEmpty(startDate)) {
				jdbcsql.append(" and stOut.create_time >= to_date('").append(startDate).append("', 'yyyy-MM-dd') ");
			}

			if(StringUtils.isNotEmpty(endDate)) {
				jdbcsql.append(" and stOut.create_time < to_date('").append(endDate).append("', 'yyyy-MM-dd') +1 ");
			}
		}
		
		jdbcsql.append(" ORDER BY stOut.create_time desc, stOut.ID desc ");
		
		return jdbcsql;
	}

	
	/**
	 * 出库信息查询sql
	 * @return
	 */
	public StringBuilder dealJdbcStoregeOut() {
		StringBuilder jdbcsql = new StringBuilder("select " +
				  "stOut.id as id, " +
				  "stOut.storege_in_id as storegeInId, " +
				  "stoWar.name storegeName, " +
				  "stOut.application_id as applicationId, " +
				  "stOut.product_id as productId, " +
				  "stOut.product_name as productName, " +
				  "stOut.standard as standard, " +
				  "stOut.price as price, " +
				  "stOut.quantity as quantity, " +
				  "stOut.amount as amount, " +
				  "stOut.express_money as expressMoney, " +
				  "stOut.cost as cost, " +
				  "stOut.remark as remark, " +
				  "stOut.create_user_id as createUserId, " +
				  "stOut.create_time as createTime, " +
				  "stOut.update_user_id as updateUserId, " +
				  "stOut.update_time as updateTime, " +
				  "stOut.is_deleted as isDeleted, " +
				  "stOut.card_end as cardEnd, " +
				  "stOut.card_start as cardStart, " +
				  "stOut.express_no as expressNo, " +
				  "stOut.express_name as expressName, " +
				  "appc.PROJECT_CODE as projectCode, " +
				  "appc.PROJECT_NAME as projectName, " +
				  "appc.PROJECT_OWNER as projectOwner, " +
				  "umser.USER_NAME as createUserName, " + //申请人
				  "appc.application_no as applicationNo " + //申请号
				"from " +
				"erp_storege_out stOut " +
				"left join erp_storege_in stoIn on stoIn.id = stOut.storege_in_id " +
				"left join STORE_WAREHOUSE stoWar on stoIn.Storege_Id = stoWar.ID " +
				"left join erp_application appc on appc.id = stOut.application_id " +
				"left join um_user umser on umser.id = appc.create_user_id " +
				"where " +
				"stOut.is_deleted = 0 ");
		return jdbcsql;
	}

}
