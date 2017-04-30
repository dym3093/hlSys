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
		action="${path}/warehouse/storegeOut!saveStoregeReback.action" method="post" theme="simple">
		
    	<input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />
	    <input type="hidden" name="storegeReback.id" id="storegeRebackId" value="${storegeReback.id }"/>
	    <input type="hidden" name="storegeReback.applicationId" id="applicationId" value="${storegeInOutVo.applicationId }"/>
	    <input type="hidden" name="storegeReback.storegeOutId" id="storegeOutId" value="${storegeInOutVo.id }"/>
	    <input type="hidden" name="storegeReback.productId" id="productId" value="${storegeInOutVo.productId }"/>
	    <input type="hidden" name="storegeReback.storegeInId" id="productId" value="${storegeInOutVo.storegeInId }"/>
	    <input type="hidden" id="quantityId" value="${storegeInOutVo.quantity }"/>
	    
	    <div class="pageFormContent" layoutH="40" style="overflow: hidden;">
	        <h1 class="press">退库信息管理</h1>
	        <div class="divider"></div>
	        <div class="tip"><span>退库信息录入</span></div>
			<div class="divider"></div>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">申请单号：</dt>
				<dd>
					<input type="text" value="${storegeInOutVo.applicationNo }" readonly="readonly"/>
				</dd>
			</dl>
				
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">仓库名称：</dt>
				<dd>
					<input type="text" id="productNameId" maxlength="30" value="${storegeInOutVo.storegeName }"  readonly="readonly"/>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品名称：</dt>
				<dd>
					<input type="text" name="storegeReback.productName" value="${storegeInOutVo.productName}" readonly="readonly"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">规格：</dt>
				<dd>
					<input type="text" name="storegeReback.standard" value="${storegeInOutVo.standard}" readonly="readonly"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">单价：</dt>
				<dd>
					<input type="text" name="storegeReback.price" value="${storegeInOutVo.price}" readonly="readonly" id="priceId"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">退库数量：</dt>
				<dd>
					<input type="text" name="storegeReback.quantity" value="" class="required numCompare" id="backQuantityId"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">成本：</dt>
				<dd>
					<input type="text" name="storegeReback.cost" value="" readonly="readonly" id="costId"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">卡号开始：</dt>
				<dd>
					<input type="text" name="storegeReback.cardStart" value="" id="cardStartId" class="cardLength cardNum"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">卡号结束：</dt>
				<dd>
					<input type="text" name="storegeReback.cardEnd" value="" readonly="readonly" id="cardEndId"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">备注：</dt>
				<dd>
					<input type="text" name="storegeReback.remark" value="" class="required"/>
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
	
	//失去事件调用;
	$("#backQuantityId", navTab.getCurrentPanel()).blur(function() {
		//计算成本; 单价*数量;
		
		var quantity = $(this).val();
		if(quantity != null && quantity != '') {
			var price = $("#priceId", navTab.getCurrentPanel()).val();
			var cost = parseFloat(quantity) * parseFloat(price);
			$("#costId", navTab.getCurrentPanel()).val(parseFloat(cost).toFixed(2));
		}
		
		var value= $("#cardStartId", navTab.getCurrentPanel()).val();
		if(value != null && value!= '') {
			dealCardEnd(value);			
		}
	});
	
	$("#cardStartId", navTab.getCurrentPanel()).blur(function() {
		var value= $(this).val();
		if(value != null && value!= '') {
			dealCardEnd(value);			
		}
	});
	
	//验证
	$.validator.addMethod("numCompare", function(value, element) {
		var flag = true;
		var quantityBack = $("#quantityId", navTab.getCurrentPanel()).val(); //发货数量
		if(value != null && value != '') {
			if(parseInt(quantityBack) < parseInt(value)) {
				flag = false;
			}
			
			var num = 0;
			
			//后台获取该发货数据的退库数量;
			$.ajax({
				type: "post",
				cache : false,
				async : false,
				data:{"id":$("#storegeOutId", navTab.getCurrentPanel()).val()},
				url: "${path }/warehouse/storegeOut!validBackNum.action",
				success: function(data){
					var data= eval("("+data+")");
					num = data.num; 
				},
				error :function(){
					alertMsg.alert("服务发生异常，请稍后再试！");
					return;
				}
			});
			
			if(num != null && num != '') {
				if(parseInt(quantityBack) < (parseInt(value) + parseInt(num))) {
					flag = false;
				}
			}
		}
		
		return this.optional(element) || flag;
	}, "退货数量大于了发货数量!");
	

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
			var quantity = $("#backQuantityId", navTab.getCurrentPanel()).val();
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
					$("#cardEndId", navTab.getCurrentPanel()).val(value.substring(0, 6) + str + "" + num);
				} else {
					alertMsg.info("入库数量没有输入,请输入你要入库的数量!");
				}
			}
			
		}
		
	}
}
</script>