package org.hpin.warehouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.StoreWarehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreWarehouse entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.hpin.warehouse.StoreWarehouse
 * @author MyEclipse Persistence Tools
 */
@Repository(value="org.hpin.warehouse.dao.StoreWarehouseDAO")
public class StoreWarehouseDAO extends BaseDao {
	private static final Logger log = LoggerFactory
			.getLogger(StoreWarehouseDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String DIRECTOR = "director";
	public static final String TEL = "tel";
	public static final String PROVICE = "provice";
	public static final String CITY = "city";
	public static final String CREATE_USER_ID = "createUserId";
	public static final String UPDATE_USER_ID = "updateUserId";
	public static final String REMARK = "remark";
	public static final String REMARK1 = "remark1";
	public static final String REMARK2 = "remark2";
	public static final String REMARK3 = "remark3";
	public static final String DELETE_USER_ID = "deleteUserId";

	public void save(StoreWarehouse transientInstance) {
		log.debug("saving StoreWarehouse instance");
		try {
			this.getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoreWarehouse persistentInstance) {
		log.debug("deleting StoreWarehouse instance");
		try {
			this.getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
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
	public StoreWarehouse findById(java.lang.String id) {
		log.debug("getting StoreWarehouse instance with id: " + id);
		try {
			StoreWarehouse instance = (StoreWarehouse) this.getHibernateTemplate().get(
					"org.hpin.warehouse.StoreWarehouse", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*public List findByExample(StoreWarehouse instance) {
		log.debug("finding StoreWarehouse instance by example");
		try {
			List results = this.getHibernateTemplate()
					.createCriteria("org.hpin.warehouse.StoreWarehouse")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}*/

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding StoreWarehouse instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StoreWarehouse as model where model."
					+ propertyName + "= ? and model.isDeleted=0";
			return this.getHibernateTemplate().find(queryString,new Object[]{value});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByDirector(Object director) {
		return findByProperty(DIRECTOR, director);
	}

	public List findByTel(Object tel) {
		return findByProperty(TEL, tel);
	}

	public List findByProvice(Object provice) {
		return findByProperty(PROVICE, provice);
	}

	public List findByCity(Object city) {
		return findByProperty(CITY, city);
	}

	public List findByCreateUserId(Object createUserId) {
		return findByProperty(CREATE_USER_ID, createUserId);
	}

	public List findByUpdateUserId(Object updateUserId) {
		return findByProperty(UPDATE_USER_ID, updateUserId);
	}

	public List findByRemark(Object remark) {
		return findByProperty(REMARK, remark);
	}

	public List findByRemark1(Object remark1) {
		return findByProperty(REMARK1, remark1);
	}

	public List findByRemark2(Object remark2) {
		return findByProperty(REMARK2, remark2);
	}

	public List findByRemark3(Object remark3) {
		return findByProperty(REMARK3, remark3);
	}

	public List findByDeleteUserId(Object deleteUserId) {
		return findByProperty(DELETE_USER_ID, deleteUserId);
	}

	public List findAll() {
		log.debug("finding all StoreWarehouse instances");
		try {
			String queryString = "from StoreWarehouse where isDeleted=0";
			return this.getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreWarehouse merge(StoreWarehouse detachedInstance) {
		log.debug("merging StoreWarehouse instance");
		try {
			StoreWarehouse result = (StoreWarehouse) this.getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreWarehouse instance) {
		log.debug("attaching dirty StoreWarehouse instance");
		try {
			this.getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreWarehouse instance) {
		log.debug("attaching clean StoreWarehouse instance");
		try {
			this.getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/** 
	 * 仓库信息查询sql处理;
	 */
	public StringBuilder dealWarehouseSql() {
		StringBuilder jdbcsql = new StringBuilder("select " +
				"stoWar.id id, " +
				"stoWar.NAME name, " +
				"stoWar.ADDRESS address, " +
				"stoWar.DIRECTOR director, " +
				"stoWar.TEL tel, " +
				"stoWar.PROVINCE province, " +
				"reg.REGION_NAME provinceName, " +
				"stoWar.CITY city, " +
				"regcity.region_name cityName, " +
				"stoWar.CREATE_TIME createTime, " +
				"stoWar.CREATE_USER_ID createUserId, " +
				"stoWar.UPDATE_USER_ID updateUserId, " +
				"stoWar.UPDATE_TIME updateTime, " +
				"stoWar.REMARK remark, " +
				"stoWar.REMARK1 remark1, " +
				"stoWar.REMARK2 remark2, " +
				"stoWar.REMARK3 remark3, " +
				"stoWar.IS_DELETED isDeleted, " +
				"stoWar.DELETE_USER_ID deleteUserId, " +
				"stoWar.DELETE_TIME deleteTime " +
				"from  " +
				"store_warehouse stoWar " +
				"left join HL_REGION reg on stoWar.PROVINCE = reg.id " +
				"left join HL_REGION regcity on regcity.id = stoWar.CITY WHERE stoWar.IS_DELETED = 0 ");
		return jdbcsql;
	}
	
	/**
	 * 仓库信息查询参数处理;
	 * @param params 参数来源页面查询条件;
	 * @return
	 */
	public StringBuilder dealWarehouseSqlByParams(Map<String, String> params) {
		StringBuilder jdbcsql = dealWarehouseSql();
		
		if(params != null ) {
			String name = params.get("name");
			String director = params.get("director");
			String tel = params.get("tel");
			String province = params.get("province");
			String city = params.get("city");
			String currentUserId = params.get("currentUserId");
			
			if(StringUtils.isNotEmpty(name)) {
				jdbcsql.append(" and stoWar.NAME like '%").append(name.trim()).append("%' ");
			}
			if(StringUtils.isNotEmpty(director)) {
				jdbcsql.append(" and stoWar.DIRECTOR like '%").append(director.trim()).append("%' ");
			}
			if(StringUtils.isNotEmpty(tel)) {
				jdbcsql.append(" and stoWar.TEL like '%").append(tel.trim()).append("%' ");
			}
			if(StringUtils.isNotEmpty(province)) {
				jdbcsql.append(" and stoWar.PROVINCE = '").append(province).append("' ");
			}
			if(StringUtils.isNotEmpty(city)) {
				jdbcsql.append(" and stoWar.CITY = '").append(city).append("' ");
			}
			
			if(StringUtils.isNotEmpty(currentUserId)) {
				jdbcsql.append(" and stoWar.CREATE_USER_ID ='").append(currentUserId).append("' ");
			}
		}
		
		return jdbcsql;
	}

	public int validNameRepeat(String id, String name) {
		String sql = "select count(1) from store_warehouse where name='"+name+"' and id not in('"+id+"')" ;
		return this.getJdbcTemplate().queryForInt(sql);
	}
}