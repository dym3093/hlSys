<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<%@ include file="/common/taglibs.jsp"%>
<html>

	<head>
		<style type="text/css">
			textarea{outline:none;resize:none;}
			
		</style>
		<script type="text/javascript">
		
		/*
		 *	@author CQ	
		 *	@since 2017年4月6日16:21:18
		 *	更新异常样本
		 */
		function updateDeletedStatus() {
			var note = $.trim($("#note" ,$.pdialog.getCurrent()).val());
			if (note == "" ) {
				alertMsg.info("请填写备注信息!");
				return;
			}
			
			var parentTabId = $("#parentTabId" ,$.pdialog.getCurrent()).val();
			var customerId = $("#customerId" ,$.pdialog.getCurrent()).val();
			var deletedStatus = $("#deletedStatus" ,$.pdialog.getCurrent()).val();
			
			$.ajax({	
				type: "post",
				cache :false,
				dateType:"json",
				data: {
					"note" : note,
					"parentTabId" : parentTabId,
					"customerId" : customerId,
					"deletedStatus" : deletedStatus,
					
				},
				url: "${path}/events/erpCustomer!updateDeletedStatus.action",
				success: function(data){
	 				var obj = eval("("+data+")");
	 				if (obj.result) {
		 				navTab.reloadFlag(obj.parentTabId);
		 				alertMsg.correct("提交成功!");
		 				closeDialog();
		 				
	 				} else {
	 					alertMsg.warn("提交失败!");
	 				}
	 			},
				error :function(){
					alertMsg.error("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
		
		function closeDialog(){
			$.pdialog.closeCurrent();
		}
		</script>
	</head>
	<body>
		<div>
			备注: <textarea id="note" type = "text" style = "height:50px; width:342px; marging-top:10px;">${note}</textarea> 
			<div style = "float:right">
			    <button type="button" onclick="updateDeletedStatus()">提交</button>
			    <button type="button" onclick="closeDialog()">返回</button>
			</div>
		</div>  
		<div hidden = "hidden">
			<input id="customerId" value="${customerId}"/>
			<input id="parentTabId" value="${parentTabId}"/>
			<input id="deletedStatus" value="${deletedStatus}"/>
			<input id="isSubmit" value="0"/>
		</div>
	
	
	</body>
</html>