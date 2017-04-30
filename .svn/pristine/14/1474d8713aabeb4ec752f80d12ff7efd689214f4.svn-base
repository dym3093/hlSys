<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript" src="${path }/scripts/plugin/shieldLayer_plugin.js"></script>
<script type="text/javascript" language="javascript">
/*
 *　遮蔽层显示/隐藏;
 * create by henry.xu 20160921;
 */
//1.加载
shieldReadyNavTab(2, navTab.getCurrentPanel()); //加载js的时候就出事化.不知道为毛在$(function());中不能初始化?待解决;
//2.单机导出进入后台查看数据;指定当前页面标示; report_eventsSalesman 当前操作类表加上界面加上累加数字;
//注意该showTheMb()方法在java中处理生成;
function showTheMb() {
	//获取后台改界面是否存在上次未导出数据;  当前处理不管是否存在直接清空,重新下载;
	var pageType = navTab.getCurrentPanel();
	var result = dealSessionPre("report_eventsSalesman");//(预留提示处理;)
	//if(result == "notexist") {
		//没200毫秒调用一次;当用户关闭该页面再次进入时;自动重新计算flag值;不考虑以前的;
		//dealSessionProcess("report_eventsSalesman", pageType);
		//shieldShow(pageType);
		 
	//} else {
	//	 alertMsg.warn("请您耐心等待上次导出,完成后才能开始本次导出功能!");
	//	 return false;
	//}
	return true;
	
}

//变更用于复选框
function changeProduct() {
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择你要变更的信息！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息进行变更！');
		return ;		
	}else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyEvents", "../events/erpEvents!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	}
}
//变更用于单行
function changeProduct(ids) {
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
    navTab.openTab("modifyEvents", "../events/erpEvents!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	
}
//删除用于单行
/*function changeProduct(ids) {
	if(confirm("确定要删除？")){ {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyEvents", "../events/erpEvents!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	}
}
	*/
//寄快递
function changeProduct2() {
	var ids = '';
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
	}
}
	
//解决分页条件未带过去的bug
var queryType = '${queryType}';
if(queryType==null||queryType==''||queryType=='undefined'||queryType==undefined){
	$("input[name='queryType'][value='1']").attr("checked","checked");
}else{
	$("input:radio[value='"+queryType+"']").attr('checked','true');
}

function clearInput(){
	$(':input','#pagerFindFormListEventsSales',navTab.getCurrentPanel())  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('selected');  
}

</script>
<style type="text/css">
	.pageFormContent td{padding-right:0px !important;}
</style>

<!--蒙版 start-->
<!--必须要的部分-->
<div class="py_theMb"><!--蒙版-->
    <div class="py_bakpic"></div><!--图片-->
</div>
<!--必须要的部分-->
<!--蒙版 end-->


<div class="tip"><span onclick="shieldShow('navTab')">查询</span></div>
<div class="pageHeader">
	<form id="pagerFindFormListEventsSales" onsubmit="if(this.action != '${path}/events/erpEvents!listEventsSalesman.action'){this.action = '${path}/events/erpEvents!listEventsSalesman.action' ;} ;return navTabSearch(this);" action="${path}/events/erpEvents!listEventsSalesman.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>场次号：</label> 
					<input type="text" name="filter_and_eventsNo_LIKE_S" value="${filter_and_eventsNo_LIKE_S}"/>
				</td>
				<td>
					<label>支公司：</label> 
					<%-- <input type="text" name="filter_and_branchCompany_LIKE_S" value="${filter_and_branchCompany_LIKE_S}"/> --%>
					<input type="hidden" id="id" name="filter_and_branchCompanyId_EQ_S" bringbackname="customer.id" value="${filter_and_branchCompanyId_EQ_S}" />
					<input type="text" id="branchCompany" name="aaaa" bringbackname="customer.branchCommanyName" value="${aaaa} " readonly="readonly"/>
					<a class="btnLook" href="${path}/resource/customerRelationShip!findCustomerRelationShip.action" lookupGroup="customer"  target="dialog" width="800" height="480">查找带回</a>
				</td>
				
				<td>
					<label>级别：</label> 
					<%-- <input type="text" name="filter_and_level2_LIKE_S" value="${filter_and_level2_LIKE_S}"/> --%>
					<select id="level2" name="filter_and_level2_LIKE_S" rel="iselect"  loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10103">
						<option value="${filter_and_level2_LIKE_S}"></option>
					</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_eventDate_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_eventDate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_eventDate_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_eventDate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<%-- <td>
					<label>是否快递：</label> 
					<select name="filter_and_isExpress_EQ_I" rel="iselect">
						 <option value="">请选择</option> 
						 <option value=1 <c:if test="${filter_and_isExpress_EQ_I==1 }">selected="selected"</c:if>>是</option>
						 <option value=0 <c:if test="${filter_and_isExpress_EQ_I==0 }">selected="selected"</c:if>>否</option>
					</select> 
				</td> --%>
				<td>
				<label>批次号：</label> 
					<input type="text" name="filter_and_batchno_EQ_S" value="${filter_and_batchno_EQ_S}"/>
				</td>
				<td>
					<input type="radio" name="queryType" value="1" checked="checked"/>精准查询
					<input type="radio" name="queryType" value="2" />模糊查询 
				</td>
			</tr>
			<tr>

				<td>
					<label>省：</label> 
					 <%-- <input type="text" name="filter_and_address_LIKE_S" value="${filter_and_address_LIKE_S}"/>  --%>
					 <select id="province" name="filter_and_province_EQ_S" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
		 			    <option value="${filter_and_province_EQ_S}"></option> 
		 			 </select>
				</td>
				<td>
					<label>市：</label>
					<select id="city" name="filter_and_city_EQ_S" rel="iselect">
						    <option value="${filter_and_city_EQ_S}"></option> 
         			</select>
         			
         		</td>
         		
         		
         		<td>
         		<label>
         		    <div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearInput()">重置</button></div></div>
				</label>
				</td>
				
				
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
		<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		<ul class="toolBar">
			<!-- <li><a class="add" href="${path}/events/erpEvents!toAddEvents.action"  target="navTab" rel="add"><span>增加场次</span></a></li>
			<li><a class="edit" onclick="changeProduct2()" style="cursor:pointer;"><span>寄快递</span></a></li> 
			<li><a class="delete" href="${path}/events/erpEvents!delEvents.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除场次</span></a></li>
			 <li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
			--> 
		 <web:exportExcelTag pagerFormId="pagerFindFormListEventsSales" 
								pagerMethodName="findByPageExcel" 
								actionAllUrl="org.hpin.events.web.ErpEventsAction" 
								informationTableId="exportExcelTable"
								fileName="events"></web:exportExcelTag> 
		
		</ul>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "eventsNo" columnChName = "场次号" >场次号</th>
				<th  export= "true" columnEnName = "eventDate" columnChName = "场次日期" >场次日期</th>
				<!-- <th  export= "true" columnEnName = "provice" columnChName = "省" >省</th> -->
				<th  export= "true" columnEnName = "provice" columnChName = "省" id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
				<th  export= "true" columnEnName = "city" columnChName = "市" id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
				<!-- <th  export= "true" columnEnName = "city" columnChName = "市" >市</th> -->
				<th  export = "true" columnEnName = "branchCompanyId" id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "ownedCompanyId" id2NameBeanId="org.hpin.base.usermanager.dao.UserDao"  columnChName = "所属公司" >所属公司</th>
				<th  export = "true" columnEnName = "comboName" columnChName = "套餐" >套餐</th>
				<th  export = "true" columnEnName = "level2" columnChName = "级别" id2NameBeanId="org.hpin.base.dict.dao.SysDictTypeDao">级别</th>
				<th  export = "true" columnEnName = "headcount" columnChName = "预计人数">预计人数</th>
				<th  export = "true" columnEnName = "nowHeadcount" columnChName = "已录人数" >已录人数</th>
				<th  export = "true" columnEnName = "pdfcount" columnChName = "已出报告数" >已出报告数</th>
				<th  export = "false" columnEnName = "" columnChName = "未出报告数" >未出报告数</th>
				<th  export = "true" columnEnName = "batchNo" columnChName = "批次号" >批次号</th>
				<!-- <th  export = "true" columnEnName = "isExpress" columnChName = "是否快递" >是否快递</th> -->
				<!-- <th  export = "false" columnEnName = "" columnChName = "" >操作</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpEvents" varStatus="status">
					<tr target="sid_user" rel="${erpEvents.id }">
						<td align="center">
								<%-- <input type="checkbox" name="ids" value="${erpEvents.id}"> --%>
							${ status.count }
						</td>
						<td align="center">
							<%-- <a title="场次信息" target="dialog" width="1000"  href="${path}/events/erpCustomer!toEventNoListCustomer.action?id=${erpEvents.id}">${erpEvents.eventsNo}</a> --%>
							<a title="场次信息" target="navTab" width="1000"  href="${path}/events/erpCustomer!toEventNoListCustomerSalesman.action?id=${erpEvents.id}">${erpEvents.eventsNo}</a>
						</td>

						<td align="center">	${fn:substring(erpEvents.eventDate,0,19)}</td>
						<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpEvents.provice }"/></td>
						<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpEvents.city }"/></td>
						<td align="center"><hpin:id2nameDB id="${erpEvents.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
						<td align="center">	<hpin:id2nameDB id="${erpEvents.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao"/></td>
						<td align="center">	${erpEvents.comboName}</td>
						<td align="center">	<hpin:id2nameDB id='${erpEvents.level2}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/></td>
						<td align="center">	${erpEvents.headcount}</td>
						<td align="center">	<a title="客户信息" tabid="_blank" target="navTab" href="${path}/events/erpCustomer!toListCustomerSalesman.action?id=${erpEvents.id}">${erpEvents.nowHeadcount}</a></td>
						<td align="center">	${erpEvents.pdfcount}</td>
						<td align="center"> <a title="未出报告信息" tabid="_blank" target="navTab" href="${path}/events/erpCustomer!toListCustomerNoReportSalesman.action?id=${erpEvents.id}">${erpEvents.nowHeadcount-erpEvents.pdfcount}</a></td>
						<td align="center">	${erpEvents.batchNo}</td>
						<%-- <td align="center"><c:if test="${erpEvents.isExpress==1}">是</c:if><c:if test="${erpEvents.isExpress==0}">否</c:if></td> --%>
							<%-- <td align="center">
							<div class="panelBar">
								<ul class="toolBar">
									<li>
										<a class="add" href="${path}/events/erpCustomer!toAddCustomer.action?eventsNo=${erpEvents.id}" target="navTab" rel="add"><span>增加客户</span></a>
									</li>
									<li><a class="edit" onclick="changeProduct('${erpEvents.id}')" style="cursor:pointer;"><span>变更场次</span></a></li>
									<li><a class="delete" href="${path}/events/erpEvents!delOneEvents.action?id=${erpEvents.id}"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
									<li><a class="add" href="${path}/events/erpCustomer!importCustomer.action?id=${erpEvents.id}" target="navTab" rel="add"><span>导入</span></a></li>
								</ul>
								</div>
							</td> --%>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 