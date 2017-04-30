package org.hpin.settlementManagement.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 保险公司套餐价格表: ERP_COMPANY_COMBO_PRICE
 * @author DengYouming
 * @since 2016-5-4 下午3:54:46
 */
public class ErpCompanyComboPrice extends BaseEntity  implements java.io.Serializable{

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = -5362865177122585384L;
	
	/** 1.	ID	VARCHAR2(100)	*/		
	private String	id;
	/** 2.	套餐名称	VARCHAR2(1000)	*/		
	private String	combo;
	/** 3.	套餐价格	NUMBER(20,2)	*/		
	private BigDecimal comboPrice;
	/** 4.	所属支公司	VARCHAR2(256)	*/		
	private String	company;
	/** 5.	公司ID	VARCHAR2(100)	*/		
	private String	companyId;
	/** 6.	创建时间	DATE	*/		
	private Date	createTime;
	/** 7.	创建人ID	VARCHAR2(100)	*/		
	private String	createUserId;
	/** 8.	创建人姓名	VARCHAR2(256)	*/		
	private String	createUser;
	/** 9.	修改时间	DATE	*/		
	private Date	updateTime;
	/** 10.	修改人ID	VARCHAR2(100)	*/		
	private String	updateUserId;
	/** 11.	修改人姓名	VARCHAR2(256)	*/		
	private String	updateUser;
	/** 12.	状态	VARCHAR2(36)	*/		
	private String	status;
	/** 13.	版本号	NUMBER	*/		
	private Integer	version;
	/** 14.	备注	VARCHAR2(1000)	*/		
	private String	remark;
	/** 15.	套餐ID	VARCHAR2(100)	*/		
	private String	comboId;
	/** 16. 总公司 VARCHAR2(400)	*/	
	private String customerNameSimple;
	/** 17. 套餐数量 VARCHAR2(400)	*/	
	private String comboCount;
	/** 18. 省份 */
	private String province;
	/** 19. 城市 */
	private String city;
	/** 20. 指导价格 */
	private BigDecimal guidancePrice;
	/** 21. 渠道价格 */
	private BigDecimal settlementPrice;
	/** 22. 客户支付价格 */
	private BigDecimal payedPrice;
	/** 23. 套餐别名 */
	private String comboNickname;
	
	
	/* **项目相关字段** */
	/** 24. 支公司关联的项目ID */
	private String projectId;
	
	/** 25. 支公司关联的项目编码 */
	private String projectCode;
	
	/** 26. 支公司关联的项目名字 */
	private String projectName;
	
	/** 27. 支公司关联的项目负责人 */
	private String projectOwner;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public static final String F_ERPCOMPANYCOMBOPRICE = "ErpCompanyComboPrice";
	
	public static final String F_ID = "id";
	public static final String F_COMBO = "combo";
	public static final String F_COMBOPRICE = "comboPrice";
	public static final String F_COMPANY = "company";
	public static final String F_COMPANYID = "companyId";
	public static final String F_CREATETIME = "createTime";
	public static final String F_CREATEUSERID = "createUserId";
	public static final String F_CREATEUSER = "createUser";
	public static final String F_UPDATETIME = "updateTime";
	public static final String F_UPDATEUSERID = "updateUserId";
	public static final String F_UPDATEUSER = "updateUser";
	public static final String F_STATUS = "status";
	public static final String F_VERSION = "version";
	public static final String F_REMARK = "remark";	
	public static final String F_COMBOID = "comboId";
	public static final String F_CUSTOMERNAMESIMPLE = "customerNameSimple";
	public static final String F_COMBOCOUNT = "comboCount";
	public static final String F_PROJECTID = "projectId";
	
	public ErpCompanyComboPrice() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCombo() {
		return combo;
	}

	public void setCombo(String combo) {
		this.combo = combo;
	}

	public BigDecimal getComboPrice() {
		return comboPrice;
	}

	public void setComboPrice(BigDecimal comboPrice) {
		this.comboPrice = comboPrice;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}
	
	public String getCustomerNameSimple() {
		return customerNameSimple;
	}

	public void setCustomerNameSimple(String customerNameSimple) {
		this.customerNameSimple = customerNameSimple;
	}
	
	public String getComboCount() {
		return comboCount;
	}

	public void setComboCount(String comboCount) {
		this.comboCount = comboCount;
	}
	

	public BigDecimal getGuidancePrice() {
		return guidancePrice;
	}

	public void setGuidancePrice(BigDecimal guidancePrice) {
		this.guidancePrice = guidancePrice;
	}

	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public BigDecimal getPayedPrice() {
		return payedPrice;
	}

	public void setPayedPrice(BigDecimal payedPrice) {
		this.payedPrice = payedPrice;
	}

	public String getComboNickname() {
		return comboNickname;
	}

	public void setComboNickname(String comboNickname) {
		this.comboNickname = comboNickname;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	
}
