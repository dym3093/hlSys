package org.hpin.base.usermanager.dao;

import java.util.List;

import org.hpin.base.usermanager.entity.UserBigRole;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * <p>@desc : </p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-11-19 下午6:33:16</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
@Repository()
public class UserBigRoleDao extends BaseDao {

	/**
	 * 根据bigROleId查询信息
	 * @param bigRoleId
	 * @return
	 */
	public List<UserBigRole> findByBigRoleId(String bigRoleId){
		String query = " from UserBigRole ubr where ubr.bigRoleId = ? " ;
		return super.getHibernateTemplate().find(query , bigRoleId) ;
	}
}

