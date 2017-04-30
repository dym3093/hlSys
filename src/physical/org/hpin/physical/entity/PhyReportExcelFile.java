package org.hpin.physical.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 读取ftp 1+X excel文件实体类
 * @author tangxing
 * @date 2016-12-6上午11:11:16
 */
public class PhyReportExcelFile extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String fileName;	//文件名
	
	private String filePath;	//文件路径
	
	private Date createTime;	//创建时间
	
	private String num;		//该Excel人数
	
	/** 状态（0：excel有误；1：excel正确） **/
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "PhyReportExcelFile [id=" + id + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", createTime=" + createTime
				+ ", num=" + num + ", status=" + status + "]";
	}
	
}
