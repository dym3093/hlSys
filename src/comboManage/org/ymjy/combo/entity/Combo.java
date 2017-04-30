package org.ymjy.combo.entity;
// default package


import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;


/**
 * Combo entity. @author MyEclipse Persistence Tools
 */

public class Combo  extends BaseEntity {


    // Fields    

     private String id;
     private String comboName;
     private String productName;
     private String comboContent;
     private Date createTime;
     private Date updateTime;
     private Date deleteTime;
     private int isDelete;
     private String createUser;
     private String updateUser;
     private String deleteUser;
     /**
      * 项目类别,PCT_001基因项目, PCT_002癌筛项目, PCT_003分子检测, PCT_004无创生物电检测
      * create by henry.xu 20160822
      */
     private String projectTypes; 
     
     /**
      * 时效天数, 表示有效的天数;
      * create by henry.xu 20160901
      */
     private Integer validityDay;
     
     /** 是否是打印套餐0不是，1是 */
     private String isPrint;
     //打印类型(0:远盟打印(需要打印的套餐);1:基因公司打印();2:其他机构打印();)
     private String printType;
     
     /** 只用于显示套餐项目中间表的是否生成报告字段 **/
     private String isCreatePdf;
     
     /**显示使用*/
     private String comboShowName;
     /**显示使用*/
     private String isUsed;

    // Property accessors

    public String getProjectTypes() {
		return projectTypes;
	}

	public String getComboShowName() {
		return comboShowName;
	}

	public void setComboShowName(String comboShowName) {
		this.comboShowName = comboShowName;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getValidityDay() {
		return validityDay;
	}

	public void setValidityDay(Integer validityDay) {
		this.validityDay = validityDay;
	}

	public void setProjectTypes(String projectTypes) {
		this.projectTypes = projectTypes;
	}

	public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getComboName() {
        return this.comboName;
    }
    
    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public String getProductName() {
        return this.productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getComboContent() {
        return this.comboContent;
    }
    
    public void setComboContent(String comboContent) {
        this.comboContent = comboContent;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeleteTime() {
        return this.deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public int getIsDelete() {
        return this.isDelete;
    }
    
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }
    
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getDeleteUser() {
        return this.deleteUser;
    }
    
    public void setDeleteUser(String deleteUser) {
        this.deleteUser = deleteUser;
    }

	public String getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public String getIsCreatePdf() {
		return isCreatePdf;
	}

	public void setIsCreatePdf(String isCreatePdf) {
		this.isCreatePdf = isCreatePdf;
	}
   








}