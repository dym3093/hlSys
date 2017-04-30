<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">

// 	$(function(){	//区分是否是销售人员
// 		$.ajax({	
// 			type: "post",
// 			cache :false,
// 			dateType:"json",
// 			url: "${path}/children/erpGroupOrderInfo!isSaleMan.action",
// 			success: function(data){
//  				if(eval("("+data+")").count==1){
//  					$("#groupOrder1 tr td").find("a").removeAttr("onclick style");
//  					$("#groupOrder1 tr td").find("button").attr("disabled",true);
//  				}
//  			},
// 			error :function(){
// 				alert("服务发生异常，请稍后再试！");
// 				return;
// 			}
// 		});
// 	});

	function showUploadPage(obj){
		var id=$(obj).parent("td").parent("tr").children().eq(1).text();
		var orderNo=$(obj).parent("td").parent("tr").children().eq(2).text();
		navTab.openTab("uploadFile", "${path}/children/erpGroupOrderInfo!showUploadPage.action", 
			{title:"团购订单信息导入", fresh:true, data:{"id":id,"orderNo":orderNo}});
		
	}
	
	// 单击订单号后进入个体订单界面
	function showPersonalOrder(obj){
		var orderNo=obj.parentNode.innerText;
		var id = $(obj).parent("td").prev("td").text();	
		navTab.openTab("showPersonalOrder", "../children/erpOrderInfo!getErpOrderList.action",
				{ title:"儿童基因订单", fresh:true,data:{"filter_and_orderNo_LIKE_S":orderNo} });
	}
	
	//更改客户订单信息
	function editOrderInfo(){
	//		navTab.openTab("editOrderInfo", "../children/erpOrderInfo!setCustomerOrderInfo.action",
	//				{ title:"更改客户订单信息", fresh:false,data:{} });
	}
	
	function showBasicInfo(){//查询客户信息
		navTab.openTab("showBasicInfo", "../children/erpGroupOrderInfo!getCustomerBasicInfo.action",
					{ title:"客户基本信息", fresh:true,data:{} });
	}
	
</script>

<div class="tip"><span>团购订单信息查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" rel="pagerForm" onsubmit="if(this.action != '${path}/children/erpGroupOrderInfo!getErpGroupOrderList.action'){this.action = '${path}/children/erpGroupOrderInfo!getErpGroupOrderList.action' ;} ;return navTabSearch(this);" 
		action="${path}/children/erpGroupOrderInfo!getErpGroupOrderList.action" method="post">
	<div class="searchBar">
		<table class="pageFormContent" id="tableId">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" name="filter_and_id_LIKE_S" value="${filter_and_id_LIKE_S}" />
				</td>
				<td>
					<label>订单号：</label> 
					<input type="text" name="filter_and_orderNo_LIKE_S"  value="${filter_and_orderNo_LIKE_S}" />
				</td>
		
				<td>
					<label>联系人姓名：</label>
					<input type="text" name="filter_and_name_LIKE_S"  value="${filter_and_name_LIKE_S}" class="condition"/>
				</td>
				<td></td>
		
					</tr>
			<tr>
				<td>
					<label>联系人手机：</label>
					<input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" name="filter_and_phone_LIKE_S"  value="${filter_and_phone_LIKE_S}" class="condition"/>
				</td>
			
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_orderDate_GEST_T"  id="d1" style="width: 170px;" class="condition" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_orderDate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_orderDate_LEET_T" id="d2" style="width: 170px;" class="condition" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_orderDate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
         			<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
			
		</table>
	</div>
	</form>
</div>


<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar" style="margin-left: 5px;">
            <!-- <li><a class="icon" onclick="showBasicInfo()"><span>查看客户信息</span></a></li> -->
            <!-- <li><a class="icon" onclick="insertCityInfo()"><span>导入信息</span></a></li> -->
            <li style="margin-left: 10px;">
				<web:exportExcelTag pagerFormId="pagerFindForm"
									pagerMethodName="exportEtOrderByExl"
									actionAllUrl="org.hpin.children.web.ErpGroupOrderInfoAction"
									informationTableId="groupOrderTable"
									fileName="tuangou"></web:exportExcelTag>
			</li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="groupOrderTable"> 
		<thead>
			<tr>
				<th width="60">序号</th>
				<th width="320" export = "true" columnEnName = "id" columnChName = "批次号" > 批次号 </th> 
				<th width="320" export = "true" columnEnName = "orderno" columnChName = "订单号" > 订单号 </th>
				<th width="200" export = "true" columnEnName = "orderdate" columnChName = "订单日期" > 订单日期 </th> 
				<th width="150" export = "true" columnEnName = "name" columnChName = "联系人姓名 " > 联系人姓名 </th> 
				<th width="200" export = "true" columnEnName = "phone" columnChName = "联系人手机" > 联系人手机 </th> 
				<th width="100" export = "true" columnEnName = "type" columnChName = "订单类型" > 订单类型 </th> 
				<th width="200" export = "true" columnEnName = "exam_num" columnChName = "检测人数"> 检测人数 </th> 
				<th width="200" export = "true" columnEnName = "price" columnChName = "金额" > 金额</th> 
				<th width="150" export = "false" columnEnName = "createUserId" columnChName = "操作" > 操作 </th>
			</tr>
		</thead>
		<tbody id="groupOrder1">
			<c:forEach items="${page.results}" var="entity" varStatus="status">
					<tr target="sid_user" rel="${entity.id}" id="rows">
						<td align="center">
							<input type="checkbox" name="ids" value="${entity.id}">${status.count}
						</td>
						<td align="center" title="${entity.id}" id="${entity.id}">${entity.id}</td>
						<td align="center" title="${entity.orderNo}"> 
							<a style="color: blue;cursor: pointer;" onclick="showPersonalOrder(this)"> ${entity.orderNo} </a> 
						</td>  
						<td align="center"> ${fn:substring(entity.orderDate,0,10)} </td>
						<td align="center"> ${entity.name} </td>
						<td align="center"> ${entity.phone} </td>
						<c:choose>
							<c:when test="${entity.type == '1'}">
								<td align="center"> 团购 </td>
							</c:when>
							<c:when test="${entity.type == '0'}">
								<td align="center"> 个人 </td>
							</c:when>
						</c:choose>
						<td align="center"> ${entity.exam_num} </td>
						<td align="center"> ${entity.price}</td>
						<td align="center"> <button type="button" onclick="showUploadPage(this)">导入</button></td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 