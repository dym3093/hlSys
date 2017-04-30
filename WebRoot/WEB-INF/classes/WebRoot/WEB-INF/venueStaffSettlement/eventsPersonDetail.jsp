<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript">
$(document).ready(function(){
	$("#superiorOrgId").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
});
</script>
<html>
<head>
</head>
<body>
	<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/venueStaffSettlement/erpEventsStaffCost!getPersonByEventsId.action?id=${eventsId}'){this.action = '${path}/venueStaffSettlement/erpEventsStaffCost!getPersonByEventsId.action?id=${eventsId}';} ;return navTabSearch(this);" action="${path}/venueStaffSettlement/erpEventsStaffCost!getPersonByEventsId.action?id=${eventsId}" method="post" rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>姓名：</label> 
					<input type="text" name="filter_and_staff_LIKE_S" value=""/>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
	</div>
	<div class="tip"><span>人员费用信息</span></div>
	<input type="hidden" name="navTabId" value="${ navTabId }"/>
	<div class="pageContent">
		<div class="panelBar">
			<jsp:include page="/common/pagination.jsp" />
		</div>
		<table class="list" width="100%">
			<thead>
				<tr>
				<th  export = "true" columnEnName = "" columnChName = "序号" >序号</th>
				<th  export = "true" columnEnName = "staff" columnChName = "姓名" >姓名</th>
				<th  export = "true" columnEnName = "position" columnChName = "职位" >职位</th>
				<th  export = "true" columnEnName = "trafficCost" columnChName = "交通费(元)" >交通费(元)</th>
				<th  export = "true" columnEnName = "hotelCost" columnChName = "住宿费(元)" >住宿费(元)</th>
				<th  export = "true" columnEnName = "mealsCost" columnChName = "餐费(元)" >餐费(元)</th>
				<th  export = "true" columnEnName = "courseCost" columnChName = "课酬费(元)" >课酬费(元)</th>
				<th  export = "true" columnEnName = "miscellaneousCost" columnChName = "公杂费(元)" >公杂费(元)</th>
				<th  export = "true" columnEnName = "serviceCost" columnChName = "劳务费(元)" >劳务费(元)</th>
				<th  export = "true" columnEnName = "materialCost" columnChName = "物资费(元)" >物资费(元)</th>
				<th  export = "true" columnEnName = "inspectionCost" columnChName = "检测费(元)" >检测费(元)</th>
				<th  export = "true" columnEnName = "expressCost" columnChName = "快递费(元)" >快递费(元)</th>
				<th  export = "true" columnEnName = "publishCost" columnChName = "打印费(元)" >打印费(元)</th>
				<th  export = "true" columnEnName = "samplingCost" columnChName = "采样费(元)" >采样费(元)</th>
				<th  export = "true" columnEnName = "otherCost" columnChName = "其他费用(元)" >其他费用(元)</th>
				<th  export = "true" columnEnName = "amount" columnChName = "小计(元)" >小计(元)</th>
				<th  export = "true" columnEnName = "remark" columnChName = "备注" >备注</th>
				<!-- <th export = "true" columnEnName = "">操作</th> -->
				</tr>
			</thead>
			<tbody >
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr >
  					<td align="center">${status.count}</td>
 					<td align="center">${entity.staff }</td>
					<td align="center">${entity.position }</td>
					<td align="center">${entity.trafficCost }</td>
					<td align="center">${entity.hotelCost }</td>
					<td align="center">${entity.mealsCost }</td>
					<td align="center">${entity.courseCost }</td>
					<td align="center">${entity.miscellaneousCost }</td>
					<td align="center">${entity.serviceCost }</td>
					<td align="center">${entity.materialCost }</td>
					<td align="center">${entity.inspectionCost }</td>
					<td align="center">${entity.expressCost }</td>
					<td align="center">${entity.publishCost }</td>
					<td align="center">${entity.samplingCost }</td>
					<td align="center">${entity.otherCost }</td>
					<td align="center">${entity.amount }</td>
					<td align="center">${entity.remark }</td>
					<!-- <td align="center"><a class="btnDel " href="javascript:void(0)">删除</a></td> -->
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>