<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
	</head>
	<body>
		<div clas="navigation">
			<web:location value="基础数据维护-状态规则时间配置" />
		</div>

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
			<table class="grid-table" border="0" cellpadding="0" cellspacing="0">
				<tr class="tr_title">
					<th width="5%">
						<nobr>
							序号
						</nobr>
					</th>
					<th width="14%">
						<nobr>
							状态名称
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							触发方式
						</nobr>
					</th>
					<th width="20%">
						<nobr>
							单据名称
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							时间限制
						</nobr>
					</th>
				</tr>
				<c:forEach items="${page.results}" var="assetStatusRule"
					varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							${status.count} &nbsp;
						</td>
						<td>
							${assetStatusRule.code} &nbsp;
						</td>
						<td>
							<web:ShowDictName identifier="trigger_type"
								code="${assetStatusRule.triggerTypeCode}" />
							&nbsp;
						</td>
						<td>
							<web:ShowDictName identifier="bill"
								code="${assetStatusRule.billCode}" />
							&nbsp;
						</td>
						<td>
							<c:if test="${assetStatusRule.timeLimit!=null}">
							${assetStatusRule.timeLimit}小时后
							<web:detail
									url="${path}/system/assetStatusRule!modifyAssetStatusRule.action?id=${assetStatusRule.id}"
									value="设 置" width="400" height="200" />
							</c:if>
							&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
