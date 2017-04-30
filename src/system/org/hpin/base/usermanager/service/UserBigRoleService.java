package org.hpin.base.usermanager.service;

import java.util.List;

import org.hpin.base.usermanager.dao.UserBigRoleDao;
import org.hpin.base.usermanager.entity.UserBigRole;
import org.hpin.common.core.orm.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>@desc : </p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-11-20 上午10:37:13</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
@Service(value = "org.hpin.base.usermanager.service.UserBigRoleService")
@Transactional(rollbackFor = Exception.class)
public class UserBigRoleService extends BaseService {
	
	@Autowired()
	private UserBigRoleDao userBigRoleDao = null ;
	
	/**
	 * 根据bigRoleId获取UserBigROle集合
	 * @param bigRoleId
	 * @return
	 */
	public List<UserBigRole> getUserBigRoleListByBigRoleId(String bigRoleId){
		return userBigRoleDao.findByBigRoleId(bigRoleId) ;
	}

}

