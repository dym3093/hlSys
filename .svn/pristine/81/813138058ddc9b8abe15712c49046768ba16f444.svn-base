package cn.yuanmeng.labelprint.dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	//private String url = "jdbc:oracle:thin:@192.168.1.50:1521:orcl";
	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";
	
//	private String url = "jdbc:oracle:thin:@192.168.1.15:1521:yuanmeng";
//	private String url = "jdbc:oracle:thin:@192.168.1.15:1521:yuanmeng";
//	private String url = "jdbc:oracle:thin:@192.168.1.17:1521:YUANMENG1";
	private String user = "YMJY";
	private String password = "YMJY";

	// String url = "jdbc:mysql://192.168.1.198:3306/javaweb";//
	// String user = "root";
	// String password = "healthlink";

	protected Connection con;
	protected PreparedStatement pstmt;
	protected ResultSet res;

	public Connection getConnection() {
		try {
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	public int updateOrInsert(String sql,Object []para){
		int n=0;
		con=this.getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			if(para!=null){
				for (int i = 0; i < para.length; i++) {
					pstmt.setObject(i+1, para[i]);
				}
			}
			n=pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return n;
	}
	public void closeAll() {
		try {
			if (res != null)
				res.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List querySql(String sql) throws SQLException {		
		con=this.getConnection();
		PreparedStatement ps = null;
		ResultSet rs=null;
		Map map=null;
		try {
			sql = sql.trim();
		    System.out.println("sql-------"+sql);
			ps = con.prepareStatement(sql);

		    rs = ps.executeQuery();
		    return getListByRs(rs, map);
		    
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
			 con.close();
		}

		return null;
	}
	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
	}

	protected void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pstmt = null;
		}
	}
	private List getListByRs(ResultSet rs, Map map) throws SQLException {
		List result = new ArrayList();
	    ResultSetMetaData rsmt=rs.getMetaData(); 
		int len=rsmt.getColumnCount(); 
		while(rs.next()){ 
		map= new HashMap(); 
		for(int i=1;i<=len;i++){ 
			map.put(rsmt.getColumnName(i).toLowerCase(), rs.getString(i)); 

		} 
		result.add(map); 
		} 

		return result;
	}
/*	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/monitor?useUnicode=true&characterEncoding=utf-8";
	private String user = "root";
	private String password = "orcl";

	// String url = "jdbc:mysql://192.168.1.198:3306/javaweb";//
	// String user = "root";
	// String password = "healthlink";

	protected Connection con;
	protected PreparedStatement pstmt;
	protected ResultSet res;

	public Connection getConnection() {
		try {
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	public int updateOrInsert(String sql,Object []para){
		int n=0;
		con=this.getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			if(para!=null){
				for (int i = 0; i < para.length; i++) {
					pstmt.setObject(i+1, para[i]);
				}
			}
			n=pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return n;
	}
	public void closeAll() {
		try {
			if (res != null)
				res.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	
}
