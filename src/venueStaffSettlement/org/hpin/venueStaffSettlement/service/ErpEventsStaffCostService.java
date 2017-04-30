/**
 * @author DengYouming
 * @since 2016-5-17 下午4:57:34
 */
package org.hpin.venueStaffSettlement.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.venueStaffSettlement.dao.ErpEventsStaffCostDao;
import org.hpin.venueStaffSettlement.entity.ErpEventsStaffCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 场次人员费用 Service
 * @author DengYouming
 * @since 2016-5-17 下午4:57:34
 */
@Service(value = "org.hpin.venueStaffSettlement.service.ErpEventsStaffCostService")
@Transactional
public class ErpEventsStaffCostService extends BaseService {

	@Autowired
	private ErpEventsStaffCostDao erpEventsStaffCostDao;
	
	
	/**
	 * 分页查找
	 * @param page
	 * @param searchMap
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-17 下午5:08:24
	 */
	public List listErpEventsStaffCost(Page page, Map searchMap) throws Exception{
		
		return erpEventsStaffCostDao.findByPage(page, searchMap);
	}
	
	/**
	 * 分页获取用户(场次人员费用)
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage3(Page page, Map searchMap,String eventsId) {
		return erpEventsStaffCostDao.findByPage3(page, searchMap,eventsId);
	}

	/**
	 * 批量保存ErpEventsStaffCost
	 * @param list
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-18 下午2:46:49
	 */
	public boolean saveList(List<ErpEventsStaffCost> entityList) throws Exception{
		boolean flag = false;
		if(entityList!=null&&entityList.size()>0){
			flag = erpEventsStaffCostDao.saveList(entityList);
		}
		return flag;
	}
	
	/**
	 * 验证是否存在
	 * @param params
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-18 下午4:15:31
	 */
	public ErpEventsStaffCost checkExist(Map<String, Object> params) throws Exception{
		ErpEventsStaffCost entity = null;
		List<ErpEventsStaffCost> list = null;
    	if(!CollectionUtils.isEmpty(params)){
    		list = erpEventsStaffCostDao.checkExist(params);
    	}
    	if(!CollectionUtils.isEmpty(list)){
    		entity = list.get(0);
    	}
    	return entity;
	}
	
	
}
