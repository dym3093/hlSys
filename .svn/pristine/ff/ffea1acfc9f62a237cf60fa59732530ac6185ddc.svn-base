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
	<form onsubmit="if(this.action != '${path }/statistics/queryaction!queryReport.action'){this.action = '${path }/statistics/queryaction!queryReport.action' ;};return navTabSearch(this); " action="${path }/statistics/queryaction!queryReport.action" method="post" rel="pagerForm" id="pagerReportFindForm">
	<input type="hidden" name="configId" value="${actionMap['configId']}">
	<div class="searchBar">
		<table class="pageFormContent" style="overflow: hidden;">
			<tr>
				<td><label>截止日期：</label>
				<input type="text" name="filter_and_createTime_GE_T" value="${ actionMap.filter_and_createTime_GE_T }"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01'})" class="required"  readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearInput()" >重置</button></div></div>
				</td>
				<td>&nbsp;</td>
			</tr>
	</div>
	</form>
</div>

<div class="tip" style="margin-bottom:0px; width: 99%">
	<span>报表</span>
</div>
<div class="pageContent">
	<div class="panelBar">
	<%-- <web:security tag="comboAdmin"> --%>
		<ul class="toolBar">
			<web:exportExcelTag4Jdbc 
				pagerFormId="pagerFindForm" 
				pagerMethodName="queryResults" 
				actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
				informationTableId="exportTable"
				fileName="stat170"
				configId="stat170"
				></web:exportExcelTag4Jdbc>
		</ul>
	<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="120" id="exportExcelTable">
		<thead>
			<tr>
				<th nowrap="nowrap" width="35px">序号</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="0" columnChName="日期" >日期</th>
				<th nowrap="nowrap" width="150px" export="true" columnEnName="1" columnChName="项目编码" >项目编码</th>
				<th nowrap="nowrap" width="200px" export="true" columnEnName="2" columnChName="项目名称" >项目名称</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="3" columnChName="项目负责人" >项目负责人</th>
				<th nowrap="nowrap" width="80px"  export="true" columnEnName="4" columnChName="邀约人数" >邀约人数</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="members" varStatus="status">
			<tr target="sid_user" >
				<td align="center">${ status.count }</td>
				<td align="center" nowrap="nowrap">${members[0] }</td>
				<td align="center" nowrap="nowrap" >${ members[1] }</td>
				<td align="center" nowrap="nowrap" >${ members[2] }</td>
				<td align="center" nowrap="nowrap" >${ members[3] }</td>
			    <td align="center" nowrap="nowrap">${ members[4] }</td> 
		</c:forEach>
		</tbody>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td>合计:</td>
			<td align="center" >${countSum}</td>
		</tr>
	</table>
</div>
