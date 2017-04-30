package org.hpin.reportdetail.service;

import java.util.List;

import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.BeanUtils;
import org.hpin.reportdetail.dao.ErpReportdetailPDFDao;
import org.hpin.reportdetail.entity.ErpReportdetailPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "org.hpin.reportdetail.service.ErpReportdetailPDFService")
@Transactional()
public class ErpReportdetailPDFService extends BaseService {
	@Autowired
	ErpReportdetailPDFDao dao;
	
	/*非物理删除*/
	public void deleteInfo(List<ErpReportdetailPDF> list) {
		for(ErpReportdetailPDF c:list){
			dao.update(c);
		}
	}
	
	/**
	 * 更新
	 * @param reportdetail
	 */
	public void updateInfo(ErpReportdetailPDF reportdetail){
		ErpReportdetailPDF _reportdetail = (ErpReportdetailPDF) dao.findById(ErpReportdetailPDF.class, reportdetail.getId());
		BeanUtils.copyProperties(_reportdetail, reportdetail);
		dao.update(_reportdetail);
	}
	
	/**
	 * 保存
	 * @param reportdetail
	 */
	public void save(ErpReportdetailPDF reportdetail){
		dao.save(reportdetail);
	}
	/**
	 * 获取全部
	 * @return
	 */
	public List<ErpReportdetailPDF> getAll(){
		return dao.getAll();
	}
	/**
	 *  工作表保存到数据库表
	 * @param obj
	 */
	public void sheetToTable(Object obj[]){
		dao.pdfToTable(obj);
	}
	
}
