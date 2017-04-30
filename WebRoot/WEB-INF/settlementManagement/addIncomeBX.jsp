<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript">
    $(function () {
        $("td").on("click", function () {
            var totalAmount = 0;
            $("table tbody tr").each(obj, function (row, col) {
                if (col==1){
                    var amount = $(this);
                    totalAmount += Number.valueOf(amount);
                }
            })
            $("#totalIncome").val(totalAmount);
        });

        //统计
//        var sumAmount = function () {
//            var totalAmount = 0;
//            $("table tbody tr").each(obj, function (row, col) {
//                if (col==1){
//                    var amount = $(this);
//                    totalAmount += Number.valueOf(amount);
//                }
//            })
//            $("#totalIncome").val(totalAmount);
//        }
    })


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
                <%--<span style="margin-left: 200px">总计：</span><span><input id="totalAmount" type="text" name="totalAmount" readonly="readonly"></span>--%>
				<thead>
				<tr>
					<%--<th type="hidden" name="incomes[#index#].id" filedStyle="width:0px"></th>--%>
					<th type="enum" name="incomes[#index#].incomeType" enumUrl="${path}/settlementManagement/erpSettlementTaskBX!listIncomeType.action"
						filedStyle="width: 120px" fieldClass="required" >费用类型</th>
					<th type="calculate" name="incomes[#index#].amount" fieldClass="number required" size="12" filedStyle="width: 120px" >金额</th>
					<th type="text" name="incomes[#index#].remark" size="300" filedStyle="width: 200px">备注</th>
					<th type="del" width="40" >操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${incomes}" var="entity" varStatus="status">
					<tr>
						<td>
							<input type="hidden" name="incomes[${status.index }].id" value="${entity.id}" style="width: 0px"/>
							<select name="incomes[${status.index }].incomeType" style="width:120px;" >
								<c:forEach items="${incomeTypeMap}" var="map">
									<option value="${map.key}" ${map.key == entity.incomeType ? 'selected' : '' }>${map.value}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<input class="textInput" type="text" name="incomes[${status.index}].amount" value="${entity.amount}" style="width: 120px;" >
						</td>
						<td>
							<input class="textInput" type="text" name="incomes[${status.index}].remark" value="${entity.remark}"  style="width: 200px;">
						</td>
						<td><a class="btnDel" title="确认删除该项收入？" href="${path}/settlementManagement/erpSettlementTaskBX!delIncome.action?
														id=${entity.id}&taskId=${taskId}&navTabId=${navTabId}" >删除</a></td>
						</span>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="text-align: right" >
			<ul>
				<li>
					<div class="buttonActive" >
						<div class="buttonContent">
							<button type="submit" >保存</button>
						</div>
						<%--<div class="buttonContent" style="margin-right: 0px">--%>
							<%--<button type="close" >关闭</button>--%>
						<%--</div>--%>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
