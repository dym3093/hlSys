<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<style>
.press {
	display:block;
	line-height:1.5em;
	overflow:visible;
	font-size:22px;
	text-shadow:#f3f3f3 1px 1px 0px, #b2b2b2 1px 2px 0;
	text-align:center;
}
</style>
<script type="text/javascript" language="javascript">
function changeExpress() {
		if(confirm("确定要寄快递？")){
			var ids=$(".eventsNo").val();
			//如果是true 
			var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("addExpress", "../events/erpCustomer!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
		}
}
function edit(ids) {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyEvents", "../events/erpCustomer!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
}

function changeProduct3(ids) {
	/*var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择要寄快递的场次！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条！');
		return ;		
	}else {
		if(confirm("确定要寄快递？")){
			 //如果是true 
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("addExpress", "../events/erpExpress!toAddExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
		}
	}*/
	//if(confirm("确定要寄快递？")){
		 //如果是true 
	/*var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("addExpress", "../events/erpExpress!addExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
	*/
		var obj=document.getElementById("expressid").style.display;
	if(obj=="none") {
		$("#expressid",navTab.getCurrentPanel()).show();
	}else{
		$("#expressid",navTab.getCurrentPanel()).hide();
	}	
	//}
}
function changeProduct4(ids) {
	/*var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择要寄快递的场次！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条！');
		return ;		
	}else {
		if(confirm("确定要寄快递？")){
			 //如果是true 
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("addExpress", "../events/erpExpress!toAddExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
		}
	}*/
	if(confirm("确定要寄快递？")){
		 //如果是true 
	/*var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("addExpress", "../events/erpExpress!addExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
	*/
		//document.getElementById("expressid").style.display='block';
	var o=document.getElementById("expressid").style.display;
	alert(o);
	if(o=="none") {
		$("#expressid",navTab.getCurrentPanel()).show();
	}else{
		$("#expressid",navTab.getCurrentPanel()).hide();
	}	
	}
}
function submitForm(){
	/*var flag = false;
	$(".required",navTab.getCurrentPanel()).each(function(){
		if($(this).val()==""){
			$(this).focus();
			flag = true;
		}
	});
	
	if(flag){
		alert("您有必选项没有填写请确认");
		return false;
	}*/
		document.form2.isBtn.value="2";
		$(".pageFormend",navTab.getCurrentPanel()).submit();

}
</script>
	
    
<div class="pageFormContent" layoutH="0">
<div class="pageContent" style="overflow: hidden">
	<h1 class="press">${events.eventsNo}场次信息详情</h1>
	<div class="divider"></div>
	 <div class="tip"><span>场次信息</span></div>
		<table>
			<tr>
				<td><label>场次号：</label></td>
				<td>${events.eventsNo }</td>
				<td>&nbsp;</td>
				<td><label>场次日期：</label></td>
				<td>${fn:substring(events.eventDate,0,19)}</td>
			</tr>
			<tr>
				<td><label>场次地址：</label></td>
				<td>${events.address}</td>
				<td>&nbsp;</td>
				<td><label>支公司：</label></td>
				<td>${events.branchCompany }</td>
			</tr>
			<tr>
				<td><label>总公司：</label></td>
				<td>${events.ownedCompany }</td>
				<td>&nbsp;</td>
				<td><label>级别：</label></td>
				<td><hpin:id2nameDB id='${events.level2 }' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/></td>
			</tr>
			<tr>
				<td><label>预定人数：</label></td>
				<td>${events.headcount }</td>
				<td>&nbsp;</td>
				<td><label>录入人数：</label></td>
				<td>${nowHeadcount }</td>
			</tr>
			<tr>
				<td><label>报告人数：</label></td>
				<td>${pdfcount }</td>
				<td>&nbsp;</td>
				<td><label>入门礼检测餐套：</label></td>
				<td>${events.comboName}</td>
			</tr>
		</table>
	      
<div class="tip"><span>客户信息清单</span></div>
	<form rel="pagerForm" id="pagerFindForms" onsubmit="if(this.action != '${path}/events/erpCustomer!toEventNoListCustomerSalesman.action'){this.action = '${path}/events/erpCustomer!toEventNoListCustomerSalesman.action' ;} ;return navTabSearch(this);" action="${path}/events/erpCustomer!toEventNoListCustomerSalesman.action" method="post">
		<input type="hidden" name="filter_and_eventsNo_EQ_S" value="${events.eventsNo}"/>
		<input type="hidden" name="id" value="${events.id}"/>
		 <input name="customer.eventsNo" class="eventsNo" type="hidden"  value="${events.eventsNo }" />
	</form>
		<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		 <ul class="toolBar">
		 <%-- <li><a class="add" href="${path }events/erpExpress!toAddExpress.action?id=${events.id}" target="dialog"   style="cursor:pointer;"><span>寄快递</span></a></li>
			<li><a class="add" href="${path}/events/erpCustomer!toAddCustomer.action?eventsNo=${events.id}" target="navTab" rel="add"><span>添加</span></a></li>
			<li><a class="delete" href="${path}/events/erpCustomer!delCustomer.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
			<li><a class="add" href="${path}/events/erpCustomer!importCustomer.action?id=${events.id}" target="navTab" rel="add"><span>导入</span></a></li>
			--%>
			
		 <web:exportExcelTag pagerFormId="pagerFindForms" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.events.web.ErpCustomerAction" 
								informationTableId="exportExcelTables"
								fileName="erpCustomerAction"></web:exportExcelTag>  
		</ul>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="98%" layoutH="280" id="exportExcelTables"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<!-- <th  export = "true" columnEnName = "eventsNo" columnChName = "场次号" >场次号</th> -->
				<th  export= "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th  export = "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<!-- <th  export = "true" columnEnName = "sex" columnChName = "性别" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">性别</th> -->
				<th  export = "true" columnEnName = "sex" columnChName = "性别" >性别</th>
				<th  export = "true" columnEnName = "age" columnChName = "年龄" >年龄</th>
				<th  export = "true" columnEnName = "branchCompany" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "setmealName" columnChName = "套餐名" >套餐名</th>
				<!-- <th  export = "true" columnEnName = "sampleType" columnChName = "样本类型" id2NameBeanId="org.hpin.base.dict.dao.SysDictTypeDao">样本类型</th> -->
				<th  export = "true" columnEnName = "samplingDate" columnChName = "采样日期" >采样日期</th>
				<th  export = "true" columnEnName = "idno" columnChName = "身份证号" >身份证号</th>
				<th  export = "true" columnEnName = "salesMan" columnChName = "营销员" >营销员</th>
								<th  export = "true" columnEnName = "salesManNo" columnChName = "营销员工号" >营销员工号</th>
				<th  export = "true" columnEnName = "department" columnChName = "部门" >部门</th>
				<th  export = "false" columnEnName = "pdffilepath" columnChName = "PDF报告状态" >PDF报告状态</th>
				<th  export = "true" columnEnName = "note" columnChName = "备注" >备注</th>
				<!-- <th  export = "false" columnEnName = "" columnChName = "" >操作</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpCustomer" varStatus="status">
					<tr target="sid_user" rel="${erpCustomer.id }">
						<td align="center">
							<!--  <input type="checkbox" name="ids" value="${erpCustomer.id}"> -->
						${ status.count }</td>
						<%-- <td align="center">	${erpCustomer.eventsNo}</td> --%>
						<td align="center">	${erpCustomer.code}</td>
						<td align="center">	${erpCustomer.name}</td>
						<td align="center">	
						<%-- <hpin:id2nameDB id='${erpCustomer.sex}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/> --%>
						${erpCustomer.sex}
						</td>
						<td align="center">	${erpCustomer.age}</td>
						<td align="center">	${erpCustomer.branchCompany}</td>
						<td align="center">	${erpCustomer.setmealName}</td>
						<td align="center">	${fn:substring(erpCustomer.samplingDate,0,14)}00:00</td>
						<td align="center">	${erpCustomer.idno}</td>
						<td align="center">${erpCustomer.salesMan }</td>
						<td align="center">	${erpCustomer.salesManNo}</td>
						<td align="center">	${erpCustomer.department}</td>
						<td align="center">	
							<c:if test="${fn:length(erpCustomer.pdffilepath)<53}">
								<span  style="color:red">没有报告</span>
							</c:if>
							<c:if test="${fn:length(erpCustomer.pdffilepath)>53}">
								有报告
							</c:if>
						</td>
						<td align="center">	${erpCustomer.note}</td>
						<%-- <td align="center">	
							<div class="panelBar">
								 <ul class="toolBar">
								 	<li><a class="edit" onclick="edit('${erpCustomer.id}')" style="cursor:pointer;"><span>变更</span></a></li>
								 	<li><a class="delete" href="${path}/events/erpCustomer!delOneCustomer.action?id=${erpCustomer.id}"  postType="string" title="确定要删除吗?" target="navTab"><span>删除</span></a></li>
								 </ul>
							</div>
						</td> --%>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 
</div>
