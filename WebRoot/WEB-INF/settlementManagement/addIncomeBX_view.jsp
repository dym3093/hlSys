<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript">

</script>

<div class="pageContent">
	<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);"
		  action="${path}/settlementManagement/erpSettlementTaskBX!saveIncome.action" method="post" >
		<div class="tabsContent" style="height: 450px;">
			<table class="list nowrap itemDetail" addButton="添加" width="100%">
				<div>
					<input type="hidden" name="taskId" value="${taskId}" />
					<input type="hidden" name="navTabId" value="${navTabId}" />
				</div>
				<thead>
				<tr>
					<th type='enum' name='incomes[#index#].incomeType' enumUrl="${path}/settlementManagement/erpSettlementTaskBX!listIncomeType.action"
						filedStyle='width: 120px' fieldClass="required">费用类型</th>
					<th type='calculate' name='incomes[#index#].amount' fieldClass="number required" size="12" filedStyle='width: 120px'>金额</th>
					<th type='text' name='incomes[#index#].remark' size="300" filedStyle='width: 200px'>备注</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${incomes}" var="entity" varStatus="status">
					<tr>
						<td>
							<input type="hidden" name="incomes[${status.index }].id" vaue="${entity.id }"/>
							<select name="incomes[${status.index }].incomeType" style="width:120px;">
								<c:forEach items="${incomeTypeMap}" var="map">
									<option value="${map.key}" ${map.key == entity.incomeType ? 'selected' : '' }>${map.value}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<input class="textInput" type="text" name="incomes[${status.index}].amount" value="${entity.amount}" style="width: 120px;">
						</td>
						<td>
							<input class="textInput" type="text" name="incomes[${status.index}].remark" value="${entity.remark}" style="width: 200px;">
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
</div>
