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
		action="${path}/warehouse/application!list.action" 
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
							<option value="5" ${params.status =='5'? "selected" : "" }>待销售</option> 
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
						<label>项目编码：</label>
						<input type="text"  name="params.projectCode" value="${params.projectCode }"/>
					</td>
					<td>
						<label>项目名称：</label>
						<input type="text"  name="params.projectName" value="${params.projectName }"/>
						
					</td>
					<td>
						<label>项目负责人：</label>
						<input type="text"  name="params.projectOwner" value="${params.projectOwner }"/>
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
				<li class=""><a class="add" href="javascript:void(0);" onclick="applyFor();"><span>申请</span></a></li>
				<li class="line">line</li>
				<li class=""><a class="edit" href="javascript:void(0);" onclick="update();"><span>修改</span></a></li>
				<li class="line">line</li>
				<li class=""><a class="delete" href="javascript:void(0);" onclick="del();"><span>删除</span></a></li>
				<li class="line">line</li>
				<li class=""><a class="icon" href="javascript:void(0);" id="batchApply_id"><span>批量申请</span></a></li>
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
						<th style="width: 80px; ">项目编码</th>
						<th style="width: 80px; ">项目名称</th>
						<th style="width: 80px; ">项目负责人</th>
						<th style="width: 80px; ">项目对接人</th>
						<th style="width: 80px; ">对接人电话</th>
						<th style="width: 80px;">收件人</th>
						<th style="width: 100px; ">申请时间</th>
						<th style="width: 80px; ">期望日期</th>
						<th style="width: 60px; ">申请人</th>
						<th style="width: 60px; ">状态</th>
						<th style="width: 80px; ">申请单类型</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.results }" var="item" varStatus="stat">
						<tr>
							<td><input type="checkbox" name="ids" value="${item.id }" status="${item.status }"/>${stat.count }</td>
							<td><a href="${path }/warehouse/storegeOut!view.action?id=${item.id}" target="navTab">${item.applicationNo }</a></td>
							<td>${item.ownerCompanyName }</td>
							<td>${item.bannyCompanyName }</td>
							<td>${item.projectCode }</td>
							<td>${item.projectName }</td>
							<td>${item.projectOwner }</td>
							<td>${item.linkName }</td>
							<td>${item.linkTel }</td>
							<td>${item.receiveName }</td>
							<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${item.hopeDate }" pattern="yyyy-MM-dd"/></td>
							<td>${item.createUserName }</td>
							<td>
								<c:if test="${item.status == '0' }">待发货</c:if>
								<c:if test="${item.status == '1' }">部分发货</c:if>
								<c:if test="${item.status == '2' }"><span style="color:green">已发货</span></c:if>
								<c:if test="${item.status == '3' }">处理中</c:if>
								<c:if test="${item.status == '4' }"><span style="color:red">退回</span></c:if>
								<c:if test="${item.status == '5' }">待销售</c:if>
							</td>
							<td>
								${item.isMark=='1' ? '保险公司' : '远盟销售' }
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	
	</div>

<script type="text/javascript">
$(function() {
	$("#batchApply_id", navTab.getCurrentPanel()).on("click", function() {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("applicationBatchAdd", "${path }/warehouse/application!editBatch.action?navTabId="+navTabId, {title:"物品批量申请", fresh:false, data:{} });
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
	
	/*当前申请单的主表中状态为：0 才能删除*/
	if(status != 0 && status != 4) {
		alertMsg.warn("该数据不能删除,只有主表中状态为待发货的数据才可以删除!");
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

	function applyFor() {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("applicationAdd", "${path }/warehouse/application!edit.action?navTabId="+navTabId, {title:"基因物品申请", fresh:false, data:{} });
	}
	
	function update() {
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
		if(status == 1  || status ==2) {
			alertMsg.warn("该数据已经部分发货或已发货完成,不能做修改!");
			return;
		}
		
		if(status == 3) {
			alertMsg.confirm("该数据在处理中不能修改，数据已变更是否进行刷新！", {
				okCall: function(){
					 navTabPageBreak();
				}
			});
			return;
		}
		
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("applicationUpdate", "../warehouse/application!edit.action?id="+ids+"&navTabId="+navTabId, { title:"申请修改", fresh:false, data:{} });
	}
	
	
</script>