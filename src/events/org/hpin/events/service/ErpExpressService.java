package org.hpin.events.service;

import org.hpin.common.core.orm.BaseService;
import org.hpin.events.dao.ErpExpressDao;
import org.hpin.events.entity.ErpExpress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service(value = "org.hpin.events.service.ErpExpressService")
@Transactional()
public class ErpExpressService extends BaseService {
	@Autowired
	private ErpExpressDao dao; 
	
	/**
	 * 保存
	 * @param erpExpress
	 */
	public void save(ErpExpress erpExpress){
		dao.save(erpExpress);
	}
	
	public String isNotRepeatNo(String trackingNumber){
		return dao.isNotRepeatNo(trackingNumber);
	}
}
