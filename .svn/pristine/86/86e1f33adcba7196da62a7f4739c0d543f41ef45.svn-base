package org.hpin.warehouse.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.ErpProduct;
import org.springframework.stereotype.Repository;
/**
 * 产品信息Dao
 * create by henry.xu 20161008
 */
@Repository
public class ErpProductDao extends BaseDao {
	
	public void save(ErpProduct prduct) {
		this.getHibernateTemplate().save(prduct);
	}
	
	public void update(ErpProduct prduct) {
		this.getHibernateTemplate().update(prduct);
	}
	
	public ErpProduct findById(String id) {
		List<?> list = findByHql(new StringBuffer("from ErpProduct where id=? "), id);
		return list != null && list.size() > 0 ? (ErpProduct)list.get(0) : null;
	}
}
