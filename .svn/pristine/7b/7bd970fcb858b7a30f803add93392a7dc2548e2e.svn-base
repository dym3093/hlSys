package org.hpin.base.priv.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.base.resource.entity.Resource;
import org.hpin.base.usermanager.dao.DeptDao;
import org.hpin.base.usermanager.dao.UserDao;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.Role;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.entity.UserRole;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.springframework.stereotype.Repository;

/**
 * @author : 张艳
 * 
 *
 */
@Repository()
public class AssignDao extends BaseDao {
	//通过roleName找role
	public List<Role> findByroleName(String roleName){
		String hql="from Role r where r.name in("+roleName+")";
		List<Role> roleList = super.getHibernateTemplate().find(hql) ;
		return roleList ;
	} 
	
	public List<UserRole> findByroleId(Long roleId){
		String sql="from UserRole where roleId=?";
		return this.getHibernateTemplate().find(sql, roleId);
	}
	public void delete(Long id) {
		this.getHibernateTemplate().bulkUpdate(
				" delete from RoleResource where roleId=?", id);
		this.getHibernateTemplate().bulkUpdate(
				" delete from UserRole where roleId=?", id);
		this.getHibernateTemplate().bulkUpdate(" delete from Role where id=?",
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
		StringBuffer query = new StringBuffer(" from Role");
		List valueList = new ArrayList();
		return super.findByHql(page, query, valueList);
	}

	/**
	 * 获取角色授权资源
	 * 
	 * @param roleId
	 * @return
	 */
	public List findRoleResource(Long roleId) {
		return this.getHibernateTemplate().find(
				" from RoleResource where roleId=?", roleId);
	}

	//duan udpate 
	public void deleteGrantResourceByRoleId(String roleId) {
		this.getJdbcTemplate().update(
				" delete from um_role_resource where role_id=?", new Object[]{roleId});
	}
	/**
	 * 获取用户授权的资源
	 * 
	 * @param userId
	 * @return
	 */
	public List<Resource> findAllEnableForUserId(Long roleId) {
		List list = new ArrayList();
		String queryString = "select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
						"from sys_resource r,um_role_resource rr where r.id=rr.resource_id and rr.role_id=?"+
						" order by r.order_code";
		List resultList = this.getJdbcTemplate().queryForList(queryString, new Object[]{roleId});
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			Resource resource = new Resource();
			map = (Map) resultList.get(i);
			resource.setId(StrUtils.ObjectToStr(map.get("id")));
			resource.setParentId(StrUtils.ObjectToStr(map.get("parent_id")));
			resource.setName(StrUtils.ObjectToString(map.get("name")));
			resource.setUrl(StrUtils.ObjectToString(map.get("url")));
			resource.setType(StrUtils.ObjectToInteger(map.get("type")));
			resource.setOrderCode(StrUtils.ObjectToInteger(map
					.get("order_code")));
			resource.setIsLeaf(StrUtils.ObjectToInteger(map.get("is_leaf")));
			list.add(resource);
		}
		return list;
	}
	/**
	 * 分页获取对象,根据角色名称获得用户集合
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findUserByPage(Page page, Map searchMap,Long roleId) {
		StringBuffer query = new StringBuffer("from User where id in (select userId from UserRole where roleId = "+roleId+")");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}
	/**
	 * 分页获取对象,根据部门ID获得用户集合
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findOrgUserByPage(Page page, Map searchMap,Long orgId) {
		StringBuffer query = new StringBuffer("from User where orgId="+orgId+"");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}
	
	/**
	 * 根据用户id获取用户授权的资源
	 * 
	 * @param userId
	 * @return
	 */
	/*public List<Resource> findAllEnableByUserId(Long userId) {
		List list = new ArrayList();
		String queryString = "select * from (select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
						"from sys_resource r,um_resource_owner ro,um_user u where ro.is_deleted=0 and r.id=ro.resource_id and " +
						"ro.owner_id=u.org_id and ro.assign_type=1 and u.id=? "+
						"union "+
						"select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
						"from sys_resource r,um_resource_owner ro where ro.is_deleted=0 and r.id=ro.resource_id and ro.assign_type=2 and ro.owner_id=? "+
						"union " +
						"select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
						"from sys_resource r,um_resource_owner ro,um_user_role ur where ro.is_deleted=0 and r.id=ro.resource_id and " +
						"ro.owner_id=ur.role_id and ro.assign_type=3 and ur.user_id=? "+
						") t order by t.order_code";
		System.out.println(queryString);
		List resultList = this.getJdbcTemplate().queryForList(queryString,userId,userId,userId);
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			Resource resource = new Resource();
			map = (Map) resultList.get(i);
			resource.setId(StrUtils.ObjectToLong(map.get("id")));
			resource.setParentId(StrUtils.ObjectToLong(map.get("parent_id")));
			resource.setName(StrUtils.ObjectToString(map.get("name")));
			resource.setUrl(StrUtils.ObjectToString(map.get("url")));
			resource.setType(StrUtils.ObjectToInteger(map.get("type")));
			resource.setOrderCode(StrUtils.ObjectToInteger(map
					.get("order_code")));
			resource.setIsLeaf(StrUtils.ObjectToInteger(map.get("is_leaf")));
			list.add(resource);
		}
		return list;
	}*/
	/**
	 * 根据用户id获取用户授权的资源
	 * @author duan update
	 * @param userId
	 * @return
	 */
	public List<Resource> findAllEnableByUserId(String userId,String userName) {
		List list = new ArrayList();
		/*String queryString = "select * from (select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
						"from sys_resource r,um_resource_owner ro,um_user u where ro.is_deleted=0 and r.id=ro.resource_id and " +
						"ro.owner_id=u.DEPT_ID and ro.assign_type=1 and u.id=? "+
						"union "+
						"select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
						"from sys_resource r,um_resource_owner ro where ro.is_deleted=0 and r.id=ro.resource_id and ro.assign_type=2 and ro.owner_id=? "+
						"union " +
						"select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
						"from sys_resource r,um_resource_owner ro,um_user_role ur where ro.is_deleted=0 and r.id=ro.resource_id and " +
						"ro.owner_id=ur.role_id and ro.assign_type=3 and ur.user_id=? "+
						") t order by t.order_code";*/
		
		String queryString = "select * from (select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
				"from sys_resource r,um_resource_owner ro,um_user u where ro.is_deleted=0 and r.id=ro.resource_id and " +
				"ro.owner_id=u.DEPT_ID and ro.assign_type=1 and u.id=? "+
				"union "+
				"select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
				"from sys_resource r,um_resource_owner ro where ro.is_deleted=0 and r.id=ro.resource_id and ro.assign_type=2 and ro.owner_id=? ) t order by t.order_code";
				/*"union " +
				"select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code " + 
				"  from sys_resource r, um_resource_owner ro, um_user_role ur " + 
				" where ro.is_deleted = 0 and r.id = ro.resource_id " + 
				"   and ro.owner_id in (select brr.role_id from um_big_role_and_role brr " + 
				"     where brr.big_role_id = (select ubo.big_role_id from um_user_big_role ubo " +
				"     	where ubo.user_id = ?)) and ro.assign_type = 3 ) t order by t.order_code";*/
		String queryString2 = "select * from (select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
				"from sys_resource r,um_resource_owner ro,um_user u where ro.is_deleted=0 and r.id=ro.resource_id and " +
				"ro.owner_id=u.DEPT_ID and ro.assign_type=1 and u.id=? "+
				"union "+
				"select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code "+
				"from sys_resource r,um_resource_owner ro where ro.is_deleted=0 and r.id=ro.resource_id and ro.assign_type=2 " +
				"and ro.owner_id=?   union select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code " +
				"from sys_resource r,um_resource_owner ro where ro.is_deleted=0 and r.id=ro.resource_id " +
				"and ro.assign_type=1 and ro.owner_id='40289b6a5206079d0152061530000007') t order by t.order_code";
		
		String sql = "select count(*) from um_user u where u.dept_id in " +
				"(select d.id from um_dept d where d.id ='40289b6a5206079d0152061530000007' or d.parent_id ='40289b6a5206079d0152061530000007')" +
				"and u.id = '"+userId+"'";
		int x =this.getJdbcTemplate().queryForInt(sql);//判断是否为保险公司
		if(x==1){
			queryString=queryString2;
		}
		List resultList = this.getJdbcTemplate().queryForList(queryString, new Object[]{userId,userName});
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			Resource resource = new Resource();
			map = (Map) resultList.get(i);
			resource.setId(StrUtils.ObjectToStr(map.get("id")));
			resource.setParentId(StrUtils.ObjectToStr(map.get("parent_id")));
			resource.setName(StrUtils.ObjectToString(map.get("name")));
			resource.setUrl(StrUtils.ObjectToString(map.get("url")));
			resource.setType(StrUtils.ObjectToInteger(map.get("type")));
			resource.setOrderCode(StrUtils.ObjectToInteger(map
					.get("order_code")));
			resource.setIsLeaf(StrUtils.ObjectToInteger(map.get("is_leaf")));
			
			list.add(resource);
		}
		return list;
	}
	
	public List<Resource> getAllEnableByUserId(String userId,String userName){
		List<Resource> userResList = findAllEnableByUserId(userId, userName) ;
		String queryString = " from Resource r where r.isEnable=1 order by r.orderCode asc";
		List<Resource> _allResource = super.getHibernateTemplate().find(queryString) ;
		List<Resource> allResourceList = new ArrayList<Resource>() ;
		for(Resource res : _allResource){
			for(Resource _res : userResList){
				if(res.getId().equals(_res.getId())) allResourceList.add(res) ;
			}
		}
		return allResourceList ;
	}
	
}
