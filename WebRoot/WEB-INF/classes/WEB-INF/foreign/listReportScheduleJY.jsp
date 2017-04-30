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
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
	
	.input{
		width: 186px;
	}
</style>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>

<script type="text/javascript" language="javascript">
	function importMoreCodes(){
		$("#jyCodes",navTab.getCurrentPanel()).val("");
		$.ajaxFileUpload({
			url:'${path}/report/reportFileTask!importCodesByExcel.action',
	        type:'post',
	       	secureuri: false,
	        fileElementId: 'affix',
	        dataType: 'json',
			success:function(data,status){
				if(data.result=="success"){
					alertMsg.correct("导入成功！");
					$("#jyCodes",navTab.getCurrentPanel()).val(data.codes);
				}else{
					alertMsg.warn("导入失败！");
				}
			},
			error:function (data,status,e){
	        	alert("服务发生异常，请稍后再试！");
	        }	
		});	
	}
 
</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportScheduleJY/erpReportScheduleJY!listReportScheduleJY.action'){this.action = '${path}/reportScheduleJY/erpReportScheduleJY!listReportScheduleJY.action' ;} ;
		return navTabSearch(this);" action="${path}/reportScheduleJY/erpReportScheduleJY!listReportScheduleJY.action" method="post"  rel="pagerForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>姓名：</label> 
						<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}" class="input"/>
					</td>
					<td>
						<label>条码：</label> 
						<input type="text" name="filter_and_code_LIKE_S" value="${filter_and_code_LIKE_S}" class="input"/>
					</td>
					<td>
						<label>套餐：</label>
						<input type="text" name="filter_and_combo_LIKE_S" value="${filter_and_combo_LIKE_S}" class="input"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<label>场次号：</label> 
						<input type="text" name="filter_and_eventsNo_LIKE_S" value="${filter_and_eventsNo_LIKE_S}" class="input"/>
					</td>
					<td>
						<label>批次号：</label> 
						<input type="text" name="filter_and_batchNo_LIKE_S" value="${filter_and_batchNo_LIKE_S}" class="input"/>
					</td>
					<td>
						<label>状态：</label>
						<select id="jyStateSel" name="filter_and_status_EQ_I" class="select">
							<option value="">---请选择---</option>
							<option value="0" ${filter_and_status_EQ_I==0?"selected":""}>未完成</option>
							<option value="1" ${filter_and_status_EQ_I==1?"selected":""}>已完成</option>
							<option value="9" ${filter_and_status_EQ_I==9?"selected":""}>信息不匹配</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>
						<label>创建开始日期：</label> 
						<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" />
						<a class="inputDateButton" href="javascript:;" >选择</a>
					</td>
					<td>
						<label>创建结束日期：</label> 
						<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" />
						<a class="inputDateButton" href="javascript:;">选择</a>
					</td>
					<td>
						<label>备注：</label> 
						<input type="text" name="filter_and_remark_LIKE_S" value="${filter_and_remark_LIKE_S}" class="input"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>批量条形码：</label>
	                    <input type="text" id="jyCodes" name="filter_and_code_IN_S" readonly="readonly" value="${filter_and_code_IN_S}"/>
					</td>
					<td>
						<input type="file" id="affix" name="affix"/>
						<button type="button" onclick="importMoreCodes()" style="float:right;height: 24px;line-height:100%;">导入条码</button>
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
 			<web:exportExcelTag pagerFormId="pagerFindForm"
								pagerMethodName="findByPage"
								actionAllUrl="org.hpin.foreign.web.ErpReportScheduleJYAction"
								informationTableId="exportExcelTable"
								fileName="ScheduleJY"></web:exportExcelTag>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	
	<table class="list" width="100%" layoutH="185" id="exportExcelTable"> 
		<thead>
			<tr>
				<th width="35" nowrap="true">序号</th>
				<th width="100"  export= "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th width="100"  export= "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th width="150"  export= "true" columnEnName = "combo" columnChName = "套餐" >套餐</th>
				<th width="80"  export= "true" columnEnName = "eventsNo" columnChName = "场次号" >场次号</th>
				<th width="100"  export= "true" columnEnName = "batchNo" columnChName = "批次号" >批次号</th>
				<th width="100"  export= "true" columnEnName = "reportName" columnChName = "报告名称" >报告名称</th>
				<th width="80"  export= "true" columnEnName = "publishedDate" columnChName = "发布报告时间" >发布报告时间</th>
				<th width="90"  export= "true" columnEnName = "entryDate" columnChName = "接收报告时间" >接收报告时间</th>
				<th width="100"  export= "true" columnEnName = "createTime" columnChName = "创建时间" >创建时间</th>
				<th width="90"  export= "true" columnEnName = "updateTime" columnChName = "修改时间" >修改时间</th>
				<th width="100"  export= "true" columnEnName = "state" columnChName = "状态" >状态</th>
				<th width="100"  export= "true" columnEnName = "counter" columnChName = "操作次数" >操作次数</th>
				<th width="80"  export= "true" columnEnName = "remark" columnChName = "备注" >备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="listReportScheduleJY" varStatus="status">
				<tr>
					<td align="center" width="35">${status.count}</td>
					<td align="center" width="100">${listReportScheduleJY.code}</td> 
					<td align="center" width="100">${listReportScheduleJY.name}</td>
					<td align="center" width="100">${listReportScheduleJY.combo}</td>
					<td align="center" width="100">${listReportScheduleJY.eventsNo}</td>
					<td align="center" width="100">${listReportScheduleJY.batchNo}</td>
					<td align="center" width="100">${listReportScheduleJY.reportName}</td>
					<td align="center" width="100">${fn:substring(listReportScheduleJY.samplingDate,0,19)}</td>
					<td align="center" width="100">${fn:substring(listReportScheduleJY.publishedDate,0,19)}</td>
					<td align="center" width="100">${fn:substring(listReportScheduleJY.createTime,0,19)}</td>
					<td align="center" width="100">${fn:substring(listReportScheduleJY.updateTime,0,19)}</td>
					<td align="center" width="100">
						<c:if test="${listReportScheduleJY.status=='0'}">未完成</c:if>
						<c:if test="${listReportScheduleJY.status=='1'}">已完成</c:if>
						<c:if test="${listReportScheduleJY.status=='9'}">信息不匹配</c:if>
					</td>
					<td align="center" width="100">${listReportScheduleJY.counter}</td>
					<td align="center" width="100" title="${listReportScheduleJY.remark}">${listReportScheduleJY.remark}</td>
				</tr>		
			</c:forEach>
		</tbody>
	</table>
	
</div> 