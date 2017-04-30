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
			onsubmit="if(this.action != '${path}/physical/phyReport!listReportInfoAll.action'){this.action = '${path}/physical/phyReport!listReportInfoAll.action' ;} ;return navTabSearch(this);"
			action="${path}/physical/phyReport!listReportInfoAll.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent" style="overflow-y:hidden">
				<tr>
					<td><label>批次号：</label> 
						<input type="text" name="queryObject.reportBatchNo" value="${queryObject.reportBatchNo}" />
					</td>
					<td><label>条形码：</label> 
						<input type="text" name="queryObject.geneCode" value="${queryObject.geneCode}" />
					</td>
				</tr>
				<tr>
					<td><label>姓名：</label> 
						<input type="text" name="queryObject.userName" value="${queryObject.userName}"/>
					</td>
					<td><label>套餐：</label> 
						<input type="text" name="queryObject.combo" value="${queryObject.combo}"/>
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
				<th columnEnName = "createTime" columnChName = "excel文件名">批次号</th>
				<th columnEnName = "comparFailNum" columnChName = "读取时间" >条形码</th>
				<th columnEnName = "comparFailNum" columnChName = "总人数" >姓名</th>
				<th columnEnName = "comparFailNum" columnChName = "状态" >支公司</th>
				<th columnEnName = "createTime" columnChName = "excel文件名">所属公司</th>
				<th columnEnName = "comparFailNum" columnChName = "读取时间" >部门</th>
				<th columnEnName = "comparFailNum" columnChName = "总人数" >套餐</th>
				<th columnEnName = "comparFailNum" columnChName = "状态" >基因报告</th>
				<th columnEnName = "comparFailNum" columnChName = "状态" >1+X报告</th>
				<th columnEnName = "comparFailNum" columnChName = "状态" >导入时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="">
 					<td align="center">
						${status.count}
					</td> 
					<td align="center">	${entity.reportBatchNo}</td>
					<td align="center">	${entity.geneCode}</td>
					<td align="center">${entity.userName}</td>
					<td align="center" width="200" nowrap="true">
						<hpin:id2nameDB id="${entity.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/>
					</td>
 					<td align="center" width="160" nowrap="true">
 						<hpin:id2nameDB id="${entity.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" />
 					</td>
					<td align="center">${entity.dept}</td>
					<td align="center">${entity.combo}</td>
					<td align="center">
						<c:if test="${empty entity.geneReportPath}">
							<span style="color:red;">未生成</span>
						</c:if>
						<c:if test="${not empty entity.geneReportPath}">
							<a target="_blank" href="${entity.geneReportPath}">查看基因报告</a>
						</c:if>
					</td>
					<td align="center">
						<c:if test="${empty entity.reportPath}">
							<span style="color:red;">未生成</span>
						</c:if>
						<c:if test="${not empty entity.reportPath}">
							<a target="_blank" href="${entity.reportPath}">查看1+X报告</a>
						</c:if>
					</td>
					<td align="center">${entity.importDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
</div>
