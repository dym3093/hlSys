<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<style>
#codeQueReport {
	display: none;
}
</style>
<script type="text/javascript" language="javascript" src="${path }/scripts/plugin/shieldLayer_plugin.js"></script>
<script type="text/javascript" language="javascript">

function submitForm() {
	$(".pageForm", navTab.getCurrentPanel()).submit();

}

$(document).ready(function(){
	$("#button").click(function() {
		$("#codeQueReport").css("display", "block");
		$("#manyQueReport").css("display", "none");
	});
	
	$("#back").click(function() {
		$("#codeQueReport").css("display", "none");
		$("#manyQueReport").css("display", "block");
	});

});

function batchDeal(){
	var checkedBoxs = $("input[name='ids']:checked");
	var ids="";
	for(var i = 0;i<checkedBoxs.length;i++){
		ids+=","+$(checkedBoxs[i]).val();
	}
	if(ids.length>1){
		ids = ids.substring(1);
	}else{
		alertMsg.warn("请至少选择一条数据！");
		return;
	}
	$.ajax({
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/reportdetail/reportDelivery!findBranchCompanyByEventIds.action",
		data:{"eventsIds":ids},
		success: function(data){
			eval("data = "+data);
			if(data.status=='200'){
				if(data.flag){
					alertMsg.confirm('<span style="color:#FF0000;">批量选择了不同支公司</span>', {
				        okCall: function(){
							var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
							navTab.openTab("batchDeal", "${path}/reportdetail/reportDelivery!toBatchDeal.action?ids="+ids+"&navTabId="+navTabId, { title:"场次信息", fresh:false});
				        }
					});
				}else{
					var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
					navTab.openTab("batchDeal", "${path}/reportdetail/reportDelivery!toBatchDeal.action?ids="+ids+"&navTabId="+navTabId, { title:"场次信息", fresh:false});
				}
			}else if(data.status=='300'){
				alert(data.message);
			}
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}
//批量录入
function batchInput(){
	var checkedBoxs = $("input[name='ids']:checked");
	var ids="";
	for(var i = 0;i<checkedBoxs.length;i++){
		ids+=","+$(checkedBoxs[i]).val();
	}
	if(ids.length>1){
		ids = ids.substring(1);
	}else{
		alertMsg.warn("请至少选择一条数据！");
		return;
	}
	$.ajax({
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/reportdetail/reportDelivery!findRepeatExpressByeventIds.action",
		data:{"eventsIdss":ids},
		success: function(data){
			eval("data = "+data);
			if(data.status=='200'){
				if(data.flag){
					alertMsg.confirm('<span style="color:#FF0000;">包含已寄送报告的客户</span>', {
				        okCall: function(){
				        	$.pdialog.open("${path}/reportdetail/reportDelivery!toExpressEntry.action?allEventsIds="+ids, 
				        			"expressEntry", "快递录入",{width:750,height:240,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
				        }
					})
				}else{
					$.pdialog.open("${path}/reportdetail/reportDelivery!toExpressEntry.action?allEventsIds="+ids, 
							"expressEntry", "快递录入",{width:750,height:240,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
				}
			}else if(data.status=='300'){
				alert(data.message);
			}
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	})
}

function expressEntry(eventNo){
	$.ajax({
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/reportdetail/reportDelivery!findRepeatExpressByeventNo.action",
		data:{"eventNo":eventNo},
		success: function(data){
			eval("data = "+data);
			if(data.status=='200'){
				if(data.flag){
					alertMsg.confirm('<span style="color:#FF0000;">包含已寄送报告的客户</span>', {
				        okCall: function(){
				        	$.pdialog.open("${path}/reportdetail/reportDelivery!toExpressEntry.action?eventNo="+eventNo, 
				        			"expressEntry", "快递录入",{width:750,height:240,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
				        }
					})
				}else{
					$.pdialog.open("${path}/reportdetail/reportDelivery!toExpressEntry.action?eventNo="+eventNo, 
							"expressEntry", "快递录入",{width:750,height:240,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
				}
			}else if(data.status=='300'){
				alert(data.message);
			}
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	})
}

function expressDetail(){
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("expressDetail", "${path}/reportdetail/reportDelivery!expressDetail.action?navTabId="+navTabId, { title:"寄送明细", fresh:false});
}


</script>

<!--蒙版 start-->
<!--必须要的部分-->
    <div class="py_theMb"><!--蒙版-->
        <div class="py_bakpic"><!--图片-->
    </div>
    </div>
    <!--必须要的部分-->
<!--蒙版 end-->

<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<div class="searchBar" id="manyQueReport">
		<form id="pagerFindForm" 
			onsubmit="if(this.action != '${path}/reportdetail/reportDelivery!listReportDelivery.action'){this.action = '${path}/reportdetail/reportDelivery!listReportDelivery.action' ;} ;return navTabSearch(this);"
			action="${path}/reportdetail/reportDelivery!listReportDelivery.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent" style="overflow-y:hidden">
				<tr>
					<td style="white-space:nowrap"><label>批次号：</label> 
						<input type="text" id="queryTypeId" name="searchQuery.batchNo" value="${searchQuery.batchNo}" />
					</td>
					<td><label>支公司：</label> <%-- <input type="text" name="filter_and_branchCompany_LIKE_S" value="${filter_and_branchCompany_LIKE_S}"/> --%>
						<input type="hidden" id="id"
						name="searchQuery.branchCommany" bringbackname="customer.id"
						value="${searchQuery.branchCommany}" /> <input type="text"
						id="branchCompany" name="aaaa"
						bringbackname="customer.branchCommanyName" value="${aaaa}"
						readonly="readonly" /> <a class="btnLook"
						href="${ path }/resource/customerRelationShip!findCustomerRelationShip.action"
						lookupGroup="customer" target="dialog" width="800" height="480">查找带回</a>
					</td>
					<td>
						<input type="radio" name="searchQuery.queryType" value="1" checked="checked" />精准查询
						<input type="radio" name="searchQuery.queryType" value="2" />模糊查询 
					</td>
					<td>
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
								<li><a id="clearText" href="javascript:;" class="button"><span>重置</span></a></li>
								<li><div class="buttonActive"><div class="buttonContent"><button onclick="expressDetail()" type="button">邮寄明细</button></div></div></li>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td><label>起始日期：</label> <input type="text" name="searchQuery.startTime" id="d1" style="width: 170px;"
						onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"
						readonly="readonly" value="${searchQuery.startTime}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<td><label>结束日期：</label> <input type="text"
						name="searchQuery.endTime" id="d2" style="width: 170px;"
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
						readonly="readonly" value="${searchQuery.endTime}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
						<td>
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button id="button" type="button">条形码查找场次</button></div></div></li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
	</form>
	</div>
	<div class="searchBar" id="codeQueReport">
		<table class="pageFormContent">
			<tr>
				<td>
					<div class="buttonActive">
						<div class="buttonContent">
							<button id="back">返回</button>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<form id="pagerFindForm"
			onsubmit="if(this.action != '${path}/reportdetail/reportDelivery!listReportDeliveryByCode.action'){this.action = '${path}/reportdetail/reportDelivery!listReportDeliveryByCode.action' ;} ;return navTabSearch(this);"
			action="${path}/reportdetail/reportDelivery!listReportDeliveryByCode.action"
			method="post" rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent">
				<tr>
					<td><label>条形码：</label> <input type="text"
						name="customerCode" value="${customerCode}" /></td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查找</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="clearText">重置</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div class="pageContent">
	<div class="panelBar">
		<c:if test="${currentUser.userType!='查询用户'}">
			<ul class="toolBar">
				<li><a class="icon"	href="javascript:void(0);" onclick="batchDeal()" 
					><span>批量处理</span></a></li>
				<li><a class="icon"	href="javascript:void(0);" onclick="batchInput()" 
					><span>批量录入</span></a></li>
				<li><a class="add" href="${path}/reportdetail/reportDelivery!importReportExpress.action" target="navTab" rel="add">
					<span>导入Excel</span></a></li>
			</ul>
		</c:if>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th export="true" columnEnName="batchNo" columnChName="批次号">批次号</th>
				<th export="true" columnEnName="eventsNo" columnChName="场次号">场次号</th>
				<th export="true" columnEnName="eventDate" columnChName="场次日期">场次日期</th>
				<th export="true" columnEnName="branchCommany" columnChName="支公司">支公司</th>
				<th export="true" columnEnName="alreadyCount" columnChName="已录人数">已录人数</th>
				<th export="true" columnEnName="haveReportCount" columnChName="已寄送报告数">已寄出报告数</th>
				<c:if test="${currentUser.userType!='查询用户'}">
					<th export="false" columnEnName="" columnChName="">操作</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpReport" varStatus="status">
				<tr target="sid_user" rel="${erpReport.id }">
					<td align="center"><c:if
							test="${currentUser.accountName!='parkson'}">
							<input type="checkbox" name="ids" value="${erpReport.id}">
						</c:if> ${ status.count }</td>
					<td align="center">${erpReport.batchNo}</td>
					<td align="center">
						<a title="场次信息" target="navTab" width="1000"
						href="${path}/reportdetail/reportDelivery!toBatchDeal.action?ids=${erpReport.id}">${erpReport.eventsNo}</a>
					</td>

					<td align="center">${fn:substring(erpReport.eventsDate,0,19)}</td>
					<td align="center">${erpReport.branchCommany}</td>
					<td align="center">${erpReport.alreadyCount}</td>
					<td align="center">${erpReport.haveReportCount}</td>
					
					<c:if test="${currentUser.userType!='查询用户'}">
						<td align="center">
							<div class="panelBar" style="width:250px;">
								<ul class="toolBar" >
									<li><a class="add"  href="javascript:void(0);" onclick="expressEntry('${erpReport.eventsNo}')">
										<span>录入快递信息</span></a></li>
                        		</ul>
							</div>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
