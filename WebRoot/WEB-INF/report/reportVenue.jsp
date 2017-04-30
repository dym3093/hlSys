<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${path }/report/reportQuery!reportVenue.action" method="post" rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent" style="overflow: hidden;">
			
			<tr>
				<td><label>支公司：</label><input name="branchCompanyName" value="${branchCompanyName }" type="text" class="textInupt" ></td>
				<td>
					<label>会场开始日期：</label>
					<input type="text" name="startDate" value="${startDate }"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})"  readonly="readonly" />
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
					<label>会场结束日期：</label>
					<input type="text" name="endDate" value="${endDate }"   id="d7112" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" readonly="readonly" />
					<a class="inputDateButton" href="javascript:;">选择</a>	
				</td>
			</tr>
			<tr>
				<td>
					<label>所在省：</label>
					<select id="province" name="province"  rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
				  		<option value="${province}"></option>
					</select>
				</td>
				<td>
					<label>所在市：</label>
                	<select id="city" name="city" rel="iselect">
                		<option value="${city}"></option>
                	</select> 
				</td>
				<td>
					<div class="subBar">
						<ul style="float: left">
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
							<li><a id="resetDo" href="javascript:;" class="button"><span>重置</span></a></li>
						</ul>
					</div>
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
					pagerFormId="pagerFindForm"
					pagerMethodName="findVenueByPage" 
					actionAllUrl="org.hpin.statistics.briefness.web.ReportQueryAction" 
					informationTableId="exportExcelTable"
					fileName="VenueData"></web:exportExcelTag>
		</ul>
	<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="120" id="exportExcelTable">
		<thead>
				<th nowrap="nowrap" width="35px">序号</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="conferenceNo" columnChName="会议号" >会议号</th>
				<th nowrap="nowrap" width="150px" export="true" columnEnName="conferenceDate" columnChName="场次号" >会议日期</th>
				<th nowrap="nowrap" width="200px" export="true" columnEnName="provinceName" columnChName="省份" >省份</th>
				<th nowrap="nowrap" width="80px" export="true" columnEnName="cityName" columnChName="城市" >城市</th>
				<th nowrap="nowrap" width="80px"  export="true" columnEnName="branchCompanyName" columnChName="支公司" >支公司</th>
				<th nowrap="nowrap" width="180px" export="true" columnEnName="ownedCompanyName" columnChName="所属公司" >所属公司</th>
				<th nowrap="nowrap" width="120px" export="true" columnEnName="address" columnChName="地点" >地点</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="conferenceName" columnChName="会议类型" >会议类型</th>
				<th nowrap="nowrap" width="100px" export="true" columnEnName="projectTypeName" columnChName="项目类型" >项目类型</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results }" var="obj" varStatus="status">
			<tr target="sid_user" >
				<td align="center">${ status.count }</td>
				<td align="center" nowrap="nowrap"><a title="会议信息" target="navTab" href="${path}/events/erpCustomer!toConferenceNoListCustomer.action?showId=${obj.id}">${obj.conferenceNo }</a></td>
				<td align="center" nowrap="nowrap" >${obj.conferenceDate }</td>
				<td align="left" nowrap="nowrap" width="80px;">${obj.provinceName }</td>
				<td align="left" nowrap="nowrap" width="80px;" >${obj.cityName }</td>
			    <td align="left" nowrap="nowrap">${obj.branchCompanyName }</td> 
			    <td align="left" nowrap="nowrap">${obj.ownedCompanyName }</td> 
			    <td align="left" nowrap="nowrap">${obj.address }</td> 
			    <td align="center" nowrap="nowrap">${obj.conferenceName } </td> 
			    <td align="center" nowrap="nowrap">${obj.projectTypeName }</td> 
		</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	$(function() {
		$("#resetDo", navTab.getCurrentPanel()).on("click", function(){
			$("input", navTab.getCurrentPanel()).val("");
			$("select", navTab.getCurrentPanel()).val("");
		}) ;
	});
</script>