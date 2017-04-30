package org.hpin.reportdetail.service;

import java.util.List;

import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.BeanUtils;
import org.hpin.reportdetail.dao.ErpReportdetailDao;
import org.hpin.reportdetail.entity.ErpReportdetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "org.hpin.reportdetail.service.ErpReportdetailService")
@Transactional()
public class ErpReportdetailService extends BaseService {
	@Autowired
	ErpReportdetailDao dao;
	
	/*非物理删除*/
	public void deleteInfo(List<ErpReportdetail> list) {
		for(ErpReportdetail c:list){
			dao.update(c);
		}
	}
	
	/**
	 * 更新
	 * @param reportdetail
	 */
	public void updateInfo(ErpReportdetail reportdetail){
		ErpReportdetail _reportdetail = (ErpReportdetail) dao.findById(ErpReportdetail.class, reportdetail.getId());
		BeanUtils.copyProperties(_reportdetail, reportdetail);
		dao.update(_reportdetail);
	}
	
	/**
	 * 保存
	 * @param reportdetail
	 */
	public void save(ErpReportdetail reportdetail){
		dao.save(reportdetail);
	}
	/**
	 * 获取全部
	 * @return
	 */
	public List<ErpReportdetail> getAll(){
		return dao.getAll();
	}
	/**
	 *  工作表保存到数据库表
	 * @param obj
	 */
	public void sheetToTable(Object obj[]){
		dao.sheetToTable(obj);
	}
	
}
