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
		<form  
		id="pagerFindForm"
		onsubmit="return navTabSearch(this);" 
		action="${path}/warehouse/proCombo!list.action" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tbody>
					<tr>
						<td>
							<label>产品套餐名称：</label><input type="text" name="params.productComboName" class="textInput" value="${params.productComboName }">
						</td>
						<td>
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
									<li><a onclick="resetDoProComb();" href="javascript:;" class="button"><span>重置</span></a></li>
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
				<li class=""><a class="add" href="javascript:void(0);" onclick="addProComb();"><span>新增</span></a></li>
				<li class=""><a class="edit" href="javascript:void(0);" onclick="updateProComb();"><span>修改</span></a></li>
				<li class=""><a class="delete" href="javascript:void(0);" onclick="delProComb();"><span>删除</span></a></li>
				<li class="line">line</li>
			</ul>
			<jsp:include page="/common/pagination.jsp" />
		</div>
			<table class="list" style="width:100%; overflow: auto" layoutH="108">
				<thead>
					<tr>
						<th style="width: 45px; ">序号</th>
						<th>产品套餐名称</th>
						<th style="width: 253px">产品套餐类型</th>
						<th>说明</th>
						<th style="width: 80px">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.results }" var="item" varStatus="stat">
						<tr target="sid_user" rel="${item.id }">
							<td><input type="checkbox" name="ids" class="" value="${item.id }"/>${stat.count }</td>
							<td><a href="${path }/warehouse/proCombo!view.action?id=${item.id}" target="navTab" title="浏览">${item.productComboName }</a></td>
							<td>
								<c:if test="${item.comboType == 'combo_type_01' }">基因套餐</c:if>
								<c:if test="${item.comboType == 'combo_type_02' }">癌筛套餐</c:if>
								<c:if test="${item.comboType == 'combo_type_03' }">分子套餐</c:if>
								<c:if test="${item.comboType == 'combo_type_04' }">其他套餐</c:if>
							</td>
							<td>${item.declare }</td>
							<td>
								<a style="margin-left: 18px;" onclick="dealIsClose('${item.id }', '${item.isClose }');" href="javascript:;" class="button"><span id="${item.id }">${item.isClose != "1"? "关闭": "启用"}</span></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	
	</div>

<script type="text/javascript">

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
		url: "${path }/warehouse/proCombo!dealIsClose.action",
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
function resetDoProComb() {
	$("#pagerFindForm :input", navTab.getCurrentPanel()).each(function() {
		$(this).val("");
	}); 
}
	
/**删除*/
function delProComb() {
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
	alertMsg.confirm("确定要删除选中的数据吗?", {
		okCall: function(){
			$.ajax({	//初始化页面时的省份
				type: "post",
				cache :false,
				data:{"ids":ids},
				url: "${path }/warehouse/proCombo!delete.action",
				success: function(data){
					var data= eval("("+data+")");
					if(data.result){
						alertMsg.info(data.message);
					}else {
						alertMsg.error(data.message);
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

/**添加按钮事件;*/
function addProComb() {
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("productAdd", "${path }/warehouse/proCombo!edit.action?navTabId="+navTabId, { title:"产品套餐新增", fresh:false, data:{} });
}
	
	
function updateProComb(){
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
	navTab.openTab("productComboUpdate", "../warehouse/proCombo!edit.action?id="+ids+"&navTabId="+navTabId, { title:"产品套餐修改", fresh:false, data:{} });
}
</script>