<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">
//加载时默认选中单选条件和输入框显示
$(document).ready(function(){
	var hiddenText = $("input[name='selectStatus']", navTab.getCurrentPanel()).val();
	if(hiddenText=='2'){
		$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).find("option[value='2']").attr("selected",true);
	}else if(hiddenText=='3'){
		$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).find("option[value='3']").attr("selected",true);
	}else if(hiddenText=='4'){
		$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).find("option[value='4']").attr("selected",true);
	}else if(hiddenText=='9'){
		$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).find("option[value='9']").attr("selected",true);
	}
	
	var hiddenTextTwo = $("input[name='selectGeCompanyId']", navTab.getCurrentPanel()).val();
	if(hiddenTextTwo=='402881b25373b2cd0153a223c8f00000'){
		$("select[name='filter_and_geCompanyId_EQ_S']", navTab.getCurrentPanel()).find("option[value='402881b25373b2cd0153a223c8f00000']").attr("selected",true);
	}else if(hiddenTextTwo=='402881b25373b2cd0153a223c8f00001'){
		$("select[name='filter_and_geCompanyId_EQ_S']", navTab.getCurrentPanel()).find("option[value='402881b25373b2cd0153a223c8f00001']").attr("selected",true);
	}
	
});

//保存下拉选中的value
$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).change(function(){
	var select = $("select[name='filter_and_status_EQ_S'][option:selected]", navTab.getCurrentPanel()).val();			//获取选中的下拉的值
	$("input[name='selectStatus']", navTab.getCurrentPanel()).val(select);
});

//保存下拉选中的value
$("select[name='filter_and_geCompanyId_EQ_S']", navTab.getCurrentPanel()).change(function(){
	var select = $("select[name='filter_and_geCompanyId_EQ_S'][option:selected]", navTab.getCurrentPanel()).val();	//获取选中的下拉的值
	$("input[name='selectGeCompanyId']", navTab.getCurrentPanel()).val(select);
});


//提交结算
function submitSettle() {
	var id = '';
	var ids = '';
	var idArray = [];
	var count = 0;
	var status = '';
	var s = '';
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				if(ids.length>0){
					idArray = ids.replaceAll(" ", "").split(",");
					id = idArray[0];
					s = idArray[1];
				}
				count += count + 1;
				status = $(this).parent().next().text();
			});
	if (count == 0) {
		alert('请选择你要提交的结算任务！');
		return;
	} else if (count > 1) {
		alert('只能选择一条信息提交！');
		return;
	} else if(s != '2'){
		alert('请选择符合条件的结算任务！');
		return;
	}else {
		if (confirm("确认提交结算?")) {
			/* var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("modifyEvents",
				"${path}/settlementManagement/erpSettlementTaskJY!toModifyEvents.action?id=" + id
						+ "&navTabId=" + navTabId, {
					title : "变更",
					fresh : false,
					data : {}
			}); */
			$.ajax({
				type: "post",
				cache :false,
				dataType: "json",
				url: "${path}/settlementManagement/erpSettlementTaskJY!submitSettleTask.action",
				data: {'id':id},
				success: function(data){
					if(data.result=="success"){
						alertMsg.correct("提交成功！");
						navTab.refreshCurrentTab();
						//$.pdialog.closeCurrent();
						return navTabSearch(this);
					}else{
						alertMsg.error("提交失败！");
					}	
				},
				error :function(data){
					alert("服务发生异常，请稍后再试！");
				}
			});
		};
	}
}


//异常任务
function exceptionSettleTask(){
	var ids = '';		//id状态字符串
	var id = '';		//id
	var idArray = [];
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				if(ids.length>0){
					idArray = ids.replaceAll(" ", "").split(",");
					id = idArray[0];
				}
			});
	alert(id);
	navTab.openTab("exceptionSettlement",
			"${path}/settlementManagement/erpSettlementTaskJY!exceptionSettleTask.action?id=" + id
					+ "&navTabId=" + navTabId,{
				title : "异常结算任务",
				fresh : false,
			});
}

</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/settlementManagement/erpSettlementTaskJY!listSettlementComplete.action')
	{this.action = '${path}/settlementManagement/erpSettlementTaskJY!listSettlementComplete.action' ;} ;return navTabSearch(this);" 
	action="${path}/settlementManagement/erpSettlementTaskJY!listSettlementComplete.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>任务号：</label> 
					<input type="text" name="filter_and_taskNo_LIKE_S" value="${filter_and_taskNo_LIKE_S}"/>
				</td>  
				<td>
					<input type="hidden" name="selectGeCompanyId" value="${selectGeCompanyId}"/>
					<label>基因公司：</label> 
					<%-- <input type="text" name="filter_and_geCompany_LIKE_S"  value="${filter_and_branchCompany_LIKE_S}"/> --%>
					<select id="geneCompany" name="filter_and_geCompanyId_EQ_S" rel="iselect">
						<option value=''>---请选择---</option>
						<option value='402881b25373b2cd0153a223c8f00000'>南方基因公司</option>
						<option value='402881b25373b2cd0153a223c8f00001'>北方基因公司</option>
		 			</select>
				</td>
			</tr>
			<tr>
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><div style="margin-left:60px;" class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
	<div>
		<fieldset>
			<legend>结算任务金额合计:</legend>
			<table class="num2_lab">
				<tr>
					<td><label>应结算金额合计：</label></td>
					<td class="num2_w"><input type="text" readonly="readonly" value="${totalAmountCal }" /><%--<span>${totalAmountCal }</span> --%>(元)</td>
					<td><label>实际结算金额合计：</label></td>
					<td class="num2_w"><input type="text" readonly="readonly" value="${actualTotalAmountCal }"/>(元)</td>
					<td><label>结余合计：</label></td>
					<td class="num2_w"><input type="text" readonly="readonly" style="color:${balanceCal>0?'#ff0000': balanceCal==0?'#0000ff':'#00ff00'}" value="${balanceCal }"/>(元)</td>
				</tr>
				<tr>
				<td colspan="6" style="text-align:right;"><b style="color:#ff0000;">注:【结余合计】=【实际结算金额合计】-【应结算金额合计】</b></td>
				</tr>
			</table>
		</fieldset>
	</div>
</div>
<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="230" id="exportExcelTable"> 
			<thead>
			<tr>
				<th>序号</th>
				<th width="160"  export = "true" columnEnName = "taskNo" columnChName = "任务号" >任务号</th>
				<th width="160" export= "true" columnEnName = "settlementTime" columnChName = "创建时间">创建时间</th>
				<th  export = "true" columnEnName = "geCompany" columnChName = "基因公司">基因公司</th>
				<th  export = "true" columnEnName = "setMealNum" columnChName = "" >结算数量</th>
				<th  export = "true" columnEnName = "totalPersonNum" columnChName = "">收款公司</th>
				<th  export = "true" columnEnName = "successNum" columnChName = "" >总付款金额</th>
				<th  export = "true" columnEnName = "abnormalNum" columnChName = "" >付款次数</th>
				<th  export = "true" columnEnName = "status" columnChName = "状态" >状态</th>
				<th  export = "true" columnEnName = "" columnChName = "" >操作人</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }" >
					<td align="center">
						<input type="checkbox" name="ids" value="${entity.id}">
						${status.count}
					</td> 
					<td align="center"><a title="结算明细" target="navTab" width="1000" style="color:#72ACE3;"
						href="${path}/settlementManagement/erpSettlementTaskJY!listJYSettleDetail.action?id=${entity.id}" rel="listJYSettleDetail">${entity.taskNo}</a></td>
					<td align="center">	${entity.createTime}</td>
					<td align="center">	${entity.geCompany}</td>
					<c:choose>
							<c:when test="${entity.status=='3'}">
								<td align="center">${entity.expectPayNum}</td>
							</c:when>
							<c:when test="${entity.status=='4'}">
								<td align="center">${entity.completePayNum}</td>
							</c:when>
							<c:otherwise>
								<td align="center"></td>
							</c:otherwise>
					</c:choose>
					<td align="center">	${entity.collectionCompany}</td>
					<td align="center">	${entity.accruedPayAmount}</td>
					<td align="center">	${entity.payCount}</td>
					<td align="center">
						<c:choose>
							<c:when test="${entity.status=='0'}">
								新增
							</c:when>
							<c:when test="${entity.status=='1'}">
								进行中
							</c:when>
							<c:when test="${entity.status=='2'}">
								可支付
							</c:when>
							<c:when test="${entity.status=='3'}">
								待支付
							</c:when>
                            <c:when test="${entity.status=='4'}">
								已支付
							</c:when>
							<c:when test="${entity.status=='9'}">
								部分支付
							</c:when>
					</c:choose>
					</td>
					<td align="center">	${entity.payOperator}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 

