package org.hpin.settlementManagement.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ComboHistoryPrice;
import org.hpin.settlementManagement.entity.ErpEventsComboPrice;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
/**
 * 保险公司套餐价格表DAO: ERP_COMPANY_COMBO_PRICE 
 * @author DengYouming
 * @since 2016-5-4 下午4:31:47
 */
@Repository()
public class ErpEventsComboPriceDao extends BaseDao {

    /**
     * 分页获取对象
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpEventsComboPrice where 1=1");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    
    /**
     * 根据ID获取套餐价
     * @param id
     * @return ErpEventsComboPrice
     */
    public ErpEventsComboPrice findById(String id){
        return (ErpEventsComboPrice)this.findById(ErpEventsComboPrice.class, id);
    }
    
    /**
     * 删除数据
     * @param comboPrice
     */
    public void delete(ErpEventsComboPrice comboPrice){
    	super.delete(comboPrice);
    }
    
    /**
     * 增加
     * @param comboPrice
     */
    public void add(ErpEventsComboPrice comboPrice){
    	super.save(comboPrice);
    }
    
    /**
     * 修改
     * @param comboPrice
     */
    public void update(ErpEventsComboPrice comboPrice){
    	super.update(comboPrice);
    }
    
    /**
     * 查找套餐价格集合
     * @return
     */
    public List listErpEventsComboPrice(){
        List list=null;
        final String queryString="from ErpEventsComboPrice where status=?";
        list = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				query = session.createQuery(queryString);
			    query.setParameter(0, "0",Hibernate.STRING);   
				return query.list();
			}
		});
        return list;
    }
    
    /**
     * 套餐集合
     * @return
     */
    public List listCombo(){
        List list=null;
        final String queryString="from Combo where status=?";
        list = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				query = session.createQuery(queryString);
			    query.setParameter(0, "0",Hibernate.STRING);   
				return query.list();
			}
		});
        return list;
    }


    /**
     * 创建一条历史价格记录
     * @param comboHistoryPrice
     */
	public void saveHistoryPrice(ComboHistoryPrice comboHistoryPrice) {
		super.save(comboHistoryPrice);
	}
	
	/**
	 * 批量删除场次套餐价格记录(逻辑删除)
	 * @param ids
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-5 下午5:16:26
	 */
    public boolean deleteErpEventsComboPriceBatch(String ids) throws Exception{
    	boolean flag = false;
    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Transaction tx = session.beginTransaction();
    	tx.begin();
    	try{
	    	if(ids.indexOf(",")!=-1){
	    		String[] idArr = ids.split(",");
	    		for (int i = 0; i < idArr.length; i++) {
					ErpEventsComboPrice entity = this.findById(idArr[i]);
					//逻辑删除
					entity.setStatus("-1");
					session.update(entity);
					if(i!=0&&i%100==0){
						session.flush();
						session.clear();
					}
				}
	    		session.flush();
	    		session.clear();
	    		flag = true;
	    	}else{
				ErpEventsComboPrice entity = this.findById(ids);
				//逻辑删除
				entity.setStatus("-1");
				session.update(entity);
	    		session.flush();
	    		session.clear();
	    		flag = true;
	    	}
	    tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		tx.rollback();
    		throw e;
    	}finally{
//    		if(session!=null){
//    			session.close();
//    		}
    	}
    	
    	return flag;
    }
    
    /**
     * 根据套餐名查找场次套餐价格相关
     * @param params
     * @return ErpEventsComboPrice
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-8 上午11:19:26
     */
    public List<ErpEventsComboPrice> findEventsComboPriceByProps(Map<String, Object> params) throws Exception{
    	List<ErpEventsComboPrice> list = null;
    	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ErpEventsComboPrice.class);
    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Transaction tx = session.beginTransaction();
    	Criteria criteria = detachedCriteria.getExecutableCriteria(session);
    	if(params!=null){
    		String eventsNo = (String) params.get(ErpEventsComboPrice.F_EVENTSNO);
    		if(eventsNo!=null&&eventsNo.length()>0){
    			//场次号
    			criteria.add(Restrictions.eq(ErpEventsComboPrice.F_EVENTSNO, eventsNo));
    		}
    		String combo = (String) params.get(ErpEventsComboPrice.F_COMBO);
    		if(combo!=null&&combo.length()>0){
    			//套餐名
    			criteria.add(Restrictions.eq(ErpEventsComboPrice.F_COMBO, combo));
    		}
    	}
    	list = criteria.list();
    	session.flush();
    	session.clear();
    	tx.commit();
//    	if(session!=null){
//    		session.close();
//    	}
    	return list;
    }
    
    /**
     * 批量保存
     * @param list
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-16 下午2:33:38
     */
    public boolean saveList(List<ErpEventsComboPrice> list) throws Exception{
    	boolean flag = false;
    	if(list!=null&&list.size()>0){
	    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	    	Transaction tx = session.beginTransaction();
	    	tx.begin();
	    	for (int i=0; i<list.size(); i++) {
	    		if(list.get(i).getId()!=null&&list.get(i).getId().trim().length()>0){
	    			session.update(list.get(i));
	    		}else{
	    			session.save(list.get(i));
	    		}
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
     * 
     * @param params
     * @return
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-17 上午10:19:51
     */
    public List<ErpEventsComboPrice> listErpEventsComboPriceByProps(Map<String, Object> params) throws Exception{
    	List<ErpEventsComboPrice> list = null;
    	if(!CollectionUtils.isEmpty(params)){
	    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	    	Criteria criteria = session.createCriteria(ErpEventsComboPrice.class);
	    	//场次号
	    	String eventsNo = (String) params.get(ErpEventsComboPrice.F_EVENTSNO);
	    	if(StringUtils.isNotEmpty(eventsNo)){
	    		criteria.add(Restrictions.eq(ErpEventsComboPrice.F_EVENTSNO, eventsNo));
	    	}
	    	//套餐名
	    	String combo = (String) params.get(ErpEventsComboPrice.F_COMBO);
	    	if(StringUtils.isNotEmpty(combo)){
	    		criteria.add(Restrictions.eq(ErpEventsComboPrice.F_COMBO, combo));
	    	}
	    	list = criteria.list();
//	    	if(session!=null){
//	    		session.close();
//	    	}
    	}
    	return list;
    }
    
}
