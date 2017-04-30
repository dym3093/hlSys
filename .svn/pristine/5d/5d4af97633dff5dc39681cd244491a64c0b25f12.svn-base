<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/resource/customerRelationShip!listCustomerRelationShip.action'){this.action = '${path }/resource/customerRelationShip!listCustomerRelationShip.action' ;} ;return navTabSearch(this);" action="${ path }/resource/customerRelationShip!listCustomerRelationShip.action" method="post" rel = "pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent" style="overflow: hidden;">
			<tr>
				<td> <label>套餐名称：</label><input type="text" name="custRelaQuery.combo" value="${custRelaQuery.combo}"/> </td>
				<td> <label>项目负责人：</label><input type="text" name="custRelaQuery.projectOwner" value="${custRelaQuery.projectOwner }"/> </td>
				<td> <label>项目对接人：</label><input type="text" name="custRelaQuery.linkName" value="${custRelaQuery.linkName }"/> </td>
			</tr>
			<tr>
                <td> <label>项目编码：</label><input type="text" name="custRelaQuery.proCode" value="${custRelaQuery.proCode}"/> </td>
                <td> <label>公司名称：</label> <input type="text" name="custRelaQuery.branchCommany"  value="${custRelaQuery.branchCommany }"/> </td>
                <td> <label>总公司名称：</label> <input type="text" name="custRelaQuery.customerNameSimple"  value="${custRelaQuery.customerNameSimple}"/> </td>
			</tr>
            <tr>
				<td align="left">
					<label>省 份：</label>
					<select  id="province" name="custRelaQuery.province" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city1" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
						<option value="${custRelaQuery.province}" ></option>
					</select>
				</td>
				<td align="left">
					<label>城 市：</label>
					<select id="city1" name="custRelaQuery.city" rel="iselect">
						<option value="${custRelaQuery.city}"></option>
					</select>
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
	 <web:security tag="customerRelationShipAdmin"> 
		<ul class="toolBar">
			<li><a class="add" href="${path }/resource/customerRelationShip!addCustomer.action" target="navTab"><span>添加</span></a></li> 
			 <li><a class="edit" href="${path}/resource/customerRelationShip!modifyResource.action?id={sid_user}" target="navTab"><span>修改</span></a></li>
			 <li><a class="delete" href="${path }/resource/customerRelationShip!deleteCustomer.action" rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li> 
			 <web:exportExcelTag 
						pagerFormId="pagerFindForm" 
						pagerMethodName="findPageByConditions"
						actionAllUrl="org.hpin.base.customerrelationship.web.CustomerRelationShipAction" 
						informationTableId="exportExcelTable"
						fileName="customerRelationShip"></web:exportExcelTag> 
			 <li><a id="proCombo_a_id" class="edit" href="javascript: void(0);"  title="项目套餐" ><span>项目套餐</span></a></li> 
		</ul>
	 </web:security> 
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="100" id="exportExcelTable">
		<thead>
			<tr>
				<th>序号</th>
				<th nowrap="nowrap" export = "true" columnEnName = "branchCommany" columnChName = "公司名称" >公司名称</th>
				<th nowrap="nowrap" export = "true" columnEnName = "customerNameSimple" columnChName = "总公司名称" >总公司名称</th>
				<th nowrap="nowrap" export = "true" columnEnName = "province" columnChName = "省份"  id2NameBeanId = "org.hpin.base.region.dao.RegionDao">省份</th>
				<th nowrap="nowrap" export = "true" columnEnName = "city" columnChName = "城市"  id2NameBeanId = "org.hpin.base.region.dao.RegionDao">城市</th>
			<!-- 	<th nowrap="nowrap" export = "true" columnEnName = "combo" columnChName = "套餐名称" >套餐名称</th> -->
				<th nowrap="nowrap" export = "true" columnEnName = "createTime" columnChName = "创建日期" >创建日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="shipVo" varStatus="status">
			<tr target="sid_user" rel="${ shipVo.id }">
				<td align="center"><input type="checkbox" name="ids" value="${ shipVo.id }">${ status.count }</td>
				
				<td align="center" nowrap="nowrap"><a href="${path }/resource/customerRelationShip!browCustomer.action?id=${shipVo.id }&flag=0" width="1100" height="480" style="color:#0000FF" target="navTab" title="${shipVo.branchCommany }">
					<c:set var="str" value="${shipVo.branchCommany }" /> 
					<c:choose> 
					<c:when test="${fn:length(str) > 18}"> 
					<c:out value="${fn:substring(str, 0, 18)}......" /> 
						</c:when> 
						<c:otherwise> 
					<c:out value="${str}" /> 
					</c:otherwise> 
					</c:choose></a></td>
				<td align="center" nowrap="nowrap">${shipVo.customerNameSimple}</td>
				<td align="center" nowrap="nowrap">${shipVo.provinceName }</td>
				<td align="center" nowrap="nowrap">${shipVo.cityName }</td>
				<%-- <td align="center" width="420px;">${shipVo.combo }</td> --%>
				<td align="center" nowrap="nowrap"><fmt:formatDate value='${shipVo.createTime }' pattern='yyyy-MM-dd'/></td>
		 	</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<script>
$(function() {
	$("#proCombo_a_id", navTab.getCurrentPanel()).on("click", function() {
		//判断是否选中数据;
		var count = 0;
		var ids = "";
		var status = "";
		
		$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
			ids = val.value ;
			count ++;
		});
		
		if(count == 0) {
			alertMsg.warn("请选择你要更改的支公司信息！");
			return;
		} else if (count > 1) {
			alertMsg.warn("只能选择一条你要更改的支公司信息！");
			return;
		}
		//页面跳转
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("proComboUpdateRelationShip", "${path}/resource/customerRelationShip!editProComboDetail.action?id="+ids+"&navTabId="+navTabId, { title:"项目套餐修改", fresh:false, data:{} });
		
	});
});
</script>