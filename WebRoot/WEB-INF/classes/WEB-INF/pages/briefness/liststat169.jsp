<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<form id="pagerFindForm" 
		onsubmit="this.action='${path }/statistics/queryaction!queryReport.action'; return navTabSearch(this); "
 		action="${path }/statistics/queryaction!queryReport.action" method="post" rel="pagerForm">
	<input type="hidden" name="configId" value="${actionMap['configId']}">
	<div class="searchBar">
	</div>
	</form>
</div> 

<div class="tabs">
		<div style="margin-bottom: 0px;"class="tip">
			<span>报表</span>
		</div>
    <div class="tabsContent" style="background-color:#FFF">
	<div style="width:100%">
		<div id="jbsxBox" class="unitBox">
			<div class="pageContent">
				<div class="panelBar">
				<ul class="toolBar">
					<c:if test="${page.results != null && !empty page.results }">
						<!-- <li><a class="edit" href="javascript:void(0);" onclick="downLoadSumary()"><span>导出Excel</span></a></li> -->
						<web:exportExcelTag4Jdbc 
										pagerFormId="pagerFindForm" 
										pagerMethodName="queryResults" 
										actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
										informationTableId="exportTable"
										fileName="stat169"
										configId="stat169"
										></web:exportExcelTag4Jdbc>
					</c:if>
				</ul>
				<jsp:include page="/common/pagination.jsp" />
				</div>
				<table class="list" width="100%" layoutH="100" id = "exportTable"> 
				   <thead>
					<tr>
					    <!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>  -->
					    <th width="35px">序号</th>
				<th nowrap="nowrap"  export="true" columnIndex="0" columnChName="支公司" >支公司</th>
				<th nowrap="nowrap"  export="true" columnIndex="1" columnChName="项目编码" >项目编码</th>
				<th nowrap="nowrap"  export="true" columnIndex="2" columnChName="项目名称" >项目名称</th>
				<th nowrap="nowrap"  export="true" columnIndex="3" columnChName="项目负责人" >项目负责人</th>
				<th nowrap="nowrap"  export="true" columnIndex="4" columnChName="套餐" >套餐</th>
				<th nowrap="nowrap"  export="true" columnIndex="5" columnChName="价格" >价格</th>
					</tr>
				  </thead>
				  <tbody>
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
						 <td align="center">
							${status.count }
						</td> 
						<td align="center">${ members[0] }</td>
				<td align="center">${ members[1] }</td>
				<td align="center">${ members[2] }</td>
				<td align="center">${ members[3] }</td>
			    <td align="center">${ members[4] }</td> 
			    <td align="center">${ members[5] }</td> 			   
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>