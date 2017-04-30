package org.hpin.base.accessories.dao ;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hpin.base.accessories.entity.TawAttachment;
import org.hpin.common.db.datasource.ConnectionPool;
import org.hpin.common.db.datasource.HpinConnection;

/**
 * 附件管理Dao
 * <p>@desc : </p>
 * <p>@see : </p>
 *
 * <p>@author : sky</p>
 * <p>@createDate : Aug 29, 2012 6:07:42 PM</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p>
 */
public class TawAttachmentDAO {

	private ConnectionPool ds = null ;
	
	public TawAttachmentDAO() {
		super() ;
	}

	/**
	 * JDBC方式插入附件
	 * @param _name
	 * @param _filename
	 * @param _size
	 * @param _cruser
	 * @param _crtime
	 * @param _appId
	 * @return
	 * @throws SQLException
	 */
	public synchronized int insert(String _name , String _filename , int _size , String _cruser , String _crtime , int _appId) throws SQLException {
		String sql ;
		int attachmentId = 0 ;
		sql = "INSERT INTO taw_attachment (attachmentname, attachmentfilename,attachmentsize,cruser,crtime,appid) VALUES (?,?,?,?,?,?)" ;

		HpinConnection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;

		try {
			conn = ds.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setString(1 , _name) ;
			pstmt.setString(2 , _filename) ;
			pstmt.setInt(3 , _size) ;
			pstmt.setString(4 , _cruser) ;
			pstmt.setString(5 , _crtime) ;
			pstmt.setInt(6 , _appId) ;
			pstmt.executeUpdate() ;
			pstmt.close() ;
			conn.commit() ;
		} catch (SQLException sqle) {
			pstmt.close() ;
		}
		sql = "SELECT attachmentid FROM taw_attachment order by attachmentid desc" ;
		try {
			pstmt = conn.prepareStatement(sql) ;
			rs = pstmt.executeQuery() ;

			if (rs.next()) {
				attachmentId = rs.getInt(1) ;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace() ;
		} finally {
			conn.close() ;
			return attachmentId ;
		}
	}

	/**
	 * 根据附件的ID，以JDBC方式删除附件
	 * @param _attachmentId
	 * @throws SQLException
	 */
	public void delete(int _attachmentId) throws SQLException {
		HpinConnection conn = null ;
		conn = ds.getConnection() ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;

		String sql = "delete from taw_attachment where attachmentid = ?" ;
		try {
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setInt(1 , _attachmentId) ;
			pstmt.executeUpdate() ;
			conn.commit() ;
		} catch (SQLException sqle) {
			sqle.printStackTrace() ;
		} finally {
			pstmt.close() ;
			conn.close() ;
		}
	}

	/**
	 * 根据id拼接而成的字符串获取附件列表
	 * @param _idList
	 * @return
	 * @throws SQLException
	 */
	public List list(String _idList) throws SQLException {
		ArrayList list = new ArrayList() ;
		HpinConnection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		try {
			conn = ds.getConnection() ;
			String sql = "SELECT attachmentid,attachmentname, attachmentfilename, " + "attachmentsize,cruser,crtime,appid FROM taw_attachment " ;
			if (!_idList.equals("")) {
				sql += "where attachmentid in (" + _idList + ")" ;
			} else {
				sql += "where attachmentid in ('" + _idList + "')" ;
			}
			pstmt = conn.prepareStatement(sql) ;
			rs = pstmt.executeQuery() ;
			while(rs.next()) {
				TawAttachment tawAttachment = new TawAttachment() ;
				tawAttachment.setAttachmentId(rs.getInt("attachmentid")) ;
				tawAttachment.setAttachmentName(rs.getString("attachmentname")) ;
				tawAttachment.setAttachmentFilename(rs.getString("attachmentfilename")) ;
				tawAttachment.setSize(rs.getInt("attachmentsize")) ;
				tawAttachment.setCrtime(rs.getString("crtime")) ;
				tawAttachment.setCruser(rs.getString("cruser")) ;
				tawAttachment.setAppId(rs.getInt("appid")) ;

				list.add(tawAttachment) ;
				tawAttachment = null ;
			}
			rs.close() ;
			pstmt.close() ;
		} catch (SQLException e) {
			rs.close() ;
			pstmt.close() ;
			e.printStackTrace() ;
		} finally {
			conn.close() ;
		}
		return list ;
	}

	/**
	 * 根据id获取附件
	 * @param _attid
	 * @return
	 * @throws SQLException
	 */
	public TawAttachment retrieve(String _attid) throws SQLException {
		HpinConnection conn = null ;
		PreparedStatement pstmt = null ;
		TawAttachment tawAttachment = null ;
		ResultSet rs = null ;
		try {
			conn = ds.getConnection() ;
			String sql = "SELECT attachmentid,attachmentname, attachmentfilename, " + "attachmentsize,cruser,crtime,appid FROM taw_attachment " + "where attachmentid = " + _attid ;
			pstmt = conn.prepareStatement(sql) ;
			rs = pstmt.executeQuery() ;
			if (rs.next()) {
				tawAttachment = new TawAttachment() ;
				tawAttachment.setAttachmentId(rs.getInt("attachmentid")) ;
				tawAttachment.setAttachmentName(rs.getString("attachmentname")) ;
				tawAttachment.setAttachmentFilename(rs.getString("attachmentfilename")) ;
				tawAttachment.setSize(rs.getInt("attachmentsize")) ;
				tawAttachment.setCrtime(rs.getString("crtime")) ;
				tawAttachment.setCruser(rs.getString("cruser")) ;
				tawAttachment.setAppId(rs.getInt("appid")) ;
			}
			rs.close() ;
			pstmt.close() ;
		} catch (SQLException e) {
			rs.close() ;
			pstmt.close() ;
			e.printStackTrace() ;
		} finally {
			conn.close() ;
		}
		return tawAttachment ;
	}

}
