package org.hpin.reportdetail.entity;

import java.io.Serializable;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author Carly
 * @since 2016年12月19日15:12:31
 * json数据的客户检测信息
 */
public class ErpCustomerExamWC extends BaseEntity implements Serializable{

	
	private static final long serialVersionUID = 1049204069733865152L;

	private String id;
	private Long wcId;				//检测指标id
	
	private Integer examItemWeight;	//指标小项权重
	private Long examId;			//检测id
	private String sysName;			//指标中项类型名称
	private String examContent;		//指标小项名称
	private Long sysId;			//指标中项类型id
	private String userId;			//用户id

	private String transition;		//修复能力值

	private Long examItemId;		//指标小项id

	private Integer examItemIndex;	//指标小项顺序

	private String functionalStatus;	//功能状态值

	private Long maxtermInfoId;	//指标大项类型id

	private Date createTime;	//创建时间
	private Integer isDeleted;
	private String batchNo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getWcId() {
		return wcId;
	}
	public void setWcId(Long wcId) {
		this.wcId = wcId;
	}
	public Integer getExamItemWeight() {
		return examItemWeight;
	}
	public void setExamItemWeight(Integer examItemWeight) {
		this.examItemWeight = examItemWeight;
	}
	public Long getExamId() {
		return examId;
	}
	public void setExamId(Long examId) {
		this.examId = examId;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getExamContent() {
		return examContent;
	}
	public void setExamContent(String examContent) {
		this.examContent = examContent;
	}
	public Long getSysId() {
		return sysId;
	}
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTransition() {
		return transition;
	}
	public void setTransition(String transition) {
		this.transition = transition;
	}
	public Long getExamItemId() {
		return examItemId;
	}
	public void setExamItemId(Long examItemId) {
		this.examItemId = examItemId;
	}
	public Integer getExamItemIndex() {
		return examItemIndex;
	}
	public void setExamItemIndex(Integer examItemIndex) {
		this.examItemIndex = examItemIndex;
	}
	public String getFunctionalStatus() {
		return functionalStatus;
	}
	public void setFunctionalStatus(String functionalStatus) {
		this.functionalStatus = functionalStatus;
	}
	public Long getMaxtermInfoId() {
		return maxtermInfoId;
	}
	public void setMaxtermInfoId(Long maxtermInfoId) {
		this.maxtermInfoId = maxtermInfoId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	
}