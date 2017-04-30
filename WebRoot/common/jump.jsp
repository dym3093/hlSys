<%@ page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>
	<body>
		<form action="${jumpUrl}" method='post' style='display: none'>
			<web:transfer />
		</form>
		<script type="text/javascript">
			var str = "${alert}";
			if (str != "") {
				alert(str);
			}<c:if test="${jumpUrl!=null}">
			    document.forms[0].submit();
			  </c:if>
        </script>
	</body>
</html>