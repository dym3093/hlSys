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
		navTab.openTab("modifyEvents", "../reportdetail/erpReportdetail!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	}
}
</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportdetail/erpReportdetail!listReportdetail.action'){this.action = '${path}/reportdetail/erpReportdetail!listReportdetail.action' ;} ;return navTabSearch(this);" action="${path}/reportdetail/erpReportdetail!listReportdetail.action" method="post">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" name="filter_and_batchno_LIKE_S" value="${filter_and_batchno_LIKE_S}"/>
				</td>
				<td>
					<label>条形码：</label> 
					<input type="text" name="filter_and_code_LIKE_S" value="${filter_and_code_LIKE_S}"/>
				</td>
				<td>
					<label>姓名：</label> 
					<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}"/>
				</td>
				
				<td>
					<label>套餐名：</label> 
					<input type="text" name="filter_and_project_LIKE_S" value="${filter_and_setmealName_LIKE_S}"/>
					<input type="hidden" name="id" value="${events.id}"/>
				</td>
				
			</tr>
			<tr>
				<td><label>采样起始日期：</label> 
					<input type="text" name="filter_and_samplingdate_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_samplingdate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>采样结束日期：</label> 
					<input type="text" name="filter_and_samplingdate_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_samplingdate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><label>报告是否匹配</label>
					<select name="filter_and_ismatch_EQ_S" rel="iselect" id="ismatch">
						 <option value="">请选择</option> 
						 <option value=1 <c:if test="${filter_and_ismatch_EQ_I==1 }">selected="selected"</c:if>>是</option>
						 <option value=0 <c:if test="${filter_and_ismatch_EQ_I==0 }">selected="selected"</c:if>>否</option>
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
		<%-- <web:security tag="noemeruser_enter"> --%>
		<ul class="toolBar">
			<%-- 
			<li><a class="add" href="${path}/reportdetail/erpReportdetail!toAddCustomer.action?eventsNo=${events.id}" target="navTab" rel="add"><span>添加</span></a></li>
			<li><a class="delete" href="${path}/reportdetail/erpReportdetail!delCustomer.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
			<li><a class="add" href="${path}/reportdetail/erpReportdetail!importCustomer.action?id=${events.id}" target="navTab" rel="add"><span>导入</span></a></li>
			--%>
		 <web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.reportdetail.web.ErpReportdetailAction" 
								informationTableId="exportExcelTable"
								fileName="ErpReportdetailAction"></web:exportExcelTag> 
		
		</ul>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "batchno" columnChName = "批次号" >批次号</th>
				<th  export = "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th  export = "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th  export = "true" columnEnName = "sex" columnChName = "性别">性别</th>
				<th  export = "true" columnEnName = "age" columnChName = "年龄" >年龄</th>
				<th  export = "true" columnEnName = "phone" columnChName = "电话" >电话</th>
				<th  export = "true" columnEnName = "branchcompany" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "project" columnChName = "套餐名">套餐名</th>
				<th  export = "true" columnEnName = "sampletype" columnChName = "样本类型" >样本类型</th>
				<th  export = "true" columnEnName = "salesman" columnChName = "营销员" >营销员</th>
				<th  export = "true" columnEnName = "samplingdate" columnChName = "采样日期" >采样日期</th>
				<th  export = "true" columnEnName = "filepath" columnChName = "Excel文件路径" >Excel文件路径</th>
				<th  export = "true" columnEnName = "ismatch" columnChName = "是否匹配" >是否匹配</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="reportdetail" varStatus="status">
					<tr target="sid_user" rel="${reportdetail.id }">
						<td align="center">
							<%-- <input type="checkbox" name="ids" value="${reportdetail.id}"> --%>
							${ status.count }
						</td>
						<td align="center">	${reportdetail.batchno}</td>
						<td align="center">	${reportdetail.code}</td>
						<td align="center">	${reportdetail.name}</td>
						<td align="center">	${reportdetail.sex}</td>
						<td align="center">	${reportdetail.age}</td>
						<td align="center">	${reportdetail.phone}</td>
						<td align="center">	${reportdetail.branchcompany}</td>
						<td align="center">
							${reportdetail.project}
						</td>
						<td align="center">	
							${reportdetail.sampletype}
						</td>
						<td align="center">	${reportdetail.salesman}</td>
						<td align="center">	${fn:substring(reportdetail.samplingdate,0,14)}00:00</td>
						<td align="center">	${reportdetail.filepath}</td>
						<td align="center">	
							<c:if test="${reportdetail.ismatch==1}">是</c:if><c:if test="${reportdetail.ismatch==0}">否</c:if>
						</td>
						
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 