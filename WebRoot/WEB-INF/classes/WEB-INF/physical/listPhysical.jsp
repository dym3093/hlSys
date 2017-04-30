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
			onsubmit="if(this.action != '${path}/physical/phyReport!getPhyReport.action'){this.action = '${path}/physical/phyReport!getPhyReport.action' ;} ;return navTabSearch(this);"
			action="${path}/physical/phyReport!getPhyReport.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent" style="overflow-y:hidden">
				<tr>
					<td >
						<label style="width:50px">条形码：</label>
						<input style="width: 140px;" type="text" name="filter_and_geneCode_EQ_S" value="${filter_and_geneCode_EQ_S}"/>
					</td>
					<td>
						<label style="width:50px">姓名：</label>
						<input style="width: 140px;" type="text" name="filter_and_userName_LIKE_S" value="${filter_and_userName_LIKE_S}"/>
					</td>
					<td>
						<label style="width:50px">身份证：</label> 
						<input style="width: 140px;" type="text" name="filter_and_userIdno_EQ_S" value="${filter_and_userIdno_EQ_S}"/>
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
		<ul class="toolBar">
			<li><a class="add" 
				href="${path}/physical/phyReport!importReport.action"
				target="dialog" rel="add" title="导入Excel"><span>导入Excel</span></a></li>
			<li><a class="icon" 
				href="${path}/physical/phyReport!listImportRecord.action"
				target="navtab" rel="add" title="查看导入记录"><span>查看导入记录</span></a></li>
			<li><a class="icon" 
				href="${path}/physical/phyReport!updateFileName.action"
				target="navtab" rel="add" title="查看导入记录"><span>修改文件名</span></a></li>	
				
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="35">序号</th>
                <th columnEnName = "failBtachNo" columnChName = "条形码" >条形码</th>
				<th columnEnName = "createTime" columnChName = "姓名">姓名</th>
				<th columnEnName = "comparFailNum" columnChName = "性别" >性别</th>
				<th columnEnName = "comparFailNum" columnChName = "年龄" >年龄</th>
				<th columnEnName = "comparFailNum" columnChName = "身份证" >身份证</th>
				<th columnEnName = "comparFailNum" columnChName = "报告创建时间" >报告创建时间</th>
				<th columnEnName = "" columnChName = "pdf状态" >pdf状态</th>
				<th columnEnName = "" columnChName = "操作" >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }">
 					<td align="center">
						<%-- <input type="checkbox" name="ids" value="${entity.id}"> --%>
						${status.count}
					</td> 
					<%-- <td align="center"><a href="javascript:void(0);" onclick="reportInfo('${entity.geneCode}'),modifyStyle()">${entity.geneCode}</a></td> --%>
					<c:choose>
						<c:when test="${fn:contains(entity.comboName,'基础一')||fn:contains(entity.comboName,'基础二')||fn:contains(entity.comboName,'基础三')}">
							<td align="center"><a class="add" href="${path}/physical/phyReport!getReportInfo.action?geneCode=${entity.geneCode}" target="navTab" title="检查详细">${entity.geneCode}</a></td>
						</c:when>
						<c:otherwise>
							<td align="center"><a class="add" href="${path}/physical/phyReport!getReportInfoRoutine.action?geneCode=${entity.geneCode}" target="navTab" title="检查详细">${entity.geneCode}</a></td>
						</c:otherwise> 
					</c:choose>
					
					<td align="center">	${entity.userName}</td>
					<td align="center">	${entity.userSex}</td>
					<td align="center">	${entity.userAge}</td>
					<td align="center">${entity.userIdno}</td>
					<td align="center">	${entity.reportCreateDate}</td>
					<td align="center">
						<c:if test="${entity.isSuccess == 0}">
							已生成
						</c:if>
						<c:if test="${entity.isSuccess == 1 || entity.isSuccess == null}">
							未生成
						</c:if>
						<%-- <c:if test="${entity.isSuccess == 2}">
							<span style="color:red;">pdf异常</span>
						</c:if> --%>
					</td>
					<td align="center">
						<c:if test="${entity.reportPath != null}">
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="seeReport('${entity.reportPath}')">查看报告</button>
								</div>
							</div>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
</div>
