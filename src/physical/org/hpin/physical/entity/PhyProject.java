package org.hpin.physical.entity;

import org.hpin.common.core.orm.BaseEntity;

public class PhyProject extends BaseEntity {

	private String id;
	
	private String projectCode;		//体检项目编码
	
	private String projectName;		//体检项目名字
	
	private String projectCategory;	//类别名称
	
	private String projectFlag;		//'0:女;  1:男;  2:男女'

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	public String getProjectFlag() {
		return projectFlag;
	}

	public void setProjectFlag(String projectFlag) {
		this.projectFlag = projectFlag;
	}
	
}
