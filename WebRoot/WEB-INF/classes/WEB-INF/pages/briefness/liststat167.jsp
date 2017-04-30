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

</script>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path }/statistics/queryaction!queryReport.action'){this.action = '${path }/statistics/queryaction!queryReport.action' ;};return navTabSearch(this); " action="${path }/statistics/queryaction!queryReport.action" method="post" rel="pagerForm">
	<div class="searchBar">
	<input type="hidden" name="configId" value="${actionMap['configId']}">
	  <table class="pageFormContent num1_tab">
	  		<tr>
				
				<td><label>场次开始日期：</label><input type="text" name="filter_and_createTime_GE_T"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" value="${actionMap['filter_and_createTime_GE_T']}" /><a class="inputDateButton" href="javascript:;">选择</a></td>
				<td><label>场次结束日期：</label><input type="text" name="filter_and_createTime_LE_T"  id="d7112" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" class="required"  readonly="readonly" value="${actionMap['filter_and_createTime_LE_T']}" /><a class="inputDateButton" href="javascript:;">选择</a></td>
				<%-- <td><label>支公司：</label><input type="text" id="id" name="filter_and_branchCommany_EQ_S" bringbackname="customer.branchCommanyName" value="${actionMap['filter_and_branchCommany_EQ_S']}" />
					<input type="hidden" id="branchCompany" name="aaaa" bringbackname="customer.branchCommanyName" value="${actionMap['filter_and_branchCommany_EQ_S']} " readonly="readonly"/>
					<a class="btnLook" href="${path}/resource/customerRelationShip!findCustomerRelationShip.action" lookupGroup="customer"  target="dialog" width="800" height="480">查找带回</a>
				</td> --%>
				<td>
					<div class="subBar">	
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
						<!-- <li><a class="edit" href="javascript:void(0);" onclick="downLoadSumary()"><span>导出Excel</span></a></li> -->
						<web:exportExcelTag4Jdbc 
										pagerFormId="pagerFindForm" 
										pagerMethodName="queryResults" 
										actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
										informationTableId="exportTable"
										fileName="stat167"
										configId="stat167"
										></web:exportExcelTag4Jdbc>
					</c:if>
				</ul>
				<jsp:include page="/common/pagination.jsp" />
				</div>
				<table class="list" width="100%" layoutH="100" id = "exportTable"> 
				   <thead>
					<tr>
					    <!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>  -->
					    <th>序号</th>
					    <th nowrap="nowrap" width="120px" export="true" columnIndex="0" columnChName="场次号" >场次号</th>
						<th nowrap="nowrap" width="150px" export="true" columnIndex="1" columnChName="批次号" >批次号</th>
						<th nowrap="nowrap" width="200px" export="true" columnIndex="2" columnChName="场次日期" >场次日期</th>
						<th nowrap="nowrap" width="120px" export="true" columnIndex="3" columnChName="省份" >省份</th>
						<th nowrap="nowrap" width="80px"  export="true" columnIndex="4" columnChName="城市" >城市</th>
						<th nowrap="nowrap" width="180px" export="true" columnIndex="5" columnChName="支公司名称" >支公司名称</th>
						<th nowrap="nowrap" width="120px" export="true" columnIndex="6" columnChName="总公司名称" >总公司名称</th>
						<th nowrap="nowrap" width="100px" export="true" columnIndex="7" columnChName="总人数" >总人数</th>
						<th nowrap="nowrap" width="100px" export="true" columnIndex="8" columnChName="已出报告人数" >已出报告人数</th>
					</tr>
				  </thead>
				  <tbody>
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
						 <td align="center">
							${status.count }
						</td> 
						<td align="center">
							<a href="${path }/statistics/queryaction!queryReport.action?filter_and_eventsNo_EQ_S=${members[0]}&configId=stat167_s" target="navTab">${members[0]}</a>
						</td>
						<td align="center">${members[1]}</td>
						<td align="center">${members[2]}</td>
						<td align="center">${members[3]}</td>
			    		<td align="center">${members[4]}</td> 
			    		<td align="center">${members[5]}</td> 
			    		<td align="center">${members[6]}</td> 
			    		<td align="center">${members[7]}</td> 
			    		<td align="center">${members[8]}</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>