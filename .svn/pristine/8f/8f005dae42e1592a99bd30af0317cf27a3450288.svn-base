<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>


<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>

<script type="text/javascript" language="javascript">
	
 
</script>
<style type="text/css">
	.select{
		height:22px; 
		width:193px; 
		margin-left:5px;
		margin-top: 4px;
	}
	.label{
		 width: 100px;
	}
	.input{
		width: 186px;
	}
</style>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportOrgJY/erpReportOrgJY!listReportOrgJY.action'){this.action = '${path}/reportOrgJY/erpReportOrgJY!listReportOrgJY.action' ;} ;
		return navTabSearch(this);" action="${path}/reportOrgJY/erpReportOrgJY!listReportOrgJY.action" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>姓名：</label> 
						<input type="text" name="filter_and_userName_LIKE_S" value="${filter_and_userName_LIKE_S}" class="input"/>
					</td>
					<td>
						<label>条码：</label> 
						<input type="text" name="filter_and_barcode_LIKE_S" value="${filter_and_barcode_LIKE_S}" class="input"/>
					</td>
					<td>
						<label>场次号：</label>
						<input type="text" name="filter_and_corServiceId_LIKE_S" value="${filter_and_corServiceId_LIKE_S}" class="input"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<label>远盟状态：</label>
						<select id="jyStateSel" name="filter_and_status_EQ_I" class="select">
							<option value="">---请选择---</option>
							<option value="0" ${filter_and_status_EQ_I==0?"selected":""}>未处理</option>
							<option value="1" ${filter_and_status_EQ_I==1?"selected":""}>已处理</option>
						</select>
					</td>
					<td>
						<label>创建开始日期：</label> 
						<input type="text" name="filter_and_createTime_GEST_T" style="width: 170px;" id="d1"
						 onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly"
						  value="${filter_and_createTime_GEST_T}" />
						<a class="inputDateButton" href="javascript:;" >选择</a>
					</td>
					<td>
						<label>创建结束日期：</label> 
						<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
						readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
					</td>
					<td style="padding-left: 10px;">
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
 			<!-- <li><a class="icon" onclick="listAlreadyPrintBatch()" style="cursor:pointer;"><span>已处理打印批次</span></a></li> -->
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	
	<table class="list" width="100%" layoutH="185" id="exportExcelTable"> 
		<thead>
			<tr>
				<th width="35" nowrap="true">序号</th>
				<th width="100"  export= "true" columnEnName = "reportName" columnChName = "报告名称" >报告名称</th>
				<th width="100"  export= "true" columnEnName = "barcode" columnChName = "条码" >条码</th>
				<th width="150"  export= "true" columnEnName = "corServiceId" columnChName = "场次号" >场次号</th>
				<th width="80"  export= "true" columnEnName = "userName" columnChName = "姓名" >姓名</th>
				<th width="100"  export= "true" columnEnName = "gender" columnChName = "性别" >性别</th>
				<th width="80"  export= "true" columnEnName = "entryAt" columnChName = "发布报告时间" >发布报告时间</th>
				<th width="90"  export= "true" columnEnName = "publishedAt" columnChName = "接收报告时间" >接收报告时间</th>
				<th width="80"  export= "true" columnEnName = "createTime" columnChName = "创建时间" >创建时间</th>
				<th width="100"  export= "true" columnEnName = "updateTime" columnChName = "修改时间" >修改时间</th>
				<th width="100"  export= "true" columnEnName = "status" columnChName = "远盟状态" >远盟状态</th>
				<th width="100"  export= "true" columnEnName = "counter" columnChName = "访问次数" >操作次数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="listReportUrlJY" varStatus="status">
				<tr>
					<td align="center" width="35">${status.count}</td>
					<td align="center" width="100">${listReportUrlJY.reportName}</td>
					<td align="center" width="100">${listReportUrlJY.barcode}</td> 
					<td align="center" width="100">${listReportUrlJY.corServiceId}</td>
					<td align="center" width="100">${listReportUrlJY.userName}</td>
					<td align="center" width="100">${listReportUrlJY.gender}</td>
					<td align="center" width="100">${fn:substring(listReportUrlJY.entryAt,0,19)}</td>
					<td align="center" width="100">${fn:substring(listReportUrlJY.publishedAt,0,19)}</td>
					<td align="center" width="100">${fn:substring(listReportUrlJY.createTime,0,19)}</td>
					<td align="center" width="100">${fn:substring(listReportUrlJY.updateTime,0,19)}</td>
					<td align="center" width="100">
						<c:if test="${listReportUrlJY.status==0}">未处理</c:if>
						<c:if test="${listReportUrlJY.status==1}">已处理</c:if>
					</td>
					<td align="center" width="100">${listReportUrlJY.counter}</td>
				</tr>		
			</c:forEach>
		</tbody>
	</table>
	
</div> 