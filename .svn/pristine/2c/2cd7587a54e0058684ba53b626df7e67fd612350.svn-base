<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">
	
	function showPrintComboPrice(){//套餐变更
		var checkbox = $(":checkbox[name='printSettlementCheckbox']:checked");
		if($(checkbox).length!=1){
			alertMsg.error("请选择一条要更改的信息");
			return;
		}
		var printTaskId = $("#printTaskId").val();
		var combo = encodeURI($(checkbox).parents("tr").children().eq(5).text());
		var price = $(checkbox).parents("tr").children().eq(6).text();
		var payedPrice = $(checkbox).parents("tr").children().eq(7).text();
		$.pdialog.open("${path}/settlementManagement/erpPrintCompanySettleTask!showPrintComboPrice.action?id="+$(checkbox).val()+"&price="+price+"&combo="+combo+"&payedPrice="+payedPrice+"&printTaskId="+printTaskId, 
				"showPrintComboPrice", "修改套餐打印价格", {width:600,height:400,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	}
	
	function editPayedPrice1(obj){//更改不结算
		var id = $(obj).parents("tr").children().find(":checkbox").val();
		var payedPrice = $(obj).parents("tr").children().eq(8).text();
		var totalPrice = $("#totalPrice").val();
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			data:{"id":id},
			url: "${path}/settlementManagement/erpPrintCompanySettleTask!editPayedPrice1.action",
			success: function(data){
 				$("#totalPrice").val(parseFloat(totalPrice)-parseFloat(payedPrice));
 				$(obj).parents("tr").children().eq(8).text(0);
 				alertMsg.correct("修改成功");
 			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	
	function exportPrintSettlementDetail(){
		var printTaskNo = $("#printTaskNo",navTab.getCurrentPanel()).val();
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			data:{"printTaskNo":printTaskNo},
			url: "${path}/settlementManagement/erpPrintCompanySettleTask!exportPrintSettlementDetail.action",
			success: function(data){
				window.open(eval("("+data+")").path,"_blank");
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
</script>

<div class="pageHeader">
	<form id="printSettlementDetail"  rel="pagerForm" onsubmit="if(this.action != '${path}/settlementManagement/erpPrintCompanySettleTask!listPrintSettlementDetail.action'){this.action = '${path}/settlementManagement/erpPrintCompanySettleTask!listPrintSettlementDetail.action' ;} ;return navTabSearch(this);" action="${path}/settlementManagement/erpPrintCompanySettleTask!listPrintSettlementDetail.action" method="post" id="pagerFindForm">
		<div class="searchBar">
			<table>
				<tr hidden="hidden">
					<td>
						<label>任务Id：</label> 
						<input id="printTaskId" type="text" name="printTaskId" value="${printTaskId}" readonly=" readonly"/>
					</td> 
				</tr>
				
				<tr>
					<td style="padding-left:12px;">
						<label for="printTaskNo">任务编号：</label>
						<input id="printTaskNo" type="text" name="filter_and_printTaskNo_LIKE_S" value="${printTaskNo}" readonly=" readonly"/>
					</td>  
					<td>
						<label>项目负责人：</label> 
						<input type="text" name="proUser" value="${proUser}" disabled="disabled"/>
					</td>
					<td>
						<label>项目编号：</label> 
						<input type="text" name="proCode" value="${proCode}" disabled="disabled"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<label>远盟对接人：</label> 
						<input type="text" name="insurerName" value="${insurerName}" disabled="disabled"/>
					</td>
					<td>
						<label>打印公司：</label> 
						<input id="printCompany" type="text" name="printCompany" value="${printCompany}" disabled="disabled"/>
					</td>
					<td>
						<label>报告完成时间：</label> 
						<input id="reportEndTime" type="text" name="reportEndTime" value="${fn:substring(reportEndTime,0,19)}" disabled="disabled"/>
					</td>
					<td>
						<label>报告打印合计金额：</label> 
						<input id="totalPrice" type="text" name="totalPrice" value="${totalPrice}" disabled="disabled"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">
		<div class="panelBar">
		<ul class="toolBar">
			<c:if test="${settltmentState !=2}">
				<li id="liPrintSettlementDetail" style="margin-left:5px;"><a class="icon" onclick="showPrintComboPrice()"><span>套餐价格变更</span></a></li>
			</c:if>
			<li id="exportPrintSettlementDetail" style="margin-left:5px;"><a class="edit" onclick="exportPrintSettlementDetail()"><span>导出EXCEL</span></a></li>	
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="printSettlementDetail2"> 
		<thead>
			<tr>
				<th width="50"  nowrap="true">序号</th>
				<th width="150"  nowrap="true" export= "true" columnEnName = "code" columnChName = "条码" >条码</th>
				<th width="100"  nowrap="true" export= "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th width="100"  nowrap="true" export= "true" columnEnName = "gender" columnChName = "性别" >性别</th>
				<th width="100"  nowrap="true" export= "true" columnEnName = "age" columnChName = "年龄" >年龄</th>
				<th width="100"  nowrap="true" export= "true" columnEnName = "combo" columnChName = "套餐" >套餐</th>
				<th width="100"  nowrap="true" export= "true" columnEnName = "comboPrintPrice" columnChName = "套餐价格" >套餐价格</th>
				<th width="100"  nowrap="true" export= "true" columnEnName = "payedPrice" columnChName = "实际价格" >实际价格</th>
				<th width="100"  nowrap="true" export= "true" columnEnName = "branchCompany" columnChName = "支公司" >支公司</th>
				<th width="100"  nowrap="true" export= "true" columnEnName = "owedComapny" columnChName = "所属公司" >所属公司</th>
				<c:if test="${settltmentState !=2}">
					<th width="100"  export= "false" columnEnName = "operation" columnChName = "操作" >操作</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr>
					<td width="50" nowrap="true">
						<input type="checkbox" name="printSettlementCheckbox" value="${entity.id}"/>${status.count}
					</td>
					<td width="150" nowrap="true" align="center">${entity.code}</td>
					<td width="150" nowrap="true" align="center">${entity.name}</td>
					<td width="150" nowrap="true" align="center">${entity.gender}</td>
					<td width="150" nowrap="true" align="center">${entity.age}</td>
					<td width="150" nowrap="true" align="center">${entity.combo}</td>
					<td width="150" nowrap="true" align="center">${entity.comboPrintPrice}</td>
					<td width="150" nowrap="true" align="center">${entity.payedPrice}</td>
					<td width="150" nowrap="true" align="center">${entity.branchCompany}</td>
					<td width="150" nowrap="true" align="center">${entity.owedCompany}</td>
					<c:if test="${settltmentState !=2}">
						<td width="150" nowrap="true" align="center"><button name="bt_noSettlement" type="button" onclick="editPayedPrice1(this)">不结算</button></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 



