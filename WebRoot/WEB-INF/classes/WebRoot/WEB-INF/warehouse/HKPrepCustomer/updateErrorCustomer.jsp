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

	function saveErrorCustomer(){
		var code = $.trim($("#code",$.pdialog.getCurrent()).val());
		var reason = $.trim($("#reason",$.pdialog.getCurrent()).val());
		var navTabId = $("#navTabId",$.pdialog.getCurrent()).val();
		if(code == "" || reason == ""){
			alertMsg.info("请输入正确的信息!");
			return;
			
		}
		$.ajax({	
			type: "post",
			cache :false,
			data:{"code":code,"navTabId":navTabId,"reason":reason},
			dateType:"json",
			url: "${path}/warehouse/prepCustomer!saveErrorCustomer.action",
			success: function(data){
				var obj = eval("("+data+")");
				if(obj.count==1){
					if(obj.result) {
						navTab.reloadFlag(obj.navTabId);
						closeDialog();
						alertMsg.correct("提交成功");
					} else {
						alertMsg.error(obj.msg);
					}
				}else{
					alertMsg.error("没有找到对应的履约单号!");
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
	    		<lable>检测码:</lable>&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input id="code" type="text" name="saveErrorCustomer" value=""/>
	    		<br/>
	    		<lable>失败原因:</lable>
	    		<input id="reason" type="text" name="reason" value=""/>
	    		
	    		<button type="button" onclick="saveErrorCustomer()">提交</button>
	    		<button type="button" onclick="closeDialog()">返回</button>
	    	</div>
		</div>
	</div>  

</body>
</html>