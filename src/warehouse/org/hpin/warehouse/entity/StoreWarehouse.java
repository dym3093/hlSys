package org.hpin.warehouse.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * StoreWarehouse entity. @author MyEclipse Persistence Tools
 */

public class StoreWarehouse extends BaseEntity{
	/**
	 * 序列号;
	 */
	private static final long serialVersionUID = 4258834124137762221L;
	// Fields
	private String id;
	private String name;
	private String address;
	private String director;
	private String tel;
	private String province;
	private String provinceName;
	private String city;
	private String cityName;
	private Date createTime;
	private String createUserId;
	private String updateUserId;
	private Date updateTime;
	private String remark;//描述
	private String remark1;//服务范围
	private String remark2;
	private String remark3;
	private Integer isDeleted;
	private String deleteUserId;
	private Date deleteTime;

	// Constructors

	/** default constructor */
	public StoreWarehouse() {
	}

	/** full constructor */


	// Property accessors

	public String getId() {
		return this.id;
	}

	public StoreWarehouse(String id, String name, String address,
			String director, String tel, String province, String city,
			Date createTime, String createUserId, String updateUserId,
			Date updateTime, String remark, String remark1, String remark2,
			String remark3, Integer isDeleted, String deleteUserId,
			Date deleteTime) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.director = director;
		this.tel = tel;
		this.province = province;
		this.city = city;
		this.createTime = createTime;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
		this.updateTime = updateTime;
		this.remark = remark;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.isDeleted = isDeleted;
		this.deleteUserId = deleteUserId;
		this.deleteTime = deleteTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getDeleteUserId() {
		return this.deleteUserId;
	}

	public void setDeleteUserId(String deleteUserId) {
		this.deleteUserId = deleteUserId;
	}

	public Date getDeleteTime() {
		return this.deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	

}