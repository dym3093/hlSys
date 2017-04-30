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
	
	tdHidden{
		white-space:nowrap;
	}
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
					<label>支公司数量：</label> 
					<span>${erpSettlementTaskJY.branchCompanyNum}</span>
				</td>
			</tr>
			<tr>
				<%-- <td>
					<label>成功导入总数：</label> 
					<span>${erpSettlementTaskJY.totalPersonNum}</span>
				</td> --%>
				<td>
					<label>匹配总数：</label> 
					<span>${erpSettlementTaskJY.successNum}</span>
				</td>
				<td> 
					<label>套餐数量：</label> 
					<span>${erpSettlementTaskJY.setMealNum}</span>
				</td>
				<td>
					<label>本次可结算金额：</label> 
					<span>${erpSettlementTaskJY.totalAmount}</span>
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="tip" style="margin-bottom:0px !important"><span>客户信息结算清单</span></div>
<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
				<!-- <li><a class="edit" onclick="confirmSettle()"><span>查看结算任务明细</span></a></li> -->
				<%-- <li><a class="icon" href="${path}/settlementManagement/erpSettlementTaskJY!exceptionSettleTask.action"  target="navTab" rel="new_js_004"><span>查看异常数据</span></a></li> --%>
			</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
		<div id="settlement">
		<table id="settlementTable1" class="list" width="100%" layoutH="170" style="table-layout:fixed;">
			<thead>
				<tr>
					<th>序号</th>
					<th>条形码</th>
					<th>姓名</th>
					<th>性别</th>
					<th>年龄</th>
                    <th>远盟套餐名称</th>
                    <th>检测机构套餐名称</th>
					<th>采样时间</th>
					<th>PDF报告状态</th>
					<th>支公司</th>
					<th>结算状态</th>
				</tr>
			</thead>
			<tbody id="tableTbody1">
				<c:forEach items="${page.results}" var="erpCustomer" varStatus="status">
					<tr>
						<td align="center">${status.count}</td>
						<td align="center">${erpCustomer.code}</td>
						<td align="center">${erpCustomer.name}</td>
						<td align="center">${erpCustomer.sex}</td>
						<td align="center">${erpCustomer.age}</td>
						<%-- <td align="center"><a href='javascript:alert("${erpCustomer.setmeal_name}");'>查看内容</a></td> --%>
						<td align="center">${erpCustomer.setmeal_name}</td>
						<td align="center" width="300" class="tdHidden" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" title="${erpCustomer.items}">${erpCustomer.items}</td>
						<c:if test="${erpCustomer.censorship_time!=''}">
							<td align="center">${fn:substring(erpCustomer.censorship_time,0,10)}</td>
						</c:if>
						<c:if test="${erpCustomer.censorship_time==''}">
							<td align="center">${erpCustomer.censorship_time}</td>
						</c:if>
						<td align="center">
							<c:choose>
								<c:when test="${erpCustomer.pdf_Status=='0'}">
									已出
								</c:when>
								<c:when test="${erpCustomer.pdf_Status=='1'}">
									未出
								</c:when>
							</c:choose>
						</td>
						<td align="center" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" title="${erpCustomer.branch_company}">${erpCustomer.branch_company}</td>
						<td align="center">
							<c:choose>
								<c:when test="${erpCustomer.status=='0'}">
									可结算
								</c:when>
								<c:when test="${erpCustomer.status=='1'}">
									待结算
								</c:when>
								<c:when test="${erpCustomer.status=='2'}">
									已结算
								</c:when>
								<c:when test="${erpCustomer.status=='6'}">
									<span style="color:red;">套餐不匹配</span>
								</c:when>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	    </div>
	</div> 