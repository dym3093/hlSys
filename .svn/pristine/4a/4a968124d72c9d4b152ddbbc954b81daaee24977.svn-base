<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>

<script type="text/javascript" language="javascript">

$(document).ready(function(){
	var selectInput = $('input[name="selectState"]',navTab.getCurrentPanel()).val();
	if(selectInput!="undefined"){
		if(selectInput=='0'){
			$('select[name="filter_and_printState_EQ_S"]',navTab.getCurrentPanel()).find("option[value='0']").attr("selected",true);
		}else if(selectInput=='1'){
			$('select[name="filter_and_printState_EQ_S"]',navTab.getCurrentPanel()).find("option[value='1']").attr("selected",true);
		}else if(selectInput=='2'){
			$('select[name="filter_and_printState_EQ_S"]',navTab.getCurrentPanel()).find("option[value='2']").attr("selected",true);
		}else if(selectInput=='3'){
			$('select[name="filter_and_printState_EQ_S"]',navTab.getCurrentPanel()).find("option[value='3']").attr("selected",true);
		}else if(selectInput=='4'){
			$('select[name="filter_and_printState_EQ_S"]',navTab.getCurrentPanel()).find("option[value='4']").attr("selected",true);
		}else if(selectInput=='5'){
			$('select[name="filter_and_printState_EQ_S"]',navTab.getCurrentPanel()).find("option[value='5']").attr("selected",true);
		}
		
	}
	
	/*
	 * 初始化的时候获取打印公司;
	 * [数据来源即库存管理中的仓库名称]
	 * create by henry.xu 20160811
	 */
	$.ajax({	//初始化页面时套餐
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/report/xreportPrintBatch!findAllCustomer.action",
		success: function(data){
			var selectWareId = $('input[name="selectWareId"]',navTab.getCurrentPanel()).val();
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
			$("select[name='filter_and_printcompanyId_EQ_S']",navTab.getCurrentPanel()).append(option);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	
	
});

//变更用于复选框
function changeProduct() {
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择要打印的打印任务！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息进行变更！');
		return ;		
	}
	$.ajax({
		type: "post",
		cache: false,
		data:{"id":ids},
		url: "../report/xreportPrintTask!confirmPrintTask.action",
		success: function(data){
			alert("打印成功！");
		},
		error:function(){
			alert("打印失败！");
			return;
		}
	});
	/*else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyConference", "../report/xreportPrintTask!confirmPrintTask.action?id="+ids+"&navTabId="+navTabId, { title:"打印任务", fresh:false, data:{} });
	}*/
}

//打开添加任务窗口
function showAddDialog(){
	$.pdialog.open("../report/xreportPrintTask!showAddDialog.action", "extendPrintTask", "增加打印任务",{width:800,height:600});
}

//删除数据(旧的 mark2016-04-23)
function deleteProduct(){
	var ids = [];
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count +=1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alertMsg.info('<span style="color:#FF0000">请选择要删除的打印任务！</span>');
		return ;
	}
	
	alertMsg.confirm('<span style="color:#FF0000;">确认删除【'+count+'】条打印任务?</span>', {
        okCall: function(){
        	$.ajax({
				url: '../report/xreportPrintTask!delPrintTask.action',
				method: 'post',
				cache: false,
				data: {'ids':ids},
				success: function(data){
					var resp= eval("("+data+")");
					if(resp.result=='success'){
						alertMsg.correct("删除成功！");
					}else{
						alertMsg.error("删除失败！");
					}					
					return navTabSearch(this);
				},
				error: function(resp){
					alertMsg.error("删除失败！");
				}
			}); 
        }
	
	});

}

//删除打印任务
function deletePrintTask(printTaskId,printTaskNo){

	alertMsg.confirm('<span style="color:#FF0000;">确认删除【'+printTaskNo+'】打印任务?</span>', {
			okCall: function(){
				$.ajax({
					url: '../report/xreportPrintTask!deletePrintTask.action',
					method: 'post',
					cache: false,
					data: {'ids':printTaskId},
					success: function(resp){
						alertMsg.correct('删除成功！');
						return navTabSearch(this);
					},
					error: function(resp){
						alertMsg.error('删除失败！');
					}
				}); 
			}
		
		});

}

//TODO
function listAlreadyPrintBatch(){
	//$.pdialog.open("../report/xreportPrintBatch!listAlreadyPrintBatch.action", "listAlreadyPrintBatch", "已处理打印批次",{width:1000,height:600});
	//改为打开一个平级页面
	navTab.openTab("listAlreadyPrintBatch", "../report/xreportPrintBatch!listAlreadyPrintBatch.action", { title:"已处理打印批次", fresh:true, data:{} });
}
/*
//变更用于单行
function changeProduct(ids) {
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
    navTab.openTab("modifyConference", "../report/xreportPrintTask!toModifyPrintTask.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
}
*/

//未确认
function state0(id, printTaskNo){

	navTab.openTab("unPrintTab", "../report/xreportPrintTask!listPrintTaskById.action?id="+id+"&printTaskNo="+printTaskNo+"&pageLocation=lookPrintTask", { title:"任务明细", fresh:true, data:{} });
} 

//下载
function state1(id, printTaskNo){
	navTab.openTab("downLoadTab", "../report/xreportPrintTask!listPrintTaskById.action?id="+id+"&printTaskNo="+printTaskNo, { title:"任务明细", fresh:true, data:{} });

}
function state2(id){
	
}
function state3(id){
	if(0==id){
		return;
	}else{
		$.pdialog.open("../report/xreportPrintTask!waitComplete.action?id="+id+"", "extendPrintTask", "待反馈",{width:800,height:400});
	}
}
function state4(id){
	if(0==id){
		return;
	}else{
		$.pdialog.open("../report/xreportPrintTask!confirmSigning.action?id="+id+"", "extendPrintTask", "确认收货",{width:800,height:400});
	}
}

//将选中的状态放入input
$('select[name="filter_and_printState_EQ_S"]').change(function (){
	var selectState = $('select[name="filter_and_printState_EQ_S"] option:selected',navTab.getCurrentPanel()).val();
	$('input[name="selectState"]',navTab.getCurrentPanel()).val(selectState);
});

//
function importMoreCodes(){
	$("#queryCodes",navTab.getCurrentPanel()).val("");
	$.ajaxFileUpload({
		url:'${path}/report/xreportPrintBatch!importCodesByExcel.action',
        type:'post',
       	secureuri: false,
        fileElementId: 'affix',
        dataType: 'json',
		success:function(data,status){
			if(data.result=="success"){
				alert("导入成功！");
				$("#queryCodes",navTab.getCurrentPanel()).val(data.codes);
				$("#queryCodes",navTab.getCurrentPanel()).attr("name","filter_and_code_IN_S");
			}else{
				alert("导入失败！");
			}
		},
		error:function (data,status,e){
        	alert("服务发生异常，请稍后再试！");
        }	
	});	
}

/*
 * 批量下载功能;
 * create by henry.xu 20160812
 */
function batchDownload() {
	
	var ids = ''; //所有的id数据拼接;
	var arr = [];
	$("input:checkbox[name='expressNo']:checked", navTab.getCurrentPanel()).each(
		function(i, n) {
			arr[i] = n.value;
	});
	//当没有选择数据退出该方法
	if (arr.length == 0) {
		alert('请选择要下载的任务！');
		return;
	} 
	//数组拼接为字符串
	ids = arr.join();
	//请求后台数据处理后返回路径进行跳转;
	$.ajax({
		type : "post",
		cache : false,
		data : {
			"ids" : ids
		},
		url : "../report/xreportPrintTask!batchDownload.action",
		success : function(data) {
			//获取访问路径,然后重定位访问;
			var pathInfo= eval("("+data+")");
			window.open(pathInfo.path,'newwindow');
		},
		error : function() {
			alert("批量下载失败！");
			return;
		}
	});

}
function showExpressInfo(){
	var checkbox = $("input:checkbox[name='expressNo']:checked");
	var id = $(checkbox).val();
	if($(checkbox).length!=1){
		alertMsg.error("请选择一条要修改的信息");
	}else{
		$.pdialog.open("../report/xreportPrintTask!showExpressInfo.action?id="+id+"", "showExpressInfo", "修改寄送信息",{width:400,height:200});
	}
}
$("#checkAll",navTab.getCurrentPanel()).change(function (){//全选
	if($(this).attr("checked")=="checked"){
		$(this).parents("table").children().eq(1).find(":checkbox").attr("checked",true);
	}else{
		$(this).parents("table").children().eq(1).find(":checkbox").attr("checked",false);
	}
});

function checkAll(){//全选或部分选择之后的操作
	var idArr = [];
	var noArr = [];
	$("input:checkbox[name='expressNo']:checked", navTab.getCurrentPanel()).each(
		function(index, items) {
			idArr[index] = items.value;
			noArr[index] = $(items).parent().next().text();
	});
	if (idArr.length == 0) {
		alert('请选择批量不打印任务');
		return;
	}
	var ids = idArr.join();
	var printTaskNos = noArr.join();
//	var form = $("#pagerFindForm",navTab.getCurrentPanel());
	$.ajax({
		type:"post",
		cache:false,
		data:{"ids":ids,"printTaskNos":printTaskNos},
		url : "../report/xreportPrintTask!confirmBatchPrintTask.action",
		success : function(data) {
			var result= eval("("+data+")").result;
			if(result=="success"){
				alertMsg.correct("提交成功");
				return navTabSearch($("#pagerFindForm",navTab.getCurrentPanel()));
			}else{
				alertMsg.error("提交失败");
			}
		},
		error : function() {
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}
</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/report/xreportPrintTask!listPrintTask2.action'){this.action = '${path}/report/xreportPrintTask!listPrintTask2.action' ;} ;return navTabSearch(this);" action="${path}/report/xreportPrintTask!listPrintTask2.action" method="post"  rel="pagerForm" >
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>打印任务号：</label> 
					<input type="text" name="filter_and_printTaskNo_LIKE_S" value="${filter_and_printTaskNo_LIKE_S}"/>
				</td>
				<td>
					<label>打印状态：</label> 
					<input type="hidden" name="selectState" value="${selectState}"/>
					<select id="printState" name="filter_and_printState_EQ_S" rel="iselect">
						 <%-- <option value="${filter_and_pritState_EQ_S}"></option>  --%>
						 <option value="">---请选择---</option>
						 <option value="0">未打印</option> 
						 <option value="1">已下载</option> 
						 <option value="2">已发送</option>
						 <option value="3">待反馈</option> 
						 <option value="4">待签收</option>
						 <option value="5">已完成</option>
         			</select>
				</td>
				<td>
					<label>打印公司：</label>
					<select id="warehouseId" name="filter_and_printcompanyId_EQ_S" style="width:193px;margin-top:5px;margin-left: 5px">
					<input type="hidden" name="selectWareId" value="${filter_and_printcompanyId_EQ_S}"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>起始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td>
					<label>收件人：</label> 
					<input type="text" name="filter_and_expressRecipient_LIKE_S" value="${filter_and_expressRecipient_LIKE_S}"/>
				</td>
				<td>
					<label>快递单号：</label> 
					<input type="text" name="filter_and_expressNo_LIKE_S" value="${filter_and_expressNo_LIKE_S}"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
					<label>条形码：</label>
                    <input type="text" id="queryCodes" name="filter_and_code_LIKE_S" readonly="readonly" value="${codes}"/>
				</td>
				<td>
					<input type="file" id="affix" name="affix"/>
                    <button type="button" onclick="importMoreCodes()" >导入条码</button>
				</td>
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
		<c:if test="${currentUser.accountName!='parkson'}">
		<ul class="toolBar">
 			<li><a class="icon" onclick="listAlreadyPrintBatch()" style="cursor:pointer;"><span>已处理打印批次</span></a></li>
 			<li><a class="icon" onclick="batchDownload();" style="cursor:pointer;" href="javascript:void(0);"><span>批量下载</span></a></li>
 			<li><a class="icon" onclick="checkAll();" style="cursor:pointer;" href="javascript:void(0);"><span>批量确认打印</span></a></li>
 			<li><a class="edit" onclick="showExpressInfo();" style="cursor:pointer;" href="javascript:void(0);"><span>修改寄送信息</span></a></li>
		</ul>
		</c:if>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
		<thead>
			<tr>
				<th width="50" nowrap="true"><input type="checkbox" id="checkAll"/>序号</th>
				<th width="100"  export= "true" columnEnName = "printTaskNo" columnChName = "批次号" >打印任务号</th>
				<th width="100"  export= "true" columnEnName = "printTaskCompany" columnChName = "打印公司" >打印公司</th>
				<th width="200"  export= "true" columnEnName = "address" columnChName = "寄送地址" >寄送地址</th>
				<th width="100"  export= "true" columnEnName = "expressRecipient" columnChName = "收件人" >收件人</th>
				<th width="100"  export= "true" columnEnName = "telephone" columnChName = "快点单号" >收件人电话</th>
				<th width="100"  export= "true" columnEnName = "expressNo" columnChName = "创建时间" >快递单号</th>
				<th width="100"  export= "true" columnEnName = "createTime" columnChName = "创建时间" >创建时间</th>
				<th width="100"  export= "true" columnEnName = "expectTime" columnChName = "预计时间" >预计时间</th>
				<th width="100"  export= "true" columnEnName = "printState" columnChName = "打印状态" >打印状态</th>
				<th width="200"  export= "true" columnEnName = "operation" columnChName = "操作" >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="xreportPrintTask" varStatus="status">
					<tr target="sid_user" rel="${xreportPrintTask.id }">
						<td align="center" style="width:45px;">
							<input type="checkbox" name="expressNo" value="${xreportPrintTask.id}">${ status.count }
						</td> 
						<td width="100" nowrap="true" align="center" title="${xreportPrintTask.printTaskNo}">${xreportPrintTask.printTaskNo}</td>
						<td width="100" nowrap="true" align="center">${xreportPrintTask.printCompany}</td>
						<td width="200" nowrap="true" align="center" title="${xreportPrintTask.address}">
							<c:if test="${fn:length(xreportPrintTask.address) > 8 }">
								${fn:substring(xreportPrintTask.address, 0, 8) }...
							</c:if>
							<c:if test="${fn:length(xreportPrintTask.address) <= 8 }">
								${xreportPrintTask.address }
							</c:if>
						</td>
						<td width="100" nowrap="true" align="center">${xreportPrintTask.expressRecipient}</td>
						<td width="100" nowrap="true" align="center">${xreportPrintTask.telephone}</td>
						<td width="100" nowrap="true" align="center">${xreportPrintTask.expressNo}</td>
						<td width="100" nowrap="true" align="center">${fn:substring(xreportPrintTask.createTime,0,19)}</td>
						<td width="100" nowrap="true" align="center">${fn:substring(xreportPrintTask.expectTime,0,19)}</td>
						<td width="100" nowrap="true" align="center">
							<c:if test="${xreportPrintTask.printState==0}">未打印</c:if>
							<c:if test="${xreportPrintTask.printState==1}">已下载</c:if>
							<c:if test="${xreportPrintTask.printState==2}">已发送</c:if>
							<c:if test="${xreportPrintTask.printState==3}">待反馈</c:if>
							<c:if test="${xreportPrintTask.printState==4}">待签收</c:if>
							<c:if test="${xreportPrintTask.printState==5}">已完成</c:if>
						</td>
						<td width="200" nowrap="true" align="center">
							<c:choose>
								<c:when test="${xreportPrintTask.printState=='0'}">
									<div class="buttonActive">
										<a class="add" href="../report/xreportPrintTask!listPrintTaskById.action?pageLocation=undonePrintTask&id=${xreportPrintTask.id}&printTaskNo=${xreportPrintTask.printTaskNo}" target="navTab" rel="add"><span>未打印</span></a>
									</div>
									<div class="buttonActive">
										<span style="border-left:10px;">
										  <button onclick="deletePrintTask('${xreportPrintTask.id}','${xreportPrintTask.printTaskNo}')">删除</button>
										</span>
							            
						          	</div>
								</c:when>
								<c:when test="${xreportPrintTask.printState=='2'}">
									<div class="buttonActive">
							            <div class="buttonContent">
							              <button onclick="state2('${xreportPrintTask.id}')" class="add">已发送</button>
							            </div>
						          	</div>
								</c:when>
								<c:when test="${xreportPrintTask.printState=='3'}">
									<div class="buttonActive">
							            <div class="buttonContent">
							              <button onclick="state3('${xreportPrintTask.id}')">待反馈</button>
							            </div>
						          	</div>
								</c:when>
								<c:when test="${xreportPrintTask.printState=='4'}">
									<div class="buttonActive">
							            <div class="buttonContent">
							              <button onclick="state4('${xreportPrintTask.id}')">待签收</button>
							            </div>
						          	</div>
								</c:when>
								<c:when test="${xreportPrintTask.printState=='5'}">
									<div class="buttonActive">
							            <div class="buttonContent">
							             	<button disabled="disabled">已完成</button>
							            </div>
						          	</div>
								</c:when>
							</c:choose>
							<c:if test="${not empty xreportPrintTask.downLoadPath}">
									<div class="buttonActive">
										<a class="add" href="${xreportPrintTask.downLoadPath}" target="_blank" ><span>下载</span></a>
						          	</div>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 