package org.hpin.base.customerrelationship.service;

import java.util.List;
import java.util.Map;

import org.hpin.base.customerrelationship.dao.CustomerRelationshipComboDao;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipDao;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.CustomerRelationshipCombo;
import org.hpin.common.core.orm.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ymjy.combo.dao.ComboDao;
import org.ymjy.combo.entity.Combo;
import org.ymjy.combo.service.ComboService;

@Service(value="org.hpin.base.customerrelationship.service.CustomerRelationshipComboService")
public class CustomerRelationshipComboService extends BaseService {
	
	@Autowired
	private CustomerRelationshipComboDao groupRelationDao;
	@Autowired
	private ComboDao serviceElementDao;
	
	public List<CustomerRelationshipCombo> findByGroupId(String groupId) {
		StringBuffer hql = new StringBuffer("from CustomerRelationshipCombo where customerId = ? order by id");
		return groupRelationDao.findByHql(hql, groupId);
	}
	public List<CustomerRelationshipCombo> findListByParentId(String groupId,String parentId) {
		StringBuffer hql = new StringBuffer("from CustomerRelationshipCombo where customerId = ? and comboId=? order by id");
		return groupRelationDao.findByHql(hql, new Object[]{groupId,parentId});
	}
	public void addServiceRelationS(String groupId, String resIds) {
		String[] arrayIds = resIds.split(",");
		for(String id: arrayIds){
			Combo element = serviceElementDao.findByElementsId(id);
			CustomerRelationshipCombo relation = new CustomerRelationshipCombo();
			relation.setCustomerRelationShipId(groupId);
			relation.setComboId(id);
			groupRelationDao.saveEntity(relation);
		}
		
	}

	public Integer delRelation(String groupId, String id) {
		List<CustomerRelationshipCombo> relationList = groupRelationDao.findByGroupIdAndElementId(groupId,id);
		if(null!=relationList&&relationList.size()>0){
			CustomerRelationshipCombo relation = relationList.get(0);
				/*List<ServiceElement> elementList = serviceElementDao.findListByParentId(id);*/
				
				if(relationList != null && relationList.size() > 0){
					StringBuffer sb = new StringBuffer();
					sb.append("'" + id + "','");
					for(int i = 0 ; i < relationList.size() ; i ++){
						CustomerRelationshipCombo element2 = relationList.get(i);
						String id1 = element2.getId();
						if(i == relationList.size() - 1){
							sb.append(id1 + "'");
						}else {
							sb.append(id1 + "','");
						}
					}
					String ids = sb.toString();
					
					if(ids != null || !"".equals(ids))
						return groupRelationDao.delRelation(groupId , ids);
				}else {
					return groupRelationDao.delDanRelation(groupId, id);
				}
		}
		return 0;
	}

	/**
	 * 根据支公司ID和套餐ID到中间表（erp_relationshippro_combo）获取isCreatePdf status
	 * @param branchCompanyId
	 * @param comboId
	 * @return
	 * @author tangxing
	 * @date 2016年12月6日14:47:44
	 */
	public String getIsCreatePdfStatus(String branchCompanyId, String comboId) throws Exception{
		List<Map<String, Object>> listMap =  groupRelationDao.getIsCreatePdfStatus(branchCompanyId, comboId); 
		String isCreatePdf = "";
		if(listMap!=null&&listMap.size()>0){
			isCreatePdf = (String) listMap.get(0).get("isCreatePdf");
		}
		return isCreatePdf;
	}
	
}

