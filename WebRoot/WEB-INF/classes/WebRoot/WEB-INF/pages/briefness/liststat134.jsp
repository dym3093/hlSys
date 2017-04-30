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
      		<td><label>场次号：</label></td>
      		<td><input name="filter_and_eventsNo_LIKE_S" value="${actionMap.filter_and_eventsNo_LIKE_S }" class="textInput"></td>
      		<td></td>
      		<td><label>支公司：</label></td>
      		<td><input type="text" id="id" name="filter_and_branchCompanyId_EQ_S" bringbackname="customer.branchCommanyName" value="${filter_and_branchCompanyId_EQ_S}" />
					<input type="hidden" id="branchCompany" name="aaaa" bringbackname="customer.branchCommanyName" value="${aaaa} " readonly="readonly"/>
					<a class="btnLook" href="${path}/resource/customerRelationShip!findCustomerRelationShip.action" lookupGroup="customer"  target="dialog" width="800" height="480">查找带回</a></td>
      		<td></td>
      		<td><label>总公司：</label></td>
      		<td><input type="text"	id="ownedCompanyId" name="filter_and_ownedCompanyId_EQ_S" bringbackname="dept.customerNameSimple"	value="${filter_and_ownedCompanyId_EQ_S}" /> 
					<input type="hidden"	id="ownedCompany" name="bbbb" bringbackname="dept.customerNameSimple" value="${bbbb}" readonly="readonly" /> 
					<a class="btnLook" href="${ path }/resource/customerRelationShip!lookDept.action" lookupGroup="dept" target="dialog" width="800" height="480">查找带回</a></td>
      		<td></td>
      	</tr>
      	
      	<tr>
      		<td><label>级别：</label></td>
      		<td><select id="level2" name="filter_and_level2_LIKE_S" rel="iselect"  loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10103">
						<option value="${filter_and_level2_LIKE_S}"></option>
					</select></td>
      		<td></td>
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
		</tr>
		
		<tr>
				<td><label>省：</label></td> 
				<td><select id="province" name="filter_and_provice_EQ_S"
						rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
						ref="city"
						refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
							<option value="${filter_and_provice_EQ_S}"></option>
					</select>
				</td>
				<td></td>
				<td><label>市：</label></td>
				<td><select id="city"
						name="filter_and_city_EQ_S" rel="iselect">
							<option value="${filter_and_city_EQ_S}"></option>
					</select>
				</td>
				<td></td>
				<td><label>批次号：</label></td>
      			<td><input name="filter_and_batchno_EQ_S" value="${actionMap.filter_and_batchno_EQ_S }" class="textInput"></td>
      			<td></td>				
		</tr>
			
		<tr>
            <td><label>项目编码：</label></td>
      		<td><input name="filter_and_projectcode_like_S" value="${actionMap.filter_and_projectcode_like_S }" class="textInput"></td>
      		<td></td>
      		<td><label>项目名称：</label></td>
      		<td><input name=filter_and_projectname_like_S value="${actionMap.filter_and_projectname_like_S }" class="textInput"></td>
      		<td></td>
      		<td></td>     		
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
		<span>场次信息</span>
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
										fileName="stat134"
										configId="stat134"></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="170" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>   
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "场次号">场次号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "批次号">批次号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "场次日期">场次日期</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "项目编码">项目编码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "项目名称">项目名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "项目负责人">项目负责人</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "支公司">支公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "总公司">总公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "8" columnChName = "入门礼套餐">入门礼套餐</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "级别">级别</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "预计人数">预计人数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "11" columnChName = "已录入人数">已录入人数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "12" columnChName = "未出报告数">未出报告数</th>					    
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
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>