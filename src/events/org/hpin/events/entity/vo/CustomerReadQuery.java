/**
 * 
 */
package org.hpin.events.entity.vo;

/**
 * 报告解读页面的查询条件
 * @author machuan
 * @date 2016年11月3日
 */
public class CustomerReadQuery {
	//姓名
	private String name;
	//条形码
	private String code;
	//套餐名
	private String setmealName;
	//身份证号
	private String idno;
	//手机号
	private String phone;
	//项目类别-编码
	private String projectTypes;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the setmealName
	 */
	public String getSetmealName() {
		return setmealName;
	}
	/**
	 * @param setmealName the setmealName to set
	 */
	public void setSetmealName(String setmealName) {
		this.setmealName = setmealName;
	}
	/**
	 * @return the idno
	 */
	public String getIdno() {
		return idno;
	}
	/**
	 * @param idno the idno to set
	 */
	public void setIdno(String idno) {
		this.idno = idno;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the projectTypes
	 */
	public String getProjectTypes() {
		return projectTypes;
	}
	/**
	 * @param projectTypes the projectTypes to set
	 */
	public void setProjectTypes(String projectTypes) {
		this.projectTypes = projectTypes;
	}
	
	
}
