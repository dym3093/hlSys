<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
 
<script type="text/javascript">


//关闭弹窗
function closeDialogChange(type){
	if('1'==type){
		$("#submitBn").attr("disabled","disabled");
	}
	var expressCommany = $("#expressCommany").val();
	var expressNo = $("#expressNo").val();
	var expressCost = $("#expressCost").val();
	var expressWeight = $("#expressWeight").val();
	var reasonArr = $("input[name='reason']:checked");
	var reason = "";
	for(var i =0;i<reasonArr.length;i++){
		reason+= ","+reasonArr[i].value;
	}
	if(reason!=""){
		reason = reason.substring(1);
	}else{
		alertMsg.error("请勾选变更原因");
		$("#submitBn").removeAttr("disabled");
		return;
	}
	var reportId = $("#reportId").val();
	if('1'==type){
		$.ajax({
			type: "post",
			cache :false,
			data:{"reportId":reportId,"reason":reason,"expressCommany":expressCommany,"expressNo":expressNo,"expressWeight":expressWeight,"expressCost":expressCost},
			url: "${path}/reportdetail/reportDelivery!changeExpress.action",
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
			<input type="hidden" id="reportId" value="${reportId }"/>
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
					<label style="margin-top: 15px">变更原因：</label> 
					<input style="margin-top: 15px" type="checkbox" name="reason" value="快递公司名称有误"  class="required"/>
					<span style="font-size: 24px;">快递公司名称有误</span> 
				</td>
				<td>
					<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> 
					<input style="margin-top: 15px" type="checkbox" name="reason" value="快递单号有误"  class="required"/>
					<span style="font-size: 24px;">快递单号有误</span>
				</td>
			</tr>
			<tr>
				<td>
					<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> 
					<input style="margin-top: 15px" type="checkbox" name="reason" value="快递费用有误"  class="required"/>
					<span style="font-size: 24px;">快递费用有误</span>
				</td>
				<td>
					<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> 
					<input style="margin-top: 15px" type="checkbox" name="reason" value="快递重量有误" class="required"/>
					<span style="font-size: 24px;">快递重量有误</span>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="submitBn" onclick="closeDialogChange('1')">确认</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialogChange('2')">返回</button></div></div>
				</td>
			</tr>
		</table>
	</form>
</div>
