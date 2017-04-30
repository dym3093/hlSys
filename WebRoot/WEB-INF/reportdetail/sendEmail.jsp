<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>    
<script type="text/javascript">
</script>
<style>
table td{
word-break: keep-all;
white-space:nowrap;
}
table th{
word-break: keep-all;
white-space:nowrap;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	/* $("input[name='sendEmail_ids']").bind("click",function (){
		var baseInfoIds = $("#baseInfoIds").val();
		if($(this).is(':checked')){
			if(baseInfoIds.indexOf($(this).val())<0){
				if(baseInfoIds!=""){
					baseInfoIds+=","+$(this).val();
				}else{
					baseInfoIds = $(this).val();
				}
			}
		}else{
			if(baseInfoIds.indexOf(","+$(this).val())>-1){
				baseInfoIds = baseInfoIds.replace((","+$(this).val()),"");
			}else if(baseInfoIds.indexOf($(this).val())>-1){
				baseInfoIds = baseInfoIds.replace($(this).val(),"");
			}
		}
		$("input[name='baseInfoIds']").val(baseInfoIds);
	}) */
	//切换查询条件渲染页面之后 将已选择的展示出来
	rederCheckboxforSendEmail();
});

function changeSendEmailCheckBox(box){
	var baseInfoIds = $("#baseInfoIds").val();
	if($(box).is(':checked')){
		if(baseInfoIds.indexOf($(box).val())<0){
			if(baseInfoIds!=""){
				baseInfoIds+=","+$(box).val();
			}else{
				baseInfoIds = $(box).val();
			}
		}
	}else{
		if(baseInfoIds.indexOf(","+$(box).val())>-1){
			baseInfoIds = baseInfoIds.replace((","+$(box).val()),"");
		}else if(baseInfoIds.indexOf($(box).val())>-1){
			baseInfoIds = baseInfoIds.replace($(box).val(),"");
		}
	}
	$("input[name='baseInfoIds']").val(baseInfoIds);
}

function rederCheckboxforSendEmail(){
	var baseInfoIds = '${baseInfoIds }' ;//$("#reportIds").val();
	var arrIds = baseInfoIds.split(",");
	for(var i in arrIds){
		$("input[name='sendEmail_ids'][value='"+arrIds[i]+"']").attr("checked","checked");
	}
}
//显示选中
function showChooseEmail(){
	var baseInfoIds = $("#baseInfoIds").val();
	$.ajax({
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/reportdetail/reportDelivery!showChooseEmail.action",
		data:{"baseInfoIds":baseInfoIds},
		success: function(data){
			eval("data="+data);
			if(data.status == '200'){
				var showMsg = data.data;
				var str = "姓名，电话，邮箱地址    \n";
				for(var i=0; i < showMsg.length;i++){
					str+=showMsg[i].name+"，";
					str+=showMsg[i].phone+"，";
					str+=showMsg[i].email+"\n";
				}
				alert(str);
			}else if(data.status == '300'){
				alertMsg.error(data.message)
			}
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

//关闭弹窗 发送邮件
function sendEmail(){
	$("#sendEmailBtn").attr("disabled","disabled");
	var baseInfoIds = $("#baseInfoIds").val();
	var reportIds = $("input[name='reportIds']").val();
	if(reportIds!=""){
		$.ajax({
			type: "post",
			cache :false,
			data:{"baseInfoIds":baseInfoIds,"reportIds":reportIds},
			url: "${path}/reportdetail/reportDelivery!sendEmail.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.status=='200'){
					$.pdialog.closeCurrent();
					alertMsg.correct(data.message);
					return navTabSearch(this);
				}else if(data.status=='300'){
					alertMsg.error(data.message);
					$("#sendEmailBtn").removeAttr("disabled");
				}
			},
			error :function(){
				alertMsg.error("服务发生异常，请稍后再试！");
				return;
			}
		});
	}else{
		alertMsg.error("请至少选择一条数据！");
	}
}

</script>
<div class="pageHeader" style="background:white">
	<form id="pagerFindForm"  rel = "pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${path }/reportdetail/reportDelivery!findBaseEmpInfo.action" method="post">

	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<input type="hidden" id="baseInfoIds" name="baseInfoIds" value="${baseInfoIds }"/>
					<input type="hidden" name="reportIds" value="${reportIds }"/>
					<label>联系人姓名：</label>
					<input type="text" name="filter_and_name_LIKE_S"  value="${filter_and_name_LIKE_S}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
				</td>
			</tr>
		</table>
		
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="formBar" >
	 <ul>
	       <li>
	          <div class="buttonActive">
	            <div class="buttonContent">
	              <button onclick="showChooseEmail()">查看选中</button>
	            </div>
	          </div>
	        </li>
	       <li>
	          <div class="buttonActive">
	            <div class="buttonContent">
	              <button id="sendEmailBtn" onclick="sendEmail()">发送邮件</button>
	            </div>
	          </div>
	        </li>
	         
	  </ul>
	<p></p>
	</div>
<div class="panelBar">
		<ul class="toolBar">
		</ul>
		<jsp:include page="/common/paginationDialog.jsp" />	
	</div>
<table class="table" width="100%" layoutH="150">
	<thead>
		<tr>
			<th style="width: 50px">序号</th>
			<th>姓名 </th>
			<th>电话</th>
			<th>邮箱地址</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${ page.results }" var="baseEmpInfo" varStatus="status">
		<tr target="sid_user" rel="${baseEmpInfo.id }">
			<td>
				<input type="checkbox" onchange="changeSendEmailCheckBox(this)" name="sendEmail_ids" value="${baseEmpInfo.id}">
				${ status.count }
			</td>
			<td align="center" nowrap="nowrap">${baseEmpInfo.name }</td>
			<td align="center" nowrap="nowrap">${baseEmpInfo.phone }</td>
			<td align="center" nowrap="nowrap">${baseEmpInfo.email }</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>