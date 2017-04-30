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
	var hiddenText = $("input[name='selectStatus']", navTab.getCurrentPanel()).val();
	if(hiddenText=='2'){
		$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).find("option[value='2']").attr("selected",true);
	}else if(hiddenText=='3'){
		$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).find("option[value='3']").attr("selected",true);
	}else if(hiddenText=='4'){
		$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).find("option[value='4']").attr("selected",true);
	}else if(hiddenText=='9'){
		$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).find("option[value='9']").attr("selected",true);
	}
	
	
	var hiddenTextTwo = $("input[name='selectGeCompanyId']", navTab.getCurrentPanel()).val();
	if(hiddenTextTwo=='402881b25373b2cd0153a223c8f00000'){
		$("select[name='filter_and_geCompanyId_EQ_S']", navTab.getCurrentPanel()).find("option[value='402881b25373b2cd0153a223c8f00000']").attr("selected",true);
	}else if(hiddenTextTwo=='402881b25373b2cd0153a223c8f00001'){
		$("select[name='filter_and_geCompanyId_EQ_S']", navTab.getCurrentPanel()).find("option[value='402881b25373b2cd0153a223c8f00001']").attr("selected",true);
	}
	
});

//保存下拉选中的value
$("select[name='filter_and_status_EQ_S']", navTab.getCurrentPanel()).change(function(){
	var select = $("select[name='filter_and_status_EQ_S'][option:selected]", navTab.getCurrentPanel()).val();			//获取选中的下拉的值
	$("input[name='selectStatus']", navTab.getCurrentPanel()).val(select);
});

//保存下拉选中的value
$("select[name='filter_and_geCompanyId_EQ_S']", navTab.getCurrentPanel()).change(function(){
	var select = $("select[name='filter_and_geCompanyId_EQ_S'][option:selected]", navTab.getCurrentPanel()).val();	//获取选中的下拉的值
	$("input[name='selectGeCompanyId']", navTab.getCurrentPanel()).val(select);
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
	var id = '';
	var ids = '';
	var idArray = [];
	var count = 0;
	var status = '';
	var s = '';
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				if(ids.length>0){
					idArray = ids.replaceAll(" ", "").split(",");
					id = idArray[0];
					s = idArray[1];
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
	} else if(s != '2'){
		alert('请选择符合条件的结算任务！');
		return;
	}else {
		if (confirm("确认提交结算?")) {
			/* var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("modifyEvents",
				"${path}/settlementManagement/erpSettlementTaskJY!toModifyEvents.action?id=" + id
						+ "&navTabId=" + navTabId, {
					title : "变更",
					fresh : false,
					data : {}
			}); */
			$.ajax({
				type: "post",
				cache :false,
				dataType: "json",
				url: "${path}/settlementManagement/erpSettlementTaskJY!submitSettleTask.action",
				data: {'id':id},
				success: function(data){
					if(data.result=="success"){
						alertMsg.correct("提交成功！");
						navTab.refreshCurrentTab();
						//$.pdialog.closeCurrent();
						return navTabSearch(this);
					}else{
						alertMsg.error("提交失败！");
					}	
				},
				error :function(data){
					alert("服务发生异常，请稍后再试！");
				}
			});
		};
	}
}

//确认结算
function confirmSettle() {
	var ids = '';		//id状态字符串
	var id = '';		//id
	var s = '';	//状态
	var idArray = [];
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				if(ids.length>0){
					idArray = ids.replaceAll(" ", "").split(",");
					id = idArray[0];
					s = idArray[1];
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
	}else if(s == '0' || s == '1' || s == '2'){
		alert('请选择符合条件的结算任务！');
		return;
	} else {
		if (confirm("确定确认结算?")) {
			/* var navTabId = navTab._getTabs().filter('.selected').attr('tabid');*/
			navTab.openTab("settleTaskDetail",
					"${path}/settlementManagement/erpSettlementTaskJY!settleTaskDetail.action?id=" + id
							, {
						title : "基因公司结算详细",
						fresh : false,
						data : {}
					}); 
			
			/* $.ajax({
				type: "post",
				cache :false,
				dataType: "json",
				url: "${path}/settlementManagement/erpSettlementTaskJY!confirmSettlement.action",
				data: {'id':id},
				success: function(data){
					if(data.result=="success"){
						alertMsg.correct("确认成功！");
						navTab.refreshCurrentTab();
						//$.pdialog.closeCurrent();
						return navTabSearch(this);
					}else{
						alertMsg.error("确认失败！");
					}	
				},
				error :function(data){
					alert("服务发生异常，请稍后再试！");
				}
			}); */
		}
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
	alert(id);
	navTab.openTab("exceptionSettlement",
			"${path}/settlementManagement/erpSettlementTaskJY!exceptionSettleTask.action?id=" + id
					+ "&navTabId=" + navTabId,{
				title : "异常结算任务",
				fresh : false,
			});
}

</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/settlementManagement/erpSettlementTaskJY!listSettlementByProperties.action')
	{this.action = '${path}/settlementManagement/erpSettlementTaskJY!listSettlementByProperties.action' ;} ;return navTabSearch(this);" 
	action="${path}/settlementManagement/erpSettlementTaskJY!listSettlementByProperties.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>任务号：</label> 
					<input type="text" name="filter_and_taskNo_LIKE_S" value="${filter_and_taskNo_LIKE_S}"/>
				</td>  
				<td>
					<input type="hidden" name="selectGeCompanyId" value="${selectGeCompanyId}"/>
					<label>基因公司：</label> 
					<%-- <input type="text" name="filter_and_geCompany_LIKE_S"  value="${filter_and_branchCompany_LIKE_S}"/> --%>
					<select id="geneCompany" name="filter_and_geCompanyId_EQ_S" rel="iselect">
						<option value=''>---请选择---</option>
						<option value='402881b25373b2cd0153a223c8f00000'>南方基因公司</option>
						<option value='402881b25373b2cd0153a223c8f00001'>北方基因公司</option>
		 			</select>
				</td>
				<td>
					<input type="hidden" name="selectStatus" value="${selectStatus}"/>
					<label>状态：</label> 
					<select name="filter_and_status_EQ_S" rel="iselect" id="status">
						<option value=''>---请选择---</option>
						<!-- <option value='2'>可支付</option> -->
						<option value='3'>待支付</option>
						<option value='9'>部分支付</option>
						<option value='4'>已支付</option>
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
				<td><div style="margin-left:60px;" class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
   
		</table>
	</div>
	</form>
	<div>
		<fieldset>
			<legend>结算任务金额合计:</legend>
			<table class="num2_lab">
				<tr>
					<td><label>应结算金额合计：</label></td>
					<td class="num2_w"><input type="text" readonly="readonly" value="${totalAmountCal }" /><%--<span>${totalAmountCal }</span> --%>(元)</td>
					<td><label>实际结算金额合计：</label></td>
					<td class="num2_w"><input type="text" readonly="readonly" value="${actualTotalAmountCal }"/>(元)</td>
					<td><label>结余合计：</label></td>
					<td class="num2_w"><input type="text" readonly="readonly" style="color:${balanceCal>0?'#ff0000': balanceCal==0?'#0000ff':'#00ff00'}" value="${balanceCal }"/>(元)</td>
				</tr>
				<tr>
				<td colspan="6" style="text-align:right;"><b style="color:#ff0000;">注:【结余合计】=【实际结算金额合计】-【应结算金额合计】</b></td>
				</tr>
			</table>
		</fieldset>
	</div>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		<c:if test="${currentUser.accountName != '项路佳' }">
			<%-- <li><a class="add" href="${path}/settlementManagement/erpSettlementTaskJY!toAddPage.action"  target="navTab" rel="new_js_001"><span>新增结算</span></a></li>
			<li><a class="icon" href="${path}/settlementManagement/erpSettlementTaskJY!exceptionSettleTask.action"  target="navTab" rel="new_js_002"><span>异常任务</span></a></li>
			<li><a class="delete" onclick="submitSettle()" ><span>提交结算</span></a></li> --%>
			<li><a class="edit"   onclick="confirmSettle()"><span>确认结算</span></a></li>
		</c:if>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="230" id="exportExcelTable"> 
			<thead>
			<tr>
				<!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> -->
				
				<th>序号</th>
                <!--<th  export = "true" columnEnName = "id" columnChName = "id" >id</th>-->
				<th width="160"  export = "true" columnEnName = "taskNo" columnChName = "任务号" >任务号</th>
				<th width="160" export= "true" columnEnName = "settlementTime" columnChName = "创建时间">创建时间</th>
				<th  export = "true" columnEnName = "geCompany" columnChName = "基因公司">基因公司</th>
				<th  export = "true" columnEnName = "branchCompanyNum" columnChName = "支公司数" >支公司数</th>
				<th  export = "true" columnEnName = "setMealNum" columnChName = "套餐数量" >套餐数量</th>
				<th  export = "true" columnEnName = "totalPersonNum" columnChName = "成功导入总数" >成功导入总数</th>
				<th  export = "true" columnEnName = "successNum" columnChName = "匹配数量" >匹配数量</th>
				<th  export = "true" columnEnName = "abnormalNum" columnChName = "异常数量" >异常数量</th>
				<th  export = "true" columnEnName = "totalAmount" columnChName = "本次可结算金额(元)" >本次可结算金额(元)</th>
				<th  export = "true" columnEnName = "totalAmount" columnChName = "" >未结算金额</th>
				<th  export = "true" columnEnName = "totalAmount" columnChName = "" >累计付款金额</th>
				<th  export = "true" columnEnName = "status" columnChName = "状态" >状态</th>
				<th  export = "true" columnEnName = "status" columnChName = "" >操作人</th>
				<th  export = "true" columnEnName = "" columnChName = "操作" >操作</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }" >
					<td align="center">
						<input type="checkbox" name="ids" value="${entity.id},${entity.status}">
						${status.count}
					</td> 
					<td align="center">	
					 <a title="结算明细" target="navTab" width="1000" style="color:#72ACE3;"
						href="${path}/settlementManagement/erpSettlementTaskJY!listJYSettleDetail.action?id=${entity.id}" rel="listJYSettleDetail">${entity.taskNo}</a>
					</td>
					<td align="center">	${entity.createTime}</td>
					<td align="center">	${entity.geCompany}</td>
					<td align="center">${entity.branchCompanyNum}</td>
					<td align="center">	${entity.setMealNum}</td>
					<td align="center">	${entity.totalPersonNum}</td>
					<td align="center">	${entity.successNum}</td>
					<td align="center">	${entity.abnormalNum}</td>
					<td align="center">	${entity.totalAmount}</td>
					<td align="center">	${entity.noPayAmount}</td>
					<td align="center">	${entity.accruedPayAmount}</td>
					<td align="center">
						<c:choose>
							<c:when test="${entity.status=='0'}">
								新增
							</c:when>
							<c:when test="${entity.status=='1'}">
								进行中
							</c:when>
							<c:when test="${entity.status=='2'}">
								可支付
							</c:when>
							<c:when test="${entity.status=='3'}">
								待支付
							</c:when>
                            <c:when test="${entity.status=='4'}">
								已支付
							</c:when>
							<c:when test="${entity.status=='9'}">
								部分支付
							</c:when>
					</c:choose>
					</td>
					<td align="center">	${entity.payOperator}</td>
					<td align="center">
						<c:if test="${entity.status=='0'}">
							<div class="panelBar">
									<ul class="toolBar">
										<li><a class="edit" href="${path}/settlementManagement/erpSettlementTaskJY!importCustomer.action?id=${entity.id}" target="dialog"><span>导入客户</span></a></li>
									</ul>
							</div>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 



