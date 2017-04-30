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
	<form rel = "pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${path }/resource/customer!lookUpCustomer.action" method="post">

	<div class="searchBar">
			<table class="pageFormContent">
			<tr>
				<td>
					<label>客户全称：</label>
					<input type="text" name="filter_and_customerName_LIKE_S" value="${filter_and_customerName_LIKE_S}" />
				</td>
				<td>
					<label>所属行业：</label>
					<select id="industry" name="filter_and_industry_EQ_S" rel="iselect" loadUrl="${path}/hpin/hpinSystemDictType!treeRegion.action?defaultID=1020301">
						<option value="${ filter_and_industry_EQ_S }"></option>
					 </select>
				</td>
				<td></td>
				</tr>
				<tr>
            	<td>
                <label>日期：</label>
				<input type="text" name="filter_and_createTime_GEST_T" id="d7370001" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7370002\')}'})" value="${ filter_and_createTime_GEST_T }" /><a class="inputDateButton" href="javascript:;">选择</a>
			</td>
			<td>
				<label>至：</label>
				<input type="text" name="filter_and_createTime_LEET_T" id="d7370002" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7370001\')}'})" value="${ filter_and_createTime_LEET_T }" /><a class="inputDateButton" href="javascript:;">选择</a>
			</td>
				<td>
				<div class="buttonActive"><div class="buttonContent" style="width: 33px;"><button type="submit">查找</button></div></div>
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
			<th>客户全称</th>
			<th>所属行业</th>
			<th>单位性质</th>
			<th>所在省</th>
			<th>所在市</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${ page.results }" var="customer" varStatus="status">
		<tr  target="sid_user">
			<td>
			<a class="btnSelect" href="javascript:$.bringBack({id:'${customer.id }', customerName:'${customer.customerName }', industry:'${ customer.industry }',orgNature:'${ customer.orgNature }'})" title="查找带回">选择</a>${ status.count }</td>
			<td><a href="${ path }/resource/customer!browCustomer.action?id=${ customer.id }" title="${customer.customerName }" width="1100" height="480" style="color:#0000FF" target="dialog">
			<c:set var="str" value="${customer.customerName }" /> 
					<c:choose> 
					<c:when test="${fn:length(str) > 18}"> 
					<c:out value="${fn:substring(str, 0, 18)}......" /> 
						</c:when> 
						<c:otherwise> 
					<c:out value="${str}" /> 
					</c:otherwise> 
					</c:choose></a></td>
			<td align="center">
				<hpin:id2nameDB id="${customer.industry}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/>
			</td>
			<td align="center">
				<hpin:id2nameDB id="${customer.orgNature}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/>
			</td>
			<td align="center"><hpin:id2nameDB id="${customer.province}" beanId="org.hpin.system.code.dao.RegionDao"/></td>
			<td align="center"><hpin:id2nameDB id="${ customer.city }" beanId="org.hpin.system.code.dao.RegionDao"/></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>