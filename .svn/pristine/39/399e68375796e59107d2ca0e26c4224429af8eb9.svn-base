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
	<form id="queryErpProductSelect_id" onsubmit="return dialogSearch(this);" action="${path}/warehouse/proCombo!addProduct.action" method="post" rel="pagerForm">
	<input type="hidden" name="params.isClose" value="0"/>
	<div class="searchBar">
		<table class="searchContent">
			<tbody>
				<tr>
					<td>
						<label>产品类别：</label>
						<select id="productType" name="params.productType" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10107" >
				    		<option value="${params.productType }"></option>
				    	</select>
					</td>
					<td>
						<label>产品名称：</label>
						<input type="text" name="params.productName" class="textInput" value="${params.productName }">
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
				<th style="width: 50px; ">序号</th>
				<th style="width: 100px; ">产品类别</th>
				<th style="width: 150px; ">产品名称</th>
				<th style="width: 100px; ">产品图片</th>
				<th style="width: 200px; ">产品规格</th>
				<th>产品描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results }" var="item" varStatus="stat">
				<tr target="sid_user" rel="${item.id }" >
					<td>
						<input type="checkbox" name="ids" id="${item.id }" productTypeName="${item.productTypeName }" productName="${item.productName }" standard="${item.standard }"/>
						${stat.count }
				 	</td>
					<td>${item.productTypeName }</td>
					<td>${item.productName }</td>
					<td>
						<c:if test="${item.imagePath == null || item.imagePath=='' }">无</c:if>
								<c:if test="${item.imagePath != null }"><a href="javascript:void(0);" onclick="showImage('${item.id }', '${item.productName }');">图片查看</a></c:if>
					</td>
					<td>${item.standard }</td>
					<td>${item.describe }</td>
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
/*图片显示*/
function showImage(id, productName) {
	$.pdialog.open("${path}/warehouse/product!imageJsp.action?id="+id, "imageshowid", productName+"图片显示", 
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
		$("#proCombo_tbody_id", navTab.getCurrentPanel()).find("tr").each(function(index) {
			trCount = index+1;
			var countAttr  = $(this).attr("count");
			//作为标记,好在下面使用list数据下标;
			if(count <= countAttr) {
				count = countAttr;
			}
			
			productIdArr.push($(this).attr("productId"));
		}) ;
		
		var tbodyStr = "";
		$("input:checkbox[name='ids']:checked", $.pdialog.getCurrent()).each(function(index) {
			
			var productId = $(this).attr("id");
			var productTypeName = $(this).attr("productTypeName");
			var productName = $(this).attr("productName");
			var standard = $(this).attr("standard");
			
			//如果产品信息列表中已存在该产品了,就不在添加;
			var flag = true; //标示符;
			if(productIdArr != null && productIdArr.length > 0) {
				for(var i = 0 ; i < productIdArr.length; i++) {
					//相等标示已存在相同的产品.不在生成行;
					if(productId == productIdArr[i]) {
						flag = false;
						break;
					}
				}
				
			}
			
			if(flag) {
				
				var index = parseInt(count) + 1 + index;
				tbodyStr += "<tr count='"+index+"' productId='"+productId+"'>" +
				"<td style='text-align: center;'>" +
				"<input type='hidden' name='proComboProducts["+index+"].id' value=''/>" +
				"<input type='hidden' name='proComboProducts["+index+"].proComboId' value=''/>" +
				"<input type='hidden' name='proComboProducts["+index+"].productId' value='"+productId+"'/>" +
				(trCount+1)+"</span></td>" +
				"<td>"+productName+"</td>" +
				"<td>"+productTypeName+"</td>" +
				"<td>"+standard+"</td>" +
				"<td align='center'><a href='javascript: void(0);' class='btnDel' style='margin-left: 17px;'>删除</a></td>" +
				"</tr>"	;
				
				trCount++ ; //自动加一
			}
			
		});
		$("#proCombo_tbody_id", navTab.getCurrentPanel()).append(tbodyStr);
		
		cancel();
	}
	
	function cancel() {
		$.pdialog.closeCurrent();
	}
</script>
