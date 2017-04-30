/**
 * 
 */
package org.hpin.events.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 * @author machuan
 * @date 2016年11月3日
 */
@Repository()
public class ErpCustomerReadDao extends BaseDao{

	
	/**
	 * 通过jdbc查询总数; 根据提供sql拼接; 
	 * @param jdbcSql 要查询的 select * from a 
	 * @param lists 参数,(注意: 跟通配符对应顺序); 
	 * @return
	 */
	public Long findJdbcCount(StringBuilder jdbcSql, List<Object> lists) {
		if(StringUtils.isNotEmpty(jdbcSql.toString())) {
			
			StringBuilder countSql = new StringBuilder("select count(1) from (");
			countSql.append(jdbcSql);
			countSql.append(")");
			
			return this.getJdbcTemplate().queryForLong(countSql.toString(), lists.toArray());
		} 
		
		return 0L;
		
	}
	
	/**
	 * @param jdbcSql 要查询的 select * from a 
	 * @param lists 参数,(注意: 跟通配符对应顺序); 
	 * @param 在继承BaseDao的Dao中实现该内部类实现RowMapper<T>接口; 处理要查询的类的封装; 
	 * //RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(Class<T> returnType); //根据类型自动封装rowMapper对象;简化操作;
	 * @return
	 */
	public List<?> findJdbcList(String content, List<Object> lists, RowMapper<?> rowMapper) {
		StringBuilder listSql = new StringBuilder("select *  from( ");
		listSql.append("select ROWNUM RN,");
		listSql.append(content);
		//分页;
		listSql.append(" and rownum <= ?");
		listSql.append(" ORDER BY ectr.CODE DESC ");
		listSql.append(") where RN >＝ ?");
		return this.getJdbcTemplate().query(listSql.toString(), lists.toArray(), rowMapper);
	}
	
}
