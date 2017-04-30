<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
function submitForm(_form) {
	if (CheckFormFunction(_form)) {
		_form.submit();
	}
}
</script>
	</head>
	<body>

		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="基础数据管理-状态规则配置  " />
				</td>
			</tr>
		</table>

		<div class="form">
			<form method="post"
				action="assetStatusRule!updateAssetStatusRule.action" class="form">
				<table>
					<input type="hidden" name="assetStatusRule.id"
						value="${assetStatusRule.id}" />
					<tr>
						<td>
							<nobr>
								<label>
									<font color="red">*</font>时间限制：
								</label>
							</nobr>
						</td>
						<td>
							<input type="text" size="22" name="assetStatusRule.timeLimit"
								value="${assetStatusRule.timeLimit}" maxlength="5"
								lang="check:INT---info:时间限制只能为整数" />
							小时
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
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