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
};

var detail = function(v_status, v_createTime, v_configId, v_field){
	
	var obj = {
			filter_and_identityStatus_EQ_T: v_status,			
			configId: v_configId,
			field: v_field
			};
	
	if(v_createTime=="合计"){
		obj.filter_and_createTime_GE_T = $("[name='filter_and_createTime_GE_T']").val();
		obj.filter_and_createTime_LE_T = $("[name='filter_and_createTime_LE_T']").val();
	}else{
		obj.filter_and_createTime_GE_T = v_createTime;
		obj.filter_and_createTime_LE_T = v_createTime;
	}
		
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("liststat105_1_s", "${path }/statistics/queryaction!performSearchDo.action", { title:"场次报表明细", fresh:true, data:obj });

};

</script>

<!-- ?filter_and_reportStatus_EQ_T="
		+v_reportStatus+"&filter_and_createTime_EQ_T="+v_createTime+"&configId="+v_configId -->
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path }/statistics/queryaction!queryReport.action'){this.action = '${path }/statistics/queryaction!queryReport.action' ;};return navTabSearch(this); " action="${path }/statistics/queryaction!queryReport.action" method="post" rel="pagerForm">
	<div class="searchBar">
	  <table class="pageFormContent">
	 
      			<tr>
			 
              <td>
            	<input type="hidden" name="configId" value="${actionMap['configId'] }">
                <label>开始日期：</label>
				<input type="text" name="filter_and_createTime_GE_T"  id="d7111" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d7112\')}'})" class="required"  readonly="readonly" value="${ actionMap['filter_and_createTime_GE_T']}" /><a class="inputDateButton" href="javascript:;">选择</a>
			</td>
			<td>
				<label>至：</label>
				<input type="text" name="filter_and_createTime_LE_T"  id="d7112" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d7111\')}'})" class="required"  readonly="readonly" value="${ actionMap['filter_and_createTime_LE_T']}" /><a class="inputDateButton" href="javascript:;">选择</a>
			</td>
             <td width="100px;">
           <div style="margin-top:0" class="subBar">
            <ul>
			  <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">查找</button></div></div></li>
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
		<div style="margin-bottom:0px" class="tip">
			<span>报表</span>
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
										fileName="stat105"
										configId="stat105"
										></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="190" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>   
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "统计时间">统计时间</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "查询次数">查询次数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "身份不匹配数">身份不匹配数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "全信息匹配数">全信息匹配数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "身份匹配手机不匹配数">身份匹配手机不匹配数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "身份匹配手机为空数">身份匹配手机为空数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "身份匹配数">身份匹配数</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "有报告数">有报告数</th>
						<th nowrap="nowrap" export = "true" columnIndex = "8" columnChName = "无报告数">无报告数</th>
						<th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "查询出报告率">查询出报告率</th>
						<th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "身份匹配出报告率">身份匹配出报告率</th>
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
						<td align="center"> 
							<c:choose>
								<c:when test="${!empty members[2] && members[2]!=0}">
									<a href="#" onclick="detail('0', '${members[0]}', '${actionMap['configId']}', 'f105_1')">
										<span style="font-size:15px; font-weight:bold;">${members[2]}</span>
									</a>
								</c:when>
								<c:otherwise>
									${members[2]}
								</c:otherwise>
							</c:choose>
						</td>
						<td align="center">  ${members[3]}</td>
						<td align="center">  
							<c:choose>
								<c:when test="${!empty members[4] && members[4]!=0}">
									<a href="#" onclick="detail('2', '${members[0]}', '${actionMap['configId']}', 'f105_1')">
										<span style="font-size:15px; font-weight:bold;">${members[4]}</span>
									</a>
								</c:when>
								<c:otherwise>
									${members[4]}
								</c:otherwise>
							</c:choose>
						</td>
						<td align="center">  ${members[5]}</td>
						<td align="center">  ${members[6]}</td>
						<td align="center">  ${members[7]}</td>
						<td align="center">  ${members[8]}</td>
						<td align="center">  ${members[9]}</td>
						<td align="center">  ${members[10]}</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>