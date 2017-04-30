package org.hpin.venueStaffSettlement.service;

import java.util.List;

import org.hpin.common.core.orm.BaseService;
import org.hpin.venueStaffSettlement.dao.ErpConferenceCostDetailDao;
import org.hpin.venueStaffSettlement.dao.ErpConferenceStaffCostDao;
import org.hpin.venueStaffSettlement.entity.ErpConferenceCostDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "org.hpin.venueStaffSettlement.service.ErpConferenceCostDetailService")
@Transactional()
public class ErpConferenceCostDetailService extends BaseService {
	
	@Autowired
	private ErpConferenceCostDetailDao dao ;

	/**
	 * @param conferenceId 会议ID
	 * @param name 姓名
	 * @return
	 */
	public List<ErpConferenceCostDetail> getConferenceDetailInfo(String conferenceId, String name) {
		String sql = "from ErpConferenceCostDetail where conferenceid =? and name=?";
		return dao.getHibernateTemplate().find(sql,conferenceId,name);
	}
	/**
	 * @param conferenceId 会议ID
	 * @param name 姓名
	 * @return
	 */
	public List<ErpConferenceCostDetail> getConferenceDetailInfo(String conferenceId, String name,String belong) {
		String sql = "from ErpConferenceCostDetail where conferenceid =? and name=? and belong=?";
		return dao.getHibernateTemplate().find(sql,conferenceId,name,belong);
	}
	
}
