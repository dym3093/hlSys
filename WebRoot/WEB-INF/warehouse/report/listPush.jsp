<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/warehouse/warehouse!listPush.action'){this.action = '${path }/warehouse/warehouse!listPush.action' ;} ;return navTabSearch(this);" 
	action="${ path }/warehouse/warehouse!listPush.action" method="post" rel = "pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td><label>仓库：</label></td>
				<td><input type="text" name="filter_and_warehouseName_LIKE_S"  value="${filter_and_warehouseName_LIKE_S}"/></td>
				<td><label>创建人：</label></td>
				<td><input type="text" name="filter_and_createUserName_LIKE_S" value="${filter_and_createUserName_LIKE_S}"/></td>
				<td><label>项目名称：</label></td>
				<td><input type="text" name="filter_and_projectBelone_LIKE_S" value="${filter_and_projectBelone_LIKE_S}"/></td>
				<td width="90px;"></td>
			</tr>
			<tr>
				<td><label>项目名称：</label></td>
				<td><input type="text" name="filter_and_projectCode_LIKE_S" value="${filter_and_projectCode_LIKE_S}"/></td>
                <td><label>日期：</label></td>
				<td><input type="text" name="filter_and_createTime_GEST_T"  id="d878888" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d989999\')}'})" class="required" readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a></td>
			    <td><label>至：</label></td>
				<td><input type="text" name="filter_and_createTime_LEET_T" id="d989999" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d878888\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a></td>
				<td width="90px;" style="display:block;"><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<%--<li><a class="add" href="${path }/warehouse/warehouse!addWarehouse.action" target="navTab"><span>添加</span></a></li> 
			 <li><a class="edit" href="${path}/warehouse/warehouse!toModifyStoreWarehouseById.action?id={sid_user}" target="navTab"><span>修改</span></a></li> 
			 <li><a class="delete" href="${path }/warehouse/warehouse!deleteWarehouse.action" rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li> --%>
			<web:exportExcelTag 
						pagerFormId="pagerFindForm" 
						pagerMethodName="findPushByPage" 
						actionAllUrl="org.hpin.warehouse.web.StoreWarehouseAction" 
						informationTableId="exportExcelTable"
						fileName="StorePush">
			</web:exportExcelTag> 
		</ul>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="160" id="exportExcelTable">
		<thead>
			<tr>
				<th width="4%;">序号</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "warehouseName" columnChName = "仓库" >仓库</th>
				<th nowrap="nowrap" width="12%;" export = "true" columnEnName = "remark1" id2NameBeanId="org.hpin.warehouse.dao.StoreTypeDAO" columnChName = "品名" >品名</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "remark" columnChName = "数量" >数量</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "businessName" columnChName = "规格" >规格</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "businessId" columnChName = "描述" >描述</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "basePrice" columnChName = "单价" >单价</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "totalPrice" columnChName = "总额" >总额</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "createTime" columnChName = "日期" >日期</th>
				<th nowrap="nowrap" width="6%;"  export = "true" columnEnName = "eventNos" columnChName = "卡ID" >卡ID</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "cardStart" columnChName = "卡号开始" >卡号开始</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "cardEnd" columnChName = "卡号截止" >卡号截止</th>
				<th nowrap="nowrap" width="12%;"  export = "true" columnEnName = "projectCode" columnChName = "项目名称" >项目名称</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "projectBelone" columnChName = "项目名称" >项目名称</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="storeProduce" varStatus="status">
			<tr target="sid_user" rel="${storeProduce.id}">
				<td align="center">${status.count}</td>
				<td align="center" nowrap="nowrap">${storeProduce.warehouseName }</td>
				<%-- ${path}/warehouse/warehouse!browStoreAll.action?id=${storeProduce.id} --%>
					<td align="center" nowrap="nowrap"><hpin:id2nameDB  beanId="org.hpin.warehouse.dao.StoreTypeDAO" id="${storeProduce.remark1}"/></td>
					<%-- <c:set var="str" value="${storeProduce.name}" /> 
					<c:choose> 
						<c:when test="${fn:length(str) > 18}"> 
							<c:out value="${fn:substring(str, 0, 18)}......" /> 
						</c:when> 
						<c:otherwise> 
							<c:out value="${str}" /> 
						</c:otherwise> 
					</c:choose>
					</a> --%>
				<td align="center" nowrap="nowrap">${storeProduce.remark}</td>
				<td align="center" nowrap="nowrap">${storeProduce.businessName}</td> 
				<td align="center" nowrap="nowrap">${storeProduce.businessId}</td>
				<td align="center" nowrap="nowrap">${storeProduce.basePrice}</td>
				<td align="center" nowrap="nowrap">${storeProduce.totalPrice}</td> 
				<td align="center" nowrap="nowrap">${fn:substring(storeProduce.createTime,0,10)}</td>
				<td align="center" nowrap="nowrap">${storeProduce.eventNos}</td>
				<td align="center" nowrap="nowrap">${storeProduce.cardStart}</td>
				<td align="center" nowrap="nowrap">${storeProduce.cardEnd}</td> 
				<td align="center" nowrap="nowrap">${storeProduce.projectCode}</td> 
				<td align="center" nowrap="nowrap">${storeProduce.projectBelone}</td> 
		 	</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
