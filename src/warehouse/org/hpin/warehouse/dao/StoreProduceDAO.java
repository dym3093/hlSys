package org.hpin.warehouse.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.StoreProduce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreProduce entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.hpin.warehouse.StoreProduce
 * @author MyEclipse Persistence Tools
 */
@Repository(value="org.hpin.warehouse.dao.StoreProduceDAO")
public class StoreProduceDAO extends BaseDao {
	private static final Logger log = LoggerFactory
			.getLogger(StoreProduceDAO.class);
	// property constants
	public static final String CREATE_USER_ID = "createUserId";
	public static final String CREATE_USER_NAME = "createUserName";
	public static final String WAREHOUSE_ID = "warehouseId";
	public static final String WAREHOUSE_NAME = "warehouseName";
	public static final String BANNY_COMPANNY_ID = "bannyCompannyId";
	public static final String BANNY_COMPANNY_NAME = "bannyCompannyName";
	public static final String EVENT_NOS = "eventNos";
	public static final String EMS_NO = "emsNo";
	public static final String RECEIVE_NAME = "receiveName";
	public static final String RECEIVE_TEL = "receiveTel";
	public static final String EMS_NAME = "emsName";
	public static final String ADDRESS = "address";
	public static final String BUSINESS_NAME = "businessName";
	public static final String BUSINESS_ID = "businessId";
	public static final String REMARK = "remark";
	public static final String REMARK1 = "remark1";
	public static final String REMARK2 = "remark2";
	public static final String REMARK3 = "remark3";
	public static final String APPLICATION_ID = "applicationId";

	public void save(StoreProduce transientInstance) {
		log.debug("saving StoreProduce instance");
		try {
			this.getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoreProduce persistentInstance) {
		log.debug("deleting StoreProduce instance");
		try {
			this.getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StoreProduce findById(java.lang.String id) {
		log.debug("getting StoreProduce instance with id: " + id);
		try {
			StoreProduce instance = (StoreProduce) this.getHibernateTemplate().get(
					"org.hpin.warehouse.StoreProduce", id);
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
	/*public List findByExample(StoreProduce instance) {
		log.debug("finding StoreProduce instance by example");
		try {
			List results = this.getHibernateTemplate()
					.createCriteria("org.hpin.warehouse.StoreProduce")
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
		log.debug("finding StoreProduce instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StoreProduce as model where model."
					+ propertyName + "= ? and model.isDeleted=0";
			return this.getHibernateTemplate().find(queryString, new Object[]{value});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCreateUserId(Object createUserId) {
		return findByProperty(CREATE_USER_ID, createUserId);
	}

	public List findByCreateUserName(Object createUserName) {
		return findByProperty(CREATE_USER_NAME, createUserName);
	}

	public List findByWarehouseId(Object warehouseId) {
		return findByProperty(WAREHOUSE_ID, warehouseId);
	}

	public List findByWarehouseName(Object warehouseName) {
		return findByProperty(WAREHOUSE_NAME, warehouseName);
	}

	public List findByBannyCompannyId(Object bannyCompannyId) {
		return findByProperty(BANNY_COMPANNY_ID, bannyCompannyId);
	}

	public List findByBannyCompannyName(Object bannyCompannyName) {
		return findByProperty(BANNY_COMPANNY_NAME, bannyCompannyName);
	}

	public List findByEventNos(Object eventNos) {
		return findByProperty(EVENT_NOS, eventNos);
	}

	public List findByEmsNo(Object emsNo) {
		return findByProperty(EMS_NO, emsNo);
	}

	public List findByReceiveName(Object receiveName) {
		return findByProperty(RECEIVE_NAME, receiveName);
	}

	public List findByReceiveTel(Object receiveTel) {
		return findByProperty(RECEIVE_TEL, receiveTel);
	}

	public List findByEmsName(Object emsName) {
		return findByProperty(EMS_NAME, emsName);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByBusinessName(Object businessName) {
		return findByProperty(BUSINESS_NAME, businessName);
	}

	public List findByBusinessId(Object businessId) {
		return findByProperty(BUSINESS_ID, businessId);
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

	public List findByApplicationId(Object applicationId) {
		return findByProperty(APPLICATION_ID, applicationId);
	}

	public List findAll() {
		log.debug("finding all StoreProduce instances");
		try {
			String queryString = "from StoreProduce where isDeleted=0";
			return this.getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreProduce merge(StoreProduce detachedInstance) {
		log.debug("merging StoreProduce instance");
		try {
			StoreProduce result = (StoreProduce) this.getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreProduce instance) {
		log.debug("attaching dirty StoreProduce instance");
		try {
			this.getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreProduce instance) {
		log.debug("attaching clean StoreProduce instance");
		try {
			this.getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}