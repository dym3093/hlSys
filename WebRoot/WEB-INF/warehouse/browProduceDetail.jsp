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
 <form onsubmit="if(this.action != '${path}/warehouse/warehouse!browProduceDetail.action?id=${produce.id}'){this.action = '${path}/warehouse/warehouse!browProduceDetail.action?id=${produce.id}' ;} ;return navTabSearch(this);" action="${path}/warehouse/warehouse!browProduceDetail.action?id=${produce.id}" method="post" rel = "pagerForm" id="pagerFindForm">   
<input type="hidden" name =filter_and_produceId_EQ_S  value="${produce.id}"/>
</form> 
</div>
<div class="pageFormContent" layoutH="56">
<div class="pageContent">
	<h1 class="press">${produce.warehouseName}调拨信息详情</h1>
	 <input type="hidden" name="navTabId" value="${navTabId}"/>
	 <input type="hidden" name ="produce.id" id="wareId" value="${produce.id}"/>
	<div class="divider"></div>
	 <div class="tip"><span>调拨信息</span></div>
		<p>
			<label>支公司名称：</label>
			
			<span>${produce.bannyCompannyName}</span>
		</p>
		<p>
			<label>场次号：</label>
			<span>${produce.eventNos}</span>
		</p>
		<p>
	    	<label>快递单号：</label>
	    	<span>${produce.emsNo}</span>
	    </p>
	    <p>
	    	<label>接收人姓名：</label>
	    	<span>${produce.receiveName}</span>
	    </p>
	    <p>
	    	<label>接收人电话：</label>
	    	<span>${produce.receiveTel}</span>
	    </p>
	    <p>
	    	<label>快递名称：</label>
	    	<span>${produce.emsName}</span>
	    </p>
	    <p>
	    	<label>详细地址：</label>
	    	<span>${produce.address}</span>
	    </p>
	    <p>
	    	<label>远盟业务员：</label>
	    	<span>${produce.businessName}</span>
	    </p>
	    
		<p>
			<label>调拨状态：</label>
			<c:choose>
					<c:when test="${storeProduce.type==1}">
						<span>调出</span>
					</c:when>
					<c:when test="${storeProduce.type==0}">
						<span>入库</span>
					</c:when>
					<c:otherwise><span></span></c:otherwise>
				</c:choose>
		</p>
	      <input name="idsp" type="hidden"  value="${produce.id}" />
	      


<div class="tip"><span>调拨信息清单</span></div>
<div class="panelBar">
	<%-- <web:security tag="comboAdmin"> --%>
		<ul class="toolBar">
			<web:exportExcelTag 
					pagerFormId="pagerFindForm" 
					pagerMethodName="findProduceDetailByPage" 
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
				<th nowrap="nowrap" export = "true" columnEnName = "typeBigName" columnChName = "品类" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">产品大类</th>
				<th nowrap="nowrap" export = "true" columnEnName = "typeSmallName" columnChName = "品名" >产品小类</th>
				<th nowrap="nowrap" export = "true" columnEnName = "count" columnChName = "数量" >数量</th> 
				<th nowrap="nowrap" export = "true" columnEnName = "price" columnChName = "单价" >单价</th>
				<th nowrap="nowrap" export = "true" columnEnName = "totalPrice" columnChName = "总价" >总价</th>
				<th nowrap="nowrap" export = "true" columnEnName = "warehouseName" columnChName = "仓库名称" >仓库名称</th>
				<th nowrap="nowrap" export = "true" columnEnName = "createTime" columnChName = "创建日期" >创建日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="produceDetail" varStatus="status" >
			<tr target="sid_user" rel="${produceDetail.id}">
				<td align="center">${status.count} </td>
				<td align="center" nowrap="nowrap"><hpin:id2nameDB  beanId="org.hpin.base.dict.dao.SysDictTypeDao" id="${produceDetail.typeBigCode}"/></td>
				<td align="center" nowrap="nowrap">${produceDetail.typeSmallName}</td>
				<td align="center" nowrap="nowrap">${produceDetail.count}</td>
				<td align="center" nowrap="nowrap">${produceDetail.price}</td>
				<td align="center" nowrap="nowrap">${produceDetail.totalPrice}</td>
				<td align="center" nowrap="nowrap">${produceDetail.warehouseName}</td>
				<td align="center" nowrap="nowrap">${fn:substring(produceDetail.createTime,0,19)}</td>
		 	</tr>
		 </c:forEach>
		</tbody>
	</table>
	</div> 
</div>
