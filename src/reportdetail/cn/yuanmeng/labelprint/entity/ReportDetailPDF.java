package cn.yuanmeng.labelprint.entity;

import java.io.Serializable;

public class ReportDetailPDF implements Serializable {
	//序号
	private String id;
	//批次号
	private String batchno;
	//pdf文件路径
	private String filepath;
	//是否匹配
	private String ismatch;
	private String filename;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public ReportDetailPDF(){}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getIsmatch() {
		return ismatch;
	}
	public void setIsmatch(String ismatch) {
		this.ismatch = ismatch;
	}
	
	

}
