<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script src="<%=request.getContextPath()%>/scripts/CheckForm.js">
</script>
	</head>

	<body>

		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="系统管理-修改参数" />
				</td>
				<td class="back">
					<web:back url="role!listRole.action" />
				</td>
			</tr>
		</table>

		<form action="parameterConfig!updateParameterConfig.action"
			method="post" id="_form" class="form">
			<input type="hidden" name="parameterConfig.id"
				value="${parameterConfig.id}">
			<table>
				<tr>
					<td class="label">
						<label>
							<font class="star">*</font>参数名称：
						</label>
					</td>
					<td>
						<input type="text" name="parameterConfig.name" 
							value="${parameterConfig.name}" maxlength="50"
							lang="check:!NULL---info:参数名称不能为空！">
					</td>
				</tr>

				<tr>
					<td class="label">
						<label>
							<font class="star">*</font>参数值：
						</label>
					</td>
					<td>
						<input type="text" name="parameterConfig.value"
							value="${parameterConfig.value}"  maxlength="50"
							lang="check:!NULL---info:参数值不能为空！">
					</td>
				</tr>

				<tr>
					<td class="label">
						<label>
							参数描述：
						</label>
					</td>
					<td>
						<textarea rows="10" cols="55" name="parameterConfig.description">${parameterConfig.description}</textarea>
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
