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
	<form id="pagerFindForm"  rel = "pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${path }/resource/customerRelationShip!findCustomerRelationShipTwo.action" method="post">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
				<td align="left">
			  		<label>省 份：</label>
	          		<select id="province" name="custRelaQuery.province" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city1" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
			  			<option value="${custRelaQuery.province}" ></option>
			  		</select>
	       	 	</td> 
	       	 	<td align="left">
	       	 	     <label>城 市：</label>
			         <select id="city1" name="custRelaQuery.city" rel="iselect">
			         	<option value="${custRelaQuery.city}"></option>
			        </select>
	       	 	</td>
				
			</tr>
			<tr>
				<td>
					<label>公司名称：</label>
					<input type="text" name="custRelaQuery.branchCommany"  value="${custRelaQuery.branchCommany}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearInput()">重置</button></div></div>
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
				<!-- <th>项目编码 </th>
				<th>项目名称 </th>
				<th>项目负责人 </th> -->
				<th>省份</th>
				<th>城市</th>
				<th>详细地址</th>
			</tr>
		</thead>
		
		<tbody>
		<c:forEach items="${ page.results }" var="customerRelationShip" varStatus="status">
			<tr  target="sid_user">
				<td >
					<a class="btnSelect" href="javascript:$.bringBack({
					id:'${customerRelationShip.id}',
					ownedCompany:'${customerRelationShip.ownedCompany}',
					branchCommany:'${customerRelationShip.branchCommany}',
					provinceNum:'${customerRelationShip.province}',
					cityNum:'${customerRelationShip.city}',
					<%-- proCode:'${customerRelationShip.proCode}',
					proBelong:'${customerRelationShip.proBelong}',
					proUser:'${customerRelationShip.proUser}', --%>
					province:'<hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${customerRelationShip.province}"/>',
					city:'<hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${customerRelationShip.city}"/>',
					customerNameSimple:'${customerRelationShip.customerNameSimple}'})" title="查找带回">选择</a>${ status.count }
				</td>
				<td align="center" nowrap="nowrap">${customerRelationShip.branchCommany }</td>
			<%-- 	<td align="center" nowrap="nowrap">${customerRelationShip.proCode}</td>
				<td align="center" nowrap="nowrap">${customerRelationShip.proBelong}</td>
				<td align="center" nowrap="nowrap">${customerRelationShip.proUser}</td> --%>
				<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${customerRelationShip.province }"/></td>
				<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${customerRelationShip.city }"/></td>
				<td align="center" nowrap="nowrap">${customerRelationShip.address}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>