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
 * 结算任务表(基因公司)
 * @author dym
 * @since 2016-04-26 14:30
 */
@Component
public class ErpSettlementTaskJY extends BaseEntity  implements java.io.Serializable{

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -3969032903438685286L;

	/**	1.	ID	VARCHAR2(100)	*/	
	private String	id	;
	/**	2.	任务号	VARCHAR2(1000)	*/	
	private String	taskNo	;
	/**	3.	结算任务名	VARCHAR2(1000)	*/	
	private String	taskName	;
	/**	4.	结算时间	DATE	*/	
	private Date	settlementTime	;
	/**	5.	基因公司	VARCHAR2(1000)	*/	
	private String	geCompany	;
	/**	6.	基因公司ID	VARCHAR2(100)	*/	
	private String	geCompanyId	;
	/**	7.	套餐数量	NUMBER	*/	
	private Integer	setMealNum	;
	/**	8.	总人数	NUMBER	*/	
	private Integer	totalPersonNum	;
	/**	9.	成功匹配数量	NUMBER	*/	
	private Integer	successNum	;
	/**	10.	异常数量	NUMBER	*/	
	private Integer	abnormalNum	;
	/**	11.	单据总额	NUMBER(20,2)	*/	
	private BigDecimal	totalAmount	;
	/**	12.	Excel路径	VARCHAR2(1000)	*/	
	private String	excelPath	;
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
	/**	19.	状态(新增,删除,结算等状态)	VARCHAR2(36)	*/	
	private String	status	;
	/**	20.	版本号	NUMBER	*/	
	private Integer	version	;
	/**	21.	备注	VARCHAR2(1000)	*/	
	private String	remark	;
	
	/** 支公司数量  NUMBER*/
	private Integer branchCompanyNum;
	
	/** 未付款金额 */
	private BigDecimal noPayAmount;
	
	/** 付款次数 */
	private Integer payCount;
	
	/** 付款操作人 */
	private String payOperator;
	
	/** 累计付款金额 */
	private BigDecimal accruedPayAmount;
	
	/** OA编号  */
	private String OANo;
	
	/** 收款公司 */
	private String collectionCompany;
	
	/** 预计付款人数 */
	private Integer expectPayNum;	
	
	/** 完成付款人数 */
	private Integer completePayNum;
	
	public static final String F_ID = "id";
	
	public static final String F_ERPSETTLEMENTTASKJY = "ErpSettlementTaskJY";
	
	public static final String F_CREATETIME = "createTime";
	
	public ErpSettlementTaskJY() {
		super();
	}
	
	
	public String getPayOperator() {
		return payOperator;
	}

	public Integer getExpectPayNum() {
		return expectPayNum;
	}

	public void setExpectPayNum(Integer expectPayNum) {
		this.expectPayNum = expectPayNum;
	}

	public Integer getCompletePayNum() {
		return completePayNum;
	}

	public void setCompletePayNum(Integer completePayNum) {
		this.completePayNum = completePayNum;
	}

	public String getCollectionCompany() {
		return collectionCompany;
	}

	public void setCollectionCompany(String collectionCompany) {
		this.collectionCompany = collectionCompany;
	}

	public void setPayOperator(String payOperator) {
		this.payOperator = payOperator;
	}

	public BigDecimal getAccruedPayAmount() {
		return accruedPayAmount;
	}

	public void setAccruedPayAmount(BigDecimal accruedPayAmount) {
		this.accruedPayAmount = accruedPayAmount;
	}

	public Integer getPayCount() {
		return payCount;
	}

	public void setPayCount(Integer payCount) {
		this.payCount = payCount;
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
	public String getGeCompany() {
		return geCompany;
	}
	public void setGeCompany(String geCompany) {
		this.geCompany = geCompany;
	}
	public String getGeCompanyId() {
		return geCompanyId;
	}
	public void setGeCompanyId(String geCompanyId) {
		this.geCompanyId = geCompanyId;
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
	public String getExcelPath() {
		return excelPath;
	}
	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
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

	public Integer getBranchCompanyNum() {
		return branchCompanyNum;
	}

	public void setBranchCompanyNum(Integer branchCompanyNum) {
		this.branchCompanyNum = branchCompanyNum;
	}

	public BigDecimal getNoPayAmount() {
		return noPayAmount;
	}

	public void setNoPayAmount(BigDecimal noPayAmount) {
		this.noPayAmount = noPayAmount;
	}

	public String getOANo() {
		return OANo;
	}

	public void setOANo(String oANo) {
		OANo = oANo;
	}
	
}
