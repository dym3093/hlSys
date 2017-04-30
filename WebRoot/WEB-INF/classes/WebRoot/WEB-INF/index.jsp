<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	session.setAttribute("path",path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="" />
<title>远盟基因平台</title>
<link href="${path}/dwz/themes/blue/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${path}/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${path}/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${path}/dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${path}/dwz/themes/css/customer.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
<style type="text/css">
	#header{height:56px}
	#leftside, #container, #splitBar, #splitBarProxy{top:56px;}
</style>

<!--[if lte IE 9]>
<script src="js/speedup.js" type="text/javascript"></script>
<![endif]-->
<script src="${path}/dwz/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${path}/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${path}/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="${path}/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${path}/dwz/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
<script src="${path}/dwz/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>
<script src="${path}/scripts/DatePicker/WdatePicker.js"></script>
<script src="${path}/scripts/jquery.iselect.js"></script>
<script src="${path}/dwz/bin/dwz.min.js" type="text/javascript"></script>
<script src="${path}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#navMenu ul li:first").addClass("selected");
	$.post($("#navMenu ul li:first>a").attr("href"), {}, function(html){
		$("#sidebar").append(html);
	},"html");
	$("#navMenu ul li:first").trigger("click");
	$("#navMenu ul li:first>a").trigger("click");
	DWZ.init("${path}/dwz/dwz.frag.xml", {
		loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
//		statusCode:{ok:200, error:300, timeout:301}, //【可选】
//		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
//		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${path}/dwz/themes"});
			//setTimeout(function() {$("#sidebar .toggleCollapse div").trigger("click");}, 10);
		}
	});
	
});

function xgmm() {
	$.pdialog.open("${path}/security/security!showUpdatePassword.action", "", "修改密码",  {width:400,height:280,mask:true,mixable:true,minable:true,resizable:true,close:"function"});
}

</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
		
					<ul class="nav">
					<li id="switchEnvBox" >
					<a href="javascript:" onclick="xgmm()">欢迎您:<span>${currentUser.userName} </span></a></li><!--  ${currentUser.ctiPassword } -->
				  	<li class="abortBtn" ><a href="${path }/security/security!logout.action" id="logout">退出</a></li>
				</ul>
			</div>
		
			<div id="navMenu">
				<ul>
					<c:forEach items="${currentUser.resourceList}" var="resource" varStatus="status">
						<c:if test="${resource.type==1}">
							<li><a href="${pageContext.request.contextPath }/security/security!getNextResources.action?parentId=${resource.id }"><span>${resource.name}</span></a></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<!--  <a id = "openNewWin" href="" target="navTab" rel="page"></a>-->
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					    <div class="page unitBox">
							<div>
							
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	<div id="footer"> <a href="demo_page2.html" target="dialog"> 远盟康健科技（北京）有限公司</a></div>


  </body>
</html>

