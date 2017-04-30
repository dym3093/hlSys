package org.hpin.reportdetail.entity;

import java.io.Serializable;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author Carly
 * @since 2016年11月9日15:27:06
 * 申友对应远盟套餐关系表
 */
public class ErpComboRelation extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 4484276138700667200L;
	
	private String id;
	private String comboNo; //套餐编号
	private String syCombo; //申友套餐
	private String ymCombo;	//远盟套餐
	private String note;	//备注
	private String createUser;
	private String updateUser;
	private String isDelete;
	private Date createTime;
	private Date updateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComboNo() {
		return comboNo;
	}
	public void setComboNo(String comboNo) {
		this.comboNo = comboNo;
	}
	public String getSyCombo() {
		return syCombo;
	}
	public void setSyCombo(String syCombo) {
		this.syCombo = syCombo;
	}
	public String getYmCombo() {
		return ymCombo;
	}
	public void setYmCombo(String ymCombo) {
		this.ymCombo = ymCombo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ErpComboRelation [id=" + id + ", comboNo=" + comboNo
				+ ", syCombo=" + syCombo + ", ymCombo=" + ymCombo + ", note="
				+ note + ", createUser=" + createUser + ", updateUser="
				+ updateUser + ", isDelete=" + isDelete + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
	
}
