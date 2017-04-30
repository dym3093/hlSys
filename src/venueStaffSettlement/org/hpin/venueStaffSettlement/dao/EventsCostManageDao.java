package org.hpin.venueStaffSettlement.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.springframework.stereotype.Repository;

@Repository()
public class EventsCostManageDao extends BaseDao {

	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from ErpEvents where 1=1 ");
		searchMap.put("order_eventDate", "desc");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}
	
}
