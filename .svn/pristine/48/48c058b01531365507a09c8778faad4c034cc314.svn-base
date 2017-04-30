package org.hpin.events.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.SampleExpCustomer;
import org.springframework.stereotype.Repository;

/**
 * 样本快递费管理Dao层
 * @author ybc
 * @since 2016/12/14
 */
@Repository
public class SampleExpressMgrDao extends BaseDao {

	//删除客户
	public int delSamExpCus(String expCusid){
		String sql = "update erp_sample_express_customer set ISDELETED=1 where id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{expCusid});
	}
	
	//连表查询样本快递费管理信息
	public StringBuilder findSamExpMgrSql(){
		StringBuilder jdbcsql = new StringBuilder("select distinct event.batchno as eventBatchno,mgr.id as id, " +
				"mgr.express_num as expressNum,mgr.total_cost as totalCost,mgr.receive_send_type as receiveSendType," +
				"mgr.express_company_id as expressCompanyId,mgr.receive_send_date as receiveSendDate," +
				"mgr.express_content as expressContent ,mgr.isbill as isbill,event.events_no as eventsNo," +
				"event.event_date as eventDate,event.branch_company_id as branchCompanyId," +
				"event.owned_company_id as ownedCompanyId,mgr.CREATE_DATE," +
				"(select count(1) from erp_customer r where r.events_no=event.events_no and r.is_deleted=0) as hasInHead," +
				"(select count(1) from erp_sample_express_customer c where c.sample_express_id=mgr.id and c.isdeleted=0 and c.events_id=event.id) as expHead " +
				"from erp_sample_express_mgr mgr,erp_sample_express_customer expcus,erp_events event " +
				"where mgr.id = expcus.sample_express_id " +
				"and expcus.events_id = event.id " +
				"and mgr.ISDELETED=0 " +
				"and event.IS_DELETED=0 ");
		return jdbcsql;
	}
	
	//连表查询场次信息
	public StringBuilder findEventSql(){
		StringBuilder jdbcsql = new StringBuilder("select distinct eve.id as eveid,eve.batchno as eventsBatchno,eve.events_no as eventsNo," +
				"eve.branch_company_id as branchCompanyId,eve.owned_company_id as ownedCompanyId,eve.event_date as eventsDate," +
				"(select count(1) from erp_customer r where r.events_no=eve.events_no and r.is_deleted=0) as hasInHead " +
				"from erp_events eve,erp_customer mer " +
				"where eve.events_no=mer.events_no " +
				"and eve.IS_DELETED=0 ");
		return jdbcsql;
	}
	
	@SuppressWarnings("unchecked")
	public List<SampleExpCustomer> getSamExpCustomerByExpId(String sampleExpMgrId){
		String hql = "from SampleExpCustomer where sampleExpMgrId=? and isdeleted=0";
		return this.getHibernateTemplate().find(hql, sampleExpMgrId);
	}
}
