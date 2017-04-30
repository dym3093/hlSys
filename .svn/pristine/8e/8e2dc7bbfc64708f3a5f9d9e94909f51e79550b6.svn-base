package org.hpin.venueStaffSettlement.service;

import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.venueStaffSettlement.dao.EventsCostManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.venueStaffSettlement.service.EventsCostManageService")
@Transactional()

public class EventsCostManageService extends BaseService {
	@Autowired
	EventsCostManageDao dao;
	
	/**
	 * 分页获取用户
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		return dao.findByPage(page, searchMap);
	}
	
}
