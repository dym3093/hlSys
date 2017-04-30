<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*,
                 org.hpin.base.accessories.service.*,
                 org.hpin.base.accessories.entity.*,
                 org.hpin.common.core.SpringTool,
                 org.hpin.common.util.StaticMehtod" %>


<head>
  <title>删除文件处理页面</title>
</head>

<%

String appId=StaticMehtod.nullObject2String(request.getParameter("appId"));
String filelist=StaticMehtod.nullObject2String(request.getParameter("filelist"));
String removeIdList=StaticMehtod.nullObject2String(request.getParameter("removeid"));
String idField=StaticMehtod.nullObject2String(request.getParameter("idField"));
TawCommonsAccessoriesManagerCOSService tawCommonsAccessoriesManagerCOSService = (TawCommonsAccessoriesManagerCOSService)SpringTool.getBean(TawCommonsAccessoriesManagerCOSService.class);
System.out.println(removeIdList); 
List list =tawCommonsAccessoriesManagerCOSService.getAllFileById(removeIdList);
for(int i=0; i<list.size(); i++)
{
  System.out.println("12323124");
  TawCommonsAccessories accessories=(TawCommonsAccessories)list.get(i);
  tawCommonsAccessoriesManagerCOSService.removeTawCommonsAccessories(accessories.getId());
  java.io.File file = new java.io.File(accessories.getAccessoriesPath()+"/"+ accessories.getAccessoriesName());
  System.out.println(accessories.getAccessoriesPath()+"/"+ accessories.getAccessoriesName());
  file.delete();
}
 response.sendRedirect("upload.jsp?appId="+appId+"&filelist="+filelist+"&idField="+idField);
%>



