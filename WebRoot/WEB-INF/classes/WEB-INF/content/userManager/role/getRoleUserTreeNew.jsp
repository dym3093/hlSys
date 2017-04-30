<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

  <div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	  <ul id="roleTrees" class="tree">
	  	<li><a><span>用户选择</span></a>
		  	<ul>
			  	<c:forEach items="${userList }" var="user" varStatus="status">
			  		<li><a href="javascript:" tname="userName" tvalue="${user.accountName},${user.userName}" onclick="$.bringBack({accountName:'${user.accountName}', userName:'${user.userName}'})"><span>${user.userName}</span></a></li>
			  	</c:forEach>
		  	</ul>
	  	</li>
	  </ul>
  </div> 
  <div class="formBar">
	<ul>
		<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="javascript:selUsers();">确认</button></div></div></li>
	</ul>
  </div>
</div>