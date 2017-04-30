<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
 
<script type="text/javascript">


//关闭弹窗
function closeDialog(type){
	if('1'==type){
		$("#submitBn").attr("disabled","disabled");
	}
	var expressCommany = $("#expressCommany").val();
	var expressNo = $("#expressNo").val();
	var expressCost = $("#expressCost").val();
	var expressWeight = $("#expressWeight").val();
	var eventNo = $("#eventNo").val();
	var reportCode = $("#reportCode").val();
	var reportId = $("#reportId").val();
	var allEventsIds = $("#allEventsIds").val();
	if('1'==type){
		$.ajax({
			type: "post",
			cache :false,
			data:{"reportId":reportId,"eventNo":eventNo,"reportCode":reportCode,"expressCommany":expressCommany,"expressNo":expressNo,"expressWeight":expressWeight,"expressCost":expressCost,"allEventsIds":allEventsIds},
			url: "${path}/reportdetail/reportDelivery!expressEntry.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.status=='200'){
					$.pdialog.closeCurrent();
					alertMsg.correct(data.message);
					return navTabSearch(this);
				}else if(data.status=='300'){
					alertMsg.error(data.message);
					$("#submitBn").removeAttr("disabled");
				}
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}else{
		$.pdialog.closeCurrent();
	}
}

</script>
<style>
#expressTable tr{
height: 50px;
}
table td{
word-break: keep-all;
white-space:nowrap;
}
table th{
word-break: keep-all;
white-space:nowrap;
}
#expressTable label{
margin-top:8px;
font-size: 20px;
}
#expressTable input{
height: 30px;
}

</style>
<div >
	<form id="form" name="form" class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);"
		 method="post">
			<table class="pageFormContent" id="expressTable">
			<input type="hidden" id="eventNo" value="${eventNo }"/>
			<input type="hidden" id="reportCode" value="${reportCode }"/>
			<input type="hidden" id="reportId" value="${reportId }"/>
			<input type="hidden" id="allEventsIds" value="${allEventsIds }"/>
			<tr>
				<td>
					<label>快递公司：</label> 
					<input type="text" id="expressCommany" name="expressCommany" value="${baseInfo.expressCommany }"  class="required"/>
				</td>
				<td>
					<label>快递单号：</label> 
					<input type="text" id="expressNo" name="expressNo" value="${baseInfo.expressNo }" class="required"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>快递费用：</label> 
					<input type="text" id="expressCost" name="expressCost" value="${baseInfo.expressCost }"  class="required number textInput"/>
				</td>
				<td>
					<label>快递重量：</label> 
					<input type="text" id="expressWeight" name="expressWeight" value="${baseInfo.expressWeight }"  class="required"/>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="submitBn" onclick="closeDialog('1')">确认</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog('2')">返回</button></div></div>
				</td>
			</tr>
		</table>
	</form>
</div>
