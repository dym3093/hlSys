package org.hpin.events.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;
import org.hpin.common.util.DateUtils;
import org.hpin.common.util.PreciseCompute;

public class SampleDeliManager extends BaseEntity{

	private static final long serialVersionUID = 9083835472929379578L;
	
	private String id; //主键ID
	private String deliNum; //快递单号
	private String expressCompanyId; //快递公司
	private String expressCompanyName; //快递公司名称
	private String sampleType; //类别(Type_01寄样/ TYPE_02收样)
	private Date receliveSendDate; //收发日期开始
	private String isBill; //发票(0有/1无)
	private String weight; //重量
	private BigDecimal costPrice; //费用(总费用)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeliNum() {
		return deliNum;
	}
	public void setDeliNum(String deliNum) {
		this.deliNum = deliNum;
	}
	public String getExpressCompanyId() {
		return expressCompanyId;
	}
	public void setExpressCompanyId(String expressCompanyId) {
		this.expressCompanyId = expressCompanyId;
	}
	public String getSampleType() {
		return sampleType;
	}
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	public String getIsBill() {
		return isBill;
	}
	public void setIsBill(String isBill) {
		this.isBill = isBill;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	public void setCostPriceBak(String costPriceBak) {
		this.costPrice = PreciseCompute.formatComma2BigDecimal(costPriceBak, 2);
	}
	
	public Date getReceliveSendDate() {
		return receliveSendDate;
	}
	public void setReceliveSendDate(Date receliveSendDate) {
		this.receliveSendDate = receliveSendDate;
	}
	public void setReceliveSendDateBak(String receliveSendDateBak) {
		try {
			this.receliveSendDate = DateUtils.convertDate(receliveSendDateBak, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		};
	}
	public String getExpressCompanyName() {
		return expressCompanyName;
	}
	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}
	
	
}
