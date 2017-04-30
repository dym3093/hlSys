<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript" src="${path }/scripts/plugin/shieldLayer_plugin.js"></script>

<div class="tip"></div>
<div class="pageHeader">
</div>
<div class="pageContent">
	<div style="width:100%;height:520px;overflow-y: scroll;">
		<div style="width: 800px;margin-left: 100px;">
			<c:if test="${details!=null }">
				${details }
			</c:if>
		</div>
	</div>
</div> 