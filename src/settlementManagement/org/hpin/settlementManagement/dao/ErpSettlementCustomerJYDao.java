/**
 * @author DengYouming
 * @since 2016-5-3 上午11:31:35
 */
package org.hpin.settlementManagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.settlementManagement.entity.ErpPrintComSettleCustomer;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerJY;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerTemp;
import org.springframework.stereotype.Repository;

/**
 * 基因公司结算人员信息核对表DAO
 * @author DengYouming
 * @since 2016-5-3 上午11:31:35
 */
@Repository()
public class ErpSettlementCustomerJYDao extends BaseDao{
	
	/**
	 * 批量入库
	 * @param customerJY
	 * @author tangxing
	 * @date 2016-8-11上午11:36:17
	 */
	public void saveSettCustomerList(ErpSettlementCustomerJY customerJY,int sum){
		List<ErpSettlementCustomerJY> list = null;
		
		if(sum>0){
			list = new ArrayList<ErpSettlementCustomerJY>();
			list.add(customerJY);
			int size = list.size();
			int a = sum%800;		//余数
			int b = sum/800;		//被800整除的次数
			if(a==0){
				for(int i=0;i<b;++i){
					if(size==800){
						
					}
				}
			}else{
				for(int i=0;i<b+1;++i){
					if(size==800){
						
					}
					if(i==b){
						
					}
				}
			}
			
		}
		
		
	}

	/**
	 * 批量保存
	 * @param list
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-3 下午2:33:30
	 */
	public void saveEntityBatch(List<ErpSettlementCustomerJY> list) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			tx.begin();
			for (int i=0; i<list.size(); i++) {
				session.save(list.get(i));
				//总数大于100，每100条提交一次
				if(list.size()>100){
					if(i!=0&&i%100==0){
						session.flush();
						session.clear();
					}
				}
			}
			session.flush();
			session.clear();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw e;
		}finally{
//			if(null!=session){
//				session.close();
//			}
		}
	}
	
	/**
	 * 批量保存打印公司结算客户信息
	 * @param list
	 * @throws Exception
	 * @author 
	 * @since 2016-5-3 下午2:33:30
	 */
	public void savePrintComSettleCustomer(List<ErpPrintComSettleCustomer> list) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			tx.begin();
			for (int i=0; i<list.size(); i++) {
				session.save(list.get(i));
				//总数大于100，每100条提交一次
				if(list.size()>100){
					if(i!=0&&i%100==0){
						session.flush();
						session.clear();
					}
				}
			}
			session.flush();
			session.clear();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw e;
		}finally{
//			if(null!=session){
//				session.close();
//			}
		}
	}
	
	
	/**
	 * 根据保险公司结算任务ID删除ErpSettlementCustomerJY表的记录
	 * @param settlementtask_id
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-3 下午11:13:57
	 */
	public void deleteErpSettlementCustomerJYBatchBySettleId(String settlementtask_id) throws Exception{
		String sql = "delete from ERP_SETTLE_CUSTOMER_JY where SETTLEMENTTASK_ID = '"+settlementtask_id+"'";
		try {
			super.getJdbcTemplate().execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 计算结算任务套餐数量
	 * @param settlementtask_id
	 * @return Integer
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-4 上午11:47:17
	 */
	public Integer countSetMealNum(String settlementtask_id) throws Exception{
		Integer num = 0;
		String sql = "select count(count(t.items)) from ERP_SETTLE_CUSTOMER_JY t where t.settlementtask_id = '"+settlementtask_id+"' group by t.items";
		num = super.getJdbcTemplate().queryForInt(sql);
		return num;
	}
	
	/**
	 * 异常的数量
	 * @param settlementtask_id
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-6-8下午5:43:41
	 */
	public Integer abnormalNum(String settlementtask_id) throws Exception{
		Integer num = 0;
		String sql = "select count(1) from ERP_SETTLE_CUSTOMER_JY t where t.settlementtask_id = '"+settlementtask_id+"' and t.status in (3,4,5)";
		num = super.getJdbcTemplate().queryForInt(sql);
		return num;
	}
	
	/**
	 * 支公司数量
	 * @param settlementtask_id
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-6-12 15:07:01
	 */
	public Integer branchCompanyNum(String settlementtask_id) throws Exception{
		Integer num = 0;
		String sql = "select count(distinct(t.branch_company)) from ERP_SETTLE_CUSTOMER_JY t where t.settlementtask_id = '"+settlementtask_id+"'";
		num = super.getJdbcTemplate().queryForInt(sql);
		return num;
	}
	
	/**
	 * 根据结算任务ID获取非异常数据(并且报告已出)
	 * @param settleTaskId
	 * @return
	 * @author tangxing
	 * @date 2016-6-14下午12:16:49
	 */
	public List<ErpSettlementCustomerJY> getSettleCustomerBySettIdStatus(String settleTaskId){
		String queryString = "from ErpSettlementCustomerJY where settlementTask_id=? and status not in (2,3,4,5) and pdf_status='0'";
		return this.getHibernateTemplate().find(queryString, new Object[]{settleTaskId});
	}
	
	/**
	 * 分批保存(临时表)
	 * @param list
	 * @author tangxing
	 * @date 2016-8-11下午2:59:15
	 */
	public void saveSettCustomerList(List<ErpSettlementCustomerTemp> list){
		for (ErpSettlementCustomerTemp erpSettlementCustomertemp : list) {
			save(erpSettlementCustomertemp);
		}
	}
	
	/**
	 * 分批保存
	 * @param list
	 * @author tangxing
	 * @date 2016年8月12日17:02:36
	 */
	public void saveSettCustomerListAll(List<ErpSettlementCustomerJY> list){
		for (ErpSettlementCustomerJY erpSettlementCustomerJY: list) {
			save(erpSettlementCustomerJY);
		}
	}
	
	public List<ErpSettlementCustomerJY> getErpSettlementCustomerJYByCode(String code){
		String queryString = "from ErpSettlementCustomerJY where code=? and status = '2'";
		return this.getHibernateTemplate().find(queryString, new Object[]{code});
	} 

}
