package org.hpin.common.AjaxCheckCode.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;
@Repository()
public class AjaxCheckCodeDao extends BaseDao{

	public List findByProperty(String beanName, String propertyName, String propertyValue,
			Object orderProperty, Object isAsc) {
		String queryString = null;
		if (null != propertyValue) {
			queryString = " from " + beanName + " where "
					+ propertyName + "=? ";
		} else {
			queryString = " from " + beanName + " where "
					+ propertyName + " is null ";
		}
		if (null != propertyValue) {
			return this.getHibernateTemplate().find(queryString, propertyValue);
		} else {
			return this.getHibernateTemplate().find(queryString);
		}
		
	}
	
	public List findModifyLogItem(String sql) {
		return this.getJdbcTemplate().queryForList(sql);
	}

}
