package org.hpin.reportdetail.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.reportdetail.entityVO.ErpReportCustomerVO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReportPassBackShenyouDao  extends BaseDao {

	/**
	 * 通过查询条件获取对应的数据,
	 * 此处使用map便于后面传参转换为json字符串;
	 * <p>Description: </p>
	 * @author herny.xu
	 * @date 2017年3月15日
	 */
	public List<ErpReportCustomerVO> findYesterdayDataByCondition() {
		String sql = "select " +
			"report.REPORT_ID reportId, 'beijingyuanmeng' account, 'shenyou' password " +
			"from erp_report_customer_info report " +
			"inner join erp_customer cus on cus.code = report.code and cus.name = report.name " +
			"where  " +
			"cus.is_deleted = 0 " +
			"and cus.PDFFILEPATH is not null  " +
			"and report.REPORT_ID is not null and report.is_success != 2 " +
			"and report.create_time <= to_date(to_char(sysdate, 'yyyy-MM-dd HH24'), 'yyyy-MM-dd HH24') and rownum <= 1400 ";
		List<ErpReportCustomerVO> list = null;
		try {
			BeanPropertyRowMapper<ErpReportCustomerVO> rowMapper = new BeanPropertyRowMapper<ErpReportCustomerVO>(ErpReportCustomerVO.class);
			list = this.getJdbcTemplate().query(sql, rowMapper);
		} catch(Exception e) {
			list = null;
		}
		
		return list;
	}
	
	/**
	 * 修改状态
	 * <p>Description: </p>
	 * @author herny.xu
	 * @date 2017年3月16日
	 */
	public void updateReportCustomerState(List<ErpReportCustomerVO> list, int state) {
		
		if(list != null && list.size() > 0) {
			String [] updateSql = new String[list.size()];
			int i = 0; 
			for(ErpReportCustomerVO vo : list) {
				updateSql[i++] = " update erp_report_customer_info set is_success = "+state+", UPDATE_TIME=sysdate, UPDATE_USER_ID='-1' where report_id = '"+vo.getReportId()+"' ";
			}
			this.getJdbcTemplate().batchUpdate(updateSql);
		}
		
		
	}
}
