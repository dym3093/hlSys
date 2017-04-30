<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

  <div class="pageContent" >
	<div class="pageFormContent" layoutH="25" >
		 <ul class='tree treeFolder'>
			<c:forEach items="${userList}" var="user" varStatus="status">
	          <li><a href="javascript:" onclick="$.bringBack({deptId:'${user.deptId}',accountName:'${user.accountName}',userName:'${user.userName}',mobile:'${user.mobile}',extension:'${user.extension}',deptName:'${user.deptName}'})"><span>${user.userName}</span></a></li>
	        </c:forEach>
      </ul>
	</div> 
	</div>

	