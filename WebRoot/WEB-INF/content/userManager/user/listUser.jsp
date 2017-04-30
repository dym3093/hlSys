<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
		function addUser(){
	        var id = parent.document.getElementById("parentId").value;
	        location.href='<%=request.getContextPath()%>/um/user!addUser.action?deptId='+id;
	    }
		/**
		*修改页面跳转;
		*/
		function updateUser() {
			var userId = "";
			var count = 0;
			$('input:checkbox[name="id"]:checked').each(function(i, n) {
				userId = n.value;
				count ++;
			});
			if (count == 0) {
				alert('请选择你要变更的信息！');
				return;
			} else if (count > 1) {
				alert('只能选择一条信息进行变更！');
				return;
			}
			location.href='<%=request.getContextPath()%>/um/user!modifyUser.action?id='+userId;
		}
		
		</script>
	</head>
	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="用户管理-用户管理" />
				</td>
			</tr>
		</table>
		<form name="searchForm" class="search_form" method="post"
			action="${path}/um/user!listUser.action">
			<input type="hidden" name="navTabId" value="${ navTabId }"/>
			<table>
				<tr>
					<td class="label">
						<label>
							用户姓名：
						</label>
					</td>
					<td>
						<input type="hidden" name="pass_orgId" value="${pass_orgId}" />
						<input type="hidden" name="nameId" value="${nameId}" />
						<input type="hidden" name="valueId" value="${valueId}" />
						<input type="text" name="filter_and_userName_LIKE_S"
							value="${filter_and_userName_LIKE_S}" />
					</td>
					<td class="label">
						<label>
							电子邮箱：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_mobile_EQ_S"
							value="${filter_and_mobile_EQ_S}" />
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
					<td class="page-td"><web:pager/></td>
					<td class="handle-td">
						<input type="button" id="add" class="button" value="新 增" onclick="addUser()" />
						<input type="button" class="button" value="修 改" onclick="updateUser()" />
					</td>
				</tr>
			</table>

			<TABLE class="grid-table">
				<thead>
					<tr>
						<th width="5%"><nobr>序号</nobr></th>
						<th width="15%"><nobr>登录名</nobr></th>
						<th width="20%"><nobr>用户姓名</nobr></th>
						<th width="12%"><nobr>数据权限</nobr></th>
						<th width="20%"><nobr>公司名称</nobr></th>
						<th width="25%"><nobr>总公司</nobr></th>
						<th width="10%"><nobr>联系电话</nobr></th>
						<th width="5%"><nobr>状态</nobr></th>
						<th width="15%"><nobr>操作</nobr></th>
					</tr>
				</thead>
				<c:forEach items="${page.results}" var="user" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							${status.count}
							<input type="checkbox" name="id" id="id" value="${user.id}" />
						</td>
						<td>
							${user.accountName}
						</td>
						<td>
						    ${user.userName}
						</td>
						<td>${user.dataPriv =="priv_01" ? "部门数据" : user.dataPriv=="priv_02"?"个人数据":"" }</td>
						
						<td>
							${user.extension}
						</td>
						<td>
							${user.deptName}
						</td>
						<td>
							${user.mobile}
						</td>
                                
						<td>
							<web:showYesNoName value="${user.isEnable}" aliasYes="启用"
								aliasNo="停用" />
						</td>
						<td>
							<nobr>
								<%-- <a href="${path}/um/user!detailUser.action?id=${user.id}">查看</a> --%>
								<a href="${path}/um/user!deleteUser.action?id=${user.id}">删除</a>
							</nobr>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
