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
							业主姓名
						</nobr>
					</th>

					<th width="10%">
						<nobr>
							房屋编号/房源编号
						</nobr>
					</th>

					<th width="10%">
						<nobr>
							合同编号
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							业主状态
						</nobr>
					</th>

					<th width="6%">
						<nobr>
							更新时间
						</nobr>
					</th>
				</tr>
				<c:forEach items="${page.results}" var="ownerStatus"
					varStatus="status">
					<tr <c:if test = "${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							<nobr>
								${status.count}
							</nobr>
						</td>
						<td>
							<nobr>
								${ownerStatus.owner.chName}
							</nobr>
						</td>
						<td>
							<nobr>
								${ownerStatus.house.houseCode}/${ownerStatus.house.houseSourceCode}
							</nobr>
						</td>
						<td>
							<nobr>
								${ownerStatus.contractCode}(
								<web:ShowDictName identifier="contract_type"
									code="${ownerStatus.contractTypeCode}" />
								)
							</nobr>
						</td>
						<td>
							<nobr>
								${ownerStatus.statusCodeStr}
							</nobr>
						</td>
						<td>
							<nobr>
								<fmt:formatDate value="${ownerStatus.createTime}"
									pattern="yyyy-MM-dd HH:mm" />
							</nobr>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>