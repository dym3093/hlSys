<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath() ;
	request.setAttribute("path" , path) ;
 %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <head>
    <title>出错啦</title>
    <script type="text/javascript">
    	function showDetail(){
    		var div = document.getElementById("detail_system_error_msg") ;
    		var allDiv = document.getElementById("all") ;
    		if(div.style.display == 'none'){
    			div.style.display = 'block' ;
    			allDiv.setAttribute("class" , "bigbig_box") ;
    		}else{
    			div.style.display = 'none' ;
    			allDiv.setAttribute("class" , "box_txt") ;
    		}
    	}
    </script>
    <link href="${path }/styles/exception/bc.css" rel="stylesheet" type="text/css">
  </head>
  <body>
    <div class="box">
	  <div class="box_txt" id = "all" >
	  	 <div class="box_txt_1">
		    <h3>  
		    	对不起,出错啦！<br />  
		    </h3> 
		 </div>  
		 <div class="box_txt_2">
	    	<b>错误信息:</b> ${exception.message }
	  	 </div>
	    
		<div class="box_txt_2">
			<button onClick="history.back();">返回</button>
	    </div>

	  	<div class="box_txt_3">
	   		<a href="#" onClick="showDetail();">点击查看详细信息</a>，&nbsp;将以下错误提交给管理员，会加快问题解决的速度。
	  	</div>
    	<div class="" id = "detail_system_error_msg" style="display: none;">  
	 		<pre>
	        	${exceptionStack }
	        </pre>
			<div class="clear"></div>
	    </div>
	   </div>
    </div>
  </body>
</html>
