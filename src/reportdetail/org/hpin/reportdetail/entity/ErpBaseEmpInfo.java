package org.hpin.reportdetail.entity;

import java.io.Serializable;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;


/**
 * @author machuan
 * @date 2016年12月12日
 * 员工基础信息
 */
public class ErpBaseEmpInfo extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;	//姓名
	private String phone;	//电话
	private String email;		//邮箱地址
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
