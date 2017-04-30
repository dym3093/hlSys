package org.hpin.events.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 导入客户信息失败批次
 * @author tangxing
 * @date 2016-6-3上午10:18:32
 */

public class ErpCustomerImpFailBtach extends BaseEntity {
	
	private String id;				

	private String failBtachNo;		//失败批次号
	
	private Date createTime;		//创建时间
	
	private String createUserName;	//创建人
	
	private Integer comparFailNum;	//比对失败数量
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFailBtachNo() {
		return failBtachNo;
	}

	public void setFailBtachNo(String failBtachNo) {
		this.failBtachNo = failBtachNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getComparFailNum() {
		return comparFailNum;
	}

	public void setComparFailNum(Integer comparFailNum) {
		this.comparFailNum = comparFailNum;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
