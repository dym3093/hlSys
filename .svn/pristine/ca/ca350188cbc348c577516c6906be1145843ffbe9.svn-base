package org.hpin.warehouse.dao;


import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.StoreApplication;
import org.springframework.stereotype.Repository;


@Repository()
public class StoreApplicationDAO extends BaseDao {
	public List getEmsList(String id){
		String queryString = "SELECT y.name,p.ems_name,p.ems_no,p.remark,p.create_time,p.use_time FROM store_produce p,store_type y where p.remark1=y.id and p.IS_DELETED = 0 and p.APPLICATION_ID=?";		
		List list = this.getJdbcTemplate().queryForList(queryString, new Object[]{id});	
	    return list;
	}
	
	/**
	 * 根据条件查询
	 * @param params
	 * @return
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-8-12 下午7:11:46
	 */
	public List<StoreApplication> listByProps(Map<String,Object> params) throws Exception{
		List<StoreApplication> list = null;
		Session session = null;
		Criteria crt = null;
		if(!params.isEmpty()){
			session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			crt = session.createCriteria(StoreApplication.class);
			String applicant = (String) params.get(StoreApplication.F_CREATEUSERNAME);
			if(applicant!=null&&applicant.length()>0){
				crt.add(Restrictions.eq(StoreApplication.F_CREATEUSERNAME, applicant));
			}
			String remark1 = (String) params.get(StoreApplication.F_REMARK1);
			if(remark1!=null&&remark1.length()>0){
				crt.add(Restrictions.eq(StoreApplication.F_REMARK1, remark1));
			}
			String remark2 = (String) params.get(StoreApplication.F_REMARK2);
			if(remark2!=null&&remark2.length()>0){
				crt.add(Restrictions.eq(StoreApplication.F_REMARK2, remark2));
			}
			String remark3 = (String) params.get(StoreApplication.F_REMARK3);
			if(remark2!=null&&remark2.length()>0){
				crt.add(Restrictions.eq(StoreApplication.F_REMARK3, remark3));
			}
			list = crt.list();
		}
		return list;
	}
}