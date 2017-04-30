package org.hpin.base.usermanager.service;

import java.util.List;

import org.hpin.base.usermanager.dao.BigRoleAndRoleDao;
import org.hpin.base.usermanager.entity.BigRoleAndRole;
import org.hpin.common.core.orm.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@desc : </p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-11-20 上午10:39:46</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
@Service(value = "org.hpin.base.usermanager.service.BigRoleAndRoleService")
public class BigRoleAndRoleService extends BaseService {

	@Autowired()
	private BigRoleAndRoleDao bigRoleAndRoleDao = null ;
	
	public List<BigRoleAndRole> getBigRoleAndRoleListByBigRoleId(String bigRoleId){
		return bigRoleAndRoleDao.findByBigRoleId(bigRoleId) ;
	}
}

