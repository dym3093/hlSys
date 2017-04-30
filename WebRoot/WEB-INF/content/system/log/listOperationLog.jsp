<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>登录日志管理</title>
	</head>

	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="系统维护-操作日志" />
				</td>
			</tr>
		</table>

		<form name="searchForm" class="search_form" method="post"
			action="operationLog!listOperationLog.action">
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
							操作开始时间：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_operationDate_GE_T"
							class="Wdate" readonly="true" onClick="WdatePicker()"
							value="${filter_and_operationDate_GE_T}" />
					</td>
					<td class = "label">
						<label>
							操作截至时间：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_operationDate_LE_T"
							class="Wdate" readonly="true" onClick="WdatePicker()"
							value="${filter_and_operationDate_LE_T}" />
					</td>
				</tr>
				<tr>
					<td class = "label">
						<label>
							业务名称：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_businessName_LIKE_S"
							value="${filter_and_businessName_LIKE_S}" />
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
				<tr>
					<th width="5%">
						<nobr>
							序号
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							姓名
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							所属部门
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							IP地址
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							业务名称
						</nobr>
					</th>
					<th width="15%">
						<nobr>
							操作名称
						</nobr>
					</th>
					<th width="15%">
						<nobr>
							操作时间
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							是否成功
						</nobr>
					</th>
				</tr>
				<c:forEach items="${page.results}" var="operationLog"
					varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							<nobr>
								${status.count}
							</nobr>
						</td>
						<td>
							<nobr>
								${operationLog.userName}
							</nobr>
						</td>
						<td>
							${operationLog.orgName}
						</td>
						<td>
							${operationLog.loginLog.loginIp}
						</td>
						<td>
							${operationLog.businessName}
						</td>
						<td>
							${operationLog.operationName}
						</td>
						<td>
							<nobr>
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
									value="${operationLog.operationTime}" />
							</nobr>
						</td>
						<td>
							<web:showYesNoName value="${operationLog.isSuccess}"
								aliasYes="成功" aliasNo="失败" />
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
