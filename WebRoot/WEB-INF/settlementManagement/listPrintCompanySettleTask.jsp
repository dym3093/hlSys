<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
</style>

<script type="text/javascript" language="javascript">

$(document).ready(function(){
	
	/*
	 * 初始化的时候获取打印公司;
	 * [数据来源即库存管理中的仓库名称]
	 * create by henry.xu 20160811 
	 */
 	py_ready(2); 
	$.ajax({	//初始化页面时套餐
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/warehouse/warehouse!findAllCustomer.action",
		success: function(data){
			var selectWareId = $("input[name='filter_and_printCompany_EQ_S']",navTab.getCurrentPanel()).val();
			$("#warehouseId",navTab.getCurrentPanel()).empty();
			var wares= eval("("+data+")");
			var option="<option value=''>---请选择---</option>";
			
			for(var i=0;i<wares.length;i++){	
				if(selectWareId==wares[i].text){
					option += "<option value='"+wares[i].text+"' selected='selected'>"+wares[i].text+"</option>";
				}else{
					option += "<option value='"+wares[i].text+"'>"+wares[i].text+"</option>";
				}
			}
			//将节点插入到下拉列表中
			$("select[name='printCompany']",navTab.getCurrentPanel()).append(option);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	
	setPageValue();	//设置页面大小
})

	$("#warehouseId").change( function() {	//打印公司下拉框
		var selectVal=$("#warehouseId option:selected",navTab.getCurrentPanel()).val();
		$("#inputPrintCompany").val(selectVal);
	});

	function showSettlementDetail(obj){
		var id = $.trim($(obj).parents("tr").children().find(":checkbox",navTab.getCurrentPanel()).val());
		navTab.openTab("listPrintSettlementDetail", "${path}/settlementManagement/erpPrintCompanySettleTask!listPrintSettlementDetail.action", 
				{ title:"打印报告费用明细", fresh:true, data:{"printTaskId":id} });
	}

	//批量删除
	function delSettlementBatch(){
	
		var ids = [];
	
		$("input[name='ids']:checked",navTab.getCurrentPanel()).each(function(i){
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

	function checkAll(obj){//全选
		if($(obj).attr("checked")=="checked"){
			$(obj).parents("table").children().eq(1).find(":checkbox").attr("checked",true);
		}else{
			$(obj).parents("table").children().eq(1).find(":checkbox").attr("checked",false);
		}
	}
	
	function confirm2UnSettled(){//全选或部分选择之后的操作,待支付
		var idArr = [];
		$("input:checkbox[name='printTaskCheckbox']:checked", navTab.getCurrentPanel()).each(
			function(index, items) {
				idArr[index] = items.value;
		});
		if (idArr.length == 0) {
			alertMsg.warn('请选择要操作的数据');
			return;
		}
		py_show();
		var ids = idArr.join();
		$.ajax({
			type:"post",
			cache:false,
			data:{"ids":ids},
			url : "${path}/settlementManagement/erpPrintCompanySettleTask!confirm2UnSettled.action",
			success : function(data) {
				var result= eval("("+data+")");
				if(result.result=="success"){
					alertMsg.correct("共提交【"+result.count0+"】条可支付,未提交【"+result.count1
							+"】条待支付,【"+result.count2+"】条已支付,【"+result.count3+"】条没有套餐价格");
					py_hide();
					return navTabSearch($("#pagerFindForm",navTab.getCurrentPanel()));
				}else{
					py_hide();
					alertMsg.error("提交失败");
				}
			},
			error : function() {
				py_hide();
				alertMsg.warn("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	
	function confirm2Settled(){//全选或部分选择之后的操作,已支付
		var idArr = [];
		$("input:checkbox[name='printTaskCheckbox']:checked", navTab.getCurrentPanel()).each(
			function(index, items) {
				idArr[index] = items.value;
		});
		if (idArr.length == 0) {
			alertMsg.warn('请选择要操作的数据');
			return;
		}
		py_show();
		var ids = idArr.join();
		$.ajax({
			type:"post",
			cache:false,
			data:{"ids":ids},
			url : "${path}/settlementManagement/erpPrintCompanySettleTask!confirm2Settled.action",
			success : function(data) {
				var result= eval("("+data+")");
				if(result.result=="success"){
					alertMsg.correct("共提交【"+result.count1+"】条待支付,未提交【"+result.count0
							+"】条可支付,【"+result.count2+"】条已支付");
					py_hide();
					return navTabSearch($("#pagerFindForm",navTab.getCurrentPanel()));
				}else{
					py_hide();
					alertMsg.error("提交失败");
				}
			},
			error : function() {
				py_hide();
				alertMsg.warn("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	
	function setPageValue(){
		$("#printTaskSettlementSubmit", navTab.getCurrentPanel()).click(function(){
			$("input[name='page.pageSize']", navTab.getCurrentPanel()).val($("#pageSizeSelect", navTab.getCurrentPanel()).val());
		});
	}

</script>

<div id="mc" class="py_theMb">
	<div class="py_bakpic"><!--图片--></div>
</div>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" 
	onsubmit="if(this.action != '${path}/settlementManagement/erpPrintCompanySettleTask!listPrintCompanySettleTask.action')
	{this.action = '${path}/settlementManagement/erpPrintCompanySettleTask!listPrintCompanySettleTask.action' ;} ;return navTabSearch(this);" 
	action="${path}/settlementManagement/erpPrintCompanySettleTask!listPrintCompanySettleTask.action" method="post"  rel="pagerForm">
	<div class="searchBar">
	<input type="hidden" name="page.pageSize" value=""/>
		<table class="pageFormContent">
			<tr>
				<td>
					<label>任务号：</label> 
					<input type="text" name="filter_and_printTaskNo_LIKE_S" value="${filter_and_printTaskNo_LIKE_S}"/>
				</td>  
				<td>
					<label>打印公司：</label>
					<select id="warehouseId" name="printCompany" style="width:194px;margin-top:6px; margin-left: 5px;">
					<input id="inputPrintCompany" type="hidden" name="filter_and_printCompany_EQ_S" value="${filter_and_printCompany_EQ_S}"/>
				</td>
				<td>
					<label>报告状态：</label> 
					<select id=printState name="filter_and_printState_EQ_S">
						<option value="">---请选择---</option>
						<option value="0" ${filter_and_printState_EQ_S=="0"?"selected":""}>未打印</option>
						<option value="3" ${filter_and_printState_EQ_S=="3"?"selected":""}>已发送</option>
						<%-- <option value="2" ${filter_and_printState_EQ_S=="5"?"selected":""}>已寄送</option> --%>
					</select>
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
				<td>
					<label>结算状态：</label> 
					<%-- <input id="settlementText" type="hidden" name="filter_and_settlementState_LIKE_S" value="${filter_and_settlementState_LIKE_S}"/> --%>
					<select id="settlementSel" name="filter_and_settlementState_EQ_S">
						<option value="">---请选择---</option>
						<option value="0" ${filter_and_settlementState_EQ_S=="0"?"selected":""}>可支付</option>
						<option value="1" ${filter_and_settlementState_EQ_S=="1"?"selected":""}>待支付</option>
						<option value="2" ${filter_and_settlementState_EQ_S=="2"?"selected":""}>已支付</option>
					</select>
				</td>
				<td>
         			<div class="buttonActive"><div class="buttonContent"><button id="printTaskSettlementSubmit" type="submit">查找</button></div></div>
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
			<li><a class="add" onclick="confirm2UnSettled()"><span>提交结算</span></a></li>
			<li><a class="icon" onclick="confirm2Settled()"><span>确认结算</span></a></li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
		<thead>
			<tr>
				<th width="50"><input type="checkbox" id="checkAll" onclick="checkAll(this)"/>序号</th>
				<th width="150"  export= "true" columnEnName = "printTaskNo" columnChName = "任务号" >任务号</th>
				<th width="100"  export= "true" columnEnName = "printState" columnChName = "报告状态" >报告状态</th>
				<th width="100"  export= "true" columnEnName = "createTime" columnChName = "任务创建时间" >任务创建时间</th>
				<th width="150"  export= "true" columnEnName = "printCompany" columnChName = "打印公司" >打印公司</th>
				<th width="100"  export= "true" columnEnName = "expressPrice" columnChName = "报告快递费" >报告快递费</th>
				<th width="100"  export= "true" columnEnName = "totalPrice" columnChName = "报告打印合计金额" >报告打印合计金额</th>
				<th width="100"  export= "true" columnEnName = "settlementState" columnChName = "结算状态" >结算状态</th>
			</tr>
		</thead>
		<tbody id="printTaskTbody">
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr>
					<td width="50" nowrap="true" align="center">
						<input type="checkbox" name="printTaskCheckbox" value="${entity.ID}"/>${status.count}
					</td>
					<td width="150" nowrap="true" align="center" title="${entity.printTaskNo}">
						<a style="cursor: pointer; color: blue;" onclick="showSettlementDetail(this)">${entity.printTaskNo}</a>
					</td>
					<td width="100" nowrap="true" align="center">
						<c:if test="${entity.printState==0}">未打印</c:if>
						<c:if test="${entity.printState==1}">已下载</c:if>
						<c:if test="${entity.printState==3}">已发送</c:if>
						<%-- <c:if test="${entity.printState==5}">已完成</c:if> --%>
					</td>
					<td width="100" nowrap="true" align="center">${fn:substring(entity.createtime,0,19)}</td>
					<td width="150" nowrap="true" align="center">${entity.printCompany}</td>
					<td width="100" nowrap="true" align="center">${entity.expressPrice}</td>
					<td width="100" nowrap="true" align="center">${entity.payedPrice}</td>
					<td width="100" nowrap="true" align="center">
						<c:if test="${entity.settlementState==0}">可支付</c:if>
						<c:if test="${entity.settlementState==1}">待支付</c:if>
						<c:if test="${entity.settlementState==2}">已支付</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 
