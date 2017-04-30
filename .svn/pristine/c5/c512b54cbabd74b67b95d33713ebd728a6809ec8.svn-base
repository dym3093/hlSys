<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
	</head>
	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="库存管理-客户状态" />
				</td>
			</tr>
		</table>
		<form name="searchForm" class="search_form" method="post"
			action="assetStatus!listCustomerAssetStatus.action">
			<table>
				<tr>
					<td class="label">
						<label>
							客户编号：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_customer__customerCode_LIKE_S"
							 value="${filter_and_customer__customerCode_LIKE_S}" />
					</td>

					<td class="label">
						<label>
							客户姓名：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_customer__chName_LIKE_S"
							 value="${filter_and_customer__chName_LIKE_S}" />
					</td>

					<td class="label">
						<label>
							合同编号：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_contractCode_LIKE_S" 
							value="${filter_and_contractCode_LIKE_S}" />
					</td>
					
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="label">
						<label>
							状态类型：
						</label>
					</td>
					<td>
						<web:dictionarySelect name="filter_and_statusCode_LIKE_S"
							hasDefault="true"
							value="${filter_and_statusCode_LIKE_S}"
							identifier="asset_status1" />
					</td>
					<td class="label">
						<label>
							更新开始时间：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_createTime_GE_T" 
							value="${filter_and_createTime_GE_T}" class="Wdate"
							readonly="true" onClick="WdatePicker()" />
					</td>
					<td class="label">
						<label>
							更新结束时间：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_createTime_LE_T"
							value="${filter_and_createTime_LE_T}" class="Wdate"
							readonly="true" onClick="WdatePicker()" />
					</td>
				</tr>
				<tr>
					<td align="right" colspan="6">
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
							客户编号
						</nobr>
					</th>
					<th width="6%">
						<nobr>
							客户姓名
						</nobr>
					</th>
					<th width="6%">
						<nobr>
							客户手机
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
					
					<th width="6%">
						<nobr>
							历史版本
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
								${customerStatus.customer.customerCode}
							</nobr>
						</td>
						<td>
							<nobr>
								${customerStatus.customer.chName}
							</nobr>
						</td>
						<td>
							<nobr>
								${customerStatus.customer.mobile}
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

						<td>
							<nobr>
								<web:detail 
									url="assetStatus!listHistoryCustomerAssetStatus.action?customerId=${customerStatus.customerId}"
									value="查看" isParent="true" />
							</nobr>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>