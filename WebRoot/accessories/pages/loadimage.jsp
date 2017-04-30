<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="
					org.hpin.base.accessories.service.*,
                 org.hpin.base.accessories.entity.*,
                 org.hpin.common.core.SpringTool" %>


<%
String path = request.getParameter("imgPath");
String appCode = request.getParameter("appCode");
TawCommonsAccessoriesManagerCOSService tawCommonsAccessoriesManagerCOSService = (TawCommonsAccessoriesManagerCOSService)SpringTool.getBean(TawCommonsAccessoriesManagerCOSService.class);
String rootFilePath = tawCommonsAccessoriesManagerCOSService.getFilePath(appCode);
path = rootFilePath + path ;
//response.reset();
 response.setContentType( "image/jpeg" );
 
 ServletOutputStream output = response.getOutputStream();
java.io.InputStream in = new java.io.FileInputStream(path);
	    byte tmp[] = new byte[1024];
	
	    int j=0;
	    while ((j = in.read(tmp)) != -1) {
	        output.write(tmp, 0, j);
	    }
	    in.close();
output.flush(); //强制清出缓冲区
output.close();
//response.flushBuffer();
out.clear();
out = pageContext.pushBody();	    
	    
%>

