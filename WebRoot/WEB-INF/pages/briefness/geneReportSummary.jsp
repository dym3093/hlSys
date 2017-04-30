<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>

<script type="text/javascript">

function submitForm(){
	var flag = false;
	$(".required",navTab.getCurrentPanel()).each(function(){
		if($(this).val()==""){
			$(this).focus();
			flag = true;
		}
	});
	if(flag){
		alert("您有必选项没有填写请确认");
		return false;
	}
	$("#pagerFindForm",navTab.getCurrentPanel()).submit();
}

function downLoadSumary(){
	var id = '';
	var ids='';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				id = n.value;
				count += count + 1;
				status = $(this).parent().next().text();
				ids = id+","+ids;
			});
	if (count == 0) {
		alert('请选择至少一条信息！');
		return;
	}else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		$.ajax({
			type: "post",
			cache: false,
			data:{"ids":ids.substring(0, ids.length-1)},
			url: "${path}/statistics/queryaction!createExSeFilePath.action",
			success: function(data){
				var obj=eval("("+data+")");
				alert(obj.path);
				window.open(obj.path);
			},
			error:function(){
				alert("下载失败，请检查服务器！");
				return;
			}
		});
	}
}
</script>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path }/statistics/queryaction!queryGeneReportSummary.action'){this.action = '${path }/statistics/queryaction!queryGeneReportSummary.action' ;};return navTabSearch(this); " action="${path }/statistics/queryaction!queryGeneReportSummary.action" method="post" rel="pagerForm">
	<div class="searchBar">
	<input type="hidden" name="configId" value="${actionMap['configId'] }">
	  <table class="pageFormContent num1_tab">
		<tr>
			<td>
                <label>项目编码：</label>
                <input type="text" name="filter_and_itemCode_EQ_S" value="${filter_and_itemCode_EQ_S}">
			</td>
			<td>
                <label>项目名称：</label>
                <input type="text" name="filter_and_itemName_EQ_S" value="${filter_and_itemName_EQ_S}">
			</td>
			<td>
                <label>项目负责人：</label>
                <input type="text" name="filter_and_itemHead_EQ_S" value="${filter_and_itemHead_EQ_S}">
			</td>
		</tr>
	  		<tr>
			<td >
				<label>开始日期：</label>
				<input type="text" name="filter_and_startTime_GE_T"  id="d7111"  onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" value="${filter_and_startTime_GE_T}" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</td>
			<td>
				<label>至：</label>
				<input type="text" name="filter_and_startTime_LE_T"  id="d7112" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" class="required"  readonly="readonly" value="${filter_and_startTime_LE_T}" />
				<a class="inputDateButton" href="javascript:;">选择</a>	
			</td>
             <td>
		        <div >	
		            <ul>
					  <li><div class="buttonActive"><div class="buttonContent"><button type="submit" >查找</button></div></div></li>
					  <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div></li>
					</ul>
				</div>
		  	</td>
		</tr>
	  </table>
	</div>
	</form>
  </div>
	<div class="tabs">
		<div style="margin-bottom: 0px;"class="tip">
			<span>报表</span>
		</div>
    <div class="tabsContent" style="background-color:#FFF">
	<div style="width:100%">
		<div id="jbsxBox" class="unitBox">
			<div class="pageContent">
				<div class="panelBar">
				<ul class="toolBar">
					<c:if test="${page.results != null && !empty page.results }">
						<li><a class="edit" href="javascript:void(0);" onclick="downLoadSumary()"><span>导出Excel</span></a></li>
					</c:if>
				</ul>
				<jsp:include page="/common/pagination.jsp" />
				</div>
				<table class="list" width="100%" layoutH="190" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> 
					    <th nowrap="nowrap" export = "true" columnIndex = "month" columnChName = "开始时间">开始时间</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "itemCode" columnChName = "项目编码">项目编码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "itemHead" columnChName = "项目负责人">项目负责人</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "itemName" columnChName = "项目名称">项目名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "haveReportNum" columnChName = "已出报告人数">会员人数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "settlementAmountBX" columnChName = "保险公司结算金额">保险公司结算金额</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "settlementAmountJY" columnChName = "基因公司结算金额">基因公司结算金额</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "moveMatterAmount" columnChName = "物料领用金额">物料领用金额</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "createDate" columnChName = "创建时间">创建时间</th>
					</tr>
				  </thead>
				  <tbody>
					
					<c:forEach items="${page.results }" var="geneSummary" varStatus="status">
					  <tr>
						 <td align="center">
						 	<input type="checkbox" name="ids" value="${geneSummary.id}">
							${status.count }
						</td> 
						<td align="center">  ${geneSummary.startTime}</td>
						<td align="center">  ${geneSummary.itemCode}</td>
						<td align="center">  ${geneSummary.itemHead}</td>
						<td align="center">  ${geneSummary.itemName}</td>
						<td align="center">  ${geneSummary.sumNum}</td>
						<td align="center">  ${geneSummary.settlementAmountBX}</td>
						<td align="center">  ${geneSummary.settlementAmountJY}</td>
						<td align="center">  ${geneSummary.moveMatterAmount}</td>
						<td align="center">  ${geneSummary.createDate}</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>