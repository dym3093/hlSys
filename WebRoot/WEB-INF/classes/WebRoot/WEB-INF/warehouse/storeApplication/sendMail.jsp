<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript">
	var sendMailByHand = function(){
		var email = $("[name='email']").val();
		var fileName = $("[name='fileName']").val();
		if(fileName!=null&&fileName!=""&&fileName!=undefined){
			$.ajax({
				url: "${path}/warehouse/storeApplication!sendMailByHand.action",
				method: "POST",
				dataType: "JSON",
				data: {"email":email,"fileName":fileName},
				success: function(resp){
					var data = "eval("+resp+")";
					alert(resp.msg);
				},
				failure: function(resp){
					var data = "eval("+resp+")";
					alert(data.msg);
				}
			});
		}else{
			alertMsg.error("请填写文件名!");			
		}
	};
</script>

<div class="pageHeader">	
    <div class="searchBar" id="manyQue">
		<form id="pagerFindForm">
			<table class="pageFormContent">
				<tr>
					<td><label>邮箱地址：</label><input type="text" name="email" /></td>
				</tr>
				<tr>
					<td><label>文&nbsp;件&nbsp;名：</label><input type="text" name="fileName" /></td>
				</tr>
				<tr>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="sendMailByHand()">发送</button>
						</div>
					</div>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div>
					</td>
				</tr>
			</table>	
		</form>
	</div>			
</div>			
						