<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<style>
#codeQue {
	display: none;
}
</style>
<script type="text/javascript" language="javascript" src="${path }/scripts/plugin/shieldLayer_plugin.js"></script>
<script type="text/javascript" language="javascript">
	$(function() {

		$("#button").click(function() {
			$("#codeQue").css("display", "block");
			$("#manyQue").css("display", "none");
		});

		$("#back").click(function() {
			$("#codeQue").css("display", "none");
			$("#manyQue").css("display", "block");
		});
		
/* 		$("a[class='add']").hover(){
			
		} */
	});

	//变更用于复选框
	function changeProduct() {
		var ids = '';
		var count = 0;
		var status = '';
		$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
				function(i, n) {
					ids = n.value;
					count += count + 1;
					status = $(this).parent().next().text();
				});
		if (count == 0) {
			alert('请选择你要变更的信息！');
			return;
		} else if (count > 1) {
			alert('只能选择一条信息进行变更！');
			return;
		} else {
			var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("modifyEvents",
					"../events/erpEvents!toModifyEvents.action?id=" + ids
							+ "&navTabId=" + navTabId, {
						title : "变更",
						fresh : false,
						data : {}
					});
		}
	}
	//变更用于单行
	function changeProduct(ids) {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyEvents",
				"../events/erpEvents!toModifyEvents.action?id=" + ids
						+ "&navTabId=" + navTabId, {
					title : "变更",
					fresh : false,
					data : {}
				});

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
		$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
				function(i, n) {
					ids = n.value;
					count += count + 1;
					status = $(this).parent().next().text();
				});
		if (count == 0) {
			alert('请选择要寄快递的场次！');
			return;
		} else if (count > 1) {
			alert('只能选择一条！');
			return;
		} else {
			if (confirm("确定要寄快递？")) {
				//如果是true 
				var navTabId = navTab._getTabs().filter('.selected').attr(
						'tabid');
				navTab.openTab("addExpress",
						"../events/erpExpress!toAddExpress.action?id=" + ids
								+ "&navTabId=" + navTabId, {
							title : "快递",
							fresh : false,
							data : {}
						});
			}
		}
	}
	
//弹出修改场次价格的窗口 
function toEditEventsPrice(){
		var ids = '';
		var count = 0;
		var eventsNo = '';
		$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
				function(i, n) {
					ids = n.value;
					count += count + 1;
					eventsNo = $(this).parent().next().text();
				});
		if (count == 0) {
			alertMsg.info('请选择修改价格的场次！');
			return;
		} else if (count > 1) {
			alertMsg.info('只能选择一条！');
			return;
		} 
	//获取当前页面id
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	$.pdialog.open("${path}/events/erpEvents!toEditEventsPrice.action?eventsId="+ids+"&eventsNo="+eventsNo+"&navTabId="+navTabId, "toEditEventsPrice",
			"修改【<span style='color:#8A2BE2;'>"+eventsNo+"</span>】场次价格", {width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
}

function submitForm() {
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

	/*document.form1.isBtn.value = "2";*/
	$(".pageForm", navTab.getCurrentPanel()).submit();

}

//单选事件
function changeType(i){
	if(i==1){
		$("#queryTypeId").attr("name","filter_and_batchno_EQ_S");
	}
	if(i==2){
		$("#queryTypeId").attr("name","filter_and_batchno_LIKE_S");		
	}
}

//改变点击按钮，会触发该事件
$("input[name='queryType']").change(function(){
	var radio = $("input[name='queryType']:checked").val();			    //获取选中的单选框的value
	var inputText = $("input[name='filter_and_batchno_EQ_S']").val();	//获取输入文本值
	var inputText2 = $("input[name='filter_and_batchno_LIKE_S']").val();//获取输入文本值
	//alert("text1"+inputText);
	//alert("text2"+inputText2);
	if(radio!='undefined'){
		$("input[name='hiddenRadio']").val(radio);		//赋值给隐藏input
		
		if(inputText!=null&&inputText!=""&&inputText!='undefined'){
			$("input[name='hiddenText']").val(inputText);
			//alert("text1----"+inputText);
		}
	
		if(inputText2!=null&&inputText2!=""&&inputText2!='undefined'){
			$("input[name='hiddenText']").val(inputText2);
			//alert("text2----"+inputText2);
		}
	}
});

//文本框被改变
$("input[name='filter_and_batchno_EQ_S']").change(function(){
	var radio = $("input[name='queryType']:checked").val();			    //获取选中的单选框的value
	var inputText = $("input[name='filter_and_batchno_EQ_S']").val();	//获取输入文本值
	var inputText2 = $("input[name='filter_and_batchno_LIKE_S']").val();//获取输入文本值
	//alert("text1"+inputText);
	//alert("text2"+inputText2);
	if(radio!='undefined'){
		$("input[name='hiddenRadio']").val(radio);		//赋值给隐藏input
		
		if(inputText!='undefined'){
			$("input[name='hiddenText']").val(inputText);
			//alert("text1----"+inputText);
		}
	
		if(inputText2!='undefined'){
			$("input[name='hiddenText']").val(inputText2);
			//alert("text2----"+inputText2);
		}
	}
});

//加载时默认选中单选条件和输入框显示
$(document).ready(function(){
	var hiddenRadio = $("input[name='hiddenRadio']").val();	//拿到加载页面的选中的radio值
	var hiddenText = $("input[name='hiddenText']").val();
	if(hiddenRadio!='undefined'){
		if(hiddenRadio=='2'){
			$("input[type='radio'][name='queryType'][value='2']").attr("checked",true);
			$("#queryTypeId").val(hiddenText);
			$("#queryTypeId").attr("name","filter_and_batchno_LIKE_S");
		}
	}
	
	//导出加载等待问题;
	shieldReadyNavTab(2, navTab.getCurrentPanel()); //加载js的时候就出事化 , 进度条显示初始化;
	
	
	//重置按钮单击事件;
	$("#resetDo", navTab.getCurrentPanel()).on("click", function() {
		$("input", navTab.getCurrentPanel()).val("");
		$("select", navTab.getCurrentPanel()).val("");
	});
});

function showTheMb() {
	//获取后台改界面是否存在上次未导出数据;  当前处理不管是否存在直接清空,重新下载;
	var pageType = navTab.getCurrentPanel();
	var result = dealSessionPre("events_listEvents");//(预留提示处理;)
	//if(result == "notexist") {
		//没200毫秒调用一次;当用户关闭该页面再次进入时;自动重新计算flag值;不考虑以前的;
	//	dealSessionProcess("events_listEvents", pageType);
	//	shieldShow(pageType);
		 
	//} else {
	//	 alertMsg.warn("请您耐心等待上次导出,完成后才能开始本次导出功能!");
	//	 return false;
	//}
	return true;
}

function saveQRCode(id){
	 var eventsId = id;
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		$.ajax({	//初始化页面时的省份
			type: "post",
			cache :false,
			data:{"eventsId":eventsId},
			url: "${path}/events/erpEvents!saveErpQRCode.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.status=='200'){
					alertMsg.correct(data.message);
				}else if(data.status=='300'){
					alertMsg.error(data.message);
				}
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
}
</script>

<!--蒙版 start-->
<!--必须要的部分-->
    <div class="py_theMb"><!--蒙版-->
        <div class="py_bakpic"><!--图片-->
    </div>
    </div>
    <!--必须要的部分-->
<!--蒙版 end-->

<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<div class="searchBar" id="manyQue">
		<form id="pagerFindForm" 
			onsubmit="if(this.action != '${path}/events/erpEvents!listEvents.action'){this.action = '${path}/events/erpEvents!listEvents.action' ;} ;return navTabSearch(this);"
			action="${path}/events/erpEvents!listEvents.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent" style="overflow-y:hidden">
				<tr>
					<td style="margin-top:-10px"><label>场次号：</label> <input type="text"
						name="filter_and_eventsNo_LIKE_S"
						value="${filter_and_eventsNo_LIKE_S}" /></td>
					<td><label>支公司：</label> <%-- <input type="text" name="filter_and_branchCompany_LIKE_S" value="${filter_and_branchCompany_LIKE_S}"/> --%>
						<input type="hidden" id="id"
						name="filter_and_branchCompanyId_EQ_S" bringbackname="customer.id"
						value="${filter_and_branchCompanyId_EQ_S}" /> <input type="text"
						id="branchCompany" name="aaaa"
						bringbackname="customer.branchCommanyName" value="${aaaa}"
						readonly="readonly" /> <a class="btnLook"
						href="${ path }/resource/customerRelationShip!findCustomerRelationShip.action"
						lookupGroup="customer" target="dialog" width="800" height="480">查找带回</a>
						<%-- <img src="${path}/images/clear.png" title="清除公司信息" id="customer" style="padding-top: 6px;"/> --%>
					</td>
					<td><label>所属公司：</label> <input type="hidden"	id="ownedCompanyId" name="filter_and_ownedCompanyId_EQ_S" bringbackname="dept.ownedCompany"	value="${filter_and_ownedCompanyId_EQ_S}" /> 
					<input type="text"	id="ownedCompany" name="bbbb" bringbackname="dept.customerNameSimple" value="${bbbb}" readonly="readonly" /> 
					<a class="btnLook" href="${ path }/resource/customerRelationShip!lookDept.action" lookupGroup="dept" target="dialog" width="800" height="480">查找带回</a>
						<%-- <img src="${path}/images/clear.png" title="清除公司信息" id="customer" style="padding-top: 6px;"/> --%>
					</td>
				</tr>
				<tr>
					<td><label>起始日期：</label> <input type="text" name="filter_and_eventDate_GEST_T" id="d1" style="width: 170px;"
						onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"
						readonly="readonly" value="${filter_and_eventDate_GEST_T}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<td><label>结束日期：</label> <input type="text"
						name="filter_and_eventDate_LEET_T" id="d2" style="width: 170px;"
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
						readonly="readonly" value="${filter_and_eventDate_LEET_T}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<%-- <td>
					<label>是否快递：</label> 
					<select name="filter_and_isExpress_EQ_I" rel="iselect">
						 <option value="">请选择</option> 
						 <option value=1 <c:if test="${filter_and_isExpress_EQ_I==1 }">selected="selected"</c:if>>是</option>
						 <option value=0 <c:if test="${filter_and_isExpress_EQ_I==0 }">selected="selected"</c:if>>否</option>
					</select> 
				</td> --%>
					<td>
						<label>级别：</label> 
						<%-- <input type="text" name="filter_and_level2_LIKE_S" value="${filter_and_level2_LIKE_S}"/> --%>
						<select id="level2" name="filter_and_level2_LIKE_S" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10103">
							<option value="${filter_and_level2_LIKE_S}"></option>
						</select>
					</td>
				</tr>
				<tr>

					<td><label>省：</label> <%-- <input type="text" name="filter_and_address_LIKE_S" value="${filter_and_address_LIKE_S}"/>  --%>
						<select id="province" name="filter_and_province_EQ_S"
						rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
						ref="city"
						refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
							<option value="${filter_and_province_EQ_S}"></option>
					</select></td>
					<td><label>市：</label> <select id="city"
						name="filter_and_city_EQ_S" rel="iselect">
							<option value="${filter_and_city_EQ_S}"></option>
					</select></td>
				</tr>

			</table>
		

		<table class="pageFormContent" style="overflow-y:hidden">
			<tr>
			<td style="white-space:nowrap"><label>批次号：</label> 
						<input type="text" id="queryTypeId" name="filter_and_batchno_EQ_S" value="${filter_and_batchno_EQ_S}" />
					</td>
					<td>
						<input type="hidden" name="hiddenRadio" value="${hiddenRadio}"/>
						<input type="hidden" name="hiddenText" value="${hiddenText}"/>
						<input type="radio" name="queryType" value="1" checked="checked" onclick="changeType(1)"/>精准查询
						<input type="radio" name="queryType" value="2" onclick="changeType(2)"/>模糊查询 
					</td>
					<td>
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
								<li><a id="resetDo" href="javascript:;" class="button"><span>重置</span></a></li>
								<li><div class="buttonActive"><div class="buttonContent"><button id="button" type="button">条形码查找场次</button></div></div></li>
							</ul>
						</div>
					</td>
				
			</tr>
		</table>
	</form>
	</div>
	<div class="searchBar" id="codeQue">
		<table class="pageFormContent">
			<tr>
				<td>
					<div class="buttonActive">
						<div class="buttonContent">
							<button id="back">返回</button>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<form id="pagerFindForm"
			onsubmit="if(this.action != '${path}/events/erpEvents!findEventsByCode.action'){this.action = '${path}/events/erpEvents!findEventsByCode.action' ;} ;return navTabSearch(this);"
			action="${path}/events/findEventsByCode!listEvents.action"
			method="post" rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent">
				<tr>
					<td><label>条形码：</label> <input type="text"
						name="filter_and_code_EQ_S" value="${filter_and_code_EQ_S}" /></td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查找</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="clearText">重置</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div class="pageContent">
	<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		<c:if test="${currentUser.userType!='查询用户'}">
			<ul class="toolBar">
				<%-- <web:security tag="EM_addEvents"> --%>
				<li><a class="add"	href="${path}/events/erpEvents!toAddEvents.action" target="navTab"
					rel="addEvents"><span>增加场次</span></a></li>
				<%-- </web:security> --%>
				<!-- <li><a class="edit" onclick="changeProduct2()" style="cursor:pointer;"><span>寄快递</span></a></li> -->
				<web:security tag="EM_delEvents">
				<li><a class="delete"
					href="${path}/events/erpEvents!delEvents.action" rel="ids"
					postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除场次</span></a></li>
				</web:security>
				<!-- <li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li> 
				<li><a class="add"	href="${path}/events/erpEvents!extEvents.action" target="navTab" rel="add"><span>导出</span></a>
			<form id="pagerFindForm" class='pageForm'
			onsubmit="if(this.action != '${path}/events/erpEvents!extEvents.action'){this.action = '${path}/events/erpEvents!extEvents.action' ;} ;return navTabSearch(this);"
			action="${path}/events/erpEvents!extEvents.action" method="post"
			rel="pagerForm" id="pagerFindForm">		
				<button type="button" id="clearText" onclick="submitForm()">导1出</button>
			</form>	
					</li> 
					-->
				<web:security tag="EM_mdyEventsPrice">
				<li><a class="edit" onclick="toEditEventsPrice()" style="cursor:pointer;"><span>修改场次价格</span></a></li>
				</web:security>
				
				<web:security tag="EM_exportExcel">
				<web:exportExcelTag pagerFormId="pagerFindForm"
					pagerMethodName="findByPage"
					actionAllUrl="org.hpin.events.web.ErpEventsAction"
					informationTableId="exportExcelTable" fileName="events"></web:exportExcelTag>
				</web:security>
				<%-- <li><a class="add" 
					href="${path}/events/erpCustomer!importCustomer.action"
					target="dialog" rel="add" title="导入Excel"><span>导入Excel</span></a></li> --%>
				<web:security tag="EM_failEvents">
				<li><a class="icon" 
					href="${path}/events/erpCustomer!listCustomerFail.action"
					target="navtab" rel="add" title="匹配场次失败"><span>匹配场次失败</span></a></li>
				</web:security>
			</ul>
		</c:if>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th export="true" columnEnName="eventsNo" columnChName="场次号">场次号</th>
				<th export="true" columnEnName="eventDate" columnChName="场次日期">场次日期</th>
				<!-- <th  export= "true" columnEnName = "provice" columnChName = "省" >省</th> -->
				<th export="true" columnEnName="provice" columnChName="省"
					id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
				<th export="true" columnEnName="city" columnChName="市"
					id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
				<!-- <th  export= "true" columnEnName = "city" columnChName = "市" >市</th> -->
				<th export="true" columnEnName="branchCompanyId"
					id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"
					columnChName="支公司">支公司</th>
				<th export="true" columnEnName="ownedCompanyId"
					id2NameBeanId="org.hpin.base.usermanager.dao.UserDao"
					columnChName="所属公司">所属公司</th>
				<th export="true" columnEnName="comboName" columnChName="套餐">套餐</th>
				<th export="true" columnEnName="level2" columnChName="级别"
					id2NameBeanId="org.hpin.base.dict.dao.SysDictTypeDao">级别</th>
				<th export="true" columnEnName="headcount" columnChName="预计人数">预计人数</th>
				<th export="true" columnEnName="nowHeadcount" columnChName="已录人数">已录人数</th>
				<th export="true" columnEnName="pdfcount" columnChName="已出报告数">已出报告数</th>
				<th export="true" columnEnName="nopdfcount" columnChName="未出报告数">未出报告数</th>
				<th export="true" columnEnName="batchNo" columnChName="批次号">批次号</th>
				<th export="true" columnEnName="createUserName" columnChName="创建人">创建人</th>
				<!-- <th  export = "true" columnEnName = "isExpress" columnChName = "是否快递" >是否快递</th> -->
				<c:if test="${currentUser.userType!='查询用户'}">
					<th export="false" columnEnName="" columnChName="">操作</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpEvents" varStatus="status">
				<tr target="sid_user" rel="${erpEvents.id }">
					<td align="center"><c:if
							test="${currentUser.accountName!='parkson'}">
							<input type="checkbox" name="ids" value="${erpEvents.id}">
						</c:if> ${ status.count }</td>
					<td align="center">
						<%-- <a title="场次信息" target="dialog" width="1000"  href="${path}/events/erpCustomer!toEventNoListCustomer.action?id=${erpEvents.id}">${erpEvents.eventsNo}</a> --%>
						<a title="场次信息" target="navTab" width="1000"
						href="${path}/events/erpCustomer!toEventNoListCustomer.action?id=${erpEvents.id}">${erpEvents.eventsNo}</a>
					</td>

					<td align="center">${fn:substring(erpEvents.eventDate,0,19)}</td>
					<td align="center">
						<hpin:id2nameDB
							beanId="org.hpin.base.region.dao.RegionDao"
							id="${erpEvents.provice }" />
					</td>
					<td align="center">
						<hpin:id2nameDB
							beanId="org.hpin.base.region.dao.RegionDao"
							id="${erpEvents.city }" />
					</td>
					<td align="center">
						<hpin:id2nameDB
							id="${erpEvents.branchCompanyId}"
							beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" />
					</td>
					<td align="center">
						<hpin:id2nameDB
							id="${erpEvents.branchCompanyId}"
							beanId="erpEventsDao" />
					</td>
					<td align="center">${erpEvents.comboName}</td>
					<td align="center">
						<hpin:id2nameDB id='${erpEvents.level2}'
							beanId='org.hpin.base.dict.dao.SysDictTypeDao' />
					</td>
					<td align="center">${erpEvents.headcount}</td>
					<td align="center"><a title="客户信息" tabid="_blank"
						target="navTab"
						href="${path}/events/erpCustomer!toListCustomer.action?id=${erpEvents.id}">${erpEvents.nowHeadcount}</a></td>
					<td align="center">${erpEvents.pdfcount}</td>
					<td align="center"><a title="未出报告信息" tabid="nopdfcount"
						target="navTab"
						href="${path}/events/erpCustomer!toListCustomerNoReport.action?id=${erpEvents.id}">${erpEvents.nopdfcount}</a></td>
					<td align="center">${erpEvents.batchNo}</td>
					<td align="center">${erpEvents.createUserName}</td>
					<%-- <td align="center"><c:if test="${erpEvents.isExpress==1}">是</c:if><c:if test="${erpEvents.isExpress==0}">否</c:if></td> --%>
					<c:if test="${currentUser.userType!='查询用户'}">
					
						<td align="center">
							<c:if test="${erpEvents.isDeleted!=2 }">
							<div class="panelBar" style="width:250px;">
								<ul class="toolBar" >
									<li><a class="add"
										href="${path}/events/erpCustomer!toAddCustomer.action?eventsNo=${erpEvents.id}"
										target="navTab" rel="add" title="增加客户"><span>增加客户</span></a></li>
									<li><a class="edit"
										onclick="changeProduct('${erpEvents.id}')"
										style="cursor:pointer;" title="变更场次"><span>变更场次</span></a></li>
									<%-- <li><a class="delete" href="${path}/events/erpEvents!delOneEvents.action?id=${erpEvents.id}"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li> --%>
									<li><a class="add"
										href="${path}/events/erpCustomer!importCustomerOriginal.action?id=${erpEvents.id}"
										target="navTab" rel="add"><span>导入</span></a></li>
									<%-- <li><a class="icon" onclick="saveQRCode('${erpEvents.id}')"
										href="javascript:;"><span>生成二维码</span></a></li> --%>
                        		</ul>
							</div>
                        	</c:if>
                       		<c:if test="${erpEvents.isDeleted==2 }">
                       		场次已取消
                       		</c:if>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
