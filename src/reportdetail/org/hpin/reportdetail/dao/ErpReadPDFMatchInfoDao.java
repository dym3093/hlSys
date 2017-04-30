package org.hpin.reportdetail.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.reportdetail.entity.ErpReadPDFMatchInfo;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpReadPDFMatchInfoDao extends BaseDao {

	@Autowired
	private ErpReportdetailPDFContentDao pdfContentDao;
	
	@Autowired
	private ErpCustomerDao customerDao;
	
	/**
	 * erp_customer表中获取需要吉思朗数据
	 * @return
	 * @author tangxing
	 * @date 2017-3-8下午5:17:44
	 */
	public List<Map<String, Object>> getCustomerAndFilePath(){
		String sql = "select c.code,c.name,c.sex,c.age,rp.filepath "+
				" from erp_customer c,erp_reportdetail_pdfcontent rp  "+
				" where rp.customerid=c.id  "+
				" and c.code like 'C%'  "+
				" and c.code not like 'CS%'  "+
				" and c.is_deleted=0  "+
				" and c.branch_company<>'无创微磁支公司测试101'  "+
				" and rp.matchstate='2' and rp.filepath is not null  "+
				" and rp.createdate = (select max(p.createdate) from erp_reportdetail_pdfcontent p where p.code=rp.code)";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 获取ErpReadPDFMatchInfo集合
	 * @return
	 * @author tangxing
	 * @date 2017-3-8下午5:17:29
	 */
	public List<ErpReadPDFMatchInfo> listErpReadPDFMatchInfo() {
		String hql = "from ErpReadPDFMatchInfo where isReadPdf=0";
		return this.getHibernateTemplate().find(hql);
	}
	
	/**
	 * 更改PDFContent文件名
	 * 
	 */
	public void renameFilePdfContent(ErpReadPDFMatchInfo pdfMatchInfo,Integer type){
		ErpReportdetailPDFContent pdfContent = (ErpReportdetailPDFContent) pdfContentDao.findById(ErpReportdetailPDFContent.class, pdfMatchInfo.getId());
		if(null!=pdfContent){
			String sql = "update ErpReportdetailPDFContent set filepath=? where id=? and filepath is not null";
			String newFileName = dealFileName(pdfContent.getFilepath(),type);
			File pdfFile = new File(pdfContent.getFilepath());
			pdfFile.renameTo(new File(newFileName));
			this.getHibernateTemplate().bulkUpdate(sql,new Object[]{newFileName,pdfContent.getId()});
		}
		
	}
	/**
	 * 更改customer文件名
	 */
	public void renameFilePdfCustomer(ErpReadPDFMatchInfo pdfMatchInfo,Integer type){
		ErpReportdetailPDFContent pdfContent = (ErpReportdetailPDFContent) pdfContentDao.findById(ErpReportdetailPDFContent.class, pdfMatchInfo.getId());
		if(null!=pdfContent){
			ErpCustomer customer = (ErpCustomer) customerDao.findById(ErpCustomer.class, pdfContent.getCustomerid());
			if(null!=customer){
				String sql = "update ErpCustomer set pdffilepath=? where id=? and pdffilepath is not null";
				String newFileName = dealFileName(customer.getPdffilepath(),type);
				File pdfFile = new File(customer.getPdffilepath());
				pdfFile.renameTo(new File(newFileName));
				this.getHibernateTemplate().bulkUpdate(sql,new Object[]{newFileName,customer.getId()});
			}
		}
		
	}
	
	/**
	 * 处理文件名
	 */
	private String dealFileName(String fileName,Integer type){
		StringBuffer filePre = new StringBuffer(fileName.substring(0,fileName.lastIndexOf("/")+1));
		String typeName = "wrong_";
		if(2==type){
			typeName = "yes_";
		}
		return filePre.append(typeName).append(fileName.substring(fileName.lastIndexOf("/")+1)).toString();
	}
}
