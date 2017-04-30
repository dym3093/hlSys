package org.hpin.reportdetail.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Carly
 * @since 2016年12月21日15:44:57
 * 用于重复打印任务导入时的页面展示
 */
public class ErpRepeatPrintEntity implements Serializable{
	
	private static final long serialVersionUID = 8773904301414132764L;
	
	private String id;
	private String code;
	private String userName;
	private String age;
	private String gender;
	private String combo;
	private String branchCompany;
	private String ownedCompany;
	private String dept;
	private String type;
	private Date createTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCombo() {
		return combo;
	}
	public void setCombo(String combo) {
		this.combo = combo;
	}
	public String getBranchCompany() {
		return branchCompany;
	}
	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}
	public String getOwnedCompany() {
		return ownedCompany;
	}
	public void setOwnedCompany(String ownedCompany) {
		this.ownedCompany = ownedCompany;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ErpRepeatPrintEntity [id=" + id + ", code=" + code
				+ ", userName=" + userName + ", age=" + age + ", gender="
				+ gender + ", combo=" + combo + ", branchCompany="
				+ branchCompany + ", ownedCompany=" + ownedCompany + ", dept="
				+ dept + ", type=" + type + "]";
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
