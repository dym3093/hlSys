<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<style>
<!--
	span.error {top: 9px;}
-->
</style>
<div class="pageContent">
		<form id="product_edit_from_id" 
		class="pageForm required-validate" 
		action="#" method="post" theme="simple"> 
    	<input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />					  
	    <input type="hidden" name="product.id" id="productId" value="${product.id }"/>
	    
	    <div class="pageFormContent" layoutH="40" style="overflow: hidden;">
	        <h1 class="press">产品信息管理</h1>
	        <div class="divider"></div>
	        <div class="tip"><span>产品信息录入</span></div>
			<div class="divider"></div>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品类别：</dt>
				<dd>
					<select id="productType" name="product.productType" rel="iselect" 
						loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10107" class="required">
				    	<option value="${product.productType }"></option>
				    </select>
				</dd>
			</dl>
				
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品名称：</dt>
				<dd>
					<input type="text" id="productNameId" maxlength="30" name="product.productName" value="${product.productName }" 
						class="textInput required exits specialSigl"/>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品图片：</dt>
				<dd>
					<input type="file" name="upload" id="uploadId" style="margin-top: 6px; margin-left: 4px;" class="textInput" onchange="uploadImageChange();">
					<input id="isUploadId" type="hidden" name="isUpload" value="0"/>
					<c:if test="${product.imagePath != null }">
						<a href="javascript:void(0);" class="button" onclick="showImage('${product.id }');" style="margin-top: 6px; margin-left: 4px;"><span>图片查看</span></a>
					</c:if>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品规格：</dt>
				<dd>
					<textarea id="standardId" name="product.standard" cols="80" rows="4" class="textInput specialSigl" maxlength="63">${product.standard }</textarea>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品描述：</dt>
				<dd>
					<textarea id="describeId" name="product.describe" class="textInput specialSigl" rows="4" cols="80" maxlength="63">${product.describe }</textarea>
				</dd>
			</dl>
			
		</div>
		
		
	    <div class="formBar" style="width:98%">
	      <ul>
	        <li>
	              <!-- <button onclick="submitIntercept();" >保存</button> -->
	            <a class="button" href="javascript:;" onclick="submitIntercept();"><span>保存</span></a>
	        </li>
	        <li>
	          <div class="button">
	            <div class="buttonContent">
	              <button type="reset">重置</button>
	            </div>
	          </div>
	        </li>
	      </ul>
	    </div>
	    </form>
</div>
<!-- 验证提示需要; -->
<script src="${path}/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>

<script type="text/javascript">

/*一旦修改变为1标示需要图片处理; 如果默认为0表示不需要处理;*/
function uploadImageChange() {
	$("#isUploadId").val(1)
}

/**
 * 编辑界面图片回显
 */
function showImage(id) {
	$.pdialog.open("${path}/warehouse/product!imageJsp.action?id="+id, "imageshowid", "图片回显", 
		{
			mask:true, 
        	width:600, height:500, 
        	maxable: false, 
        	resizable:false 
    	});
}
//提交拦截方法,上传图片;
function submitIntercept() {
	
	//验证;
	var $form = $("#product_edit_from_id", navTab.getCurrentPanel());
	if(!$form.valid()) {
		return false;
	}
	var isUpload = $("#isUploadId", navTab.getCurrentPanel()).val();
	var productType = $("select", navTab.getCurrentPanel()).val();
	var productNameId = $("#productNameId", navTab.getCurrentPanel()).val();
	var standardId = $("#standardId", navTab.getCurrentPanel()).val();
	var describeId = $("#describeId", navTab.getCurrentPanel()).val();
	var id = $("#productId", navTab.getCurrentPanel()).val();
	
	if(!productType) {
		alertMsg.error("请选择产品类别后再做保存!");
		return ;
	}
	//文件上传
	$.ajaxFileUpload({
	    url : "${path}/warehouse/product!uploadFile.action",// 用于文件上传的服务器端请求地址
	    type : "post",
	    dataType : "JSON",
	    data: {
	    	"product.productType": productType,
	    	"product.productName":productNameId,	
	    	"product.standard":standardId,	
	    	"product.describe":describeId,
	    	"isUpload":isUpload,
	    	"product.id": id
	    },
	    secureuri : false,// 一般设置为false
	    async: false,
	    fileElementId : "uploadId",// 文件上传空间的id属性 <input type="file" id="uploadId" />
	    success : function(data) {
			if(data.statusCode == "200") {
				//将自己关闭;
				navTab.closeCurrentTab();
				navTab.reloadFlag("${navTabId}");
			}
	    },
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alertMsg.alert("服务发生异常，请稍后再试！");
	    }

	});
	
}

$.validator.addMethod("exits", function(value, element) {
	
	var id = $("#productId").val();
	var exits = true;
	//后台请求查看是否已经存在了
	$.ajax({
		type: "post",
		cache : false,
		async : false,
		data:{"productId":id, "productName": value},
		url: "${path }/warehouse/product!validProductNameIsExits.action",
		success: function(data){
			var data= eval("("+data+")");
			exits = data.result; 
		},
		error :function(){
			alertMsg.alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	
	return this.optional(element) || exits;
}, "输入的产品名称已存在!");

$.validator.addMethod("specialSigl", function(value, element) {
	var flag = true;
	
	var num = value.indexOf("'");
	if(num >= 0) {
		flag = false;
	}
	num = value.indexOf('"');
	if(num >= 0) {
		flag = false;
	}
	
	return this.optional(element) || flag;
}, "存在\"或'符号,请换为中文符号!");

$(function() {
	
});

</script>