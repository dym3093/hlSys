<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
  <style type="text/css">
	   .suggest_out{background-color:#FFFFFF;color:black}
	   .suggest_over{background-color:#ECE9D8}
    </style> 
<script type="text/javascript">
	function closeDialog(){
		$.pdialog.closeCurrent();
	}
  	
	function fnDelivery(){
		var expressComapny = $.trim($("#expressComapny").val());
		var expressNum = $.trim($("#expressNum").val());
		var expressNo = $.trim($("#expressNo").val());
		var total = $.trim($("#total").val());
		var epressPrice = $.trim($("#epressPrice").val());
		var basePrice = $.trim($("#basePrice").val());
		var batNo = $.trim($("#batNo").val());
		var applyedId = $.trim($("#applyedId").val());
		var houseId = $.trim($("#houseId").val());
		var typeCode = $.trim($("#typeCode").val());
		var houseName = $.trim($("#houseName").val());
		var storeTypeId = $.trim($("#storeTypeId").val());
		var flag = $.trim($("#flag").val());
		if(expressComapny=="" || expressNum=="" || expressNo=="" || total=="" || epressPrice=="" || basePrice==""){
			alertMsg.error("提交数据不完整,请改正后再提交");
			return;
		}
		var dataJson = "{"+"\"expressComapny\":\""+expressComapny+"\","+"\"expressNum\":\""+expressNum+"\","+"\"expressNo\":\""+expressNo+"\","+"\"total\":\""+total
			+"\","+"\"epressPrice\":\""+epressPrice+"\","+"\"basePrice\":\""+basePrice+"\","+"\"batNo\":\""+batNo+"\","+"\"houseId\":\""+houseId
			+"\","+"\"typeCode\":\""+typeCode+"\","+"\"houseName\":\""+houseName+"\","+"\"storeTypeId\":\""+storeTypeId+"\","+"\"flag\":\""+flag+"\","+"\"applyedId\":\""+applyedId+"\"}";
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/warehouse/storeDelivery!setStoreProduce.action",
			data:{"dataJson":dataJson},
			success: function(data){
				if(eval("("+data+")").count==1){
					alertMsg.info("提交成功");
					navTab.closeCurrentTab();
					navTab.openTab("delivery", "${path}/warehouse/storeDelivery!modifyDeliveryInfo.action", { title:"派发申请【"+batNo+"】", fresh:true, data:{id:applyedId,batNo:batNo } });	
					$.pdialog.closeCurrent();
				}else{
					alertMsg.error("提交失败");
				}
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	
</script>
 
<div class="pageHeader">
	<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="${path}/warehouse/storeDelivery!setStoreProduce.action" method="post" id="jyDelivery">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>快递公司：</label>
					<input id="expressComapny" type="text" name="filter_and_emsName_LIKE_S"  class="required"  value="${filter_and_emsName_LIKE_S}"/>
				</td>
				<td>
					<label>数量：</label>
					<input id="expressNum" type="text" name="filter_and_remark_LIKE_S" readonly="readonly" class="required" value="${remark}"/>
				</td>
			</tr>
			
			<tr> 
				<td>
					<label>快递单号：</label>
					<input id="expressNo" type="text" name="filter_and_emsNo_LIKE_S"  class="required" value="${filter_and_emsNo_LIKE_S}"/>
				</td>
				<td>
					<label>总额：</label>
					<input id="total" type="text" name="filter_and_totalPrice_LIKE_S"  class="required" value="${filter_and_totalPrice_LIKE_S}"/>
				</td>
	       	</tr>
	       	
			<tr> 
	       	 	<td>
	       	 		<label>快递费：</label>
	       	 		<input id="epressPrice" type="text" name="filter_and_emsPrice_LIKE_S"  class="required" value="${filter_and_emsPrice_LIKE_S}"/>
	       	 	</td>
	       	 	<td>
	       	 		<label>成本：</label>
	       	 		<input id="basePrice" type="text" name="filter_and_basePrice_LIKE_S"  class="required" value="${filter_and_basePrice_LIKE_S}"/>
	       	 	</td>
			</tr>
				<td></td>
				<td style="padding-left:245px;">
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="fnDelivery()">发货</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog()">返回</button></div></div>
				</td>
			</tr> 
	       	<tr hidden="hidden">
	       		<td>
	       			<label>申请单号：</label>
	       			<input id="batNo" type="text" name="filter_and_batchNo_LIKE_S" value="${batchNo}"/>
	       			<input id="applyedId" type="text" name="filter_and_applyedId_LIKE_S" value="${applyedId}"/>
	       		</td> 	
	       		<td>
	       			<label>仓库ID：</label>
	       			<input id="houseId" type="text" name="filter_and_id_LIKE_S" value="${id}"/>
	       		</td> 	
			</tr>
	       	<tr hidden="hidden">
	       		<td>
	       			<label>品类：</label>
	       			<input id="typeCode" type="text" name="filter_and_typeCode_LIKE_S" value="${typeCode}"/>
	       		</td> 	
	       		<td>
	       			<label>仓库名：</label>
	       			<input id="houseName" type="text" name="filter_and_wareHouse_LIKE_S" value="${wareHouse}"/>
	       		</td> 	
			</tr>
	       	<tr hidden="hidden">
	       		<td>
	       			<label>storeTypeId：</label>
	       			<input id="storeTypeId" type="text" name="filter_and_storeTypeId_LIKE_S" value="${storeTypeId}"/>
	       		</td> 	
	       		<td>
	       			<label>是否发货：</label><!-- 0：部分发货,1：全部发货 -->
	       			<input id="flag" type="text" name="filter_and_flag_LIKE_S" value="${flag}"/>
	       		</td> 	
			</tr>
		</table>
	</div>
	</form>
</div>