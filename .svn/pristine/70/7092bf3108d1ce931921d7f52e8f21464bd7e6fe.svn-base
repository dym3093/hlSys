package org.hpin.warehouse.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hpin.base.dict.exceptions.DictDAOException;
import org.hpin.base.dict.util.Util;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.StoreType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreType entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.hpin.warehouse.StoreType
 * @author MyEclipse Persistence Tools
 */
@Repository(value="org.hpin.warehouse.dao.StoreTypeDAO")
public class StoreTypeDAO extends BaseDao implements org.hpin.base.dict.dao.ID2NameDAO{
	private static final Logger log = LoggerFactory
			.getLogger(StoreTypeDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String STANDARDS = "standards";
	public static final String DESCRIPE = "descripe";
	public static final String CREATE_USER_ID = "createUserId";
	public static final String CREATE_USER_NAME = "createUserName";
	public static final String UPDATE_USER_ID = "updateUserId";
	public static final String UPDATE_USER_NAME = "updateUserName";
	public static final String DELETE_USER_ID = "deleteUserId";
	public static final String DELETE_USER_NAME = "deleteUserName";
	public static final String REMARK = "remark";
	public static final String REMARK1 = "remark1";
	public static final String REMARK2 = "remark2";
	public static final String REMARK3 = "remark3";

	public void save(StoreType transientInstance) {
		log.debug("saving StoreType instance");
		try {
			this.getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
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
	public void delete(StoreType persistentInstance) {
		log.debug("deleting StoreType instance");
		try {
			this.getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public String id2Name(final String id) throws DictDAOException {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = " from StoreType type where type.id=:dictId";
				Query query = session.createQuery(queryStr);
				query.setString("dictId", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				query.setCacheable(true) ;
				List list = query.list();
				StoreType dictType = null;

				if (list != null && !list.isEmpty()) {
					dictType = (StoreType) list.iterator().next();
				} else {
					dictType = new StoreType();
					dictType.setName(Util.idNoName());
				}
				return dictType;
			}
		};

		StoreType dictType = null;
		try {
			dictType = (StoreType) getHibernateTemplate().execute(
					callback);
		} catch (Exception e) {
			throw new DictDAOException(e);
		}
		return dictType.getName();
	}
	public StoreType findById(java.lang.String id) {
		log.debug("getting StoreType instance with id: " + id);
		try {
			StoreType instance = (StoreType) this.getHibernateTemplate().get(
					"org.hpin.warehouse.StoreType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*public List findByExample(StoreType instance) {
		log.debug("finding StoreType instance by example");
		try {
			List results = this.getHibernateTemplate()
					.createCriteria("org.hpin.warehouse.StoreType")
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
		log.debug("finding StoreType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from StoreType as model where model."
					+ propertyName + "= ? and model.isDeleted=0";
			return this.getHibernateTemplate().find(queryString, new Object[]{value});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByStandards(Object standards) {
		return findByProperty(STANDARDS, standards);
	}

	public List findByDescripe(Object descripe) {
		return findByProperty(DESCRIPE, descripe);
	}

	public List findByCreateUserId(Object createUserId) {
		return findByProperty(CREATE_USER_ID, createUserId);
	}

	public List findByCreateUserName(Object createUserName) {
		return findByProperty(CREATE_USER_NAME, createUserName);
	}

	public List findByUpdateUserId(Object updateUserId) {
		return findByProperty(UPDATE_USER_ID, updateUserId);
	}

	public List findByUpdateUserName(Object updateUserName) {
		return findByProperty(UPDATE_USER_NAME, updateUserName);
	}

	public List findByDeleteUserId(Object deleteUserId) {
		return findByProperty(DELETE_USER_ID, deleteUserId);
	}

	public List findByDeleteUserName(Object deleteUserName) {
		return findByProperty(DELETE_USER_NAME, deleteUserName);
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
		log.debug("finding all StoreType instances");
		try {
			String queryString = "from StoreType where isDeleted=0";
			return this.getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreType merge(StoreType detachedInstance) {
		log.debug("merging StoreType instance");
		try {
			StoreType result = (StoreType) this.getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreType instance) {
		log.debug("attaching dirty StoreType instance");
		try {
			this.getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreType instance) {
		log.debug("attaching clean StoreType instance");
		try {
			this.getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	@Override
	public String id2Field(String id, String beanId, String field)
			throws DictDAOException {
		// TODO Auto-generated method stub
		return null;
	}
}