<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>

<script type="text/javascript">

$(function(){
	
/* 	function toAddPage(){
		$.pdialog.open("${path}/hpin/erpDict!toAddPage.action", "addErpDict", "新增数据字典项",
		{width:300,height:400,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true, param:"{msg:’message’}"});     
	}; */
	
});

function toAddPage(){
	$.pdialog.open("${path}/hpin/erpDict!toAddPage.action", "addErpDict", "新增数据字典项",
	{width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true, param:"{msg:’message’}"});     
};

</script>


<div class="pageContent">

	<div class="panelBar">
		<c:if test="${currentUser.accountName!='parkson'}">
			<ul class="toolBar">
				<li><a class="add" href="#" onclick="toAddPage()"><span>新增</span></a></li>
				<%-- <li><a class="edit" href="${path}/hpin/erpDict!toAddPage.action" target="navTab"><span>修改</span></a></li> --%>
			</ul>
		</c:if>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "id" columnChName = "ID" width="100"> ID </th> 
				<th  export = "true" columnEnName = "keyName" columnChName = "键" width="100" > 键 </th> 
				<th  export = "true" columnEnName = "valueName" columnChName = "值" width="100" > 值 </th> 
				<th  export = "true" columnEnName = "typeFilter" columnChName = "过滤字段" width="100" > 类型 </th> 
				<th  export = "true" columnEnName = "remark" columnChName = "备注" width="160" > 备注 </th> 
				<th  export = "true" columnEnName = "createTime" columnChName = "创建时间" width="160" > 创建时间 </th> 
				<th  export = "true" columnEnName = "createUserId" columnChName = "创建人ID" width="100" > 创建人ID </th> 
				<th  export = "true" columnEnName = "createUser" columnChName = "创建人" width="100" > 创建人 </th> 
				<th  export = "true" columnEnName = "updateTime" columnChName = "修改时间" width="160" > 修改时间 </th> 
				<th  export = "true" columnEnName = "updateUserId" columnChName = "修改人ID" width="100" > 修改人ID </th> 
				<th  export = "true" columnEnName = "updateUser" columnChName = "修改人" width="100" > 修改人 </th> 
				<th  export = "true" columnEnName = "status" columnChName = "状态" width="100" > 状态 </th> 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
					
					<tr target="sid_user" rel="${entity.id }">
					<td align="center"><input type="checkbox" name="ids" value="${entity.id}"/>${ status.count }</td>
					
					<td align="center"> ${entity.id} </td>
					<td align="center"> ${entity.keyName} </td>
					<td align="center"> ${entity.valueName} </td>
					<td align="center"> ${entity.typeFilter} </td>
					<td align="center"> ${entity.remark} </td>
					<td align="center"> ${entity.createTime} </td>
					<td align="center"> ${entity.createUserId} </td>
					<td align="center"> ${entity.createUser} </td>
					<td align="center"> ${entity.updateTime} </td>
					<td align="center"> ${entity.updateUserId} </td>
					<td align="center"> ${entity.updateUser} </td>
					<td align="center"> ${entity.status} </td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>


