<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">

</script>

<div class="tip"><span>付款明细</span></div>
<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="table" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> -->
				<th>序号</th>
                <!--<th  export = "true" columnEnName = "id" columnChName = "id" >id</th>-->
				<th width="160"  export = "true" columnEnName = "taskNo" columnChName = "任务号" >基因公司</th>
				<th width="160" export= "true" columnEnName = "settlementTime" columnChName = "创建时间">总付款金额</th>
				<th  export = "true" columnEnName = "geCompany" columnChName = "基因公司">收款公司</th>
				<th  export = "true" columnEnName = "branchCompanyNum" columnChName = "支公司数" >付款方式</th>
				<th  export = "true" columnEnName = "setMealNum" columnChName = "套餐数量" >付款时间</th>
				<th  export = "true" columnEnName = "totalPersonNum" columnChName = "成功导入总数" >付款银行</th>
				<th  export = "true" columnEnName = "successNum" columnChName = "匹配数量" >本次付款金额</th>
				<th  export = "true" columnEnName = "abnormalNum" columnChName = "异常数量" >未付款</th>
				<th  export = "true" columnEnName = "totalAmount" columnChName = "本次可结算金额(元)" >OA编号</th>
				<th  export = "true" columnEnName = "status" columnChName = "" >入账说明</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }" >
					<td align="center">
						<input type="checkbox" name="ids" value="${entity.id}">
						${status.count}
					</td> 
					<td align="center">${entity.geCompany}</td>
					<td align="center">${entity.sumAmount}</td>
					<td align="center">${entity.collectionCompany}</td>
					<td align="center">${entity.payMode}</td>
					<td align="center">	${entity.payTime}</td>
					<td align="center">	${entity.payBank}</td>
					<td align="center">	${entity.currentAmount}</td>
					<td align="center">	${entity.noPayAmount}</td>
					<td align="center">	${entity.OANo}</td>
					<td align="center">	${entity.note}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 



