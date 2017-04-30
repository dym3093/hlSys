<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
	function saveEmsInfo(){
		var emsName = $("input[name='emsName']").val();
		var emsNo = $("input[name='emsNo']").val();
		var emsPrice = $("input[name='emsPrice']").val();
		var navTabId = $("#upNavTabId").val();
		var storeProduceId = $("input[name='storeProduceId']").val();
		$.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			data:{"navTabId":navTabId,"storeProduceId":storeProduceId,"emsName":emsName,"emsNo":emsNo,"emsPrice":emsPrice},
			url: "../warehouse/warehouse!saveEmsInfo.action",
			success: function(data){
				var resp = eval('('+data+')');
				if(resp.result=='success'){
					alertMsg.correct(resp.message);
					$.pdialog.closeCurrent();
					return navTabSearch(this);
				}else{
					alertMsg.error(resp.message);
				}
			},
			error :function(data){
				alertMsg.error("补录失败！");
			}
		});
	}

</script>
<div style="text-align:center">
	<input type="hidden" value="${navTabId}" id="upNavTabId" />
	<input type="hidden" value="${storeProduceId}" name="storeProduceId" />
	<p style="margin-top:5px"><span>快递公司:</span><input type="text" value="" name="emsName" style="margin-left:5px"/></p>
	<p style="margin-top:5px"><span>快递单号:</span><input type="text" value="" name="emsNo" style="margin-left:5px"/></p>
	<p style="margin-top:5px"><span>快&nbsp&nbsp递&nbsp&nbsp费:</span><input type="text" value="" name="emsPrice" style="margin-left:5px"/></p>
	<p style="margin-top:5px"><input type="button" value="提交" onclick="saveEmsInfo()"/></p>
</div>
