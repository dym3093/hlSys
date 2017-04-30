<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<style>
#codeQue {
	display: none;
}
</style>

<script type="text/javascript" language="javascript">
	$(function() {
		$("#button").click(function() {
			$("#codeQue").css("display", "block");
			$("#manyQue").css("display", "none");
		});

		$("#back").click(function() {
			$("#codeQue").css("display", "none");
			$("#manyQue").css("display", "block");
		});
		
/* 		$("a[class='add']").hover(){
			
		} */
	});
	
$(document).ready(function(){
	$("#allTableDiv").css("display", "none");
	
	var hiddenText = $("input[name='selectStatus']", navTab.getCurrentPanel()).val();
	if(hiddenText=='0'){
		$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).find("option[value='0']").attr("selected",true);
	}else if(hiddenText=='1'){
		$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).find("option[value='1']").attr("selected",true);
	}
});

//保存下拉选中的value
$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).change(function(){
	var select = $("select[name='filter_and_status_EQ_S'][option:selected]", navTab.getCurrentPanel()).val();			//获取选中的下拉的值
	$("input[name='selectStatus']", navTab.getCurrentPanel()).val(select);
});
	
function submitForm() {
	/*var flag = false;
	$(".required",navTab.getCurrentPanel()).each(function(){
		if($(this).val()==""){
			$(this).focus();
			flag = true;
		}
	});
	
	if(flag){
		alert("您有必选项没有填写请确认");
		return false;
	}*/

	/*document.form1.isBtn.value = "2";*/
	$(".pageForm", navTab.getCurrentPanel()).submit();

}

function reportInfo(reportPath){
	$.ajax({
		type: "post",
		cache :false,
		dateType:"json",
		data:{"reportPath":reportPath},
		url: "${path}/physical/phyReport!getReportInfo.action",
		success: function(data){
			$("#diseaseTable").empty();
			$("#projectTable").empty();
			var s= eval("("+data+")");
			var diseaseMap = s.diseaseMap;
			var projectMap = s.projectMap;
			var table1 = "";
			var table2 = "";
			for(var key1 in diseaseMap){
				//alert("key--"+key1+",value--"+diseaseMap[key1]);
				table1=table1+"<tr><td align='center'>"+key1+"</td>"+"<td align='center'>"+diseaseMap[key1]+"</td></tr>";
			}
			for(var key2 in projectMap){
				table2=table2+"<tr><td align='center'width='200'>"+key2+"</td>"+"<td align='center' width='800' style='word-break:break-all'>"+projectMap[key2]+"</td></tr>";
			}
			//将节点插入到下拉列表中
			$("#diseaseTable").append(table1);
			$("#projectTable").append(table2);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

function seeReport(reportPath){
	alert(reportPath);
	$.ajax({
		type: "post",
		cache: false,
		data:{"reportPath":reportPath},
		url: "${path}/physical/phyReport!seeReport.action",
		success: function(data){
			var obj=eval("("+data+")");
			window.open(obj.path);
			//window.location.href=obj.path;
		},
		error:function(){
			alert("查看失败，请检查服务器！");
			return;
		}
	});
};

function modifyStyle(){
	$("#allTableDiv").css("display", "block");
}
</script>
<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<div class="searchBar" style="overflow-x: hidden;">
		<form id="pagerFindForm" 
			onsubmit="if(this.action != '${path}/physical/phyReport!listPhyReportExcelFile.action'){this.action = '${path}/physical/phyReport!listPhyReportExcelFile.action' ;} ;return navTabSearch(this);"
			action="${path}/physical/phyReport!listPhyReportExcelFile.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent" style="overflow-y:hidden">
				<tr>
					<td><label>起始日期：</label> 
						<input type="hidden" name="hiddenInput" value=""/><!-- 用于解决时间插件BUG -->
						<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
						
						<input type="hidden" name="selectStatus" value="${selectStatus}"/>
					</td>
					<td><label>结束日期：</label> 
						<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
					</td>
				</tr>
				<tr>
					<td><label>读取状态：</label> 
						<select name="filter_and_status_EQ_S"  rel="iselect">
						<option value=''>全部</option>
						<option value='1'>成功</option>
						<option value='0'>失败</option>
						</select>
					</td>
					<td style="padding-left:35px"><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
						<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
					</td>
				</tr>
			</table>
	</form>
	</div>
</div>
<div class="pageContent" style="overflow-x: hidden;">
	<div class="panelBar">
		<%-- <ul class="toolBar">
			<li><a class="add" 
				href="${path}/physical/phyReport!importReport.action"
				target="dialog" rel="add" title="导入Excel"><span>导入Excel</span></a></li>
			<li><a class="icon" 
				href="${path}/physical/phyReport!listImportRecord.action"
				target="navtab" rel="add" title="查看导入记录"><span>查看导入记录</span></a></li>
			<li><a class="icon" 
				href="${path}/physical/phyReport!updateFileName.action"
				target="navtab" rel="add" title="查看导入记录"><span>修改文件名</span></a></li>	
		</ul> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="35">序号</th>
				<th columnEnName = "createTime" columnChName = "excel文件名">excel文件名</th>
				<th columnEnName = "comparFailNum" columnChName = "读取时间" >读取时间</th>
				<th columnEnName = "comparFailNum" columnChName = "总人数" >总人数</th>
				<th columnEnName = "comparFailNum" columnChName = "状态" >状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }">
 					<td align="center">
						<%-- <input type="checkbox" name="ids" value="${entity.id}"> --%>
						${status.count}
					</td> 
					<td align="center">	${entity.fileName}</td>
					<td align="center">	${entity.createTime}</td>
					<td align="center">${entity.num}</td>
					<td align="center">
						<c:if test="${entity.status=='1'}">
							<span style="color:green;">读取成功</span>
						</c:if>
						<c:if test="${entity.status=='0'}">
							<span style="color:red;">读取失败</span>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
</div>
