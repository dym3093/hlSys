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
	var hiddenText = $("input[name='geCompanyId']", navTab.getCurrentPanel()).val();
	if(hiddenText=='402881b25373b2cd0153a223c8f00000'){
		$("#geCompanyID").find("option[value='402881b25373b2cd0153a223c8f00000']").attr("selected",true);
	}else if(hiddenText=='402881b25373b2cd0153a223c8f00001'){
		$("#geCompanyID").find("option[value='402881b25373b2cd0153a223c8f00001']").attr("selected",true);
	}
});

//保存下拉选中的value
$("#geCompanyID").change(function(){
	var select = $("#geCompanyID").val();			    //获取选中的下拉的值
	$("input[name='geCompanyId']", navTab.getCurrentPanel()).val(select);
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
			window.location.href=obj.path;
		},
		error:function(){
			alert("下载失败，请检查服务器！");
			return;
		}
	});
}

</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/settlementManagement/erpSettlementTaskJY!exceptionSettleTask.action')
	{this.action = '${path}/settlementManagement/erpSettlementTaskJY!exceptionSettleTask.action' ;} ;return navTabSearch(this);" 
	action="${path}/settlementManagement/erpSettlementTaskJY!exceptionSettleTask.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>任务号：</label> 
					<input type="text" name="filter_and_taskNo_LIKE_S" value="${filter_and_taskNo_LIKE_S}"/>
				</td>  
				<td>
					<label>条形码：</label> 
					<input type="text" name="filter_and_code_EQ_S" value="${filter_and_code_EQ_S}"/>
				</td>
				<td>
					<label>基因公司：</label> 
					<input type="hidden" name="geCompanyId" value="${geCompanyId}"/>
					<%-- <input type="text" name="filter_and_geCompany_LIKE_S"  value="${filter_and_branchCompany_LIKE_S}"/> --%>
					<select style="margin-top:5px;width:193px;" id="geCompanyID" name="filter_and_geCompanyId_EQ_S">
						<option value=''>---请选择---</option>
						<option value='402881b25373b2cd0153a223c8f00000'>南方基因公司</option>
						<option value='402881b25373b2cd0153a223c8f00001'>北方基因公司</option>
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
				<td style="padding-left:60px;"><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
			
		</table>
	</div>
	</form>
</div>
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
							rel="showSetInfo">${entity.abnormalNum}</a></td>
       					</c:otherwise>
       				</c:choose>
       				
					<c:choose>
       					<c:when test="${entity.noHavePdfNum=='0'}">
       						<td align="center">${entity.noHavePdfNum}</td>
       					</c:when>
       					<c:otherwise>
       						<td align="center"><a title="未出报告信息" target="navTab" style="color:#72ACE3;"
							href="${path}/settlementManagement/erpSettlementTaskJY!noHavePdfInfo.action?id=${entity.id}&flag=noHavePdf" 
							rel="showSetInfo">${entity.noHavePdfNum}</a></td>
       					</c:otherwise>
       				</c:choose>
       				
       				<c:choose>
       					<c:when test="${entity.errInfoNum=='0'}">
       						<td align="center">${entity.errInfoNum}</td>
       					</c:when>
       					<c:otherwise>
       						<td align="center"><a title="信息有误信息" target="navTab" style="color:#72ACE3;"
							href="${path}/settlementManagement/erpSettlementTaskJY!errInfo.action?id=${entity.id}&flag=errInfo" 
							rel="showSetInfo">${entity.errInfoNum}</a></td>
       					</c:otherwise>
       				</c:choose>
       				
       				<c:choose>
       					<c:when test="${entity.haveSettlementNum=='0'}">
       						<td align="center">${entity.haveSettlementNum}</td>
       					</c:when>
       					<c:otherwise>
       						<td align="center"><a title="已结算信息" target="navTab" style="color:#72ACE3;"
							href="${path}/settlementManagement/erpSettlementTaskJY!haveSettlementInfo.action?id=${entity.id}&flag=haveSettlement" 
							rel="showSetInfo">${entity.haveSettlementNum}</a></td>
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



