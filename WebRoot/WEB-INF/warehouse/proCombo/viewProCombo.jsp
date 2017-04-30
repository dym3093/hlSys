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
	<form id="pagerFindForm" 
		class="pageForm required-validate" 
		onsubmit="return validateCallback(this, navTabAjaxDone);" 
		action="${path}/warehouse/proCombo!saveOrUpdate.action" method="post" theme="simple"> 
    	<input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />					  
	    <input type="hidden" name="proCombo.id" id="proComboId" value="${proCombo.id }"/>
	    
	    <div class="pageFormContent" layoutH="40" style="overflow: hidden;">
	        <h1 class="press">产品套餐管理</h1>
	        <div class="divider"></div>
	        <div class="tip"><span>产品套餐录入</span></div>
			<div class="divider"></div>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品套餐名称：</dt>
				<dd>
					<input name="proCombo.productComboName" type="text" value="${proCombo.productComboName }" class="textInput" readonly="readonly"/>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品套餐类型：</dt>
				<dd>
					<select name="proCombo.comboType" class="required" rel="iselect" disabled="disabled">
						<option value="">请选择</option>
						<option value="combo_type_01" ${proCombo.comboType == 'combo_type_01' ? 'selected':'' }>基因套餐</option>
						<option value="combo_type_02" ${proCombo.comboType == 'combo_type_02' ? 'selected':'' }>癌筛套餐</option>
						<option value="combo_type_03" ${proCombo.comboType == 'combo_type_03' ? 'selected':'' }>分子套餐</option>
						<option value="combo_type_04" ${proCombo.comboType == 'combo_type_04' ? 'selected':'' }>其他套餐</option>
					</select>
				</dd>
			</dl>
				
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">说明：</dt>
				<dd>
					<input type="text" id="productNameId" name="proCombo.declare" value="${proCombo.declare }" class="textInput"  readonly="readonly"/>
				</dd>
			</dl>
			
			<fieldset id="applyInfo" style="width:100%;">
				<legend>【<span id="status_text" style="color:#ff0000;">产品信息列表明细</span>】</legend>
		  	    <table id="data_grid" class="list" style="width: 100%">
			  	    <thead>
						<tr>
							<th width="60px">序号</th>
							<th width="300px">品类名称</th>
							<th width="200px">产品类别</th>
							<th >产品规格</th>
						</tr>					
					</thead>
					<tbody id="proCombo_tbody_id">
						<c:forEach items="${products }" var="item" varStatus="stat">
						<tr count="${stat.index }" productId='${item.id }'>
							<td style="text-align: center;"><span name="numNo">${stat.count }</span></td>
							<td>
								<input name="proComboProducts[${stat.index }].id" type="hidden" value=""/>
								<input name="proComboProducts[${stat.index }].proComboId" type="hidden" value=""/>
								<input name="proComboProducts[${stat.index }].productId" type="hidden" value="${item.id }"/>
								${item.productName }
							</td>
							<td>${item.productTypeName }</td>
							<td>${item.standard }</td>
						</tr>
						</c:forEach>
					
					</tbody>
		   		</table>
   			</fieldset>
			
		</div>
		
	</form>
</div>
<!-- 验证提示需要; -->
<script src="${path}/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>

<script type="text/javascript">




</script>