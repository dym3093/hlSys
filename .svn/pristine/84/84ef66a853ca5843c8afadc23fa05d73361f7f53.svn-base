<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
 
<script type="text/javascript">

</script>
<style>
#expressTable tr{
height: 40px;
}
table td{
word-break: keep-all;
white-space:nowrap;
}
table th{
word-break: keep-all;
white-space:nowrap;
}
#expressTable label{
margin-top:8px;
font-size: 16px;
}
#expressTable input{
height: 24px;
}

</style>
<div >
	<div class="tip">
		<span>历史变更信息</span>
	</div>
	<form id="form" name="form" class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);"
		 method="post">
			<table class="pageFormContent" id="expressTable" style="overflow: auto;height:480px ">
			<input type="hidden" id="reportId" value="${reportId }"/>
			<c:forEach items="${hisList}" var="expressHis" varStatus="status">
				<tr>
					<td>
						<label>变更时间：</label> 
						<input type="text" value="${fn:substring(expressHis.createTime,0,19)}"/>
					</td>
					<td>
						<label>快递公司：</label> 
						<input type="text" value="${expressHis.expressCompany}"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>快递单号：</label> 
						<input type="text" value="${expressHis.expressNo}"/>
					</td>
					<td>
						<label>快递重量：</label> 
						<input style="width: 80px;" type="text" value="${expressHis.expressWeight}"/>
						<div style="font-size: 24px;margin-top: 15px;">kg</div>
					</td>
				</tr>
				<tr>
					<td>
						<label>快递费用：</label> 
						<input style="width: 80px;" type="text" value="${expressHis.expressCost}"/>
						<div style="font-size: 24px;margin-top: 15px;">元</div>
					</td>
					<td>
						<label>变更原因：</label> 
						<input type="text" value="${expressHis.reason}"/>
					</td>
				</tr>
				<tr><td colspan="2">
					<div style="border-bottom:1px dashed #000000"></div>
				</td></tr>
			</c:forEach>
		</table>
	</form>
</div>
