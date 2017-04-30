package org.hpin.base.resource.service;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.hpin.base.resource.dao.ResourceDao;
import org.hpin.base.resource.entity.Resource;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.XmlDom4j;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 资源Service实现类
 * 
 * @author thinkpad
 * @data May 10, 2009
 */
@Service(value = "org.hpin.base.resource.service.ResourceService")
@Transactional()
public class ResourceService extends BaseService {
	@Autowired
	private ResourceDao resourceDao = null;

	/**
	 * 获取菜单项
	 * @return
	 */
	public List<Resource> findMenuByType() {
		return resourceDao.findMenuByType();
	}
	/**
	 * 分页查询模块
	 * @param page
	 * @param paramsMap
	 * @return
	 */
	public List<Resource> findByModulePage(Page page , Map paramsMap){
		
		return resourceDao.findByModulePage(page, paramsMap) ;
	}
	/**
	 * 获取所有可用的系统资源
	 */
	public List<Resource> findAllEnable() {
		return resourceDao.findAllEnable();
	}

	/**
	 * 获取可以访问的模块级别的资源
	 */
	public List<Resource> findAllEnableModuleResource() {
		return resourceDao.findAllEnableModuleResource();
	}

	/**
	 * 修改资源
	 */
	public void update(Resource resource) {
		resourceDao.update(resource);
	}

	/**
	 * 根据父ID获取可用的资源
	 * duan update
	 */
	public List<Resource> findEnableMenuByParentId(String parentId) {
		return resourceDao.findEnableMenuByParentId(parentId);
	}
	
	/**
	 * 根据父ID获取可用的资源(包含按钮)
	 */
//	public List<Resource> findEnableMenuAndButtonByParentId(Long parentId) {
//		return resourceDao.findEnableMenuAndButtonByParentId(parentId) ;
//	}
	public List<Resource> findEnableMenuAndButtonByParentId(String parentId) {
		return resourceDao.findEnableMenuAndButtonByParentId(parentId) ;
	}
	
	/**
	 * 获取菜单的叶子节点下的按钮
	 * duan update
	 * @param 父节点id，即菜单的叶子节点id
	 * @return
	 */
	public List<Resource> findEnableButtonByParentId(String parentId){
		return resourceDao.findEnableButtonByParentId(parentId) ;
	}
	
	/**
	 * 修改资源状态
	 */
//	public boolean updateEnable(Long menuId) {
//		Resource resource = (Resource) resourceDao.findById(Resource.class,
//				menuId);
//		if (resource.isEnable == 0) {
//			resource.setIsEnable(1);
//			return true;
//		} else {
//			resource.setIsEnable(0);
//			return false;
//		}
//	}
	public boolean updateEnable(String menuId) {
		Resource resource = (Resource) resourceDao.findById(Resource.class,
				menuId);
		if (resource.isEnable == 0) {
			resource.setIsEnable(1);
			return true;
		} else {
			resource.setIsEnable(0);
			return false;
		}
	}
	
	private void initResourceForRecursion(Element element, String parentId,
			Integer orderCode) {
		List<Element> childList = element.selectNodes("resource");
		Resource resource = new Resource();
		resource.setName(element.attributeValue("name"));
		if (null != element.attributeValue("isEnable")) {
			resource
					.setIsEnable(new Integer(element.attributeValue("isEnable")));
		} else {
			resource.setIsEnable(1);
		}
		resource.setOrderCode(orderCode);
		if (null != element.attributeValue("type")) {
			resource.setType(new Integer(element.attributeValue("type")));
		}
		if ((resource.getType() == 3 || resource.getType() == 4)
				|| (childList.size() == 0)) {
			resource.setIconCls("leafMenuIcon");
			resource.setIsLeaf(1);
		} else {
			resource.setIsLeaf(0);
		}
		if (null != element.attributeValue("url")) {
			resource.setUrl(element.attributeValue("url"));
		}
		if (null != element.attributeValue("iconCls")) {
			resource.setIconCls(element.attributeValue("iconCls"));
		}
		resource.setParentId(parentId);
		resourceDao.saveForJdbc(resource);
		for (int i = 1; i <= childList.size(); i++) {
			element = childList.get(i - 1);
			initResourceForRecursion(element, resource.getId(), i);
		}
	}

	/**
	 * 初始化系统菜单资源
	 */
	public synchronized void initResource() {
		resourceDao.deleteAllResource();
		Document document = XmlDom4j.loadFile("initData/resource.xml");
		Element rootElement = XmlDom4j.getRootElement(document);
		initResourceForRecursion(rootElement, null, 1);
	}

	/**
	 * 获取根节点
	 * 
	 * @return
	 */
	public Resource findRootResource() {
		return resourceDao.findRootResource();
	}

	/**
	 * 根据父ID获取集合
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Resource> findByParentId(String parentId) {
		return resourceDao.findByParentId(parentId);
	}

	public List<Resource> findEnableOperation() {
		return resourceDao.findEnableOperation();
	}
	public List<Resource> findByparentId(Page page , Map paramsMap,String parentId){
		
		return resourceDao.findByparentId(page, paramsMap, parentId) ;
	}
	
	public List<Resource> findBaseResource(){
		return resourceDao.findBaseResources() ;
	}
}
