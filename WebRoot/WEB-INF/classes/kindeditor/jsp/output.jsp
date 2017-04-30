<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="org.hpin.common.core.SpringTool,org.hpin.base.accessories.service.*" %>

<%
try {
	String fileName = request.getParameter("fileName");
	TawCommonsAccessoriesManagerCOSService tawCommonsAccessoriesManagerCOSService = (TawCommonsAccessoriesManagerCOSService)SpringTool.getBean(TawCommonsAccessoriesManagerCOSService.class);
	String rootFilePath = tawCommonsAccessoriesManagerCOSService.getFilePath();
	String path = rootFilePath + "/editor/attached/" + fileName;
	// 若文件系统中的文件不存在则给出提示
	File file = new File(path);
	if (!file.exists()) {
		return;
	}
	InputStream inStream = new FileInputStream(path);
	response.reset();
    out.clear();
    out=pageContext.pushBody();
	response.setContentType("application/x-msdownload");
	response.setCharacterEncoding("GB2312");
	response.addHeader("Content-Disposition",
			"attachment;filename=" + fileName);
	// 循环取出流中的数据
	byte[] b = new byte[128];
	int len;
	while ((len = inStream.read(b)) > 0)
		response.getOutputStream().write(b, 0, len);
	inStream.close();
} catch (Exception e) {
	e.printStackTrace();
}
%>