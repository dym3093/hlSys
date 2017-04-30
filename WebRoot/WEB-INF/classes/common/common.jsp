<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="widget/tags-web" prefix="web"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri = "/WEB-INF/hpin.tld" prefix="hpin" %>

<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<script language="javascript">  
var path = '<%=request.getContextPath()%>';
</script>

<c:set var="app" scope="page" value="${pageContext.request.contextPath}" />

<script type="text/javascript" language="javascript"
	src="${path}/scripts/common.js">
</script>
<script language="javascript" type="text/javascript"
	src="${path}/scripts/DatePicker/WdatePicker.js">
</script>
<script type="text/javascript" language="javascript"
	src="${path}/scripts/jquery.js">
</script>
<script type="text/javascript" language="javascript"
	src="${path}/scripts/CheckForm.js">
</script>

<script type="text/javascript" src="${path}/scripts/jquery.iselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("form.form tr").each(function(){
		$(this).mouseover(function(){
				$(this).css({backgroundColor:"#CFE9E7"});
		}).mouseout(function(){
				$(this).css({backgroundColor:"#FFFFFF"});
		})
	});
});
</script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="${path}/styles/style.css" rel="stylesheet" type="text/css" />
<%-- --%>
<link href="${path}/css/${style==null?'default':style }/${style==null?'default':style }.css" rel="stylesheet" type="text/css" />
