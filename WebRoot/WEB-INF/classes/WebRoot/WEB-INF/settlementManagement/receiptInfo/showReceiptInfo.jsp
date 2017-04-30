<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">

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
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/settlementManagement/erpReceiptInfo!showReceiptInfo.action')
	{this.action = '${path}/settlementManagement/erpReceiptInfo!showReceiptInfo.action' ;} ;return navTabSearch(this);" 
	action="${path}/settlementManagement/erpReceiptInfo!showReceiptInfo.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>收款账户：</label> 
					<input type="text" name="filter_and_receivablesAccount_EQ_S" value="${filter_and_receivablesAccount_EQ_S}"/>
				</td>  
				<td>
					<label>付款账户：</label> 
					<input type="text" name="filter_and_paymentAccount_EQ_S" value="${filter_and_paymentAccount_EQ_S}"/>
					
				</td>
				<%-- <td>
				<label>收付款金额：</label> 
					<input type="text" name="filter_and_paymentCost_EQ_I" value="${filter_and_paymentCost_EQ_I}"/>
				</td> --%>
			</tr>
			<tr>
				<td><label>付款日期：</label> 
					<input type="text" name="filter_and_paymentDate_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_paymentDate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>至：</label> 
					<input type="text" name="filter_and_paymentDate_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_paymentDate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><div style="margin-left:60px;" class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> -->
				<th>序号</th>
				<th width="160"  export = "true" columnEnName = "ownCashBankAccountNamePOSNo" columnChName = "我方现金/银行账户名称/POS机号" >我方现金/银行账户名称/POS机号</th>
				<th width="160" export= "true" columnEnName = "ownCashBankAccounts" columnChName = "我方现金/银行账户账号">我方现金/银行账户账号</th>
				<th  export = "true" columnEnName = "paymentDate" columnChName = "收付款日期">收付款日期</th>
				<th  export = "true" columnEnName = "actualBankAccountArrival" columnChName = "实际银行到账日期" >实际银行到账日期</th>
				<th  export = "true" columnEnName = "paymentCost" columnChName = "收付款金额" >收付款金额</th>
				<th  export = "true" columnEnName = "POSFee" columnChName = "POS机刷卡手续费" >POS机刷卡手续费</th>
				<th  export = "true" columnEnName = "actualBankAccountCost" columnChName = "实际银行到账金额" >实际银行到账金额</th>
				<th  export = "true" columnEnName = "payerCostPayerName" columnChName = "付款方现金/付款方名称" >付款方现金/付款方名称</th>
				<th  export = "true" columnEnName = "payerCostBankAccount" columnChName = "付款方现金/银行账户账号" >付款方现金/银行账户账号</th>
				<th  export = "true" columnEnName = "payerBankAccountName" columnChName = "付款方银行账户名称" >付款方银行账户名称</th>
				<th  export = "true" columnEnName = "BankSummary" columnChName = "银行摘要" >银行摘要</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }" >
					<td align="center">
						<%-- <input type="checkbox" name="ids" value="${entity.id},${entity.status}"> --%>
						${status.count}
					</td> 
					<td align="center">	${entity.ownCashBankAccountNamePOSNo}</td>
					<td align="center">	${entity.ownCashBankAccounts}</td>
					<td align="center">	${entity.paymentDate}</td>
					<td align="center">	${entity.actualBankAccountArrival}</td>
					<td align="center">	${entity.paymentCost}</td>
					<td align="center">	${entity.POSFee}</td>
					<td align="center">	${entity.actualBankAccountCost}</td>
					<td align="center">	${entity.payerCostPayerName}</td>
					<td align="center">	${entity.payerCostBankAccount}</td>
					<td align="center">	${entity.payerBankAccountName}</td>
					<td align="center">	${entity.bankSummary}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 



