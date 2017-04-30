package org.hpin.base.dict.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 用户邮件字典
 * @author wuhao
 *
 */
public class DictMailBase extends BaseEntity{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3570561897007159786L;

	private String id;	
	private String userName;	
	private String mailAddress; //邮箱地址
	private Integer isStatus; //是否启用:默认0启用，1未启用
	private Integer isDeleted; //是否删除:默认0未删除，1删除
	private Date createTime;
	private String createUserId;
	private String updateUserId;
	private Date updateTime;
	private String dictConcat;//对应字典表名称
	
	
	public String getDictConcat() {
		return dictConcat;
	}
	public void setDictConcat(String dictConcat) {
		this.dictConcat = dictConcat;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public Integer getIsStatus() {
		return isStatus;
	}
	public void setIsStatus(Integer isStatus) {
		this.isStatus = isStatus;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * 
	 * @param id
	 * @param userName
	 * @param mailAddress
	 * @param isStatus
	 * @param isDeleted
	 * @param createTime
	 * @param createUserId
	 * @param updateUserId
	 * @param updateTime
	 */
	public DictMailBase(String id, String userName, String mailAddress,
			Integer isStatus, Integer isDeleted, Date createTime,
			String createUserId, String updateUserId, Date updateTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.mailAddress = mailAddress;
		this.isStatus = isStatus;
		this.isDeleted = isDeleted;
		this.createTime = createTime;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
		this.updateTime = updateTime;
	}
	
	/**
	 * default constructor
	 */
	public DictMailBase() {
		super();
	}	
	
	
}
