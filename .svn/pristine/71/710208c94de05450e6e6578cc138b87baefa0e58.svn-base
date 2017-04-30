<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>
	<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0"
		bottommargin="0" rightmargin="0" style="margin: 0px;">
		<table class="portal-table">
			<tr>
				<th width="60%">
					<nobr>
						事项名称
					</nobr>
				</th>
				<th width="30%">
					<nobr>
						操作
					</nobr>
				</th>
			</tr>

			<c:if test="${auditHireContract!=null}">
				<tr>

					<td>
						${auditHireContract}
					</td>

					<td>
						<a href="javascript:portalActiveTab('${path}/hire/audit!listContractAudit.action','待审核合同管理')">点击审核</a>
					</td>
				</tr>
			</c:if>
			
			
		</table>
	</body>
</html>
