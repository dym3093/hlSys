<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
	
function fnSave(){
	var mailNo=$("#mailNo3").val();
	if($.trim(mailNo)==""){
		alertMsg.info("请输入快递单号！");
		return;
	}
	var dataJson ="[";
	$("#mailCustomer3 tr td").find("#groupId").each(function(index,data){
		var id = $.trim($(data).text());
		var orderNo=$.trim($(data).parent().next().text());
		var name = $.trim($(data).parent().parent().children().find("#name").text());
		var guardianName = $.trim($(data).parent().parent().children().find("#guardianName").text());
		var phone = $.trim($(data).parent().parent().children().find("#guardianPhone").text());
		dataJson += "{"+"\"id\":\""+id+"\","+"\"name\":\""+name+"\","+"\"guardianName\":\""+guardianName+"\","+"\"phone\":\""+phone+"\","+"\"orderNo\":\""+orderNo+"\"},";
//		ids+=$.trim($(data).text())+",";
	});
	if (dataJson.lastIndexOf(",")) {  
		dataJson = dataJson.substring(0,dataJson.length -1);  
		dataJson +="]";
	}  
	$.ajax({	
		type: "post",
		cache :true,
		dateType:"json",
		url: "${path}/children/erpOrderInfo!insertMailCustomerReportInfo.action",
		data:{"dataJson":dataJson,"mailNo":mailNo},
		success: function(data){
			var s= eval("("+data+")");
			if(s.count!="error"){
				if(s.info==0){
					alertMsg.info("保存成功,微信推送失败");
				}else{
					alertMsg.info("保存成功,微信推送成功");
				}
				$("#mailCustomer3 tr td").find("#smailNo").each(function(index,data){
					$.trim($(data).text(mailNo));
					$.trim($(data).parent().next().text("报告已寄出"));
				});
			}else{
				alertMsg.error("保存失败");
			}
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}
	
	
	//取消快递录入
	function cancelCourier(){
		navTab.closeCurrentTab();
	}
	
</script>

<div class="tip"><span>快递信息录入</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" rel="pagerForm" onsubmit="if(this.action != '${path}/children/erpOrderInfo!insertMailCompanyBoxInfo.action'){this.action = '${path}/children/erpOrderInfo!insertMailCompanyBoxInfo.action' ;} ;return navTabSearch(this);" 
		action="${path}/children/erpOrderInfo!insertMailCompanyBoxInfo.action" method="post"	>
	<div class="searchBar">
		<table class="pageFormContent" id="tableId">
			<tr>
				<td >
					<label style="width: 120px;">寄客户报告快递号：</label> 
					<input type="text"  value="${mailNo}" id="mailNo3"/>
					<input type="text"  name="filter_and_ids_LIKES_S"  id="ids" value="${ids}" hidden="hidden"/>
				</td>
				<td>
         			<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="fnSave()">保存</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar" >
            <li style="margin-left:5px;"><a class="icon" onclick="cancelCourier()"><span>取消快递录入</span></a></li>
			<web:exportExcelTag pagerFormId="pagerFindForm"
								pagerMethodName="exportEtOrderByExl"
								actionAllUrl="org.hpin.children.web.ErpOrderInfoAction"
								informationTableId="groupOrderTable3"
								fileName="ExpressReport"></web:exportExcelTag>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="groupOrderTable3"> 
			<thead>
			<tr>
				<th width="50" nowrap="true" >序号</th>
				<th width="240" export = "true" columnEnName = "id" columnChName = "批次号" nowrap="true" > 批次号 </th> 
				<th width="200" export = "true" columnEnName = "orderNo" columnChName = "订单号" nowrap="true" > 订单号 </th>
				<th width="80" export = "true" columnEnName = "orderDate" columnChName = "订单日期" nowrap="true" > 订单日期 </th> 
				<th width="100" export = "true" columnEnName = "name" columnChName = "姓名 " nowrap="true" > 姓名 </th> 
				<!-- <th width="150" export = "true" columnEnName = "sex" columnChName = "性别 " > 性别 </th> 
				<th width="150" export = "true" columnEnName = "age" columnChName = "年龄 " > 年龄 </th>  -->
				<th width="80" export = "true" columnEnName = "setMealName" columnChName = "检测项目 " nowrap="true" > 检测项目 </th> 
				<th width="100" export = "false" columnEnName = "guardianName" columnChName = "监护人姓名 " hidden="hidden" > 监护人姓名 </th>
				<th width="120" export = "true" columnEnName = "guardianPhone" columnChName = "手机 " nowrap="true" > 手机 </th> 
				<th width="100" export = "true" columnEnName = "mailNo" columnChName = "快递号 " nowrap="true" > 快递号 </th> 
				<!-- <th width="100" export = "true" columnEnName = "relationship" columnChName = "关系" > 关系 </th>  -->
				<th width="80" export = "true" columnEnName = "status" columnChName = "订单状态" nowrap="true"> 订单状态 </th> 
				<th width="150" export = "true" columnEnName = "reportAddress" columnChName = "地址" nowrap="true"> 地址 </th> 
				<th width="200" export = "true" columnEnName = "note" columnChName = "备注" nowrap="true" > 备注 </th>
			</tr>
		</thead>
		<tbody id="mailCustomer3">
			<c:forEach items="${page.results}" var="entity" varStatus="status">
					<tr target="sid_user" rel="${entity.id}" id="rows">
						<td align="center">
							<%-- <input type="checkbox" name="ids" value="${entity.rn}"> --%>${status.count}
						</td>
						<td align="center"><span id="groupId">${entity.id}</span></td>
						<td align="center"><span>${entity.orderNo}</span></td>
						<td align="center"> ${fn:substring(entity.createDate,0,10)} </td>
						<td align="center" title="${entity.name}"><span id="name">${entity.name}</span></td>
						<%-- <td align="center"> ${entity.sex} </td>
						<td align="center"> ${entity.age} </td> --%>
						<td align="center" title="${entity.setMealName}"> ${entity.setMealName} </td>
						<td align="center" title="${entity.guardianName}" hidden="hidden"><span id="guardianName">${entity.guardianName}</span></td>
						<td align="center" title="${entity.guardianPhone}"><span id="guardianPhone">${entity.guardianPhone}</span></td>
						<td align="center" > <span id="smailNo">${entity.mailNo}</span></td>
						<%-- <td align="center"> ${entity.relationship} </td> --%>
						<td align="center" title="${entity.status}"> <span id="sstatus">${entity.status}</span></td>
						<td align="center" title="${entity.reportAddress}"> ${entity.reportAddress} </td>
						<%-- <td align="center" title="${entity.price}"> ${entity.price}</td> --%>
						<td align="center" title="${entity.note}"> ${entity.note}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 