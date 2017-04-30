package org.hpin.settlementManagement.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ErpComboPrice;
import org.hpin.settlementManagement.entity.ErpPrintComSettleCustomer;
import org.hpin.settlementManagement.entity.ErpPrintCompanySettleTask;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerJY;
import org.hpin.settlementManagement.entity.ErpSettlementTaskJY;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpPrintCompanySettleTaskDao extends BaseDao{

	 /**
     * 分页获取对象
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpPrintCompanySettleTask where 1=1");
        searchMap.put("createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
   /**
    * 修改会议价格
    * @param companySettleTask
    * @author tangxing
    * @date 2016-5-25下午6:26:40
    */
    public void updatePCCustomer(ErpPrintComSettleCustomer comSettleCustomer){
    	super.update(comSettleCustomer);
    }
    
    /**
     * 添加打印公司结算任务
     * @param companySettleTask
     */
    public void addPrintCompanySettleTask(ErpPrintCompanySettleTask companySettleTask){
    	super.save(companySettleTask);
    }
    
	/**
	 * 根据保险公司结算任务ID删除ErpSettlementCustomerJY表的记录
	 * @param settle_id
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-3 下午11:13:57
	 */
	public void deletePrintCompanyCustomerBatch(String settlementtask_id) throws Exception{
		String sql = "delete from ERP_PRINTCOMSETTLE_CUSTOMER where SETTLEMENTTASK_ID = '"+settlementtask_id+"'";
		try {
			super.getJdbcTemplate().execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 根据ID查找结算任务类
     * @param id
     * @return
     */
    public ErpPrintCompanySettleTask getPrintComSettleTask(String id){
        return (ErpPrintCompanySettleTask)this.findById(ErpPrintCompanySettleTask.class, id);
    }
    
   /**
    * 根据打印公司结算任务id查找对应的会员信息
    * @param Id
    * @return
    * @author tangxing
    * @date 2016-5-5下午9:50:52
    */
    public List getPrintComCustomer(String Id){
        String queryString="from ErpPrintComSettleCustomer where settlementTask_id='"+Id+"'";
        return this.getHibernateTemplate().find(queryString);
    }
    
    /**
     * 根据打印公司结算任务id查找对应的会员信息
     * @param Id
     * @return
     * @author tangxing
     * @date 2016-5-5下午9:50:52
     */
     public List getPrintComCustomerGBCombo(String Id){
         String queryString="from ErpPrintComSettleCustomer where settlementTask_id='"+Id+"'";
         return this.getHibernateTemplate().find(queryString);
     }
    
   /**
    * 核对会员信息后重新加载会员信息（只加载可结算的）
    * @param settlementId
    * @param status
    * @return
    * @author tangxing
    * @date 2016-5-5下午10:08:48
    */
    public List showCustomerByStatus(String settlementId,String status){
    	String queryString="from ErpPrintComSettleCustomer where settlementTask_id='"+settlementId+"' and status='"+status+"'";
        return this.getHibernateTemplate().find(queryString);
    }
    
    /**
     * 根据ID查找打印公司结算任务类
     * @param id
     * @return
     */
    public ErpPrintCompanySettleTask getSettlementTask(String id){
        return (ErpPrintCompanySettleTask)this.findById(ErpPrintCompanySettleTask.class, id);
    }
    
    /**
     * 根据ID查找读取客户类
     * @param id
     * @return
     */
    public ErpPrintComSettleCustomer get(String id){
        return (ErpPrintComSettleCustomer)this.findById(ErpPrintComSettleCustomer.class, id);
    }
    
    /**
     * 获取当前结算任务的会员数量
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016-5-9上午10:41:31
     */
    public int getExcelNum(String settlementId){
		String queryString = "select count(*) from ERP_PRINTCOMSETTLE_CUSTOMER where settlementTask_id=?";
		int count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{settlementId});
		return count;
    }
    
    /**
     * 统计支公司数量
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016-5-16上午10:22:29
     */
    public int getBranchCompanyNum(String settlementId){
    	String queryString="select count(distinct p.branch_company) from ERP_PRINTCOMSETTLE_CUSTOMER p where  p.settlementTask_id='"+settlementId+"'";
        return this.getJdbcTemplate().queryForInt(queryString);
    }
    
    /**
     * 获取当前结算任务的会员的异常数量
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016-5-9上午10:52:38
     */
    public int getExceptionNum(String settlementId){
		String queryString = "select count(*) from ERP_PRINTCOMSETTLE_CUSTOMER where settlementTask_id=? and status='5'";
		int count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{settlementId});
		return count;
    }
    
    /**
     * 获取导入的总人数
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016年5月19日21:39:38
     */
    public int getSumNum(String settlementId){
		String queryString = "select count(code) from ERP_PRINTCOMSETTLE_CUSTOMER where settlementTask_id=?";
		int count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{settlementId});
		return count;
    }
    
    /**
     * 获取导入的异常数量
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016年5月19日21:39:38
     */
    public int getExceNum(String settlementId){
		String queryString = "select count(*) from ERP_PRINTCOMSETTLE_CUSTOMER where settlementTask_id=? and status='5'";
		int count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{settlementId});
		return count;
    }
    
    /**
     * 统计套餐数量
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016年5月19日21:38:57
     */
    public int getComboNum(String settlementId){
    	String queryString="select count(distinct p.setmeal_name) from ERP_PRINTCOMSETTLE_CUSTOMER p where  p.settlementTask_id=?";
    	int count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{settlementId});
        return count;
    }
    
    /**
     * 统计可结算数量
     * @param settlementId
     * @return
     * @author tangxing
     * @date 2016年5月19日21:38:57
     */
    public int getIsSett(String settlementId){
    	String queryString="select count(*) from ERP_PRINTCOMSETTLE_CUSTOMER where settlementTask_id=? and status='2'";
    	int count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{settlementId});
        return count;
    }
	
}
