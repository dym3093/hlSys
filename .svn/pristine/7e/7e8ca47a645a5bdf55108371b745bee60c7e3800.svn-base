package org.hpin.base.resource.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.base.resource.entity.Resource;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.springframework.stereotype.Repository;

/**
 * <p>@desc : 资源Dao</p>
 * <p>@see : </p>
 *
 * <p>@author : sky</p>
 * <p>@createDate : Aug 30, 2012 11:13:57 AM</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p>
 */
@Repository()
public class ResourceDao extends BaseDao {
	/**
	 * 获取菜单项
	 * @return
	 */
	public List<Resource> findMenuByType() {
		return this.getHibernateTemplate().find(
				" from Resource where type = 1 and isEnable=1 order by orderCode asc");
	}
		
	/**
	 * 获取可用的菜单
	 * 
	 * @return
	 */
	public List<Resource> findAllEnable() {
		String queryString = " from Resource r where r.isEnable=1 order by r.orderCode asc";
		return this.getHibernateTemplate().find(queryString);
	}

	/**
	 * 获取可以访问的模块
	 * 
	 * @return
	 */
	public List<Resource> findAllEnableModuleResource() {
		List list = new ArrayList();
		String queryString = " select r.id,m.name from sys_resource r where r.is_enable!=0 and r.type=2 order by r.orderCode asc";
		List resultList = this.getJdbcTemplate().queryForList(queryString);
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			Resource resource = new Resource();
			map = (Map) resultList.get(i);
			//resource.setId(StrUtils.ObjectToLong(map.get("id")));
			resource.setId(StrUtils.ObjectToStr(map.get("id")));
			resource.setName(StrUtils.ObjectToString(map.get("name")));
			list.add(resource);
		}
		return list;
	}

	/**
	 * 根据父ID获取可用的菜单
	 * duan update
	 * @param parentId
	 * @return
	 */
	public List<Resource> findEnableMenuByParentId(String parentId) {
		String queryString = null;
		List list = null;
		if (null != parentId) {
			queryString = "  from Resource r where r.parentId =? and isEnable!=0 and type!=4 order by orderCode";
			list = super.getHibernateTemplate().find(queryString, parentId);
		} else {
			queryString = "  from Resource r where r.parentId is null and isEnable!=0 and type!=4 order by orderCode";
			list = super.getHibernateTemplate().find(queryString);
		}
		return list;
	}
	
	/**
	 * 根据父ID获取可用的菜单和button
	 * 
	 * @param parentId
	 * @return
	 */
//	public List<Resource> findEnableMenuAndButtonByParentId(Long parentId) {
//		String queryString = null;
//		List list = null;
//		if (null != parentId) {
//			Resource resource = (Resource)findById(Resource.class, parentId) ;
//			if(Integer.valueOf(1).equals(resource.getIsLeaf())){
//				queryString = "  from Resource r where r.parentId =? and isEnable!=0  order by orderCode";
//			}else{
//				queryString = "  from Resource r where r.parentId =? and isEnable!=0 and type!=4 order by orderCode";
//			}
//			list = super.getHibernateTemplate().find(queryString, parentId);
//		} else {
//			queryString = "  from Resource r where r.parentId is null and isEnable!=0 and type!=4 order by orderCode";
//			list = super.getHibernateTemplate().find(queryString);
//		}
//		return list;
//	}
	public List<Resource> findEnableMenuAndButtonByParentId(String parentId) {
		String queryString = null;
		List list = null;
		if (null != parentId) {
			Resource resource = (Resource)findById(Resource.class, parentId) ;
			if(Integer.valueOf(1).equals(resource.getIsLeaf())){
				queryString = "  from Resource r where r.parentId =? and isEnable!=0  order by orderCode";
			}else{
				queryString = "  from Resource r where r.parentId =? and isEnable!=0 and type!=4 order by orderCode";
			}
			list = super.getHibernateTemplate().find(queryString, parentId);
		} else {
			queryString = "  from Resource r where r.parentId is null and isEnable!=0 and type!=4 order by orderCode";
			list = super.getHibernateTemplate().find(queryString);
		}
		return list;
	}
	/**
	 * 获取菜单的叶子节点下的按钮
	 * @param 父节点id，即菜单的叶子节点id
	 * @return
	 */
	public List<Resource> findEnableButtonByParentId(String parentId){
		String queryString = null;
		List list = null;
		if (null != parentId) {
			queryString = "  from Resource r where r.parentId = ? and isEnable != 0 and type = 4 and isButton = 1 order by orderCode";
			list = super.getHibernateTemplate().find(queryString, parentId);
		} 
		return list;
	}

	/**
	 * 基于jdbc保存菜单
	 * duan update
	 * @param resource
	 */
	public void saveForJdbc(Resource resource) {
//		long id = this.getJdbcTemplate().queryForLong(
//				"select max(id) from sys_resource") + 1;
//		resource.setId(id);
		String sql = "insert into sys_resource(name,is_leaf,url,order_code,is_enable,parent_id,type,icon_cls)values(?,?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(
				sql,
				new Object[] { resource.getName(), resource.getIsLeaf(),
						resource.getUrl(), resource.getOrderCode(),
						resource.getIsEnable(), resource.getParentId(),
						resource.getType(), resource.getIconCls()});
	}

	/**
	 * 获取根节点
	 * 
	 * @return
	 */
	public Resource findRootResource() {
		String queryString = " from Resource r where parentId is null";
		List list = this.getHibernateTemplate().find(queryString);
		return (Resource) list.get(0);
	}
	
	public List<Resource> findBaseResources(){
		 String hql = " from Resource where url is null and isEnable  = 1 and parentId = 1 and type = 1 order by orderCode asc" ;
		 return super.getHibernateTemplate().find(hql) ;
	}

	/**
	 * 获取用户可以访问的资源
	 * 
	 * @param userId
	 * @return
	 */
	public List<Resource> findAllEnableByUserId(Long userId) {
		String queryString = "select r  from Resource r,UserRole ur,RoleResource rs "
				+ "where r.id=rs.resourceId and rs.roleId=ur.roleId and ur.userId=? order by r.orderCode asc";
		return this.getHibernateTemplate().find(queryString, userId);
	}

	/**
	 * 根据ID获取父ID
	 * 
	 * @param menuId
	 * @return
	 */
	public String findParentIdByIdForJdbc(String menuId) {
		String queryString = " select m.parentId from Resource m where m.id=?";
		//Long parentId = this.getJdbcTemplate().queryForLong(queryString, new Object[]{menuId });
		List list = this.getHibernateTemplate().find(queryString, new Object[]{menuId });
		String parentId = "";
		if (list != null && list.size() > 0) parentId = (String)list.get(0); 
		return parentId;
	}

	/**
	 * 根据父ID显示资源树
	 * duan update
	 * @param parentId
	 * @return
	 */
	public List<Resource> findByParentId(String parentId) {
		return this.getHibernateTemplate().find(
				" from Resource where parentId=?", parentId);
	}

	/**
	 * 删除菜单资源
	 */
	public void deleteAllResource() {
		this.getJdbcTemplate().update(" delete from sys_resource");
	}

	public List<Resource> findEnableOperation() {
		return this.getHibernateTemplate().find(
				" from Resource where type=4 and isEnable=1 order by id asc");
	}

	/**
	 * 获取用户授权的资源
	 * 
	 * @param userId
	 * @return
	 */
	public List<Resource> findAllEnableForUserId(Long userId) {
		List list = new ArrayList();
		String queryString = " select distinct r.id,r.name,r.url,r.is_leaf,r.parent_id,r.type,r.order_code from sys_resource r,um_user_role ur,um_role_resource rr "
				+ " where r.id=rr.resource_id and rr.role_id=ur.role_id and r.is_enable=1 and ur.user_id=? order by r.order_code";
		List resultList = this.getJdbcTemplate().queryForList(queryString,
				new Object[]{userId});
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
	public List<Resource> findByModulePage(Page page , Map paramsMap){
		StringBuffer queryBuffer = new StringBuffer(" from Resource where type = 1 ") ;
		List valueList = new ArrayList() ;
		OrmConverter.getQuery(queryBuffer, paramsMap,
				"filter_and_name_LIKE_S", valueList);
		queryBuffer.append(" order by orderCode asc") ;
		return super.findByHql(page, queryBuffer, valueList) ;
	}
	public List<Resource> findByparentId(Page page , Map paramsMap,String parentId){
		StringBuffer queryBuffer = new StringBuffer(" from Resource where parentId = '"+parentId+"'") ;
		List valueList = new ArrayList() ;
		OrmConverter.getQuery(queryBuffer, paramsMap,
				"filter_and_name_LIKE_S", valueList);
		queryBuffer.append(" order by orderCode asc") ;
		return super.findByHql(page, queryBuffer, valueList) ;
	}
	
	
}
