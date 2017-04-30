package org.hpin.events.entity;

import java.util.Date;

import javax.management.loading.PrivateClassLoader;

import org.hpin.common.core.orm.BaseEntity;

/**
 * ErpEvents entity. @author MyEclipse Persistence Tools
 */

public class ErpEvents extends BaseEntity implements java.io.Serializable {


	private String id;
	private String eventsNo;//场次号
	private String batchNo;//批次号
	private Date eventDate;//场次日期
	private String address;//地址
	private String branchCompany;//支公司
	private String branchCompanyId;//支公司ID
	private String ownedCompany;//所属公司
	private String ownedCompanyId;//总公司Id
	private String level2;//级别
	private String comboId;//默认套餐ID
	private String comboName;//默认套餐name
	private Integer headcount;//预计人数
	private Integer nowHeadcount;//现有人数
	private Integer isDeleted;//是否删除 0正常1删除2取消
	private Integer isExpress;//是否寄快递
	private String createUserName;//创建者
	private Date createTime;//创建时间
	private String updateUserName;//更新用户名
	private Date updateTime;//更新时间
//	private String businessMan;//业务员联系人
	private String ename;
	private String etrackingNumber;
	private Date edate;
	private String hour;//时间
	private String provice;
	private String city;
	private Integer pdfcount;//PDF人数
	private String ymSalesman;
	private Integer nopdfcount;//未出报告
	private Integer settNumbers;	//结算的人数
	private Double produceCost;	//产生的费用
	private String groupOrderNo;//团单号
	private String customerRelationShipProId; //HL_CUSTOMER_RELATIONSHIP_PRO的ID  create by henry.xu 20160919; 
	private Integer nurseNumber;	//护士人数 @since 2017年1月17日15:32:00 @author chenqi
	private Integer statusBX; //保险公司结算状态(0:未结算， 1：部分结算， 2：已全部结算) add by DengYouming 2016-07-15

	private String eventsType; //场次类别：说明会、启动会、解析会

	public static final String F_ID = "id";
	public static final String F_EVENTSNO = "eventsNo";
	public static final String F_BATCHNO = "batchNo";
	public static final String F_EVENTDATE = "eventDate";
	public static final String F_ADDRESS = "address";
	public static final String F_BRANCHCOMPANY = "branchCompany";
	public static final String F_BRANCHCOMPANYID = "branchCompanyId";
	public static final String F_OWNEDCOMPANY = "ownedCompany";
	public static final String F_OWNEDCOMPANYID = "ownedCompanyId";
	public static final String F_LEVEL2 = "level2";
	public static final String F_COMBOID = "comboId";
	public static final String F_COMBONAME = "comboName";
	public static final String F_HEADCOUNT = "headcount";
	public static final String F_NOWHEADCOUNT = "nowHeadcount";
	public static final String F_ISDELETED = "isDeleted";
	public static final String F_ISEXPRESS = "isExpress";
	public static final String F_CREATEUSERNAME = "createUserName";
	public static final String F_CREATETIME = "createTime";
	public static final String F_UPDATEUSERNAME = "updateUserName";
	public static final String F_UPDATETIME = "updateTime";
	public static final String F_ENAME = "ename";
	public static final String F_ETRACKINGNUMBER = "etrackingNumber";
	public static final String F_EDATE = "edate";
	public static final String F_HOUR = "hour";
	public static final String F_PROVICE = "provice";
	public static final String F_CITY = "city";
	public static final String F_PDFCOUNT = "pdfcount";
	public static final String F_YMSALESMAN = "ymSalesman";
	public static final String F_NOPDFCOUNT = "nopdfcount";
	public static final String F_CUSTOMERRELATIONSHIPPROID = "customerRelationShipProId";
	
	public static final String F_STATUSBX = "statusBX";

	public static final String F_EVENTSTYPE = "eventsType";

	public String getCustomerRelationShipProId() {
		return customerRelationShipProId;
	}

	public void setCustomerRelationShipProId(String customerRelationShipProId) {
		this.customerRelationShipProId = customerRelationShipProId;
	}

	public Integer getNopdfcount() {
		return nopdfcount;
	}

	public void setNopdfcount(Integer nopdfcount) {
		this.nopdfcount = nopdfcount;
	}

	public String getYmSalesman() {
		return ymSalesman;
	}

	public void setYmSalesman(String ymSalesman) {
		this.ymSalesman = ymSalesman;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getPdfcount() {
		return pdfcount;
	}

	public void setPdfcount(Integer pdfcount) {
		this.pdfcount = pdfcount;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEventsNo() {
		return this.eventsNo;
	}

	public void setEventsNo(String eventsNo) {
		this.eventsNo = eventsNo;
	}

	public Date getEventDate() {
		
		return this.eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBranchCompany() {
		return branchCompany;
	}

	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}

	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public Integer getHeadcount() {
		return headcount;
	}

	public void setHeadcount(Integer headcount) {
		this.headcount = headcount;
	}

	public Integer getNowHeadcount() {
		return nowHeadcount;
	}

	public void setNowHeadcount(Integer nowHeadcount) {
		this.nowHeadcount = nowHeadcount;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOwnedCompany() {
		return ownedCompany;
	}

	public void setOwnedCompany(String ownedCompany) {
		this.ownedCompany = ownedCompany;
	}

	public Integer getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(Integer isExpress) {
		this.isExpress = isExpress;
	}
	//快递信息

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getEtrackingNumber() {
		return etrackingNumber;
	}

	public void setEtrackingNumber(String etrackingNumber) {
		this.etrackingNumber = etrackingNumber;
	}

	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public String getOwnedCompanyId() {
		return ownedCompanyId;
	}

	public void setOwnedCompanyId(String ownedCompanyId) {
		this.ownedCompanyId = ownedCompanyId;
	}
	
	public Integer getSettNumbers() {
		return settNumbers;
	}

	public void setSettNumbers(Integer settNumbers) {
		this.settNumbers = settNumbers;
	}
	
	public Double getProduceCost() {
		return produceCost;
	}

	public void setProduceCost(Double produceCost) {
		this.produceCost = produceCost;
	}

	public Integer getStatusBX() {
		return statusBX;
	}

	public void setStatusBX(Integer statusBX) {
		this.statusBX = statusBX;
	}

	public String getGroupOrderNo() {
		return groupOrderNo;
	}

	public void setGroupOrderNo(String groupOrderNo) {
		this.groupOrderNo = groupOrderNo;
	}

	public Integer getNurseNumber() {
		return nurseNumber;
	}

	public void setNurseNumber(Integer nurseNumber) {
		this.nurseNumber = nurseNumber;
	}

	@Override
	public String toString() {
		return "ErpEvents [id=" + id + ", eventsNo=" + eventsNo + ", batchNo="
				+ batchNo + ", eventDate=" + eventDate + ", address=" + address
				+ ", branchCompany=" + branchCompany + ", branchCompanyId="
				+ branchCompanyId + ", ownedCompany=" + ownedCompany
				+ ", ownedCompanyId=" + ownedCompanyId + ", level2=" + level2
				+ ", comboId=" + comboId + ", comboName=" + comboName
				+ ", headcount=" + headcount + ", nowHeadcount=" + nowHeadcount
				+ ", isDeleted=" + isDeleted + ", isExpress=" + isExpress
				+ ", createUserName=" + createUserName + ", createTime="
				+ createTime + ", updateUserName=" + updateUserName
				+ ", updateTime=" + updateTime + ", ename=" + ename
				+ ", etrackingNumber=" + etrackingNumber + ", edate=" + edate
				+ ", hour=" + hour + ", provice=" + provice + ", city=" + city
				+ ", pdfcount=" + pdfcount + ", ymSalesman=" + ymSalesman
				+ ", nopdfcount=" + nopdfcount + ", settNumbers=" + settNumbers
				+ ", produceCost=" + produceCost + ", groupOrderNo="
				+ groupOrderNo + ", customerRelationShipProId="
				+ customerRelationShipProId + ", nurseNumber=" + nurseNumber
				+ ", statusBX=" + statusBX + "]";
	}

	public String getEventsType() {
		return eventsType;
	}

	public void setEventsType(String eventsType) {
		this.eventsType = eventsType;
	}
}