<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.*,
                 org.hpin.system.accessories.service.*,
                 org.hpin.system.accessories.entity.*,
                 org.hpin.common.util.StaticMehtod,
                 org.hpin.common.core.SpringTool"%>
<!-- 
<head>
  <title>文件展现页面</title>
  18685508732

</head>
<link rel="stylesheet" type="text/css" media="all" href="${path}/styles/default/theme.css" />
<body topmargin="0" leftmargin="0" style="background-image:none">
 -->
<%! TawCommonsAccessoriesManagerCOSService tawCommonsAccessoriesManagerCOSService = 
	(TawCommonsAccessoriesManagerCOSService)SpringTool.getBean( TawCommonsAccessoriesManagerCOSService.class );%>
	
<div style="text-align:left; padding: 8px 16px;">
<%
 String fileIdList = StaticMehtod.nullObject2String( request.getParameter("filelist") );
 //String fileIdList = StaticMehtod.nullObject2String( request.getAttribute("filelist") );
 System.out.println("fileIdList="+fileIdList);
 String[] fileIds=fileIdList.split(",");
 fileIdList="";
 for(int k=0;k<fileIds.length;k++){
   fileIdList = k>0 ? (fileIdList+ ","+ fileIds[k]) : fileIds[k];
 }
  System.out.println("fileIdList="+fileIdList);

 List list = null;
// String fileid = "";
String rootFilePath, fileName, path;

  if(!(fileIdList.equals("")||fileIdList.equals("null")))
 {
   list = tawCommonsAccessoriesManagerCOSService.getAllFileById(fileIdList);
 }
 if(list != null) {

   for(int i=0; i<list.size(); i++)
 {
   TawCommonsAccessories accessories=(TawCommonsAccessories)list.get(i);
   rootFilePath = tawCommonsAccessoriesManagerCOSService.getFilePath(accessories.getAppCode());
   
   //fileid = fileid + "," + accessories.getId();
  fileName = accessories.getAccessoriesName();
  // String filename = "kmanager/upload/access/"+accessories.getAccessoriesName();
  
   path = rootFilePath + accessories.getAccessoriesName();
   
System.out.println(i+"_path33"+path);

%>
<%--
<script language="javascript">
 document.write("<img style=\"margin: 5px\" src=\"<%=request.getContextPath()%>/accessories/pages/loadimage.jsp?imgPath=<%=fileName%>&appCode=<%=accessories.getAppCode()%>\" align=\"top\">");
</script> --%>
<%if(fileName.toUpperCase().indexOf("JPG")>0){%>
<%="<img style='margin: 5px' width='100' height='100' src='" + request.getContextPath()+"/accessories/pages/loadimage.jsp?imgPath="+fileName+"&appCode="+ accessories.getAppCode() +"' align='top'>"%> 
<%}%>
<%
}   
}
%>
<div>
<br>
<ul style="list-style:none;">
<% 
if(list!=null){
    String filelist="";
    for(int i=0;i<list.size();i++){
      TawCommonsAccessories file=(TawCommonsAccessories)list.get(i);
      
      fileName=file.getAccessoriesCnName();
//      filelist=filelist+",'"+file.getAccessoriesName()+"'";
		
//		rootFilePath = tawCommonsAccessoriesManagerCOSService.getFilePath( file.getAppCode() );
//		System.out.println("rootFilePath:::"+rootFilePath);
//		path = rootFilePath + file.getAccessoriesName();
		
//		System.out.println("path:::"+path);
		
%>
	<%-- <input type='checkbox' name='files' value='<%=file.getAccessoriesName()%>' class="checkbox">--%>
	<li><%=i+1%>:<a href='${path}/hpin/acessesoriesconfig!download.action?idd=<%=file.getId()%>' class="filelink"
	><%=fileName%></a></li>
  
<% 
  }
//  if(filelist.indexOf(",")==0){
//    filelist=filelist.substring(1);
//  }
  }
 %>
 
</ul>
<!-- </body>-->
