<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pageHeader">
	<form id = "pagerFindForm" onsubmit="if(this.action != '${path }/resource/customer!listCustomer.action'){this.action = '${path }/resource/customer!listCustomer.action' ;} ;return navTabSearch(this);" action="${path }/resource/customer!listCustomer.action" method="post" rel="pagerForm">
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
				<td>
					<label>单位性质：</label>
					<select id="orgNature" name="filter_and_orgNature_EQ_S" rel="iselect" loadUrl="${path}/hpin/hpinSystemDictType!treeRegion.action?defaultID=1020302">
						<option value="${ filter_and_orgNature_EQ_S }"></option>
					 </select>
				</td>
			</tr>
			<tr>
				<td>
					 <label>客户分类：</label>
					 <select id="customType" name="filter_and_customType_EQ_S" rel="iselect" loadUrl="${path}/hpin/hpinSystemDictType!treeRegion.action?defaultID=1020303">
						<option value="${ filter_and_customType_EQ_S }"></option>
					 </select>
				</td>
				<td><label>所在省：</label>
				  <select id="province" name="filter_and_province_LIKE_S"  rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
				  	<option value="${filter_and_province_LIKE_S}"></option>
				  </select>
				  </td>
                <td><label>所在市：</label>
                	<select id="city" name="filter_and_city_LIKE_S" rel="iselect">
                		<option value="${filter_and_province_LIKE_S}"></option>
                	</select> 
				</td>
			</tr>
            <tr>
               <td>
				<label>日期：</label>
				<input type="text" name="filter_and_createTime_GEST_T"  id="d878888" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d989999\')}'})" class="required" readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
			</td>
			<td>
				<label>至：</label>
				<input type="text" name="filter_and_createTime_LEET_T" id="d989999" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d878888\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
			</td>
            <td align="left">
            <label>状态：</label>
            <select name="filter_and_isDeleted_EQ_I" class="combox">
            	<option value="">全部</option>
            	<option value="0" 
            	<c:if test="${ filter_and_isDeleted_EQ_I=='0' }"> selected="selected"</c:if>
            	>启用</option>
            	<option value="1"
            	<c:if test="${ filter_and_isDeleted_EQ_I=='1' }"> selected="selected"</c:if>
            	>停用</option>
            </select>
			<div class="buttonActive" style="margin-left: 10px"><div class="buttonContent"><button type="submit">查找</button></div></div>
            <div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
            </td>
            </tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
	<web:security tag="customerAdmin">
		<ul class="toolBar">
			<li><a class="add" href="${path}/resource/customer!addCustomer.action" target="navTab"><span>添加</span></a></li>
			<li><a class="delete" href="${path}/resource/customer!deleteCustomer.action" rel="ids" postType="string" title="确定要停用吗?" target="selectedTodo"><span>停用</span></a></li>
			<li><a class="add" href="${path}/resource/customer!startusingCustomer.action" rel="ids" postType="string" title="确定要启用吗?" target="selectedTodo"><span>启用</span></a></li>
			<li><a class="edit" href="${path}/resource/customer!modifyResource.action?id={sid_user}" target="navTab"><span>修改</span></a></li>
			<web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.customer.web.CustomerAction" 
								informationTableId="exportExcelTable"
								fileName="customer"></web:exportExcelTag>
		</ul>
		</web:security>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="98.5%" layoutH="150" id="exportExcelTable">
		<thead>
			<tr>
				<th width="40px">序号</th>
				<th export = "true" columnEnName = "customerName" columnChName = "客户全称">客户全称</th>
				<th export = "true" columnEnName = "industry" columnChName = "所属行业" id2NameBeanId = "org.hpin.system.dict.dao.HpinSystemDictTypeDao"><a href="javascript:navSort('order_industry','desc');">所属行业</a></th>
				<th export = "true" columnEnName = "orgNature" columnChName = "单位性质" id2NameBeanId = "org.hpin.system.dict.dao.HpinSystemDictTypeDao"><a href="javascript:navSort('order_orgNature','desc');">单位性质</a></th>
				<th export = "false" columnEnName = "isDeleted" columnChName = "状态"><a href="javascript:navSort('order_isDeleted','asc');">状态</a></th>
				<th export = "true" columnEnName = "province" columnChName = "所在省份" id2NameBeanId = "org.hpin.system.code.dao.RegionDao"><a href="javascript:navSort('order_province','desc');">所在省</a></th>
				<th export = "true" columnEnName = "city" columnChName = "所在城市" id2NameBeanId = "org.hpin.system.code.dao.RegionDao"><a href="javascript:navSort('order_city','desc');">所在市</a></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="customer" varStatus="status">
			<tr target="sid_user" rel="${customer.id }">
				<td width="40px"><input type="checkbox" name="ids" value="${customer.id }">&nbsp;${ status.count }</td>
				<td><a href="${ path }/resource/customer!browCustomer.action?id=${ customer.id }" width="1100" height="480" style="color:#0000FF" target="navTab" title="${ customer.customerName }">
					<c:set var="str" value="${customer.customerName }" /> 
					<c:choose> 
					<c:when test="${fn:length(str) > 18}"> 
					<c:out value="${fn:substring(str, 0, 18)}......" /> 
						</c:when> 
						<c:otherwise> 
					<c:out value="${str}" /> 
					</c:otherwise> 
					</c:choose></a>
				</td>
				<td  align="center">
					<hpin:id2nameDB id="${customer.industry}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/>
				</td>
				<td align="center">
					<hpin:id2nameDB id="${customer.orgNature}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/>
				</td>
				<td align="center">
					<c:choose>
						<c:when test="${customer.isDeleted=='0'}"><font color="green">启用</font></c:when>
						<c:when test="${customer.isDeleted=='1'}"><font color="red">停用</font></c:when>
					</c:choose>
				</td>
				<td align="center"><hpin:id2nameDB id="${customer.province}" beanId="org.hpin.system.code.dao.RegionDao"/></td>
				<td align="center"><hpin:id2nameDB id="${ customer.city }" beanId="org.hpin.system.code.dao.RegionDao"/></td>
			</tr>
		</c:forEach>
		</tbody> 
	</table>
		
</div>