<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

%>
<script type="text/javascript" language="javascript">


	function yestoprint(){
		//var id=$("input[name='id']").val();
		$("input[name='toPrint']").attr("disabled",true);
		var id=$("#taskId").val();
		var printTaskNo=$("input[name='printTaskNo']").val();
		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "${path}/reportdetail/erpPrintTask!confirmPrintTaskReloadWeb.action",
			data: {"id":id,"printTaskNo":printTaskNo},
			success: function(data){
				if(data.result=="success"){
					alert("更改成功！");
					navTab.closeCurrentTab();
					//$.pdialog.closeCurrent();
					return navTabSearch(this);
					//return navTabSearch(this);
				}else{
					alert("更改失败！");
					$("input[name='toPrint']").attr("disabled",false);
				}	
			},
			error :function(data){
				alert("服务发生异常，请稍后再试！");
			}
		});
	}
	
	//增加打印任务中的批次
function showUnPrintBatchDialog(){
	$.pdialog.open("../reportdetail/erpPrintBatch!listUnPrintBatch.action?id=${erpPrintTask.id}&printTaskNo=${erpPrintTask.printTaskNo}", "listUnPrintBatch", "增加打印批次",
			{width:800,height:600,max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
}

var printTaskId = '${erpPrintTask.id}';

var printTaskNo = '${erpPrintTask.printTaskNo}';

//删除任务中的批次
function deletePrintBatch(){
	
	var printBatchIds = [];

	$('input:checkbox[name="ids"]:checked').each(function(n){
		   printBatchIds.push($(this).val());
	});

	var count = printBatchIds.length;
	if(count == 0) {
		alertMsg.info('<span style="color:#FF0000">请选择要删除的打印任务！</span>');
		return ;
	}

	//拼接数组为字符串
	var paramStr = printBatchIds.join();
	
	alertMsg.confirm('<span style="color:#FF0000;">确认删除【'+count+'】条打印任务?</span>', {
        okCall: function(){
        	$.ajax({
				url: '${path}/reportdetail/erpPrintBatch!removePrintBatchFromPrintTask.action',
				method: 'post',
				cache: false,
				dataType: 'json',
				data: {"ids":paramStr,'printTaskId':printTaskId},
				success: function(data){
					if(data.result=='success'){
						alertMsg.correct("删除成功！");
					}else{
						alertMsg.error("删除失败！");
					}
					return navTabSearch(this);
				},
				error: function(data){
					//var resp = eval("("+data+")");
					alertMsg.error("删除失败！");
					return navTabSearch(this);
				}
			}); 
        }
	
	});

}
</script>



<div class="pageHeader" style="background:white">
	
	<div class="searchBar">
			<table class="pageFormContent">
			<tr>
				<td><label>任务编号：</label> 
					<input type="text" name="printTaskNo" value="${erpPrintTask.printTaskNo}" readonly="readonly"/>
					<input type="hidden" id="taskId" name="id" value="${erpPrintTask.id}"/>
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
			<tr>
            	<td>
            		<label>报告总数：</label> 
					<input type="text" name="batchNum" value="${erpPrintTask.reportCount}" readonly="readonly"/>
                </td>
                <td>
                	<input name="toPrint" type="button" onclick="yestoprint()" value="确认任务" style="float:right;"/>
                </td>
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
				<!--<th  export = "true" columnEnName = "events" columnChName = "场次" >场次</th>-->
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

					<!--<td align="center">	${erpPrintBatch.events}</td>-->
					<td align="center">	${erpPrintBatch.combo}</td>
					<td align="center">	${erpPrintBatch.count}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>






