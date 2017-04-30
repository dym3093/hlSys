/**
 * @author DengYouming
 * @since 2016-5-23 上午10:04:18
 */
package org.hpin.settlementManagement.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 会员场次价格  Table: ERP_CUSTOMER_EVENTS_PRICE
 * @author DengYouming
 * @since 2016-5-23 上午10:04:18
 */
public class ErpCustomerEventsPrice extends BaseEntity{

	private static final long serialVersionUID = 872252697986509251L;
	
	/** 1. ID VARCHAR2(100) */
	private String id; 
	/** 2. 会员ID VARCHAR2(100) */
	private String customerId; 
	/** 3. 会员姓名 VARCHAR2(256) */
	private String customer; 
	/** 4. 场次日期 DATE */
	private Date eventDate; 
	/** 5. 场次号 VARCHAR2(256) */
	private String eventsNo; 
	/** 6. 套餐名 VARCHAR2(256) */
	private String combo; 
	/** 7. 套餐ID VARCHAR2(100) */
	private String comboId; 
	/** 8. 创建时间 DATE */
	private Date createTime; 
	/** 9. 创建人ID VARCHAR2(100) */
	private String createUserId; 
	/** 10. 创建人姓名 VARCHAR2(256) */
	private String createUser; 
	/** 11. 修改时间 DATE */
	private Date updateTime; 
	/** 12. 修改人ID VARCHAR2(100) */
	private String updateUserId; 
	/** 13. 修改人姓名 VARCHAR2(256) */
	private String updateUser; 
	/** 14. 状态(新增,删除,结算等状态) VARCHAR2(36) */
	private String status; 
	/** 15. 版本号 NUMBER */
	private Integer version; 
	/** 16. 备注 VARCHAR2(1000) */
	private String remark; 
	/** 17. 场次ID VARCHAR2(100) */
	private String eventsId;

	public static final String F_ID = "id";
	public static final String F_CUSTOMERID = "customerId";
	public static final String F_CUSTOMER = "customer";
	public static final String F_EVENTDATE = "eventDate";
	public static final String F_EVENTSNO = "eventsNo";
	public static final String F_COMBO = "combo";
	public static final String F_COMBOID = "comboId";
	public static final String F_CREATETIME = "createTime";
	public static final String F_CREATEUSERID = "createUserId";
	public static final String F_CREATEUSER = "createUser";
	public static final String F_UPDATETIME = "updateTime";
	public static final String F_UPDATEUSERID = "updateUserId";
	public static final String F_UPDATEUSER = "updateUser";
	public static final String F_STATUS = "status";
	public static final String F_VERSION = "version";
	public static final String F_REMARK = "remark";
	public static final String F_EVENTSID= "eventsId";
	
	public ErpCustomerEventsPrice() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventsNo() {
		return eventsNo;
	}

	public void setEventsNo(String eventsNo) {
		this.eventsNo = eventsNo;
	}

	public String getCombo() {
		return combo;
	}

	public void setCombo(String combo) {
		this.combo = combo;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
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

	public String getEventsId() {
		return eventsId;
	}

	public void setEventsId(String eventsId) {
		this.eventsId = eventsId;
	}

}
