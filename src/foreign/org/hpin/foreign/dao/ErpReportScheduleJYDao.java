package org.hpin.foreign.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.foreign.entity.ErpReportOrgJY;
import org.hpin.foreign.entity.ErpReportScheduleJY;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository()
@SuppressWarnings({"rawtypes","unchecked"})
public class ErpReportScheduleJYDao extends BaseDao{

	public List<ErpReportScheduleJY> findByPage(Page page, Map<String,String> map) {
		StringBuffer query = new StringBuffer(" from ErpReportScheduleJY where 1=1 ");
	    List<ErpReportOrgJY> valueList = new ArrayList();
	    OrmConverter.assemblyQuery(query, map, valueList);
	    return super.findByHql(page, query, valueList);
	}

	public void updateupdateUserInfo(String customerId, String jyId) {
		String hql ="from ErpEvents where isDeleted=0 and eventsNo=?";
		ErpCustomer customer = this.getHibernateTemplate().get(ErpCustomer.class, customerId);
		List<ErpEvents> eventsList= this.getHibernateTemplate().find(hql,customer.getEventsNo());
		String sql = "update erp_report_schedule_jy set code=?,name=?,gender=?,combo=?,events_no=?,status=?,remark=?,batch_no=?,update_time=to_date(?,'yyyy-MM-dd hh24:mi:ss'),update_user_name=? where id=?";
		this.getJdbcTemplate().update(sql,customer.getCode(),
				customer.getName(),customer.getSex(),customer.getSetmealName(),
				customer.getEventsNo(),11,"手动更改",eventsList.get(0).getBatchNo(),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()),"ymjy",jyId );
	}

	public List<ErpReportScheduleJY> listScheduleJobByProps(Map<String, String> params, boolean isExact) {
		List<ErpReportScheduleJY> list = null;
		Session session;
		Criteria criteria;
		if (!CollectionUtils.isEmpty(params)) {
			session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			criteria = session.createCriteria(ErpReportScheduleJY.class);

			for (String key : params.keySet()) {
				String value = params.get(key);
				System.out.println(key + " : " + value);
				if (key.equalsIgnoreCase(ErpReportScheduleJY.F_ID)) {
					String[] idArr;
					if (value.indexOf(",") != -1) {
						idArr = value.split(",");
					} else {
						idArr = new String[1];
						idArr[0] = value;
					}
					criteria.add(Restrictions.in(ErpReportScheduleJY.F_ID, idArr));
				}else if(ErpReportScheduleJY.F_STATUS.equalsIgnoreCase(key)){
					criteria.add(Restrictions.eq(ErpReportScheduleJY.F_STATUS, Integer.valueOf(value)));
				}else  if(ErpReportScheduleJY.F_ISDELETED.equalsIgnoreCase(key)){
					criteria.add(Restrictions.eq(ErpReportScheduleJY.F_ISDELETED, Integer.valueOf(value)));
				}else{
					if (isExact) {
						criteria.add(Restrictions.eq(key, value));
					} else {
						criteria.add(Restrictions.like(key, value, MatchMode.ANYWHERE));
					}
				}
			}
			list = criteria.list();
		}
		return list;
	}

	/**
	 * 根据条件精确查询
	 * @param params 查询条件
	 * @return List
	 * @throws Exception
	 */
	public List<ErpReportScheduleJY> listByProps(Map<String, String> params) throws Exception{
		return this.listScheduleJobByProps(params, true);
	}

	/**
	 * @description 根据状态获取定时任务, condtions=="notIn"查询 not in statusArr状态的，
	 *              否则默认获取所有状态的未删除数据
	 * @param statusArr 状态数组
	 * @param conditions 条件， not in ， 其他值则默认为 in 查询
	 * @return List
	 * @author YoumingDeng
	 * @since: 2016/12/8 11:02
	 */
	public List<ErpReportScheduleJY> listByStatus(Integer[] statusArr, String conditions)throws Exception{
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ErpReportScheduleJY.class);
		//未删除
		criteria.add(Restrictions.eq(ErpReportScheduleJY.F_ISDELETED, 0));
		//状态
		if(statusArr!=null&&statusArr.length>0) {
			//条件
			if ("notIn".equalsIgnoreCase(conditions)) {
				criteria.add(Restrictions.not(Restrictions.in(ErpReportScheduleJY.F_STATUS, statusArr)));
			} else {
				criteria.add(Restrictions.in(ErpReportScheduleJY.F_STATUS, statusArr));
			}
		}
		return criteria.list();
	}

	/**
	 * 逻辑删除定时任务中的数据
	 * @param code 条码
	 * @param name 姓名
	 * @return boolean
	 */
	public boolean cleanOld(String code, String name){
		boolean flag = false;
		if(StringUtils.isNotEmpty(code)&&StringUtils.isNotEmpty(name)){
			String sql = " update ERP_REPORT_SCHEDULE_JY t set t.is_deleted = 1 where t.code = ? and t.name = ? and t.is_deleted <> 1 ";
			this.getJdbcTemplate().update(sql, new String[]{code, name});
			flag = true;
		}
		return flag;
	}

}
