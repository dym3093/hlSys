package org.hpin.events.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;
@Repository()
public class ErpExpressDao extends BaseDao {

	/**
	 * 是否已发送快递
	 * @param eventsNo
	 * @return
	 */
	public String isNotRepeatNo(String trackingNumber){
		String repeat="";
		String sql = "from ErpExpress where trackingNumber=?";
		List list = this.getHibernateTemplate().find(sql,trackingNumber);
		if(list.size()>0){
			repeat="快递单号已存在！";
		}
		return repeat;
	}
}
