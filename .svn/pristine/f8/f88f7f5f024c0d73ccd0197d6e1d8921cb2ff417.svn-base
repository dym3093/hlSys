<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>    
<script type="text/javascript">
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

<div class="pageContent">
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
		<c:forEach items="${pros }" var="pro" varStatus="status">
			<tr  target="sid_user">
				<td >
				<a class="btnSelect"
				 	href="javascript:$.bringBack({
				 	id:'${pro.id }',
				 	projectCode:'${pro.projectCode }',
				 	projectName:'${pro.projectName}', 
				 	projectOwner:'${pro.projectOwner }',
				 	linkName:'${pro.linkName }', 
				 	linkTel:'${pro.linkTel }',
				 	projectTypeName:'<c:forEach items="${proTypes }" var="type"><c:if test="${type.id == pro.projectType }">${type.projectTypeName }</c:if></c:forEach>',
				 	projectType:'${pro.projectType }'})" 
				 title="查找带回">选择</a>${ status.count }</td>
				<td align="center" nowrap="nowrap">${pro.projectCode }</td>
				<td align="center">${pro.projectName}</td>
				<td align="center">${pro.projectOwner }</td>
				<td align="center" nowrap="nowrap">
					<c:forEach items="${proTypes }" var="type">
						<c:if test="${type.id == pro.projectType }">${type.projectTypeName }</c:if>
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>