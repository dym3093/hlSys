<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- <div class="pageHeader">
	
</div> -->
<style>
.press {
	display:block;
	line-height:1.5em;
	overflow:visible;
	font-size:22px;
	text-shadow:#f3f3f3 1px 1px 0px, #b2b2b2 1px 2px 0;
	text-align:center;
}
</style>
<div class="pageFormContent" layoutH="56">
<div class="pageContent">
<h1 class="press">${stroeWarehouse.name}仓库库存信息详情</h1>
	<%--  <input type="hidden" name="navTabId" value="${navTabId}"/> --%>
	 <input type="hidden" name ="stroeWarehouse.id" id="wareId" value="${stroeWarehouse.id}"/>
	<div class="divider"></div>
	 <div class="tip"><span>仓库信息</span></div>
		<p>
			<label>仓库名称：</label>
			
			<span>${stroeWarehouse.name}</span>
		</p>
		<p>
			<label>省份：</label>
			<span><hpin:id2nameDB id="${stroeWarehouse.province}" beanId="org.hpin.base.region.dao.RegionDao"/></span>
		</p>
		<p>
	    	<label>城市：</label>
	    	<span><hpin:id2nameDB id="${stroeWarehouse.city}" beanId="org.hpin.base.region.dao.RegionDao"/></span>
	    </p>
	    <p>
	    	<label>联系人：</label>
	    	<span>${stroeWarehouse.director}</span>
	    </p>
	    <p>
	    	<label>联系电话：</label>
	    	<span>${stroeWarehouse.tel}</span>
	    </p>
	    <p>
	    	<label>详细地址：</label>
	    	<span>${stroeWarehouse.address}</span>
	    </p>
<div class="tip"><span>库存信息</span></div>
	<table class="list" width="100%" layoutH="160" >
		<thead>
			<tr>
				<th>序号</th>
				<th nowrap="nowrap" export = "true" columnEnName = "warehouseName" columnChName = "仓库名称" >仓库名称</th>
				<th nowrap="nowrap" export = "true" columnEnName = "typeBigName" columnChName = "产品大类名称" >产品大类名称</th>
				<th nowrap="nowrap" export = "true" columnEnName = "typeSmallName" columnChName = "产品小类名称" >产品小类名称</th> 
				<th nowrap="nowrap" export = "true" columnEnName = "count" columnChName = "数量" >数量</th>
				<th nowrap="nowrap" export = "true" columnEnName = "createTime" columnChName = "创建日期" >创建日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${storeWarehouseAlls }" var="stores" varStatus="status" >
			<tr target="sid_user" rel="${stores.id}">
				<td align="center">${status.count}</td>
				<td align="center" nowrap="nowrap">${stores.warehouseName}</td>
				<td align="center" nowrap="nowrap"><hpin:id2nameDB  beanId="org.hpin.base.dict.dao.SysDictTypeDao" id="${stores.typeBigCode}"/></td>
				<td align="center" nowrap="nowrap">${stores.typeSmallName}</td>
				<td align="center" nowrap="nowrap">${stores.count}</td>
				<td align="center" nowrap="nowrap">${fn:substring(stores.createTime,0,19)}</td>
		 	</tr>
		 </c:forEach>
		</tbody>
	</table>
</div>
</div>
