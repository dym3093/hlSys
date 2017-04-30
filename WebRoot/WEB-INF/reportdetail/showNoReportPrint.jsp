<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

	function addNoReportPrint(){
		var name = $.trim($("#name",$.pdialog.getCurrent()).val());
		var code = $.trim($("#code",$.pdialog.getCurrent()).val());
		var gender = $.trim($("#gender",$.pdialog.getCurrent()).val());
		if(name=="" || code==""){
			alertMsg.warn("信息不完整,请重新输入");
			return;
		}
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			data:{"name":name,"code":code,"gender":gender},
			url: "${path}/reportdetail/erpReportPrintManagement!addNoReportPrint.action",
			success: function(data){
				var str = eval("("+data+")");
				if(str.count==1){
					alertMsg.correct("提交成功");
					navTab.closeCurrentTab();
					navTab.openTab("reportDetailManagement", "${path}/reportdetail/erpReportPrintManagement!reportDetailManagement.action", 
							{ title:"不打印任务", fresh:true, data:{} });
					$.pdialog.closeCurrent();
				}else if(str.count==2){
					alertMsg.warn("已有该数据,不能重复添加");
				}else{
					alertMsg.error("提交失败");
				}
				
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}

	function closeDialog(){
		$.pdialog.closeCurrent();
	}

</script>
<head></head>
<body>
	<div>
		<form class="pageForm required-validate"  onsubmit="return validateCallback(this, dialogAjaxDone);"  action="${path}/reportdetail/erpReportPrintManagement!addNoReportPrint.action" method="post">
			<table>
				<tr>
					<td>
						<label>姓名：</label> 
						<input id="name" type="text" name="name" value="${name}" >
					</td>
					<td>
						<label>编码：</label> 
						<input id="code" type="text" name="code" value="${code}">
					</td>
				</tr>
				
				<tr>
					<td>
						<label>性别：</label> 
						<input id="gender" type="text" name="gender" value="${gender}">
					</td>
					<td style="padding-left: 105px;">
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="addNoReportPrint()" >保存</button></div></div>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog()">返回</button></div></div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>



