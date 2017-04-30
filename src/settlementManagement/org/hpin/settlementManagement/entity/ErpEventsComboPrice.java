package org.hpin.settlementManagement.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 场次套餐价格表: ERP_EVENTS_COMBO_PRICE
 * @author DengYouming
 * @since 2016-5-5 下午4:39:07
 */
public class ErpEventsComboPrice extends BaseEntity  implements java.io.Serializable{

	private static final long serialVersionUID = -3841293162454290483L;
	
	/** 1.	ID	VARCHAR2(100)	*/		
	private String	id;
	/** 2.	套餐ID	VARCHAR2(100)	*/		
	private String	comboId;
	/** 3.	套餐	VARCHAR2(1000)	*/		
	private String	combo;
	/** 4.	套餐价格	NUMBER(20,2)	*/		
	private BigDecimal comboPrice;
	/** 5.	场次号	VARCHAR2(256)	*/		
	private String	eventsNo;
	/** 6.	创建时间	DATE	*/		
	private Date createTime;
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
	private String	version;
	/** 14.	备注	VARCHAR2(1000)	*/		
	private String	remark;
	/**折扣率  create by henry.xu 20160829*/
	private BigDecimal discount;
	
	/**套餐人数, 不做数据库操作; create by henry.xu 20160830*/
	private String userNum;
	
	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public static final String F_ERPEVENTSCOMBOPRICE = "ErpEventsComboPrice";
	
	public static final String F_ID = "id";
	public static final String F_COMBOID = "comboId";
	public static final String F_COMBO = "combo";
	public static final String F_COMBOPRICE = "comboPrice";
	public static final String F_EVENTSNO = "eventsNo";
	public static final String F_CREATETIME = "createTime";
	public static final String F_CREATEUSERID = "createUserId";
	public static final String F_CREATEUSER = "createUser";
	public static final String F_UPDATETIME = "updateTime";
	public static final String F_UPDATEUSERID = "updateUserId";
	public static final String F_UPDATEUSER = "updateUser";
	public static final String F_STATUS = "status";
	public static final String F_VERSION = "version";
	public static final String F_REMARK = "remark";
	
	public ErpEventsComboPrice() {

	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
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

	public String getEventsNo() {
		return eventsNo;
	}

	public void setEventsNo(String eventsNo) {
		this.eventsNo = eventsNo;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
