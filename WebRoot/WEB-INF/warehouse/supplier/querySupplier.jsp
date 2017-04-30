<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<div class="pageHeader" style="overflow: hidden;">
	<form id="pagerFindForm" rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${path}/warehouse/supplier!querySupplier.action" method="post">
	<div class="searchBar">
		<table class="pageFormContent">
			<tbody>
				<tr>
					<td>
						<label>供货商名称：</label><input type="text" name="filter_and_supplierName_LIKE_S" class="textInput" value="${filter_and_supplierName_LIKE_S}">
					</td>
					<td>
						<label>联系人：</label><input type="text" name="filter_and_linkMan_LIKE_S" class="textInput" value="${filter_and_linkMan_LIKE_S}">
					</td>
					<td >
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button id="clearBtn" type="reset">重置</button></div></div></li>
							</ul>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	</form>
</div>

<div class="pageContent">
<div class="panelBar">
		<ul class="toolBar">
		</ul>
		<jsp:include page="/common/paginationDialog.jsp" />	
	</div>
<table class="table" width="100%" layoutH="90">
	<thead>
		<tr>
			<th style="width: 45px; ">序号</th>
			<th style="width: 300px; ">供货商名称</th>
			<th style="width: 150px; ">联系人</th>
			<th style="width: 150px; ">联系人电话</th>
			<th style="width: 300px; ">详细地址</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.results }" var="item" varStatus="stat">
			<tr target="sid_user" rel="${item.id }" >
				<td>
					<a class="btnSelect"
					   href="javascript:$.bringBack({supplierId:'${item.id }',supplierName:'${item.supplierName}'})"
					   title="查找带回">选择</a>${stat.count}
				</td>
				<td>${item.supplierName}</td>
				<td>${item.linkMan}</td>
				<td>${item.linkPhone}</td>
				<td>${item.address}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>

<script>

$("#clearBtn").onclick(function(){
	$(".textInput").clean();
});

</script>