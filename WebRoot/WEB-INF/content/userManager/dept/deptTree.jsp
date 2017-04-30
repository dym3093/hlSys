<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
  <head>
	<title>部门管理</title>
	<script type="text/javascript">
	function selDept(x,y) {
	  document.getElementById("parentId").value=x;
	  document.getElementById("mainFrame").src='<%=request.getContextPath()%>/um/dept!listDept.action?deptId='+x+'&level='+y;
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
			  <li><a href="javascript:" onclick="selDept(0,1);">部门树</a>
			  <ul>
			  <c:forEach items="${dept.deptList}" var="dept1" varStatus="status">
				<c:if test="${dept1.deptLevel==1}">
					<li><a href="javascript:" onclick="selDept('${dept1.id}',2);"><span>${dept1.deptName}</span></a>
				      <ul>
				        <c:forEach items="${dept.deptList}" var="dept2" varStatus="status">
						  <c:if test="${dept2.deptLevel==2 && dept2.parentId==dept1.id}">
							<li><a href="javascript:" onclick="selDept('${dept2.id}',3);"><span>${dept2.deptName}</span></a>
						      <ul>
						        <c:forEach items="${dept.deptList}" var="dept3" varStatus="status">
								  <c:if test="${dept3.deptLevel==3 && dept3.parentId==dept2.id}">
									<li><a href="javascript:" onclick="selDept('${dept3.id}',4);"><span>${dept3.deptName}</span></a>
								      <ul>
								        <c:forEach items="${dept.deptList}" var="dept4" varStatus="status">
										  <c:if test="${dept4.deptLevel==4 && dept4.parentId==dept3.id}">
											<li><a href="javascript:" onclick="selDept('${dept4.id}',5);"><span>${dept4.deptName}</span></a></li>
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
		<div id="center-div" class="unitBox" style="margin-left:246px;">
		  <iframe id="mainFrame" name="mainFrame" boder="0" height="500" width="100%"
				src="<%=request.getContextPath()%>/um/dept!listDept.action" ></iframe>
        </div>
	  </div>
	</div>
	</div>
	</div>
  </body>
</html>