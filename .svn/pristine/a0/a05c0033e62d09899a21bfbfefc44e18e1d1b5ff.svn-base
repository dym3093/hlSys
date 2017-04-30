<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>    
<script type="text/javascript">
	
</script>

<div class="tip"><span>查询</span></div>

<div class="pageHeader" >
	<form rel = "pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${path }/resource/customer!listCustomerForContract.action" method="post">
<input type="hidden" name="contractId" value="${ contractId }">
	<div class="searchBar">
			<table class="pageFormContent">
			<tr>
				<td>
					<label>客户全称：</label><input type="text" name="filter_and_customerName_LIKE_S" value="${filter_and_customerName_LIKE_S}" />
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
				<input type="text" name="filter_and_createTime_GE_T" class="date" value="${ filter_and_createTime_GE_T }" /><a class="inputDateButton" href="javascript:;">选择</a>
			</td>
			<td>
				<label>至：</label>
				<input type="text" name="filter_and_createTime_LE_T" class="date" value="${ filter_and_createTime_LE_T }" /><a class="inputDateButton" href="javascript:;">选择</a>
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
<form onsubmit="return validateCallback(this, tabAjaxDone);" rel = "pagerForm" 
		action="${path}/resource/agreement!saveArgument.action" class="pageForm required-validate" method="post">
<input type="hidden" name="contractId" value="${ contractId }">
<table class="table" width="97%" layoutH="180">
	<thead>
		<tr>
			<th style="width: 40px;">序号</th>
			<th>客户全称</th>
			<th>客户编码</th>
			<th>所属行业</th>
			<th align="center">单位性质</th>
			<th>所在省</th>
			<th>所在市</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${ page.results }" var="customer" varStatus="status">
		<tr  target="sid_user">
			<td><input type="checkbox" name="ids" id="id" value="${customer.id}" />${ status.count }</td>
			<td><a href="${ path }/resource/customer!browCustomer.action?id=${ customer.id }" width="1100" height="480" style="color:#0000FF" target="dialog">${customer.customerName }</a></td>
			<td>${ customer.customerCode }</td>
			<td align="center"><hpin:id2nameDB id="${customer.industry}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/></td>
			<td align="center"><hpin:id2nameDB id="${customer.orgNature}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/></td>
			<td align="center"><hpin:id2nameDB id="${customer.province}" beanId="org.hpin.system.code.dao.RegionDao"/></td>
			<td align="center"><hpin:id2nameDB id="${ customer.city }" beanId="org.hpin.system.code.dao.RegionDao"/></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="formBar">
	<ul class="toolBar"> 
		<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
		<li><div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
	</ul>
</div>
</form>
</div>