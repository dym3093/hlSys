/**
 * dym
 * 2016-4-26 下午2:36:30
 */
package org.hpin.settlementManagement.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpPrintBatch;
import org.hpin.settlementManagement.entity.ErpJYSettleTaskDetail;
import org.hpin.settlementManagement.entity.ErpSettleExcetaskJY;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerJY;
import org.hpin.settlementManagement.entity.ErpSettlementTaskJY;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;


/**
 * 基因公司结算任务DAO
 * @author dym
 * 2016-4-26 下午2:36:30
 */
@Repository()
public class ErpSettlementTaskJYDao extends BaseDao{

	public Session getCurrentSession() throws Exception{
		Session session = null;
		if(session!=null){
			getHibernateTemplate().getSessionFactory().getCurrentSession();
		}
		return session;
	}
	
	public List<ErpSettlementTaskJY> getSettleTaskAll(){
		String query = "from ErpSettlementTaskJY where 1=1 order by createTime";
		return this.getHibernateTemplate().find(query);
	}
	
	/**
	 * 基因结算任务（运营权限）查看所有状态
	 * @param page
	 * @param searchMap
	 * @return
	 * @author tangxing
	 * @date 2016-9-13下午8:57:20
	 */
	public List listSettleJyTask(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer("from ErpSettlementTaskJY where status in ('0','1','2','3','4','9')");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
	}
	
	/**
	 * 基因结算任务（财务权限）待付款，部分付款，已付款
	 * @param page
	 * @param searchMap
	 * @return
	 * @author tangxing
	 * @date 2016-9-13下午8:58:25
	 */
	public List listSettleJyTaskTwo(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer("from ErpSettlementTaskJY where status in ('4')");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
	}
	
	public List listSettleJyTaskTwoAll(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer("from ErpSettlementTaskJY where status in ('3','4','9')");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
	}
	
	/**
	 * 所有异常结算任务
	 * @return
	 * @author tangxing
	 * @date 2016-6-6下午7:52:49
	 */
	public List listSettleExceTask(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer("from ErpSettleExcetaskJY where 1=1");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
	
	public List<ErpSettleExcetaskJY> getExceSettleTaskBySettleTaskNo(String taskNo,Page page,Map searchMap){
		/*String query = "from ErpSettleExcetaskJY where taskNo=?";
		return getHibernateTemplate().find(query, new Object[]{taskNo});*/
		
		StringBuffer query = new StringBuffer("from ErpSettleExcetaskJY where taskNo='"+taskNo+"'");
        //searchMap.put("order_create_time", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
	}
	
	
	public List<ErpSettlementCustomerJY> getSettleCusByExceId(String exceTaskId,String status,Page page,Map searchMap){
		/*String query = "from ErpSettlementCustomerJY where exceSettle_id=? and status=?";
		return getHibernateTemplate().find(query, new Object[]{exceTaskId,status});*/
		
		if(page==null&&searchMap==null){
			String query = "from ErpSettlementCustomerJY where exceSettle_id=? and status=?";
			return getHibernateTemplate().find(query, new Object[]{exceTaskId,status});
		}else{
			StringBuffer query = new StringBuffer("from ErpSettlementCustomerJY where exceSettle_id='"+exceTaskId+"' and status='"+status+"'");
	        //searchMap.put("order_create_time", "desc");
	        List valueList = new ArrayList();
	        OrmConverter.assemblyQuery(query, searchMap, valueList);
	        return super.findByHql(page, query, valueList);
		}
		
	}
	
	public List<ErpSettlementCustomerJY> getSettleCusByExceIdAnd(String exceTaskId,String pdfStatus,Page page,Map searchMap){
		/*String query = "from ErpSettlementCustomerJY where exceSettle_id=? and pdf_Status=?";
		return getHibernateTemplate().find(query, new Object[]{exceTaskId,pdfStatus});*/
		
		if(page==null&&searchMap==null){
			String query = "from ErpSettlementCustomerJY where exceSettle_id=? and pdf_Status=?";
			return getHibernateTemplate().find(query, new Object[]{exceTaskId,pdfStatus});
		}else{
			StringBuffer query = new StringBuffer("from ErpSettlementCustomerJY where exceSettle_id='"+exceTaskId+"' and pdf_Status='"+pdfStatus+"'");
	        //searchMap.put("order_create_time", "desc");
	        List valueList = new ArrayList();
	        OrmConverter.assemblyQuery(query, searchMap, valueList);
	        return super.findByHql(page, query, valueList);
		}
		
	}
	
	public List<ErpSettlementCustomerJY> getExceSettleCusAll(String exceTaskId,Page page,Map searchMap){
		/*String query = "from ErpSettlementCustomerJY where exceSettle_id=? and pdf_Status='1' or status in ('2','3')";
		return getHibernateTemplate().find(query, new Object[]{exceTaskId});*/
		
		if(page==null&&searchMap==null){
			String query = "from ErpSettlementCustomerJY where exceSettle_id=? and (pdf_Status='1' or status in ('2','3','4','5','6'))";
			return getHibernateTemplate().find(query, new Object[]{exceTaskId});
		}else{
			StringBuffer query = new StringBuffer("from ErpSettlementCustomerJY where exceSettle_id='"+exceTaskId+"' and (pdf_Status='1' or status in ('2','3','4','5','6'))");
	        //searchMap.put("order_create_time", "desc");
	        List valueList = new ArrayList();
	        OrmConverter.assemblyQuery(query, searchMap, valueList);
	        return super.findByHql(page, query, valueList);
		}
	}
	
	/**
	 * 查找6状态的结算customer
	 * @return
	 * @author tangxing
	 * @date 2016-11-10上午11:08:13
	 */
	public List<ErpSettlementCustomerJY> getSettlementCustomerByStatus(String settlementId,String status){
		String query = "from ErpSettlementCustomerJY where settlementTask_id = ? and status = ?";
		return getHibernateTemplate().find(query, new Object[]{settlementId,status});
	}
	
	/**
	 * 查找0、1、2状态的结算customer，并不拿异常的已结算数据
	 * @param settlementId
	 * @return
	 * @author tangxing
	 * @date 2016-11-11下午6:01:47
	 */
	public List<ErpSettlementCustomerJY> getSettlementCustomerByStatus(String settlementId){
		String query = "from ErpSettlementCustomerJY where settlementTask_id = ? and status in ('0','1','2') and exceSettle_id is null ";
		return getHibernateTemplate().find(query, new Object[]{settlementId});
	}
	
	/**
	 * 确认结算任务
	 * @param params
	 * @return boolean
	 * 2016-4-26 下午2:40:31
	 */	
	public boolean confirmErpSettlementTaskJY(Map<String, Object> params) throws Exception{
		boolean flag = false;
		// TODO Auto-generated method stub
		return flag;
	}

	/**
	 * 批量删除结算任务
	 * @param params
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-4-26 下午5:41:09
	 */
	public boolean deleteBatch(String params) throws Exception {
		boolean flag = false;
		String id = null;
		if(params.indexOf(",")!=-1){
			String[] ids = params.split(",");
			if(ids.length>0){				
				Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				for (int i = 0; i < ids.length; i++) {
					id = ids[i];
				
				}
			}
		}
		return flag;
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @param searchMap
	 * @return
	 * @author DengYouming
	 * @since 2016-4-26 下午7:29:36
	 */
    public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpSettlementTaskJY where 1=1 and status <> -1");
        searchMap.put("order_createTime","desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    public List listByPage(Map<String, Object> searchMap) throws Exception{
    	List resultList = null;
    	StringBuffer query = new StringBuffer(" from ErpSettlementTaskJY where 1=1 and status <> -1 ");
    	Session session = this.getCurrentSession();
    	Criteria criteria = session.createCriteria(ErpSettlementTaskJY.class);
    	
    	criteria.add(Restrictions.eq("", ""));
//    	if(session!=null){
//    		session.close();
//    	}
    	return resultList;
    }
    
    /**
     * 列出所有数据
     * @return
     * @author DengYouming
     * @since 2016-4-27 下午2:20:41
     */
    public List<ErpSettlementTaskJY> listAll(){
       	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Criteria criteria = session.createCriteria(ErpSettlementTaskJY.class);
    	criteria.addOrder(Order.desc(ErpSettlementTaskJY.F_CREATETIME));
    	List<ErpSettlementTaskJY> list = criteria.list();
//    	if(session!=null){
//    		session.close();
//    	}
    	return list;
    }
    
    public List<Map<String, Object>> getSettleCustomerByCode(String code){
    	String queryString="select distinct exceSettle_id from Erp_Settle_Customer_Jy where code=? and EXCESETTLE_ID is not null";
        return this.getJdbcTemplate().queryForList(queryString, new Object[]{code});
    }
    
    /**
     * 保存ErpSettlementTaskJY对象
     * @param obj
     * @author DengYouming
     * @since 2016-4-27 上午5:46:38
     */
    public void saveSettlementTask(ErpSettlementTaskJY obj){
    	/*Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Transaction tx = session.beginTransaction();
    	try{
	    	tx.begin();
	    	session.save(obj);
	    	session.flush();
			session.clear();
	    	tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		
    	}finally{
    		if(session!=null){
    			session.close();
    		}
    	}*/
    	this.getHibernateTemplate().save(obj);
    	
    }
    
    /**
     * 保存ErpSettleExcetaskJY对象
     * @param obj
     * @author tangxing
     * @date 2016-6-8下午12:17:11
     */
    public void saveExcepSettleTask(ErpSettleExcetaskJY obj){
    	this.getHibernateTemplate().save(obj);
    	
    }
    
    /**
     * 检查line中是否包含content
     * @param content
     * @param line
     * @return boolean
     * @author DengYouming
     * @since 2016-4-27 下午1:34:54
     */
    private boolean checkContains(String content, String line){
    	return line.contains(content);
    }
    
   
  /*  public List listCombo(){
        List list=null;
        final String queryString="from Combo where isDelete=?";
        list = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				query = session.createQuery(queryString);
			    query.setParameter(0, "0",Hibernate.STRING);   
				return query.list();
			}
		});
        return list;
    }*/
    
    /**
     * 根据状态查找客户信息 
     * @return
     */
    public List showCustomerByStatus(String settlementId,String status){
    	String queryString="from ErpSettlementCustomerJY where settlementTask_id='"+settlementId+"' and status='"+status+"'";
        return this.getHibernateTemplate().find(queryString);
    }
    
    /**
     * 根据状态查找客户信息 
     * @return
     */
    public List getCustomer(String settlementId){
       /* String sql = "select * from ErpSettlement where settlementTask_id='"+settlementId+"'";
        List list = this.getJdbcTemplate().queryForList(sql);*/
        String queryString="from ErpSettlementCustomerJY where settlementTask_id='"+settlementId+"'";
        
        return this.getHibernateTemplate().find(queryString);
    }
    
    /**
     * 根据状态查找客户信息 
     * @return
     */
    public List getCustomerBySettleTaskId(String settlementId){
       /* String sql = "select * from ErpSettlement where settlementTask_id='"+settlementId+"'";
        List list = this.getJdbcTemplate().queryForList(sql);*/
        String queryString="from ErpSettlementCustomerJY where settlementTask_id='"+settlementId+"' and exceSettle_id is null";
        
        return this.getHibernateTemplate().find(queryString);
    }
    
    /**
     * 根据状态查找客户信息(符合结算条件的) 有报告，状态为0,6(可结算，套餐不匹配)
     * @return
     */
    public List getCustomer(Map searchMap,Page page,String settlementId){
       /* String sql = "select * from ErpSettlement where settlementTask_id='"+settlementId+"'";
        List list = this.getJdbcTemplate().queryForList(sql);*/
        
        StringBuffer query = new StringBuffer("from ErpSettlementCustomerJY where settlementTask_id='"+settlementId+"' and pdf_Status='0' and status in ('0','1','2','6') and exceSettle_id is null");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
        
    }
    
    
    /**
     * 根据状态查找客户信息 
     * @return
     */
    public List getCustomerByStatus(String settlementId){
        String queryString="from ErpSettlementCustomerJY where settlementTask_id='"+settlementId+"' and status in(3,4,5)" ;
        return this.getHibernateTemplate().find(queryString);
    }
    
    /**
     * 统计excel总数 
     * @return
     */
    public int getExcleNum(String settlementId){
        String queryString="select count(*) from Erp_Settle_Customer_JY where settlementTask_id='"+settlementId+"'";
        return this.getJdbcTemplate().queryForInt(queryString);
    }
    
    /**
     * 统计异常数量 
     * @return
     */
    public int getExceptionNum(String settlementId){
        String queryString="select count(*) from Erp_Settle_Customer_JY where settlementTask_id='"+settlementId+"' and status='5'";
        return this.getJdbcTemplate().queryForInt(queryString);
    }
    
    /**
     * 统计支公司数量
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016-5-16上午10:22:29
     */
    public int getBranchCompanyNum(String settlementId){
    	String queryString="select count(distinct s.branch_company) from Erp_Settle_Customer_JY s where  s.settlementTask_id='"+settlementId+"'";
        return this.getJdbcTemplate().queryForInt(queryString);
    }
    
    
    /**
     * 根据ID查找读取客户类
     * @param id
     * @return
     */
    public ErpSettlementCustomerJY get(String id){
        return (ErpSettlementCustomerJY)this.findById(ErpSettlementCustomerJY.class, id);
    }
    
    public ErpSettleExcetaskJY getExceSettleById(String exceTaskId){
    	return (ErpSettleExcetaskJY)this.findById(ErpSettleExcetaskJY.class, exceTaskId);
    }
    
    /**
     * 根据ID查找结算任务类
     * @param id
     * @return
     */
    public ErpSettlementTaskJY getSettlementTask(String id){
        return (ErpSettlementTaskJY)this.findById(ErpSettlementTaskJY.class, id);
    }
    
    public List<ErpJYSettleTaskDetail> getJYSettleTaskDetailBysettleId(String settleTaskId,Page page, Map searchMap){
    	StringBuffer query = new StringBuffer("from ErpJYSettleTaskDetail where settleTaskId='"+settleTaskId+"'");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    	
    }
    
    public List<ErpJYSettleTaskDetail> getJYSettleTaskDetailBysettleId(String settleTaskId){
    	String query = "from ErpJYSettleTaskDetail where settleTaskId=?";
    	return this.getHibernateTemplate().find(query, new Object[]{settleTaskId});
    }
    
    public ErpJYSettleTaskDetail getJYSettleTaskDetailByDetailId(String settleTaskDetailId){
    	return this.getHibernateTemplate().get(ErpJYSettleTaskDetail.class, settleTaskDetailId);
    }
    
    /**
     * 根据ID查找异常结算任务类
     * @param id
     * @return
     * @author tangxing
     * @date 2016-6-14下午6:54:13
     */
    public ErpSettleExcetaskJY getExceSettlementTask(String id){
        return (ErpSettleExcetaskJY)this.findById(ErpSettleExcetaskJY.class, id);
    }
    
    /**
	 * 根据异常任务ID查找客户信息
	 * @param exSettTaskId
	 * @return
	 * @author tangxing
	 * @date 2016-6-14下午6:51:55
	 */
    public List<ErpSettlementCustomerJY> getSettleCustomerByExSettTaskId(String exSettTaskId){
    	String queryString="from ErpSettlementCustomerJY where exceSettle_id=?";
        return this.getHibernateTemplate().find(queryString,new Object[]{exSettTaskId});
    }
    
    /* ******存储过程****** */
    /**
     * 存储过程比对JYcustomer
     * @param settlementId
     * @author tangxing
     * @throws SQLException 
     * @date 2016-8-17下午3:04:13
     */
    public void comparJYcustomer(String settlementId) throws SQLException{
    	String procdure = "{Call JY_SETTLEMENT_TASK(?)}";
		//CallableStatement cs = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection().prepareCall(procdure);
    	Connection connection =this.getJdbcTemplate().getDataSource().getConnection();
    	CallableStatement cs = connection.prepareCall(procdure);
		cs.setString(1, settlementId);	//第一个参数
		cs.execute();
		cs.close();
		connection.close();
    }
    
    /**
     * 保存结算任务的相关信息，比对customer的comboprice
     * @param settlementId
     * @param currentName
     * @return
     * @throws HibernateException
     * @throws SQLException
     * @author tangxing
     * @date 2016-8-17下午3:23:05
     */
    public boolean saveSettleTaskByCustomer(String settlementId,String currentName) throws HibernateException, SQLException{
    	String procdure = "{Call JY_SETTEMENT_SAVETASK(?,?,?)}";
//		CallableStatement cs = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection().prepareCall(procdure);
    	Connection connection =this.getJdbcTemplate().getDataSource().getConnection();
    	CallableStatement cs = connection.prepareCall(procdure);
		cs.setString(1, settlementId);	//第一个参数
		cs.setString(2, currentName);	//第二个参数
		cs.registerOutParameter(3, java.sql.Types.VARCHAR);//第三个参数（输出）
		cs.execute();
		System.out.println("saveSettleTaskByCustomer--"+cs.getString(3));
		if(("fail").equals(cs.getString(3))){
			return false;
		}
		cs.close();
		connection.close();
		return true;
    }
    
    
    	String procdure = "{Call JY_SETTLEMENT_CREATE_EXCETASK(?,?,?,?)}";
    	public boolean createExceSettleTask(String settlementId,String exceTaskId,String exceTaskNo) throws HibernateException, SQLException{
		//CallableStatement cs = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection().prepareCall(procdure);
    	Connection connection =this.getJdbcTemplate().getDataSource().getConnection();
    	CallableStatement cs = connection.prepareCall(procdure);
		cs.setString(1, settlementId);	//第一个参数
		cs.setString(2, exceTaskNo);	//第二个参数
		cs.setString(3, exceTaskId);	//第三个参数
		cs.registerOutParameter(4, java.sql.Types.VARCHAR);//第四个参数（输出）
		cs.execute();
		System.out.println("createExceSettleTask--"+cs.getString(4));
		if(("fail").equals(cs.getString(4))){
			return false;
		}
		cs.close();
		connection.close();
		return true;
    }
    
    //更改状态
	public void updateStateSettleTask(String state,String id){
		String updateSql = "update ERP_SETTLEMENT_TASK_JY set status=? where id=?";
		this.getJdbcTemplate().update(updateSql,new Object[]{state,id});
	}

}
