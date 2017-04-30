package org.hpin.warehouse.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.exception.MyException;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.dao.ErpApplicationDetailDao;
import org.hpin.warehouse.dao.ErpProductDao;
import org.hpin.warehouse.dao.ErpStoregeInDao;
import org.hpin.warehouse.entity.ErpProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 产品信息service
 * create by henry.xu 20161008
 */
@Service
@Transactional
public class ErpProductService extends BaseService{
	
	@Autowired
	private ErpProductDao erpProductDao;
	@Autowired
	private ErpStoregeInDao erpStoregeInDao;
	@Autowired
	private ErpApplicationDetailDao erpApplicationDetailDao;

	/**
	 * 验证名字是否存在;
	 * @param productId
	 * @param productName
	 * @return false 已存在不能进行保存, true不存在;
	 */
	public boolean validProductNameIsExits(String productId, String productName) {
		List<String> objs = new ArrayList<String>();
		StringBuilder jdbcSql = new StringBuilder(" select count(1) from erp_product where product_name = ? ");
		objs.add(productName);
		//排除新增的情况;
		if(StringUtils.isNotEmpty(productId)) {
			jdbcSql.append(" and id <> ? ");
			objs.add(productId);
		}
		
		long count = this.erpProductDao.getJdbcTemplate().queryForLong(jdbcSql.toString(), objs.toArray());
		/*
		 * 当count 大于零,标示数据看存在相同的名称;
		 */
		if(count > 0) {
			return false ;
		}
		
		return true;
	}
	
	/**
	 * 查询分页数据;
	 * @param page
	 * @param params["productType产品类型, productName产品名称"]
	 */
	@SuppressWarnings("rawtypes")
	public void findErpProductByPage(Page page, Map<String, String> params) {
		List<Object> objs = new ArrayList<Object>();
		//sql
		StringBuilder jdbcSql = new StringBuilder("select " +
				"pro.id, " +
				"pro.PRODUCT_TYPE productType, " +
				"dict.DICTNAME productTypeName," +
				"pro.DESCRIBE, " +
				"pro.PRODUCT_NAME productName, " +
				"pro.STANDARD, " +
				"pro.IMAGE_PATH imagePath, " +
				"pro.IS_CLOSE isClose " +
				"from ERP_PRODUCT pro " +
				"left join SYS_DICTTYPE dict on pro.PRODUCT_TYPE = dict.DICTCODE Where pro.IS_DELETED=0 ");
		
		//参数处理
		if(params != null) {
			if(params.get("productName") != null && StringUtils.isNotEmpty(params.get("productName"))) {
				jdbcSql.append(" AND pro.PRODUCT_NAME like '%").append(params.get("productName").trim()).append("%' ");
			}
			
			if(params.get("productType") != null && StringUtils.isNotEmpty(params.get("productType"))) {
				jdbcSql.append(" AND pro.PRODUCT_TYPE = '").append(params.get("productType")).append("' ");
			}
			
			String isClose = params.get("isClose"); //0启用, 1关闭;
			if(StringUtils.isNotEmpty(isClose)) {
				jdbcSql.append(" and pro.IS_CLOSE = '").append(isClose).append("'"); 
			}
		}
		
		//count
		Long count = this.erpProductDao.findJdbcCount(jdbcSql, objs);
		page.setTotalCount(count);
		
		//order
		jdbcSql.append(" order by pro.create_time, pro.id desc ");
		
		//list
		
		objs.add(page.getPageNumEndCount());
		objs.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpProduct> rowMapper = new BeanPropertyRowMapper<ErpProduct>(ErpProduct.class);
		List<?> list = this.erpProductDao.findJdbcList(jdbcSql, objs, rowMapper);
		page.setResults(list);
		
	}
	
	/**
	 * 保存产品信息;
	 * @param product
	 * @param userInfo
	 * @throws Exception
	 */
	public void saveProduct(ErpProduct product, User userInfo) throws Exception {
		product.setCreateTime(new Date());
		product.setCreateUserId(userInfo.getId());
		product.setProductName(product.getProductName().trim()); //去掉空格符号;
		product.setIsDeleted(0); //默认有效为0
		erpProductDao.save(product);
	}
	/**
	 * 修改产品信息;
	 * @param product
	 * @param userInfo
	 * @throws Exception
	 */
	public void updateProduct(ErpProduct product, User userInfo, String isUpload) throws Exception {
		//查找原有数据;
		ErpProduct oldData = findErpProductById(product.getId());
		
		if(oldData != null) {
			//判断
			
			//设置数据;
			oldData.setUpdateTime(new Date());
			oldData.setUpdateUserId(userInfo.getId());
			if("1".equals(isUpload)) {
				//同时删除原有图片;
				String filePath = oldData.getImagePath();
				if(StringUtils.isNotEmpty(filePath)) {
					File file = new File(filePath);
					file.delete();
				}
				
				oldData.setImagePath(product.getImagePath());		
				
			}
			oldData.setDescribe(product.getDescribe());
			oldData.setProductName(product.getProductName());
			oldData.setProductType(product.getProductType());
			oldData.setStandard(product.getStandard());
			
			erpProductDao.update(oldData);
		} else {
			throw new MyException("原有数据为空,请检查该数据; 表ERP_PRODUCT 字段id为"+product.getId());
		}
	}
	
	/**
	 * 根据ID查询对象
	 * @param id
	 * @return
	 */
	public ErpProduct findErpProductById(String id) {
		return (ErpProduct) erpProductDao.findById(id);
	}

	/**
	 * 根据选中id进行删除;
	 * @param ids
	 * @return 成功true, 否则为false
	 */
	public boolean deleteProductById(String ids) {
		if(StringUtils.isNotEmpty(ids)) {
			String[] idsArr = ids.split(",");
			StringBuilder jdbcsql = new StringBuilder("update ERP_PRODUCT SET IS_DELETED = 1 WHERE id in (");
			
			if(idsArr != null && idsArr.length > 0) {
				for(int i=0; i<idsArr.length; i++) {
					if(i == 0) {
						jdbcsql.append(" '").append(idsArr[i].trim()).append("' ");		
					} else {
						jdbcsql.append(", '").append(idsArr[i].trim()).append("' ");					
					}
				}
				
			} else {
				jdbcsql.append(" '").append(ids).append("' ");	
			}
			jdbcsql.append(")");
			this.erpProductDao.getJdbcTemplate().update(jdbcsql.toString());
			
			return true;
		}
		
		return false;
	}

	/**
	 * 选择一条产品，点击【删除】按钮，请验证当前商品是否已经被入库表引用，以及是否被申请表的子表引用。如果有引用，则不允许删除
	 * @param ids
	 * @return
	 */
	public boolean validDel(String ids) {
		StringBuilder idSql = new StringBuilder();
		if(StringUtils.isNotEmpty(ids)) {
			String arr[] = ids.split(",");
			for(int i = 0; i<arr.length; i++) {
				if(StringUtils.isNotEmpty(arr[i])) {
					if(i == 0) {
						idSql.append(" '").append(arr[i].trim()).append("' ");
					} else {
						idSql.append(", '").append(arr[i].trim()).append("' ");
					}
					
				}
			}
		}
		
		int count = 0;
		//>>1. 当前商品是否已经被入库表引用
		count = this.erpStoregeInDao.findStoregeInUseProductCount(idSql.toString());
		
		if(count > 0) {
			return false;
		}
		
		//>>2. 以及是否被申请表的子表引用
		count = this.erpApplicationDetailDao.findAppDetailUseProductCount(idSql.toString());
		if(count > 0) {
			return false;
		}
		
		return true;
	}

	/**
	 * 是否关闭或者开启该数据;
	 * @param id
	 * @param status 0启用, 1关闭;
	 */
	public void dealIsClose(String id, String status) throws Exception {
		
		if(StringUtils.isEmpty(status)) {
			throw new MyException("status参数为不能空!");
		}
		
		String sql = "update erp_product set is_close = '"+status+"' where id='"+id+"'";
		this.erpProductDao.getJdbcTemplate().update(sql);
	}

}
