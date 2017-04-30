<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>    
<style> 
.tableClass table{border-right:1px solid #000;border-bottom:1px solid #000} 
.tableClass table td{border-left:1px solid #000;border-top:1px solid #000} 
.tableClass table th{border-left:1px solid #000;border-top:1px solid #000} 
</style> 
<script type="text/javascript" language="javascript" src="${path}/scripts/jquery.js"></script>
<script>
function trClick(obj) {
	$(obj).find("input").attr("checked", "checked");
}
</script>
<div class="pageHeader" style="background:white">
	<form id="pagerFindForm"  rel = "pagerForm" action="${path }/resource/customerRelationShip!findCompanyByOwnerCompanyId.action" method="post">
		<input type="hidden" name="params.wonedCompanyId" value="${params.wonedCompanyId }">
		
		<div class="searchBar">
			<table class="pageFormContent">
			<tr>
				<td>
					<label>公司名称：</label>
					<input type="text" name="params.companyName"  value="${params.companyName}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
				</td>
			</tr>
			</table>
		</div>
	</form>
</div>

<div class="tableClass">
	<table width="100%" cellspacing="0" cellpadding="0">
		<thead>
			<tr>
				<th style="width: 35px">序号</th>
				<th>公司名称 </th>
				<th>省份</th>
				<th>城市</th>
			</tr>
		</thead>
		
		<tbody>
		<c:forEach items="${shipVos }" var="ship" varStatus="status">
			<tr onclick="trClick(this)">
				<td>
					<input type="radio" name="choiceRadio" value="${ship.id }" <c:if test="${params.companyId==ship.id }">checked="checked"</c:if> complanyName="${ship.branchCommany }"/>
				</td>
				<td align="center" nowrap="nowrap">${ship.branchCommany }</td>
				<td align="center">${ship.provinceName }</td>
				<td align="center">${ship.cityName }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>