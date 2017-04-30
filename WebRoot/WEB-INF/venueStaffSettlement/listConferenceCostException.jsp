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
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/venueStaffSett/conferCostMan!listExportExceptionInfo.action'){this.action = '${path}/venueStaffSett/conferCostMan!listExportExceptionInfo.action' ;} ;return navTabSearch(this);" action="${path}/venueStaffSett/conferCostMan!listExportExceptionInfo.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>会议号：</label> 
					<input type="text" name="filter_and_conferenceNo_LIKE_S" value="${filter_and_conferenceNo_LIKE_S}"/>
				</td>
				<td>
					<label>起始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_conferenceDate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td>
					<label>结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_conferenceDate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
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
				<th width="80" align="center" nowrap="true">出差补助</th>
				<th width="80" align="center" nowrap="true">讲师费</th>
				<th width="80" align="center" nowrap="true">市内交通费</th>
				<th width="80" align="center" nowrap="true">往返交通费</th>
				<th width="80" align="center" nowrap="true">住宿费</th>
				<th width="80" align="center" nowrap="true">采样费</th>
				<th width="80" align="center" nowrap="true">其他</th>
				<th width="80" align="center" nowrap="true">小计</th>
				<th width="200" align="center" nowrap="true">描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="costException" varStatus="status">
					<tr target="sid_user" rel="${costException.id}">
					<td align="center">${status.count}</td>
					<td align="center">${costException.conferenceNo}</td>
					<td align="center">${costException.name}</td>
					<td align="center">${costException.travelCost}</td>
					<td align="center">${costException.instructorCost}</td>
					<td align="center">${costException.cityTrafficCost}</td>
					<td align="center">${costException.provinceTrafficCost}</td>
					<td align="center">${costException.hotelCost}</td>
					<td align="center">${costException.sampleCost}</td>
					<td align="center">${costException.otherCost}</td>
					<td align="center">${costException.amount}</td>
					<td align="center">
						<c:if test="${costException.descripe==0}">没有该场次信息</c:if>
						<c:if test="${costException.descripe==1}">该场次已存在该用户</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div> 