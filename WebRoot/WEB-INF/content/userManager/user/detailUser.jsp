<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>
	<body>
		<div class="form">
				<table height="250px">
					<tr height="60">
						<td colspan="6" align="center">
							<nobr>
								<span style="font-weight: bolder;" >人员信息</span>
							</nobr>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								所属部门：
							</label>
						</td>
						<td>
							${user.deptName}
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								登录名：
							</label>
						</td>
						<td>
							${user.accountName}
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								用户姓名：
							</label>
						</td>
						<td>
							${user.userName}
						</td>
					</tr>
					
					<tr>
						<td class="label">
							<label>
								产品名称：
							</label>
						</td>
						<td>
						${user.roleNames}
						</td>
					</tr>
					<!-- 
						<tr>
							<td>
								<label>
									角色：
								</label>
							</td>
							<td>
							  ${user.roleNames}
							</td>
						</tr>
					  -->
					<tr>
						<td class="label">
							<label>
								是否启用：
							</label>
						</td>
						<td>
						<c:choose>
					  	  <c:when test="${user.isEnable==0}">停用</c:when>
					      <c:otherwise>       
					       	启用
					      </c:otherwise>
					     </c:choose>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="button" value="返 回"
								onclick="javascript:history.back(-1)" />
						</td>
					</tr>
				</table>
		</div>
	</body>
</html>
