package org.hpin.reportdetail.dao;

import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.reportdetail.entity.ErpExpressInfo;
import org.hpin.reportdetail.entity.ErpExpressInfoTemp;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpExpressInfoDao extends BaseDao {

	/**
	 * 获取所有未收货的
	 * @return
	 * @author tangxing
	 * @date 2016-8-24下午12:28:45
	 */
	public List<ErpExpressInfoTemp> getExpressInfoTempList(){
		String hql = "from ErpExpressInfoTemp where status<>'6'";
		return this.getHibernateTemplate().find(hql);
	}
	
	public List<ErpExpressInfoTemp> getExpressInfoTempByNoCompany(String expressNo,String expressCompanyCode){
		String hql = "from ErpExpressInfoTemp where status<>'6' and expressNo=? and expressCompanyCode=?";
		return this.getHibernateTemplate().find(hql, new Object[]{expressNo,expressCompanyCode});
	}
	
	public List<ErpExpressInfo> getExpressInfoByNoCompany(String expressNo,String expressCompanyCode){
		String hql = "from ErpExpressInfo where status<>'6' and expressNo=? and expressCompanyCode=?";
		return this.getHibernateTemplate().find(hql, new Object[]{expressNo,expressCompanyCode});
	}
	
	public Map<String, Object> getExpressPY(String expressName){
		String sql = "select t.COMPANY_CODE from erp_express_company t where t.COMPANY_NAME='"+expressName+"'";
		Map<String, Object> maps = super.getJdbcTemplate().queryForMap(sql);
		return maps;
	}
	
	public List<ErpCustomer> getCustomerByName(String name){
		String hql = "from ErpCustomer where name=?";
		return this.getHibernateTemplate().find(hql, new Object[]{name});
	}
	
}
