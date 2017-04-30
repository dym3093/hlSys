package org.hpin.foreign.entity;

import org.hpin.common.core.orm.BaseEntity;

import java.util.Date;

/**
 * 报告详情里(ErpReportOrgJy)的results字段(金域报告)
 * @author Damian
 * @since 2017-02-23
 */
public class ErpReportResultsJY extends BaseEntity{

    /** 1 UUID VARCHAR2(32) */
    private String id;
    /** 2 ERP_REPORT_ORG_JY表的ID VARCHAR2(32) */
    private String orgId;
    /** 3 所属报告ID VARCHAR2(256) */
    private String reportId;
    /** 4 检测人条码(唯一的) VARCHAR2(256) */
    private String barCode;
    /** 5 单项或组合项名称 VARCHAR2(256) */
    private String name;
    /** 6 检测项类型 VARCHAR2(32) */
    private String itemType;
    /** 7 组合项代码 VARCHAR2(256) */
    private String itemGroupId;
    /** 8 组合项名称 VARCHAR2(256) */
    private String itemGroupName;
    /** 9 单项代码 VARCHAR2(256) */
    private String singleItemId;
    /** 10 单项名称 VARCHAR2(256) */
    private String singleItemName;
    /** 11 结果值 VARCHAR2(256) */
    private String result;
    /** 12 服务状态（金域） NUMBER */
    private Integer state;
    /** 13 结果值的单位( nmol/L ) VARCHAR2(256) */
    private String unit;
    /** 14 提示 VARCHAR2(256) */
    private String hint;
    /** 15 风险提示 VARCHAR2(1024) */
    private String meaning;
    /** 16 建议 VARCHAR2(1024) */
    private String suggestion;
    /** 17 参考值描述 VARCHAR2(256) */
    private String referenceDesc;
    /** 18 结果批准人 VARCHAR2(256) */
    private String authorizeName;
    /** 19 结果批准时间 DATE */
    private Date authorizeTime;
    /** 20 结果审核人 VARCHAR2(256) */
    private String checkerName;
    /** 21 结果审核时间 DATE */
    private Date checkTime;
    /** 22 结果录入人 VARCHAR2(256) */
    private String recorderName;
    /** 23 结果录入时间 DATE */
    private Date recordTime;
    /** 24 检测号 VARCHAR2(256) */
    private String testNo;
    /** 25 样品名称，如血清 VARCHAR2(256) */
    private String specimenName;
    /** 26 检测机构 VARCHAR2(256) */
    private String departmentName;
    /** 27 附属检查项目 VARCHAR2(256) */
    private String subsidiaryItem;
    /** 28 附属检测项目描述 VARCHAR2(1024) */
    private String subsidiaryItemDesc;
    /** 29 TPL VARCHAR2(256) */
    private String tpl;
    /** 30 是否删除 (0:否,1:是) NUMBER(6) */
    private Integer isDeleted;
    /** 31 创建人ID VARCHAR2(32) */
    private String createUserId;
    /** 32 创建人 VARCHAR2(256) */
    private String createUserName;
    /** 33 创建时间 DATE */
    private Date createTime;
    /** 34 修改人ID VARCHAR2(32) */
    private String updateUserId;
    /** 35 修改人姓名 VARCHAR2(256) */
    private String updateUserName;
    /** 36 修改时间 DATE */
    private Date updateTime;
    /** 37 备注信息 VARCHAR2(1024) */
    private String remark;
    /** 38 状态 NUMBER */
    private Integer status;

    public static final String F_ID = "id";
    public static final String F_ORGID = "orgId";
    public static final String F_REPORTID = "reportId";
    public static final String F_BARCODE = "barCode";
    public static final String F_NAME = "name";
    public static final String F_ITEMTYPE = "itemType";
    public static final String F_ITEMGROUPID = "itemGroupId";
    public static final String F_ITEMGROUPNAME = "itemGroupName";
    public static final String F_SINGLEITEMID = "singleItemId";
    public static final String F_SINGLEITEMNAME = "singleItemName";
    public static final String F_RESULT = "result";
    public static final String F_STATE = "state";
    public static final String F_UNIT = "unit";
    public static final String F_HINT = "hint";
    public static final String F_MEANING = "meaning";
    public static final String F_SUGGESTION = "suggestion";
    public static final String F_REFERENCEDESC = "referenceDesc";
    public static final String F_AUTHORIZENAME = "authorizeName";
    public static final String F_AUTHORIZETIME = "authorizeTime";
    public static final String F_CHECKERNAME = "checkerName";
    public static final String F_CHECKTIME = "checkTime";
    public static final String F_RECORDERNAME = "recorderName";
    public static final String F_RECORDTIME = "recordTime";
    public static final String F_TESTNO = "testNo";
    public static final String F_SPECIMENNAME = "specimenName";
    public static final String F_DEPARTMENTNAME = "departmentName";
    public static final String F_SUBSIDIARYITEM = "subsidiaryItem";
    public static final String F_SUBSIDIARYITEMDESC = "subsidiaryItemDesc";
    public static final String F_TPL = "tpl";
    public static final String F_ISDELETED = "isDeleted";
    public static final String F_CREATEUSERID = "createUserId";
    public static final String F_CREATEUSERNAME = "createUserName";
    public static final String F_CREATETIME = "createTime";
    public static final String F_UPDATEUSERID = "updateUserId";
    public static final String F_UPDATEUSERNAME = "updateUserName";
    public static final String F_UPDATETIME = "updateTime";
    public static final String F_REMARK = "remark";
    public static final String F_STATUS = "status";

    public ErpReportResultsJY() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemGroupId() {
        return itemGroupId;
    }

    public void setItemGroupId(String itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    public String getItemGroupName() {
        return itemGroupName;
    }

    public void setItemGroupName(String itemGroupName) {
        this.itemGroupName = itemGroupName;
    }

    public String getSingleItemId() {
        return singleItemId;
    }

    public void setSingleItemId(String singleItemId) {
        this.singleItemId = singleItemId;
    }

    public String getSingleItemName() {
        return singleItemName;
    }

    public void setSingleItemName(String singleItemName) {
        this.singleItemName = singleItemName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getReferenceDesc() {
        return referenceDesc;
    }

    public void setReferenceDesc(String referenceDesc) {
        this.referenceDesc = referenceDesc;
    }

    public String getAuthorizeName() {
        return authorizeName;
    }

    public void setAuthorizeName(String authorizeName) {
        this.authorizeName = authorizeName;
    }

    public Date getAuthorizeTime() {
        return authorizeTime;
    }

    public void setAuthorizeTime(Date authorizeTime) {
        this.authorizeTime = authorizeTime;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getRecorderName() {
        return recorderName;
    }

    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getTestNo() {
        return testNo;
    }

    public void setTestNo(String testNo) {
        this.testNo = testNo;
    }

    public String getSpecimenName() {
        return specimenName;
    }

    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSubsidiaryItem() {
        return subsidiaryItem;
    }

    public void setSubsidiaryItem(String subsidiaryItem) {
        this.subsidiaryItem = subsidiaryItem;
    }

    public String getSubsidiaryItemDesc() {
        return subsidiaryItemDesc;
    }

    public void setSubsidiaryItemDesc(String subsidiaryItemDesc) {
        this.subsidiaryItemDesc = subsidiaryItemDesc;
    }

    public String getTpl() {
        return tpl;
    }

    public void setTpl(String tpl) {
        this.tpl = tpl;
    }

    @Override
    public Integer getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
