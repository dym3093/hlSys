<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>

<div class="tip">
	<span>添加</span>
</div>
<div class="pageHeader">
	<form id="addSampleExpressMgr_id" onsubmit="return validateCallback(this, navTabAjaxDone);" 
		action="${path}/events/sampleExpressMgr!saveSampleExpressMgr.action" method="post" class="pageForm required-validate">
	<input name="sampleExpMgrId" type="hidden" value="${sampleExpMgrId}" />
	<input name="rootNavTabId" type="hidden" value="${rootNavTabId}" />
	<div class="searchBar">
		<table class="searchContent">
			<tbody>
				<tr>
					<td>
						<label>快递单号：</label>
						<input type="text" name="expressNum" id="addSamExpMgrExpNum" value="${expressNum}" class="required" style="margin-left:5px;"/>
					</td>
					<td>
						<label>快递公司：</label>
						<select id="expressCompanyId_add" name="expressCompanyId" rel="iselect" style="width: 194px; margin-left: 0px;" class="required">
							<option value="">请选择</option>
							<c:forEach items="${exprComps}" var="comp">
								<option value="${comp.id}" ${comp.id== expressCompanyId ? "selected":"" }>${comp.companyName}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label>重量：</label>
						<input type="text" name="weight" value="${weight}" style="margin-left:5px;"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>费用：</label>
						<input type="text" name="totalCost" value="${totalCost}" class="required" style="margin-left:5px;"/>
					</td>
					<td>
						<label>收发件日期：</label> 
						<input type="text" name="receiveSendDate" id="d1" style="width: 170px;margin-left:5px;float:left" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})" readonly="readonly" value="${receiveSendDate}" class="required"/>
						<a class="inputDateButton" href="javascript:;" >选择</a>
					</td>
					<td>
						<label style="margin-top:3px;">发票有无：</label>
						<c:if test="${empty isbill}">
							<input checked="true" type="radio" name="isbill" value="1" style="vertical-align:text-bottom"/><span>有</span>
							<input type="radio" name="isbill" value="0" style="vertical-align:text-bottom"/><span>无</span>
						</c:if>
						<c:if test="${not empty isbill}">
							<input type="radio" name="isbill" value="1" style="vertical-align:text-bottom" <c:if test="${isbill=='1'}">checked="true"</c:if> />
							<span>有</span>
							<input type="radio" name="isbill" value="0" style="vertical-align:text-bottom" <c:if test="${isbill=='0'}">checked="true"</c:if> />
							<span>无</span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<label>收寄类别：</label>
						<select name="receiveSendType" rel="iselect" style="width: 194px;" class="required" id="receiveSendType_add">
							<c:if test="${empty receiveSendType}">
								<option value="">请选择</option>
								<option value="1">收样</option>
								<option value="2">寄样</option>
							</c:if>
							<c:if test="${not empty receiveSendType}">
								<option value="1" <c:if test="${receiveSendType=='1'}">selected="selected"</c:if>>收样</option>
								<option value="2" <c:if test="${receiveSendType=='2'}">selected="selected"</c:if>>寄样</option>
							</c:if>
						</select>
					</td>
					<td>
						<label>包裹内容：</label>
						<select name="expressContent" rel="iselect" id="expressContent_add" style="width: 194px; margin-left: 0px;" class="required">
							<c:if test="${empty expressContent}">
								<option value="">请选择</option>
								<option value="1">样本同意书</option>
								<option value="2">样本</option>
								<option value="3">同意书</option>
							</c:if>
							<c:if test="${not empty expressContent}">
								<option value="1" <c:if test="${expressContent=='1'}">selected="selected"</c:if>>样本同意书</option>
								<option value="2" <c:if test="${expressContent=='2'}">selected="selected"</c:if>>样本</option>
								<option value="3" <c:if test="${expressContent=='3'}">selected="selected"</c:if>>同意书</option>
							</c:if>
						</select> 
					</td>
					<td>
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
							</ul>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	</form>
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/sampleExpressMgr!refreshAddSampleExpMgr.action?sampleExpMgrId=${sampleExpMgrId}&expressNum=${expressNum}&
			expressCompanyId=${expressCompanyId}&weight=${weight}&totalCost=${totalCost}&receiveSendDate=${receiveSendDate}&isbill=${isbill}&receiveSendType=${receiveSendType}&
			expressContent=${expressContent}&rootNavTabId=${rootNavTabId}&pageType=${pageType}')
		{this.action = '${path}/events/sampleExpressMgr!refreshAddSampleExpMgr.action?sampleExpMgrId=${sampleExpMgrId}&expressNum=${expressNum}&expressCompanyId=${expressCompanyId}&
		weight=${weight}&totalCost=${totalCost}&receiveSendDate=${receiveSendDate}&isbill=${isbill}&receiveSendType=${receiveSendType}&expressContent=${expressContent}&rootNavTabId=${rootNavTabId}&pageType=${pageType}' ;}; 
		return navTabSearch(this);" 
		action="${path}/events/sampleExpressMgr!refreshAddSampleExpMgr.action?sampleExpMgrId=${sampleExpMgrId}&expressNum=${expressNum}&expressCompanyId=${expressCompanyId}&
		weight=${weight}&totalCost=${totalCost}&receiveSendDate=${receiveSendDate}&isbill=${isbill}&receiveSendType=${receiveSendType}&expressContent=${expressContent}&rootNavTabId=${rootNavTabId}&pageType=${pageType}" 
		method="post"  rel="pagerForm" style="display:none">
	</form>
</div>

<div class="pageContent j-resizeGrid">
	<div class="panelBar">
		<ul class="toolBar">
			<li class=""><a class="add" href="javascript:void(0);" onclick="addByEvents();"><span>按批次添加</span></a></li>
			<li class=""><a class="edit" href="javascript:void(0);" onclick="addByCustomer();"><span>按会员添加</span></a></li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
		<table class="list" style="width:100%; overflow: auto" layoutH="138">
			<thead>
				<tr>
					<th style="width: 45px; ">序号</th>
					<th style="width: 100px; ">批次号</th>
					<th style="width: 200px; ">场次号</th>
					<th style="width: 200px; ">场次日期</th>
					<th style="width: 150px; ">条码</th>
					<th style="width: 80px; ">姓名</th>
					<th style="width: 45px; ">性别</th>
					<th style="width: 40px; ">年龄</th>
					<th style="width: 200px; ">套餐</th>
					<th style="width: 200px; ">支公司</th>
					<th style="width: 200px; ">所属公司</th>
					<th style="width: 50px; ">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="erpCustomer" varStatus="status">
					<tr target="sid_user" rel="${erpCustomer.id }">
						<td align="left">${ status.count }</td>
						<td align="center">${erpCustomer.batchno}</td>
						<td align="center">${erpCustomer.eventsNo}</td>
						<td align="center">${fn:substring(erpCustomer.eventsDate,0,14)}00:00</td>
						<td align="center">${erpCustomer.code}</td>
						<td align="center">${erpCustomer.name}</td>
						<td align="center">${erpCustomer.sex}</td>
						<td align="center">${erpCustomer.age}</td>
						<td align="center">${erpCustomer.setmealName}</td>
						<td align="center"><hpin:id2nameDB id="${erpCustomer.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
						<td align="center"><hpin:id2nameDB id="${erpCustomer.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
						<td align="center"><a href="#" onclick="delSampleExpCus('${erpCustomer.id}')">删除</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

</div>

	</div>
</div>

<script type="text/javascript">
$(function(){
	var addClassName = $("li[tabid='sampleExpressMgrAdd']").attr("class");
	var updateClassName = $("li[tabid='sampleExpressMgrUpdate']").attr("class");
	if(addClassName=='selected'||updateClassName=='selected'){
		var expNumObj = $("#addSamExpMgrExpNum");
		if(typeof(expNumObj) !== "undefined"){
			$(navTab._getTabs().filter('.selected').find(":last-child")[1]).mouseover(function(){
				if(expNumObj.val()!==""){
					alertMsg.warn("关闭前请保存！");
				}
			});
		}
	}
})

function addByEvents(){
	var expressNum = $("input[name='expressNum']",navTab.getCurrentPanel()).val();
	var expressCompanyId = $("select[name='expressCompanyId']",navTab.getCurrentPanel()).val();
	var weight = $("input[name='weight']",navTab.getCurrentPanel()).val();
	var totalCost = $("input[name='totalCost']",navTab.getCurrentPanel()).val();
	var receiveSendDate = $("input[name='receiveSendDate']",navTab.getCurrentPanel()).val();
	var isbill = $("input[type='radio']:checked",navTab.getCurrentPanel()).val();
	var receiveSendType = $("select[name='receiveSendType']",navTab.getCurrentPanel()).val();
	var expressContent = $("select[name='expressContent']",navTab.getCurrentPanel()).val();
	var rootNavTabId = $("input[name='rootNavTabId']",navTab.getCurrentPanel()).val();
	//样本快递费ID
	var sampleExpMgrId = $("input[name='sampleExpMgrId']",navTab.getCurrentPanel()).val();
	//当前tabId
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	//打开导入窗口
	$.pdialog.open("${path}/events/sampleExpressMgr!toAddByEvents.action?sampleExpMgrId="+sampleExpMgrId+"&navTabId="+navTabId+"&expressNum="+expressNum+"&expressCompanyId="+
			expressCompanyId+"&weight="+weight+"&totalCost="+totalCost+"&receiveSendDate="+receiveSendDate+"&isbill="+isbill+"&receiveSendType="+receiveSendType+"&expressContent="+expressContent
			+"&rootNavTabId="+rootNavTabId,
			"toAddByEvents", "按批次添加",
		{width:800,height:600,max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true} );
};

function addByCustomer(){
	var expressNum = $("input[name='expressNum']",navTab.getCurrentPanel()).val();
	var expressCompanyId = $("select[name='expressCompanyId']",navTab.getCurrentPanel()).val();
	var weight = $("input[name='weight']",navTab.getCurrentPanel()).val();
	var totalCost = $("input[name='totalCost']",navTab.getCurrentPanel()).val();
	var receiveSendDate = $("input[name='receiveSendDate']",navTab.getCurrentPanel()).val();
	var isbill = $("input[type='radio']:checked",navTab.getCurrentPanel()).val();
	var receiveSendType = $("select[name='receiveSendType']",navTab.getCurrentPanel()).val();
	var expressContent = $("select[name='expressContent']",navTab.getCurrentPanel()).val();
	var rootNavTabId = $("input[name='rootNavTabId']",navTab.getCurrentPanel()).val();
	//样本快递费ID
	var sampleExpMgrId = $("input[name='sampleExpMgrId']",navTab.getCurrentPanel()).val();
	//当前tabId
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	//打开导入窗口
	$.pdialog.open("${path}/events/sampleExpressMgr!toAddByCustomer.action?sampleExpMgrId="+sampleExpMgrId+"&navTabId="+navTabId+"&expressNum="+expressNum+"&expressCompanyId="+
			expressCompanyId+"&weight="+weight+"&totalCost="+totalCost+"&receiveSendDate="+receiveSendDate+"&isbill="+isbill+"&receiveSendType="+receiveSendType+"&expressContent="+expressContent
			+"&rootNavTabId="+rootNavTabId,
			"toAddByCustomer", "按会员添加",
		{width:800,height:600,max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true} );
};
	
function delSampleExpCus(expCusid){
	var expressNum = $("input[name='expressNum']",navTab.getCurrentPanel()).val();
	var expressCompanyId = $("select[name='expressCompanyId']",navTab.getCurrentPanel()).val();
	var weight = $("input[name='weight']",navTab.getCurrentPanel()).val();
	var totalCost = $("input[name='totalCost']",navTab.getCurrentPanel()).val();
	var receiveSendDate = $("input[name='receiveSendDate']",navTab.getCurrentPanel()).val();
	var isbill = $("input[type='radio']:checked",navTab.getCurrentPanel()).val();
	var receiveSendType = $("select[name='receiveSendType']",navTab.getCurrentPanel()).val();
	var expressContent = $("select[name='expressContent']",navTab.getCurrentPanel()).val();
	var rootNavTabId = $("input[name='rootNavTabId']",navTab.getCurrentPanel()).val();
	//样本快递费ID
	var sampleExpMgrId = $("input[name='sampleExpMgrId']",navTab.getCurrentPanel()).val();
	//当前tabId
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/events/sampleExpressMgr!delSampleExpCus.action",
		data: {'navTabId':navTabId,'expCusid':expCusid,'sampleExpMgrId':sampleExpMgrId,'expressNum':expressNum,'expressCompanyId':expressCompanyId,'weight':weight,'totalCost':totalCost,'receiveSendDate':receiveSendDate,'isbill':isbill,'receiveSendType':receiveSendType,'expressContent':expressContent,'rootNavTabId':rootNavTabId},
		success: function(data){
			if(data.statusCode=="200"){
				alertMsg.correct("删除成功！");
				navTab.reload(data.forwardUrl,{});
			}else{
				alertMsg.error("添加失败！");
			}
		},
		error :function(data){
			alertMsg.error("服务发生异常，请稍后再试！");
			$("#addCustomerBtn").removeAttr("disabled");
		}
	});
};
</script>





