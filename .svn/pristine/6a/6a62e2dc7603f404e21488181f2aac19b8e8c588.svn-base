package org.hpin.warehouse.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.ErpSupplier;
import org.springframework.stereotype.Repository;

/**
 * 供货商dao
 * @author tangxing
 * @date 2017-2-7上午10:55:12
 */

@Repository
public class ErpSupplierDao extends BaseDao{

private static final Logger log = Logger.getLogger(ErpSupplierDao.class);
	
	public List findByPage(Page page, Map searchMap) {
		/*StringBuffer query = new StringBuffer(" from ErpSupplier where 1=1 ");
		searchMap.put("order_createTime", "desc");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		log.info("ErpSupplierDao findByPage valueList size : " + valueList.size()+"=======");
		return valueList;*/
		
		StringBuffer query = new StringBuffer(" from ErpSupplier where 1=1 ");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        log.info("ErpSupplierDao findByPage valueList size : " + valueList.size()+"=======");
        return super.findByHql(page, query, valueList);
	}
	
	/**
	 * 根据ID获取ErpSupplier
	 * @param supplierId
	 * @return
	 * @author tangxing
	 * @date 2017-2-7下午2:18:13
	 */
	public ErpSupplier getSupplierById(String supplierId){
		return this.getHibernateTemplate().get(ErpSupplier.class, supplierId);
	}
	
	/**
	 * 根据条件导出excel
	 * @param hql
	 * @return
	 * @author tangxing
	 * @date 2017-2-7下午5:36:22
	 */
	public List<ErpSupplier> exportExcelList(String hql){
		return this.getHibernateTemplate().find(hql);
	}
	
    /**
     * 根据regionId获取省份城市
     * @param regionId
     * @return
     * @author tangxing
     * @date 2017-2-6下午1:30:24
     */
    public List<Map<String, Object>> getRegionById(String regionId){
    	String sql = "select hr.region_name as regionName from hl_region hr where hr.id = ? ";
    	return this.getJdbcTemplate().queryForList(sql, new Object[]{regionId});
    }
	
}
