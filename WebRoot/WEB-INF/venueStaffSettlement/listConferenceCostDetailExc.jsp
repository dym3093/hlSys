<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

	
</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/venueStaffSett/conferCostMan!listConferenceCostDetailExc.action'){this.action = '${path}/venueStaffSett/conferCostMan!listConferenceCostDetailExc.action' ;} ;return navTabSearch(this);" action="${path}/venueStaffSett/conferCostMan!listConferenceCostDetailExc.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>会议号：</label> 
					<input type="text" name="filter_and_conferenceNo_LIKE_S" value="${filter_and_conferenceNo_LIKE_S}"/>
				</td>
				<td>
					<label>起始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td>
					<label>结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
         			<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
			
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp"/>
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40" align="center" nowrap="true">序号</th>
				<th width="100" align="center" nowrap="true">会议号</th>
				<th width="100" align="center" nowrap="true">姓名</th>
				<th width="80" align="center" nowrap="true">类别</th>
				<th width="80" align="center" nowrap="true">天数</th>
				<th width="80" align="center" nowrap="true">航班</th>
				<th width="80" align="center" nowrap="true">描述</th>
				<th width="80" align="center" nowrap="true">金额</th>
				<th width="80" align="center" nowrap="true">错误原因</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="costExc" varStatus="status">
					<tr target="sid_user" rel="${costExc.id}">
					<td align="center">${status.count}</td>
					<td align="center">${costExc.conferenceNo}</td>
					<td align="center">${costExc.name}</td>
					<td align="center">${costExc.costName}</td>
					<td align="center">${costExc.days}</td>
					<td align="center">${costExc.flight}</td>
					<td align="center">${costExc.describe}</td>
					<td align="center">${costExc.cost}</td>
					<td align="center">${costExc.exceptionDescribe}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div> 