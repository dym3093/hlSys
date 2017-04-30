<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

String id = (String)request.getAttribute("id");
%>

<style>
span.error {
	overflow: hidden;
	width: 165px;
	height: 20px;
	padding: 0 3px;
	line-height: 20px;
	background: #F00;
	color: #FFF;
	top: 5px;
	left: 318px;
	margin-top: 5px;
}

</style>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/jquery/json2.js" type="text/javascript"></script>
<!-- 验证提示需要; -->
<script src="${path}/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">

$(function() {
	dealPrice("comboPrice");
	dealPrice("comboPriceActual");

	$("#comboPriceActual", $.pdialog.getCurrent()).blur(function() {
		var price = $(this).val();
		if(price) {
			price = parseFloat(price).toFixed(2);
		}
		$(this).val(price);
	});
	
});

function dealPrice($id) {
	var price = $("#"+$id+"", $.pdialog.getCurrent()).val();
	if(price != null && price != '') {
		price = parseFloat(price).toFixed(2);
	} else {
		price = "0.00";
	}
	$("#"+$id+"", $.pdialog.getCurrent()).val(price);
}

</script>

	<form id="pagerDialogFindForm"
	method="post" 
	class="pageForm required-validate"
	action="${path}/settlementManagement/erpSettlementTaskBX!updateCustomerTask.action" 
	onsubmit="return validateCallback(this, dialogAjaxDone);"
	rel="pagerFindForm">
		<input type="hidden" id="navTabId" name="navTabId" value="${navTabId }"/>
		<input type="hidden" id="id" name="id" value="${customerSettle.id }" />
		
		<div class="pageContent" style="width: 100%; overflow:hidden;">
			
			<div class="pageFormContent" layouth="97" style="overflow:auto;">
				<dl>
					<dt style="background-color:#FFFFFF;">选择场次：</dt>
					<dd>
						<input type="text" id="eventsNo" name="eventsNo" value="${customerSettle.eventsNo }" readonly="readonly"/>
					</dd>
				</dl>
				
				<dl>
					<dt style="background-color:#FFFFFF;">条形码：</dt>
					<dd>
						<input type="text" id="code" name="code" value="${customerSettle.code }" readonly="readonly"/>
					</dd>
				</dl>
				
				<dl>
					<dt style="background-color:#FFFFFF;">套餐类型：</dt>
					<dd>
						<input type="text" id="combo" name="combo" value="${customerSettle.combo }" readonly="readonly"/>
					</dd>
				</dl>
				<dl>
					<dt style="background-color:#FFFFFF;">套餐价格：</dt>
					<dd>
						<input type="text" id="comboPrice" name="comboPrice" value="${customerSettle.comboPrice }" readonly="readonly"/>
					</dd>
				</dl>
				
				<dl>
					<dt style="background-color:#FFFFFF;">设置价格：</dt>
					<dd>
						<input type="text"  class="required number valid" id="comboPriceActual" name="comboPriceActual" value="${customerSettle.comboPriceActual }" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
					</dd>
				</dl>
				<dl>
					<dt style="background-color:#FFFFFF;">价格变更原因：</dt>
					<dd>
						<input type="text" id="remark" name="remark" value="${customerSettle.remark}" class="required textInput valid" maxlength="200"   /> 
					</dd>
				</dl>
			</div>
			
			<div class="formBar" style="margin-top: 55px;">
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
								<button type="button" class="close">返回</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			
		</div>
	</form>
	
