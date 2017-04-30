<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>

<html>
	<head>

	</head>

	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="系统维护-登录日志" />
				</td>
			</tr>
		</table>

		<form name="searchForm" class="search_form"
			action="loginLog!listLoginLog.action" method="post">
			<table>
				<tr>
					<td class = "label">
						<label>
							姓名：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_userName_LIKE_S" 
							value="${filter_and_userName_LIKE_S}" />
					</td>
					<td class = "label">
						<label>
							所属部门：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_orgName_LIKE_S" 
							value="${filter_and_orgName_LIKE_S}" />
					</td>
					<td class = "label">
						<label>
							登录开始时间：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_loginTime_GE_T"
							class="Wdate" readonly="true" onClick="WdatePicker()"
							value="${filter_and_loginTime_GE_T}" />
					</td>
					<td class = "label">
						<label>
							登录截至时间：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_loginTime_LE_T"
							class="Wdate" readonly="true" onClick="WdatePicker()"
							value="${_loginTime_endTime}" />
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

			<table class="grid-table">
				<tr>
					<th width="5%">
						<nobr>
							序号
						</nobr>
					</th>
					<th width="15%">
						<nobr>
							姓名
						</nobr>
					</th>
					<th width="25%">
						<nobr>
							所属部门
						</nobr>
					</th>
					<th width="20%">
						<nobr>
							登录时间
						</nobr>
					</th>
					<th width="15%">
						<nobr>
							登录人IP地址
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							查看具体操作
						</nobr>
					</th>
				</tr>
				<c:forEach items="${page.results}" var="loginLog" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							<nobr>
								${status.count}
							</nobr>
						</td>
						<td>
							<nobr>
								${loginLog.userName}
							</nobr>
						</td>
						<td>
							${loginLog.orgName}
						</td>
						<td>
							<nobr>
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
									value="${loginLog.loginTime}" />
							</nobr>
						</td>
						<td>
							${loginLog.loginIp}
						</td>
						<td>
							<a
								href="javascript:href('operationLog!listOperationLogForLoginLog.action?pass_loginLogId=${loginLog.id}')">操作日志</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
