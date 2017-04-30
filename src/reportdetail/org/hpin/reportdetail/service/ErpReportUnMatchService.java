package org.hpin.reportdetail.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.orm.BaseService;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.reportdetail.dao.ErpReportUnMatchDao;
import org.hpin.reportdetail.dao.ErpReportdetailPDFContentDao;
import org.hpin.reportdetail.entity.ErpReportMatchBean;
import org.hpin.reportdetail.entity.ErpReportUnMatch;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.hpin.reportdetail.entity.PrintBatchInfoBean;
import org.hpin.reportdetail.job.ErpReportWillPrintJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.reportdetail.service.ErpReportUnMatchService")
@Transactional()
public class ErpReportUnMatchService extends BaseService {
	@Autowired
	ErpReportUnMatchDao dao;
	@Autowired
	ErpCustomerDao cusdao;
	@Autowired
	ErpReportdetailPDFContentDao contentdao;
	
	public void save(ErpReportUnMatch bean){
		dao.save(bean);
	}

	public void updateCustomerInfo(String name, String sex,String cusid) {
		String sql ="update ERP_CUSTOMER set name=?,sex=?where id=?";
		dao.getJdbcTemplate().update(sql, new Object[]{name,sex,cusid});
		
	}

	public void updatePdfContent(String name, String sex, String age,String pdfid) {
		String sql ="update ERP_REPORTDETAIL_PDFCONTENT set USERNAME=?,SEX=?,AGE=? where id=?";
		dao.getJdbcTemplate().update(sql, new Object[]{name,sex,age,pdfid});
	}

	public void updateUnMatchInfo(String unmatchid,User currentUser) {
		String sql="update erp_reportdetail_unmatchinfo set MODIFYSTATE='2',OPERATORNAME=?,OPERATORDATE=sysdate where id=?";
		dao.getJdbcTemplate().update(sql,new Object[]{currentUser.getAccountName(),unmatchid});
	}
	
	/**
	 * @since 2017年3月3日10:22:31
	 * @author Carly
	 * @param unmatchid
	 * @param currentUser
	 * @param pdfCombo 报告套餐
	 * @param pdfCode  报告条码
	 * @param pdfUserName 报告姓名
	 * @param pdfUserSex 报告性别
	 */
	public void updateUnMatchPdfError(String unmatchid,User currentUser, String pdfCombo, String pdfCode, String pdfUserName, String pdfUserSex) {
		String sql="update erp_reportdetail_unmatchinfo set MODIFYSTATE='3',OPERATORNAME=?,OPERATORDATE=sysdate,pdfcombo=?,pdfcode=?,pdfusername=?,pdfusersex=? where id=?";
		dao.getJdbcTemplate().update(sql,new Object[]{currentUser.getAccountName(),pdfCombo,pdfCode,pdfUserName,pdfUserSex,unmatchid});
	}
	
	public void updateUnMatchPdfError(String unmatchid,User currentUser) {
		String sql="update erp_reportdetail_unmatchinfo set MODIFYSTATE='3',OPERATORNAME=?,OPERATORDATE=sysdate where id=?";
		dao.getJdbcTemplate().update(sql,new Object[]{currentUser.getAccountName(),unmatchid});
	}

	public void dealUnMatch3InMatchInfo(List<ErpReportdetailPDFContent> contentMatch3) {
		List<ErpReportUnMatch> unMatchList = new ArrayList<ErpReportUnMatch>();
		for(ErpReportdetailPDFContent content : contentMatch3){
			ErpReportUnMatch unMatch = new ErpReportUnMatch();
			unMatch.setPdfid(content.getId());
			unMatch.setPdfcode(content.getCode());
			unMatch.setPdfusername(content.getUsername());
			unMatch.setPdfusersex(content.getSex());
			unMatch.setPdfuserage(content.getAge());
			unMatch.setPdffilepath(content.getFilepath());
			unMatch.setCreatedate(new Date());
			unMatch.setModifystate("0");
			unMatchList.add(unMatch);
		}
		if(0!=unMatchList.size()){
			dao.save(unMatchList);
		}
	}

	public void dealUnMatch4InMatchInfo(List<ErpReportdetailPDFContent> contentMatch4) {
		List<ErpReportUnMatch> unMatchList = new ArrayList<ErpReportUnMatch>();
		for(ErpReportdetailPDFContent content : contentMatch4){
			ErpCustomer customer = new ErpCustomer();
			if(null!=content.getCode()||("").equals(content.getCode())){
				List<ErpCustomer> cusList = cusdao.getCustomerByCode(content.getCode());
				customer = cusList.get(0);
			}
			ErpReportUnMatch unMatch = new ErpReportUnMatch();
			unMatch.setPdfid(content.getId());
			unMatch.setPdfcode(content.getCode());
			unMatch.setPdfusername(content.getUsername());
			unMatch.setPdfusersex(content.getSex());
			unMatch.setPdfuserage(content.getAge());
			unMatch.setPdffilepath(content.getFilepath());
			unMatch.setCreatedate(new Date());
			unMatch.setCuscode(customer.getCode());
			unMatch.setCususername(customer.getName());
			unMatch.setCusid(customer.getId());
			unMatch.setCususersex(customer.getSex());
			unMatch.setCusfilepath(customer.getPdffilepath());
			unMatch.setCususerage(customer.getAge());
			unMatch.setModifystate("1");
			unMatchList.add(unMatch);
		}
		if(0!=unMatchList.size()){
			dao.save(unMatchList);
		}
	}
	
	public List<ErpReportUnMatch> queryHasDealUnMatch(){
		return dao.queryHasDealUnMatch();
	}
	
	public List<ErpReportUnMatch> queryHasDealUnMatch(List<String> ids){
		StringBuffer sb = new StringBuffer();
		if(ids.size()>0){
			for(int i=0;i<ids.size();i++){
				if(i!=ids.size()-1){
					sb.append("'"+ids.get(i)+"',");
				}else{
					sb.append("'"+ids.get(i)+"'");
				}
			}
			return dao.queryHasDealUnMatch(sb.toString());
		}
		return null;
	}
	
	public void update2Matched(String id){
		dao.update2Matched(id);
	}
	
	public void unMatchAgain(String id){
		dao.unMatchAgain(id);
	}

	/**
	 * @since 2017年3月2日11:30:17
	 * @author Carly
	 * @param pdfId PDFContent的id
	 * @param pdfCombo 需要更新为的套餐
	 */
	public void updatePdfContent(String pdfId, String pdfCombo,String pdfCode,String pdfUserName,String pdfUserSex) {
		dao.updatePdfContent(pdfId,pdfCombo,pdfCode,pdfUserName,pdfUserSex);
	}
	
}
