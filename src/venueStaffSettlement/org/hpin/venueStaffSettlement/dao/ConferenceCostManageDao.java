package org.hpin.venueStaffSettlement.dao;

import java.util.ArrayList;
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
import org.hpin.venueStaffSettlement.entity.ErpConferenceStaffCost;
import org.hpin.venueStaffSettlement.entity.ErpEventsStaffCost;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository()
public class ConferenceCostManageDao extends BaseDao {


    /**
     * 分页获取对象
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpConference where 1=1");
        searchMap.put("order_conferenceDate", "desc");
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
    public List findByPage2(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpConferenceStaffCost where 1=1");
        searchMap.put("order_createTime", "desc");
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
    public List findByPage3(Page page, Map searchMap,String conferenceId) {
        StringBuffer query = new StringBuffer(" from ErpConferenceStaffCost where conferenceId='"+conferenceId+"'");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    
    /**
     * 验证是否存在List
     * @param params
     * @return
     * @throws Exception
     * @author tangxing
     * @date 2016-5-19下午1:46:29
     */
    public List<ErpConferenceStaffCost> checkExist(Map<String, Object> params) throws Exception{
    	List<ErpConferenceStaffCost> list = null;
    	if(!CollectionUtils.isEmpty(params)){
	    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	    	Criteria criteria = session.createCriteria(ErpConferenceStaffCost.class);
	    	
	    	//会议ID
	    	String conferenceId = (String) params.get("conferenceId");
	      	if(StringUtils.isNotEmpty(conferenceId)){
	    		criteria.add(Restrictions.eq("conferenceId", conferenceId));
	    	}
	    	//会议号
	    	String conferenceNo = (String) params.get("conferenceNo");
	      	if(StringUtils.isNotEmpty(conferenceNo)){
	    		criteria.add(Restrictions.eq("conferenceNo", conferenceNo));
	    	}
	    	//员工名
	    	String staff = (String) params.get("staff");
	    	if(StringUtils.isNotEmpty(staff)){
	    		criteria.add(Restrictions.eq("staff", staff));
	    	}
	    	//员工职能
	    	String position = (String) params.get("position");
	    	if(StringUtils.isNotEmpty(position)){
	    		criteria.add(Restrictions.eq("position", position));
	    	}
	    	list = criteria.list();
//	    	if(session!=null){
//	    		session.close();
//	    	}
    	}
    	return list;
    }
    
    /**
     * 批量保存
     * @param list
     * @return boolean
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-18 下午2:49:07
     */
    public boolean saveList(List<ErpConferenceStaffCost> list) throws Exception{
    	boolean flag = false;
    	if(list!=null&&list.size()>0){
	    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	    	Transaction tx = session.beginTransaction();
	    	tx.begin();
	    	ErpConferenceStaffCost entity = null;
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
	
}
