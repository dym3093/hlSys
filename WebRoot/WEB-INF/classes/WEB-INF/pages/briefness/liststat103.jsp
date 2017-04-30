<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<script type="text/javascript">
function submitForm(){
	var flag = false;
	$(".required",navTab.getCurrentPanel()).each(function(){
		if($(this).val()==""){
			$(this).focus();
			flag = true;
		}
	});
	if(flag){
		alert("您有必选项没有填写请确认");
		return false;
	}
	$("#pagerFindForm",navTab.getCurrentPanel()).submit();
}
</script>
	<div class="tabs">
		<div style="margin:0px" class="tip">
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
										fileName="stat103"
										configId="stat103"
										>
						</web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="10" id = "exportTable"> 
				   <thead>
					<tr>
					    <th export = "true" columnIndex = "0" columnChName = "日期批次">日期批次</th>
					    <th export = "true" columnIndex = "1" columnChName = "XLS名单数">XLS名单数</th>
					    <th export = "true" columnIndex = "2" columnChName = "PDF文件数">PDF文件数</th>
					    <th export = "true" columnIndex = "3" columnChName = "XLS和PDF正确匹配数">XLS和PDF正确匹配数</th>
					</tr>
				  </thead>
				  <tbody>
					
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
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
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>