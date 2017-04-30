<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">

//异常任务
function exceptionSettleTask(){
	var ids = '';		//id状态字符串
	var id = '';		//id
	var idArray = [];
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				if(ids.length>0){
					idArray = ids.replaceAll(" ", "").split(",");
					id = idArray[0];
				}
			});
	
}

function downLoadPrintTask(id){
	$.ajax({
		type: "post",
		cache: false,
		data:{"id":id},
		url: "${path}/events/erpCustomer!createExSeFilePath.action",
		success: function(data){
			var obj=eval("("+data+")");
			window.location.href=obj.path;
		},
		error:function(){
			alert("下载失败，请检查服务器！");
			return;
		}
	});
}

</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/erpCustomer!listCustomerFail.action')
	{this.action = '${path}/events/erpCustomer!listCustomerFail.action' ;} ;return navTabSearch(this);" 
	action="${path}/events/erpCustomer!listCustomerFail.action" method="post"  rel="pagerForm" id="pagerFindForm">
		<table class="pageFormContent">
			<tr>
				
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
			</tr>
			<tr>
				<td>
					<label>任务号：</label> 
					<input type="text" name="filter_and_failBtachNo_LIKE_S" value="${filter_and_failBtachNo_LIKE_S}"/>
				</td>  
				<td></td>
         		<td><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> -->
				
				<th>序号</th>
                <!--<th  export = "true" columnEnName = "id" columnChName = "id" >id</th>-->
                <th columnEnName = "failBtachNo" columnChName = "异常批次号" >异常批次号</th>
				<th columnEnName = "createTime" columnChName = "创建时间">创建时间</th>
				<th columnEnName = "comparFailNum" columnChName = "匹配失败数量" >匹配失败数量</th>
				<th columnEnName = "" columnChName = "操作" >操作</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }" >
					<td align="center">
						<input type="checkbox" name="ids" value="${entity.id}">
						${status.count}
					</td> 
					<td align="center">${entity.failBtachNo}</td>
					<td align="center">	${entity.createTime}</td>
					<td align="center">	${entity.comparFailNum}</td>
					<td align="center">
						<div class="panelBar" style="width:220px;">
								<ul class="toolBar" >
									<li><a class="icon" 
										href="javascript:;"
										title="导出表格" onclick="downLoadPrintTask('${entity.id}')"><span></span>导出</a></li>
                        		</ul>
							</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div> 



