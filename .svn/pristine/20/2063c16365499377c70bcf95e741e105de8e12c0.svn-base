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
		<form id="applicationForm_id" onsubmit="return navTabSearch(this);" 
		action="${path}/warehouse/application!ygbxList.action" 
		method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>总公司名称：</label>
						<input type="text" name="params.ownerCompanyName" value="${params.ownerCompanyName }"/>
					</td>	
					<td>
						<label>支公司名称：</label>
						<input type="text" name="params.bannyCompanyName" value="${params.bannyCompanyName }"/>
					</td>		
					<td>
						<label>发货状态：</label>
						<select id="statusSel" name="params.status" style="width: 194px; margin-left:4px; margin-top: 6px;">
							<option value="">全部</option> 
							<option value="0" ${params.status =='0'? "selected" : "" }>待发货</option> 
							<option value="3" ${params.status =='3'? "selected" : "" }>处理中</option>
							<option value="1" ${params.status =='1'? "selected" : "" }>部分发货</option>
							<option value="2" ${params.status =='2'? "selected" : "" }>已发货</option>
	         			</select>
					</td>
					<td></td>
				</tr>	
				<tr>
					<td>
						<label>申请单号：</label>
						<input type="text"  name="params.applicationNo" value="${params.applicationNo }"/>
					</td>
					<td>
						<label>申请开始日期：</label>
					  	<input type="text" name="params.startDate"  id="d1" style="width: 170px;" readonly="readonly" value="${params.startDate}" onFocus="WdatePicker({minDate:'2015-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})" />
					  	<a class="inputDateButton" href="javascript:;" >选择</a>
					</td>
					<td>
						<label>申请截止日期：</label>					
						<input type="text" name="params.endDate" id="d2" style="width: 170px;" readonly="readonly" value="${params.endDate}"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"/>
						<a class="inputDateButton" href="javascript:;">选择</a>
					</td>
					<td>
						<ul style="float: left">
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
							<li><a onclick="resetDo();" href="javascript:;" class="button"><span>重置</span></a></li>
						</ul>
					</td>
				</tr>
			
			</table>
			
		</div>
		</form>
	</div>

	<div class="pageContent j-resizeGrid">
		<div class="panelBar">
			<ul class="toolBar">
				<li class=""><a class="add" href="javascript:void(0);" id="applyFor"><span>申请</span></a></li>
				<li class="line">line</li>
				<li class=""><a class="edit" href="javascript:void(0);" id="updateId"><span>修改</span></a></li>
				<li class="line">line</li>
				<li class=""><a class="delete" href="javascript:void(0);" onclick="del();"><span>删除</span></a></li>
			</ul>
			<jsp:include page="/common/pagination.jsp" />
		</div>
			<table class="list" style="width:100%; overflow: auto" layoutH="108">
				<thead>
					<tr>
						<th style="width: 45px; ">序号</th>
						<th style="width: 100px; ">申请单号</th>
						<th>总公司名称</th>
						<th>支公司名称</th>
						<th style="width: 80px;">收件人</th>
						<th style="width: 100px; ">申请时间</th>
						<th style="width: 80px; ">期望日期</th>
						<th style="width: 100px; ">远盟对接人</th>
						<th style="width: 60px; ">状态</th>
						<th style="width: 120px; ">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.results }" var="item" varStatus="stat">
						<tr>
							<td><input type="checkbox" name="ids" value="${item.id }" status="${item.status }"/>${stat.count }</td>
							<td><a href="${path }/warehouse/application!ygbxView.action?id=${item.id}" target="navTab">${item.applicationNo }</a></td>
							<td>${item.ownerCompanyName }</td>
							<td>${item.bannyCompanyName }</td>
							<td>${item.receiveName }</td>
							<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${item.hopeDate }" pattern="yyyy-MM-dd"/></td>
							<td style="text-align: center;">${item.createUserName }</td>
							<td>
								<c:if test="${item.status == '0' }">待发货</c:if>
								<c:if test="${item.status == '1' }">部分发货</c:if>
								<c:if test="${item.status == '2' }"><span style="color:green">已发货</span></c:if>
								<c:if test="${item.status == '3' }">处理中</c:if>
								<c:if test="${item.status == '4' }"><span style="color:red">退回</span></c:if>
								<c:if test="${item.status == '5' }">待销售</c:if>
							</td>
							<td>
								<a class="buttonActive" href="${path}/warehouse/application!impCustomerUpload.action?applicationNo=${item.applicationNo }"
										target="dialog" name="excelImport"><span>客户信息导入</span></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	
	</div>

<script type="text/javascript">
$(function() {
	
	/**申请单击事件;*/
	$("#applyFor", navTab.getCurrentPanel()).on("click", function() {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("applicationYgbxAdd", "${path }/warehouse/application!ygbxEdit.action?navTabId="+navTabId, {title:"基因物物料申请", fresh:false, data:{} });
	});
	
	/**修改单击事件处理*/
	$("#updateId", navTab.getCurrentPanel()).on("click", function() {
		var count = 0;
		var ids = "";
		var status = "";
		
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
		//后台数据请求;获取状态;
		$.ajax({
			type: "post",
			cache : false,
			async : false,
			data:{"id":ids},
			url: "${path }/warehouse/application!findStatusById.action",
			success: function(data){
				var data= eval("("+data+")");
				status = data.status; 
			},
			error :function(){
				alertMsg.alert("服务发生异常，请稍后再试！");
				return;
			}
		});
		
		/*如果还没有发货的数据可以修改;*/
		if(status != 5) {
			alertMsg.warn("该数据已被使用,不能做修改!");
			return;
		}
		
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("applicationYgbxUpdate", "../warehouse/application!ygbxUpdate.action?id="+ids+"&navTabId="+navTabId, { title:"申请修改", fresh:false, data:{} });
	});
	
	
});

/**
 * 删除功能;
 * 当前申请单的主表中状态为：0 才能删除
 */
function del() {
	var count = 0;
	var ids = "";
	var status = "";
	
	$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
		ids = val.value ;
		count ++;
	});
	
	if(count == 0) {
		alertMsg.warn("请选择你要变更的信息！");
		return;
	} else if (count > 1) {
		alertMsg.warn("只能选择一条信息进行删除！");
		return;
	}
	//后台数据请求;获取状态;
	$.ajax({
		type: "post",
		cache : false,
		async : false,
		data:{"id":ids},
		url: "${path }/warehouse/application!findStatusById.action",
		success: function(data){
			var data= eval("("+data+")");
			status = data.status; 
		},
		error :function(){
			alertMsg.alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	
	/*如果还没有发货的数据可以修改;*/
	if(status != 5) {
		alertMsg.warn("该数据已被使用,不能做删除!");
		return;
	}
	
	//后台逻辑删除;
	alertMsg.confirm("您确认要删除选中的数据吗?", {
		okCall: function(){
			$.ajax({
				type: "post",
				cache : false,
				async : false,
				data:{"id":ids},
				url: "${path }/warehouse/application!delete.action",
				success: function(data){
					var data= eval("("+data+")");
					if(data.result) {
						alertMsg.correct(data.message);
						navTabPageBreak();
					} else {
						alertMsg.warn(data.message);
					}
				},
				error :function(){
					alertMsg.error("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
	});

}

	function resetDo() {
		$("#applicationForm_id :input", navTab.getCurrentPanel()).each(function() {
			$(this).val("");
		}); 
		$("#applicationForm_id :select", navTab.getCurrentPanel()).each(function() {
			$(this).val("");
		});
	}

	
	
</script>