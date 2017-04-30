<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tip"><span>查询</span></div>
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
	<form onsubmit="return dwzSearch(this, 'dialog');" action="${ path }/combo/comboAction!lookUpCombo.action" method="post" rel = "pagerForm" >
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>套餐名称：</label>
					<input type="text" name="filter_and_comboName_LIKE_S" value="${ filter_and_comboName_LIKE_S }"/>
				</td>
				<td>
					<label>产品名称：</label>
					<input type="text" name="filter_and_productName_LIKE_S" value="${ filter_and_productName_LIKE_S }"/>
				</td>
			</tr>
			
			<tr>
			<td>
				<label>套餐上线日期：</label>
				<input type="text" name="filter_and_createTime_GEST_T"  id="d1897654" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2345678\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
			</td>
			<td>
				<label>至：</label>
				<input type="text" name="filter_and_createTime_LEET_T" id="d2345678" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1897654\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
			</td>
				<td>
					<ul>
				 		 <li><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div></li>
			    	</ul>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/paginationDialog.jsp" />	
	</div>
	<table class="table" width="100%" layoutH="200">
		<thead>
			<tr>
				<th width="50px">带回</th>
				<th>套餐名称</th>
				<th>产品名称</th>
				<th>内容</th>
				<th>套餐上线期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="combo" varStatus="status">
			<tr target="sid_user"  >
				<td><a class="btnSelect" href="javascript:$.bringBack({id:'${combo.id }', productName:'${ combo.productName }', comboName:'${ combo.comboName }',comboContent:'${ combo.comboContent }'})" title="查找带回">选择</a>${ status.count }</td>
				<td>${ combo.comboName }</td>
				<td>${ fn:substring(combo.productName , 0 , 10 )} </td>
				<td>${ fn:substring(combo.comboContent , 0 , 18 )} </td>
				<td>${ fn:substring(combo.createTime , 0 , 10 )}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>