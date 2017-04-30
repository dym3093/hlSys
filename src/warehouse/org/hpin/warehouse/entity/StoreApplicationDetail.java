/**
 * @author DengYouming
 * @since 2016-7-14 下午12:30:23
 */
package org.hpin.warehouse.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author DengYouming
 * @since 2016-7-14 下午12:30:23
 */
public class StoreApplicationDetail extends BaseEntity {

	private static final long serialVersionUID = 4953388783728549970L;

	/** 1. ID VARCHAR2(100) */ 
	private String id; 
 /** 2. 关联表的ID VARCHAR2(100) */ 
	private String idRelated; 
 /** 3. 物品名称 VARCHAR2(256) */ 
	private String prdouceName; 
 /** 4. 规格 VARCHAR2(256) */ 
	private String standards; 
 /** 5. 申请数量 NUMBER */ 
	private Integer applyNum; 
 /** 6. 物品描述 VARCHAR2(1000) */ 
	private String descripe; 
 /** 7. 大类编码 VARCHAR2(256) */ 
	private String typeBigCode; 
 /** 8. STORE_TYPE表的ID VARCHAR2(100) */ 
	private String storeTypeId; 
 /** 9. 仓库名称 VARCHAR2(256) */ 
	private String warehouse; 
 /** 10. 仓库ID VARCHAR2(100) */ 
	private String warehouseId; 
 /** 11. 物品类型 VARCHAR2(256) */ 
	private String typeName; 
 /** 12. 物品类型 VARCHAR2(100) */ 
	private String typeId; 
 /** 13. 卡号段开始 VARCHAR2(256) */ 
	private String cardStart; 
 /** 14. 卡号段截至 VARCHAR2(256) */ 
	private String cardEnd; 
 /** 15. 卡号数量 NUMBER */ 
	private Integer cardCount; 
 /** 16. 备注 VARCHAR2(1000) */ 
	private String remark; 
 /** 17. 创建时间 DATE */ 
	private Date createTime; 
 /** 18. 创建人ID VARCHAR2(100) */ 
	private String createUserId; 
 /** 19. 创建人姓名 VARCHAR2(256) */ 
	private String createUser; 
 /** 20. 修改时间 DATE */ 
	private Date updateTime; 
 /** 21. 修改人ID VARCHAR2(100) */ 
	private String updateUserId; 
 /** 22. 修改人姓名 VARCHAR2(256) */ 
	private String updateUser; 
 /** 23. 状态(未处理,,处理中,已完成) NUMBER */ 
	private Integer status; 
 /** 24. 版本号 NUMBER */ 
	private Integer version; 
 /** 25. 备用字段（根据使用改备注信息） VARCHAR2(256) */ 
	private String attribute1; 
 /** 26. 备用字段（根据使用改备注信息） VARCHAR2(256) */ 
	private String attribute2; 
 /** 27. 备用字段（根据使用改备注信息） VARCHAR2(256) */ 
	private String attribute3; 
 /** 28. 备用字段（根据使用改备注信息） VARCHAR2(256) */ 
	private String attribute4; 
 /** 29. 备用字段（根据使用改备注信息） VARCHAR2(1000) */ 
	private String attribute5; 
 /** 30. 备用字段（根据使用改备注信息） VARCHAR2(1000) */ 
	private String attribute6; 
 /** 31. 备用字段（根据使用改备注信息） VARCHAR2(1000) */ 
	private String attribute7; 
 /** 32. 备用字段（根据使用改备注信息） VARCHAR2(1000) */ 
	private String attribute8; 
 /** 33. 备用字段（根据使用改备注信息） NUMBER */ 
	private String attribute9; 
 /** 34. 备用字段（根据使用改备注信息） NUMBER(20,2) */ 
	private String attribute10;
	

	public static final String F_ID = "id";
	public static final String F_IDRELATED = "idRelated";
	public static final String F_PRDOUCENAME = "prdouceName";
	public static final String F_STANDARDS = "standards";
	public static final String F_APPLYNUM = "applyNum";
	public static final String F_DESCRIPE = "descripe";
	public static final String F_TYPEBIGCODE = "typeBigCode";
	public static final String F_STORETYPEID = "storeTypeId";
	public static final String F_WAREHOUSE = "warehouse";
	public static final String F_WAREHOUSEID = "warehouseId";
	public static final String F_TYPENAME = "typeName";
	public static final String F_TYPEID = "typeId";
	public static final String F_CARDSTART = "cardStart";
	public static final String F_CARDEND = "cardEnd";
	public static final String F_CARDCOUNT = "cardCount";
	public static final String F_REMARK = "remark";
	public static final String F_CREATETIME = "createTime";
	public static final String F_CREATEUSERID = "createUserId";
	public static final String F_CREATEUSER = "createUser";
	public static final String F_UPDATETIME = "updateTime";
	public static final String F_UPDATEUSERID = "updateUserId";
	public static final String F_UPDATEUSER = "updateUser";
	public static final String F_STATUS = "status";
	public static final String F_VERSION = "version";
	public static final String F_ATTRIBUTE1 = "attribute1";
	public static final String F_ATTRIBUTE2 = "attribute2";
	public static final String F_ATTRIBUTE3 = "attribute3";
	public static final String F_ATTRIBUTE4 = "attribute4";
	public static final String F_ATTRIBUTE5 = "attribute5";
	public static final String F_ATTRIBUTE6 = "attribute6";
	public static final String F_ATTRIBUTE7 = "attribute7";
	public static final String F_ATTRIBUTE8 = "attribute8";
	public static final String F_ATTRIBUTE9 = "attribute9";
	public static final String F_ATTRIBUTE10 = "attribute10";

	
	public StoreApplicationDetail() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdRelated() {
		return idRelated;
	}

	public void setIdRelated(String idRelated) {
		this.idRelated = idRelated;
	}

	public String getPrdouceName() {
		return prdouceName;
	}

	public void setPrdouceName(String prdouceName) {
		this.prdouceName = prdouceName;
	}

	public String getStandards() {
		return standards;
	}

	public void setStandards(String standards) {
		this.standards = standards;
	}

	public Integer getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(Integer applyNum) {
		this.applyNum = applyNum;
	}

	public String getDescripe() {
		return descripe;
	}

	public void setDescripe(String descripe) {
		this.descripe = descripe;
	}

	public String getTypeBigCode() {
		return typeBigCode;
	}

	public void setTypeBigCode(String typeBigCode) {
		this.typeBigCode = typeBigCode;
	}

	public String getStoreTypeId() {
		return storeTypeId;
	}

	public void setStoreTypeId(String storeTypeId) {
		this.storeTypeId = storeTypeId;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getCardStart() {
		return cardStart;
	}

	public void setCardStart(String cardStart) {
		this.cardStart = cardStart;
	}

	public String getCardEnd() {
		return cardEnd;
	}

	public void setCardEnd(String cardEnd) {
		this.cardEnd = cardEnd;
	}

	public Integer getCardCount() {
		return cardCount;
	}

	public void setCardCount(Integer cardCount) {
		this.cardCount = cardCount;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

}
