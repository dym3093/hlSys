<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>    
<script type="text/javascript">

//关闭弹窗
function isClose(){
	$.pdialog.closeCurrent();
}


</script>
<style>
table td{
word-break: keep-all;
white-space:nowrap;
}
table th{
word-break: keep-all;
white-space:nowrap;
}
</style>
<div class="pageHeader" style="background:white">
	<form rel = "pagerForm" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/report/xreportPrintTask!confirm.action" method="post">
		<div class="divider"></div>
		<div class="tip"><span>确认签收快递</span></div>
		<div class="searchBar">
			<table>
			<tr>
				<td align="center">
					<label>签收人：</label>
					<input type="text" name="signPerson" value="${person}" class="required"/>
					<input type="hidden" name="id" value="${id}" />
				</td>
				<td align="center">
					<label>签收时间：</label>
					<input id="signingTime" style="width: 110px;" name="signTime" datefmt="yyyy-MM-dd"  value="${now}" type="text" class="date required" />
				</td>
			</tr>	
			<tr>
				<td></td>
				<td></td>
				<td>
					<div class="buttonActive">
			            <div class="buttonContent">
			             	<button id="query" type="submit" onclick="isClose()">确认签收</button>
			            </div>
		          	</div>
				</td>
			</tr>
			</table>
		</div>
	</form>
</div>
