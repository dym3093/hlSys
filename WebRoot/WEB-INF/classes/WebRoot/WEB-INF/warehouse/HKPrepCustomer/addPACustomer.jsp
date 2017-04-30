<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript">

	function savePACustomer(){
		var code = $.trim($("#code",$.pdialog.getCurrent()).val());
		if(code==""){
			alertMsg.info("请输入条形码!");
			return;
		}
		var navTabId = $("#navTabId",$.pdialog.getCurrent()).val();
		$.ajax({	
			type: "post",
			cache :false,
			data:{"code":code,"navTabId":navTabId},
			dateType:"json",
			url: "${path}/warehouse/prepCustomer!savePACustomer.action",
			success: function(data){
				var obj = eval("("+data+")");
				if(obj.result) {
					navTab.reloadFlag(obj.navTabId);
					alertMsg.correct("提交成功");
					closeDialog();
				} else {
					alertMsg.error(obj.msg);
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
		<div  style="height: 410px; padding: 0px;">
		<input id="navTabId" type="hidden" value="${navTabId}">
	    	<div> 
	    		<lable>条形码:</lable>
	    		<input id="code" type="text" name="savePACustomer" value=""/>
	    		<button type="button" onclick="savePACustomer()">添加</button>
	    		<button type="button" onclick="closeDialog()">返回</button>
	    	</div>
		</div>
	</div>  

</body>
</html>