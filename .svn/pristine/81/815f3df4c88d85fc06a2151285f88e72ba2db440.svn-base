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

<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$("#customer").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
});
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

$(document).ready(function(){		//默认选中下拉
	var select= $('input[name="selectCost"]').val();
	if(select=="0"){
		$('select[name="filter_and_produceCost_LT_D"]').find("option[value='0']").attr("selected",true);

	}else if(select=="1"){
		$('select[name="filter_and_produceCost_LT_D"]').find("option[value='1']").attr("selected",true);
	}
});
</script>
<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<div class="searchBar" id="manyQue">
		<form id="pagerFindForm" 
			onsubmit="if(this.action != '${path}/venueStaffSettlement/erpEventsStaffCost!listErpEvents.action'){this.action = '${path}/venueStaffSettlement/erpEventsStaffCost!listErpEvents.action' ;} ;return navTabSearch(this);"
			action="${path}/venueStaffSettlement/erpEventsStaffCost!listErpEvents.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>场次号：</label> <input type="text"
						name="filter_and_eventsNo_LIKE_S"
						value="${filter_and_eventsNo_LIKE_S}" />
					</td>
					<td>
						<label>支公司：</label> 
						<input type="hidden" id="id"name="filter_and_branchCompanyId_EQ_S" bringbackname="customer.id"value="${filter_and_branchCompanyId_EQ_S}" /> 
						<input type="text"id="branchCompany" name="aaaa"
						bringbackname="customer.branchCommanyName" value="${aaaa}" readonly="readonly" /> 
						<a class="btnLook"href="${ path }/resource/customerRelationShip!findCustomerRelationShip.action"
						lookupGroup="customer" target="dialog" width="800" height="480">查找带回</a>
						<img src="${path}/images/clear.png" title="清除公司信息" id="customer" style="padding-top: 6px;"/>
					</td>
					<td>
						<input type="hidden" name="selectCost" value="${selectCost}"/>
						<label>是否产生费用：</label> 
						<select name="filter_and_produceCost_LT_D" rel="iselect" id="produceCost">
							<option value="">请选择</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
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
					<td>
					<label>批次号：</label> 
						<input type="text" id="queryTypeId" name="filter_and_batchno_EQ_S" value="${filter_and_batchno_EQ_S}" />
	                    <input type="radio" name="queryType" value="1" checked="checked" onclick="changeType(1)"/>精准查询
	                    <input type="radio" name="queryType" value="2" onclick="changeType(2)"/>模糊查询                  
					</td>
				</tr>
				<tr>
					<td>
						<label>省：</label>
						<select id="province" name="filter_and_province_EQ_S"
						rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
						ref="city"
						refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
							<option value="${filter_and_province_EQ_S}""></option>
						</select>
					</td>
					<td>
						<label>市：</label> 
						<select id="city" name="filter_and_city_EQ_S" rel="iselect">
							<option value="${filter_and_city_EQ_S}""></option>
						</select>
					</td>
					<td>
						<div class="buttonActive" style="margin-left:16px;">
							<div class="buttonContent">
								<button type="submit">查找</button>
							</div>
						</div>
						<div class="buttonActive" style="margin-left:5px;">
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
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th export="true" columnEnName="eventsNo" columnChName="场次号">场次号</th>
				<th export="true" columnEnName="eventDate" columnChName="场次日期">场次日期</th>
				<th export="true" columnEnName="provice" columnChName="省"id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
				<th export="true" columnEnName="city" columnChName="市"id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
				<th export="true" columnEnName="branchCompanyId"id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"columnChName="支公司">支公司</th>
				<th export="true" columnEnName="ownedCompanyId"id2NameBeanId="org.hpin.base.usermanager.dao.UserDao"columnChName="所属公司">所属公司</th>
				<th export="true" columnEnName="comboName" columnChName="套餐">套餐</th>
				<th export="true" columnEnName ="settNumbers" columnChName = "人数" >人数</th>
				<th export="true" columnEnName ="produceCost" columnChName = "产生费用" >产生费用</th>
				<th export="true" columnEnName="batchNo" columnChName="批次号">批次号</th>
				<th export="true" columnEnName="" columnChName="是否录入人员">是否录入人员</th>
				<th export="false" columnEnName="" columnChName="">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpEvents" varStatus="status">
				<tr target="sid_user" rel="${erpEvents.id }">
					<td align="center"><input type="checkbox" name="ids" value="${erpEvents.id}"/>${ status.count }</td>
					<td align="center">
						<a title="场次费用人员信息" target="navTab" width="1000"  href="${path}/venueStaffSettlement/erpEventsStaffCost!getPersonByEventsId.action?id=${erpEvents.id}">${erpEvents.eventsNo}</a>
					</td>
					<td align="center">${fn:substring(erpEvents.eventDate,0,19)}</td>
					<td align="center"><hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${erpEvents.provice}" /></td>
					<td align="center"><hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${erpEvents.city}" /></td>
					<td align="center"><hpin:id2nameDB id="${erpEvents.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" /></td>
					<td align="center"><hpin:id2nameDB id="${erpEvents.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
					<td align="center">${erpEvents.comboName}</td>
					<td align="center">${erpEvents.settNumbers}</td>
					<td align="center">${erpEvents.produceCost}</td>
					<td align="center">${erpEvents.batchNo}</td>
					<td align="center"></td>
					<td align="center">
						<div class="panelBar">
							<ul class="toolBar">
								<li><a class="add" href="${path}/venueStaffSettlement/erpEventsStaffCost!toAddErpEventsStaffCost.action?id=${erpEvents.id}&eventsNo=${erpEvents.eventsNo}"
										target="navTab" rel="add"><span>人员费用录入</span></a></li>
							</ul>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
