/**
 * @author DengYouming
 * @since 2016-5-16 下午2:30:26
 */
package org.hpin.settlementManagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hpin.base.usermanager.entity.User;
import org.hpin.common.util.HttpTool;
import org.hpin.settlementManagement.dao.ErpEventsComboPriceDao;
import org.hpin.settlementManagement.entity.ErpEventsComboPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author DengYouming
 * @since 2016-5-16 下午2:30:26
 */
@Service(value = "org.hpin.settlementManagement.service.ErpEventsComboPriceService")
@Transactional()
public class ErpEventsComboPriceService {

	@Autowired
	private ErpEventsComboPriceDao erpEventsComboPriceDao;
	
	/**
	 * 批量保存
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-16 下午2:32:30
	 */
	public boolean saveList(List<ErpEventsComboPrice> list) throws Exception{
		boolean flag = false;
		if(!CollectionUtils.isEmpty(list)){
			 //获取当前用户
			User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
			List<ErpEventsComboPrice> tempList = new ArrayList<ErpEventsComboPrice>();
			Map<String,Object> params = new HashMap<String, Object>();
			for (ErpEventsComboPrice entity : list) {
				params.clear();
				//场次号
				params.put(ErpEventsComboPrice.F_EVENTSNO, entity.getEventsNo());
				//套餐名称
				params.put(ErpEventsComboPrice.F_COMBO, entity.getCombo());
				List<ErpEventsComboPrice> existList = erpEventsComboPriceDao.listErpEventsComboPriceByProps(params);
				//未找到相关套餐记录,则为新增
				if(CollectionUtils.isEmpty(existList)){
					entity.setCreateTime(new Date());
					entity.setCreateUser(currentUser.getUserName());
					entity.setCreateUserId(currentUser.getCreateUserId());
					tempList.add(entity);
				}else{
					ErpEventsComboPrice exitsObj = existList.get(0);
					exitsObj.setComboPrice(entity.getComboPrice());
					exitsObj.setUpdateTime(new Date());
					exitsObj.setUpdateUser(currentUser.getUserName());
					exitsObj.setUpdateUserId(currentUser.getCreateUserId());
					tempList.add(exitsObj);
				}
				
			}
			flag = erpEventsComboPriceDao.saveList(tempList);
		}
		return flag;
	}
}
