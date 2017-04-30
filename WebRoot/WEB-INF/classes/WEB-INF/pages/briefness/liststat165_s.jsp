<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
 function reset() {
	 $("#clearText").click(function(){
		 $("#detectionCompanyId").val("");
		 $(".textInput").val("");
		 $("#endDate").val("${endDate }");
	 }) ;
 };
 
 	function clearInput(){
		$(':input','#pagerReportFindForm',navTab.getCurrentPanel())  
		 .not(':button, :submit, :reset, :hidden')  
		 .val('');
	}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${path }/report/reportQuery!queryProvinceEvents.action" method="post" rel="pagerForm" id="pagerReportFindForm">
	<input type="hidden" name="configId" value="${actionMap['configId']}">
	<div class="searchBar">
		<table class="pageFormContent" style="overflow: hidden;">
		</table>
	</div>
	</form>
</div> 

<div class="tip" style="margin-bottom:0px; width: 99%">
	<span>报表</span>
</div>
<div class="pageContent">
	<div class="panelBar">
	<%-- <web:security tag="comboAdmin"> --%>
		<%-- <ul class="toolBar">
			<web:exportExcelTag4Jdbc 
				pagerFormId="pagerFindForm" 
				pagerMethodName="queryResults" 
				actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
				informationTableId="exportTable"
				fileName="stat165s"
				configId="stat165_s"
				></web:exportExcelTag4Jdbc>
		</ul> --%>
	<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="60" id="exportExcelTable">
		<thead>
			<tr>
				<th nowrap="nowrap" width="35px">序号</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="0" columnChName="采样日期" >采样日期</th>
				<th nowrap="nowrap" width="150px" export="true" columnEnName="1" columnChName="客户检测码" >客户检测码</th>
				<th nowrap="nowrap" width="200px" export="true" columnEnName="2" columnChName="姓名" >姓名</th>
				<th nowrap="nowrap" width="200px" export="true" columnEnName="3" columnChName="性别" >性别</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="4" columnChName="年龄" >年龄</th>
				<th nowrap="nowrap" width="80px"  export="true" columnEnName="5" columnChName="身份证" >身份证</th>
				<th nowrap="nowrap" width="180px" export="true" columnEnName="6" columnChName="电话" >电话</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="7" columnChName="二维码对应支公司" >二维码对应支公司</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="8" columnChName="设备所在支公司" >设备所在支公司</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="9" columnChName="套餐名称" >套餐名称</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="10" columnChName="营销员" >营销员</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="11" columnChName="营销员工号" >营销员工号</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="12" columnChName="身高" >身高</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="13" columnChName="体重" >体重</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="members" varStatus="status">
			<tr target="sid_user" >
				<td align="center">${ status.count }</td>
				<td align="center" nowrap="nowrap">${ members[0] }</td>
				<td align="center" nowrap="nowrap" >${ members[1] }</td>
				<td align="center" nowrap="nowrap" >${ members[2] }</td>
				<td align="center" nowrap="nowrap" >${ members[3] }</td>
			    <td align="center" nowrap="nowrap">${ members[4] }</td> 
			    <td align="center" nowrap="nowrap">${ members[5] }</td> 
			    <td align="center" nowrap="nowrap">${ members[6] }</td> 
			    <td align="center" nowrap="nowrap">${ members[7] } </td> 
			    <td align="center" nowrap="nowrap">${ members[8] }</td> 
			    <td align="center" nowrap="nowrap">${ members[9] }</td> 
			    <td align="center" nowrap="nowrap">${ members[10] }</td> 
			    <td align="center" nowrap="nowrap">${ members[11] }</td> 
			    <td align="center" nowrap="nowrap">${ members[12] }</td> 
			    <td align="center" nowrap="nowrap">${ members[13] }</td> 
		</c:forEach>
		</tbody>
	</table>
</div>
