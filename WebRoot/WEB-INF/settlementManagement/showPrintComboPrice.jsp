<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<style type="text/css">
	select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
</style>
<script type="text/javascript" language="javascript">

function closeDialog(){
	$.pdialog.closeCurrent();	
}

$(function(){
	getCombos();
});

$("#comboSel").change( function() {	//状态下拉框
	var selectVal=$("#comboSel option:selected").val();
	$("#comboTxt").val(selectVal);
});

function getCombos(){
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/settlementManagement/erpPrintComboCost!setComboSelect.action",
		success: function(data){
//			var s= eval("("+data+")");
			var option="<option value=''>---请选择---</option>";
			var comboName = $("#comboTxt").val();
			var combos = eval("("+data+")").combo;
			for(var i=0;i<combos.length;i++){	
				if(comboName==combos[i].comboName){
					option += "<option value='"+combos[i].comboName+"' selected='selected'>"+combos[i].comboName+"</option>";
				}else{
					option += "<option value='"+combos[i].comboName+"'>"+combos[i].comboName+"</option>";
				}
			}
			//将节点插入到下拉列表中
			$("#comboSel").append(option);
			},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

function editPayedPrice2(){
	var id = $("#costId").val();
	var price =$("#price").val();
	var combo = $("#comboTxt").val();
	var printTaskId =$("#printTaskId").val();
	if (isNaN(price)) { 
		alertMsg.error("请输入正确的价格"); 
		$("#price").focus().select();
		return;
	} 
	$.ajax({
		type: "post",
		cache: false,
		data:{"id":id,"price":price,"combo":combo},
		url: "../settlementManagement/erpPrintCompanySettleTask!editPayedPrice2.action",
		success: function(data){
			if(eval("("+data+")").count==1){
				alertMsg.correct("修改成功");
				navTab.closeCurrentTab();
				navTab.openTab("listPrintSettlementDetail", "${path}/settlementManagement/erpPrintCompanySettleTask!listPrintSettlementDetail.action", 
						{ title:"打印报告费用明细", fresh:true, data:{"id":printTaskId} });
				$.pdialog.closeCurrent();
			}else{
				alertMsg.error("修改失败");
			}
		},
		error:function(){
			alertMsg.error('服务发生异常，请稍后再试');
			return;
		}
	});
}

</script>

<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="return validateCallback(this, dialogAjaxDone);" action="${path}/settlementManagement/erpPrintCompanySettleTask!editPayedPrice2.action" method="post" id="pagerFindForm">
		<div class="searchBar">
			<table>
				<tr hidden="hidden">
					<td>
						<label>ID：</label> 
						<input id="costId" type="text" name="id" value="${id}"/>
					</td>
					<td>
						<label>printTaskId：</label> 
						<input id="printTaskId" type="text" name="printTaskId" value="${printTaskId}"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<label>套餐名称：</label> 
						<input id="comboTxt" name="comboTxt" type="hidden" value="${combo}"/>
						<select id="comboSel" name="comboSel"></select>
					</td>
				</tr>
				
				<tr>
					<td>
						<label>套餐价格：</label> 
						<input type="text" name="comboPrice" readonly="readonly" value="${price}"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<label>实际价格：</label> 
						<input id="price" type="text" name="payedPrice" value="${payedPrice}"/>
						<input id="price2" type="hidden" name="payedPrice" value="${payedPrice}"/>
					</td>
				</tr>
				
				<tr>
					<td style="padding-left: 244px;">														
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="editPayedPrice2()">提交</button></div></div>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog()">返回</button></div></div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
