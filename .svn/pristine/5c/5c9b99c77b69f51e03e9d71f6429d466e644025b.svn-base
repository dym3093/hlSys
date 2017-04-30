package org.hpin.base.usermanager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.resource.dao.ResourceDao;
import org.hpin.base.resource.entity.Resource;
import org.hpin.base.usermanager.dao.RoleDao;
import org.hpin.base.usermanager.dao.UserDao;
import org.hpin.base.usermanager.dao.UserRoleDao;
import org.hpin.base.usermanager.entity.Role;
import org.hpin.base.usermanager.entity.RoleResource;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.entity.UserRole;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色Service
 * 
 * @author thinkpad
 * @data Sep 10, 2009
 */
@Service(value = "org.hpin.base.usermanager.service.RoleService")
@Transactional()
public class RoleService extends BaseService {

	@Autowired
	private RoleDao roleDao = null;

	@Autowired
	private ResourceDao resourceDao = null;
	
	@Autowired
	private UserDao userDao; // add by DengYouming 2016-06-21
	
	@Autowired
	private UserRoleDao userRoleDao; // add by DengYouming 2016-06-21

	/**
	 * 删除实体类
	 * 
	 * @param ids
	 */
	public void deleteIds(String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			roleDao.delete(idArray[i]);
		}
	}

	public List findByPage(Page page, Map searchMap) {
		return roleDao.findByPage(page, searchMap);
	}

	public List findRoleResource(String roleId) {
		return roleDao.findRoleResource(roleId);
	}
	
	public List roleTree(){
		List list = roleDao.findAll(Role.class, null, null);
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0){
		  for (int i = 0 ; i < list.size() ; i++) {
			Role role = (Role)list.get(i);
			
		  }
		}
		return null;
	}
	
	/**
	 * 给角色授权
	 * @param roleId
	 * @param grantInfo
	 * @return
	 */
	public boolean grantResource(String roleId, String grantInfo) {
		Resource rootResource = resourceDao.findRootResource();
		// 根据角色ID删除之前赋予的角色
		roleDao.deleteGrantResourceByRoleId(roleId);
		String resourceId = null;
		RoleResource roleResource = null;
		if (StrUtils.isNullOrBlank(grantInfo)) {
			return true;
		}
		String[] grantInfoArray = grantInfo.split(",");
		Map grantMenuMap = new HashMap();
		for (int i = 0; i < grantInfoArray.length; i++) {
			if (StrUtils.isNullOrBlank(grantInfoArray[i])) {
				continue;
			}
			if (grantInfoArray[i].indexOf("menu") >= 0) {
				//resourceId = new Long(grantInfoArray[i].substring(4));
				resourceId = grantInfoArray[i].substring(4);
				grantMenuMap.put(resourceId.toString(), resourceId);
				// 添加其相应的父节点
				String parentId = resourceId;
				while (true) {
					parentId = resourceDao.findParentIdByIdForJdbc(parentId);
					if (parentId.compareTo(rootResource.getId()) != 0) {
						if (null == grantMenuMap.get(parentId.toString())) {
							grantMenuMap.put(parentId.toString(), parentId);
						} else {
							break;
						}
					} else {
						break;
					}
				}
			} else if (grantInfoArray[i].indexOf("operation") >= 0) {
				//resourceId = new Long(grantInfoArray[i].substring(9));
				resourceId = grantInfoArray[i].substring(9);
				roleResource = new RoleResource();
				roleResource.setResourceId(resourceId);
				roleResource.setRoleId(roleId);
				roleResource.setIsMenu(0);
				roleDao.save(roleResource);
			}
		}
		
		for (Object o : grantMenuMap.keySet()) {
			
			//resourceId = new Long(grantMenuMap.get(o).toString());
			resourceId = grantMenuMap.get(o).toString();
			roleResource = new RoleResource();
			roleResource.setResourceId(resourceId);
			roleResource.setRoleId(roleId);
			roleResource.setIsMenu(1);
			roleDao.save(roleResource);
		}
		return true;
	}
	
	/**
	 * @author duan update
	 * 保存用户角色信息
	 * @param roleId
	 * @param userIds
	 */
	public void saveUserRoles(String roleId, String userIds){
		//先删除关系
		roleDao.deleteUserRoleByRoleId(roleId);
		//查找角色  modify by DengYouming 2016-06-21 
		Role role = (Role) roleDao.findById(Role.class, roleId);
		//插入关系
		if(StringUtils.isNotBlank(userIds)&&role!=null){
			String[] arrayUserId = userIds.split(",");
			for(String userId : arrayUserId){
				UserRole userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(userId);
				 
				userRoleDao.save(userRole); // roleDao.save(userRole)改为  userRoleDao.save(userRole)  modify by DengYouming 2016-06-21 
				
				// MODIFY START add by DengYouming 2016-06-21  
				//用户表中更新角色名称
				User user = (User) userDao.findById(User.class, userId);
				if(user!=null){
					user.setRoleNames(role.getName());
					userDao.update(user);
				}
				// MODIFY END add by DengYouming 2016-06-21  
			}
		}
	}
	
//	public List getUserRoles(Long roleId){
//		return roleDao.getUserRoles(roleId);
//	}
	
	public List getUserRoles(String roleId){
		return roleDao.getUserRoles(roleId);
	}
		
	public List getRolesByParentId(String parentId){
		return roleDao.getRolesByParentId(parentId);
	}
	/**
	 * 待完成
	 * @param parentId
	 * @param roleList
	 * @return
	 */
	public List getAllRolesByParentId(String parentId,List roleList){
		
		List<Role>  resList =  roleDao.getRolesByParentId(parentId);
		for( Role role:resList){
			 roleList.add(role);
			 List<Role>  sonList = roleDao.getRolesByParentId(role.getId());
			 if(sonList != null && sonList.size()>0 ){
				 getAllRolesByParentId(role.getId(),roleList);
			}
		}
		return roleDao.getRolesByParentId(parentId);
	}
	
	/**
	 * 获取所有的角色
	 * @parm isDeleted = 0
	 * @return
	 */
	public List findAllRole() {
		
		return roleDao.findAllRole();
	}
	
	/**
	 * 根据角色编码找出角色组
	 * @return
	 */
	public List<Role> findRoleBycode(String code) {
		System.out.println(code+"  code====");
		List roleList = new ArrayList();
		List<Role> fList = roleDao.findRoleBycode(code);
		if (fList != null && fList.size() > 0) {
			for (int i = 0 ; i < fList.size() ; i++) {
				Role fRole = (Role)fList.get(i);
				System.out.println("fRole : " + fRole.getName());
				roleList.add(fRole);
				String rParentId = fRole.getId();
				List<Role> sList = roleDao.getRolesByParentId(rParentId);
				if (sList != null && sList.size() > 0) {
					for (int j = 0 ; j < sList.size(); j++) {
						Role sRole  = (Role)sList.get(j);
						System.out.println("sRole : " + sRole.getName());
						roleList.add(sRole);
						String sParentId = sRole.getId();
						List tList = roleDao.getRolesByParentId(sParentId);
						if (tList != null && tList.size() > 0) {
							for (int x = 0; x < tList.size(); x++) {
								Role tRole = (Role)tList.get(x);
								System.out.println("tRole : " + tRole.getName());
								roleList.add(tRole);
							}
						}
					}
				}
			}
		}
		
		return roleList;
	}
	
	/**
	 * 获取所有的用户角色表信息
	 * @parm isDeleted = 0
	 * @return
	 */
	public List<UserRole> findAllUserRole() {
		List<UserRole> roleList = new ArrayList();
		List list = roleDao.findAllUserRole();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				UserRole userRole = new UserRole();
				userRole.setId(String.valueOf(obj[0]));
				userRole.setUserId(String.valueOf(obj[1]));
				userRole.setRoleId(String.valueOf(obj[2]));
				userRole.setUserName(String.valueOf(obj[3]));
				userRole.setAccountName(String.valueOf(obj[4]));
				userRole.setJobNumber(String.valueOf(obj[5]));
				roleList.add(userRole);
			}
		}
		return roleList;
	}
	
}
