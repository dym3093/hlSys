package org.hpin.reportdetail.entity;

import java.io.Serializable;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;
import org.hpin.statistics.briefness.web.QueryAction.ReportType;

/**
 * @author Carly
 * @since 2016年9月21日16:02:39
 * @return 需要重复的打印的，通过手动添加或者excel导入code从ErpPrintTaskContent表中查询
 */
public class ErpRepeatPrintTask extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 3868863064104226646L;
	private String id;
	private String userName;	//姓名
	private String age;			//年龄
	private String gender;		//性别
	private String code;		//条形码
	private String combo;		//套餐
	private String isPrint;		//是否打印，0未打印，1已打印
	private String batchNo;		//导入批次
	private Date createTime;	//创建时间
	private String createUser;
	private Date updateTime;
	private String updateUser;
	private String isDelete;
	private String isManuallyAdd;		//是否是通过excel导入：0不是，1是
	private String pdfContentId;	//pdfcontent表id
	private Integer reportType;		//报告类型 	0基因报告；1癌筛报告；2癌筛报告单;3：1+X报告；4无创
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCombo() {
		return combo;
	}
	public void setCombo(String combo) {
		this.combo = combo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getIsPrint() {
		return isPrint;
	}
	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getPdfContentId() {
		return pdfContentId;
	}
	public void setPdfContentId(String pdfContentId) {
		this.pdfContentId = pdfContentId;
	}
	public String getIsManuallyAdd() {
		return isManuallyAdd;
	}
	public void setIsManuallyAdd(String isManuallyAdd) {
		this.isManuallyAdd = isManuallyAdd;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	
	
}
