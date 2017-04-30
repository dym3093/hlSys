<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	.title{
		font-size: 24px;
		font-weight: bold;
	}
	
	.tip2{
		font-size: 14px;
		margin-left: 40px;
	}
	
	.tip3{
		font-weight: bold;
		margin-left: 14px;
	}
</style>

<script src="${path}/jquery/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path}/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	
	$(function(){
		$("span[class='title']").text($(":text[name='orderNo']").val());
		$("span[class='tip2']").text($(":text[name='id']").val());
	});
	
	function getModelExcel(){
		
	}
	
	function uploadFile(){
		if($(":file").val()==""){
			alertMsg.info("请先选择上传的EXCEL文件");
			return false;
		};
		var id = $(":text[name='id']").val();
		var orderNo = $(":text[name='orderNo']").val();
		$.ajaxFileUpload({
			url:'${path}/children/erpGroupOrderInfo!uploadFile.action',
			type:'post',
			data:{"id":id,"orderNo":orderNo},
			secureuri: false,
			fileElementId: 'file',
			dataType: 'json',
			success:function(data){
				if(data.count==0){
					alertMsg.info("共导入【"+data.count+"】条数据,导入失败");	
				}else{
					alertMsg.info("成功导入【"+data.count+"】条数据");				
				}
			},
			error:function (data,status,e){
				alert("服务发生异常，请稍后再试！");
			}
		});
	}
	
	function checked(){		//上传文件之前先检查
		if($("#file").val()==""){
			alert("请先选择要上传的文件！");
			return false;
		}
	}
	
</script>

<span class="title"></span>
<div class="pageHeader">
	<br/>
	<span class="tip2"></span>
	<br/><br/>

</div>

<div class="pageContent">
	<form action="${path}/children/erpGroupOrderInfo!uploadFile.action" 
	onsubmit="if(this.action != '${path}/children/erpGroupOrderInfo!uploadFile.action'){this.action = '${path}/children/erpGroupOrderInfo!uploadFile.action' } ;return navTabSearch(this);" 
	enctype="multipart/form-data"" method="post">
		<div>
			<br/>
			<!-- <div class="tip3">模板下载<button type="button" style="margin-left: 10px;" onclick="getModelExcel()">模板</button></div><br/> -->
			<div>
				<lable class="tip3">客户信息导入：</lable>
				<input type="file" id="file" name="file" required="required"/><button type="button" onclick="uploadFile()">上传</button>
			</div>		
		</div>
	</form>
	<div>
		<input type="text" name="orderNo" value="${orderNo}" hidden="hidden"/>
		<input type="text" name="id" value="${id}" hidden="hidden"/>
	</div>
</div> 