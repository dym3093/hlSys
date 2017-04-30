package org.hpin.events.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.ErpCustomerSync;
import org.springframework.stereotype.Repository;


@Repository()
public class ErpCustomerSyncDao extends BaseDao {
	
	/**
	 * 根据id判断sync表中是否有重复数据
	 * @param id
	 * @return
	 * @author machuan
	 * @date  2016年11月28日
	 */
	@SuppressWarnings("unchecked")
	public ErpCustomerSync getCustomerSyncByCId(String id) {
		String hql = "from ErpCustomerSync where customerId=? and status=0";
		List<ErpCustomerSync> list = this.getHibernateTemplate().find(hql,new Object[]{id});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
