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

//关闭弹窗
function isClose(){
	$.pdialog.closeCurrent();
}
</script>
<div class="tip"><span>修改价格</span></div>
<div class="pageHeader" style="background:white">
	<form class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);" id="modifyPrice" action="${path}/settlementManagement/erpSettlementTaskJY!modifyPriceEnd.action" method="post">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>当前价格：</label> 
					<input type="text" name="setmealPrice" value="${setmealPrice}" readonly="readonly" />
					<input type="hidden" name="setCustomerId" value="${setCustomerId}"/>
                    <input type="hidden" name="navTabId" value="${navTabId}"/>
				</td>
				<td>
					<label>更改价格：</label> 
					<input type="text" name="currentPrice" value="" class="required"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
         		<td>
         			<div class="buttonActive"><div class="buttonContent"><button type="submit">改价</button></div>
				</td>
			</tr>
		</table>
	</form>
</div>
