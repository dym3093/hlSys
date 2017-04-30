<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
JSONArray seCustomer =(JSONArray) request.getAttribute("seCustomer");
%>

<style>
	#settlementPrice {
		display: none;
	}
	.handle span{line-height:30px;text-decoration:underline;}
	.handle label{width:120px;}
	
</style>

<script type="text/javascript" language="javascript">

 	$(function() {
		$("#settlementButton").click(function() {
			$("#settlement").css("display", "none");
			$("#settlementPrice").css("display", "block");
		});
	
		$("#settlementPriceButton").click(function() {
			$("#settlement").css("display", "block");
			$("#settlementPrice").css("display", "none");
		});
	}); 
	
</script>
<div class="tip"><span>${erpSettlementTaskJY.taskNo}任务详情</span></div>
<div class="pageHeader handle">
	<div class="searchBar" style="background:white">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>任务号：</label> 
					<span>${erpSettlementTaskJY.taskNo}</span>
				</td>
				<td>
					<label>创建结算日期：</label> 
					<span>${erpSettlementTaskJY.createTime}</span>
				</td>
				<td>
					<label>基因公司：</label> 
					<span>${erpSettlementTaskJY.geCompany}</span>
				</td>
				<td>
					<label>本次可结算金额：</label> 
					<span>${erpSettlementTaskJY.totalAmount}</span>
				</td>
			</tr>
			<tr>
				<td>
					<label>成功导入总数：</label> 
					<span>${erpSettlementTaskJY.totalPersonNum}</span>
				</td>
				<td>
					<label>匹配总数：</label> 
					<span>${erpSettlementTaskJY.successNum}</span>
				</td>
                <td> 
					<label>异常数量：</label> 
					<span>${erpSettlementTaskJY.abnormalNum}</span>
				</td>
				<td> 
					<label>套餐数量：</label> 
					<span>${erpSettlementTaskJY.setMealNum}</span>
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="pageContent">
	<div class="panelBar" style="margin:0px auto;">
			<ul class="toolBar">
				<!-- <li><a class="edit" onclick="confirmSettle()"><span>查看结算任务明细</span></a></li> -->
				<li><a class="edit" href="${path}/settlementManagement/erpSettlementTaskJY!getSettlementByIdDetail.action?id=${erpSettlementTaskJY.id}"  target="navTab" rel="new_js_003"><span>查看结算任务明细</span></a></li>
				<li><a class="icon" href="${path}/settlementManagement/erpSettlementTaskJY!exceptionSettleTaskById.action?taskNo=${erpSettlementTaskJY.taskNo}"  target="navTab" rel="new_js_004"><span>查看该任务异常数据</span></a></li>
			</ul>
	</div>
	</div> 