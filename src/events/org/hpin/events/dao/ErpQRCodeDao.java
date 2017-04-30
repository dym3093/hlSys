package org.hpin.events.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.ErpRelationShipCombo;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpConference;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.entity.ErpQRCode;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpQRCodeDao extends BaseDao {

	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPageQRCOde(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from ErpQRCode where isDelete='0' ");
		searchMap.put("order_createTime", "desc");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}
	
	/**
	 * 根据ID获取ErpQRCode对象
	 * @param QRCodeId
	 * @return
	 * @author tangxing
	 * @date 2016-8-18下午5:03:22
	 */
	public ErpQRCode getErpQRCodeById(String QRCodeId){
		return this.getHibernateTemplate().get(ErpQRCode.class, QRCodeId);
	}
	
	public void saveErpQRCode(ErpQRCode erpQRCode){
		this.getHibernateTemplate().saveOrUpdate(erpQRCode);
	}
	
	/**
	 * 根据支公司名字查找具体套餐
	 * @param bcId
	 * @return
	 * @author tangxing
	 * @date 2016-8-19下午4:50:57
	 */
	public List<CustomerRelationShip> getComboByBranchCompanyId(String bcId){
		String query = " from CustomerRelationShip where id=? and isDeleted='0' ";
		List<CustomerRelationShip> list = this.getHibernateTemplate().find(query, new Object[]{bcId});
		return list;
	}
	
	/**
	 * 根据支公司ID获取支公司对应项目，项目下的所有套餐ID
	 * @param bcId
	 * @return
	 * @author tangxing
	 * @date 2016-11-17下午3:17:53
	 */
	public List<Map<String, Object>> getComboIdByProjectId(String projectId){
		String query = "select rc.combo_id as comboId from ERP_RelationShipPro_Combo rc where rc.customer_relationship_pro_id=? and rc.is_used = '1' ";
		return this.getJdbcTemplate().queryForList(query, new Object[]{projectId});
	}
	
	
	
	/**
	 * 根据combo名字找comboId
	 * @param name
	 * @return
	 * @author tangxing
	 * @date 2016-8-19下午5:10:23
	 */
	public Map<String, Object> getComboIdByName(String name){
		String query = "select t.id from hl_jy_combo t where t.combo_name='"+name+"' and t.is_delete='0'";
		return this.getJdbcTemplate().queryForMap(query);
	}
	
	/**
	 * 根据comboId找combo名字
	 * @param name
	 * @return
	 * @author tangxing
	 * @date 2016-11-17下午3:53:01
	 */
	public Map<String, Object> getComboNameById(String id){
		String query = "select t.combo_name as comboName from hl_jy_combo t where t.id=? and t.is_delete='0'";
		return this.getJdbcTemplate().queryForMap(query, new Object[]{id});
	}
	
	/**
	 * 根据场次No查找场次
	 * @param eventsNo
	 * @return
	 * @author tangxing
	 * @date 2016-8-19下午7:00:38
	 */
	public List<ErpEvents> getEventsByNo(String eventsNo){
		String query = " from ErpEvents where eventsNo=? and isDeleted='0' ";
		List<ErpEvents> list = this.getHibernateTemplate().find(query, new Object[]{eventsNo});
		return list;
	}
	
	/**
	 * 根据关键字查ErpQRCode
	 * @param keyword
	 * @return
	 * @author tangxing
	 * @date 2016-8-22下午12:25:43
	 */
	public List<ErpQRCode> getErpQRCodeByKeyword(String keyword){
		String query = " from ErpQRCode where keyword=? and isDelete='0' ";
		List<ErpQRCode> list = this.getHibernateTemplate().find(query, new Object[]{keyword});
		return list;
	}

	/**
	 * 无创二维码sql语句拼接;
	 * create by henry.xu 2016年12月1日
	 * @return
	 */
	public StringBuilder dealQRCodeSql() {
		StringBuilder sql = new StringBuilder("select " +
			"qrc.id,  " +
			"event.id eventsId, " +
			"qrc.EVENTS_NO eventsNo,  " +
			"qrc.EVENTS_DATE eventsDate,  " +
			"qrc.province_id provinceId, " +
			"qrc.PROVINCE_NAME provinceName, " +
			"qrc.city_id cityId, " +
			"qrc.CITY_NAME cityName,  " +
			"qrc.BANCHCOMPANY_ID banchCompanyId,  " +
			"ship.BRANCH_COMMANY banchCompanyName, " +
			"qrc.OWNEDCOMPANY_ID ownedCompanyId,  " +
			"dep.DEPT_NAME ownedCompanyName, " +
			"qrc.combo, " +
			"qrc.QR_LEVEL \"level\", " + //  \"level\关键字处理;
			"dict.DICTNAME levelName,  " +
			"qrc.EXPECT_NUM expectNum,  " +
			"qrc.QRCODE_STATUS QRCodeStatus,  " +
			"qrc.BATCH_NO batchNo,  " +
			"qrc.QRCODE_NAME QRcodeName,  " +
			"qrc.QRCODE_PATH QRCodePath,  " +
			"qrc.KEYWORD keyword,  " +
			"qrc.PUSH_STATUS pushStatus,  " +
			"qrc.QRCODE_LOCALPATH QRCodeLocalPath " +
			"from erp_qrcode qrc " + 
			"inner join erp_events event on event.EVENTS_NO = qrc.EVENTS_NO " +
			"inner join HL_CUSTOMER_RELATIONSHIP_PRO pro on event.CUSTOMERRELATIONSHIPPRO_ID = pro.ID " +
			"inner join T_PROJECT_TYPE ty on ty.id = pro.PROJECT_TYPE " +
			"inner join HL_CUSTOMER_RELATIONSHIP ship on ship.id = qrc.BANCHCOMPANY_ID " +
			"inner join UM_DEPT dep on dep.id = qrc.OWNEDCOMPANY_ID " +
			"left join sys_dicttype dict on dict.DICTCODE = qrc.QR_LEVEL WHERE ISDETELE = '0' ");
		return sql;
	}
	
	/**
	 * 无创二维码sql语句参数拼接;
	 * create by henry.xu 2016年12月1日
	 * @return
	 */
	public StringBuilder dealQRCodeSqlByParams(HashMap<String, String> params) {
		StringBuilder sql = this.dealQRCodeSql();
		
		//当条件不为空时, 进行参数处理;
		if(null != params && !params.isEmpty()) {
			String eventsNo = params.get("eventsNo");
			String banchCompanyName = params.get("banchCompanyName");
			String ownedCompanyName = params.get("ownedCompanyName");
			String startDate = params.get("startDate");
			String endDate = params.get("endDate");
			String level = params.get("level");
			String provinceId = params.get("provinceId");
			String cityId = params.get("cityId");
			String QRCodeStatus = params.get("QRCodeStatus");
			String batchNo = params.get("batchNo");
			String projectType = params.get("projectType");
			
			if(StringUtils.isNotEmpty(projectType)) {
				sql.append(" and ty.PROJECT_TYPE in ('PCT_004', 'PCT_005') ");
			}
			if(StringUtils.isNotEmpty(eventsNo)) {
				sql.append(" and qrc.EVENTS_NO like '%").append(eventsNo.trim()).append("%' ");
			}
			if(StringUtils.isNotEmpty(banchCompanyName)) {
				sql.append(" and ship.BRANCH_COMMANY like '%").append(banchCompanyName.trim()).append("%' ");
			}
			if(StringUtils.isNotEmpty(ownedCompanyName)) {
				sql.append(" and dep.DEPT_NAME like '%").append(ownedCompanyName.trim()).append("%' ");
			}
			if(StringUtils.isNotEmpty(startDate)) {
				sql.append(" and qrc.EVENTS_DATE >= to_date('").append(startDate).append("', 'yyyy-MM-dd') ");
			}
			if(StringUtils.isNotEmpty(endDate)) {
				sql.append(" and qrc.EVENTS_DATE < to_date('").append(endDate).append("', 'yyyy-MM-dd') + 1 ");
			}
			if(StringUtils.isNotEmpty(level)) {
				sql.append(" and qrc.QR_LEVEL = '").append(level).append("' ");
				
			}
			if(StringUtils.isNotEmpty(provinceId)) {
				sql.append(" and qrc.province_id = '").append(provinceId).append("' ");
				
			}
			if(StringUtils.isNotEmpty(cityId)) {
				sql.append(" and qrc.city_id = '").append(cityId).append("' ");
				
			}
			if(StringUtils.isNotEmpty(QRCodeStatus)) {
				
				sql.append(" and qrc.QRCODE_STATUS = '").append(QRCodeStatus).append("' ");
			}
			if(StringUtils.isNotEmpty(batchNo)) {
				sql.append(" and qrc.BATCH_NO like '%").append(batchNo.trim()).append("%' ");
				
			}
			
		}
		
		
		return sql;
	}

	/**
	 * 修改二维码场次推送状态为推送;
	 * create by henry.xu 2016年12月8日
	 * @param id 二维码场次id;
	 */
	public void updateErpQRCodePush(String id, String pushStatus) {
		String sql = "update erp_qrcode set push_status = '"+pushStatus+"', UPDATE_TIME=sysdate where EVENTS_ID = '"+id+"' ";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 根据项目ID和套餐ID查找ErpRelationShipCombo
	 * @param pro_id 项目ID
	 * @param comboId 套餐ID
	 * @return List
	 * @throws Exception
	 * @author Damian
     * @since 2016-12-26 14:44
	 */
	public List<ErpRelationShipCombo> listRelationShipCombo(String pro_id, String comboId) throws Exception{
		List<ErpRelationShipCombo> list = null;
		if(StringUtils.isNotEmpty(pro_id)&&StringUtils.isNotEmpty(comboId)){
			String query = "select * from ERP_RelationShipPro_Combo rc where rc.customer_relationship_pro_id=? and combo_id = ? and rc.is_used = '1' ";
			list = this.getJdbcTemplate().query(query, new String[]{pro_id, comboId}, new BeanPropertyRowMapper<ErpRelationShipCombo>(ErpRelationShipCombo.class));
		}
		return list;
	}
}
































