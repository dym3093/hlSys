<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>

<div class="pageHeader">
	<form
		id="pagerFindForm" 
		action="${path }/statistics/queryaction!queryReport.action" 
		method="post" 
		rel="pagerForm" 
		onsubmit="this.action='${path }/statistics/queryaction!queryReport.action'; return navTabSearch(this); ">
        <input type="hidden" name="configId" value="${actionMap.configId }" />
	
	<div class="searchBar">
	  <table class="pageFormContent num1_tab">
      	<tr>
      		<td><label>场次号：</label></td>
      		<td><input name="filter_and_eventsNo_LIKE_S" value="${actionMap.filter_and_eventsNo_LIKE_S }" class="textInput"></td>
      		<td></td>
      		<td><label>批次号：</label></td>
      		<td><input name=filter_and_batchNo_LIKE_S value="${actionMap.filter_and_batchNo_LIKE_S }" class="textInput"></td>
      		<td></td>
      		<td><label>姓名：</label></td>
      		<td><input name=filter_and_name_LIKE_S value="${actionMap.filter_and_name_LIKE_S }" class="textInput"></td>
      		<td></td>
      	</tr>
      	
      	<tr>
      		<td><label>条码：</label></td>
      		<td><input name="filter_and_code_LIKE_S" value="${actionMap.filter_and_code_LIKE_S }" class="textInput"></td>
      		<td></td>
      		<td><label>手机号：</label></td>
      		<td><input name=filter_and_phone_LIKE_S value="${actionMap.filter_and_phone_LIKE_S }" class="textInput"></td>
      		<td></td>
      		<td><label>套餐：</label></td>
      		<td><input name=filter_and_setmealName_LIKE_S value="${actionMap.filter_and_setmealName_LIKE_S }" class="textInput"></td>
      		<td></td>
      	</tr>
      	
      	<tr>
            <td><label>证件号：</label></td>
      		<td><input name=filter_and_idno_LIKE_S value="${actionMap.filter_and_idno_LIKE_S }" class="textInput"></td>
      		<td></td>
            <td><label>采样开始日期：</label></td>
			<td>
				<input type="text" name="filter_and_samplingDate_GE_T" value="${ actionMap.filter_and_samplingDate_GE_T }"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</td>
			<td></td>
			<td><label>采样结束日期：</label></td>
			<td>
				<input type="text" name="filter_and_samplingDate_LE_T" value="${ actionMap.filter_and_samplingDate_LE_T }"   id="d7112" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>	
			</td>            
		</tr>
		
		<tr>
            <td><label>检测机构：</label></td>
      		<td>
				<select id="testInstitution" name="filter_and_testInstitution_LIKE_S" style="width:195px;margin-top:5px;margin-left:5px">
						<c:if test="${filter_and_testInstitution_LIKE_S==''}">
							<option value="" selected='selected'>---请选择---</option>
							<option value="南方" ${filter_and_testInstitution_LIKE_S == '南方' ? "selected='selected'" : ''}>南方检测</option>
							<option value="金域" ${filter_and_testInstitution_LIKE_S == '金域' ? "selected='selected'" : ''}>金域检测</option>
							<option value="艾迪康" ${filter_and_testInstitution_LIKE_S == '艾迪康' ? "selected='selected'" : ''}>艾迪康检测</option>
							<option value="北方" ${filter_and_testInstitution_LIKE_S == '北方' ? "selected='selected'" : ''}>北方检测</option>
							<option value="知康" ${filter_and_testInstitution_LIKE_S == '知康' ? "selected='selected'" : ''}>知康检测</option>
							<option value="吉思朗" ${filter_and_testInstitution_LIKE_S == '吉思朗' ? "selected='selected'" : ''}>吉思朗检测</option>
						</c:if>
						<c:if test="${filter_and_testInstitution_LIKE_S!=''}">
							<c:if test="${filter_and_testInstitution_LIKE_S=='南方'}">
								<option value=""  selected='selected'>---请选择---</option>
								<option value="南方" ${filter_and_testInstitution_LIKE_S == '南方' ? "selected='selected'" : ''}>南方检测</option>
								<option value="金域" ${filter_and_testInstitution_LIKE_S == '金域' ? "selected='selected'" : ''}>金域检测</option>
								<option value="艾迪康" ${filter_and_testInstitution_LIKE_S == '艾迪康' ? "selected='selected'" : ''}>艾迪康检测</option>
							<option value="北方" ${filter_and_testInstitution_LIKE_S == '北方' ? "selected='selected'" : ''}>北方检测</option>
							<option value="知康" ${filter_and_testInstitution_LIKE_S == '知康' ? "selected='selected'" : ''}>知康检测</option>
							<option value="吉思朗" ${filter_and_testInstitution_LIKE_S == '吉思朗' ? "selected='selected'" : ''}>吉思朗检测</option>
							</c:if>
							<c:if test="${filter_and_testInstitution_LIKE_S!='南方'}">
								<option value=""  selected='selected'>---请选择---</option>
								<option value="金域" ${filter_and_testInstitution_LIKE_S == '金域' ? "selected='selected'" : ''}>金域检测</option>
								<option value="南方" ${filter_and_testInstitution_LIKE_S == '南方' ? "selected='selected'" : ''}>南方检测</option>
								<option value="艾迪康" ${filter_and_testInstitution_LIKE_S == '艾迪康' ? "selected='selected'" : ''}>艾迪康检测</option>
							<option value="北方" ${filter_and_testInstitution_LIKE_S == '北方' ? "selected='selected'" : ''}>北方检测</option>
							<option value="知康" ${filter_and_testInstitution_LIKE_S == '知康' ? "selected='selected'" : ''}>知康检测</option>
							<option value="吉思朗" ${filter_and_testInstitution_LIKE_S == '吉思朗' ? "selected='selected'" : ''}>吉思朗检测</option>
							</c:if>
						</c:if>
					</select>
			</td>
      		<td></td>
            <td><label>导入开始日期：</label></td>
			<td>
				<input type="text" name="filter_and_createTime_GE_T" value="${ actionMap.filter_and_createTime_GE_T }"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</td>
			<td></td>
			<td><label>导入结束日期：</label></td>
			<td>
				<input type="text" name="filter_and_createTime_LE_T" value="${ actionMap.filter_and_createTime_LE_T }"   id="d7112" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>	
			</td>            
		</tr>
		
		<tr>
				<td><label>省：</label></td> 
				<td><select id="province" name="filter_and_provice_EQ_S"
						rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
						ref="city"
						refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
							<option value="${filter_and_provice_EQ_S}"></option>
					</select>
				</td>
				<td></td>
				<td><label>市：</label></td>
				<td><select id="city"
						name="filter_and_city_EQ_S" rel="iselect">
							<option value="${filter_and_city_EQ_S}"></option>
					</select>
				</td>
				<td></td>
				<td><label>客户标记：</label></td>
				<td>
					<select name="filter_and_isDeleted_S" style="width:195px;margin-top:6px;margin-left:5px; line-height: 25px;">
						<option value="" selected='selected'>---请选择---</option>
						<option value="0" ${filter_and_isDeleted_S == '0' ? "selected='selected'" : ''}>正常样本</option>
						<option value="2" ${filter_and_isDeleted_S == '2' ? "selected='selected'" : ''}>异常样本</option>
					</select>
				</td>
				<td></td>				
		</tr>
			
		<tr>
            <td><label>项目编码：</label></td>
      		<td><input name="filter_and_projectcode_like_S" value="${actionMap.filter_and_projectcode_like_S }" class="textInput"></td>
      		<td></td>
      		<td><label>项目名称：</label></td>
      		<td><input name=filter_and_projectname_like_S value="${actionMap.filter_and_projectname_like_S }" class="textInput"></td>
      		<td></td>
      		<td></td>
      		<td>
		        <div style="width:100px;" class="subBar">	
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
		<span>客户信息</span>
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
										pagerMethodName="queryResults" 
										actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
										informationTableId="exportTable"
										fileName="stat133"
										configId="stat133"></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="230" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>   
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "场次号">场次号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "批次号">批次号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "条形码">条形码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "姓名">姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "性别">性别</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "年龄">年龄</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "身份证">身份证</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "电话">电话</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "8" columnChName = "部门">部门</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "支公司名称">支公司名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "总公司名称">总公司名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "11" columnChName = "套餐">套餐</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "12" columnChName = "采样日期">采样日期</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "13" columnChName = "导入日期">导入日期</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "14" columnChName = "报告查看">报告查看</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "15" columnChName = "报告匹配日期">报告匹配日期</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "16" columnChName = "备注">备注</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "17" columnChName = "项目编码">项目编码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "18" columnChName = "项目名称">项目名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "19" columnChName = "项目负责人">项目负责人</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "20" columnChName = "项目对接人">项目对接人</th>
				 	    <th nowrap="nowrap" export = "true" columnIndex = "21" columnChName = "营销员">营销员</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "22" columnChName = "营销员工号">营销员工号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "23" columnChName = "快递单号">快递单号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "24" columnChName = "客户标记">客户标记</th>
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
						<td align="center" name="report_td" path="${members[14]}">${members[14]}</td>
						<td align="center">  ${members[15]}</td>
						<td align="center">  ${members[16]}</td>
						<td align="center">  ${members[17]}</td>
						<td align="center">  ${members[18]}</td>
						<td align="center">  ${members[19]}</td>
						<td align="center">  ${members[20]}</td>
						<td align="center">  ${members[21]}</td>
						<td align="center">  ${members[22]}</td>
						<td align="center">  ${members[23]}</td>
						<td align="center">  ${members[24]}</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>

<script>

	$(function() {
		$("td[name='report_td']").each(function() {
			var str = $(this).attr("path");
			var htmlStr = "";
			if(str != null && str != "") {
				var arr = str.split(",");
				if(arr.length > 1) {
					htmlStr += "<a href='"+arr[0]+"'>"+arr[0].substring(arr[0].length-8, arr[0].length)+"</a>";
					htmlStr += ",";
					htmlStr += "<a href='"+arr[1]+"'>"+arr[1].substring(arr[1].length-8, arr[1].length)+"</a>";
				}else {
					htmlStr += "<a href='"+arr[0]+"'>"+arr[0].substring(arr[0].length-8, arr[0].length)+"</a>";
				}
			}
			
			$(this).html(htmlStr);
		});
	
	})

</script>