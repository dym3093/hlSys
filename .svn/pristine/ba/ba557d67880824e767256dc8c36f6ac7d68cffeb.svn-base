/**
 * 
 */
package org.hpin.events.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpConference;
import org.springframework.stereotype.Repository;

/**
* 公司名称: 远盟康健(北京)科技有限公司
* 
* author: dengqin 
* createDate: 2016-3-29 下午12:27:05
*/
/**
 * @author dengqin
 *
 */

@Repository()
public class ErpConferenceDao extends BaseDao {

    /**
     * 分页获取对象
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
//      StringBuffer query = new StringBuffer(" from ErpEvents where isDeleted=0  and isEnable = 1");//没有isEnable
        StringBuffer query = new StringBuffer(" from ErpConference where 1=1");
        //searchMap.put(" eventsNo", "desc");
        searchMap.put("order_conferenceDate", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    /*
     * 统计所有场次每个场次人数
     */
    public List getAllInputNumsList(String userName){
        List list=null;
        String queryString="select conference_no,count(*) as total from ERP_CONFERENCE where is_deleted=0 group by conference_no";
        list = this.getJdbcTemplate().queryForList(queryString);
         return list;
    }
    
    /**
     * 是否存在已有的会议
     * @param compannyName 支公司名称 eventDate 场次日期
     * @return
     */
    public String isNotRepeatBycompanyIdAndDate(String compannyName,String ownedCompany,Date conferneceDate,String address){
        String repeat="";
        String hql = "from ErpConference where branch_company =? and owned_company = ? and conference_date = ? and is_deleted=0 AND address=?";
//      String hql = "from ErpEvents where branch_company =? and owned_company = ? and event_date = ? and is_deleted=0 ";
        System.out.println("校验场次："+hql);
        List list = this.getHibernateTemplate().find(hql,new Object[]{compannyName,ownedCompany,conferneceDate,address});
//      List list = this.getHibernateTemplate().find(hql,new Object[]{compannyName,ownedCompany,eventDate});
        if(list.size()>0){
            repeat="该时间已经存在场次";
        }
        return repeat;
    }
    
    public ErpConference get(String id){
        return (ErpConference)this.findById(ErpConference.class, id);
    }
    
    /**
     * 根据场次号统计每个场次人数
     * @param conferenceNo
     * @return
     */
    public Integer getCustomerNums(String conferenceNo) {
        String queryString = "select count(*) from ERP_CUSTOMER where  IS_DELETED=0 and CONFERENCE_NO=?";
        Integer count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{conferenceNo});
        return count;
    }
    
    /**
     * 查询当天最大场次号
     * @param date
     * @return
     */
    public String maxNo(String date){
        String conferenceNo="HL1512142048000";
        String sql = "select max(conference_no) as conference_no from Erp_Conference where to_char(conference_Date,'yyyy-mm-dd')='"+date+"'";
        List list = this.getJdbcTemplate().queryForList(sql);
        Map map = (Map) list.get(0);
        String maxNo = (String) map.get("CONFERENCE_NO");
        if(StrUtils.isNotNullOrBlank(maxNo)){
            conferenceNo=maxNo.toString();
        }
        return conferenceNo;
    }
    
    /**
     * 根据条件查询相关场次
     * @param params 传入的条件
     * @return
     * @author tangxing
     * @date 2016-5-19上午10:45:57
     */
	public List<ErpConference> listConferenceByProps(Map<String,Object> params){
		List<ErpConference> list = null;
		String id = (String)params.get("id");
		String conferenceNo = (String)params.get("conferenceNo");
		
		/*Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ErpConference.class);
		if(params.get(id)!=null){
			criteria.add(Restrictions.eq(id, params.get(id)));
		}
		if(params.get(conferenceNo)!=null){
			criteria.add(Restrictions.like(conferenceNo, (String)params.get(conferenceNo), MatchMode.ANYWHERE));
		}
		//未删除，按创建日期倒序
		criteria.add(Restrictions.eq(isDeleted, Constants.STATUS_NEW_INT)).addOrder(Order.desc(createTime));
		System.out.println(criteria.toString());
		list = criteria.list();
		session.close();*/
        
		String queryString="from ErpConference where isDeleted='0' and conferenceNo=? and id=? order by createTime desc";
    	return this.getHibernateTemplate().find(queryString, new Object[]{conferenceNo,id});
	}
    
}
