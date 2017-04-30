<%@ page language="java" import="java.util.*,java.sql.*,java.io.*,cn.yuanmeng.labelprint.test.TestExcelProcess,cn.yuanmeng.labelprint.dao.BaseDao,java.sql.*" pageEncoding="UTF-8"%>
<%@ page import="cn.yuanmeng.labelprint.test.FileCopy" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'gene.jsp' starting page</title>
    <style type="text/css">
    	.border{border-style:solid;}
    </style>
    <script type="text/javascript">
    	function geneFormSubmit(){
    		var dirPath=document.geneform.dirPath.value;
    		if(dirPath.length!=8){
    			alert("格式错误，必须符合yyyymmdd格式，如20160101");
    			return false;
    		}
    		return true;
    	}
    </script>
  </head>

  <body>
    <h1 align="center">基因数据处理</h1> <br>
    <form action="dogene.jsp" name="geneform" method="post" onsubmit="return geneFormSubmit()">
    <table align="center">
    	<tr><td>请复制下表中未处理文件夹名：</td><td><input name="dirPath" value=""/></td><td><input type="submit" value="提交"></td></tr>
    	<tr><td></td><td></td><td></td></tr>
    </table>
   
    <table align="center" border="1" width="35%" class="border">
    	<tr><th>FTP文件夹名</th><th>处理状态</th><th></th></tr>
    	 <%
   // String dir="d:\\ftp\\upload";
    String dir=TestExcelProcess.GENEFTP+"upload";
	File f=new File(dir);
	if(f.exists()&&f.isDirectory()){
		System.out.println("存在");
		File fileList[]=f.listFiles();
		for (File file : fileList) {
			String uploadSubDirName=(file.getName());
	
    %>
    	<tr><td><%=file.getName() %></td><td><%=FileCopy.isExists(uploadSubDirName) %></td><td></td></tr>
   
   <%	}
	}
	
	%>
	 </table>
    </form>
  </body>
</html>
