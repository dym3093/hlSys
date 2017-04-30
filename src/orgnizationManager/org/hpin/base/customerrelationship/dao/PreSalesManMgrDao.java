package org.hpin.base.customerrelationship.dao;

import org.apache.log4j.Logger;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.PreSalesManMgr;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.widget.pagination.Page;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author ybc
 * @since 2017/02/08
 */
@Repository("preSalesManMgrDao")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PreSalesManMgrDao extends BaseDao{

	private static Logger log = Logger.getLogger(PreSalesManMgrDao.class);

    // add by Damian 2017-04-12 begin
	private static final String SQL_FIND_SALES_MAN_HEAD = " SELECT k.* FROM ( ";
    private static final String SQL_FIND_SALES_MAN_BODY = " SELECT t.* , ROWNUM AS RN FROM VIEW_ERP_SALEMANNUM_INFO_PRE t WHERE 1=1 ";
	private static final String SQL_FIND_SALES_MAN_TAIL = " AND ROWNUM <= ? ) k WHERE 1 = 1 AND k.rn >= ? ORDER BY k.createTime desc ";
    private static final String SQL_COUNT_SALES_MAN_HEAD = " SELECT COUNT(k.id) FROM ( ";
    private static final String SQL_COUNT_SALES_MAN_TAIL = " ) k WHERE 1=1 ";
    // add by Damian 2017-04-12 end

	public List<CustomerRelationShip> getCompanyList(int i, String companyName) {
		StringBuilder sql = new StringBuilder("select id id,branch_commany branchCommany,owned_company ownedCompany,customer_name_simple customerNameSimple from hl_customer_relationship where 1 = 1 ");
		switch (i) {
		case 1:
			sql.append("and customer_name_simple like '%").append(companyName).append("%'");
			break;

		default:
			sql.append("and branch_commany like '%").append(companyName).append("%'");
			break;
		}
		return this.getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(CustomerRelationShip.class));
	}
	
	//通过 姓名，营销员id，手机号做重复判断
	public boolean hasObjByNoAndName(PreSalesManMgr salesMan){
		String hql = "from PreSalesManMgr where salesman = ? and employeeNo=? and isDeleted=0";
		List<PreSalesManMgr> resultList = this.getHibernateTemplate().find(hql, new Object[]{salesMan.getSalesman(),salesMan.getEmployeeNo()});
		if(null!=resultList&&resultList.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * 条件查询
	 * @param page
	 * @param searchMap
	 * @return
	 * @author Damian
	 * @since 2017-04-12
	 */
	public Page listPages(Page page, Map searchMap){
		List list;
        StringBuilder sqlBld = new StringBuilder(SQL_FIND_SALES_MAN_BODY);
        //查询所需参数值
        Object[] values;
        int i = 0;
        if (!CollectionUtils.isEmpty(searchMap)) {
            values = new Object[searchMap.keySet().size()+2];
            Iterator iter = searchMap.keySet().iterator();
            String key;
            while (iter.hasNext()) {
                key = (String) iter.next();
                if ("filter_and_employeeNo_EQ_S".equals(key)) {
                    sqlBld.append(" and t.employeeNo like '%'||?||'%' ");
                    values[i++] = searchMap.get(key);
                } else if ("filter_and_salesman_EQ_S".equals(key)) {
                    sqlBld.append(" and t.salesman like '%'||?||'%' ");
                    values[i++] = searchMap.get(key);
                } else if ("filter_and_ymCompany_IN_S".equals(key)) {
                    sqlBld.append(" and t.ymCompany like '%'||?||'%' ");
                    values[i++] = searchMap.get(key);
                } else if ("filter_and_ymOwncompany_IN_S".equals(key)) {
                    sqlBld.append(" and t.ymOwncompany like '%'||?||'%' ");
                    values[i++] = searchMap.get(key);
                } else if ("filter_and_provice_EQ_S".equals(key)) {
                    sqlBld.append(" and t.province like '%'||?||'%' ");
                    values[i++] = searchMap.get(key);
                } else if ("filter_and_city_EQ_S".equals(key)) {
                    sqlBld.append(" and t.city like '%'||?||'%' ");
                    values[i++] = searchMap.get(key);
                }
            }
        }else{
            values = new Object[2];
        }

        String countSql = SQL_COUNT_SALES_MAN_HEAD + sqlBld.toString() + SQL_COUNT_SALES_MAN_TAIL;
        log.info("find customer countSql: "+ countSql);
        Object[] countArr = null;
        log.info("find customer valuesArr: "+ Arrays.toString(values));
        if (values.length>2) {
            countArr = Arrays.copyOf(values, values.length - 2);
        }
        log.info("find customer countArr: "+ Arrays.toString(countArr));
        //查询记录数
        int count = this.getJdbcTemplate().queryForInt(countSql, countArr);
        log.info("find customer count: "+ count);

        String sqlFind = SQL_FIND_SALES_MAN_HEAD + sqlBld.toString() + SQL_FIND_SALES_MAN_TAIL;
        values[i++] = page.getPageNumEndCount();
        values[i++] = page.getPageNumStartCount();
        log.info("find customer findSql sql: "+ sqlFind);
        log.info("find customer findSql valuesArr: "+ Arrays.toString(values));
        list = this.getJdbcTemplate().queryForList(sqlFind, values);
        log.info("find customer list size : "+ list.size());
        page.setResults(list);
        page.setTotalCount((long)count);

		return page;
	}
}
