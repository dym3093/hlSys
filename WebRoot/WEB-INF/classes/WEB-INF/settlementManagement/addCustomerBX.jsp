<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

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

function saveCustomerToSettleBx(){
	
	$("#addCustomerBtn").attr("disabled","disabled");
	
	var settleId = $("input[name='settleId']").val();
	var navTabId = $("input[name='navTabId']").val();
	var taskNo = $("#taskNo").val();
	var settlementTime = $("#settlementTime").val();
	var idArr = [];
	var count = 0;
	var status = '';
	$('input[name="ids"]:checked').each(
			function(i, n) {
				idArr.push($(this).val());
				count += 1;
			});
	if (count == 0) {
		alertMsg.info('请选择你要添加的会员！');
		return;
	}
	
	var ids = idArr.join();
	
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/settlementManagement/erpSettlementTaskBX!saveCustomerToSettleBxNew.action",
		data: {'settleId': settleId, 'navTabId':navTabId, 'ids':ids, 'taskNo':taskNo, 'settlementTime':settlementTime},
		success: function(data){
			console.log("data: "+data);
			if(data.result=="success"){
				alertMsg.correct("添加成功！");
				//$.pdialog.closeCurrent();
				navTab.reload(data.forwardUrl,{});
				var dialog = $.pdialog.getCurrent();
				if($.pdialog.reload(dialog.data("url"))){
					$("#addCustomerBtn").removeAttr("disabled");
				}
			}else{
				alertMsg.error("添加失败！");
				$("#addCustomerBtn").removeAttr("disabled");
			}	
		},
		error :function(data){
			alertMsg.error("服务发生异常，请稍后再试！");
			$("#addCustomerBtn").removeAttr("disabled");
		}
	});		
};

$(function() {	

	//不可选的场次变色
	if($("table[class='list'] tbody tr").length>0){
		$("table[class='list'] tbody tr").each(function(){
			var statusBX = $(this).find("td").last().text().trim();
			if(statusBX=="已结算"){
				$(this).css("background-color","#D3D3D3");
			}
		});
	};
	
});

</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<input name="settleId" type="hidden" value="${settleId }" />
	<input name="navTabId" type="hidden" value="${navTabId }" />
	<input id="taskNo" name="taskNo" type="hidden" value="${taskNo }" />
	<input id="settlementTime" name="settlementTime" type="hidden" value="${settlementTime }" />

	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/settlementManagement/erpSettlementTaskBX!toAddCustomer.action?settleId=${settleId}&navTabId=${navTabId}')
			{this.action = '${path}/settlementManagement/erpSettlementTaskBX!toAddCustomer.action?settleId=${settleId}&navTabId=${navTabId}' ;}; return dialogSearch(this);"
		  action="${path}/settlementManagement/erpSettlementTaskBX!toAddCustomer.action?settleId=${settleId}&navTabId=${navTabId}" method="post"  rel="pagerForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>场次号：</label> 
					<input type="text" name="filter_and_eventsNo_LIKE_S" value="${filter_and_eventsNo_LIKE_S}"/>
				</td>
				<td>
					<label>姓名：</label> 
					<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}"/>
				</td>
				<td>
					<label>条形码：</label> 
					<input type="text" name="filter_and_code_LIKE_S" value="${filter_and_code_LIKE_S}"/>
				</td>
				<td>
					<label>手机号：</label> 
					<input type="text" name="filter_and_phone_LIKE_S" value="${filter_and_phone_LIKE_S}"/>
				</td>
				
			</tr>
			<tr>
				<td>
					<label>套餐名：</label> 
					<input type="text" name="filter_and_setmealName_LIKE_S" value="${filter_and_setmealName_LIKE_S}"/><input type="hidden" name="id" value="${events.id}"/>
				</td>
				<td><label>采样起始日期：</label> 
					<input type="text" name="filter_and_samplingDate_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_samplingDate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>采样结束日期：</label> 
					<input type="text" name="filter_and_samplingDate_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_samplingDate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
					<label>身份证号：</label> 
					<input type="text" name="filter_and_idno_LIKE_S" value="${filter_and_idno_LIKE_S}"/>
				</td>
				
			</tr>
			<tr>
				<td colspan="4"> <div class="subBar">
           			 <ul>
			       		<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div></li>
			      		<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div></li>
			      </ul>
			   </div></td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<c:if test="${currentUser.userType!='查询用户'}">
			<ul class="toolBar">
			<!-- 	<li>
					<a class="add" href="#" onclick="saveCustomerToSettleBx()" rel="add"><span>添加</span></a>
				</li>	 -->
				<li>
					<input id="addCustomerBtn" type="button" value="添加会员" onclick="saveCustomerToSettleBx()" />
				</li>	
			</ul>
		</c:if>
		<jsp:include page="/common/paginationDialog.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
		<thead>
			<tr>
				<th width="80"><input type="checkbox" class="checkboxCtrl" group="ids" />序号</th>
				<th  export = "true" columnEnName = "eventsNo" columnChName = "场次号" >场次号</th>
				<th  export= "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th  export = "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th  export = "true" columnEnName = "sex" columnChName = "性别" >性别</th>
				<th  export = "true" columnEnName = "age" columnChName = "年龄" >年龄</th>
				<th  export = "true" columnEnName = "idno" columnChName = "身份证号" >身份证号</th>
				<th  export = "true" columnEnName = "phone" columnChName = "电话" >电话</th>
				<th  export = "true" columnEnName = "companyId" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "setmealName" columnChName = "套餐名" >套餐名</th>
				<th  export = "true" columnEnName = "salesMan" columnChName = "营销员" >营销员</th>
				<th  export = "true" columnEnName = "samplingDate" columnChName = "采样日期" >采样日期</th>
				<th  export = "true" columnEnName = "note" columnChName = "note" >备注</th>
				<th  export = "true" columnEnName = "status" columnChName = "status" >状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpCustomer" varStatus="status">
					<tr target="sid_user" rel="${erpCustomer.id }">
						<td align="left">
							<c:if test="${currentUser.accountName!='parkson'}">
								<c:if test="${erpCustomer.status=='0' }">
									<input type="checkbox" name="ids" value="${erpCustomer.id}">
								</c:if>
							</c:if>
							${ status.count }
						</td>
						<td align="center">	${erpCustomer.eventsNo}</td>
						<td align="center">	${erpCustomer.code}</td>
						<td align="center">	${erpCustomer.name}</td>
						<td align="center">	${erpCustomer.sex}</td>
						<td align="center">	${erpCustomer.age}</td>
						<td align="center">	${erpCustomer.idno}</td>
						<td align="center">	${erpCustomer.phone}</td>
						<td align="center"><hpin:id2nameDB id="${erpCustomer.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
						<td align="center">
						<%-- <hpin:id2nameDB  beanId="org.ymjy.combo.dao.ComboDao" id="${erpCustomer.setmealName}"/> --%>
						${erpCustomer.setmealName}
						</td>
						<td align="center">	${erpCustomer.salesMan}</td>
						<td align="center">	${fn:substring(erpCustomer.samplingDate,0,14)}00:00</td>						
						<td align="center">	${erpCustomer.note}</td>
						<td align="center">	
							<c:choose>
								<c:when test="${erpCustomer.status=='0'}">未结算</c:when>
								<c:when test="${erpCustomer.status=='-1'}">已禁用</c:when>
								<c:otherwise>已结算</c:otherwise>
							</c:choose>
								
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
</div> 