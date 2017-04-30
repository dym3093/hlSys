package org.hpin.base.usermanager.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hpin.base.customerrelationship.dao.ID2NameDAO;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * 部门DAO
 * 
 * @author thinkpad
 * @data Oct 8, 2009
 */
@Repository(value="org.hpin.system.userManager.dao.DeptDao")
public class DeptDao extends BaseDao implements ID2NameDAO{
	/**
	 * 查询下一级信息
	 * 
	 * @param parentdictid
	 * @return
	 */
	public ArrayList getDictSonsByDictid(final String parentdictid) {
		final String hql = " from Dept dicttype where dicttype.deptCode = ? order by dicttype.dictCode";
		List list = (List)super.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException , SQLException {
				Query query = session.createQuery(hql).setParameter(0 , parentdictid) ;
				query.setCacheable(true) ;
				return query.list() ;
			}
		}) ;
		return (ArrayList) list;
	}
	
	/*
	 * id2name，即字典id转为字典名称 added by qinmin
	 * 
	 * @see com.hpin.base.dao.ID2NameDAO#id2Name(java.lang.String)
	 */
	public String id2Name( String id) throws Exception {
		String queryString = " from Dept o where o.isDeleted=0 and o.id = ?";
		Dept d = (Dept) super.getHibernateTemplate().find(queryString,new Object[]{id}).get(0);
		return d.getDeptName();
	}
	/**
	 * 获取根节点
	 * 
	 * @return
	 */
	public Dept findRootOrg() {
		String queryString = null;
		//queryString = " from Org o where o.parentId is null and isDel=0";
		//String orgPath = SystemConstant.getSystemConstant("show_parent_org");
		queryString = " from Dept o where isDeleted = 0";
		List<Dept> list = super.getHibernateTemplate().find(queryString);
		list = super.getHibernateTemplate().find(queryString);
		return list.get(0);
	}
	
	/**
	 * 根据部门编号得到部门信息
	 * @param setid 公司setid
	 * @param deptid　部门编号
	 * @return
	 */
	public Dept findOrgByDeptid(String deptid) {
		String queryString = " from Dept o where o.isDeleted=0 and o.id = ?";
		List<Dept> list = super.getHibernateTemplate().find(queryString,new Object[]{deptid});
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 根据总公司编号得到所有分公司信息
	 * @param setid 公司setid
	 * @param deptid　部门编号
	 * @return
	 */
	public List<CustomerRelationShip> findBannyByDeptid(String deptid) {
		String queryString = " from CustomerRelationShip o where o.isDeleted=0 and o.ownedCompany = ? order by o.createTime desc";
		List<CustomerRelationShip> list = super.getHibernateTemplate().find(queryString,new Object[]{deptid});
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}
	
	/**
	 * 根据部门级别得到所有部门信息
	 * @param setid 公司setid
	 * @param hlDeptGra　部门级别
	 * @return list
	 */
	public List<Dept> findAllDept() {
		String queryString = "from Dept o where o.isDeleted=0 order by o.parentId,o.sortId";
		List<Dept> list = super.getHibernateTemplate().find(queryString);
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}
	
	public List<User> findUserByDeptId(String deptId) {
		String queryString = "from User o where o.isDeleted=0 and o.deptId = ?";
		List<User> list = super.getHibernateTemplate().find(queryString,deptId);
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}

	/**
	 * 是否含有叶子节点
	 * 
	 * @param parentId
	 * @return
	 */
	public Long hasCountChild(Long parentId) {
		String queryString = " select count(*) from Dept where parentId=? and isDeleted=0";
		List list = this.getHibernateTemplate().find(queryString, parentId);
		Long count = (Long) list.get(0);
		return count;
	}

	/**
	 * 根据父ID获取对象
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Dept> findByParentId(String parentId) {
		return this
		.getHibernateTemplate().find(
				" from Dept where parentId=? and isDeleted=0 order by sortId asc",
				parentId);
	}

	/**
	 * 根据父path获取对象
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Dept> findByParentPath(String parentId) {
		if (null == parentId) {
			return this
					.getHibernateTemplate()
					.find(
							" from Dept where isDeleted = 0 order by sortId asc");
		} else {
			return this
					.getHibernateTemplate()
					.find(
							" from Dept where parentId=? and isDeleted = 0 order by sortId asc",
							parentId);
		}
	}
	
	/**
	 * 根据hlTreepath获取对象
	 * @param hlTreepath
	 * @return
	 */
	public Dept findDeptByTreepath(String deptId) {
		String queryString = null;
		queryString = " from Dept o where o.isDeleted=0 and o.id = ?";
		List<Dept> list = super.getHibernateTemplate().find(queryString,deptId);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<User> findAllUser() {
		String queryString = null;
		queryString = " from User o where o.isDeleted=0";
		List<User> list = super.getHibernateTemplate().find(queryString);
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}
	
	/**
	 * 根据hlPath取得树结构列表
	 * @param pathList
	 * @param setId
	 * @return
	 */
	public List<Dept> findDeptByTreepathList(String parentId){
		StringBuffer queryBuffer = new StringBuffer(" from Dept o where o.isDeleted = 0 and o.parentId = ?") ;
		List<Dept> list = super.getHibernateTemplate().find(queryBuffer.toString(),parentId) ;
		if(list.isEmpty() || list == null){
			return new ArrayList() ;
		}
		return list ;
	}

	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findAllByPage(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from Dept where isDeleted=0 ");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap,valueList);
		return super.findByHql(page, query, valueList);
	}
	
	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from Dept where isDeleted=0 ");
		List valueList = new ArrayList();
		query.append(" order by sortId asc");
		return super.findByHql(page, query, valueList);
	}

	@Override
	public String id2Field(String id, String beanId, String field)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Dept findByDeptName(String deptName){
		String hql = " from Dept where isDeleted=0 and deptName = ? ";
		List<Dept> list = super.getHibernateTemplate().find(hql, deptName);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
