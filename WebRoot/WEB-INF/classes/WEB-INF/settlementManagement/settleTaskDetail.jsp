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
	var payModeValue=$("#payModeValue").val();
	var payBankValue=$("#payBankValue").val();
	var payTimeValue=$("#payTimeValue").val();
	var OANo = $("#OANo").val();
	var collectionCompanyValue = $("#collectionCompanyValue").val();
	var noPayAmountTemp = $("#noPayAmountTemp").val();
	
	if(noPayAmountTemp==0){		//未有未结算金额，支付按钮不能点击
		$("#formSumbit").attr("disabled", true);
	}
	
	/* //已有信息就不能再修改
	if(payBankValue!=''){
		$("#payBankInput").css("display","block");
		$("#payBank").css("display","none");
	}else{
		$("#payBankInput").css("display","none");
	}
	
	 if(payModeValue!=''){
		$("#payModeInput").css("display","block");
		$("#payMode").css("display","none");
	}else{
		$("#payModeInput").css("display","none");
	}
	
	if(payTimeValue!=''){
		$("#payTimeInput").css("display","block");
		$("#payTime").css("display","none");
		$("#timeA").css("display","none");
	}else{
		$("#payTimeInput").css("display","none");
	}
	
	if(collectionCompanyValue!=''){
		$("#collectionCompanyInput").css("display","block");
		$("#collectionCompany").css("display","none");
	}else{
		$("#collectionCompanyInput").css("display","none");
	} 
	
	if(OANo!=''){
		$("#OANo").attr("readonly","readonly");
	} */
	if(payBankValue=='招行富力103'){
		$("#payBank").find("option[value='招行富力103']").attr("selected",true);
	}else if(payBankValue=='招行富力303'){
		$("#payBank").find("option[value='招行富力303']").attr("selected",true);
	}else if(payBankValue=='招行万达408'){
		$("#payBank").find("option[value='招行万达408']").attr("selected",true);
	}else if(payBankValue=='其他'){
		$("#payBank").find("option[value='其他']").attr("selected",true);
	}
	
	if(payModeValue=='网银'){
		$("#payMode").find("option[value='网银']").attr("selected",true);
	}else if(payModeValue=='支票'){
		$("#payMode").find("option[value='支票']").attr("selected",true);
	}else if(payModeValue=='其他'){
		$("#payMode").find("option[value='其他']").attr("selected",true);
	}
	
	if(collectionCompanyValue=='上海申友生物技术有限责任公司'){
		$("#collectionCompany").find("option[value='上海申友生物技术有限责任公司']").attr("selected",true);
	}else if(collectionCompanyValue=='南京申友生物技术有限公司'){
		$("#collectionCompany").find("option[value='南京申友生物技术有限公司']").attr("selected",true);
	}else if(collectionCompanyValue=='北京上诺佰益生物技术有限公司'){
		$("#collectionCompany").find("option[value='北京上诺佰益生物技术有限公司']").attr("selected",true);
	}else if(collectionCompanyValue=='广州市金埻网络科技有限公司'){
		$("#collectionCompany").find("option[value='广州市金埻网络科技有限公司']").attr("selected",true);
	}else if(collectionCompanyValue=='知康科技（北京）有限公司'){
		$("#collectionCompany").find("option[value='知康科技（北京）有限公司']").attr("selected",true);
	}else if(collectionCompanyValue=='其他'){
		$("#collectionCompany").find("option[value='其他']").attr("selected",true);
	}
	
});

var flag = "";	//标记处理状态
$("#currentAmount").blur(function(){
	var reg = new RegExp("^[0-9]*$"); 
	var sumAmount = 0;		//总金额
	var noPayAmount = 0;	//未付款金额
	var noPayAmountTemp = 0;	//未付款金额（临时）
	var currentAmount = 0;	//本次付款金额
	sumAmount = $("#sumAmount").val();
	noPayAmount = $("#noPayAmount").val();
	noPayAmountTemp = $("#noPayAmountTemp").val();
	currentAmount = $("#currentAmount").val();
	
	
	if(!reg.test(currentAmount)){  	//判断本次付款金额是否为数字
        $("#currentAmountSpan").text("请输入正确的数字");
        $("#noPayAmount").val(noPayAmountTemp);
		flag="noNumber";
		return;
    }else{
    	$("#currentAmountSpan").text("");
    }
	if(currentAmount==''){
		$("#currentAmountSpan").text("");
	}
	
	if(noPayAmountTemp-currentAmount<0){
		$("#currentAmountSpan").text("请输入正确的付款金额");
		$("#noPayAmount").val(noPayAmountTemp);
		flag="noTrueAmount";
		return;
	}else{
		$("#noPayAmount").val(noPayAmountTemp-currentAmount);	//设置计算后的未付款金额
	}
	
	if((noPayAmount-currentAmount)>=0){
		flag="success";
	}
});

//获取form
$.fn.serializeJson=function(){  
	var serializeObj={};  
	var array=this.serializeArray();  
	$(array).each(function(){  
		if(serializeObj[this.name]!=null&&serializeObj[this.name]!=""&&serializeObj[this.name]!=undefined){
			if($.isArray(serializeObj[this.name])){  
				serializeObj[this.name].push(this.value.trim());  
			}else{  
				serializeObj[this.name]=[serializeObj[this.name],this.value.trim()];  
			}  
		}else{  
			serializeObj[this.name]=this.value.trim();   
		}  
	});  
	return serializeObj;  
}; 

//继续付款按钮
$("#formSumbitButton").click(function(){
	var currentAmount = $("#currentAmount").val();
	var settleTaskId = $("#settleTaskId").val();
	var noPayAmountTemp = $("#noPayAmountTemp").val();
	var select = $("select[name='payMode'][option:selected]").val();
	var select2 = $("select[name='payBank'][option:selected]").val();
	var select3 = $("select[name='collectionCompany'][option:selected]").val();
	if(currentAmount==''||currentAmount==0){
		alert("请输入付款金额");
		return;
	}
	
	if(flag=='success'){
		if(select==''||select2==''||select3==''){
			alert("请完善信息！");
			return;
		}
		
		if(noPayAmountTemp==0){	//未付款金额为 0
			alert("无可支付的未付款！");
			return;
		}
		
		var data = $("#settleTaskForm").serializeJson();
		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "${path}/settlementManagement/erpSettlementTaskJY!settleTask.action",
			data: {'data': JSON.stringify(data),"settleTaskId":settleTaskId},
			success: function(data){
				if(data.status=="200"){
					alertMsg.correct(data.message);
					return navTabSearch(this);
				}else{
					alertMsg.error(data.message);
				}	
			},
			error :function(data){
				alert("服务发生异常，请稍后再试！");
			}
		});
	}else if(flag=='noNumber'){
		alert("请确认付款金额是否为数字");
	}else if(flag=='noTrueAmount'){
		alert("请输入正确的付款金额");
	}
});

function buttonDisabledTrue(){
	$("#formSumbitButton").attr("disabled",true);	//设置保存按钮不能点击
	setTimeout("buttonDisabled()",3000);	//3秒后可点击
}

//设置按钮能点击
function buttonDisabled(){
	$("#formSumbitButton").attr("disabled",false);
}

//保存按钮提交
$("#formSumbitOnce").click(function(){
	var noPayAmount = $("#noPayAmount").val();
	var noPayAmountTemp = $("#noPayAmountTemp").val();
	var settleTaskId = $("#settleTaskId").val();
	var currentAmount = $("#currentAmount").val();
	var select = $("select[id='payMode'][option:selected]").val();
	var select2 = $("select[id='payBank'][option:selected]").val();
	var select3 = $("select[id='collectionCompany'][option:selected]").val();
	
	if(currentAmount==''||currentAmount==0){
		alert("请输入付款金额");
		return;
	}
	
	if(flag=='success'){
		if(noPayAmountTemp==0){	//未付款金额为 0
			alert("无可支付的未付款！");
			return;
		}
		if((noPayAmountTemp-currentAmount)==0){			//必须一次结清
			if(select==''||select2==''||select3==''){
				alert("请完善信息！");
				return;
			}
			var data = $("#settleTaskForm").serializeJson();
			$.ajax({
				type: "post",
				cache :false,
				dataType: "json",
				url: "${path}/settlementManagement/erpSettlementTaskJY!settleTask.action",
				data: {'data': JSON.stringify(data),"settleTaskId":settleTaskId},
				success: function(data){
					if(data.status=="200"){
						alertMsg.correct(data.message);
						return navTabSearch(this);
					}else{
						alertMsg.error(data.message);
					}		
				},
				error :function(data){
					alert("服务发生异常，请稍后再试！");
				}
			});
		}else{
			alert("请支付该结算任务的所有待付金额！");
		}
	}else if(flag=='noNumber'){
		alert("请确认付款金额是否为数字");
	}else if(flag=='noTrueAmount'){
		alert("请输入正确的付款金额");
	}
});

function updatePage(){
	var id = '';		//id
	var count = 0;
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				id = n.value;
				count = count + 1;
			});
	if(count==0){
		alert("请选择需要修改的信息");
	}else if(count>1){
		alert("请选择一条进行修改");
	}else{
		var op = '{width:500px,height:600px,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true}';
		$.pdialog.open("${path}/settlementManagement/erpSettlementTaskJY!toUpdateSettlementDetail.action?id="+id,"uploadSettlementDetail", "修改支付详情", op);
	}
}

</script>

<div class="tip"><span>付款</span></div>
<div class="pageHeader" style="margin-left: 180px;">
	<form id="settleTaskForm" 
	action="${path}/settlementManagement/erpSettlementTaskJY!listSettlementByProperties.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<!-- 默认选中信息 -->
					<input type="hidden" name="payModeValue" id="payModeValue" value="${settleTaskDetailValue.payMode}"/>
					<input type="hidden" name="payBankValue" id="payBankValue" value="${settleTaskDetailValue.payBank}"/>
					<input type="hidden" name="collectionCompanyValue" id="collectionCompanyValue" value="${settleTaskDetailValue.collectionCompany}"/>
					<input type="hidden" name="payTimeValue" id="payTimeValue" value="${settleTaskDetailValue.payTime}"/>
				
					<label>基因公司：</label> 
					<input type="hidden" name="settleTaskId" id="settleTaskId" value="${settlementTaskJY.id}"/>
					<input type="text" name="geCompany"  readonly="readonly" value="${settlementTaskJY.geCompany}"/>
				</td>  
				<td><label>付款时间：</label> 
					<%-- <input type="text" name="payTimeInput" id="payTimeInput" readonly="readonly" value="${settleTaskDetailValue.payTime}"/> --%>
					<input type="text" name="payTime"  id="payTime" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"  readonly="readonly" value="${currentTime}" /><a class="inputDateButton" id="timeA" href="javascript:;" >选择</a>
				</td>
			</tr>
			<tr>
				<td>
					<label>付款方式：</label> 
					<%-- <input type="text" name="payModeInput" id="payModeInput" readonly="readonly" value="${settleTaskDetailValue.payMode}"/> --%>
					<select name="payMode"  id="payMode" style="width:198px;margin-top:5px;" >
						<option value=''>---请选择---</option>
						<option value='网银'>网银</option>
						<option value='支票'>支票</option>
						<option value='其他'>其他</option>
		 			</select>
				</td>
				<td>
					<label>总付款金额：</label> 
					<input type="text" name="sumAmount" id="sumAmount" readonly="readonly" value="${settlementTaskJY.totalAmount}"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>付款银行：</label> 
					<%-- <input type="text" name="payBankInput" id="payBankInput" readonly="readonly" value="${settleTaskDetailValue.payBank}"/> --%> 
					<select name="payBank"  id="payBank" style="width:198px;margin-top:5px;">
						<option value=''>---请选择---</option>
						<option value='招行富力103'>招行富力103</option>
						<option value='招行富力303'>招行富力303</option>
						<option value='招行万达408'>招行万达408</option>
						<option value='其他'>其他</option>
		 			</select>
				</td>
				<td><label>本次付款金额：</label> 
					<input type="text" name="currentAmount" id="currentAmount" value="" class="required"/>
					&nbsp;&nbsp;<span id="currentAmountSpan" style="color:red;margin-top:10px;"></span>
				</td>
			</tr>
			<tr>
				<td><label>OA编号：</label> 
					<input type="text" name="OANo" id="OANo" value="${settleTaskDetailValue.OANo}" class="required"/>
				</td>
				<td>
					<label>未付款：</label> 
					<input type="hidden" name="noPayAmountTemp" id="noPayAmountTemp" value="${settlementTaskJY.noPayAmount}"/>
					<input type="text" name="noPayAmount" id="noPayAmount" readonly="readonly" value="${settlementTaskJY.noPayAmount}" />
				</td>
			</tr>
			<tr>
				<td>
					<label>收款公司：</label> 
					<c:choose>
						<c:when test="${settlementTaskJY.geCompany=='南方基因公司'}">
							<select name="collectionCompany" id="collectionCompany" style="width:198px;margin-top:5px;" class="required">
								<option value=''>---请选择---</option>
								<option value='上海申友生物技术有限责任公司'>上海申友生物技术有限责任公司</option>
								<option value='南京申友生物技术有限公司'>南京申友生物技术有限公司</option>
		 					</select>
						</c:when>
						
						<c:when test="${settlementTaskJY.geCompany=='北方基因公司'}">
							<select name="collectionCompany"  id="collectionCompany" style="width:198px;margin-top:5px;" class="required">
								<option value=''>---请选择---</option>
								<option value='北京上诺佰益生物技术有限公司'>北京上诺佰益生物技术有限公司</option>
		 					</select>
						</c:when>
						
						<c:when test="${settlementTaskJY.geCompany=='金域基因公司'}">
							<select name="collectionCompany"  id="collectionCompany" style="width:198px;margin-top:5px;" class="required">
								<option value=''>---请选择---</option>
								<option value='广州市金埻网络科技有限公司'>广州市金埻网络科技有限公司</option>
		 					</select>
						</c:when>
						
						<c:when test="${settlementTaskJY.geCompany=='知康基因公司'}">
							<select name="collectionCompany"  id="collectionCompany" style="width:198px;margin-top:5px;" class="required">
								<option value=''>---请选择---</option>
								<option value='知康科技（北京）有限公司'>知康科技（北京）有限公司</option>
		 					</select>
						</c:when>
						
						<c:otherwise>
							<select name="collectionCompany"  id="collectionCompany" style="width:198px;margin-top:5px;" class="required">
							<option value=''>---请选择---</option>
							<option value='其他'>其他</option>
		 				</select>
						</c:otherwise>
					</c:choose>
					
					<%-- <c:if test="${settlementTaskJY.geCompany=='南方基因公司'}">
						<input type="text" name="collectionCompanyInput" id="collectionCompanyInput" readonly="readonly" value="${settleTaskDetailValue.collectionCompany}"/>
						<select name="collectionCompany" id="collectionCompany" style="width:198px;margin-top:5px;" class="required">
							<option value=''>---请选择---</option>
							<option value='上海申友生物技术有限责任公司'>上海申友生物技术有限责任公司</option>
							<option value='南京申友生物技术有限公司'>南京申友生物技术有限公司</option>
		 				</select>
					</c:if>
					<c:if test="${settlementTaskJY.geCompany=='北方基因公司'}">
						<input type="text" name="collectionCompanyInput" id="collectionCompanyInput" readonly="readonly" value="${settleTaskDetailValue.collectionCompany}"/>
						<select name="collectionCompany"  id="collectionCompany" style="width:198px;margin-top:5px;" class="required">
							<option value=''>---请选择---</option>
							<option value='北京上诺佰益生物技术有限公司'>北京上诺佰益生物技术有限公司</option>
		 				</select>
					</c:if>
					
					<c:if test="${settlementTaskJY.geCompany=='金域基因公司'}">
						<input type="text" name="collectionCompanyInput" id="collectionCompanyInput" readonly="readonly" value="${settleTaskDetailValue.collectionCompany}"/>
						<select name="collectionCompany"  id="collectionCompany" style="width:198px;margin-top:5px;" class="required">
							<option value=''>---请选择---</option>
							<option value='广州市金埻网络科技有限公司'>广州市金埻网络科技有限公司</option>
		 				</select>
					</c:if>
					
					<c:if test="${settlementTaskJY.geCompany=='知康基因公司'}">
						<input type="text" name="collectionCompanyInput" id="collectionCompanyInput" readonly="readonly" value="${settleTaskDetailValue.collectionCompany}"/>
						<select name="collectionCompany"  id="collectionCompany" style="width:198px;margin-top:5px;" class="required">
							<option value=''>---请选择---</option>
							<option value='知康科技（北京）有限公司'>知康科技（北京）有限公司</option>
		 				</select>
					</c:if>
					
					<c:if test="${settlementTaskJY.geCompany!='北方基因公司'&&settlementTaskJY.geCompany!='南方基因公司'&&settlementTaskJY.geCompany!='金域基因公司'&&settlementTaskJY.geCompany!='知康基因公司'}">
						<select name="collectionCompany"  id="collectionCompany" style="width:198px;margin-top:5px;" class="required">
							<option value=''>---请选择---</option>
							<option value='其他'>其他</option>
		 				</select>
					</c:if> --%>
					
				</td>
				<td>
					<label>入账说明：</label> 
					<input type="text" name="note" id="note" value="${settleTaskDetailValue.note}" width="20px"/>
				</td>
			</tr>
			<tr>
				<td><div class="buttonActive" style="margin-left: 300px;"><div class="buttonContent"><button type="button" id="formSumbitButton" onclick="buttonDisabledTrue()">支付</button></div></div>
					<!-- <div class="buttonActive"><div class="buttonContent" id="formSumbitOnce"><button type="button" >保存</button></div></div> -->
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<!-- <li><a class="add" href="javascript:void(0);" onclick="updatePage()"><span>修改结算详细</span></a></li> -->
			<!-- <li><a class="edit"   onclick="confirmSettle()"><span>确认结算</span></a></li> -->
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="table" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> -->
				
				<th>序号</th>
                <!--<th  export = "true" columnEnName = "id" columnChName = "id" >id</th>-->
				<th width="160"  export = "true" columnEnName = "taskNo" columnChName = "任务号" >基因公司</th>
				<th width="160" export= "true" columnEnName = "settlementTime" columnChName = "创建时间">总付款金额</th>
				<th  export = "true" columnEnName = "geCompany" columnChName = "基因公司">收款公司</th>
				<th  export = "true" columnEnName = "branchCompanyNum" columnChName = "支公司数" >付款方式</th>
				<th  export = "true" columnEnName = "setMealNum" columnChName = "套餐数量" >付款时间</th>
				<th  export = "true" columnEnName = "totalPersonNum" columnChName = "成功导入总数" >付款银行</th>
				<th  export = "true" columnEnName = "successNum" columnChName = "匹配数量" >本次付款金额</th>
				<th  export = "true" columnEnName = "abnormalNum" columnChName = "异常数量" >未付款</th>
				<th  export = "true" columnEnName = "totalAmount" columnChName = "本次可结算金额(元)" >OA编号</th>
				<th  export = "true" columnEnName = "status" columnChName = "" >入账说明</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }" >
					<td align="center">
						<input type="checkbox" name="ids" value="${entity.id}">
						${status.count}
					</td> 
					<td align="center">${entity.geCompany}</td>
					<td align="center">${entity.sumAmount}</td>
					<td align="center">${entity.collectionCompany}</td>
					<td align="center">${entity.payMode}</td>
					<td align="center">	${entity.payTime}</td>
					<td align="center">	${entity.payBank}</td>
					<td align="center">	${entity.currentAmount}</td>
					<td align="center">	${entity.noPayAmount}</td>
					<td align="center">	${entity.OANo}</td>
					<td align="center">	${entity.note}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 



