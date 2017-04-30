<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="widget/tags-web" prefix="web"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>

<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%
response.setHeader("Content-disposition","attachment;filename="+ new String( "测试".getBytes("UTF-8"), "ISO8859-1" )+".xls");
%>
