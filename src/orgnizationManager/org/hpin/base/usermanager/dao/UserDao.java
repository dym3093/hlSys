package org.hpin.base.usermanager.dao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hpin.base.dict.dao.ID2NameDAO;
import org.hpin.base.dict.exceptions.DictDAOException;
import org.hpin.base.region.entity.Region;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.Constant;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.springframework.stereotype.Repository;

@Repository(value="org.hpin.base.usermanager.dao.UserDao")
public class UserDao extends BaseDao implements ID2NameDAO{
	/**
	 * 根据申请人USER_NAME查询用户
	 * @return
	 */
	public User getUserByUserName(String  userName) {
		String sql = "from User where userName = ?  and  isDeleted = 0 and isEnable = 1 ";
		List<User> list = this.getHibernateTemplate().find(sql,new Object[]{userName});
		User user = null;
		if(list != null && list.size() >0){
			user = list.get(0);
		}
		return user;
	}
	/**
	 * 根据支公司，所属公司，创建用户查询用户
	 * @return
	 */
	public User findUserByUserName(User u) {
		String sql = "from User where userName = ? and extension=? and deptName=? and  isDeleted = 0 and isEnable = 1 ";
		List<User> list = this.getHibernateTemplate().find(sql,new Object[]{u.getUserName(),u.getExtension(),u.getDeptName()});
		
//		String sql = "from User where userName = ? and jobNumber=? and  isDeleted = 0 and isEnable = 1 ";
//		List<User> list = this.getHibernateTemplate().find(sql,new Object[]{u.getUserName(),u.getJobNumber()});//支公司号
		
		User user = null;
		if(list != null && list.size() >0){
			user = list.get(0);
		}
		return user;
	}
	public String id2Name(String id) {
		// TODO Auto-generated method stub
		String hql = " from User where deptId =? order by id asc " ;
		List<User> regionList = super.getHibernateTemplate().find(hql , id) ;
		if(regionList != null && regionList.size() > 0){
			return regionList.get(0).getDeptName() ;
		}
		return null ;
	}
	
	/**
	 * 判断账户是否重复
	 * @param deptid
	 * @return
	 */
	public boolean findUserByAccountName(String accountName) {
		String sql = "from User where accountName = ? and isDeleted = 0 and isEnable = 1";
		List<User> list = this.getHibernateTemplate().find(sql,accountName);
		User user = null;
		if(list != null && list.size() >0){
			return true;
		}
		return false;
	}
	/**
	 * 删除用户所有的角色
	 * @author duan  update
	 * @param userId
	 */
	public void deleteGrantRole(String userId) {
		this.getHibernateTemplate().bulkUpdate(
				"delete from UserRole where userId=?", userId);
	}

	/**
	 * 根据用户名加载用户
	 * 
	 * @param username
	 * @return
	 */
	public User loadUserByUsername(String username) {
		String queryString = null;
		if (username.equals(Constant.admin_name) // 管理员情况的查询语句
				|| username.equals(Constant.administrator_name) || username.equals(Constant.AMSadmin_name)) {
			queryString = " from User u where isDeleted=0 and u.accountName=?";
		} else { // 管理员情况的查询语句
			queryString = " from User u where isDeleted=0  and isEnable = 1 and u.accountName=? ";
		}
		List<User> list = this.getHibernateTemplate().find(queryString,username);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据用户编号和部门setid查询用户
	 * 
	 * @param emplid
	 * @param setidDept
	 * @return
	 */
	public User findUserByEmplid(String emplid, String setidDept) {
		//String queryString = " from User u where isDeleted=0 and u.emplStatus='A' and u.emplid=? and u.setidDept=? order by emplRcd";
		//emplRcd 员工记录（0为主岗、其它为兼职岗位）实际使用的时候需要使用
		//effseq 生效序号（一天内的多次变动则去生效序号最大的）
		String queryString = " from User u where isDeleted=0 ";
		List<User> list = this.getHibernateTemplate().find(queryString, new Object[]{emplid,
				setidDept});
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 0) { // 用户有多条数据，取emplRcd最大的一条
			return list.get(list.size() - 1);
		} else {
			return list.get(0);
		}
	}
	public List<User> findUserByEmplidNew(String emplid, String setidDept) {
		//String queryString = " from User u where isDeleted=0 and u.emplStatus='A' and u.emplid=? and u.setidDept=? order by emplRcd";
		//emplRcd 员工记录（0为主岗、其它为兼职岗位）实际使用的时候需要使用
		//effseq 生效序号（一天内的多次变动则去生效序号最大的）
		String queryString = " from User u where isDeleted=0";
		List<User> list = this.getHibernateTemplate().find(queryString, new Object[]{emplid,
				setidDept});
		if (list.isEmpty()) {
			return null;
		}
		else {
			return list;
		}
	}

	public List<Long> getAllUserId(){
		String query = "select u.id from User u where u.isDeleted = 0" ;
		return super.getHibernateTemplate().find(query) ;
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
		StringBuilder queryString = new StringBuilder(
				"from User u where isDeleted=0 and u.deptid=?");
		if (jobcode != null && jobcode.length > 0) {
			queryString.append(" and u.jobcode in (");
			for (int i = 0; i < jobcode.length; i++) {
				queryString.append(jobcode[i]);
				if (i < jobcode.length - 1) {
					queryString.append(",");
				}
			}
			queryString.append(") ");
		}
		queryString.append(" order by u.emplRcd");
		List<User> list = this.getHibernateTemplate().find(
				queryString.toString(), new Object[]{deptid, setidDept});
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 0) { // 用户有多条数据，取emplRcd最大的一条
			return list.get(list.size() - 1);
		} else {
			return list.get(0);
		}
	}

	/**
	 * 删除用户
	 */
	public void delete(String id) {
		/*this.getJdbcTemplate().update("delete from um_user_role where user_id=" + id);
		this.getJdbcTemplate().update("update um_user set is_delete = 1 where id='"+id+"'");*/
		this.getJdbcTemplate().update("delete from um_user where id='"+id+"'");
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param password
	 */
	public void updatePassword(Long userId, String password) {
		this.getHibernateTemplate().bulkUpdate(
				" update User set password=? where id=?",
				new Object[] { password, userId });
	}

	/**
	 * 判断是否含有超级管理员
	 * 
	 * @return
	 */
	public User hasTypeForAdmin(String userType) {
		String queryString = " from User where userType=?";
		List list = this.getHibernateTemplate().find(queryString, userType);
		if (list.size() == 0) {
			return null;
		}
		return (User) list.get(0);
	}

	/**
	 * 根据用户Id获取角色ID集合
	 * @author duan update
	 * @param userId
	 * @return
	 */
	public Collection findRoleIdByUserId(String userId) {
		return this.getHibernateTemplate().find(
				"select roleId from UserRole where userId=?", userId);
	}

	/**
	 * 根据用户Id获取角色名称集合
	 * @author duan update
	 * @param userId
	 * @return
	 */
	public Collection findRoleNameByUserId(String userId) {
		return this.getHibernateTemplate().find(
				"select role.name from UserRole where userId=?", userId);
	}
	
	/**
	 * 根据角色名称和机构ID获得用户集合
	 * 
	 * @param roleName
	 * @param orgId
	 * @return
	 */
	public List<User> findUserByRoldName(String roleName, Long orgId) {
		return this
				.getHibernateTemplate()
				.find(
						"from user where id in (select userId from UserRole where role.name = ?) and orgId = ? ",
						new Object[]{roleName, orgId});
	}

	/**
	 * 根据组ID获取用户ID集合
	 * 
	 * @param groupId
	 * @return
	 */
	public List findUserIdByGroupId(Long groupId) {
		List list = this.getHibernateTemplate().find(
				"select distinct(userId) from UserGroup where groupId=?",
				groupId);
		return list;
	}

	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from User where isDeleted=0  and isEnable = 1");
		searchMap.put(" deptId", "asc");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}

	public List findByPageOfParamsList(Page page , Map searchMap , List<String> deptIdList){
		StringBuffer query = new StringBuffer(" from User where isDeleted = 0 and isEnable = 1"); 
		List valueList = new ArrayList() ;
		OrmConverter.getQuery(query, searchMap, "filter_and_deptId_EQ_S", valueList) ;
		if(deptIdList != null){
			for(String str : deptIdList){
				query.append(" or deptId = '").append(str).append("' "); 
			}
		}
		searchMap.put(" deptId", "asc") ;
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}
	
	//duan update
	public Collection findRoleCodeByUserId(String userId) {
		return this.getHibernateTemplate().find(
				"select role.code from UserRole where userId=?", userId);
	}

	/**
	 * 更新用户表的org_id这个字段
	 */
	public void updateOrgId() {
		this
				.getJdbcTemplate()
 				.update(
						"update um_user u set u.org_id=(select max(o.id) from um_org o where u.deptid=o.deptid)");
	}

	/**
	 * 根据hr登录系统
	 * 
	 * @param username
	 * @return
	 */
	public User findByHlUser(String username) {
		// 临时处理，用户有多条数据，取emplRcd最大的一条，HR同步的时候不应该出现多条（thinkpad）
		String queryString = null;
		if (username.equals(Constant.admin_name) // 管理员情况的查询语句
				|| username.equals(Constant.administrator_name)) {
			queryString = " from User u where isDeleted=0 and u.username=?";
		} else { // 管理员情况的查询语句
			queryString = " from User u where u.isDeleted=0 ";
		
		}
		List<User> list = this.getHibernateTemplate().find(queryString,
				username);
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 0) {// 临时处理，用户有多条数据，取emplRcd最大的一条，HR同步的时候不应该出现多条（thinkpad）
			return list.get(list.size() - 1);
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 根据机构id
	 * @param deptid
	 * @return
	 */
	public List<User> findUserByDeptId(String username) {
		User user = findUserByUserName(username);
		if(user == null){
			return null;
		}else{
			return this.getHibernateTemplate().find("from User where deptId = ?",user.getDeptId());
		}
	}
	/**
	 * 根据用户编号查找用户
	 * @param deptid
	 * @return
	 */
	public User findUserByUserName(String username) {
		String sql = "from User where username = ? and emplRcd = 0 and isDeleted = 0 and isEnable = 1 and setidDept = 'HL001' and emplStatus = 'A'";
		List<User> list = this.getHibernateTemplate().find(sql,username);
		User user = null;
		if(list != null && list.size() >0){
			user = list.get(0);
		}
		return user;
	}
	
	/**
	 * 根据用户编号查找用户
	 * @param deptid
	 * @return
	 */
	public User findDirectorByDeptId(String deptid) {
		String sql = "select um from User um,JobcodeStg hl where um.jobcode = hl.jobcode and hl.setid = 'HL001' and hl.hlJobSeries = 'FUNC' and hl.hlJobLevel = '2'and um.deptid = ?";
		List<User> list = this.getHibernateTemplate().find(sql,deptid);
		User user = null;
		if(list != null && list.size() >0){
			user = list.get(0);
		}
		return user;
	}
	
	/**
	 * 根据机构id
	 * @param deptid
	 * @return
	 */
	public List<User> getUserListByDeptId(String deptId) {
		return super.getHibernateTemplate().find(" from User where deptId = ? and isDeleted = 0" , deptId) ;
	}
	@Override
	public String id2Field(String id, String beanId, String field)
			throws DictDAOException {
		String hql = "from User where "+field+"=?";
		@SuppressWarnings("unchecked")
		List<User> users = super.getHibernateTemplate().find(hql, id);
		if(users != null && users.size() > 0) {
			return users.get(0).getUserName();
			
		}
		return null;
	}
	
	/**
	 * 通过id查询User对象;
	 * create by henry.xu 2016年12月14日
	 * @param userId
	 * @return
	 */
	public User findUserById(String userId) {
		return this.getHibernateTemplate().get(User.class, userId);
	}
}
