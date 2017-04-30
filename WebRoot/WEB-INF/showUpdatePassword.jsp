<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function submitForm(thisForm) {
	var newPassword1 = document.getElementById("newPassword1");
	var newPassword2 = document.getElementById("newPassword2");
	if (newPassword1.value == '') {
		alert("请输入新密码！");
		return;
	} 
	else if (newPassword2.value == '') {
		alert("请确认新密码！");
		return;
	}
	else if (newPassword1.value != '' && newPassword2.value !='') {
		if (newPassword1.value != newPassword2.value) {
			alert("新密码输入不一致！");
			newPassword1.value = '';
			newPassword2.value = '';
			return;
		}
		else {
			thisForm.submit();
		}
	}
}
</script>
<form method="post" onsubmit="alert(validateCallback(this, dialogAjaxDone));return validateCallback(this, dialogAjaxDone);" action="${path }/security/security!updatePassword.action" class="pageForm required-validate">
<div class="pageContent">
	<div class="pageFormContent" layoutH="60">
		<div style="float:left;clear:both">
		    <table class='list nowrap' width='100%'>
			    <tr>
					<td width="30%">
						<nobr>
							<label>
								用户姓名：
							</label>
						</nobr>
					</td>
					<td>
						${currentUser.userName}
					</td>
				</tr>
				<tr>
					<td>
						<nobr>
							<label>
								账户：
							</label>
						</nobr>
					</td>
					<td>
						${currentUser.accountName}
					</td>
				</tr>
				<tr>
					<td>
						<nobr>
							<label>
								<font color="red">*</font>新密码：
							</label>
						</nobr>
					</td>
					<td>
						<input type="password" name="newPassword1" id="newPassword1"
							size="16" maxlength="16"
							lang="check:!NULL&长度大于5---info:新密码不能为空并且长度6～16位">
					</td>
				</tr>
				<tr>
					<td>
						<nobr>
							<label>
								<font color="red">*</font>新密码确认：
							</label>
						</nobr>
					</td>
					<td>
						<input type="password" name="newPassword2" id="newPassword2"
							size="16" maxlength="16" lang="check:!NULL---info:新密码确认不能为空！">
					</td>
				</tr>
	  	    </table>
	  	 </div>
	  </div>
	 <div class="formBar">
		<ul>
		    <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm(this.form)">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>
</form>