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
			action="${path}/warehouse/storegeOut!list.action" 
			method="post" 
			rel="pagerForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>申请号：</label>
						<input type="text" name="params.applicationNo" value="${params.applicationNo }"/>
					</td>
					<td>
						<label>总公司名称：</label>
						<input type="text" name="params.ownerCompanyName" value="${params.ownerCompanyName }"/>
					</td>	
					<td>
						<label>支公司名称：</label>
						<input type="text" name="params.bannyCompanyName" value="${params.bannyCompanyName }"/>
					</td>		
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
				</tr>
				<tr>
					<td>
						<label>发货状态：</label>
						<select id="statusSel" name="params.status" style="width: 192px; margin-left:4px; margin-top: 6px;">
							<option value="">全部</option> 
							<option value="0" ${params.status =='0'? "selected" : "" }>待处理</option> 
							<option value="3" ${params.status =='3'? "selected" : "" }>待发货</option>
							<option value="1" ${params.status =='1'? "selected" : "" }>部分发货</option>
							<option value="2" ${params.status =='2'? "selected" : "" }>已发货</option>
	         			</select>
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
					
				</tr>
				<tr>
					<td>
						<label>收件人：</label>
						<input type="text" name="params.receiveName" value="${params.receiveName }"/>
						
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
			</table>
			
		</div>
		</form>
	</div>

	<div class="pageContent j-resizeGrid">
		<div class="panelBar">
			<ul class="toolBar">
				<li class=""><a class="icon" href="javascript:void(0);" onclick="dealRepeal();"><span>撤销处理</span></a></li>
				<li class="line">line</li>
				<li class=""><a class="icon" href="javascript:void(0);" onclick="dealBack();"><span>退回</span></a></li>
			</ul>
			<jsp:include page="/common/pagination.jsp" />
		</div>
			<table class="list" style="width:100%; overflow: auto" layoutH="160">
				<thead>
					<tr>
						<th style="width: 45px; ">序号</th>
						<th style="width: 100px; ">申请单号</th>
						<th>总公司名称</th>
						<th>支公司名称</th>
						<th style="width: 80px; ">项目编码</th>
						<th style="width: 150px; ">项目名称</th>
						<th style="width: 80px; ">项目负责人</th>
						<th style="width: 80px; ">收件人</th>
						<th style="width: 80px; ">收件人电话</th>
						<th style="width: 60px; ">申请人</th>
						<th style="width: 60px; ">状态</th>
						<th style="width: 60px;" >操作</th>
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
							<td>${item.receiveName }</td>
							<td>${item.receiveTel }</td>
							<td>${item.createUserName }</td>
							<td>
								<c:if test="${item.status == '0' }">待处理</c:if>
								<c:if test="${item.status == '3' }">待发货</c:if>
								<c:if test="${item.status == '1' }">部分发货</c:if>
								<c:if test="${item.status == '2' }">已发货</c:if>
							</td>
							<td style="text-align: center;">
							
								<c:if test="${item.dealUserId == null }">
									<c:if test="${item.status == '0' }">
										<a class="button" href="javascript:void(0);" onclick="dealStatus('${item.id }');"><span>处理</span></a>
									</c:if>
									
									<c:if test="${item.status == '1' || item.status == '3'}">
										<a class="button" href="javascript:void(0);" onclick="sendEdit('${item.id }');"><span>发货</span></a>
									</c:if>
									<c:if test="${item.status == '2' }">
										<span style="color: green">已发货</span>
									</c:if>
								</c:if>
								
								<c:if test="${item.dealUserId != null && item.dealUserId == user.id }">
									<c:if test="${item.status == '0' }">
										<a class="button" href="javascript:void(0);" onclick="dealStatus('${item.id }');"><span>处理</span></a>
									</c:if>
									<c:if test="${item.status == '1' || item.status == '3'}">
										<a class="button" href="javascript:void(0);" onclick="sendEdit('${item.id }');"><span>发货</span></a>
									</c:if>
									<c:if test="${item.status == '2' }">
										<span style="color: green">已发货</span>
									</c:if>
								</c:if>
								
								<c:if test="${item.dealUserId != null && item.dealUserId != user.id}">
									<c:if test="${item.status== '3'}">
										<span style="color: red">${item.dealUserName }<br />正在处理</span>
									</c:if>
									
									<c:if test="${item.status== '1'}">
										<a class="button" href="javascript:void(0);" onclick="sendEdit('${item.id }');"><span>发货</span></a>
									</c:if>
									
									<c:if test="${item.status== '0'}">
										<a class="button" href="javascript:void(0);" onclick="dealStatus('${item.id }');"><span>处理</span></a>
									</c:if>
									
									<c:if test="${item.status=='2'}">
										<span style="color: green">已发货</span>
									</c:if>
									
								</c:if>
								
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	
	</div>

<script type="text/javascript">

/**
 * 退回;
 * 当前申请单的主表中状态不为：1和2才能退回
 */
function dealBack() {
	var count = 0;
	var ids = "";
	var status = "";
	$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
		ids = val.value ;
		count ++;
		status = $(this).attr("status");
	});
	
	if(count == 0) {
		alertMsg.warn("请选择你要变更的信息！");
		return;
	} else if (count > 1) {
		alertMsg.warn("只能选择一条信息进行删除！");
		return;
	}
	
	//后台逻辑删除;
	
	alertMsg.confirm("您确认要回退选中的数据吗?", {
		okCall: function(){
			$.ajax({
				type: "post",
				cache : false,
				async : false,
				data:{"id":ids},
				url: "${path }/warehouse/storegeOut!backDeal.action",
				success: function(data){
					var data= eval("("+data+")");
					if(data.result) {
						alertMsg.correct(data.message);
						navTabPageBreak(); //及时刷新页面
					} else {
						if(data.failFlag == '0') {
							alertMsg.warn(data.message);
							navTabPageBreak(); //及时刷新页面
						} else {
							alertMsg.warn(data.message);
						}
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

	/** 页面跳转
	*/
	function sendEdit(id) {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("storegeOutAdd", "${path }/warehouse/storegeOut!sendEdit.action?id="+id+"&navTabId="+navTabId, {title:"基因物品发货", fresh:false, data:{} });
	}
	
	/**处理;
	*/
	 function dealRepeal() {
		var count = 0;
		var ids = "";
		
		$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
			ids = val.value ;
			count ++;
		});
		
		if(count == 0) {
			alertMsg.warn("请选择你要处理的信息！");
			return;
		} else if(count > 1) {
			alertMsg.warn("请选择单条数据进行操作!");
		}
		
		$.ajax({	//初始化页面时的省份
			type: "post",
			cache :false,
			data: {"id":ids},
			url: "${path }/warehouse/storegeOut!dealRepeal.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.result) {
					alertMsg.correct(data.message);
					navTabPageBreak(); //及时刷新页面
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
	
	/**处理按钮事件*/
	function dealStatus(id) {
		$.ajax({	//初始化页面时的省份
			type: "post",
			cache :false,
			data: {"ids":id},
			url: "${path }/warehouse/storegeOut!dealApplicationStatus.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.result) {
					alertMsg.correct(data.message);
					navTabPageBreak(); //及时刷新页面
				} else {
					alertMsg.confirm(data.message, {
						okCall: function(){
							navTabPageBreak(); //及时刷新页面
						}
					});
				}
				
			},
			error :function(){
				alertMsg.error("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	
	
</script>