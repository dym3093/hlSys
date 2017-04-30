package org.hpin.settlementManagement.entity;

import org.hpin.common.core.orm.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 保险公司结算模块，结算任务中的其他收入表
 * @since 17-3-9
 * @author daimian
 */
public class ErpSettlementIncomeBX extends BaseEntity{

    /**1 ID VARCHAR2(32 BYTE) */
    private String id;
    /**2 结算任务表ID VARCHAR2(32 BYTE) */
    private String taskId;
    /**3 收入类型 VARCHAR2(256 BYTE) */
    private String incomeType;
    /**4 金额 NUMBER(12,2) */
    private BigDecimal amount;
    /**5 备注 VARCHAR2(1024 BYTE) */
    private String remark;
    /**6 是否删除(0:否,1:是) NUMBER(3,0) */
    private Integer isDeleted;
    /**7 结算状态(与主表相同) NUMBER(3,0) */
    private Integer status;
    /**8 结算状态变更时间 DATE */
    private Date statusUpdateTime;
    /**9 创建时间 DATE */
    private Date createTime;
    /**10 创建人ID VARCHAR2(32 BYTE) */
    private String createUserId;
    /**11 创建人姓名 VARCHAR2(256 BYTE) */
    private String createUserName;
    /**12 修改时间 DATE */
    private Date updateTime;
    /**13 修改人ID VARCHAR2(32 BYTE) */
    private String updateUserId;
    /**14 修改人姓名 VARCHAR2(256 BYTE) */
    private String updateUserName;

    public static final String F_ID = "id";
    public static final String F_TASKID = "taskId";
    public static final String F_INCOMETYPE = "incomeType";
    public static final String F_AMOUNT = "AMOUNT";
    public static final String F_REMARK = "REMARK";
    public static final String F_ISDELETED = "isDeleted";
    public static final String F_STATUS = "STATUS";
    public static final String F_STATUSUPDATETIME = "statusUpdateTime";
    public static final String F_CREATETIME = "createTime";
    public static final String F_CREATEUSERID = "createUserId";
    public static final String F_CREATEUSERNAME = "createUserName";
    public static final String F_UPDATETIME = "updateTime";
    public static final String F_UPDATEUSERID = "updateUserId";
    public static final String F_UPDATEUSERNAME = "updateUserName";

    public ErpSettlementIncomeBX() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Integer getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getCreateUserId() {
        return createUserId;
    }

    @Override
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getUpdateUserId() {
        return updateUserId;
    }

    @Override
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
}
