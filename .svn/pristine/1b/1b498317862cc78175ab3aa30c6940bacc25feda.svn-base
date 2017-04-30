package org.hpin.warehouse.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hpin.base.dict.entity.SysDictType;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.StoreApplicationDetail;
import org.hpin.warehouse.entity.StoreProduceDetail;
import org.hpin.warehouse.entity.StoreType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreProduceDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.hpin.warehouse.StoreProduceDetail
 * @author MyEclipse Persistence Tools
 */
@Repository(value="org.hpin.warehouse.dao.StoreProduceDetailDAO")
public class StoreProduceDetailDAO extends BaseDao {
	private static final Logger log = LoggerFactory
			.getLogger(StoreProduceDetailDAO.class);
	// property constants
	public static final String TYPE_BIG_CODE = "typeBigCode";
	public static final String TYPE_BIG_NAME = "typeBigName";
	public static final String TYPE_SMALL_CODE = "typeSmallCode";
	public static final String TYPE_SMALL_NAME = "typeSmallName";
	public static final String PRICE = "price";
	public static final String TOTAL_PRICE = "totalPrice";
	public static final String WAREHOUSE_ID = "warehouseId";
	public static final String WAREHOUSE_NAME = "warehouseName";
	public static final String PRODUCE_ID = "produceId";
	public static final String REMARK = "remark";
	public static final String REMARK1 = "remark1";
	public static final String REMARK2 = "remark2";
	public static final String REMARK3 = "remark3";

	public void save(StoreProduceDetail transientInstance) {
		log.debug("saving StoreProduceDetail instance");
		try {
			this.getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoreProduceDetail persistentInstance) {
		log.debug("deleting StoreProduceDetail instance");
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
	public StoreProduceDetail findById(java.lang.String id) {
		log.debug("getting StoreProduceDetail instance with id: " + id);
		try {
			StoreProduceDetail instance = (StoreProduceDetail) this.getHibernateTemplate()
					.get("org.hpin.warehouse.StoreProduceDetail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*public List findByExample(StoreProduceDetail instance) {
		log.debug("finding StoreProduceDetail instance by example");
		try {
			List results = this.getHibernateTemplate()
					.createCriteria("org.hpin.warehouse.StoreProduceDetail")
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
		log.debug("finding StoreProduceDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StoreProduceDetail as model where model."
					+ propertyName + "= ? and model.isDeleted=0";
			return this.getHibernateTemplate().find(queryString, new Object[]{value});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
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

	public List findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	public List findByTotalPrice(Object totalPrice) {
		return findByProperty(TOTAL_PRICE, totalPrice);
	}

	public List findByWarehouseId(Object warehouseId) {
		return findByProperty(WAREHOUSE_ID, warehouseId);
	}

	public List findByWarehouseName(Object warehouseName) {
		return findByProperty(WAREHOUSE_NAME, warehouseName);
	}

	public List findByProduceId(Object produceId) {
		return findByProperty(PRODUCE_ID, produceId);
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
		log.debug("finding all StoreProduceDetail instances");
		try {
			String queryString = "from StoreProduceDetail where isDeleted=0";
			return this.getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreProduceDetail merge(StoreProduceDetail detachedInstance) {
		log.debug("merging StoreProduceDetail instance");
		try {
			StoreProduceDetail result = (StoreProduceDetail) this.getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreProduceDetail instance) {
		log.debug("attaching dirty StoreProduceDetail instance");
		try {
			this.getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreProduceDetail instance) {
		log.debug("attaching clean StoreProduceDetail instance");
		try {
			this.getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List findByPage(Page page,Map searchMap) {
		StringBuffer query = new StringBuffer(" from StoreType where 1=1 and is_deleted=0");
		List valueList = new ArrayList();
	    OrmConverter.assemblyQuery(query, searchMap, valueList);
	    return super.findByHql(page, query, valueList);
	}

	public List<StoreApplicationDetail> getApplyDetails(String id) {
//		StringBuffer query = new StringBuffer(" from StoreApplicationDetail where 1=1 and id_related='"+id+"'");
//		List valueList = new ArrayList();
//	    OrmConverter.assemblyQuery(query, searchMap, valueList);
//	    return super.findByHql(page, query, valueList);
		String sql1 = " from StoreApplicationDetail where 1=1 and id_related=?";
		String sql2 = " from SysDictType where 1=1 and dictid=?";
		List<StoreApplicationDetail> applyDetailsList = this.getHibernateTemplate().find(sql1,id);
		List<StoreApplicationDetail> list = new ArrayList<StoreApplicationDetail>();
		
		for (int i=0;i<applyDetailsList.size();i++) {
//			if (applyDetailsList.get(i).getApplyNum()==0) {
//				continue;
//			}
			StoreApplicationDetail detail = new StoreApplicationDetail();
			List<SysDictType> detailsList = this.getHibernateTemplate().find(sql2,applyDetailsList.get(0).getTypeBigCode());
			detail.setTypeName(detailsList.get(0).getDictName());
			detail.setPrdouceName(applyDetailsList.get(i).getPrdouceName());
			detail.setStandards(applyDetailsList.get(i).getStandards());
			detail.setDescripe(applyDetailsList.get(i).getDescripe());
			detail.setApplyNum(applyDetailsList.get(i).getApplyNum());
			list.add(detail);
		}
		return list;
		
	}

	public List<StoreType> getStoreType(String remark) {
		String sql = "from StoreType where is_deleted=0 and remark=?";
		return this.getHibernateTemplate().find(sql,remark);
	}

	/**
	 * @param remark1
	 * @return 品类
	 */
	public String getTypeName(String remark1) {
		String sql ="select dictname from sys_dicttype where dictid='"+remark1+"'";
		return this.getJdbcTemplate().queryForList(sql).get(0).get("DICTNAME").toString();
	}

	/**
	 * @param remark	仓库
	 * @param remark1	品类id
	 * @return
	 */
	public Integer getCount(String remark, String remark1) {
		String sql = "select count from store_warehouse_all where warehouse_id='"+remark+"' and type_big_code='"+remark1+"'";
		return this.getJdbcTemplate().queryForInt(sql);
	}

	/**
	 * @param remark4
	 * @return 根据发货的remark找到对应的品类规格
	 */
	public List<StoreType> getStoreTypeInfo(String remark4) {
		String sql = "from StoreType where id=?";
		return this.getHibernateTemplate().find(sql,remark4);
	}
}