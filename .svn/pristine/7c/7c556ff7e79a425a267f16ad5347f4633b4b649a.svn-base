/**
 * 
 */
package org.hpin.reportdetail.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpBaseEmpInfo;
import org.hpin.reportdetail.entity.vo.ErpReportMailVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author machuan
 * @date 2016年12月12日
 */
@Repository()
public class ErpReportExpressDao extends BaseDao{

	/**
	 * 根据批次ID  获取所有快递信息
	 * @param reportIDs
	 * @return
	 * @author machuan
	 * @date  2016年12月20日
	 */
	public List<ErpReportMailVo> findErpReportMailVoByReportID(String reportIDs) throws Exception{
		String []arrId = reportIDs.split(",");
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
//		包含批次号，条形码，客户姓名，性别，年龄，支公司信息，部门，套餐，采样日期，保险公司营销员，快递单号。
		String sql = "select e.batch_no batchNo,e.code,e.name,c.sex,c.age,"
					+" c.branch_company branchCompany,c.department dept,c.setmeal_name combo,"
					+" c.sampling_date samplingDate,c.sales_man saleMan,e.express_no expressNo"
					+" from erp_report_express e  "
					+" left join erp_customer c on e.events_no=c.events_no and e.code=c.code and e.name=c.name "
					+" where e.report_id in"
					+ ids
					+" and e.express_delete='0' and c.is_deleted='0'";
		BeanPropertyRowMapper<ErpReportMailVo> rowMapper = new BeanPropertyRowMapper<ErpReportMailVo>(ErpReportMailVo.class);				
		return this.getJdbcTemplate().query(sql, rowMapper);
	}

	/**
	 * 根据批次ID获取邮件正文信息
	 * @param reportIDs
	 * @return
	 * @author machuan
	 * @date  2016年12月20日
	 */
	public String getTextForMail(String reportIDs) throws Exception{
		String []arrId = reportIDs.split(",");
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String text = "";
		String sql = "select ee.batchno,ee.branch_company from erp_events ee where ee.events_no in"
					+" (select e.events_no from erp_report_express e where e.report_id in"
					+ ids
					+" and e.express_delete='0' group by e.events_no) and ee.is_deleted='0'";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		String batchNo = "";
		String branchCompany = "";
		for(Map<String, Object> map : list){
			batchNo+= ","+map.get("batchno");
			branchCompany+= ","+map.get("branch_company");
		}
		if(batchNo.length()>1&&branchCompany.length()>1){
			batchNo = batchNo.substring(1);
			branchCompany = branchCompany.substring(1);
		}
		String countSql = "select count(*) from erp_report_express where report_id in"
						+ ids
						+ " and express_delete='0'";
		int countNum = this.getJdbcTemplate().queryForInt(countSql);
		text = batchNo+"批次报告已寄出，至"+branchCompany+"支公司，包含"+countNum+"份报告。";
		return text;
	}

	/**
	 * 根据 reportCode 判断临时表是否存在
	 * @param reportCode
	 * @return
	 * @author machuan
	 * @date  2017年1月4日
	 */
	public boolean checkReportCode(String reportCode) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param page
	 * @param searchMap
	 * @return
	 * @author machuan
	 * @date  2017年1月6日
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ErpBaseEmpInfo> findBaseEmpInfo(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from ErpBaseEmpInfo where 1=1 ");
		List<ErpBaseEmpInfo> valueList = new ArrayList<ErpBaseEmpInfo>();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}

	/**
	 * @param baseInfoIds
	 * @return
	 * @author machuan
	 * @date  2017年1月9日
	 */
	public List<ErpBaseEmpInfo> getBaseInfoByIds(String baseInfoIds) {
		String []arrId = baseInfoIds.split(",");
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String sql = " select * from  erp_base_emp_info where id in "+ids;
		BeanPropertyRowMapper<ErpBaseEmpInfo> rowMapper = new BeanPropertyRowMapper<ErpBaseEmpInfo>(ErpBaseEmpInfo.class);	
		return this.getJdbcTemplate().query(sql, rowMapper);
	}

	/**
	 * 根据批次号，客户姓名，客户条形码判断数据是否匹配
	 * @param batchNo
	 * @param name
	 * @param code
	 * @author machuan
	 * @date  2017年2月24日
	 */
	public boolean isMatchingByCustomer(String batchNo, String name, String code) {
		String sql = "select count(*) from erp_events e "
					+"left join erp_customer c on e.events_no = c.events_no " 
					+"where e.is_deleted=0  and c.is_deleted=0 and e.batchno=? and c.name=? and c.code=?";
		return this.getJdbcTemplate().queryForInt(sql, batchNo,name,code)<1;
	}
}
