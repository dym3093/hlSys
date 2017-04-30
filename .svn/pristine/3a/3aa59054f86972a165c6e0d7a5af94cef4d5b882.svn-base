<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

//已超时的打印任务列表

$(document).ready(function(){
	var selectInput = $('input[name="selectState"]').val();
	if(selectInput!="undefined"){
		if(selectInput=='0'){
			$('select[name="filter_and_printState_EQ_S"]').find("option[value='0']").attr("selected",true);
		}else if(selectInput=='1'){
			$('select[name="filter_and_printState_EQ_S"]').find("option[value='1']").attr("selected",true);
		}else if(selectInput=='2'){
			$('select[name="filter_and_printState_EQ_S"]').find("option[value='2']").attr("selected",true);
		}else if(selectInput=='3'){
			$('select[name="filter_and_printState_EQ_S"]').find("option[value='3']").attr("selected",true);
		}else if(selectInput=='4'){
			$('select[name="filter_and_printState_EQ_S"]').find("option[value='4']").attr("selected",true);
		}else if(selectInput=='5'){
			$('select[name="filter_and_printState_EQ_S"]').find("option[value='5']").attr("selected",true);
		}
		
	}
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
		url: "../reportdetail/erpPrintTask!confirmPrintTask.action",
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
		navTab.openTab("modifyConference", "../reportdetail/erpPrintTask!confirmPrintTask.action?id="+ids+"&navTabId="+navTabId, { title:"打印任务", fresh:false, data:{} });
	}*/
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
				url: '../reportdetail/erpPrintTask!delPrintTask.action',
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
					url: '../reportdetail/erpPrintTask!deletePrintTask.action',
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
	$.pdialog.open("../reportdetail/erpPrintBatch!listAlreadyPrintBatch.action", "listAlreadyPrintBatch", "已处理打印批次",{width:1000,height:600});
}
/*
//变更用于单行
function changeProduct(ids) {
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
    navTab.openTab("modifyConference", "../reportdetail/erpPrintTask!toModifyPrintTask.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
}
*/

//未确认
function state0(id, printTaskNo){

	navTab.openTab("unPrintTab", "../reportdetail/erpPrintTask!listPrintTaskById.action?id="+id+"&printTaskNo="+printTaskNo+"&pageLocation=lookPrintTask", { title:"任务明细", fresh:true, data:{} });
} 

//下载
function state1(id, printTaskNo){
	navTab.openTab("downLoadTab", "../reportdetail/erpPrintTask!listPrintTaskById.action?id="+id+"&printTaskNo="+printTaskNo, { title:"任务明细", fresh:true, data:{} });

}
function state2(id){
	
}
function state3(id){
	if(0==id){
		return;
	}else{
		$.pdialog.open("../reportdetail/erpPrintTask!waitComplete.action?id="+id+"", "extendPrintTask", "待反馈",{width:800,height:400});
	}
}
function state4(id){
	if(0==id){
		return;
	}else{
		$.pdialog.open("../reportdetail/erpPrintTask!confirmSigning.action?id="+id+"", "extendPrintTask", "确认收货",{width:800,height:400});
	}
}

//将选中的状态放入input
$('select[name="filter_and_printState_EQ_S"]').change(function (){
	var selectState = $('select[name="filter_and_printState_EQ_S"] option:selected').val();
	$('input[name="selectState"]').val(selectState);
});
	
</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportdetail/erpPrintTask!listPrintTaskOverTime.action'){this.action = '${path}/reportdetail/erpPrintTask!listPrintTask.action' ;} ;return navTabSearch(this);" action="${path}/reportdetail/erpPrintTask!listPrintTask.action" method="post"  rel="pagerForm">
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
			</tr>
			<tr>
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
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
		</ul>
		</c:if>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "printTaskNo" columnChName = "批次号" >打印任务号</th>
				<th  export= "true" columnEnName = "createTime" columnChName = "创建时间" >创建时间</th>
				<th  export= "true" columnEnName = "createTime" columnChName = "创建时间" >打印公司</th>
				<th  export= "true" columnEnName = "createTime" columnChName = "创建时间" >预计时间</th>
				<th  export= "true" columnEnName = "createTime" columnChName = "创建时间" >打印状态</th>
				<th  export= "true" columnEnName = "createTime" columnChName = "创建时间" >操作</th>
				<%-- <c:if test="${currentUser.accountName!='parkson'}">
					<th  export = "false" columnEnName = "" columnChName = "" >操作</th>
				</c:if> --%>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpPrintTask" varStatus="status">
				<tr target="sid_user" rel="${erpPrintTask.id }">
					<td align="center">
						<c:if test="${currentUser.accountName!='parkson'}">
							<input type="checkbox" name="ids" value="${erpPrintTask.id}">
						</c:if>
						${ status.count }
					</td> 
					
					<td align="center">
						${erpPrintTask.printTaskNo}
					</td>
					<td align="center">${fn:substring(erpPrintTask.createTime,0,19)}</td>
					<td align="center">${erpPrintTask.printCompany}</td>
					<td align="center">${fn:substring(erpPrintTask.expectTime,0,19)}</td>
					<td align="center">
						<c:if test="${erpPrintTask.printState==0}">未打印</c:if>
						<c:if test="${erpPrintTask.printState==1}">已下载</c:if>
						<c:if test="${erpPrintTask.printState==2}">已发送</c:if>
						<c:if test="${erpPrintTask.printState==3}">待反馈</c:if>
						<c:if test="${erpPrintTask.printState==4}">待签收</c:if>
						<c:if test="${erpPrintTask.printState==5}">已完成</c:if>
					</td>
					<td align="center">
						<c:choose>
							<c:when test="${erpPrintTask.printState=='0'}">
								<div class="buttonActive">
									<a class="add" href="../reportdetail/erpPrintTask!listPrintTaskById.action?pageLocation=undonePrintTask&id=${erpPrintTask.id}&printTaskNo=${erpPrintTask.printTaskNo}" 
									target="navTab" rel="add"><span>未打印</span></a>
								</div>
								<div class="buttonActive">
									<span style="border-left:10px;">
									  <button onclick="deletePrintTask('${erpPrintTask.id}','${erpPrintTask.printTaskNo}')">删除</button>
									</span>
					          	</div>
							</c:when>
							<c:when test="${erpPrintTask.printState=='2'}">
								<div class="buttonActive">
						            <div class="buttonContent">
						              <button onclick="state2('${erpPrintTask.id}')" class="add">已发送</button>
						            </div>
					          	</div>
							</c:when>
							<c:when test="${erpPrintTask.printState=='3'}">
								<div class="buttonActive">
						            <div class="buttonContent">
						              <button onclick="state3('${erpPrintTask.id}')">待反馈</button>
						            </div>
					          	</div>
							</c:when>
							<c:when test="${erpPrintTask.printState=='4'}">
								<div class="buttonActive">
						            <div class="buttonContent">
						              <button onclick="state4('${erpPrintTask.id}')">待签收</button>
						            </div>
					          	</div>
							</c:when>
							<c:when test="${erpPrintTask.printState=='5'}">
								<div class="buttonActive">
						            <div class="buttonContent">
						             	<button disabled="disabled">已完成</button>
						            </div>
					          	</div>
							</c:when>
						</c:choose>
						<c:if test="${not empty erpPrintTask.downLoadPath}">
								<div class="buttonActive">
									<a class="add" href="${erpPrintTask.downLoadPath}" target="_blank" ><span>下载</span></a>
					          	</div>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div> 