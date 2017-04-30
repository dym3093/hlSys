<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

%>

<style type="text/css">
	.select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
</style>

<script type="text/javascript" language="javascript">

function getRepeatPrintTask(){//添加重复打印任务
	$.pdialog.open("${path}/reportdetail/erpRepeatPrintTask!getRepeatPrintTask.action", "getRepeatPrintTask", "新增", 
			{width:600,height:400,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
}
function showUploadFile(){//添加重复打印任务
	$.pdialog.open("${path}/reportdetail/erpRepeatPrintTask!showUploadFile.action", "showUploadFile", "导入excel", 
			{width:800,height:600,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
}
function clearInput(){
	$(':input','#pagerFindForm',navTab.getCurrentPanel())  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('selected');  
}

function test(){
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/reportdetail/erpRepeatPrintTask!test.action",
		success: function(data){
				alert(1);
			},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}
</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportdetail/erpRepeatPrintTask!listRepeatPrintTask.action'){this.action = '${path}/reportdetail/erpRepeatPrintTask!listRepeatPrintTask.action' ;} ;return navTabSearch(this);" action="${path}/reportdetail/erpRepeatPrintTask!listRepeatPrintTask.action" method="post">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>条形码：</label>
						<input type="text" name="filter_and_code_LIKE_S" id="code" value="${filter_and_code_LIKE_S}"/> 
					</td>
					<td>
						<label>姓名：</label>
						<input type="text" name="filter_and_userName_LIKE_S" id="code" value="${filter_and_userName_LIKE_S}"/> 
					</td>
					<td>
						<label>打印状态：</label>
						<select name="filter_and_isPrint_LIKE_S" id="isPrintSel" class="select">
							<option value="">---请选择---</option>
							<option value="0" ${isPrint=="0"?"selected='selected'":""}>未打印</option>
							<option value="1" ${isPrint=="1"?"selected='selected'":""}>已打印</option>
						</select>
					</td>
					<td style="padding-left:30px;">
						<div class="buttonActive" style="float:right;"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
						<div class="buttonActive" style="float:right;"><div class="buttonContent"><button type="submit">查找</button></div></div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
            <li><a class="add" href="javascript:void(0)" onclick="getRepeatPrintTask()"><span>添加</span></a></li>
            <li><a class="icon" href="javascript:void(0)" onclick="showUploadFile()"><span>导入excel</span></a></li>
            <!-- <li><a class="icon" href="javascript:void(0)" onclick="test()"><span>测试</span></a></li> -->
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> -->
				<th width="35">序号</th>
				<th>条形码</th>
				<th>姓名</th>
				<th>年龄</th>
				<th>性别</th>
				<th>套餐名称</th>
				<th>报告类型</th>
				<th>打印状态</th>
				<th>创建日期</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="repeatPrint" varStatus="status">
					<tr target="sid_user" rel="${repeatPrint.id }">
						<td align="center">${status.count}</td> 
						<td align="center">${repeatPrint.code}</td>
						<td align="center">${repeatPrint.userName}</td>
						<td align="center">${repeatPrint.age}</td>
						<td align="center">${repeatPrint.gender}</td>
						<td align="center">${repeatPrint.combo}</td>
						<td align="center">
							<c:if test="${repeatPrint.reportType==0}">基因报告</c:if>
							<c:if test="${repeatPrint.reportType==2}">癌筛报告</c:if>
							<c:if test="${repeatPrint.reportType==3}">癌筛报告单</c:if>
							<c:if test="${repeatPrint.reportType==4}">1+X报告</c:if>
							<c:if test="${repeatPrint.reportType==5}">无创</c:if>
						</td>
						<td align="center">
							<c:if test="${repeatPrint.isPrint=='0'}">未打印</c:if>
							<c:if test="${repeatPrint.isPrint=='1'}">已打印</c:if>
						</td>
						<%-- <td align="center">${repeatPrint.eventNo}</td> --%>
						<td align="center">${fn:substring(repeatPrint.createTime,0,19)}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
</div> 