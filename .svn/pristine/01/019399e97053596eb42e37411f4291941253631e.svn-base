<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>
	<body>

		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="系统菜单管理-添加菜单模块" />
				</td>
				<td class="back">
					<web:back url="resource!listResourceModule.action" />
				</td>
			</tr>
		</table>
		<div class="form-content">
			<form id="_form" class="form" method="post"
				action="resource!saveResource.action">
				<input type="hidden" name = "resource.parentId" value="1">
				<input type = "hidden" name = "resource.isLeaf" value = "0">
				<table>
					<tr>
						<td class="label">
							<label>
								<font color="red">*</font>模块名称：
							</label>
						</td>
						<td>
							<input type="text" name="resource.name" maxlength="100"
								lang="check:!NULL---info:模板名称不能为空！" />
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								排序：
							</label>
						</td>
						<td>
							<input type="text" name="resource.orderCode" maxlength="100" />
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								URL：
							</label>
						</td>
						<td>
							<input type="text" name="resource.url" maxlength="100" />
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								图标：
							</label>
						</td>
						<td>
							<input type="text" name="resource.iconCls" maxlength="100" />
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								是否可用：
							</label>
						</td>
						<td>
							<select name="resource.isEnable">
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
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