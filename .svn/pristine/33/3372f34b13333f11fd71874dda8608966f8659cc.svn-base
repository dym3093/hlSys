<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>

	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="用户管理-添加部门" />
				</td>
				<td class="back">
					<web:back url="org!listOrg.action" />
				</td>
			</tr>
		</table>

		<div class="form-content">
			<form method="post" action="org!saveOrg.action" class="form">
				<web:transfer />
				<table>
					<tr>
						<td class="label">
							<label>
								上级部门：
							</label>
						</td>
						<td>
							<input type="text" value="${parent.name}"
								disabled="disabled">
						</td>
					</tr>

					<tr>
						<td class="label">
							<label>
								<font class="star">*</font>部门名称：
							</label>
						</td>
						<td>
							<input type="text" name="org.name" value="${org.name}"
								maxlength="100" class="name:部门名称---check:!NULL---info:部门名称不能为空!">
						</td>
					</tr>

					<tr>
						<td class="label">
							<label>
								序号：
							</label>
						</td>
						<td>
							<input type="text" name="org.orderCode"
								value="${org.orderCode}" maxlength="2"
								class="check:INT---info:序号必须为整数!">
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
		</div>
	</body>
</html>
