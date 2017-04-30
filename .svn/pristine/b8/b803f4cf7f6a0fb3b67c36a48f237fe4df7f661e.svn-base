package org.hpin.base.resource.dao;

import org.hpin.base.resource.entity.ParameterConfig;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * <p>@desc : 系统参数Dao</p>
 * <p>@see : </p>
 *
 * <p>@author : sky</p>
 * <p>@createDate : Aug 30, 2012 11:12:55 AM</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p>
 */
@Repository()
public class ParameterConfigDao extends BaseDao {

	/**
	 * JDBC方式删除所有参数配置
	 */
	public void deleteAllData() {
		this.getJdbcTemplate().update(" delete from sys_parameter_config");
	}

	/**
	 * 保存参数配置(JDBC方式)
	 * @param parameterConfig
	 */
	public void saveForJdbc(ParameterConfig parameterConfig) {
		long id = this.getJdbcTemplate().queryForLong(
				"select max(id) from sys_parameter_config") + 1;
		parameterConfig.setId(id);
		String sql = "insert into sys_parameter_config(id,name,value,description)values(?,?,?,?)";
		this.getJdbcTemplate().update(
				sql,
				new Object[] { id, parameterConfig.getName(), parameterConfig.getValue(),
						parameterConfig.getDescription() });
	}

}
