package org.hpin.common.core.orm;

import java.util.List;
import java.util.Map;

import org.hpin.common.core.SpringTool;
import org.hpin.common.widget.pagination.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础Service
 * 
 * @author thinkpad 2010-9-26
 */
@Transactional()
public class BaseService {

	public BaseDao getDao() {
		GenericDao genericDao = (GenericDao) SpringTool
				.getBean(GenericDao.class);
		return genericDao;
	}
	
	/**
	 * 获取实体类类型
	 * 
	 * @return
	 */
	private Class getClazzFromName(String classname) {
		Class clazz = null;
		
		String className = this.getClass().getName().replace("service","entity");	
		className=className.substring(0, className.lastIndexOf("."))+"."+classname;
		System.out.println("className---"+className);
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}
	/**
	 * 获取实体类类型
	 * 
	 * @return
	 */
	private Class getClazz() {
		Class clazz = null;
		
		String className = this.getClass().getName().replace("service","entity");
	
		className=className.substring(0, className.length()-7);
		System.out.println("className---"+className);
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}
	/**
	 * 获取实体类类型
	 * 
	 * @return
	 */
	private Class getClazz2() {
		Class clazz = null;
		
		String className = this.getClass().getName().replace("service",
				"model");
		
		className=className.substring(0, className.length()-7);
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}
	/**
	 * 根据ID获取对象
	 * 
	 * @param id
	 *            对象ID
	 * @return 对象
	 */
	public BaseEntity findById(String id) {
		return getDao().findById(getClazz(), id);
	}
	public BaseEntity findByIdClassName(String id,String className) {
		return getDao().findById(getClazzFromName(className), id);
	}
	/**
	 * 根据ID获取对象
	 * 
	 * @param id
	 *            对象ID
	 * @return 对象
	 */
	public BaseEntity findById(long id) {
		return getDao().findById(getClazz(), new Long(id));
	}
	
	/**
	 * 获取所有的实体
	 * 
	 * @return
	 */
	public List findAll() {
		return getDao().findAll(getClazz(), "id", false);
	}

	/**
	 * 获取所有的实体
	 * 
	 * @return
	 */
	public List findAll(String orderProperty, Boolean isAsc) {
		return getDao().findAll(getClazz(), orderProperty, isAsc);
	}
	
	/**
	 * 保存实体类
	 * 
	 * @param baseEntity
	 */
	public void save(BaseEntity baseEntity) {
		getDao().save(baseEntity);
	}

	/**
	 * 修改实体类
	 * 
	 * @param baseEntity
	 */
	public void update(BaseEntity baseEntity) {
		getDao().update(baseEntity);
	}

	/**
	 * 删除实体类
	 * 
	 * @param ids
	 */
	public void deleteIds(String ids) {
		//包含逗号为数组,modify by dengym 2016-04-22
		if(ids.indexOf(",")>-1){
			String[] idArray = ids.split(",");
			for (int i = 0; i < idArray.length; i++) {
				//getDao().delete(getClazz(), new Long(idArray[i]));
				getDao().delete(getClazz(), idArray[i]);
			}
		}else{
			//不包含逗号为单个
			getDao().delete(getClazz(), ids);
		}
	}

	/**
	 * 分页获取对象集合
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		return getDao().findByPage(getClazz(), page, searchMap);
	}
	
	public List findByPageClassName(Page page, Map searchMap,String className) {
		return getDao().findByPage(getClazzFromName(className), page, searchMap);
	}
	/**
	 * 分页获取对象集合
	 * 业务流程费用审核信息
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage2(Page page, Map searchMap) {
		return getDao().findByPage(getClazz2(), page, searchMap);
	}
	public List findByProperty(Class clazz, String propertyName, Object propertyValue, String orderProperty, Boolean isAsc) {
		return getDao().findByProperty(clazz, propertyName, propertyValue, orderProperty, isAsc);
	}
	public List findByPropertyAndIsDelete(Class clazz, String propertyName, Object propertyValue,Object isDelte, String orderProperty, Boolean isAsc) {
		return getDao().findByPropertyAndIsDelete(clazz, propertyName, propertyValue,isDelte, orderProperty, isAsc);
	}
	
	/**
	 * 清空hibernate数据库连接实例对象
	 * 利用hibernate查询结果直接更改其对象会导致数据库数据同时被更改
	 */
	public void clearDBLink(Object obj){
		getDao().getHibernateTemplate().evict(obj);
	}
}
