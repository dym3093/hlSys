<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
	<div class="tip">
		<span>查询</span>
	</div>

	<div class="pageHeader" style="overflow: hidden;">
		<form id="pagerFindForm" onsubmit="this.action='${path}/warehouse/storegeReturn!liststoregeReturn.action' ; return navTabSearch(this);" 
			action="${path}/warehouse/storegeReturn!liststoregeReturn.action" 
			method="post"
			rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tbody>
					<tr>
						<td>
							<label>开始日期：</label>	
							<input type="hidden"/>
							<input type="text" name="params.startDate"  id="d1" readonly="readonly" value="${params.startDate}" onFocus="WdatePicker({minDate:'2015-01-01',maxDate:'#F{$dp.$D(\'d2\')}'});" class="textInput readonly" />
					  		<a class="inputDateButton " href="javascript:void(0);" >选择</a>
						</td>	
						<td>
							<label>结束日期：</label>	
							<input type="text" name="params.endDate" id="d2" readonly="readonly" value="${params.endDate}"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'});" class="textInput readonly"/>
							<a class="inputDateButton" href="javascript:void(0);">选择</a>
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

	<div class="pageContent j-resizeGrid">
		<div class="panelBar">
			<ul class="toolBar">
				<li class=""><a class="add" href="javascript:void(0);" onclick="addReturn();"><span>新增</span></a></li>
			</ul>
			<jsp:include page="/common/pagination.jsp" />
		</div>
			<table class="list" style="width:100%; overflow: auto" layoutH="108" id="exportExcelTable">
				<thead>
					<tr>
						<th style="width: 40px; ">序号</th>
						<th style="width: 80px; " nowrap="nowrap" export="true" columnEnName="projectCode" columnChName="项目编码">项目编码</th>
						<th style="width: 150px; " nowrap="nowrap" export="true" columnEnName="projectName" columnChName="项目名称">项目名称</th>
						<th style="width: 80px; "  nowrap="nowrap" export="true" columnEnName="projectowner" columnChName="项目负责人">项目负责人</th>
						<th style="width: 120px; "  nowrap="nowrap" export="true" columnEnName="storege" columnChName="仓库">仓库</th>
						<th style="width: 120px; "  nowrap="nowrap" export="true" columnEnName="product" columnChName="退回产品名称">退回产品名称</th>
						<th style="width: 80px; " nowrap="nowrap" export="true" columnEnName="unitPrice" columnChName="单价">单价</th>
						<th style="width: 80px; " nowrap="nowrap" export="true" columnEnName="returnNumber" columnChName="数量">数量</th>
						<th style="width: 80px; " nowrap="nowrap" export="true" columnEnName="cost" columnChName="总金额">总金额</th>
						<th style="width: 80px; " nowrap="nowrap" export="true" columnEnName="expressCost" columnChName="快递费">快递费</th>
						<th style="width: 120px; " nowrap="nowrap" export="true" columnEnName="createTime" columnChName="创建日期">创建日期</th>
					</tr>
				</thead>
				<tbody style="text-align: center">
					<c:forEach items="${page.results }" var="item" varStatus="stat">
						<tr target="sid_user" rel="${item.id }" >
							<td>${stat.count }</td>
							<td>${item.projectCode }</td>
							<td>${item.projectName }</td>
							<td>${item.projectowner }</td>
							<td>${item.storege }</td>
							<td>${item.product }</td>
							<td>${item.unitPrice }</td>
							<td>${item.returnNumber }</td>
							<td>${item.cost }</td>
							<td>${item.expressCost}</td>
							<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	
	</div>

<script type="text/javascript">
	
	function resetDo() {
		$("input", navTab.getCurrentPanel()).each(function() {
			$(this).val("");
		});
	}
	function addReturn() {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("productAdd", "${path }/warehouse/storegeReturn!toAddReturn.action?navTabId="+navTabId, { title:"退货信息新增", fresh:false, data:{} });
	}

</script>