<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript">

$(function(){
	//点击tab上的关闭按钮
	$(navTab._getTabs().filter('.selected').find(":last-child")[1]).bind("click", function(){
		console.log(this+", id: "+$settleId);
		if($settleId.trim()!=""&&$settleId!=null&&$settleId!=undefined&&$settleId!="undefined"){
			$.ajax({
				url: "${path}/settlementManagement/erpSettlementTaskBX!cancelTask.action",
				type: "json",
				type: "post",
				cache :false,
				data: {"id": $settleId},
				success: function(resp){
					var data = eval("("+resp+")");
					if(data.result=="success"){
						//navTab.closeCurrentTab();
						//return navTabSearch(this);
					}
				},
				error: function(data){
					alertMsg.error("网络异常！请稍后再试！");
				}
			});
		}
	});
	
	$("input[name='idArrs']").bind("click", function(){
		var boxes = $("input[name='idArrs']");
		 $("#selectAll").attr("checked", boxes.length == boxes.filter(":checked").length);
	});
})

$.fn.serializeJson=function(){
		var serializeObj={};  
		var array=this.serializeArray();  
		$(array).each(function(){  
			if(serializeObj[this.name]){  
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

//保存结算任务
function saveSettlementTask(){

	var projectNo = $("#projectNo").val();
	if(projectNo.trim()==""||projectNo==null||projectNo==undefined||projectNo=='undefined'){
		alertMsg.info("请填写项目编号!");
	}

	var data = $("#addForm").serializeJson();
	
	if(jQuery.isEmptyObject(data)){
		alertMsg.info("请填写数据！");
		return;
	}
	
	var type = $("#type").val();
	
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/settlementManagement/erpSettlementTaskBX!saveSettlementTask.action",
		data: {'data': JSON.stringify(data), 'type':type},
		success: function(data){
			if(data.result=="success"){
				alertMsg.correct("保存成功！");
				navTab.closeCurrentTab();
				//$.pdialog.closeCurrent();
				return navTabSearch(this);
			}else{
				alertMsg.error("保存失败！");
			}	
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
}

//取消时更新ErpCustomer表中的状态和删除ErpCustomerSettleBx表相关数据
function cancel(){
	navTab.closeCurrentTab();
}

/*
 * 按场次设置实结价
 */
function eventsAdd() {
	//打开导入窗口
	// //结算任务Id;
	var settlementTaskId = $("#id").val(); //"b052b822d7f54c06bcdf89812fc83ccd" 
	//当前tabId
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
    $.pdialog.open("${path}/settlementManagement/erpSettlementTaskBX!eventsAdd.action?settlementTaskId="+settlementTaskId+"&navTabId="+navTabId, "eventsAddDialogId", "按场次设置实结价",
    		{width:900,height:530, maxable:false, minable:false, mask:true,fresh:false,close:dialogClose} );
}

//关闭后刷新页面;
function dialogClose() {
	var forwardUrl = $("#addForm").attr("action");
	navTab.reload(forwardUrl);
	return true;
}
	
/*
 * 按会员设置实结价
 * 只能选择一个会员;
 */
function customerAdd() {
	//判断选择一个会员;
	var ids = [];
	$("input[name='idArrs']:checked", navTab.getCurrentPanel()).each(function(i){
		ids.push($(this).val());
	});

	if(ids.length!=1){
		alertMsg.info("请选择一个会员！");
		return;
	} 
	
	//当前tabId
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	//打开导入窗口
    $.pdialog.open("${path}/settlementManagement/erpSettlementTaskBX!customerAdd.action?customerId="+ids[0]+"&navTabId="+navTabId, "customerAddDialogId", "按会员设置实结价",
    		{width:530,height:330,mask:true,fresh:false, close:dialogClose });
	
}
</script>

<div class="pageHeader" style="background:white">
	<input type="hidden" id="type" name="type" value="${type}"/>
	<form id = "addForm" action="${path}/settlementManagement/erpSettlementTaskBX!toSetSale.action?id=${taskBX.id}&type=${type}" rel = "pagerForm" >
	<div class="searchBar">
			<input type="hidden" id="id" name="id" value="${taskBX.id}"/>
			<input type="hidden" name="status" value="${taskBX.status}"/>
			<input type="hidden" name="version" value="${taskBX.version}"/>
			<input type="hidden" name="createUserId" value="${taskBX.createUserId}"/>
			<input type="hidden" name="createUser" value="${taskBX.createUser}"/>
			<input type="hidden" name="updateUserId" value="${taskBX.updateUserId}"/>
			<input type="hidden" name="updateUser" value="${taskBX.updateUser}"/>
			<input type="hidden" name="proUser" value="${taskBX.proUser}"/> <!-- 项目负责人 -->
			<input type="hidden" name="branchCompany" value="${taskBX.branchCompany}"/> 
			<input type="hidden" name="ownedCompany" value="${taskBX.ownedCompany}"/> 
			<input type="hidden" name="branchCompanyId" value="${taskBX.branchCompanyId}"/> 
			<input type="hidden" name="ownedCompanyId" value="${taskBX.ownedCompanyId}"/> 
			
		<table class="pageFormContent">
			<tr>				
				<td><label style="width:90px">任务编号：</label> 
					<input id="taskNo" type="text" name="taskNo"  value="${taskBX.taskNo}" readonly="readonly" style="width:140px" />
				</td>
				<td><label style="width:90px">任务名称：</label> 
					<input id="taskName" type="text" name="taskName" value="${taskBX.taskName}" style="width:140px" />
				</td>
			 	<td>
					<label style="width:90px">项目编号：</label> 
					<input id="projectNo" type="text" name="projectNo" value="${taskBX.projectNo}" class="required" style="width:140px" />
				</td>				
				<td align="center">
					<label style="width:90px">结算时间：</label> 
					<input id="settlementTime" type="text" name="settlementTime" value="${taskBX.settlementTime}" class="date" readonly="readonly" style="background-color:#eee;width:140px"/> 
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>	
			</tr>
			<tr>
				<td>
					<label style="width:90px">套餐数量：</label> 
					<input type="text" name="setMealNum" value="${taskBX.setMealNum}" readonly="readonly" style="background-color:#eee;width:140px"/>
				</td>
				<td>
					<label style="width:90px">总人数：</label> 
					<input type="text" name="totalPersonNum" value="${taskBX.totalPersonNum}" readonly="readonly" style="background-color:#eee;width:140px"/>
				</td>
				<td>
					<label style="width:90px">应结算金额：</label> 
					<input type="text" name="totalAmount" value="${taskBX.totalAmount}" readonly="readonly" style="background-color:#eee;width:140px"/>
					<span style="line-height:30px;">(元)</span>
				</td>
				<td>
					<label style="width:90px">实际结算金额：</label> 
					<input id="actualTotalAmount" type="text" name="actualTotalAmount" value="${taskBX.actualTotalAmount}" class="required" readonly="readonly" style="width:140px"/>
					<span style="line-height:30px;">(元)</span>
				</td>
				<td>
					<label style="width:90px">其他收入金额：</label>
					<input id="totalIncome" type="text" name="totalIncome" value="${taskBX.totalIncome}" readonly="readonly" style="background-color:#eee;width:140px"/>
					<span style="line-height:30px;">(元)</span>
				</td>
			</tr>
			<tr>
				<td>
					<label style="width:90px">支公司数量：</label> 
					<input type="text" name="companyNum" value="${taskBX.companyNum}" readonly="readonly" style="background-color:#eee;width:140px"/>
				</td>
				<td>
					<label for="paymentType" style="width:90px">付款方式：</label> 
					<select id="paymentType" name="paymentType" class="equired" style="height:20px;margin:5px;width:140px;" >
						<option value="transferAccounts" <c:if test="${taskBX.paymentType == 'transferAccounts'}">selected=selected</c:if>>公对公银行转账</option>
						<option value="personalEBank" <c:if test="${taskBX.paymentType == 'personalEBank'}">selected=selected</c:if>>个人网银</option>
						<option value="Alipay" <c:if test="${taskBX.paymentType == 'Alipay'}">selected=selected</c:if>>支付宝</option>
						<option value="POS" <c:if test="${taskBX.paymentType == 'POS'}">selected=selected</c:if>>POS机刷卡</option>
		 			</select>
				</td>
				<td colspan="2">
					<label style="width:90px">是否开发票：</label> 
					<span style="margin-left:20px; margin-right:40px;">
						<input type="radio" name="invoice" value="1" <c:if test="${taskBX.invoice=='1'}">checked="checked"</c:if> />是
					</span>
					<span>
						<input type="radio" name="invoice" value="0" <c:if test="${taskBX.invoice=='0'}">checked="checked"</c:if> />否
					</span>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<p class="nowrap"><label style="height:60px;width:90px;" for="remark">备注：</label>
						<textarea style="width:520px;" id="remark" name="remark" >${taskBX.remark}</textarea>
						<b style="color:#ff0000;line-height:80px;">注:【备注】填写请勿超过300字</b>
					</p>
				</td>
				<td style="padding-left:50px">
					<div class="buttonActive" style="margin-right:20px;"><div class="buttonContent"><button type="button" onclick="saveSettlementTask()">保存</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button  type="button" onclick="cancel()">取消</button></div></div>
				</td>	
			</tr>
		</table>
	</div>
	</form>
</div>

<div>
	<div class="panelBar">
		<c:if test="${currentUser.accountName!='parkson'}">
			<ul class="toolBar">
				<li style="margin-right:15px;">
					<a class="add" onclick="eventsAdd()" ><span>按场次设置实结价</span></a>
				</li>
				<li style="margin-right:15px;">
					<a class="add" onclick="customerAdd()" ><span>按会员设置实结价</span></a>
				</li>
			</ul>
			<jsp:include page="/common/pagination.jsp" />
		</c:if>
	</div>	
	<table class="list" width="130%" layoutH="205" id="exportExcelTable"> 
		<thead>
			<tr>
				<th width="80">全选<input type="checkbox" id="selectAll" onclick="selectAll(this);" /></th>
				<th  export = "true" columnEnName = "eventsNo" columnChName = "场次号" > 场次号 </th> 
				<th  export = "true" columnEnName = "code" columnChName = "条码" > 条码 </th> 
				<th  export = "true" columnEnName = "name" columnChName = "姓名" > 姓名 </th> 
				<th  export = "true" columnEnName = "phone" columnChName = "电话" > 电话 </th> 
				<th  export = "true" columnEnName = "idno" columnChName = "身份证号" > 身份证号 </th> 
				<th  export = "true" columnEnName = "combo" columnChName = "套餐类型" > 套餐类型 </th> 
				<th  export = "true" columnEnName = "comboPrice" columnChName = "套餐价格" > 套餐价格 </th> 
				<th  export = "true" columnEnName = "" columnChName = "实际价格">实际价格</th>
				<th  export = "true" columnEnName = "" columnChName = "变更说明">变更说明</th>
				<th  export = "true" columnEnName = "sex" columnChName = "性别" > 性别 </th> 
				<th  export = "true" columnEnName = "age" columnChName = "年龄" > 年龄 </th> 
				<th  export = "true" columnEnName = "company" columnChName = "支公司" > 支公司 </th> 
				<th  export = "true" columnEnName = "ownedCompany" columnChName = "所属公司" > 所属公司 </th> 
				<th  export = "true" columnEnName = "salesMan" columnChName = "远盟对接人" > 远盟对接人 </th> 
				<th  export = "true" columnEnName = "samplingDate" columnChName = "采样日期" > 采样日期 </th> 
				<th  export = "true" columnEnName = "createTime" columnChName = "创建日期" > 创建日期 </th>
				<th  export = "true" columnEnName = "createUser" columnChName = "创建人" > 创建人 </th> 
				<th  export = "true" columnEnName = "status" columnChName = "状态" > 状态 </th> 
			</tr>
			
		</thead>
		<tbody >
			<c:forEach items="${page.results}" var="entity" varStatus="status">
			 <tr target="sid_user" rel="${entity.id }">
				<td align="center">
					<c:if test="${currentUser.accountName!='parkson'}">
						<input type="checkbox" name="idArrs" value="${entity.id}">
					</c:if>
					${ status.count }
				</td> 
				<td align="center"> ${entity.eventsNo} </td>
				<td align="center"> ${entity.code} </td>
				<td align="center"> ${entity.name} </td>
				<td align="center"> ${entity.phone} </td>
				<td align="center"> ${entity.idno} </td>
				<td align="center"> ${entity.combo} </td>
				<td align="center"> ${entity.comboPrice} </td>
				<td align="center"> ${entity.comboPriceActual} </td>
				<td align="center">
				<c:if test="${fn:length(entity.remark) > 6 }">
					${fn:substring(entity.remark, 0, 6)}
				</c:if>
				<c:if test="${fn:length(entity.remark) <= 6 }">
					${entity.remark} 
				</c:if>
				</td>
				<td align="center"> ${entity.sex} </td>
				<td align="center"> ${entity.age} </td>
				<td align="center">	<hpin:id2nameDB id="${entity.companyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
				<td align="center">	<hpin:id2nameDB id="${entity.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
				<td align="center"> ${entity.salesMan} </td>
				<td align="center"> ${entity.samplingDate} </td>
				<td align="center"> ${entity.createTime} </td>
				<td align="center"> ${entity.createUser} </td>
				<td align="center">  
					<c:choose>
						<c:when test="${entity.status=='0'}"> 未收款 </c:when>
						<c:when test="${entity.status=='1'}"> 待收款 </c:when>
						<c:when test="${entity.status=='2'}"> 已收款 </c:when>
						<c:otherwise><span style="color:#ff0000">状态异常</span></c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table> 
	
</div>
