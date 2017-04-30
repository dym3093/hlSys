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
	<form rel = "pagerForm" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/reportdetail/erpPrintTask!complete.action" method="post">
	<div class="divider"></div>
	<div class="tip"><span>录入发货信息</span></div>
	<div class="searchBar">
			<table>
			<tr>
				<td align="center">
					<label>快递单号：</label>
					<input type="text" name="expressNo" value="" class="required"/>
					<input type="hidden" name="id" value="${id}" />
				</td>
				<td align="center">
					<label>快递公司：</label>
					<input type="text" name="expressConpany" value="" class="required"/>
				</td>
			</tr>	
			<tr>
				<td></td>
				<td></td>
				<td>
					<div class="buttonActive">
			            <div class="buttonContent">
			             	<button id="query" type="submit" onclick="isClose()">确认</button>
			            </div>
		          	</div>
				</td>
			</tr>
			</table>
	</div>
	</form>
</div>
