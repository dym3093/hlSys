package org.hpin.settlementManagement.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.hpin.settlementManagement.entity.ErpPrintComboCost;
import org.hpin.settlementManagement.entity.ErpPrintTaskSettlement;
import org.springframework.stereotype.Repository;
@Repository()
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ErpPrintTaskSettlementDao extends BaseDao {
    
	public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpPrintTaskSettlement where 1=1");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }

	public List<Map<String, Object>> getPrintSettlementTask(Page page,String sql) {
		return getPageList(page, sql);
	}

	/**
	 * @param printTaskNo 打印任务号
	 * @since 2016年12月29日10:44:52
	 * @return 获取打印费用明细
	 */
	public List<ErpPrintTaskSettlement> getPrintSettlementDetail(
			String printTaskNo) {
		String hql = "from ErpPrintTaskSettlement where printtaskno=?";
		return this.getHibernateTemplate().find(hql,printTaskNo);
	}

	public CustomerRelationShip getCompanyInfo(String branchCompanyId) {
		return this.getHibernateTemplate().get(CustomerRelationShip.class, branchCompanyId);
	}
	
	/**
	 * @param printTaskNo
	 * @return 通过打印任务号获取客户信息
	 */
	public List<ErpPrintTaskContent> getPrintTaskContentInfo(String printTaskNo) {
		String hql = "from ErpPrintTaskContent where printTaskNo=?";
		return this.getHibernateTemplate().find(hql,printTaskNo);
	}

	/**
	 * @param printCompanyId
	 * @param combo
	 * @return 通过支公司和套餐去获取套餐价格
	 */
	public List<ErpPrintComboCost> getPrintComboCostInfo(String printCompanyId,
			String combo) {
		String hql = "from ErpPrintComboCost where printCompanyId=? and comboName=? and isDeleted=0";
		return this.getHibernateTemplate().find(hql,printCompanyId,combo);
	}
	/**
	 * @param id
	 * 根据id更新报告结算状态
	 */
	public void updatePrintTaskState(String id,String settlementState) {
		String sql = "update erp_printtask set settlementstate=? where id=?";
		this.getJdbcTemplate().update(sql,settlementState,id);
	}

	public void updatePrintTaskState(String id, String settlementState, String userName) {
		String sql = "update erp_printtask set settlementstate=?,updateusername=?,updatetime=to_date(?,'yyyy-MM-dd hh24:mi:ss') where id=?";
		String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		this.getJdbcTemplate().update(sql,settlementState,userName,date,id);
		
	}

	/**
	 * @param printTaskNo
	 * @return 根据打印任务号获取是否已有结算明细，防止以前的数据再次添加
	 */
	public int getPrintSettlementCount(String printTaskNo) {
		String sql = "select count(1) from erp_print_task_settlement where printtaskno=?";
		return this.getJdbcTemplate().queryForInt(sql,printTaskNo);
	}

	/**
	 * @param printTaskNo 打印任务号
	 * @return 
	 */
	public List<ErpReportdetailPDFContent> getPdfContentInfo(String printTaskNo) {
		String hql = "from ErpReportdetailPDFContent where printtaskno=? ";
		return this.getHibernateTemplate().find(hql,printTaskNo);
	}
}
