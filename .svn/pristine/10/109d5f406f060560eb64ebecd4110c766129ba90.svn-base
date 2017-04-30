/**
 * 
 */
package org.hpin.reportdetail.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.base.region.entity.Region;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpPrintBatch;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
* 公司名称: 远盟康健(北京)科技有限公司
* 
* author: dengqin 
* createDate: 2016-4-14 下午3:36:32
*/
/**
 * @author dengqin
 *
 */
@Repository()
public class ErpPrintBatchDao extends BaseDao{
    /**
     * 分页获取对象
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
//      StringBuffer query = new StringBuffer(" from ErpEvents where isDeleted=0  and isEnable = 1");//没有isEnable
        StringBuffer query = new StringBuffer(" from ErpPrintBatch where 1=1");
        //searchMap.put(" eventsNo", "desc");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    public List findByPageReport(Page page, Map searchMap) {//ErpReportdetailPDFContent
        StringBuffer query = new StringBuffer(" from ErpPrintTaskContent where 1=1");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }

    
    public void save(List<ErpPrintBatch> erpPrintBatchs){
        this.getHibernateTemplate().saveOrUpdateAll(erpPrintBatchs);
    }
    
    /**
     * 获取所有打印批次集合
     * @return
     * @author tangxing
     * @date 2016-5-10下午2:59:14
     */
    public List<ErpPrintBatch> getProvinceCity(){
    	String queryString="from ErpPrintBatch where isDelete='0' and isPrintTask='0'";
    	return this.getHibernateTemplate().find(queryString);
    }
    
    /**
     * 获取已经打印批次集合
     * @return
     * @author tangxing
     * @date 2016-5-31下午5:11:44
     */
    public List<ErpPrintBatch> getProvinceCityTwo(){
    	String queryString="from ErpPrintBatch where isDelete='0' and isPrintTask='1'";
    	return this.getHibernateTemplate().find(queryString);
    }
    
    /*
     * 统计
     */
    public List getAllInputNumsList(String userName){
        List list=null;
        String queryString="select printBatchNo,count(*) as total from ERP_PRINT_BATCH where isdelete=0 group by printBatchNo";
        list = this.getJdbcTemplate().queryForList(queryString);
         return list;
    }
    
    
    public List listErpPrintBatchs(){
        List list=null;
        String queryString="from ErpPrintBatch where isDelete=? order by createTime desc";
        
        list = this.getHibernateTemplate().find(queryString, "0");
        
        System.out.println("list---------"+list.get(0));
        return list;
    }
    
    /**
     * 查询当天最大批次号
     * @param date
     * @return
     */
    public String maxNo(String date){
        String conferenceNo="HL1512142048000";
        String sql = "select max(printBatchNo) as printBatchNo from Erp_printBatch where to_char(create,'yyyy-mm-dd')='"+date+"'";
        List list = this.getJdbcTemplate().queryForList(sql);
        Map map = (Map) list.get(0);
        String maxNo = (String) map.get("printBatchNo");
        if(StrUtils.isNotNullOrBlank(maxNo)){
            conferenceNo=maxNo.toString();
        }
        return conferenceNo;
    }
    
    
    /**
     * 根据ID获取批次
     * @description 
     * @param id
     * @return
     *
     * @return ErpConference
     * @throws
     *
     */
    public ErpPrintBatch get(String id){
        return (ErpPrintBatch)this.findById(ErpPrintBatch.class, id);
    }
    
    /**
     * 是否存在已有的批次
     * @param compannyName 支公司名称 eventDate 场次日期
     * @return
     */
    public String isNotRepeatNoByEvents(String printBatchNo,String events){
        String repeat="";
        String hql = "from ErpPrintBatch where printBatchNo =? and events = ?";
//      String hql = "from ErpEvents where branch_company =? and owned_company = ? and event_date = ? and is_deleted=0 ";
        System.out.println("校验批次："+hql);
        List list = this.getHibernateTemplate().find(hql,new Object[]{printBatchNo,events});
//      List list = this.getHibernateTemplate().find(hql,new Object[]{compannyName,ownedCompany,eventDate});
        if(list.size()>0){
            repeat="该时间已经存在批次";
        }
        return repeat;
    }
    
	public List<Region> findRegionByParentId(String parentId){
		String hql = " from Region where parentId =? order by id asc " ;
		return super.getHibernateTemplate().find(hql,parentId); 
	}
	
	/**
	 * 显示未在打印任务的打印批次
	 * @author dym
	 * @return
	 */
    public List listErpPrintBatchsNotInPrintTask(){
        List list=null;
        String queryString="from ErpPrintBatch where isDelete=? and isPrintTask=? order by createTime desc";
        
        list = this.getHibernateTemplate().find(queryString,new Object[]{"0", "0"});
       
        if(!CollectionUtils.isEmpty(list)){
        	System.out.println("list---------"+list.get(0));
        }else{
        	System.out.println("list为空！");
        }
        return list;
    }
    
    /**
     * 显示未在该次打印任务的打印批次
     * @author dym
     * @param params
     * @return
     */
    public List listUnErpPrintBatchs(Map<String,Object> params){
    	final String  printTaskId= (String)params.get("printTaskId");
        List list=null;
        String queryString="from ErpPrintBatch where isDelete=? and isPrintTask=? and printTaskId <> ? or printTaskId is null order by createTime desc";
        
        list = this.getHibernateTemplate().find(queryString,new Object[]{"0", "0", printTaskId});
        
        if(!CollectionUtils.isEmpty(list)){
        	System.out.println("list---------"+list.get(0));
        }else{
        	System.out.println("list为空！");
        }
        return list;
    }
    
    /**
     * 
     * @param params
     * @return
     */
    public List listErpPrintBatchByTaskId(final String printTaskId){
        List list=null;
        String queryString="from ErpPrintBatch where isDelete=? and printTaskId = ? order by createTime desc";
        
        list = this.getHibernateTemplate().find(queryString,new Object[]{"0", printTaskId});
        
        if(!CollectionUtils.isEmpty(list)){
        	System.out.println("list---------"+list.get(0));
        }else{
        	System.out.println("list为空！");
        }
        return list;
    }
    
    /**
     * 根据条件显示已处理批次
     * @param params
     * @return
     */
    public List<ErpPrintBatch> listAlreadyErpPrintBatchs(){
        List list=null;
        String queryString="from ErpPrintBatch where isDelete=? and isPrintTask=? order by createTime desc";
        
        list = this.getHibernateTemplate().find(queryString, new Object[]{"0", "1"});
               
        if(!CollectionUtils.isEmpty(list)){
        	System.out.println("list---------"+list.get(0));
        }else{
        	System.out.println("list为空！");
        }
    	return list;
    }
    
    /**
     * 根据条件显示已处理批次
     * @return
     * @author tangxing
     * @date 2016-5-31下午4:01:41
     */
    public List<ErpPrintBatch> findByPageIsPrint(Page page, Map searchMap){
    /*    List list=null;
        String queryString="from ErpPrintBatch where isDelete=? and isPrintTask=? order by createTime desc";
        
        list = this.getHibernateTemplate().find(queryString, new Object[]{"0", "1"});*/
    	
        StringBuffer query = new StringBuffer(" from ErpPrintBatch where isDelete=0 and isPrintTask=1");
        //searchMap.put(" eventsNo", "desc");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }

	public List<ErpPrintBatch> getPrintBatchList(String printTaskNo) {
		StringBuilder helps = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		String [] printTaskNos = printTaskNo.split(",");
		for (int i = 0; i < printTaskNos.length; i++) {
			if(i==0) {
				helps.append("'"+printTaskNos[i]+"'");
			} else {
				helps.append(",").append("'"+printTaskNos[i]+"'");
			}
		}
		sql.append("select * from erp_print_batch where isdelete='0' and printtaskid in("+helps.toString()+")  order by createtime desc");
//		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql.toString());
//		System.out.println(list);
		List<ErpPrintBatch> lists = this.getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(ErpPrintBatch.class));
		return lists;
	}
	
//	class ReportValidQueryRowMapper implements RowMapper<ErpPrintBatch> {
//
//		@Override
//		public ErpPrintBatch mapRow(ResultSet rs, int index) throws SQLException {
//			ErpPrintBatch bean = new ErpPrintBatch();
//			bean.setId(rs.getString("id"));
//			bean.setPrintBatchNo(rs.getString("printbatchno"));
//			return bean;
//		}
//		
//	}
}
