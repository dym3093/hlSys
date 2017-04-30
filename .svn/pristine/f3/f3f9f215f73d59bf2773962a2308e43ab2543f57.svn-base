package org.hpin.warehouse.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.dao.ErpStoregeReturnDao;
import org.hpin.warehouse.entity.ErpProduct;
import org.hpin.warehouse.entity.StoreWarehouse;
import org.hpin.warehouse.entity.vo.ErpStoregeReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author machuan
 * @date 2017年3月21日
 */
@Service
@Transactional
public class ErpStoregeReturnService extends BaseService{
	
	@Autowired
	private ErpStoregeReturnDao erpStoregeReturnDao ; //退货Dao
	
	/**
	 * 根据条件分页查询;
	 * @param params [开始日期，结束日期]
	 */
	@SuppressWarnings("rawtypes")
	public void findStoregeReturnsByPage(Page page, User userInfo,
			HashMap<String, String> params) {
		List<Object> objs = new ArrayList<Object>();
		StringBuilder jdbcSql = this.erpStoregeReturnDao.dealJdbcSqlByParams(params);
		
		//count;
		long count = this.erpStoregeReturnDao.findJdbcCount(jdbcSql, objs);
		page.setTotalCount(count);
		
		//list;
		objs.add(page.getPageNumEndCount());
		objs.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpStoregeReturnVo> rowMapper = new BeanPropertyRowMapper<ErpStoregeReturnVo>(ErpStoregeReturnVo.class);
		List<?> list = this.erpStoregeReturnDao.findJdbcList(jdbcSql, objs, rowMapper);
		page.setResults(list);
	}

	/**
	 * @return
	 * @author machuan
	 * @date  2017年3月21日
	 */
	public List<StoreWarehouse> getStoreWareHouse() {
		String hql = "from StoreWarehouse sw where sw.isDeleted=0";
		return erpStoregeReturnDao.getHibernateTemplate().find(hql);
	}
	/**
	 * @return
	 * @author machuan
	 * @date  2017年3月21日
	 */
	public List<ErpProduct> getProduct() {
		String hql = "from ErpProduct ep where ep.isDeleted=0";
		return erpStoregeReturnDao.getHibernateTemplate().find(hql);
	}
}
