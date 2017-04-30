package org.hpin.settlementManagement.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.settlementManagement.entity.ErpProceeds;
import org.springframework.stereotype.Repository;


/**
 * 收款Dao
 * @author Henry 20160829
 *
 */
@Repository
public class ErpProceedsDao extends BaseDao {
	
	/**
	 * 保存
	 */
	public void save(ErpProceeds proceeds) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			tx.begin();
			this.getHibernateTemplate().save(proceeds);
			tx.commit();
			
		} catch (Exception e) {
			System.out.println("保存错误异常,ErpProceedsDao.save!");
		} finally {
//			if(session!=null){
//				session.close();
//			}
		}
	}
	
	/**
	 * 修改
	 */
	public void update(ErpProceeds proceeds) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			tx.begin();
			this.getHibernateTemplate().update(proceeds);
			tx.commit();
			
		} catch (Exception e) {
			System.out.println("保存错误异常,ErpProceedsDao.update!");
		} finally {
//			if(session!=null){
//				session.close();
//			}
		}
	}
	
	/**
	 * 根据主键id查询;
	 * @param id
	 * @return
	 */
	public ErpProceeds findById(String id) {
		return this.getHibernateTemplate().get(ErpProceeds.class, id);
	}
	
	/**
	 * 
	 * @param settleId
	 * @return
	 */
	public ErpProceeds findBySettlementId(String settleId) {
		StringBuilder stb = new StringBuilder("from ErpProceeds where settlementId=? and isDelete=0 ");
		List<?> list = this.getHibernateTemplate().find(stb.toString(), settleId);
		if(list != null && list.size() > 0) {
			return (ErpProceeds) list.get(0);
		}
		return null;
	}
}
