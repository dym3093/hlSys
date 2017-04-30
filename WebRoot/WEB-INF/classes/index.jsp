<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<script>
window.location.href = "${path}/security/security!index.action";
</script>