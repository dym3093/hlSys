<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
			
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
								onclick="addAction('bigRole!addBigRole.action')" />

							<input type="button" class="button" value="修 改"
								onclick="updateAction('bigRole!modifyBigRole.action','id','id')" />

							<input type="button" class="button" value="删 除"
								onclick="deleteAction('bigRole!deleteBigRole.action','ids','id')" />
							
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
							${role.roleName}
						</td>
						<td>
							${role.roleCode}
						</td>
						<td title="${role.desc}">
							<web:strTruncate field="${role.desc}" length="50" />
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
