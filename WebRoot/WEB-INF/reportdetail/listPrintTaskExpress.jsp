<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	.select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
</style>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>

<script type="text/javascript" language="javascript">

$(document).ready(function(){
	
	/*
	 * 初始化的时候获取打印公司;
	 * [数据来源即库存管理中的仓库名称]
	 * create by henry.xu 20160811
	 */
	$.ajax({	//初始化页面打印公司
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/warehouse/warehouse!findAllCustomer.action",
		success: function(data){
			var selectWareId = $('input[name="filter_and_printCompanyId_EQ_S"]',navTab.getCurrentPanel()).val();
			$("#warehouseId",navTab.getCurrentPanel()).empty();
			var wares= eval("("+data+")");
			var option="<option value=''>---请选择---</option>";
			
			for(var i=0;i<wares.length;i++){	
				if(selectWareId==wares[i].id){
					option += "<option value='"+wares[i].id+"' selected='selected'>"+wares[i].text+"</option>";
				}else{
					option += "<option value='"+wares[i].id+"'>"+wares[i].text+"</option>";
				}
			}
			//将节点插入到下拉列表中
			$("#warehouseId",navTab.getCurrentPanel()).append(option);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	
	
});


//将选中的状态放入input
$("#warehouseId",navTab.getCurrentPanel()).change(function (){
	var selectState = $('#warehouseId option:selected',navTab.getCurrentPanel()).val();
	$('input[name="filter_and_printCompanyId_EQ_S"]',navTab.getCurrentPanel()).val(selectState);
});

function insertExpressInfo(){
	var checkbox = $("input:checkbox[name='expressNo']:checked",navTab.getCurrentPanel());
	var id = $(checkbox).val();
	var conditions =$("#pagerFindForm",navTab.getCurrentPanel()).serializeArray();
	var jsonData = "[";
	var count = 0;
	jQuery.each( conditions, function(i, field){
// 		if($.trim(field.value)!=""){
			jsonData += "{\""+field.name+"\":\""+encodeURI(field.value)+"\""+"},";
//		}
		count++;
	});
	if (jsonData.lastIndexOf(",")) {  
		jsonData = jsonData.substring(0,jsonData.length -1);  
		jsonData += "]";
	}
	if(count==0){
		jsonData="";
	}
	if($(checkbox).length!=1){
		alertMsg.error("请选择一条要修改的信息");
	}else{
		$.pdialog.open("../reportdetail/erpPrintTask!showExpressInput.action?id="+id+"&jsonData="+jsonData, "showExpressInput", "快递补录",{width:500,height:200});
	}
}
function updateState(obj){
	var id = $(obj).parents("tr").children().find(":checkbox").val();
	$.ajax({	//初始化页面打印公司
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/reportdetail/erpPrintTask!updateState.action",
		data:{"id":id},
		success: function(data){
			var str= eval("("+data+")");
			if(str.count==1){
				alertMsg.correct("修改成功");
			}else{
				alertMsg.error("修改失败");
			}
			navTabSearch(this);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}
</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportdetail/erpPrintTask!listPrintTaskExpress.action'){this.action = '${path}/reportdetail/erpPrintTask!listPrintTaskExpress.action' ;} ;return navTabSearch(this);" action="${path}/reportdetail/erpPrintTask!listPrintTaskExpress.action" method="post">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>打印任务号：</label> 
					<input type="text" name="filter_and_printTaskNo_LIKE_S" value="${filter_and_printTaskNo_LIKE_S}"/>
				</td>
				<td>
					<label>打印状态：</label> 
					<select id="printState" name="filter_and_printState_EQ_S" class="select">
						 <option value="" >---请选择---</option>
						 <option value="0" ${filter_and_printState_EQ_S=="0"?"selected":""}>未打印</option> 
						 <option value="1" ${filter_and_printState_EQ_S=="1"?"selected":""}>已下载</option> 
						 <option value="2" ${filter_and_printState_EQ_S=="2"?"selected":""}>已发送</option>
						 <option value="3" ${filter_and_printState_EQ_S=="3"?"selected":""}>待反馈</option> 
						 <option value="4" ${filter_and_printState_EQ_S=="4"?"selected":""}>待签收</option>
						 <option value="5" ${filter_and_printState_EQ_S=="5"?"selected":""}>已完成</option>
         			</select>
				</td>
				<td>
					<label>打印公司：</label>
					<select id="warehouseId" style="width:198px;margin-top:5px;">
					<input type="hidden" name="filter_and_printCompanyId_EQ_S" value="${filter_and_printCompanyId_EQ_S}"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>起始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td>
					<label>结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
			</tr>
			
			<tr>
				<td></td>
				<td></td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
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
 			<li><a class="edit" onclick="insertExpressInfo();" style="cursor:pointer;" href="javascript:void(0);"><span>快递补录</span></a></li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="35" nowrap="true">序号</th>
				<th width="100"  export= "true" columnEnName = "printTaskNo" columnChName = "批次号" >打印任务号</th>
				<th width="100"  export= "true" columnEnName = "printTaskCompany" columnChName = "打印公司" >打印公司</th>
				<th width="200"  export= "true" columnEnName = "address" columnChName = "寄送地址" >寄送地址</th>
				<th width="100"  export= "true" columnEnName = "expressRecipient" columnChName = "收件人" >收件人</th>
				<th width="100"  export= "true" columnEnName = "telephone" columnChName = "快点单号" >收件人电话</th>
				<th width="100"  export= "true" columnEnName = "expressNo" columnChName = "创建时间" >快递单号</th>
				<th width="100"  export= "true" columnEnName = "createTime" columnChName = "创建时间" >创建时间</th>
				<th width="100"  export= "true" columnEnName = "expectTime" columnChName = "预计时间" >预计时间</th>
				<th width="100"  export= "true" columnEnName = "printState" columnChName = "打印状态" >打印状态</th>
				<th width="100"  export= "false" columnEnName = "operation" columnChName = "操作" >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpPrintTask" varStatus="status">
				<tr target="sid_user" rel="${erpPrintTask.id }">
					<td align="center" style="width:35px;" nowrap="true">
						<input type="checkbox" name="expressNo" value="${erpPrintTask.id}">${ status.count }
					</td> 
					<td width="100" nowrap="true" align="center" title="${erpPrintTask.printTaskNo}">${erpPrintTask.printTaskNo}</td>
					<td width="100" nowrap="true" align="center">${erpPrintTask.printCompany}</td>
					<td width="200" nowrap="true" align="center" title="${erpPrintTask.address}">${erpPrintTask.address}</td>
					<td width="100" nowrap="true" align="center">${erpPrintTask.expressRecipient}</td>
					<td width="100" nowrap="true" align="center">${erpPrintTask.telephone}</td>
					<td width="100" nowrap="true" align="center">${erpPrintTask.expressNo}</td>
					<td width="100" nowrap="true" align="center">${fn:substring(erpPrintTask.createTime,0,19)}</td>
					<td width="100" nowrap="true" align="center">${fn:substring(erpPrintTask.expectTime,0,19)}</td>
					<td width="100" nowrap="true" align="center">
						<c:if test="${erpPrintTask.printState==0}">未打印</c:if>
						<c:if test="${erpPrintTask.printState==1}">已下载</c:if>
						<c:if test="${erpPrintTask.printState==2}">已发送</c:if>
						<c:if test="${erpPrintTask.printState==3}">待反馈</c:if>
						<c:if test="${erpPrintTask.printState==4}">待签收</c:if>
						<c:if test="${erpPrintTask.printState==5}">已完成</c:if>
					</td>
					<td width="100" nowrap="true" align="center"><button type="button" onclick="updateState(this)">报告完成</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div> 