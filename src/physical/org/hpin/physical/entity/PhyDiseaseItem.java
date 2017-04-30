package org.hpin.physical.entity;

import org.hpin.common.core.orm.BaseEntity;

public class PhyDiseaseItem extends BaseEntity {

	private String id;
	
	private String diseaseCode;	//疾病编码
	
	private String diseaseName;	//疾病名字
	
	private String groupCode;	//分组编码
	
	private String groupName;	//分组姓名

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
