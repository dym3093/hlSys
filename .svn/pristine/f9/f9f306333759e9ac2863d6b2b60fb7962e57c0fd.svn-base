<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript">
function startMatch(batchno){
		$("input[ybc="+batchno+"]").attr("disabled","disabled");
		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			data:null,	
			url: "${path}/report/reportFileTask!dealCustomerMatch.action?batchno="+batchno,
			success: function(data){
				var reObj = data.isRepeat;
				var jsonObj = data.results;
				if(reObj=='fail'){
					$("#"+batchno).text("本批次没有需要匹配的数据");
					return;
				}
				if(reObj=='success'){
					$("#"+batchno).text("PDF数量="+jsonObj.needmatchnum+";子公司数量="+jsonObj.companynum+";更新数据="+jsonObj.updatenum+";未匹配数据="+jsonObj.unmatchnum);
					return;
				}
				if(typeof(jsonObj.exception)!="undefined"){
					$("#"+batchno).text(jsonObj.exception);
					return;
				}
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
</script>
<div class="tip"><span>手工处理PDF比对</span></div>
<div class="pageHeader">
</div>
	
<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<div layouth="170" style="overflow: auto; height: 102px;">
		<table class="list" width="100%" layoutH="170" id="">
			<thead>
				<tr>
					<th>序号</th>
					<th>批次号</th>
					<th>创建时间</th>
					<th>PDF数量</th>
                    <th>比对结果</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="pdftask" varStatus="status">
					<tr>
						<td align="center">${status.count}
                            <input type="hidden" name="taskid" value="${pdftask.id}">
                        </td>
						<td align="center">${pdftask.batchno}</td>
                        <td align="center">${fn:substring(pdftask.createdate,0,17)}00</td>
                        <td align="center">${pdftask.pdftotal}</td>
                        <td align="center" id="${pdftask.batchno}"></td>
                        <td align="center">
                        	<input type="button" value="开始比对" ybc="${pdftask.batchno}" onclick="startMatch('${pdftask.batchno}')">
                        </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
