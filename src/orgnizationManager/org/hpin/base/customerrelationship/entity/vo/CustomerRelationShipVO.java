package org.hpin.base.customerrelationship.entity.vo;

import java.util.Date;

public class CustomerRelationShipVO {
	private String id;
	/* 公司编码 */
	private String companyCode;
	/* 公司名称 */
	private String branchCommany;
	/* 总公司ID */
	private String ownedCompany;
	/* 总公司名称  */ 
	private String customerNameSimple;
	/* 省份 */
	private String province;
	private String provinceName;
	/* 城市 */
	private String city;
	private String cityName;
	/* 对应套餐信息逗号分割 */
	private String combo;
	/* 地址 */
	private String address;
	/* 二维码地址 add by machuan -2016-12-02 */
	private String qrCodeUrl;
	
	private Date createTime; //创建日期;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBranchCommany() {
		return branchCommany;
	}

	public void setBranchCommany(String branchCommany) {
		this.branchCommany = branchCommany;
	}

	public String getOwnedCompany() {
		return ownedCompany;
	}

	public void setOwnedCompany(String ownedCompany) {
		this.ownedCompany = ownedCompany;
	}

	public String getCustomerNameSimple() {
		return customerNameSimple;
	}

	public void setCustomerNameSimple(String customerNameSimple) {
		this.customerNameSimple = customerNameSimple;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	
}
