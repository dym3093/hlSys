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

<script src="${path}/jquery/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path}/jquery/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	
	$(function(){	//初始化儿童套餐
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/children/erpOrderInfo!getSelectInput.action",
			success: function(data){
 				var selectComboId = $("#comboText2").val();
				$("#comboSel2").empty();
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
 				$("#comboSel2").append(option);
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
			$("#statusSel2").find("option[value='"+selectStatus+"']").attr("selected",true);
		}
		
	});
	
	$("#statusSel2").change( function() {	//状态下拉框
		var selectVal=$("#statusSel2 option:selected").val();
		$(":input[status='os']").val(selectVal);
	});
	
	$("#comboSel2").change( function() {	//套餐下拉框
		var selectVal=$("#comboSel2 option:selected").text();
		$("#comboText2").val(selectVal);
	});
	

	

	
</script>

<div class="tip"><span>个体订单信息查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" rel="pagerForm" onsubmit="if(this.action != '${path}/children/erpGroupOrderInfo!getCustomerBasicInfo.action'){this.action = '${path}/children/erpGroupOrderInfo!getCustomerBasicInfo.action' ;} ;return navTabSearch(this);" 
		action="${path}/children/erpGroupOrderInfo!getCustomerBasicInfo.action" method="post"	>
	<div class="searchBar">
		<table class="pageFormContent" id="tableId">
			<tr>
				<td>
					<label>条码：</label> 
					<input type="text" name="filter_and_code_LIKE_S" value="${filter_and_code_LIKE_S}" />
				</td>
				<td>
					<label>订单号：</label> 
					<input type="text" name="filter_and_orderNo_LIKE_S"  value="${filter_and_orderNo_LIKE_S}" />
				</td>
				<td>
					<label>订单状态：</label>
					<input type="text" status="os" name="filter_and_status_LIKE_S" value="${filter_and_status_LIKE_S}" hidden="hidden"/> 
					<select id="statusSel2">
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
				<td> </td>
			</tr>
			
			<tr>
				<td>
					<label>检测人姓名：</label>
					<input type="text" name="filter_and_name_LIKE_S"  value="${filter_and_name_LIKE_S}" />
				</td>
				<td>
					<label>监测人姓名：</label>
					<input type="text" name="filter_and_guardianName_LIKE_S"  value="${filter_and_guardianName_LIKE_S}" />
				</td>
				<td>
					<label>套餐：</label>
					<input type="text" name="filter_and_setMealName_LIKE_S" id="comboText2" value="${filter_and_setMealName_LIKE_S}" hidden="hidden"/> 
					<select id="comboSel2">
				
					</select>
				</td>
				<td> </td>
			</tr>
			<tr>
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_orderDate_GEST_T"  id="d1" style="width: 170px;" 
					class="condition" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  
					readonly="readonly" value="${filter_and_orderDate_GEST_T}" />
					<a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_orderDate_LEET_T" id="d2" style="width: 170px;" 
					class="condition" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" 
					readonly="readonly" value="${filter_and_orderDate_LEET_T}" />
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td></td>
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
		<ul class="toolBar" >
			<li style="margin-left: 10px;">
	            <web:exportExcelTag pagerFormId="pagerFindForm"
									pagerMethodName="exportBasicCustomerInfo"
									actionAllUrl="org.hpin.children.web.ErpGroupOrderInfoAction"
									informationTableId="groupOrderTable4"
									fileName="BasicCustomerInfo"></web:exportExcelTag>
			</li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="groupOrderTable4"> 
			<thead>
			<tr>
				<th width="60">序号</th>
				<th width="100" export = "true" columnEnName = "code" columnChName = "条码" nowrap="true" > 条码 </th> 
				<th width="100" export = "true" columnEnName = "name" columnChName = "姓名 " nowrap="true" > 姓名 </th> 
				<th width="50" export = "true" columnEnName = "sex" columnChName = "性别 " nowrap="true" > 性别 </th> 
				<th width="50" export = "true" columnEnName = "age" columnChName = "年龄 " nowrap="true" > 年龄 </th> 
				<th width="100" export = "true" columnEnName = "setMealName" columnChName = "检测项目 " nowrap="true" > 检测项目 </th> 
				<th width="100" export = "true" columnEnName = "guardianName" columnChName = "监护人姓名 " nowrap="true" > 监护人姓名 </th> 
				<th width="100" export = "true" columnEnName = "guardianPhone" columnChName = "手机 " nowrap="true" > 手机 </th> 
				<th width="80" export = "true" columnEnName = "relationship" columnChName = "关系" nowrap="true" > 关系 </th> 
				<th width="200" export = "true" columnEnName = "reportAddress" columnChName = "地址" nowrap="true"> 地址 </th> 
				<th width="150" export = "true" columnEnName = "familyHistory" columnChName = "备注" nowrap="true"> 家族病史 </th>
				<th width="50" export = "true" columnEnName = "height" columnChName = "备注" nowrap="true"> 身高 </th>
				<th width="50" export = "true" columnEnName = "weight" columnChName = "备注" nowrap="true"> 体重 </th>
				<th width="200" export = "true" columnEnName = "reportStatus" columnChName = "报告状态" nowrap="true"> 报告状态 </th>
				<th width="80" export = "true" columnEnName = "createDate" columnChName = "下单日期" nowrap="true"> 下单日期 </th>
				<th width="200" export = "true" columnEnName = "note" columnChName = "备注" nowrap="true" nowrap="true"> 备注 </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
					<tr target="sid_user" rel="${entity.id}" id="rows">
						<td align="center">${status.count}</td>
						<td align="center" id="createDate"> ${code} </td>
						<td align="center" id="name" title="${entity.name}"> ${entity.name} </td>
						<td align="center" id="sex"> ${entity.sex} </td>
						<td align="center" id="age"> ${entity.age} </td>
						<td align="center" id="setMealName" title="${entity.setMealName}"> ${entity.setMealName} </td>
						<td align="center" id="guardianName" title="${entity.guardianName}"> ${entity.guardianName} </td>
						<td align="center" id="guardianPhone" title="${entity.guardianPhone}"> ${entity.guardianPhone} </td>
						<td align="center" id="relationship"> ${entity.relationship} </td>
						<td align="center" id="reportAddress" title="${entity.reportAddress}"> ${entity.reportAddress} </td>
						<td align="center" id="familyHistory" title="${entity.familyHistory}"> ${entity.familyHistory}</td>
						<td align="center" id="height" title="${entity.height}"> ${entity.height}</td>
						<td align="center" id="weight" title="${entity.weight}"> ${entity.weight}</td>
						<td align="center" id="reportStatus" title="${entity.reportStatus}"> ${entity.reportStatus} </td>
						<td align="center" id="createDate" title="${entity.createDate}"> ${fn:substring(entity.createDate,0,10)} </td>
						<td align="center" id="note" title="${entity.note}"> ${entity.note}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 