package org.hpin.base.customerrelationship.dao;

import java.util.List;

import org.hpin.base.customerrelationship.entity.CustomerRelationshipLink;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;
@Repository
public class CustomerRelationshipLinkDao extends BaseDao {

	public List<CustomerRelationshipLink> findByCustomerRelationShipId(String hql, String customerId) {
		return this.getHibernateTemplate().find(hql, customerId);
	}
}
