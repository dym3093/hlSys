<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">
	$(document).ready(function(){
		findEventInfoByNo($("#eventsId").val());
		//当场次号改变的时候  联动显示 批次号  批次日期，录入人数
		$("#eventsId").bind("change",function(){
			var eventsId = $(this).val();
			findEventInfoByNo(eventsId);
		})
		$("input[name='idss']").bind("change",function (){
			var flag = $(this).is(':checked');
			var customerId =  $(this).val();
			var reportCode = "${reportCode}";
			$.ajax({
				type: "post",
				cache :false,
				dateType:"json",
				url: "${path}/reportdetail/reportDelivery!saveOrDelTemp.action",
				data:{"c_reportCode":reportCode,"c_customerId":customerId,"c_flag":flag},
				success: function(data){
					eval("data = "+data);
					if(data.status=='200'){
					}else if(data.status=='300'){
						alertMsg.error(data.msg);
					}
				},
				error :function(){
					alertMsg.error("服务发生异常，请稍后再试！");
					return;
				}
			});
		})
		//切换查询条件渲染页面之后 将已选择的展示出来
		rederCheckbox();
	})
	function rederCheckbox(){
		var reportCode = "${reportCode }";
		$.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/reportdetail/reportDelivery!findCustomerInfo.action",
			data:{"reportCode":reportCode},
			success: function(data){
				eval("data = "+data);
				if(data.status=='200'){
					var arrIds = data.customerIds.split(",");
					for(var i in arrIds){
						$("input[name='idss'][value='"+arrIds[i]+"']").attr("checked","checked");
					}
				}else if(data.status=='300'){
					alertMsg.error(data.msg);
				}
			},
			error :function(){
				alertMsg.error("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	
	function findEventInfoByNo(eventsId){
		$.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/reportdetail/reportDelivery!findEventInfoByNo.action",
			data:{"eventID":eventsId},
			success: function(data){
				eval("data = "+data);
				$("#batchNo").text(data.batchno);
				$("#eventDate").text(data.eventDate);
				$("#customerNum").text(data.customerNum);
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	function expressEntryCustomer(){
		var reportCode = "${reportCode }";
		$.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/reportdetail/reportDelivery!findRepeatExpressByReportCode.action",
			data:{"reportCode":reportCode},
			success: function(data){
				eval("data = "+data);
				if(data.status=='200'){
					if(data.msg!=""&&data.msg!=undefined){
						alertMsg.error(data.msg);
						return;
					}
					if(data.flag){
						alertMsg.confirm('<span style="color:#FF0000;">包含已寄送报告的客户</span>', {
					        okCall: function(){
								$.pdialog.open("${path}/reportdetail/reportDelivery!toExpressEntry.action?reportCode="+reportCode, 
									"expressEntry", "快递录入",{width:750,height:240,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
					        }
						})
					}else{
						$.pdialog.open("${path}/reportdetail/reportDelivery!toExpressEntry.action?reportCode="+reportCode, 
								"expressEntry", "快递录入",{width:750,height:240,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
					}
				}else if(data.status=='300'){
					alert(data.message);
				}
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		})
	}
	function selectAll(allBox){
		var checkBoxs = $("input[name='idss']");
		for(var i=0; i < checkBoxs.length;i++){
			if($(allBox).is(':checked')){
				$(checkBoxs[i]).attr("checked","checked");
			}else{
				$(checkBoxs[i]).attr("checked",false);
			}
			$(checkBoxs[i]).trigger("change");
		}
		
	}
	//导出excel
	function exportExcelForReport(){
		var eventsIds = "";
		var reportType = $("#reportType").val();
		var dept = $("#dept").val();
		var comboName = $("#comboName").val();
		if($("#eventsId").val()!=""){
			eventsIds = $("#eventsId").val();
		}else{
			eventsIds = '${ids}';
		}
		$.ajax({
			type: "post",
			cache: false,
			data:{"eventsIds":eventsIds,"reportType":reportType,"dept":dept,"comboName":comboName},
			url: "../reportdetail/reportDelivery!exportExcelForReport.action",
			success: function(data){
				if(data==null||data==""){
					alertMsg.error("下载失败，请检查服务器！");
					return;
				}
				var obj=eval("("+data+")");
				if(obj.error){
					alertMsg.error(obj.error);
					return;
				}
				window.location.href=obj.path;
			},
			error:function(){
				alertMsg.error("下载失败，请检查服务器！");
				return;
			}
		});
	}
</script>
	
<div class="tip">
	<span>场次信息</span>
</div>    
<div class="pageHeader">
	<div class="searchBar" id="manyQue">
		<form id="pagerFindForm" 
			onsubmit="if(this.action != '${path}/reportdetail/reportDelivery!toBatchDeal.action'){this.action = '${path}/reportdetail/reportDelivery!toBatchDeal.action' ;} ;return navTabSearch(this);"
			action="${path}/reportdetail/reportDelivery!toBatchDeal.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent"  style="overflow-y:hidden">
				<tr>
					<td><label>批次号：</label></td>
					<td><span id="batchNo"></span></td>
					<td><label>批次日期：</label></td>
					<td><span id="eventDate"></span></td>
					<td><label>录入人数：</label></td>
					<td><span id="customerNum"></span></td>
				</tr>
				<tr>
   	 				<input name="ids" type="hidden"  value="${ids }" />
   	 				<input name="reportCode" type="hidden"  value="${reportCode }" />
					<td><label>场次号：</label></td>
					<td>
						<select id="eventsId" name="eventsId" style="margin:5px;width:193px;">
							<option value="">---请选择---</option>
							<c:forEach items="${erpEventsList}" var="event">
								<option value='${event.id}' <c:if test="${event.id == eventsId || event.id == ids}">selected</c:if> >${event.eventsNo}</option>
							</c:forEach>
						</select>
					</td>
					<td><label>部门：</label></td>
					<td>
						<select id="dept" name="dept" style="margin:5px;width:193px;">
							<option value="">---请选择---</option>
							<c:forEach items="${links}" var="link">
								<option value='${link.department}' <c:if test="${link.department == dept}">selected</c:if>>${link.department}</option>
							</c:forEach>
						</select>
					</td>
					<td><label>客户状态：</label></td>
					<td>
					<select id="reportType" name="reportType"  style="margin:5px;width:193px;">
						<option value="" >---请选择---</option>
						<option value="110" <c:if test="${reportType == '110'}">selected</c:if> >采样盒已寄出</option>
						<option value="150" <c:if test="${reportType == '150'}">selected</c:if> >样本已获取</option>
						<option value="200" <c:if test="${reportType == '200'}">selected</c:if> >样本已送检</option>
						<option value="300" <c:if test="${reportType == '300'}">selected</c:if> >电子报告已出具</option>
						<option value="400" <c:if test="${reportType == '400'}">selected</c:if> >报告已下载</option>
						<option value="500" <c:if test="${reportType == '500'}">selected</c:if> >报告已打印</option>
						<option value="600" <c:if test="${reportType == '600'}">selected</c:if> >报告已寄送</option>
					</select>
					</td>
				</tr>
				<tr>
					<td><label>检测套餐：</label></td>
					<td>
					<select id="comboName" name="comboName"  style="margin:5px;width:193px;">
						<option value="" >---请选择---</option>
						<c:forEach items="${combos}" var="combo">
								<option value='${combo.combo}' <c:if test="${combo.combo == comboName}">selected</c:if>>${combo.combo}</option>
							</c:forEach>
					</select>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td>
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
								<!-- <li><a id="clearText" href="javascript:;" class="button"><span>重置</span></a></li> -->
							</ul>
						</div>
					</td>
				</tr>
			</table>
	</form>
	</div>
</div>
<div class="pageContent">
<c:if test="${currentUser.userType!='查询用户'}">
<div class="formBar" >
 <ul>
       <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button onclick="expressEntryCustomer()">录入快递信息</button>
            </div>
          </div>
        </li>
         
      </ul>
<p></p>
</div>
</c:if>
	

<div class="tip"><span>客户信息清单</span></div>
		<div class="panelBar">
		<c:if test="${currentUser.userType!='查询用户'||currentUser.accountName=='data'}">
		 <ul class="toolBar">
          <li>
          	 <a class="edit" href="javascript:;"
				title="导出表格" onclick="exportExcelForReport()"><span>导出Excel</span></a></li>
          </li>
		</ul>
		</c:if>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="98%" layoutH="200" id="exportExcelTables"> 
			<thead>
			<tr>
				<th width="50" nowrap="true">全选<input type="checkbox" checked="checked" class="checkboxCtrl" onclick="selectAll(this)"/></th>
				<th  export= "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th  export = "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th  export = "true" columnEnName = "comboName" columnChName = "套餐名称" >套餐名称</th>
				<th  export = "true" columnEnName = "brachCommany" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "dept" columnChName = "部门" >部门</th>
				<th  export = "true" columnEnName = "expressNo" columnChName = "快递单号" >快递单号</th>
				<th  export = "true" columnEnName = "expressDate" columnChName = "寄送日期" >寄送日期</th>
				<th  export = "true" columnEnName = "reportType" columnChName = "客户状态" >客户状态</th>
				<th  export = "true" columnEnName = "note" columnChName = "备注" >备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpReportCustomer" varStatus="status">
					<tr target="sid_user" rel="${erpReportCustomer.id }">
						<td align="center">
							<input type="checkbox" name="idss" value="${erpReportCustomer.id}">
							${ status.count }
						</td>
						<td align="center">	${erpReportCustomer.code}</td>
						<td align="center">	${erpReportCustomer.name}</td>
						<td align="center">	${erpReportCustomer.comboName}</td>
						<td align="center">	${erpReportCustomer.brachCommany}</td>
						<td align="center">	${erpReportCustomer.dept}</td>
						<td align="center"> ${erpReportCustomer.expressNo}</td>
						<td align="center">	${fn:substring(erpReportCustomer.expressDate,0,19)}</td>
						<td align="center">	
							<c:if test="${erpReportCustomer.reportType == '110'}">采样盒已寄出</c:if>
							<c:if test="${erpReportCustomer.reportType == '150'}">样本已获取</c:if>
							<c:if test="${erpReportCustomer.reportType == '200'}">样本已送检</c:if>
							<c:if test="${erpReportCustomer.reportType == '300'}">电子报告已出具</c:if>
							<c:if test="${erpReportCustomer.reportType == '400'}">报告已下载</c:if>
							<c:if test="${erpReportCustomer.reportType == '500'}">报告已打印</c:if>
							<c:if test="${erpReportCustomer.reportType == '600'}">报告已寄送</c:if>
						</td>
						<td align="center">	${erpReportCustomer.note}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 
</div>
