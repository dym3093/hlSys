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

<div class="pageHeader" >
	<form rel = "pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${path }/resource/customer!lookUpCustomerAll.action" method="post">
	<div class="tip"><span>查询</span></div>
	<div class="searchBar">
			<table class="pageFormContent">
			<tr>
				<td>
					<label>客户全称：</label>
					<input type="text" name="filter_and_customerName_LIKE_S" value="${filter_and_customerName_LIKE_S}" />
				</td>
				<td>
					<label>所属行业：</label>
					<select name="filter_and_industry_EQ_S" id="industry" rel="iselect" loadUrl="${path}/hpin/hpinSystemDictType!treeRegion.action?defaultID=1020301">
						<option value="${ filter_and_industry_EQ_S }"></option>
					 </select>
				</td>
				<td></td>
				</tr>
				<tr>
            	<td>
                <label>日期：</label>
				<input type="text" name="filter_and_createTime_GE_T" class="date" size="27" value="${ filter_and_createTime_GE_T }" /><a class="inputDateButton" href="javascript:;">选择</a>
			</td>
			<td>
				<label>至：</label>
				<input type="text" name="filter_and_createTime_LE_T" class="date" size="27" value="${ filter_and_createTime_LE_T }" /><a class="inputDateButton" href="javascript:;">选择</a>
			</td>
				<td>
				<div class="buttonActive"><div class="buttonContent" style="width: 33px;"><button type="submit">查找</button></div></div>
				</td>
            </tr>
		</table>
		
	</div>
	</form>
</div>
<div class="pageContent" id="lookupDialog">
	<div class="panelBar">
		<ul class="toolBar">
		</ul>
		<jsp:include page="/common/paginationDialog.jsp" />	
	</div>
<table class="table" width="97%" layoutH="150">
	<thead>
		<tr>
			<th width="50px">带回</th>
			<th>客户全称</th>
			<th>所属行业</th>
			<th>单位性质</th>
			<th>所在省</th>
			<th>所在市</th>
			<th>状态</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${ page.results }" var="customer" varStatus="status">
		<tr  target="sid_user">
			<td><a class="btnSelect" href="javascript:$.bringBack({id:'${customer.id }', customerName:'${customer.customerName }', industry:'${ customer.industry }',orgNature:'${ customer.orgNature }'})" title="查找带回">选择</a>${ status.count }</td>
			<td><a href="${ path }/resource/customer!browCustomer.action?id=${ customer.id }" width="1100" height="480" style="color:#0000FF" target="dialog">${customer.customerName }</a></td>
			<td align="center">
				<hpin:id2nameDB id="${customer.industry}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/>
			</td>
			<td align="center">
				<hpin:id2nameDB id="${customer.orgNature}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/>
			</td>
			<td align="center"><hpin:id2nameDB id="${customer.province}" beanId="org.hpin.system.code.dao.RegionDao"/></td>
			<td align="center"><hpin:id2nameDB id="${ customer.city }" beanId="org.hpin.system.code.dao.RegionDao"/></td>
			<td align="center">
			  <c:choose>
				<c:when test="${customer.isDeleted=='0'}"><font color="green">启用</font></c:when>
				<c:when test="${customer.isDeleted=='1'}"><font color="red">停用</font></c:when>
			  </c:choose>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>