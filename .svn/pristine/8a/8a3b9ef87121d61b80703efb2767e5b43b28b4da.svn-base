package org.hpin.common.core.orm;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hpin.common.core.orm.daoWrapper.DaoSupport;
import org.hpin.common.log.listener.ModifyHistoryService;
import org.hpin.common.util.DateUtils;
import org.hpin.common.util.StaticMethod;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

import com.itextpdf.awt.geom.misc.RenderingHints.Key;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/**
 * 基础DAO，封装了常用的CRUD操作
 * 
 * @author thinkpad
 * @Apr 15, 2009
 */
public class BaseDao {
	@Autowired
	private DaoSupport daoSupport;
	
	/**
	 * 大于该时间
	 */
	private final static String LT="GEST_T";
	
	/**
	 * 小于该时间
	 */
	private final static String RT="LEET_T";

	public HibernateTemplate getHibernateTemplate() {
		return daoSupport.getHibernateDaoSupport().getHibernateTemplate();
	}

	public JdbcTemplate getJdbcTemplate() {
		return daoSupport.getJdbcDaoSupport().getJdbcTemplate();
	}

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void save(BaseEntity entity) {
		Assert.notNull(entity, "实体类不能为空");
		this.getHibernateTemplate().saveOrUpdate(entity);
		System.out.println("save entity:"+entity);
		ModifyHistoryService logService = new ModifyHistoryService();
		logService.onPostInsert(entity);
	}

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void update(BaseEntity entity) {
		Assert.notNull(entity, "实体类不能为空");
		ModifyHistoryService logService = new ModifyHistoryService();
		logService.onSaveOrUpdate(entity);
		this.getHibernateTemplate().update(entity);
	}

	/**
	 * 删除实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void delete(BaseEntity entity) {
		Assert.notNull(entity);
		ModifyHistoryService logService = new ModifyHistoryService();
		logService.onPostDelete(entity);
		this.getHibernateTemplate().delete(entity);
	}

	/**
	 * 根据ID删除实体类
	 * 
	 * @param id
	 *            主键
	 * @param clazz
	 *            实体类class
	 */
	public void delete(Class clazz, String id) {
		BaseEntity entity = (BaseEntity) this.getHibernateTemplate().get(clazz, id);
		delete(entity);
	}

	/**
	 * 删除实体类集合
	 * 
	 * @author duanguowei update
	 * @param clazz
	 * @param ids
	 */
	public void deleteIds(Class clazz, String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			// BaseEntity entity = this.findById(clazz, new Long(idArray[i]));
			BaseEntity entity = this.findById(clazz, idArray[i]);
			this.delete(entity);
		}

	}

	/**
	 * 删除实体集合(非物理删除，将删除标示由0改为1)
	 * 
	 * @author duanguowei update
	 * @param clazz
	 * @param ids
	 */
	public void deleteByIds(Class clazz, String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			// BaseEntity entity = this.findById(clazz, new Long(idArray[i]));
			BaseEntity entity = this.findById(clazz, idArray[i]);
			ModifyHistoryService logService = new ModifyHistoryService();
			logService.onPostDelete(entity);
			this.getHibernateTemplate().delete(entity);
		}
	}

	/**
	 * 根据主键取得实体对象
	 * 
	 * @param id主键
	 * @param clazz
	 *            实体类class
	 * @return 实体类
	 */
	public BaseEntity findById(Class clazz, String id) {
		return (BaseEntity) this.getHibernateTemplate().get(clazz, id);
	}

	/**
	 * 根据主键取得实体对象
	 * 
	 * @param id主键
	 * @param clazz
	 *            实体类class
	 * @return 实体类
	 */
	public BaseEntity findById(Class clazz, long id) {
		return (BaseEntity) this.getHibernateTemplate().get(clazz, id);
	}

	/**
	 * 获取所有的实体
	 * 
	 * @param clazz
	 *            实体类型
	 * @param orderProperty
	 *            排序字段名称
	 * @param isAsc
	 *            是否升序
	 * @return
	 */
	public List findAll(Class clazz, String orderProperty, Boolean isAsc) {
		String queryString = " from " + clazz.getSimpleName();
		if (null != orderProperty && null != isAsc) {
			String orderType = isAsc == true ? " asc " : " desc ";
			queryString = queryString + " order by " + orderProperty + " " + orderType;
		}
		return this.getHibernateTemplate().find(queryString);
	}

	/**
	 * 根据属性获取对象
	 * 
	 * @param clazz
	 *            实体类型
	 * @param propertyName
	 *            属性字段名称
	 * @param propertyValue
	 *            属性字段类型
	 * @param orderProperty
	 *            排序字段名称
	 * @param isAsc
	 *            是否升序
	 * @return
	 */
	public List findByProperty(Class clazz, String propertyName, Object propertyValue, String orderProperty, Boolean isAsc) {
		String queryString = null;
		if (null != propertyValue) {
			queryString = " from " + clazz.getSimpleName() + " where " + propertyName + "=? ";
		} else {
			queryString = " from " + clazz.getSimpleName() + " where " + propertyName + " is null ";
		}
		if (null != isAsc && null != orderProperty) {
			String orderType = isAsc == true ? " asc " : " desc ";
			queryString = queryString + " order by " + orderProperty + " " + orderType;
		}
		if (null != propertyValue) {
			return this.getHibernateTemplate().find(queryString, propertyValue);
		} else {
			return this.getHibernateTemplate().find(queryString);
		}
	}

	/**
	 * 使用HQL进行分页查询 截取hql的第一个from 拼凑select count(*)进行记录总数查询
	 * 
	 * @param page
	 *            分页对象
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            HQL语句参数
	 * @return
	 */
	public List findByHql(final Page page, final String hql, final Object[] values) {
		int index = hql.toLowerCase().indexOf("from");
		String hqlCount = "";
		hqlCount = "select count(*) " + hql.substring(index);
		return findByHql(page, hqlCount, hql, values);
	}

	/**
	 * 根据查询获取参数
	 * 
	 * @param page
	 *            分页对象
	 * @param hql
	 *            HQL语句
	 * @param valueList
	 *            HQL语句的参数
	 * @return
	 */
	public List findByHql(Page page, StringBuffer hql, List valueList) {
		Object object[] = new Object[valueList.size()];
		for (int i = 0; i < valueList.size(); i++) {
			object[i] = valueList.get(i);
		}
		return findByHql(page, hql.toString(), object);
	}

	public List findPageByCountSql(Page page, String sql, String sqlCount, List valueList) {
		Object object[] = new Object[valueList.size()];
		valueList.toArray(object);

		return findBySql(page, sqlCount, sql, object);
	}

	public List<Object> findByHqlOnTotal(final Page page, final long totalCount, final String hql, final List values) {
		Assert.notNull(page, "分页类对象page不能为空");
		List<Object> list = getHibernateTemplate().executeFind(new HibernateCallback() {

			public List<Object> doInHibernate(Session session) throws HibernateException, SQLException {
				Assert.notNull(hql, "hql不能为空");
				Query query = null;

				page.setTotalCount(totalCount);
				int offset = (page.getPageNum() - 1) * page.getPageSize();
				query = session.createQuery(hql);
				if (values != null) {
					for (int i = 0; i < values.size(); i++) {
						query.setParameter(i, values.get(i));
					}
				}
				return query.setFirstResult(offset).setMaxResults(page.getPageSize()).list();

			}
		});
		page.setResults(list);
		return list;
	}

	public List findByHql(StringBuffer hql, Object... obj) {
		return this.getHibernateTemplate().find(hql.toString(), obj);
	}

	/**
	 * 使用HQL进行分页查询
	 * 
	 * @param page
	 *            分页对象
	 * @param hqlCount
	 *            HQL语句查询有多少条记录
	 * @param hql
	 *            HQL语句
	 * @return
	 */
	public List findByHql(final Page page, final String hqlCount, final String hql, final Object[] values) {
		Assert.notNull(page, "分页类对象page不能为空");
		List list = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Assert.notNull(hqlCount, "hqlCount不能为空");
				Assert.notNull(hql, "hql不能为空");
				Query query = null;
				query = session.createQuery(hqlCount);
				Long totalCount = 0l;
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				totalCount = ((Long) query.uniqueResult());
				page.setTotalCount(totalCount);
				int offset = 0;
				offset = (page.getPageNum() - 1) * page.getPageSize();
				query = session.createQuery(hql);
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				return query.setFirstResult(offset).setMaxResults(page.getPageSize()).list();

			}
		});
		page.setResults(list);
		return list;
	}

	/**
	 * 添加实体时判断属性是否唯一
	 * 
	 * @param clazz
	 *            实体对象
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return
	 */
	public boolean isUniqueByFieldForAdd(Class clazz, String propertyName, Object value) {
		List list = this.findByProperty(clazz, propertyName, value, null, null);
		if (list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改实体时判断属性是否唯一
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @param id
	 * @return
	 */
	public boolean isUniqueByFieldForUpdate(Class clazz, String propertyName, Object value, String id) {
		List list = this.getHibernateTemplate().find(" select id from " + clazz.getSimpleName() + " where " + propertyName + "=?", value);
		Serializable oldId = null;
		if (list.isEmpty()) {
			oldId = null;
		} else {
			oldId = list.get(0).toString();
		}
		if (oldId != null) {
			if (id != null && oldId != null && id.toString().equals(oldId)) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	public Object[] findCountByHql(String hqlFrom, Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer(hqlFrom);

		final List valuelist = new ArrayList();

		OrmConverter.assemblyQuery(queryString, filterMap, valuelist);
		final String hqlCount = queryString.toString();

		List rel = getHibernateTemplate().find(hqlCount, valuelist.toArray());
		return rel.isEmpty() ? null : (Object[]) rel.get(0);
	}

	/**
	 * 通用分页查询
	 * 
	 * @param clazz
	 *            实体类类型
	 * @param page
	 *            分页对象
	 * @param filterMap
	 *            过滤参数
	 */
	public List findByPage(Class clazz, Page page, Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer(" from " + clazz.getSimpleName() + " where 1=1 ");
		List valuelist = new ArrayList();
		OrmConverter.assemblyQuery(queryString, filterMap, valuelist);
		System.out.println("queryString---"+queryString);
		return findByHql(page, queryString, valuelist);
	}

	public List findByPage(String hqlFrom, Page page, Map<String, String> filterMap) {
		// 与 上面方法类似
		StringBuffer queryString = new StringBuffer(hqlFrom);

		List valuelist = new ArrayList();
		OrmConverter.assemblyQuery(queryString, filterMap, valuelist);
		return findByHql(page, queryString, valuelist);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findBySql(final Page page, final String sqlCount, final String sql, final Object[] values) {
		Assert.notNull(page, "分页类对象page不能为空");
		List list = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Assert.notNull(sql, "sql不能为空");
				Query query = null;
				query = session.createSQLQuery(sqlCount);
				Long totalCount = 0l;
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				totalCount = ((java.math.BigDecimal) query.uniqueResult()).longValue();
				page.setTotalCount(totalCount);
				int offset = 0;
				offset = (page.getPageNum() - 1) * page.getPageSize();
				query = session.createSQLQuery(sql);
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				return query.setFirstResult(offset).setMaxResults(page.getPageSize()).list();

			}
		});
		page.setResults(list);
		return list;
	}

	/**
	 * SQL分页查询
	 * 
	 * @param page
	 * @param sql
	 * @param values
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findBySql(final Page page, final String sql, final Object[] values) {
		Assert.notNull(page, "分页类对象page不能为空");
		List list = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Assert.notNull(sql, "sql不能为空");
				Query query = null;
				int index = sql.toLowerCase().indexOf("from");
				String sqlCount = "select count(*) " + sql.substring(index);
				query = session.createSQLQuery(sqlCount);
				Long totalCount = 0l;
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				totalCount = ((java.math.BigDecimal) query.uniqueResult()).longValue();
				page.setTotalCount(totalCount);
				int offset = 0;
				offset = (page.getPageNum() - 1) * page.getPageSize();
				query = session.createSQLQuery(sql);
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				return query.setFirstResult(offset).setMaxResults(page.getPageSize()).list();

			}
		});
		page.setResults(list);
		return list;
	}

	/**
	 * SQL分页查询
	 */
	public List findBySql(Page page, String sql, List valueList) {
		Object object[] = new Object[valueList.size()];
		for (int i = 0; i < valueList.size(); i++) {
			object[i] = valueList.get(i);
		}
		return findBySql(page, sql, object);
	}

	public List findBySql(final Page page, final String sqlCount, final String sql, List valueList) {
		Object object[] = new Object[valueList.size()];
		for (int i = 0; i < valueList.size(); i++) {
			object[i] = valueList.get(i);
		}
		return findBySql(page, sqlCount, sql, object);
	}

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void saveOrUpdate(BaseEntity entity) {
		Assert.notNull(entity, "实体类不能为空");
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public void saveEntity(BaseEntity entity) {
		Assert.notNull(entity, "实体类不能为空");
		this.getHibernateTemplate().save(entity);
	}

	/**
	 * 获取当天最大序号
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCodeMaxByTaday(String tableName) throws Exception {
		Long count = this.getJdbcTemplate().queryForLong(" select count(id) as num from " + tableName + " where date_num=?", new Object[] { DateUtils.toInteger(new Date()) });
		count = count + 1;
		String code = count.toString();
		while (code.length() <= 3) {
			code = "0" + code;
		}
		code = DateUtils.DateToStr(new Date(), "yyyyMMdd") + code;
		return code;
	}

	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
	}

	protected void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pstmt = null;
		}
	}

	protected void populate(Object bean, ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int ncolumns = metaData.getColumnCount();

		HashMap properties = new HashMap();
		String sTemp = "";
		// Scroll to next record and pump into hashmap
		for (int i = 1; i <= ncolumns; i++) {
			// System.out.println(metaData.getColumnName(i) +":"+
			// metaData.getColumnTypeName(i));
			if (metaData.getColumnTypeName(i).toLowerCase().equals("date") || metaData.getColumnTypeName(i).toLowerCase().equals("datetime")
					|| metaData.getColumnTypeName(i).toLowerCase().equals("datetime year to second") || metaData.getColumnTypeName(i).toLowerCase().equals("number")) {
				sTemp = StaticMethod.null2String(rs.getString(i)).replaceAll("\\.0", "");
			} else {
				sTemp = StaticMethod.dbNull2String(rs.getString(i));
			}
			properties.put(sql2javaName(metaData.getColumnName(i).toLowerCase()), sTemp);
		}
		// Set the corresponding properties of our bean
		try {
			BeanUtils.populate(bean, properties);
		} catch (Exception ite) {
			throw new SQLException("BeanUtils.populate threw " + ite.toString());
		}

	}

	protected static String sql2javaName(String name) {
		int k = name.indexOf(".");
		name = name.substring(k + 1);

		String column = "";
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) == '_') {
				column += ++i < name.length() ? String.valueOf(name.charAt(i)).toUpperCase() : "";
			} else {
				column += name.charAt(i);
			}
		}
		return column;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page findObjectListBySql(final Page page, final String sql, final Object[] values, final int objectLength) {
		Page p = new Page();

		p = (Page) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				String sqlCount = "select count(*) from (" + sql + ")";
				query = session.createSQLQuery(sqlCount);
				Long totalCount = 0l;
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				totalCount = ((java.math.BigDecimal) query.uniqueResult()).longValue();
				System.out.println("totalCount:" + totalCount);
				page.setTotalCount(totalCount);
				Connection con = session.connection();
				String querySql = "select * from ( select row_.*, rownum rownum_ from ( " + sql + " ) row_ where rownum <= ?) where rownum_ > ?";
				System.out.println(querySql);
				PreparedStatement ps = con.prepareStatement(querySql);
				int j = 0;
				if (values != null && values.length > 0) {
					for (j = 0; j < values.length; j++) {
						ps.setObject(j + 1, values[j]);
					}
				}
				ps.setObject(++j, page.getPageNum() * page.getPageSize());
				ps.setObject(++j, (page.getPageNum() - 1) * page.getPageSize());
				ResultSet rs = ps.executeQuery();
				List<Object[]> all = new ArrayList<Object[]>();
				while (rs.next()) {
					Object[] obj = new Object[objectLength];
					for (int i = 0; i < obj.length; i++) {
						obj[i] = rs.getObject(i + 1);
					}
					all.add(obj);
				}
				page.setResults(all);
				rs.close();
				ps.close();
				session.flush();
				session.close();
				return page;
			}
		});
		return p;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findObjectListBySql(final String sql, final Object[] values, final int objectLength) {

		List list = (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				Connection con = session.connection();
				PreparedStatement ps = con.prepareStatement(sql);
				int j = 0;
				if (values != null && values.length > 0) {
					for (j = 0; j <= values.length; ++j) {
						ps.setObject(j, values[j]);
					}
				}
				ResultSet rs = ps.executeQuery();
				List<Object[]> all = new ArrayList<Object[]>();
				while (rs.next()) {
					Object[] obj = new Object[objectLength];
					for (int i = 0; i < obj.length; i++) {
						obj[i] = rs.getObject(i + 1);
					}
					all.add(obj);
				}
				rs.close();
				ps.close();
				session.flush();
				session.close();
				return all;
			}
		});
		return list;

	}
	/**
	 * 根据属性获取对象
	 * 
	 * @param clazz
	 *            实体类型
	 * @param propertyName
	 *            属性字段名称
	 * @param propertyValue
	 *            属性字段类型
	 * @param orderProperty
	 *            排序字段名称
	 * @param isAsc
	 *            是否升序
	 * @param isDeleted
	 *            是否删除
	 * @return
	 */
	public List findByPropertyAndIsDelete(Class clazz, String propertyName, Object propertyValue,Object isDelte, String orderProperty, Boolean isAsc) {
		String queryString = null;
		if (null != propertyValue) {
			if(null!=isDelte){
				queryString = " from " + clazz.getSimpleName() + " where " + propertyName + "=? and isDeleted=\'"+isDelte+"\'";
			}else{
				queryString = " from " + clazz.getSimpleName() + " where " + propertyName + "=? ";
			}
		} else {
			if(null!=isDelte){
				queryString = " from " + clazz.getSimpleName() + " where " + propertyName + " is null and isDeleted=\'"+isDelte+"\'";
			}else{
				queryString = " from " + clazz.getSimpleName() + " where " + propertyName + " is null ";
			}
		}
		if (null != isAsc && null != orderProperty) {
			String orderType = isAsc == true ? " asc " : " desc ";
			queryString = queryString + " order by " + orderProperty + " " + orderType;
		}
		if (null != propertyValue) {
			return this.getHibernateTemplate().find(queryString, propertyValue);
		} else {
			return this.getHibernateTemplate().find(queryString);
		}
	}
	
	/**
	 * 通过jdbc查询总数; 根据提供sql拼接; 
	 * create by henry.xu 20160830
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
	 * 根据传入sql,分页查询; 注意别名尽量不要使用sbx;分页参数; 放在最后;注意顺序;
	 * create by henry.xu 20160830
	 * @param jdbcSql 要查询的 select * from a 
	 * @param lists 参数,(注意: 跟通配符对应顺序); 
	 * @param 在继承BaseDao的Dao中实现该内部类实现RowMapper<T>接口; 处理要查询的类的封装; 
	 * //RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(Class<T> returnType); //根据类型自动封装rowMapper对象;简化操作;
	 * @return
	 */
	public List<?> findJdbcList(StringBuilder jdbcSql, List<Object> lists, RowMapper<?> rowMapper) {
		StringBuilder listSql = new StringBuilder("select *  from( ");
		listSql.append("select sbx.*, ROWNUM RN from ( ");
		listSql.append(jdbcSql);
		//分页;
		listSql.append(") sbx where rownum <= ?) where RN >＝ ?");
		
		return this.getJdbcTemplate().query(listSql.toString(), lists.toArray(), rowMapper);
	}
	
	
	@SuppressWarnings("rawtypes")
	public List<Map<String , Object>> getPageList(Page page,String sql){
		int pageNum=page.getPageNum();
		int pageSize=page.getPageSize();
		int start=(pageNum-1)*pageSize;
		int end=pageNum*pageSize;
		List<Map<String, Object>> list=this.getJdbcTemplate().queryForList(sql);
		System.out.println(list.size());
		if(list.size()>=1){
			if (end>list.size()) {
				end=list.size();
			}
			page.setTotalCount((long)list.size());
			return list.subList(start, end);
		}else {
			return list;
		}
	}
	
	/**
	 * @param str 
	 * @return 根据获取到的name值截取查询字段
	 */
	public String getSearchMap(String key) {
		if (key.split("_").length==5) {
			return key.split("_")[2];
		}else {
			String str="";
			String regex="";
			if (key.contains("AND")) {
				regex = "and_(.*)_AND";
			}else if(key.contains("LIKE")){
				regex = "and_(.*)_LIKE";
			}else if (key.contains("GEST")) {
				regex = "and_(.*)_GEST";
			}else if (key.contains("LEET")) {
				regex = "and_(.*)_LEET";
			}
		    Pattern pattern = Pattern.compile(regex);
		    Matcher matcher = pattern.matcher(key);
		    if (matcher.find()) {
		       str=matcher.group(1);
		    }
		    return str;
		}
	}
	
	/**
	 * @param suffix
	 * @return 是否是开始时间，以“GEST_T”结尾
	 */
	public boolean isEndLT(String key) {
		if (key.endsWith(LT)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @param suffix
	 * @return 是否是结束时间，以“LEET_T”结尾
	 */
	public boolean isEndRT(String key) {
		if (key.endsWith(RT)) {
			return true;
		}else {
			return false;
		}
	}
	
}
