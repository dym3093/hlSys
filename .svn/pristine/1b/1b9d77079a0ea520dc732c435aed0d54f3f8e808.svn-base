package org.hpin.warehouse.dao;

import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.warehouse.entity.ErpBxCompanyPreSet;
import org.springframework.stereotype.Repository;

@Repository
public class ErpBxCompanyPreSetDao extends BaseDao {
	
	/**
	 * 保存保险公司预制客户;
	 * create by henry.xu 2016年12月16日
	 */
	public void saveoBxCustomer(ErpBxCompanyPreSet preSet) throws Exception {
		this.getHibernateTemplate().save(preSet);
	}
	
	/**
	 * 根据申请单号查询是否已存在;
	 * create by henry.xu 2016年12月16日
	 * @param applicationNo
	 * @return true标示已存在, false标示不存在;
	 */
	public boolean findByApplicationNo(String applicationNo) {
		String sql = "select count(1) from ERP_BXCOMPANY_PRESET where application_no = '" + applicationNo + "'";
		int count = this.getJdbcTemplate().queryForInt(sql);
		if(count > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据申请单号和客户唯一标识号做验证是否有已存在的数据;
	 * create by henry.xu 2016年12月16日
	 * @return
	 */
	public boolean findCustomerSkuNumIsExits(String applicationNo, String customerSkuNum, String comboName, String customerName) {
		String sql = "select count(1) from ERP_BXCOMPANY_PRESET where application_no='"+applicationNo+"' and CUSTOMER_SKU_NUM='"+customerSkuNum+"' and COMBO_NAME = '"+comboName+"' and CUSTOMER_NAME = '"+customerName+"'";
		int count = this.getJdbcTemplate().queryForInt(sql);
		if(count > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据申请单号删除已存在数据;
	 * create by henry.xu 2016年12月16日
	 * @param applicationNo
	 * @throws Exception
	 */
	public void deleteByApplicationNO(String applicationNo) throws Exception {
		String sql = "delete from ERP_BXCOMPANY_PRESET where application_no = '" + applicationNo + "'";
		this.getJdbcTemplate().update(sql);
	}

	public String findEventsNoByApplicationNo(String applicationNo) {
		String sql = "select EVENTS_NO eventsNo from ERP_BXCOMPANY_PRESET where application_no = '" + applicationNo + "' group by EVENTS_NO ";
		Map<String, Object> map = this.getJdbcTemplate().queryForMap(sql);
		if(null != map && !map.isEmpty()) {
			return (String)map.get("eventsNo");
		}
		return "";
	}
	
}
