package org.hpin.warehouse.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.dao.ErpProductComboDao;
import org.hpin.warehouse.entity.ErpProComboProduct;
import org.hpin.warehouse.entity.ErpProduct;
import org.hpin.warehouse.entity.ErpProductCombo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

@Service
public class ErpProductComboService extends BaseService {
	
	@Autowired
	private ErpProductComboDao erpProductComboDao; //产品套餐Dao

	/**
	 * 保存产品套餐数据
	 * 
	 * @param proCombo 产品套餐对象
	 * @param userInfo User对象
	 * @param proComboProducts 套餐对应产品
	 */
	public void saveProCombo(ErpProductCombo proCombo, User userInfo,
			List<ErpProComboProduct> proComboProducts) {
		proCombo.setCreateTime(new Date());
		proCombo.setCreateUserId(userInfo.getId());
		proCombo.setIsDeleted(0); //默认为0有效, 1删除
		proCombo.setIsClose("0"); //默认为0启用, 1关闭;
		//保存产品套餐数据;
		this.erpProductComboDao.saveProCombo(proCombo);
		
		//保存中间表
		String proComboId = proCombo.getId();
		if(proComboProducts != null && proComboProducts.size() > 0) {
			
			for(int i=0; i<proComboProducts.size(); i++) {
				ErpProComboProduct proComboProduct = proComboProducts.get(i);
				if(proComboProduct != null) {
					proComboProduct.setProComboId(proComboId);
					proComboProduct.setId(null);
					this.erpProductComboDao.saveProComboProduct(proComboProduct);
				}
				
			}
		}
		
	}

	/**
	 * 修改产品套餐数据;
	 * @param proCombo 产品套餐对象
	 * @param userInfo User对象
	 * @param proComboProducts 套餐对应产品
	 */
	public void updateProCombo(ErpProductCombo proCombo, User userInfo,
			List<ErpProComboProduct> proComboProducts) {
		//获取原有数据;
		ErpProductCombo old = (ErpProductCombo)this.erpProductComboDao.findById(ErpProductCombo.class, proCombo.getId());
		//修改主数据;
		old.setUpdateTime(new Date());
		old.setUpdateUserId(userInfo.getId());
		old.setProductComboName(proCombo.getProductComboName());
		old.setDeclare(proCombo.getDeclare());
		old.setComboType(proCombo.getComboType());
		
		this.erpProductComboDao.updateProCombo(old);
		
		//删除原有关联数据;
		this.erpProductComboDao.deleteProComboProduct(proCombo.getId());
		
		//重新保存; 保存中间表
		String proComboId = proCombo.getId();
		if(proComboProducts != null && proComboProducts.size() > 0) {
			
			for(int i=0; i<proComboProducts.size(); i++) {
				ErpProComboProduct proComboProduct = proComboProducts.get(i);
				if(proComboProduct != null) {
					proComboProduct.setProComboId(proComboId);
					proComboProduct.setId(null);
					this.erpProductComboDao.saveProComboProduct(proComboProduct);
				}
				
			}
		}
	}

	/**
	 * 分页查询.根据时间倒序
	 * @param page
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void findErpProComboByPage(Page page, HashMap<String, String> params) {
		//参数;
		List<Object> objs = new ArrayList<Object>();
		//sql
		StringBuilder jdbcSql = this.erpProductComboDao.dealErpProComboSqlByParam(params);
		//count
		long count = this.erpProductComboDao.findJdbcCount(jdbcSql, objs);
		page.setTotalCount(count);
		
		//排序;
		jdbcSql.append(" order by create_time desc");
		
		//list
		objs.add(page.getPageNumEndCount());
		objs.add(page.getPageNumStartCount());
		
		BeanPropertyRowMapper<ErpProductCombo> rowMapper = new BeanPropertyRowMapper<ErpProductCombo>(ErpProductCombo.class);
		List<?> results = this.erpProductComboDao.findJdbcList(jdbcSql, objs, rowMapper);
		//处理产品明细数据;用于前台显示;
		if(results != null && results.size() > 0) {
			for(int i=0; i<results.size(); i++) {
				String id = ((ErpProductCombo) results.get(i)).getId();
				((ErpProductCombo) results.get(i)).setProDetails(findProComboProductsById(id));
			}
		}
		
		page.setResults(results);
		
	}

	/**
	 * 根据Id与状态处理close状态;
	 * @param id
	 */
	public void dealIsClose(String id, String status, User userInfo) {
		
		List<Object> objs = new ArrayList<Object>();
		objs.add(status);
		objs.add(userInfo.getId());
		objs.add(id);
		String sql = "update erp_product_combo set is_close=?, update_user_id=?, update_time=sysdate where id=? ";
		this.erpProductComboDao.getJdbcTemplate().update(sql, objs.toArray());
	}

	/**
	 * 通过套餐Id获取对应的产品明细;
	 * @param id
	 * @return
	 */
	public List<ErpProduct> findProComboProductsById(String id) {
		StringBuilder jdbcsql = this.erpProductComboDao.dealErpProComboProductSql(id);
		BeanPropertyRowMapper<ErpProduct> rowMapper = new BeanPropertyRowMapper<ErpProduct>(ErpProduct.class);
		jdbcsql.append(" order by product.create_time desc ");
		return this.erpProductComboDao.getJdbcTemplate().query(jdbcsql.toString(), rowMapper);
	}

	public boolean deleteValid(String ids, User userInfo) {
		//参数;
		List<Object> objs = new ArrayList<Object>();
		objs.add("1");
		objs.add(userInfo.getId());
		
		if(StringUtils.isEmpty(ids)) {
			return false;
		}
		
		String[] arrs = ids.split(",");
		String id = "";
		for(int i =0; i<arrs.length; i++) {
			id = arrs[i];
			if(StringUtils.isNotEmpty(id)) {
				objs.add(id.trim());
				
				//获取数据查看是否被使用;
				String sqlUse = "select count(1) from ERP_APPLICATION_DETAIL where PROCOMBO_ID = ? ";
				int count = this.erpProductComboDao.getJdbcTemplate().queryForInt(sqlUse, id.trim());
				//当count为0标示可以删除; 否则不能删除;
				if(count == 0) {
					//如果没有被使用则删除;否则返回false;
					String sql = "update erp_product_combo set is_deleted=?, update_user_id=?, update_time=sysdate where id=? ";
					this.erpProductComboDao.getJdbcTemplate().update(sql, objs.toArray());					
				} else {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * 当存在数据重复返回true, 否则返回false标示没有重复;
	 * @param id
	 * @param productComboName
	 * @return
	 */
	public boolean exitsObject(String id, String productComboName) {
		
		List<Object> objs = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder("select count(1) from erp_product_combo where is_deleted='0' and PRODUCT_COMBO_NAME = ? ");
		objs.add(productComboName);

		//新增的时候id为空; 修改的时候id不为空
		if(StringUtils.isNotEmpty(id)) {
			sql.append(" and id not in (?) ");
			objs.add(id);
		}
		
		int count = this.erpProductComboDao.getJdbcTemplate().queryForInt(sql.toString(), objs.toArray());
		
		//当count> 0标示数据中存在重名;
		if(count > 0) {
			return true;
		}
		
		return false;
	}
	

	
}
