<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
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
<div class="pageHeader">	
 <form onsubmit="if(this.action != '${path}/warehouse/warehouse!browStoreProduce.action?id=${storeWarehouse.id}'){this.action = '${path}/warehouse/warehouse!browStoreProduce.action?id=${storeWarehouse.id}' ;} ;return navTabSearch(this);" action="${path}/warehouse/warehouse!browStoreProduce.action?id=${storeWarehouse.id}" method="post" rel = "pagerForm" id="pagerFindForm">   
<input type="hidden" name =filter_and_warehouseId_EQ_S  value="${stroeWarehouse.id}"/>
</form> 
</div>
<div class="pageFormContent" layoutH="56">
<div class="pageContent">
	<h1 class="press">${stroeWarehouse.name}仓库调拨信息详情</h1>
	 <input type="hidden" name="navTabId" value="${navTabId}"/>
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
	      <input name="idsp" type="hidden"  value="${stroeWarehouse.id}" />
<div class="tip"><span>调拨信息清单</span></div>
	<div class="panelBar">
	<%-- <web:security tag="comboAdmin"> --%>
		<ul class="toolBar">
			<web:exportExcelTag 
					pagerFormId="pagerFindForm" 
					pagerMethodName="findProduceByPage" 
					actionAllUrl="org.hpin.warehouse.web.StoreWarehouseAction" 
					informationTableId="exportExcelTable"
					fileName="${page.results[0].warehouseName}"></web:exportExcelTag>
		</ul>
	<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="98%" layoutH="170" id="exportExcelTable" > 
		<thead>
			<tr>
				<th>序号</th>
				<th nowrap="nowrap" export = "true" columnEnName = "bannyCompannyName" columnChName = "支公司名称" >支公司名称</th>
				<th nowrap="nowrap" export = "true" columnEnName = "eventNos" columnChName = "场次号" >场次号</th>
				<th nowrap="nowrap" export = "true" columnEnName = "emsNo" columnChName = "快递单号" >快递单号</th> 
				<th nowrap="nowrap" export = "true" columnEnName = "receiveName" columnChName = "接收人姓名" >接收人姓名</th>
				<th nowrap="nowrap" export = "true" columnEnName = "receiveTel" columnChName = "接收人电话" >接收人电话</th>
				<th nowrap="nowrap" export = "true" columnEnName = "emsName" columnChName = "快递名称" >快递名称</th>
				<th nowrap="nowrap" export = "true" columnEnName = "address" columnChName = "派发地址" >派发地址</th>
				<th nowrap="nowrap" export = "true" columnEnName = "businessName" columnChName = "远盟业务员" >远盟业务员</th>
				<th nowrap="nowrap" export = "true" columnEnName = "createTime" columnChName = "创建日期" >创建日期</th>
				<th nowrap="nowrap" export = "true" columnEnName = "type" columnChName = "调拨类型(1调出,0入库)" >调拨类型</th>
				<th nowrap="nowrap" export = "false" columnEnName = "details" columnChName = "调拨详情" >调拨详情</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="storeProduce" varStatus="status" >
			<tr target="sid_user" rel="${storeProduce.id}">
				<td align="center"><%-- <input type="checkbox" name="ids" value="${storeProduce.id}"> --%>${status.count} </td>
				<td align="center" nowrap="nowrap">${storeProduce.bannyCompannyName}</td>
				<td align="center" nowrap="nowrap">${storeProduce.eventNos}</td>
				<td align="center" nowrap="nowrap">${storeProduce.emsNo}</td>
				<td align="center" nowrap="nowrap">${storeProduce.receiveName}</td>
				<td align="center" nowrap="nowrap">${storeProduce.receiveTel}</td>
				<td align="center" nowrap="nowrap">${storeProduce.emsName}</td>
				<%-- <td align="center" nowrap="nowrap"><hpin:id2nameDB  beanId="org.hpin.base.dict.dao.SysDictTypeDao" id="${stores.typeBigCode}"/></td> --%>
				<td align="center" nowrap="nowrap">${storeProduce.address}</td>
				<td align="center" nowrap="nowrap">${storeProduce.businessName}</td>
				<td align="center" nowrap="nowrap">${fn:substring(storeProduce.createTime,0,19)}</td>
				<c:choose>
					<c:when test="${storeProduce.type==1}">
						<td align="center" nowrap="nowrap">调出</td>
					</c:when>
					<c:when test="${storeProduce.type==0}">
						<td align="center" nowrap="nowrap">入库</td>
					</c:when>
					<c:otherwise><td align="center" nowrap="nowrap"></td></c:otherwise>
				</c:choose>
				<td align="center" nowrap="nowrap"><a class="add" href="${path }/warehouse/warehouse!browProduceDetail.action?id=${storeProduce.id }" target="navTab"><span>详情</span></a></td>
		 	</tr>
		 </c:forEach>
		</tbody>
	</table>
	</div> 
</div>
