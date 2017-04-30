/**
 * @author DengYouming
 * @since 2016-9-14 上午10:35:03
 */
package org.hpin.events.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.ErpScheduleJob;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 
 * @author DengYouming
 * @since 2016-9-14 上午10:35:03
 */
@Repository
public class ErpScheduleJobDao extends BaseDao{

	public static String DEAL_SCHEDULE_JOB = "{call ERP_COMM_PKG.dealschedulejob(?,?,?,?,?,?,?,?,?)}";
	
	/**
	 * 处理调度任务
	 * @param params
	 * @return Map
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-9-14 上午10:37:13
	 */
	public Map<String, String> dealScheduleJob(Map<String,String> params) throws Exception{
		Map<String, String> result = null;
		Connection conn = null;
		CallableStatement cs = null;
		if(params!=null&&params.keySet().size()>0){
			conn = this.getJdbcTemplate().getDataSource().getConnection();
			cs = conn.prepareCall(DEAL_SCHEDULE_JOB);
			cs.setString(1, params.get(ErpScheduleJob.F_INFOTYPE));
			cs.setString(2, params.get(ErpScheduleJob.F_INFO));
			cs.setString(3, params.get(ErpScheduleJob.F_SCHEDULETIME));
			cs.setString(4, params.get(ErpScheduleJob.F_KEYWORD));
			cs.setString(5, params.get(ErpScheduleJob.F_METHOD));
			cs.setString(6, params.get(ErpScheduleJob.F_PARAMS));
			cs.setString(7, params.get(ErpScheduleJob.F_CREATEUSER));
			cs.setString(8, params.get(ErpScheduleJob.F_CREATEUSERID));
			cs.registerOutParameter(9, Types.CHAR);
			
			cs.execute();
			
			result = new HashMap<String, String>();
			result.put(ErpScheduleJob.F_REMARK, cs.getString(9));
		}
		return result;
	}

	/**
	 *
	 * @param params
	 * @param isExact true:精确查找，false:模糊查找
	 * @return List
	 * @author DengYouming
	 * @since 2016-10-31 下午4:52:02
	 */
	public List<ErpScheduleJob> listScheduleJobByProps(Map<String,String> params, boolean isExact){
		List<ErpScheduleJob> list = null;
		Session session;
		Criteria criteria;
		if(!CollectionUtils.isEmpty(params)){
			session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			criteria = session.createCriteria(ErpScheduleJob.class);

			for (String key : params.keySet()) {
				String value = params.get(key);
				System.out.println(key+" : "+value);
				if(key.equalsIgnoreCase(ErpScheduleJob.F_ID)){
					String[] idArr;
					if(value.indexOf(",")!=-1){
						idArr = value.split(",");
					}else{
						idArr = new String[1] ;
						idArr[0] = value;
					}
					criteria.add(Restrictions.in(ErpScheduleJob.F_ID, idArr));
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

	public List<ErpScheduleJob> listScheduleJobByProps(Map<String,String> params){
		return this.listScheduleJobByProps(params,true);
	}
}
