<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

%>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$("#customerRelation").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
});

//增加打印任务中的批次
function showUnPrintBatchDialog(){
	$.pdialog.open("../reportdetail/erpPrintBatch!listUnPrintBatch.action?id=${erpPrintTask.id}&printTaskNo=${erpPrintTask.printTaskNo}", "listUnPrintBatch", "增加打印批次",{width:800,height:600});
}


//保存任务信息
function savePrintTask(){
	
	var printCompany = $("input[name='printCompany']").val();
	var expectTime = $("input[name='expectTime']").val();
	var navTabId = $("#navTabId").val();


	if(printCompany.length<1){
		alertMsg.info("请填写打印公司!");
		return;
	}

	if(expectTime.length<1){
		alertMsg.info("请选择日期!");
		return;
	}
	
	$.ajax({
		url: '../reportdetail/erpPrintTask!savePrintTaskInfo.action',
		method: 'post',
		cache: false,
		dataType: 'json',
		data: {'printTaskId':printTaskId,'printCompany':printCompany,'expectTime':expectTime,'navTabId':navTabId},
		success: function(data){
			if(data.result='success'){
				alertMsg.correct("保存成功！");
				return navTabSearch(this);
			}else{
				alertMsg.error("保存失败！");
			}		
					
		},
		error: function(data){
			alertMsg.error("保存失败！");
		}
	}); 
}

//新的保存任务信息方法
function savePrintInfo(){
	var printTaskId =$("input[name='id']").val();
	var printCompany = $("input[name='printCompany']").val();
	var expectTime = $("input[name='expectTime']").val();
	var navTabId = $("#navTabId").val();


	if(printCompany.length<1){
		alertMsg.info("请填写打印公司!");
		return;
	}

	if(expectTime.length<1){
		alertMsg.info("请选择日期!");
		return;
	}
	
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/reportdetail/erpPrintTask!savePrintTaskInfo.action",
		data: {'id':printTaskId,'printCompany':printCompany,'expectTime':expectTime,'navTabId':navTabId},
		success: function(data){
			if(data.result=="success"){
				alert("更改成功！");
				navTab.closeCurrentTab();
				//$.pdialog.closeCurrent();
				return navTabSearch(this);
				//return navTabSearch(this);
			}else{
				alert("更改失败！");
			}	
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
}

function downLoadPrintTask(id){
	$.ajax({
		type: "post",
		cache: false,
		data:{"id":id},
		url: "../reportdetail/erpPrintTask!downLoad.action",
		success: function(data){
			var obj=eval("("+data+")");
			window.location.href=obj.path;
		},
		error:function(){
			alert("下载失败，请检查服务器！");
			return;
		}
	});
}

</script>



<div class="pageHeader" style="background:white">
		
	<div class="searchBar">
			<table class="pageFormContent">
			<tr>
				<input type="hidden" name="navTabId" id="navTabId"	value="${navTabId }" />
				<td><label>任务编号：</label> 
					<input type="text" name="printTaskNo" value="${erpPrintTask.printTaskNo}" class="required"/>
					<input type="hidden" name="id" value="${erpPrintTask.id}"/>
					<input type="hidden" name="printState" value="${erpPrintTask.printState}"/>
				</td>
				<td><label>任务名称：</label> 
					<input type="text" name="taskName" value="${erpPrintTask.taskName}" readonly="readonly">
				</td>
				
			</tr>
			<tr>
			<td>
					<label>批次数量：</label> 
					<input type="text" name="batchNum" value="${erpPrintTask.batchNum}" readonly="readonly"/>
				</td>
				<td align="center">
					<label>创建时间：</label> 
					<input type="text" name="createTime" value="${erpPrintTask.createTime}" readonly="readonly"/>
				</td>
         		
			</tr>

			<c:if test="${erpPrintTask.printState=='1'}">
				<tr>
					<td>
						<label>打印公司：</label> 
						<input type="text" name="printCompany" value="${erpPrintTask.printCompany}" class="required"/>
					</td>
					<td align="center">
						<label>预计时间：</label> 
						<input type="text" name="expectTime" value="${erpPrintTask.expectTime}" class="date" minDate="2000-01-15" maxDate="2099-12-30" readonly="true"/>
							<a class="inputDateButton" href="javascript:;">选择</a>					
					</td>
					
				</tr>
			</c:if>

			<tr>
				<td></td><td></td>
				<c:if test="${erpPrintTask.printState=='1'}">
					<td>
						<c:if test="${erpPrintTask.downLoadPath!=null}">
							<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="javascript:downLoadPrintTask('${erpPrintTask.id}');">打印包下载</button></div></div>
						</c:if>
					<!--<li>
	<a class="add" href="" onclick="javascript:savePrintTask();" target="navTab" rel="add"><span>保存</span></a>
</li>-->
						
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="savePrintInfo()">保存</button></div></div>
						
						<div class="buttonActive"><div class="buttonContent"><button class="close" onclick="isClose()">取消</button></div></div>
					</td>
				</c:if>
			</tr>
		</table>
		
	</div>
	
</div>

<div class="panelBar">
	<%-- <c:if test="${currentUser.accountName!='parkson'}"> --%>
	<!-- 未打印的任务才能修改批次信息 -->
	<c:if test="${erpPrintTask.printState=='0'}">
		<ul class="toolBar">
			<li><a class="add" onclick="showUnPrintBatchDialog()" style="cursor:pointer;"><span>增加批次</span></a></li>
			<li><a class="delete" onclick="deletePrintBatch()" style="cursor:pointer;"><span>删除批次</span></a></li>
		</ul>
	</c:if>
	<jsp:include page="/common/pagination.jsp" />

</div>

<div class="pageContent">
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
		<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "printBatchNo" columnChName = "批次号" >批次号</th>
				<th  export= "true" columnEnName = "province" columnChName = "省" id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
				<th  export= "true" columnEnName = "city" columnChName = "市" id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
				<th  export = "true" columnEnName = "branchCompany" id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "events" columnChName = "场次" >场次</th>
				<th  export = "true" columnEnName = "combo" columnChName = "套餐" >套餐</th>
				<th  export = "true" columnEnName = "count" columnChName = "数量" >数量</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpPrintBatch" varStatus="status">
				<tr target="sid_user" rel="${erpPrintBatch.id }">
					<td align="center">
						<c:if test="${erpPrintTask.printState=='0'}">
							<input type="checkbox" name="ids" value="${erpPrintBatch.id}">
						</c:if>
						${ status.count }
					</td>
					
						<%-- <a title="批次信息" target="navTab" width="1000"  href="${path}/events/erpCustomer!toConferenceNoListCustomer.action?showId=${erpPrintBatch.id}">${erpPrintBatch.printBatchNo}</a> --%>
					<%-- <a title="批次信息" target="navTab" width="1000"  href="${path}/report/reportFileTask!toTaskDetailByBatchno.action?id=${erpPrintBatch.id}">${erpPrintBatch.printBatchNo}</a> --%>
					<td align="center">	${erpPrintBatch.printBatchNo}</td>
					
					<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.province }"/></td>
					<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.city }"/></td>
					<td align="center"><hpin:id2nameDB id="${erpPrintBatch.branchCompany}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>

					<td align="center">	${erpPrintBatch.events}</td>
					<td align="center">	${erpPrintBatch.combo}</td>
					<td align="center">	${erpPrintBatch.count}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>


