package org.hpin.warehouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.ErpApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
/**
 * 
 * @description: 基因物品申请Dao
 * create by henry.xu 2016年10月10日
 */
@Repository
public class ErpApplicationDao extends BaseDao{
	
	/**
	 * 状态获取;
	 * @param id
	 * @return
	 */
	public String findStatusById(String id) {
		String sql = "select status from erp_application where id='"+id+"'";
		Map<String, Object> map = this.getJdbcTemplate().queryForMap(sql);
		String status = null;
		if(map != null && map.get("status") != null) {
			status = (String) map.get("status");
		}
		return status;
	}
	
	
	/**
	 * 保存
	 * @param entity
	 * @throws Exception
	 */
	public void saveErpApplication(ErpApplication entity) throws Exception {
		this.getHibernateTemplate().save(entity);
	}
	
	/**
	 * 修改
	 * @param entity
	 * @throws Exception
	 */
	public void updateErpApplication(ErpApplication entity) throws Exception {
		this.getHibernateTemplate().update(entity);
	}

	/**
	 * 根据Id获取对象
	 * @param id
	 * @param sql 查询对象sql
	 * @return
	 */
	public ErpApplication findObjectById(String id) {
		StringBuilder jdbcsql = dealApplicationJdbcSql();
		jdbcsql.append(" and apc.id='").append(id).append("'");
		BeanPropertyRowMapper<ErpApplication> rowMapper = new BeanPropertyRowMapper<ErpApplication>(ErpApplication.class);
		return this.getJdbcTemplate().queryForObject(jdbcsql.toString(), rowMapper);
	}
	
	/**
	 * 设置默认jdbcsql
	 * @return
	 */
	public StringBuilder dealApplicationJdbcSql() {
		StringBuilder jdbcsql = new StringBuilder("select " +
				"apc.id, " +
				"apc.PROJECT_CODE projectCode, " +
				"apc.PROJECT_NAME projectName, " +
				"apc.PROJECT_OWNER projectOwner, " +
				"apc.REQUIREMENT requirement, " +
				"apc.LINK_NAME linkName, " +
				"apc.LINK_TEL linkTel, " +
				"apc.APPLICATION_NO applicationNo, " +
				"apc.BANNY_COMPANY_ID bannyCompanyId, " +
				"ship.BRANCH_COMMANY bannyCompanyName, " +
				"ship.OWNED_COMPANY ownerCompanyId, " +
				"dept.DEPT_NAME ownerCompanyName, " +
				"apc.RECEIVE_NAME receiveName, " +
				"apc.RECEIVE_TEL receiveTel, " +
				"apc.ADDRESS address, " +
				"apc.HOPE_DATE hopeDate, " +
				"apc.STATUS status, " +
				"apc.ATTACHMENT_PATH attachmentPath, " +
				"apc.CREATE_USER_ID createUserId, " +
				"apc.CREATE_TIME createTime, " +
				"apc.UPDATE_USER_ID updateUserId, " +
				"apc.UPDATE_USER_TIME updateTime, " +
				"apc.IS_DELETED isDeleted, " +
				"apc.IS_MARK isMark, " +
				"umUser.USER_NAME createUserName, " + 
				"apc.deal_user_id dealUserId, " + 
				"umUserdeal.USER_NAME dealUserName " + 
				"from  " +
				"erp_application apc  " +
				"left join HL_CUSTOMER_RELATIONSHIP ship on ship.id = apc.BANNY_COMPANY_ID " +
				"left join UM_DEPT dept on dept.id = ship.OWNED_COMPANY " + 
				"left join UM_USER umUserdeal on umUserdeal.id = apc.deal_user_id " + 
				"left join UM_USER umUser on umUser.id = apc.CREATE_USER_ID WHERE apc.IS_DELETED = 0 ");
		return jdbcsql;
	}
	
	/**
	 * 
	 * @param jdbcsql
	 * @param params查询参数[ ownerCompanyName:总公司名称, bannyCompanyName:支公司名称,
	 * status:状态, projectCode:项目编码, projectName:项目名称
	 * ,projectOwner:项目负责人, startDate:申请开始日期, endDate:申请截止日期 , receiveName:收件人]
	 * @return
	 */
	public StringBuilder dealParamsReturnSql(Map<String, String> params) {
		StringBuilder jdbcsql = dealApplicationJdbcSql();
		if(params != null ) {
			
			String ownerCompanyName = (String)params.get("ownerCompanyName");
			if(StringUtils.isNotEmpty(ownerCompanyName)) {
				jdbcsql.append(" and dept.DEPT_NAME like '%").append(ownerCompanyName.trim()).append("%' ");
			}
			
			String bannyCompanyName = (String)params.get("bannyCompanyName");
			if(StringUtils.isNotEmpty(bannyCompanyName)) {
				jdbcsql.append(" and ship.BRANCH_COMMANY like '%").append(bannyCompanyName.trim()).append("%' ");
				
			}
			
			String status = (String)params.get("status");
			if(StringUtils.isNotEmpty(status)) {
				jdbcsql.append(" and apc.STATUS = '").append(status).append("'");
			}
			
			String projectCode = (String)params.get("projectCode");
			if(StringUtils.isNotEmpty(projectCode)) {
				jdbcsql.append(" and apc.PROJECT_CODE like '%").append(projectCode.trim()).append("%'");
				
			}
			
			String projectName = (String)params.get("projectName");
			if(StringUtils.isNotEmpty(projectName)) {
				jdbcsql.append(" and apc.PROJECT_NAME like '%").append(projectName.trim()).append("%'");
				
			}
			
			String projectOwner = (String)params.get("projectOwner");
			if(StringUtils.isNotEmpty(projectOwner)) {
				jdbcsql.append(" and apc.PROJECT_OWNER like '%").append(projectOwner.trim()).append("%'");
				
			}
			
			String startDate = (String)params.get("startDate");
			if(StringUtils.isNotEmpty(startDate)) {
				jdbcsql.append(" and apc.CREATE_TIME >= to_date('").append(startDate).append("', ").append("'yyyy-MM-dd') ");
				
			}
			
			String endDate = (String)params.get("endDate");
			if(StringUtils.isNotEmpty(endDate)) {
				jdbcsql.append(" and apc.CREATE_TIME < to_date('").append(endDate).append("', ").append("'yyyy-MM-dd') + 1 ");
				
			}
			
			String receiveName = (String)params.get("receiveName");
			if(StringUtils.isNotEmpty(receiveName)) {
				jdbcsql.append(" and apc.RECEIVE_NAME like '%").append(receiveName.trim()).append("%'");
				
			}
			
			String applicationNo = (String)params.get("applicationNo");
			if(StringUtils.isNotEmpty(applicationNo)) {
				jdbcsql.append(" and apc.APPLICATION_NO like '%").append(applicationNo.trim()).append("%'");
				
			}
			/*
			 * 目的在于控制发货界面不能查询状态为4的数据(回退数据)
			 * 注意: 该控制只在发货列表界面使用;
			 */
			String storegeOutStatus = (String)params.get("storegeOutStatus"); 
			if(StringUtils.isNotEmpty(storegeOutStatus)) {
				jdbcsql.append(" and apc.STATUS != '").append(storegeOutStatus).append("'");
			}
			
			/*
			 * 阳光保险列表查询;加入支公司;和标示;
			 */
			String bannyCompanyId = params.get("bannyCompanyId");
			if(StringUtils.isNotEmpty(bannyCompanyId)) {
				jdbcsql.append(" and apc.BANNY_COMPANY_ID = '").append(bannyCompanyId).append("'");
			}
			/*
			 * 标示
			 */
			String isMark = params.get("isMark");
			if(StringUtils.isNotEmpty(isMark)) {
				jdbcsql.append(" and apc.is_mark = '").append(isMark).append("'");
				
			}
			
			//当前登陆人只能查看自己的申请; 此处作为后期扩展;
			String createUser = params.get("createUser");
			if(StringUtils.isNotEmpty(createUser)) {
				jdbcsql.append(" and apc.create_user_id = '").append(createUser).append("'");
				
			}
			
		}
		
		return jdbcsql;
	}


	/**
	 * 根据申请单号查询对应的申请单数据
	 * create by herny.xu 20170217
	 * @param applicationNo
	 * @return
	 */
	public ErpApplication findByApplicationNo(String applicationNo) {
		StringBuilder sqlSb = dealApplicationJdbcSql();
		sqlSb.append(" and apc.APPLICATION_NO = '" + applicationNo + "'");
		
		BeanPropertyRowMapper<ErpApplication> rowMapper = new BeanPropertyRowMapper<ErpApplication>(ErpApplication.class);
		
		List<ErpApplication> results = this.getJdbcTemplate().query(sqlSb.toString(), rowMapper);
		return results != null && results.size() > 0 ? results.get(0) : null;
	}

	
}
