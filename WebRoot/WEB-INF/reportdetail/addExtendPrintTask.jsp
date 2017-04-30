<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$("#customerRelation").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
});

</script>



<div class="pageHeader" style="background:white">
	<form rel = "pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${path}/reportdetail/erpPrintTask!toAddPrintTask.action" method="post">
	<div class="searchBar">
			<table class="pageFormContent">
			<tr>
				
				<td><label>任务编号：</label> 
					<input type="text" name="printTaskNo" value="${printTaskNo}" class="required"/>
					<input type="hidden" name="threeNum" value="${threeNum}"/>
					<input type="hidden" name="printBatchId" value="${printBatchId}"/>
				</td>
				<td><label>任务名称：</label> 
					<input type="text" name="taskName" value="${taskName}" readonly="readonly">
				</td>
				
			</tr>
			<tr>
			<td>
					<label>批次数量：</label> 
					<input type="text" name="batchNum" value="${batchNum}" readonly="readonly"/>
				</td>
				<td align="center">
					<label>创建时间：</label> 
					<input type="text" name="createTime" value="${createTime}" readonly="readonly"/>
				</td>
         		
			</tr>
			<tr>
				<td></td><td></td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit" onclick="isClose()">保存</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button class="close" onclick="isClose()">取消</button></div></div>
				</td>
			</tr>
		</table>
		
	</div>
	</form>
</div>

<table class="list" width="100%" layoutH="470" id="exportExcelTable"> 
	<thead>
		<tr>
			<!-- <th width="35">序号</th> -->
			<th  export = "true" columnEnName = "printBatchNo" columnChName = "批次号" >批次号</th>
			<th  export= "true" columnEnName = "province" columnChName = "省" id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
			<th  export= "true" columnEnName = "city" columnChName = "市" id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
			<th  export = "true" columnEnName = "branchCompany" id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "支公司" >支公司</th>
			<th  export = "true" columnEnName = "events" columnChName = "场次" >场次</th>
			<th  export = "true" columnEnName = "combo" columnChName = "套餐" >套餐</th>
			<th  export = "true" columnEnName = "count" columnChName = "数量" >数量</th>
		</tr>
		
	</thead>
	<tbody>
		<c:forEach items="${page.results}" var="erpPrintBatch" varStatus="status">
			<%-- <tr target="sid_user" rel="${erpPrintBatch.id }">
				<td align="center">
					<c:if test="${currentUser.accountName!='parkson'}">
						<input type="checkbox" name="ids" value="${erpPrintBatch.id}">
					</c:if>
					${ status.count }
				</td> --%> 
				
					<%-- <a title="批次信息" target="navTab" width="1000"  href="${path}/events/erpCustomer!toConferenceNoListCustomer.action?showId=${erpPrintBatch.id}">${erpPrintBatch.printBatchNo}</a> --%>
				<%-- <a title="批次信息" target="navTab" width="1000"  href="${path}/report/reportFileTask!toTaskDetailByBatchno.action?id=${erpPrintBatch.id}">${erpPrintBatch.printBatchNo}</a> --%>
				<td align="center">	${erpPrintBatch.printBatchNo}</td>
				
				<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.province }"/></td>
				<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.city }"/></td>
				<td align="center"><hpin:id2nameDB id="${erpPrintBatch.branchCompany}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>

				<td align="center">	${erpPrintBatch.events}</td>
				<td align="center">	${erpPrintBatch.combo}</td>
				<td align="center">	${erpPrintBatch.count}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>



