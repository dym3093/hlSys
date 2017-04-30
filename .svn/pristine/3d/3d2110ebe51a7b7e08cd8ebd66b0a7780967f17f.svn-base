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
					<input name="proCombo.productComboName" value="${proCombo.productComboName }" class="textInput required exitProCombo"/>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">产品套餐类型：</dt>
				<dd>
					<select name="proCombo.comboType" class="required" rel="iselect">
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
					<input type="text" id="productNameId" name="proCombo.declare" value="${proCombo.declare }" class="textInput"/>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF; padding-left: 14px;"><a class="button" href="javascript:void(0);" onclick="addProduct();"><span>选择产品</span></a></dt>
				<dd></dd>
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
							<th width="50px">操作</th>
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
							<td><a href='javascript: void(0);' class='btnDel' style='margin-left: 17px;'>删除</a></td>
						</tr>
						</c:forEach>
					
					</tbody>
		   		</table>
   			</fieldset>
			
		</div>
		
	    <div class="formBar" style="width:98%">
	    	<ul>
		        <li>
		            <div class="button">
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
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>

<script type="text/javascript">
$(function() {
	//明细数据删除操作
	$(".btnDel").live("click", function(){
		$(this).parent().parent().remove();
		//处理序号;
		var trCount = 0;
		$("#proCombo_tbody_id", navTab.getCurrentPanel()).find("tr").each(function(index) {
			trCount = index+1;
			$(this).find("span[name='numNo']").html(trCount);
		}) ;
		
	});
});

/**
 * 产品弹出框;
 */
function addProduct() {
	//获取当前页面id
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	$.pdialog.open(
		"${path}/warehouse/proCombo!addProduct.action?params.isClose=0&navTabId="+navTabId, 
		"addProductDetail01", 
		"产品信息列表", 
		{width:800,height:400,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true}
	);
}

$.validator.addMethod("exitProCombo", function(value, element) {
	
	var id = $("#proComboId", navTab.getCurrentPanel()).val();
	var exits = true;
	//后台请求查看是否已经存在了
	$.ajax({
		type: "post",
		cache : false,
		async : false,
		data:{"id":id, "productComboName": value},
		url: "${path }/warehouse/proCombo!exitsObject.action",
		success: function(data){
			var data= eval("("+data+")");
			exits = !data.result; 
		},
		error :function(){
			alertMsg.alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	
	return this.optional(element) || exits;
}, "输入的产品名称已存在!");



</script>