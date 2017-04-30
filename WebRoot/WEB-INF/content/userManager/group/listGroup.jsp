<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>
	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="用户管理-用户组管理" />
				</td>
			</tr>
		</table>


		<div class="content">
			<table class="handle-table">
				<tr>
					<td class="page-td">
						<web:pager />
					</td>
					<td class="handle-td">
						<web:security tag="addGroup">
							<input type="button" class="button6" value="添 加 用 户 组"
								onclick="addAction('group!addGroup.action')" />
						</web:security>
						<web:security tag="modifyGroup">
							<input type="submit" type="button" class="button" value="修 改" onclick="updateAction('group!modifyGroup.action','id','id')"/>
						</web:security>
						<web:security tag="deleteGroup">
							<input type="submit" type="button" class="button" value="删 除" onclick="deleteAction('group!deleteGroup.action','ids','id')"/>
						</web:security>
					</td>
				</tr>
			</table>
			<TABLE class="grid-table">
				<tr>
					<th width="5%">
						<nobr>
							序号<input type="checkbox" title="全选" onClick="checkAll(this,'id')" />
						</nobr>
					</th>
					<th width="40%">
						<nobr>
							用户组名称
						</nobr>
					</th>
					<th>
						<nobr>
							描述
						</nobr>
					</th>

				</tr>
				<c:forEach items="${page.results}" var="group" varStatus="status">
					<tr>
							<td class="no_td">
							${status.count}<input type="checkbox" name="id" id="id" value="${group.id}" />
						</td>
						<td>
							${group.name}
						</td>
						<td title="${group.description}">
							<web:strTruncate field="${group.description}" length="100" />
						</td>

					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
