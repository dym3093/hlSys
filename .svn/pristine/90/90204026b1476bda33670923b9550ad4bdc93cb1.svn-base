<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

function toCustomerList(val1, val2, val3, val4){
	
	var obj = {
			filter_and_provice_EQ_S: val1,
			filter_and_city_EQ_S: val2,
			field: val3,
			configId: val4
			};
	
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("liststat107_1_s", "${path }/statistics/queryaction!performSearchDo.action", { title:"场次报表明细", fresh:true, data:obj });

}

</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path }/statistics/queryaction!queryReport.action')
	{this.action = '${path }/statistics/queryaction!queryReport.action' ;};return navTabSearch(this); " 
	action="${path }/statistics/queryaction!queryReport.action" method="post" rel="pagerForm">
		<div class="searchBar">
		  <table class="pageFormContent">
	      	<tr>
     			<td>
     				<input type="hidden" name="configId" value="${actionMap['configId'] }">
					<label>省：</label>
				</td>
				<td class="num1_td">
					<select id="province" name="filter_and_province_EQ_S"
					rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
					ref="city"
					refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
						<option value="${ actionMap['filter_and_province_EQ_S']}"></option>
					</select>
				</td>
				<td>
					<label>市：</label> 
				</td>
				<td  class="num1_td">
					<select id="city" name="filter_and_city_EQ_S" rel="iselect">
						<option value="${ actionMap['filter_and_city_EQ_S']}"></option>
					</select>
				</td>
	            <td>
			        <div style="width:100px;" class="subBar">
			            <ul>
						  <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">查找</button></div></div></li>
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
										fileName="stat107"
										configId="stat107"
										></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="190" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>  				    
						<th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "省">省</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "市">市</th>
<!-- 					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "支公司">支公司</th>
 -->					<th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "场次数量（场）">场次数量（场）</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "报告总数（份）">报告总数（份）</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "已出报告数（份）">已出报告数（份）</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "未出报告数（份）">未出报告数（份）</th>
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
							${members[2]}
						</td>
 						<td align="center">${members[4]}</td>
						<td align="center">
							<c:choose>
								<c:when test="${!empty members[5] && members[5]!=0}">
									<a href="#" onclick="toCustomerList(${members[1]},${members[3]}, 'f107_1', '${actionMap['configId']}')">
										${members[5]}
									</a>
								</c:when>
								<c:otherwise>
									${members[5]}
								</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${!empty members[6] && members[6]!=0}">
									<a href="#" onclick="toCustomerList(${members[1]},${members[3]}, 'f107_2', '${actionMap['configId']}')">
										${members[6]}
									</a>
								</c:when>
								<c:otherwise>
									${members[6]}
								</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${!empty members[7] && members[7]!=0}">
									<a href="#" onclick="toCustomerList(${members[1]},${members[3]}, 'f107_3', '${actionMap['configId']}')">
										${members[7]}
									</a>
								</c:when>
								<c:otherwise>
									${members[7]}
								</c:otherwise>
							</c:choose>
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