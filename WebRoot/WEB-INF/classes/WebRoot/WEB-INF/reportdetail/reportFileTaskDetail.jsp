<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
function downloadAllExcel(branchCompany){
	var batchno = $.trim($("#batchno").text());
	$("#alertBackground").show();
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "/ymjy/report/reportFileTask!downloadAllFile.action",
		data: {"batchno":batchno,"branchCompany":branchCompany},
		success: function(data){
			window.location.href=data.downloadurl;
			$("#alertBackground").hide();
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
			$("#alertBackground").show();
		}
	});
}
</script>

<div class="pageFormContent" layoutH="56">
<div class="pageContent">
	<h1 class="press">${reporttask.batchno}任务信息详情</h1>
	<div class="divider"></div>
	<div class="tip"><span>任务信息</span></div>
		<p>
			<label>批次号：</label>
			<span id="batchno">${reporttask.batchno}</span>
		</p>
		<p>
			<label>任务日期：</label>
			<c:if test="${not empty reporttask.createdate}"><span>${fn:substring(reporttask.createdate,0,17)}00</span></c:if>
			<c:if test="${empty reporttask.createdate}"><span>${reporttask.createdate}</span></c:if>
		</p>
		<p>
			<label>报告文件总数：</label>
			<span>
				${reporttask.pdftotal}
			</span>
		</p>
		<p>
			<label>子公司数量：</label>
			<span>
				${reporttask.companynum}
			</span>
		</p>
	<div class="tip"><span>任务详情清单</span></div>
	
<%-- <form rel="pagerForm" id="pagerFindForms1" class=".pageForm" name="pagerFindForms" onsubmit="if(this.action != '${path}/reportdetail/erpReportdetailBatch!toReportdetailBatch.action'){this.action = '${path}/reportdetail/erpReportdetailBatch!toReportdetailBatch.action' ;} ;return navTabSearch(this);" action="${path}/reportdetail/erpReportdetailBatch!toReportdetailBatch.action" method="post">
		<input type="hidden" name="id" value="${reporttask.id}"/>
		<c:if test="${reporttask.ismatch==1}"><input type="hidden" name="filter_and_ismatch_LIKE_S" value=""/></c:if>
		<c:if test="${reporttask.ismatch!=1}"><input type="hidden" name="filter_and_ismatch_LIKE_S" value="${reporttask.ismatch}"/></c:if>
	</form> --%>
		<div class="panelBar">
			<ul class="toolBar">
				<a href="javascript:void(0)" onclick="downloadAllExcel('')">下载</a>
			</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTables"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th>批次号</th>
				<th>支公司</th>
				<th>报告数量</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${taskBeans}" var="taskdetail" varStatus="status">
				<tr>
					<td align="center">${status.count}</td>
					<td align="center">${taskdetail.batchno}</td>
					<td align="center">${taskdetail.branchcompany}</td>
					<td align="center">${taskdetail.pdfnum}</td>
					<td align="center"><a href="javascript:void(0)" onclick="downloadAllExcel('${taskdetail.branchcompany}')">下载</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div> 
</div>
