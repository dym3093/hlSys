<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
			function selectOrg(userId,userName) {
				parent.document.getElementById('${nameId}').value = userName;
				if ('${valueId}' != "null") {
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

		<div class="content">
		<form name="searchForm" class="search_form" method="post"
			action="${path}/um/org!listSelectedOrg.action">
			<input type = "hidden" name = "nameId" value = "${nameId }"/>
			<input type = "hidden" name = "valueId" value = "${valueId }"/>
			<input type = "hidden" name = "fun" value = "${fun }"/>
			<table>
				<tr>

					<td class="label">
						<label>
							部门编号：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_deptid_LIKE_S"
							value="${filter_and_deptid_LIKE_S}" />
					</td>

					<td class="label">
						<label>
							部门名称：
						</label>
					</td>
					<td>
						<input type="hidden" name="nameId" value="${nameId}" />
						<input type="hidden" name="valueId" value="${valueId}" />
						<input type="text" name="filter_and_descrshort_LIKE_S"
							value="${filter_and_descrshort_LIKE_S}" />
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
			<table class="handle-table">
				<tr>
					<td class="page-td">
						<web:pager />
					</td>
					<td class="handle-td">
					</td>

				</tr>
			</table>
			</table>
			<table class="grid-table">
				<tr class="tr_title">
					<th width="5%">
						序号
					</th>
					<th width="30%">
						部门编号
					</th>
					<th width="30%">
						部门名称
					</th>
				</tr>
				<c:forEach items="${page.results}" var="org" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							${status.count}
							<input type="radio" name="id" id="id" value="${org.id}" onclick="selectOrg('${org.deptid}','${org.descrshort}')"/>
						</td>
						<td>
							${org.deptid}
						</td>
						<td>
							${org.descrshort}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
