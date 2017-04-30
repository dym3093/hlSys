/**
 * @author DengYouming
 * @since 2016-5-17 下午4:26:26
 */
package org.hpin.venueStaffSettlement.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 场次人员费用   TALBE: ERP_EVENTS_STAFF_COST
 * @author DengYouming
 * @since 2016-5-17 下午4:26:26
 */
public class ErpEventsStaffCost extends BaseEntity{

	private static final long serialVersionUID = 5127843183673246828L;
	
	/** 1. ID VARCHAR2(100) */ 
	private String id; 
	/** 2. 场次ID VARCHAR2(100) */ 
	private String eventsId; 
	/** 3. 场次号 VARCHAR2(256) */ 
	private String eventsNo; 
	/** 4. 人员ID VARCHAR2(100) */ 
	private String staffId; 
	/** 5. 人员姓名 VARCHAR2(256) */ 
	private String staff; 
	/** 6. 职位ID VARCHAR2(100) */ 
	private String positionId; 
	/** 7. 职位名 VARCHAR2(256) */ 
	private String position; 
	/** 8. 交通费 NUMBER(20,2) */ 
	private BigDecimal trafficCost; 
	/** 9. 住宿费 NUMBER(20,2) */ 
	private BigDecimal hotelCost; 
	/** 10. 餐费 NUMBER(20,2) */ 
	private BigDecimal mealsCost; 
	/** 11. 课酬费 NUMBER(20,2) */ 
	private BigDecimal courseCost; 
	/** 12. 公杂费 NUMBER(20,2) */ 
	private BigDecimal miscellaneousCost; 
	/** 13. 劳务费 NUMBER(20,2) */ 
	private BigDecimal serviceCost; 
	/** 14. 物资费 NUMBER(20,2) */ 
	private BigDecimal materialCost; 
	/** 15. 检测费 NUMBER(20,2) */ 
	private BigDecimal inspectionCost; 
	/** 16. 快递费 NUMBER(20,2) */ 
	private BigDecimal expressCost; 
	/** 17. 印刷费 NUMBER(20,2) */ 
	private BigDecimal publishCost; 
	/** 18. 采样费 NUMBER(20,2) */ 
	private BigDecimal samplingCost; 
	/** 19. 其他费用 NUMBER(20,2) */ 
	private BigDecimal otherCost; 
	/** 20. 费用总计 NUMBER(20,2) */ 
	private BigDecimal amount; 
	/** 21. 备注 VARCHAR2(1000) */ 
	private String remark; 
	/** 22. 创建时间 DATE */ 
	private Date createTime; 
	/** 23. 创建人ID VARCHAR2(100) */ 
	private String createUserId; 
	/** 24. 创建人姓名 VARCHAR2(256) */ 
	private String createUser; 
	/** 25. 修改时间 DATE */ 
	private Date updateTime; 
	/** 26. 修改人ID VARCHAR2(100) */ 
	private String updateUserId; 
	/** 27. 修改人姓名 VARCHAR2(256) */ 
	private String updateUser; 
	/** 28. 状态 VARCHAR2(36) */ 
	private String status; 
	/** 29. 版本号 NUMBER */ 
	private Integer version;
	
	/** 类名 */
	public static final String F_CLASS_NAME = "ErpEventsStaffCost";
	
	public static final String F_ID = "id";
	public static final String F_EVENTSID = "eventsId";
	public static final String F_EVENTSNO = "eventsNo";
	public static final String F_STAFFID = "staffId";
	public static final String F_STAFF = "staff";
	public static final String F_POSITIONID = "positionId";
	public static final String F_POSITION = "position";
	public static final String F_TRAFFICCOST = "trafficCost";
	public static final String F_HOTELCOST = "hotelCost";
	public static final String F_MEALSCOST = "mealsCost";
	public static final String F_COURSECOST = "courseCost";
	public static final String F_MISCELLANEOUSCOST = "miscellaneousCost";
	public static final String F_SERVICECOST = "serviceCost";
	public static final String F_MATERIALCOST = "materialCost";
	public static final String F_INSPECTIONCOST = "inspectionCost";
	public static final String F_EXPRESSCOST = "expressCost";
	public static final String F_PUBLISHCOST = "publishCost";
	public static final String F_SAMPLINGCOST = "samplingCost";
	public static final String F_OTHERCOST = "otherCost";
	public static final String F_AMOUNT = "amount";
	public static final String F_REMARK = "remark";
	public static final String F_CREATETIME = "createTime";
	public static final String F_CREATEUSERID = "createUserId";
	public static final String F_CREATEUSER = "createUser";
	public static final String F_UPDATETIME = "updateTime";
	public static final String F_UPDATEUSERID = "updateUserId";
	public static final String F_UPDATEUSER = "updateUser";
	public static final String F_STATUS = "status";
	public static final String F_VERSION = "version";

	
	public ErpEventsStaffCost() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventsId() {
		return eventsId;
	}
	public void setEventsId(String eventsId) {
		this.eventsId = eventsId;
	}
	public String getEventsNo() {
		return eventsNo;
	}
	public void setEventsNo(String eventsNo) {
		this.eventsNo = eventsNo;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaff() {
		return staff;
	}
	public void setStaff(String staff) {
		this.staff = staff;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public BigDecimal getTrafficCost() {
		return trafficCost;
	}
	public void setTrafficCost(BigDecimal trafficCost) {
		this.trafficCost = trafficCost;
	}
	public BigDecimal getHotelCost() {
		return hotelCost;
	}
	public void setHotelCost(BigDecimal hotelCost) {
		this.hotelCost = hotelCost;
	}
	public BigDecimal getMealsCost() {
		return mealsCost;
	}
	public void setMealsCost(BigDecimal mealsCost) {
		this.mealsCost = mealsCost;
	}
	public BigDecimal getCourseCost() {
		return courseCost;
	}
	public void setCourseCost(BigDecimal courseCost) {
		this.courseCost = courseCost;
	}
	public BigDecimal getMiscellaneousCost() {
		return miscellaneousCost;
	}
	public void setMiscellaneousCost(BigDecimal miscellaneousCost) {
		this.miscellaneousCost = miscellaneousCost;
	}
	public BigDecimal getServiceCost() {
		return serviceCost;
	}
	public void setServiceCost(BigDecimal serviceCost) {
		this.serviceCost = serviceCost;
	}
	public BigDecimal getMaterialCost() {
		return materialCost;
	}
	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}
	public BigDecimal getInspectionCost() {
		return inspectionCost;
	}
	public void setInspectionCost(BigDecimal inspectionCost) {
		this.inspectionCost = inspectionCost;
	}
	public BigDecimal getExpressCost() {
		return expressCost;
	}
	public void setExpressCost(BigDecimal expressCost) {
		this.expressCost = expressCost;
	}
	public BigDecimal getPublishCost() {
		return publishCost;
	}
	public void setPublishCost(BigDecimal publishCost) {
		this.publishCost = publishCost;
	}
	public BigDecimal getSamplingCost() {
		return samplingCost;
	}
	public void setSamplingCost(BigDecimal samplingCost) {
		this.samplingCost = samplingCost;
	}
	public BigDecimal getOtherCost() {
		return otherCost;
	}
	public void setOtherCost(BigDecimal otherCost) {
		this.otherCost = otherCost;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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


	
	
}
