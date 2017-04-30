<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

$(function(){
	$("#price").focus().select();
});

function closeDialog(){
	$.pdialog.closeCurrent();	
}

function editComboCost(){
	var id = $("#costId").val();
	var price =$("#price").val();
	if (isNaN(price)) { 
		alertMsg.error("请输入正确的价格"); 
		$("#price").focus().select();
		return;
	} 
	$.ajax({
		type: "post",
		cache: false,
		data:{"id":id,"price":price},
		url: "../settlementManagement/erpPrintComboCost!editComoboCost.action",
		success: function(data){
			if(eval("("+data+")").count==1){
				alertMsg.correct("修改成功");
				navTabSearch(this);
				$.pdialog.closeCurrent();
			}else{
				alertMsg.correct("修改失败");
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
	<form id="pagerFindForm" onsubmit="return validateCallback(this, dialogAjaxDone);" action="${path}/settlementManagement/erpPrintComboCost!editComoboCost.action" method="post" id="pagerFindForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr hidden="hidden">
					<td>
						<label>ID：</label> 
						<input id="costId" type="text" name="filter_and_printCompanyId_LIKE_S" value="${id}"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<label>打印公司：</label> 
						<input type="text" name="filter_and_printCompany_LIKE_S" readonly="readonly" value="${company}"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<label>套餐名称：</label> 
						<input type="text" name="filter_and_comboName_LIKE_S" readonly="readonly" value="${combo}"/>
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
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="editComboCost()">提交</button></div></div>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog()">返回</button></div></div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
