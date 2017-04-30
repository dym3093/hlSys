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
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/genepack/erpGenepack!toListGenepack.action'){this.action = '${path}/genepack/erpGenepack!toListGenepack.action' ;} ;return navTabSearch(this);" action="${path}/genepack/erpGenepack!toListGenepack.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" name="filter_and_batchno_LIKE_S" value="${filter_and_batchno_LIKE_S}"/>
				</td>
				<td>
					<label>快递单号：</label> 
					<input type="text" name="filter_and_expressno_LIKE_S" value="${filter_and_expressno_LIKE_S}"/>
				</td>
				
				<td>
					<label>远盟联系人：</label> 
					<input type="text" name="filter_and_ymsalesman_LIKE_S" value="${filter_and_ymsalesman_LIKE_S}"/> 
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>货架号：</label> 
					<input type="text" name="filter_and_shelvesno_LIKE_S" value="${filter_and_shelvesno_LIKE_S}"/>
				</td>
				<td><label>上架起始日期：</label> 
					<input type="text" name="filter_and_updates_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_updates_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>上架结束日期：</label> 
					<input type="text" name="filter_and_updates_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_updates_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
				<%-- <td>
					<label>是否发货：</label> 
					<select id="isExpress" name="filter_and_isExpress_EQ_I" rel="iselect">
						 <option value="">请选择</option> 
						 <option value=1 <c:if test="${filter_and_isExpress_EQ_I==1 }">selected="selected"</c:if>>是</option>
						 <option value=0 <c:if test="${filter_and_isExpress_EQ_I==0 }">selected="selected"</c:if>>否</option>
					</select> 
				</td> --%>
			</tr>
			
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
		<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		<c:if test="${currentUser.accountName!='parkson'}">
		<ul class="toolBar">
			<li><a class="add" href="${path}/genepack/erpGenepack!toAddGenepack.action"  target="navTab" rel="add"><span>增加物品</span></a></li>
			<li><a class="delete" href="${path}/genepack/erpGenepack!delGenepack.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除物品</span></a></li>
			<li><a class="edit" onclick="changeProductw()" style="cursor:pointer;"><span>变更</span></a></li>
		 <web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.genepack.web.GenepackAction" 
								informationTableId="exportExcelTable"
								fileName="genepack"></web:exportExcelTag>
		
		</ul>
		</c:if>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "batchno" columnChName = "批次号" >批次号</th>
				<th  export= "true" columnEnName = "expressno" columnChName = "快递单号" >快递单号</th>
				<th  export= "true" columnEnName = "expresscompany" columnChName = "快递公司" >快递公司</th>
				<th  export= "true" columnEnName = "ymsalesman" columnChName = "远盟联系人">远盟联系人</th>
				<th  export= "true" columnEnName = "sender" columnChName = "寄件人" >寄件人</th>
				<th  export= "true" columnEnName = "senderphone" columnChName = "寄件人电话">寄件人电话</th>
				<th  export= "true" columnEnName = "senderaddress" columnChName = "寄件地址" >寄件地址</th>
				<th  export= "true" columnEnName = "sendercompany" columnChName = "寄件单位" >寄件单位</th>
				<th  export= "true" columnEnName = "updates" columnChName = "上架日期" >上架日期</th>
				<th  export= "true" columnEnName = "downdates" columnChName = "下架日期" >下架日期</th>
				<th  export= "true" columnEnName = "shelvesno" columnChName = "货架号" >货架号</th>
				<th  export= "true" columnEnName = "deleverybatchno" columnChName = "发货批次号" >发货批次号</th>
			    <th  export = "false" columnEnName = "" columnChName = "" >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="genepack" varStatus="status">
					<tr target="sid_user" rel="${genepack.id }">
						<td align="center">
							<c:if test="${currentUser.accountName!='parkson'}">
								<input type="checkbox" name="ids" value="${genepack.id}">
							</c:if>
							${ status.count }
						</td>
						<td align="center">
							<%-- <a title="场次信息" target="dialog" width="1000"  href="${path}/events/erpCustomer!toEventNoListCustomer.action?id=${genepack.id}">${genepack.eventsNo}</a> --%>
							<a title="基因信息" target="navTab" width="1000"  href="${path}/genepack/erpGenepackDetail!toGenepackDetail.action?id=${genepack.id}">${genepack.batchno}</a>
						</td>
						<td align="center">	${genepack.expressno}</td>
						<td align="center">	${genepack.expresscompany}</td>
						<td align="center">	${genepack.ymsalesman}</td>
						<td align="center">	${genepack.sender}</td>
						<td align="center">	${genepack.senderphone}</td>
						<td align="center">	${genepack.senderaddress}</td>
						<td align="center">	${genepack.sendercompany}</td>
						<td align="center">	${fn:substring(genepack.updates,0,19)}</td>
						<td align="center">	${fn:substring(genepack.downdates,0,19)}</td>
						<td align="center">	${genepack.shelvesno}</td>
						<td align="center">	${genepack.deliverybatchno}</td>
						
						<td align="center">
							<div class="panelBar">
								<ul class="toolBar">
									<li><a class="edit" onclick="changeProduct('${genepack.id}')" style="cursor:pointer;"><span>变更</span></a></li>
								<%-- 	<li><a class="add" href="${path}/genepack/erpGenepack!importCustomer.action?id=${genepack.id}" target="navTab" rel="add"><span>导入</span></a></li> --%>
								</ul>
								</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 