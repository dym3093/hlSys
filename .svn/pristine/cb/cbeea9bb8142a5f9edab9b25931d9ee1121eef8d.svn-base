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
					<web:location value="系统菜单管理-修改子菜单模块" />
				</td>
				<td class="back">
					<web:back url="resource!listResourceModule.action" />
				</td>
			</tr>
		</table>
		<div class="form-content">
			<form id="_form" class="form" method="post"
				action="resource!updateResource.action">
				<input type="hidden" name = "resource.parentId" value="${parent.id}">
				<input type="hidden" name = "resource.id" value = "${resource.id }" />
				<input type="hidden" name = "resource.type" value = "3" />
				<table>
					<tr>
						<td class="label">
							<label>
								父模块名称：
							</label>
						</td>
						<td>
							<input type="text" value="${parent.name}" readonly="readonly" maxlength="100"
							 />
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								<font color="red">*</font>子模块名称：
							</label>
						</td>
						<td>
							<input type="text" name="resource.name" value="${resource.name }" maxlength="100"
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
							<input type="text" name="resource.orderCode" value="${resource.orderCode }"  lang="check:INT---info:排序码请输入正整数！" maxlength="100"/>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								<font color="red">*</font>URL：
							</label>
						</td>
						<td>
							<input type="text" name="resource.url" value="${resource.url }" maxlength="100" lang="check:!NULL---info:URL不能为空！"/>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								图标：
							</label>
						</td>
						<td>
							<input type="text" name="resource.iconCls" value="${resource.iconCls }" maxlength="100" />
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
								<option value="1" <c:if test="${resource.isEnable == 1 }"> selected="selected"</c:if>>是</option>
								<option value="0" <c:if test="${resource.isEnable == 0 }"> selected="selected"</c:if>>否</option>
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
								<option value="1" <c:if test="${resource.isButton == 1 }">selected</c:if> >是</option>
								<option value="0" <c:if test="${resource.isButton == 0 }">selected</c:if>>否</option>
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
							<select name="resource.isLeaf" id = "resource.isLeaf"  lang="check:!NULL---info:是否是叶子节点不能为空！">
								<option value="1" <c:if test="${resource.isLeaf == 1 }"> selected="selected"</c:if>>是</option>
								<option value="0" <c:if test="${resource.isLeaf == 0 }"> selected="selected"</c:if>>否</option>
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
								<option value="0" <c:if test="${resource.isFrame == 0 }">selected</c:if> >否</option>
								<option value="1" <c:if test="${resource.isFrame == 1 }">selected</c:if>>是</option>
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