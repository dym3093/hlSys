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
	<form id="pagerFindForm" onsubmit="return navTabSearch(this);" action="${path }/statistics/queryaction!queryReport.action" method="post" rel="pagerForm">
	<input type="hidden" name="configId" value="${actionMap['configId']}">
	<div class="searchBar">
		<input type="hidden" name="filter_and_eventsNo_EQ_S" value="${filter_and_eventsNo_EQ_S }">
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
										fileName="stat167_s"
										configId="stat167_s"
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
				<th nowrap="nowrap" width="120px" export="true" columnIndex="0" columnChName="采样日期" >采样日期</th>
				<th nowrap="nowrap" width="150px" export="true" columnIndex="1" columnChName="客户检测码" >客户检测码</th>
				<th nowrap="nowrap" width="200px" export="true" columnIndex="2" columnChName="姓名" >姓名</th>
				<th nowrap="nowrap" width="200px" export="true" columnIndex="3" columnChName="性别" >性别</th>
				<th nowrap="nowrap" width="120px" export="true" columnIndex="4" columnChName="年龄" >年龄</th>
				<th nowrap="nowrap" width="80px"  export="true" columnIndex="5" columnChName="身份证" >身份证</th>
				<th nowrap="nowrap" width="180px" export="true" columnIndex="6" columnChName="电话" >电话</th>
				<th nowrap="nowrap" width="120px" export="true" columnIndex="7" columnChName="二维码对应支公司" >二维码对应支公司</th>
				<th nowrap="nowrap" width="100px" export="true" columnIndex="8" columnChName="设备所在支公司" >设备所在支公司</th>
				<th nowrap="nowrap" width="100px" export="true" columnIndex="9" columnChName="套餐名称" >套餐名称</th>
				<th nowrap="nowrap" width="100px" export="true" columnIndex="10" columnChName="营销员" >营销员</th>
				<th nowrap="nowrap" width="100px" export="true" columnIndex="11" columnChName="营销员工号" >营销员工号</th>
				<th nowrap="nowrap" width="100px" export="true" columnIndex="12" columnChName="身高" >身高</th>
				<th nowrap="nowrap" width="100px" export="true" columnIndex="13" columnChName="体重" >体重</th>
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
			    <td align="center">${ members[6] }</td> 
			    <td align="center">${ members[7] } </td> 
			    <td align="center">${ members[8] }</td> 
			    <td align="center">${ members[9] }</td> 
			    <td align="center">${ members[10] }</td> 
			    <td align="center">${ members[11] }</td> 
			    <td align="center">${ members[12] }</td> 
			    <td align="center">${ members[13] }</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>