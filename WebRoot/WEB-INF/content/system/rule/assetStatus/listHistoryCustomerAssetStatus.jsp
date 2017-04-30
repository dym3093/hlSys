<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
	</head>
	<body>
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

			<table class="grid-table" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="tr_title">
					<th width="5%">
						<nobr>
							序号
						</nobr>
					</th>
					<th width="6%">
						<nobr>
							客户姓名
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							合同编号
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							客户状态
						</nobr>
					</th>

					<th width="6%">
						<nobr>
							更新时间
						</nobr>
					</th>
				</tr>
				<c:forEach items="${page.results}" var="customerStatus"
					varStatus="status">
					<tr <c:if test = "${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							<nobr>
								${status.count}
							</nobr>
						</td>
						<td>
							<nobr>
								${customerStatus.customer.chName}
							</nobr>
						</td>
						<td>
							<nobr>
								${customerStatus.contractCode}(
								<web:ShowDictName identifier="contract_type"
									code="${customerStatus.contractTypeCode}" />
								)
							</nobr>
						</td>
						<td>
							<nobr>
								${customerStatus.statusCodeStr}
							</nobr>
						</td>
						<td>
							<nobr>
								<fmt:formatDate value="${customerStatus.createTime}"
									pattern="yyyy-MM-dd HH:mm" />
							</nobr>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>