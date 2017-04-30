<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">

//加载时默认选中单选条件和输入框显示
$(document).ready(function(){
	var hiddenText = $("input[name='geCompanyId']").val();
	if(hiddenText=='402881b25373b2cd0153a223c8f00000'){
		$("#geCompanyID").find("option[value='402881b25373b2cd0153a223c8f00000']").attr("selected",true);
	}else if(hiddenText=='402881b25373b2cd0153a223c8f00001'){
		$("#geCompanyID").find("option[value='402881b25373b2cd0153a223c8f00001']").attr("selected",true);
	}
});

//保存下拉选中的value
$("#geCompanyID").change(function(){
	var select = $("#geCompanyID").val();			    //获取选中的下拉的值
	$("input[name='geCompanyId']").val(select);
});

//批量删除
function delSettlementBatch(){

	var ids = [];

	$("input[name='ids']:checked").each(function(i){
		ids.push($(this).val());
	});

	if(ids.length<1){
		alertMsg.info("请选择要删除的记录！");
		return;
	}

	alertMsg.confirm('<span style="color:#FF0000;">确认删除【'+ids.length+'】条结算任务?</span>', {
			okCall: function(){
				$.ajax({
					url: '../settlementManagement/erpSettlementTaskJY!deleteBatch.action',
					method: 'post',
					cache: false,
					data: {'ids':ids},
					success: function(data){
						if(data.result=='success'){
							alertMsg.correct('删除成功！');
							return navTabSearch(this);
						}else{
							alertMsg.error('删除失败！');
						}
					},
					error: function(resp){
						alertMsg.error('删除失败！');
					}
				}); 
			}		
		});
}	

//提交结算
function submitSettle() {
	var ids = '';
	var idArray = [];
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				if(ids.length>0){
					idArray = ids.replaceAll(" ", "").split(",");
					id = idArray[0];
					status = idArray[1];
				}
				count += count + 1;
				status = $(this).parent().next().text();
			});
	if (count == 0) {
		alert('请选择你要提交的结算任务！');
		return;
	} else if (count > 1) {
		alert('只能选择一条信息提交！');
		return;
	} else if(status != '2'){
		alert('请选择符合条件的结算任务！');
		return;
	}else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyEvents",
				"../events/erpEvents!toModifyEvents.action?id=" + ids
						+ "&navTabId=" + navTabId, {
					title : "变更",
					fresh : false,
					data : {}
				});
	}
}

//确认结算
function confirmSettle() {
	var ids = '';		//id状态字符串
	var id = '';		//id
	var status = '';	//状态
	var idArray = [];
	var count = 0;
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				if(ids.length>0){
					idArray = ids.replaceAll(" ", "").split(",");
					id = idArray[0];
					status = idArray[1];
				}
				count += count + 1;
				status = $(this).parent().next().text();
			});
	if (count == 0) {
		alert('请选择你要确认的结算任务！');
		return;
	} else if (count > 1) {
		alert('只能选择一条信息确认！');
		return;
	}else if(status!='3'){
		alert('请选择符合条件的结算任务！');
		return;
	} else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyEvents",
				"../events/erpEvents!toModifyEvents.action?id=" + ids
						+ "&navTabId=" + navTabId, {
					title : "变更",
					fresh : false,
					data : {}
				});
	}
}

//异常任务
function exceptionSettleTask(){
	var ids = '';		//id状态字符串
	var id = '';		//id
	var idArray = [];
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				if(ids.length>0){
					idArray = ids.replaceAll(" ", "").split(",");
					id = idArray[0];
				}
			});
	
}

function downLoadPrintTask(id){
	$.ajax({
		type: "post",
		cache: false,
		data:{"id":id},
		url: "${path}/settlementManagement/erpSettlementTaskJY!createExSeFilePath.action",
		success: function(data){
			var obj=eval("("+data+")");
			window.open(obj.path);
		},
		error:function(){
			alert("下载失败，请检查服务器！");
			return;
		}
	});
}

</script>

<!-- <div class="tip"><span>查询</span></div> -->
<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> -->
				
				<th>序号</th>
                <!--<th  export = "true" columnEnName = "id" columnChName = "id" >id</th>-->
                <th  export = "true" columnEnName = "taskNo" columnChName = "异常任务号" >异常任务号</th>
				<th  export = "true" columnEnName = "taskNo" columnChName = "任务号" >任务号</th>
				<th  export= "true" columnEnName = "settlementTime" columnChName = "结算时间">创建时间</th>
				<th  export = "true" columnEnName = "geCompany" columnChName = "基因公司">基因公司</th>
				<th  export = "true" columnEnName = "setMealNum" columnChName = "支公司数量" >支公司数量</th>
				<th  export = "true" columnEnName = "setMealNum" columnChName = "套餐数量" >套餐数量</th>
				<th  export = "true" columnEnName = "setMealNum" columnChName = "异常数量" >异常数量</th>
				<th  export = "true" columnEnName = "setMealNum" columnChName = "未出报告" >未出报告 </th>
				<th  export = "true" columnEnName = "setMealNum" columnChName = "基础信息有误" >信息有误</th>
				<th  export = "true" columnEnName = "setMealNum" columnChName = "已结算" >已结算</th>
				<th  export = "true" columnEnName = "status" columnChName = "操作" >操作</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }" >
					<td align="center">
						<input type="checkbox" name="ids" value="${entity.id},${entity.status}">
						${status.count}
					</td> 
					<td align="center">${entity.exceptionTaskNo}</td>
					<td align="center">${entity.taskNo}</td>
					<td align="center">	${entity.createTime}</td>
					<td align="center">	${entity.geCompanyId}</td>
					<td align="center">${entity.branchCompanyNum}</td>
					<td align="center">${entity.setMealNum}</td>
					<c:choose>
       					<c:when test="${entity.abnormalNum=='0'}">
       						<td align="center">${entity.abnormalNum}</td>
       					</c:when>
       					<c:otherwise>
       						<td align="center"><a title="异常总数信息" target="navTab" style="color:#72ACE3;"
							href="${path}/settlementManagement/erpSettlementTaskJY!abnormalInfo.action?id=${entity.id}&flag=abnormal" 
							rel="abnormalInfo">${entity.abnormalNum}</a></td>
       					</c:otherwise>
       				</c:choose>
       				
					<c:choose>
       					<c:when test="${entity.noHavePdfNum=='0'}">
       						<td align="center">${entity.noHavePdfNum}</td>
       					</c:when>
       					<c:otherwise>
       						<td align="center"><a title="未出报告信息" target="navTab" style="color:#72ACE3;"
							href="${path}/settlementManagement/erpSettlementTaskJY!noHavePdfInfo.action?id=${entity.id}&flag=noHavePdf" 
							rel="noHavePdfInfo">${entity.noHavePdfNum}</a></td>
       					</c:otherwise>
       				</c:choose>
       				
       				<c:choose>
       					<c:when test="${entity.errInfoNum=='0'}">
       						<td align="center">${entity.errInfoNum}</td>
       					</c:when>
       					<c:otherwise>
       						<td align="center"><a title="信息有误信息" target="navTab" style="color:#72ACE3;"
							href="${path}/settlementManagement/erpSettlementTaskJY!errInfo.action?id=${entity.id}&flag=errInfo" 
							rel="errInfoInfo">${entity.errInfoNum}</a></td>
       					</c:otherwise>
       				</c:choose>
       				
       				<c:choose>
       					<c:when test="${entity.haveSettlementNum=='0'}">
       						<td align="center">${entity.haveSettlementNum}</td>
       					</c:when>
       					<c:otherwise>
       						<td align="center"><a title="已结算信息" target="navTab" style="color:#72ACE3;"
							href="${path}/settlementManagement/erpSettlementTaskJY!haveSettlementInfo.action?id=${entity.id}&flag=haveSettlement" 
							rel="errInfoInfo">${entity.haveSettlementNum}</a></td>
       					</c:otherwise>
       				</c:choose>
					
					<td align="center">
						<div class="panelBar" style="width:220px;">
								<ul class="toolBar" >
									<li><a class="icon" 
										href="javascript:;"
										title="导出表格" onclick="downLoadPrintTask('${entity.id}')"><span></span>导出异常数据</a></li>
                        		</ul>
							</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div> 



