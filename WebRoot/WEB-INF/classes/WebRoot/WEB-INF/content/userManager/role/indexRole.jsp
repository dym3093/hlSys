<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
  <head>
	<title>角色管理</title>
	<script type="text/javascript">
	function selRole(x) {
	  document.getElementById("parentId").value=x;
	  //document.getElementById("mainFrame").src=;
	  var url = "<%=request.getContextPath()%>/um/role!listRole.action?parentId="+x ; 
	  document.getElementById("mainFrame2").src = url ;
	  
	}
	</script>
  </head>
  <body>
	<div class="pageContent">
	  <div class="tabs">
		<div class="tabsContent">
		  <div><input type="hidden" name="parentId" id="parentId" value="0">
		  <!-- deptTree -->
			<div layoutH="10" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
			  <ul class="tree treeFolder">
			  <li><a href="javascript:" onclick="selRole(0);">角色树</a>
			  <ul>
			  <c:forEach items="${role.roleList}" var="role1" varStatus="status">
				<c:if test="${role1.parentId eq '0'}">
					<li><a href="javascript:" onclick="selRole('${role1.id}');"><span>${role1.name}</span></a>
				      <ul>
				        <c:forEach items="${role.roleList}" var="role2" varStatus="status">
						  <c:if test="${role1.id eq role2.parentId}">
							<li><a href="javascript:" onclick="selRole('${role2.id}');"><span>${role2.name}</span></a>
							  <ul>
						        <c:forEach items="${role.roleList}" var="role3" varStatus="status">
								  <c:if test="${role2.id eq role3.parentId}">
									<li><a href="javascript:" onclick="selRole('${role3.id}');"><span>${role3.name}</span></a></li>
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
		<div id="center-div" class="unitBox" style="margin-left:246px;">
		  <iframe id="mainFrame2" name="mainFrame" boder="0" height="500" width="100%"
				src="<%=request.getContextPath()%>/um/role!listRole.action" ></iframe>
        </div>
	  </div>
	</div>
	</div>
	</div>
  </body>
</html>