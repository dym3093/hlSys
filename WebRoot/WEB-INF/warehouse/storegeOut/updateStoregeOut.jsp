<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<div class="pageContent">
	<form id="storegeRebackFrom_id" 
		class="pageForm required-validate" 
		onsubmit="return validateCallback(this, navTabAjaxDone);" 
		action="${path}/warehouse/storegeOut!update.action" method="post" theme="simple">
		
    	<input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />
	    <input type="hidden" name="storegeOut.id" id="storegeOutId" value="${storegeInOutVo.id }"/>
	    <input type="hidden" name="" id="costId" value="${storegeInOutVo.cost }"/>
	    
	    <div class="pageFormContent" layoutH="40" style="overflow: hidden;">
	        <div class="tip"><span>发货信息修改</span></div>
			<div class="divider"></div>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">申请单号：</dt>
				<dd>
					<div style="margin-top: 11px;">${storegeInOutVo.applicationNo }</div>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">仓库名称：</dt>
				<dd>
					<div style="margin-top: 11px;">${storegeInOutVo.storegeName }</div>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品名称：</dt>
				<dd>
					<div style="margin-top: 11px;">${storegeInOutVo.productName}</div>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">规格：</dt>
				<dd>
					<div style="margin-top: 11px;">${storegeInOutVo.standard}</div>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">单价：</dt>
				<dd>
					<div style="margin-top: 11px;">${storegeInOutVo.price}</div>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">数量：</dt>
				<dd>
					<div style="margin-top: 11px;">${storegeInOutVo.quantity }</div>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">总金额：</dt>
				<dd>
					<div style="margin-top: 11px;" id="amount_div_id">${storegeInOutVo.amount }</div>
					<input type="hidden" id="amount_input_id" name="storegeOut.amountBak" value="${storegeInOutVo.amount }" readonly="readonly"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">快递单号：</dt>
				<dd>
					<input type="text" name="storegeOut.expressNo" value="${storegeInOutVo.expressNo }" class="textInput"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">快递公司：</dt>
				<dd>
					<input type="text" name="storegeOut.expressName" value="${storegeInOutVo.expressName }" class="textInput"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">快递费：</dt>
				<dd>
					<input type="text" name="storegeOut.expressMoneyBak" onblur="dealAmount(this);" value="${storegeInOutVo.expressMoney }" class="textInput number"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">备注：</dt>
				<dd>
					<input type="text" name="storegeOut.remark" value="${storegeInOutVo.remark }" class="textInput"/>
				</dd>
			</dl>
			
		</div>
		
	    <div class="formBar" style="width:98%">
	      <ul>
	        <li>
	          <div class="buttonActive">
	            <div class="buttonContent">
	              <button type="submit">保存</button>
	            </div>
	          </div>
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

<script type="text/javascript">
function dealAmount(obj) {
	var thisVal = $(obj).val();
	if(!isNaN(thisVal)) {
		var cost  = $("#costId").val();
		var amount = parseFloat(thisVal) + parseFloat(cost);
		$("#amount_div_id").html(parseFloat(amount).toFixed(2));
		$("#amount_input_id").val(parseFloat(amount).toFixed(2));
		$(obj).val(parseFloat(thisVal).toFixed(2));
	}
	
}

$(function() {
	
});

</script>