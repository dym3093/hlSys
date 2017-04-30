<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">

$(function(){
	if($("table[class='list'] tbody tr").length>0){
		$("table[class='list'] tbody tr").each(function(){
			var actualTotalAmount = $(this).find("td").eq(10).html();
			var totalAmount = $(this).find("td").eq(9).html();
//			console.log("actualTotalAmount: "+actualTotalAmount+" , totalAmount:"+totalAmount);
			var actualTotalNum = new Number(0);
			var totalNum = new Number(0);
			if(actualTotalAmount!=null&&actualTotalAmount.length>0){
				actualTotalNum = new Number(actualTotalAmount);	
			}
			if(totalAmount!=null&&totalAmount.length>0){
				totalNum = new Number(totalAmount);	
			}
			var balance = actualTotalNum-totalNum;
//			console.log("balance: "+balance);
			if(balance>0){
				$(this).find("td").eq(11).children("span").css("color","#ff0000").text(balance);
			}
			if(balance==0){
				$(this).find("td").eq(11).children("span").css("color","#0000ff").text(balance);
			}
			if(balance<0){
				$(this).find("td").eq(11).children("span").css("color","#00ff00").text(balance);
			}
		});
	}
});

/*
 * 收款功能;
 * create by henry.xu 20180825;
 */
function proceeds() {
	//获取当前的navTabId;
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	
	//获取选中的数据;当前只支持一条数据收款;
	var idArr = [];  //接收选中数据;
	var status = ""; //获取选中数据的最后td的text值;
	$("input[name='ids']:checked").each(function(i){
		idArr.push($(this).val());
		status = $(this).parent("td").siblings("td:last").text().trim();
	});

	if(idArr.length!=1){
		alertMsg.info("请选择一条结算任务！");
		return;
	}
	
	if(status=="已收款"||status=="变更收款"){
		alertMsg.info("<span style='color:#FF0000;'>【"+status+"】</span>状态的结算任务不能收款！");
		return;
	}
	var selVal = idArr[0];
	//跳转到新的navtab
	navTab.openTab("proceeds_01", 
			"${path}/settlementManagement/erpSettlementTaskBX!proceeds.action", 
			{ title:"收款", fresh:false, data:{"navTabId" : navTabId, "settlementTaskId" : selVal} });
	
}

function viewSettlementBX(settleId){

	//获取当前页面id
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	$.pdialog.open("${path}/settlementManagement/erpSettlementTaskBX!viewSettlementBX.action?settleId="+settleId, "viewSettlementBX", "查看结算任务明细", 
			{width:600,height:400,max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	
}

//新增结算
function addSettlement(){

//	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');

//    var op = { title:"新增结算任务", fresh:false, data:{"navTabId":navTabId, "type":"toAddPage"} };
    var op = { title:"新增结算任务", fresh:true, data:{"type":"toAddPage"} };
	navTab.openTab("addSettlement", "${path}/settlementManagement/erpSettlementTaskBX!toAddPage.action", op);

}
//修改结算
function editSettlement(){

	var idArr = [];
	var status = "";
	$("input[name='ids']:checked", navTab.getCurrentPanel()).each(function(i){
		idArr.push($(this).val());
		status = $(this).parent("td").siblings("td:last").text().trim();
	});

	if(idArr.length!=1){
		alertMsg.info("请选择一条结算任务！");
		return;
	}
	
	if(status=="已收款"||status=="变更收款"){
		alertMsg.info("<span style='color:#FF0000;'>【"+status+"】</span>状态的结算任务不能修改！");
		return;
	}
	
	var id = idArr[0];
	
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	
	navTab.openTab("editSettlement", "${path}/settlementManagement/erpSettlementTaskBX!toAddPage.action", 
			{ title:"编辑结算任务", fresh:false, data:{"id":id, "type":"toEditPage"} });
}

//批量删除
function delSettlementBatch(){

	var idArr = [];
	var status = "";
	$("input[name='ids']:checked", navTab.getCurrentPanel()).each(function(i){
		status = $(this).parent("td").siblings("td:last").text().trim();
		if(status=="已收款"||status=="变更收款"){
			alertMsg.info("<span style='color:#FF0000;'>【"+status+"】</span>状态的结算任务不能删除！");
			return;
		}else{
			idArr.push($(this).val());
		}
	});
	
	if(idArr.length<1){
		alertMsg.info("请选择要删除的结算任务！");
		return;
	}
	
	var ids = idArr.join();
	alertMsg.confirm('<span style="color:#FF0000;">确认删除【'+idArr.length+'】条结算任务?</span><br/>'+
					'<span style="color:#FF0000;">注：任务删除后不可恢复！</span>', {
			okCall: function(){
				$.ajax({
					url: '${path}/settlementManagement/erpSettlementTaskBX!delSettlementBatch.action',
					method: 'post',
					cache: false,
					data: {'ids':ids},
					success: function(data){
						if(data.result='success'){
							alertMsg.correct('删除成功！');
							return navTabSearch(this);
						}else{
							alertMsg.error('删除失败！');
						}
					},
					error: function(data){
						alertMsg.error('删除失败！');
					}
				}); 
			}		
		});
}

//批量删除(管理员操作)
function delSettlementBatchByMgr(){

	var idArr = [];
	var status = "";
	var userRoles = "${currentUser.roleNames}";
	
	$("input[name='ids']:checked", navTab.getCurrentPanel()).each(function(i){
		status = $(this).parent("td").siblings("td:last").text().trim();
		idArr.push($(this).val());
	});
	
	if(idArr.length<1){
		alertMsg.info("请选择要删除的结算任务！");
		return;
	}
	
	var ids = idArr.join();
	alertMsg.confirm('<span style="color:#FF0000;">确认删除【'+idArr.length+'】条结算任务?</span><br/>'+
					'<span style="color:#FF0000;">注：任务删除后不可恢复！</span>', {
			okCall: function(){
				$.ajax({
					url: '${path}/settlementManagement/erpSettlementTaskBX!delSettlementBatch.action',
					method: 'post',
					cache: false,
					data: {'ids':ids},
					success: function(data){
						if(data.result='success'){
							alertMsg.correct('删除成功！');
							return navTabSearch(this);
						}else{
							alertMsg.error('删除失败！');
						}
					},
					error: function(data){
						alertMsg.error('删除失败！');
					}
				}); 
			}		
		});
}


//提交收款&确认收款
function confirmSettlementTask(code){
	var ids = [];
	var status = "";
	$("input[name='ids']:checked", navTab.getCurrentPanel()).each(function(i){
		ids.push($(this).val());
		status = $(this).parent("td").siblings("td:last").text().trim();
	});
	
	if(ids.length<1){
		alertMsg.info("请选择一条记录记录！");
		return;
	}
	
	var opera = "";
	var reqStatus = "";
	var tip = "";
	if(code=="1"){
		opera = "提交收款";
		reqStatus = "未收款";
		tip = '<span style="color:#FF0000;">确定'+opera+'?</span><br/>'+
		'<span style="color:#FF0000;">注：'+opera+'后不可再对该条记录进行【删除】操作！</span>';
	}
	if(code=="2"){
		opera = "确认收款";
		reqStatus = "待收款";
		tip = '<span style="color:#FF0000;">确定【'+opera+'】?</span><br/>'+
		'<span style="color:#FF0000;">注：'+opera+'后不可再对该条记录进行【编辑】,【删除】操作！</span>';
	}
	
	if(status=="未收款"||status=="待收款"){
		var idArr = ids.join();
		
		alertMsg.confirm(tip, {
				okCall: function(){
					$.ajax({
						url: '${path}/settlementManagement/erpSettlementTaskBX!confirmSettlementTask.action',
						method: 'post',
						cache: false,
						data: {'ids':idArr,'status':code},
						success: function(resp){
							var data = eval("("+resp+")");
							if(data.result=='success'){
								alertMsg.correct('操作成功！');
								return navTabSearch(this);
							}else{
								alertMsg.error('操作失败！');
								return navTabSearch(this);
							}
						},
						error: function(resp){
							alertMsg.error('网络异常，请稍后再试...');
						}
					}); 
				}		
			});
	}else{
		alertMsg.info("<span style='color:#FF0000;'>【"+reqStatus+"】</span>状态的结算任务才能进行【"+opera+"】操作！");
	}
	
}

//变更收款
function changeSettlementAmount(code){

	var idArr = [];
	var status = "";
	$("input[name='ids']:checked", navTab.getCurrentPanel()).each(function(i){
		idArr.push($(this).val());
		status = $(this).parent("td").siblings("td:last").text().trim();
	});
	if(status!="已收款"&&status!="变更收款"){
		alertMsg.info("<span style='color:#FF0000;'>【已收款】</span>状态的结算任务才能变更收款！");
		return;
	}
	if(idArr.length!=1){
		alertMsg.info("请选择一条结算任务！");
		return;
	}
	
	var id = idArr[0];

	navTab.openTab("editSettlement", "${path}/settlementManagement/erpSettlementTaskBX!toChangeAmount.action", 
			{ title:"结算任务变更收款", fresh:false, data:{"id":id, "type":"toChangeAmount", "status":code} });
}

function toUploadFilePage(settleId, taskNo){

	//获取当前页面id
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	$.pdialog.open("${path}/settlementManagement/erpSettlementTaskBX!toUploafFilePage.action?id="+settleId+"&navTabId="+navTabId, "toUploadFilePage", 
			"任务【<span style='color:#ff0000;'>"+taskNo+"</span>】  添加附件", 
			{width:800,height:400,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	
}

var updateEventsStatusBX = function(){
	alertMsg.confirm("确认更新场次状态？", {
		okCall: function(){
			$.ajax({
				url: '${path}/settlementManagement/erpSettlementTaskBX!updateEventsStatusBX.action',
				method: 'post',
				cache: false,
				success: function(resp){
					var data = eval("("+resp+")");
					console.log(data);
					if(data.result==true){
						alertMsg.correct(data.msg);
					}else{
						alertMsg.error(data.msg);
					}
				},
				error: function(resp){
					alertMsg.error('网络异常，请稍后再试...');
				}
			}); 
		}		
	});
};

</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/settlementManagement/erpSettlementTaskBX!listSettlementByProperties.action')
	{this.action = '${path}/settlementManagement/erpSettlementTaskBX!listSettlementByProperties.action' ;} ;return navTabSearch(this);" 
	action="${path}/settlementManagement/erpSettlementTaskBX!listSettlementByProperties.action" method="post"  rel="pagerForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td><label>任&nbsp;&nbsp;务&nbsp;&nbsp;号：</label></td>
				<td><input type="text" name="filter_and_taskNo_LIKE_S" value="${filter_and_taskNo_LIKE_S}"/></td>
				<td><label>项目编号：</label></td>
				<td><input type="text" name="filter_and_projectNo_LIKE_S" value="${filter_and_projectNo_LIKE_S}"/></td>
				<td><label>项目对接人：</label></td>
				<td><input type="text" name="filter_and_salesManYM_LIKE_S" value="${filter_and_salesManYM_LIKE_S}"/></td>
			</tr>
			<tr>
			    <td><label>状态：</label></td>
				<td> 
					<select id="status" name="filter_and_status_IN_S" class="required" style="height:20px;margin:5px;width:200px;">
						<option value="" 	<c:if test="${empty filter_and_status_IN_S}">selected="selected"</c:if> >-- 全部  --</option>
						<c:if test="${currentUser.roleNames=='远盟销售' || currentUser.roleNames=='系统管理员'}">
							<option value="'0'" <c:if test="${filter_and_status_IN_S == '【0】'}">selected="selected"</c:if> >未收款</option>
						</c:if>
						<option value="'1'" <c:if test="${filter_and_status_IN_S == '【1】'}">selected="selected"</c:if> >待收款</option>
						<option value="'2'" <c:if test="${filter_and_status_IN_S == '【2】'}">selected="selected"</c:if> >已收款</option>
		 			</select>
				</td>
				<td><label>创建日期：</label></td>
				<td>
					<input type="text" name="filter_and_createTime_GEST_T" value="${filter_and_createTime_GEST_T}" class="date" readonly="readonly" style="background-color:#eee;"/> 
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><label>至：</label></td>
				<td>
					<input type="text" name="filter_and_createTime_LEET_T" value="${filter_and_createTime_LEET_T}" class="date" readonly="readonly" style="background-color:#eee;"/> 
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
         		<td class="block_btn"><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
	<div>
		<fieldset>
			<legend>【<span id="status_text" style="color:#ff0000;">${status_text}</span>】的结算任务金额合计:</legend>
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
		<c:if test="${currentUser.accountName!='parkson'}">
		<ul class="toolBar">
			<c:if test="${currentUser.roleNames=='远盟销售' || currentUser.roleNames=='系统管理员'}">
				<li><a class="add" href="#" onclick="addSettlement()"><span>新增任务</span></a></li>
				<li><a class="edit" href="#" onclick="editSettlement()"><span>修改任务</span></a></li>
				<li><a class="delete" href="#" onclick="delSettlementBatch()" ><span>删除任务</span></a></li>
				<li>
				<a class="icon" href="javascript:void(0)" onclick="confirmSettlementTask('1')"><span>提交收款</span></a>
            	</li>
			</c:if>
            <c:if test="${currentUser.roleNames == '会计' || currentUser.roleNames=='系统管理员'}">
            <li>
           		<a class="icon" href="javascript:void(0);" onclick="proceeds();"><span>收款</span></a>
           	</li>
           	</c:if>
            <li>
           		<span style="color:#ff0000; font-size: 10px;">注：【已收款】状态的结算任务不能执行删除！</span>
           	</li> 
		</ul>		
		<jsp:include page="/common/pagination.jsp" />
		</c:if>
		
	</div>	
	
	<div>
		<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
				<thead>
				<tr>
					<th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>
					
					<th  export = "true" columnEnName = "taskNo"  columnChName = "任务号" >任务号</th>
					<th  export=  "true" columnEnName = "taskName" columnChName = "任务名" >任务名</th>
					<th  export=  "true" columnEnName = "projectNo" columnChName = "项目编号" >项目编号</th>
					<th  export=  "true" columnEnName = salesManYM columnChName = "项目对接人" >项目对接人</th>					
					<th  export = "true" columnEnName = "createTime" columnChName = "创建日期" >创建日期</th>
					<th  export = "true" columnEnName = "createUser" columnChName = "创建人" >创建人</th>
					<th  export = "true" columnEnName = "setMealNum" columnChName = "套餐数量" >套餐数量</th>
					<th  export = "true" columnEnName = "totalPersonNum" columnChName = "总人数" >总人数</th>
					<th  export = "true" columnEnName = "totalAmount" columnChName = "应结算金额(元)" >应结算金额(元)</th>
					<th  export = "true" columnEnName = "actualTotalAmount" columnChName = "实际结算金额(元)" >实际结算金额(元)</th>
					<th  export = "true" columnEnName = "actualTotalAmount" columnChName = "结余(元)" >结余(元)</th>
					<th  export = "true" columnEnName = "status" columnChName = "状态" >状态</th>
				</tr>
				
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="entity" varStatus="status">
					<tr target="sid_user" rel="${entity.id }">
						<td align="center">
							<c:if test="${currentUser.accountName!='parkson'}">
								<input type="checkbox" name="ids" value="${entity.id}">
							</c:if>
							${status.count}
						</td> 
						<td align="center">
							<a style="color:#6495ED" href="#" onclick="viewSettlementBX('${entity.id}')">${entity.taskNo}</a>
						</td>
						<td align="center">${entity.taskName}</td>
						<td align="center">${entity.projectNo}</td>
						<td align="center">${entity.salesManYM}</td>
						<td align="center">${fn:substring(entity.createTime,0,10)}</td>
						<td align="center">${entity.createUser}</td>
						<td align="center">${entity.setMealNum}</td>
						<td align="center">${entity.totalPersonNum}</td>
						<td align="center">${entity.totalAmount}</td>
						<td align="center">${entity.actualTotalAmount}</td>
						<td align="center" ><span></span></td>
						<td align="center">
							<c:choose>
								<c:when test="${entity.status=='0'}"> 未收款 </c:when>
								<c:when test="${entity.status=='1'}"> 待收款 </c:when>
								<c:when test="${entity.status=='2'}"> 已收款 </c:when>
								<c:when test="${entity.status=='3'}"> 变更收款 </c:when>
								<c:otherwise>  <span style="color:#ff0000">状态异常</span> </c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div> 



