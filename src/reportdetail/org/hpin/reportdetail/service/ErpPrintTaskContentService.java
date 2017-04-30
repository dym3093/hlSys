/**
 * @author DengYouming
 * @since 2016-8-16 下午6:30:02
 */
package org.hpin.reportdetail.service;

import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.reportdetail.dao.ErpPrintTaskContentDao;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author Carly
 * @since 2016年10月8日15:14:59
 */
@Service("org.hpin.reportdetail.service.ErpPrintTaskContentService")
@Transactional()
public class ErpPrintTaskContentService {

	@Autowired
	private ErpPrintTaskContentDao dao;
	
	/**
	 * @param taskContent
	 * @throws Exception
	 * @author Carly
	 * @2016年10月8日15:14:13
	 */
	public void save(ErpPrintTaskContent taskContent) throws Exception{
		dao.save(taskContent);
	}
	
	/**
	 * @param obj
	 * @throws Exception
	 * @author Carly
	 * @since 2016年10月8日15:14:29
	 */
	public void update(ErpPrintTaskContent obj) throws Exception{
		dao.update(obj);
	}

	/**
	 * @param code
	 * @throws Exception
	 * 通过条形码查找客户信息
	 */
	public List<ErpCustomer> getCustomerInfoByCode(String code) throws Exception{
		return dao.getCustomerInfoByCode(code);
	}

	/**
	 * @param eventsNo
	 * @return
	 * 通过场次号获取项目编码
	 */
	public CustomerRelationShipPro getProjectCodeByEvent(String eventsNo) throws Exception{
		return dao.getProjectCodeByEvent(eventsNo);
	}

	/**
	 * @param pdfId
	 * 通过PdfContentId更新路径
	 */
	public List<ErpPrintTaskContent> getContentByPdfId(String pdfId) throws Exception{
		return dao.getContentByPdfId(pdfId);
	}
}
