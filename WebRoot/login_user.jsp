<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="widget/tags-web" prefix="web"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<html>
<head>
<title>云途会员激活平台</title>
<meta http-equiv="pragma" content="no-cache"></meta>
<meta http-equiv="cache-control" content="no-cache"></meta>
<meta http-equiv="expires" content="0"></meta>
<link href="${path }/dwz/themes/css/login.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" language="javascript"
	src="${path }/scripts/jquery.js"></script>
<style type="text/css">
<!--
body {
	
}

.STYLE1 {
	font-size: 12px;
	color: #FFFFFF;
}
-->
</style>
<script type="text/javascript" src="${path }/scripts/index/baseJs.js"></script>
<script type="text/javascript">
	$(function(){
		document.onkeydown = function(e){
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		           $("#_form").submit();
		     }
		}
	});  
	 
	function submitsForm(_form) {
		var j_username  = $("#j_username").val();
		var j_password  = $("#j_password").val();
		var j_extension = $("#j_extension").val() ;
		if (j_username == '') {
			alert('用户名不能为空！');
			$("#j_username").focus();
			return false;
		}
		if (j_password == '') {
			$("#j_password").focus();
			alert('密码不能为空！');
			return false;
		}
		if (j_extension == '') {
			$("#j_extension").focus();
			alert('分机号码不能为空！');
			return false;
		}
		_form.submit();
	}
	
	function enterSubmit(_form){
		if(event.keyCode==13){
	      return submitsForm(_form);
	    } 
	}
</script>
</head>
<body>
<div style="float: right; padding: 10px;color:#00a658;font-weight:bold;font-size:12px;"><a href="${path }/login.jsp">非坐席登陆</a></div>
<form method='post' action='${path}/security/security!login.action' id='_form'>
<div class="pageBox">
<div class="logo"></div>
<div class="loginContent">
<div class="loginbg"></div>
<div class="loginBox">
<h1>云途会员激活平台</h1>
<ul>
	<li><label class="dlzh" id="dlzh1">用户名</label> <input
		name="j_username" value="" type="text" class="input01" id="j_username"
		onblur="blur1()" onkeydown="enterSubmit(this.form)" autocomplete="off" />
	</li>
	<li><label class="dlzh" id="dlzh2">密码</label> <input
		name="j_password" style="width:149px;" value="" type="password" class="input01" 
		id="j_password" onblur="blur2()" onkeydown="enterSubmit(this.form)" />
	</li>
	<li><label class="dlzh" id="dlzh1">分机号码</label> <input
		name="j_extension" type="text" class="input01" id="j_extension"
		onblur="blur1()" onkeydown="enterSubmit(this.form)" autocomplete="off" />
	</li>
	<li><input type="button" value="登录" class="loginBtn" style="float: left;"
		onclick="submitsForm(this.form)" />
	</li>
</ul>
</div>
</div>
</div>
</form>
</body>
</html>