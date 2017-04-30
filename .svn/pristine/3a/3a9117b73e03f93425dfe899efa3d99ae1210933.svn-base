package org.hpin.base.usermanager.service;

import java.util.List;
import java.util.Map;

import org.hpin.base.usermanager.dao.GroupDao;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional()
@Service(value = "org.hpin.base.usermanager.service.GroupService")
public class GroupService extends BaseService {
	@Autowired
	private GroupDao groupDao = null;

	/**
	 * 删除组
	 * 
	 * @param ids
	 */
	public void deleteIds(String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			groupDao.delete(new Long(idArray[i]));
		}
	}

	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 */
	public List findByPage(Page page, Map searchMap) {
		return groupDao.findByPage(page, searchMap);
	}
}
