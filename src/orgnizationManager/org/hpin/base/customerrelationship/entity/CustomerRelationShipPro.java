package org.hpin.base.customerrelationship.entity;

import java.util.Date;
import java.util.List;

import org.ymjy.combo.entity.Combo;

public class CustomerRelationShipPro {
	private String id; //主键ID
	private String customerRelationShipId; //支公司ID
	private String projectCode; //项目编码
	private String projectName; //项目名称
	private String projectOwner; //项目负责人
	private String projectType;  //项目类型(癌筛/基因)
	private String linkName; //远盟链接人
	private String linkTel; //远盟链接人电话
	private String remark;  //记录
	private String mailAddress; //邮件地址
	private String reception; //收件人
	private String receptionTel; //收件人电话
	private Date createTime; //创建日期;
	private String createUserId; //创建人id
	private String isDeleted; //是否删除;(0未删除,1删除)
	private Date deleteTime; //删除时间
	private String deleteUserId; //删除人ID
	private Date updateTime; //修改时间;
	private String updateUserId; //修改人;
	private String isSeal; //是否封存 0否, 1是
	private String batchPre; //批次号前缀; 如: HK
	
	private List<Combo> combos; //添加来处理套餐的.只是展示使用;
	
	public String getBatchPre() {
		return batchPre;
	}
	public void setBatchPre(String batchPre) {
		this.batchPre = batchPre;
	}
	public List<Combo> getCombos() {
		return combos;
	}
	public void setCombos(List<Combo> combos) {
		this.combos = combos;
	}
	public String getIsSeal() {
		return isSeal;
	}
	public void setIsSeal(String isSeal) {
		this.isSeal = isSeal;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerRelationShipId() {
		return customerRelationShipId;
	}
	public void setCustomerRelationShipId(String customerRelationShipId) {
		this.customerRelationShipId = customerRelationShipId;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectOwner() {
		return projectOwner;
	}
	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkTel() {
		return linkTel;
	}
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getReception() {
		return reception;
	}
	public void setReception(String reception) {
		this.reception = reception;
	}
	public String getReceptionTel() {
		return receptionTel;
	}
	public void setReceptionTel(String receptionTel) {
		this.receptionTel = receptionTel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	public String getDeleteUserId() {
		return deleteUserId;
	}
	public void setDeleteUserId(String deleteUserId) {
		this.deleteUserId = deleteUserId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	
	
	
	
}
