<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
 
<script type="text/javascript">


//关闭弹窗
function closeDialog(type){
	var keyWord = $("#keyWord").val();
	var crid = $("#crid").val();
	var projectTypeCode = $("#projectTypeCode").val();
	var proId = $("#proId").val();
	if('1'==type){
		$.ajax({
			type: "post",
			cache :false,
			data:{"crid":crid,"keyWord":keyWord,"projectTypeCode":projectTypeCode,"proId":proId},
			url: "${path}/resource/customerRelationShip!createQRCode.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.status=='200'){
					$.pdialog.closeCurrent();
					alertMsg.correct(data.message);
					return navTabSearch(this);
				}else if(data.status=='300'){
					alertMsg.error(data.message);
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
table td{
word-break: keep-all;
white-space:nowrap;
}
table th{
word-break: keep-all;
white-space:nowrap;
}
</style>
<div style="background:white">
	<form onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/resource/customerRelationShip!createQRCode.action" method="post">
			<table class="pageFormContent">
			<tr>
				<td>
					<label>关键字：</label> 
					<input type="text" id="keyWord" name="keyWord" value="${ship.keyWord }"  class="required"/>
					<input type="hidden" id="crid" name="crid" value="${crid }">
					<input type="hidden" id="projectTypeCode" name="projectTypeCode" value="${projectTypeCode }">
					<input type="hidden" id="proId" name="proId" value="${proId }">
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog('1')">确认</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog('2')">返回</button></div></div>
				</td>
			</tr>
		</table>
	</form>
</div>
