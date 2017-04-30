package org.hpin.events.entity;

import org.hpin.common.core.orm.BaseEntity;

import java.util.Date;

/**
 * 远盟套餐与外部套餐对应关系表
 * Created by admin on 2016/11/9.
 */
public class ErpComboMap extends BaseEntity{

    /** 1. ID VARCHAR2(32) */
    private String id;
    /** 2. 支公司ID VARCHAR2(32) */
    private String branchCompanyId;
    /** 3. 远盟套餐名 VARCHAR2(256) */
    private String comboYm;
    /** 4. 显示名称 VARCHAR2(512) */
    private String comboDisname;
    /** 5. 备注 VARCHAR2(1024) */
    private String remark;
    /** 6. 创建时间 DATE */
    private Date createTime;
    /** 7. 创建人ID VARCHAR2(100) */
    private String createUserId;
    /** 8. 创建人姓名 VARCHAR2(256) */
    private String createUser;
    /** 9. 修改时间 DATE */
    private Date updateTime;
    /** 10. 修改人ID VARCHAR2(100) */
    private String updateUserId;
    /** 11. 修改人姓名 VARCHAR2(256) */
    private String updateUser;
    /** 12. 状态(新增:0 ,删除:1 ) VARCHAR2(36) */
    private Integer status;

    public static final String F_ID = "id";
    public static final String F_BRANCHCOMPANYID = "branchCompanyId";
    public static final String F_COMBOYM = "comboYm";
    public static final String F_COMBODISNAME = "comboDisname";
    public static final String F_REMARK = "remark";
    public static final String F_CREATETIME = "createTime";
    public static final String F_CREATEUSERID = "createUserId";
    public static final String F_CREATEUSER = "createUser";
    public static final String F_UPDATETIME = "updateTime";
    public static final String F_UPDATEUSERID = "updateUserId";
    public static final String F_UPDATEUSER = "updateUser";
    public static final String F_STATUS = "status";

    public ErpComboMap() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getComboYm() {
        return comboYm;
    }

    public void setComboYm(String comboYm) {
        this.comboYm = comboYm;
    }

    public String getComboDisname() {
        return comboDisname;
    }

    public void setComboDisname(String comboDisname) {
        this.comboDisname = comboDisname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErpComboMap{" +
                "id='" + id + '\'' +
                ", branchCompanyId='" + branchCompanyId + '\'' +
                ", comboYm='" + comboYm + '\'' +
                ", comboDisname='" + comboDisname + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", createUserId='" + createUserId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUserId='" + updateUserId + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", status=" + status +
                '}';
    }
}
