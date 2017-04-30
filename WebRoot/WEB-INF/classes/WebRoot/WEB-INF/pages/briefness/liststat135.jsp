<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>

<div class="pageHeader">
	<form
		id="pagerFindForm" 
		action="${path }/statistics/queryaction!queryReport.action" 
		method="post" 
		rel="pagerForm" 
		onsubmit="this.action='${path }/statistics/queryaction!queryReport.action'; return navTabSearch(this); ">
        <input type="hidden" name="configId" value="${actionMap.configId }" />
	
	<div class="searchBar">
	  <table class="pageFormContent num1_tab">
      	
      	<tr>
            <td><label>开始日期：</label></td>
			<td>
				<input type="text" name="filter_and_eventDate_GE_T" value="${ actionMap.filter_and_eventDate_GE_T }"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</td>
			<td></td>
			<td><label>结束日期：</label></td>
			<td>
				<input type="text" name="filter_and_eventDate_LE_T" value="${ actionMap.filter_and_eventDate_LE_T }"   id="d7112" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>	
			</td>
             <td>
		        <div style="width:100px;" class="subBar">	
		            <ul>
					    <li><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div></li>
					    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div></li>
					</ul>
				</div>
		  	</td>
		</tr>
	  
	  </table>
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
					<jsp:include page="/common/pagination.jsp" />
					 <ul class="toolBar">
					<c:if test="${page.results != null && !empty page.results }">
						<web:exportExcelTag4Jdbc 
										pagerFormId="pagerFindForm" 
										pagerMethodName="queryResults" 
										actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
										informationTableId="exportTable"
										fileName="stat135"
										configId="stat135"></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="130" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>   
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "批次号">批次号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "场次号">场次号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "场次日期">场次日期</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "支公司">支公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "所属公司">所属公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "已录人数">已录人数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "已寄送人数">已寄送人数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "快递单号">快递单号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "8" columnChName = "快递公司">快递公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "快递费用">快递费用</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "均摊价格">均摊价格</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "11" columnChName = "寄送类别">寄送类别</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "12" columnChName = "收发件日期">收发件日期</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "13" columnChName = "包裹内容">包裹内容</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "14" columnChName = "发票有无">发票有无</th>
					</tr>
				  </thead>
				  <tbody>
					
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
						 <td align="center">
							${status.count }
						</td> 
						<td align="center">  ${members[0]}</td>
						<td align="center">  ${members[1]}</td>
						<td align="center">  ${members[2]}</td>
						<td align="center">  ${members[3]}</td>
						<td align="center">  ${members[4]}</td>
						<td align="center">  ${members[5]}</td>
						<td align="center">  ${members[6]}</td>
						<td align="center">  ${members[7]}</td>
						<td align="center">  ${members[8]}</td>
						<td align="center">  ${members[9]}</td>
						<td align="center">  ${members[10]}</td>
						<td align="center">  ${members[11]}</td>
						<td align="center">  ${members[12]}</td>
						<td align="center">  ${members[13]}</td>
						<td align="center">  ${members[14]}</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>
