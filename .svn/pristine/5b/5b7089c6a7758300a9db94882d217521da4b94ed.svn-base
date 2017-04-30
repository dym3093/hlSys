package org.hpin.reportdetail.dao;

import java.util.List;

import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.reportdetail.entity.ErpReportUnMatch;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpReportUnMatchDao extends BaseDao {
	
	public void save(ErpReportUnMatch matchBean){
		this.getHibernateTemplate().save(matchBean);
	}
	
	public void save(List<ErpReportUnMatch> matchBeanList){
		this.getHibernateTemplate().saveOrUpdateAll(matchBeanList);
	}

	public List<ErpReportUnMatch> queryHasDealUnMatch(){
		String hql = "from ErpReportUnMatch where modifystate=2";
		return this.getHibernateTemplate().find(hql);
	}
	
	public List<ErpReportUnMatch> queryHasDealUnMatch(String pdfids){
		String sql = "select * from ERP_REPORTDETAIL_UNMATCHINFO where modifystate=2 and pdfid in ("+pdfids+")";
		return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(ErpReportUnMatch.class));
	}
	
	public void update2Matched(String id){
		String sql = "update ERP_REPORTDETAIL_UNMATCHINFO set modifystate=4 where id='"+id+"'";
		this.getJdbcTemplate().update(sql);
	}

	public void unMatchAgain(String id) {
		String sql = "update ERP_REPORTDETAIL_UNMATCHINFO set modifystate=2 where id='"+id+"'";
		this.getJdbcTemplate().update(sql);
	}
	
	public List<ErpReportUnMatch> findUnMatchByPdfId(String pdfid){
		String hql = "from ErpReportUnMatch where pdfid=?";
		return this.getHibernateTemplate().find(hql,pdfid);
	}

	/**
	 * @since 2017年3月2日11:35:06
	 * @author Carly
	 * @param pdfId
	 * @param pdfCombo
	 */
	public void updatePdfContent(String pdfId, String pdfCombo,String pdfCode,String pdfUserName,String pdfUserSex) {
		String sql = "update erp_reportdetail_pdfcontent set setmeal_name=?,code=?,username=?,sex=? where id=?";
		this.getJdbcTemplate().update(sql,pdfCombo,pdfCode,pdfUserName,pdfUserSex,pdfId);
		
	}

	/**
	 * @param user 客户
	 * @param state 转台 
	 * @param unMatchId
	 */
	public void updateState(User user, String state, String unMatchId) {
		String sql = "update erp_reportdetail_unmatchinfo set MODIFYSTATE=?, OPERATORNAME=?, OPERATORDATE=sysdate where id=?";
		this.getJdbcTemplate().update(sql, state, user.getAccountName(), unMatchId);
	}
}
