package org.hpin.venueStaffSettlement.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.ErpConference;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpConferenceStaffCostDao extends BaseDao {

	public List<ErpConference> getConferenceListByConferenceNo(String conferenceNo) {
		String hql = "from ErpConference where conferenceNo=? and isDeleted=0";
		return this.getHibernateTemplate().find(hql,conferenceNo);
	}

	
}
