package org.hpin.reportdetail.service;


import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.dao.ErpReportdetailImginfoDao;
import org.hpin.reportdetail.dao.ErpReportdetailImgtaskDao;
import org.hpin.reportdetail.entity.ErpReportdetailImginfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.reportdetail.service.ErpReportdetailImginfoService")
@Transactional()
public class ErpReportdetailImginfoService extends BaseService {

	@Autowired
	ErpReportdetailImgtaskDao dao;
	@Autowired
	ErpReportdetailImginfoDao infoDao;
	
	/**
	 * @param page
	 * @param taskId 
	 * @return 图片路径
	 */
	public void getImgPathList(Page<ErpReportdetailImginfo> page, String taskId) {
		
		infoDao.getImgPathList(page, taskId);
	}
	
	
}
