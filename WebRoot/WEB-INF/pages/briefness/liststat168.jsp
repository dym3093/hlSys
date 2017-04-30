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
				<td><label>场次号：</label><input name="filter_and_eventsNo_LIKE_S" value="${actionMap.filter_and_eventsNo_LIKE_S }" class="textInput"></td>
				<td><label>批次号：</label><input name=filter_and_batchNo_LIKE_S value="${actionMap.filter_and_batchNo_LIKE_S }" class="textInput"></td>
				<td><label>条形码：</label><input name="filter_and_code_LIKE_S" value="${actionMap.filter_and_code_LIKE_S }" class="textInput"></td>
			</tr>
			<tr>
				<td><label>省份：</label>
					<select id="province" name="filter_and_provice_EQ_S"
						rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
						ref="city"
						refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
							<option value="${filter_and_provice_EQ_S}"></option>
					</select>
				</td>
				<td><label>城市：</label>
					<select id="city"
						name="filter_and_city_EQ_S" rel="iselect">
							<option value="${filter_and_city_EQ_S}"></option>
					</select>
				</td>
				<td><label>客户姓名：</label><input name=filter_and_name_LIKE_S value="${actionMap.filter_and_name_LIKE_S }" class="textInput"></td>		
			</tr>
			<tr>
				<td><label>采样开始日期：</label>
					<input type="text" name="filter_and_samplingDate_GE_T" value="${ actionMap.filter_and_samplingDate_GE_T }"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" />
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><label>采样结束日期：</label>
					<input type="text" name="filter_and_samplingDate_LE_T" value="${ actionMap.filter_and_samplingDate_LE_T }"   id="d7112" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" readonly="readonly" />
					<a class="inputDateButton" href="javascript:;">选择</a>	
				</td>
				<td><label>证件号：</label><input name=filter_and_idno_LIKE_S value="${actionMap.filter_and_idno_LIKE_S }" class="textInput"></td>   
			</tr>
			<tr>
				<td><label>套餐名称：</label><input name=filter_and_setmealName_LIKE_S value="${actionMap.filter_and_setmealName_LIKE_S }" class="textInput"></td>	
				<td></td>
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
										fileName="stat168"
										configId="stat168"
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
					    <th nowrap="nowrap"  export="true" columnIndex="0" columnChName="支公司" >支公司</th>
						<th nowrap="nowrap"  export="true" columnIndex="1" columnChName="场次号" >场次号</th>
						<th nowrap="nowrap"  export="true" columnIndex="2" columnChName="条形码" >条形码</th>
						<th nowrap="nowrap"  export="true" columnIndex="3" columnChName="姓名" >姓名</th>
						<th nowrap="nowrap"  export="true" columnIndex="4" columnChName="套餐" >套餐</th>
						<th nowrap="nowrap"  export="true" columnIndex="5" columnChName="采样日期" >采样日期</th>
						<th nowrap="nowrap"  export="true" columnIndex="6" columnChName="是否有报告" >是否有报告</th>
						<th nowrap="nowrap"  export="true" columnIndex="7" columnChName="套餐价格" >套餐价格</th>
					</tr>
				  </thead>
				  <tbody>
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
						 <td align="center">
							${status.count }
						</td> 
						<td align="center">${members[0]}</td>
						<td align="center">${members[1]}</td>
						<td align="center">${members[2]}</td>
						<td align="center">${members[3]}</td>
			    		<td align="center">${members[4]}</td> 
			    		<td align="center">${members[5]}</td> 
			    		<td align="center">${members[6]}</td> 
			    		<td align="center">${members[7]}</td> 
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>