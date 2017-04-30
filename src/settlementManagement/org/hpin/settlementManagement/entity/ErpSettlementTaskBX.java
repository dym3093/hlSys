/**
 * dym
 * 2016-4-26 下午2:20:31
 */
package org.hpin.settlementManagement.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;
import org.springframework.stereotype.Component;

/**
 * 结算任务表（保险公司）
 * @author DengYouming
 * @since 2016-5-1 下午4:20:53
 */
@Component
public class ErpSettlementTaskBX extends BaseEntity  implements java.io.Serializable{

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -9092897645066784378L;

	/**	1.	ID	VARCHAR2(100)	*/	
	private String	id	;
	/**	2.	任务号	VARCHAR2(1000)	*/	
	private String	taskNo	;
	/**	3.	结算任务名	VARCHAR2(1000)	*/	
	private String	taskName	;
	/**	4.	结算时间	DATE	*/	
	private Date	settlementTime	;
	/**	5.	基因公司	VARCHAR2(1000)	*/	
	private String	branchCompany	;
	/**	6.	基因公司ID	VARCHAR2(100)	*/	
	private String	branchCompanyId	;
	/**	7.	套餐数量	NUMBER	*/	
	private Integer	setMealNum	;
	/**	8.	总人数	NUMBER	*/	
	private Integer	totalPersonNum	;
	/**	9.	可结算人数	NUMBER	*/	
	private Integer	successNum	;
	/**	10.	不可结算人数	NUMBER	*/	
	private Integer	abnormalNum	;
	/**	11.	单据总额	NUMBER(20,2)	*/	
	private BigDecimal	totalAmount	;
	/**	13.	创建时间	DATE	*/	
	private Date	createTime	;
	/**	14.	创建人ID	VARCHAR2(100)	*/	
	private String	createUserId	;
	/**	15.	创建人姓名	VARCHAR2(256)	*/	
	private String	createUser	;
	/**	16.	修改时间	DATE	*/	
	private Date	updateTime	;
	/**	17.	修改人ID	VARCHAR2(100)	*/	
	private String	updateUserId	;
	/**	18.	修改人姓名	VARCHAR2(256)	*/	
	private String	updateUser	;
	/**	19.	结算流转状态[-1 : 删除， 0：未收款（即新增）, 1：待收款， 2：已收款， 3：变更收款 ] VARCHAR2(36)	*/
	private String	status	;
	/**	20.	版本号	NUMBER	*/	
	private Integer	version	;
	/**	21.	备注	VARCHAR2(1000)	*/	
	private String	remark;
	/** 22. 实际结算金额  NUMBER(20,2)	*/
	private BigDecimal actualTotalAmount;
	/** 23. 结算类型   VARCHAR2(100) */
	private String paymentType;
	/** 24. 是否开发票(1：是，0：否)   VARCHAR2(32) */
	private String invoice;
	/** 25. 支公司数量	NUMBER */
	private Integer companyNum;
	/** 26. 远盟对接人 VARCHAR2(256) */
	private String salesManYM;
	/** 27. 远盟对接人ID VARCHAR2(100) */
	private String salesManIdYM;
	/** 28. 项目编号	VARCHAR2(256) */
	private String projectNo;
	
	/** 29. 总公司	VARCHAR2(256) */  
	private String ownedCompany; // add 2016-08-29
	/** 30. 总公司ID	VARCHAR2(100) */
	private String ownedCompanyId; // add 2016-08-29
	
	/** 31. 项目负责人	VARCHAR2(256) */
	private String proUser; // add 2016-09-02

	private BigDecimal totalIncome;//其他费用总额 add by author 2017-03-16

	public static final String F_ERPSETTLEMENTTASKBX = "ErpSettlementTaskBX";
	
	public static final String F_ID = "id";
	public static final String F_TASKNO = "taskNo";
	public static final String F_TASKNAME = "taskName";
	public static final String F_SETTLEMENTTIME = "settlementTime";
	public static final String F_BRANCHCOMPANY = "branchCompany";
	public static final String F_BRANCHCOMPANYID = "branchCompanyId";
	public static final String F_SETMEALNUM = "setMealNum";
	public static final String F_TOTALPERSONNUM = "totalPersonNum";
	public static final String F_SUCCESSNUM = "successNum";
	public static final String F_ABNORMALNUM = "abnormalNum";
	public static final String F_TOTALAMOUNT = "totalAmount";
	public static final String F_CREATETIME = "createTime";
	public static final String F_CREATEUSERID = "createUserId";
	public static final String F_CREATEUSER = "createUser";
	public static final String F_UPDATETIME = "updateTime";
	public static final String F_UPDATEUSERID = "updateUserId";
	public static final String F_UPDATEUSER = "updateUser";
	public static final String F_STATUS = "status";
	public static final String F_VERSION = "version";
	public static final String F_REMARK = "remark";
	public static final String F_ACTUALTOTALAMOUNT = "actualTotalAmount";
	public static final String F_PAYMENTTYPE = "paymentType";
	public static final String F_INVOICE = "invoice";
	public static final String F_COMPANYNUM = "companyNum";
	public static final String F_SALESMANYM = "salesManYM";
	public static final String F_SALESMANIDYM = "salesManIdYM";
	public static final String F_PROJECTNO = "projectNo";
	public static final String F_PROUSER = "proUser";
	public static final String F_OWNEDCOMPANY = "ownedCompany";
	public static final String F_OWNEDCOMPANYID = "ownedCompanyId";
	public static final String F_TOTALINCOME = "totalIncome";

	
	public ErpSettlementTaskBX() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}
	public Integer getSetMealNum() {
		return setMealNum;
	}
	public void setSetMealNum(Integer setMealNum) {
		this.setMealNum = setMealNum;
	}
	public Integer getTotalPersonNum() {
		return totalPersonNum;
	}
	public void setTotalPersonNum(Integer totalPersonNum) {
		this.totalPersonNum = totalPersonNum;
	}
	public Integer getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	public Integer getAbnormalNum() {
		return abnormalNum;
	}
	public void setAbnormalNum(Integer abnormalNum) {
		this.abnormalNum = abnormalNum;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	public String getBranchCompany() {
		return branchCompany;
	}

	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public BigDecimal getActualTotalAmount() {
		return actualTotalAmount;
	}

	public void setActualTotalAmount(BigDecimal actualTotalAmount) {
		this.actualTotalAmount = actualTotalAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public Integer getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(Integer companyNum) {
		this.companyNum = companyNum;
	}

	public String getSalesManYM() {
		return salesManYM;
	}

	public void setSalesManYM(String salesManYM) {
		this.salesManYM = salesManYM;
	}

	public String getSalesManIdYM() {
		return salesManIdYM;
	}

	public void setSalesManIdYM(String salesManIdYM) {
		this.salesManIdYM = salesManIdYM;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getOwnedCompany() {
		return ownedCompany;
	}

	public void setOwnedCompany(String ownedCompany) {
		this.ownedCompany = ownedCompany;
	}

	public String getOwnedCompanyId() {
		return ownedCompanyId;
	}

	public void setOwnedCompanyId(String ownedCompanyId) {
		this.ownedCompanyId = ownedCompanyId;
	}

	public String getProUser() {
		return proUser;
	}

	public void setProUser(String proUser) {
		this.proUser = proUser;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}
}
