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
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path }/statistics/queryaction!queryReport.action'){this.action = '${path }/statistics/queryaction!queryReport.action' ;};return navTabSearch(this); " action="${path }/statistics/queryaction!queryReport.action" method="post" rel="pagerForm">
	<div class="searchBar">
	<input type="hidden" name="configId" value="${actionMap['configId']}">
	  <table class="pageFormContent num1_tab">
	  		
	  		<tr>
				<td >
					<label>场次号：</label>
					<input type="text" name="filter_and_eventsNo_EQ_S" value="${filter_and_eventsNo_EQ_S}" />
				</td>
				<td>
					<label>项目编码：</label>
					<input type="text" name="filter_and_projectCode_EQ_S" value="${filter_and_projectCode_EQ_S}" />
				</td>
			<tr>
	  			<td >
					<label>项目名称：</label>
					<input type="text" name="filter_and_projectName_LIKE_S" value="${filter_and_projectName_LIKE_S}" />
				</td>
				<td>
		        <div class="subBar">	
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
				</div>
				<table class="list" width="100%" layoutH="190" id = "exportTable"> 
				   <thead>
					<tr>
					    <!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>  -->
					    <th>序号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "项目编码">项目编码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "项目名称">项目名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "项目负责人">项目负责人</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "场次号">场次号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "批次号">批次号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "条形码">条形码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "姓名">姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "性别">性别</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "8" columnChName = "年龄">年龄</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "部门">部门</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "支公司">支公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "11" columnChName = "套餐">套餐</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "12" columnChName = "保险公司单价">保险公司单价</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "13" columnChName = "基因公司单价">基因公司单价</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "14" columnChName = "是否有报告">是否有报告</th>
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