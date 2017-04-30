package org.hpin.reportdetail.dao;

import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.vo.ReportOverdueCustomerVO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReportOverdueWarnDao extends BaseDao{
	
	private static final String DELAYDAY = "3"; //延迟天数,默认为3天
	
	public List<ReportOverdueCustomerVO> findCustomerByWarnDay() {
		String sql = "select to_char(sampling_date, 'yyyy-MM-dd') samplingDate, code, name, " +
		"sex, age, idno, phone, BRANCH_COMPANY branchCompany, BRANCH_COMMANY deviceInCompany,  "+
		"SETMEAL_NAME comboName, SALES_MAN salesMan,SALES_MAN_NO salesManNo, HEIGHT height, WEIGHT weight, "+ 
		"EVENTS_NO eventsNo,BATCH_NO batchNo from ( "+
		"select  "+
		"to_date(to_char(sysdate, 'yyyy-MM-dd'), 'yyyy-MM-dd') - to_date(to_char(qrc.update_time, 'yyyy-MM-dd'), 'yyyy-MM-dd') tdate, "+ 
		"cus.sampling_date, cus.code, cus.name, cus.sex, cus.age, cus.idno, cus.PHONE, cus.BRANCH_COMPANY, ship.BRANCH_COMMANY, "+
		"cus.SETMEAL_NAME, cus.SALES_MAN,cus.SALES_MAN_NO, cus.HEIGHT, cus.WEIGHT, cus.EVENTS_NO, qrc.BATCH_NO "+
		"from erp_qrcode qrc  "+
		"left join erp_customer cus on cus.events_no = qrc.events_no "+
		"left join HL_CUSTOMER_RELATIONSHIP ship on ship.id = cus.OTHER_COMPANY_ID "+
		"where  "+
		"qrc.push_status = '1' "+ 
		"and qrc.update_time is not null "+
		"and cus.PDFFILEPATH is null "+
		"and cus.IS_DELETED = 0 "+ //查询有效的数据;
		"and cus.code like 'W%' "+
		") where tdate>="+DELAYDAY+ " "+
		"order by events_no desc";
		BeanPropertyRowMapper<ReportOverdueCustomerVO> rowMapper = new BeanPropertyRowMapper<ReportOverdueCustomerVO>(ReportOverdueCustomerVO.class);
		return this.getJdbcTemplate().query(sql, rowMapper);
	}
	
}
