<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
function changeIssue() {
	var ids = '';
	var count = 0;
	var status = 0;
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		
	});
	if(count == 0) {
		alert('请选择一条信息！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息！');
		return ;		
	}
	else {
		
		 	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("listbarcodeissue", "../business/barCodeIssue!modifybarCodeIssue.action?id="+ids+"&navTabId="+navTabId, { title:"接收", fresh:false, data:{} });		
    
   }
}
</script>
<html>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/business/barCodeIssue!listbarCodeIssue.action'){this.action = '${path }/business/barCodeIssue!listbarCodeIssue.action' ;} ;return navTabSearch(this);" action="${path }/business/barCodeIssue!listbarCodeIssue.action"  method="post" rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>公司名称：</label>
					<%-- <input type="text" id="companyName" name="filter_and_companyName_LIKE_S" value="${ filter_and_companyName_LIKE_S }"/> --%>
					<input type="hidden" id="id" name="filter_and_companyId_EQ_S" bringbackname="customer.id" value="${filter_and_companyId_EQ_S}" />
					<input type="text" id="branchCompany" name="aaaa" bringbackname="customer.branchCommanyName" value="${aaaa} " readonly="readonly"/>
					<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShip.action" lookupGroup="customer">查找带回</a>
					<%-- <img src="${path}/images/clear.png" title="清除公司信息" id="customer" style="padding-top: 6px;"/> --%>
					
					
				</td>			
				<td>
					<label>开始日期：</label>
					<input type="hidden" id="navTabId" name="navTabId" value="${navTabId }">					
				  <input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2015-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})" class="required" readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td>
					<label>截止日期：</label>					
				<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				
				<td>
				<div class="buttonActive"><div class="buttonContent"><button type="submit"">查找</button></div></div>
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
			<li><a class="add" href="${path}/business/barCodeIssue!addbarCodeIssue.action" target="navTab"><span>派发</span></a></li>
			<li><a class="edit" onclick="changeIssue()" style="cursor:pointer;"><span>接收</span></a></li>
			<li><a class="delete" href="${path}/business/barCodeIssue!deletebarCodeIssue.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.barcode.web.BarCodeIssueAction" 
								informationTableId="exportExcelTable"
								fileName="BarCodeIssue"></web:exportExcelTag>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="40px"><nobr>序号</nobr></th>
				<th export = "true" columnEnName = "companyName" columnChName = "支公司名称"><nobr>支公司名称</nobr></th>
				<th export = "true" columnEnName = "barcodeCount" columnChName = "基因包数量"><nobr>基因包数量</nobr></th>
				<th export = "true" columnEnName = "contacts" columnChName = "联系人"><nobr>联系人</nobr></th>				
				<th export = "true" columnEnName = "contactsTel" columnChName = "联系人电话"><nobr>联系人电话</nobr></th>
				<th export = "true" columnEnName = "expressNo" columnChName = "快递单号"><nobr>快递单号</nobr></th>
				<th export = "true" columnEnName = "sendDate" columnChName = "发送日期"><nobr>发送日期</nobr></th>
				<th export = "true" columnEnName = "receiveDate" columnChName = "接收日期"><nobr>接收日期</nobr></th>
				<th><nobr>状态</nobr></th>
				<th export = "true" columnEnName = "createTime" columnChName = "创建时间"><nobr>创建时间</nobr></th>
					
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="barCodeIssue" varStatus="status">
			<tr target="sid_user" rel="${barCodeIssue.id }">
				<td align="center"><input type="checkbox" name="ids" value="${barCodeIssue.id }" >${ status.count }</td>				
				<td>
					<a href="${ path }/business/barCodeIssue!browbarCodeIssue.action?id=${barCodeIssue.id }" target="navTab" rel="${barCodeIssue.id }">${barCodeIssue.companyName }</a>						 	
			  </td>			 
				<td align="center" nowrap="nowrap">${barCodeIssue.barcodeCount }</td>
				<td align="center" nowrap="nowrap">${barCodeIssue.contacts }</td>
				<td align="center" nowrap="nowrap">${barCodeIssue.contactsTel }</td>
				<td align="center" nowrap="nowrap">${barCodeIssue.expressNo }</td>
				<td align="center" nowrap="nowrap">${barCodeIssue.sendDate }</td>
				<td align="center" nowrap="nowrap">${barCodeIssue.receiveDate }</td>
				<td align="center" nowrap="nowrap">
					     <c:choose>			  	  	  
				  	  	    <c:when test="${ barCodeIssue.status==0 }">未发出</c:when>
				  	  	    <c:when test="${ barCodeIssue.status==1 }">已发出</c:when>
				  	  	    <c:when test="${ barCodeIssue.status==2 }">已接收</c:when>  	  	    
				      </c:choose>	
					</td>
				<td align="center" nowrap="nowrap">${ fn:substring(barCodeIssue.createTime , 0 , 19 )}</td> 					
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>

</html>