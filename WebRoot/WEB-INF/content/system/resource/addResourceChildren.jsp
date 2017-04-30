<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		
		<script type="text/javascript">
			function loadLeafCheck(obj){
				var isLeaf = document.getElementById("resource.isLeaf") ;
				if(obj.value == 1){
					//当前连接是按钮
					isLeaf.value = 1 ;
				}else{
					isLeaf.value = "" ;
				}
			}
		</script>
		
	</head>
	<body>

		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="系统菜单管理-添加子菜单模块(只有叶子节点上才可以附加按钮！)" />
				</td>
				<td class="back">
					<web:back url="resource!listResourceChildren.action" />
				</td>
			</tr>
		</table>
		<div class="form-content">
			<form id="_form" class="form" method="post"
				action="resource!saveResourceChildren.action">
				<input type="hidden" name = "resource.parentId" value="${parent.id}">
				<table>
					<tr>
						<td class="label">
							<label>
								父模块名称：
							</label>
						</td>
						<td>
							<input type="text" value="${parent.name}" disabled="disabled" maxlength="100"/>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								<font color="red">*</font>子模块名称：
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
							<input type="text" name="resource.orderCode" lang="check:INT---info:排序码请输入正整数！" maxlength="100"/>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								<font color="red">*</font>URL：
							</label>
						</td>
						<td>
							<input type="text" name="resource.url" maxlength="100" lang="check:!NULL---info:URL不能为空！"/>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								图标：
							</label>
						</td>
						<td>
							<input type = "hidden" name="resource.iconCls" value = "leafMenuIcon">
							<input type="text" value="leafMenuIcon" disabled="disabled" maxlength="100"/>
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
						<td class="label">
							<label>
								是否是按钮：
							</label>
						</td>
						<td>
							<select name="resource.isButton" id = "resource.isButton" onchange="loadLeafCheck(this)" lang="check:!NULL---info:请选择是否是按钮！">
								<option></option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								<font color="red">*</font>是否叶子节点：
							</label>
						</td>
						<td>
							<select name="resource.isLeaf" id = "resource.isLeaf" lang="check:!NULL---info:是否是叶子节点不能为空！">
								<option></option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								是否在frame中展现：
							</label>
						</td>
						<td>
							<select name="resource.isFrame">
								<option value="0">否</option>
								<option value="1">是</option>
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