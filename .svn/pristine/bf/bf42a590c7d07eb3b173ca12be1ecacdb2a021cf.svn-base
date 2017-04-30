package org.hpin.events.dao;

import java.util.List;

import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.ProjectRelationship;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRelationshipDao extends BaseDao{

	/**
	 * 根据样本快递费用Id删除对应的项目关联;
	 * @param id
	 */
	public void deleteBySampleDeliId(String sampleDeliId) throws Exception{
		String sql = "delete from ERP_PROJECT_RELEVANCE where SAMPLE_DELI_MANAGER_ID=?";
		this.getJdbcTemplate().update(sql, sampleDeliId);
	}
	
	public List<ProjectRelationship> findAllBySampleDeliId(String sampleDeliId) {
		String sql = "select " +
				"pro.id id,  " +
				"pro.batch_num batchNum, " +
				"pro.CUSTOMER_RELATIONSHIP_ID customerRelationShipId, " +
				"ship.BRANCH_COMMANY customerRelationShipName, " +
				"pro.CUSTOMER_RELATIONSHIP_PRO_ID customerRelationShipProId, " +
				"shipPro.PROJECT_CODE projectCode, " +
				"shipPro.PROJECT_NAME projectName, " +
				"shipPro.PROJECT_OWNER projectOwner, " +
				"shipPro.LINK_NAME linkName, " + 
				"pro.PRO_JOINT_PERSON proJointPerson, " +
				"pro.PERSON_NUM personNum, " +
				"pro.PRICE price, " +
				"pro.SAMPLE_DELI_MANAGER_ID sampleDeliManagerId " +
				"from  " +
				"ERP_PROJECT_RELEVANCE pro " +
				"left join HL_CUSTOMER_RELATIONSHIP ship on ship.id = pro.CUSTOMER_RELATIONSHIP_ID " +
				"left join HL_CUSTOMER_RELATIONSHIP_PRO shipPro on shipPro.id = pro.CUSTOMER_RELATIONSHIP_PRO_ID " +
				"where isdeleted=0 and SAMPLE_DELI_MANAGER_ID=?";
		RowMapper<ProjectRelationship> rowMapper = new BeanPropertyRowMapper<ProjectRelationship>(ProjectRelationship.class);
		return this.getJdbcTemplate().query(sql, new Object[]{sampleDeliId}, rowMapper);
	}
	
	public void deletePro(String targetId, User currentUser) {
		String sql = "update ERP_PROJECT_RELEVANCE set isdeleted = 1, DELETE_TIME=sysdate, DELETE_USER_ID='"+currentUser.getId()+"' where SAMPLE_DELI_MANAGER_ID='"+targetId+"'";
		this.getJdbcTemplate().update(sql);
	}

}
