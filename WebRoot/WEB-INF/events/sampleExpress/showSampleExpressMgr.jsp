<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<div class="tip">
	<span>样本快递费明细</span>
</div>
<div class="pageHeader">
	<form id="addSampleExpressMgr_id" onsubmit="return validateCallback(this, navTabAjaxDone);" 
		action="${path}/events/sampleExpressMgr!saveSampleExpressMgr.action" method="post" class="pageForm required-validate">
	<div class="searchBar">
		<table class="searchContent">
			<tbody>
				<tr>
					<td>
						<label>快递单号：</label>
						<input type="text" name="expressNum" id="addSamExpNum" value="${expressNum}" style="margin-left:5px;" readonly="readonly"/>
					</td>
					<td>
						<label>快递公司：</label>
						<c:forEach items="${exprComps}" var="comp">
							<c:if test="${comp.id==expressCompanyId}">
								<input type="text" name="expressCompanyId" value="${comp.companyName}" style="margin-left:5px;" readonly="readonly"/>
							</c:if>
						</c:forEach>
					</td>
					<td>
						<label>重量：</label>
						<input type="text" name="weight" value="${weight}" style="margin-left:5px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>费用：</label>
						<input type="text" name="totalCost" value="${totalCost}" class="required" style="margin-left:5px;" readonly="readonly"/>
					</td>
					<td>
						<label>收发件日期：</label> 
						<input type="text" name="receiveSendDate" style="width: 170px;margin-left:5px;float:left" readonly="readonly" value="${fn:substring(receiveSendDate,0,14)}00:00"/>
					</td>
					<td>
						<label style="margin-top:3px;">发票有无：</label>
							<input type="radio" name="isbill" value="1" style="vertical-align:text-bottom" <c:if test="${isbill=='1'}">checked="true"</c:if> />
							<span>有</span>
							<input type="radio" name="isbill" value="0" style="vertical-align:text-bottom" <c:if test="${isbill=='0'}">checked="true"</c:if> />
							<span>无</span>
					</td>
				</tr>
				<tr>
					<td>
						<label>收寄类别：</label>
						<c:if test="${receiveSendType=='1'}">
							<input type="text" name="expressCompanyId" value="收样" style="margin-left:5px;" readonly="readonly"/>
						</c:if>
						<c:if test="${receiveSendType=='2'}">
							<input type="text" name="expressCompanyId" value="寄样" style="margin-left:5px;" readonly="readonly"/>
						</c:if>
					</td>
					<td>
						<label>包裹内容：</label>
						<c:if test="${expressContent=='1'}">
							<input type="text" name="expressContent" value="样本同意书" style="margin-left:5px;" readonly="readonly"/>
						</c:if>
						<c:if test="${expressContent=='2'}">
							<input type="text" name="expressContent" value="样本" style="margin-left:5px;" readonly="readonly"/>
						</c:if>
						<c:if test="${expressContent=='3'}">
							<input type="text" name="expressContent" value="同意书" style="margin-left:5px;" readonly="readonly"/>
						</c:if>
					</td>
					<td></td>
				</tr>
			</tbody>
		</table>
		
	</div>
	</form>
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/sampleExpressMgr!refreshAddSampleExpMgr.action?sampleExpMgrId=${sampleExpMgrId}&expressNum=${expressNum}&
			expressCompanyId=${expressCompanyId}&weight=${weight}&totalCost=${totalCost}&receiveSendDate=${receiveSendDate}&isbill=${isbill}&receiveSendType=${receiveSendType}&
			expressContent=${expressContent}&rootNavTabId=${rootNavTabId}&pageType=${pageType}')
		{this.action = '${path}/events/sampleExpressMgr!refreshAddSampleExpMgr.action?sampleExpMgrId=${sampleExpMgrId}&expressNum=${expressNum}&expressCompanyId=${expressCompanyId}&
		weight=${weight}&totalCost=${totalCost}&receiveSendDate=${receiveSendDate}&isbill=${isbill}&receiveSendType=${receiveSendType}&expressContent=${expressContent}&rootNavTabId=${rootNavTabId}&pageType=${pageType}' ;}; 
		return navTabSearch(this);" 
		action="${path}/events/sampleExpressMgr!refreshAddSampleExpMgr.action?sampleExpMgrId=${sampleExpMgrId}&expressNum=${expressNum}&expressCompanyId=${expressCompanyId}&
		weight=${weight}&totalCost=${totalCost}&receiveSendDate=${receiveSendDate}&isbill=${isbill}&receiveSendType=${receiveSendType}&expressContent=${expressContent}&rootNavTabId=${rootNavTabId}&pageType=${pageType}" 
		method="post"  rel="pagerForm" style="display:none">
	</form>
</div>

<div class="pageContent j-resizeGrid">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>
		<table class="list" style="width:100%; overflow: auto" layoutH="138">
			<thead>
				<tr>
					<th style="width: 45px; ">序号</th>
					<th style="width: 100px; ">批次号</th>
					<th style="width: 200px; ">场次号</th>
					<th style="width: 200px; ">场次日期</th>
					<th style="width: 150px; ">条码</th>
					<th style="width: 80px; ">姓名</th>
					<th style="width: 45px; ">性别</th>
					<th style="width: 40px; ">年龄</th>
					<th style="width: 200px; ">套餐</th>
					<th style="width: 200px; ">支公司</th>
					<th style="width: 200px; ">所属公司</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="erpCustomer" varStatus="status">
					<tr target="sid_user" rel="${erpCustomer.id }">
						<td align="left">${ status.count }</td>
						<td align="center">${erpCustomer.batchno}</td>
						<td align="center">${erpCustomer.eventsNo}</td>
						<td align="center">${fn:substring(erpCustomer.eventsDate,0,14)}00:00</td>
						<td align="center">${erpCustomer.code}</td>
						<td align="center">${erpCustomer.name}</td>
						<td align="center">${erpCustomer.sex}</td>
						<td align="center">${erpCustomer.age}</td>
						<td align="center">${erpCustomer.setmealName}</td>
						<td align="center"><hpin:id2nameDB id="${erpCustomer.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
						<td align="center"><hpin:id2nameDB id="${erpCustomer.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>
</div>
</div>





