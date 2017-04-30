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
	  			<td >
					<label>邀约日期：</label>
					<input type="text" name="filter_and_createTime_GE_T"  id="d7111"  onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" value="${filter_and_createTime_GE_T}" />
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
					<label>至：</label>
					<input type="text" name="filter_and_createTime_LE_T"  id="d7112" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" class="required"  readonly="readonly" value="${filter_and_createTime_LE_T}" />
					<a class="inputDateButton" href="javascript:;">选择</a>	
				</td>
				<td><label>支公司：</label>
      		<input type="text" id="id" name="filter_and_branchCompanyId_EQ_S" bringbackname="customer.branchCommanyName" value="${filter_and_branchCompanyId_EQ_S}" />
					<input type="hidden" id="branchCompany" name="aaaa" bringbackname="customer.branchCommanyName" value="${aaaa} " readonly="readonly"/>
					<a class="btnLook" href="${path}/resource/customerRelationShip!findCustomerRelationShip.action" lookupGroup="customer"  target="dialog" width="800" height="480">查找带回</a></td>
	  		</tr>
	  		<tr>
			<td>
				<label>营销员姓名：</label>
				<input type="text" name="filter_and_salesMan_LIKE_S" class="required" value="${filter_and_salesMan_LIKE_S}" />
			</td>
			<td >
				<label>营销员工号：</label>
				<input type="text" name="filter_and_salesManNo_LIKE_S" class="required" value="${filter_and_salesManNo_LIKE_S}" />
			</td>
			<td>
				<label>检测码：</label>
				<input type="text" name="filter_and_code_EQ_S" value="${filter_and_code_EQ_S}" />
			</td>
			</tr>
			<tr>
			<td>
				<label>检测人姓名：</label>
				<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}" />
			</td>
			<td>
				<label>检测人证件号：</label>
				<input type="text" name="filter_and_idno_EQ_S" value="${filter_and_idno_EQ_S}" />
			</td>
			<td >
				<label>手机号：</label>
				<input type="text" name="filter_and_phone_EQ_S" value="${filter_and_phone_EQ_S}" />
			</td>
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
										fileName="stat126"
										configId="stat126"
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
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "保险公司">保险公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "市公司">市公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "扫码支公司">扫码支公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "营销员姓名">营销员姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "营销员工号">营销员工号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "客户检测码">客户检测码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "姓名">姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "证件号">证件号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "8" columnChName = "性别">性别</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "年龄">年龄</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "生日">生日</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "11" columnChName = "电话">电话</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "12" columnChName = "身高">身高</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "13" columnChName = "体重">体重</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "14" columnChName = "信息采集日期">信息采集日期</th>
					</tr>
				  </thead>
				  <tbody>
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
						 <td align="center">
							${status.count }
						</td> 
						<td align="center">  ${members[0]}</td>
						<td align="center">  ${members[1]}</td>
						<td align="center">  ${members[2]}</td>
						<td align="center">  ${members[3]}</td>
						<td align="center">  ${members[4]}</td>
						<td align="center">  ${members[5]}</td>
						<td align="center">  ${members[6]}</td>
						<td align="center">  ${members[7]}</td>
						<td align="center">  ${members[8]}</td>
						<td align="center">  ${members[9]}</td>
						<td align="center">  ${members[10]}</td>
						<td align="center">  ${members[11]}</td>
						<td align="center">  ${members[12]}</td>
						<td align="center">  ${members[13]}</td>
						<td align="center">  ${members[14]}</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>