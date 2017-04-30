<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>

<script type="text/javascript">
/* $(function(){
	var productId=$("#productId").val();
	if(productId==null||""){
		return;
	}else{
		$("filter_and_productId_EQ_S").val(productId);
	}
}); */
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
			 <%-- <input type="hidden" name= 'filter_and_productId_EQ_S' value="${ actionMap['filter_and_productId_EQ_S']}"/>
			 <input type="hidden" id="productId" name="filter_and_productId_EQ_S" value="${currentUser.ctiPassword}"/> --%>
             <td width="100px;">
           <div class="subBar">
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
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a><span>报表</span></a></li>
				</ul>
			</div>
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
										fileName="stat141"
										configId="stat141"
										></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="190" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>  
					   <!--  <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "姓名">姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "卡号">卡号</th> -->
					    
					     <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "产品名称">产品名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "卡号">卡号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "营销员工号">营销员工号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "营销员代码">营销员代码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "会员姓名">会员姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "会员联系方式">会员联系方式</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "6" columnChName = "会员身份证号">会员身份证号</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "7" columnChName = "激活时间">激活时间</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "8" columnChName = "紧急联系人姓名">联系人姓名</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "9" columnChName = "紧急联系人电话">联系人电话</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "10" columnChName = "客户名称">客户名称</th>
					    
					</tr>
				  </thead>
				  <tbody>
					
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
						<td align="center">
							${status.count }
						</td>
						<td align="center">
	            ${members[0]}
						</td>
							<td align="center">
	            ${members[1]}
						</td>
							<td align="center">
	            ${members[2]}
						</td>
							<td align="center">
	            ${members[3]}
						</td>
							<td align="center">
	            ${members[4]}
						</td>
							<td align="center">
	            ${members[5]}
						</td>
							<td align="center">
	            ${members[6]}
						</td>
							<td align="center">
	            ${members[7]}
						</td>
							<td align="center">
	            ${members[8]}
						</td>
							<td align="center">
	            ${members[9]}
						</td>
							<td align="center">
	            ${members[10]}
						</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>