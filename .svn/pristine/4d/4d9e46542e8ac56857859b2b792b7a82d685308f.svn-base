package org.hpin.reportdetail.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.reportdetail.entity.ErpComboRelation;
import org.springframework.stereotype.Repository;

@Repository()
@SuppressWarnings("unchecked")
public class ErpComboRelationDao extends BaseDao{

	
	public List<ErpComboRelation> getYmCombo(String syCombo) {
		String sql = "from ErpComboRelation where isDelete='0' and syCombo=? ";
		return this.getHibernateTemplate().find(sql, syCombo);
	}

	public List<ErpCustomer> getCustomerComboInfo(String code, String name) {
		String sql = "from ErpCustomer where isDeleted=0 and upper(code)=? and replace(name,' ','')=? ";
		return this.getHibernateTemplate().find(sql,code.toUpperCase(),name);
	}

	
}
