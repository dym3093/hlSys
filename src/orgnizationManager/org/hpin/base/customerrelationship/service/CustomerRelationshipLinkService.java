package org.hpin.base.customerrelationship.service;
import java.util.Date;
import java.util.List;

import org.hpin.base.customerrelationship.dao.CustomerRelationshipDao;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipLinkDao;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.CustomerRelationshipLink;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service(value = "org.hpin.base.customerrelationship.service.CustomerRelationshipLinkService")
@Transactional()
public class CustomerRelationshipLinkService extends BaseService {
	@Autowired()
	CustomerRelationshipLinkDao clDao = null;
	@Autowired
	CustomerRelationshipDao cDao = null;
	
	public void deleteCustomerRelationShip(String [] ids,String accountName,Date deleteTime) {
		for(int i=0;i< ids.length;i++){
			CustomerRelationshipLink customerRelationshipLink = (CustomerRelationshipLink) clDao.findById(CustomerRelationshipLink.class,ids[i]);
			customerRelationshipLink.setIsDeleted(1);
			customerRelationshipLink.setDeleteUserId(accountName);
			customerRelationshipLink.setDeleteTime(deleteTime);
			clDao.update(customerRelationshipLink);
		}
	}
	
	public void save(CustomerRelationshipLink customerRelationshipLink) {
		CustomerRelationShip CustomerRelationShip = (CustomerRelationShip)cDao.findById(CustomerRelationShip.class, customerRelationshipLink.getCustomerRelationShipId());
		CustomerRelationShip.getCustomerRelationshipLinkSet().add(customerRelationshipLink);
		/*customerRelationshipLink.setCustomerRelationShip(CustomerRelationShip);*/
		clDao.save(customerRelationshipLink);
	}

	public void update(CustomerRelationshipLink customerRelationshipLink) {
		CustomerRelationShip customerRelationShip = (CustomerRelationShip)cDao.findById(CustomerRelationShip.class, customerRelationshipLink.getCustomerRelationShipId());
		CustomerRelationshipLink link = (CustomerRelationshipLink)clDao.findById(CustomerRelationshipLink.class, customerRelationshipLink.getId());
		ReflectionUtils.copyPropertiesForHasValue(link, customerRelationshipLink);
		/*link.setCustomerRelationShip(customerRelationShip);*/
		clDao.update(link);
	}

	public List<CustomerRelationshipLink> findByCustomerRelationShipId(String CustomerRelationShipId) {
		String hql = "from CustomerRelationshipLink where customerRelationShip=? ";
		List<CustomerRelationshipLink> customerRelationshipLink = (List<CustomerRelationshipLink>)clDao.findByCustomerRelationShipId(hql,CustomerRelationShipId);
		return customerRelationshipLink;
	}

	/**
	 * @param companyId 
	 * @param dept
	 * @return 根据支公司和部门获取寄送信息
	 */
	public List<CustomerRelationshipLink> getBranchCompanyExpressInfo(String companyId,String dept) {
		String sql = "from CustomerRelationshipLink where customerRelationShipId=? and department=?";
		return clDao.getHibernateTemplate().find(sql,companyId,dept);
	}
	
}
