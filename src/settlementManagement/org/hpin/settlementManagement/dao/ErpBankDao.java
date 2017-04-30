package org.hpin.settlementManagement.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.settlementManagement.entity.ErpBank;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpBankDao extends BaseDao{
	
	/**
	 * 查询所有没有删除的银行信心集合;
	 * create by henry.xu 20160826
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ErpBank> findAllErpBanks() {
        String queryString = "from ErpBank where isDelete=?";
        return getHibernateTemplate().find(queryString, new Object[]{"0"});
	}
}
