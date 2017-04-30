package org.hpin.warehouse.entity;

import java.text.ParseException;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;
import org.hpin.common.util.DateUtil;

/**
 * 
 * @description: 基因物品申请实体类;
 * create by henry.xu 2016年10月10日
 */
public class ErpApplication extends BaseEntity{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6895122832818227275L;
	/**主键ID*/
	private String id;
	/**申请单号*/
	private String applicationNo;
	/**支公司ID*/
	private String bannyCompanyId;
	private String bannyCompanyName; //支公司名称;
	
	private String ownerCompanyId; //总公司Id;
	private String ownerCompanyName; //总公司名称;
	
	/**项目编码*/
	private String projectCode;
	/**项目名称*/
	private String projectName;
	/**项目负责人*/
	private String projectOwner;
	/**收件人姓名*/
	private String receiveName;
	/**收件人电话*/
	private String receiveTel;
	/**寄送地址*/
	private String address;
	/**期望日期*/
	private Date hopeDate;
	
	/**需求说明*/
	private String requirement;
	/**状态：0待发货 1部分发货 2已发货*/
	private String status;
	/**附件（特指销售提交申请单时，系统发给基因业务部负责人的邮件中的附件：申请产品的明细表）*/
	private String attachmentPath;
	/**远盟对接人*/
	private String linkName;
	/**对接人电话*/
	private String linkTel;
	/**处理人ID*/
	private String dealUserId;
	
	private String dealUserName; //处理人名字;
	private String proviceName; //省份名称
	private String cityName; //城市名称;
	private String createUserName; //申请人;
	private String isMark; //表示(0远盟, 1阳光);
	
	public String getIsMark() {
		return isMark;
	}
	public void setIsMark(String isMark) {
		this.isMark = isMark;
	}
	public String getDealUserName() {
		return dealUserName;
	}
	public void setDealUserName(String dealUserName) {
		this.dealUserName = dealUserName;
	}
	public String getDealUserId() {
		return dealUserId;
	}
	public void setDealUserId(String dealUserId) {
		this.dealUserId = dealUserId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getBannyCompanyId() {
		return bannyCompanyId;
	}
	public void setBannyCompanyId(String bannyCompanyId) {
		this.bannyCompanyId = bannyCompanyId;
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
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceiveTel() {
		return receiveTel;
	}
	public void setReceiveTel(String receiveTel) {
		this.receiveTel = receiveTel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getHopeDate() {
		return hopeDate;
	}
	public void setHopeDate(Date hopeDate) {
		this.hopeDate = hopeDate;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
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
	public String getBannyCompanyName() {
		return bannyCompanyName;
	}
	public void setBannyCompanyName(String bannyCompanyName) {
		this.bannyCompanyName = bannyCompanyName;
	}
	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}
	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}
	public String getOwnerCompanyName() {
		return ownerCompanyName;
	}
	public void setOwnerCompanyName(String ownerCompanyName) {
		this.ownerCompanyName = ownerCompanyName;
	}
	public String getProviceName() {
		return proviceName;
	}
	public void setProviceName(String proviceName) {
		this.proviceName = proviceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 日期格式化参数处理;
	 * @param hopeDateBak
	 */
	public void setHopeDateBak(String hopeDateBak) {
		try {
			this.hopeDate = DateUtil.convertStringToDate(hopeDateBak);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	
}
