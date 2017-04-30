<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
function readPdfContentAll(){
	var willReadPdfId=[];
	$('input[name="ids"]:checked').each(function(){
		willReadPdfId.push($(this).val());
	});
	var paramStr = willReadPdfId.join();
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/report/reportFileTask!readPdfContent.action",
		data: {"pdfids":paramStr},
		success: function(data){
			if(data.result=='success'){
				alert("读取成功！");
				return navTabSearch(this);
			}else{
				alert("读取失败！");
			}
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
};

function readPdfContent(pdfid){
	$.ajax({
		type:"post",
		cache:false,
		dataType: "json",
		url: "${path}/report/reportFileTask!readPdfContent.action",
		data:{"pdfids":pdfid},
		success: function(data){
			if(data.result=='success'){
				alert("读取成功！");
				return navTabSearch(this);
			}else{
				alert("读取失败！");
			}
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
			
		}
	});
	
};

function dealPdfAbnormal(pdfid){
	alertMsg.confirm("确认此PDF是异常文件?", {
		okCall: function(){
			$.ajax({
				type:"post",
				cache:false,
				dataType: "json",
				url: "${path}/report/reportFileTask!dealPdfAbnormal.action",
				data:{"pdfid":pdfid},
				success: function(data){
					if(data.result=='success'){
						alert("处理成功！");
						return navTabSearch(this);
					}else{
						alert("处理失败！");
					}
				},
				error :function(data){
					alert("服务发生异常，请稍后再试！");
					
				}
			});
		}
	});
};

function checkAll(obj){
	$("#box input[type='checkbox']").prop('checked', $(obj).prop('checked'));
}

</script>
<div class="tip"><span>读取失败详情</span></div>
<div class="pageHeader">
	<form id="queryUnRecordForm" onsubmit="if(this.action != '${path}/report/reportFileTask!queryAllUnRecord.action'){this.action = '${path}/report/reportFileTask!queryAllUnRecord.action' ;} ;return navTabSearch(this);" action="${path}/report/reportFileTask!queryAllUnRecord.action" method="post">
		<div class="searchBar" style="height:40px;">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>条形码：</label> 
						<input type="text" name="filter_and_code_EQ_S" value="${filter_and_code_EQ_S}"/>
					</td>
					<td>
						<label>批次号：</label> 
						<input id="recordPdfid" type="text" name="filter_and_batchno_EQ_S" value="${filter_and_batchno_EQ_S}"/>
					</td>
					<td>
						<label style="margin-top:9px;"><input type="submit" value="查询" /></label>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<!-- <ul class="toolBar">
			<li><a class="icon" href="javascript:void(0)" onclick="readPdfContentAll()"><span>重新读取</span></a></li>
		</ul> -->
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170">
		<thead>
			<tr>
				<th width="45">序号<!-- <input id="checkAll" type="checkbox" onclick="checkAll(this)"> --></th>
				<th>PDF名称</th>
				<th>时间</th>
				<th>查看</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="box">
			<c:forEach items="${unRecordView}" var="bean" varStatus="status">
			<tr>
				<td align="center">${status.count}<%-- <input type="checkbox" name="ids" value="${bean.id}"> --%></td>
				<td align="center">${bean.pdfname}</td>
				<td align="center">${bean.createdate}</td>
				<td align="center"><a target="_blank" href="${fn:replace(bean.filepath,'/home/ftp/transact','http://img.healthlink.cn:8099/ymReport')}">查看</a></td>
				<td align="center">
					<%-- <a href="javascript:void(0)" onclick="readPdfContent('${bean.id}')">重新读取</a> --%>
					<a href="${path}/report/reportFileTask!showManualMatch.action?pdfid=${bean.id}" style="color:#0000FF" target="dialog">人工匹配</a>
					<a href="javascript:void(0)" onclick="dealPdfAbnormal('${bean.id}')" style="color:red">PDF异常</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
