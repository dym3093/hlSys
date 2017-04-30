package org.hpin.venueStaffSettlement.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.venueStaffSettlement.dao.ErpConferenceCostExceptionDao;
import org.hpin.venueStaffSettlement.entity.ErpConferenceCostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "org.hpin.venueStaffSettlement.service.ErpConferenceCostExceptionService")
@Transactional()
public class ErpConferenceCostExceptionService extends BaseService {
	
	@Autowired
	private ErpConferenceCostExceptionDao dao ;
	private Logger logger = Logger.getLogger(ErpConferenceCostExceptionService.class);

	public List findByPage(Page page,Map map){
		return dao.findByPage( page, map);
	}
	
}
