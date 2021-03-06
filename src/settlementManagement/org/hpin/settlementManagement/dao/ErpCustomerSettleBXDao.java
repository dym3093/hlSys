package org.hpin.settlementManagement.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.settlementManagement.entity.ErpCustomerSettleBX;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 保险公司套餐价格表DAO: ERP_COMPANY_COMBO_PRICE 
 * @author DengYouming
 * @since 2016-5-4 下午4:31:47
 */
@Repository()
public class ErpCustomerSettleBXDao extends BaseDao {
	
	/**
	 * 根据id修改实际价格;
	 * @param objs
	 */
	public void updateCustomerTask(Object ...objs) {
		String sql = "UPDATE ERP_CUSTOMER_SETTLE_BX SET "
				+ "COMBO_PRICE_ACTUAL=?, REMARK=?, "
				+ "UPDATE_USER_ID=?, "
				+ "UPDATE_USER=?,"
				+ "UPDATE_TIME=? WHERE ID=? AND status <> '-1' "; //modify by Damian 2017-03-16
		// 由status=0 改为status<> '-1',价格有可能在任何结算节点进行设置
		this.getJdbcTemplate().update(sql, objs);
	}
	
	/**
	 * 根据id修改实际价格;
	 * @param objs
	 */
	public void updateCustomerTaskPrice(Object ...objs) {
		String sql = "UPDATE ERP_CUSTOMER_SETTLE_BX SET "
				+ "COMBO_PRICE_ACTUAL=?, REMARK=?, DISCOUNT=?, "
				+ "UPDATE_USER_ID=?, "
				+ "UPDATE_USER=?, "
				+ "UPDATE_TIME=? WHERE SETTLE_ID=? and EVENTS_NO=? and COMBO=? AND status <> '-1' "; //modify by Damian 2017-03-16
		// 由status=0 改为status<> '-1',价格有可能在任何结算节点进行设置
		this.getJdbcTemplate().update(sql, objs);
	}

	/**
	 * 批量保存CustomerToSettleBx
	 * @param list
	 * @return int 保存的数量
	 * @author DengYouming
	 * @since 2016-5-9 下午5:25:49
	 */
	public int saveOrUpdateList(List<ErpCustomerSettleBX> list) throws Exception{
		int count = 0;
		if(list!=null&&list.size()>0){
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			for (int i = 0; i < list.size(); i++) {
				session.saveOrUpdate(list.get(i));
				if(i!=0&&i%100==0){
					session.flush();
					session.clear();
				}
				count ++;
			}
			session.flush();
			session.clear();
		}
		return count;
	}
   
	/**
	 * 根据条件查询
	 * @param params
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-9 下午8:38:29
	 */
	public List<ErpCustomerSettleBX> listByProps(Map<String,Object> params) throws Exception{
		List<ErpCustomerSettleBX> list = null;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ErpCustomerSettleBX.class);
		if(StringUtils.isNotEmpty((String)params.get(ErpCustomerSettleBX.F_SETTLEID))){
			criteria.add(Restrictions.eq(ErpCustomerSettleBX.F_SETTLEID, (String)params.get(ErpCustomerSettleBX.F_SETTLEID)));
		}
		//状态
		if(StringUtils.isNotEmpty((String)params.get(ErpCustomerSettleBX.F_STATUS))){
			criteria.add(Restrictions.eq(ErpCustomerSettleBX.F_STATUS, (String)params.get(ErpCustomerSettleBX.F_STATUS)));
		}
		list = criteria.list();
		return list;
	}
	
	/**
	 * 根据条件查询
	 * @param params
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-9 下午8:38:29
	 */
	public List<ErpCustomerSettleBX> listByProps2(Map<String,Object> params) throws Exception{
		List<ErpCustomerSettleBX> list;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ErpCustomerSettleBX.class);
		if(StringUtils.isNotEmpty((String)params.get(ErpCustomerSettleBX.F_SETTLEID))){
			criteria.add(Restrictions.eq(ErpCustomerSettleBX.F_SETTLEID, (String)params.get(ErpCustomerSettleBX.F_SETTLEID)));
		}
		//状态
		if(StringUtils.isNotEmpty((String)params.get(ErpCustomerSettleBX.F_STATUS))){
			criteria.add(Restrictions.ne(ErpCustomerSettleBX.F_STATUS, (String)params.get(ErpCustomerSettleBX.F_STATUS)));
		}
		list = criteria.list();
		return list;
	}
		
	/**
	 * 条件分页查询
	 * @param page
	 * @param searchMap
	 * @return List
	 * @author DengYouming
	 * @since 2016-5-10 下午4:05:16
	 */
    public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpCustomerSettleBX where 1=1 and status<>'-1' ");
        searchMap.put("id", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
        
    }
	

    /**
     * 根据结算任务ID删除客户结算信息(逻辑删除)
     * @param settleId
     * @return
     * @throws Exception
     * @author DengYouming
     * @since 2016-6-14 下午4:34:39
     */
    public boolean updateStatusById(String settleId) throws Exception{
    	boolean flag = false;
    	if(settleId!=null&&settleId.length()>0){
    		String sqlDelete = " update ERP_CUSTOMER_SETTLE_BX set status = '-1' where SETTLE_ID = '"+settleId+"'";
			this.getJdbcTemplate().update(sqlDelete);
    		flag = true;
    	}
    	return flag;
    }
    
    /**
     * 根据传入的ID数组查找相关数据
     * @param ids
     * @return List
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-25 下午4:51:16
     */
    public List<ErpCustomerSettleBX> listByIds(String[] ids) throws Exception{
    	List<ErpCustomerSettleBX> list = null;
    	if(ids!=null&&ids.length>0){
	    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Criteria criteria = session.createCriteria(ErpCustomerSettleBX.class);
			criteria.add(Restrictions.in(ErpCustomer.F_ID, ids));	
			list = criteria.list();
    	}
    	return list;
    }

    /**
     * 根据code查找已结算的ErpCustomerSettleBX
     * @param code
     * @return
     * @author tangxing
     * @date 2016-9-1下午5:00:04
     */
    public List<ErpCustomerSettleBX> getErpCustomerSettleBXByCode(String code){
    	String query = "from ErpCustomerSettleBX where code=? and status='2'";
    	return this.getHibernateTemplate().find(query, new Object[]{code});
    }
    
}
