<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script src="${path}/jquery/json2.js" type="text/javascript"></script>  
  
<script type="text/javascript">

	function createFunction(id){
		var QRCodeId = id;
		var keyword = $("#keyword").val();
		var expiry = $("#expiry").find("option:selected").val();
		if(keyword==''){
			alert("请输入关键字！");
			return;
		}
		$.ajax({
			type: "post",
			cache :false,
			data:{"QRCodeId":QRCodeId,"keyword":keyword,"expiry":expiry,},
			url: "${path}/events/erpQRCode!createQRCode.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.status=='200'){
					//alert(data.message);
					alertMsg.correct(data.message);
					$.pdialog.closeCurrent();
					return navTabSearch(this);
				}else if(data.status=='300'){
					//alert(data.message);
					alertMsg.error(data.message);
				}
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}

</script>
<div class="pageHeader" style="background:white">
	<form class="pageForm required-validate" id="modifyPrice" action="${path}/events/erpQRCode!createQRCode.action" method="post">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>关键字：</label> 
					<input type="text" id="keyword" name="keyword" value="" class="required"/>
					<input type="hidden" id="QRCodeId" name="QRCodeId" value="${QRCodeId}"/>
					
				</td>
				<td>
					<label>有效期：</label> 
					<select id="expiry" name="expiry" style="margin:5px;width:193px;">
						<!-- <option value="12">12小时</option>
						<option value="24">24小时</option> -->
						<option value="36">36小时</option>
						<option value="48">48小时</option>
						<option value="72">72小时</option>
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
         		<td>
         			<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="createFunction('${QRCodeId}')">生成</button></div></div>
				</td>
			</tr>
		</table>
	</form>
</div>