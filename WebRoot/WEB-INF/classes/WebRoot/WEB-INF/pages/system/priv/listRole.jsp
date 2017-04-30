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
		/*
		win = showWindow('应用授权', 300, 450,
				'${path}/um/role!showGrantResource.action?roleId=' + roleId,
				false, true);
		win.show();
		*/
		window.open('${path}/assign/assignAction!showGrantResource.action?tag=2&roleId=' + roleId,'newwindow','height=500, width=800,toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
	}
}
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
						<web:security tag="grant_role">
							<input type="button" class="button" value="授 权" onclick="grant()" />
						</web:security>
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
							<web:detail width="830"
									url="${path}/assign/assignAction!listUserDetail.action?roleId=${role.id}"
									value="${role.name}" />
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
