<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>

<div class="pageHeader">
	<form id="pagerFindForm"></form>
</div>
	<div class="tabs">
		<div style="margin-bottom:0" class="tip">
			<span>场次信息报表</span>
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
										fileName="stat104"
										configId="stat104"></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="190" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>  
						<th nowrap="nowrap" export = "true" columnIndex = "0" columnChName="日期批次">日期</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName ="场次数量">场次数量</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName ="客户数量">客户数量</th>
					    
					</tr>
				  </thead>
				  <tbody>
					
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
						<td align="center">
							${status.count}
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
					  </tr>
					</c:forEach>
					</tbody>
				</table>
			  </div>
		</div>
	</div>
  </div>
</div>