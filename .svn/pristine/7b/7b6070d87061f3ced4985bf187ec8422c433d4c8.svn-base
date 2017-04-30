<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script src="${path}/scripts/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
<link href='${path}/scripts/uploadify/uploadify.css' type=text/css rel=stylesheet>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript">
$(function(){
	
	var settleId = $("#settleId").val();
	
	 $("#uploadify").uploadify({
		swf      		: '${path}/scripts/uploadify/uploadify.swf',
		uploader		: '${path}/settlementManagement/erpSettlementTaskBX!uploadFile.action',
		// Options
		auto            : false,               
		buttonText      : '选择文件...',     
		height          : 30,                 
		itemTemplate    : false,             
		method          : 'post',             
		multi           : true, 
		formData        : {id:settleId},                
		queueID         : false,             
		queueSizeLimit  : 20,                
		removeCompleted : false,               
		removeTimeout   : 3,                
		requeueErrors   : false,            
		successTimeout  : 3,                
		uploadLimit     : 20,              
		width           : 120,              
        onQueueComplete : function(queueData){
            alert('所选文件已经全部上传');
          }//这里可以获取上传完成后所有上传队列里的信息
	});  
	
/* 	$("#uploadify").uploadify({
		// Required Settings
		flash_url      		: '../scripts/uploadify/uploadify.swf',
		upload_url			: '${path}/settlementManagement/erpSettlementTaskBX!uploadFile.action',
		use_query_string    : 'post',
		auto            	: false,               
		button_text      	: '选择文件...',     
		button_width        : 30, 
		button_height		: 120, 
		post_params        	: {id:"sss"},                
		file_queue_limit  	: 20,                
		file_upload_limit   : 20,  
		assume_success_timeout  	: 3000,
		
	});   */
})

</script>

<div>

    <div>
    	<span>
    		<input type="hidden" name="settleId" value="${settleId}" id="settleId" />
	   		<input type="file" name="uploadify" id="uploadify" />
	    	<input type="button" value="上传" onclick="javascript:$('#uploadify').uploadify('upload')" />
	    	<!-- <input type="button" value="取消" onclick="javascript:$('#uploadify').uploadify('cancel')" />  -->  	
    	</span>
    </div>
    <div>
    	<span id="result" style="color:#ff0000"></span>
    </div>
    <%--用来作为文件队列区域--%>
    <div id="fileQueue">
    </div>

</div>

 