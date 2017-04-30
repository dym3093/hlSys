<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" language="javascript">
function showEmsInfo(){
	var storeProduce=[];
	$('input[name="ids"]:checked').each(function(){
		storeProduce.push($(this).val());
	});
	if(storeProduce.length!=1){
		alert("请选择1条数据进行快递补录！");
		return;
	}else{
		var id = storeProduce[0];
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		$.pdialog.open("${path}/warehouse/warehouse!showEmsInfo.action?id="+id+"&navTabId="+navTabId, "addEmsInfo", "补录快递信息",{width:400,height:200});
	}
}
</script>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/warehouse/warehouse!listPull.action'){this.action = '${path }/warehouse/warehouse!listPull.action' ;} ;return navTabSearch(this);" 
	action="${ path }/warehouse/warehouse!listPull.action" method="post" rel = "pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
		<input type="hidden" name="navTabId" value="${navTabId}"/>
			<tr>
				<td><label>仓库名称：</label></td>
				<td><input type="text" name="filter_and_warehouseName_LIKE_S"  value="${filter_and_warehouseName_LIKE_S}"/></td>
				<td><label>远盟业务人员：</label></td>
				<td><input type="text" name="filter_and_businessName_LIKE_S" value="${filter_and_businessName_LIKE_S}"/></td>
				<td><label>联系电话：</label></td>
				<td><input type="text" name="filter_and_receiveTel_LIKE_S" value="${filter_and_receiveTel_LIKE_S}"/></td>
			</tr>
			<tr> 
				<td><label>项目编码：</label></td>
				<td><input type="text" name="filter_and_projectCode_LIKE_S" value="${filter_and_projectCode_LIKE_S}"/></td>
				<td><label>品名：</label></td>
				<td><input type="text" name="filter_and_remark2_LIKE_S" value="${filter_and_remark2_LIKE_S}"/></td>
	       	 	<td><label>公司名称：</label></td>
				<td><input type="text" name="filter_and_bannyCompannyName_LIKE_S" value="${filter_and_bannyCompannyName_LIKE_S}"/></td>
	       	</tr>
			<tr> 
	       	 	<td><label>日期：</label></td>
				<td><input type="text" name="filter_and_createTime_GEST_T"  id="d878888" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d989999\')}'})" class="required" readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a></td>
			    <td><label>至：</label></td>
				<td><input type="text" name="filter_and_createTime_LEET_T" id="d989999" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d878888\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a></td>
				<td><label>申请单号：</label></td>
				<td><input type="text" name="filter_and_remark3_LIKE_S" value="${filter_and_remark3_LIKE_S}"/></td>
			    <td style="padding-left:40px;" colspan="2">
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
		<ul class="toolBar">
			 <%--<li><a class="add" href="${path }/warehouse/warehouse!addWarehouse.action" target="navTab"><span>添加</span></a></li> 
			 <li><a class="edit" href="${path}/warehouse/warehouse!toModifyStoreWarehouseById.action?id={sid_user}" target="navTab"><span>修改</span></a></li> 
			 <li><a class="delete" href="${path }/warehouse/warehouse!deleteWarehouse.action" rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li> --%>
			<web:exportExcelTag 
						pagerFormId="pagerFindForm" 
						pagerMethodName="findPullByPage" 
						actionAllUrl="org.hpin.warehouse.web.StoreWarehouseAction" 
						informationTableId="exportExcelTable"
						fileName="StorePull">
			</web:exportExcelTag>
			<li><a class="add" href="javascript:void(0)" onclick="showEmsInfo()"><span>快递补录</span></a></li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="160" id="exportExcelTable">
		<thead>
			<tr>
				<th width="45px;">序号</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "warehouseName" columnChName = "仓库" >仓库</th>
				<th nowrap="nowrap" width="12%;" export = "true" columnEnName = "remark1" id2NameBeanId="org.hpin.warehouse.dao.StoreTypeDAO" columnChName = "品名" >品名</th>
				<th nowrap="nowrap" width="12%;"  export = "true" columnEnName = "bannyCompannyName" columnChName = "公司名称" >公司名称</th>
				<th nowrap="nowrap" width="12%;"  export = "true" columnEnName = "emsNo" columnChName = "快递号" >快递号</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "remark" columnChName = "数量" >数量</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "businessName" columnChName = "申请人" >申请人</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "receiveName" columnChName = "联系人" >联系人</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "receiveTel" columnChName = "联系电话" >联系电话</th> 
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "totalPrice" columnChName = "合计金额" >合计金额</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "emsPrice" columnChName = "快递费" >快递费</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "eventNos" columnChName = "卡ID" >卡ID</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "cardStart" columnChName = "卡号开始" >卡号开始</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "cardEnd" columnChName = "卡号截止" >卡号截止</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "projectBusinessName" columnChName = "项目名称" >项目名称</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "projectCode" columnChName = "项目编码" >项目编码</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "projectBusinessName" columnChName = "项目负责人" >项目负责人</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "createTime" columnChName = "日期" >日期</th>
				<th nowrap="nowrap" width="8%;"  export = "true" columnEnName = "createTime" columnChName = "申请单号" >申请单号</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="storeProduce" varStatus="status">
			<tr target="sid_user" rel="${storeProduce.id}">
				<td align="center"><input type="checkbox" name="ids" value="${storeProduce.id}">${status.count}</td>
				<td align="center" nowrap="nowrap">${storeProduce.warehouseName }</td>
				<td align="center" nowrap="nowrap"><hpin:id2nameDB  beanId="org.hpin.warehouse.dao.StoreTypeDAO" id="${storeProduce.remark1}"/></td>
					<%-- <a href="${path}/warehouse/warehouse!browstoreProduce.action?id=${storeProduce.bannyCompannyId}" 
				    width="1100" height="480" style="color:#0000FF" target="navTab" title="${storeProduce.bannyCompannyName}"> --%>
			    <td align="center" nowrap="nowrap">
				<c:set var="str" value="${storeProduce.bannyCompannyName}" /> 
					<c:choose> 
						<c:when test="${fn:length(str) > 18}"> 
							<c:out value="${fn:substring(str, 0, 18)}......" /> 
						</c:when> 
						<c:otherwise> 
							<c:out value="${str}" /> 
						</c:otherwise> 
					</c:choose>
					</a>
				</td>
				<td align="center" nowrap="nowrap"><a title="快递信息" target="dialog" href="${path}/events/express!listExpress.action?postid=${storeProduce.emsNo }">${storeProduce.emsNo }</td>
				<td align="center" nowrap="nowrap">${storeProduce.remark }</td>
				<td align="center" nowrap="nowrap">${storeProduce.businessName }</td>
				<td align="center" nowrap="nowrap">${storeProduce.receiveName}</td> 
				<td align="center" nowrap="nowrap">${storeProduce.receiveTel}</td>
				<td align="center" nowrap="nowrap">${storeProduce.totalPrice}</td>
				<td align="center" nowrap="nowrap">${storeProduce.emsPrice}</td>
				<td align="center" nowrap="nowrap">${storeProduce.eventNos}</td>
				<td align="center" nowrap="nowrap">${storeProduce.cardStart}</td> 
				<td align="center" nowrap="nowrap">${storeProduce.cardEnd}</td>
				<td align="center" nowrap="nowrap">${storeProduce.projectBusinessName}</td>
				<td align="center" nowrap="nowrap">${storeProduce.projectCode}</td> 
				<td align="center" nowrap="nowrap">${storeProduce.projectBusinessName }</td>
				<td align="center" nowrap="nowrap">${fn:substring(storeProduce.createTime,0,10)}</td>
				<td align="center" nowrap="nowrap">${storeProduce.remark3}</td>
		 	</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
