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
	<form id="pagerFindForm" rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${path}/warehouse/product!query.action" method="post">
	<div class="searchBar">
		<table class="pageFormContent">
			<tbody>
				<tr>
					<td>
						<label>产品名称：</label><input type="text" name="params.productName" class="textInput" value="${params.productName }">
					</td>
					<td>
						<label>产品类型：</label>
						<select id="productType" name="params.productType" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10107" >
				    		<option value="${params.productType }"></option>
				    	</select>
					</td>
					<td >
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
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
			<th style="width: 150px; ">产品名称</th>
			<th style="width: 100px; ">产品类别</th>
			<th style="width: 200px; ">产品规格</th>
			<th>产品描述</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.results }" var="item" varStatus="stat">
			<tr target="sid_user" rel="${item.id }" >
				<td>
					<a class="btnSelect"
					 	href="javascript:$.bringBack({
					 	productId:'${item.id }',
					 	productName:'${item.productName }',
					 	standard:'${item.standard }', 
					 	describe:'${item.describe }', 
					 	productType:'${item.productType }'})" 
					 	title="查找带回">选择</a>${stat.count }
			 	</td>
				<td>${item.productName }</td>
				<td>${item.productTypeName }</td>
				<td>${item.standard }</td>
				<td>${item.describe }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>