<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<style>
	.panel .panelContent{border-top-width: 1px;}
</style>

<div class="tip"><span>仓库基础信息</span></div>
<div class="pageHeader">
    <table class="pageFormContent xxmes" id="lineHeight">
        <tr>
            <td><label>仓库名称：</label><input type="hidden" name ="stroeWarehouse.id" id="wareId" value="${stroeWarehouse.id}"/></td>
            <td>${stroeWarehouse.name}</td>
            <td><label>省&emsp;&emsp;份：</label></td>
            <td><hpin:id2nameDB id="${stroeWarehouse.province}" beanId="org.hpin.base.region.dao.RegionDao"/></td>
            <td><label>城&emsp;&emsp;市：</label></td>
            <td><hpin:id2nameDB id="${stroeWarehouse.city}" beanId="org.hpin.base.region.dao.RegionDao"/></td>
        </tr>
        <tr>
        	<td><label>联&ensp;系&ensp;人：</label></td>
            <td>${stroeWarehouse.director}</td>
            <td><label>联系电话：</label></td>
            <td>${stroeWarehouse.tel}</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
        	<td><label>详细地址：</label></td>
            <td>${stroeWarehouse.address}</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
        	<td><label>服务范围：</label></td>
            <td>${stroeWarehouse.remark1}</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
        	<td><label>描述：</label></td>
            <td>${stroeWarehouse.remark2}</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</div>


<div class="tip"><span>入库记录</span></div>
<div class="pageHeader" style="overflow: hidden;">
		<form id="pagerFindForm" onsubmit="return navTabSearch(this);" 
			action="${path}/warehouse/warehouse!viewDetailed.action?id=${stroeWarehouse.id}" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tbody>
					<tr>
						<td>
							<label>产品名称：</label>
							<input type="text" name="params.productName" class="textInput" value="${params.productName }">
						</td>
						<td>
							<div class="subBar">
								<ul style="float: left">
									<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
									<li><a onclick="resetDo();" href="javascript:;" class="button"><span>重置</span></a></li>
								</ul>
							</div>
						</td>
						<td></td>
					</tr>
				</tbody>
			</table>
			
		</div>
		</form>
	</div>
<div class="pageHeader">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>
    <table class="list" id="lineHeight" width="100%">
        <tr>
        	<th width="35px">序号</th>
            <th>产品名称</th>
            <th>规格</th>
            <th>单价</th>
            <th>数量</th>
            <th>可用数量</th>
            <th>入库状态</th>
        </tr>
        <c:forEach items="${page.results }" var="item" varStatus="status">
	        <tr>
	        	<td style="text-align: center;">${status.count }</td>
	        	<td>${item.productName }</td>
	        	<td>${item.standard }</td>
	        	<td style="text-align: right;">${item.price }</td>
	        	<td style="text-align: center;">${item.quantity }</td>
	        	<td style="text-align: center;">${item.useableQuantity }</td>
	        	<td style="text-align: center;"><c:if test="${item.storegeStatus==1 }">正常入库</c:if><c:if test="${item.storegeStatus==0 }">退货入库</c:if></td>
	        </tr>
        	
        </c:forEach>
    </table>
</div>

<script type="text/javascript">
function resetDo() {
	$("input", navTab.getCurrentPanel()).val("");
}


</script>
