package org.hpin.reportdetail.service;

import java.util.List;

import org.hpin.common.core.orm.BaseService;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.reportdetail.dao.ErpComboRelationDao;
import org.hpin.reportdetail.entity.ErpComboRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.reportdetail.service.ErpComboRelationService")
@Transactional()
public class ErpComboRelationService extends BaseService {

	@Autowired
	ErpComboRelationDao dao;
	
	/**
	 * @param syCombo 套餐
	 * @return 根据截取的申友套餐获取对用的远盟套餐
	 */
	public List<ErpComboRelation> getYmCombo(String syCombo) {
		return dao.getYmCombo(syCombo);
	}

	/**
	 * @param code
	 * @param name
	 * @return 根据条码和客户姓名去获取该用户信息
	 */
	public List<ErpCustomer> getCustomerComboInfo(String code, String name) {
		return dao.getCustomerComboInfo(code,name);
	}

	
}
