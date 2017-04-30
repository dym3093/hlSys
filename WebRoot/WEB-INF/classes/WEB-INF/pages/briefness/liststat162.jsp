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
			<td>
				<label>条码：</label>
				<input type="text" name="filter_and_code_EQ_S" value="${filter_and_code_EQ_S}" />
			</td>
			<td>
				<label>姓名：</label>
				<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}" />
			</td>
			<td><label>生日：</label>
				<input type="text" name="filter_and_birthday_EQ_S"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'1900-01-01',maxDate:'2110-01-01'})"  readonly="readonly" value="${filter_and_birthday_EQ_S}" /><a class="inputDateButton" href="javascript:;" >选择</a>
			</td></tr>
	  		<tr>
			<td >
				<label>手机号：</label>
				<input type="text" name="filter_and_phone_EQ_S" value="${filter_and_phone_EQ_S}" />
			</td>
			<td>
				<label>证件号：</label>
				<input type="text" name="filter_and_idno_EQ_S" value="${filter_and_idno_EQ_S}" />
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
				<ul class="toolBar">
					<c:if test="${page.results != null && !empty page.results }">
						<!-- <li><a class="edit" href="javascript:void(0);" onclick="downLoadSumary()"><span>导出Excel</span></a></li> -->
						<web:exportExcelTag4Jdbc 
										pagerFormId="pagerFindForm" 
										pagerMethodName="queryResults" 
										actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
										informationTableId="exportTable"
										fileName="stat162"
										configId="stat162"
										></web:exportExcelTag4Jdbc>
					</c:if>
				</ul>
				<jsp:include page="/common/pagination.jsp" />
				</div>
				<table class="list" width="100%" layoutH="150" id = "exportTable"> 
				   <thead>
					<tr>
					    <!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>  -->
					    <th>序号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "条码">条码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "姓名">姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "身份证号">身份证号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "手机号">手机号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "生日">生日</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "套餐名称">套餐名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "性别">性别</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "创建时间">创建时间</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "8" columnChName = "采样时间">采样时间</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "支公司">支公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "总公司">总公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "11" columnChName = "营销员姓名">营销员姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "12" columnChName = "营销员工号">营销员工号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "13" columnChName = "pdf路径">pdf路径</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "14" columnChName = "状态">状态</th>					    
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
						<td align="center" name="report_td" path="${members[13]}">  ${members[13]}</td>
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
<script>

	$(function() {
		$("td[name='report_td']").each(function() {
			var str = $(this).attr("path");
			var htmlStr = "";
			if(str != null && str != "") {
				var arr = str.split(",");
				if(arr.length > 1) {
					htmlStr += "<a href='"+arr[0]+"'>"+arr[0].substring(arr[0].length-8, arr[0].length)+"</a>";
					htmlStr += ",";
					htmlStr += "<a href='"+arr[1]+"'>"+arr[1].substring(arr[1].length-8, arr[1].length)+"</a>";
				}else {
					htmlStr += "<a href='"+arr[0]+"'>"+arr[0].substring(arr[0].length-8, arr[0].length)+"</a>";
				}
			}
			
			$(this).html(htmlStr);
		});
	
	})

</script>