/**
 * @author DengYouming
 * @since 2016-5-19 上午10:27:15
 */
package org.hpin.base.dict.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hpin.base.dict.entity.ErpDict;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 数据字典DAO
 * @author DengYouming
 * @since 2016-5-19 上午10:27:15
 */
@Repository
public class ErpDictDao extends BaseDao{

	/**
	 * 批量保存
	 * @param list
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-19 上午10:30:17
	 */
	public boolean saveList(List<ErpDict> list) throws Exception{
		boolean flag = false;
		if(list!=null&&list.size()>0){
	    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	    	Transaction tx = session.beginTransaction();
	    	tx.begin();
	    	for (int i=0; i<list.size(); i++) {
	    		session.saveOrUpdate(list.get(i));
	    		if(i!=0&&i%100==0){
					session.flush();
					session.clear();
	    		}
			}
			session.flush();
			session.clear();
	    	tx.commit();
			if(session!=null){
				session.close();
			}
			flag = true;
			
    	}
		return flag;
	}

	/**
	 * 条件查询
	 * @param params 传入的条件
	 * @param exact 是否精确查询
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-19 上午10:43:28
	 */
	public List<ErpDict> listByProps(Map<String,Object> params, boolean exact) throws Exception{
		List<ErpDict> list = null;
//		if(!CollectionUtils.isEmpty(params)){
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			tx.begin();
			Criteria criteria = session.createCriteria(ErpDict.class);
			//ID
			String id = (String) params.get(ErpDict.F_ID);
			if(StringUtils.isNotEmpty(id)){
				criteria.add(Restrictions.eq(ErpDict.F_ID, id));
			}
			//键
			String keyName = (String) params.get(ErpDict.F_KEYNAME);
			if(StringUtils.isNotEmpty(keyName)){
				if(exact){
					criteria.add(Restrictions.eq(ErpDict.F_KEYNAME, keyName));
				}else{
					criteria.add(Restrictions.like(ErpDict.F_KEYNAME, keyName, MatchMode.ANYWHERE));
				}
			}
			//值
			String valueName = (String) params.get(ErpDict.F_VALUENAME);
			if(StringUtils.isNotEmpty(valueName)){
				if(exact){
					criteria.add(Restrictions.eq(ErpDict.F_VALUENAME, valueName));
				}else{
					criteria.add(Restrictions.like(ErpDict.F_VALUENAME, valueName, MatchMode.ANYWHERE));
				}
			}
			//类型
			String typeFilter = (String) params.get(ErpDict.F_TYPEFILTER);
			if(StringUtils.isNotEmpty(typeFilter)){
				criteria.add(Restrictions.eq(ErpDict.F_TYPEFILTER, typeFilter));
			}
			//注释
			String remark = (String) params.get(ErpDict.F_REMARK);
			if(StringUtils.isNotEmpty(remark)){
				if(exact){
					criteria.add(Restrictions.eq(ErpDict.F_REMARK, remark));
				}else{
					criteria.add(Restrictions.like(ErpDict.F_REMARK, remark, MatchMode.ANYWHERE));
				};
			}
			list = criteria.list();
			tx.commit();
			if(session!=null){
				session.close();
			}
//		}
		return list;
	}
	
}
