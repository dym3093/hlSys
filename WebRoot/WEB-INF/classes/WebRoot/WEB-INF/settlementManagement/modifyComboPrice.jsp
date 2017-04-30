<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	
});

//变更用于复选框
function changeProduct() {
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择你要变更的信息！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息进行变更！');
		return ;		
	}else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyConference", "../events/erpConference!toModifyConference.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	}
}
//关闭弹窗
function isClose(){
	$.pdialog.closeCurrent();
}
</script>
<div class="tip"><span>修改价格</span></div>
<div class="pageHeader" style="background:white">
	<form class="pageForm required-validate" id="modifyPrice" action="${path}/combo/erpComboPrice!modifyErpComboPrice.action" method="post">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>当前价格：</label> 
					<input type="text" name="historyPrice" value="${price}" readonly="readonly" />
					<input type="hidden" name="comboPriceId" value="${comboPriceId}"/>
					
				</td>
				<td>
					<label>更改价格：</label> 
					<input onkeyup="value=value.replace(/[^\d\.]/g,'')" type="text" name="currentPrice" value="" class="required"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
         		<td>
         			<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="validateCallback('#modifyPrice', navTabAjaxDone),isClose()">修改</button></div></div>
				</td>
			</tr>
		</table>
	</form>
</div>
