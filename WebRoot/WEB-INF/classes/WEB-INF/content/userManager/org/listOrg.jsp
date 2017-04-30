<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>

	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="用户管理-部门管理" />
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

						
							<input type="button" class="button" value="添 加 "
								onclick="addAction('org!addOrg.action')" />&nbsp;

							<input type="submit" type="button" class="button" value="修 改"
								onclick="updateAction('${path}/um/org!modifyOrg.action','id','id')" />&nbsp;

							<input type="submit" type="button" class="button" value="删 除"
								onclick="deleteAction('${path}/um/org!deleteOrg.action','ids','id')" />
					</td>

				</tr>
			</table>

			<table class="grid-table">
				<tr class="tr_title">
					<th width="5%">
						序号
					</th>
					<th width="10%">
						<nobr>
							部门编号
						</nobr>
					</th>

					<th width="20%">
						<nobr>
							部门全称
						</nobr>
					</th>


					<th width="15%">
						<nobr>
							地点
						</nobr>
					</th>
				</tr>
				<c:forEach items="${page.results}" var="org" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							${status.count}
							<input type="checkbox" name="id" id="id" value="${org.id}" />
						</td>

						<td>
							<nobr>
								${org.deptid}
							</nobr>
						</td>
						<td>
							<nobr>
								${org.name}
							</nobr>
						</td>

						<td>
							<nobr>
								${org.location}
							</nobr>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
