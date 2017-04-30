package org.hpin.reportdetail.dao;

import java.util.ArrayList;
import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpReportdetailImginfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpReportdetailImginfoDao extends BaseDao{

	/**
	 * @author Carly
	 * @since 2017年4月17日11:43:09
	 * @param page
	 * @param taskId
	 * @return 图片信息
	 */
	public void getImgPathList(Page<ErpReportdetailImginfo> page, String taskId) {
		StringBuilder sql = new StringBuilder("select imgPath from erp_reportdetail_imginfo where isdeleted=0 ");
		sql.append("and taskid='").append(taskId).append("' ").append("order by imgorder");
		List<Object> params = new ArrayList<Object>();
		page.setTotalCount(this.findJdbcCount(sql, params));
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpReportdetailImginfo> rowMapper = new BeanPropertyRowMapper<ErpReportdetailImginfo>(ErpReportdetailImginfo.class);
		page.setResults(this.findJdbcList(sql, params, rowMapper));
	} 

	
	
}
