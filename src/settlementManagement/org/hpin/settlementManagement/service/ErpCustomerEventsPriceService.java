/**
 * @author DengYouming
 * @since 2016-5-23 下午3:30:14
 */
package org.hpin.settlementManagement.service;

import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseService;
import org.hpin.settlementManagement.dao.ErpCustomerEventsPriceDao;
import org.hpin.settlementManagement.entity.ErpCustomerEventsPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DengYouming
 * @since 2016-5-23 下午3:30:14
 */
@Service("org.hpin.settlementManagement.service.ErpCustomerEventsPriceService")
public class ErpCustomerEventsPriceService extends BaseService{

	@Autowired
	private ErpCustomerEventsPriceDao dao;
	
	/**
	 * 根据参数查询
	 * @param params 
	 * @param exact 是否精确查找，true:是， false:否
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-23 下午3:01:24
	 */
	public List<ErpCustomerEventsPrice> listByProps(Map<String, Object> params, boolean exact) throws Exception{
		return dao.listByProps(params, exact);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-23 下午3:32:30
	 */
	public boolean save(ErpCustomerEventsPrice entity) throws Exception{
		return dao.save(entity);
	}
}
