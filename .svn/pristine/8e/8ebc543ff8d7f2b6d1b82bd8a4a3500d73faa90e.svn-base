package org.hpin.base.priv.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hpin.base.priv.dao.AssignDao;
import org.hpin.base.priv.entity.Assign;
import org.hpin.base.resource.dao.ResourceDao;
import org.hpin.base.resource.entity.Resource;
import org.hpin.bg.system.util.Iuser;
import org.hpin.bg.system.util.LogTool;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : 张艳
 * 
 *
 */
@Service(value="org.hpin.base.priv.service.AssignService")
@Transactional(rollbackFor = Exception.class)
public class AssignService extends BaseService {
	@Autowired()
	private AssignDao assignDao;
	@Autowired
	private ResourceDao resourceDao = null;
	
	private LogTool logTool = null;
	/**
	 * 删除实体类
	 * 
	 * @param ids
	 */
	public void deleteIds(String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			assignDao.delete(new Long(idArray[i]));
		}
	}

	public List findByPage(Page page, Map searchMap) {
		return assignDao.findByPage(page, searchMap);
	}

	public List findRoleResource(Long roleId) {
		return assignDao.findRoleResource(roleId);
	}
    
	//duan update
	public boolean grantResource(String roleId, String grantInfo,Integer objectType) {
		Resource rootResource = resourceDao.findRootResource();
		// 根据角色ID删除之前赋予的角色
		assignDao.deleteGrantResourceByRoleId(roleId);
		//Long resourceId = null;
		String resourceId = null;
		Assign assign = null;
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
				resourceId = grantInfoArray[i].substring(4);
				grantMenuMap.put(resourceId.toString(), resourceId);
				// 添加其相应的父节点
				String parentId = resourceId;
				while (true) {
					parentId = resourceDao.findParentIdByIdForJdbc(parentId);
					if (parentId.compareTo(String.valueOf(rootResource.getId())) != 0) {
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
				assign = new Assign();
				assign.setResourceId(resourceId);
				assign.setRoleId(roleId);
				assign.setIsMenu(0);
				assign.setObjectType(objectType);
				assignDao.save(assign);
			}
		}
		for (Object o : grantMenuMap.keySet()) {
			//resourceId = new Long(grantMenuMap.get(o).toString());
			resourceId = grantMenuMap.get(o).toString();
			assign = new Assign();
			assign.setResourceId(resourceId);
			assign.setRoleId(roleId);
			assign.setIsMenu(1);
			assign.setObjectType(objectType);
			assignDao.save(assign);
			
		}
		
		logTool.saveOperationLog(Iuser.getCurentUser(), "assignAction.grantResource", 1);
		
		return true;
		
	}
	/**
	 * 根据角色名称得用户集合
	 * 
	 * @param roleName
	 * @param orgId
	 * @return
	 */
	public List findUserByPage(Page page, Map searchMap,Long roleId) {
		logTool.saveOperationLog(Iuser.getCurentUser(), "assignAction.grantResource", 1);
		return assignDao.findUserByPage(page, searchMap, roleId);
	}
	/**
	 * 根据机构ID获得用户集合
	 * 
	 * @param roleName
	 * @param orgId
	 * @return
	 */
	public List findOrgUserByPage(Page page, Map searchMap,Long orgId) {
		
		return assignDao.findOrgUserByPage(page, searchMap, orgId);
	}
	
	public List<Resource> findAllEnableByUserId(String userId,String userName){
		
		return assignDao.findAllEnableByUserId(userId,userName);
	}
}
