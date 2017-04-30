package org.hpin.base.usermanager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.base.usermanager.entity.Role;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.springframework.stereotype.Repository;

/**
 * Role Dao
 * 
 * @author thinkpad
 * 
 */
@Repository()
public class RoleDao extends BaseDao {

	public void delete(String id) {
		this.getHibernateTemplate().bulkUpdate(
				" delete from ResourceOwner where ownerId=? and assignType=3", id);
		this.getHibernateTemplate().bulkUpdate(
				" delete from UserRole where roleId=?", id);
		this.getHibernateTemplate().bulkUpdate(" delete from Role where id=?",
				id);
		this.getHibernateTemplate().bulkUpdate(" delete from Role where parentId=?",
				id);
	}

	/**
	 * 分页获取角色
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from Role where 1=1 ");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}

	/**
	 * 获取角色授权资源
	 * 
	 * @param roleId
	 * @return
	 */
	public List findRoleResource(String roleId) {
		return this.getHibernateTemplate().find(
				" from RoleResource where roleId=?", roleId);
	}
	
	/**
	 * 获取所有的角色
	 * @return
	 */
	public List findAllRole() {
		return this.getHibernateTemplate().find(
				" from Role where isDeleted=0");
	}
	
	public List findRoleBycode(String code) {
		return this.getHibernateTemplate().find(" from Role where code = ? ",code);
	}
	
	/**
	 * 获取所有的用户角色表信息
	 * @return
	 */
	public List findAllUserRole() {
		return this.getHibernateTemplate().find(
				"select role.id,role.userId,role.roleId,user.userName,user.accountName,user.jobNumber from UserRole role,User user where role.userId = user.id and user.isDeleted=0 ");
	}

	public void deleteGrantResourceByRoleId(String roleId) {
		this.getJdbcTemplate().update(
				" delete from um_role_resource where role_id=?", new Object[]{roleId});
	}
	/**
	 * 通过角色编号找到对应的角色
	 * @param code 编号
	 * @return 
	 * zhangyan
	 */

	public Role getRoleByCode(String code){
		//String sql = "from Role where code = ?";
		List<Role> list = this.getHibernateTemplate().find("from Role where code = ?", code);
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
//	public List getUserRoles(Long roleId){
//		return this.getHibernateTemplate().find(
//				"select ur.userId,u.name from UserRole ur,User u where ur.userId=u.id and ur.roleId=?", roleId);
//	}
	
	public List getUserRoles(String roleId){
		return this.getHibernateTemplate().find(
				"select ur.userId,u.userName from UserRole ur,User u where ur.userId=u.id and ur.roleId=?", roleId);
	}
	
	//duan update
	public void deleteUserRoleByRoleId(String roleId) {
		this.getJdbcTemplate().update(
				" delete from um_user_role where role_id=?", new Object[]{roleId});
	}
	
	public List getRolesByParentId(String parentId){
		return this.getHibernateTemplate().find(
				"select r from Role r where r.parentId=?", parentId);
	}
}
