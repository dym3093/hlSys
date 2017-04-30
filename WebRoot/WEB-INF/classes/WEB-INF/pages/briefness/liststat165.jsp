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
	<form onsubmit="if(this.action != '${path }/statistics/queryaction!queryReport.action'){this.action = '${path }/statistics/queryaction!queryReport.action' ;};return navTabSearch(this); " action="${path }/statistics/queryaction!queryReport.action" method="post" rel="pagerForm" id="pagerReportFindForm">
	<input type="hidden" name="configId" value="${actionMap['configId']}">
	<div class="searchBar">
		<table class="pageFormContent" style="overflow: hidden;">
			<tr>
				<td><label>城市：</label>
					<select id="city" name="filter_and_city_EQ_S"
						rel="iselect" loadUrl="${path}/statistics/queryaction!treeCity.action">
							<option value="${actionMap['filter_and_city_EQ_S']}"></option>
					</select>
			  	</td>
				<td><label>场次开始日期：</label><input type="text" name="filter_and_createTime_GE_T"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" value="${actionMap['filter_and_createTime_GE_T']}" /><a class="inputDateButton" href="javascript:;">选择</a></td>
				<td><label>场次结束日期：</label><input type="text" name="filter_and_createTime_LE_T"  id="d7112" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" class="required"  readonly="readonly" value="${actionMap['filter_and_createTime_LE_T']}" /><a class="inputDateButton" href="javascript:;">选择</a></td>
			</tr>
			<tr>
				<td><label>支公司：</label><input type="text" id="id" name="filter_and_branchCommany_EQ_S" bringbackname="customer.branchCommanyName" value="${actionMap['filter_and_branchCommany_EQ_S']}" />
					<input type="hidden" id="branchCompany" name="aaaa" bringbackname="customer.branchCommanyName" value="${actionMap['filter_and_branchCommany_EQ_S']} " readonly="readonly"/>
					<a class="btnLook" href="${path}/resource/customerRelationShip!findCustomerRelationShip.action" lookupGroup="customer"  target="dialog" width="800" height="480">查找带回</a>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearInput()" >重置</button></div></div>
				</td>
				<td>&nbsp;</td>
			</tr>
	</div>
	</form>
</div>

<div class="tip" style="margin-bottom:0px; width: 99%">
	<span>报表</span>
</div>
<div class="pageContent">
	<div class="panelBar">
	<%-- <web:security tag="comboAdmin"> --%>
		<ul class="toolBar">
			<web:exportExcelTag4Jdbc 
				pagerFormId="pagerFindForm" 
				pagerMethodName="queryResults" 
				actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
				informationTableId="exportTable"
				fileName="stat165"
				configId="stat165"
				></web:exportExcelTag4Jdbc>
		</ul>
	<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="120" id="exportExcelTable">
		<thead>
			<tr>
				<th nowrap="nowrap" width="35px">序号</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="0" columnChName="场次号" >场次号</th>
				<th nowrap="nowrap" width="150px" export="true" columnEnName="1" columnChName="批次号" >批次号</th>
				<th nowrap="nowrap" width="200px" export="true" columnEnName="2" columnChName="场次日期" >场次日期</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="3" columnChName="省份" >省份</th>
				<th nowrap="nowrap" width="80px"  export="true" columnEnName="4" columnChName="城市" >城市</th>
				<th nowrap="nowrap" width="180px" export="true" columnEnName="5" columnChName="支公司名称" >支公司名称</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="6" columnChName="总公司名称" >总公司名称</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="7" columnChName="总人数" >总人数</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="8" columnChName="已出报告人数" >已出报告人数</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="members" varStatus="status">
			<tr target="sid_user" >
				<td align="center">${ status.count }</td>
				<td align="center" nowrap="nowrap">
					<a href="${path }/statistics/queryaction!queryReport.action?filter_and_eventsNo_EQ_S=${members[0] }&configId=stat165_s" target="navTab">${members[0] }</a>
				</td>
				<td align="center" nowrap="nowrap" >${ members[1] }</td>
				<td align="center" nowrap="nowrap" >${ members[2] }</td>
				<td align="center" nowrap="nowrap" >${ members[3] }</td>
			    <td align="center" nowrap="nowrap">${ members[4] }</td> 
			    <td align="center" nowrap="nowrap">${ members[5] }</td> 
			    <td align="center" nowrap="nowrap">${ members[6] }</td> 
			    <td align="center" nowrap="nowrap">${ members[7] } </td> 
			    <td align="center" nowrap="nowrap">${ members[8] }</td> 
		</c:forEach>
		</tbody>
	</table>
</div>
