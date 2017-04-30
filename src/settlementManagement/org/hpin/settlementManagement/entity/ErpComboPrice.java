package org.hpin.settlementManagement.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 套餐价格
 * @author tangxing
 *
 */

public class ErpComboPrice extends BaseEntity  implements java.io.Serializable{

	private String id;
	
	private String comboName;		//对应套餐名
	
	private String productName;		//产品名称
		
	private String comboContent;	//套餐内容
	
	private String comboId;			//对应套餐ID
			
	private String geneCompany;		//基因公司
	
	private String geneCompanyId;	//基因公司ID
	
	private BigDecimal price;		//价格
	
	private String createUsername;
	
	private Date createTime;
	
	private String updateUsername;
		
	private Date updateTime;
	
	private String isDelete;		

	
	
	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getComboContent() {
		return comboContent;
	}


	public void setComboContent(String comboContent) {
		this.comboContent = comboContent;
	}


	public String getComboId() {
		return comboId;
	}


	public void setComboId(String comboId) {
		this.comboId = comboId;
	}


	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public String getGeneCompany() {
		return geneCompany;
	}

	public void setGeneCompany(String geneCompany) {
		this.geneCompany = geneCompany;
	}

	public String getGeneCompanyId() {
		return geneCompanyId;
	}

	public void setGeneCompanyId(String geneCompanyId) {
		this.geneCompanyId = geneCompanyId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUsername() {
		return updateUsername;
	}

	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}


}
