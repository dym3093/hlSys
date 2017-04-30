<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div style="width:780px;height:400px;overflow:auto;">

	<div class="tip"><span>快递单号：<c:forEach items="${express}" var="express" varStatus="status">
	<c:if test="${status.count==1}">
       ${express.id}
    </c:if></c:forEach></span>
    <span>快递公司：<c:forEach items="${express}" var="express" varStatus="status">
	<c:if test="${status.count==1}">
       ${express.compTyp}
    </c:if></c:forEach></span>
    </div>
<div class="express_info">
	 
      <table class="list" width="100%">
      	<tbody>
      	<thead>
			<tr>
				<td>时间</td>
				<td>地点和跟踪进度</td>
			</tr>
		</thead>
      	<c:forEach items="${express}" var="express">
      		<tr>
      			<td>${express.time}</td>
      			<td>${express.context}</td>
      		</tr>
      	</c:forEach>
      	</tbody>
      </table>
	</div>

</div>
