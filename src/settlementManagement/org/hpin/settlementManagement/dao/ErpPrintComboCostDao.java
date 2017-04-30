package org.hpin.settlementManagement.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.springframework.stereotype.Repository;
@Repository()
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ErpPrintComboCostDao extends BaseDao {

	public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpPrintComboCost where 1=1 and isdeleted=0");
        List valueList = new ArrayList();
        searchMap.put("order_createTime","desc");
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
}
