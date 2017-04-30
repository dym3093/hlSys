package org.hpin.events.dao;

import java.util.List;
import java.util.Map;

import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.SampleDeliManager;
import org.springframework.stereotype.Repository;

@Repository
public class SampleDeliManagerDao extends BaseDao {

	public List<Map<String, Object>> findJdbcListAll(StringBuilder jdbcSql) {
		return this.getJdbcTemplate().queryForList(jdbcSql.toString());
	}

	public void saveSampleDeli(SampleDeliManager sampleDeli) throws Exception {
		this.getHibernateTemplate().save(sampleDeli);
	}

	public void deleteSampleDleiManagerIds(String id, User currentUser) {
		
		String sql = "update ERP_SAMPLE_DELI_MANAGER set isdeleted = 1, DELETE_TIME=sysdate, DELETE_USER_ID='"+currentUser.getId()+"' where id='"+id+"'";
		this.getJdbcTemplate().update(sql);
	}
}
