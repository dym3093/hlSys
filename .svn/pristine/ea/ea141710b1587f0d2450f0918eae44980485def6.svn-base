package org.hpin.base.priv.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hpin.base.priv.dao.AssignPrivDao;
import org.hpin.base.priv.entity.ResourceOwner;
import org.hpin.base.resource.dao.ResourceDao;
import org.hpin.base.resource.entity.Resource;
import org.hpin.common.core.orm.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : wangzhiyong
 * 
 *
 */
@Service(value="org.hpin.base.priv.service.AssignPrivService")
@Transactional(rollbackFor = Exception.class)
public class AssignPrivService extends BaseService {
	@Autowired
	private AssignPrivDao assignPrivDao = null;
	@Autowired
	private ResourceDao resourceDao = null;
	/**
	 * 添加权限与拥有者关系
	 * @author duan
	 * @param ids
	 */
	public void addAssignByIds(String ownerId, String ids, Integer assignType, String createUserId) {
		String[] arrayIds = ids.split(",");
		Date date = new Date();
		for(String id: arrayIds){
			ResourceOwner resourceWoner = new ResourceOwner();
			resourceWoner.setAssignType(assignType);
			resourceWoner.setCreateTime(date);
			resourceWoner.setCreateUserId(createUserId);
			resourceWoner.setOwnerId(ownerId);
			resourceWoner.setResourceId(id);
			resourceWoner.setIsDeleted(0);
			assignPrivDao.save(resourceWoner);
		}
	}
	
	
	/**
	 * 删除权限与拥有者关系
	 * duan update
	 * @param ownerId 拥有者id
	 * @param assignType 拥有者类型
	 * @param id 权限id
	 * @return
	 */
	public Integer deleteAssignPriv(String ownerId, Integer assignType, String id) {
		StringBuffer sb = new StringBuffer();
		sb.append(id).append(",");
		getAllChildIds(sb, id);
		String ids = sb.substring(0, sb.length()-1);
		return assignPrivDao.deleteAssignByIds(ownerId, assignType, ids);
	}
	
	/**
	 * 根据parentId获取用户的权限资源
	 */
//	public List<Resource> findEnableMenuByUserIdAndParentId(Long userId, Long parentId) {
//		return assignPrivDao.findEnableMenuByUserIdAndParentId(userId, parentId);
//	}
	public List<Resource> findEnableMenuByUserIdAndParentId(String userId, String parentId) {
		return assignPrivDao.findEnableMenuByUserIdAndParentId(userId, parentId);
	}
	/**
	 * 根据父ID获取可用的资源
	 */
	public List<Resource> findEnableMenuByParentId(String ownerId, Integer assignType, String parentId) {
		return assignPrivDao.findEnableMenuByParentId(ownerId, assignType, parentId);
	}

	/**
	 * 递归查询某个菜单的所有子菜单
	 * @param sb 用来保存所有子菜单的id
	 * @param id 菜单权限id
	 * duan update
	 */
	private void getAllChildIds(StringBuffer sb,String id){
		List<Resource> reses = resourceDao.findByParentId(id);
		if(CollectionUtils.isEmpty(reses)){
			return;
		}
		for(Resource r: reses){
			String tmpId = String.valueOf(r.getId());
			sb.append(tmpId).append(",");
			getAllChildIds(sb,tmpId);
		}
	}
	
	/**
	 * 根据用户id和父ID获取从部门和角色中获取的菜单
	 * @author duan   update
	 * @param userId 用户id
	 * @param parentId 父级菜单id
	 * @return
	 */
	public List<Resource> findUserResExtendsDeptAndRoles(String userId, String parentId){
		return assignPrivDao.findUserResExtendsDeptAndRoles(userId, parentId);
	}
	
}
