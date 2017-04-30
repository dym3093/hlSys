<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
	</head>
	
<script type="text/javascript">
	var win;
	function grant(roleId) {
		var roleId = getCheckId('id');
		if (null != roleId) {
			window.open('${path}/assign/assignAction!showGrantResource.action?tag=1&roleId=' + roleId,'newwindow','height=500, width=800,toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
		}
	}
</script>
	
	<body>

		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="角色授权管理-用户管理" />
				</td>
			</tr>
		</table>
		<form name="searchForm" id="_form" class="search_form" method="post"
			action="#">
			<table>
				<tr>
					<td>
						<label>
							用户姓名：
						</label>
					</td>
					<td>
						<input type="hidden" name="pass_orgId" value="${pass_orgId}" />
						<input type="hidden" name="nameId" value="${nameId}" />
						<input type="hidden" name="valueId" value="${valueId}" />
						<input type="text" size="16" name="filter_and_name_LIKE_S"
							value="${filter_and_name_LIKE_S}" />
					</td>
					<td>
						<label>
							用户编号：
						</label>
					</td>
					<td>
						<input type="text" size="16" name="filter_and_emplid_LIKE_S"
							value="${filter_and_emplid_LIKE_S}" />
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
								姓名
							</nobr>
						</th>
						<th width="20%">
							<nobr>
								用户编号
							</nobr>
						</th>
						<th width="15%">
							<nobr>
								状态
							</nobr>
						</th>
						<th width="15%">
							<nobr>
								操作
							</nobr>
						</th>
					</tr>
				</thead>
				<c:forEach items="${page.results}" var="user" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							${status.count}
							<input type="checkbox" name="id" id="id" value="${user.id}" />
						</td>
						<td>
							${user.name}
						</td>
						<td>
							${user.username}
						</td>
						<td>
							<web:showYesNoName value="${user.isEnable}" aliasYes="启用"
								aliasNo="停用" />
						</td>
						<td>
							<nobr>
								<web:detail
									url="${path}/um/user!detailUser.action?id=${user.id}&orgId=${org.id}"
									value="查看" height="350" width="550"></web:detail>
							</nobr>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
