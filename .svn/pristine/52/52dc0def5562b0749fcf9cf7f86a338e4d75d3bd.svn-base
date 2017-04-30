package org.hpin.venueStaffSettlement.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.venueStaffSettlement.entity.ErpConferenceCostException;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpConferenceCostExceptionDao extends BaseDao {

	public List<ErpConferenceCostException> findByPage(Page page,Map map) {
		StringBuffer query = new StringBuffer(" from ErpConferenceCostException where 1=1");
		map.put("order_createTime" , "desc") ;
	    List valueList = new ArrayList();
	    OrmConverter.assemblyQuery(query, map, valueList);
	    return super.findByHql(page, query, valueList);
	}
	
}
