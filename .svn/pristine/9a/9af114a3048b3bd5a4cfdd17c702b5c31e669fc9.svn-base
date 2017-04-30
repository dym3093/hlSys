package org.hpin.warehouse.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.ErpPreCustomer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * 弘康预导入客户Dao
 * @author tangxing
 * @date 2016-12-29下午3:15:33
 */
@Repository(value="erpHKPrepCustomerDao")
@SuppressWarnings("unchecked")
public class ErpHKPrepCustomerDao extends BaseDao{
	
	public List<ErpPreCustomer> getListPrepCustomer(Page page,Map searchMap){
		StringBuffer query = new StringBuffer("from ErpPreCustomer where 1=1");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
	}
	
	public ErpPreCustomer getErpPreCustomerById(String id){
		return this.getHibernateTemplate().get(ErpPreCustomer.class, id);
	}
	
	public List<?> findJdbcListLocal(String content, RowMapper<?> rowMapper) {
		return this.getJdbcTemplate().query(content.toString(), rowMapper);
	}
	
	public List<?> findJdbcListLocalView(String content, List<Object> lists, RowMapper<?> rowMapper) {
		StringBuilder listSql = new StringBuilder("select *  from( ");
		listSql.append("select ROWNUM RN,");
		listSql.append(content);
		//分页;
		listSql.append(" and rownum <= ? ");
		listSql.append(" ORDER BY preCus.Order_Create_Date DESC ");
		listSql.append(" ) where RN >＝ ? ");
		return this.getJdbcTemplate().query(listSql.toString(), lists.toArray(), rowMapper);
	}

	public List<ErpPreCustomer> getAdressInfo(String customerId) {
		String hql = "from ErpPreCustomer where isDeleted=0 and erpCustomerId=?";
		return this.getHibernateTemplate().find(hql,customerId);
	}
	
	/**
	 * 根据检测公司套餐名，查找基因公司套餐
	 * @param TestComboName
	 * @return
	 * @author tangxing
	 * @date 2017-1-19下午5:26:34
	 */
	public List<Map<String, Object>> getYMComboNameByTestComboName(String TestComboName){
		String sql = "select ytcombo.ym_comboname as ymComboname from  ERP_YMCOMBONAME_TESTCOMBONAME ytcombo where ytcombo.test_comboname=?";
		return this.getJdbcTemplate().queryForList(sql, TestComboName);
	}
	
	/**
	 * 场次对应的批次号算法
	 * @param project
	 * @return
	 * @author tangxing
	 * @date 2017-2-15下午4:13:16
	 */
	public String findBatchNo(String companyPro) {
		String batchNo = "";
		String sql = "select max(to_number(substr(preCus.batch_no, 3, length(preCus.batch_no)))) batchNo from ERP_PRE_CUSTOMER preCus where preCus.batch_no is not null and preCus.is_Deleted = 0 and preCus.batch_No like'%"+companyPro+"%'";
		Map<String, Object> map = this.getJdbcTemplate().queryForMap(sql);
		if(null != map) {
			batchNo = map.get("batchNo") != null ? map.get("batchNo").toString() : "" ;
		}
		
		return batchNo;
	}
	
	public CustomerRelationShipPro findShipProById(String projectId) {
		String sql = "from CustomerRelationShipPro r where id=?";
		return (CustomerRelationShipPro) (this.getHibernateTemplate().find(sql, projectId).get(0));
	}
	
	/**
	 * 获取对应关系表的信息
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getCorrespondingTableInfo(String testComboName){
		String sql = "select yt.ym_comboname as ymComboName,yt.project from erp_ymcomboname_testcomboname yt where yt.test_comboname=?";
		return this.getJdbcTemplate().queryForList(sql, new Object[]{testComboName});
	}
	
	/**
	 * 根据场次号获取ErpPreCustomer
	 * @param eventsNo
	 * @return
	 * @author tangxing
	 * @date 2017-2-15下午5:17:06
	 */
	public String getBatchByEventsNo(String eventsNo){
		String batchNo = "";
		String hql = "from ErpPreCustomer pre where pre.isDeleted=0 and pre.batchNo is not null and pre.eventsNo=?"; 
		List<ErpPreCustomer> customers = this.getHibernateTemplate().find(hql, new Object[]{eventsNo});
		if(customers!=null&&customers.size()>0){
			batchNo = customers.get(0).getBatchNo();
		}
		return batchNo;
	}
	
	/**
	 * 根据code获取ErpPreCustomer
	 * @param code
	 * @return
	 * @author tangxing
	 * @date 2017-2-16上午10:12:29
	 */
	public List<ErpPreCustomer> getPreCustomerByCode(String code){
		String hql = "from ErpPreCustomer pre where  pre.isDeleted=0 and pre.code=?";
		return this.getHibernateTemplate().find(hql, new Object[]{code});
	}
	
	/**
	 * 根据被检人姓名和被检人身份证查找ErpPreCustomer
	 * @param name
	 * @param idno
	 * @return
	 * @author tangxing
	 * @date 2017-2-16上午11:08:55
	 */
	public List<ErpPreCustomer> getPreCustomerByNameAndIdon(String name,String idno){
		String hql = "from ErpPreCustomer pre where pre.isDeleted=0 and pre.wereName=? and pre.wereIdcard=?";
		return this.getHibernateTemplate().find(hql, new Object[]{name,idno});
	}
	
	/**
	 * 根据保险公司套餐获取ym套餐（通过ERP_RELATIONSHIPPRO_COMBO表）
	 * @param checkCombo
	 * @return
	 * @author tangxing
	 * @date 2017-2-17下午2:59:38
	 */
	public List<Map<String, Object>> getYmComboByShipProTable(String checkCombo){
		String sql = "select combo.combo_name as comboName from hl_jy_combo combo where combo.id  =  "
				+"(select shipProCombo.Combo_Id from ERP_RELATIONSHIPPRO_COMBO shipProCombo where shipProCombo.Combo_Show_Name=? and rownum = 1) ";
		return this.getJdbcTemplate().queryForList(sql, new Object[]{checkCombo});
	}
	
	/**
	 * 根据companyId，shipProId获取 批次前缀
	 * @param companyId
	 * @param shipProId
	 * @return
	 * @author tangxing
	 * @date 2017-2-17下午4:34:00
	 */
	public String getBacthPre(String companyId,String shipProId){
		String batchPre = "";
		String sql = "select pro.BATCH_PRE as batchPre from HL_CUSTOMER_RELATIONSHIP_PRO pro where pro.CUSTOMER_RELATIONSHIP_ID = ? and pro.id=? ";
		List<Map<String, Object>> results = this.getJdbcTemplate().queryForList(sql,new Object[]{companyId,shipProId});
		if(results!=null&&results.size()>0){
			batchPre = (String) results.get(0).get("batchPre");
		}
		return batchPre;
	}

	public List<ErpPreCustomer> getPreCustomerByCustomerId(String customerId) {
		String hql = "from ErpPreCustomer where erpCustomerId=?";
		return this.getHibernateTemplate().find(hql,customerId);
	}

	public List<ErpPreCustomer> getErpPreCustomerByCode(String code) {
		String hql = "from ErpPreCustomer where code = ? and isDeleted=0";
		return this.getHibernateTemplate().find(hql,code);
	}
	
	/**
	 * 
	 * <p>Description: 阳光保险数据进入作为验证是否重复导入;</p>
	 * @author herny.xu
	 * @date 2017年2月20日
	 */
	public boolean findIsExcitByConditions(String customerSkuNum,
			String customerName) {
		String sql = "select count(1) from erp_pre_customer where WERE_IDCARD = '"+customerSkuNum+"' and WERE_NAME = '"+customerName+"' and CODE is null ";
		int count = this.getJdbcTemplate().queryForInt(sql);
		if(count > 0) {
			return true;
		}
		return false;
		
	}
	

	public int updateCustomerState(String erpCustomerId) {
		String sql = "update erp_customer set is_deleted=1 where id =?";
		return this.getJdbcTemplate().update(sql,erpCustomerId);
	}
}
