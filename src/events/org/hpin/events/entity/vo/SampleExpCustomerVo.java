package org.hpin.events.entity.vo;

import java.util.Date;

/**
 * 样本快递费用管理展示客户信息VO
 * @author ybc
 * @since 2016/12/15
 */
public class SampleExpCustomerVo{
	
	private String id;
	
	private String batchno;

	private String eventsNo;
	
	private Date eventsDate;
	
	private String code;
	
	private String name;
	
	private String sex;
	
	private String age;
	
	private String setmealName;
	
	private String branchCompanyId;
	
	private String ownedCompanyId;
	
	private String customerId;
	
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

	public String getEventsNo() {
		return eventsNo;
	}

	public void setEventsNo(String eventsNo) {
		this.eventsNo = eventsNo;
	}

	public Date getEventsDate() {
		return eventsDate;
	}

	public void setEventsDate(Date eventsDate) {
		this.eventsDate = eventsDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSetmealName() {
		return setmealName;
	}

	public void setSetmealName(String setmealName) {
		this.setmealName = setmealName;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
}
