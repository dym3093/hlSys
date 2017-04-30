package org.hpin.base.usermanager.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hpin.base.priv.dao.AssignDao;
import org.hpin.base.resource.dao.ResourceDao;
import org.hpin.base.resource.entity.Resource;
import org.hpin.base.usermanager.dao.DeptDao;
import org.hpin.base.usermanager.dao.UserDao;
import org.hpin.base.usermanager.dao.UserRoleDao;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.entity.UserRole;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.PasswordMd5;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户Service实现类
 * 
 * @author thinkpad
 * @data Sep 13, 2009
 */
@Service(value = "org.hpin.base.usermanager.service.UserService")
@Transactional(rollbackFor = Exception.class)
public class UserService extends BaseService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class) ;
	@Autowired
	private AssignDao assignDao = null;
	@Autowired
	private UserDao userDao = null;

	@Autowired
	private ResourceDao resourceDao = null;
	
	@Autowired
	private DeptDao deptDao = null;
	
	@Autowired
	private UserRoleDao userRoleDao = null;
	/**
	 * 根据申请人USER_NAME查询用户
	 * @return
	 */
	public User getUserByCreateName(String  userName){
		return userDao.getUserByUserName(userName);
	}
	/**
	 * 根据支公司，所属公司，创建用户查询用户
	 * @return
	 */
	public User findUserByUserName(User u) {
		return userDao.findUserByUserName(u);
	}
	
	/**
	 * 根据用户名加载用户
	 * 
	 * @param username
	 * @return
	 */
	public User loadUserByUsername(String username) {
		return userDao.loadUserByUsername(username);
	}

	/**
	 * 删除用户
	 * 
	 * @param ids
	 */
	public void deleteIds(String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			userDao.delete(idArray[i]);
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param password
	 * @param userId
	 */
	public void updatePassword(Long userId, String password) {
		userDao.updatePassword(userId, password);
	}

	/**
	 * 初始化系统管理员
	 * 
	 * @param rootOrgId
	 */
	public void initAdministrator(String rootOrgId) {
		User administrator = userDao.hasTypeForAdmin("0");
		User admin = userDao.hasTypeForAdmin("1");
		User AMSadmin = userDao.hasTypeForAdmin("2") ;
		if (null == administrator) {
			User user = new User();
			user.setAccountName("administrator");
			user.setPassword("111111");
			user.setPassword(PasswordMd5.createPassword(user.getPassword()));// 密码跟登录名一样,并且进行加密
			user.setUserName("系统管理员");
			user.setDeptId(rootOrgId);
			user.setIsDeleted(0);
			user.setLoginNum(0l);
			user.setUserType("0");
			userDao.save(user);
		} else {
			administrator.setDeptId(null);
			userDao.update(administrator);
		}
		if (null == admin) {
			User user = new User();
			user.setAccountName("admin");
			user.setPassword("111111");
			user.setPassword(PasswordMd5.createPassword(user.getPassword()));// 密码跟登录名一样,并且进行加密
			user.setUserName("管理员");
			user.setDeptId(rootOrgId);
			user.setIsDeleted(0);
			user.setLoginNum(0l);
			user.setUserType("1");
			userDao.save(user);
		} else {
			admin.setDeptId(null);
			userDao.update(admin);
		}
	}

	/**
	 * 组织登录用户
	 * 
	 * @param user
	 * @return
	 */
	public User loadSecurityUser(User user) {

		// 获取用户可以访问的菜单和资源
		List<Resource> resurceList = new ArrayList<Resource>();
		List<Resource> resurceList_ = new ArrayList<Resource>();
		// 超级管理员可以访问所有的资源
		if (null != user.getUserType()
				&& (user.getUserType().equals("0") || user.getUserType().equals("1") || user.getUserType().equals("2"))) {
			resurceList = resourceDao.findAllEnable();
		} else {
//				user.setDeptName("");
//				Dept org = deptDao.findOrgByDeptid(user.getDeptId());
//				if(org != null){
//					user.setDeptName(org.getDeptName());
//				}
				
			//以下是新权限结构
			//修改为从新结构获取权限
			resurceList_ = assignDao.getAllEnableByUserId(user.getId(),user.getAccountName());
			 for (Resource resource : resurceList_) {
				if(resurceList.size() == 0){
					resurceList.add(resource) ;
				}else{
					boolean flag = false ;
					for(Resource _temp : resurceList){
						if(_temp.getId() == resource.getId() || _temp.getId().equals(resource.getId())) {
							flag = true ;
							break ;
						}
					}
					if(!flag) resurceList.add(resource) ;
				}
			}
		}
		//String resourceStr = "";
		StringBuffer resourceStr = new StringBuffer();
		for (int i = 0; i < resurceList.size(); i++) {
			if (StrUtils.isNotNullOrBlank(resurceList.get(i).getUrl())) {
				resourceStr.append( resurceList.get(i).getUrl()+",");
				//resourceStr = resourceStr + resurceList.get(i).getUrl() + ",";
			}
		}
		LOGGER.info(resourceStr.toString() ) ;
		user.setResources(resourceStr.toString());
		user.setResourceList(resurceList);
		// 获取用户可以访问的模块
		List moduleResourceList = new ArrayList();
		for (int i = 0; i < resurceList.size(); i++) {
			if (null != resurceList.get(i).getType()
					&& resurceList.get(i).getType() == 1) {
				moduleResourceList.add(resurceList.get(i));
			}
		}
		user.setModuleResourceList(moduleResourceList);
		String roleCodes = StrUtils.CollectionToStr(userDao
				.findRoleCodeByUserId(user.getId()), ",", false);
		user.setRoleCodes(roleCodes);
		if (null != user.getUserType()
				&& (user.getUserType().equals("0") || user.getUserType().equals("1") || user.getUserType().equals("2"))) {
			user.setDeptName("系统节点");
			if (null == user.getRoleNames()) {
				user.setRoleNames("系统管理员");
			}
		} else if (null != user.getDept()) {
			user.setDeptName(user.getDept().getDeptName());
		}

		return user;
	
	}
	public boolean findUserByAccountName(String accountName){
		boolean b=userDao.findUserByAccountName(accountName);
		return b;
	}
	/**
	 * 保存用户
	 * 
	 * @param user
	 * @param userGroup
	 */
	public void save(User user, String[] roles) {
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		user.setIsDeleted(0);
		user.setCreateDeptId(currentUser.getDeptId());
		user.setCreateTime(new Date());
		user.setCreateUserId(currentUser.getId());
		user.setLoginNum(0l);
		
			userDao.save(user);
			if (null != roles) {
				for (String roleId : roles) {
					UserRole userRole = new UserRole();
					userRole.setRoleId(roleId);
					userRole.setUserId(user.getId());
					userDao.save(userRole);
				}
			}
			assembleUser(user);
		
	}
	

	/**
	 * 保存用户
	 * 
	 * @param user
	 * @param groups
	 */
	public void update(User user, String[] roles) {
		User oldUser = (User) userDao.findById(User.class, String.valueOf(user.getId()));
		oldUser.setUserName(user.getUserName());
		oldUser.setPassword(user.getPassword());
		oldUser.setDataPriv(user.getDataPriv());
		oldUser.setMobile(user.getMobile());
		oldUser.setCtiPassword(user.getCtiPassword());
		oldUser.setEmail(user.getEmail());
		oldUser.setJobNumber(user.getJobNumber());
		oldUser.setExtension(user.getExtension());
		oldUser.setYmSaleMan(user.getYmSaleMan());
		oldUser.setCity(user.getCity());
		oldUser.setProvice(user.getProvice());
		oldUser.setProjectCode(user.getProjectCode());
		//oldUser.setIsEnable(user.getIsEnable());
		//oldUser.setDeptId(user.getDeptId());
		//Dept dept = deptDao.findOrgByDeptid(user.getDeptId());
		//oldUser.setDeptName(dept.getDeptName());
		
		userDao.update(oldUser);
		if (null != roles) {
			userDao.deleteGrantRole(user.getId());
			for (String roleId : roles) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				userDao.save(userRole);
			}
		}
		assembleUser(oldUser);
	}

	/**
	 * 根据用户名获取组ID集合
	 * 
	 * @param userId
	 * @return
	 */
	private void assembleUser(User user) {
		String roleNames = StrUtils.CollectionToStr(userDao
				.findRoleNameByUserId(user.getId()), ",", false);
		if(StrUtils.isNotNullOrBlank(roleNames)){
			user.setRoleNames(roleNames);
		}
		userDao.update(user);
	}

	/**
	 * 根据用户Id获取角色ID集合
	 *@author duan update 
	 * @param userId
	 * @return
	 */
	public Collection findRoleIdByUserId(String userId) {
		return userDao.findRoleIdByUserId(userId);
	}

	/**
	 * 分页获取用户
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		return userDao.findByPage(page, searchMap);
	}

	public List findByPageOfParamsList(Page page , Map searchMap , List<String> deptIdList){
		return userDao.findByPageOfParamsList(page, searchMap , deptIdList) ;
	}
	
	/**
	 * 根据角色名称和机构ID获得用户集合
	 * 
	 * @param roleName
	 * @param orgId
	 * @return
	 */
	public List<User> findUserByRoldName(String roleName, Long orgId) {
		return userDao.findUserByRoldName(roleName, orgId);
	}
	
	/**
	 * 根据用户编号和部门setid查询用户
	 * 
	 * @param emplid
	 * @param setidDept
	 * @return
	 */
	public User findUserByEmplid(String emplid, String setidDept) {
		return userDao.findUserByEmplid(emplid, setidDept);
	}
	
	public List<Long> getAllUserId(){
		return userDao.getAllUserId() ;
	}

	/**
	 * 根据用户职务和部门setid查询用户
	 * 
	 * @param emplid
	 * @param setidDept
	 * @return
	 */
	public User findUserByJobcode(String[] jobcode, String deptid,
			String setidDept) {
		return userDao.findUserByJobcode(jobcode, deptid, setidDept);
	}

	/**
	 * 更新用户表的org_id这个字段
	 */
	public void updateOrgId() {
		userDao.updateOrgId();
	}

	public User findByHlUser(String username) {
		return userDao.findByHlUser(username);
	}
	/**
	 * 根据用户编号查找用户
	 * @param username
	 * @return
	 */
	public User findUserByUserName(String username) {
		return userDao.findUserByUserName(username);
	}
	
	/**
	 * 根据user的部门编号找该部门的主管
	 * @param deptid
	 * @return
	 */
	public User findDirectorByDeptId(String deptid){
		return userDao.findDirectorByDeptId(deptid);
	}
	
	/**
	 * 根据部门id获取此部门下面的所有用户
	 * @param deptId
	 * @return
	 */
	public List<User> getUserListByDeptId(String deptId){
		return userDao.getUserListByDeptId(deptId) ;
	}
	public List<User> findByDeptIds(String deptId,String companyId) {
	  if(!"undefined".equals(companyId) && !"".equals(companyId)){
		   StringBuffer hql = new StringBuffer("from User u where u.deptId=? and u.jobNumber=?");
		   return userDao.findByHql(hql, new Object[]{deptId,companyId});
	  }else{
		  StringBuffer hql = new StringBuffer("from User u where u.deptId=?");
		  return userDao.findByHql(hql, new Object[]{deptId});  
	  }
	}
	
}
