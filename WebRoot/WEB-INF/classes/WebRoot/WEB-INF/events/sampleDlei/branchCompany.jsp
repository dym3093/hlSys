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
<div class="pageHeader" style="background:white">
	<form rel = "pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${ path }/events/sampleDlei!findBranchComplany.action" method="post">
	<input name="bitchNum" type="hidden" value="${bitchNum }"/>
	<div class="searchBar">
			<table class="pageFormContent">
			<tr>
				<td>
				<label>公司名称：</label>
					<input type="text" name="branchCommany"  value="${branchCommany}"/>
				</td>
				</tr>
				
				<tr>
				<td align="left">
		  		<label>省 份：</label>
          		<select id="province" name="province" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city1" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
		  			<option value="${province}" ></option>
		  		</select>
	       	 	</td> 
	       	 	<td align="left">
	       	 	     <label>城 市：</label>
			         <select id="city1" name="city" rel="iselect">
			         	<option value="${city}"></option>
			        </select>
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
		<ul class="toolBar">
		</ul>
		<jsp:include page="/common/paginationDialog.jsp" />	
	</div>
<table class="table" width="100%" layoutH="150">
	<thead>
		<tr>
			<th style="width: 50px">带回</th>
			<th>公司名称 </th>
			<th>省份</th>
			<th>城市</th>
			<th>总公司</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${ page.results }" var="customerRelationShip" varStatus="status">
		<tr  target="sid_user">
			<td >
			<a class="btnSelect"
			 	href="javascript:$.bringBack({
			 	id:'${customerRelationShip.id }',
			 	provice:'${customerRelationShip.province }',
			 	city:'${customerRelationShip.city }', 
			 	branchCommanyName:'${customerRelationShip.branchCommany }', 
			 	ownedCompanyName:'${customerRelationShip.customerNameSimple }',
			 	ownedCompanyId:'${customerRelationShip.ownedCompany }'})" 
			 title="查找带回">选择</a>${ status.count }</td>
			<td align="center" nowrap="nowrap">${customerRelationShip.branchCommany }</td>
			<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${customerRelationShip.province }"/></td>
			<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${customerRelationShip.city }"/></td>
			<td align="center" nowrap="nowrap">${customerRelationShip.customerNameSimple}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>