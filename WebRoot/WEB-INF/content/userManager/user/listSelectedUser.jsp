<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
function selectUser(userId, userName, userCode) {
	if ('${codeId}' != "null" && '${codeId}' != "") {
		parent.document.getElementById('${codeId}').value = userCode;
	}
	if ('${nameId}' != "null" && '${nameId}' != "") {
		parent.document.getElementById('${nameId}').value = userName;
	}
	if ('${valueId}' != "null" && '${valueId}' != '') {
		parent.document.getElementById('${valueId}').value = userId;
	}
	//判断是否触发父页面的js函数
	if ('${fun}' != "null" && '${fun}' != '') {
		eval('${fun}');
	}
	parent.win.close();
}
</script>
	</head>
	<body>
		<form name="searchForm" class="search_form" method="post"
			action="${path}/um/user!listSelectedUser.action">
			<input type = "hidden" name = "fun" value = "${fun }" />
			<input type = "hidden" name = "codeId" value = "${codeId }" />
			<input type = "hidden" name = "nameId" value = "${nameId }" />
			<input type = "hidden" name = "valueId" value = "${valueId }" />
			<input type = "hidden" name = "emplid" value = "${emplid }" />
			<table>
				<tr>

					<td class="label">
						<label>
							员工编号：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_emplid_LIKE_S"
							value="${filter_and_emplid_LIKE_S}" />
					</td>

					<td class="label">
						<label>
							员工姓名：
						</label>
					</td>
					<td>
						<input type="hidden" name="nameId" value="${nameId}" />
						<input type="hidden" name="valueId" value="${valueId}" />
						<input type="hidden" name="emplid" value="${emplid}" />
						<input type="text" name="filter_and_name_LIKE_S"
							value="${filter_and_name_LIKE_S}" />
					</td>

					<td align="center">
						<input type="hidden" name="_pageNum" value="${page.pageNum}" />
						<input type="submit" class="button" value="查 询" />
						<input type="button" class="button" value="清 空"
							onclick="clearSearchForm(this.form)" />
					</td>
				</tr>
			</table>
		</form>

		<div class="content">
			<table class="handle-table">
				<tr>
					<td class="page-td">
						<web:pager />
					</td>

					<td class="handle-td">

					</td>
				</tr>
			</table>

			<TABLE class="grid-table">
				<thead>
					<tr>
						<th width="5%">
							<nobr>
								序号
							</nobr>
						</th>
						<th width="20%">
							<nobr>
								员工编号
							</nobr>
						</th>
						<th width="20%">
							<nobr>
								员工姓名
							</nobr>
						</th>
						<th width="20%">
							<nobr>
								手机
							</nobr>
						</th>
						<th width="20%">
							<nobr>
								所属部门
							</nobr>
						</th>
					</tr>
				</thead>
				<c:forEach items="${page.results}" var="user" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							${status.count}
							<input type="radio" name="id" id="id" value="${user.id}"
								onclick="selectUser('${user.id}','${user.name}','${user.emplid}')" />
						</td>
						<td>
							${user.emplid}
						</td>

						<td>
							${user.name}
						</td>

						<td>
							${user.phoneMobile}
						</td>

						<td>
							${user.org.name}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
