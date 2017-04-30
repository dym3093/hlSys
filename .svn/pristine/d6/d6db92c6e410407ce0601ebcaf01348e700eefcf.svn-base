<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	//【保险公司结算任务-->添加场次页面】
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<style>
#codeQue {
	display: none;
}
</style>

<script type="text/javascript" language="javascript">



$(function() {
	
	$("#button").click(function() {
		$("#codeQue").css("display", "block");
		$("#manyQue").css("display", "none");
	});

	$("#back").click(function() {
		$("#codeQue").css("display", "none");
		$("#manyQue").css("display", "block");
	});

	//不可选的场次变色
	if($("table[class='list'] tbody tr").length>0){
		$("table[class='list'] tbody tr").each(function(){
			var statusBX = $(this).find("td").eq(14).text().trim();
			if(statusBX=="已全部结算"){
				$(this).css("background-color","#D3D3D3");
			}
		});
	}
});

	//清除查找框中的所有内容
	$("#clearText",$.pdialog.getCurrent()).click(function(){
		$("#pagerFindForm",$.pdialog.getCurrent()).find("input").each(function(){
			if($(this).attr("name")!="configId"){
				$(this).val("");
			}
		});
		$("#pagerFindForm",$.pdialog.getCurrent()).find("select").each(function(){
			$(this).val("");
		});
	});	
	
	//变更用于复选框
	function changeProduct() {
		var ids = '';
		var count = 0;
		var status = '';
		$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
				function(i, n) {
					ids = n.value;
					count += count + 1;
					status = $(this).parent().next().text();
				});
		if (count == 0) {
			alert('请选择你要变更的信息！');
			return;
		} else if (count > 1) {
			alert('只能选择一条信息进行变更！');
			return;
		} else {
			var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("modifyEvents",
					"../events/erpEvents!toModifyEvents.action?id=" + ids
							+ "&navTabId=" + navTabId, {
						title : "变更",
						fresh : false,
						data : {}
					});
		}
	}

	//变更用于单行
	function changeProduct(ids) {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyEvents",
			"../events/erpEvents!toModifyEvents.action?id=" + ids
					+ "&navTabId=" + navTabId, {
				title : "变更",
				fresh : false,
				data : {}
			});
	
	}

		
	//弹出修改场次价格的窗口 
	function toEditEventsPrice(){
			var ids = '';
			var count = 0;
			var status = '';
			$('input[name="ids"]:checked').each(
					function(i, n) {
						ids = n.value;
						count += 1;
						//status = $(this).parent().next().text();
					});
			if (count == 0) {
				alertMsg.info('请选择修改价格的场次！');
				return;
			} else if (count > 1) {
				alertMsg.info('只能选择一条！');
				return;
			} 
		//获取当前页面id
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		$.pdialog.open("${path}/events/erpEvents!toEditEventsPrice.action?eventsId="+ids+"&navTabId="+navTabId, "toEditEventsPrice", "修改场次价格", {width:600,height:200});
	}
	
	//添加场次到结算任务
	function addEventsToSettle(){
		
		$("#addEvents").attr("disabled","disabled");
		
		var idArr = [];
		//场次号
		var eventsNoArr = [];
		
		var count = 0;
		var status = '';
		var eventsNo = '';
		$('input[name="ids"]:checked').each(
				function(i, n) {
					idArr.push($(this).val());
					eventsNo = $(this).parent("td").next().text().trim();
					eventsNoArr.push(eventsNo);
					count += 1;
					//status = $(this).parent().next().text();
				});
		if (idArr.length < 1) {
			alertMsg.info('请选择你要添加的场次！');
			return;
		}
		
		var ids = idArr.join();
		var eventsNos = eventsNoArr.join();
		
		var settleId = $("#settleId").val();
		var navTabId = $("#navTabId").val();
		var taskNo = $("#taskNo").val();
		var settlementTime = $("#settlementTime").val();
		var type = $("#type").val();

		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "${path}/settlementManagement/erpSettlementTaskBX!addEventsToSettleBXNew.action",
			data: {'settleId': settleId, 'navTabId':navTabId, 'ids':ids, "taskNo":taskNo, "settlementTime":settlementTime, "type":type, "eventsNos":eventsNos},
			success: function(data){
				if(data.result=="success"){
					alertMsg.correct("保存成功！");					
					var dialog = $.pdialog.getCurrent();
					//$.pdialog.closeCurrent();
					navTab.reload(data.forwardUrl,{});
					if($.pdialog.reload(dialog.data("url"))){
						$("#addEvents").removeAttr("disabled");
					}
				}else{
					alertMsg.error(data.msg);
					$("#addEvents").removeAttr("disabled");
				}	
			},
			error :function(data){
				alertMsg.error("服务发生异常，请稍后再试！");
				$("#addEvents").remove("disabled");
			}
		});	
	}

		
</script>
<div class="tip">
	<span>查询</span>
	<input id="settleId" name="settleId" type="hidden" value="${settleId }" />
	<input id="navTabId" name="navTabId" type="hidden" value="${navTabId }" />
	<input id="taskNo" name="taskNo" type="hidden" value="${taskNo }" />
	<input id="settlementTime" name="settlementTime" type="hidden" value="${settlementTime }" />
	<input id="type" name="type" type="hidden" value="${type }">
	<input id='data' name='data' type='hidden' value='${data }'>
</div>
<div class="pageHeader">	
    <div class="searchBar" id="manyQue">
		<form id="pagerFindForm"
			onsubmit="if(this.action != '${path}/settlementManagement/erpSettlementTaskBX!toAddEvents.action?settleId=${settleId}&navTabId=${navTabId}')
			{this.action = '${path}/settlementManagement/erpSettlementTaskBX!toAddEvents.action?settleId=${settleId}&navTabId=${navTabId}' ;} ;return dialogSearch(this);"
			action="${path}/settlementManagement/erpSettlementTaskBX!toAddEvents.action?settleId=${settleId}&navTabId=${navTabId}" method="post"
			rel="pagerForm">
			<table class="pageFormContent">
				<tr>
					<td><label>场次号：</label> <input type="text"
						name="filter_and_eventsNo_LIKE_S"
						value="${filter_and_eventsNo_LIKE_S}" /></td>
					<td><label>支公司：</label>
						<input type="hidden" id="id"
						name="filter_and_branchCompanyId_EQ_S" bringbackname="customer.id"
						value="${filter_and_branchCompanyId_EQ_S}" /> <input type="text"
						id="branchCompany" name="aaaa"
						bringbackname="customer.branchCommanyName" value="${aaaa}"
						readonly="readonly" /> <a class="btnLook"
						href="${ path }/resource/customerRelationShip!findCustomerRelationShip.action"
						lookupGroup="customer" target="dialog" width="800" height="480">查找带回</a>
						<%-- <img src="${path}/images/clear.png" title="清除公司信息" id="customer" style="padding-top: 6px;"/> --%>
					</td>
					<td><label>所属公司：</label> <input type="hidden"
						id="ownedCompanyId" name="filter_and_ownedCompanyId_EQ_S"
						bringbackname="dept.ownedCompany"
						value="${filter_and_ownedCompanyId_EQ_S}" /> <input type="text"
						id="ownedCompany" name="bbbb"
						bringbackname="dept.customerNameSimple" value="${bbbb}"
						readonly="readonly" /> <a class="btnLook"
						href="${ path }/resource/customerRelationShip!lookDept.action"
						lookupGroup="dept" target="dialog" width="800" height="480">查找带回</a>
					</td>
					
				</tr>
				<tr>
					<td><label>起始日期：</label> <input type="text"
						name="filter_and_eventDate_GEST_T" id="d1" style="width: 170px;"
						onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"
						readonly="readonly" value="${filter_and_eventDate_GEST_T}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<td><label>结束日期：</label> <input type="text"
						name="filter_and_eventDate_LEET_T" id="d2" style="width: 170px;"
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
						readonly="readonly" value="${filter_and_eventDate_LEET_T}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<td><label>批次号：</label> <input type="text"
						name="filter_and_batchno_EQ_S" value="${filter_and_batchno_EQ_S}" />
					</td>
				</tr>
				<tr>

					<td><label>省：</label>
						<select id="province" name="filter_and_province_EQ_S"
						rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
						ref="city"
						refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
							<option value="${filter_and_province_EQ_S}"></option>
					</select></td>
					<td><label>市：</label>
						<select id="city" name="filter_and_city_EQ_S" rel="iselect">
							<option value="${filter_and_city_EQ_S}"></option>
						</select>
					</td>
					<td><label>级别：</label>
						<select name="filter_and_level2_LIKE_S" rel="iselect"
								loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10103">
							<option value="${filter_and_level2_LIKE_S}"></option>
					</select></td>
					<td><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查找</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="clearText">重置</button>
							</div>
						</div></td>
				</tr>

			</table>
		</form>

	</div>
	<div class="searchBar" id="codeQue">
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
			onsubmit="if(this.action != '${path}/events/erpEvents!findEventsByCode.action'){this.action = '${path}/events/erpEvents!findEventsByCode.action' ;} ;return navTabSearch(this);"
			action="${path}/events/findEventsByCode!listEvents.action"
			method="post" rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent">
				<tr>
					<td><label>条形码：</label> <input type="text"
						name="filter_and_code_EQ_S" value="${filter_and_code_EQ_S}" /></td>
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
		<%-- <web:security tag="noemeruser_enter"> --%>
		<c:if test="${currentUser.userType!='查询用户'}">
			<ul class="toolBar">
				<li><input id="addEvents" type="button" class="add" onclick="addEventsToSettle()" value="添加场次会员" /></li>
			</ul>
		</c:if>
		<%-- </web:security> --%>
		<jsp:include page="/common/paginationDialog.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="80"><input id="selectAll" type="checkbox" class="checkboxCtrl" group="ids" />序号</th>
				<th export="true" columnEnName="eventsNo" columnChName="场次号">场次号</th>
				<th export="true" columnEnName="eventDate" columnChName="场次日期">场次日期</th>
				<th export="true" columnEnName="provice" columnChName="省"
					id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
				<th export="true" columnEnName="city" columnChName="市"
					id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
				<th export="true" columnEnName="branchCompanyId"
					id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"
					columnChName="支公司">支公司</th>
				<th export="true" columnEnName="ownedCompanyId"
					id2NameBeanId="org.hpin.base.usermanager.dao.UserDao"
					columnChName="所属公司">所属公司</th>
				<th export="true" columnEnName="comboName" columnChName="套餐">套餐</th>
				<th export="true" columnEnName="level2" columnChName="级别"
					id2NameBeanId="org.hpin.base.dict.dao.SysDictTypeDao">级别</th>
				<th export="true" columnEnName="headcount" columnChName="预计人数">预计人数</th>
				<th export="true" columnEnName="nowHeadcount" columnChName="已录人数">已录人数</th>
				<th export="true" columnEnName="pdfcount" columnChName="已出报告数">已出报告数</th>
				<th export="true" columnEnName="nopdfcount" columnChName="未出报告数">未出报告数</th>
				<th export="true" columnEnName="batchNo" columnChName="批次号">批次号</th>
				<th export="true" columnEnName="ymSalesman" columnChName="远盟对接人">远盟对接人</th>
				<th export="true" columnEnName="statusBX" columnChName="结算状态">结算状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpEvents" varStatus="status">
				<tr target="sid_user" rel="${erpEvents.id }">
					<td align="center">
						<c:if test="${erpEvents.statusBX!='2' && erpEvents.pdfcount!=0 }">
							<input type="checkbox" name="ids" value="${erpEvents.id}">
						</c:if>
                        ${status.count}
					</td>
					<td align="center">${erpEvents.eventsNo}</td>
					<td align="center">${fn:substring(erpEvents.eventDate,0,19)}</td>
					<td align="center"><hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${erpEvents.provice }" /></td>
					<td align="center"><hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${erpEvents.city }" /></td>
					<td align="center"><hpin:id2nameDB id="${erpEvents.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" /></td>
					<td align="center"><hpin:id2nameDB id="${erpEvents.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
					<td align="center">${erpEvents.comboName}</td> <td align="center"><hpin:id2nameDB id='${erpEvents.level2}' beanId='org.hpin.base.dict.dao.SysDictTypeDao' /></td>
					<td align="center">${erpEvents.headcount}</td>
					<td align="center">${erpEvents.nowHeadcount}</td>
					<td align="center">
						<c:choose>
							<c:when test="${erpEvents.pdfcount==0}"><span style="color: #FF0000">报告数量为[${erpEvents.pdfcount}]，请勿添加</span></c:when>
							<c:otherwise> ${erpEvents.pdfcount} </c:otherwise>
						</c:choose>
					</td>
					<td align="center"> ${erpEvents.nopdfcount} </td>
					<td align="center">${erpEvents.batchNo}</td>
					<td align="center">${erpEvents.ymSalesman}</td>
					<td align="center">
						<c:choose>
							<c:when test="${erpEvents.statusBX=='0'}"> <span>未结算</span> </c:when>
							<c:when test="${erpEvents.statusBX=='1'}"> <span>部分结算</span> </c:when>
							<c:when test="${erpEvents.statusBX=='2'}"> <span>已全部结算</span> </c:when>
							<c:otherwise> <span>未结算</span> </c:otherwise>
						</c:choose>
						
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
