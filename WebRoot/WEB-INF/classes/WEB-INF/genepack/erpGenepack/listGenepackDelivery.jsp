<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
//变更用于复选框
function changeProductw() {
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择你要变更的信息！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息进行变更！');
		return ;		
	}else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyGenepackDelivery", "../genepack/erpGenepackDelivery!toModifyGenepackDelivery.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	}
}
function changeProduct() {
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择你要变更的信息！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息进行变更！');
		return ;		
	}else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyGenepackDelivery", "../genepack/erpGenepackDelivery!toModifyGenepackDelivery.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	}
}
//变更用于单行
function changeProduct(ids) {
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
        navTab.openTab("modifyGenepackDelivery", "../genepack/erpGenepackDelivery!toModifyGenepackDelivery.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	
}
//删除用于单行
/*function changeProduct(ids) {
	if(confirm("确定要删除？")){ {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyEvents", "../events/erpGenepackDelivery!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	}
}
	*/

</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/genepack/erpGenepackDelivery!toListGenepackDelivery.action'){this.action = '${path}/genepack/erpGenepackDelivery!toListGenepackDelivery.action' ;} ;return navTabSearch(this);" action="${path}/genepack/erpGenepackDelivery!toListGenepackDelivery.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" name="filter_and_deliverybatchno_LIKE_S" value="${filter_and_deliverybatchno_LIKE_S}"/>
				</td>
				<td>
					<label>快递单号：</label> 
					<input type="text" name="filter_and_deliveryexpressno_LIKE_S" value="${filter_and_deliveryexpressno_LIKE_S}"/>
				</td>
				<td>
				
				</td>
				
			</tr>
			<tr>
				<td><label>发货起始日期：</label> 
					<input type="text" name="filter_and_deliverydate_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_deliverydate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>发货结束日期：</label> 
					<input type="text" name="filter_and_deliverydate_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_deliverydate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
		<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		<ul class="toolBar">
			<li><a class="add" href="${path}/genepack/erpGenepackDelivery!toAddGenepackDelivery.action"  target="navTab" rel="add"><span>增加批次</span></a></li>
			<li><a class="delete" href="${path}/genepack/erpGenepackDelivery!delGenepackDelivery.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除批次</span></a></li>
			<li><a class="edit" onclick="changeProductw()" style="cursor:pointer;"><span>变更批次</span></a></li>
		 <web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.genepack.web.GenepackDeliveryAction" 
								informationTableId="exportExcelTable"
								fileName="genepackDelivery"></web:exportExcelTag>
		
		</ul>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export= "true" columnEnName = "deliverybatchno" columnChName = "发货批次号" >发货批次号</th>
				<th  export= "true" columnEnName = "deliveryexpressno" columnChName = "快递单号" >快递单号</th>
				<th  export= "true" columnEnName = "deliverydate" columnChName = "发货日期" >发货日期</th>
				<th  export= "true" columnEnName = "packcount" columnChName = "发货数量" >发货数量</th>
				<th  export= "true" columnEnName = "note" columnChName = "备注" >备注</th>
			   <!--  <th  export = "false" columnEnName = "" columnChName = "" >操作</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="genepackDelivery" varStatus="status">
					<tr target="sid_user" rel="${genepackDelivery.id }">
						<td align="center">
								<input type="checkbox" name="ids" value="${genepackDelivery.id}">
							${ status.count }
						</td>
						<td align="center">
							<a title="发货信息" target="navTab" width="1000"  href="${path}/genepack/erpGenepackDelivery!toGenepackDelivery.action?id=${genepackDelivery.id}">${genepackDelivery.deliverybatchno}</a>
						</td>
						<td align="center">	${genepackDelivery.deliveryexpressno}</td>
						<td align="center">	${fn:substring(genepackDelivery.deliverydate,0,19)}</td>
						<td align="center">	${genepackDelivery.packcount}</td>
						<td align="center">	${genepackDelivery.note}</td>
						
						<%-- <td align="center">
							<div class="panelBar">
								<ul class="toolBar">
									<li><a class="edit" onclick="changeProduct('${genepackDelivery.id}')" style="cursor:pointer;"><span>变更</span></a></li>
									<li><a class="add" href="${path}/genepack/erpGenepackDelivery!importCustomer.action?id=${genepack.id}" target="navTab" rel="add"><span>导入</span></a></li>
								</ul>
								</div>
						</td> --%>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 