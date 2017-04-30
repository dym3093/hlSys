<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
	
</script>
	</head>
	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="系统菜单管理-子菜单管理" />
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
						
						<input type="button" class="button" value="添 加"
							onclick="addAction('resource!addResourceChildren.action?parentId=${parentId}')" />
						
						<input type="button" class="button" value="修 改"
							onclick="updateAction('resource!modifyResourceChildren.action?parentId=${parentId}','id','id')" />
						
						<input type="button" class="button" value="更改状态"
							onclick="updateAction('resource!deleteProduct.action','ids','id')" />
						
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
					<th width="20%">
						<nobr>
							菜单名称
						</nobr>
					</th>
					<th width="50%">
						<nobr>
							URL
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							是否启用
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							排序
						</nobr>
					</th>
					<th width="55%">
						<nobr>
							图标
						</nobr>
					</th>
				</tr>
				<c:forEach items="${page.results}" var="resource"
					varStatus="status">
					<tr>
						<td class="no_td">
							<nobr>
								${status.count }
								<input type="checkbox" value="${resource.id }" name="id"
									id="id" />
							</nobr>
						</td>
						<td>
							<nobr>
								${resource.name}
							</nobr>
						</td>
						<td>
							<nobr>
								${resource.url}
							</nobr>
						</td>
						<td>
							<nobr>
								${resource.isEnable==1?'是':'否'}
							</nobr>
						</td>
						<td>
							<nobr>
								${resource.orderCode}
							</nobr>
						</td>
						<td>
							<nobr>
								${resource.iconCls}
							</nobr>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>