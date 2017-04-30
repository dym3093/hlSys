package org.hpin.warehouse.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.dao.StoreTypeDAO;
import org.hpin.warehouse.dao.StoreWarehouseDAO;
import org.hpin.warehouse.entity.StoreType;
import org.hpin.warehouse.entity.StoreWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service(value = "org.hpin.warehouse.service.StoreWarehouseService")
@Transactional()
public class StoreWarehouseService extends BaseService{
	@Autowired
	private StoreWarehouseDAO dao;
	@Autowired
	private StoreTypeDAO typeDao;
	
	/**
	 * 条件查询;
	 * @param page
	 * @param params
	 * @param flag 是否分页; true 分页查询, false不分页;
	 */ 
	@SuppressWarnings("rawtypes")
	public void findStoreWarehouseByPage(Page page,
			HashMap<String, String> params, boolean flag) {
		//参数
		List<Object> objs = new ArrayList<Object>();
		
		//sql
		StringBuilder jdbcSql = this.dao.dealWarehouseSqlByParams(params);
		
		//count
		long count = this.dao.findJdbcCount(jdbcSql, objs);
		page.setTotalCount(count);
		
		//排序
		jdbcSql.append(" order by create_time desc ");
		
		//list
		if(flag) {
			objs.add(page.getPageNumEndCount());
			objs.add(page.getPageNumStartCount());
			
		} else {
			objs.add(Integer.MAX_VALUE); //最大值;
			objs.add(0);
		}
		BeanPropertyRowMapper<StoreWarehouse> rowMapper = new BeanPropertyRowMapper<StoreWarehouse>(StoreWarehouse.class);
		List<?> list = this.dao.findJdbcList(jdbcSql, objs, rowMapper);
		page.setResults(list);
		
	}
	
	
	public void deleteWareHouse(String[] id, String accountName, Date deleteTime) {
		for(int  i = 0 ; i<id.length ; i++){
			StoreWarehouse storewarehouse = (StoreWarehouse) dao.findById(StoreWarehouse.class, id[i]);
			storewarehouse.setIsDeleted(1);
			storewarehouse.setDeleteUserId(accountName);
			storewarehouse.setDeleteTime(deleteTime);
			dao.update(storewarehouse);
			List<StoreType> links = typeDao.findByProperty(StoreType.class, "remark", id[i], null, null);
			for(int j=0;j<links.size();j++){
				StoreType l = links.get(j);
				l.setDeleteTime(deleteTime);
				l.setIsDeleted(1);
				l.setDeleteUserName(accountName);
				typeDao.update(l);
			}
		}
	}
	public void updateWarehouse(StoreWarehouse s){
		dao.saveOrUpdate(s);
	}
	public List findByStoreWarehouse() {
		StringBuffer hql = new StringBuffer();
		hql.append("from StoreWarehouse c where c.isDeleted=0 order by c.createTime ");
		List<StoreWarehouse> storeWarehouseList = dao.findByHql(hql);
		return storeWarehouseList;
	}
	public List findByStoreWarehouse(String id) {
		StringBuffer hql = new StringBuffer();
		hql.append("from StoreWarehouse c where c.isDeleted=0 and id='"+id+"' order by c.createTime ");
		List<StoreWarehouse> storeWarehouseList = dao.findByHql(hql);
		return storeWarehouseList;
	}

	/**
	 * create by henry.xu 20161019
	 * 验证查询;
	 */
	public boolean validNameRepeat(String id, String name) {
		boolean flag = true;
		int count = this.dao.validNameRepeat(id, name);
		if(count > 0) {
			flag = false ;
		}
		return flag ;
	}
	
}
