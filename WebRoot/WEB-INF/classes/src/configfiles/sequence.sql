
--收房序列
create sequence aseq_bz_charge_examine maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_clean maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_clean_examine maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_clean_examine_detail maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_effectst_examine maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_examine maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_examine_feedback maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_examine_voucher maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_contract maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_contract_attach maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_contract_audit maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_contract_payment maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_delivery_charge maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_delivery_effects maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_delivery_key maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_payment_voucher maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_vacancy maxvalue 999999999999 start with 1000 increment by 1 nocache;


create sequence aseq_bz_main_part_examine maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_room_attach maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_nopayment_voucher minvalue 1 maxvalue 999999999999 start with 1007 increment by 1 nocache;

create sequence aseq_bz_room_attach maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_HIRECONTRACTREF maxvalue  999999999999 start with 1000 increment by 1 nocache; 
--出房序列
create sequence aseq_bz_earnest maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_hire_dealreport maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_clean_payment maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_contract maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_contract_attach maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_contract_audit maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_contract_payment maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_contract_record maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_delivery_charge maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_delivery_effects maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_delivery_key maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_payment_sms maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_payment_voucher maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_tv_payment maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_rent_dealreport maxvalue 999999999999 start with 1000 increment by 1 nocache;
--配置序列
create sequence aseq_bz_dispose_basic_info maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置基本信息

create sequence aseq_bz_dispose_info_attach maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置基本信息附件

create sequence aseq_BZ_CLEANING_DISPATCH maxvalue  999999999999 start with 1000 increment by 1 nocache;  --保洁派工单

create sequence aseq_BZ_CONTRACT_DISP_PLAN maxvalue  999999999999 start with 1000 increment by 1 nocache;  --合同配置计划

create sequence aseq_BZ_CONT_DISP_PLAN_DET maxvalue  999999999999 start with 1000 increment by 1 nocache;  --合同配置计划明细

create sequence aseq_bz_dispose_item_count maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置物品

create sequence aseq_bz_decoration maxvalue  999999999999 start with 1000 increment by 1 nocache;  --装修标准

create sequence aseq_bz_dispatch_main maxvalue  999999999999 start with 1000 increment by 1 nocache;  --派工单

create sequence aseq_bz_service_affiliated maxvalue  999999999999 start with 1000 increment by 1 nocache;  --派工单明细

create sequence aseq_bz_house_dispose_main maxvalue  999999999999 start with 1000 increment by 1 nocache;  --房屋配置主表

create sequence aseq_bz_house_disp_aff maxvalue  999999999999 start with 1000 increment by 1 nocache;  --房屋配置明细

create sequence aseq_bz_dispose_plan maxvalue  999999999999 start with 1000 increment by 1 nocache;  --老版配置计划

create sequence aseq_BZ_DISPOSE_PLAN_STANDARD maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置计划模板

create sequence aseq_BZ_DISP_PLAN_STAN_DETAIL maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置计划模板明细

create sequence aseq_bz_dispose_program maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置方案

create sequence aseq_bz_purchase maxvalue  999999999999 start with 1000 increment by 1 nocache;  --采购单

create sequence aseq_bz_purchase_details maxvalue  999999999999 start with 1000 increment by 1 nocache;  --采购单明细

create sequence aseq_bz_fitment_dispatch maxvalue  999999999999 start with 1000 increment by 1 nocache;  --空间优化派工单

create sequence aseq_bz_fitment_acceptance maxvalue  999999999999 start with 1000 increment by 1 nocache;  --空间优化验收单

create sequence aseq_bz_dispose_standard maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置标准

create sequence aseq_bz_disp_stan_attach maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置标准附件

create sequence aseq_bz_dispose_product maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置标准模板

create sequence aseq_bz_disp_product_version maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置标准产品版本

create sequence aseq_bz_supplier_settle maxvalue  999999999999 start with 1000 increment by 1 nocache;  --供应商结算

create sequence aseq_bz_supplier_sett_detail maxvalue  999999999999 start with 1000 increment by 1 nocache;  --供应商结算明细

create sequence aseq_bz_supplier_allocat maxvalue  999999999999 start with 1000 increment by 1 nocache;  

create sequence aseq_bz_supplier_attach maxvalue  999999999999 start with 1000 increment by 1 nocache;  --供应商附件

create sequence aseq_bz_allocat_item maxvalue  999999999999 start with 1000 increment by 1 nocache;  --配置项

create sequence aseq_bz_supplier maxvalue  999999999999 start with 1000 increment by 1 nocache;  --供应商

create sequence aseq_bz_item_code maxvalue  999999999999 start with 1000 increment by 1 nocache;  

create sequence aseq_bz_brand maxvalue  999999999999 start with 1000 increment by 1 nocache;  --品牌


--退租序列
create sequence aseq_BZ_TENANT_SURRENDER_MAIN maxvalue  999999999999 start with 1000 increment by 1 nocache;  --客户退租结算主表

create sequence aseq_BZ_TENANT_SURREND_CHECK maxvalue  999999999999 start with 1000 increment by 1 nocache;  --客户退租结算物品检验

create sequence aseq_BZ_TENANT_SURREND_REP maxvalue  999999999999 start with 1000 increment by 1 nocache;  --客户退租结算物品修理
--业务功能其他序列

create sequence aseq_bz_customer maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_house maxvalue 999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_bz_room maxvalue 999999999999 start with 1000 increment by 1 nocache;

--系统序列
create sequence aseq_sys_login_log maxvalue  999999999999 start with 1000 increment by 1 nocache;  --登录日志

create sequence aseq_sys_operation_log maxvalue  999999999999 start with 1000 increment by 1 nocache;  --操作日志

create sequence aseq_sys_operation_log maxvalue  999999999999 start with 1000 increment by 1 nocache;  --操作日志

create sequence aseq_sys_parameter_config maxvalue  999999999999 start with 1000 increment by 1 nocache;  --系统参数

create sequence aseq_sys_resource maxvalue  999999999999 start with 1000 increment by 1 nocache;  --系统资源

create sequence aseq_bz_asset_status_level maxvalue  999999999999 start with 1000 increment by 1 nocache;  --资产状态层级表

create sequence aseq_bz_customer_status maxvalue  999999999999 start with 1000 increment by 1 nocache;  --资产客户状态

create sequence aseq_bz_house_status maxvalue  999999999999 start with 1000 increment by 1 nocache;  --资产房屋状态

create sequence aseq_bz_owner_status maxvalue  999999999999 start with 1000 increment by 1 nocache;  --资产业主状态

create sequence aseq_bz_room_status maxvalue  999999999999 start with 1000 increment by 1 nocache;  --资产业主状态

create sequence aseq_sys_code_address maxvalue  999999999999 start with 1000 increment by 1 nocache;  --资产业主状态

create sequence aseq_SYS_UNITY_CODING maxvalue  999999999999 start with 1000 increment by 1 nocache; --统一编号序列




-- ************************* 配置成本序列   Start **************************

create sequence aseq_special_dispose_detail maxvalue  999999999999 start with 1000 increment by 1 nocache ;
                                                                                                        
create sequence aseq_room_dispose_detail maxvalue  999999999999 start with 1000 increment by 1 nocache; 
                                                                                                        
create sequence aseq_house_repair_detail maxvalue  999999999999 start with 1000 increment by 1 nocache; 
                                                                                                        
create sequence aseq_house_decoration_detail maxvalue  999999999999 start with 1000 increment by 1 nocache; 
                                                                                                        
create sequence aseq_home_app_access maxvalue  999999999999 start with 1000 increment by 1 nocache;     
                                                                                                        
create sequence aseq_distribution_count_cost maxvalue  999999999999 start with 1000 increment by 1 nocache; 
                                                                                                        
create sequence aseq_dispose_lock_info maxvalue  999999999999 start with 1000 increment by 1 nocache;   
                                                                                                        
create sequence aseq_bz_dispose_cost maxvalue  999999999999 start with 1000 increment by 1 nocache;     
                                                                                                        
create sequence aseq_bz_cleaning_detail maxvalue  999999999999 start with 1000 increment by 1 nocache;  
                                                                                                        
create sequence aseq_bz_broadband_service maxvalue  999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_distribution_times_code maxvalue  999999999999 start with 1000 increment by 1 nocache;


--刘丹工作环境

create sequence aseq_BZ_BUTLERTABLE maxvalue  999999999999 start with 1000 increment by 1 nocache;--管家工作台
create sequence aseq_BZ_SURRENDER_APPLY maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租申请
create sequence aseq_BZ_SUR_HIRE_DELIVERY_EFFECTS maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租出租房屋交割_各房间物品信息
create sequence aseq_BZ_SUR_HIRE_DELIVERY_CHARGE maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租出租房屋交割_各项费用信息
create sequence aseq_BZ_SUR_HIRE_DELIVERY_KEY maxvalue  999999999999 start with 1000 increment by 1 nocache；--退租出租房屋交割_钥匙及凭证信息
create sequence aseq_BZ_SUR_RENT_DELIVERY_CHARGE maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租承租房屋交割_各项费用信息
create sequence aseq_BZ_SUR_RENT_DELIVERY_KEY maxvalue  999999999999 start with 1000 increment by 1 nocache；--退租承租房屋交割_钥匙及凭证信息
create sequence aseq_BZ_SUR_RENT_DELIVERY_EFFECTS maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租承租房屋交割_各房间物品信息
create sequence aseq_BZ_SURRENDER_HISTORY maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租申请打回
create sequence aseq_BZ_SURRENDER_QUOTA maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租费用结算单
create sequence aseq_BZ_SURRENDER_HIRTENEMENT maxvalue  999999999999 start with 1000 increment by 1 nocache;--出租物业交割单 
create sequence aseq_BZ_SUR_ABNORMAL maxvalue  999999999999 start with 1000 increment by 1 nocache;-- 退租申请单_退租异常打回 


create sequence aseq_bz_rent_contract_manage maxvalue  999999999999 start with 1000 increment by 1 nocache;-- 收据库存管理
create sequence aseq_BZ_RENTCONTRACT_REJECT maxvalue  999999999999 start with 1000 increment by 1 nocache;-- 收据丢失原因
create sequence aseq_BZ_RENT_DELEREJECT maxvalue  999999999999 start with 1000 increment by 1 nocache;-- 收据作废 

--退租付款管理
create sequence aseq_BZ_SUR_PAYMENT maxvalue  999999999999 start with 1000 increment by 1 nocache;-- 退租付款管理--审核
create sequence aseq_BZ_SUR_PAYMENT_REJECT maxvalue  999999999999 start with 1000 increment by 1 nocache;-- 退租付款管理--审核打回
create sequence aseq_bz_surpay_abnormal maxvalue  999999999999 start with 1000 increment by 1 nocache;-- 退租付款管理--确认付款异常




create sequence aseq_bz_assembly_detail maxvalue  999999999999 start with 1000 increment by 1 nocache;

--退租管理
create sequence aseq_BZ_SURRENDER_APPLY maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租申请
create sequence aseq_bz_room_detail maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租管理--房间信息
create sequence aseq_bz_Decoration_Detail maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租管理--维修信息
create sequence aseq_bz_key_Certificate maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租管理--钥匙与凭证
create sequence aseq_bz_Cost_information maxvalue  999999999999 start with 1000 increment by 1 nocache;--退租管理--费用信息

-- ************************* 配置成本序列     end  **************************

--用户序列
--非退费用结算序列

create sequence aseq_BZ_CHUZUHT_ID maxvalue  999999999999 start with 1000 increment by 1 nocache; --退租序列
create sequence aseq_BZ_SURRENDER_DETAIL_ID maxvalue  999999999999 start with 1000 increment by 1 nocache; --结算明细序列
create sequence aseq_BZ_HOUSE_INFORMATION_ID maxvalue  999999999999 start with 1000 increment by 1 nocache; --房内物品信息序列
create sequence aseq_BZ_MAINTAIN_ID maxvalue  999999999999 start with 1000 increment by 1 nocache; --维修费用序列
create sequence aseq_BZ_CUSTOMERS_CHECKIN_ID maxvalue  999999999999 start with 1000 increment by 1 nocache; --客户入住信息序列
--合同状态序列

create sequence aseq_BZ_RENTCONTRACT_CHANGE_ID maxvalue  999999999999 start with 1000 increment by 1 nocache; --出租合同状态变更序列

create sequence aseq_BZ_HIRECONTRACT_CHANGE_ID maxvalue  999999999999 start with 1000 increment by 1 nocache; --承租合同状态变更序列


create sequence aseq_bz_rentcontract_renfobj maxvalue  999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_BZ_AUDIT_PAYMENT_HISTORY maxvalue  999999999999 start with 1000 increment by 1 nocache;--付款单审核历史序列
create sequence aseq_BZ_NOPAYMENT_HISTORY  maxvalue  999999999999 start with 1000 increment by 1 nocache;--扣款单审核历史序列
create sequence aseq_BZ_NOPAYMENT_TYPE maxvalue  999999999999 start with 1000 increment by 1 nocache;--扣款单审核历史序列






create sequence aseq_bz_delRent_voucher maxvalue  999999999999 start with 1000 increment by 1 nocache;--承租合同收款单作废序列

create sequence aseq_BZ_HIRECONTRACT_CHANGE_ID maxvalue  999999999999 start with 1000 increment by 1 nocache; --承租合同状态变更序列


create sequence aseq_bz_rentcontract_renfobj maxvalue  999999999999 start with 1000 increment by 1 nocache;



create sequence aseq_BZ_HIRECONTRACT_CHANGE_ID maxvalue  999999999999 start with 1000 increment by 1 nocache; --承租合同状态变更序列


create sequence aseq_bz_rentcontract_renfobj maxvalue  999999999999 start with 1000 increment by 1 nocache;


create sequence aseq_BZ_hpin_rent_collectionpay maxvalue  999999999999 start with 1000 increment by 1 nocache;--催收管理

create sequence aseq_BZ_SURD_APPLY_AUDIT_HISTY maxvalue  999999999999 start with 1000 increment by 1 nocache;--催收管理



create sequence aseq_BZ_SURD_VOUCHER maxvalue  999999999999 start with 1000 increment by 1 nocache; --退租收款单
create sequence aseq_BZ_AUVO_HIS maxvalue  999999999999 start with 1000 increment by 1 nocache; --退租收款单审核历史

create sequence aseq_bz_reserve_info maxvalue  999999999999 start with 1000 increment by 1 nocache;

create sequence aseq_HM_FULL_RESBLOCK start with 1000 increment by 1 ;


CREATE SEQUENCE ASEQ_SYS_SMS_TEMPLATE MINVALUE 1 MAXVALUE 999999999999 INCREMENT BY 1 START WITH 500 NOCACHE NOORDER NOCYCLE; --短信模版
CREATE SEQUENCE ASEQ_SYS_RUSH_CONFIG MINVALUE 1 MAXVALUE 999999999999 INCREMENT BY 1 START WITH 500 NOCACHE NOORDER NOCYCLE; --催收时限设置
create sequence ASEQ_SMS_SEND_RECORD minvalue 1 maxvalue 999999999999 start with 522 increment by 1 nocache; --短信记录

CREATE SEQUENCE ASEQ_SYS_MAIL_TEMPLATE MINVALUE 1 MAXVALUE 999999999999 START WITH 500 INCREMENT BY 1 NOCACHE;--邮件模版
create sequence ASEQ_MAIL_SEND_RECORD minvalue 1 maxvalue 999999999999 start with 500 increment by 1 nocache; --邮件记录

	--催收序列
 	create sequence aseq_BZ_RENT_RUSH_FLLOW maxvalue  999999999999 start with 1000 increment by 1 nocache; --催收跟进
 	create sequence aseq_bz_rent_rush_record maxvalue  999999999999 start with 1000 increment by 1 nocache; --催收跟进记录
    
create sequence ASEQ_BZ_RENT_DRIVE_AWAY minvalue 1 maxvalue 999999999999 start with 30 increment by 1 nocache;  --清退派工表
create sequence ASEQ_BZ_RENT_DRIVE_CONFIRM minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache; --清退确认表
create sequence ASEQ_BZ_RENT_DRIVE_RUSH minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;  --清退跟进表


create sequence aseq_bz_collenction_type maxvalue  999999999999 start with 1000 increment by 1 nocache; --收款方式

	create sequence aseq_BZ_PERFORMANCE_VANCANT maxvalue  999999999999 start with 1000 increment by 1 nocache; --业绩扣除


create sequence aseq_datachange_custorm  maxvalue  999999999999 start with 1000 increment by 1 nocache; --租客/业主信息变更
create sequence aseq_datachange_custormref  maxvalue  999999999999 start with 1000 increment by 1 nocache; --租客/业主信息变更信息项

create sequence aseq_datachange_housebelong  maxvalue  999999999999 start with 1000 increment by 1 nocache; --房源所属变更=======

create sequence aseq_bz_customer_share  maxvalue  999999999999 start with 1000 increment by 1 nocache; --租客/业主信息合作人信息


create sequence aseq_bz_product_ziroom  maxvalue  999999999999 start with 1000 increment by 1 nocache; --房源产品管理

create sequence aseq_bz_product_ziroom_version  maxvalue  999999999999 start with 1000 increment by 1 nocache; --自如产品版本

create sequence aseq_bz_product_style  maxvalue  999999999999 start with 1000 increment by 1 nocache; --装修风格

create sequence aseq_bz_supplier_org  maxvalue  999999999999 start with 1000 increment by 1 nocache; --供应商部门关系表序列

create sequence aseq_bz_product_rules  maxvalue  999999999999 start with 1000 increment by 1 nocache; --装修规则

create sequence aseq_bz_standard_rule  maxvalue  999999999999 start with 1000 increment by 1 nocache; --标配规则规则

