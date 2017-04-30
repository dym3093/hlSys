<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/erpCustomerBak!listCustomerBak.action'){this.action = '${path}/events/erpCustomerBak!listCustomerBak.action' ;} ;return navTabSearch(this);" action="${path}/events/erpCustomerBak!listCustomerBak.action" method="post">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>场次号：</label> 
					<input type="text" name="filter_and_eventsNo_LIKE_S" value="${filter_and_eventsNo_LIKE_S}"/>
				</td>
				<td>
					<label>姓名：</label> 
					<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}"/>
				</td>
				<td>
					<label>条形码：</label> 
					<input type="text" name="filter_and_code_LIKE_S" value="${filter_and_code_LIKE_S}"/>
				</td>
				<td>
					<label>电话：</label> 
					<input type="text" name="filter_and_phone_LIKE_S" value="${filter_and_phone_LIKE_S}"/><input type="hidden" name="id" value="${events.id}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
					
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
		<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		<ul class="toolBar">
			<li><a class="delete" href="${path}/events/erpCustomerBak!delCustomerBak.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
		 <web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.events.web.ErpCustomerBakAction" 
								informationTableId="exportExcelTable"
								fileName="CustomerBak"></web:exportExcelTag> 
		
		</ul>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "eventsNo" columnChName = "场次号" >场次号</th>
				<th  export= "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th  export = "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th  export = "true" columnEnName = "idno" columnChName = "身份证号" >身份证号</th>
				<th  export = "true" columnEnName = "sex" columnChName = "性别" >性别</th>
				<th  export = "true" columnEnName = "age" columnChName = "年龄" >年龄</th>
				<th  export = "true" columnEnName = "phone" columnChName = "电话" >电话</th>
				<th  export = "true" columnEnName = "branchCompany" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "setmealName" columnChName = "套餐名" >套餐名</th>
				<th  export = "true" columnEnName = "sampleType" columnChName = "样本类型" >样本类型</th>
				<th  export = "true" columnEnName = "samplingDate" columnChName = "采样时间" >采样时间</th>
				<th  export = "true" columnEnName = "familyHistory" columnChName = "家族疾病史" >家族疾病史</th>
				<th  export = "true" columnEnName = "salesMan" columnChName = "保险营销员" >保险营销员</th>
				<th  export = "true" columnEnName = "ownedCompany" columnChName = "所属公司" >所属公司</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpCustomer" varStatus="status">
					<tr target="sid_user" rel="${erpCustomer.id }">
						<td align="center"><input type="checkbox" name="ids" value="${erpCustomer.id}">${ status.count }</td>
						<td align="center">	${erpCustomer.eventsNo}</td>
						<td align="center">	${erpCustomer.code}</td>
						<td align="center">	${erpCustomer.name}</td>
						<td align="center">	${erpCustomer.idno}</td>
						<td align="center">	${erpCustomer.sex}</td>
						<td align="center">	${erpCustomer.age}</td>
						<td align="center">	${erpCustomer.phone}</td>
						<td align="center">	${erpCustomer.branchCompany}</td>
						<td align="center">	${erpCustomer.setmealName}</td>
						<td align="center">	${erpCustomer.sampleType}</td>
						<td align="center">	${fn:substring(erpCustomer.samplingDate,0,19)}</td>
						<td align="center">	${erpCustomer.familyHistory}</td>
						<td align="center">	${erpCustomer.salesMan}</td>
						<td align="center">	${erpCustomer.ownedCompany}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 