<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

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

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path }/statistics/queryaction!performSearchDo.action')
		{this.action = '${path }/statistics/queryaction!performSearchDo.action' ;} ;return navTabSearch(this);" 
		action="${path }/statistics/queryaction!performSearchDo.action" method="post"  rel="pagerForm" id="pagerFindForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<input type="hidden" name="filter_and_provice_EQ_S" value="${filter_and_provice_EQ_S}"/>
						<input type="hidden" name="filter_and_city_EQ_S" value="${filter_and_city_EQ_S}"/>
						<input type="hidden" name="filter_and_branchCompanyId_EQ_S" value="${filter_and_branchCompanyId_EQ_S}"/>
						<input type="hidden" name="filter_and_branchCompany_EQ_S" value="${filter_and_branchCompany_EQ_S}"/>
						<input type="hidden" name="configId" value="${configId}"/>
						<input type="hidden" name="field" value="${field}"/>
						
						<label>姓名：</label> 
						<input type="text" name="filter_and_name_LIKE_S" value="${actionMap[filter_and_name_LIKE_S]}"/>
					</td>
					<td>
						<label>条形码：</label> 
						<input type="text" name="filter_and_code_LIKE_S" value="${actionMap[filter_and_code_LIKE_S]}"/>
					</td>
					<td>
						<label>套餐名：</label> 
						<input type="text" name="filter_and_setmealName_LIKE_S" value="${actionMap[filter_and_setmealName_LIKE_S]}"/>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td><label>采样起始日期：</label> 
						<input type="text" name="filter_and_samplingDate_GEST_T"  id="d1" style="width: 170px;" 
						onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  
						readonly="readonly" value="${actionMap[filter_and_samplingDate_GEST_T]}" />
						<a class="inputDateButton" href="javascript:;" >选择</a>
					</td>
					<td><label>采样结束日期：</label> 
						<input type="text" name="filter_and_samplingDate_LEET_T" id="d2" style="width: 170px;" 
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" 
						readonly="readonly" value="${actionMap[filter_and_samplingDate_LEET_T]}" />
						<a class="inputDateButton" href="javascript:;">选择</a>
					</td>
					<td>
					</td>
					<td>
						<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearForm(this)">重置</button></div></div>
					</td>
				</tr>
					
			</table>
		</div>
	</form>
  </div>
	<div class="tabs">
	<div style="margin-bottom: 0px;"class="tip">
		<span>场次报表统计信息</span>
	</div>
    <div class="tabsContent" style="background-color:#FFF">
	<div style="width:100%">
		<div id="jbsxBox" class="unitBox">
			<div class="pageContent">
				<div class="panelBar">
					<jsp:include page="/common/pagination.jsp" />
					 <ul class="toolBar">
					<c:if test="${page.results != null && !empty page.results }">
						<web:exportExcelTag4Jdbc 
										pagerFormId="pagerFindForm" 
										pagerMethodName="findBySearchDoPage" 
										actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
										informationTableId="exportTable"
										fileName="stat107_s"
										field="${field}"
										configId="stat107_s"
										></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="190" id = "exportTable"> 
				   <thead>
					<tr>
					    <th width="40">序号</th>  				    
						<th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "场次号">场次号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "批次号">批次号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "条形码">条形码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "姓名">姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "电话">电话</th>
					    
					    <th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "身份证号">身份证号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "套餐名">套餐名</th>					    
				    	<th nowrap="nowrap" export = "true" columnIndex = "11" columnChName = "性别">性别</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "12" columnChName = "年龄">年龄</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "13" columnChName = "支公司">支公司</th>
					    
					    <th nowrap="nowrap" export = "true" columnIndex = "16" columnChName = "营销员">营销员</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "19" columnChName = "远盟销售员">远盟销售员</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "20" columnChName = "PDF报告状态">PDF报告状态</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "21" columnChName = "采样日期">采样日期</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "22" columnChName = "家族病史">家族病史</th>
					    
					    <th nowrap="nowrap" export = "true" columnIndex = "23" columnChName = "备注">备注</th>
					    
					</tr>
				  </thead>
				  <tbody>
					
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
						<td align="center">
							${status.count }
						</td>
						<td align="center">${members[0]}</td>
						<td align="center">${members[4]}</td>
						<td align="center">${members[5]}</td>
						<td align="center">${members[6]}</td>
						<td align="center">${members[7]}</td>
						
						<td align="center">${members[9]}</td>
						<td align="center">${members[10]}</td>
						<td align="center">${members[11]}</td>
						<td align="center">${members[12]}</td>
						<td align="center">${members[13]}</td>
						
						<td align="center">${members[16]}</td>
						<td align="center">${members[19]}</td>
						<td align="center">
							<c:if test="${empty members[20] || null==menbers[20]}">
								无PDF
							</c:if>
							<c:if test="${! empty members[20] }">
								有PDF报告
							</c:if>
						</td>
						<td align="center">${members[21]}</td>
						<td align="center">${members[22]}</td>
						
						<td align="center">${members[23]}</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>