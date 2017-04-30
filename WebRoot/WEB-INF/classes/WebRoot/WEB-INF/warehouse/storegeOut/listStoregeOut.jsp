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
		<form id="pagerFindForm" 
		onsubmit="this.action='${path}/warehouse/storegeOut!listStoregeOut.action'; return navTabSearch(this);" 
		action="" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="pageFormContent">
					
				<tr>
					<td>
						<label>仓库名称：</label>
						<input type="text"  name="params.storegeName" value="${params.storegeName }"/>
					</td>
					<td>
						<label>申请单号：</label>
						<input type="text"  name="params.applicationNo" value="${params.applicationNo }"/>
						
					</td>
					<td>
						<label>产品名称：</label>
						<input type="text"  name="params.productName" value="${params.productName }"/>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>
						<label>申请人：</label>	
					  	<input type="text" name="params.applyUserName"  value="${params.applyUserName}" />
					  	
					</td>
					<td>
						<label>开始时间：</label>	
						<input type="text" name="params.startDate"  id="d1" style="width: 170px;" readonly="readonly" value="${params.startDate}" onFocus="WdatePicker({minDate:'2015-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})" />
					  	<a class="inputDateButton" href="javascript:;" >选择</a>
					</td>	
					<td>
						<label>结束时间：</label>	
						<input type="text" name="params.endDate" id="d2" style="width: 170px;" readonly="readonly" value="${params.endDate}"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"/>
						<a class="inputDateButton" href="javascript:;">选择</a>
					</td>	
					<td>
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
								<li><a onclick="resetDo();" href="javascript:;" class="button"><span>重置</span></a></li>
							</ul>
						</div>
					</td>
					<td >
					</td>
				</tr>
			
			</table>
			
		</div>
		</form>
	</div>

	<div class="pageContent j-resizeGrid">
		<div class="panelBar">
			<ul class="toolBar">
				<li class=""><a class="edit" href="javascript:void(0);" onclick="updateStoregeOut();"><span>修改</span></a></li>
				<li class="line">line</li>
				<li class=""><a class="icon" href="javascript:void(0);" onclick="backStorege();"><span>退货</span></a></li>
				<li class="line">line</li>
				<web:exportExcelTag 
						pagerFormId="pagerFindForm" 
						pagerMethodName="exportExcel" 
						actionAllUrl="org.hpin.warehouse.web.ErpStoregeOutAction" 
						informationTableId="exportExcelTable"
						fileName="storegeOutData">
				</web:exportExcelTag>
			</ul>
			<jsp:include page="/common/pagination.jsp" />
		</div>
			<table class="list" style="width:100%; overflow: auto" layoutH="108" id="exportExcelTable">
				<thead>
					<tr>
						<th style="width: 45px; ">序号</th>
						<th style="width: 80px; " nowrap="nowrap" export="true" columnEnName="storegeName" columnChName="仓库名称">仓库名称</th>
						<th style="width: 80px; " nowrap="nowrap" export="true" columnEnName="applicationNo" columnChName="申请单号">申请单号</th>
						<th nowrap="nowrap" export="true" columnEnName="productName" columnChName="出库产品名称">出库产品名称</th>
						<th style="width: 60px; " nowrap="nowrap" export="true" columnEnName="price" columnChName="单价">单价</th>
						<th style="width: 60px; " nowrap="nowrap" export="true" columnEnName="quantity" columnChName="数量">数量</th>
						<th style="width: 60px; " nowrap="nowrap" export="true" columnEnName="expressMoney" columnChName="快递费">快递费</th>
						<th style="width: 60px; " nowrap="nowrap" export="true" columnEnName="cost" columnChName="成本">成本</th>
						<th style="width: 60px; " nowrap="nowrap" export="true" columnEnName="amount" columnChName="总金额">总金额</th>
						<th style="width: 80px; " nowrap="nowrap" export="true" columnEnName="cardStart" columnChName="卡号开始">卡号开始</th>
						<th style="width: 80px; " nowrap="nowrap" export="true" columnEnName="cardEnd" columnChName="卡号结束">卡号结束</th>
						<th style="width: 60px; " nowrap="nowrap" export="true" columnEnName="projectCode" columnChName="项目编码">项目编码</th>
						<th style="width: 70px; " nowrap="nowrap" export="true" columnEnName="projectOwner" columnChName="项目负责人">项目负责人</th>
						<th style="width: 110px; " nowrap="nowrap" export="true" columnEnName="projectName" columnChName="项目名称">项目名称</th>
						<th style="width: 60px; " nowrap="nowrap" export="true" columnEnName="createUserName" columnChName="申请人">申请人</th>
						<th style="width: 80px; " nowrap="nowrap" export="true" columnEnName="createTime" columnChName="出库日期">出库日期</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.results }" var="item" varStatus="stat">
						<tr>
							<td><input type="checkbox" name="ids" value="${item.id }"/>${stat.count }</td>
							<td>${item.storegeName }</td>
							<td>${item.applicationNo }</td>
							<td>${item.productName }</td>
							<td style="text-align: right">${item.price }</td>
							<td style="text-align: right">${item.quantity }</td>
							<td style="text-align: right">${item.expressMoney }</td>
							<td style="text-align: right">${item.cost }</td>
							<td style="text-align: right">${item.amount }</td>
							<td>${item.cardStart }</td>
							<td>${item.cardEnd }</td>
							<td>${item.projectCode }</td>
							<td>${item.projectOwner }</td>
							<td>${item.projectName }</td>
							<td>${item.createUserName }</td>
							<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd"/></td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
	
	</div>

<script type="text/javascript">

/**
 * 修改;
 */
function updateStoregeOut() {
	var count = 0;
	var ids = "";
	var status = "";
	
	$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
		ids = val.value ;
		count ++;
	});
	
	if(count == 0) {
		alertMsg.warn("请选择你要修改的信息！");
		return;
	} else if (count > 1) {
		alertMsg.warn("请选择一条数据信息来修改！");
		return;
	}
	
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("updateStoregeOut", "${path }/warehouse/storegeOut!updateStoregeOut.action?id="+ids+"&navTabId="+navTabId, {title:"出库信息修改", fresh:false, data:{} });
	
}

	function resetDo() {
		$("#pagerFindForm :input", navTab.getCurrentPanel()).each(function() {
			$(this).val("");
		}); 
	}

	/** 页面跳转
	*/
	/* function sendEdit(id) {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("storegeOutAdd", "${path }/warehouse/storegeOut!sendEdit.action?id="+id+"&navTabId="+navTabId, {title:"基因物品发货", fresh:false, data:{} });
	} */
	
	/**退货;
	*/
	function backStorege() {
		var count = 0;
		var ids = "";
		var status = "";
		
		$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
			ids = val.value ;
			count ++;
		});
		
		if(count == 0) {
			alertMsg.warn("请选择你要处理的信息！");
			return;
		} else if (count > 1) {
			alertMsg.warn("请选择一条数据信息来退货！");
			return;
		}
		
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("storegeOutBackGoods", "${path }/warehouse/storegeOut!backStorege.action?id="+ids+"&navTabId="+navTabId, {title:"退库", fresh:false, data:{} });
		
	}
	
	
</script>