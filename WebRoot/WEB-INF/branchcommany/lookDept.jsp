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
<script type="text/javascript">
	function clearInput(){
		$(':input','#pagerFindForm',$.pdialog.getCurrent())  
		 .not(':button, :submit, :reset, :hidden')  
		 .val('')  
		 .removeAttr('selected');  
	}
</script>
<div class="pageHeader" style="background:white">

	<div class="searchBar" id="manyQue">
	
	<form onsubmit="return dwzSearch(this, 'dialog');" action="${path }/resource/customerRelationShip!lookDept.action" method="post" rel = "pagerForm" id="pagerFindForm" >

			<table class="pageFormContent">
			<tr>
				<td>
					<label>所属公司名称：</label>
					<input type="text" name="filter_and_deptName_LIKE_S"  value="${filter_and_deptName_LIKE_S}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearInput()">重置</button></div></div>
				</td>
				</tr>
				
				<tr>
				<%-- <td align="left">
			  		<label>省 份：</label>
	          		<select id="province" name="filter_and_province_EQ_S" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city1" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
			  			<option value="${filter_and_province_EQ_S}" ></option>
			  		</select>
	       	 	</td> 
	       	 	<td align="left">
	       	 	     <label>城 市：</label>
			         <select id="city1" name="filter_and_city_EQ_S" rel="iselect">
			         	<option value="${filter_and_city_EQ_S}"></option>
			        </select>
	       	 	</td> --%>
				
			</tr>
		</table>
		
	</form>
	</div>
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
			<!-- <th>公司名称 </th>
			<th>省份</th>
			<th>城市</th> -->
			<th>总公司</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${ page.results }" var="dept" varStatus="status">
		<tr  target="sid_user">
			<td >
			<a class="btnSelect"
			 	href="javascript:$.bringBack({
			 	ownedCompany:'${dept.id }',
			 	customerNameSimple:'${dept.deptName }'})" 
			 title="查找带回">选择</a>${status.count}</td>
		<%-- 	<td align="center" nowrap="nowrap">${customerRelationShip.branchCommany }</td>
			<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${customerRelationShip.province }"/></td>
			<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${customerRelationShip.city }"/></td> --%>
			<td align="center" nowrap="nowrap">${dept.deptName}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>