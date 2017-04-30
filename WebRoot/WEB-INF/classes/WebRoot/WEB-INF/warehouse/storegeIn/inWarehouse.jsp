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
		action="${path}/warehouse/storegeIn!saveOrUpdateStoIn.action" method="post" theme="simple">
		
    	<input type="hidden" id="navTabId" name="navTabId" value="${navTabId }" />					  
	    <input type="hidden" id="storegeInId" name="stroregeIn.id" value="${stroregeIn.id }"/>
	    <input type="hidden" id="stroregeInStoregeId" name="stroregeIn.storegeId" value="${warehouseId }"/>
	    
	    <div class="pageFormContent" layoutH="40" style="overflow: hidden;">
	        <h1 class="press">产品入库管理</h1>
	        <div class="divider"></div>
	        <div class="tip"><span>产品入库录入</span></div>
			<div class="divider"></div>
				
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品名称：</dt>
				<dd>
				    <input type="hidden" id="stroregeInProductId" name="stroregeIn.productId" bringbackname="warehouse.productId" value="${stroregeIn.productId }"/>
					<input type="hidden" id="stroregeInStandard" name="stroregeIn.standard" bringbackname="warehouse.standard"  value="${stroregeIn.standard }"/>
	    			<input type="hidden" id="stroregeInDescribe" name="stroregeIn.describe" bringbackname="warehouse.describe"  value="${stroregeIn.describe }"/>
	    			<input type="hidden" id="stroregeInProductType" name="stroregeIn.productType" bringbackname="warehouse.productType" value="${stroregeIn.productType }"/>
	    			
					<input type="text" id="stroregeInPruductName" maxlength="30" name="" bringbackname="warehouse.productName" value="${stroregeIn.productName }" class="textInput required" readonly="readonly"/>
					<a class="btnLook" href="${ path }/warehouse/product!query.action" callback="pro_callback" lookupGroup="warehouse">查找带回</a>
					<img src="${path}/images/clear.png" title="清除产品信息" id="stroregeInClear" style="padding-top: 6px; cursor: pointer;"/>
				</dd>
			</dl>
			<%-- 
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">供货商：</dt>
				<dd>
					<input type="text" id="stroregeInSupplierId" maxlength="30" name="" bringbackname="warehouse.supplierName" value="${stroregeIn.supplierName }" class="textInput required" readonly="readonly"/>
					<input type="hidden" id="stroregeInSupplierName" name="stroregeIn.supplierId" bringbackname="warehouse.supplierId" value="${stroregeIn.supplierId}"/>
					<a class="btnLook" href="${ path }/warehouse/supplier!query.action" callback="pro_callback" lookupGroup="warehouse">查找带回</a>
					<img src="${path}/images/clear.png" title="清除供货商信息" id="stroregeInClear" style="padding-top: 6px; cursor: pointer;"/>
				</dd>
			</dl> --%>

			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">单价：</dt>
				<dd>
					<input type="text" id="stroregeInPrice" name="stroregeIn.priceBak" value="${stroregeIn.price == null ? 0.00 : stroregeIn.price }" class="textInput required number" maxlength="16"/>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">入库数量：</dt>
				<dd>
					<input type="text" id="stroregeInQuantity" name="stroregeIn.quantity" value="${stroregeIn.quantity == null ? 0 : stroregeIn.quantity }" maxlength="16" class="textInput required" onkeyup="this.value=this.value.replace(/[^\d|chun]/g,'')"  />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">总价：</dt>
				<dd>
					<input type="text" id="stroregeInAmount" name="stroregeIn.amountBak" value="${stroregeIn.amount == null ? 0.00 : stroregeIn.amount }" class="textInput" readonly="readonly"/>
				</dd>
			</dl>
			
			<dl class="nowrap" id="cardNum">
				<dt style="background-color:#FFFFFF;">卡号：</dt>
				<dd>
					<input type="text" id="stroregeInCardNum" name="stroregeIn.cardNum" value="${stroregeIn.cardNum }" class="textInput"/>
				</dd>
				
			</dl>
			
			<dl class="nowrap" id="cardStart">
				<dt style="background-color:#FFFFFF;">卡号开始：</dt>
				<dd>
					<input type="text" id="stroregeInCardStart" name="stroregeIn.cardStart" value="${stroregeIn.cardStart }" class="textInput cardLength cardNum"/>
					<span class="info">卡号开始规则:总共12位,前六位为任意数字,后六位必须输入数字(如:111111000001)</span>
				</dd>
			</dl>
			<dl class="nowrap" id="cardEnd">
				<dt style="background-color:#FFFFFF;">卡号结束：</dt>
				<dd>
					<input type="text" id="stroregeInCardEnd" name="stroregeIn.cardEnd" readonly="readonly" value="${stroregeIn.cardEnd }" class="textInput"/>
				</dd>
			</dl>

			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">供货商：</dt>
				<dd>
					<input type="hidden" id="supplierId" maxlength="60" name="stroregeIn.supplierId" bringbackname="wareHouse.supplierId" value="${stroregeIn.supplierId}" />
					<input type="text" id="supplier" maxlength="60" name="supplierName" bringbackname="wareHouse.supplierName" value="${supplierName}" class="textInput required" readonly="readonly"/>
					<a class="btnLook" href="${ path }/warehouse/supplier!querySupplier.action" callback="pro_callback" lookupGroup="wareHouse">查找带回</a>
					<img src="${path}/images/clear.png" title="清除供货商信息" id="supplierIdClear" style="padding-top: 6px; cursor: pointer;"/>
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
$(function() {
	
	//自定义验证;cardValid
	$.validator.addMethod("cardLength",function(value,element){
			var flag = true;
			if(value != null && value.length != 12) { //满足条件;
				flag = false;
			}
			return this.optional(element)||flag;
		},
		"请输入长度为12位的字符串"
	);
	
	$.validator.addMethod("cardNum",function(value,element){
			var flag = true;
			if(value != null && value.length == 12) { //满足条件;
				//判断后六位;
				var val = value.substring(6, 12);
				if(isNaN(val)) {
					flag = false;
				}
			}
			return this.optional(element)||flag;
		},
		"后六位必须输入数字"
	);
	
	/*
	* 当选择产品信息类型为卡的类型,及编码为 1010701;显示       卡号/卡号开始/卡号结束
	*/
	if("${stroregeIn.productType }" == "1010701") { 
		dealCard(true);
	} else {
		dealCard(false);
	}
	
	/**
	* 价格离开事件调用处理;
	*/
	$("#stroregeInPrice", navTab.getCurrentPanel()).blur(function() {
		var price = $(this).val();
		if(isNaN(price)) {
			return ;
		}
		$(this).val(parseFloat(price).toFixed(2));
		dealCalculate();
		
	});
	
	/**
	* 入库数量离开事件调用处理;
	*/
	$("#stroregeInQuantity", navTab.getCurrentPanel()).blur(function() {
		dealCalculate();
		//开始卡号
		var value = $("#stroregeInCardStart", navTab.getCurrentPanel()).val();
		dealCardEnd(value);
	});
	
	/**
	* 卡号开始离开事件;
	*/
	$("#stroregeInCardStart", navTab.getCurrentPanel()).blur(function() {
		
		//当该字段满足所有条件后计算;
		var flag = true;
		var value = $(this).val();
		
		dealCardEnd(value);
	});
	
	/*
	 * 清除数据事件;
	 */
	$("#stroregeInClear", navTab.getCurrentPanel()).click(function() {
		$("#stroregeInProductId", navTab.getCurrentPanel()).val("");
		$("#stroregeInStandard", navTab.getCurrentPanel()).val("");
		$("#stroregeInDescribe", navTab.getCurrentPanel()).val("");
		$("#stroregeInPruductName", navTab.getCurrentPanel()).val("");
		$("#stroregeInSupplierId", navTab.getCurrentPanel()).val("");
		$("#stroregeInSupplierName", navTab.getCurrentPanel()).val("");
		dealCard(false);
	});
});

/**
 * value卡号值;
 */
function dealCardEnd(value) {
	var val = "";
	if(value != null && value.length == 12) { //满足条件;
		//判断后六位;
		val = value.substring(6, 12);
		if(!isNaN(val)) {
			//入库数量;
			var quantity = $("#stroregeInQuantity", navTab.getCurrentPanel()).val();
			//计算卡号结束;
			var num = parseInt(val)+parseInt(quantity);
			//不管开始为是几,只要相加和大于0,都要自减去1
			if(num > 0) {
				num -- ;
			}
			var len = (num+"").length;
			if( len < 6) { //前面补0; 当超过999999时暂时不考虑;
				var count = 6 - len;
				var i = 0; 
				var str = "";
				while(i < count) {
					str += "0";
					i++;
				}
				if(parseInt(quantity) >0) {
					$("#stroregeInCardEnd", navTab.getCurrentPanel()).val(value.substring(0, 6) + str + "" + num);
				} else {
					alertMsg.info("入库数量没有输入,请输入你要入库的数量!");
				}
			}
			
		}
		
	}
}

function dealCalculate() {
	var price = $("#stroregeInPrice", navTab.getCurrentPanel()).val();
	var quantity = $("#stroregeInQuantity", navTab.getCurrentPanel()).val();
	
	if(isNaN(price) && isNaN(quantity)) {
		return ;
	}
	
	$("#stroregeInAmount", navTab.getCurrentPanel()).val((parseFloat(price) * parseFloat(quantity)).toFixed(2));
}

/*
 * 回调函数;
 */
function pro_callback(obj) {
	
	var elementId = $(obj).attr("id");
	if(elementId == "stroregeInProductType") {
		/*
		* 当选择产品信息类型为卡的类型,及编码为 1010701;显示       卡号/卡号开始/卡号结束
		*/
		if($(obj).val() == "1010701") { 
			dealCard(true);
		} else {
			dealCard(false);
		}
	}
}

/*
 * type false 标示隐藏, true标示显示
 */
function dealCard(type) {
	if(type) {
		$("#cardNum", navTab.getCurrentPanel()).show();
		$("#cardStart", navTab.getCurrentPanel()).show();
		$("#cardEnd", navTab.getCurrentPanel()).show();
	} else {
		$("#cardNum", navTab.getCurrentPanel()).hide();
		$("#cardStart", navTab.getCurrentPanel()).hide();
		$("#cardEnd", navTab.getCurrentPanel()).hide();
	}
}

</script>