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

$(function(){
	getPrintCompanys();
	getCombos();
});

function getPrintCompanys(){
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/settlementManagement/erpPrintComboCost!setPrintCompanySelect.action",
		success: function(data){
//			var s= eval("("+data+")");
			var option="<option value=''>---请选择---</option>";
			var companys = eval("("+data+")").company;
			for(var i=0;i<companys.length;i++){	
				option += "<option value='"+companys[i].id+"'>"+companys[i].name+"</option>";
			}
			//将节点插入到下拉列表中
				$("#printCompany").append(option);
			},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

function getCombos(){
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/settlementManagement/erpPrintComboCost!setComboSelect.action",
		success: function(data){
//			var s= eval("("+data+")");
			var option="<option value=''>---请选择---</option>";
			var combos = eval("("+data+")").combo;
			for(var i=0;i<combos.length;i++){	
				option += "<option value='"+combos[i].id+"'>"+combos[i].comboName+"</option>";
			}
			//将节点插入到下拉列表中
			$("#comboName").append(option);
			},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

function closeDialog(){
	$.pdialog.closeCurrent();	
}

function addComboCost(){
	var price = $.trim($("#price").val());
	var companyId = $.trim($("#printCompany option:selected").val());
	var comboId = $.trim($("#comboName option:selected").val());
	if(companyId=="" || comboId==""){
		alertMsg.error("请选择对应的套餐或者打印公司");
		return;
	}
	if (isNaN(price) || price=="") { 
		alertMsg.error("请输入正确的价格"); 
		$("#price").focus().select();
		return;
	} 
	$.ajax({
		type: "post",
		cache: false,
		data:{"companyId":companyId,"comboId":comboId,"price":price},
		url: "../settlementManagement/erpPrintComboCost!addComoboCost.action",
		success: function(data){
			var str = eval("("+data+")");
			if(str.count==1){
				alertMsg.correct("新增成功");
				navTabSearch(this);
				$.pdialog.closeCurrent();
			}else if(str.count==2){
				alertMsg.error("该打印公司已有该套餐");
			}else{
				alertMsg.error("提交失败");
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
	<form id="pagerFindForm" onsubmit="return validateCallback(this, dialogAjaxDone);" action="${path}/settlementManagement/erpPrintComboCost!addComoboCost.action" method="post" id="pagerFindForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td></td>
					<td>
						<label>打印公司：</label> 
						<select id="printCompany" name="filter_and_printCompany_LIKE_S"></select>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<label>套餐名称：</label> 
						<select id="comboName" name="filter_and_comboName_LIKE_S"></select>
					</td>
				</tr>
				
				<tr>
					<td></td>
					<td>
						<label>套餐价格：</label> 
						<input id="price" type="text" name="filter_and_price_LIKE_S" value="${price}"/>
					</td>
				</tr>
				
				<tr>
					<td></td>
					<td style="padding-left: 244px;">														
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="addComboCost()">提交</button></div></div>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog()">返回</button></div></div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
