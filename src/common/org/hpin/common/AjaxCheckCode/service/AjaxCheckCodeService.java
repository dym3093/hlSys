package org.hpin.common.AjaxCheckCode.service;

import java.util.ArrayList;
import java.util.List;

import org.hpin.common.AjaxCheckCode.dao.AjaxCheckCodeDao;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.log.entity.ModifyHistory;
import org.hpin.common.log.entity.ModifyHistoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.common.AjaxCheckCode.service.AjaxCheckCodeService")
@Transactional()
public class AjaxCheckCodeService extends BaseService {
	@Autowired()
	AjaxCheckCodeDao dao;
	public boolean findByCode(String beanName, String propertyName, String propertyValue) {
		//List list = dao.findByProperty(beanName, propertyName, propertyValue, null, null);
		StringBuffer hql = new StringBuffer();
		hql.append("from "+beanName+" where "+propertyName+"=? and isDeleted=0");
		List<?> list = dao.findByHql(hql, propertyValue);
		return list.size()>0?true:false;
	}
	public boolean findNoemerByCode(String beanName, String propertyName, String propertyValue,String propertyName2,String propertyValue2) {
		//List list = dao.findByProperty(beanName, propertyName, propertyValue, null, null);
		StringBuffer hql = new StringBuffer();
		hql.append("from "+beanName+" where "+propertyName+"=? and "+propertyName2+"=? and isDeleted=0");
		List<?>  list = dao.findByHql(hql, propertyValue,propertyValue2);
		return list.size()>0?true:false;
	}

	public boolean findHisByName(String beanName, String propertyName, String propertyValue,String propertyName2,String propertyValue2,
			String propertyName3,String propertyValue3) {
		String sql = "SELECT COUNT(*) FROM HL_CUSTOMER_RELATIONSHIP WHERE BRANCH_COMMANY = '"+propertyValue+"' AND CITEY ='"+propertyValue2+"' AND OWNED_COMPANY='"+propertyValue3+"' AND IS_DELETED=0";
		int total = dao.getJdbcTemplate().queryForInt(sql);
		System.out.println(sql);
		System.out.println(total);
		/*StringBuffer hql = new StringBuffer();
		hql.append("from "+beanName+" where "+propertyName+"= ? and "+propertyName2+" = ? and "+propertyName3+" = ? and isDeleted=0");
		List<?>  list = dao.findByHql(hql, propertyValue,propertyValue2,propertyValue3);
		dao.findbyhql*/
		return total>0?true:false;
	}
	
	public List findModifyLogItem(String id) {
		StringBuffer hql = new StringBuffer();
		StringBuffer hql1 = new StringBuffer();
		List<ModifyHistoryItem> mhtList = new ArrayList();
		hql.append("from ModifyHistory mh where mh.state is null and mh.parentPrimaryKey = ?");
		List<ModifyHistory> list = dao.findByHql(hql, id);
		String mId = "";
		if(list != null && list.size() > 0) {
			for (ModifyHistory mh : list) {
				mId += ",'" + mh.getId() + "'";
			}
		}
		if (mId.length() > 0) {
			mId = mId.substring(1);
			hql1.append("select field_name,old_value from (select field_name,old_value,")
			    .append("row_number() over(partition by field_name order by create_Time) rn ")
			    .append("from LOG_HISTORY_ITEM  where history_id in ("+mId+") ) t ")
			    .append("where t.rn = 1 ");
			mhtList = dao.findModifyLogItem(hql1.toString());
		}
		return mhtList;
	}
}
