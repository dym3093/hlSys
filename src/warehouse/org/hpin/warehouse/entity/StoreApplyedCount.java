/**
 * @author DengYouming
 * @since 2016-7-14 下午12:30:23
 */
package org.hpin.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * @author chenqi
 * @since 2016年8月11日17:34:31
 * 用于处理部分发货数据
 */
public class StoreApplyedCount extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -1490640931314808899L;
	
	private String typeName;		//规格
	private String prdouceName; 	//名称
	private String standards; 		//规格	
	private String descripe; 		//描述
	private Integer applyNum; 		//申请数量
	private Integer applyedCount;	//已申请的数量
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getPrdouceName() {
		return prdouceName;
	}
	public void setPrdouceName(String prdouceName) {
		this.prdouceName = prdouceName;
	}
	public String getStandards() {
		return standards;
	}
	public void setStandards(String standards) {
		this.standards = standards;
	}
	public String getDescripe() {
		return descripe;
	}
	public void setDescripe(String descripe) {
		this.descripe = descripe;
	}
	public Integer getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(Integer applyNum) {
		this.applyNum = applyNum;
	}
	public Integer getApplyedCount() {
		return applyedCount;
	}
	public void setApplyedCount(Integer applyedCount) {
		this.applyedCount = applyedCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
