package org.hpin.base.customerrelationship.dao;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.ErpRelationShipCombo;
import org.hpin.base.dict.dao.ID2NameDAO;
import org.hpin.base.dict.exceptions.DictDAOException;
import org.hpin.base.region.entity.Region;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.settlementManagement.entity.ErpCompanyComboPrice;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Repository(value="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao")
public class CustomerRelationshipDao extends BaseDao implements ID2NameDAO{

	
	/**
	 * 根据支公司名字查找CustomerRelationShip
	 * @param branchCommany
	 * @return
	 * @author tangxing
	 * @date 2016-9-1下午2:18:00
	 */
	public List<CustomerRelationShip> getCustomerRelationShipBybranch(String branchCommany){
		String query = " from CustomerRelationShip where branchCommany=? and proUser is not null and proBelong is not null and proCode is not null";
		return this.getHibernateTemplate().find(query, new Object[]{branchCommany});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String id2Name(String id) throws DictDAOException {
		String queryString = " from CustomerRelationShip customer where customer.id=?";
		List<CustomerRelationShip> list = this.getHibernateTemplate().find(queryString,id);
		String customerName = "";
		if( list != null && list.size() >0){
			CustomerRelationShip customerRelationShip = list.get(0);
			customerName = customerRelationShip.getBranchCommany();
		}
		return customerName;
	}

	@Override
	public String id2Field(String id, String beanId, String field) throws DictDAOException {
		

		String result="";
		CustomerRelationShip customer=  this.getHibernateTemplate().get(CustomerRelationShip.class, id);
		if("province".equals(field)) {
			String hql = "from Region t where t.id=?";
			List<Region> regin = this.getHibernateTemplate().find(hql, customer.getProvince());
			result  = regin.get(0).getRegionName();
		}else if("city".equals(field)) {
			String hql = "from Region t where t.id=?";
			List<Region> region = this.getHibernateTemplate().find(hql, customer.getCity());
			result  = region.get(0).getRegionName();
		}
			return result;
    }
	
	/**
	 * 根据公司ID查找套餐
	 * @param companyName 公司名称
	 * @return String
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-5 下午2:49:28
	 */
	public String findComboByCompanyId(String companyId) throws Exception{
		String combo = null;
		String hql = "from CustomerRelationShip customer where customer.id=?";
		CustomerRelationShip entity = (CustomerRelationShip)this.getHibernateTemplate().find(hql, companyId).get(0);
		combo = entity.getCombo();
		return combo;
	}
	
	/**
	 * 根据公司ID查找对象
	 */
	public CustomerRelationShip findShipByCompanyId(String companyId) throws Exception{
		String hql = "from CustomerRelationShip customer where customer.id=?";
		List<CustomerRelationShip> list = this.getHibernateTemplate().find(hql, companyId);
		if(null!=list&&0<list.size()){
			CustomerRelationShip entity = list.get(0);
			return entity;
		}else{
			return null;
		}
	}

	/**
	 * @param companyName
	 * @param companyId
	 * @return 获取的该支公司所有套餐
	 * @throws Exception
	 */
	public List getCompanyPackagePrice1(String companyId) throws Exception{

		String getStringComboSql="select a.combo from hl_customer_relationship a where a.id='"+companyId+"'";
		return this.getJdbcTemplate().queryForList(getStringComboSql);		
	}
	
	/**
	 * @param companyName
	 * @param companyId
	 * @return 获取的该支公司所有套餐价格
	 * @throws Exception
	 */
	public List getCompanyPackagePrice2(String companyId) throws Exception{
		String getName_PriceSql="select a.combo,a.combo_price from erp_company_combo_price a where a.company_id='"+companyId+"'";
		return this.getJdbcTemplate().queryForList(getName_PriceSql);	
	}
	
	/**
	 * 根据时间段查找CustomerRelationShip
	 * @param startTime
	 * @param endDate
	 * @return
	 * @author tangxing
	 * @date 2016-8-31下午2:36:06
	 */
	public List<CustomerRelationShip> getCustomerRelationShipByDate(Date startTime,Date endDate){
		String query = "from CustomerRelationShip where createTime>=? and createTime<?";
		return this.getHibernateTemplate().find(query, new Object[]{startTime,endDate});
	}

	/**
	 * 根据支公司和项目编码获取对应的项目ID;
	 * create by henry.xu 2016年12月29日
	 * @param bannyCompanyId
	 * @param projectCode
	 * @return
	 */
	public String findCustomerRelationshipProId(String bannyCompanyId, String projectCode) {
		String sql = "select id from HL_Customer_relationship_pro where PROJECT_CODE='"+projectCode+"' and CUSTOMER_RELATIONSHIP_ID='"+bannyCompanyId+"'";
		String id = "";
		try {
			Map<String, Object> mapObj = this.getJdbcTemplate().queryForMap(sql);
			if(mapObj != null && !mapObj.isEmpty()) {
				id = (String)mapObj.get("id");
			}
		} catch (Exception e) {
			id = "";
		}
		
		return id;
	}
	
	/**
	 * 根据支公司和项目code查询对应的项目批次前缀;
	 * @author herny.xu
	 * @date 2017年2月21日
	 */
	public String findBacthPreByCondition(String bannyCompanyId, String projectCode) {
		String sqlQuery = "select BATCH_PRE barchPre from HL_CUSTOMER_RELATIONSHIP_PRO shipPro where shipPro.CUSTOMER_RELATIONSHIP_ID = '"+bannyCompanyId+"' and shipPro.PROJECT_CODE = '"+projectCode+"'";
		//存在异常; 但是由于业务需要,不会出现不存在的情况.如果出现了,说明业务出问题了
		Map<String, Object> objMap = this.getJdbcTemplate().queryForMap(sqlQuery);
		String bacthPre = (String)objMap.get("barchPre");
		return bacthPre;
	}
	
	public CustomerRelationShip getCustomerRelationShipById(String id){
		return this.getHibernateTemplate().get(CustomerRelationShip.class, id);
	}

	public CustomerRelationShip findCustomerRelationShipByName(String companyName,String ownCompanyName){
		String hql = "from CustomerRelationShip where branchCommany=? and customerNameSimple=?";
		List<CustomerRelationShip> list = this.getHibernateTemplate().find(hql,companyName,ownCompanyName);
		if(null!=list&&0<list.size()){
			CustomerRelationShip entity = list.get(0);
			return entity;
		}else{
			return null;
		}
	}

	/**
	 * @param crid
	 * @param projectCode
	 * @return
	 * @author machuan
	 * @date  2017年2月21日
	 */
	public List<Map<String, Object>> checkShowCombo(String crid, String projectCode) {
		String sql = "SELECT c.combo_show_name,c.is_used from erp_relationshippro_combo c "
				    +" left join hl_customer_relationship_pro p on c.customer_relationship_pro_id=p.id "
				    +" where p.is_deleted='0' and p.customer_relationship_id=? and p.project_code=?";
		return this.getJdbcTemplate().queryForList(sql,crid,projectCode);
	}
}