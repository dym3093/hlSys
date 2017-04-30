<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
  <head>
	<title>用户管理</title>
	<script type="text/javascript">
	function selDepts() {
		var oidStr='';
		var divs = $("#deptTree div:[class$='ckbox checked'] input[type='checkbox']");
		divs.each(function(i,n){
		  oidStr += n.value+',';
		});
		//alert(oidStr);
	}
	</script>
  </head>
  <body>
	<div layoutH="10" style="float:left; display:block; overflow:auto; width:255px;height:400px; border:solid 1px #CCC; line-height:21px; background:#fff">
	  <ul id="deptTree" class="tree collapse">
	  <li><a href="javascript:">部门人员树</a>
	  <ul>
	    <c:forEach items="${dept.deptList}" var="dept1" varStatus="status">
		  <c:if test="${dept1.deptLevel==1}">
			<li><a href="javascript:" tname="deptName" tvalue="${dept1.id}"><span>${dept1.deptName}</span></a>
		      <ul>
		        <c:forEach items="${dept.userList}" var="user1" varStatus="status">
		          <c:if test="${dept1.id==user1.deptId}">
		          <li><a href="javascript:" tname="userName" tvalue="${user1.accountName}" onclick="$.bringBack({deptId:'${dept1.id}',accountName:'${user1.accountName}',deptName:'${dept1.deptName}', userName:'${user1.userName}'})"><span>${user1.userName}</span></a></li>
		          </c:if>
		        </c:forEach>
		        <c:forEach items="${dept.deptList}" var="dept2" varStatus="status">
				  <c:if test="${dept2.deptLevel==2 && dept2.parentId==dept1.id}">
					<li><a href="javascript:" tname="deptName" tvalue="${dept2.id}"><span>${dept2.deptName}</span></a>
				      <ul>
				        <c:forEach items="${dept.userList}" var="user2" varStatus="status">
				          <c:if test="${dept2.id==user2.deptId}">
				          <li><a href="javascript:" tname="userName" tvalue="${user2.accountName}" onclick="$.bringBack({deptId:'${dept2.id}',accountName:'${user2.accountName}',deptName:'${dept2.deptName}', userName:'${user2.userName}'})"><span>${user2.userName}</span></a></li>
				          </c:if>
				        </c:forEach>
				        <c:forEach items="${dept.deptList}" var="dept3" varStatus="status">
						  <c:if test="${dept3.deptLevel==3 && dept3.parentId==dept2.id}">
							<li><a href="javascript:" tname="deptName" tvalue="${dept3.id}"><span>${dept3.deptName}</span></a>
						      <ul>
						        <c:forEach items="${dept.userList}" var="user3" varStatus="status">
						          <c:if test="${dept3.id==user3.deptId}">
						          <li><a href="javascript:" tname="userName" tvalue="${user3.accountName}" onclick="$.bringBack({deptId:'${dept3.id}',accountName:'${user3.accountName}',deptName:'${dept3.deptName}', userName:'${user3.userName}'})"><span>${user3.userName}</span></a></li>
						          </c:if>
						        </c:forEach>
				              	<c:forEach items="${dept.deptList}" var="dept4" varStatus="status">
								  <c:if test="${dept4.deptLevel==4 && dept4.parentId==dept3.id}">
									<li><a href="javascript:" tname="deptName" tvalue="${dept4.id}"><span>${dept4.deptName}</span></a>
								      <ul>
								        <c:forEach items="${dept.userList}" var="user4" varStatus="status">
								          <c:if test="${dept4.id==user4.deptId}">
								          <li><a href="javascript:" tname="userName" tvalue="${user4.accountName}" onclick="$.bringBack({deptId:'${dept4.id}',accountName:'${user4.accountName}',deptName:'${dept4.deptName}', userName:'${user4.userName}'})"><span>${user4.userName}</span></a></li>
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
		  </c:if>
	    </c:forEach>
	  </ul>
	</li>
  </ul>
  </div> 
  </body>
</html>