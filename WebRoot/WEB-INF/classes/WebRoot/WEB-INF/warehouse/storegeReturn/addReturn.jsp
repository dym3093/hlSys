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
		<form id="return_add_from_id" 
		class="pageForm required-validate" 
		onsubmit="return validateCallback(this, navTabAjaxDone);" 
		action="${path}/warehouse/storegeReturn!addReturn.action" method="post" theme="simple"> 
    	<input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />					  
	    
	    <div class="pageFormContent" layoutH="40" style="overflow: hidden;">
	        <h1 class="press">退货信息管理</h1>
	        <div class="divider"></div>
	        <div class="tip"><span>退货信息录入</span></div>
			<div class="divider"></div>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">仓库名称：</dt>
				<dd>
					<select id="storegeId" name="storegeReturn.storegeId" rel="iselect" 
						loadUrl="${path}/warehouse/storegeReturn!getStoreWareHouse.action" class="required">
				    </select>
				</dd>
			</dl>
				
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品名称：</dt>
				<dd>
					<%-- <select id="productId" name="storegeReturn.productId" rel="iselect" 
						loadUrl="${path}/warehouse/storegeReturn!getProduct.action" class="required">
				    </select> --%>
				    <input type="hidden" id="storegeReturnProductId" name="storegeReturn.productId" bringbackname="warehouse.productId" />
	    			<input type="hidden" id="storegeReturnProductType" name="productType" bringbackname="warehouse.productType" />
	    			<input type="hidden" id="stroregeReturnStandard" name="standard" bringbackname="warehouse.standard" />
					<input type="text" id="storegeReturnPruductName" maxlength="30" name="" bringbackname="warehouse.productName" class="textInput required" readonly="readonly"/>
					<a class="btnLook" href="${ path }/warehouse/product!query.action" callback="pro_callback" lookupGroup="warehouse">查找带回</a>
					<img src="${path}/images/clear.png" title="清除产品信息" id="stroregeReturnClear" style="padding-top: 6px; cursor: pointer;"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">项目编码：</dt>
				<dd>
					<input type="text" id="projectCode" maxlength="30" name="storegeReturn.projectCode" 
						class="textInput required onblurClass"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">项目名称：</dt>
				<dd>
					<input type="text" id="projectName" maxlength="30" name="storegeReturn.projectName" 
						class="textInput required " readOnly="true"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">项目负责人：</dt>
				<dd>
					<input type="text" id="projectOwner" maxlength="30" name="storegeReturn.projectOwner" 
						class="textInput required" readOnly="true"/>
				</dd>
			</dl>
			<!-- <dl class="nowrap">
				<dt style="background-color:#FFFFFF;">规格：</dt>
				<dd>
					<input type="text" id="productNameId" maxlength="30" name="product.productName" 
						class="textInput required  specialSigl"/>
				</dd>
			</dl> -->
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">单价：</dt>
				<dd>
					<input type="text" id="unitPrice" maxlength="30" name="storegeReturn.unitPrice" 
						class="textInput required  number"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">退货数量：</dt>
				<dd>
					<input type="text" id="returnNumber" maxlength="30" name="storegeReturn.returnNumber" 
						class="textInput required " onkeyup="this.value=this.value.replace(/[^\d|chun]/g,'')"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">成本：</dt>
				<dd>
					<input type="text" id="cost" maxlength="30" name="storegeReturn.cost" 
						class="textInput required  number" readonly="readonly"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">快递公司：</dt>
				<dd>
					<input type="text" id="expressCompany" maxlength="30" name="storegeReturn.expressCompany" 
						class="textInput required  specialSigl"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">快递费用：</dt>
				<dd>
					<input type="text" id="expressCost" maxlength="30" name="storegeReturn.expressCost" 
						class="textInput required  number"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">总成本：</dt>
				<dd>
					<input type="text" id="totalCost" maxlength="30" name="storegeReturn.totalCost" 
						class="textInput required  number" readonly="readonly"/>
				</dd>
			</dl>
			<dl class="nowrap" id="cardStart">
				<dt style="background-color:#FFFFFF;">卡号开始：</dt>
				<dd>
					<input type="text" id="input_cardStart" maxlength="30" name="storegeReturn.cardStart" 
						class="textInput cardLength cardNum"/>
						<span class="info">卡号开始规则:总共12位,前六位为任意数字,后六位必须输入数字(如:111111000001)</span>
				</dd>
			</dl>
			<dl class="nowrap" id="cardEnd">
				<dt style="background-color:#FFFFFF;">卡号结束：</dt>
				<dd>
					<input type="text" id="input_cardEnd" maxlength="30" name="storegeReturn.cardEnd" 
						class="textInput cardLength cardNum"/>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">备注：</dt>
				<dd>
					<textarea id="remark" name="storegeReturn.remark" class="textInput specialSigl" rows="4" cols="80" maxlength="63"></textarea>
				</dd>
			</dl>
			
		</div>
		
		
	    <div class="formBar" style="width:98%">
	      <ul>
	        <li>
	            <button type="submit">保存</button>
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
$(document).ready(function(){
	$(".onblurClass").live("blur", function() {
		var $th = $(this);
		var proNum = $th.val(); //项目编码;
		var count = 0; //判断该项目编码出现次数;大于1了提示不能重复录入相同项目编码;
		
		//减少交互次数
		if(proNum== null || proNum == '') {
			return;
		}
		
		$(".onblurClass").each(function(){
			if($(this).val() == proNum) {
				count ++;
			}
		});
		
		if(count > 1) {
			alert("不能重复输入相同的项目编码"+proNum+"!");
			$th.val("");
			return;
		}
		
		var $thParent = $th.parent().parent();
		$.ajax({
		      type: "post",
		      cache :false,
		      dataType: "json",
		      url: "${path }/warehouse/storeApplication!getCrmBaseInfoByProCode.action",
		      data: {"proNum":proNum},
		      success: function(data){
		    	  var json =  data.result;
		    	  $("#projectOwner", navTab.getCurrentPanel()).val(json.projectOwner);//项目对接人
		    	  $("#projectName", navTab.getCurrentPanel()).val(json.projectName); //项目名称;
		      },
		      error :function(XMLHttpRequest, textStatus, errorThrown){
		      }    
		});
	});
});

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
	$("#unitPrice", navTab.getCurrentPanel()).blur(function() {
		var price = $(this).val();
		if(isNaN(price)) {
			return ;
		}
		if(price==""){
			return ;
		}
		$(this).val(parseFloat(price).toFixed(2));
		dealCalculate();
		
	});
	
	/**
	* 入库数量离开事件调用处理;
	*/
	$("#returnNumber", navTab.getCurrentPanel()).blur(function() {
		dealCalculate();
		//开始卡号
		var value = $("#input_cardStart", navTab.getCurrentPanel()).val();
		dealCardEnd(value);
	});
	
	/**
	* 快递费用离开事件调用处理;
	*/
	$("#expressCost", navTab.getCurrentPanel()).blur(function() {
		dealCalculate();
	});
	
	/**
	* 卡号开始离开事件;
	*/
	$("#input_cardStart", navTab.getCurrentPanel()).blur(function() {
		
		//当该字段满足所有条件后计算;
		var flag = true;
		var value = $(this).val();
		
		dealCardEnd(value);
	});
	
	/*
	 * 清除数据事件;
	 */
	$("#stroregeRenturnClear", navTab.getCurrentPanel()).click(function() {
		$("#storegeReturnProductId", navTab.getCurrentPanel()).val("");
		$("#storegeReturnPruductName", navTab.getCurrentPanel()).val("");
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
			var quantity = $("#returnNumber", navTab.getCurrentPanel()).val();
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
					$("#input_cardEnd", navTab.getCurrentPanel()).val(value.substring(0, 6) + str + "" + num);
				} else {
					alertMsg.info("入库数量没有输入,请输入你要入库的数量!");
				}
			}
			
		}
		
	}
}

 /*
  * 回调函数;
  */
 function pro_callback(obj) {
 	
 	var elementId = $(obj).attr("id");
 	if(elementId == "storegeReturnProductType") {
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
 		$("#cardStart", navTab.getCurrentPanel()).show();
 		$("#cardEnd", navTab.getCurrentPanel()).show();
 	} else {
 		$("#cardStart", navTab.getCurrentPanel()).hide();
 		$("#cardEnd", navTab.getCurrentPanel()).hide();
 		$("#input_cardStart", navTab.getCurrentPanel()).val("");
 		$("#input_cardEnd", navTab.getCurrentPanel()).val("");
 	}
 }
 
function submitForm() {
	$(".pageForm", navTab.getCurrentPanel()).submit();

}
function dealCalculate() {
	var price = $("#unitPrice", navTab.getCurrentPanel()).val();
	var quantity = $("#returnNumber", navTab.getCurrentPanel()).val();
	var expressCost = $("#expressCost", navTab.getCurrentPanel()).val();
	
	if(price=="" || quantity=="") {
		return ;
	}
	$("#cost", navTab.getCurrentPanel()).val((parseFloat(price) * parseFloat(quantity)).toFixed(2));
	if(expressCost==""){
		return ;
	}
	$("#totalCost", navTab.getCurrentPanel()).val(((parseFloat(price) * parseFloat(quantity))+parseFloat(expressCost)).toFixed(2));
}


</script>