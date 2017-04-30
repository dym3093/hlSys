<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/sampleExpressMgr!listCustomers.action?navTabId=${parentNavTabId}')
		{this.action = '${path}/events/sampleExpressMgr!listCustomers.action?navTabId=${parentNavTabId}' ;}; return dialogSearch(this);" 
		action="${path}/events/sampleExpressMgr!listCustomers.action?navTabId=${parentNavTabId}" method="post"  rel="pagerForm">
		
	<input name="parentNavTabIdCusHidden" type="hidden" value="${parentNavTabId}" />
	<input name="sampleExpMgrIdCusHidden" type="hidden" value="${sampleExpMgrId}" />
	<input name="expressNumCusHidden" type="hidden" value="${expressNum}" />
	<input name="expressCompanyIdCusHidden" type="hidden" value="${expressCompanyId}" />
	<input name="weightCusHidden" type="hidden" value="${weight}" />
	<input name="totalCostCusHidden" type="hidden" value="${totalCost}" />
	<input name="receiveSendDateCusHidden" type="hidden" value="${receiveSendDate}" />
	<input name="isbillCusHidden" type="hidden" value="${isbill}" />
	<input name="receiveSendTypeCusHidden" type="hidden" value="${receiveSendType}" />
	<input name="expressContentCusHidden" type="hidden" value="${expressContent}" />
	<input name="rootNavTabId" type="hidden" value="${rootNavTabId}" />
	
	<div class="searchBar">
		<table class="searchContent">
			<tbody>
				<tr>
					<td>
						<label>姓名：</label>
						<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_EQ_S}" />
					</td>
					<td>
						<label>条形码：</label>
						<input type="text" name="filter_and_code_EQ_S" value="${filter_and_code_EQ_S}" />
					</td>
					<td>
						<label>手机号：</label>
						<input type="text" name="filter_and_phone_EQ_S" value="${filter_and_phone_EQ_S}" />
					</td>
					<td>
						<label>身份证号：</label>
						<input type="text" name="filter_and_idno_EQ_S" value="${filter_and_idno_EQ_S}" >
					</td>
				</tr>
				<tr>
					<td>
					<label>条形码：</label>
					<input type="text" id="queryCodes" name="filter_and_code_IN_S" value="${codes}" readonly="readonly" />
					<%-- <c:choose>
						<c:when test="${fn:contains(codes,',')}">
							<input type="text" id="queryCodes" name="filter_and_code_IN_S" value="${codes}" readonly="readonly" />
						</c:when>
						<c:otherwise>
							<input type="text" id="queryCodes" name="filter_and_code_LIKE_S" value="${codes}" readonly="readonly" />
						</c:otherwise>
					</c:choose> --%>
					</td>
					<td>
						<input type="file" id="affix" name="affix"/>
						<input type="button" onclick="importMoreCodes()" value="导入条码">
					</td>
					<td></td>
					<td>
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button id="addByCurResetBtn" type="button">重置</button></div></div></li>
							</ul>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	</form>
</div>

<div class="pageContent j-resizeGrid">
	<div class="panelBar">
		<ul class="toolBar">
			<li class=""><a id="addCustomerBtn" class="edit" href="javascript:void(0);" onclick="saveByCustomer();"><span>确认添加</span></a></li>
		</ul>
		<jsp:include page="/common/paginationDialog.jsp" />
	</div>
		<table class="list" style="width:100%; overflow: auto" layoutH="108">
			<thead>
				<tr>
					<th style="width: 45px; ">序号</th>
					<th style="width: 200px; ">批次号</th>
					<th style="width: 200px; ">场次号</th>
					<th style="width: 200px; ">场次日期</th>
					<th style="width: 200px; ">条码</th>
					<th style="width: 200px; ">姓名</th>
					<th style="width: 200px; ">性别</th>
					<th style="width: 200px; ">年龄</th>
					<th style="width: 200px; ">套餐</th>
					<th style="width: 200px; ">支公司</th>
					<th style="width: 200px; ">所属公司</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="erpCustomer" varStatus="status">
					<tr target="sid_user" rel="${erpCustomer.id }">
						<td align="left"><input type="checkbox" name="ids" value="${erpCustomer.id}">${ status.count }</td>
						<td align="center">${erpCustomer.batchno}</td>
						<td align="center">${erpCustomer.eventsNo}</td>
						<td align="center">${fn:substring(erpCustomer.eventsDate,0,14)}00:00</td>
						<td align="center">${erpCustomer.code}</td>
						<td align="center">${erpCustomer.name}</td>
						<td align="center">${erpCustomer.sex}</td>
						<td align="center">${erpCustomer.age}</td>
						<td align="center">${erpCustomer.setmealName}</td>
						<td align="center"><hpin:id2nameDB id="${erpCustomer.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
						<td align="center"><hpin:id2nameDB id="${erpCustomer.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

</div>

	</div>
</div>

<script type="text/javascript">
$(function(){
	$("#addByCurResetBtn").click(function(){
		var formObj = $(this).closest("form[id='pagerFindForm']");
		formObj.find("input[name^='filter_and_']").val('');
	});
});

function saveByCustomer(){
	var expressNum = $("input[name='expressNumCusHidden']").val();
	var expressCompanyId = $("input[name='expressCompanyIdCusHidden']").val();
	var weight = $("input[name='weightCusHidden']").val();
	var totalCost = $("input[name='totalCostCusHidden']").val();
	var receiveSendDate = $("input[name='receiveSendDateCusHidden']").val();
	var isbill = $("input[name='isbillCusHidden']").val();
	var receiveSendType = $("input[name='receiveSendTypeCusHidden']").val();
	var expressContent = $("input[name='expressContentCusHidden']").val();
	$("#addCustomerBtn").attr("disabled","disabled");
	var navTabId = $("input[name='parentNavTabIdCusHidden']").val();
	var sampleExpMgrId = $("input[name='sampleExpMgrIdCusHidden']").val();
	var rootNavTabId = $("input[name='rootNavTabId']",navTab.getCurrentPanel()).val();
	var idArr = [];
	var count = 0;
	var status = '';
	$("input:checkbox[name='ids']:checked", $.pdialog.getCurrent()).each(
		function(i, n) {
			idArr.push($(this).val());
			count += 1;
		});
	if (count == 0) {
		alertMsg.info('请选择你要添加的会员！');
		return;
	}
	var ids = idArr.join();
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/events/sampleExpressMgr!saveByCustomer.action",
		data: {'navTabId':navTabId,'ids':ids,'sampleExpMgrId':sampleExpMgrId,'expressNum':expressNum,'expressCompanyId':expressCompanyId,'weight':weight,'totalCost':totalCost,'receiveSendDate':receiveSendDate,'isbill':isbill,'receiveSendType':receiveSendType,'expressContent':expressContent,'rootNavTabId':rootNavTabId},
		success: function(data){
			if(data.statusCode=="200"){
				alertMsg.correct("添加成功！");
				navTab.reload(data.forwardUrl,{});
				var dialog = $.pdialog.getCurrent();
				if($.pdialog.reload(dialog.data("url"))){
					$("#addCustomerBtn").removeAttr("disabled");
				}
			}else{
				alertMsg.error("添加失败！");
				$("#addCustomerBtn").removeAttr("disabled");
			}
		},
		error :function(data){
			alertMsg.error("服务发生异常，请稍后再试！");
			$("#addCustomerBtn").removeAttr("disabled");
		}
	});
};
function importMoreCodes(){
	$("#queryCodes",navTab.getCurrentPanel()).val("");
	var value = $("input[name='affix']").val();
	$.ajaxFileUpload({
		url:'${path}/report/reportFileTask!importCodesByExcel.action',
		type:'post',
		data:{"filePath":value},
		secureuri: false,
		fileElementId: 'affix',
		dataType: 'json',
		success:function(data,status){
			if(data.result=="success"){
				alert("导入成功！");
				$("#queryCodes").val(data.codes);
				//$("#queryCodes",navTab.getCurrentPanel()).attr("name","filter_and_code_IN_S");
			}else{
				alert("导入失败！");
			}
		},
		error:function (data,status,e){
			alert("服务发生异常，请稍后再试！");
		}
	});
}
</script>





