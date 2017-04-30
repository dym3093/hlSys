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
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/sampleExpressMgr!listEvents.action?navTabId=${parentNavTabId}')
		{this.action = '${path}/events/sampleExpressMgr!listEvents.action?navTabId=${parentNavTabId}' ;}; return dialogSearch(this);" 
		action="${path}/events/sampleExpressMgr!listEvents.action?navTabId=${parentNavTabId}" method="post"  rel="pagerForm">
		
	<input name="parentNavTabIdCusHidden" type="hidden" value="${parentNavTabId}" />
	<input name="sampleExpMgrIdCusHidden" type="hidden" value="${sampleExpMgrId}" />
	<input name="expressNumCusHidden" type="hidden" value="${expressNum}" />
	<input name="expressCompanyIdCusHidden" type="hidden" value="${expressCompanyId}" />
	<input name="weightCusHidden" type="hidden" value="${weight}" />
	<input name="totalCostCusHidden" type="hidden" value="${totalCost}" />
	<input name="receiveSendDateCusHidden" type="hidden" value="${receiveSendDate}" />
	<input name="isbillCusHidden" type="hidden" value="${isbill}" />
	<input name="receiveSendTypeCusHidden" type="hidden" value="${receiveSendType}" />
	<input name="expressContentCusHidden" type="hidden" value="${expressContent}" />
	<input name="rootNavTabId" type="hidden" value="${rootNavTabId}" />
		
	<div class="searchBar">
		<table class="searchContent">
			<tbody>
				<tr>
					<td>
						<label>批次号：</label>
						<input type="text" name="eventsBatchno" value="${eventsBatchno}" />
					</td>
					<td>
						<label>导入起始日期：</label> 
						<input type="text" name="samplingDateGest"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${samplingDateGest}" /><a class="inputDateButton" href="javascript:;" >选择</a>
					</td>
					<td>
						<label>导入结束日期：</label> 
						<input type="text" name="samplingDateLeet" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${samplingDateLeet}" /><a class="inputDateButton" href="javascript:;">选择</a>
					</td>
					<td>
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button id="addByEveResetBtn" type="button">重置</button></div></div></li>
							</ul>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	</form>
</div>

<div class="pageContent j-resizeGrid">
	<div class="panelBar">
		<ul class="toolBar">
			<li class=""><a id="addEventsBtn" class="edit" href="javascript:void(0);" onclick="saveByEvents();"><span>确认添加</span></a></li>
		</ul>
		<jsp:include page="/common/paginationDialog.jsp" />
	</div>
		<table class="list" style="width:100%; overflow: auto" layoutH="108">
			<thead>
				<tr>
					<th style="width: 45px; ">序号<input type="checkbox" class="checkboxCtrl" group="ids" /></th>
					<th style="width: 200px; ">批次号</th>
					<th style="width: 200px; ">场次号</th>
					<th style="width: 200px; ">场次日期</th>
					<th style="width: 200px; ">支公司</th>
					<th style="width: 200px; ">所属公司</th>
					<th style="width: 200px; ">已录入人数</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="erpEvents" varStatus="status">
					<tr target="sid_user" rel="${erpEvents.eveid}">
						<td align="left"><input type="checkbox" name="ids" value="${erpEvents.eveid}">${ status.count }</td>
						<td align="center">${erpEvents.eventsBatchno}</td>
						<td align="center">${erpEvents.eventsNo}</td>
						<td align="center">${fn:substring(erpEvents.eventsDate,0,14)}00:00</td>
						<td align="center"><hpin:id2nameDB id="${erpEvents.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
						<td align="center"><hpin:id2nameDB id="${erpEvents.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
						<td align="center">${erpEvents.hasInHead}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</div>
</div>

<script type="text/javascript">
$(function(){
	$("#addByEveResetBtn").click(function(){
		var formObj = $(this).closest("form[id='pagerFindForm']");
		formObj.find("input[name='eventsBatchno']").val('');
		formObj.find("input[name^='samplingDate']").val('');
	});
	
	
});

function saveByEvents(){
	$("#addEventsBtn").attr("disabled","disabled");
	var expressNum = $("input[name='expressNumCusHidden']").val();
	var expressCompanyId = $("input[name='expressCompanyIdCusHidden']").val();
	var weight = $("input[name='weightCusHidden']").val();
	var totalCost = $("input[name='totalCostCusHidden']").val();
	var receiveSendDate = $("input[name='receiveSendDateCusHidden']").val();
	var isbill = $("input[name='isbillCusHidden']").val();
	var receiveSendType = $("input[name='receiveSendTypeCusHidden']").val();
	var expressContent = $("input[name='expressContentCusHidden']").val();
	$("#addCustomerBtn").attr("disabled","disabled");
	var navTabId = $("input[name='parentNavTabIdCusHidden']").val();
	var sampleExpMgrId = $("input[name='sampleExpMgrIdCusHidden']").val();
	var rootNavTabId = $("input[name='rootNavTabId']",navTab.getCurrentPanel()).val();
	var idArr = [];
	var count = 0;
	var status = '';
	$("input:checkbox[name='ids']:checked", $.pdialog.getCurrent()).each(
		function(i, n) {
			idArr.push($(this).val());
			count += 1;
		});
	if (count == 0) {
		alertMsg.info('请选择你要添加的批次！');
		return;
	}
	var ids = idArr.join();
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/events/sampleExpressMgr!saveByEvents.action",
		data: {'navTabId':navTabId,'ids':ids,'sampleExpMgrId':sampleExpMgrId,'expressNum':expressNum,'expressCompanyId':expressCompanyId,'weight':weight,'totalCost':totalCost,'receiveSendDate':receiveSendDate,'isbill':isbill,'receiveSendType':receiveSendType,'expressContent':expressContent,'rootNavTabId':rootNavTabId},
		success: function(data){
			if(data.statusCode=="200"){
				alertMsg.correct("添加成功！");
				navTab.reload(data.forwardUrl,{});
				var dialog = $.pdialog.getCurrent();
				if($.pdialog.reload(dialog.data("url"))){
					$("#addEventsBtn").removeAttr("disabled");
				}
			}else{
				alertMsg.error("添加失败！");
				$("#addEventsBtn").removeAttr("disabled");
			}
		},
		error :function(data){
			alertMsg.error("服务发生异常，请稍后再试！");
			$("#addEventsBtn").removeAttr("disabled");
		}
	});
};
</script>





