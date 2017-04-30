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
		  <web:back url="dept!listDept.action" />
		</td>
	  </tr>
    </table>
	<div class="form-content">
	 <form id="_form" class="form" cellspacing="1" action="dept!saveDept.action" method="post">
	  <table>
		<tr>
		  <td class="label"><label>上级部门：</label></td>
		  <td>
			<input type="text" value="${parent.deptName}" disabled="disabled">
		  </td>
		</tr>
		<tr>
		  <td class="label"><label><font class="star">*</font>部门名称：</label></td>
		  <td>
		    <input type="text" name="dept.deptName" value=""
			  maxlength="100" class="name:部门名称---check:!NULL---info:部门名称不能为空!">
		  </td>
		</tr>
		<tr>
		  <td class="label"><label>部门编号：</label></td>
		  <td>
			<input type="text" name="dept.deptCode" value="" maxlength="20">
			<input type="hidden" name="dept.parentId" value="${parent.id}"/>
			<input type="hidden" name="dept.deptLevel" value="${parent.deptLevel}"/>
		  </td>
		</tr>
		<tr>
		  <td class="label"><label>部门地址：</label></td>
		  <td>
			<input type="text" name="dept.deptAddress" value="" maxlength="20">
		  </td>
		</tr>
		<tr>
		  <td class="label"><label>排序字段：</label></td>
		  <td>
			<input type="text" name="dept.sortId" value=""  maxlength="2"
								class="check:INT---info:排序字段必须为整数!">
		  </td>
		</tr>
		<tr>
		  <td colspan="2" align="center">
			<input type="button" class="button" value="保 存" onclick="submitForm(this.form)" />
			<input type="reset" class="button" value="重置" />
		  </td>
		</tr>
	  </table>
	</form>
	</div>
  </body>
</html>