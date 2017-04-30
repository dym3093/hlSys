package org.hpin.venueStaffSettlement.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.venueStaffSettlement.entity.ErpNonConferenceCostDetail;
import org.springframework.stereotype.Repository;

/**
 * @author Carly
 * @since 2017年1月23日11:14:35
 */
@Repository()
@SuppressWarnings("unchecked")
public class ErpNonConferenceCostDetailDao extends BaseDao {

	/**
	 * @param costId
	 * @param belong
	 * @return 
	 */
	public List<ErpNonConferenceCostDetail> getCostDetailList(String costId,String belong) {
		int type = Integer.valueOf(belong);
		String hql = "from ErpNonConferenceCostDetail where costId=? and belong=?";
		return this.getHibernateTemplate().find(hql,costId,type);
	}

	public void deleteCostDetails(String costId) {
		String sql = "delete from erp_nonconference_cost_detail where costid='"+costId+"'";
		this.getJdbcTemplate().execute(sql);
	}

}
