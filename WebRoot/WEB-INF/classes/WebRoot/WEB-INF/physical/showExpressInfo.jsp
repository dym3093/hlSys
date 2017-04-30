<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	
</style>
<script type="text/javascript" language="javascript">


	//关闭弹窗
	function closeDialog(){
		$.pdialog.closeCurrent();
	}
	
	function editExpressInfo(){
		var id = $.trim($("#printTaskId").val());
		var recipient = $.trim($("#recipient").val());
		var telephone = $.trim($("#telephone").val());
		var address = $.trim($("#address").val());
		var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
	    var isMob=/^(?:13\d|15[89])-?\d{5}(\d{3}|\*{3})$/;
	    if(recipient=="" || telephone=="" || address==""){
	    	alertMsg.error("你还有字段没有输入，请重新检查输入");
	    	return;
	    }
	    if(isNaN(telephone)){
	    	alertMsg.error("请输入正确的手机号");
	        return false;
	    }
		$.ajax({
			type:"post",
			cache:false,
			data:{"id":id,"recipient":recipient,"telephone":telephone,"address":address},
			url:"../report/xreportPrintTask!editExpressInfo.action",
			success : function(data) {
				var str= eval("("+data+")");
				if(str.count==1){
					alertMsg.correct("修改成功");
				}else{
					alertMsg.error("修改失败");
				}
				navTabSearch(this);
				$.pdialog.closeCurrent();
			},
			error : function() {
				alert("服务器出错,请稍后再试");
				return;
			}
		});		
	}
	
</script>
<div>
	<table>
		<tr hidden="hidden">
			<td>
				<label>ID：</label>
				<input id="printTaskId" type="text" name="filter_and_id_LIKE_S" value="${id}" />
			</td>
		</tr>
		<tr>
			<td style="padding-left:24px;">
				<label>收件人：</label>
				<input id="recipient" type="text" name="filter_and_expressRecipent_LIKE_S" value="${recipient}" />
			</td>
		</tr>
		<tr>
			<td>
				<label>收件人电话：</label>
				<input id="telephone" type="text" name="filter_and_telephone_LIKE_S" value="${telephone}" />
			</td>
		</tr>
		<tr>
			<td style="padding-left:12px;">
				<label>寄送地址：</label>
				<textarea id="address" cols="35" rows="3" name="filter_and_address_LIKE_S" value="${address}">${address}</textarea>
			</td>
		</tr>
		<tr>
			<td style="padding-left:278px;">
				<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="editExpressInfo()">提交</button></div></div>
				<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog()">返回</button></div></div>
			</td>
		</tr>
	</table>
</div>
