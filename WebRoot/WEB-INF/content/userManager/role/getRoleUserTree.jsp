<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
  <head>
	<title>用户管理</title>
	<script type="text/javascript">
	//function selDepts() {
		//var oidStr='';
		//var divs = $("#deptTree div:[class$='ckbox checked'] input[type='checkbox']");
		//divs.each(function(i,n){
		  //oidStr += n.value+',';
		//});
		//alert(oidStr);
	//}
	</script>
  </head>
  <body>
	<div layoutH="10" style="float:left; display:block; overflow:auto; width:255px;height:400px; border:solid 1px #CCC; line-height:21px; background:#fff">
	  <ul id="deptTree" class="tree collapse">
	  <li><a href="javascript:">角色人员树</a>
	  <ul>
	  <c:forEach items="${role.roleList}" var="role1" varStatus="status">
		<c:if test="${role1.parentId=='0'}">
			<li><a href="javascript:" tname="roleName" tvalue="${role1.id}" onclick="$.bringBack({roleId:'${role1.id}',roleName:'${role1.name}'})"><span>${role1.name}</span></a>
		      <ul>
		        <c:forEach items="${role.userRoleList}" var="userRole1" varStatus="status">
		          <c:if test="${role1.id==userRole1.roleId}">
		          <li><a href="javascript:" tname="userName" tvalue="${userRole1.userId}"><span>${userRole1.userName}</span></a></li>
		          </c:if>
		        </c:forEach>
		        <c:forEach items="${role.roleList}" var="role2" varStatus="status">
				  <c:if test="${role2.parentId==role1.id}">
					<li><a href="javascript:" tname="roleName" tvalue="${role2.id}" onclick="$.bringBack({roleId:'${role2.id}',roleName:'${role2.name}'})"><span>${role2.name}</span></a>
				      <ul>
				        <c:forEach items="${role.userRoleList}" var="userRole2" varStatus="status">
				          <c:if test="${role2.id==userRole2.roleId}">
				          <li><a href="javascript:" tname="userName" tvalue="${userRole2.userId}"><span>${userRole2.userName}</span></a></li>
				          </c:if>
				        </c:forEach>
				        <c:forEach items="${role.roleList}" var="role3" varStatus="status">
						  <c:if test="${role3.parentId==role2.id}">
							<li><a href="javascript:" tname="roleName" tvalue="${role3.id}" onclick="$.bringBack({roleId:'${role3.id}',roleName:'${role3.name}'})"><span>${role3.name}</span></a>
						      <ul>
						        <c:forEach items="${role.userRoleList}" var="userRole3" varStatus="status">
						          <c:if test="${role3.id==userRole3.roleId}">
						          <li><a href="javascript:" tname="userName" tvalue="${userRole3.userId}"><span>${userRole3.userName}</span></a></li>
						          </c:if>
						        </c:forEach>
						      </ul>
							</li>
						  </c:if>
					    </c:forEach>
				      </ul>
					</li>
				  </c:if>
			    </c:forEach>
		      </ul>
			</li>
		  </c:if>
	    </c:forEach>
	  </ul>
	</li>
  </ul>
  </div> 
  </body>
</html>