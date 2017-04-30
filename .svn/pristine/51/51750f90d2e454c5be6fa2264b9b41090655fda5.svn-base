package org.hpin.base.customerrelationship.dao;

import java.util.Map;

import org.hpin.base.dict.dao.ID2NameDAO;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

@Repository("projectTypeDao")
public class ProjectTypeDao extends BaseDao implements ID2NameDAO {
	
	/**
	 * 通过类型ID查找对应的名称;
	 * create by henry.xu
	 * 20160928
	 */
	@Override
	public String id2Name(String id) {
		String sql = "select project_type_name proTypeName from T_PROJECT_TYPE where id=? ";
		Map<String, Object> result = this.getJdbcTemplate().queryForMap(sql, id);
		if(result != null && result.get("proTypeName") != null) {
			return result.get("proTypeName").toString();
		}
		
		return null;
	}

	@Override
	public String id2Field(String id, String beanId, String field) {
		return null;
	}
}
