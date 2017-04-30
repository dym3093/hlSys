<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">

</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader" style="overflow-x: hidden;">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/physical/phyReport!listImportRecord.action')
	{this.action = '${path}/physical/phyReport!listImportRecord.action' ;} ;return navTabSearch(this);" 
	action="${path}/physical/phyReport!listImportRecord.action" method="post"  rel="pagerForm" id="pagerFindForm">
		<table class="pageFormContent">
			<tr>
				<input type="hidden" name="" value=""/><!-- 为了正常显示，此行解决框架BUG -->
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_importDate_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_importDate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_importDate_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_importDate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="pageContent" style="overflow-x: hidden;">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable" > 
			<thead>
			<tr>
				<!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> -->
				
				<th>序号</th>
                <th columnEnName = "userCode" columnChName = "条形码" >条形码</th>
				<th columnEnName = "userName" columnChName = "姓名">姓名</th>
				<th columnEnName = "userSex" columnChName = "性别" >性别</th>
				<th columnEnName = "userAge" columnChName = "年龄" >年龄</th>
				<th columnEnName = "userIdno" columnChName = "身份证" >身份证</th>
				<th columnEnName = "reportDate" columnChName = "报告时间" >报告时间</th>
				<th columnEnName = "importDate" columnChName = "导入时间" >导入时间</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }">
 					<td align="center">
						<%-- <input type="checkbox" name="ids" value="${entity.id}"> --%>
						${status.count}
					</td> 
					<td align="center">${entity.geneCode}</td>
					<td align="center">	${entity.userName}</td>
					<td align="center">	${entity.userSex}</td>
					<td align="center">	${entity.userAge}</td>
					<td align="center">${entity.userIdno}</td>
					<td align="center">	${entity.reportDate}</td>
					<td align="center">	${entity.importDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div> 



