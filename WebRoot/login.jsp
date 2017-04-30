<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="widget/tags-web" prefix="web"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<html>
<head>
 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
<title>远盟基因平台</title>
<meta http-equiv="pragma" content="no-cache"></meta>
<meta http-equiv="cache-control" content="no-cache"></meta>
<meta http-equiv="expires" content="0"></meta>
 <meta name="description" content="Custom Login Form Styling with CSS3" />
 <meta name="keywords" content="css3, login, form, custom, input, submit, button, html5, placeholder" />
  <meta name="author" content="Codrops" />

<link href="${path }/dwz/themes/css/demo.css" rel="stylesheet" type="text/css" />
<link href="${path }/dwz/themes/css/font-awesome.css" rel="stylesheet" type="text/css" />
<link href="${path }/dwz/themes/css/login.css" rel="stylesheet" type="text/css" />
  <script src="${path }/dwz/themes/js/modernizr.custom.63321.js"></script>
   <!--[if lte IE 7]><style>.main{display:none;} .support-note .note-ie{display:block;}</style><![endif]-->
  <script type="text/javascript" src="${path }/dwz/js/jquery.min.js"></script>
		<script type="text/javascript">
			$(function(){
			    $(".showpassword").each(function(index,input) {
			        var $input = $(input);
			        $("<p class='opt'/>").append(
			            $("<input type='checkbox' class='showpasswordcheckbox' id='showPassword' />").click(function() {
			                var change = $(this).is(":checked") ? "text" : "password";
			                var rep = $("<input placeholder='Password' type='" + change + "' />")
			                    .attr("id", $input.attr("id"))
			                    .attr("name", $input.attr("name"))
			                    .attr('class', $input.attr('class'))
			                    .val($input.val())
			                    .insertBefore($input);
			                $input.remove();
			                $input = rep;
			             })
			        ).append($("<label for='showPassword'/>").text(" 显示密码")).insertAfter($input.parent());
			    });

			    $('#showPassword').click(function(){
					if($("#showPassword").is(":checked")) {
						$('.icon-lock').addClass('icon-unlock');
						$('.icon-unlock').removeClass('icon-lock');    
					} else {
						$('.icon-unlock').addClass('icon-lock');
						$('.icon-lock').removeClass('icon-unlock');
					}
			    });
			});
			
			$(document.body).ready(function () {
			    //首次获取验证码
			    $("#imgVerify").attr("src","${path}/security/security!getVerify.action?");
			});

			//获取验证码
			function getVerify(obj){
			    obj.src = "${path}/security/security!getVerify.action?";
			}

		</script>


</head>
<body>
 <div class="container">
		 <div id="header" style="margin-bottom:100px; height:56px;background:url(${path}/images/header.png) repeat-x;">
		 <div style="color:#fff; padding:15px 0 0 50px; font-size: 22px;">远盟基因平台入口</div></div>	
			
			
			<section class="main">
<form method='post' class="form-2" action='${path}/security/security!login.action' id='_form'>

			<h1><span class="log-in">远盟基因平台</span>- <span class="sign-up">登录</span></h1>
                    <p class="float">
                        <label for="login"><i class="icon-user"></i>用户名：</label>

                    <input name="j_username"  type="text"  id="j_username" placeholder="Username" onblur="blur1()" onkeydown="enterSubmit(this.form)" autocomplete="off" />   
                    </p>
                    <p class="float">
                        <label for="password"><i class="icon-lock"></i>密 码：</label>
  
                 <input name="j_password"  type="password" class="showpassword"  placeholder="Password"  id="j_password" onblur="blur2()" onkeydown="enterSubmit(this.form)" />
              
                    </p>
                     <p class="float">
                        <label >验证码：</label>
  
                 <input style="width: 220px" name="verifyCode" type="text" id="verifyCode"  placeholder="请输入验证码">
                    </p>
                    <p class="float">
                    <label ></label>
                          <img id="imgVerify" src="" alt="点击更换验证码" width="112" height="38" onclick="getVerify(this);">
                    </p>
					<p class="clearfix"> 
					 <input  type="submit" name="submit" value="登录" class="loginBtn" onclick="submitForm(this.form)" />
						</p>
				</form>

	</section>
        </div>

	<div style="text-align:center;position:relative;top:260px;color:#106499;">友情链接：<a href="http://m.kuaidi.com" target="_blank">快递查询</a></div>
</body>
</html>

