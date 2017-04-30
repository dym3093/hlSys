package org.hpin.statistics.briefness.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException ;
import org.hibernate.Query ;
import org.hibernate.Session ;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.db.datasource.ConnectionPool;
import org.hpin.common.db.datasource.HpinConnection;
import org.hpin.common.widget.pagination.Page ;
import org.hpin.settlementManagement.entity.ErpComboPrice;
import org.hpin.settlementManagement.entity.ErpCompanyComboPrice;
import org.hpin.statistics.briefness.entity.GeneReportSummary;
import org.hpin.statistics.briefness.entity.GeneReportSummaryFinance;
import org.hpin.warehouse.entity.StoreProduce;
import org.hpin.warehouse.entity.StoreProduceDetail;
import org.springframework.orm.hibernate3.HibernateCallback ;
import org.springframework.stereotype.Repository ;
import org.springframework.util.Assert ;

@Repository()
public class QueryDAO extends BaseDao {
	
    public Logger logger = Logger.getLogger(this.getClass());
    
    public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from GeneReportSummary where sumNum>0");
        searchMap.put("order_createDate", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    public List findByPageFinance(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from GeneReportSummaryFinance where haveReportNum>0");
        searchMap.put("order_createDate", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }

private List getListByRs(ResultSet rs, String className) throws SQLException {
	List result = new ArrayList();

	while(rs.next())
	{
			try {
				Object vo = Class.forName(className).newInstance();
				populate(vo, rs);
				result.add(vo);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	}

	return result;
}
private List getListByRs(ResultSet rs, Map map) throws SQLException {
	List result = new ArrayList();
    ResultSetMetaData rsmt=rs.getMetaData(); 
	int len=rsmt.getColumnCount(); 
	while(rs.next()){ 
	map= new HashMap(); 
	for(int i=1;i<=len;i++){ 
		map.put(rsmt.getColumnName(i).toLowerCase(), rs.getString(i)); 

	} 
	result.add(map); 
	} 

	return result;
}

public List searchConfig(int[] pagePra, String sql,String sqltotal, String className,String flagexcel) throws SQLException {
	//pagePra [80,20,84]
	HpinConnection conn = ConnectionPool.getInstance().getConnection();
	PreparedStatement ps = null;
	ResultSet rs=null;
	PreparedStatement tps = null;
	ResultSet trs=null;
	className="com.boco.eoms.queryconfig.vo."+className;
	int total=0;
	try {
	  if(!"true".equals(flagexcel)){
		sqltotal = sqltotal.trim();
		tps = conn.prepareStatement(sqltotal);
        trs = tps.executeQuery();
        while (trs.next()) {
        	total=trs.getInt(1);
          }
        pagePra[2]=total;
	  }
		
		if("true".equals(flagexcel)){
           sql = sql.trim();
		}else{
		   sql = "SELECT * FROM(SELECT A.*, ROWNUM RN FROM ("+sql+") A WHERE ROWNUM <= "+(pagePra[1]+pagePra[0])+")WHERE RN > "+ pagePra[0];
		}
		logger.info("\n seacch sql :" + sql);
		ps = conn.prepareStatement(sql);

	    rs = ps.executeQuery();
		return getListByRs(rs, className);

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		close(rs);
		close(ps);
		e.printStackTrace();
	} finally {
		 conn.close();
	}

	return null;
}
public List queryConfigSql(int pageIndex, int pageSize, String sql) throws SQLException {
	
	HpinConnection conn = ConnectionPool.getInstance().getConnection();
	PreparedStatement ps = null;
	ResultSet rs=null;
	Map map=null;
	try {
		sql = sql.trim();
	    logger.info("\n query sql :" + sql);
		sql = "SELECT * FROM(SELECT A.*, ROWNUM RN FROM ("+sql+") A WHERE ROWNUM <= "+(pageIndex + 1) * pageSize+")WHERE RN > "+ (pageIndex * pageSize);
		ps = conn.prepareStatement(sql);

	    rs = ps.executeQuery();
	    return getListByRs(rs, map);
	    
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		close(rs);
		close(ps);
		e.printStackTrace();
	} finally {
		 conn.close();
	}

	return null;
}
public List queryAllConfigSqlBak(String sql) throws SQLException {
	
	HpinConnection conn = ConnectionPool.getInstance().getConnection();
	PreparedStatement ps = null;
	ResultSet rs=null;
	Map map=null;
	try {
		sql = sql.trim();
	    logger.info("\n query sqltotal :" + sql);
		ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
	    return getListByRs(rs, map);
	  
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		close(rs);
		close(ps);
		e.printStackTrace();
	} finally {
		 conn.close();
	}

	return null;
}

public List queryAllConfigSql(String sql) throws SQLException {
	return super.getJdbcTemplate().queryForList(sql) ;
}

public List statConfig(String sql, String className) throws SQLException {
	
	HpinConnection conn = ConnectionPool.getInstance().getConnection();
	PreparedStatement ps = null;
	ResultSet rs=null;
	if(!"".equals(className)){
	className="org.hpin.statistics.briefness.entity."+className;
	}
	Map map=null;
	try {
		sql = sql.trim();
	    logger.info("\n stat sql :" + sql);
		ps = conn.prepareStatement(sql);

	    rs = ps.executeQuery();
	    if(!"".equals(className)){
		  return getListByRs(rs, className);
	    }else{
	     return getListByRs(rs, map);
	    }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		close(rs);
		close(ps);
		e.printStackTrace();
	} finally {
		 conn.close();
	}

	return null;
}
public int queryConfigTotal(String sql) {
	HpinConnection conn = ConnectionPool.getInstance().getConnection();
	Statement stmt = null;
	int ir = 0;
	try {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			ir = rs.getInt(1);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		try{
			stmt.close() ;
			conn.close() ;
		}catch (Exception e) {
		}
	}
	
	return ir;
}
public String getCpmcFormTree(String sql) {
	HpinConnection conn = ConnectionPool.getInstance().getConnection();
	Statement stmt = null;
	String ir = "";
	try {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			ir = rs.getString(1);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		try{
			stmt.close() ;
			conn.close() ;
		}catch (Exception e) {
		}
	}
	
	return ir;
}
	
	/**
	 * SQL分页查询，报表框架需要
	 * 
	 * @param page
	 * @param sql
	 * @param values
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findBySql(final Page page, final String sql,
			final Object[] values) {
		Assert.notNull(page, "分页类对象page不能为空");
		List list = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Assert.notNull(sql, "sql不能为空");
						Query query = null;
						String sqlCount = "select count(*) from (" + sql + ")" ;
						query = session.createSQLQuery(sqlCount);
						Long totalCount = 0l;
						if (values != null && values.length > 0) {
							for (int i = 0; i < values.length; i++) {
								query.setParameter(i, values[i]);
							}
						}
						totalCount = ((java.math.BigDecimal) query
								.uniqueResult()).longValue();
						page.setTotalCount(totalCount);
						int offset = 0;
						offset = (page.getPageNum() - 1) * page.getPageSize();
						query = session.createSQLQuery(sql);
						if (values != null && values.length > 0) {
							for (int i = 0; i < values.length; i++) {
								query.setParameter(i, values[i]);
							}
						}
						return query.setFirstResult(offset).setMaxResults(
								page.getPageSize()).list();
	
					}
				});
		page.setResults(list);
		return list;
	}
	
	/**
	 * 根据项目编码查找StoreProduce
	 * @param itemCode
	 * @return
	 * @author tangxing
	 * @date 2016-9-5上午11:27:02
	 */
	public List<StoreProduce> getStoreProduceByItemCode(String itemCode,Date startTime,Date endTime){
		String query = "from StoreProduce where projectCode=? and createTime>=? and createTime<?";
		return this.getHibernateTemplate().find(query,new Object[]{itemCode,startTime,endTime});
	}
	
	public List<StoreProduceDetail> getStoreProduceDetailByProduceId(String produceId){
		String query = "from StoreProduceDetail where produceId=?";
		return this.getHibernateTemplate().find(query,new Object[]{produceId});
	}
	
	/**
	 * 根据Id查找GeneReportSummary
	 * @param id
	 * @return
	 * @author tangxing
	 * @date 2016-9-5下午3:13:53
	 */
	public List<GeneReportSummary> getGeneReportSummaryById(String id){
		String query = "from GeneReportSummary where id=?";
		return this.getHibernateTemplate().find(query,new Object[]{id});
	}
	
	public List<GeneReportSummaryFinance> getGeneReportSummaryFinanceById(String id){
		String query = "from GeneReportSummaryFinance where id=?";
		return this.getHibernateTemplate().find(query,new Object[]{id});
	}
	
	/**
	 * 获取中间表中的项目信息数据
	 * @param proById
	 * @return
	 * @author tangxing
	 * @date 2016-9-21下午3:47:30
	 */
	public List<Map<String,Object>> getCustomerRelationShipProById(String cusRelShipId){
		String query = "select hcr.id,hcr.project_code,hcr.project_name,hcr.project_owner from erp_events e join hl_customer_relationship_pro hcr "+
				"on e.customerrelationshippro_id=hcr.id "+
				"where e.customerrelationshippro_id=?";
		return this.getJdbcTemplate().queryForList(query, new Object[]{cusRelShipId});
	}
	
	public List<Map<String,Object>> getCRShipProById(String cusRelShipId){
		String query = "select hcr.project_code from erp_events e join hl_customer_relationship_pro hcr "+
				"on e.customerrelationshippro_id=hcr.id "+
				"where e.customerrelationshippro_id=?";
		return this.getJdbcTemplate().queryForList(query, new Object[]{cusRelShipId});
	}
	
	/**
	 * 基因公司结算套餐价格
	 * @return
	 * @author tangxing
	 * @date 2016-9-21下午3:48:10
	 */
	public List<ErpComboPrice> getComboPriceByComboName(String comboName,String geneCompany){
		String queryString = "from ErpComboPrice where comboName=? and geneCompany like ?";
		return this.getHibernateTemplate().find(queryString,new Object[]{comboName,geneCompany});
	}
	
	/**
	 * 保险公司结算套餐价格
	 * @return
	 * @author tangxing
	 * @date 2016-9-21下午3:45:32
	 */
	public List<ErpCompanyComboPrice> getCompanyComboPriceByComboName(String comboName,String branchCompany){
		String queryString = "from ErpCompanyComboPrice where combo=? and company=?";
		return this.getHibernateTemplate().find(queryString,new Object[]{comboName,branchCompany});
	}
	
	/**
	 * 根据用户ID获取User
	 * @param userName
	 * @return
	 * @author tangxing
	 * @date 2016-12-21下午2:32:41
	 */
	public User getUserByUserId(String userId){
		return this.getHibernateTemplate().get(User.class, userId);
	}
}