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
      		<td><label>仓库名称：</label></td>
      		<td><input name="filter_and_StoregeName_like_S" value="${actionMap.filter_and_StoregeName_like_S }" class="textInput"></td>
      		<td></td>
      		<td><label>申请单号：</label></td>
      		<td><input name="filter_and_applicationNo_like_S" value="${actionMap.filter_and_applicationNo_like_S }" class="textInput"></td>
      		<td></td>
      		<td><label>产品名称：</label></td>
      		<td><input name="filter_and_productName_like_S" value="${actionMap.filter_and_productName_like_S }" class="textInput"></td>
      		<td></td>
      	</tr>
      	
      	<tr>
      		<td><label>申请人：</label></td>
      		<td><input name="filter_and_username_like_S" value="${actionMap.filter_and_username_like_S }" class="textInput"></td>
      		<td></td>
            <td><label>开始日期：</label></td>
			<td>
				<input type="text" name="filter_and_createTime_GE_T" value="${ actionMap.filter_and_createTime_GE_T }"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</td>
			<td></td>
			<td><label>结束日期：</label></td>
			<td>
				<input type="text" name="filter_and_createTime_LE_T" value="${ actionMap.filter_and_createTime_LE_T }"   id="d7112" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>	
			</td>
             <td>
		        <div style="width:100px;" class="subBar">	
		            <ul>
					    <li><div class="buttonActive"><div class="buttonContent"><button type="submit" >查找</button></div></div></li>
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
										fileName="stat175"
										configId="stat175"></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="130" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>   
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "仓库名称">仓库名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "申请单号">申请单号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "出库产品名称">出库产品名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "单价">单价</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "数量">数量</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "快递费">快递费</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "成本">成本</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "总金额">总金额</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "8" columnChName = "卡号开始">卡号开始</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "卡号结束">卡号结束</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "项目编码">项目编码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "11" columnChName = "项目负责人">项目负责人</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "12" columnChName = "项目名称">项目名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "13" columnChName = "申请人">申请人</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "14" columnChName = "出库日期">出库日期</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "15" columnChName = "供货商">供货商</th>
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
						<td align="center">  ${members[15]}</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>