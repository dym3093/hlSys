package org.hpin.events.entity;


import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author machuan
 * @date 2016年12月22日
 */
public class ErpCustomerTesting extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1053798540727266084L;
	
	private String id;//id保存的是场次号
	private String status;//状态0为正常1为删除
	private Date createTime;
	private Date updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
}