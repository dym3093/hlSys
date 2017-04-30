package org.hpin.base.usermanager.service;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hpin.base.usermanager.dao.BigRoleAndRoleDao;
import org.hpin.base.usermanager.dao.BigRoleDao;
import org.hpin.base.usermanager.dao.UserBigRoleDao;
import org.hpin.base.usermanager.entity.BigRole;
import org.hpin.base.usermanager.entity.BigRoleAndRole;
import org.hpin.base.usermanager.entity.UserBigRole;
import org.hpin.common.core.orm.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>@desc : 角色Service</p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-11-18 上午11:34:21</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
@Service(value = "org.hpin.base.usermanager.service.BigRoleService")
@Transactional(rollbackFor = Exception.class)
public class BigRoleService extends BaseService {
	
	@Autowired()
	private BigRoleDao bigRoleDao = null ;
	
	@Autowired()
	private BigRoleAndRoleDao brrDao = null ;
	
	@Autowired()
	private UserBigRoleDao userBigRoleDao = null ;
	
	/**
	 * 保存角色人员信息
	 * @param bigRole
	 * @param moduleIds
	 * @param userIds
	 */
	public void saveBigRoleInfo(BigRole bigRole , String moduleIds , String userIds){
		bigRoleDao.save(bigRole) ;
		String[] moduleIdArray = moduleIds.split(",") ;
		for(String moduleId : moduleIdArray){
			if(StringUtils.isBlank(moduleId)) continue ;
			BigRoleAndRole brr = new BigRoleAndRole() ;
			brr.setRoleId(moduleId) ;
			brr.setBigRoleId(bigRole.getId()) ;
			brrDao.save(brr) ;
		}
		String[] userIdArray = userIds.split(",") ;
		for(String userId : userIdArray){
			if(StringUtils.isBlank(userId)) continue ;
			UserBigRole userBigRole = new UserBigRole() ;
			userBigRole.setUserId(userId) ;
			userBigRole.setBigRoleId(bigRole.getId()) ;
			userBigRoleDao.save(userBigRole) ;
		}
	}
	
	/**
	 * 更新bigROle信息
	 * @param bigRole
	 * @param moduleIds
	 * @param userIds
	 */
	public void updateBigRoleInfo(BigRole bigRole , String moduleIds , String userIds){
		List<UserBigRole> ubrList= userBigRoleDao.findByBigRoleId(bigRole.getId()) ;
		for(UserBigRole ubr : ubrList){
			userBigRoleDao.delete(ubr) ;
		}
		List<BigRoleAndRole> brrList = brrDao.findByBigRoleId(bigRole.getId()) ;
		for(BigRoleAndRole brr : brrList){
			brrDao.delete(brr) ;
		}
		bigRoleDao.update(bigRole) ;
		String[] moduleIdArray = moduleIds.split(",") ;
		for(String moduleId : moduleIdArray){
			if(StringUtils.isBlank(moduleId)) continue ;
			BigRoleAndRole brr = new BigRoleAndRole() ;
			brr.setRoleId(moduleId) ;
			brr.setBigRoleId(bigRole.getId()) ;
			brrDao.save(brr) ;
		}
		String[] userIdArray = userIds.split(",") ;
		for(String userId : userIdArray){
			if(StringUtils.isBlank(userId)) continue ;
			UserBigRole userBigRole = new UserBigRole() ;
			userBigRole.setUserId(userId) ;
			userBigRole.setBigRoleId(bigRole.getId()) ;
			userBigRoleDao.save(userBigRole) ;
		}
	}
	
	/**
	 * 删除bigRole相关信息
	 * @param ids
	 */
	public void deleteBigRoleByIds(String ids){
		String[] idArray = ids.split(",") ;
		for(String id : idArray){
			BigRole bigRole = (BigRole)bigRoleDao.findById(BigRole.class, id) ;
			List<UserBigRole> ubrList = userBigRoleDao.findByBigRoleId(bigRole.getId()) ;
			for(UserBigRole ubr : ubrList){
				userBigRoleDao.delete(ubr) ;
			}
			List<BigRoleAndRole> brrList = brrDao.findByBigRoleId(bigRole.getId()) ;
			for(BigRoleAndRole brr : brrList){
				brrDao.delete(brr) ;
			}
			bigRoleDao.delete(bigRole) ;
		}
	}

}

