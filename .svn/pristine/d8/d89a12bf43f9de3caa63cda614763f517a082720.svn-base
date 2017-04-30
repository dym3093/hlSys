package org.hpin.warehouse.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.StoreWarehouseAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreWarehouseAll entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.hpin.warehouse.StoreWarehouseAll
 * @author MyEclipse Persistence Tools
 */
@Repository(value="org.hpin.warehouse.dao.StoreWarehouseAllDAO")
public class StoreWarehouseAllDAO extends BaseDao {
	private static final Logger log = LoggerFactory
			.getLogger(StoreWarehouseAllDAO.class);
	// property constants
	public static final String WAREHOUSE_ID = "warehouseId";
	public static final String WAREHOUSE_NAME = "warehouseName";
	public static final String TYPE_BIG_CODE = "typeBigCode";
	public static final String TYPE_BIG_NAME = "typeBigName";
	public static final String TYPE_SMALL_CODE = "typeSmallCode";
	public static final String TYPE_SMALL_NAME = "typeSmallName";
	public static final String CREATE_USER_ID = "createUserId";
	public static final String UPDATE_USER_ID = "updateUserId";
	public static final String DELETE_USER_ID = "deleteUserId";
	public static final String REMARK = "remark";
	public static final String REMARK1 = "remark1";
	public static final String REMARK2 = "remark2";
	public static final String REMARK3 = "remark3";

	public void save(StoreWarehouseAll transientInstance) {
		log.debug("saving StoreWarehouseAll instance");
		try {
			this.getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoreWarehouseAll persistentInstance) {
		log.debug("deleting StoreWarehouseAll instance");
		try {
			this.getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StoreWarehouseAll findById(java.lang.String id) {
		log.debug("getting StoreWarehouseAll instance with id: " + id);
		try {
			StoreWarehouseAll instance = (StoreWarehouseAll) this.getHibernateTemplate().get(
					"org.hpin.warehouse.StoreWarehouseAll", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
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
/*
	public List findByExample(StoreWarehouseAll instance) {
		log.debug("finding StoreWarehouseAll instance by example");
		try {
			List results = this.getHibernateTemplate()
					.createCriteria("org.hpin.warehouse.StoreWarehouseAll")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
*/
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding StoreWarehouseAll instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StoreWarehouseAll as model where model."
					+ propertyName + "= ? and model.isDeleted=0";
			return this.getHibernateTemplate().find(queryString,new Object[]{value});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByWarehouseId(Object warehouseId) {
		return findByProperty(WAREHOUSE_ID, warehouseId);
	}

	public List findByWarehouseName(Object warehouseName) {
		return findByProperty(WAREHOUSE_NAME, warehouseName);
	}

	public List findByTypeBigCode(Object typeBigCode) {
		return findByProperty(TYPE_BIG_CODE, typeBigCode);
	}

	public List findByTypeBigName(Object typeBigName) {
		return findByProperty(TYPE_BIG_NAME, typeBigName);
	}

	public List findByTypeSmallCode(Object typeSmallCode) {
		return findByProperty(TYPE_SMALL_CODE, typeSmallCode);
	}

	public List findByTypeSmallName(Object typeSmallName) {
		return findByProperty(TYPE_SMALL_NAME, typeSmallName);
	}

	public List findByCreateUserId(Object createUserId) {
		return findByProperty(CREATE_USER_ID, createUserId);
	}

	public List findByUpdateUserId(Object updateUserId) {
		return findByProperty(UPDATE_USER_ID, updateUserId);
	}

	public List findByDeleteUserId(Object deleteUserId) {
		return findByProperty(DELETE_USER_ID, deleteUserId);
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

	public List findAll() {
		log.debug("finding all StoreWarehouseAll instances");
		try {
			String queryString = "from StoreWarehouseAll where isDeleted=0";
			return this.getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreWarehouseAll merge(StoreWarehouseAll detachedInstance) {
		log.debug("merging StoreWarehouseAll instance");
		try {
			StoreWarehouseAll result = (StoreWarehouseAll) this.getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreWarehouseAll instance) {
		log.debug("attaching dirty StoreWarehouseAll instance");
		try {
			this.getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreWarehouseAll instance) {
		log.debug("attaching clean StoreWarehouseAll instance");
		try {
			this.getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}