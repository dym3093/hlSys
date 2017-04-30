<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" language="javascript">
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
		navTab.openTab("modifyEvents", "../reportdetail/erpReportdetailBatch!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	}
}
</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportdetail/erpReportdetailBatch!listReportdetailBatch.action'){this.action = '${path}/reportdetail/erpReportdetailBatch!listReportdetailBatch.action' ;} ;return navTabSearch(this);" action="${path}/reportdetail/erpReportdetailBatch!listReportdetailBatch.action" method="post">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" name="filter_and_batchno_LIKE_S" value="${filter_and_batchno_LIKE_S}"/>
				</td>	
				<td>
					<label>验证是否正确</label>
					<select name="filter_and_ismatch_EQ_S" rel="iselect" id="ismatch">
						 <option value="">请选择</option> 
						 <option value=1 <c:if test="${filter_and_ismatch_EQ_I==1 }">selected="selected"</c:if>>是</option>
						 <option value=0 <c:if test="${filter_and_ismatch_EQ_I==0 }">selected="selected"</c:if>>否</option>
					</select> 
				</td>
				
				<td>
				</td>
			</tr>
			<tr>
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_batchdate_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_batchdate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_batchdate_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_batchdate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><input type="hidden" name="id" value="${erpReportdetailBatch.id }"/>
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
		<%-- <web:security tag="noemeruser_enter"> --%>
		<ul class="toolBar">
		<%--
			<li><a class="add" href="${path}/reportdetail/erpReportdetailBatch!toAddCustomer.action?eventsNo=${events.id}" target="navTab" rel="add"><span>添加</span></a></li>
			<li><a class="delete" href="${path}/reportdetail/erpReportdetailBatch!delCustomer.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
			<li><a class="add" href="${path}/reportdetail/erpReportdetailBatch!importCustomer.action?id=${events.id}" target="navTab" rel="add"><span>导入</span></a></li>
		 --%>
		 <web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.reportdetail.web.ErpReportdetailBatchAction" 
								informationTableId="exportExcelTable"
								fileName="erpReportdetailBatchAction"></web:exportExcelTag> 
		
		</ul>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "batchno" columnChName = "批次号" >批次号</th>
				<th  export = "true" columnEnName = "batchdate" columnChName = "批次日期" >批次日期</th>
				<th  export = "true" columnEnName = "xlscodecount" columnChName = "Excel名单数" >Excel名单总数</th>
				<th  export = "true" columnEnName = "xlspdfcount" columnChName = "正确验证人数" >Excel正确名单数</th>
				<th  export = "true" columnEnName = "codenoreportfilecount" columnChName = "多余条形码数" >Excel多余名单数</th>
				<th  export = "true" columnEnName = "pdffilecount" columnChName = "PDF报告数" >基因报告文件数</th>
				<th  export = "true" columnEnName = "reportfilenocodecount" columnChName = "多余报告文件数" >多余报告文件数</th>
				<th  export = "true" columnEnName = "ismatch" columnChName = "验证是否正确" >验证是否正确</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpReportdetailBatch" varStatus="status">
					<tr target="sid_user" rel="${erpReportdetailBatch.id }">
						<td align="center">
							<%-- <input type="checkbox" name="ids" value="${erpReportdetailBatch.id}"> --%>
							${ status.count }</td>
						<td align="center">	
						
							<a title="批次信息" target="navTab" width="1000"  href="${path}/reportdetail/erpReportdetailBatch!toReportdetailBatch.action?id=${erpReportdetailBatch.id}">${erpReportdetailBatch.batchno}</a>
						
						</td>
						<td align="center"> ${fn:substring(erpReportdetailBatch.batchdate,0,14)}00:00</td>
						<td align="center">	${erpReportdetailBatch.xlscodecount}</td>
						<td align="center">	${erpReportdetailBatch.xlspdfcount}</td>
						<td align="center">	${erpReportdetailBatch.codenoreportfilecount}</td>
						<td align="center">	${erpReportdetailBatch.pdffilecount}</td>
						<td align="center">	${erpReportdetailBatch.reportfilenocodecount}</td>
						<td align="center">	
							<c:if test="${erpReportdetailBatch.reportfilenocodecount==0 && erpReportdetailBatch.codenoreportfilecount==0}">本批次正确</c:if>
	    					<c:if test="${erpReportdetailBatch.reportfilenocodecount>0 || erpReportdetailBatch.codenoreportfilecount>0}">本批次有误</c:if>
						</td>
						
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 