<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tip"><span>查询</span></div>
<style>
table td{
word-break: keep-all;
white-space:nowrap;
}
table th{
word-break: keep-all;
white-space:nowrap;
}
</style>
<div class="pageHeader" style="background:white">
	<form onsubmit="return dwzSearch(this, 'dialog');" action="${ path }/warehouse/storeApplication!lookUpStoreApplication.action" method="post" rel = "pagerForm" >
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>总公司名称：</label>
					<input type="text"  name="filter_and_ownedCompany_LIKE_S" value="${ filter_and_ownedCompany_LIKE_S }"/>
				</td>	
				<td>
					<label>支公司名称：</label>
					<input type="text"  name="filter_and_bannyCompanyName_LIKE_S" value="${ filter_and_bannyCompanyName_LIKE_S }"/>
				</td>	
		</tr>		
		<tr>
					<td>
					<label>收件人：</label>
					<input type="text"  name="filter_and_receiveName_LIKE_S" value="${ filter_and_receiveName_LIKE_S }"/>
				</td>		
					
				
				
					<td>
					<label>联系电话：</label>
					<input type="text"  name="filter_and_receiveTel_LIKE_S" value="${ filter_and_receiveTel_LIKE_S }"/>
				</td>		
		</tr>		
		<tr>		
				<td>
					<label>申请开始日期：</label>				
				  <input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2015-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})" class="required" readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td>
					<label>截止日期：</label>					
				<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
		</tr>
		<tr>
					<td>
					<label>申请批次号：</label>
					<input type="text"  name="filter_and_batNo_LIKE_S" value="${ filter_and_batNo_LIKE_S }"/>
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
		<jsp:include page="/common/paginationDialog.jsp" />	
	</div>
	<table class="table" width="100%" layoutH="200">
		<thead>
			<tr>
				<th width="50px">带回</th>
				<th><nobr>申请批次号</nobr></th>
				<th><nobr>总公司名称</nobr></th>
				<th><nobr>支公司名称</nobr></th>
				<th><nobr>项目负责人</nobr></th>
				<th><nobr>项目归属</nobr></th>
				<th><nobr>项目编码</nobr></th>
				<th><nobr>对接人</nobr></th>
				<th><nobr>对接人联系电话</nobr></th>
				<th><nobr>使用日期</nobr></th>
				<th><nobr>状态</nobr></th>
				<th><nobr>申请时间</nobr></th>
				<th><nobr>申请人</nobr></th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="storeApplication" varStatus="status">		
			<tr target="sid_user" rel="${storeApplication.id }">
				<td><a class="btnSelect" href="javascript:$.bringBack({id:'${storeApplication.id }', batNo:'${ storeApplication.batNo }', ownedCompany:'${ storeApplication.ownedCompany }', ownedCompanyId:'${ storeApplication.ownedCompanyId }',bannyCompanyName:'${ storeApplication.bannyCompanyName }',bannyCompanyId:'${ storeApplication.bannyCompanyId }',receiveName:'${ storeApplication.receiveName }',receiveId:'${ storeApplication.receiveId }',receiveTel:'${ storeApplication.receiveTel }',useDate:'${ fn:substring(storeApplication.useDate,0,10) }',remark1:'${storeApplication.remark1}',remark2:'${storeApplication.remark2}',remark3:'${storeApplication.remark3}',createUserName:'${ storeApplication.createUserName }',address:'${ storeApplication.address }',requires:'${ storeApplication.requires }'})" title="查找带回">选择</a>${ status.count }</td>
				<td align="center" nowrap="nowrap">
					<%-- <a href="${ path }/warehouse/storeApplication!browstoreApplication.action?id=${storeApplication.id }" target="navTab" rel="${storeApplication.id }">
					${ storeApplication.batNo }</a>	 --%>
					${ storeApplication.batNo }
				</td>
				<td align="center" nowrap="nowrap">${ storeApplication.ownedCompany }</td>
				<td align="center" nowrap="nowrap">${ storeApplication.bannyCompanyName }</td>		
				<td align="center" nowrap="nowrap">${ storeApplication.receiveName }</td>
				<td align="center" nowrap="nowrap">${ storeApplication.receiveTel }</td>
				<td align="center" nowrap="nowrap">${ fn:substring(storeApplication.useDate , 0 , 10 )}</td>	
					<td align="center" nowrap="nowrap">
					    <c:choose>			  	  	  
				  	  	    <c:when test="${ storeApplication.status==0 }">未完成</c:when>
				  	  	    <c:when test="${ storeApplication.status==1 }">处理中</c:when>	
				  	  	     <c:when test="${ storeApplication.status==2 }">已完成</c:when>	   	  	    	  	  	    
				     </c:choose>	
					</td>							
				<td align="center" nowrap="nowrap">${ fn:substring(storeApplication.createTime , 0 , 19 )}</td> 
				<td align="center" nowrap="nowrap">${ storeApplication.createUserName }</td>			  		
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>