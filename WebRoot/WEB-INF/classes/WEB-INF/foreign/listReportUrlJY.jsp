<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	.select{
		height:22px; 
		width:186px; 
		margin-top: 4px;
		margin-left:5px;
	}
	
	.input{
		width: 186px;
	}
</style>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>

<script type="text/javascript" language="javascript">
	
 
</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportUrlJY/erpReportUrlJY!listReportUrlJY.action'){this.action = '${path}/reportUrlJY/erpReportUrlJY!listReportUrlJY.action' ;} ;
		return navTabSearch(this);" action="${path}/reportUrlJY/erpReportUrlJY!listReportUrlJY.action" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>姓名：</label> 
						<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}" class="input"/>
					</td>
					<td>
						<label>条形码：</label> 
						<input type="text" name="filter_and_code_EQ_S" value="${filter_and_code_EQ_S}" class="input"/>
					</td>
					<td>
						<label>文件类型：</label>
						<select id="fileTypeSel" name="filter_and_fileType_EQ_S" class="select">
							<option value="">---请选择---</option>
							<option value="pdf" ${filter_and_fileType_EQ_S=="pdf"?"selected":""}>报告</option>
							<option value="jpg" ${filter_and_fileType_EQ_S=="jpg"?"selected":""}>报告单</option>
						</select>
					</td>
				</tr>
				
				<tr style="width: 800px;">
					
					<td>
						<label>创建开始日期：</label> 
						<input type="text" name="filter_and_createTime_GEST_T" id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})" 
							readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
					</td>
					<td>
						<label>创建结束日期：</label> 
						<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})" 
							readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
					</td>
					<td>
						<label>是否下载：</label>
						<select id="fileTypeSel" name="filter_and_status_EQ_I" class="select">
							<option value="">---请选择---</option>
							<option value="0" ${filter_and_status_EQ_I==0?"selected":""}>未下载</option>
							<option value="1" ${filter_and_status_EQ_I==1?"selected":""}>已下载</option>
						</select>
					</td>
					
				</tr>
				
				<tr>	
					<td>
						<label>状态码：</label> 
						<input type="text" name="filter_and_httpCode_EQ_I" value="${filter_and_httpCode_EQ_I}" class="input"/>
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
				<th width="80"  export= "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th width="100"  export= "true" columnEnName = "code" columnChName = "条码" >条码</th>
				<th width="120"  export= "true" columnEnName = "url" columnChName = "地址" >地址</th>
				<th width="80"  export= "true" columnEnName = "fileType" columnChName = "文件类型" >文件类型</th>
				<th width="60"  export= "true" columnEnName = "httpCode" columnChName = "状态码" >状态码</th>
				<th width="100"  export= "true" columnEnName = "createtime" columnChName = "创建时间" >创建时间</th>
				<th width="60"  export= "true" columnEnName = "status" columnChName = "状态" >状态</th>
				<th width="60"  export= "true" columnEnName = "counter" columnChName = "操作次数" >操作次数</th>
				<th width="60"  export= "true" columnEnName = "remark" columnChName = "备注" >备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="listReportUrlJY" varStatus="status">
				<tr>
					<td align="center" width="35" nowrap="true">${status.count}</td>
					<td align="center" width="80" nowrap="true">${listReportUrlJY.name}</td>
					<td align="center" width="100" nowrap="true">${listReportUrlJY.code}</td> 
					<td align="center" width="120" nowrap="true" title="${listReportUrlJY.url}">${listReportUrlJY.url}</td> 
					<td align="center" width="80" nowrap="true">
						<c:if test="${listReportUrlJY.fileType=='pdf'}">报告</c:if>
						<c:if test="${listReportUrlJY.fileType=='jpg'}">报告单</c:if>
					</td>
					<td align="center" width="60" nowrap="true">${listReportUrlJY.httpCode}</td>
					<td align="center" width="100" nowrap="true">${fn:substring(listReportUrlJY.createTime,0,19)}</td>
					<td align="center" width="60" nowrap="true">
						<c:if test="${listReportUrlJY.status==0}">未下载</c:if>
						<c:if test="${listReportUrlJY.status==1}">已下载</c:if>
					</td>
					<td align="center" width="60" nowrap="true">${listReportUrlJY.counter}</td>
					<td align="center" width="60" nowrap="true" title="${listReportUrlJY.remark}">${listReportUrlJY.remark}</td>
				</tr>		
			</c:forEach>
		</tbody>
	</table>
	
</div> 