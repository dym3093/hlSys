/**
 * @author DengYouming
 * @since 2016-10-26 下午6:19:48
 */
package org.hpin.foreign.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.foreign.entity.ErpReportDetail;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author DengYouming
 * @since 2016-10-26 下午6:19:48
 */
@Repository
public class ErpReportDetailDao extends BaseDao {

	/**
	 * 根据条件查询
	 * @param params 传入的Map键值对
	 * @param isExact 是否精确查找（只对字符串类型有效，ID为in查找，不用like）
	 * @return List ErpReportDetail对象集
	 * @author DengYouming
	 * @since 2016-8-18 上午11:57:59
	 */
	public List<ErpReportDetail> listReportDetailByProps(Map<String,String> params, boolean isExact){
		List<ErpReportDetail> list = null;
		Session session;
		Criteria criteria;
		if(!CollectionUtils.isEmpty(params)){
			session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			criteria = session.createCriteria(ErpReportDetail.class);
		
			for (String key : params.keySet()) {
				String value = params.get(key);
				if(key.equalsIgnoreCase(ErpReportDetail.F_ID)){
					String[] idArr;
					if(value.indexOf(",")!=-1){
						idArr = value.split(",");
					}else{
						idArr = new String[1] ;
						idArr[0] = value;
					}
					criteria.add(Restrictions.in(ErpReportDetail.F_ID, idArr));
				}else{
					if(isExact){
						criteria.add(Restrictions.eq(key, value));
					}else{
						criteria.add(Restrictions.like(key, value, MatchMode.ANYWHERE));
					}
				}
			}
			list = criteria.list();
		}
		return list;
	}
	
	/**
	 * 
	 * @param params Map
	 * @return List
	 * @author DengYouming
	 * @since 2016-10-26 下午6:28:25
	 */
	public List<ErpReportDetail> listReportDetailByProps(Map<String,String> params){
		return this.listReportDetailByProps(params, true);
	}
}
