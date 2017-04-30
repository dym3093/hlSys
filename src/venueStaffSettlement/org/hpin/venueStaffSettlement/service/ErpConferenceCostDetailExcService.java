package org.hpin.venueStaffSettlement.service;

import org.hpin.common.core.orm.BaseService;
import org.hpin.venueStaffSettlement.dao.ErpNonConferenceCostDetailExcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "org.hpin.venueStaffSettlement.service.ErpConferenceCostDetailExcService")
@Transactional()
public class ErpConferenceCostDetailExcService extends BaseService {
	
	@Autowired
	private ErpNonConferenceCostDetailExcDao dao ;

	
}
