package cn.yuanmeng.labelprint.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.yuanmeng.labelprint.dao.BaseDao;
import cn.yuanmeng.labelprint.dao.LabelPrintDao;
import cn.yuanmeng.labelprint.entity.LabelPrint;

public class LabelPrintDaoImpl extends BaseDao implements LabelPrintDao{
	public int sheetToTable(Object obj[]){
		int n=0;
		String sql="insert into labelprint(id,code,name,sex,age,phone,idNumber,enterprise,saleMan,batch,dueDate,familyHistory,note,dwellTime,baseDetection) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		n=this.updateOrInsert(sql, obj);
		return n;
	}

	@Override
	public int add(LabelPrint lp) {
		// TODO Auto-generated method stub
		int n=0;
		String sql="insert into labelprint(id,code,name,sex,age,phone,idNumber,enterprise,saleMan,batch,dueDate,familyHistory,note,dwellTime,baseDetection) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object []parameter={};
		con=getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, lp.getId());
			pstmt.setString(2, lp.getCode());
			pstmt.setString(3, lp.getName());
			pstmt.setString(4, lp.getSex());
			pstmt.setString(5, lp.getAge());
			pstmt.setString(6, lp.getPhone());
			pstmt.setString(7, lp.getIdNumber());
			pstmt.setString(8, lp.getEnterprise());
			pstmt.setString(9, lp.getSaleMan());
			pstmt.setString(10, lp.getBatch());
			pstmt.setString(11, lp.getDueDate());
			pstmt.setString(12, lp.getFamilyHistory());
			pstmt.setString(13, lp.getNote());
			pstmt.setString(14, lp.getDwellTime());
			pstmt.setString(15, lp.getBaseDetection());
			n=pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return n;
	}

	@Override
	public List<LabelPrint> getAll() {
		// TODO Auto-generated method stub
				String sql = "select * from labelprint order by code asc";
				List list = new ArrayList();
				con = getConnection();
				try {
					pstmt = con.prepareStatement(sql);
					res = pstmt.executeQuery();
					while (res.next()) {
						LabelPrint lp=new LabelPrint();
						lp.setAge(res.getString("age"));
						lp.setBaseDetection(res.getString("baseDetection"));
						lp.setBatch(res.getString("batch"));
						lp.setCode(res.getString("code"));
						lp.setDueDate(res.getString("dueDate"));
						lp.setDwellTime(res.getString("dwellTime"));
						lp.setEnterprise(res.getString("enterprise"));
						lp.setFamilyHistory(res.getString("familyHistory"));
						lp.setId(res.getString("id"));
						lp.setIdNumber(res.getString("idNumber"));
						lp.setName(res.getString("name"));
						lp.setNote(res.getString("note"));
						lp.setPhone(res.getString("phone"));
						lp.setSaleMan(res.getString("saleMan"));
						lp.setSex(res.getString("sex"));
						list.add(lp);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					closeAll();
				}
				return list;
	}
}
