package org.hpin.base.usermanager.dao;

import java.util.List;

import org.hpin.base.usermanager.entity.BigRoleAndRole;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * <p>@desc : </p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-11-19 下午6:33:53</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
@Repository()
public class BigRoleAndRoleDao extends BaseDao {

	/**
	 * 根据bigRoleId删除关联关系
	 * @param bigRoleId
	 */
	public List<BigRoleAndRole> findByBigRoleId(String bigRoleId){
		String query = "from BigRoleAndRole brr where brr.bigRoleId = ?" ;
		return super.getHibernateTemplate().find(query , bigRoleId) ;
	}
}

