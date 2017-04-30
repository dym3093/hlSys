<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
	<div class="tip">
		<span>查询</span>
	</div>

	<div class="pageHeader" style="overflow: hidden;">
		<form id="productForm_id" 
		onsubmit="return navTabSearch(this);" 
		action="${path}/warehouse/product!list.action" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tbody>
					<tr>
						<td>
							<label>产品名称：</label><input type="text" name="params.productName" class="textInput" value="${params.productName }">
						</td>
						<td>
							<label>产品类型：</label>
							<select id="productType" name="params.productType" rel="iselect" 
								loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10107" >
							
					    		<option value="${params.productType }"></option>
					    	</select>
						</td>
						<td >
							<div class="subBar">
								<ul style="float: left">
									<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
									<li><a onclick="resetDo();" href="javascript:;" class="button"><span>重置</span></a></li>
								</ul>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
		</div>
		</form>
	</div>

	<div class="pageContent j-resizeGrid">
		<div class="panelBar">
			<ul class="toolBar">
				<li class=""><a class="add" href="javascript:void(0);" onclick="add();"><span>新增</span></a></li>
				<li class=""><a class="edit" href="javascript:void(0);" onclick="update();"><span>修改</span></a></li>
				<li class=""><a class="delete" href="javascript:void(0);" onclick="del();"><span>删除</span></a></li>
				<li class="line">line</li>
			</ul>
			<jsp:include page="/common/pagination.jsp" />
		</div>
			<table class="list" style="width:100%; overflow: auto" layoutH="108">
				<thead>
					<tr>
						<th style="width: 45px; ">序号</th>
						<th style="width: 200px; ">产品名称</th>
						<th style="width: 200px; ">产品类别</th>
						<th style="width: 300px; ">产品规格</th>
						<th>产品描述</th>
						<th style="width: 100px; ">产品图片</th>
						<th style="width: 80px">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.results }" var="item" varStatus="stat">
						<tr target="sid_user" rel="${item.id }" >
							<td><input type="checkbox" name="ids" class="" value="${item.id }"/>${stat.count }</td>
							<td><a href="${path }/warehouse/product!view.action?productId=${item.id}" target="navTab" title="浏览">${item.productName }</a></td>
							<td>${item.productTypeName }</td>
							<td>${item.standard }</td>
							<td>${item.describe }</td>
							<td>
								<c:if test="${item.imagePath == null || item.imagePath=='' }">无</c:if>
								<c:if test="${item.imagePath != null }"><a href="javascript:void(0);" onclick="showImage('${item.id }', '${item.productName }');">图片查看</a></c:if>
								
							</td>
							<td>
								<a onclick="dealIsClose('${item.id }', '${item.isClose }');" href="javascript:;" class="button"><span id="${item.id }">${item.isClose != "1"? "关闭": "启用"}</span></a>
								
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	
	</div>

<script type="text/javascript">

function showImage(id, productName) {
	$.pdialog.open("${path}/warehouse/product!imageJsp.action?id="+id, "imageshowid", productName, 
		{
			mask:true, 
        	width:600, height:500, 
        	maxable: false, 
        	resizable:false 
    	});
}

/*关闭或者启用;*/
function dealIsClose(id, status) {
	var htmlStr = "";
	if(status != 1) {
		status = "1";
		htmlStr = "启用";
	} else {
		status = "0";
		htmlStr = "关闭";
	}
	
	$.ajax({	//初始化页面时的省份
		type: "post",
		cache :false,
		async : false, //同步请求;
		data:{"id":id, "status": status},
		url: "${path }/warehouse/product!dealIsClose.action",
		success: function(data){
			var data= eval("("+data+")");
			if(!data.result){
				alertMsg.warn("后台处理异常!");
			}
			navTabPageBreak({pageNum: "${page.pageNum}", numPerPage: "${page.pageSize}"});
		},
		error :function(){
			alertMsg.alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

/*输入框重置方法*/
function resetDo() {
	$("#productForm_id :input").each(function() {
		$(this).val("");
	}); 
	$("#productForm_id :select").each(function() {
		$(this).val("");
	});
}
	
	/**删除*/
	function del() {
		var count = 0;
		var ids = "";
		
		$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
			if(ids.length > 0) {
				ids += ", " +val.value  ;
			} else {
				ids += val.value
			}
			count ++;
			
		});
		
		if(count == 0) {
			alertMsg.warn("请选择你要删除的信息！");
			return;
		}
		
		/*验证该数据是否被引用,如果被引用则不能删除;*/
		var flagDel = false;		
		$.ajax({	//初始化页面时的省份
			type: "post",
			cache :false,
			async : false, //同步请求;
			data:{"ids":ids},
			url: "${path }/warehouse/product!validDel.action",
			success: function(data){
				var data= eval("("+data+")");
				if(!data.result){
					flagDel = true;
				}
			},
			error :function(){
				alertMsg.alert("服务发生异常，请稍后再试！");
				return;
			}
		});
		
		if(flagDel) {
			alertMsg.info("选中删除数据中存在已被使用的数据不能删除!");
			return;
		}

		alertMsg.confirm("确定要删除选中的数据吗?", {
			okCall: function(){
				$.ajax({	//初始化页面时的省份
					type: "post",
					cache :false,
					data:{"ids":ids},
					url: "${path }/warehouse/product!delete.action",
					success: function(data){
						var data= eval("("+data+")");
						if(data.result){
							alertMsg.info("数据删除成功!");
						}else {
							alertMsg.error("数据删除失败!");
						}
						
						navTabPageBreak();
					},
					error :function(){
						alertMsg.alert("服务发生异常，请稍后再试！");
						return;
					}
				});
			}
		});
		
	}

	function add() {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("productAdd", "${path }/warehouse/product!edit.action?navTabId="+navTabId, { title:"产品信息新增", fresh:false, data:{} });
	}
	
	function update(){
		var count = 0;
		var ids = "";
		
		$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
			ids = val.value ;
			count ++;
			
		});
		
		if(count == 0) {
			alertMsg.warn("请选择你要变更的信息！");
			return;
		} else if (count > 1) {
			alertMsg.warn("只能选择一条信息进行修改！");
			return;
		}
		
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("productUpdate", "../warehouse/product!edit.action?productId="+ids+"&navTabId="+navTabId, { title:"产品信息修改", fresh:false, data:{} });
	}
</script>