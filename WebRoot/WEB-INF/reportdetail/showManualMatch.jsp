<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

	function getCodeVal(obj){
		if($("#code").val()==""){
			alertMsg.info("请输入条形码");
			return false;
		}
		return true;
	}

	function setManualData(){
		var pdfid = $("#pdfid").val();
		var checkbox = $("input:checkbox[name='manualNo']:checked");
		if($(checkbox).length!=1){
			alertMsg.info("请选择一条对应的信息");
			return;
		}
		var dataJson="[";
		var id = $(checkbox).parent().parent().children().eq(1).text();
		var code = $(checkbox).parent().parent().children().eq(2).text();
		var name = $(checkbox).parent().parent().children().eq(3).text();
		var sex = $(checkbox).parent().parent().children().eq(4).text();
		var age = $(checkbox).parent().parent().children().eq(5).text();
		dataJson += "{"+"\"id\":\""+id+"\","+"\"pdfid\":\""+pdfid+"\","+"\"name\":\""+name+"\","+"\"code\":\""+code+"\","+"\"sex\":\""+sex+"\","+"\"age\":\""+age+"\"}]";
		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "${path}/report/reportFileTask!setManualData.action",
			data: {"dataJson": dataJson},
			success: function(data){
				if(data.count==1){
					alertMsg.info("提交成功");
					$.pdialog.closeCurrent();
					return navTabSearch($("#queryUnRecordForm"));
				}else{
					alertMsg.error("提交失败");
				}
				
			},
			error :function(data){
				alertMsg.error("服务发生异常，请稍后再试！");
			}
		});		
	}
</script>

<div class="pageHeader">
	<form id="pagerFindForm" rel="pagerForm" onsubmit="if(this.action != '${path}/report/reportFileTask!manualMatch.action'){this.action ='${path}/report/reportFileTask!manualMatch.action' ;};return dialogSearch(this); " 
		action="${path}/report/reportFileTask!manualMatch.action" method="post">
		<div class="searchBar">
			<table class="pageFormContent" id="tableUser">
				<tr>
					<td>
						<label>条形码：</label> 
						<input id="code" type="text" name="filter_and_code_LIKE_S" value="${filter_and_code_LIKE_S}"/>
					</td>
					<td>
						<input name="userInfo" type="submit" value="查找" />
						<input name="manual" type="button" value="提交" onclick="setManualData()"/>
					</td>
					<td>
						<input id="pdfid" type="hidden" name="pdfid" value="${pdfid}"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="150">
		<thead>
			<tr>
				<th width="45">序号</th>
				<th hidden="hidden">ID</th>
				<th>条码</th>
				<th>姓名</th>
				<th>性别</th>
				<th>年龄</th>
			</tr>
		</thead>
		<tbody id="userTbody">
			<c:forEach items="${page.results}" var="entity" varStatus="status">
			<tr target="sid_user" rel="${entity.id}" id="rows">
				<td align="center">
					<input type="checkbox" name="manualNo">${status.count}
				</td>
				<td align="center" name="id" hidden="hidden">${entity.id}</td>
				<td align="center" name="code">${entity.code}</td>
				<td align="center" name="name">${entity.name}</td>
				<td align="center" name="sex">${entity.sex }</td>
				<td align="center" name="age">${entity.age}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
