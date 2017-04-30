<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>    

<script type="text/javascript">
</script>
<style>
table td{
word-break: keep-all;
white-space:nowrap;
}
table th{
word-break: keep-all;
white-space:nowrap;
}
</style>
<div class="pageHeader" style="overflow: hidden;">
	<form id="queryErpProductSelect_id" onsubmit="return dialogSearch(this);" action="${path}/warehouse/application!addProduct.action" method="post" rel="pagerForm">
	<input type="hidden" name="params.isClose" value="0"/>
	<div class="searchBar">
		<table class="searchContent">
			<tbody>
				<tr>
					<td>
						<label>产品套餐名称：</label>
						<input type="text" name="params.productComboName" style="margin-top: 2px;" class="textInput" value="${params.productComboName }">
					</td>
					<td style="text-align: right">
						<label>产品套餐类型：</label>
						<select name="params.comboType" style="width:194px; margin-top: 2px;">
							<option value="">请选择</option>
							<option value="combo_type_01" ${params.comboType == 'combo_type_01' ? 'selected':'' }>基因套餐</option>
							<option value="combo_type_02" ${params.comboType == 'combo_type_02' ? 'selected':'' }>癌筛套餐</option>
							<option value="combo_type_03" ${params.comboType == 'combo_type_03' ? 'selected':'' }>分子套餐</option>
							<option value="combo_type_04" ${params.comboType == 'combo_type_04' ? 'selected':'' }>其他套餐</option>
						</select>
					</td>
					<td >
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
							</ul>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/paginationDialog.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="90" >
		<thead>
			<tr>
				<th style="width: 45px; ">序号</th>
				<th>产品套餐名称</th>
				<th>产品套餐类型</th>
				<th style="width: 300px; ">说明</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results }" var="item" varStatus="stat">
				<tr>
					<td style="width: 45px; ">
						<input type="checkbox" name="ids" id="${item.id }" proComboName="${item.productComboName }"/>
						${stat.count }
				 	</td>
					<td><img  name="proCombo_icon_name" src="${path}/images/ico04.gif" style="cursor: pointer; margin-left: 4px;"></img><span style="margin-left: 4px;">${item.productComboName }</span></td>
					<td style="width: 180px; ">
						<c:if test="${item.comboType == 'combo_type_01' }">基因套餐</c:if>
						<c:if test="${item.comboType == 'combo_type_02' }">癌筛套餐</c:if>
						<c:if test="${item.comboType == 'combo_type_03' }">分子套餐</c:if>
						<c:if test="${item.comboType == 'combo_type_04' }">其他套餐</c:if>
					</td>
					<td style="width: 300px; ">${item.declare }</td>
					
				</tr>
				<tr name="hidTr_name" style="width: 100%">
					<td style="width: 45px; ">&nbsp;</td>
					<td colspan="2">
						<table width="100%" class="list">
							<tr style="background-color: #DDE7F4;">
								<th style="width: 45px;">序号</th>
								<th>产品名称</th>
								<th>产品图片</th>
							</tr>
							<c:forEach items="${item.proDetails }" var="obj" varStatus="statObj">
								<tr>
									<td style="text-align: center;">${statObj.count }</td>
									<td>${obj.productName }</td>
									<td>
										<c:if test="${obj.imagePath == null || obj.imagePath=='' }">无</c:if>
										<c:if test="${obj.imagePath != null }"><a href="javascript:void(0);" onclick="showImage('${obj.id }', '${obj.productName }');">图片查看</a></c:if>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div style="width: 100%" >
	<a class="button" onclick="cancel()" href="javascript:void(0);" style="float:right; margin-top: 4px;"><span>取消</span></a>
	<a class="button" onclick="sure()" href="javascript:void(0);" style="float:right; margin-top: 4px; margin-right: 10px;"><span>确定</span></a>
</div>

<script type="text/javascript">
$(function() {
	$("tr[name='hidTr_name']").each(function() {
		$(this).hide();
	});
	
	$("tr[name='hidTr_name']").on("click", function() {
		$(this).css("background-color", "#F8F8F8");
	});
	
	$("img[name='proCombo_icon_name']").on("click", function() {
		var icom3 = $(this).attr("src");
		if(icom3.indexOf("ico03.gif")>1) {
			$(this).attr("src",  "${path}/images/ico04.gif");
			//关闭已显示行的tr;隐藏;
			$(this).parent().parent().next().hide();
			return ;
		}
		
		//恢复所有的向下图标;
		$("img[name='proCombo_icon_name']").each(function() {
			var icom3 = $(this).attr("src");
			if(icom3.indexOf("ico03.gif")>1) {
				$(this).attr("src",  "${path}/images/ico04.gif");
			}
		});
		//关闭其他已显示行的tr;隐藏;
		$("tr[name='hidTr_name']").each(function() {
			$(this).hide();
		});
		
		//图标更换;
		$(this).attr("src", "${path}/images/ico03.gif");
		
		//显示当前选中行next行显示数据;
		$(this).parent().parent().next().show();
		
	});
});

function showImage(id, productName) {
	$.pdialog.open("${path}/warehouse/product!imageJsp.action?id="+id, "imageshowid", productName, 
		{
			mask:true, 
        	width:600, height:500, 
        	maxable: false, 
        	resizable:false 
    	});
}

function sure() {
	//处理选中数据;
	var count = 0;
	$("input:checkbox[name='ids']:checked", $.pdialog.getCurrent()).each(function(index, val) {
		count ++;
	});
	
	if(count == 0) {
		alertMsg.warn("请选择一条或多条产品信息！");
		return;
	}
	var trCount = 0;
	var count  = -1; //修改时作为索引判断;
	var productIdArr = [];
	var proComboIdArr = [];
	$("#application_tbody_id", navTab.getCurrentPanel()).find("tr").each(function(index) {
		trCount = index+1;
		var countAttr  = $(this).attr("count");
		//作为标记,好在下面使用list数据下标;
		if(count <= countAttr) {
			count = countAttr;
		}
		
		productIdArr.push($(this).attr("proComboId"));
	}) ;
	var tbodyStr = "";
	var validIsExictProduct = true;
	$("input:checkbox[name='ids']:checked", $.pdialog.getCurrent()).each(function(index) {
		
		var proComboId = $(this).attr("id");
		var proComboName = $(this).attr("proComboName");
		
		//如果产品信息列表中已存在该产品了,就不在添加;
		var flag = true; //标示符;
		if(productIdArr != null && productIdArr.length > 0) {
			for(var i = 0 ; i < productIdArr.length; i++) {
				//相等标示已存在相同的产品.不在生成行;
				if(proComboId == productIdArr[i]) {
					flag = false;
					break;
				}
			}
			
			
		}
		
		if(flag) {
			proComboIdArr.push(proComboId);
			
			$.ajax({
				type: "post",
				cache : false,
				async : false,
				data:{"proComboId":proComboId},
				url: "${path }/warehouse/application!ajaxProductComboDetail.action",
				success: function(data){
					var data= eval("("+data+")");
					
					if(data.result != null || data.result != '') {
						var arrs = data.result ;
						
						for(var i=0; i<arrs.length; i++){
							var index = parseInt(++count);
							tbodyStr += "<tr count='"+index+"' proComboId='"+proComboId+"'>" +
							"<td style='text-align: center;'>" + 
							"<input type='hidden' name='details["+index+"].proComboId' value='"+proComboId+"'/>"+
							"<input type='hidden' name='details["+index+"].productId' value='"+arrs[i].id+"'/><span name='numNo'>" +
							(trCount+1)+"</span></td>" +
							"<td>"+proComboName+"</td>" +
							"<td>"+arrs[i].productName+"</td>" +
							"<td>"+arrs[i].productTypeName+"</td>" +
							"<td>"+arrs[i].standard+"</td>" +
							"<td><input type='text' name='details["+index+"].applicationCountInt' value='' class='required textInput digits'/></td>" +
							"<td align='center'><a href='javascript: void(0);' class='btnDel' style='margin-left: 17px;'>删除</a></td>" +
							"</tr>"	;
							
							trCount++ ; //自动加一
						}
						
					} else {
						alertMsg.warn("您选择的产品套餐中不存在产品!");
					}
				},
				error :function(){
					alertMsg.alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
		
	});
	
	//验证去后台判断,当前所选择的数据是否包含相同的产品id.如果存在则不能被选中,并给予提示是;
	
	var ids = "";
	var areadyIds = "";
	//选中的套餐id
	for(var i=0; i<proComboIdArr.length; i++) {
		if(i == 0) {
			ids += proComboIdArr[i];
		} else {
			ids += ","+proComboIdArr[i];
		}
	}
	//已选择在列表中的套餐;
	for(var i=0; i<productIdArr.length; i++) {
		if(i == 0) {
			areadyIds += productIdArr[i];
		} else {
			areadyIds += ","+productIdArr[i];
		}
	}
	
	$.ajax({
		type: "post",
		cache : false,
		async : false,
		data:{"ids":ids, "areadyIds": areadyIds},
		url: "${path }/warehouse/application!validProductEqual.action",
		success: function(data){
			var data= eval("("+data+")");
			
			console.info(data.result);
			
			if(data.result) {
				$("#application_tbody_id", navTab.getCurrentPanel()).append(tbodyStr);
				if(validIsExictProduct) {
					cancel();		
				}
			} else {
				alertMsg.warn("您选择的产品套餐中包含相同的产品,所以不能添加,请确认要选择的套餐!");
			}
		},
		error :function(){
			alertMsg.alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	
}
	
function cancel() {
	$.pdialog.closeCurrent();
}
</script>
