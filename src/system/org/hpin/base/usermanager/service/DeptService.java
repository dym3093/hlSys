package org.hpin.base.usermanager.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.usermanager.dao.DeptDao;
import org.hpin.base.usermanager.dao.UserDao;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.constant.UIConstants;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 单位实现类
 * 
 * @author thinkpad
 * @data Oct 8, 2009
 */
@Transactional()
@Service(value = "org.hpin.base.usermanager.service.DeptService")
public class DeptService extends BaseService {
	@Autowired
	private DeptDao deptDao = null;
	
	@Autowired
	private UserDao userDao = null;

	/**
	 * 获取根节点
	 */
	public Dept findRootDept() {
		//return deptDao.findRootDept();
		return null;
	}
	
	/**
	 * 根据部门编号得到部门信息
	 * @param setid 公司setid
	 * @param deptid　部门编号
	 * @return
	 */
	public Dept findDeptByDeptid(String deptid,String setid) {
		//return deptDao.findDeptByDeptid(deptid,setid);
		return null;
	}
	public List<CustomerRelationShip> findBanny(String deptid){
		return deptDao.findBannyByDeptid(deptid);
	}
	/**
	 * 根据部门级别得到部门信息
	 * @param setid 公司setid
	 * @param deptid　部门编号
	 * @param hlDeptGra　部门级别
	 * @return
	 */
	public Dept findDeptByDeptGra(String deptid,String setid,String hlDeptGra) {
		//return DeptDao.findDeptByDeptGra(deptid,setid,hlDeptGra);
		return null;
	}

	/**
	 * 保存部门
	 * 
	 * @param Dept
	 */
	public void save(Dept dept) {
		deptDao.save(dept);
	}

	/**
	 * 人力资源中保存部门
	 * 
	 * @param Dept
	 */
	public void saveDept(Dept dept) {
		deptDao.save(dept);
	}

	public List<User> findAllUser() {
		List<User> userList = deptDao.findAllUser();
		return userList;
	}
	public String getDeptUser(String node) {
		List<Dept> deptlist = new ArrayList<Dept>();
		List<User> userlist = new ArrayList<User>();
		if(node.equals("0")){
			deptlist = deptDao.findDeptByTreepathList("0");
			userlist = deptDao.findAllUser();
			
			
		}else{
			Dept dept = (Dept)deptDao.findById(Dept.class,node);
			deptlist = deptDao.findByParentPath(node);
			
			userlist = deptDao.findUserByDeptId(dept.getId());
		}
		JSONArray json = new JSONArray();  
		if (deptlist.size() > 0){
	    	for (int i = 0; i < deptlist.size(); i++) {
				Dept dept = deptlist.get(i);			
				JSONObject jitem = new JSONObject();
				jitem.put(UIConstants.JSON_ID, dept.getId());
				jitem.put(UIConstants.JSON_NAME, dept.getId());
				jitem.put(UIConstants.JSON_TEXT, dept.getDeptName());
				jitem.put("iconCls", "dept");
				jitem.put("leaf", 0);
				
				json.add(jitem);				
			}
		}
			
		if (userlist.size() > 0) {
			for (int j = 0; j < userlist.size(); j++) {
				User user = userlist.get(j);			
				JSONObject jitem = new JSONObject();
				jitem.put(UIConstants.JSON_ID, user.getAccountName());
				jitem.put(UIConstants.JSON_TEXT, user.getUserName());
				jitem.put("leaf", 1);
				jitem.put("iconCls", "user");
				
				json.add(jitem);
			}
		}
		return json.toString();
	}
	/**
	 * 删除部门
	 * 
	 * @param ids
	 */
	public void deleteIds(String ids) {
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String[] idArray = ids.split(",");
		//Dept parent = null;
		Dept dept = null;
		for (int i = 0; i < idArray.length; i++) {
			dept = (Dept) deptDao.findById(Dept.class, idArray[i]);
			List<Dept> childList = deptDao.findByParentId(dept.getId());
			if (childList != null && childList.size() > 0) {
			   for (Dept parent : childList) {
				 parent.setIsDeleted(1);
				 parent.setDeleteTime(new Date());
				 parent.setDeleteUserId(currentUser.getId());
				 deptDao.update(parent);
			   }
			}
			dept.setIsDeleted(1);
			dept.setDeleteTime(new Date());
			dept.setDeleteUserId(currentUser.getId());
			deptDao.update(dept);
		}
	}

	/**
	 * 根据父ID获取对象
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Dept> findByParentId(String parentId) {
		return deptDao.findByParentId(parentId);
	}
	
	/**
	 * 查找所有dept，为树做准备
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Dept> findAllDept() {
		return deptDao.findAllDept();
	}

	/**
	 * 根据hlTreepath获取对象
	 * @param hlTreepath
	 * @return
	 */
	public Dept findByTreepath(String deptId){
		return deptDao.findDeptByTreepath(deptId);
	}
	
	public List<Dept> findDeptByTreepathList(String parentId){
		return deptDao.findDeptByTreepathList(parentId) ;
	}
	
	/**
	 * 根据hlTreepath和部门级别获取对象
	 * @param hlTreepath
	 * @return
	 */
//	public Dept findDeptByPathDeptGra(String hlTreepath,String hlDeptGra,String setid) {
//		return DeptDao.findDeptByPathDeptGra(hlTreepath, hlDeptGra, setid);
//	}
	/**
	 * 根据父path获取对象
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Dept> findByParentPath(String parentId) {
		return deptDao.findByParentPath(parentId);
	}
	
	/**
	 * 根据部门级别得到所有部门信息
	 * @param setid
	 * @param hlDeptGra
	 * @return
	 */
//	public List<Dept> findAllDeptByDeptGra(String setid,String hlDeptGra) {
//		return DeptDao.findAllDeptByDeptGra(setid, hlDeptGra);
//	} 

	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
//	public List findAllByPage(Page page, Map searchMap) {
//		return DeptDao.findAllByPage(page, searchMap);
//	}
	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
//	public List findByPage(Page page, Map searchMap) {
//		return DeptDao.findByPage(page, searchMap);
//	}

	/**
	 * 根据店名称获取list
	 * @param name
	 * @return
	 */
//	public List<Dept> getDeptListByName(String name){
//		return DeptDao.getDeptListByName(name) ;
//	}
	
	/**
	 * 获取所有的业务组
	 * @author sky
	 * @see BusinessDeptAndGroupTag.java-->"业务部与业务组联动标签"
	 * @return 业务组列表
	 */
	public List<Dept> getAllBusinessGroup(){
		List<Dept> businessGroupList = new ArrayList<Dept>() ;
//		Dept o = findDeptByDeptid(SystemConstant.getSystemConstant("dept_id_of_business_department") , SystemConstant.getSystemConstant("dept_setid_of_beijing")) ; 
//		List<Dept> businessDeptList = findByParentPath(o.getHlTreepath()) ;
//		for(Dept Dept : businessDeptList){
//			List<Dept> DeptList = findByParentPath(Dept.getHlTreepath()) ;
//			businessGroupList.addAll(DeptList) ;
//		}
		return businessGroupList ;
	}

	public List<Dept> findDeptByDeptLevel(int deptLevel,String ukey) {
		StringBuffer hql = new StringBuffer("from Dept where deptLevel= ? and id in("+ukey+") order by id asc");
		List<Dept> list=deptDao.findByHql(hql, deptLevel);
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}
	
	public List<String> findUserId () {
		StringBuffer hql1=new StringBuffer();
		hql1.append("select distinct t.deptId from User t where t.deptId is not null");
		List<String> list1 = deptDao.findByHql(hql1);
		return list1;
	}

	@SuppressWarnings("unchecked")
	public List<User> findUser() {
		
		StringBuffer hql = new StringBuffer();
		hql.append("from User t where t.isDeleted=0  and t.isEnable=1 and t.jobNumber is not null");
		List<User> list=deptDao.findByHql(hql);
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}
		
	/**
	 * 根据hlTreepath获取运营
	 * @author zhangyan
	 * @see SupplierAction.java-->"供应商选择区域"
	 * @param hlTreepath
	 * @param setId
	 * @return
	 */
//	public List<Dept> findDeptOperationsByTreepathList(String hlTreepath , String setId){
//		
//		return DeptDao.findDeptOperationsByTreepathList(hlTreepath , setId) ;
//	}
	/**
	 * 获取运营下面的所有的区域
	 * @author zhangyan
	 * @see SupplierAction.java-->"供应商选择区域"
	 * @param hlTreepath 运营的hlTreepath
	 * @param hlTreepath1 运营失效部门的hlTreepath
	 * @param hlTreepath2 人员待分配中心hlTreepath
	 * @param setId
	 * @return
	 */
//	public List<Dept> findByDeptAreaParentPath(String hlTreePath,String hlTreepath1,String hlTreepath2) {
//		
//		return DeptDao.findByDeptAreaParentPath(hlTreePath , hlTreepath1 , hlTreepath2);
//	}
}
