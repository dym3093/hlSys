package org.hpin.base.region.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hpin.base.dict.dao.ID2NameDAO;
import org.hpin.base.dict.exceptions.DictDAOException;
import org.hpin.base.region.entity.JYRegion;
import org.hpin.base.region.entity.Region;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * 行政区划管理
 * @author yifan
 *
 */
@Repository(value="org.hpin.base.region.dao.RegionDao")
public class RegionDao extends BaseDao implements ID2NameDAO {

	public List<Region> findRegionByParentId(String parentId){
		String hql = " from Region where parentId =? order by id asc " ;
		return super.getHibernateTemplate().find(hql,parentId); 
	}
	
	public List<JYRegion> getJYRegion(String parentId){
		String hql = "";
		if (parentId==null) {
			hql = " from JYRegion where pid is null order by cityid asc " ;
			return super.getHibernateTemplate().find(hql); 
		}else {
			hql = " from JYRegion where pid =? order by cityid asc " ;
			return super.getHibernateTemplate().find(hql,parentId); 
		}
	}
	
	public String id2Name(String id) {
		// TODO Auto-generated method stub
		String hql = " from Region where id =? order by id asc " ;
		List<Region> regionList = super.getHibernateTemplate().find(hql , id) ;
		if(regionList != null && regionList.size() > 0){
			return regionList.get(0).getRegionName() ;
		}
		return null ;
	}
	
	public List<Region> findRegionsByParams(boolean isProvince){
		StringBuffer hqlBuffer = new StringBuffer(" from Region ") ;
		if(isProvince){
			hqlBuffer.append(" where id like '%0000' ") ;
		}else{
			hqlBuffer.append(" where id like '%00' and parentId != 0 ") ;
		}
		hqlBuffer.append(" order by nlssort(regionName , 'NLS_SORT=SCHINESE_PINYIN_M') ") ;
		
		return super.getHibernateTemplate().find(hqlBuffer.toString()) ;
	}
	
	public List<Region> findRegionByDeep(final int deep){
		final String hql = "from Region where deep = ? order by id asc" ;
		 List list = (List)super.getHibernateTemplate().execute(new HibernateCallback() {
			 @Override
			public Object doInHibernate(Session session) throws HibernateException , SQLException {
				 Query query = null ;
				 query = session.createQuery(hql) ;
				 query.setInteger(0, deep);
				 query.setCacheable(true) ;
				 return query.list() ;
			}
		 }) ;
		return list;
	}

	@Override
	public String id2Field(String id, String beanId, String field) throws DictDAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
