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

//不要清空隐藏域
var clearSrchForm = function(){
	$("[name='filter_and_name_LIKE_S']").val("");
	$("[name='filter_and_code_LIKE_S']").val("");
};
</script>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path }/statistics/queryaction!performSearchDo.action')
		{this.action = '${path }/statistics/queryaction!performSearchDo.action' ;} ;return navTabSearch(this);" 
		action="${path }/statistics/queryaction!performSearchDo.action" method="post" rel="pagerForm">
	<div class="searchBar">
	  <table class="pageFormContent">
    		<tr>
              <td>
            	<input type="hidden" name="configId" value="${configId}">
            	<input type="hidden" name="field" value="${field}">
            	<input type="hidden" name="filter_and_createTime_GE_T" value="${filter_and_createTime_GE_T}">
            	<input type="hidden" name="filter_and_createTime_LE_T" value="${filter_and_createTime_LE_T}">
            	<input type="hidden" name="filter_and_identityStatus_EQ_T" value="${filter_and_identityStatus_EQ_T}">
                <label>姓名：</label>
				<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}"/>
			</td>
			<td>
				<label>条形码：</label>
				<input type="text" name="filter_and_code_LIKE_S" value="${filter_and_code_LIKE_S}"/>
			</td>
             <td width="100px;">
           <div style="margin-top:0" class="subBar">
            <ul>
			  <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">查找</button></div></div></li>
			  <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearSrchForm()">重置</button></div></div></li>
			</ul>
			</div>
		  </td>
		</tr>
	  
	  </table>
	</div>
	</form>
  </div>
	<div class="tabs">
		<div style="margin-bottom:0px" class="tip">
			<span>微服务查询明细</span>
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
										pagerMethodName="findBySearchDoPage" 
										actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
										informationTableId="exportTable"
										fileName="service_${filter_and_createTime_GE_T }~${filter_and_createTime_LE_T }"
										field="${field}"
										configId="${configId}_s"
										></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="190" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>   
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "姓名">姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "身份证号">身份证号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "电话">电话</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "条形码">条形码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "身份匹配状态">身份匹配状态</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "报告匹配状态">报告匹配状态</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "报告地址">报告地址</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "创建时间">创建时间</th>
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
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>