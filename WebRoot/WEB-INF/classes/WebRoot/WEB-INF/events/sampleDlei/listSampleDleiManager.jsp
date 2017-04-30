<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<script type="text/javascript">
	function clearInput(){
		$(':input','#sampleDeliForm_id',navTab.getCurrentPanel())  
		 .not(':button, :submit, :reset, :hidden')  
		 .val('')  
		 .removeAttr('checked')  
		 .removeAttr('selected');  
	}

</script>
<div class="tip">
	<span>查询</span>
</div>

<div class="pageHeader">
	<form id="sampleDeliForm_id" onsubmit="return navTabSearch(this);" action="${path}/events/sampleDlei!listSampleDleiManager.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tbody>
				<tr>
					<td>
						<label>快递单号：</label><input type="text" name="deliNum" class="textInput" value="${deliNum }">
					</td>
					<td>
						<label>快递公司：</label>
						<select id="expressCompanyId" name="expressCompanyId" style="width: 194px; margin-left: 0px;">
							<option value="">请选择</option>
							<c:forEach items="${exprComps }" var="comp">
								<option value="${comp.id }" ${comp.id== expressCompanyId ? "selected":"" }>${comp.companyName }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label>类别：</label>
						<select name="sampleType" style="width: 194px; margin-left: 0px;">
							<option value="">请选择</option>
							<option value="TYPE_01" ${sampleType=="TYPE_01" ? "selected": "" }>寄样</option>						
							<option value="TYPE_02" ${sampleType=="TYPE_02" ? "selected": "" }>收样</option>						
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>收发日期开始：</label>
						<input type="text" name="startDate" class="date textInput readonly" readonly="true" value="${startDate }">
						<a class="inputDateButton" href="javascript:;">选择</a>
					</td>
					<td>
						<label>收发日期结束：</label>
						<input type="text" name="endDate" class="date textInput readonly" readonly="true" value="${endDate }">
						<a class="inputDateButton" href="javascript:;">选择</a>
					</td>
					<td >
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button onclick="clearInput()" type="button">重置</button></div></div></li>
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
			<li class=""><a class="add" href="javascript:void(0);" onclick="add();"><span>添加</span></a></li>
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
					<th style="width: 200px; ">快递单号</th>
					<th>快递公司</th>
					<th style="width: 120px; ">收发日期</th>
					<th style="width: 200x; ">类别</th>
					<th style="width: 120px; ">重量</th>
					<th style="width: 120px; ">费用(元)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results }" var="item" varStatus="stat">
					<tr target="sid_user" rel="${item.id }" >
						<td style="width: 45px;"><input type="checkbox" name="ids" class="" value="${item.id }"/>${stat.count }</td>
						<td style="width: 200px;"><a href="${path }/events/sampleDlei!viewSampleDleiManager.action?sampleDeliId=${item.id}" target="navTab" title="样本快递费浏览">${item.deliNum }</a></td>
						<td>${item.expressCompanyName }</td>
						<td style="text-align: center; width: 200px;" style="width: 100px;"><fmt:formatDate value="${item.receliveSendDate }" pattern="yyyy-MM-dd"/></td>
						<td style="width: 100px; text-align: center;">${item.sampleType eq "TYPE_01" ? "寄样" : item.sampleType eq "TYPE_02" ? "收样" : "" }</td>
						<td style="text-align: center;" style="width: 80px;">${item.weight }</td>
						<td style="text-align: center;" class="center" style="width: 80px;">${item.costPrice }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

</div>

	</div>
</div>

<script type="text/javascript">
$(function(){
	//重置按钮问题解决
	$("#resetDo", navTab.getCurrentPanel()).on("click", function() {
		$("input", navTab.getCurrentPanel()).val("");
		$("select", navTab.getCurrentPanel()).val("");
	});
});
	

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

		alertMsg.confirm("确定要删除选中的数据吗?", {
			okCall: function(){
				$.ajax({	//初始化页面时的省份
					type: "post",
					cache :false,
					data:{"ids":ids},
					url: "${path }/events/sampleDlei!deleteSampleDleiManager.action",
					success: function(data){
						var data= eval("("+data+")");
						if(data.statusCode=='200'){
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
		navTab.openTab("sampleDeliManagerUpdate", "${path }/events/sampleDlei!editSampleDleiManager.action?navTabId="+navTabId, { title:"样本快递费管理修改", fresh:false, data:{} });
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
		navTab.openTab("sampleDeliManagerUpdate", "../events/sampleDlei!editSampleDleiManager.action?sampleDeliId="+ids+"&navTabId="+navTabId, { title:"样本快递费管理修改", fresh:false, data:{} });
	}
</script>





