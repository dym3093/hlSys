/**
 * @author DengYouming
 * @since 2016-7-15 下午12:07:16
 */
package org.hpin.warehouse.service;

import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseService;
import org.hpin.warehouse.dao.StoreApplicationDetailDao;
import org.hpin.warehouse.entity.StoreApplicationDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 物品申请表明细Service
 * @author DengYouming
 * @since 2016-7-15 下午12:07:16
 */
@Service(value="org.hpin.warehouse.service.StoreApplicationDetailService")
@Transactional
public class StoreApplicationDetailService extends BaseService {

	@Autowired
	private StoreApplicationDetailDao dao;
	
	/**
	 * 根据条件查询
	 * @param params 传入的参数
	 * @return List 
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-7-15 下午12:08:39
	 */
	public List<StoreApplicationDetail> listByProps(Map<String,Object> params) throws Exception{
		List<StoreApplicationDetail> list = null;
		if(!CollectionUtils.isEmpty(params)){
			list = dao.listByProps(params);
		}
		return list;
	}
	
}
