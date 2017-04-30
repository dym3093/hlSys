/**
 * 
 */
package org.hpin.events.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.ErpCustomerReadDao;
import org.hpin.events.entity.vo.CustomerReadQuery;
import org.hpin.events.entity.vo.CustomerReadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author machuan
 * @date 2016年11月3日
 */

@Service(value = "org.hpin.events.service.ErpCustomerReadService")
@Transactional()
public class ErpCustomerReadService extends BaseService{
	@Autowired
	private ErpCustomerReadDao erpCustomerReadDao;
	
	@SuppressWarnings("rawtypes")
	public void findListReportInterpretation(Page page, CustomerReadQuery customerReadQuery) throws Exception{
		//参数集合;
		List<Object> params = new ArrayList<Object>();
		//sql
		String sql = "select " ;
		String content=	"ectr.ID id,"
						+ "ectr.CODE code,"
						+ "ectr.NAME name,"
						+ "ectr.SEX sex,"
						+ "ectr.AGE age,"
						+ "ectr.IDNO idno,"
						+ "ectr.PHONE phone,"
						+ "ectr.SETMEAL_NAME setmealName,"
						+ "tpt.PROJECT_TYPE_NAME projectTypeName,"
						+ "ectr.PDFFILEPATH pdffilepath,"
						+ "decode((select length(VIEW_PATH) from erp_report_detail where CODE = ectr.CODE and rownum =1),null,null,'true') \"detail\","
						+ "decode((select length(REPORT_PATH) from phy_report where GENE_CODE = ectr.CODE and rownum =1),null,null,'true') \"phyReport\","
						+ "ectr.NOTE note "
						+ "FROM erp_customer ectr "
						+ "left join hl_jy_combo hjc on ectr.SETMEAL_NAME = hjc.COMBO_NAME "
						+ "left join t_project_type tpt on hjc.PROJECT_TYPES = tpt.PROJECT_TYPE "
						+ "where ectr.IS_DELETED = 0 and hjc.IS_DELETE = 0 and tpt.IS_DELETED = 0 ";
		StringBuilder contentb = new StringBuilder(content);
		//参数处理
		dealParams(customerReadQuery, contentb);
		StringBuilder jdbcSql = new StringBuilder(sql+contentb);
		
		jdbcSql.append(" ORDER BY ectr.CODE DESC ");
		
		//count
		page.setTotalCount(erpCustomerReadDao.findJdbcCount(jdbcSql, params));
		
		//list
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<CustomerReadVo> rowMapper = new BeanPropertyRowMapper<CustomerReadVo>(CustomerReadVo.class);
		page.setResults(erpCustomerReadDao.findJdbcList(contentb.toString(), params, rowMapper));
		
	}
	
	private void dealParams(CustomerReadQuery customerReadQuery, StringBuilder jdbcSql) {
		if(customerReadQuery != null) {
			//姓名
			if(StringUtils.isNotEmpty(customerReadQuery.getName())) {
				jdbcSql.append(" AND ectr.NAME = '").append(customerReadQuery.getName().trim()).append("' ");
			}
			//条形码
			if(StringUtils.isNotEmpty(customerReadQuery.getCode())) {
				jdbcSql.append(" AND ectr.CODE = '").append(customerReadQuery.getCode().trim()).append("' ");
			}
			//身份证号
			if(StringUtils.isNotEmpty(customerReadQuery.getIdno())) {
				jdbcSql.append(" AND ectr.IDNO = '").append(customerReadQuery.getIdno().trim()).append("' ");
			}
			//套餐名
			if(StringUtils.isNotEmpty(customerReadQuery.getSetmealName())) {
				jdbcSql.append(" AND ectr.SETMEAL_NAME like '%").append(customerReadQuery.getSetmealName().trim()).append("%' ");
			}
			//手机号
			if(StringUtils.isNotEmpty(customerReadQuery.getPhone())) {
				jdbcSql.append(" AND ectr.PHONE = '").append(customerReadQuery.getPhone().trim()).append("' ");
			}
			//项目编码
			if(StringUtils.isNotEmpty(customerReadQuery.getProjectTypes())) {
				jdbcSql.append(" AND hjc.PROJECT_TYPES = '").append(customerReadQuery.getProjectTypes().trim()).append("' ");
			}
		}
	}

	/**
	 * @return
	 * @author machuan
	 * @date  2016年11月3日
	 */
	public String findPhyReport(String code) throws Exception{
		String sql = "select p.report_path from phy_report p where p.gene_code = '"+code+"' and rownum = 1";
		Map<String,Object> map = erpCustomerReadDao.getJdbcTemplate().queryForMap(sql);
		return String.valueOf(map.get("report_path"));
	}

	/**
	 * @param code
	 * @return
	 * @author machuan
	 * @date  2016年11月4日
	 */
	public List<String> findDetail(String code) throws Exception{
		String sql = "select e.view_path from erp_report_detail e where e.code = '"+code+"' order by file_name ";
		List<Map<String, Object>> results = erpCustomerReadDao.getJdbcTemplate().queryForList(sql);
		List<String> details = new ArrayList<String>();
		for(Map<String, Object> map : results){
			details.add(String.valueOf(map.get("view_path")));
		}
		return details;
	}
	
}
