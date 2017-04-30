package org.hpin.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品信息表;(ERP_PRODUCT)
 * @author Henry.xu
 * create by 20161008
 *
 */
public class ErpProduct implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7274568015788134419L;
	
	/**主键ID*/
	private String id;
	/**产品类别; 数据来源: SYS_DICTTYPE表  PARENTDICTID字段为'10107' */
	private String productType;
	
	private String productTypeName; //显示使用;
	
	/**产品名称*/
	private String productName;
	/**规格*/
	private String standard;
	/**描述*/
	private String describe;
	private String isClose = "0"; //是否关闭(0启用[默认值],1关闭)
	private String imagePath; //产品图片路径;
	
	private Date createTime;
	private String createUserId;
	private Date updateTime;
	private String updateUserId;
	private Integer isDeleted;
	
	
	public String getIsClose() {
		return isClose;
	}
	public void setIsClose(String isClose) {
		this.isClose = isClose;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
