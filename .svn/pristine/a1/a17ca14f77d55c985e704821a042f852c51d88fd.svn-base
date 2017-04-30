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
		onsubmit="return validateCallback(this, navTabAjaxDone);" 
		action="${path}/warehouse/product!saveOrUpdate.action" method="post" theme="simple">
		
    	<input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />					  
	    <input type="hidden" name="product.id" id="productId" value="${product.id }"/>
	    
	    <div class="pageFormContent" layoutH="40" style="overflow: hidden;">
	        <h1 class="press">产品信息管理</h1>
	        <div class="divider"></div>
	        <div class="tip"><span>产品信息浏览</span></div>
			<div class="divider"></div>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品类别：</dt>
				<dd>
					<select id="productType" name="product.productType" rel="iselect" 
						loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10107" >
						
				    	<option value="${product.productType }"></option>
				    </select>
				</dd>
			</dl>
				
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品名称：</dt>
				<dd>
					<input type="text" id="productNameId" maxlength="30" name="product.productName" value="${product.productName }" 
						oldvalue="${product.productName }" class="textInput"/>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品图片：</dt>
				<dd>
					<img src="${path}/warehouse/product!showImage.action?id=${product.id }&numMath=${numMath }" width="158px" height="145px;"/>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品规格：</dt>
				<dd>
					<textarea name="product.standard" cols="80" rows="4" class="textInput" maxlength="63">${product.standard }</textarea>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品描述：</dt>
				<dd>
					<textarea name="product.describe" class="textInput" rows="4" cols="80" maxlength="63">${product.describe }</textarea>
				</dd>
			</dl>
			
		</div>
</form>
</div>

<script type="text/javascript">


$(function() {
	$("input", navTab.getCurrentPanel()).attr("readonly", "readonly");
	$("select",navTab.getCurrentPanel()).attr("disabled", "disabled");
	$("textarea", navTab.getCurrentPanel()).attr("readonly", "readonly");
});

</script>