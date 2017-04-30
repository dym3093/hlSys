package cn.yuanmeng.labelprint.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.yuanmeng.labelprint.dao.BaseDao;
import cn.yuanmeng.labelprint.dao.ReportDetailDao;
import cn.yuanmeng.labelprint.entity.LabelPrint;
import cn.yuanmeng.labelprint.entity.ReportDetail;

public class ReportDetailDaoImpl extends BaseDao implements ReportDetailDao {

	@Override
	public List<ReportDetail> getAll() {
		String sql = "select * from  erp_reportdetail order by code asc";
		List<ReportDetail> list = new ArrayList<ReportDetail>();
		con = getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			res = pstmt.executeQuery();
			while (res.next()) {
				ReportDetail reportDetail=new ReportDetail();
				reportDetail.setAge(res.getString("age"));
				reportDetail.setBranchCompany(res.getString("branchCompany"));
				reportDetail.setCode(res.getString("code"));
				reportDetail.setCollectionDate(res.getString("collectionDate"));
				reportDetail.setEntering(res.getString("entering"));
				reportDetail.setId(res.getString("id"));
				reportDetail.setInstitution(res.getString("institution"));
				reportDetail.setName(res.getString("name"));
				reportDetail.setPage(res.getString("page"));
				reportDetail.setPhone(res.getString("phone"));
				reportDetail.setProject(res.getString("project"));
				reportDetail.setSalesMan(res.getString("salesMan"));
				reportDetail.setSampleType(res.getString("sampleType"));
				reportDetail.setSamplingDate(res.getString("samplingDate"));
				reportDetail.setSex(res.getString("sex"));
				reportDetail.setSimpleStatus(res.getString("simpleStatus"));
				list.add(reportDetail);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return list;
	}

	@Override
	public int add(ReportDetail reportDetail) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sheetToTable(Object[] obj) {
		// TODO Auto-generated method stub
		int n=0;                
		String sql="insert into ERP_REPORTDETAIL(id,xlsno,code,name,sex,age,phone,branchCompany,project,sampleType,salesMan,entering,institution,samplingDate,collectionDate,simpleStatus,page,batchno,filepath,ismatch) "
		+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,?,?)";
		n=this.updateOrInsert(sql, obj);
		return n;
	}

	@Override
	public int del() {
		// TODO Auto-generated method stub
		int n=0;
		String sql="delete from ERP_REPORTDETAIL";
		n=this.updateOrInsert(sql, null);
		return n;
	}
	
}
