<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style>
	.select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
</style>

<script type="text/javascript" language="javascript">

$(document).ready(function(){
	var list = $("#hiddenList").val();
	
	/*
		create by henry.xu 20170117
		跳转界面到北京易安预导入客户清单;
	*/
	$("#preCustomerList", navTab.getCurrentPanel()).on("click", function() {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		var url = "${path}/warehouse/prepCustomer!preCustomerListEdit.action";
		navTab.openTab("precustomerList", url, { title:"预导入客户清单", fresh:false, data:{} });
	});
	
});
//录入客户
function addErpCustomer() {
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				count += count + 1;
				status = $(this).parent().next().text();
			});
	if (count == 0) {
		alertMsg.warn("请选择你要录入的信息！");
		return;
	} else if (count > 1) {
		alertMsg.warn('只能选择一条信息进行录入！');
		return;
	} else {
		var idArr = ids.split(",");//数据结构为：erpPrepCustomerId,btachNo
		if(idArr[1]==""){		//未有批次号的才能进入
			var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("addPrepCustomer",
					"../warehouse/prepCustomer!toAddOrUpdatePrepCustomer.action?id=" + idArr[0]
							+ "&navTabId=" + navTabId, {
						title : "录入客户",
						fresh : false,
						data : {}
					});
		}else{
			alertMsg.warn('客户已录入！');
			return;
		}
		
	}
}

//删除
function deletePrepCustomer(prepCustomerId){
	if(prepCustomerId=="undefined"||prepCustomerId==""){
		alertMsg.error("删除失败！");
	}else{
		alertMsg.confirm("确认删除?",{okCall:function(){
			$.ajax({
				type : "post",
				cache : false,
				dataType : "json",
				url : "${path}/warehouse/prepCustomer!deletePrepCustomer.action",
				data : {'id':prepCustomerId},
				success : function(data){
					if(data.status=="200"){
						alertMsg.correct(data.message);
						navTab.refreshCurrentTab();
						//$.pdialog.closeCurrent();
						return navTabSearch(this); 
					}else if(data.status=="300"){
						alertMsg.error(data.message);
					}
				},
				error : function(data){
					alert("服务发生异常，请稍后再试！");
				}
			});
		}});
		
		
	}
	
}

//导出excel
function exportPrepCustomer(){
	
	var pEventsNo = $("#pEventsNo").val();
	var pWereName = $("#pWereName").val();
	var pCode = $("#pCode").val();
	var pWereIdcard = $("#pWereIdcard").val();
	var pWerePhone = $("#pWerePhone").val();
	var pApplicationNo= $("#pApplicationNo").val();
	
	if(pEventsNo=="undefined"){
		pEventsNo="";
	}
	if(pWereName=="undefined"){
		pWereName="";
	}
	if(pCode=="undefined"){
		pCode="";
	}
	if(pWereIdcard=="undefined"){
		pWereIdcard="";
	}
	if(pWerePhone=="undefined"){
		pWerePhone="";
	}
	if(pApplicationNo=="undefined"){
		pApplicationNo="";
	}
	var json = "{\"eventsNo\":\""+pEventsNo+"\","+"\"wereName\":\""+pWereName+"\","+"\"code\":\""+pCode+"\","+"\"wereIdcard\":\""+pWereIdcard+"\","+"\"werePhone\":\""+pWerePhone+"\","+"\"applicationNo\":\""+pApplicationNo+"\"}";
	$.ajax({
		type : "post",
		cache : false,
		dataType : "json",
		url : "${path}/warehouse/prepCustomer!exportExcelPrepCustomer.action",
		data : {'data':json},
		success : function(data){
			if(data.status=="200"){
				window.open(data.message);
			}else if(data.status=="300"){
				alertMsg.error(data.message);
			}
		},
		error : function(data){
			alert("服务发生异常，请稍后再试！");
		}
	}); 
}

//变更预导入客户
function updatePrepCustomer(){
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				count += count + 1;
				status = $(this).parent().next().text();
			});
	if (count == 0) {
		alertMsg.warn("请选择你要变更的信息！");
		return;
	} else if (count > 1) {
		alertMsg.warn('只能选择一条信息进行变更！');
		return;
	} else {
		var idArr = ids.split(",");//数据结构为：erpPrepCustomerId,erpCustomerId
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("updatePrepCustomer",
				"${path}/warehouse/prepCustomer!toUpdatePrepCustomer.action?id=" + idArr[0]
						+ "&navTabId=" + navTabId, {
					title : "变更预导入客户",
					fresh : false,
					data : {}
				});
		
	}
}

function exportExcelForStrem(){
	var pBatchNo = $("#pBatchNo",navTab.getCurrentPanel()).val();
	var pWereName = $("#pWereName",navTab.getCurrentPanel()).val();
	var pCode = $("#pCode",navTab.getCurrentPanel()).val();
	var pWereIdcard = $("#pWereIdcard",navTab.getCurrentPanel()).val();
	var pWerePhone = $("#pWerePhone",navTab.getCurrentPanel()).val();
	var pApplicationNo= $("#pApplicationNo",navTab.getCurrentPanel()).val();
	var checkCombo= $("#checkCombo",navTab.getCurrentPanel()).val();
	checkCombo = checkCombo==undefined?"":checkCombo;
	var startTime= $("#d1",navTab.getCurrentPanel()).val();
	startTime = startTime==undefined?"":startTime;
	var endTime= $("#d2",navTab.getCurrentPanel()).val();
	endTime = endTime==undefined?"":endTime;
	var statusYm= $("#statusYm",navTab.getCurrentPanel()).val();
	var branchCompany= $("#branchCompany",navTab.getCurrentPanel()).val();
	
	var queryString = "pBatchNo=" + pBatchNo+"&wereName="+pWereName+"&code="+pCode+ "&wereIdcard="
	+pWereIdcard +"&werePhone="+pWerePhone+"&applicationNo="+pApplicationNo+"&checkCombo="+checkCombo+"&startTime="+startTime
	+"&endTime="+endTime+"&statusYm="+statusYm+"&branchCompany="+branchCompany;
	/* var a = document.createElement("A");
	a.href = "${path}/warehouse/prepCustomer!exportExcelForStrem.action?"+queryString;
	a.target = "_blank";
	a.innerHTML = "${path}/warehouse/prepCustomer!exportExcelForStrem.action?"+queryString;
	document.body.appendChild(a); */
	//location.href="${path}/warehouse/prepCustomer!exportExcelForStrem.action?"+queryString;
	window.open("${path}/warehouse/prepCustomer!exportExcelForStrem.action?"+queryString);
	/* navTab.openTab("updatePrepCustomer",
			"${path}/warehouse/prepCustomer!exportExcelForStrem.action?"+queryString, {
				title : "变更预导入客户",
				fresh : false,
				data : {}
			}); */
}

	function addPACustomer(){//获取平安客户
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		$.pdialog.open("${path}/warehouse/prepCustomer!addPACustomer.action?navTabId="+navTabId, "addPACustomer", "平安客户获取", 
				{width:400,height:200,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	}
	
	function updateErrorCustomer(){//检测失败客户
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		$.pdialog.open("${path}/warehouse/prepCustomer!updateErrorCustomer.action?navTabId="+navTabId, "updateErrorCustomer", "检测失败客户", 
				{width:400,height:200,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	}

</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/warehouse/prepCustomer!listPrepCustomer.action')
	{this.action = '${path}/warehouse/prepCustomer!listPrepCustomer.action' ;} ;return navTabSearch(this);" 
	action="${path}/warehouse/prepCustomer!listPrepCustomer.action" method="post" rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" id="pBatchNo" name="preCustomerQueryObj.batchNo" value="${preCustomerQueryObj.batchNo}"/>
				</td>  
				<td>
					<label>姓名：</label> 
					<input type="text" id="pWereName" name="preCustomerQueryObj.wereName"  value="${preCustomerQueryObj.wereName}"/>
				</td>
				<td>
					<label>条形码：</label> 
					<input type="text" id="pCode" name="preCustomerQueryObj.code" value="${preCustomerQueryObj.code}"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>被检人身份证号：</label> 
					<input type="text" id="pWereIdcard" name="preCustomerQueryObj.wereIdcard"  value="${preCustomerQueryObj.wereIdcard}"/>
				</td>
				<td>
					<label>被检人手机号：</label> 
					<input type="text" id="pWerePhone" name="preCustomerQueryObj.werePhone"  value="${preCustomerQueryObj.werePhone}"/>
				</td>
				<td>
					<label>物料申请单号：</label> 
					<input type="text" id="pApplicationNo" name="preCustomerQueryObj.applicationNo"  value="${preCustomerQueryObj.applicationNo}"/>
				</td>
			</tr>	
			
			<tr>
				<td>
					<label>套餐名：</label> 
					<input type="text" id="checkcombo" name="preCustomerQueryObj.checkCombo"  value="${preCustomerQueryObj.checkCombo}"/>
				</td>
				<td>
					<label>导入开始时间：</label> 
					<input type="text" id="d1" name="preCustomerQueryObj.startTime" 
						style="width: 170px;"
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
						readonly="readonly" value="${preCustomerQueryObj.startTime}" /> <a
						class="inputDateButton" href="javascript:;">选择</a> 
				</td>
				<td>
					<label>导入结束时间：</label> 
					<input type="text" id="d1" name="preCustomerQueryObj.endTime" 
						style="width: 170px;"
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
						readonly="readonly" value="${preCustomerQueryObj.endTime}" /> <a
						class="inputDateButton" href="javascript:;">选择</a> 
				</td>
			</tr>
			
			<tr>
				<td>
					<label>状态：</label>
					<select id="statusYm" class="select" name="preCustomerQueryObj.statusYm">
						<option value="" >请选择</option> 
						<option value="0" ${preCustomerQueryObj.statusYm=="100"?"selected":""}>采样盒已寄出</option> 
						<option value="100" ${preCustomerQueryObj.statusYm=="140"?"selected":""}>样本采集中</option> 
						<option value="100" ${preCustomerQueryObj.statusYm=="150"?"selected":""}>样本已获取</option> 
						<option value="200" ${preCustomerQueryObj.statusYm=="200"?"selected":""}>样本已送检</option> 
						<option value="300" ${preCustomerQueryObj.statusYm=="300"?"selected":""}>电子报告已出具</option> 
						<option value="400" ${preCustomerQueryObj.statusYm=="400"?"selected":""}>报告已下载</option> 
						<option value="500" ${preCustomerQueryObj.statusYm=="500"?"selected":""}>报告已打印</option> 
						<option value="500" ${preCustomerQueryObj.statusYm=="600"?"selected":""}>报告已寄送</option> 
					</select> 
				</td>
				<td>
					<label>支公司：</label> 
					<input type="text" id="branchCompany" name="preCustomerQueryObj.branchCompany"  value="${preCustomerQueryObj.branchCompany}"/>
				</td>
				<td>
					<div style="margin-left:60px;" class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
			
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="addErpCustomer()" href="javascript:void(0);" ><span>录入客户</span></a></li>
			<li><a class="edit" onclick="updatePrepCustomer()" href="javascript:void(0);"><span>变更预导入客户</span></a></li>
			<li><a class="icon" id="preCustomerList" href="javascript:void(0);"><span>预导入客户清单</span></a></li>
			<li><a class="icon" onclick="exportExcelForStrem()" href="javascript:void(0);"><span>导出Excel</span></a></li>
			<li><a class="icon" onclick="addPACustomer()" href="javascript:void(0);"><span>获取平安客户</span></a></li>
			<li><a class="icon" onclick="updateErrorCustomer()" href="javascript:void(0);"><span>检测失败</span></a></li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> -->
				
				<th>序号</th>
				<th  export = "true" columnEnName = "batchNo" columnChName = "批次号" >批次号</th>
				<th  export = "true" columnEnName = "code" columnChName = "条码" >条码</th>
				<th  export = "true" columnEnName = "wereName" columnChName = "姓名" >姓名</th>
				<th  export = "true" columnEnName = "wereSex" columnChName = "性别" >性别</th>
				<th  export = "true" columnEnName = "wereAge" columnChName = "年龄" >年龄</th>
				<th  export = "true" columnEnName = "wereIdcard" columnChName = "身份证号" >身份证号</th>
				<th  export = "true" columnEnName = "werePhone" columnChName = "电话" >电话</th>
				<th  export = "true" columnEnName = "checkCobmo" columnChName = "套餐" >套餐</th>
				<th  export = "true" columnEnName = "" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "salesman" columnChName = "营销员" >营销员</th>
				<th  export = "true" columnEnName = "statusYM" columnChName = "客户状态" >客户状态</th>
				<th  export = "true" columnEnName = "createTime" columnChName = "导入时间" >导入时间</th>
				<th  export = "true" columnEnName = "filePath" columnChName = "电子报告" >电子报告</th>
				<th  export = "true" columnEnName = "uploadReportTime" columnChName = "报告上传时间" >报告上传时间</th>
				<th  export = "true" columnEnName = "remark" columnChName = "备注" >备注</th>
				<th  export = "false" columnEnName = "" columnChName = "操作" width="50">操作</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }" >
					<td align="center">
						<input type="checkbox" name="ids" value="${entity.id},${entity.batchNo}">
						${status.count}
					</td> 
					<td align="center">	${entity.batchNo}</td>
					<td align="center">	${entity.code}</td>
					<td align="center">	${entity.wereName}</td>
					<td align="center">	${entity.wereSex}</td>
					<td align="center">	${entity.wereAge}</td>
					<td align="center">	${entity.wereIdcard}</td>
					<td align="center">	${entity.werePhone}</td>
					<td align="center">	${entity.checkCobmo}</td>
					<td align="center">	${entity.branchCompany}</td>
					<td align="center">	${entity.salesman}</td>
					<td align="center">
						<c:choose>
							<c:when test="${entity.statusYm==110}">采样盒已寄出</c:when>
							<c:when test="${entity.statusYm==140}">样本采集中</c:when>
							<c:when test="${entity.statusYm==150}">样本已获取</c:when>
							<c:when test="${entity.statusYm==200}">样本已送检</c:when>
							<c:when test="${entity.statusYm==300}">电子报告已出具</c:when>
							<c:when test="${entity.statusYm==400}">报告已下载</c:when>
							<c:when test="${entity.statusYm==500}">报告已打印</c:when>
							<c:when test="${entity.statusYm==600}">报告已寄送</c:when>
						</c:choose>
					</td>
					<td align="center">	${fn:substring(entity.createTime,0,19)}</td>
					<td align="center">
						<c:if test="${null != entity.filePath}"><a href="${entity.filePath}" target="blank">查看</a></c:if>
					</td>
					<td align="center">	${fn:substring(entity.uploadReportTime,0,19)}</td>
					<td align="center">	${entity.remark}</td>
					<td align="center">	<div class="panelBar"><ul class="toolBar"><li><a class="delete" onclick="deletePrepCustomer('${entity.id}')" ><span>删除</span></a></li></ul></div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 



