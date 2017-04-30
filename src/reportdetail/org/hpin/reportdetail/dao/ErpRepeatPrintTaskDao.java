/**
 * 
 */
package org.hpin.reportdetail.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.hpin.reportdetail.entity.vo.ErpRepeatPrintEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 * @author Carly
 * @since 2016年9月19日17:23:15
 * 
 */
@Repository()
@SuppressWarnings({"rawtypes","unchecked"})
public class ErpRepeatPrintTaskDao extends BaseDao{
    
	public List findByPage(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from ErpRepeatPrintTask where 1=1 and isdelete='0'");
	    List valueList = new ArrayList();
	    OrmConverter.assemblyQuery(query, searchMap, valueList);
	    return super.findByHql(page, query, valueList);
	}
	
	public List getPdfContentInfoByCode(Page page, Map searchMap){
		StringBuffer query = new StringBuffer(" from ErpPrintTaskContent where 1=1 and ps='2' ");
	    List valueList = new ArrayList();
	    OrmConverter.assemblyQuery(query, searchMap, valueList);
	    return super.findByHql(page, query, valueList);
	}
	
	public void getPdfContentInfoByCode(Page page, String code){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT eptc.ID id, eptc.code code,eptc.username userName,eptc.age age,eptc.gender gender,eptc.createtime createTime,");
		sql.append("eptc.combo combo,hcr.branch_commany branchCompany,hcr.customer_name_simple ownedCompany,eptc.dept dept,eptc.type type");
		sql.append(" from erp_print_task_content eptc, hl_customer_relationship hcr");
		sql.append(" where eptc.code in("+code+") and eptc.isrepeatprint is null and eptc.branchcompanyid =hcr.id order by eptc.code desc");
		page.setTotalCount(this.findJdbcCount(sql, params));
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpRepeatPrintEntity> rowMapper = new BeanPropertyRowMapper<ErpRepeatPrintEntity>(ErpRepeatPrintEntity.class);
		page.setResults(this.findJdbcList(sql, params, rowMapper));
	}
	
	public List<ErpPrintTaskContent> getPdfContentInfo(String ids) {
		StringBuilder sql = new StringBuilder();
		StringBuilder help = new StringBuilder();
		String [] id = ids.split(",");
		for(int i=0;i<id.length;i++){
			if(i==0){
				help.append("'"+id[i]+"'");
			}else{
				help.append(",'"+id[i]+"'");
			}
		}
		sql.append("select * from erp_print_task_content where id in("+help+") and ps='2' and isrepeatprint is null");
		return this.getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(ErpPrintTaskContent.class));
	}

	/**
	 * @param branch_company
	 * @return 通过支公司id查询信息
	 */
	public CustomerRelationShip getCompanyInfoById(String branch_company) {
		return this.getHibernateTemplate().get(CustomerRelationShip.class, branch_company);
	}

	/**
	 * @param code 条码
	 * @return 通过条形码获取pdfcontent表中的信息
	 */
	public List<ErpPrintTaskContent> getPdfContentInfoByCode(String code) {
		String sql = "from ErpPrintTaskContent where code=? and ps='2' ";
		return this.getHibernateTemplate().find(sql,code);
	}
}
