package cn.yuanmeng.labelprint.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.yuanmeng.labelprint.dao.BaseDao;
import cn.yuanmeng.labelprint.dao.BatchFileColumnNameDao;

public class BatchFileColumnNameDaoImpl extends BaseDao implements
		BatchFileColumnNameDao {

	@Override
	public void add(Object[] obj) {
		// TODO Auto-generated method stub
		this.getConnection();
		String sql = "insert into batchFileColumnName(fileName,columnCount,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		this.updateOrInsert(sql, obj);
		this.closeAll();
	}

	@Override
	public List<List<String>> search() {
		// TODO Auto-generated method stub
		List<List<String>> list = new ArrayList<List<String>>();
		this.getConnection();
		String sql = "select * from batchFileColumnName order by columnCount,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20";
		try {
			pstmt = con.prepareStatement(sql);
			res = pstmt.executeQuery();
			while (res.next()) {
				List<String> record = new ArrayList<String>();
				record.add(res.getInt("id") + "");
				record.add(res.getString("fileName").substring(0,6));
				record.add(res.getString("columnCount"));
				record.add(res.getString("a1"));
				record.add(res.getString("a2"));
				record.add(res.getString("a3"));
				record.add(res.getString("a4"));
				record.add(res.getString("a5"));
				record.add(res.getString("a6"));
				record.add(res.getString("a7"));
				record.add(res.getString("a8"));
				record.add(res.getString("a9"));
				record.add(res.getString("a10"));
				record.add(res.getString("a11"));
				record.add(res.getString("a12"));
				record.add(res.getString("a13"));
				record.add(res.getString("a14"));
				record.add(res.getString("a15"));
				record.add(res.getString("a16"));
				record.add(res.getString("a17"));
				record.add(res.getString("a18"));
				record.add(res.getString("a19"));
				record.add(res.getString("a20"));
				list.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return list;
	}

}
