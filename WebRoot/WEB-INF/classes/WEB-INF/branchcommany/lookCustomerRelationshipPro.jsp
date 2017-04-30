<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>  
<script type="text/javascript">
	function clearInput(){
		$(':input',$.pdialog.getCurrent())  
		 .not(':button, :submit, :reset, :hidden')  
		 .val('')  
		 .removeAttr('selected');  
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
	<div class="searchBar"  id="manyQue">
	
		<form onsubmit="return dwzSearch(this, 'dialog');" action="${path }/resource/customerRelationShip!findCustomerRelshipPro.action" method="post" rel = "pagerForm" id="pagerFindForm" >
			<table class="pageFormContent">
				<tr>
					<td>
						<label>项目编码：</label>
						<input id="projectCode" type="text" name="filter_and_projectCode_LIKE_S"  value="${filter_and_projectCode_LIKE_S}"/>
					</td>
					<td>
						<label>项目名称：</label>
						<input id="projectName" type="text" name="filter_and_projectName_LIKE_S"  value="${filter_and_projectName_LIKE_S}"/>
					</td>
					</tr>
					
					<tr>
						<td>
							<label>项目负责人：</label>
							<input id="projectOwner" type="text" name="filter_and_projectOwner_LIKE_S"  value="${filter_and_projectOwner_LIKE_S}"/>
						</td>
						<td>
							<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
							<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearInput()">重置</button></div></div>
						</td>
					</tr>
			</table>
		</form>
	</div>
</div>
<%-- <%@ include file="/common/taglibs.jsp"%>    --%> 
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		</ul>
		<jsp:include page="/common/paginationDialog.jsp" />	
	</div>
	<table class="table" width="100%" layoutH="50">
		<thead>
			<tr>
				<th>带回</th>
				<th>项目编码 </th>
				<th>项目名称</th>
				<th>项目负责人</th>
				<th>项目类型</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${page.results}" var="proEntity" varStatus="status">
				<tr  target="sid_user23">
					<td >
					<a class="btnSelect"
					 	href="javascript:$.bringBack({
					 	id:'${proEntity.id}',
					 	projectCode:'${proEntity.projectCode}',
					 	projectName:'${proEntity.projectName}', 
					 	projectOwner:'${proEntity.projectOwner }',
					 	projectTypeName:'<c:forEach items="${proTypes }" var="type"><c:if test="${type.id == proEntity.projectType }">${type.projectTypeName }</c:if></c:forEach>',
					 	projectType:'${proEntity.projectType }'})" 
					 title="查找带回">选择</a>${ status.count }</td>
					<td align="center" nowrap="nowrap">${proEntity.projectCode }</td>
					<td align="center">${proEntity.projectName}</td>
					<td align="center">${proEntity.projectOwner }</td>
					<td align="center" nowrap="nowrap">
						<c:forEach items="${proTypes }" var="type">
							<c:if test="${type.id == proEntity.projectType }">${type.projectTypeName }</c:if>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
</div>