<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
		var win;
		function grant(roleId) {
		  var roleId = getCheckId('id');
		  if (null != roleId) {
			window.open('${path}/um/role!showGrantResource.action?roleId=' + roleId,'newwindow','height=500, width=800,toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
		  }
	     }

		$(document).ready(function(){
	  		if("${flush}" =="true"){
	  			parent.navTab.reload();
	  		}
	      });
		</script>
	</head>
	<body>
		<div clas="navigation">
			<web:location value="用户管理-角色管理" />
		</div>

		<div class="content">
			<table class="handle-table">
				<tr>
					<td class="page-td">
						<web:pager />
					</td>
					<td class="handle-td">

							<input type="button" class="button" value="新 增"
								onclick="addAction('role!addRole.action?parentId=${parentId }')" />

							<input type="button" class="button" value="修 改"
								onclick="updateAction('role!modifyRole.action','id','id')" />

							<input type="button" class="button" value="删 除"
								onclick="deleteAction('role!deleteRole.action','ids','id')" />

							<input type="button" class="button" value="授 权" onclick="grant()" />
						
						<c:if test="${parentId=='100'}">
						<input type="button" class="button" value="子角色管理" onclick="updateAction('role!listRole.action','parentId','id');" />
						</c:if>
					</td>

				</tr>
			</table>
			<table class="grid-table">
				<tr class="tr_title">
					<th width="2%">
						<nobr>
							序号
						</nobr>
					</th>
					<th width="35%">
						<nobr>
							角色名称
						</nobr>
					</th>
					<th width="15%">
						<nobr>
							角色编码
						</nobr>
					</th>
					<th width="35%">
						<nobr>
							描述
						</nobr>
					</th>
				</tr>
				<c:forEach items="${page.results}" var="role" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td>
							${status.count}
							<input type="checkbox" name="id" id="id" value="${role.id}" />
						</td>
						<td>
							${role.name}
						</td>
						<td>
							${role.code}
						</td>
						<td title="${role.description}">
							<web:strTruncate field="${role.description}" length="50" />
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
