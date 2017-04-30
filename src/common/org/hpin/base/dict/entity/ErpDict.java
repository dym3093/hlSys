/**
 * @author DengYouming
 * @since 2016-5-19 上午10:09:39
 */
package org.hpin.base.dict.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 数据字典
 * @author DengYouming
 * @since 2016-5-19 上午10:09:39
 */
public class ErpDict extends BaseEntity{
	
	private static final long serialVersionUID = 8291577197490973378L;
	
	/** 1. ID VARCHAR2(100) */ 
		private String id; 
	 /** 2. 键 VARCHAR2(256) */ 
		private String keyName; 
	 /** 3. 值 VARCHAR2(256) */ 
		private String valueName; 
	 /** 4. 类型（用于过滤） VARCHAR2(256) */ 
		private String typeFilter; 
	 /** 5. 备注 VARCHAR2(1000) */ 
		private String remark; 
	 /** 6. 创建时间 DATE */ 
		private Date createTime; 
	 /** 7. 创建人ID VARCHAR2(100) */ 
		private String createUserId; 
	 /** 8. 创建人姓名 VARCHAR2(256) */ 
		private String createUser; 
	 /** 9. 修改时间 DATE */ 
		private Date updateTime; 
	 /** 10. 修改人ID VARCHAR2(100) */ 
		private String updateUserId; 
	 /** 11. 修改人姓名 VARCHAR2(256) */ 
		private String updateUser; 
	 /** 12. 状态(新增,删除,结算等状态) VARCHAR2(36) */ 
		private String status; 
	 /** 13. 版本号 NUMBER */ 
		private String version; 
	 /** 14. 备用字段1 VARCHAR2(1000) */ 
		private String attr1; 
	 /** 15. 备用字段2 VARCHAR2(1000) */ 
		private String attr2; 
	 /** 16. 备用字段3 VARCHAR2(1000) */ 
		private String attr3; 
	 /** 17. 备用字段4 VARCHAR2(1000) */ 
		private String attr4; 
	 /** 18. 备用字段5 VARCHAR2(1000) */ 
		private String attr5;

	public static final String F_ERPDICT = "ErpDict";
		
	public static final String F_ID = "id";
	public static final String F_KEYNAME = "keyName";
	public static final String F_VALUENAME = "valueName";
	public static final String F_TYPEFILTER = "typeFilter";
	public static final String F_REMARK = "remark";
	public static final String F_CREATETIME = "createTime";
	public static final String F_CREATEUSERID = "createUserId";
	public static final String F_CREATEUSER = "createUser";
	public static final String F_UPDATETIME = "updateTime";
	public static final String F_UPDATEUSERID = "updateUserId";
	public static final String F_UPDATEUSER = "updateUser";
	public static final String F_STATUS = "status";
	public static final String F_VERSION = "version";
	public static final String F_ATTR1 = "attr1";
	public static final String F_ATTR2 = "attr2";
	public static final String F_ATTR3 = "attr3";
	public static final String F_ATTR4 = "attr4";
	public static final String F_ATTR5 = "attr5";	
	
		
	public ErpDict() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public String getTypeFilter() {
		return typeFilter;
	}
	public void setTypeFilter(String typeFilter) {
		this.typeFilter = typeFilter;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public String getAttr3() {
		return attr3;
	}
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	public String getAttr4() {
		return attr4;
	}
	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}
	public String getAttr5() {
		return attr5;
	}
	public void setAttr5(String attr5) {
		this.attr5 = attr5;
	} 

}
