package org.hpin.statistics.briefness.service;

import org.hpin.base.usermanager.dao.UserDao;
import org.hpin.common.core.orm.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "customerTempService")
@Transactional()
public class CustomerTempService extends BaseService{
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 
	 * <p>Description: </p>
	 * @author herny.xu
	 * @date 2017年3月24日
	 */
	public boolean updateCustomerInfo(String code) throws Exception {
		String udSqlTemp = "update erp_customer_temp_wuchuang set NAME = NAME || '-close', IDNO = IDNO || '-close' where code = '"+code+"' ";
		String udSqlReceive = "update erp_customer_receive set USERNAME = USERNAME || '-close', SERVICECODE = SERVICECODE || '-close' where SERVICECODE = '"+code+"' ";
		
		this.userDao.getJdbcTemplate().update(udSqlTemp);
		
		this.userDao.getJdbcTemplate().update(udSqlReceive);
		
		return true;
	}
	
}
