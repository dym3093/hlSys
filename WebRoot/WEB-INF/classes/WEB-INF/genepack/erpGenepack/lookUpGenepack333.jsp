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
		navTab.openTab("modifyGenepack", "../genepack/erpGenepack!toModifyGenepack.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
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
		navTab.openTab("modifyEvents", "../events/erpCustomer!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	}
}
//变更用于单行
function changeProduct(ids) {
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
        navTab.openTab("modifyGenepack", "../genepack/erpGenepack!toModifyGenepack.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	
}
//删除用于单行
/*function changeProduct(ids) {
	if(confirm("确定要删除？")){ {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyEvents", "../events/erpGenepack!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	}
}
	*/

</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm"  onsubmit="return dwzSearch(this, 'dialog');"  action="${path}/genepack/erpGenepackDelivery!toLookUpGenepack.action" method="post"  rel="pagerForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" name="filter_and_batchno_LIKE_S" value="${filter_and_batchno_LIKE_S}"/>
				</td>
				
				<td>
					<label>远盟联系人：</label> 
					<input type="text" name="filter_and_ymsalesman_LIKE_S" value="${filter_and_ymsalesman_LIKE_S}"/> 
				</td>
			</tr>
			<tr>
				<td><label>上架起始日期：</label> 
					<input type="text" name="filter_and_updates_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_updates_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>上架结束日期：</label> 
					<input type="text" name="filter_and_updates_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_updates_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td style="text-align: right;"><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
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
			<%-- 
			<li><a class="add" href="${path}/genepack/erpGenepack!toAddGenepack.action"  target="navTab" rel="add"><span>增加物品</span></a></li>
			<li><a class="delete" href="${path}/genepack/erpGenepack!delGenepack.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除物品</span></a></li>
			<li><a class="edit" onclick="changeProductw()" style="cursor:pointer;"><span>变更</span></a></li> 
			--%>
		 <web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.genepack.web.GenepackAction" 
								informationTableId="exportExcelTable"
								fileName="genepack"></web:exportExcelTag>
		
		</ul>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">带回</th>
				<th width="60"><nobr>批次号</nobr></th>
				<th width="50"><nobr>远盟联系人</nobr></th>
				<th><nobr>寄件单位</nobr></th>
				<th><nobr>上架日期</nobr></th>
				<th><nobr>货架号</nobr></th>
				<th><nobr>发货批次号</nobr></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="genepack" varStatus="status">
					<tr target="sid_user" rel="${genepack.id }">
						<td>
							<a class="btnSelect" href="javascript:$.bringBack({
								id:'${genepack.id }', 
								deliverybatchno:'${genepack.deliverybatchno}', 
								downdates:'${ fn:substring(genepack.downdates,0,10) }',
								updates:'${ fn:substring(genepack.updates,0,10) }',
								ymsalesman:'${genepack.ymsalesman }', 
								sendercompany:'${genepack.sendercompany}',
								shelvesno:'${ genepack.shelvesno }',
								batchno:'${genepack.batchno}'
							})" 
							title="查找带回">选择</a>${ status.count }
						</td>
						<td align="center" nowrap="nowrap">
							<%-- <a title="场次信息" target="dialog" width="1000"  href="${path}/events/erpCustomer!toEventNoListCustomer.action?id=${genepack.id}">${genepack.eventsNo}</a> --%>
							<a title="基因信息" target="navTab" width="1000"  href="${path}/genepack/erpGenepackDetail!toGenepackDetail.action?id=${genepack.id}">${genepack.batchno}</a>
						</td>
						<td align="center" nowrap="nowrap">	${genepack.ymsalesman}</td>
						<td align="center" nowrap="nowrap">	${genepack.sendercompany}</td>
						<td align="center" nowrap="nowrap">	${fn:substring(genepack.updates,0,19)}</td>
						<td align="center" nowrap="nowrap">	${genepack.shelvesno}</td>
						<td align="center" nowrap="nowrap">	${genepack.deliverybatchno}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 