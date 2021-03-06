package org.hpin.reportdetail.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.util.DBUtil;
import org.hpin.reportdetail.entity.ErpReportFileTask;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

@Repository()
@SuppressWarnings("unchecked")
public class ErpReportdetailPDFTaskDao extends BaseDao {
	
	public void save(ErpReportdetailPDFContent pdfContent){
		this.getHibernateTemplate().save(pdfContent);
	}
	
	public void save(List<ErpReportdetailPDFContent> pdfContentList){
		this.getHibernateTemplate().saveOrUpdateAll(pdfContentList);
	}
	
	public void compare() throws SQLException{
		SessionFactoryUtils.getDataSource(this.getHibernateTemplate().getSessionFactory()).getConnection().prepareCall("");
		DBUtil.makeConnection("dataSource");
	}
	
	public void save(ErpReportFileTask task){
		this.getHibernateTemplate().save(task);
	}
	
	public ErpReportFileTask queryTaskByBatchno(String batchno){
		String hql = "from ErpReportFileTask where batchno = ?";
		List<ErpReportFileTask> list = this.getHibernateTemplate().find(hql, batchno);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<ErpReportFileTask> queryAllTask(){
		String hql = "from ErpReportFileTask order by createdate desc";
		return this.getHibernateTemplate().find(hql);
	}
	
	//查询未去重的批次号
	public List<String> queryUnRepeatBatchno(){
		String hql="select batchno from ErpReportFileTask where isdisrepeat =0";
		return this.getHibernateTemplate().find(hql);
	}
	
	//获取未匹配会员信息的批次号
	public List<String> queryUnMatchBatchno(){
		String hql="select batchno from ErpReportFileTask where ismatch =0";
		return this.getHibernateTemplate().find(hql);
	}
	
	public int getNumUnRecord(String batchno) {
		String sql = "select count(*) from ERP_REPORTDETAIL_PDFCONTENT where batchno=? and isrecord=0";
		return this.getJdbcTemplate().queryForInt(sql,batchno);
	}

	public int getBthRepeatNum(String batchno, int i) {
		String sql = "select count(*) from ERP_REPORTDETAIL_PDFCONTENT where batchno=? and isrepeat=?";
		return this.getJdbcTemplate().queryForInt(sql,new Object[]{batchno,i});
	}

	public void updateTaskState(String batchno) {
		String taskHql = "update ERP_REPORTDETAIL_PDFTASK set companynum=?,updatenum=?,unmatchnum=?,abnormalnnum=?,cusmorenum=?,updatedate=?,ismatch=? where batchno = ?";
		this.getJdbcTemplate().update(taskHql,new Object[]{0,0,0,0,0,new Date(),1,batchno});
	}

	public int dealPdfAbnormal(String pdfid,int state){
		String sql="update ERP_REPORTDETAIL_PDFCONTENT set MATCHSTATE=?,ISRECORD=3 where id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{state,pdfid});
	}
	
	/**
	 * @author Carly
	 * @since 2017年3月23日18:19:21
	 * @param batchno 批次号
	 * @param matchState 匹配数字的数组
	 */
	public void updateFileTaskState(String batchno, int[] matchState) {
		String sql = "update ERP_REPORTDETAIL_PDFTASK set updatenum=?, unmatchnum=?, abnormalnnum=?, cusmorenum=?, noYMCombo=?,"
				+ "noCustomerCombo2SY=?, noSYcombo=?, errorPdfName=?, stopcombo=?, stopreport=?, ismatch=?, updatedate=? where batchno = ?";
		this.getJdbcTemplate().update(sql, matchState[0], matchState[1], matchState[2], matchState[3], matchState[4], 
				matchState[5], matchState[6], matchState[7], matchState[8], matchState[9], matchState[10], new Date(), batchno);
	}
	
}
