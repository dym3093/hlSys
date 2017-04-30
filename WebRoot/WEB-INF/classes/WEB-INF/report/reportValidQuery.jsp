<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
 function reset() {
	 $("#clearText").click(function(){
		 $("#detectionCompanyId").val("");
		 $(".textInput").val("");
		 $("#endDate").val("${endDate }");
	 }) ;
 };
 
 	function clearInput(){
		$(':input','#pagerReportFindForm',navTab.getCurrentPanel())  
		 .not(':button, :submit, :reset, :hidden')  
		 .val('');
	}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${path }/report/reportQuery!reportValidQuery.action" method="post" rel="pagerForm" id="pagerReportFindForm">
	<div class="searchBar">
		<table class="pageFormContent" style="overflow: hidden;">
			<tr>
				<td><label>条形码：</label><input type="text" name="code" class="textInput" value="${code }"/></td>
				<td><label>批次号：</label><input type="text" name="batchNum" class="textInput" value="${batchNum }"/></td>
				<td><label>套餐：</label> <input type="text" name="combo" class="textInput" value="${combo }"/></td>
			</tr>
			
			<tr>
				<td>
					<label>检测公司：</label>
					<select id="detectionCompanyId" name="company" style="width: 193px; margin-top: 6px; margin-left: 5px;">
						<option value="">全部</option>
						<option value="南方" ${company == "南方" ? "selected": "" }>南方公司</option>
						<option value="金域" ${company == "金域" ? "selected": "" }>金域公司</option>
						<option value="北方" ${company == "北方" ? "selected": "" }>北方公司</option>
					</select>
					<%-- <input type="text" name="filter_and_comboName_LIKE_S" value="${filter_and_comboName_LIKE_S }"/> --%>
				</td>
				<td>
					<label>日期截止：</label>
					<input type="text" id="endDate" name="endDate" class="date" value="${endDate }"/>	
					<a class="inputDateButton" href="javascript:void(0);">选中</a>		
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearInput()" >重置</button></div></div>
				</td>
					
			</tr>
		</table>
	</div>
	</form>
</div>

<div class="tip" style="margin-bottom:0px;">
	<span>报表</span>
</div>
<div class="pageContent">
	<div class="panelBar">
	<%-- <web:security tag="comboAdmin"> --%>
		<ul class="toolBar">
			<web:exportExcelTag 
					pagerFormId="pagerReportFindForm"
					pagerMethodName="findValidQueryByPage" 
					actionAllUrl="org.hpin.statistics.briefness.web.ReportQueryAction" 
					informationTableId="exportExcelTable"
					fileName="ReportValidityDay"></web:exportExcelTag>
		</ul>
	<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="120" id="exportExcelTable">
		<thead>
			<tr>
				<th nowrap="nowrap" width="35px">序号</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="batchNum" columnChName="批次号" >批次号</th>
				<th nowrap="nowrap" width="150px" export="true" columnEnName="eventsNum" columnChName="场次号" >场次号</th>
				<th nowrap="nowrap" width="200px" export="true" columnEnName="batchCompany" columnChName="支公司" >支公司</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="barcode" columnChName="条码" >条码</th>
				<th nowrap="nowrap" width="80px"  export="true" columnEnName="personName" columnChName="姓名" >姓名</th>
				<th nowrap="nowrap" width="180px" export="true" columnEnName="comboName" columnChName="套餐名称" >套餐名称</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="detectionCompany" columnChName="检测公司" >检测公司</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="taskSamleTime" columnChName="采样时间" >采样时间</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="sampleTime" columnChName="寄样时间" >寄样时间</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="validityDay" columnChName="时效性" >时效性</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="expectDate" columnChName="期望日期" >期望日期</th>
				<th nowrap="nowrap" width="80px"  export="true" columnEnName="delayNumDay" columnChName="延迟天数" >延迟天数</th>
				<th nowrap="nowrap" width="160px" export="true" columnEnName="note" columnChName="备注" >备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="obj" varStatus="status">
			<tr target="sid_user" >
				<td align="center">${ status.count }</td>
				<td align="center" nowrap="nowrap">${obj.batchNum }</td>
				<td align="left" nowrap="nowrap" >${obj.eventsNum }</td>
				<td align="left" width="80px">
					<c:if test="${fn:length(obj.batchCompany) > 13 }">
						<span title=""${obj.batchCompany }>${fn:substring(obj.batchCompany, 0, 13) }...</span>
					</c:if>
					<c:if test="${fn:length(obj.batchCompany) <= 13 }">
						${obj.batchCompany }	
					</c:if>
				</td>
				<td align="left" nowrap="nowrap" width="120px;">${obj.barcode }</td>
				<td align="center" nowrap="nowrap" >${obj.personName }</td>
				<td align="left" nowrap="nowrap">
					<c:if test="${fn:length(obj.comboName) > 20 }">
						<span title=""${obj.comboName }>${fn:substring(obj.comboName, 0, 13) }...</span>
					</c:if>
					<c:if test="${fn:length(obj.comboName) <= 20 }">
						${obj.comboName }	
					</c:if>
				</td>
			    <td align="center" nowrap="nowrap">${obj.detectionCompany }</td> 
			    <td align="center" nowrap="nowrap">${obj.taskSamleTime }</td> 
			    <td align="center" nowrap="nowrap">${obj.sampleTime }</td> 
			    <td align="center" nowrap="nowrap">${obj.validityDay } </td> 
			    <td align="center" nowrap="nowrap">${obj.expectDate }</td> 
			    <td align="center" nowrap="nowrap">${obj.delayNumDay }</td> 
			    <td align="left" nowrap="nowrap">${obj.note }</td> 
		</c:forEach>
		</tbody>
	</table>
</div>
