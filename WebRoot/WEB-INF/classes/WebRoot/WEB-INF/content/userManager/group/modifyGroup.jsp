<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
	<head>
		<title>修改角色</title>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/scripts/CheckForm.js">
</script>
	</head>

	<body>

		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="用户管理-修改用户组" />
				</td>
				<td class="back">
					<web:back url="group!listGroup.action" />
				</td>
			</tr>
		</table>
		<form action="group!updateGroup.action?group.id=${group.id}"
			method="post" id="_form" class="form">
			<table width="100%">
				<tr>
					<td width="30%">
						<label>
							<font class="star">*</font>用户组名称：
						</label>
					</td>
					<td>
						<input type="text" name="group.name" value="${group.name}"
							size="40" maxlength="50"
							lang="check:!NULL---info:用户组名称不能为空！">
					</td>
				</tr>

				<tr>
					<td>
						<label>
							序号：
						</label>
					</td>
					<td>
						<input type="text" size="40" name="group.orderCode"
							value="${group.orderCode}" maxlength="2"
							lang="check:INT---info:序号必须为整数!">
					</td>
				</tr>

				<tr>
					<td>
						<label>
							说明：
						</label>
					</td>
					<td>
						<textarea rows="10" cols="55" name="group.description">${group.description}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" class="button" value="保 存"
							onclick="submitForm(this.form)" />
						<input type="reset" class="button" value="重置" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
