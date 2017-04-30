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
	
	}	
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
 	
 	function downLoadSettleTask(id){
 		var flag = $("#flag").val();
 		var status = "";
 		if(flag=='noHavePdf'){
 			status="1";//未出报告
 		}
 		if(flag=='haveSettlement'){
 			status="2";
 		}
 		if(flag=='errInfo'){
 			status="3";
 		}
 		
 		$.ajax({
 			type: "post",
 			cache: false,
 			data:{"id":id,"status":status,"flag":flag},
 			url: "${path}/settlementManagement/erpSettlementTaskJY!createExSeFilePathDetail.action",
 			success: function(data){
 				var obj=eval("("+data+")");
 				window.open(obj.path);
 			},
 			error:function(){
 				alert("下载失败，请检查服务器！");
 				return;
 			}
 		});
 	}
	
</script>
<div class="tip"><span>${erpSettlementTaskJY.taskNo}任务详情</span></div>
<div class="pageHeader handle">
	<div class="searchBar" style="background:white">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>异常任务号：</label> 
					<span>${exceSettlementTask.exceptionTaskNo}</span>
				</td>
				<td>
					<label>创建结算日期：</label> 
					<span>${exceSettlementTask.createTime}</span>
				</td>
			</tr>
			<tr>
				<td>
					<label>匹配总数：</label> 
					<span>${exceSettlementTask.successNum}</span>
				</td>
				
				<c:if test="${flag=='noHavePdf'}">
					<td> 
					<label>未出报告数量：</label> 
					<span>${exceSettlementTask.noHavePdfNum}</span>
					</td>
				</c:if>
				<c:if test="${flag=='haveSettlement'}">
					<td> 
					<label>已结算数量：</label> 
					<span>${exceSettlementTask.haveSettlementNum}</span>
					</td>
				</c:if>
				<c:if test="${flag=='errInfo'}">
					<td> 
					<label>基础信息有误数量：</label> 
					<span>${exceSettlementTask.errInfoNum}</span>
					</td>
				</c:if>
				<c:if test="${flag=='abnormal'}">
					<td> 
					<label>异常总数量：</label> 
					<span>${exceSettlementTask.abnormalNum}</span>
					</td>
				</c:if>
				
			</tr>
		</table>
	</div>
</div>
<div class="tip" style="margin-bottom:0px !important"><span>客户信息结算清单</span></div>
<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
				<!-- <li><a class="edit" onclick="confirmSettle()"><span>查看结算任务明细</span></a></li> -->
				<li><a class="icon" href="javascript:;" onclick="downLoadSettleTask('${exceSettlementTask.id}')">
					<c:if test="${flag=='noHavePdf'}">
						<span>导出未出报告数据</span>
					</c:if>
					<c:if test="${flag=='haveSettlement'}">
						<span>导出已结算数据</span>
					</c:if>
					<c:if test="${flag=='errInfo'}">
						<span>导出信息有误数据</span>
					</c:if>
				</a></li>
			</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
		<div id="settlement">
		<input type="hidden" id="flag" name="flag" value="${flag}"/>
		<table id="settlementTable1" class="list" width="100%" layoutH="170" style="table-layout:fixed;">
			<thead>
				<tr>
					<th>序号</th>
					<th>条形码</th>
					<th>姓名</th>
					<th>性别</th>
					<th>年龄</th>
                    <th>套餐名称</th>
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
						<td align="center" class="tdHidden" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" title="${erpCustomer.branch_company}">${erpCustomer.branch_company}</td>
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
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	    </div>
	</div> 