package org.hpin.children.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author chenqi
 * @since 2016年6月23日09:52:10
 * 个体订单entity
 */
public class ErpOrderInfo extends BaseEntity  implements java.io.Serializable{

	/** 编号 */
	private String id;
	/** 订单号 */
	private String orderNo;
	/** 检测人姓名 */
	private String name;
	/** 身份证号 */
	private String idNo;
	/** 性别 */
	private String sex;
	/** 年龄 */
	private BigDecimal age;
	/** 检测套餐 */
	private String setMealName;
	/** 检测人电话 */
	private String phone;
	/** 采样盒接收地址 */
	private String sampleAddress;
	/** 报告接收地址 */
	private String reportAddress;
	/** 监护人姓名 */
	private String guardianName;
	/** 监护人手机 */
	private String guardianPhone;
	/** 关系 */
	private String relationship;
	/** 家庭病史 */
	private String familyHistory;
	/** 身高 */
	private String height;
	/** 体重 */
	private String weight;
	/** 备注 */
	private String note;
	/** 状态 */
	private String status;
	/** 创建时间 */
	private Date createDate;
	/** 是否删除（0：删除，1：使用）*/
	private String isDelete;
	/** 创建人 */
	private String createUser;
	/** 更新人 */
	private String updateUser;
	/** 更新时间 */
	private Date updateTime;
	/** 报告状态 */
	private String reportStatus;
	/** 条形码 */
	private String code;
	/** 寄采样盒日期  */
	private Date sampleDate;
	
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public BigDecimal getAge() {
		return age;
	}
	public void setAge(BigDecimal age) {
		this.age = age;
	}
	public String getSetMealName() {
		return setMealName;
	}
	public void setSetMealName(String setMealName) {
		this.setMealName = setMealName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSampleAddress() {
		return sampleAddress;
	}
	public void setSampleAddress(String sampleAddress) {
		this.sampleAddress = sampleAddress;
	}
	public String getReportAddress() {
		return reportAddress;
	}
	public void setReportAddress(String reportAddress) {
		this.reportAddress = reportAddress;
	}
	public String getGuardianName() {
		return guardianName;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public String getGuardianPhone() {
		return guardianPhone;
	}
	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getFamilyHistory() {
		return familyHistory;
	}
	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getSampleDate() {
		return sampleDate;
	}
	public void setSampleDate(Date sampleDate) {
		this.sampleDate = sampleDate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
}
