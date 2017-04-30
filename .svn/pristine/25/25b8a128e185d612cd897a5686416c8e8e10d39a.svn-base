/**
 * @author DengYouming
 * @since 2016-5-17 下午5:01:02
 */
package org.hpin.venueStaffSettlement.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ErpEventsComboPrice;
import org.hpin.venueStaffSettlement.entity.ErpEventsStaffCost;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 场次人员费用 Dao
 * @author DengYouming
 * @since 2016-5-17 下午5:01:02
 */
@Repository
public class ErpEventsStaffCostDao extends BaseDao {

	/**
	 * 获取分页数据
	 * @param page
	 * @param searchMap
	 * @return List
	 * @author DengYouming
	 * @since 2016-5-17 下午5:01:41
	 */
    public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpEventsStaffCost where 1=1");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    /**
     * 分页获取对象
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage3(Page page, Map searchMap,String eventsId) {
        StringBuffer query = new StringBuffer(" from ErpEventsStaffCost where eventsId='"+eventsId+"'");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    /**
     * 批量保存
     * @param list
     * @return boolean
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-18 下午2:49:07
     */
    public boolean saveList(List<ErpEventsStaffCost> list) throws Exception{
    	boolean flag = false;
    	if(list!=null&&list.size()>0){
	    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	    	Transaction tx = session.beginTransaction();
	    	tx.begin();
	    	ErpEventsStaffCost entity = null;
	    	for (int i=0; i<list.size(); i++) {
	    		entity = list.get(i);
/*	    		if(entity.getId()!=null&&entity.getId().trim().length()>0){
	    			session.update(list.get(i));
	    		}else{
	    			session.save(list.get(i));
	    		}*/
	    		session.saveOrUpdate(entity);
	    		if(i!=0&&i%100==0){
					session.flush();
					session.clear();
	    		}
			}
			session.flush();
			session.clear();
	    	tx.commit();
//			if(session!=null){
//				session.close();
//			}
			flag = true;
    	}
    	return flag;
    }
    
    /**
     * 验证是否存在List
     * @param params
     * @return List
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-18 下午3:46:32
     */
    public List<ErpEventsStaffCost> checkExist(Map<String, Object> params) throws Exception{
    	List<ErpEventsStaffCost> list = null;
    	if(!CollectionUtils.isEmpty(params)){
	    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	    	Criteria criteria = session.createCriteria(ErpEventsStaffCost.class);
	    	
	    	//场次号
	    	String eventsId = (String) params.get(ErpEventsStaffCost.F_EVENTSID);
	      	if(StringUtils.isNotEmpty(eventsId)){
	    		criteria.add(Restrictions.eq(ErpEventsStaffCost.F_EVENTSID, eventsId));
	    	}
	    	//场次号
	    	String eventsNo = (String) params.get(ErpEventsStaffCost.F_EVENTSNO);
	      	if(StringUtils.isNotEmpty(eventsNo)){
	    		criteria.add(Restrictions.eq(ErpEventsStaffCost.F_EVENTSNO, eventsNo));
	    	}
	    	//员工名
	    	String staff = (String) params.get(ErpEventsStaffCost.F_STAFF);
	    	if(StringUtils.isNotEmpty(staff)){
	    		criteria.add(Restrictions.eq(ErpEventsStaffCost.F_STAFF, staff));
	    	}
	    	//员工职能
	    	String position = (String) params.get(ErpEventsStaffCost.F_POSITION);
	    	if(StringUtils.isNotEmpty(position)){
	    		criteria.add(Restrictions.eq(ErpEventsStaffCost.F_POSITION, position));
	    	}
	    	list = criteria.list();
//	    	if(session!=null){
//	    		session.close();
//	    	}
    	}
    	return list;
    }
    
}
