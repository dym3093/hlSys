<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	$(document).ready(function(){
// 		$("#modifyType").change(function(){
// 			var modifyType=$("#modifyType").val();
// 			if(modifyType=="1"){
// 				$("input[name='cususername']").removeAttr("disabled");
// 				$("input[name='cususerage']").removeAttr("disabled");
// 				$("#cususersex").removeAttr("disabled");
// 				$("input[name='pdfusername']").attr("disabled","disabled");
// 				$("input[name='pdfuserage']").attr("disabled","disabled");
// 				$("#pdfusersex").attr("disabled","disabled");
// 				$("#pdffile").attr("disabled","disabled");
				
// 			}
// 			if(modifyType=="2"){
// 				$("input[name='cususername']").attr("disabled","disabled");
// 				$("input[name='cususerage']").attr("disabled","disabled");
// 				$("#cususersex").attr("disabled","disabled");
// 				$("input[name='pdfusername']").removeAttr("disabled");
// 				$("input[name='pdfuserage']").removeAttr("disabled");
// 				$("#pdfusersex").removeAttr("disabled");
// 				$("#pdffile").removeAttr("disabled");
// 			}
// 		});
	});
	
	function updatePdfContentInfo() {
		var unmatchid = $("#unmatchid",$.pdialog.getCurrent()).val();
		var cusid = $("#cusid",$.pdialog.getCurrent()).val();
		var pdfid = $("#pdfid",$.pdialog.getCurrent()).val();
		var pdfCode = $.trim($("#pdfCode",$.pdialog.getCurrent()).val());
		var pdfUserName = $.trim($("#pdfUserName",$.pdialog.getCurrent()).val());
		var pdfUserSex = $.trim($("#pdfUserSex",$.pdialog.getCurrent()).val());
		var pdfCombo = $.trim($("#pdfCombo",$.pdialog.getCurrent()).val());
		var matchStateObj = $("#matchState",$.pdialog.getCurrent());
		if ($(matchStateObj).val() == 1) {
			alertMsg.warn("请勿重复提交!");
			return;
		}
		$(matchStateObj).val(1);
		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "${path}/report/unMatch!updatePdfContentInfo.action",
			data: {"unMatchId":unmatchid,"cusId":cusid,"pdfId":pdfid,"pdfCombo":pdfCombo,"pdfUserSex":pdfUserSex,"pdfUserName":pdfUserName,"pdfCode":pdfCode},
			success: function(data){
				if(data.result=='success'){
					alertMsg.correct("同步成功！");
					$(matchStateObj).val(0);
					var form = "${path}/report/unMatch!query.action";
					navTabSearch(form);
					$.pdialog.closeCurrent();
				}else{
					alertMsg.warn("同步失败！");
					$(matchStateObj).val(0);
				} 	
			},
			error :function(data){
				alertMsg.error("服务发生异常，请稍后再试！");
				$(matchStateObj).val(0);
			}
		});
	}
	
	

</script>

<div class="pageFormContent" >
	<div class="pageContent">
    		<div style="height: 200px">
      			<div class="tip"><span>更改信息</span></div>
				    <input id="matchState" type="hidden" name="matchState" value="0">
				    <input id="pdfid" type="hidden" name="pdfid" value="${unMatch.pdfid}">
				    <input id="cusid" type="hidden" name="cusid" value="${unMatch.cusid}">
				    <input id="unmatchid" type="hidden" name="unmatchid" value="${unMatch.id}">
      				<table class="list">
        				<tr>
        					<td align="center" style="width: 60px;">报告信息</td>
        					<td align="center" style="width: 60px;"><input id="pdfCode" name="pdfCode" type="text" style="width: 70px;" value="${unMatch.pdfcode}"/></td>
        					<td align="center" style="width: 60px;"><input id="pdfUserName" name="pdfUserName" style="width: 70px;" type="text" value="${unMatch.pdfusername}"/></td>
        					<td align="center" style="width: 50px;"><input id="pdfUserSex" name="pdfUserSex" style="width: 70px;" type="text" value="${unMatch.pdfusersex}"/></td>
        					<td align="center" style="width: 80px;"><input id="pdfCombo" name="pdfCombo" type="text" value="${unMatch.pdfCombo}"/></td>
        				</tr>
        				
        				<tr>
        					<td align="center">客户信息</td>
        					<td align="center"><span>${unMatch.cuscode}</span></td>
        					<td align="center"><span>${unMatch.cususername}</span></td>
        					<td align="center"><span>${unMatch.cususersex}</span></td>
        					<td align="center"><span>${unMatch.cusCombo}</span></td>
        				</tr>
          				
          				<tr>
          					<td colspan="5"><button  type="button" style="float: right;" onclick="updatePdfContentInfo()">更改报告信息</button></td>
          				</tr>
          				
      				</table>
      		</div>
	</div>
</div>
