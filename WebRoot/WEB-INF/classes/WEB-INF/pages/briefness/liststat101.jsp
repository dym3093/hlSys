<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>


<div class="pageHeader">
	<form id="pagerFindForm"  rel="pagerForm">
	</form>
  </div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a><span>报表</span></a></li>
				</ul>
			</div>
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
										fileName="stat101"
										configId="stat101"
										></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="190" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>  
					    
					    
						 <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "保险公司">保险公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "已发数量">已发数量</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "收到数量">收到数量</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "已使用数量">已使用数量</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "使用率">使用率</th>
					</tr>
				  </thead>
				  <tbody>
					
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
						<td align="center">
							${status.count }
						</td>
						<td align="center">
	            ${members[0]}
						</td>
							<td align="center">
	            ${members[1]}
						</td>
							<td align="center">
	            ${members[2]}
						</td>
						<td align="center">
	            ${members[3]}
						</td>
						<td align="center">
	            ${members[4]}
						</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>