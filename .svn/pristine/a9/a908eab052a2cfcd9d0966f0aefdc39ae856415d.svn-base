package org.hpin.reportdetail.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpReportdetailManagement;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpReportPrintManagementDao extends BaseDao {
	
	public void findByPages(Page<ErpReportdetailManagement> page, Map<String,Object> searchMap) {
	    
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select eptc.batchno batchNo, eptc.ID id, eptc.code code,eptc.name name,eptc.gender gender,eptc.createtime createTime ");
		sql.append("from erp_reportdetail_management eptc where state='1' ");
		if(!searchMap.isEmpty()){
			System.out.println(searchMap.get("filter_and_name_LIKE_S"));
			if(searchMap.get("filter_and_name_LIKE_S")!=null){
				sql.append("and eptc.name like '%"+searchMap.get("filter_and_name_LIKE_S")+"%' ");
			}
			if(searchMap.get("filter_and_code_LIKE_S")!=null){
				sql.append("and eptc.code like '%"+searchMap.get("filter_and_code_LIKE_S")+"%' ");
			}
		}
		sql.append("order by createtime,id desc ");
		page.setTotalCount(this.findJdbcCount(sql, params));
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpReportdetailManagement> rowMapper = new BeanPropertyRowMapper<ErpReportdetailManagement>(ErpReportdetailManagement.class);
		page.setResults(this.findJdbcList(sql, params, rowMapper));
		
	}
}
