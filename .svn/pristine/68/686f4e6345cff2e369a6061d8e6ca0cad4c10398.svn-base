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

/**
 * 关闭按钮触发方法
 * 该方法用于关闭已邀约数据,使用与重新邀约;
 * create by henry.xu 20170324;
 */
function closeUserInfo(code) {
	//首先判断是否要关闭.
	alertMsg.confirm("请确定是否要关闭条码为: "+code+"的客户信息!", {
		okCall: function(){
			$.ajax({
				type: "post",
				cache : false,
				async : false,
				data:{"code":code},
				url: "${path }/report/reportQuery!ajaxCloseUserInfo.action",
				success: function(data){
					var data= eval("("+data+")");
					if(data.result) {
						$("#pagerFindForm").submit();
					}
				},
				error :function(){
					alertMsg.warn("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
	});

	//然后计入后台程序修改.
}
</script>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path }/statistics/queryaction!queryReport.action'){this.action = '${path }/statistics/queryaction!queryReport.action' ;};return navTabSearch(this); " action="${path }/statistics/queryaction!queryReport.action" method="post" rel="pagerForm">
	<div class="searchBar">
	<input type="hidden" name="configId" value="${actionMap['configId']}">
	  <table class="pageFormContent num1_tab">
	  		<tr>
	  			<td>
				<label>客户姓名：</label>
				<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}" />
			</td>
			<td>
				<label>支公司：</label>
				<input type="text" id="id" name="filter_and_branchCompany_EQ_S" bringbackname="customer.branchCommanyName" value="${filter_and_branchCompany_EQ_S}" />
					<input type="hidden" id="branchCompany" name="aaaa" bringbackname="customer.branchCommanyName" value="${aaaa} " readonly="readonly"/>
					<a class="btnLook" href="${path}/resource/customerRelationShip!findCustomerRelationShip.action" lookupGroup="customer"  target="dialog" width="800" height="480">查找带回</a>
			</td>
			<td>
      			<label>总公司：</label>
      			<input type="text"	id="ownedCompanyId" name="filter_and_ownedCompanyId_EQ_S" bringbackname="dept.customerNameSimple"	value="${filter_and_ownedCompanyId_EQ_S}" /> 
					<input type="hidden"	id="ownedCompany" name="bbbb" bringbackname="dept.customerNameSimple" value="${bbbb}" readonly="readonly" /> 
					<a class="btnLook" href="${ path }/resource/customerRelationShip!lookDept.action" lookupGroup="dept" target="dialog" width="800" height="480">查找带回</a>
			</td>
			
	  		</tr>
	  		<tr>
	  		<td>
				<label>身份证号：</label>
				<input type="text" name="filter_and_idno_EQ_S" value="${filter_and_idno_EQ_S}" />
			</td>
			<td >
				<label>手机号：</label>
				<input type="text" name="filter_and_phone_EQ_S" value="${filter_and_phone_EQ_S}" />
			</td>
			<td >
				<label>检测码：</label>
				<input type="text" name="filter_and_code_EQ_S" value="${filter_and_code_EQ_S}" />
			</td>
			</tr>
			<tr>
			<td >
				<label>营销员工号：</label>
				<input type="text" name="filter_and_salesmanno_EQ_S" value="${filter_and_salesmanno_EQ_S}" />
			</td>
			<td><label>省：</label>
				<select id="province" name="filter_and_provice_EQ_S"
						rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
						ref="city"
						refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
							<option value="${filter_and_provice_EQ_S}"></option>
					</select>
				</td>
				<td><label>市：</label>
				<select id="city"
						name="filter_and_city_EQ_S" rel="iselect">
							<option value="${filter_and_city_EQ_S}"></option>
					</select>
				</td>
				</tr>
				<tr>
      		
             <td>
					<label>项目类型：</label>
					<select name="filter_and_projecttype_S" style="width:195px;margin-top:6px;margin-left:5px; line-height: 25px;">
						<option value="" selected='selected'>---请选择---</option>
						<option value="PCT_001" ${filter_and_projecttype_S == 'PCT_001' ? "selected='selected'" : ''}>健康管理-基因</option>
						<option value="PCT_002" ${filter_and_projecttype_S == 'PCT_002' ? "selected='selected'" : ''}>健康管理-癌筛</option>
						<option value="PCT_003" ${filter_and_projecttype_S == 'PCT_003' ? "selected='selected'" : ''}>分子检测</option>
						<option value="PCT_004" ${filter_and_projecttype_S == 'PCT_004' ? "selected='selected'" : ''}>健康管理-无创-生物电</option>
						<option value="PCT_005" ${filter_and_projecttype_S == 'PCT_005' ? "selected='selected'" : ''}>健康管理-无创-微磁</option>
						<option value="PCT_006" ${filter_and_projecttype_S == 'PCT_006' ? "selected='selected'" : ''}>其他</option>
					</select>
				</td>
			<td>
				<label>开始日期：</label>
				<input type="text" name="filter_and_createTime_GE_T" value="${ actionMap.filter_and_createTime_GE_T }"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</td>
			<td>
				<label>结束日期：</label>
				<input type="text" name="filter_and_createTime_LE_T" value="${ actionMap.filter_and_createTime_LE_T }"   id="d7112" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>	
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
										fileName="stat127"
										configId="stat127"
										></web:exportExcelTag4Jdbc>
					</c:if>
				</ul>
				<jsp:include page="/common/pagination.jsp" />
				</div>
				<table class="list" width="100%" layoutH="170" id = "exportTable"> 
				   <thead>
					<tr>
					    <!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>  -->
					    <th>序号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "保险公司">保险公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "市公司">市公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "扫码支公司">扫码支公司</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "营销员姓名">营销员姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "营销员工号">营销员工号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "项目类型">项目类型</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "客户检测码">客户检测码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "姓名">姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "8" columnChName = "证件号">证件号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "性别">性别</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "年龄">年龄</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "11" columnChName = "生日">生日</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "12" columnChName = "电话">电话</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "13" columnChName = "身高">身高</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "14" columnChName = "体重">体重</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "15" columnChName = "信息采集日期">信息采集日期</th>
					    <web:security tag="yaoyueClose"><th>操作</th></web:security>
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
						<td align="center">  ${members[15]}</td>
						<web:security tag="yaoyueClose">
						<td align="center">
							<c:if test="${fn:contains(members[8], '-close')}">
								已关闭
							</c:if>
							
							<c:if test="${!fn:contains(members[8] , '-close')}">
								<a class="buttonActive" href="javascript:;" onclick="closeUserInfo('${members[6]}')"><span>关闭</span></a>
							</c:if> 
						</td>
						</web:security>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>