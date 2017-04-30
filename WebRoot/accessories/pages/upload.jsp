<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_hpin_ext.jsp" %>
<%@ page import="org.hpin.common.util.StaticMehtod,
                 org.hpin.base.accessories.service.*,
                 org.hpin.base.accessories.entity.*,
                 org.hpin.common.core.SpringTool,
                 java.util.*" %>
 <script type="text/javascript" charset="utf-8" src="${path}/scripts/base/hpin.js"></script>
 
<%
 String appId=StaticMehtod.nullObject2String(request.getParameter("appId"));
 String filelist=StaticMehtod.nullObject2String(request.getParameter("filelist"));
 String idField=StaticMehtod.nullObject2String(request.getParameter("idField"));
 String startwith = StaticMehtod.nullObject2String(request.getParameter("startsWith"));
 String type = StaticMehtod.nullObject2String(request.getParameter("type"));
String startStr = "";
if(!"0".equals(startwith)){
	startStr = "文件开头";
}
%>
<html>          
<head>
<title>
 附件上传
</title>
<%@ include file="/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href="${path}/styles/default/theme.css" />
<script type="text/javascript">
    var idseed = 1;
	function addFile() {
		idseed++;
		Ext.DomHelper.append('fileDiv', 
			{tag:'span', 			
			 html:'<input type="file" name="file'+idseed+'" size="30"  class="file"/>&nbsp;&nbsp;<img src="${path}/images/icons/list-delete.gif" alt="删除" onclick="removeFile(parentNode);"/><br/>'
            }
        );
        //autoIframe();
	}
 function confirmForm(){ 
  		var frm=document.tawCommonsAccessoriesForm; 
  		var appId=frm.appId.value;
 		var filelist=frm.filelist.value;
  		var idField=frm.idField.value;
  		var filetype = '<%=type%>';
  		var startwith = '<%=startwith%>';
  		var startStr = '<%=startStr%>';
  		var flag = 1;
  		var filetypeflag = 1 ;
  		var fileNameStartflag = 1 ;
   		hpin.select("input[type=file]", "tawCommonsAccessoriesForm").each(
   			function(n){
   				if(n.value=="") { 
   					flag = 0; 
   				}

	   			var fileName = n.value;
    			var fileLast = fileName.substring(fileName.lastIndexOf(".") + 1)
     			var fileNameStart = "";
     			
     			if(fileName.indexOf("\\")>-1){
     				fileNameStart= fileName.substring(fileName.lastIndexOf("\\") + 1)
     			}

    			//alert(fileLast);
    			//test = frm.FILE.value.split(".");
    			//var lasttype = test[1];

    			if(filetype!=null || !filetype.eques("")){
    				if(filetype.indexOf(fileLast.toLowerCase())<0){
    					//filetypeflag = 0 ;
    					return;
    				}
    			}

    			if(startStr!=""){
    				if(fileNameStart.indexOf(startStr)!=0){
    					var fileNameStartflag = 0 ;
    					return;
    				}
    			}
    		}
    	);
    	
    	if(flag==0){
    		alert("请选择上传附件!") ;
    		return false;
    	}
    	
    	if(filetypeflag==0){
    		alert("请正确选择上传文件的类型。" + "\n" + "文件类型为xls,txt,doc,jpg,gif!");
    		return false;
    	}
    	
    	if(fileNameStartflag==0){
    		alert("您选择的附件必须以["+startStr+"]\n" + "开头的文件");
    		return false;
    	}
    	
    	frm.action="${path}/accessories/pages/upload.jsp?appId="+appId+"&filelist="+filelist+"&idField="+idField+"&startsWith="+startwith;
    	frm.submit();
 	}
 function deleteFile()
  {
    var frm=document.tawCommonsAccessoriesForm;
    	var temp = "";
    	var filelist=frm.filelist.value;
    	var startUrlStr = '<%=startwith%>';
    	
    	if(filelist==""){
      		alert("请选择您要删除的文件");
      		return;
    	}
    	
    	if(document.uploadfile.files.length != null){
      		for(var i=0;i<document.uploadfile.files.length;i++){
        		if(document.uploadfile.files[i].checked){
          			temp = temp + "'" + document.uploadfile.files[i].value+"',";
        		}
      		}
    	}
    	else {
    		if(document.uploadfile.files.checked){
    			temp = temp + "'" + document.uploadfile.files.value+"',";
    		}
    	}
    	
    	if(temp.length==0){
    		alert("请选择您要删除的文件。");
      		return;
    	}
   		
   		if(confirm("您确定要删除上传的文件吗？")){
   			location.href="remove.jsp?appId=<%=appId%>&removeid=" + temp.substring(0,temp.length-1) + "&filelist=" + filelist + "&idField=<%=idField%>&startsWith=" + startUrlStr;
  		}
  }
 	function removeFile(obj) {   
   		obj.removeNode(true);  
   		//autoIframe(); 
	}
</script>
</head>
<body style="background-image:none;">
<div class="upload-box">
<%
 if(!appId.equals("")){
  TawCommonsAccessoriesConfigService  tawCommonsAccessoriesConfigService = (TawCommonsAccessoriesConfigService)SpringTool.getBean(TawCommonsAccessoriesConfigService.class);
	TawCommonsAccessoriesManagerCOSService tawCommonsAccessoriesManagerCOSService = (TawCommonsAccessoriesManagerCOSService)SpringTool.getBean(TawCommonsAccessoriesManagerCOSService.class);
	TawCommonsAccessoriesManagerFileuploadService tawCommonsAccessoriesManagerFileuploadService = (TawCommonsAccessoriesManagerFileuploadService)SpringTool.getBean(TawCommonsAccessoriesManagerFileuploadService.class);

  List fileList=tawCommonsAccessoriesManagerCOSService.saveFile(request,appId,filelist);
  
  if(fileList!=null){
%>
   <form name="uploadfile">
<% 
    filelist="";
    for(int i=0;i<fileList.size();i++){
      TawCommonsAccessories file=(TawCommonsAccessories)fileList.get(i);
      String fileName=file.getAccessoriesCnName();
      filelist=filelist+",'"+file.getAccessoriesName()+"'";
     
	
		
		String rootFilePath = tawCommonsAccessoriesManagerCOSService.getFilePath(
				file.getAppCode());
		System.out.println("rootFilePath:::"+rootFilePath);
		String paths = rootFilePath;
		paths = paths + file.getAccessoriesName();
		
		System.out.println("path:::"+path);
		
%>
	<input type='checkbox' name='files' value='<%=file.getAccessoriesName()%>' class="checkbox">
	<a href='${path}/hpin/acessesoriesconfig!download.action?idd=<%=file.getId()%>' class="filelink">
	    <%=fileName%>
	</a>

	<br>
   
<% 
  }
  if(filelist.indexOf(",")==0){
    filelist=filelist.substring(1);
  } 
 %> 
 </form>
 <%}
   else {filelist="";}
 }
%>
 
 <form name="tawCommonsAccessoriesForm" enctype="multipart/form-data" onsubmit="javascript: return confirmForm();" method="post" id="tawCommonsAccessoriesForm"> 
<table>
	<tr>
    	<td colspan="2">
    		<div id="fileDiv">
    			<span>
    				<!--input type="file" id="FILE" name="FILE" size="30" class="upload" class="file"-->
    				<input type="file" id="FILE" name="FILE1" size="30" class="file">&nbsp;&nbsp;
    				<br/>
    			</span>
    		</div>    		
            <input type="hidden" name="appId" value="<%=appId%>">
            <input type="hidden" name="filelist" value="<%=filelist%>" >
            <input type="hidden" name="idField" value="<%=idField%>" >   
     	</td>
	<tr>
    	<td>
          <input type="button" class="btn" value="上传" name="button" onclick="confirmForm();">
          <input type="button" class="btn" value="删除" name="button" onclick="deleteFile();">
       </td>
       
     
     </tr>
  </table>      
 </form>
 
</div>
<script type="text/javascript">
   try{
   	 parent.document.getElementById('<%=idField%>').value= "<%=filelist%>";
	 if("<%=filelist%>"!="") parent.v.clear();
   }
   catch(e){};
</script>
</body>
</html>
