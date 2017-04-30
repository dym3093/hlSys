package org.hpin.base.customerrelationship.dao;

import java.util.List;

import org.hpin.base.customerrelationship.entity.GeneTestInstitution;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 增加修改ErpCustomer页面【检测结构】下拉选项 dao
 * @author LeslieTong
 * @date 2017-4-12下午5:03:50
 */

@Repository
public class GeneTestInstitutionDao extends BaseDao {

	public List<GeneTestInstitution> findGeneTestInstitution(){
		String hql = "from GeneTestInstitution where 1=1 ";
		return this.getHibernateTemplate().find(hql);
	}
	
}
