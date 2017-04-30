<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

  <div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	  <ul id="roleTrees" class="tree">
	  <c:forEach items="${role.roleList}" var="role1" varStatus="status">
		<c:if test="${role1.parentId ne '0'}">
			<li><a href="javascript:" tname="roleName" tvalue="0,${role1.name}")"><span>${role1.name}</span></a>
		      <ul>
		        <c:forEach items="${role.userRoleList}" var="userRole1" varStatus="status">
		          <c:if test="${role1.id==userRole1.roleId}">
		          <li><a href="javascript:" tname="userName" tvalue="${userRole1.accountName},${userRole1.userName}" onclick="$.bringBack({accountName:'${userRole1.accountName}', userName:'${userRole1.userName}'})"><span>${userRole1.userName}</span></a></li>
		          </c:if>
		        </c:forEach>
		      </ul>
			</li>
		  </c:if>
	    </c:forEach>
	  </ul>
  </div> 
  <div class="formBar">
	<ul>
		<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="javascript:selUsers();">确认</button></div></div></li>
	</ul>
  </div>
</div>