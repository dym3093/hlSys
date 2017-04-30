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

<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/sampleExpressMgr!listSampleExpressMgr.action')
		{this.action = '${path}/events/sampleExpressMgr!listSampleExpressMgr.action';}; return navTabSearch(this);" 
		action="${path}/events/sampleExpressMgr!listSampleExpressMgr.action" method="post"  rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tbody>
				<tr>
					<td>
						<label>批次号：</label>
						<input type="text" id="listMgrEveBatchno" name="eventsBatchno" value="${eventsBatchno}"/>
					</td>
					<td>
						<label>快递单号：</label>
						<input type="text" id="listMgrEveExpNum" name="expressNum" value="${expressNum}"/>
					</td>
					<td>
						<label>快递公司：</label>
						<select id="expressCompanyId_list" name="expressCompanyId" style="width: 194px; margin-left: 0px;">
							<option value="">请选择</option>
							<c:forEach items="${exprComps}" var="comp">
								<option value="${comp.id}" ${comp.id== expressCompanyId ? "selected":"" }>${comp.companyName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>寄送类别：</label>
						<select name="receiveSendType" rel="receiveSendType" id="receiveSendType_list" style="width: 194px; margin-left:0px;">
							<option value="">请选择</option>
							<option value="1" <c:if test="${receiveSendType==1 }">selected="selected"</c:if>>收样</option>
							<option value="2" <c:if test="${receiveSendType==2 }">selected="selected"</c:if>>寄样</option>
						</select> 
					</td>
					<td>
						<label>收发件日期：</label> 
						<input type="text" name="receiveSendDate_gest"  id="d1" style="width:170px;float:left" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${receiveSendDate_gest}" /><a class="inputDateButton" href="javascript:;" >选择</a>
					</td>
					<td>
						<label>至：</label> 
						<input type="text" name="receiveSendDate_leet" id="d2" style="width:170px;float:left" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${receiveSendDate_leet}" /><a class="inputDateButton" href="javascript:;">选择</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>包裹内容：</label>
						<select name="expressContent" rel="expressContent" id="expressContent_list" style="width: 194px; margin-left: 0px;">
							<option value="">请选择</option>
							<option value="1" <c:if test="${expressContent==1 }">selected="selected"</c:if>>样本同意书</option>
							<option value="2" <c:if test="${expressContent==2 }">selected="selected"</c:if>>样本</option>
							<option value="3" <c:if test="${expressContent==3 }">selected="selected"</c:if>>同意书</option>
						</select> 
					</td>
					<td>
						<label>发票有无：</label>
						<select name="isbill" rel="isbill" id="isbill_list" style="width: 194px; margin-left: 0px;">
							<option value="">请选择</option>
							<option value="0" <c:if test="${isbill==0 }">selected="selected"</c:if>>无</option>
							<option value="1" <c:if test="${isbill==1 }">selected="selected"</c:if>>有</option>
						</select> 
						
					</td>
					<td>
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
			<web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="exportSampleExpressMgr" 
								actionAllUrl="org.hpin.events.web.SampleExpressMgrAction" 
								informationTableId="exportExcelTableSamExpMgr"
								fileName="SampleExpressMgrAction">
			</web:exportExcelTag> 
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
		<table class="list" style="width:100%; overflow: auto" layoutH="138" id="exportExcelTableSamExpMgr">
			<thead>
				<tr>
					<th style="width: 50px; ">序号</th>
					<th export = "true" columnEnName = "eventBatchno" columnChName = "批次号" style="width: 100px; ">批次号</th>
					<th export = "true" columnEnName = "eventsNo" columnChName = "场次号" style="width: 160px; ">场次号</th>
					<th export = "true" columnEnName = "eventDate" columnChName = "场次日期" style="width: 190px; ">场次日期</th>
					<th export = "true" columnEnName = "branchCompanyId" columnChName = "支公司" style="width: 200px; ">支公司</th>
					<th export = "true" columnEnName = "ownedCompanyId" columnChName = "所属公司" style="width: 200px; ">所属公司</th>
					<th export = "true" columnEnName = "hasInHead" columnChName = "已录人数" style="width: 100px; ">已录人数</th>
					<th export = "true" columnEnName = "expHead" columnChName = "已寄送人数" style="width: 90px; ">已寄送人数</th>
					<th export = "true" columnEnName = "expressNum" columnChName = "快递单号" style="width: 200px; ">快递单号</th>
					<th export = "true" columnEnName = "expressCompanyId" columnChName = "快递公司" style="width: 50px; ">快递公司</th>
					<th export = "true" columnEnName = "totalCost" columnChName = "快递费用(元)" style="width: 160px; ">快递费用(元)</th>
					<th export = "true" columnEnName = "avgCost" columnChName = "均摊价格(元)" style="width: 160px; ">均摊价格(元)</th>
					<th export = "true" columnEnName = "receiveSendType" columnChName = "寄送类别" style="width: 180px; ">寄送类别</th>
					<th export = "true" columnEnName = "receiveSendDate" columnChName = "收发件日期" style="width: 120px; ">收发件日期</th>
					<th export = "true" columnEnName = "expressContent" columnChName = "包裹内容" style="width: 200px; ">包裹内容</th>
					<th export = "true" columnEnName = "isbill" columnChName = "有无发票" style="width: 40px; ">有无发票</th>
				</tr>
			</thead>
			<tbody id="listSamExpMgrTbody">
				<c:forEach items="${page.results }" var="item" varStatus="status">
					<tr target="sid_user" rel="${item.id }" >
						<td align="left"><input type="checkbox" name="ids" value="${item.id}"/>${status.count}</td>
						<td align="center">${item.eventBatchno}</td>
						<td align="center">${item.eventsNo}</td>
						<td align="center">${fn:substring(item.eventDate,0,14)}00:00</td>
						<td align="center"><hpin:id2nameDB id="${item.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
						<td align="center"><hpin:id2nameDB id="${item.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
						<td align="center">${item.hasInHead}</td>
						<td align="center">${item.expHead}</td>
						<td align="center"><a href="javascript:void(0);" onclick="showMgrInfo('${item.id}')">${item.expressNum}</a></td>
						<td align="center">
							<c:forEach items="${exprComps}" var="comp">
								<c:if test="${comp.id==item.expressCompanyId}">${comp.companyName}</c:if>
							</c:forEach>
						</td>
						<td align="center">${item.totalCost}</td>
						<td align="center">${item.avgCost}</td>
						<td align="center">
							<c:if test="${1==item.receiveSendType}">收样</c:if>
							<c:if test="${2==item.receiveSendType}">寄样</c:if>
						</td>
						<td align="center">${fn:substring(item.receiveSendDate,0,11)}</td>
						<td align="center">
							<c:if test="${item.expressContent==1 }">样本同意书</c:if>
							<c:if test="${item.expressContent==2 }">样本</c:if>
							<c:if test="${item.expressContent==3 }">同意书</c:if>
						</td>
						<td align="center">
							<c:if test="${item.isbill==0 }">无</c:if>
							<c:if test="${item.isbill==1 }">有</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

</div>

	</div>
</div>

<script type="text/javascript">
	function clearInput(){
		$("#listMgrEveBatchno").val('');
		$("#listMgrEveExpNum").val('');
		$("#expressCompanyId_list").get(0).selectedIndex=0;
		$("#expressContent_list").get(0).selectedIndex=0;
		$("#receiveSendType_list").get(0).selectedIndex=0;
		$("#isbill_list").get(0).selectedIndex=0;
		$("#expressCompanyId_list").get(0).selectedIndex=0;
		$("input[name^='receiveSendDate']").val('');
	}
	
	function add() {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("sampleExpressMgrAdd", "${path}/events/sampleExpressMgr!addSampleExpressMgr.action?navTabId="+navTabId, { title:"添加样本快递费", fresh:false, data:{} });
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
		var sampleExpMgrId =$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).val();
		navTab.openTab("sampleExpressMgrUpdate","${path}/events/sampleExpressMgr!editSampleExpressMgr.action?sampleExpMgrId="+sampleExpMgrId+"&navTabId="+navTabId, { title:"修改样本快递费", fresh:false, data:{} });
	}
	
	function showMgrInfo(sampleExpMgrId){
		navTab.openTab("showSampleExpressMgr","${path}/events/sampleExpressMgr!showSampleExpressMgr.action?sampleExpMgrId="+sampleExpMgrId, { title:"样本快递费明细", fresh:false, data:{} });
	}
</script>





