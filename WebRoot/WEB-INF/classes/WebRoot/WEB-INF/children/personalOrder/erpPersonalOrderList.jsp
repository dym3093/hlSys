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
	
	$(function(){	//初始化儿童套餐
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/children/erpOrderInfo!getSelectInput.action",
			success: function(data){
 				var selectComboId = $("#comboText").val();
				$("#comboSel").empty();
// 				var s= eval("("+data+")");
  				var option="<option value=''>---请选择---</option>";
  				var combos = eval("("+data+")").combo;
				for(var i=0;i<combos.length;i++){	
					if(selectComboId==combos[i].SETMEALNAME){
						option += "<option value='"+combos[i].SETMEALNAME+"' selected='selected'>"+combos[i].SETMEALNAME+"</option>";
					}else{
						option += "<option value='"+combos[i].SETMEALNAME+"'>"+combos[i].SETMEALNAME+"</option>";
					}
				}
				//将节点插入到下拉列表中
 				$("#comboSel").append(option);
 			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
			
	});
	
	$(function(){
		var selectStatus = $(":input[status='os']").val();
		if(selectStatus!=""){
			$("#statusSel").find("option[value='"+selectStatus+"']").attr("selected",true);
		}
		
	});
	
	$("#statusSel").change( function() {	//状态下拉框
		var selectVal=$("#statusSel option:selected").val();
		$(":input[status='os']").val(selectVal);
	});
	
	$("#comboSel").change( function() {	//套餐下拉框
		var selectVal=$("#comboSel option:selected").text();
		$("#comboText").val(selectVal);
	});
	
	//打印
	function showPirntPreviewPage(){
		var count = 0;
		$('input:checkbox[name="lp"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
			count += count+1;
		});
		if(count == 0) {
			alertMsg.info('请选择要打印的订单信息！');
			return ;
		}
		else if(count > 1) {
			alertMsg.info('请选择要打印的一条订单信息！');
			return ;		
		}
		var id = $('input:checkbox[name="lp"]:checked').parent().next().text();
		var name = $('input:checkbox[name="lp"]:checked').parent().parent().find("td").eq(4).text();
		$.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			data:{"id":id,"name":name},
			url: "${path}/children/erpOrderInfo!getPrintFile.action",
			success: function(data){
				window.open(eval("("+data+")").path,"_blank");
 			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
		
// 		navTab.openTab("showPirntPreviewPage", "../children/erpOrderInfo!getPrintFile.action",
// 				{ title:"打印预览", fresh:true,data:{"id":id} });
	}
	
	//寄客户采样盒
	function showMailCustomerBoxPage(){
		var ids="";
		if($("input:checkbox[name='lp']:checked").length==0){
			alertMsg.info("请选择要寄给客户的采样信息！");
			return;
		}else{
			$("input:checkbox[name='lp']:checked").each(function(index,data){
				ids+=$.trim($(data).parent().next().text())+",";
			});
		}
		if (ids.lastIndexOf(",")) {  
			ids = ids.substring(0,ids.length -1);  
		}
		navTab.openTab("showMailCustomerBoxPage", "../children/erpOrderInfo!getMailCustomerBoxList.action",
				{ title:"寄客户采样盒", fresh:true,data:{"ids":ids} });
	}
	
	//转到条形码录入界面
	function showBarCodeInputPage(){
		var id;
		var count = $("input:checkbox[name='lp']:checked").length;
		if(count==0){
			alertMsg.info("请选择要录入条码的订单");
			return false;
		}else if(count >=2){
			alertMsg.info("请选择一条要录入条码的订单");
			return false;
		}
		id=$.trim($("input:checkbox[name='lp']:checked").parent().next().text());
		navTab.openTab("showBarCodeInputPage", "../children/erpOrderInfo!getBarCodeInputInfo.action",
				{ title:"条形码录入", fresh:true,data:{"id":id} });
	}
	
	//寄基因公司采样盒
	function showMailCompanyBoxPage(){
		var ids="";
		var count = $("input:checkbox[name='lp']:checked").length;
		if(count==0){
			alertMsg.info("请选择相应的订单信息");
			return false;
		}
		$("input:checkbox[name='lp']:checked").each(function(index,data){
			ids+=$.trim($(data).parent().next().text())+",";
		});
		if (ids.lastIndexOf(",")) {  
			ids = ids.substring(0,ids.length -1);  
		}
		navTab.openTab("showMailCompanyBoxPage", "../children/erpOrderInfo!getMailCompanyBoxList.action",
				{ title:"寄基因公司采样盒", fresh:true,data:{"ids":ids} });
	}
	
	//给客户的报告
	function showCustomerReportPage(){
		var ids="";
		if($("input:checkbox[name='lp']:checked").length==0){
			alertMsg.info("请选择要寄给客户的采样信息！");
			return;
		}else{
			$("input:checkbox[name='lp']:checked").each(function(index,data){
				ids+=$.trim($(data).parent().next().text())+",";
			});
		}
		if (ids.lastIndexOf(",")) {  
			ids = ids.substring(0,ids.length -1);  
		}
		navTab.openTab("showCustomerReportPage", "../children/erpOrderInfo!getMailCustomerReportList.action",
				{ title:"寄客户报告", fresh:true,data:{"ids":ids} });
	}
	
	function showEditCustomerInfoPage(){		//将参数传到修改页面
		var count = 0;
		$('input:checkbox[name="lp"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
			count += count+1;
		});
		if(count == 0) {
			alertMsg.info('请选择要更改的订单信息！');
			return ;
		}
		else if(count > 1) {
			alertMsg.info('请选择要更改的一条订单信息！');
			return ;		
		}
		var id = $.trim($("input:checkbox[name='lp']:checked").parent().next("td").text());
		navTab.openTab("showEditCustomerInfoPage", "../children/erpOrderInfo!setCustomerOrderInfo.action",
				{ title:"订单信息修改", fresh:true, data:{"id":id} });
	}
	
	function deleteOrderInfo(){			//删除订单信息
		var count = $(":input:checkbox[name='lp']:checked']").length;
		if(count==0){
			alertMsg.info("请选择要删除的订单信息");
		}else{
			if(window.confirm("确定删除该订单信息吗？")){
				var str="";
				$(":checkbox[checked='checked']").each(function(index,item) {
					str += $.trim($(this).parent().next("td").text())+",";
				})
				if (str.lastIndexOf(",")) {  
	    			str = str.substring(0,str.length -1);  
				}
				$.ajax({
					type: "post",
					cache :false,
					dateType:"json",
					data:{"dataJson":str},
					url: "${path}/children/erpOrderInfo!deleteOrderInfo.action",
					success: function(data){
		 				if(data.count==0){
		 					alertMsg.error("删除订单失败");
		 				}else{
		 					alertMsg.info("成功删除【"+count+"】条数据");
		 					return navTabSearch(this);	//用于删除成功后刷新table
		 				}
		 			},
					error :function(){
						alert("服务发生异常，请稍后再试！");
						return;
					}
				});
			}else{
				return false;
			}
		}
	}
	
</script>

<div class="tip"><span>个体订单信息查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" rel="pagerForm" onsubmit="if(this.action != '${path}/children/erpOrderInfo!getErpOrderList.action'){this.action = '${path}/children/erpOrderInfo!getErpOrderList.action' ;} ;return navTabSearch(this);" 
		action="${path}/children/erpOrderInfo!getErpOrderList.action" method="post"	>
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
					<label>订单状态：</label>
					<input type="text" status="os" name="filter_and_status_LIKE_S" value="${filter_and_status_LIKE_S}" hidden="hidden"/> 
					<select id="statusSel">
						<option value="">---请选择---</option>
						<option value="处理中">处理中</option>
						<option value="已处理">已处理</option>
						<option value="已送检">已送检</option>
						<option value="检测中">检测中</option>
						<option value="已结束">已结束</option>
						<option value="报告已寄出">报告已寄出</option>
						<option value="报告已上传">报告已上传</option>
						<option value="客户已收采样盒">客户已收采样盒</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>检测人姓名：</label>
					<input type="text" name="filter_and_name_LIKE_S"  value="${filter_and_name_LIKE_S}" />
				</td>
				<td>
					<label>监护人姓名：</label>
					<input type="text" name="filter_and_guardianName_LIKE_S"  value="${filter_and_guardianName_LIKE_S}" />
				</td>
				<td>
					<label>监护人电话：</label>
					<input type="text" name="filter_and_guardianPhone_LIKE_S"  value="${filter_and_guardianPhone_LIKE_S}" />
				</td>
			</tr>
			
			<tr>
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_sampleDate_GEST_T"  id="d1" style="width: 170px;" 
					class="condition" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  
					readonly="readonly" value="${filter_and_orderDate_GEST_T}" />
					<a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_sampleDate_LEET_T" id="d2" style="width: 170px;" 
					class="condition" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" 
					readonly="readonly" value="${filter_and_orderDate_LEET_T}" />
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
					<label>套餐：</label>
					<input type="text" name="filter_and_setMealName_LIKE_S" id="comboText" value="${filter_and_setMealName_LIKE_S}" hidden="hidden"/> 
					<select id="comboSel">
				
					</select>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>条形码：</label>
					<input type="text" name="filter_and_code_LIKE_S"  value="${filter_and_code_LIKE_S}" />
				</td>
				<td style="width:380px;"></td>
				<td style="padding-left:225px;">
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
		<ul class="toolBar" >
            <li style="margin-left:5px;"><a class="icon" onclick="showPirntPreviewPage()"><span>打印订单</span></a></li>
			<li style="margin-left:5px;"><a class="add" onclick="showMailCustomerBoxPage()"><span>寄客户采样盒</span></a></li>
			<li style="margin-left:5px;"><a class="edit" onclick="showBarCodeInputPage()"><span>录入条码</span></a></li>
			<li style="margin-left:5px;"><a class="delete" onclick="showMailCompanyBoxPage()"><span>寄基因公司采样盒</span></a></li>
			<li style="margin-left:5px;"><a class="icon" onclick="showCustomerReportPage()"><span>寄客户报告</span></a></li>
			<li style="margin-left:5px;"><a class="add" onclick="showEditCustomerInfoPage()"><span>修改</span></a></li>
			<!-- <li style="margin-left:5px;"><a class="delete" onclick="deleteOrderInfo()"><span>删除</span></a></li> -->
			<!-- <li style="margin-left:5px;"><a class="add" ><span>导出</span></a></li> -->
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="50" nowrap="true" >序号</th>
				<th width="240" export = "true" columnEnName = "id" columnChName = "批次号" nowrap="true"> 批次号 </th> 
				<th width="200" export = "true" columnEnName = "orderNo" columnChName = "订单号" nowrap="true" > 订单号 </th>
				<th width="100" export = "true" columnEnName = "orderDate" columnChName = "订单日期" nowrap="true" > 订单日期 </th> 
				<th width="100" export = "true" columnEnName = "name" columnChName = "姓名 " nowrap="true" > 姓名 </th> 
				<th width="50" export = "true" columnEnName = "sex" columnChName = "性别 " nowrap="true" > 性别 </th> 
				<th width="40" export = "true" columnEnName = "age" columnChName = "年龄 " nowrap="true" > 年龄 </th> 
				<th width="120" export = "true" columnEnName = "setMealName" columnChName = "检测项目 " nowrap="true" > 检测项目 </th> 
				<th width="100" export = "true" columnEnName = "guardianName" columnChName = "监护人姓名 " nowrap="true" > 监护人姓名 </th> 
				<th width="120" export = "true" columnEnName = "guardianPhone" columnChName = "手机 " nowrap="true" > 手机 </th> 
				<th width="100" export = "true" columnEnName = "relationship" columnChName = "关系" nowrap="true" > 关系 </th> 
				<th width="100" export = "true" columnEnName = "status" columnChName = "订单状态" nowrap="true"> 订单状态 </th> 
				<th width="200" export = "true" columnEnName = "sampleAddress" columnChName = "采样盒地址" nowrap="true"> 采样包地址 </th> 
				<th width="200" export = "true" columnEnName = "reportAddress" columnChName = "报告地址" nowrap="true"> 接收报告地址 </th> 
				<th width="40" export = "true" columnEnName = "height" columnChName = "备注" nowrap="true" > 身高 </th>
				<th width="40" export = "true" columnEnName = "weight" columnChName = "备注" nowrap="true" > 体重 </th>
				<th width="200" export = "true" columnEnName = "note" columnChName = "备注" nowrap="true"> 备注 </th>
				<th width="200" export = "true" columnEnName = "familyHistory" columnChName = "备注" nowrap="true" hidden="hidden"> 家族病史 </th>
				<th width="200" export = "true" columnEnName = "idNo" columnChName = "备注" nowrap="true" hidden="hidden"> 身份证 </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
					<tr target="sid_user" rel="${entity.id}" id="rows">
						<td width="50" nowrap="true" align="center">
							<input type="checkbox" name="lp">${status.count}
						</td>
						<td width="240" align="center" id="id"  nowrap="true" title="${entity.id}">  ${entity.id} </td>
						<td width="200" align="center" id="orderNo" nowrap="true" title="${entity.orderNo}">  ${entity.orderNo} </td>  
						<td width="100" align="center" id="createDate" nowrap="true"> ${fn:substring(entity.createDate,0,10)} </td>
						<td width="150" align="center" id="name" nowrap="true" title="${entity.name}"> ${entity.name} </td>
						<td width="50" align="center" id="sex" nowrap="true"> ${entity.sex} </td>
						<td width="40" align="center" id="age" nowrap="true"> ${entity.age} </td>
						<td width="120" align="center" id="setMealName" nowrap="true" title="${entity.setMealName}"> ${entity.setMealName} </td>
						<td width="150" align="center" id="guardianName" nowrap="true" title="${entity.guardianName}"> ${entity.guardianName} </td>
						<td width="120" align="center" id="guardianPhone" nowrap="true" title="${entity.guardianPhone}"> ${entity.guardianPhone} </td>
						<td width="100" align="center" id="relationship" nowrap="true"> ${entity.relationship} </td>
						<td width="100" align="center" id="status" nowrap="true" title="${entity.status}"> ${entity.status} </td>
						<td width="200" align="center" id="sampleAddress" nowrap="true" title="${entity.sampleAddress}"> ${entity.sampleAddress} </td>
						<td width="200" align="center" id="reportAddress" nowrap="true" title="${entity.reportAddress}"> ${entity.reportAddress} </td>
						<td width="40" align="center" id="note" nowrap="true" title="${entity.height}"> ${entity.height}</td>
						<td width="40" align="center" id="note" nowrap="true" title="${entity.weight}"> ${entity.weight}</td>
						<td width="200" align="center" id="note" nowrap="true" title="${entity.note}"> ${entity.note}</td>
						<td width="200" align="center" id="note" nowrap="true" title="${entity.familyHistory}" hidden="hidden"> ${entity.familyHistory}</td>
						<td width="200" align="center" id="note" nowrap="true" title="${entity.idNo}" hidden="hidden"> ${entity.idNo}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 